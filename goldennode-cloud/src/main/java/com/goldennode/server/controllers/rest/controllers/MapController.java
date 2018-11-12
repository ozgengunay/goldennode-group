package com.goldennode.server.controllers.rest.controllers;

import java.io.InputStream;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = { "/goldennode/map/id/{mapId}" })
@CrossOrigin(origins = "*")
public class MapController {
    @Autowired
    private MapService mapService;

    @RequestMapping(value = { "/size" }, method = { RequestMethod.GET })
    public int size(@RequestParam("userId") String userId, @PathVariable("mapId") String mapId) {
        return mapService.size(userId, mapId);
    }

    @RequestMapping(value = { "/isEmpty" }, method = { RequestMethod.GET })
    public boolean isEmpty(@RequestParam("userId") String userId, @PathVariable("mapId") String mapId) {
        return mapService.isEmpty(userId, mapId);
    }

    @RequestMapping(value = { "/containsKey/key/{key}" }, method = { RequestMethod.GET })
    public boolean containsKey(@RequestParam("userId") String userId, @PathVariable("mapId") String mapId, @PathVariable("key") Object key) {
        return mapService.containsKey(userId, mapId, key);
    }

    @RequestMapping(value = { "/containsValue" }, method = { RequestMethod.GET })
    public boolean containsValue(@RequestParam("userId") String userId, @PathVariable("mapId") String mapId, @RequestBody InputStream value) {
        return mapService.containsValue(userId, mapId, value);
    }

    @RequestMapping(value = { "/get/key/{key}" }, method = { RequestMethod.GET })
    public Object get(@RequestParam("userId") String userId, @PathVariable("mapId") String mapId, @PathVariable("key") Object key) {
        return mapService.get(userId, mapId, key);
    }

    @RequestMapping(value = { "/put/key/{key}" }, method = { RequestMethod.POST })
    public Object put(@RequestParam("userId") String userId, @PathVariable("mapId") String mapId, @PathVariable("key") Object key, @RequestBody String data) {
        return mapService.put(userId, mapId, key, data);
    }

    @RequestMapping(value = { "/remove/key/{key}" }, method = { RequestMethod.DELETE })
    public Object remove(@RequestParam("userId") String userId, @PathVariable("mapId") String mapId, @PathVariable("key") Object key) {
        return mapService.remove(userId, mapId, key);
    }

    @RequestMapping(value = { "/putAll" }, method = { RequestMethod.POST })
    public void putAll(@RequestParam("userId") String userId, @PathVariable("mapId") String mapId, @RequestBody String data) {
        throw new RuntimeException("Not implemented");
        // mapService.putAll(data);
    }

    @RequestMapping(value = { "/clear" }, method = { RequestMethod.DELETE })
    public void clear(@RequestParam("userId") String userId, @PathVariable("mapId") String mapId) {
        mapService.clear(userId, mapId);
    }

    @RequestMapping(value = { "/keySet" }, method = { RequestMethod.GET })
    public Set<Object> keySet(@RequestParam("userId") String userId, @PathVariable("mapId") String mapId) {
        return mapService.keySet(userId, mapId);
    }

    @RequestMapping(value = { "/values" }, method = { RequestMethod.GET })
    public Collection<Object> values(@RequestParam("userId") String userId, @PathVariable("mapId") String mapId) {
        return mapService.values(userId, mapId);
    }

    @RequestMapping(value = { "/entrySet" }, method = { RequestMethod.GET })
    public Set<Entry<Object, Object>> entrySet(@RequestParam("userId") String userId, @PathVariable("mapId") String mapId) {
        return mapService.entrySet(userId, mapId);
    }
}
