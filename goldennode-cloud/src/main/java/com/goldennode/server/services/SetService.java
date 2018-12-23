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

    private Set init(String userId, String objectId) {
        return hzInstance.getSet(userId + "_" + objectId);
    }

    public int size(String userId, String objectId) {
        return init(userId, objectId).size();
    }

    public boolean isEmpty(String userId, String objectId) {
        return init(userId, objectId).isEmpty();
    }

    public boolean contains(String userId, String objectId, Object o) {
        return init(userId, objectId).contains(o);
    }

    public Iterator iterator(String userId, String objectId) {
        return init(userId, objectId).iterator();
    }

    public Object[] toArray(String userId, String objectId) {
        return init(userId, objectId).toArray();
    }

    public Object[] toArray(String userId, String objectId, Object[] a) {
        return init(userId, objectId).toArray();
    }

    public boolean remove(String userId, String objectId, Object o) {
        return init(userId, objectId).remove(o);
    }

    public boolean containsAll(String userId, String objectId, Collection c) {
        return init(userId, objectId).containsAll(c);
    }

    public boolean addAll(String userId, String objectId, Collection c) {
        return init(userId, objectId).addAll(c);
    }

    public boolean removeAll(String userId, String objectId, Collection c) {
        return init(userId, objectId).removeAll(c);
    }

    public boolean retainAll(String userId, String objectId, Collection c) {
        return init(userId, objectId).retainAll(c);
    }

    public void clear(String userId, String objectId) {
        init(userId, objectId).clear();
    }

    public boolean add(String userId, String objectId, Object e) {
        return init(userId, objectId).add(e);
    }

   
}
