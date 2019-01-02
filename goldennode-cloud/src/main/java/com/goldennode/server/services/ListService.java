package com.goldennode.server.services;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.core.HazelcastInstance;

@Service
public class ListService {
    //private static final Logger LOGGER = LoggerFactory.getLogger(ListService.class);
    @Autowired
    private HazelcastInstance hzInstance;

    public ListService(HazelcastInstance hzInstance) {
        this.hzInstance = hzInstance;
    }

    private List<String> init(String userId, String listId) {
        return hzInstance.getList(userId + "_" + listId);
    }

    public int size(String userId, String listId) {
        return init(userId, listId).size();
    }

    public boolean isEmpty(String userId, String listId) {
        return init(userId, listId).isEmpty();
    }

    public boolean contains(String userId, String listId, String element) {
        return init(userId, listId).contains(element);
    }

    public Iterator<String> iterator(String userId, String listId) {
        return init(userId, listId).iterator();
    }

    public String[] toArray(String userId, String listId) {
        return init(userId, listId).toArray(new String[0]);
    }

    public boolean add(String userId, String listId, String element) {
        return init(userId, listId).add(element);
    }

    public boolean remove(String userId, String listId, String element) {
        return init(userId, listId).remove(element);
    }

    public boolean containsAll(String userId, String listId, Collection<String> collection) {
        return init(userId, listId).containsAll(collection);
    }

    public boolean addAll(String userId, String listId, Collection<String> collection) {
        return init(userId, listId).addAll(collection);
    }

    public boolean addAll(String userId, String listId, int index, Collection<String> collection) {
        return init(userId, listId).addAll(collection);
    }

    public boolean removeAll(String userId, String listId, Collection<String> collection) {
        return init(userId, listId).removeAll(collection);
    }

    public boolean retainAll(String userId, String listId, Collection<String> collection) {
        return init(userId, listId).retainAll(collection);
    }

    public void clear(String userId, String listId) {
        init(userId, listId).clear();
    }

    public String get(String userId, String listId, int index) {
        return  (String)init(userId, listId).get(index);
    }

    public String set(String userId, String listId, int index, String element) {
        return (String)init(userId, listId).set(index, element);
    }

    public void add(String userId, String listId, int index, String element) {
        init(userId, listId).add(index, element);
    }

    public String remove(String userId, String listId, int index) {
        return init(userId, listId).remove(index);
    }

    public int indexOf(String userId, String listId, String element) {
        return init(userId, listId).indexOf(element);
    }

    public int lastIndexOf(String userId, String listId, String element) {
        return init(userId, listId).lastIndexOf(element);
    }

    public List<String> subList(String userId, String listId, int fromIndex, int toIndex) {
        return init(userId, listId).subList(fromIndex, toIndex);
    }
}
