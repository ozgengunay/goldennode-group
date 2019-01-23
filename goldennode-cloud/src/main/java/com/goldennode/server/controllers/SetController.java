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
        return new ResponseEntity(setService.size(userDetails.getUsername(), setId), StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/isEmpty" }, method = { RequestMethod.GET })
    public ResponseEntity isEmpty(@PathVariable("setId") String setId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(setService.isEmpty(userDetails.getUsername(), setId), StatusCode.SUCCESS);
    }
    
   
    @RequestMapping(value = { "/contains/element/{element}" }, method = { RequestMethod.GET })
    public ResponseEntity contains(@PathVariable("setId") String setId, @PathVariable("element") String element) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(setService.contains(userDetails.getUsername(), setId, URLUtils.unescapeSpecialChars(element)), StatusCode.SUCCESS);
    }
 

    @RequestMapping(value = { "/toArray" }, method = { RequestMethod.GET })
    public ResponseEntity toArray(@PathVariable("setId") String setId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(setService.toArray(userDetails.getUsername(), setId), StatusCode.SUCCESS);
    }
    

    @RequestMapping(value = { "/remove/element/{element}" }, method = { RequestMethod.DELETE })
    public ResponseEntity remove(@PathVariable("setId") String setId, @PathVariable("element") String element) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(setService.remove(userDetails.getUsername(), setId, URLUtils.unescapeSpecialChars(element)), StatusCode.SUCCESS);
    }
   

    /*public boolean containsAll(String userId, String setId, Collection c) {
        return init(userId, setId).containsAll(c);
    }

    public boolean addAll(String userId, String setId, Collection c) {
        return init(userId, setId).addAll(c);
    }

    public boolean removeAll(String userId, String setId, Collection c) {
        return init(userId, setId).removeAll(c);
    }

    public boolean retainAll(String userId, String setId, Collection c) {
        return init(userId, setId).retainAll(c);
    }*/

    @RequestMapping(value = { "/clear" }, method = { RequestMethod.DELETE })
    public ResponseEntity clear(@PathVariable("setId") String setId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        setService.clear(userDetails.getUsername(), setId);
        return new ResponseEntity(null, StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/add" }, method = { RequestMethod.POST })
    public ResponseEntity add(@PathVariable("setId") String setId, @RequestBody String data) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(setService.add(userDetails.getUsername(), setId, data), StatusCode.SUCCESS);
    }
    
    
}
