package com.goldennode.client.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goldennode.client.GoldenNodeException;
import com.goldennode.client.Response;
import com.goldennode.client.RestClient;
import com.goldennode.client.Utils;

public class QueueServiceImpl<E> implements QueueService<E> {
    @Override
    public int size(String queueId) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/queue/id/{queueId}/size".replace("{queueId}", queueId), "GET");
    }

    @Override
    public boolean isEmpty(String queueId) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/queue/id/{queueId}/isEmpty".replace("{queueId}", queueId), "GET");
    }

    @Override
    public boolean contains(String queueId, Object object) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/queue/id/{queueId}/contains/object/{object}".replace("{queueId}", queueId).replace("{object}", Utils.encode(Utils.encapObject(object))),
                "GET");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<E> iterator(String queueId) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/queue/id/{queueId}/iterator".replace("{queueId}", queueId), "GET");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object[] toArray(String queueId) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/queue/id/{queueId}/toArray".replace("{queueId}", queueId), "GET");
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(String queueId, T[] a) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/queue/id/{queueId}/toArray".replace("{queueId}", queueId), "GET");
    }

    @Override
    public boolean remove(String queueId, Object object) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/queue/id/{queueId}/remove/object/{object}".replace("{queueId}", queueId).replace("{object}", Utils.encode(Utils.encapObject(object))),
                "DELETE");
    }

    @Override
    public boolean containsAll(String queueId, Collection<?> collection) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/queue/id/{queueId}/containsAll".replace("{queueId}", queueId), "POST", new ObjectMapper().writeValueAsString(temp));
    }

    @Override
    public boolean addAll(String queueId, Collection<? extends E> collection) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/queue/id/{queueId}/addAll".replace("{queueId}", queueId), "POST", new ObjectMapper().writeValueAsString(temp));
    }

    @Override
    public boolean removeAll(String queueId, Collection<?> collection) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/queue/id/{queueId}/removeAll".replace("{queueId}", queueId), "PUT", new ObjectMapper().writeValueAsString(temp));
    }

    @Override
    public boolean retainAll(String queueId, Collection<?> collection) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/queue/id/{queueId}/retainAll".replace("{queueId}", queueId), "PUT", new ObjectMapper().writeValueAsString(temp));
    }

    @Override
    public void clear(String queueId) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/queue/id/{queueId}/clear".replace("{queueId}", queueId), "DELETE");
    }

    @Override
    public boolean add(String queueId, E element) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/queue/id/{queueId}/add".replace("{queueId}", queueId), "POST", Utils.encapObject(element));
    }

    @Override
    public boolean offer(String queueId, E element) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/queue/id/{queueId}/offer".replace("{queueId}", queueId), "POST", Utils.encapObject(element));
    }

    @SuppressWarnings("unchecked")
    @Override
    public E remove(String queueId) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/queue/id/{queueId}/remove".replace("{queueId}", queueId), "GET");
    }

    @SuppressWarnings("unchecked")
    @Override
    public E poll(String queueId) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/queue/id/{queueId}/poll".replace("{queueId}", queueId), "GET");
    }

    @SuppressWarnings("unchecked")
    @Override
    public E element(String queueId) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/queue/id/{queueId}/element".replace("{queueId}", queueId), "GET");
    }

    @SuppressWarnings("unchecked")
    @Override
    public E peek(String queueId) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/queue/id/{queueId}/peek".replace("{queueId}", queueId), "GET");
    }
}
