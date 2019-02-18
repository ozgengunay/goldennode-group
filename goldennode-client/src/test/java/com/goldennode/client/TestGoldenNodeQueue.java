package com.goldennode.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
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
        response = m0.isEmpty();
        Assert.assertEquals(false, response);
        response = m0.contains(tb1);
        Assert.assertEquals(true, response);
        response = m0.poll();
        Assert.assertEquals(tb1, response);
        response = m0.poll();
        Assert.assertEquals(tb2, response);
        response = m0.poll();
        Assert.assertEquals(tb3, response);
        response = m0.size();
        Assert.assertEquals(0, response);
        response = m0.contains(tb1);
        Assert.assertEquals(false, response);
        response = m0.add(tb1);
        Assert.assertEquals(true, response);
        response = m0.size();
        Assert.assertEquals(1, response);
        response = m0.remove();
        Assert.assertEquals(tb1, response);
        response = m0.size();
        Assert.assertEquals(0, response);
        try {
            response = m0.remove();
            Assert.fail("should have thrown exception");
        } catch (NoSuchElementException e) {
            // Expected path
        }
        /*
         * Iterator<E> iterator(String queueId);
         * 
         * Object[] toArray(String queueId);
         * 
         * <T> T[] toArray(String queueId, T[] a);
         * 
         * boolean remove(String queueId, Object object);
         * 
         * boolean containsAll(String queueId, Collection<?> collection);
         * 
         * boolean addAll(String queueId, Collection<? extends E> collection);
         * 
         * boolean removeAll(String queueId, Collection<?> collection);
         * 
         * boolean retainAll(String queueId, Collection<?> collection);
         * 
         * void clear(String queueId);
         * 
         * boolean add(String queueId, E element);
         * 
         * boolean offer(String queueId, E element);
         * 
         * E remove(String queueId);
         * 
         * E poll(String queueId);
         * 
         * E element(String queueId);
         * 
         * E peek(String queueId);
         */
    }
}
