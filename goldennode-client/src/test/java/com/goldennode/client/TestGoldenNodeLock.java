package com.goldennode.client;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import org.junit.Assert;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestGoldenNodeLock {
    ObjectMapper om = new ObjectMapper();
    Object response;
    Lock m0;

    @Test
    public void test1() throws InterruptedException {
        m0 = new GoldenNodeLock();
        m0.lock();
        System.out.println("locked");
        m0.unlock();
        System.out.println("unlocked");
        response = m0.tryLock();
        Assert.assertEquals(true, response);
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                response = m0.tryLock();
                Assert.assertEquals(false, response);
            }
        });
        th.start();
        th.join();
        th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    response = m0.tryLock(2000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    //
                }
                Assert.assertEquals(false, response);
            }
        });
        th.start();
        th.join();
        m0.unlock();
    }
}
