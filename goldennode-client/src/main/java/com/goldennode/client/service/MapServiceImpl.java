package com.goldennode.client.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import com.goldennode.client.GoldenNodeException;
import com.goldennode.client.RestClient;
import com.goldennode.client.Utils;

public class MapServiceImpl<K, V> implements MapService<K, V> {
    @Override
    public int size(String id) throws GoldenNodeException {
        return ((Integer) RestClient.call("/goldennode/map/id/{mapId}/size".replace("{mapId}", id), "GET").getEntityValue()).intValue();
    }

    @Override
    public boolean isEmpty(String id) throws GoldenNodeException {
        return ((Boolean) RestClient.call("/goldennode/map/id/{mapId}/isEmpty".replace("{mapId}", id), "GET").getEntityValue()).booleanValue();
    }

    @Override
    public boolean containsKey(String id, Object key) throws GoldenNodeException {
        return ((Boolean) RestClient.call("/goldennode/map/id/{mapId}/containsKey/key/{key}".replace("{mapId}", id).replace("{key}", Utils.encode(Utils.encapObject(key))), "GET").getEntityValue())
                .booleanValue();
    }

    @Override
    public boolean containsValue(String id, Object value) throws GoldenNodeException {
        return ((Boolean) RestClient.call("/goldennode/map/id/{mapId}/containsValue/value/{value}".replace("{mapId}", id).replace("{value}", Utils.encode(Utils.encapObject(value))), "GET")
                .getEntityValue()).booleanValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public V get(String id, Object key) throws GoldenNodeException {
        return (V) RestClient.call("/goldennode/map/id/{mapId}/get/key/{key}".replace("{mapId}", id).replace("{key}", Utils.encode(Utils.encapObject(key))), "GET").getEntityValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public V put(String id, K key, V value) throws GoldenNodeException {
        return (V) RestClient.call("/goldennode/map/id/{mapId}/put/key/{key}".replace("{mapId}", id).replace("{key}", Utils.encode(Utils.encapObject(key))), "POST", Utils.encapObject(value))
                .getEntityValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public V remove(String id, Object key) throws GoldenNodeException {
        return (V) RestClient.call("/goldennode/map/id/{mapId}/remove/key/{key}".replace("{mapId}", id).replace("{key}", Utils.encode(Utils.encapObject(key))), "DELETE").getEntityValue();
    }

    @Override
    public void putAll(String id, Map<? extends K, ? extends V> m) throws GoldenNodeException {
        RestClient.call("/goldennode/map/id/{mapId}/putAll".replace("{mapId}", id), "POST", Utils.toJsonString(m));
    }

    @Override
    public void clear(String id) throws GoldenNodeException {
        RestClient.call("/goldennode/map/id/{mapId}/clear".replace("{mapId}", id), "DELETE");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<K> keySet(String id) throws GoldenNodeException {
        return new HashSet<K>(Arrays.asList((K[]) RestClient.call("/goldennode/map/id/{mapId}/keySet".replace("{mapId}", id), "GET").getEntityValue()));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<V> values(String id) throws GoldenNodeException {
        return Arrays.asList((V[]) RestClient.call("/goldennode/map/id/{mapId}/values".replace("{mapId}", id), "GET").getEntityValue());
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Set<Entry<K, V>> entrySet(String id) throws GoldenNodeException {
        return new HashSet(Arrays.asList((Object[]) RestClient.call("/goldennode/map/id/{mapId}/entrySet".replace("{mapId}", id), "GET").getEntityValue()));
    }
}
