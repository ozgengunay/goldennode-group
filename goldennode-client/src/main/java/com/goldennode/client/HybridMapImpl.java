package com.goldennode.client;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HybridMapImpl<K, V> implements HybridMap<K,V> {
	Map<K, V> cloudMap;
	Map<K, V> localMap;

	public HybridMapImpl() {
		cloudMap = new GoldenNodeMap<>();
		localMap = new ConcurrentHashMap<>();
	}

	@Override
	public int size() {
		return cloudMap.size() + localMap.size();
	}

	@Override
	public boolean isEmpty() {
		return (cloudMap.size() + localMap.size()) == 0;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V remove(Object key) {
		if (localMap.containsKey(key)) {
			return localMap.remove(key);
		} else if (cloudMap.containsKey(key)) {
			return cloudMap.remove(key);
		} else {
			return null;
		}
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		// TODO Auto-generated method stub
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
	public void moveAllDataTo(Storage storage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void populate(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reconfigure(Options options) {
		// TODO Auto-generated method stub
		
	}
}
