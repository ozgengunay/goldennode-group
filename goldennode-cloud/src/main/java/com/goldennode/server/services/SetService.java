package com.goldennode.server.services;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.core.HazelcastInstance;

@Service
public class SetService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SetService.class);
    @Autowired
    private HazelcastInstance hzInstance;

    public SetService(HazelcastInstance hzInstance) {
        this.hzInstance = hzInstance;
    }

    private Set init(String userId, String setId) {
        return hzInstance.getSet(userId + "_" + setId);
    }

    public int size(String userId, String setId) {
        return init(userId, setId).size();
    }

    public boolean isEmpty(String userId, String setId) {
        return init(userId, setId).isEmpty();
    }

    public boolean contains(String userId, String setId, Object o) {
        return init(userId, setId).contains(o);
    }

    public Iterator iterator(String userId, String setId) {
        return init(userId, setId).iterator();
    }

    public Object[] toArray(String userId, String setId) {
        return init(userId, setId).toArray();
    }

    public Object[] toArray(String userId, String setId, Object[] a) {
        return init(userId, setId).toArray();
    }

    public boolean remove(String userId, String setId, Object o) {
        return init(userId, setId).remove(o);
    }

    public boolean containsAll(String userId, String setId, Collection c) {
        return init(userId, setId).containsAll(c);
    }

    public boolean addAll(String userId, String setId, Collection c) {
        return init(userId, setId).addAll(c);
    }

    public boolean removeAll(String userId, String setId, Collection c) {
        return init(userId, setId).removeAll(c);
    }

    public boolean retainAll(String userId, String setId, Collection c) {
        return init(userId, setId).retainAll(c);
    }

    public void clear(String userId, String setId) {
        init(userId, setId).clear();
    }

    public boolean add(String userId, String setId, Object e) {
        return init(userId, setId).add(e);
    }

   
}
