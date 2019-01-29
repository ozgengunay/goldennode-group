package com.goldennode.server.controllers;

import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.goldennode.server.common.ResponseEntity;
import com.goldennode.server.common.StatusCode;
import com.goldennode.server.security.GoldenNodeUser;
import com.goldennode.server.services.LockService;

@RestController
@RequestMapping(value = { "/goldennode/lock/id/{lockId}" })
@CrossOrigin(origins = "*")
public class LockController {
    @Autowired
    private LockService lockService;

    @RequestMapping(value = { "/lock" }, method = { RequestMethod.POST })
    public ResponseEntity lock(String userId, String lockId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        lockService.lock(userDetails.getUsername(), lockId);
        return new ResponseEntity(null, StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/lockInterruptibly" }, method = { RequestMethod.POST })
    public ResponseEntity lockInterruptibly(String userId, String lockId) throws InterruptedException {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        lockService.lockInterruptibly(userDetails.getUsername(), lockId);
        return new ResponseEntity(null, StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/tryLock" }, method = { RequestMethod.POST })
    public ResponseEntity tryLock(String userId, String lockId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(lockService.tryLock(userDetails.getUsername(), lockId), StatusCode.SUCCESS);
    }

    @RequestMapping(value = { "/tryLock/time/{time}/timeUnit/{timeUnit}" }, method = { RequestMethod.POST })
    public ResponseEntity tryLock(String userId, String lockId, long time, TimeUnit unit) throws InterruptedException {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(lockService.tryLock(userDetails.getUsername(), lockId, time, unit), StatusCode.SUCCESS);
    }

    public ResponseEntity unlock(String userId, String lockId) {
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        lockService.unlock(userDetails.getUsername(), lockId);
        return new ResponseEntity(null, StatusCode.SUCCESS);
    }

    /*public ResponseEntity newCondition(String userId, String lockId) {
        return init(userId, lockId).newCondition();
    }*/
}
