package com.goldennode.client.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goldennode.client.GoldenNodeException;
import com.goldennode.client.Response;
import com.goldennode.client.RestClient;
import com.goldennode.client.Utils;

public class SetServiceImpl<E> implements SetService<E> {
    @Override
    public int size(String setId) throws GoldenNodeException {
        return ((Integer) RestClient.call("/goldennode/set/id/{setId}/size".replace("{setId}", setId), "GET").getEntityValue()).intValue();
    }

    @Override
    public boolean isEmpty(String setId) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/set/id/{setId}/isEmpty".replace("{setId}", setId), "GET");
    }

    @Override
    public boolean contains(String setId, Object object) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/set/id/{setId}/contains/object/{object}".replace("{setId}", setId).replace("{object}", Utils.encode(Utils.encapObject(object))), "GET");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<E> iterator(String setId) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/set/id/{setId}/toArray".replace("{setId}", setId), "GET");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object[] toArray(String setId) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/set/id/{setId}/toArray".replace("{setId}", setId), "GET");
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(String setId, T[] a) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/set/id/{setId}/toArray".replace("{setId}", setId), "GET");
    }

    @Override
    public boolean add(String setId, E element) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/set/id/{setId}/add".replace("{setId}", setId), "POST", Utils.encapObject(element));
    }

    @Override
    public boolean remove(String setId, Object object) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/set/id/{setId}/remove/object/{object}".replace("{setId}", setId).replace("{object}", Utils.encode(Utils.encapObject(object))), "DELETE");
    }

    @Override
    public boolean containsAll(String setId, Collection<?> c) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/set/id/{setId}/containsAll".replace("{setId}", setId), "POST", new ObjectMapper().writeValueAsString(temp));
    }

    @Override
    public boolean addAll(String setId, Collection<? extends E> c) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/set/id/{setId}/addAll".replace("{setId}", setId), "POST", new ObjectMapper().writeValueAsString(temp));
    }

    @Override
    public boolean retainAll(String setId, Collection<?> c) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/set/id/{setId}/retainAll".replace("{setId}", setId), "PUT", new ObjectMapper().writeValueAsString(temp));
    }

    @Override
    public boolean removeAll(String setId, Collection<?> c) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/set/id/{setId}/removeAll".replace("{setId}", setId), "PUT", new ObjectMapper().writeValueAsString(temp));
    }

    @Override
    public void clear(String setId) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/set/id/{setId}/clear".replace("{setId}", setId), "DELETE");
    }
}
