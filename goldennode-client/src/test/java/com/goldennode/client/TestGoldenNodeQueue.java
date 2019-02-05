package com.goldennode.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import org.junit.Assert;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestGoldenNodeQueue {
    ObjectMapper om = new ObjectMapper();

    @Test
    public void test1() {
        Object response;
        Queue<TestBean> m0 = new GoldenNodeQueue<>("list0");
        m0.clear();
        response = m0.isEmpty();
        Assert.assertEquals(true, response);
        TestBean tb1 = new TestBean("String1", 1);
        TestBean tb2 = new TestBean("String2", 2);
        TestBean tb3 = new TestBean("~!@#$%^&*()_+{}:\"|<>?>[];'\\,./", 3);
        TestBean tb1_ = new TestBean("String1_", 10);
        TestBean tb2_ = new TestBean("String2_", 20);
        Collection<TestBean> m0_ = new ArrayList<TestBean>();
        m0_.add(tb2);
        m0_.add(tb3);
        Collection<TestBean> m1 = new ArrayList<TestBean>();
        m1.add(tb1_);
        m1.add(tb2_);
        Collection<TestBean> m2 = new ArrayList<TestBean>();
        m2.add(tb1_);
        response = m0.offer(tb1);
        Assert.assertEquals(true, response);
        response = m0.offer(tb2);
        Assert.assertEquals(true, response);
        response = m0.offer(tb3);
        Assert.assertEquals(true, response);
        response = m0.size();
        Assert.assertEquals(3, response);
        response = m0.poll();
        Assert.assertEquals(tb1, response);
        response = m0.poll();
        Assert.assertEquals(tb2, response);
        response = m0.poll();
        Assert.assertEquals(tb3, response);
        response = m0.size();
        Assert.assertEquals(3, response);
    }
}
