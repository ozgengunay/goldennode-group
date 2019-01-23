package com.goldennode.client.service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import com.goldennode.client.GoldenNodeException;

public interface LockService<E> extends Service {
    public void lock(String lockId) throws GoldenNodeException;

    public void lockInterruptibly(String lockId) throws GoldenNodeException;

    public boolean tryLock(String lockId) throws GoldenNodeException;

    public boolean tryLock(String lockId, long time, TimeUnit unit) throws GoldenNodeException;

    public void unlock(String lockId) throws GoldenNodeException;

    public Condition newCondition(String lockId);
}
