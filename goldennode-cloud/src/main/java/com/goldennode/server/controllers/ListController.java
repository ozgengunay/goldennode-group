package com.goldennode.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.goldennode.commons.util.URLUtils;
import com.goldennode.server.common.ResponseEntity;
import com.goldennode.server.common.StatusCode;
import com.goldennode.server.security.GoldenNodeUser;
import com.goldennode.server.services.ListService;

@RestController
@RequestMapping(value = { "/goldennode/list/id/{listId}" })
@CrossOrigin(origins = "*")
public class ListController {
    @Autowired
    private ListService listService;

    @RequestMapping(value = { "/size" }, method = { RequestMethod.GET })
    public ResponseEntity size(@PathVariable("listId") String listId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(listService.size(userDetails.getUsername(), listId), StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/isEmpty" }, method = { RequestMethod.GET })
    public ResponseEntity isEmpty(@PathVariable("listId") String listId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(listService.isEmpty(userDetails.getUsername(), listId), StatusCode.SUCCESS);
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
}
