package com.goldennode.client.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import com.goldennode.client.GoldenNodeException;
import com.goldennode.client.RestClient;
import com.goldennode.client.Utils;

public class SetServiceImpl<E> implements SetService<E> {
    @Override
    public int size(String setId) throws GoldenNodeException {
        return ((Integer) RestClient.call("/goldennode/set/id/{setId}/size".replace("{setId}", setId), "GET").getEntityValue()).intValue();
    }

    @Override
    public boolean isEmpty(String setId) throws GoldenNodeException {
        return ((Boolean) RestClient.call("/goldennode/set/id/{setId}/isEmpty".replace("{setId}", setId), "GET").getEntityValue()).booleanValue();
    }

    @Override
    public boolean contains(String setId, Object object) throws GoldenNodeException {
        return ((Boolean) RestClient.call("/goldennode/set/id/{setId}/contains/object/{object}".replace("{setId}", setId).replace("{object}", Utils.encode(Utils.encapObject(object))), "GET")
                .getEntityValue()).booleanValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<E> iterator(String setId) throws GoldenNodeException {
        return Arrays.asList((E[]) RestClient.call("/goldennode/set/id/{setId}/toArray".replace("{setId}", setId), "GET").getEntityValue()).iterator();
    }

    @Override
    public Object[] toArray(String setId) throws GoldenNodeException {
        return (Object[]) RestClient.call("/goldennode/set/id/{setId}/toArray".replace("{setId}", setId), "GET").getEntityValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(String setId, T[] a) throws GoldenNodeException {
        return Arrays.asList((E[]) RestClient.call("/goldennode/set/id/{setId}/toArray".replace("{setId}", setId), "GET").getEntityValue()).toArray(a);
    }

    @Override
    public boolean add(String setId, E element) throws GoldenNodeException {
        return ((Boolean) RestClient.call("/goldennode/set/id/{setId}/add".replace("{setId}", setId), "POST", Utils.encapObject(element)).getEntityValue()).booleanValue();
    }

    @Override
    public boolean remove(String setId, Object object) throws GoldenNodeException {
        return ((Boolean) RestClient.call("/goldennode/set/id/{setId}/remove/object/{object}".replace("{setId}", setId).replace("{object}", Utils.encode(Utils.encapObject(object))), "DELETE")
                .getEntityValue()).booleanValue();
    }

    @Override
    public boolean containsAll(String setId, Collection<?> c) throws GoldenNodeException {
        return ((Boolean) RestClient.call("/goldennode/set/id/{setId}/containsAll".replace("{setId}", setId), "POST", Utils.toJsonString(c)).getEntityValue()).booleanValue();
    }

    @Override
    public boolean addAll(String setId, Collection<? extends E> c) throws GoldenNodeException {
        return ((Boolean) RestClient.call("/goldennode/set/id/{setId}/addAll".replace("{setId}", setId), "POST", Utils.toJsonString(c)).getEntityValue()).booleanValue();
    }

    @Override
    public boolean retainAll(String setId, Collection<?> c) throws GoldenNodeException {
        return ((Boolean) RestClient.call("/goldennode/set/id/{setId}/retainAll".replace("{setId}", setId), "PUT", Utils.toJsonString(c)).getEntityValue()).booleanValue();
    }

    @Override
    public boolean removeAll(String setId, Collection<?> c) throws GoldenNodeException {
        return ((Boolean) RestClient.call("/goldennode/set/id/{setId}/removeAll".replace("{setId}", setId), "PUT", Utils.toJsonString(c)).getEntityValue()).booleanValue();
    }

    @Override
    public void clear(String setId) throws GoldenNodeException {
        RestClient.call("/goldennode/set/id/{setId}/clear".replace("{setId}", setId), "DELETE");
    }
}
