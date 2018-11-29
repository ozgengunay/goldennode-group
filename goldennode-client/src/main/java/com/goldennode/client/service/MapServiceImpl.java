package com.goldennode.client.service;

import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.goldennode.client.GoldenNodeException;
import com.goldennode.client.Response;
import com.goldennode.client.RestClient;

public class MapServiceImpl<K, V> implements MapService<K, V> {
    public int size(String id) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/map/id/{mapId}/size".replace("{mapId}", id), "GET");
        if (response.getResponseCode() == 200)
            return Integer.parseInt((String) response.getValue());
        else {
            throw new GoldenNodeException("Error occured" + response.getResponseCode() + " - " + response.getValue());
        }
    }

    public boolean isEmpty(String id) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/map/id/{mapId}/isEmpty".replace("{mapId}", id), "GET");
        if (response.getResponseCode() == 200) {
            return Boolean.parseBoolean(response.getValue());
        } else {
            throw new GoldenNodeException("Error occured" + response.getResponseCode() + " - " + response.getValue());
        }
    }

    public boolean containsKey(String id, Object key) throws GoldenNodeException {
        ObjectMapper om = new ObjectMapper();
        String json;
        try {
            json = om.writeValueAsString(key);
            json = URLEncoder.encode(json);
        } catch (JsonProcessingException e) {
            throw new GoldenNodeException(e);
        }
        Response response = RestClient.call("/goldennode/map/id/{mapId}/containsKey/key/{key}".replace("{mapId}", id).replace("{key}", json), "GET");
        if (response.getResponseCode() == 200)
            return Boolean.parseBoolean((String) response.getValue());
        else {
            throw new GoldenNodeException("Error occured" + response.getResponseCode() + " - " + response.getValue());
        }
    }

    public boolean containsValue(String id, Object value) throws GoldenNodeException {
        ObjectMapper om = new ObjectMapper();
        String json;
        try {
            json = om.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new GoldenNodeException(e);
        }
        Response response = RestClient.call("/goldennode/map/id/{mapId}/containsValue".replace("{mapId}", id), "GET", json);
        if (response.getResponseCode() == 200)
            return Boolean.parseBoolean((String) response.getValue());
        else {
            throw new GoldenNodeException("Error occured" + response.getResponseCode() + " - " + response.getValue());
        }
    }

    public V get(String id, Object key) throws GoldenNodeException {
        try {
            ObjectMapper om = new ObjectMapper();
            String json;
            json = om.writeValueAsString(key);
            json = URLEncoder.encode(json);
            Response response = RestClient.call("/goldennode/map/id/{mapId}/get/key/{key}".replace("{mapId}", id).replace("{key}", json), "GET");
            if (response.getResponseCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = om.readTree(response.getValue());
                String className = node.get("className").asText();
                JsonNode objectNode = node.get("object");
                Class c = Class.forName(className);
                return (V) om.treeToValue(objectNode, c);
            } else {
                throw new GoldenNodeException("Error occured" + response.getResponseCode() + " - " + response.getValue());
            }
        } catch (ClassNotFoundException e) {
            throw new GoldenNodeException(e);
        } catch (IOException e) {
            throw new GoldenNodeException(e);
        }
    }

    public V put(String id, K key, V value) throws GoldenNodeException {
        ObjectMapper om = new ObjectMapper();
        String jsonKey;
        String jsonValue;
        try {
            jsonKey = om.writeValueAsString(key);
            jsonValue = om.writeValueAsString(value);
            JsonNode editedNode = om.readTree(jsonValue);
            JsonNode newNode = om.createObjectNode();
            ((ObjectNode) newNode).put("className", value.getClass().getName());
            ((ObjectNode) newNode).set("object", editedNode);
            jsonKey = URLEncoder.encode(jsonKey);
            Response response = RestClient.call("/goldennode/map/id/{mapId}/put/key/{key}".replace("{mapId}", id).replace("{key}", jsonKey), "POST", newNode.toString());
            if (response.getResponseCode() == 200)
                return (V) response.getValue();
            else {
                throw new GoldenNodeException("Error occured" + response.getResponseCode() + " - " + response.getValue());
            }
        } catch (IOException e) {
            throw new GoldenNodeException(e);
        }
    }

    public V remove(String id, Object key) throws GoldenNodeException {
        ObjectMapper om = new ObjectMapper();
        String json;
        try {
            json = om.writeValueAsString(key);
            json = URLEncoder.encode(json);
        } catch (JsonProcessingException e) {
            throw new GoldenNodeException(e);
        }
        Response response = RestClient.call("/goldennode/map/id/{mapId}/remove/key/{key}".replace("{mapId}", id).replace("{key}", json), "DELETE");
        if (response.getResponseCode() == 200)
            return (V) response.getValue();
        else {
            throw new GoldenNodeException("Error occured" + response.getResponseCode() + " - " + response.getValue());
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
        if (response.getResponseCode() == 200)
            return;
        else {
            throw new GoldenNodeException("Error occured" + response.getResponseCode() + " - " + response.getValue());
        }
    }

    public void clear(String id) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/map/id/{mapId}/clear".replace("{mapId}", id), "DELETE");
        if (response.getResponseCode() == 200)
            return;
        else {
            throw new GoldenNodeException("Error occured" + response.getResponseCode() + " - " + response.getValue());
        }
    }

    public Set<K> keySet(String id) throws GoldenNodeException {
        /*
         * try { ObjectMapper om = new ObjectMapper(); Response response = RestClient.call("/goldennode/map/id/{mapId}/keySet".replace("{mapId}", id), "GET"); if (response.getResponseCode() == 200) {
         * ObjectMapper mapper = new ObjectMapper(); JsonNode node = om.readTree(response.getValue()); String className = node.get("className").asText(); JsonNode objectNode = node.get("object");
         * Class c = Class.forName(className); return (V) om.treeToValue(objectNode, c); } else { throw new GoldenNodeException("Error occured" + response.getResponseCode() + " - " +
         * response.getValue()); } } catch (ClassNotFoundException e) { throw new GoldenNodeException(e); } catch (IOException e) { throw new GoldenNodeException(e); }
         */
        return null;
    }

    public Collection<V> values(String id) throws GoldenNodeException {
        try {
            ObjectMapper om = new ObjectMapper();
            Response response = RestClient.call("/goldennode/map/id/{mapId}/values".replace("{mapId}", id), "GET");
            if (response.getResponseCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = om.readTree(response.getValue());
                String className = node.get("className").asText();
                JsonNode objectNode = node.get("object");
                Class c = Class.forName(className);
                return (Collection<V>) om.treeToValue(objectNode, c);
            } else {
                throw new GoldenNodeException("Error occured" + response.getResponseCode() + " - " + response.getValue());
            }
        } catch (ClassNotFoundException e) {
            throw new GoldenNodeException(e);
        } catch (IOException e) {
            throw new GoldenNodeException(e);
        }
    }

    public Set<Entry<K, V>> entrySet(String id) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/map/id/{mapId}/size".replace("{mapId}", id), "GET");
        if (response.getResponseCode() == 200) {
            ObjectMapper om = new ObjectMapper();
            TypeReference<Entry<K, V>> typeRef = new TypeReference<Entry<K, V>>() {
            };
            try {
                HashSet<Entry<K, V>> set = om.readValue((String) response.getValue(), typeRef);
                return set;
            } catch (IOException e) {
                throw new GoldenNodeException(e);
            }
        } else {
            throw new GoldenNodeException("Error occured" + response.getResponseCode() + " - " + response.getValue());
        }
    }
}
