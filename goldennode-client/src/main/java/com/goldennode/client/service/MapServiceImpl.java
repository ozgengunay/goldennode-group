package com.goldennode.client.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.goldennode.client.GoldenNodeException;
import com.goldennode.client.Response;
import com.goldennode.client.RestClient;
import com.goldennode.client.Utils;

public class MapServiceImpl<K, V> implements MapService<K, V> {
    

    public int size(String id) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/map/id/{mapId}/size".replace("{mapId}", id), "GET");
        if (response.getStatusCode() == 200)
            return Integer.parseInt((String) response.getEntityValue());
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
        }
    }

    public boolean isEmpty(String id) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/map/id/{mapId}/isEmpty".replace("{mapId}", id), "GET");
        if (response.getStatusCode() == 200) {
            return Boolean.parseBoolean(response.getEntityValue());
        } else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
        }
    }

    public boolean containsKey(String id, Object key) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/map/id/{mapId}/containsKey/key/{key}".replace("{mapId}", id).replace("{key}", Utils.encode(Utils.encapObject(key))), "GET");
            if (response.getStatusCode() == 200)
                return Boolean.parseBoolean((String) response.getEntityValue());
            else {
                throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
            }
        } catch (JsonProcessingException e) {
            throw new GoldenNodeException(e);
        }
    }

    public boolean containsValue(String id, Object value) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/map/id/{mapId}/containsValue/value/{value}".replace("{mapId}", id).replace("{value}", Utils.encode(Utils.encapObject(value))), "GET");
            if (response.getStatusCode() == 200)
                return Boolean.parseBoolean((String) response.getEntityValue());
            else {
                throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
            }
        } catch (JsonProcessingException e) {
            throw new GoldenNodeException(e);
        }
    }

    public V get(String id, Object key) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/map/id/{mapId}/get/key/{key}".replace("{mapId}", id).replace("{key}", Utils.encode(Utils.encapObject(key))), "GET");
            if (response.getStatusCode() == 200) {
                return (V) Utils.extractObject(response.getEntityValue());
            } else {
                throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
            }
        } catch (ClassNotFoundException e) {
            throw new GoldenNodeException(e);
        } catch (IOException e) {
            throw new GoldenNodeException(e);
        }
    }

    public V put(String id, K key, V value) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/map/id/{mapId}/put/key/{key}".replace("{mapId}", id).replace("{key}", Utils.encode(Utils.encapObject(key))), "POST", Utils.encapObject(value));
            if (response.getStatusCode() == 200)
                return (V) Utils.extractObject(response.getEntityValue());
            else {
                throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
            }
        } catch (ClassNotFoundException e) {
            throw new GoldenNodeException(e);
        } catch (IOException e) {
            throw new GoldenNodeException(e);
        }
    }

    public V remove(String id, Object key) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/map/id/{mapId}/remove/key/{key}".replace("{mapId}", id).replace("{key}", Utils.encode(Utils.encapObject(key))), "DELETE");
            if (response.getStatusCode() == 200)
                return (V) Utils.extractObject(response.getEntityValue());
            else {
                throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
            }
        } catch (ClassNotFoundException e) {
            throw new GoldenNodeException(e);
        } catch (IOException e) {
            throw new GoldenNodeException(e);
        }
    }

    public void putAll(String id, Map<? extends K, ? extends V> m) throws GoldenNodeException {
        for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(id, entry.getKey(), entry.getValue());
        }
    }

    public void clear(String id) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/map/id/{mapId}/clear".replace("{mapId}", id), "DELETE");
        if (response.getStatusCode() == 200)
            return;
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getBody());
        }
    }

    public Set<K> keySet(String id) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/map/id/{mapId}/keySet".replace("{mapId}", id), "GET");
            if (response.getStatusCode() == 200) {
                Set<K> set = new HashSet<>();
                Iterator<JsonNode> iter = response.getEntityIterator();
                while (iter.hasNext()) {
                    set.add((K) Utils.extractObject(iter.next().asText()));
                }
                return set;
            } else {
                throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
            }
        } catch (ClassNotFoundException e) {
            throw new GoldenNodeException(e);
        } catch (IOException e) {
            throw new GoldenNodeException(e);
        }
    }

    public Collection<V> values(String id) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/map/id/{mapId}/values".replace("{mapId}", id), "GET");
            if (response.getStatusCode() == 200) {
                List<V> list = new ArrayList<>();
                Iterator<JsonNode> iter = response.getEntityIterator();
                while (iter.hasNext()) {
                    list.add((V) Utils.extractObject(iter.next().asText()));
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

    public Set<Entry<K, V>> entrySet(String id) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/map/id/{mapId}/entrySet".replace("{mapId}", id), "GET");
            if (response.getStatusCode() == 200) {
                Map<K, V> map = new HashMap<>();
                Iterator<JsonNode> iter = response.getEntityIterator();
                while (iter.hasNext()) {
                    JsonNode node = iter.next();
                    K key = (K) Utils.extractObject(node.fieldNames().next());
                    V value = (V) Utils.extractObject(node.fields().next().getValue().asText());
                    map.put(key, value);
                }
                return map.entrySet();
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
