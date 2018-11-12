package com.goldennode.server.controllers.rest.controllers;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.core.HazelcastInstance;

@Service
public class MapService {
    @Autowired
    private HazelcastInstance hzInstance;

    public MapService() {
    }

    public int size(String userId, String mapId) {
        return init(userId, mapId).size();
    }

    public boolean isEmpty(String userId, String mapId) {
        return init(userId, mapId).isEmpty();
    }

    public boolean containsKey(String userId, String mapId, Object key) {
        return init(userId, mapId).containsKey(key);
    }

    public boolean containsValue(String userId, String mapId, Object value) {
        return init(userId, mapId).containsValue(value);
    }

    public Object get(String userId, String mapId, Object key) {
        return init(userId, mapId).get(key);
    }

    public Object put(String userId, String mapId, Object key, Object value) {
        return init(userId, mapId).put(key, value);
    }

    public Object remove(String userId, String mapId, Object key) {
        return init(userId, mapId).remove(key);
    }

    public void clear(String userId, String mapId) {
        init(userId, mapId).clear();
    }

    public Set<Object> keySet(String userId, String mapId) {
        return init(userId, mapId).keySet();
    }

    public Collection<Object> values(String userId, String mapId) {
        return init(userId, mapId).values();
    }

    
    public void putAll(String userId, String mapId, Map<?,?> m) {
        
        init(userId,mapId).putAll(m);
    }

    public Set<Entry<Object,Object>> entrySet(String userId, String mapId) {
      
        return init(userId,mapId).entrySet();
    }
    
    private Map init(String userId, String mapId) {
        return hzInstance.getMap(userId + "_" + mapId);
    }

}
