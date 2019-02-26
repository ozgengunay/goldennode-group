package com.goldennode.client.service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import com.goldennode.client.GoldenNodeException;
import com.goldennode.client.RestClient;

public class LockServiceImpl implements LockService {
    @Override
    public void lock(String lockId, String threadId) throws GoldenNodeException {
        RestClient.call("/goldennode/lock/id/{lockId}/threadId/{threadId}/lock".replace("{lockId}", lockId).replace("{threadId}", threadId), "GET");
    }

    @Override
    public void lockInterruptibly(String lockId, String threadId) throws GoldenNodeException {
        RestClient.call("/goldennode/lock/id/{lockId}/threadId/{threadId}/lockInterruptibly".replace("{lockId}", lockId).replace("{threadId}", threadId), "GET");
    }

    @Override
    public boolean tryLock(String lockId, String threadId) throws GoldenNodeException {
        return ((Boolean) RestClient.call("/goldennode/lock/id/{lockId}/threadId/{threadId}/tryLock".replace("{lockId}", lockId).replace("{threadId}", threadId), "GET").getEntityValue())
                .booleanValue();
    }

    @Override
    public boolean tryLock(String lockId, String threadId, long time, TimeUnit unit) throws GoldenNodeException {
        return ((Boolean) RestClient.call("/goldennode/lock/id/{lockId}/threadId/{threadId}/tryLock/time/{time}/unit/{unit}".replace("{lockId}", lockId).replace("{threadId}", threadId)
                .replace("{time}", new Long(time).toString()).replace("{unit}", unit.toString()), "GET").getEntityValue()).booleanValue();
    }

    @Override
    public void unlock(String lockId, String threadId) throws GoldenNodeException {
        RestClient.call("/goldennode/lock/id/{lockId}/threadId/{threadId}/unlock".replace("{lockId}", lockId).replace("{threadId}", threadId), "GET");
    }

    @Override
    public Condition newCondition(String lockId) throws GoldenNodeException {
        String conditionId = (String) RestClient.call("/goldennode/lock/id/{lockId}/newCondition".replace("{lockId}", lockId), "GET").getEntityValue();
        return new GoldenNodeCondition(lockId, conditionId);
    }
}
