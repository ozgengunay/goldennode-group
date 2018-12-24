package com.goldennode.server.services;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.core.HazelcastInstance;

@Service
public class QueueService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueueService.class);
    @Autowired
    private HazelcastInstance hzInstance;

    public QueueService(HazelcastInstance hzInstance) {
        this.hzInstance = hzInstance;
    }

    private Queue init(String userId, String queueId) {
        return hzInstance.getQueue(userId + "_" + queueId);
    }

    public int size(String userId, String queueId) {
        return init(userId, queueId).size();
    }

    public boolean isEmpty(String userId, String queueId) {
        return init(userId, queueId).isEmpty();
    }

    public boolean contains(String userId, String queueId, Object o) {
        return init(userId, queueId).contains(o);
    }

    public Iterator iterator(String userId, String queueId) {
        return init(userId, queueId).iterator();
    }

    public Object[] toArray(String userId, String queueId) {
        return init(userId, queueId).toArray();
    }

    public Object[] toArray(String userId, String queueId, Object[] a) {
        return init(userId, queueId).toArray(a);
    }

    public boolean remove(String userId, String queueId, Object o) {
        return init(userId, queueId).remove(o);
    }

    public boolean containsAll(String userId, String queueId, Collection c) {
        return init(userId, queueId).containsAll(c);
    }

    public boolean addAll(String userId, String queueId, Collection c) {
        return init(userId, queueId).addAll(c);
    }

    public boolean removeAll(String userId, String queueId, Collection c) {
        return init(userId, queueId).removeAll(c);
    }

    public boolean retainAll(String userId, String queueId, Collection c) {
        return init(userId, queueId).retainAll(c);
    }

    public void clear(String userId, String queueId) {
        init(userId, queueId).clear();
    }

    public boolean add(String userId, String queueId, Object e) {
        return init(userId, queueId).add(e);
    }

    public boolean offer(String userId, String queueId, Object e) {
        return init(userId, queueId).offer(e);
    }

    public Object remove(String userId, String queueId) {
        return init(userId, queueId).remove();
    }

    public Object poll(String userId, String queueId) {
        return init(userId, queueId).poll();
    }

    public Object element(String userId, String queueId) {
        return init(userId, queueId).element();
    }

    public Object peek(String userId, String queueId) {
        return init(userId, queueId).peek();
    }
}
