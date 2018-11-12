package com.goldennode.server.controllers.rest.controllers;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.goldennode.commons.entity.Thing;

@RestController
@RequestMapping(value = { "/rest/map" })
@CrossOrigin(origins = "*")
public class MapController {
    @Autowired
    private MapService mapService;

    
    
    @RequestMapping(value = { "/{id}" }, method = { RequestMethod.GET })
    public Thing get(@PathVariable("id") String id, @RequestParam("idType") String idType)
    
    
    
    public void init(String userId, String key) {
        mapService.init(userId, key);
    }

    public int size() {
        return mapService.size();
    }

    public boolean isEmpty() {
        return mapService.isEmpty();
    }

    public boolean containsKey(Object key) {
        return mapService.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return mapService.containsValue(value);
    }

    public Object get(Object key) {
        return mapService.get(key);
    }

    public Object put(Object key, Object value) {
        return mapService.put(key, value);
    }

    public Object remove(Object key) {
        return mapService.remove(key);
    }

    public void putAll(Map<? extends Object, ? extends Object> m) {
        mapService.putAll(m);
    }

    public void clear() {
        mapService.clear();
    }

    public Set<Object> keySet() {
        return mapService.keySet();
    }

    public Collection<Object> values() {
        return mapService.values();
    }

    public Set<Entry<Object, Object>> entrySet() {
        return mapService.entrySet();
    }
}
