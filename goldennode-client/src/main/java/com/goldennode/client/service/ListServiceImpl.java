package com.goldennode.client.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
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

    public boolean contains(String listId, Object object) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/list/id/{listId}/contains/element/{element}".replace("{listId}", listId).replace("{element}", Utils.encode(Utils.encapObject(object))), "GET");
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
    public Iterator<E> iterator(String listId) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/list/id/{listId}/toArray".replace("{listId}", listId), "GET");
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
    public Object[] toArray(String listId) throws GoldenNodeException {
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

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(String listId, T[] a) throws GoldenNodeException {
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
    public boolean add(String listId, E element) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/list/id/{listId}/add".replace("{listId}", listId), "POST", Utils.encapObject(element));
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
    public boolean remove(String listId, Object object) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/list/id/{listId}/remove/object/{object}".replace("{listId}", listId).replace("{object}", Utils.encode(Utils.encapObject(object))),
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
    public boolean containsAll(String listId, Collection<?> c) throws GoldenNodeException {
        try {
            Iterator<?> iter = c.iterator();
            List<String> temp = new ArrayList<>();
            while (iter.hasNext()) {
                Object o = iter.next();
                temp.add(Utils.encapObject(o));
            }
            Response response = RestClient.call("/goldennode/list/id/{listId}/containsAll".replace("{listId}", listId), "POST", new ObjectMapper().writeValueAsString(temp));
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
    public boolean addAll(String listId, Collection<? extends E> c) throws GoldenNodeException {
        try {
            Iterator<? extends E> iter = c.iterator();
            List<String> temp = new ArrayList<>();
            while (iter.hasNext()) {
                Object o = iter.next();
                temp.add(Utils.encapObject(o));
            }
            Response response = RestClient.call("/goldennode/list/id/{listId}/addAll".replace("{listId}", listId), "POST", new ObjectMapper().writeValueAsString(temp));
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
    public boolean addAll(String listId, int index, Collection<? extends E> c) throws GoldenNodeException {
        try {
            Iterator<? extends E> iter = c.iterator();
            List<String> temp = new ArrayList<>();
            while (iter.hasNext()) {
                Object o = iter.next();
                temp.add(Utils.encapObject(o));
            }
            Response response = RestClient.call("/goldennode/list/id/{listId}/addAll/index/{index}".replace("{listId}", listId).replace("{index}", Integer.toString(index)), "POST",
                    new ObjectMapper().writeValueAsString(temp));
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
    public boolean removeAll(String listId, Collection<?> c) throws GoldenNodeException {
        try {
            Iterator<?> iter = c.iterator();
            List<String> temp = new ArrayList<>();
            while (iter.hasNext()) {
                Object o = iter.next();
                temp.add(Utils.encapObject(o));
            }
            Response response = RestClient.call("/goldennode/list/id/{listId}/removeAll".replace("{listId}", listId), "PUT", new ObjectMapper().writeValueAsString(temp));
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
    public boolean retainAll(String listId, Collection<?> c) throws GoldenNodeException {
        try {
            Iterator<?> iter = c.iterator();
            List<String> temp = new ArrayList<>();
            while (iter.hasNext()) {
                Object o = iter.next();
                temp.add(Utils.encapObject(o));
            }
            Response response = RestClient.call("/goldennode/list/id/{listId}/retainAll".replace("{listId}", listId), "PUT", new ObjectMapper().writeValueAsString(temp));
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
    public void clear(String listId) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/list/id/{listId}/clear".replace("{listId}", listId), "DELETE");
        if (response.getStatusCode() == 200)
            return;
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getBody());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public E get(String listId, int index) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/list/id/{listId}/get/index/{index}".replace("{listId}", listId).replace("{index}", Integer.toString(index)), "GET");
            if (response.getStatusCode() == 200) {
                return (E) Utils.extractObject(response.getEntityValue());
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
    public E set(String listId, int index, E element) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/list/id/{listId}/set/index/{index}".replace("{listId}", listId).replace("{index}", Integer.toString(index)), "PUT",
                    Utils.encapObject(element));
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

    @Override
    public void add(String listId, int index, E element) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/list/id/{listId}/add/index/{index}".replace("{listId}", listId).replace("{index}", Integer.toString(index)), "POST",
                    Utils.encapObject(element));
            if (response.getStatusCode() == 200)
                return;
            else {
                throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
            }
        } catch (IOException e) {
            throw new GoldenNodeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public E remove(String listId, int index) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/list/id/{listId}/remove/index/{index}".replace("{listId}", listId).replace("{index}", Integer.toString(index)), "DELETE");
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

    @Override
    public int indexOf(String listId, Object object) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/list/id/{listId}/indexOf/object/{object}".replace("{listId}", listId).replace("{object}", Utils.encode(Utils.encapObject(object))), "GET");
            if (response.getStatusCode() == 200) {
                return Integer.parseInt((String) response.getEntityValue());
            } else {
                throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
            }
        } catch (IOException e) {
            throw new GoldenNodeException(e);
        }
    }

    @Override
    public int lastIndexOf(String listId, Object object) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/list/id/{listId}/lastIndexOf/object/{object}".replace("{listId}", listId).replace("{object}", Utils.encode(Utils.encapObject(object))),
                    "GET");
            if (response.getStatusCode() == 200) {
                return Integer.parseInt((String) response.getEntityValue());
            } else {
                throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
            }
        } catch (IOException e) {
            throw new GoldenNodeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public ListIterator<E> listIterator(String listId) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/list/id/{listId}/listIterator".replace("{listId}", listId), "GET");
            if (response.getStatusCode() == 200) {
                List<E> list = new ArrayList<>();
                Iterator<JsonNode> iter = response.getEntityIterator();
                while (iter.hasNext()) {
                    list.add((E) Utils.extractObject(iter.next().asText()));
                }
                return list.listIterator();
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
    public ListIterator<E> listIterator(String listId, int index) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/list/id/{listId}/listIterator/index/{index}".replace("{listId}", listId).replace("{index}", Integer.toString(index)), "GET");
            if (response.getStatusCode() == 200) {
                List<E> list = new ArrayList<>();
                Iterator<JsonNode> iter = response.getEntityIterator();
                while (iter.hasNext()) {
                    list.add((E) Utils.extractObject(iter.next().asText()));
                }
                return list.listIterator();
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
    public List<E> subList(String listId, int fromIndex, int toIndex) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/list/id/{listId}/subList/fromIndex/{fromIndex}/toIndex/{toIndex}".replace("{listId}", listId)
                    .replace("{fromIndex}", Integer.toString(fromIndex)).replace("{toIndex}", Integer.toString(toIndex)), "GET");
            if (response.getStatusCode() == 200) {
                List<E> list = new ArrayList<>();
                Iterator<JsonNode> iter = response.getEntityIterator();
                while (iter.hasNext()) {
                    list.add((E) Utils.extractObject(iter.next().asText()));
                }
                return list;
            } else {
                throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
            }
        } catch (ClassNotFoundException e) {
            throw new GoldenNodeException(e);
        } catch (IOException e) {
            throw new GoldenNodeException(e);
        }
    }
}
