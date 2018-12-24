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

    private List init(String userId, String listId) {
        return hzInstance.getList(userId + "_" + listId);
    }

    public int size(String userId, String listId) {
        return init(userId, listId).size();
    }

    public boolean isEmpty(String userId, String listId) {
        return init(userId, listId).isEmpty();
    }

    public boolean contains(String userId, String listId, Object o) {
        return init(userId, listId).contains(o);
    }

    public Iterator iterator(String userId, String listId) {
        return init(userId, listId).iterator();
    }

    public Object[] toArray(String userId, String listId) {
        return init(userId, listId).toArray();
    }

    public Object[] toArray(String userId, String listId, Object[] a) {
        return init(userId, listId).toArray(a);
    }

    public boolean add(String userId, String listId, Object e) {
        return init(userId, listId).add(e);
    }

    public boolean remove(String userId, String listId, Object o) {
        return init(userId, listId).remove(o);
    }

    public boolean containsAll(String userId, String listId, Collection c) {
        return init(userId, listId).containsAll(c);
    }

    public boolean addAll(String userId, String listId, Collection c) {
        return init(userId, listId).addAll(c);
    }

    public boolean addAll(String userId, String listId, int index, Collection c) {
        return init(userId, listId).addAll(c);
    }

    public boolean removeAll(String userId, String listId, Collection c) {
        return init(userId, listId).removeAll(c);
    }

    public boolean retainAll(String userId, String listId, Collection c) {
        return init(userId, listId).retainAll(c);
    }

    public void clear(String userId, String listId) {
        init(userId, listId).clear();
    }

    public Object get(String userId, String listId, int index) {
        return init(userId, listId).get(index);
    }

    public Object set(String userId, String listId, int index, Object element) {
        return init(userId, listId).set(index, element);
    }

    public void add(String userId, String listId, int index, Object element) {
        init(userId, listId).add(index, element);
    }

    public Object remove(String userId, String listId, int index) {
        return init(userId, listId).remove(index);
    }

    public int indexOf(String userId, String listId, Object o) {
        return init(userId, listId).indexOf(o);
    }

    public int lastIndexOf(String userId, String listId, Object o) {
        return init(userId, listId).lastIndexOf(o);
    }

    public ListIterator listIterator(String userId, String listId) {
        return init(userId, listId).listIterator();
    }

    public ListIterator listIterator(String userId, String listId, int index) {
        return init(userId, listId).listIterator(index);
    }

    public List subList(String userId, String listId, int fromIndex, int toIndex) {
        return init(userId, listId).subList(fromIndex, toIndex);
    }
}
