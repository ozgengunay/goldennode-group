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
        if (response.getStatusCode() == 200)
            return Integer.parseInt((String) response.getEntityValue());
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
        }
    }

    @Override
    public boolean isEmpty(String queueId) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/queue/id/{queueId}/isEmpty".replace("{queueId}", queueId), "GET");
        if (response.getStatusCode() == 200) {
            return Boolean.parseBoolean(response.getEntityValue());
        } else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
        }
    }

    @Override
    public boolean contains(String queueId, Object object) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/queue/id/{queueId}/contains/object/{object}".replace("{queueId}", queueId).replace("{object}", Utils.encode(Utils.encapObject(object))),
                    "GET");
            if (response.getStatusCode() == 200) {
                return Boolean.parseBoolean(response.getEntityValue());
            } else {
                throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
            }
        } catch (JsonProcessingException e) {
            throw new GoldenNodeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<E> iterator(String queueId) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/queue/id/{queueId}/iterator".replace("{queueId}", queueId), "GET");
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

    @SuppressWarnings("unchecked")
    @Override
    public Object[] toArray(String queueId) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/queue/id/{queueId}/toArray".replace("{queueId}", queueId), "GET");
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

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(String queueId, T[] a) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/queue/id/{queueId}/toArray".replace("{queueId}", queueId), "GET");
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
            Response response = RestClient.call("/goldennode/queue/id/{queueId}/remove/object/{object}".replace("{queueId}", queueId).replace("{object}", Utils.encode(Utils.encapObject(object))),
                    "DELETE");
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
        try {
            Iterator<?> iter = collection.iterator();
            List<String> temp = new ArrayList<>();
            while (iter.hasNext()) {
                Object o = iter.next();
                temp.add(Utils.encapObject(Utils.encapObject(o)));
            }
            Response response = RestClient.call("/goldennode/queue/id/{queueId}/containsAll".replace("{queueId}", queueId), "POST", new ObjectMapper().writeValueAsString(temp));
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
    public boolean addAll(String queueId, Collection<? extends E> collection) throws GoldenNodeException {
        try {
            Iterator<? extends E> iter = collection.iterator();
            List<String> temp = new ArrayList<>();
            while (iter.hasNext()) {
                Object o = iter.next();
                temp.add(Utils.encapObject(Utils.encapObject(o)));
            }
            Response response = RestClient.call("/goldennode/queue/id/{queueId}/addAll".replace("{queueId}", queueId), "POST", new ObjectMapper().writeValueAsString(temp));
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
    public boolean removeAll(String queueId, Collection<?> collection) throws GoldenNodeException {
        try {
            Iterator<?> iter = collection.iterator();
            List<String> temp = new ArrayList<>();
            while (iter.hasNext()) {
                Object o = iter.next();
                temp.add(Utils.encapObject(Utils.encapObject(o)));
            }
            Response response = RestClient.call("/goldennode/queue/id/{queueId}/removeAll".replace("{queueId}", queueId), "PUT", new ObjectMapper().writeValueAsString(temp));
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
    public boolean retainAll(String queueId, Collection<?> collection) throws GoldenNodeException {
        try {
            Iterator<?> iter = collection.iterator();
            List<String> temp = new ArrayList<>();
            while (iter.hasNext()) {
                Object o = iter.next();
                temp.add(Utils.encapObject(Utils.encapObject(o)));
            }
            Response response = RestClient.call("/goldennode/queue/id/{queueId}/retainAll".replace("{queueId}", queueId), "PUT", new ObjectMapper().writeValueAsString(temp));
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
    public void clear(String queueId) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/queue/id/{queueId}/clear".replace("{queueId}", queueId), "DELETE");
        if (response.getStatusCode() == 200)
            return;
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getBody());
        }
    }

    @Override
    public boolean add(String queueId, E element) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/queue/id/{queueId}/add".replace("{queueId}", queueId), "POST", Utils.encapObject(element));
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
    public boolean offer(String queueId, E element) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/queue/id/{queueId}/offer".replace("{queueId}", queueId), "POST", Utils.encapObject(element));
            if (response.getStatusCode() == 200)
                return Boolean.parseBoolean(response.getEntityValue());
            else {
                throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
            }
        } catch (IOException e) {
            throw new GoldenNodeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public E remove(String queueId) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/queue/id/{queueId}/remove".replace("{queueId}", queueId), "GET");
            if (response.getStatusCode() == 200)
                return (E) Utils.extractObject(response.getEntityValue());
            else if (response.getStatusCode() == 400) {
                throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
            } else {
                throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
            }
        } catch (ClassNotFoundException e) {
            throw new GoldenNodeException(e);
        } catch (IOException e) {
            throw new GoldenNodeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public E poll(String queueId) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/queue/id/{queueId}/poll".replace("{queueId}", queueId), "GET");
            if (response.getStatusCode() == 200)
                return (E) Utils.extractObject(response.getEntityValue());
            else {
                throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
            }
        } catch (ClassNotFoundException e) {
            throw new GoldenNodeException(e);
        } catch (IOException e) {
            throw new GoldenNodeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public E element(String queueId) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/queue/id/{queueId}/element".replace("{queueId}", queueId), "GET");
            if (response.getStatusCode() == 200)
                return (E) Utils.extractObject(response.getEntityValue());
            else {
                throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
            }
        } catch (ClassNotFoundException e) {
            throw new GoldenNodeException(e);
        } catch (IOException e) {
            throw new GoldenNodeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public E peek(String queueId) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/queue/id/{queueId}/peek".replace("{queueId}", queueId), "GET");
            if (response.getStatusCode() == 200)
                return (E) Utils.extractObject(response.getEntityValue());
            else {
                throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
            }
        } catch (ClassNotFoundException e) {
            throw new GoldenNodeException(e);
        } catch (IOException e) {
            throw new GoldenNodeException(e);
        }
    }
}
