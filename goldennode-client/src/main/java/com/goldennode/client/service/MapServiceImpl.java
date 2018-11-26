package com.goldennode.client.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.goldennode.commons.util.GoldenNodeException;
import com.goldennode.commons.util.RestClient;

public class MapServiceImpl<K extends Serializable, V extends Serializable> implements MapService<K, V> {
    public int size(String id) throws GoldenNodeException {
        ResponseEntity<String> response= RestClient.call("/goldennode/map/id/{mapId}/size".replace("{mapId}", id), HttpMethod.GET);
        if( response.getStatusCodeValue()==HttpStatus.OK.value())
            return Integer.parseInt(response.getBody());
        else {
            throw new GoldenNodeException("Error occured" +response.getStatusCode()+" - "+ response.getBody());a
        }
    }

    public boolean isEmpty(String id) throws GoldenNodeException {
        ResponseEntity<String> response = RestClient.call("/goldennode/map/id/{mapId}/size".replace("{mapId}", id), HttpMethod.GET);
        if (response.getStatusCodeValue() == HttpStatus.OK.value())
            return Boolean.parseBoolean(response.getBody());
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getBody());
        }
    }

    public boolean containsKey(String id, Object key)  throws GoldenNodeException{
        ResponseEntity<String> response = RestClient.call("/goldennode/map/id/{mapId}/size".replace("{mapId}", id), HttpMethod.GET);
        if (response.getStatusCodeValue() == HttpStatus.OK.value())
            return Boolean.parseBoolean(response.getBody());
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getBody());
        }
    }

    public boolean containsValue(String id, Object value)  throws GoldenNodeException{
        ResponseEntity<String> response = RestClient.call("/goldennode/map/id/{mapId}/size".replace("{mapId}", id), HttpMethod.GET);
        if (response.getStatusCodeValue() == HttpStatus.OK.value())
            return Boolean.parseBoolean(response.getBody());
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getBody());
        }
    }

    public V get(String id, Object key) {
        ResponseEntity<String> response = RestClient.call("/goldennode/map/id/{mapId}/size".replace("{mapId}", id), HttpMethod.GET);
        if (response.getStatusCodeValue() == HttpStatus.OK.value())
            return Boolean.parseBoolean(response.getBody());
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getBody());
    }

    public V put(String id, K key, V value) {
        ResponseEntity<String> response = RestClient.call("/goldennode/map/id/{mapId}/size".replace("{mapId}", id), HttpMethod.GET);
        if (response.getStatusCodeValue() == HttpStatus.OK.value())
            return Boolean.parseBoolean(response.getBody());
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getBody());
        }
    }

    public V remove(String id, Object key) {
        ResponseEntity<String> response = RestClient.call("/goldennode/map/id/{mapId}/size".replace("{mapId}", id), HttpMethod.GET);
        if (response.getStatusCodeValue() == HttpStatus.OK.value())
            return Boolean.parseBoolean(response.getBody());
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getBody());
        }
    }

    public void putAll(String id, Map<? extends K, ? extends V> m) {
        ResponseEntity<String> response = RestClient.call("/goldennode/map/id/{mapId}/size".replace("{mapId}", id), HttpMethod.GET);
        if (response.getStatusCodeValue() == HttpStatus.OK.value())
            return Boolean.parseBoolean(response.getBody());
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getBody());
        }
    }

    public void clear(String id) {
        ResponseEntity<String> response = RestClient.call("/goldennode/map/id/{mapId}/size".replace("{mapId}", id), HttpMethod.GET);
        if (response.getStatusCodeValue() == HttpStatus.OK.value())
            return Boolean.parseBoolean(response.getBody());
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getBody());
        }
    }

    public Set<K> keySet(String id) {
        ResponseEntity<String> response = RestClient.call("/goldennode/map/id/{mapId}/size".replace("{mapId}", id), HttpMethod.GET);
        if (response.getStatusCodeValue() == HttpStatus.OK.value())
            return Boolean.parseBoolean(response.getBody());
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getBody());
        }
    }

    public Collection<V> values(String id) {
        ResponseEntity<String> response = RestClient.call("/goldennode/map/id/{mapId}/size".replace("{mapId}", id), HttpMethod.GET);
        if (response.getStatusCodeValue() == HttpStatus.OK.value())
            return Boolean.parseBoolean(response.getBody());
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getBody());
        }
    }

    public Set<Entry<K, V>> entrySet(String id) {
        ResponseEntity<String> response = RestClient.call("/goldennode/map/id/{mapId}/size".replace("{mapId}", id), HttpMethod.GET);
        if (response.getStatusCodeValue() == HttpStatus.OK.value())
            return Boolean.parseBoolean(response.getBody());
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getBody());
        }
    }
}
