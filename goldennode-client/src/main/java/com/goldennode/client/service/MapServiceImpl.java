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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goldennode.client.GoldenNodeException;
import com.goldennode.client.RestClient;

public class MapServiceImpl<K extends Serializable, V extends Serializable> implements MapService<K, V> {
    public int size(String id) throws GoldenNodeException {
        ResponseEntity<String> response = RestClient.call("/goldennode/map/id/{mapId}/size".replace("{mapId}", id), HttpMethod.GET);
        if (response.getStatusCodeValue() == HttpStatus.OK.value())
            return Integer.parseInt(response.getBody());
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getBody());
        }
    }

    public boolean isEmpty(String id) throws GoldenNodeException {
        ResponseEntity<String> response = RestClient.call("/goldennode/map/id/{mapId}/isEmpty".replace("{mapId}", id), HttpMethod.GET);
        if (response.getStatusCodeValue() == HttpStatus.OK.value())
            return Boolean.parseBoolean(response.getBody());
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getBody());
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
        ResponseEntity<String> response = RestClient.call("/goldennode/map/id/{mapId}/containsKey/key/{key}".replace("{mapId}", id).replace("{key}", json), HttpMethod.GET);
        if (response.getStatusCodeValue() == HttpStatus.OK.value())
            return Boolean.parseBoolean(response.getBody());
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getBody());
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
        ResponseEntity<String> response = RestClient.call("/goldennode/map/id/{mapId}/containsValue".replace("{mapId}", id), HttpMethod.POST, json);
        if (response.getStatusCodeValue() == HttpStatus.OK.value())
            return Boolean.parseBoolean(response.getBody());
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getBody());
        }
    }

    public V get(String id, Object key) throws GoldenNodeException {
        ObjectMapper om = new ObjectMapper();
        String json;
        try {
            json = om.writeValueAsString(key);
            json = URLEncoder.encode(json);
        } catch (JsonProcessingException e) {
            throw new GoldenNodeException(e);
        }
        ResponseEntity<String> response = RestClient.call("/goldennode/map/id/{mapId}/get/key/{key}".replace("{mapId}", id).replace("{key}", json), HttpMethod.GET);
        if (response.getStatusCodeValue() == HttpStatus.OK.value())
            return (V) response.getBody();
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getBody());
        }
    }

    public V put(String id, K key, V value) throws GoldenNodeException {
        ObjectMapper om = new ObjectMapper();
        String jsonKey;
        String jsonValue;
        try {
            jsonKey = om.writeValueAsString(key);
            jsonValue = om.writeValueAsString(value);
            jsonKey = URLEncoder.encode(jsonKey);
        } catch (JsonProcessingException e) {
            throw new GoldenNodeException(e);
        }
        ResponseEntity<String> response = RestClient.call("/goldennode/map/id/{mapId}/put/key/{key}".replace("{mapId}", id).replace("{key}", jsonKey), HttpMethod.GET, jsonValue);
        if (response.getStatusCodeValue() == HttpStatus.OK.value())
            return (V) response.getBody();
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getBody());
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
        ResponseEntity<String> response = RestClient.call("/goldennode/map/id/{mapId}/remove/key/{key}".replace("{mapId}", id).replace("{key}", json), HttpMethod.DELETE);
        if (response.getStatusCodeValue() == HttpStatus.OK.value())
            return (V) response.getBody();
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getBody());
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
        ResponseEntity<String> response = RestClient.call("/goldennode/map/id/{mapId}/putAll".replace("{mapId}", id), HttpMethod.POST, json);
        if (response.getStatusCodeValue() == HttpStatus.OK.value())
            return;
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getBody());
        }
    }

    public void clear(String id) throws GoldenNodeException {
        ResponseEntity<String> response = RestClient.call("/goldennode/map/id/{mapId}/size".replace("{mapId}", id), HttpMethod.GET);
        if (response.getStatusCodeValue() == HttpStatus.OK.value())
            return;
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getBody());
        }
    }

    public Set<K> keySet(String id) throws GoldenNodeException {
        ResponseEntity<String> response = RestClient.call("/goldennode/map/id/{mapId}/keySet".replace("{mapId}", id), HttpMethod.GET);
        if (response.getStatusCodeValue() == HttpStatus.OK.value()) {
            ObjectMapper om = new ObjectMapper();
            TypeReference<HashSet<K>> typeRef = new TypeReference<HashSet<K>>() {
            };
            try {
                HashSet<K> set = om.readValue(response.getBody(), typeRef);
                return set;
            } catch (IOException e) {
                throw new GoldenNodeException(e);
            }
        } else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getBody());
        }
    }

    public Collection<V> values(String id) throws GoldenNodeException {
        ResponseEntity<String> response = RestClient.call("/goldennode/map/id/{mapId}/values".replace("{mapId}", id), HttpMethod.GET);
        if (response.getStatusCodeValue() == HttpStatus.OK.value()) {
            ObjectMapper om = new ObjectMapper();
            TypeReference<ArrayList<V>> typeRef = new TypeReference<ArrayList<V>>() {
            };
            try {
                ArrayList<V> list = om.readValue(response.getBody(), typeRef);
                return list;
            } catch (IOException e) {
                throw new GoldenNodeException(e);
            }
        } else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getBody());
        }
    }

    public Set<Entry<K, V>> entrySet(String id) throws GoldenNodeException {
        ResponseEntity<String> response = RestClient.call("/goldennode/map/id/{mapId}/size".replace("{mapId}", id), HttpMethod.GET);
        if (response.getStatusCodeValue() == HttpStatus.OK.value()) {
            ObjectMapper om = new ObjectMapper();
            TypeReference<Entry<K, V>> typeRef = new TypeReference<Entry<K, V>>() {
            };
            try {
                HashSet<Entry<K, V>> set = om.readValue(response.getBody(), typeRef);
                return set;
            } catch (IOException e) {
                throw new GoldenNodeException(e);
            }
        } else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getBody());
        }
    }
}
