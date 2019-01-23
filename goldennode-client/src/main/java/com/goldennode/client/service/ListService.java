package com.goldennode.client.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import com.goldennode.client.GoldenNodeException;

public interface ListService<E> extends Service {
    public int size(String listId) throws GoldenNodeException;

    public boolean isEmpty(String listId) throws GoldenNodeException;

    public boolean contains(String listId, Object o) throws GoldenNodeException;

    public Iterator<E> iterator(String listId) throws GoldenNodeException;

    public Object[] toArray(String listId) throws GoldenNodeException;

    public <T> T[] toArray(String listId, T[] a) throws GoldenNodeException;

    public boolean add(String listId, E e) throws GoldenNodeException;

    public boolean remove(String listId, Object o) throws GoldenNodeException;

    public boolean containsAll(String listId, Collection<?> c) throws GoldenNodeException;

    public boolean addAll(String listId, Collection<? extends E> c) throws GoldenNodeException;

    public boolean addAll(String listId, int index, Collection<? extends E> c) throws GoldenNodeException;

    public boolean removeAll(String listId, Collection<?> c) throws GoldenNodeException;

    public boolean retainAll(String listId, Collection<?> c) throws GoldenNodeException;

    public void clear(String listId) throws GoldenNodeException;

    public E get(String listId, int index) throws GoldenNodeException;

    public E set(String listId, int index, E element) throws GoldenNodeException;

    public void add(String listId, int index, E element) throws GoldenNodeException;

    public E remove(String listId, int index) throws GoldenNodeException;

    public int indexOf(String listId, Object o) throws GoldenNodeException;

    public int lastIndexOf(String listId, Object o) throws GoldenNodeException;

    public ListIterator<E> listIterator(String listId) throws GoldenNodeException;

    public ListIterator<E> listIterator(String listId, int index) throws GoldenNodeException;

    public List<E> subList(String listId, int fromIndex, int toIndex) throws GoldenNodeException;
}
