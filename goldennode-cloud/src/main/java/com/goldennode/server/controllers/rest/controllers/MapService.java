package com.goldennode.server.controllers.rest.controllers;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

@Service
public class MapService implements Map<Object, Object>, DistributedObject {
    Map<Object, Object> map;
    @Autowired
    private HazelcastInstance hzInstance;

    public MapService() {

    }
    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Object get(Object key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object put(Object key, Object value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object remove(Object key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void putAll(Map<? extends Object, ? extends Object> m) {
        // TODO Auto-generated method stub
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
    }

    @Override
    public Set<Object> keySet() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Object> values() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Entry<Object, Object>> entrySet() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void init(String userId, String key) {
        
        map = hzInstance.getMap(userId + "_" + key);
    }

}
