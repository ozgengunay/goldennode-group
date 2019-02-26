package com.goldennode.client.service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import com.goldennode.client.GoldenNodeException;

public interface LockService extends Service {
    public void lock(String lockId, String threadId) throws GoldenNodeException;

    public void lockInterruptibly(String lockId, String threadId) throws GoldenNodeException;

    public boolean tryLock(String lockId, String threadId) throws GoldenNodeException;

    public boolean tryLock(String lockId, String threadId, long time, TimeUnit unit) throws GoldenNodeException;

    public void unlock(String lockId, String threadId) throws GoldenNodeException;

    public Condition newCondition(String lockId) throws GoldenNodeException;
}
