package com.goldennode.client.service;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import com.goldennode.client.GoldenNodeException;

public interface ConditionService {
    public void await(String lockId, String conditionId, String threadId) throws GoldenNodeException;

    public void awaitUninterruptibly(String lockId, String conditionId, String threadId) throws GoldenNodeException;

    public long awaitNanos(String lockId, String conditionId, String threadId, long nanosTimeout) throws GoldenNodeException;

    public boolean await(String lockId, String conditionId, String threadId, long time, TimeUnit unit) throws GoldenNodeException;

    public boolean awaitUntil(String lockId, String conditionId, String threadId, Date deadline) throws GoldenNodeException;

    public void signal(String lockId, String conditionId, String threadId) throws GoldenNodeException;

    public void signalAll(String lockId, String conditionId, String threadId) throws GoldenNodeException;
}
