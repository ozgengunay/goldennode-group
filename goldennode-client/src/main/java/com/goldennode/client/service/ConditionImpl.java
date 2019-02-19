package com.goldennode.client.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import com.goldennode.client.GoldenNodeException;
import com.goldennode.client.GoldenNodeRuntimeException;
import com.goldennode.client.RestClient;

public class ConditionImpl implements Condition {
    private String lockId;
    private String conditionId;

    public ConditionImpl(String lockId, String conditionId) {
        this.lockId = lockId;
        this.conditionId = conditionId;
    }

    @Override
    public void await() throws InterruptedException {
        try {
            RestClient.call("/goldennode/lock/id/{lockId}/condition/id/{conditionId}/await".replace("{lockId}", lockId).replace("{conditionId}", conditionId), "GET");
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    @Override
    public void awaitUninterruptibly() {
        try {
            RestClient.call("/goldennode/lock/id/{lockId}/condition/id/{conditionId}/awaitUninterruptibly".replace("{lockId}", lockId).replace("{conditionId}", conditionId), "GET");
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    @Override
    public long awaitNanos(long nanosTimeout) throws InterruptedException {
        try {
            return ((Long) RestClient.call("/goldennode/lock/id/{lockId}/condition/id/{conditionId}/awaitNanos/nanosTimeout/{nanosTimeout}".replace("{lockId}", lockId)
                    .replace("{conditionId}", conditionId).replace("{nanosTimeout}", new Long(nanosTimeout).toString()), "GET").getEntityValue()).longValue();
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    @Override
    public boolean await(long time, TimeUnit unit) throws InterruptedException {
        try {
            return ((Boolean) RestClient.call("/goldennode/lock/id/{lockId}/condition/id/{conditionId}/await/nanosTimeout/{nanosTimeout}".replace("{lockId}", lockId)
                    .replace("{conditionId}", conditionId).replace("{nanosTimeout}", unit.toString()), "GET").getEntityValue()).booleanValue();
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    @Override
    public boolean awaitUntil(Date deadline) throws InterruptedException {
        try {
            return ((Boolean) RestClient.call("/goldennode/lock/id/{lockId}/condition/id/{conditionId}/awaitUntil/deadline/{deadline}".replace("{lockId}", lockId).replace("{conditionId}", conditionId)
                    .replace("{deadline}", new SimpleDateFormat().format(deadline)), "GET").getEntityValue()).booleanValue();
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    @Override
    public void signal() {
        try {
            RestClient.call("/goldennode/lock/id/{lockId}/condition/id/{conditionId}/signal".replace("{lockId}", lockId).replace("{conditionId}", conditionId), "GET");
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    @Override
    public void signalAll() {
        try {
            RestClient.call("/goldennode/lock/id/{lockId}/condition/id/{conditionId}/signalAll".replace("{lockId}", lockId).replace("{conditionId}", conditionId), "GET");
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }
}
