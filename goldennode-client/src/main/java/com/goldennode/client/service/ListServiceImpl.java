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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.Set;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.goldennode.client.GoldenNodeException;
import com.goldennode.client.Response;
import com.goldennode.client.RestClient;
import com.goldennode.client.Utils;
import com.goldennode.commons.util.URLUtils;
import com.goldennode.server.common.ResponseEntity;
import com.goldennode.server.common.StatusCode;
import com.goldennode.server.security.GoldenNodeUser;

public class ListServiceImpl<K, V> implements MapService<K, V> {
    ObjectMapper om = new ObjectMapper();

    
    public int size(String id) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/list/id/{listId}/size".replace("{listId}", id), "GET");
        if (response.getStatusCode() == 200)
            return Integer.parseInt((String) response.getEntityValue());
        else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
        }
    }
    
    public boolean isEmpty(String id) throws GoldenNodeException {
        Response response = RestClient.call("/goldennode/list/id/{listId}/isEmpty".replace("{listId}", id), "GET");
        if (response.getStatusCode() == 200) {
            return Boolean.parseBoolean(response.getEntityValue());
        } else {
            throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
        }
    }

    public boolean contains(String id, Object element) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/map/id/{mapId}/contains/element/{element}".replace("{listId}", id).replace("{element}", Utils.encode(encapObject(element))), "GET");
            if (response.getStatusCode() == 200)
                return Boolean.parseBoolean((String) response.getEntityValue());
            else {
                throw new GoldenNodeException("Error occured" + response.getStatusCode() + " - " + response.getEntityValue());
            }
        } catch (JsonProcessingException e) {
            throw new GoldenNodeException(e);
        }
    }

    @RequestMapping(value = { "/contains/element/{element}" }, method = { RequestMethod.GET })
    public ResponseEntity contains(@PathVariable("listId") String listId, @PathVariable("element") String element) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(listService.contains(userDetails.getUsername(), listId, URLUtils.unescapeSpecialChars(element)), StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/toArray" }, method = { RequestMethod.GET })
    public ResponseEntity toArray(@PathVariable("listId") String listId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(listService.toArray(userDetails.getUsername(), listId), StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/add" }, method = { RequestMethod.POST })
    public ResponseEntity add(@PathVariable("listId") String listId, @RequestBody String data) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(listService.add(userDetails.getUsername(), listId, data), StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/remove/element/{element}" }, method = { RequestMethod.DELETE })
    public ResponseEntity remove(@PathVariable("listId") String listId, @PathVariable("element") String element) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(listService.remove(userDetails.getUsername(), listId, URLUtils.unescapeSpecialChars(element)), StatusCode.SUCCESS);
    }

    /*public ResponseEntity containsAll(@PathVariable("listId") String listId, @PathVariable("objects") Collection objects) {
        return null;
        // return init(userId, listId).containsAll(collection);
    }

    @RequestMapping(value = { "/putAll" }, method = { RequestMethod.POST })
    public ResponseEntity addAll(@PathVariable("listId") String listId, Collection collection) {
        return null;
        // return init(userId, listId).addAll(collection);
    }

    @RequestMapping(value = { "/putAll" }, method = { RequestMethod.POST })
    public ResponseEntity addAll(@PathVariable("listId") String listId, @PathVariable("index") int index, Collection collection) {
        return null;
        // return init(userId, listId).addAll(collection);
    }

    @RequestMapping(value = { "/removeAll" }, method = { RequestMethod.DELETE })
    public ResponseEntity removeAll(@PathVariable("listId") String listId, Collection collection) {
        return null;
        // return init(userId, listId).removeAll(collection);
    }

    public ResponseEntity retainAll(@PathVariable("listId") String listId, Collection collection) {
        return null;
        // return init(userId, listId).retainAll(collection);
    }*/

    @RequestMapping(value = { "/clear" }, method = { RequestMethod.DELETE })
    public ResponseEntity clear(@PathVariable("listId") String listId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        listService.clear(userDetails.getUsername(), listId);
        return new ResponseEntity(null, StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/get/index/{index}" }, method = { RequestMethod.GET })
    public ResponseEntity get(@PathVariable("listId") String listId, @PathVariable("index") int index) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(listService.get(userDetails.getUsername(), listId, index), StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/set/index/{index}" }, method = { RequestMethod.PUT })
    public ResponseEntity set(@PathVariable("listId") String listId, @PathVariable("index") int index,  @RequestBody String data) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(listService.set(userDetails.getUsername(), listId, index, data), StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/add/index/{index}" }, method = { RequestMethod.POST })
    public ResponseEntity add(@PathVariable("listId") String listId, @PathVariable("index") int index,  @RequestBody String data) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        listService.add(userDetails.getUsername(), listId, index, data);
        return new ResponseEntity(null, StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/remove/index/{index}" }, method = { RequestMethod.DELETE })
    public ResponseEntity remove(@PathVariable("listId") String listId, int index) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(listService.remove(userDetails.getUsername(), listId, index), StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/indexOf/element/{element}" }, method = { RequestMethod.GET })
    public ResponseEntity indexOf(@PathVariable("listId") String listId, @PathVariable("element") String element) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(listService.indexOf(userDetails.getUsername(), listId, URLUtils.unescapeSpecialChars(element)), StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/lastIndexOf/element/{element}" }, method = { RequestMethod.GET })
    public ResponseEntity lastIndexOf(@PathVariable("listId") String listId, @PathVariable("element") String element) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(listService.lastIndexOf(userDetails.getUsername(), listId, URLUtils.unescapeSpecialChars(element)), StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/subList/fromIndex/{fromIndex}/toIndex/{toIndex}" }, method = { RequestMethod.GET })
    public ResponseEntity subList(@PathVariable("listId") String listId, int fromIndex, int toIndex) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(listService.subList(userDetails.getUsername(), listId, fromIndex, toIndex), StatusCode.SUCCESS);
    }

    
    /*
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
            Response response = RestClient.call("/goldennode/map/id/{mapId}/containsKey/key/{key}".replace("{mapId}", id).replace("{key}", Utils.encode(encapObject(key))), "GET");
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
            Response response = RestClient.call("/goldennode/map/id/{mapId}/containsValue/value/{value}".replace("{mapId}", id).replace("{value}", Utils.encode(encapObject(value))), "GET");
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
            Response response = RestClient.call("/goldennode/map/id/{mapId}/get/key/{key}".replace("{mapId}", id).replace("{key}", Utils.encode(encapObject(key))), "GET");
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
        if (value == null)
            return null;
        JsonNode node = om.readTree(value);
        String className = node.get("c").asText();
        JsonNode objectNode = node.get("o");
        if (className.equals("NULL")) {
            return null;
        }
        Class<?> c = Class.forName(className);
        return om.treeToValue(objectNode, c);
    }

    public String encapObject(Object value) throws JsonProcessingException {
        JsonNode newNode = om.createObjectNode();
        ((ObjectNode) newNode).put("c", value == null ? "NULL" : value.getClass().getName());
        ((ObjectNode) newNode).set("o", om.valueToTree(value == null ? "NULL" : value));
        return om.writeValueAsString(newNode);
    }

    public V put(String id, K key, V value) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/map/id/{mapId}/put/key/{key}".replace("{mapId}", id).replace("{key}", Utils.encode(encapObject(key))), "POST", encapObject(value));
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

    public V remove(String id, Object key) throws GoldenNodeException {
        try {
            Response response = RestClient.call("/goldennode/map/id/{mapId}/remove/key/{key}".replace("{mapId}", id).replace("{key}", Utils.encode(encapObject(key))), "DELETE");
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
    }*/
}
