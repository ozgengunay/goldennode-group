package com.goldennode.server.services;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.core.HazelcastInstance;

@Service
public class LockService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LockService.class);
    @Autowired
    private HazelcastInstance hzInstance;

    public LockService(HazelcastInstance hzInstance) {
        this.hzInstance = hzInstance;
    }

    private Lock init(String userId, String objectId) {
        return hzInstance.getLock(userId + "_" + objectId);
    }

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
