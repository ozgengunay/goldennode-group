package com.goldennode.server.controllers;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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

    @RequestMapping(value = { "/contains/object/{object}" }, method = { RequestMethod.GET })
    public ResponseEntity contains(@PathVariable("listId") String listId, @PathVariable("object") String object) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(listService.contains(userDetails.getUsername(), listId, object), StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/iterator" }, method = { RequestMethod.GET })
    public ResponseEntity iterator(@PathVariable("listId") String listId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(listService.iterator(userDetails.getUsername(), listId), StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/toArray" }, method = { RequestMethod.GET })
    public ResponseEntity toArray(@PathVariable("listId") String listId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(listService.toArray(userDetails.getUsername(), listId), StatusCode.SUCCESS);
    }


    @RequestMapping(value = { "/add/object/{object}" }, method = { RequestMethod.POST })
    public ResponseEntity add(@PathVariable("listId") String listId, @PathVariable("object")String object) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(listService.add(userDetails.getUsername(), replaceFrwdSlsh(object)), StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/remove/key/{key}" }, method = { RequestMethod.DELETE })
    public ResponseEntity remove(@PathVariable("listId") String listId,@PathVariable("object") String object) {
        return init(userId, listId).remove(o);
    }

    public ResponseEntity containsAll(@PathVariable("listId") String listId, @PathVariable("objects") Collection objects) {
        return init(userId, listId).containsAll(c);
    }

    @RequestMapping(value = { "/putAll" }, method = { RequestMethod.POST })
    public ResponseEntity addAll(@PathVariable("listId") String listId, Collection c) {
        return init(userId, listId).addAll(c);
    }

    @RequestMapping(value = { "/putAll" }, method = { RequestMethod.POST })
    public ResponseEntity addAll(@PathVariable("listId") String listId, @PathVariable("index") int index, Collection c) {
        return init(userId, listId).addAll(c);
    }

    @RequestMapping(value = { "/remove/key/{key}" }, method = { RequestMethod.DELETE })
    public ResponseEntity removeAll(@PathVariable("listId") String listId, Collection c) {
        return init(userId, listId).removeAll(c);
    }

    public ResponseEntity retainAll(@PathVariable("listId") String listId, Collection c) {
        return init(userId, listId).retainAll(c);
    }

    @RequestMapping(value = { "/remove/key/{key}" }, method = { RequestMethod.DELETE })
    public ResponseEntity clear(@PathVariable("listId") String listId) {
        init(userId, listId).clear();
    }

    @RequestMapping(value = { "/get/key/{key}" }, method = { RequestMethod.GET })
    public ResponseEntity get(@PathVariable("listId") String listId, @PathVariable("index") int index) {
        return init(userId, listId).get(index);
    }

    public ResponseEntity set(@PathVariable("listId") String listId, @PathVariable("index") int index, @PathVariable("object") Object object) {
        return init(userId, listId).set(index, element);
    }

    public ResponseEntity add(@PathVariable("listId") String listId, @PathVariable("index")int index, @PathVariable("object")Object object) {
        init(userId, listId).add(index, element);
    }

    @RequestMapping(value = { "/remove/key/{key}" }, method = { RequestMethod.DELETE })
    public ResponseEntity remove(@PathVariable("listId") String listId, int index) {
        return init(userId, listId).remove(index);
    }

    public ResponseEntity indexOf(@PathVariable("listId") String listId, @PathVariable("object")Object o) {
        return init(userId, listId).indexOf(o);
    }

    public ResponseEntity lastIndexOf(@PathVariable("listId") String listId, @PathVariable("object")Object o) {
        return init(userId, listId).lastIndexOf(o);
    }

    public ResponseEntity listIterator(@PathVariable("listId") String listId) {
        return init(userId, listId).listIterator();
    }

    public ResponseEntity listIterator(@PathVariable("listId") String listId,@PathVariable("index") int index) {
        return init(userId, listId).listIterator(index);
    }

    public ResponseEntity subList(@PathVariable("listId") String listId, int fromIndex, int toIndex) {
        return init(userId, listId).subList(fromIndex, toIndex);
    }
}
