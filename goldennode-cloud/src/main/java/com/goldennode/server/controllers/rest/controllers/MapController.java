package com.goldennode.server.controllers.rest.controllers;

import java.io.InputStream;
import java.util.Collection;
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
import com.goldennode.server.security.GoldenNodeUserDetails;

@RestController
@RequestMapping(value = { "/goldennode/map/id/{mapId}" })
@CrossOrigin(origins = "*")
public class MapController {
    @Autowired
    private MapService mapService;

    @RequestMapping(value = { "/size" }, method = { RequestMethod.GET })
    public int size(@PathVariable("mapId") String mapId) {
        GoldenNodeUserDetails userDetails = (GoldenNodeUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return mapService.size(userDetails.getUserId(), mapId);
    }

    @RequestMapping(value = { "/isEmpty" }, method = { RequestMethod.GET })
    public boolean isEmpty(@PathVariable("mapId") String mapId) {
        GoldenNodeUserDetails userDetails = (GoldenNodeUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return mapService.isEmpty(userDetails.getUserId(), mapId);
    }

    @RequestMapping(value = { "/containsKey/key/{key}" }, method = { RequestMethod.GET })
    public boolean containsKey(@PathVariable("mapId") String mapId, @PathVariable("key") Object key) {
        GoldenNodeUserDetails userDetails = (GoldenNodeUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return mapService.containsKey(userDetails.getUserId(), mapId, key);
    }

    @RequestMapping(value = { "/containsValue" }, method = { RequestMethod.GET })
    public boolean containsValue(@PathVariable("mapId") String mapId, @RequestBody InputStream value) {
        GoldenNodeUserDetails userDetails = (GoldenNodeUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return mapService.containsValue(userDetails.getUserId(), mapId, value);
    }

    @RequestMapping(value = { "/get/key/{key}" }, method = { RequestMethod.GET })
    public Object get(@PathVariable("mapId") String mapId, @PathVariable("key") Object key) {
        GoldenNodeUserDetails userDetails = (GoldenNodeUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return mapService.get(userDetails.getUserId(), mapId, key);
    }

    @RequestMapping(value = { "/put/key/{key}" }, method = { RequestMethod.POST })
    public Object put(@PathVariable("mapId") String mapId, @PathVariable("key") Object key, @RequestBody String data) {
        GoldenNodeUserDetails userDetails = (GoldenNodeUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return mapService.put(userDetails.getUserId(), mapId, key, data);
    }

    @RequestMapping(value = { "/remove/key/{key}" }, method = { RequestMethod.DELETE })
    public Object remove(@PathVariable("mapId") String mapId, @PathVariable("key") Object key) {
        GoldenNodeUserDetails userDetails = (GoldenNodeUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return mapService.remove(userDetails.getUserId(), mapId, key);
    }

    @RequestMapping(value = { "/putAll" }, method = { RequestMethod.POST })
    public void putAll(@PathVariable("mapId") String mapId, @RequestBody String data) {
        throw new RuntimeException("Not implemented");
        // mapService.putAll(data);
    }

    @RequestMapping(value = { "/clear" }, method = { RequestMethod.DELETE })
    public void clear(@PathVariable("mapId") String mapId) {
        GoldenNodeUserDetails userDetails = (GoldenNodeUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        mapService.clear(userDetails.getUserId(), mapId);
    }

    @RequestMapping(value = { "/keySet" }, method = { RequestMethod.GET })
    public Set<Object> keySet(@PathVariable("mapId") String mapId) {
        GoldenNodeUserDetails userDetails = (GoldenNodeUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return mapService.keySet(userDetails.getUserId(), mapId);
    }

    @RequestMapping(value = { "/values" }, method = { RequestMethod.GET })
    public Collection<Object> values(@PathVariable("mapId") String mapId) {
        GoldenNodeUserDetails userDetails = (GoldenNodeUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return mapService.values(userDetails.getUserId(), mapId);
    }

    @RequestMapping(value = { "/entrySet" }, method = { RequestMethod.GET })
    public Set<Entry<Object, Object>> entrySet(@PathVariable("mapId") String mapId) {
        GoldenNodeUserDetails userDetails = (GoldenNodeUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return mapService.entrySet(userDetails.getUserId(), mapId);
    }
}
