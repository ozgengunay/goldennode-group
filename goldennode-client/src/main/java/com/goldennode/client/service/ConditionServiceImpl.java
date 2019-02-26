package com.goldennode.client.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import com.goldennode.client.GoldenNodeException;
import com.goldennode.client.RestClient;

public class ConditionServiceImpl implements ConditionService {
    @Override
    public void await(String lockId, String conditionId, String threadId) throws GoldenNodeException {
        RestClient.call(
                "/goldennode/lock/id/{lockId}/threadId/{threadId}/condition/id/{conditionId}/await".replace("{lockId}", lockId).replace("{conditionId}", conditionId).replace("{threadId}", threadId),
                "GET");
    }

    @Override
    public void awaitUninterruptibly(String lockId, String conditionId, String threadId) throws GoldenNodeException {
        RestClient.call("/goldennode/lock/id/{lockId}/threadId/{threadId}/condition/id/{conditionId}/awaitUninterruptibly".replace("{lockId}", lockId).replace("{conditionId}", conditionId), "GET");
    }

    @Override
    public long awaitNanos(String lockId, String conditionId, String threadId, long nanosTimeout) throws GoldenNodeException {
        return ((Long) RestClient.call("/goldennode/lock/id/{lockId}/threadId/{threadId}/condition/id/{conditionId}/awaitNanos/nanosTimeout/{nanosTimeout}".replace("{lockId}", lockId)
                .replace("{conditionId}", conditionId).replace("{nanosTimeout}", new Long(nanosTimeout).toString()), "GET").getEntityValue()).longValue();
    }

    @Override
    public boolean await(String lockId, String conditionId, String threadId, long time, TimeUnit unit) throws GoldenNodeException {
        return ((Boolean) RestClient.call("/goldennode/lock/id/{lockId}/threadId/{threadId}/condition/id/{conditionId}/await/nanosTimeout/{nanosTimeout}".replace("{lockId}", lockId)
                .replace("{conditionId}", conditionId).replace("{nanosTimeout}", unit.toString()), "GET").getEntityValue()).booleanValue();
    }

    @Override
    public boolean awaitUntil(String lockId, String conditionId, String threadId, Date deadline) throws GoldenNodeException {
        return ((Boolean) RestClient.call("/goldennode/lock/id/{lockId}/threadId/{threadId}/condition/id/{conditionId}/awaitUntil/deadline/{deadline}".replace("{lockId}", lockId)
                .replace("{conditionId}", conditionId).replace("{deadline}", new SimpleDateFormat().format(deadline)), "GET").getEntityValue()).booleanValue();
    }

    @Override
    public void signal(String lockId, String conditionId, String threadId) throws GoldenNodeException {
        RestClient.call("/goldennode/lock/id/{lockId}/threadId/{threadId}/condition/id/{conditionId}/signal".replace("{lockId}", lockId).replace("{conditionId}", conditionId), "GET");
    }

    @Override
    public void signalAll(String lockId, String conditionId, String threadId) throws GoldenNodeException {
        RestClient.call("/goldennode/lock/id/{lockId}/threadId/{threadId}/condition/id/{conditionId}/signalAll".replace("{lockId}", lockId).replace("{conditionId}", conditionId), "GET");
    }
}
