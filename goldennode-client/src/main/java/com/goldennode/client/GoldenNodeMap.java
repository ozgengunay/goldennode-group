package com.goldennode.client;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import com.goldennode.client.service.MapService;
import com.goldennode.client.service.MapServiceImpl;

public class GoldenNodeMap<K, V> implements Map<K, V> {
    MapService<K, V> service;
    private String id;

    public GoldenNodeMap() {
        this(UUID.randomUUID().toString());
    }

    public GoldenNodeMap(String id) {
        this.id = id;
        service = new MapServiceImpl<>();
    }

    public int size() {
        try {
            return service.size(id);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    public boolean isEmpty() {
        try {
            return service.isEmpty(id);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    public boolean containsKey(Object key) {
        try {
            return service.containsKey(id, key);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    public boolean containsValue(Object value) {
        try {
            return service.containsValue(id, value);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    public V get(Object key) {
        try {
            return service.get(id, key);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    public V put(K key, V value) {
        try {
            return service.put(id, key, value);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    public V remove(Object key) {
        try {
            return service.remove(id, key);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        try {
            service.putAll(id, m);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    public void clear() {
        try {
            service.clear(id);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    public Set<K> keySet() {
        try {
            return service.keySet(id);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    public Collection<V> values() {
        try {
            return service.values(id);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    public Set<Entry<K, V>> entrySet() {
        try {
            return service.entrySet(id);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }
}
