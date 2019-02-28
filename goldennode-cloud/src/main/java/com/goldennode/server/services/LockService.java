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
import com.goldennode.server.controllers.GoldenThread;
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

    public void lock(String userId, String lockId, String threadId) throws Exception {
        LockRunnable lr = new LockRunnable() {
            @Override
            public void run() {
                try {
                    init(userId, lockId).lock();
                } catch (Exception e) {
                    setException(e);
                }
            }
        };
        Thread th = new GoldenThread(new Long(threadId), lr);
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            throw e;
        }
        if (lr.getException() != null)
            throw lr.getException();
    }

    public void lockInterruptibly(String userId, String lockId, String threadId) throws Exception {
        LockRunnable lr = new LockRunnable() {
            @Override
            public void run() {
                try {
                    init(userId, lockId).lockInterruptibly();
                } catch (Exception e) {
                    setException(e);
                }
            }
        };
        Thread th = new GoldenThread(new Long(threadId), lr);
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            throw e;
        }
        if (lr.getException() != null)
            throw lr.getException();
    }

    public boolean tryLock(String userId, String lockId, String threadId) throws Exception {
        LockRunnable lr = new LockRunnable() {
            @Override
            public void run() {
                try {
                    setResult(init(userId, lockId).tryLock());
                } catch (Exception e) {
                    setException(e);
                }
            }
        };
        Thread th = new GoldenThread(new Long(threadId), lr);
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            throw e;
        }
        if (lr.getException() != null)
            throw lr.getException();
        return (Boolean) lr.getResult();
    }

    public boolean tryLock(String userId, String lockId, String threadId, long time, TimeUnit unit) throws Exception {
        LockRunnable lr = new LockRunnable() {
            @Override
            public void run() {
                try {
                    setResult(init(userId, lockId).tryLock(time, unit));
                } catch (Exception e) {
                    setException(e);
                }
            }
        };
        Thread th = new GoldenThread(new Long(threadId), lr);
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            throw e;
        }
        if (lr.getException() != null)
            throw lr.getException();
        return (Boolean) lr.getResult();
    }

    public void unlock(String userId, String lockId, String threadId) throws Exception {
        LockRunnable lr = new LockRunnable() {
            @Override
            public void run() {
                try {
                    init(userId, lockId).unlock();
                } catch (Exception e) {
                    setException(e);
                }
            }
        };
        Thread th = new GoldenThread(new Long(threadId), lr);
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            throw e;
        }
        if (lr.getException() != null)
            throw lr.getException();
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

    public void await(String userId, String lockId, String conditionId, String threadId) throws Exception {
        LockRunnable lr = new LockRunnable() {
            @Override
            public void run() {
                try {
                    conditions.get(userId + "_" + lockId + "_" + conditionId).getCondition().await();
                } catch (Exception e) {
                    setException(e);
                }
            }
        };
        Thread th = new GoldenThread(new Long(threadId), lr);
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            throw e;
        }
        if (lr.getException() != null)
            throw lr.getException();
    }

    public void awaitUninterruptibly(String userId, String lockId, String conditionId, String threadId) throws Exception {
        LockRunnable lr = new LockRunnable() {
            @Override
            public void run() {
                try {
                    conditions.get(userId + "_" + lockId + "_" + conditionId).getCondition().awaitUninterruptibly();
                } catch (Exception e) {
                    setException(e);
                }
            }
        };
        Thread th = new GoldenThread(new Long(threadId), lr);
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            throw e;
        }
        if (lr.getException() != null)
            throw lr.getException();
    }

    public long awaitNanos(String userId, String lockId, String conditionId, String threadId, long nanosTimeout) throws Exception {
        LockRunnable lr = new LockRunnable() {
            @Override
            public void run() {
                try {
                    setResult(conditions.get(userId + "_" + lockId + "_" + conditionId).getCondition().awaitNanos(nanosTimeout));
                } catch (Exception e) {
                    setException(e);
                }
            }
        };
        Thread th = new GoldenThread(new Long(threadId), lr);
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            throw e;
        }
        if (lr.getException() != null)
            throw lr.getException();
        return (Long) lr.getResult();
    }

    public boolean await(String userId, String lockId, String conditionId, String threadId, long time, TimeUnit unit) throws Exception {
        LockRunnable lr = new LockRunnable() {
            @Override
            public void run() {
                try {
                    setResult(conditions.get(userId + "_" + lockId + "_" + conditionId).getCondition().await(time, unit));
                } catch (Exception e) {
                    setException(e);
                }
            }
        };
        Thread th = new GoldenThread(new Long(threadId), lr);
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            throw e;
        }
        if (lr.getException() != null)
            throw lr.getException();
        return (Boolean) lr.getResult();
    }

    public boolean awaitUntil(String userId, String lockId, String conditionId, String threadId, Date deadline) throws Exception {
        LockRunnable lr = new LockRunnable() {
            @Override
            public void run() {
                try {
                    setResult(conditions.get(userId + "_" + lockId + "_" + conditionId).getCondition().awaitUntil(deadline));
                } catch (Exception e) {
                    setException(e);
                }
            }
        };
        Thread th = new GoldenThread(new Long(threadId), lr);
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            throw e;
        }
        if (lr.getException() != null)
            throw lr.getException();
        return (Boolean) lr.getResult();
    }

    public void signal(String userId, String lockId, String conditionId, String threadId) throws Exception {
        LockRunnable lr = new LockRunnable() {
            @Override
            public void run() {
                try {
                    conditions.get(userId + "_" + lockId + "_" + conditionId).getCondition().signal();
                } catch (Exception e) {
                    setException(e);
                }
            }
        };
        Thread th = new GoldenThread(new Long(threadId), lr);
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            throw e;
        }
        if (lr.getException() != null)
            throw lr.getException();
    }

    public void signalAll(String userId, String lockId, String conditionId, String threadId) throws Exception {
        LockRunnable lr = new LockRunnable() {
            @Override
            public void run() {
                try {
                    conditions.get(userId + "_" + lockId + "_" + conditionId).getCondition().signalAll();
                } catch (Exception e) {
                    setException(e);
                }
            }
        };
        Thread th = new GoldenThread(new Long(threadId), lr);
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            throw e;
        }
        if (lr.getException() != null)
            throw lr.getException();
    }
}
