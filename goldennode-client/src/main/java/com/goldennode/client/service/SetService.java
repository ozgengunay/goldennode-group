package com.goldennode.client.service;

import java.util.Collection;
import java.util.Iterator;
import com.goldennode.client.GoldenNodeException;

public interface SetService<E> extends Service {
    public int size(String setId) throws GoldenNodeException;

    public boolean isEmpty(String setId) throws GoldenNodeException;

    public boolean contains(String setId, Object o) throws GoldenNodeException;

    public Iterator<E> iterator(String setId) throws GoldenNodeException;

    public Object[] toArray(String setId) throws GoldenNodeException;

    public <T> T[] toArray(String setId, T[] a) throws GoldenNodeException;

    public boolean add(String setId, E e) throws GoldenNodeException;

    public boolean remove(String setId, Object o) throws GoldenNodeException;

    public boolean containsAll(String setId, Collection<?> c) throws GoldenNodeException;

    public boolean addAll(String setId, Collection<? extends E> c) throws GoldenNodeException;

    public boolean retainAll(String setId, Collection<?> c) throws GoldenNodeException;

    public boolean removeAll(String setId, Collection<?> c) throws GoldenNodeException;

    public void clear(String setId) throws GoldenNodeException;
}
