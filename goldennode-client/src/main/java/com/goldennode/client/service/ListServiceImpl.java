package com.goldennode.client.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goldennode.client.GoldenNodeException;
import com.goldennode.client.RestClient;
import com.goldennode.client.Utils;

public class ListServiceImpl<E> implements ListService<E> {
    ObjectMapper om = new ObjectMapper();

    public int size(String listId) throws GoldenNodeException {
        return ((Integer) RestClient.call("/goldennode/list/id/{listId}/size".replace("{listId}", listId), "GET").getEntityValue()).intValue();
    }

    public boolean isEmpty(String listId) throws GoldenNodeException {
        return ((Boolean) RestClient.call("/goldennode/list/id/{listId}/isEmpty".replace("{listId}", listId), "GET").getEntityValue()).booleanValue();
    }

    public boolean contains(String listId, Object object) throws GoldenNodeException {
        return ((Boolean) RestClient.call("/goldennode/list/id/{listId}/contains/object/{object}".replace("{listId}", listId).replace("{object}", Utils.encode(Utils.encapObject(object))), "GET")
                .getEntityValue()).booleanValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<E> iterator(String listId) throws GoldenNodeException {
        return Arrays.asList((E[]) RestClient.call("/goldennode/list/id/{listId}/toArray".replace("{listId}", listId), "GET").getEntityValue()).iterator();
    }

    @Override
    public Object[] toArray(String listId) throws GoldenNodeException {
        return (Object[]) RestClient.call("/goldennode/list/id/{listId}/toArray".replace("{listId}", listId), "GET").getEntityValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(String listId, T[] a) throws GoldenNodeException {
        return Arrays.asList((T[]) RestClient.call("/goldennode/list/id/{listId}/toArray".replace("{listId}", listId), "GET").getEntityValue()).toArray(a);
    }

    @Override
    public boolean add(String listId, E element) throws GoldenNodeException {
        return ((Boolean) RestClient.call("/goldennode/list/id/{listId}/add".replace("{listId}", listId), "POST", Utils.encapObject(element)).getEntityValue()).booleanValue();
    }

    @Override
    public boolean remove(String listId, Object object) throws GoldenNodeException {
        return ((Boolean) RestClient.call("/goldennode/list/id/{listId}/remove/object/{object}".replace("{listId}", listId).replace("{object}", Utils.encode(Utils.encapObject(object))), "DELETE")
                .getEntityValue()).booleanValue();
    }

    @Override
    public boolean containsAll(String listId, Collection<?> c) throws GoldenNodeException {
        return ((Boolean) RestClient.call("/goldennode/list/id/{listId}/containsAll".replace("{listId}", listId), "POST", Utils.toJsonString(c)).getEntityValue()).booleanValue();
    }

    @Override
    public boolean addAll(String listId, Collection<? extends E> c) throws GoldenNodeException {
        return ((Boolean) RestClient.call("/goldennode/list/id/{listId}/addAll".replace("{listId}", listId), "POST", Utils.toJsonString(c)).getEntityValue()).booleanValue();
    }

    @Override
    public boolean addAll(String listId, int index, Collection<? extends E> c) throws GoldenNodeException {
        return ((Boolean) RestClient.call("/goldennode/list/id/{listId}/addAll/index/{index}".replace("{listId}", listId).replace("{index}", Integer.toString(index)), "POST", Utils.toJsonString(c))
                .getEntityValue()).booleanValue();
    }

    @Override
    public boolean removeAll(String listId, Collection<?> c) throws GoldenNodeException {
        return ((Boolean) RestClient.call("/goldennode/list/id/{listId}/removeAll".replace("{listId}", listId), "PUT", Utils.toJsonString(c)).getEntityValue()).booleanValue();
    }

    @Override
    public boolean retainAll(String listId, Collection<?> c) throws GoldenNodeException {
        return ((Boolean) RestClient.call("/goldennode/list/id/{listId}/retainAll".replace("{listId}", listId), "PUT", Utils.toJsonString(c)).getEntityValue()).booleanValue();
    }

    @Override
    public void clear(String listId) throws GoldenNodeException {
        RestClient.call("/goldennode/list/id/{listId}/clear".replace("{listId}", listId), "DELETE");
    }

    @SuppressWarnings("unchecked")
    @Override
    public E get(String listId, int index) throws GoldenNodeException {
        return (E) RestClient.call("/goldennode/list/id/{listId}/get/index/{index}".replace("{listId}", listId).replace("{index}", Integer.toString(index)), "GET").getEntityValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public E set(String listId, int index, E element) throws GoldenNodeException {
        return (E) RestClient.call("/goldennode/list/id/{listId}/set/index/{index}".replace("{listId}", listId).replace("{index}", Integer.toString(index)), "PUT", Utils.encapObject(element))
                .getEntityValue();
    }

    @Override
    public void add(String listId, int index, E element) throws GoldenNodeException {
        RestClient.call("/goldennode/list/id/{listId}/add/index/{index}".replace("{listId}", listId).replace("{index}", Integer.toString(index)), "POST", Utils.encapObject(element));
    }

    @SuppressWarnings("unchecked")
    @Override
    public E remove(String listId, int index) throws GoldenNodeException {
        return ((E) RestClient.call("/goldennode/list/id/{listId}/remove/index/{index}".replace("{listId}", listId).replace("{index}", Integer.toString(index)), "DELETE").getEntityValue());
    }

    @Override
    public int indexOf(String listId, Object object) throws GoldenNodeException {
        return ((Integer) RestClient.call("/goldennode/list/id/{listId}/indexOf/object/{object}".replace("{listId}", listId).replace("{object}", Utils.encode(Utils.encapObject(object))), "GET")
                .getEntityValue()).intValue();
    }

    @Override
    public int lastIndexOf(String listId, Object object) throws GoldenNodeException {
        return ((Integer) RestClient.call("/goldennode/list/id/{listId}/lastIndexOf/object/{object}".replace("{listId}", listId).replace("{object}", Utils.encode(Utils.encapObject(object))), "GET")
                .getEntityValue()).intValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public ListIterator<E> listIterator(String listId) throws GoldenNodeException {
        return Arrays.asList((E[]) RestClient.call("/goldennode/list/id/{listId}/toArray".replace("{listId}", listId), "GET").getEntityValue()).listIterator();
    }

    @SuppressWarnings("unchecked")
    @Override
    public ListIterator<E> listIterator(String listId, int index) throws GoldenNodeException {
        return Arrays.asList((E[]) RestClient.call("/goldennode/list/id/{listId}/toArray".replace("{listId}", listId), "GET").getEntityValue()).listIterator(index);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<E> subList(String listId, int fromIndex, int toIndex) throws GoldenNodeException {
        return Arrays.asList((E[]) RestClient.call("/goldennode/list/id/{listId}/subList/fromIndex/{fromIndex}/toIndex/{toIndex}".replace("{listId}", listId)
                .replace("{fromIndex}", Integer.toString(fromIndex)).replace("{toIndex}", Integer.toString(toIndex)), "GET").getEntityValue());
    }
}
