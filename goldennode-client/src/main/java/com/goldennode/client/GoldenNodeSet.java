package com.goldennode.client;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import com.goldennode.client.service.SetService;
import com.goldennode.client.service.SetServiceImpl;

public class GoldenNodeSet<E> implements Set<E> {
    SetService<E> service;
    private String setId;

    public GoldenNodeSet(String setId) {
        this.setId = setId;
        service = new SetServiceImpl<>();
    }

    @Override
    public int size() {
        try {
            return service.size(setId);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    @Override
    public boolean isEmpty() {
        try {
            return service.isEmpty(setId);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    @Override
    public boolean contains(Object o) {
        try {
            return service.contains(setId, o);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    @Override
    public Iterator<E> iterator() {
        try {
            return service.iterator(setId);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    @Override
    public Object[] toArray() {
        try {
            return service.toArray(setId);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    @Override
    public <T> T[] toArray(T[] a) {
        try {
            return service.toArray(setId, a);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    @Override
    public boolean add(E e) {
        try {
            return service.add(setId, e);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public boolean remove(Object o) {
        try {
            return service.remove(setId, o);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        try {
            return service.containsAll(setId, c);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        try {
            return service.addAll(setId, c);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        try {
            return service.removeAll(setId, c);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        try {
            return service.retainAll(setId, c);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public void clear() {
        try {
            service.clear(setId);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }
}
