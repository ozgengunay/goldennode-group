package com.goldennode.client.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.goldennode.client.GoldenNodeException;
import com.goldennode.client.Response;
import com.goldennode.client.RestClient;
import com.goldennode.client.Utils;

public class QueueServiceImpl<E> implements QueueService<E> {

    @Override
    public int size(String queueId) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/list/id/{listId}/size".replace("{listId}", listId), "GET");
        if (response.getStatusCode() == 200)
            return Integer.parseInt((String) response.getEntityValue());
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
        }
    }

    @Override
    public boolean isEmpty(String queueId) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/list/id/{listId}/isEmpty".replace("{listId}", listId), "GET");
        if (response.getStatusCode() == 200) {
            return Boolean.parseBoolean(response.getEntityValue());
        } else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
        }
    }

    @Override
    public boolean contains(String queueId, Object object) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/list/id/{listId}/contains/element".replace("{listId}", listId).replace("{element}", Utils.encode(Utils.encapObject(object))), "GET");
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
    public Iterator<E> iterator(String queueId) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/list/id/{listId}/iterator".replace("{listId}", listId), "GET");
            if (response.getStatusCode() == 200) {
                List<E> list = new ArrayList<>();
                Iterator<JsonNode> iter = response.getEntityIterator();
                while (iter.hasNext()) {
                    list.add((E) Utils.extractObject(iter.next().asText()));
                }
                return list.iterator();
            } else {
                throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
            }
        } catch (ClassNotFoundException e) {
            throw new GoldenNodeException(e);
        } catch (IOException e) {
            throw new GoldenNodeException(e);
        }
    }

    @Override
    public Object[] toArray(String queueId) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/list/id/{listId}/toArray".replace("{listId}", listId), "GET");
            if (response.getStatusCode() == 200) {
                List<E> list = new ArrayList<>();
                Iterator<JsonNode> iter = response.getEntityIterator();
                while (iter.hasNext()) {
                    list.add((E) Utils.extractObject(iter.next().asText()));
                }
                return list.toArray();
            } else {
                throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
            }
        } catch (ClassNotFoundException e) {
            throw new GoldenNodeException(e);
        } catch (IOException e) {
            throw new GoldenNodeException(e);
        }
    }

    @Override
    public <T> T[] toArray(String queueId, T[] a) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/list/id/{listId}/toArray".replace("{listId}", listId), "GET");
            if (response.getStatusCode() == 200) {
                List<E> list = new ArrayList<>();
                Iterator<JsonNode> iter = response.getEntityIterator();
                while (iter.hasNext()) {
                    list.add((E) Utils.extractObject(iter.next().asText()));
                }
                return list.toArray(a);
            } else {
                throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
            }
        } catch (ClassNotFoundException e) {
            throw new GoldenNodeException(e);
        } catch (IOException e) {
            throw new GoldenNodeException(e);
        }
    }

    @Override
    public boolean remove(String queueId, Object object) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/list/id/{listId}/remove/object/{object}".replace("{listId}", listId).replace("{object}", Utils.encode(Utils.encapObject(object))), "DELETE");
            if (response.getStatusCode() == 200)
                return Boolean.parseBoolean(response.getEntityValue());
            else {
                throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
            }
        } catch (IOException e) {
            throw new GoldenNodeException(e);
        }
    }

    @Override
    public boolean containsAll(String queueId, Collection<?> collection) throws GoldenNodeException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean addAll(String queueId, Collection<? extends E> collection) throws GoldenNodeException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean removeAll(String queueId, Collection<?> collection) throws GoldenNodeException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean retainAll(String queueId, Collection<?> collection) throws GoldenNodeException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void clear(String queueId) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/list/id/{listId}/clear".replace("{listId}", listId), "DELETE");
        if (response.getStatusCode() == 200)
            return;
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getBody());
        }
        
    }

    @Override
    public boolean add(String queueId, E element) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/list/id/{listId}/add/element/{element}".replace("{listId}", listId).replace("{element}", Utils.encode(Utils.encapObject(element))), "POST");
            if (response.getStatusCode() == 200)
                return Boolean.parseBoolean(response.getEntityValue());
            else {
                throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
            }
        }  catch (IOException e) {
            throw new GoldenNodeException(e);
        }
    }

    @Override
    public boolean offer(String queueId, E element) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/list/id/{listId}/offer/element/{element}".replace("{listId}", listId).replace("{element}", Utils.encode(Utils.encapObject(element))), "POST");
            if (response.getStatusCode() == 200)
                return Boolean.parseBoolean(response.getEntityValue());
            else {
                throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
            }
        }  catch (IOException e) {
            throw new GoldenNodeException(e);
        }
    }

    @Override
    public E remove(String queueId) throws GoldenNodeException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public E poll(String queueId) throws GoldenNodeException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public E element(String queueId) throws GoldenNodeException {
        // TODO Auto-generated method stub
        Queue<E>
        return null;
    }

    @Override
    public E peek(String queueId) throws GoldenNodeException {
        // TODO Auto-generated method stub
        return null;
    }
}
