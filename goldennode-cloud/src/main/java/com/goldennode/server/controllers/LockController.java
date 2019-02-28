package com.goldennode.server.controllers;

import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.goldennode.server.common.GoldenNodeRestException;
import com.goldennode.server.common.ResponseEntity;
import com.goldennode.server.security.GoldenNodeUser;
import com.goldennode.server.services.LockService;

@RestController
@RequestMapping(value = { "/goldennode/lock/id/{lockId}" })
@CrossOrigin(origins = "*")
public class LockController {
    @Autowired
    private LockService lockService;

    @RequestMapping(value = { "/threadId/{threadId}/lock" }, method = { RequestMethod.GET })
    public ResponseEntity lock(@PathVariable("lockId") String lockId, @PathVariable("threadId") String threadId) throws Exception {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        lockService.lock(userDetails.getUsername(), lockId, threadId);
        return new ResponseEntity(null);
    }

    @RequestMapping(value = { "/threadId/{threadId}/lockInterruptibly" }, method = { RequestMethod.GET })
    public ResponseEntity lockInterruptibly(@PathVariable("lockId") String lockId, @PathVariable("threadId") String threadId) throws Exception {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            lockService.lockInterruptibly(userDetails.getUsername(), lockId, threadId);
        } catch (InterruptedException e) {
            throw new GoldenNodeRestException(e);
        }
        return new ResponseEntity(null);
    }

    @RequestMapping(value = { "/threadId/{threadId}/tryLock" }, method = { RequestMethod.GET })
    public ResponseEntity tryLock(@PathVariable("lockId") String lockId, @PathVariable("threadId") String threadId) throws Exception {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(lockService.tryLock(userDetails.getUsername(), lockId, threadId));
    }

    @RequestMapping(value = { "/threadId/{threadId}/tryLock/time/{time}/unit/{unit}" }, method = { RequestMethod.GET })
    public ResponseEntity tryLock(@PathVariable("lockId") String lockId, @PathVariable("threadId") String threadId, @PathVariable("time") long time, @PathVariable("unit") TimeUnit unit)
            throws Exception {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(lockService.tryLock(userDetails.getUsername(), lockId, threadId, time, unit));
    }

    @RequestMapping(value = { "/threadId/{threadId}/unlock" }, method = { RequestMethod.GET })
    public ResponseEntity unlock(@PathVariable("lockId") String lockId, @PathVariable("threadId") String threadId) throws Exception {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        lockService.unlock(userDetails.getUsername(), lockId, threadId);
        return new ResponseEntity(null);
    }

    @RequestMapping(value = { "/newCondition" }, method = { RequestMethod.GET })
    public ResponseEntity newCondition(@PathVariable("lockId") String lockId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(lockService.newCondition(userDetails.getUsername(), lockId));
    }
}
