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

    private Lock init(String userId, String lockId) {
        return hzInstance.getLock(userId + "_" + lockId);
    }

    public void lock(String userId, String lockId) {
        init(userId, lockId).lock();
    }

    public void lockInterruptibly(String userId, String lockId) throws InterruptedException {
        init(userId, lockId).lockInterruptibly();
    }

    public boolean tryLock(String userId, String lockId) {
        return init(userId, lockId).tryLock();
    }

    public boolean tryLock(String userId, String lockId, long time, TimeUnit unit) throws InterruptedException {
        return init(userId, lockId).tryLock(time, unit);
    }

    public void unlock(String userId, String lockId) {
        init(userId, lockId).unlock();
    }

    public Condition newCondition(String userId, String lockId) {
        return init(userId, lockId).newCondition();
    }
}
