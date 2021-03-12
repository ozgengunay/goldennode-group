package com.goldennode.client;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import com.goldennode.client.service.LockService;
import com.goldennode.client.service.LockServiceImpl;

public class GoldenNodeLock implements Lock {
	LockService service;
	private String lockId;

	public GoldenNodeLock() {
		this(UUID.randomUUID().toString());
	}

	public GoldenNodeLock(String lockId) {
		this(lockId, new LockServiceImpl());
	}

	public GoldenNodeLock(String lockId, LockService service) {
		this.lockId = lockId;
		this.service = service;
	}

	@Override
	public void lock() {
		try {
			service.lock(lockId, new Long(Thread.currentThread().getId()).toString());
		} catch (GoldenNodeException e) {
			throw new GoldenNodeRuntimeException(e);
		}
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		try {
			service.lockInterruptibly(lockId, new Long(Thread.currentThread().getId()).toString());
		} catch (GoldenNodeException e) {
			throw new GoldenNodeRuntimeException(e);
		}
	}

	@Override
	public boolean tryLock() {
		try {
			return service.tryLock(lockId, new Long(Thread.currentThread().getId()).toString());
		} catch (GoldenNodeException e) {
			throw new GoldenNodeRuntimeException(e);
		}
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		try {
			return service.tryLock(lockId, new Long(Thread.currentThread().getId()).toString(), time, unit);
		} catch (GoldenNodeException e) {
			throw new GoldenNodeRuntimeException(e);
		}
	}

	@Override
	public void unlock() {
		try {
			service.unlock(lockId, new Long(Thread.currentThread().getId()).toString());
		} catch (GoldenNodeException e) {
			throw new GoldenNodeRuntimeException(e);
		}
	}

	@Override
	public Condition newCondition() {
		try {
			return service.newCondition(lockId);
		} catch (GoldenNodeException e) {
			throw new GoldenNodeRuntimeException(e);
		}
	}
}
