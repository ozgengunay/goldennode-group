package com.goldennode.server.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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
    private Map<String, ConditionData> conditions = new HashMap<>();

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

    public String newCondition(String userId, String lockId) {
        Condition condition = init(userId, lockId).newCondition();
        ConditionData data = new ConditionData();
        String conditionId = UUID.randomUUID().toString();
        data.setId(userId + "_" + lockId + "_" + conditionId);
        data.setCreatedAt(new Date());
        data.setCondition(condition);
        conditions.put(data.getId(), data);
        return conditionId;
    }

    public void await(String userId, String lockId, String conditionId, String threadId) throws InterruptedException {
        conditions.get(userId + "_" + lockId + "_" + conditionId).getCondition().await();
    }

    public void awaitUninterruptibly(String userId, String lockId, String conditionId, String threadId) {
        conditions.get(userId + "_" + lockId + "_" + conditionId).getCondition().awaitUninterruptibly();
    }

    public long awaitNanos(String userId, String lockId, String conditionId, String threadId, long nanosTimeout) throws InterruptedException {
        return conditions.get(userId + "_" + lockId + "_" + conditionId).getCondition().awaitNanos(nanosTimeout);
    }

    public boolean await(String userId, String lockId, String conditionId, String threadId, long time, TimeUnit unit) throws InterruptedException {
        return conditions.get(userId + "_" + lockId + "_" + conditionId).getCondition().await(time, unit);
    }

    public boolean awaitUntil(String userId, String lockId, String conditionId, String threadId, Date deadline) throws InterruptedException {
        return conditions.get(userId + "_" + lockId + "_" + conditionId).getCondition().awaitUntil(deadline);
    }

    public void signal(String userId, String lockId, String conditionId, String threadId) {
        conditions.get(userId + "_" + lockId + "_" + conditionId).getCondition().signal();
    }

    public void signalAll(String userId, String lockId, String conditionId, String threadId) {
        conditions.get(userId + "_" + lockId + "_" + conditionId).getCondition().signalAll();
    }
}
