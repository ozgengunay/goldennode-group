package com.goldennode.client;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import com.goldennode.client.service.ListService;
import com.goldennode.client.service.ListServiceImpl;

public class GoldenNodeList<E> implements List<E> {
    ListService<E> service;
    private String id;

    public GoldenNodeList(String id) {
        this.id = id;
        service = new ListServiceImpl<>();
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
    public boolean add(E e) {
        try {
            return service.add(id, e);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
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
    public boolean addAll(int index, Collection<? extends E> c) {
        try {
            return service.addAll(id, index, c);
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
    public E get(int index) {
        try {
            return service.get(id, index);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public E set(int index, E element) {
        try {
            return service.set(id, index, element);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public void add(int index, E element) {
        try {
            service.add(id, index, element);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public E remove(int index) {
        try {
            return service.remove(id, index);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public int indexOf(Object o) {
        try {
            return service.indexOf(id, o);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public int lastIndexOf(Object o) {
        try {
            return service.lastIndexOf(id, o);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public ListIterator<E> listIterator() {
        try {
            return service.listIterator(id);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        try {
            return service.listIterator(id, index);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        try {
            return service.subList(id, fromIndex, toIndex);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }
}
