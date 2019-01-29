package com.goldennode.server.services;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hazelcast.core.HazelcastInstance;

@Service
public class MapService {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(MapService.class);
    @Autowired
    private HazelcastInstance hzInstance;

    public MapService(HazelcastInstance hzInstance) {
        this.hzInstance = hzInstance;
    }

    private Map<String,String> init(String userId, String mapId) {
        return hzInstance.getMap(userId + "_" + mapId);
    }

    public int size(String userId, String mapId) {
        return init(userId, mapId).size();
    }

    public boolean isEmpty(String userId, String mapId) {
        return init(userId, mapId).isEmpty();
    }

    public boolean containsKey(String userId, String mapId, String key) {
        return init(userId, mapId).containsKey(key);
    }

    public boolean containsValue(String userId, String mapId, String value) {
        return init(userId, mapId).containsValue(value);
    }

    public String get(String userId, String mapId, String key) {
        return (String) init(userId, mapId).get(key);
    }

    public String put(String userId, String mapId, String key, String value) {
        String val = (String) init(userId, mapId).put(key, value);
        return val;
    }

    public String remove(String userId, String mapId, String key) {
        return (String) init(userId, mapId).remove(key);
    }

    public void clear(String userId, String mapId) {
        init(userId, mapId).clear();
    }

    public Set<String> keySet(String userId, String mapId) {
        return init(userId, mapId).keySet();
    }

    public Collection<String> values(String userId, String mapId) {
        return init(userId, mapId).values();
    }

    public void putAll(String userId, String mapId, Map<String, String> m) throws IOException {
        init(userId, mapId).putAll(m);
    }

    public Set<Entry<String, String>> entrySet(String userId, String mapId) {
        return init(userId, mapId).entrySet();
    }
}
