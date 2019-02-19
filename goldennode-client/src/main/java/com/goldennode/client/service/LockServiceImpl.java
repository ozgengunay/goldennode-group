package com.goldennode.client.service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import com.goldennode.client.GoldenNodeException;
import com.goldennode.client.RestClient;

public class LockServiceImpl implements LockService {
    @Override
    public void lock(String lockId) throws GoldenNodeException {
        RestClient.call("/goldennode/lock/id/{lockId}/lock".replace("{lockId}", lockId), "GET");
    }

    @Override
    public void lockInterruptibly(String lockId) throws GoldenNodeException {
        RestClient.call("/goldennode/lock/id/{lockId}/lockInterruptibly".replace("{lockId}", lockId), "GET");
    }

    @Override
    public boolean tryLock(String lockId) throws GoldenNodeException {
        return ((Boolean) RestClient.call("/goldennode/lock/id/{lockId}/tryLock".replace("{lockId}", lockId), "GET").getEntityValue()).booleanValue();
    }

    @Override
    public boolean tryLock(String lockId, long time, TimeUnit unit) throws GoldenNodeException {
        return ((Boolean) RestClient
                .call("/goldennode/lock/id/{lockId}/tryLock/time/{time}/unit/{unit}".replace("{lockId}", lockId).replace("{time}", new Long(time).toString()).replace("{unit}", unit.toString()), "GET")
                .getEntityValue()).booleanValue();
    }

    @Override
    public void unlock(String lockId) throws GoldenNodeException {
        RestClient.call("/goldennode/lock/id/{lockId}/unlock".replace("{lockId}", lockId), "GET");
    }

    @Override
    public Condition newCondition(String lockId) throws GoldenNodeException {
        String conditionId = (String) RestClient.call("/goldennode/lock/id/{lockId}/newCondition".replace("{lockId}", lockId), "GET").getEntityValue();
        return new ConditionImpl(lockId, conditionId);
    }
}
