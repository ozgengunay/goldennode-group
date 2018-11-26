package com.goldennode.client.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import com.goldennode.client.GoldenNodeException;

public interface MapService<K extends Serializable, V extends Serializable> extends Service {
    
    int size(String id) throws GoldenNodeException;

    boolean isEmpty(String id) throws GoldenNodeException;

    boolean containsKey(String id, Object key) throws GoldenNodeException;

    boolean containsValue(String id, Object value) throws GoldenNodeException;

    V get(String id, Object key) throws GoldenNodeException;

    V put(String id, K key, V value) throws GoldenNodeException;

    V remove(String id, Object key) throws GoldenNodeException;

    void putAll(String id, Map<? extends K, ? extends V> m) throws GoldenNodeException;

    void clear(String id) throws GoldenNodeException;

    Set<K> keySet(String id) throws GoldenNodeException;

    Collection<V> values(String id) throws GoldenNodeException;

    Set<Entry<K, V>> entrySet(String id) throws GoldenNodeException;
}
