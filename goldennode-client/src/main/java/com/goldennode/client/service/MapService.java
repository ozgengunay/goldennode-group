package com.goldennode.client.service;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import com.goldennode.client.GoldenNodeException;

public interface MapService<K, V> extends Service {
    int size(String mapId) throws GoldenNodeException;

    boolean isEmpty(String mapId) throws GoldenNodeException;

    boolean containsKey(String mapId, Object key) throws GoldenNodeException;

    boolean containsValue(String mapId, Object value) throws GoldenNodeException;

    V get(String mapId, Object key) throws GoldenNodeException;

    V put(String mapId, K key, V value) throws GoldenNodeException;

    V remove(String mapId, Object key) throws GoldenNodeException;

    void putAll(String mapId, Map<? extends K, ? extends V> m) throws GoldenNodeException;

    void clear(String mapId) throws GoldenNodeException;

    Set<K> keySet(String mapId) throws GoldenNodeException;

    Collection<V> values(String mapId) throws GoldenNodeException;

    Set<Entry<K, V>> entrySet(String mapId) throws GoldenNodeException;
}
