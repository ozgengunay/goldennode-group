package com.goldennode.client.service;

import java.util.Collection;
import java.util.Iterator;
import com.goldennode.client.GoldenNodeException;

public interface QueueService<E> extends Service {
    public int size(String queueId) throws GoldenNodeException;

    public boolean isEmpty(String queueId) throws GoldenNodeException;

    public boolean contains(String queueId, Object object) throws GoldenNodeException;

    public Iterator<E> iterator(String queueId) throws GoldenNodeException;

    public Object[] toArray(String queueId) throws GoldenNodeException;

    public <T> T[] toArray(String queueId, T[] a) throws GoldenNodeException;

    public boolean remove(String queueId, Object object) throws GoldenNodeException;

    public boolean containsAll(String queueId, Collection<?> collection) throws GoldenNodeException;

    public boolean addAll(String queueId, Collection<? extends E> collection) throws GoldenNodeException;

    public boolean removeAll(String queueId, Collection<?> collection) throws GoldenNodeException;

    public boolean retainAll(String queueId, Collection<?> collection) throws GoldenNodeException;

    public void clear(String queueId) throws GoldenNodeException;

    public boolean add(String queueId, E element) throws GoldenNodeException;

    public boolean offer(String queueId, E element) throws GoldenNodeException;

    public E remove(String queueId) throws GoldenNodeException;

    public E poll(String queueId) throws GoldenNodeException;

    public E element(String queueId) throws GoldenNodeException;

    public E peek(String queueId) throws GoldenNodeException;
}
