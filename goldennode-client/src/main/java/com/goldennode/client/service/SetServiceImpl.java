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
        Response response = RestClient.call("/goldennode/set/id/{setId}/size".replace("{setId}", setId), "GET");
        if (response.getStatusCode() == 200)
            return Integer.parseInt((String) response.getEntityValue());
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
        }
    }

    @Override
    public boolean isEmpty(String setId) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/set/id/{setId}/isEmpty".replace("{setId}", setId), "GET");
        if (response.getStatusCode() == 200) {
            return Boolean.parseBoolean(response.getEntityValue());
        } else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
        }
    }

    @Override
    public boolean contains(String setId, Object object) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/set/id/{setId}/contains/element".replace("{setId}", setId).replace("{element}", Utils.encode(Utils.encapObject(object))), "GET");
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
    public Iterator<E> iterator(String setId) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/set/id/{setId}/iterator".replace("{setId}", setId), "GET");
            if (response.getStatusCode() == 200) {
                Set<E> set = new HashSet<E>();
                Iterator<JsonNode> iter = response.getEntityIterator();
                while (iter.hasNext()) {
                    set.add((E) Utils.extractObject(iter.next().asText()));
                }
                return set.iterator();
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
    public Object[] toArray(String setId) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/set/id/{setId}/toArray".replace("{setId}", setId), "GET");
            if (response.getStatusCode() == 200) {
                Set<E> set = new HashSet<E>();
                Iterator<JsonNode> iter = response.getEntityIterator();
                while (iter.hasNext()) {
                    set.add((E) Utils.extractObject(iter.next().asText()));
                }
                return set.toArray();
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
    public <T> T[] toArray(String setId, T[] a) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/set/id/{setId}/toArray".replace("{setId}", setId), "GET");
            if (response.getStatusCode() == 200) {
                Set<E> set = new HashSet<E>();
                Iterator<JsonNode> iter = response.getEntityIterator();
                while (iter.hasNext()) {
                    set.add((E) Utils.extractObject(iter.next().asText()));
                }
                return set.toArray(a);
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
    public boolean add(String setId, E element) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/set/id/{setId}/add/element/{element}".replace("{setId}", setId).replace("{element}", Utils.encode(Utils.encapObject(element))), "POST");
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
    public boolean remove(String setId, Object object) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/set/id/{setId}/remove/object/{object}".replace("{setId}", setId).replace("{object}", Utils.encode(Utils.encapObject(object))), "DELETE");
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
    public boolean containsAll(String setId, Collection<?> c) throws GoldenNodeException {
        try {
            Iterator<?> iter = c.iterator();
            List<String> temp = new ArrayList<>();
            while (iter.hasNext()) {
                Object o = iter.next();
                temp.add(Utils.encapObject(Utils.encapObject(o)));
            }
            Response response = RestClient.call("/goldennode/set/id/{setId}/containsAll".replace("{setId}", setId), "POST", new ObjectMapper().writeValueAsString(temp));
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
    public boolean addAll(String setId, Collection<? extends E> c) throws GoldenNodeException {
        try {
            Iterator<? extends E> iter = c.iterator();
            List<String> temp = new ArrayList<>();
            while (iter.hasNext()) {
                Object o = iter.next();
                temp.add(Utils.encapObject(Utils.encapObject(o)));
            }
            Response response = RestClient.call("/goldennode/set/id/{setId}/addAll".replace("{setId}", setId), "POST", new ObjectMapper().writeValueAsString(temp));
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
    public boolean retainAll(String setId, Collection<?> c) throws GoldenNodeException {
        try {
            Iterator<?> iter = c.iterator();
            Set<String> temp = new HashSet<>();
            while (iter.hasNext()) {
                Object o = iter.next();
                temp.add(Utils.encapObject(Utils.encapObject(o)));
            }
            Response response = RestClient.call("/goldennode/set/id/{setId}/retainAll".replace("{setId}", setId), "PUT", new ObjectMapper().writeValueAsString(temp));
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
    public boolean removeAll(String setId, Collection<?> c) throws GoldenNodeException {
        try {
            Iterator<?> iter = c.iterator();
            Set<String> temp = new HashSet<>();
            while (iter.hasNext()) {
                Object o = iter.next();
                temp.add(Utils.encapObject(Utils.encapObject(o)));
            }
            Response response = RestClient.call("/goldennode/set/id/{setId}/removeAll".replace("{setId}", setId), "PUT", new ObjectMapper().writeValueAsString(temp));
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
    public void clear(String setId) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/set/id/{setId}/clear".replace("{setId}", setId), "DELETE");
        if (response.getStatusCode() == 200)
            return;
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getBody());
        }
    }
}
