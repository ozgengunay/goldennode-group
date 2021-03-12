package com.goldennode.client;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HybridMapImpl<K, V> implements HybridMap<K, V> {
	private Map<K, V> cloudMap;
	private Map<K, V> localMap;
	private Options options;

	public HybridMapImpl(Options options) {
		this(new GoldenNodeMap<K, V>(UUID.randomUUID().toString()), new ConcurrentHashMap<K, V>(), options);
	}

	public HybridMapImpl(Map<K, V> cloudMap, Map<K, V> localMap, Options options) {
		this.cloudMap = cloudMap;
		this.localMap = localMap;
		this.options = options;
	}

	@Override
	public int size() {
		return cloudMap.size() + localMap.size();
	}

	@Override
	public int cloudSize() {
		return cloudMap.size();
	}

	@Override
	public boolean isEmpty() {
		return (cloudMap.size() + localMap.size()) == 0;
	}

	@Override
	public boolean cloudIsEmpty() {
		return cloudMap.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return (localMap.containsKey(key) || cloudMap.containsKey(key));
	}

	@Override
	public boolean containsValue(Object value) {
		return (localMap.containsValue(value) || cloudMap.containsValue(value));
	}

	@Override
	public V get(Object key) {
		if (localMap.containsKey(key)) {
			return localMap.get(key);
		} else if (cloudMap.containsKey(key)) {
			return cloudMap.get(key);
		} else {
			return null;
		}
	}

	@Override
	public V put(K key, V value) {
		boolean storageLocal;
		if (localMap.containsKey(key)) {
			storageLocal = true;
		} else {
			storageLocal = false;
		}

		if (storageLocal) {
			return localMap.put(key, value);
		} else {
			return decide(1).put(key, value);
		}

	}

	private Map<K, V> decide(int itemsToPut) {
		int localVotes = 0;
		int cloudVotes = 0;
		if (options.getStorageOption() == StorageOption.ONLY_CLOUD) {
			return cloudMap;
		} else if (options.getStorageOption() == StorageOption.ONLY_LOCAL) {
			return localMap;
		}
		if (options.getMaxLocalEntries() != null) {
			int localSize = localMap.size();
			if ((localSize + itemsToPut) <= options.getMaxLocalEntries()) {
				localVotes++;
			} else {
				localVotes = Integer.MIN_VALUE;
				cloudVotes++;
			}
		}
		if (options.getMinLocalFreeMemory() < Options.getSystemFree()) {
			localVotes++;
		} else {
			localVotes = Integer.MIN_VALUE;
			cloudVotes++;
		}
		if (localVotes > 0 && localVotes > cloudVotes) {
			return localMap;
		} else {
			return cloudMap;
		}
	}

	@Override
	public V remove(Object key) {
		if (localMap.containsKey(key))
			return localMap.remove(key);

		return cloudMap.remove(key);

	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		Map<? extends K, ? extends V> map = new HashMap<K, V>(m);
		for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
			if (localMap.containsKey(entry.getKey())) {
				localMap.put(entry.getKey(), entry.getValue());
				map.remove(entry.getKey());
			}
		}

		decide(m.size()).putAll(map);

	}

	@Override
	public void clear() {
		localMap.clear();
		cloudMap.clear();
	}

	@Override
	public Set<K> keySet() {
		return Stream.concat(localMap.entrySet().stream(), cloudMap.entrySet().stream())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).keySet();
	}

	@Override
	public Collection<V> values() {
		return Stream.concat(localMap.entrySet().stream(), cloudMap.entrySet().stream())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return Stream.concat(localMap.entrySet().stream(), cloudMap.entrySet().stream())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).entrySet();
	}

	@Override
	// TODO fix
	public String dump(String fileName) throws IOException {
		String data = Utils.toJsonString(localMap);
		RandomAccessFile stream = new RandomAccessFile(fileName, "rw");
		FileChannel channel = stream.getChannel();
		FileLock lock = null;
		try {
			lock = channel.tryLock();
		} catch (final OverlappingFileLockException e) {
			stream.close();
			channel.close();
		}
		for (int i = 0; i < data.length(); i = i + 1000) {
			stream.writeUTF(data.substring(i, i + 500));
		}
		lock.release();
		stream.close();
		channel.close();
		return fileName;
	}

	@Override
	public String dump() throws IOException {
		File tmpFile = File.createTempFile("hybridMapLocalDump", ".tmp");
		return dump(tmpFile.getAbsolutePath());
	}
}
