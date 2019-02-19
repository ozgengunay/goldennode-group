package com.goldennode.client;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import com.goldennode.client.service.ListService;
import com.goldennode.client.service.ListServiceImpl;

public class GoldenNodeList<E> implements List<E> {
    ListService<E> service;
    private String listId;

    public GoldenNodeList(String listId) {
        this.listId = listId;
        service = new ListServiceImpl<>();
    }

    @Override
    public int size() {
        try {
            return service.size(listId);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    @Override
    public boolean isEmpty() {
        try {
            return service.isEmpty(listId);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    @Override
    public boolean contains(Object o) {
        try {
            return service.contains(listId, o);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    @Override
    public Iterator<E> iterator() {
        try {
            return service.iterator(listId);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    @Override
    public Object[] toArray() {
        try {
            return service.toArray(listId);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    @Override
    public <T> T[] toArray(T[] a) {
        try {
            return service.toArray(listId, a);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    @Override
    public boolean add(E e) {
        try {
            return service.add(listId, e);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public boolean remove(Object o) {
        try {
            return service.remove(listId, o);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        try {
            return service.containsAll(listId, c);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        try {
            return service.addAll(listId, c);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        try {
            return service.addAll(listId, index, c);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        try {
            return service.removeAll(listId, c);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        try {
            return service.retainAll(listId, c);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public void clear() {
        try {
            service.clear(listId);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public E get(int index) {
        try {
            return service.get(listId, index);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public E set(int index, E element) {
        try {
            return service.set(listId, index, element);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public void add(int index, E element) {
        try {
            service.add(listId, index, element);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public E remove(int index) {
        try {
            return service.remove(listId, index);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public int indexOf(Object o) {
        try {
            return service.indexOf(listId, o);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public int lastIndexOf(Object o) {
        try {
            return service.lastIndexOf(listId, o);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public ListIterator<E> listIterator() {
        try {
            return service.listIterator(listId);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        try {
            return service.listIterator(listId, index);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        try {
            return service.subList(listId, fromIndex, toIndex);
        } catch (GoldenNodeException ex) {
            throw new GoldenNodeRuntimeException(ex);
        }
    }
}
