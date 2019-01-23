package com.goldennode.client.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goldennode.client.GoldenNodeException;
import com.goldennode.client.Response;
import com.goldennode.client.RestClient;
import com.goldennode.client.Utils;

public class ListServiceImpl<E> implements ListService<E> {
    ObjectMapper om = new ObjectMapper();

    public int size(String listId) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/list/id/{listId}/size".replace("{listId}", listId), "GET");
        if (response.getStatusCode() == 200)
            return Integer.parseInt((String) response.getEntityValue());
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
        }
    }

    public boolean isEmpty(String listId) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/list/id/{listId}/isEmpty".replace("{listId}", listId), "GET");
        if (response.getStatusCode() == 200) {
            return Boolean.parseBoolean(response.getEntityValue());
        } else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
        }
    }

    public boolean contains(String listId, Object element) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/list/id/{listId}/contains/element".replace("{listId}", listId).replace("{element}", Utils.encode(Utils.encapObject(element))), "GET");
            if (response.getStatusCode() == 200) {
                return Boolean.parseBoolean(response.getEntityValue());
            } else {
                throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
            }
        } catch (JsonProcessingException e) {
            throw new GoldenNodeException(e);
        }
    }

    @Override
    public Iterator<E> iterator(String listId) throws GoldenNodeException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object[] toArray(String listId) throws GoldenNodeException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T[] toArray(String listId, T[] a) throws GoldenNodeException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean add(String listId, E e) throws GoldenNodeException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean remove(String listId, Object o) throws GoldenNodeException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean containsAll(String listId, Collection<?> c) throws GoldenNodeException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean addAll(String listId, Collection<? extends E> c) throws GoldenNodeException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean addAll(String listId, int index, Collection<? extends E> c) throws GoldenNodeException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean removeAll(String listId, Collection<?> c) throws GoldenNodeException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean retainAll(String listId, Collection<?> c) throws GoldenNodeException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void clear(String listId) throws GoldenNodeException {
        // TODO Auto-generated method stub
    }

    @Override
    public E get(String listId, int index) throws GoldenNodeException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public E set(String listId, int index, E element) throws GoldenNodeException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void add(String listId, int index, E element) throws GoldenNodeException {
        // TODO Auto-generated method stub
    }

    @Override
    public E remove(String listId, int index) throws GoldenNodeException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int indexOf(String listId, Object o) throws GoldenNodeException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int lastIndexOf(String listId, Object o) throws GoldenNodeException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ListIterator<E> listIterator(String listId) throws GoldenNodeException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ListIterator<E> listIterator(String listId, int index) throws GoldenNodeException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<E> subList(String listId, int fromIndex, int toIndex) throws GoldenNodeException {
        // TODO Auto-generated method stub
        return null;
    }
}
