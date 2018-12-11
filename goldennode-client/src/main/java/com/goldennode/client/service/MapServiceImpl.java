package com.goldennode.client.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.goldennode.client.GoldenNodeException;
import com.goldennode.client.Response;
import com.goldennode.client.RestClient;

public class MapServiceImpl<K, V> implements MapService<K, V> {
    ObjectMapper om = new ObjectMapper();

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

    public static void main(String arg[]) {
        System.out.println(escape("~!@#$%^&*()_+{}:|<>?>[];',./\""));
    }
    private static String escape(String str) {
        try {
            return URLEncoder.encode(str.replace("/", "&sol;").replace("\"", "&quot;").replace("\\", "&bsol;"), "UTF-8");
        } catch (UnsupportedEncodingException e) {  
            return null;
        }
    }

    public boolean containsKey(String id, Object key) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/map/id/{mapId}/containsKey/key/{key}".replace("{mapId}", id).replace("{key}", escape(encapObject(key))), "GET");
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
            Response response = RestClient.call("/goldennode/map/id/{mapId}/containsValue/value/{value}".replace("{mapId}", id).replace("{value}", escape(encapObject(value))), "GET");
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
            Response response = RestClient.call("/goldennode/map/id/{mapId}/get/key/{key}".replace("{mapId}", id).replace("{key}", escape(encapObject(key))), "GET");
            if (response.getStatusCode() == 200) {
                return (V) extractObject(response.getEntityValue());
            } else {
                throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
            }
        } catch (ClassNotFoundException e) {
            throw new GoldenNodeException(e);
        } catch (IOException e) {
            throw new GoldenNodeException(e);
        }
    }

    public Object extractObject(String value) throws IOException, ClassNotFoundException {
        JsonNode node = om.readTree(value);
        String className = node.get("c").asText();
        JsonNode objectNode = node.get("o");
        Class<?> c = Class.forName(className);
        return om.treeToValue(objectNode, c);
    }

    public String encapObject(Object value) throws JsonProcessingException {
        JsonNode newNode = om.createObjectNode();
        ((ObjectNode) newNode).put("c", value.getClass().getName());
        ((ObjectNode) newNode).set("o", om.valueToTree(value));
        return om.writeValueAsString(newNode);
    }

    public V put(String id, K key, V value) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/map/id/{mapId}/put/key/{key}".replace("{mapId}", id).replace("{key}", escape(encapObject(key))), "POST", encapObject(value));
            if (response.getStatusCode() == 200)
                return (V) response.getEntityValue();
            else {
                throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
            }
        } catch (JsonProcessingException e) {
            throw new GoldenNodeException(e);
        }
    }

    public V remove(String id, Object key) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/map/id/{mapId}/remove/key/{key}".replace("{mapId}", id).replace("{key}", escape(encapObject(key))), "DELETE");
            if (response.getStatusCode() == 200)
                return (V) extractObject(response.getEntityValue());
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
                    set.add((K) extractObject(iter.next().asText()));
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
                    list.add((V) extractObject(iter.next().asText()));
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
                    K key = (K) extractObject(node.fieldNames().next());
                    V value = (V) extractObject(node.fields().next().getValue().asText());
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
