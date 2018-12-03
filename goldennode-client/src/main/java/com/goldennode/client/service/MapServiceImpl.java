package com.goldennode.client.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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

    public boolean containsKey(String id, Object key) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/map/id/{mapId}/containsKey/key/{key}".replace("{mapId}", id).replace("{key}", URLEncoder.encode(encapObject(key))), "GET");
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
            Response response = RestClient.call("/goldennode/map/id/{mapId}/containsValue".replace("{mapId}", id), "GET", encapObject(value));
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
            Response response = RestClient.call("/goldennode/map/id/{mapId}/get/key/{key}".replace("{mapId}", id).replace("{key}", URLEncoder.encode(encapObject(key))), "GET");
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
        String className = node.get("c").asText();//.replaceAll("_", ".");
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
            Response response = RestClient.call("/goldennode/map/id/{mapId}/put/key/{key}".replace("{mapId}", id).replace("{key}", URLEncoder.encode(encapObject(key))), "POST", encapObject(value));
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
            Response response = RestClient.call("/goldennode/map/id/{mapId}/remove/key/{key}".replace("{mapId}", id).replace("{key}", URLEncoder.encode(encapObject(key))), "DELETE");
            if (response.getStatusCode() == 200)
                return (V) response.getEntityValue();
            else {
                throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
            }
        } catch (JsonProcessingException e) {
            throw new GoldenNodeException(e);
        }
    }

    public void putAll(String id, Map<? extends K, ? extends V> m) throws GoldenNodeException {
        ObjectMapper om = new ObjectMapper();
        String json;
        try {
            json = om.writeValueAsString(m);
        } catch (JsonProcessingException e) {
            throw new GoldenNodeException(e);
        }
        Response response = RestClient.call("/goldennode/map/id/{mapId}/putAll".replace("{mapId}", id), "POST", json);
        if (response.getStatusCode() == 200)
            return;
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
        }
    }

    public void clear(String id) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/map/id/{mapId}/clear".replace("{mapId}", id), "DELETE");
        if (response.getStatusCode() == 200)
            return;
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
        }
    }

    public Set<K> keySet(String id) throws GoldenNodeException {
        try {
            ObjectMapper om = new ObjectMapper();
            Response response = RestClient.call("/goldennode/map/id/{mapId}/keySet".replace("{mapId}", id), "GET");
            Set<K> list = new HashSet<>();
            if (response.getStatusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                Iterator<JsonNode> iter = response.getEntityIterator();
                while (iter.hasNext()) {
                    JsonNode node = iter.next();
                    JsonNode element = om.readTree(node.asText());
                    String className = element.get("c>>lassName").asText();
                    JsonNode objectNode = element.get("object");
                    Class c = Class.forName(className);
                    list.add((K) om.treeToValue(objectNode, c));
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

    public Collection<V> values(String id) throws GoldenNodeException {
        try {
            ObjectMapper om = new ObjectMapper();
            Response response = RestClient.call("/goldennode/map/id/{mapId}/values".replace("{mapId}", id), "GET");
            List<V> list = new ArrayList<>();
            if (response.getStatusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                Iterator<JsonNode> iter = response.getEntityIterator();
                while (iter.hasNext()) {
                    JsonNode node = iter.next();
                    JsonNode element = om.readTree(node.asText());
                    String className = element.get("className").asText();
                    JsonNode objectNode = element.get("object");
                    Class c = Class.forName(className);
                    list.add((V) om.treeToValue(objectNode, c));
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
        Response response = RestClient.call("/goldennode/map/id/{mapId}/size".replace("{mapId}", id), "GET");
        if (response.getStatusCode() == 200) {
            ObjectMapper om = new ObjectMapper();
            TypeReference<Entry<K, V>> typeRef = new TypeReference<Entry<K, V>>() {
            };
            try {
                HashSet<Entry<K, V>> set = om.readValue((String) response.getEntityValue(), typeRef);
                return set;
            } catch (IOException e) {
                throw new GoldenNodeException(e);
            }
        } else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
        }
    }
}
