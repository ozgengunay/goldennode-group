package com.goldennode.server.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
import com.goldennode.commons.util.URLUtils;
import com.goldennode.server.common.ErrorCode;
import com.goldennode.server.common.GoldenNodeRestException;
import com.goldennode.server.common.ResponseEntity;
import com.goldennode.server.security.GoldenNodeUser;
import com.goldennode.server.services.SetService;

@RestController
@RequestMapping(value = { "/goldennode/set/id/{setId}" })
@CrossOrigin(origins = "*")
public class SetController {
    @Autowired
    private SetService setService;

    @RequestMapping(value = { "/size" }, method = { RequestMethod.GET })
    public ResponseEntity size(@PathVariable("setId") String setId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(setService.size(userDetails.getUsername(), setId));
    }

    @RequestMapping(value = { "/isEmpty" }, method = { RequestMethod.GET })
    public ResponseEntity isEmpty(@PathVariable("setId") String setId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(setService.isEmpty(userDetails.getUsername(), setId));
    }

    @RequestMapping(value = { "/contains/object/{object}" }, method = { RequestMethod.GET })
    public ResponseEntity contains(@PathVariable("setId") String setId, @PathVariable("object") String object) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(setService.contains(userDetails.getUsername(), setId, URLUtils.unescapeSpecialChars(object)));
    }

    @RequestMapping(value = { "/toArray" }, method = { RequestMethod.GET })
    public ResponseEntity toArray(@PathVariable("setId") String setId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(setService.toArray(userDetails.getUsername(), setId));
    }

    @RequestMapping(value = { "/remove/object/{object}" }, method = { RequestMethod.DELETE })
    public ResponseEntity remove(@PathVariable("setId") String setId, @PathVariable("object") String object) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(setService.remove(userDetails.getUsername(), setId, URLUtils.unescapeSpecialChars(object)));
    }

    @RequestMapping(value = { "/containsAll" }, method = { RequestMethod.POST })
    public ResponseEntity containsAll(@PathVariable("setId") String setId, @RequestBody String data) throws GoldenNodeRestException {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            ObjectMapper om = new ObjectMapper();
            JsonNode node = om.readTree(data);
            if (!node.isArray())
                throw new GoldenNodeRestException(ErrorCode.EXPECTED_JSON_ARRAY);
            Collection<String> list = new ArrayList<>();
            Iterator<JsonNode> iter = node.iterator();
            while (iter.hasNext()) {
                JsonNode nd = iter.next();
                list.add(nd.asText());
            }
            return new ResponseEntity(setService.containsAll(userDetails.getUsername(), setId, list));
        } catch (IOException e) {
            throw new GoldenNodeRestException(e);
        }
    }

    @RequestMapping(value = { "/addAll" }, method = { RequestMethod.POST })
    public ResponseEntity addAll(String userId, @PathVariable("setId") String setId, @RequestBody String data) throws GoldenNodeRestException {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            ObjectMapper om = new ObjectMapper();
            JsonNode node = om.readTree(data);
            if (!node.isArray())
                throw new GoldenNodeRestException(ErrorCode.EXPECTED_JSON_ARRAY);
            Collection<String> list = new ArrayList<>();
            Iterator<JsonNode> iter = node.iterator();
            while (iter.hasNext()) {
                JsonNode nd = iter.next();
                list.add(nd.asText());
            }
            return new ResponseEntity(setService.addAll(userDetails.getUsername(), setId, list));
        } catch (IOException e) {
            throw new GoldenNodeRestException(e);
        }
    }

    @RequestMapping(value = { "/removeAll" }, method = { RequestMethod.PUT })
    public ResponseEntity removeAll(@PathVariable("setId") String setId, @RequestBody String data) throws GoldenNodeRestException {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            ObjectMapper om = new ObjectMapper();
            JsonNode node = om.readTree(data);
            if (!node.isArray())
                throw new GoldenNodeRestException(ErrorCode.EXPECTED_JSON_ARRAY);
            Collection<String> list = new ArrayList<>();
            Iterator<JsonNode> iter = node.iterator();
            while (iter.hasNext()) {
                JsonNode nd = iter.next();
                list.add(nd.asText());
            }
            return new ResponseEntity(setService.removeAll(userDetails.getUsername(), setId, list));
        } catch (IOException e) {
            throw new GoldenNodeRestException(e);
        }
    }

    @RequestMapping(value = { "/retainAll" }, method = { RequestMethod.PUT })
    public ResponseEntity retainAll(@PathVariable("setId") String setId, @RequestBody String data) throws GoldenNodeRestException {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            ObjectMapper om = new ObjectMapper();
            JsonNode node = om.readTree(data);
            if (!node.isArray())
                throw new GoldenNodeRestException(ErrorCode.EXPECTED_JSON_ARRAY);
            Collection<String> list = new ArrayList<>();
            Iterator<JsonNode> iter = node.iterator();
            while (iter.hasNext()) {
                JsonNode nd = iter.next();
                list.add(nd.asText());
            }
            return new ResponseEntity(setService.retainAll(userDetails.getUsername(), setId, list));
        } catch (IOException e) {
            throw new GoldenNodeRestException(e);
        }
    }

    @RequestMapping(value = { "/clear" }, method = { RequestMethod.DELETE })
    public ResponseEntity clear(@PathVariable("setId") String setId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        setService.clear(userDetails.getUsername(), setId);
        return new ResponseEntity(null);
    }

    @RequestMapping(value = { "/add" }, method = { RequestMethod.POST })
    public ResponseEntity add(@PathVariable("setId") String setId, @RequestBody String data) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(setService.add(userDetails.getUsername(), setId, data));
    }
}
