package com.goldennode.server.controllers.rest.controllers;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goldennode.server.security.GoldenNodeUser;

@RestController
@RequestMapping(value = { "/goldennode/map/id/{mapId}" })
@CrossOrigin(origins = "*")
public class MapController {
    @Autowired
    private MapService mapService;

    @RequestMapping(value = { "/size" }, method = { RequestMethod.GET })
    public int size(@PathVariable("mapId") String mapId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return mapService.size(userDetails.getUsername(), mapId);
    }

    @RequestMapping(value = { "/isEmpty" }, method = { RequestMethod.GET })
    public boolean isEmpty(@PathVariable("mapId") String mapId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return mapService.isEmpty(userDetails.getUsername(), mapId);
    }

    @RequestMapping(value = { "/containsKey/key/{key}" }, method = { RequestMethod.GET })
    public boolean containsKey(@PathVariable("mapId") String mapId, @PathVariable("key") String key) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return mapService.containsKey(userDetails.getUsername(), mapId, key);
    }

    @RequestMapping(value = { "/containsValue" }, method = { RequestMethod.POST })
    public boolean containsValue(@PathVariable("mapId") String mapId, @RequestBody String value) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return mapService.containsValue(userDetails.getUsername(), mapId, value);
    }

    @RequestMapping(value = { "/get/key/{key}" }, method = { RequestMethod.GET })
    public String get(@PathVariable("mapId") String mapId, @PathVariable("key") String key) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return mapService.get(userDetails.getUsername(), mapId, key);
    }

    @RequestMapping(value = { "/put/key/{key}" }, method = { RequestMethod.POST })
    public String put(@PathVariable("mapId") String mapId, @PathVariable("key") String key, @RequestBody String data) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return mapService.put(userDetails.getUsername(), mapId, key, data);
    }

    @RequestMapping(value = { "/remove/key/{key}" }, method = { RequestMethod.DELETE })
    public String remove(@PathVariable("mapId") String mapId, @PathVariable("key") String key) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return mapService.remove(userDetails.getUsername(), mapId, key);
    }

    @RequestMapping(value = { "/putAll" }, method = { RequestMethod.POST })
    public void putAll(@PathVariable("mapId") String mapId, @RequestBody String data) throws IOException {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, String> m = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(data);
        if (actualObj.isArray()) {
            for (final JsonNode objNode : actualObj) {
                m.put(objNode.get("key").toString(), objNode.get("value").toString());
            }
        }
        mapService.putAll(userDetails.getId(), mapId, m);
    }

    @RequestMapping(value = { "/clear" }, method = { RequestMethod.DELETE })
    public void clear(@PathVariable("mapId") String mapId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        mapService.clear(userDetails.getUsername(), mapId);
    }

    @RequestMapping(value = { "/keySet" }, method = { RequestMethod.GET })
    public Set<String> keySet(@PathVariable("mapId") String mapId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return mapService.keySet(userDetails.getUsername(), mapId);
    }

    @RequestMapping(value = { "/values" }, method = { RequestMethod.GET })
    public Collection<String> values(@PathVariable("mapId") String mapId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return mapService.values(userDetails.getUsername(), mapId);
    }

    @RequestMapping(value = { "/entrySet" }, method = { RequestMethod.GET })
    public Set<Entry<String, String>> entrySet(@PathVariable("mapId") String mapId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return mapService.entrySet(userDetails.getUsername(), mapId);
    }
}
