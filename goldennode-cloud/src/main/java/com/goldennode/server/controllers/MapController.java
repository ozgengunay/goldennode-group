package com.goldennode.server.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
import com.goldennode.server.common.ErrorCode;
import com.goldennode.server.common.GoldenNodeRestException;
import com.goldennode.server.common.ResponseEntity;
import com.goldennode.server.common.StatusCode;
import com.goldennode.server.security.GoldenNodeUser;
import com.goldennode.server.services.MapService;

@RestController
@RequestMapping(value = { "/goldennode/map/id/{mapId}" })
@CrossOrigin(origins = "*")
public class MapController {
    @Autowired
    private MapService mapService;

    @RequestMapping(value = { "/size" }, method = { RequestMethod.GET })
    public ResponseEntity size(@PathVariable("mapId") String mapId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(mapService.size(userDetails.getUsername(), mapId), StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/isEmpty" }, method = { RequestMethod.GET })
    public ResponseEntity isEmpty(@PathVariable("mapId") String mapId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(mapService.isEmpty(userDetails.getUsername(), mapId), StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/containsKey/key/{key}" }, method = { RequestMethod.GET })
    public ResponseEntity containsKey(@PathVariable("mapId") String mapId, @PathVariable("key") String key) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(mapService.containsKey(userDetails.getUsername(), mapId, replaceFrwdSlsh(key)), StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/containsValue/value/{value}" }, method = { RequestMethod.GET })
    public ResponseEntity containsValue(@PathVariable("mapId") String mapId, @PathVariable("value") String value) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(mapService.containsValue(userDetails.getUsername(), mapId, replaceFrwdSlsh(value)), StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/get/key/{key}" }, method = { RequestMethod.GET })
    public ResponseEntity get(@PathVariable("mapId") String mapId, @PathVariable("key") String key) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(mapService.get(userDetails.getUsername(), mapId, replaceFrwdSlsh(key)), StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/put/key/{key}" }, method = { RequestMethod.POST })
    public ResponseEntity put(@PathVariable("mapId") String mapId, @PathVariable("key") String key, @RequestBody String data) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(mapService.put(userDetails.getUsername(), mapId, replaceFrwdSlsh(key), data), StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/remove/key/{key}" }, method = { RequestMethod.DELETE })
    public ResponseEntity remove(@PathVariable("mapId") String mapId, @PathVariable("key") String key) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(mapService.remove(userDetails.getUsername(), mapId, replaceFrwdSlsh(key)), StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/putAll" }, method = { RequestMethod.POST })
    public ResponseEntity putAll(@PathVariable("mapId") String mapId, @RequestBody String data) throws GoldenNodeRestException {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            ObjectMapper om = new ObjectMapper();
            JsonNode node = om.readTree(data);
            if (!node.isArray())
                throw new GoldenNodeRestException(ErrorCode.EXPECTED_JSON_ARRAY);
            node.iterator();
            Map map = new HashMap<>();
            Iterator<JsonNode> iter = node.iterator();
            while (iter.hasNext()) {
                JsonNode nd = iter.next();
                map.put(nd.fieldNames().next(), nd.fields().next().getValue().asText());
            }
            return new ResponseEntity(mapService.remove(userDetails.getUsername(), mapId, replaceFrwdSlsh(key)), StatusCode.SUCCESS);
        } catch (IOException e) {
            throw new GoldenNodeRestException(ErrorCode.JSON_PROCESSING_ERROR);
        }
    }

    @RequestMapping(value = { "/clear" }, method = { RequestMethod.DELETE })
    public ResponseEntity clear(@PathVariable("mapId") String mapId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        mapService.clear(userDetails.getUsername(), mapId);
        return new ResponseEntity(null, StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/keySet" }, method = { RequestMethod.GET })
    public ResponseEntity keySet(@PathVariable("mapId") String mapId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(mapService.keySet(userDetails.getUsername(), mapId), StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/values" }, method = { RequestMethod.GET })
    public ResponseEntity values(@PathVariable("mapId") String mapId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(mapService.values(userDetails.getUsername(), mapId), StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/entrySet" }, method = { RequestMethod.GET })
    public ResponseEntity entrySet(@PathVariable("mapId") String mapId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(mapService.entrySet(userDetails.getUsername(), mapId), StatusCode.SUCCESS);
    }

    private String replaceFrwdSlsh(String str) {
        return str.replace("&sol;", "/").replace("&quot;", "\"").replace("&bsol;", "\\");
    }
}
