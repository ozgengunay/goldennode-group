package com.goldennode.server.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
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
public class LockController {
    @Autowired
    private MapService mapService;

    public void lock(String userId, String objectId) {
        init(userId, objectId).lock();
    }

    public void lockInterruptibly(String userId, String objectId) throws InterruptedException {
        init(userId, objectId).lockInterruptibly();
    }

    public boolean tryLock(String userId, String objectId) {
        return init(userId, objectId).tryLock();
    }

    public boolean tryLock(String userId, String objectId, long time, TimeUnit unit) throws InterruptedException {
        return init(userId, objectId).tryLock(time, unit);
    }

    public void unlock(String userId, String objectId) {
        init(userId, objectId).unlock();
    }

    public Condition newCondition(String userId, String objectId) {
        return init(userId, objectId).newCondition();
    }
}
