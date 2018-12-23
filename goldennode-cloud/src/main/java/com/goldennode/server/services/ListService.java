package com.goldennode.server.services;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.core.HazelcastInstance;

@Service
public class ListService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListService.class);
    @Autowired
    private HazelcastInstance hzInstance;

    public ListService(HazelcastInstance hzInstance) {
        this.hzInstance = hzInstance;
    }

    private List init(String userId, String objectId) {
        return hzInstance.getList(userId + "_" + objectId);
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
        return init(userId, objectId).toArray(a);
    }

    public boolean add(String userId, String objectId, Object e) {
        return init(userId, objectId).add(e);
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

    public boolean addAll(String userId, String objectId, int index, Collection c) {
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

    public Object get(String userId, String objectId, int index) {
        return init(userId, objectId).get(index);
    }

    public Object set(String userId, String objectId, int index, Object element) {
        return init(userId, objectId).set(index, element);
    }

    public void add(String userId, String objectId, int index, Object element) {
        init(userId, objectId).add(index, element);
    }

    public Object remove(String userId, String objectId, int index) {
        return init(userId, objectId).remove(index);
    }

    public int indexOf(String userId, String objectId, Object o) {
        return init(userId, objectId).indexOf(o);
    }

    public int lastIndexOf(String userId, String objectId, Object o) {
        return init(userId, objectId).lastIndexOf(o);
    }

    public ListIterator listIterator(String userId, String objectId) {
        return init(userId, objectId).listIterator();
    }

    public ListIterator listIterator(String userId, String objectId, int index) {
        return init(userId, objectId).listIterator(index);
    }

    public List subList(String userId, String objectId, int fromIndex, int toIndex) {
        return init(userId, objectId).subList(fromIndex, toIndex);
    }
}
