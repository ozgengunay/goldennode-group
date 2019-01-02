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
import com.goldennode.server.services.QueueService;

@RestController
@RequestMapping(value = { "/goldennode/queue/id/{queueId}" })
@CrossOrigin(origins = "*")
public class QueueController {
    @Autowired
    private QueueService queueService;

    @RequestMapping(value = { "/size" }, method = { RequestMethod.GET })
    public ResponseEntity size(@PathVariable("queueId") String queueId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(queueService.size(userDetails.getUsername(), queueId), StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/isEmpty" }, method = { RequestMethod.GET })
    public ResponseEntity isEmpty(@PathVariable("queueId") String queueId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(queueService.isEmpty(userDetails.getUsername(), queueId), StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/contains/element/{element}" }, method = { RequestMethod.GET })
    public ResponseEntity contains(@PathVariable("queueId") String queueId, @PathVariable("element") String element) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(queueService.contains(userDetails.getUsername(), queueId, URLUtils.unescapeSpecialChars(element)), StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/toArray" }, method = { RequestMethod.GET })
    public ResponseEntity toArray(@PathVariable("queueId") String queueId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(queueService.toArray(userDetails.getUsername(), queueId), StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/remove/element/{element}" }, method = { RequestMethod.DELETE })
    public ResponseEntity remove(@PathVariable("queueId") String queueId, @PathVariable("element") String element) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(queueService.remove(userDetails.getUsername(), queueId, URLUtils.unescapeSpecialChars(element)), StatusCode.SUCCESS);
    }
    /*
     * public boolean containsAll(String userId, String queueId, Collection c) { return init(userId, queueId).containsAll(c); }
     * 
     * public boolean addAll(String userId, String queueId, Collection c) { return init(userId, queueId).addAll(c); }
     * 
     * public boolean removeAll(String userId, String queueId, Collection c) { return init(userId, queueId).removeAll(c); }
     * 
     * public boolean retainAll(String userId, String queueId, Collection c) { return init(userId, queueId).retainAll(c); }
     */

    @RequestMapping(value = { "/clear" }, method = { RequestMethod.DELETE })
    public ResponseEntity clear(@PathVariable("queueId") String queueId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        queueService.clear(userDetails.getUsername(), queueId);
        return new ResponseEntity(null, StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/add" }, method = { RequestMethod.POST })
    public ResponseEntity add(@PathVariable("queueId") String queueId, @RequestBody String data) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(queueService.add(userDetails.getUsername(), queueId, data), StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/offer" }, method = { RequestMethod.POST })
    public ResponseEntity offer(@PathVariable("queueId") String queueId, @RequestBody String data) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(queueService.offer(userDetails.getUsername(), queueId, data), StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/poll" }, method = { RequestMethod.GET })
    public ResponseEntity poll(@PathVariable("queueId") String queueId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(queueService.poll(userDetails.getUsername(), queueId), StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/element" }, method = { RequestMethod.GET })
    public ResponseEntity element(@PathVariable("queueId") String queueId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(queueService.element(userDetails.getUsername(), queueId), StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/peek" }, method = { RequestMethod.GET })
    public ResponseEntity peek(@PathVariable("queueId") String queueId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(queueService.peek(userDetails.getUsername(), queueId), StatusCode.SUCCESS);
    }
}
