package com.goldennode.client.service;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import com.goldennode.client.GoldenNodeException;
import com.goldennode.client.GoldenNodeRuntimeException;

public class GoldenNodeCondition implements Condition {
    private String lockId;
    private String conditionId;
    ConditionService service;

    GoldenNodeCondition(String lockId, String conditionId) {
        this.lockId = lockId;
        this.conditionId = conditionId;
        service = new ConditionServiceImpl();
    }

    @Override
    public void await() throws InterruptedException {
        try {
            service.await(lockId, conditionId, new Long(Thread.currentThread().getId()).toString());
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    @Override
    public void awaitUninterruptibly() {
        try {
            service.await(lockId, conditionId, new Long(Thread.currentThread().getId()).toString());
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    @Override
    public long awaitNanos(long nanosTimeout) throws InterruptedException {
        try {
            return service.awaitNanos(lockId, conditionId, new Long(Thread.currentThread().getId()).toString(), nanosTimeout);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    @Override
    public boolean await(long time, TimeUnit unit) throws InterruptedException {
        try {
            return service.await(lockId, conditionId, new Long(Thread.currentThread().getId()).toString(), time, unit);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    @Override
    public boolean awaitUntil(Date deadline) throws InterruptedException {
        try {
            return service.awaitUntil(lockId, conditionId, new Long(Thread.currentThread().getId()).toString(), deadline);
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    @Override
    public void signal() {
        try {
            service.signal(lockId, conditionId, new Long(Thread.currentThread().getId()).toString());
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }

    @Override
    public void signalAll() {
        try {
            service.signalAll(lockId, conditionId, new Long(Thread.currentThread().getId()).toString());
        } catch (GoldenNodeException e) {
            throw new GoldenNodeRuntimeException(e);
        }
    }
}
