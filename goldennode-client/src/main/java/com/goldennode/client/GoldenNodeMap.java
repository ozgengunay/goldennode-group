package com.goldennode.client;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import com.goldennode.client.service.MapService;
import com.goldennode.client.service.MapServiceImpl;

public class GoldenNodeMap<K, V> implements Map<K, V> {
	MapService<K, V> service;
	private String mapId;

	public GoldenNodeMap() {
		this(UUID.randomUUID().toString());
	}

	public GoldenNodeMap(String mapId) {
		this(mapId, new MapServiceImpl<>());
	}

	public GoldenNodeMap(String mapId, MapService<K, V> service) {
		this.mapId = mapId;
		this.service = service;
	}

	public int size() {
		try {
			return service.size(mapId);
		} catch (GoldenNodeException e) {
			throw new GoldenNodeRuntimeException(e);
		}
	}

	public boolean isEmpty() {
		try {
			return service.isEmpty(mapId);
		} catch (GoldenNodeException e) {
			throw new GoldenNodeRuntimeException(e);
		}
	}

	public boolean containsKey(Object key) {
		try {
			return service.containsKey(mapId, key);
		} catch (GoldenNodeException e) {
			throw new GoldenNodeRuntimeException(e);
		}
	}

	public boolean containsValue(Object value) {
		try {
			return service.containsValue(mapId, value);
		} catch (GoldenNodeException e) {
			throw new GoldenNodeRuntimeException(e);
		}
	}

	public V get(Object key) {
		try {
			return service.get(mapId, key);
		} catch (GoldenNodeException e) {
			throw new GoldenNodeRuntimeException(e);
		}
	}

	public V put(K key, V value) {
		try {
			return service.put(mapId, key, value);
		} catch (GoldenNodeException e) {
			throw new GoldenNodeRuntimeException(e);
		}
	}

	public V remove(Object key) {
		try {
			return service.remove(mapId, key);
		} catch (GoldenNodeException e) {
			throw new GoldenNodeRuntimeException(e);
		}
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		try {
			service.putAll(mapId, m);
		} catch (GoldenNodeException e) {
			throw new GoldenNodeRuntimeException(e);
		}
	}

	public void clear() {
		try {
			service.clear(mapId);
		} catch (GoldenNodeException e) {
			throw new GoldenNodeRuntimeException(e);
		}
	}

	public Set<K> keySet() {
		try {
			return service.keySet(mapId);
		} catch (GoldenNodeException e) {
			throw new GoldenNodeRuntimeException(e);
		}
	}

	public Collection<V> values() {
		try {
			return service.values(mapId);
		} catch (GoldenNodeException e) {
			throw new GoldenNodeRuntimeException(e);
		}
	}

	public Set<Entry<K, V>> entrySet() {
		try {
			return service.entrySet(mapId);
		} catch (GoldenNodeException e) {
			throw new GoldenNodeRuntimeException(e);
		}
	}
}
