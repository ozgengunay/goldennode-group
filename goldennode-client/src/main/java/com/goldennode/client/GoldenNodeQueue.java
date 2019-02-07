package com.goldennode.client;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import com.goldennode.client.service.QueueService;
import com.goldennode.client.service.QueueServiceImpl;

public class GoldenNodeQueue<E> implements Queue<E> {
    QueueService<E> service;
    private String id;

    public GoldenNodeQueue(String id) {
        this.id = id;
        service = new QueueServiceImpl<>();
    }

    @Override
    public int size() {
        try {
            return service.size(id);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    @Override
    public boolean isEmpty() {
        try {
            return service.isEmpty(id);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    @Override
    public boolean contains(Object o) {
        try {
            return service.contains(id, o);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    @Override
    public Iterator<E> iterator() {
        try {
            return service.iterator(id);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    @Override
    public Object[] toArray() {
        try {
            return service.toArray(id);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    @Override
    public <T> T[] toArray(T[] a) {
        try {
            return service.toArray(id, a);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    @Override
    public boolean remove(Object o) {
        try {
            return service.remove(id, o);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        try {
            return service.containsAll(id, c);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        try {
            return service.addAll(id, c);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        try {
            return service.removeAll(id, c);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        try {
            return service.retainAll(id, c);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public void clear() {
        try {
            service.clear(id);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public boolean add(E e) {
        try {
            return service.add(id, e);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public boolean offer(E e) {
        try {
            return service.offer(id, e);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public E remove() {
        try {
            return service.remove(id);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public E poll() {
        try {
            return service.poll(id);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public E element() {
        try {
            return service.element(id);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public E peek() {
        try {
            return service.peek(id);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }
}
