package com.goldennode.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestGoldenNodeList {
    ObjectMapper om = new ObjectMapper();

    @Test
    public void test1() {
        Object response;
        List<TestBean> m0 = new GoldenNodeList<>("list0");
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
        response = m0.add(tb1);
        Assert.assertEquals(true, response);
        response = m0.add(tb2);
        Assert.assertEquals(true, response);
        response = m0.add(tb3);
        Assert.assertEquals(true, response);
        response = m0.get(0);
        Assert.assertEquals(tb1, response);
        response = m0.get(1);
        Assert.assertEquals(tb2, response);
        response = m0.get(2);
        Assert.assertEquals(tb3, response);
        response = m0.size();
        Assert.assertEquals(3, response);
        response = m0.contains(tb1);
        Assert.assertEquals(true, response);
        response = m0.contains(tb2);
        Assert.assertEquals(true, response);
        response = m0.contains(tb3);
        Assert.assertEquals(true, response);
        Iterator<TestBean> iter = m0.iterator();
        while (iter.hasNext()) {
            Assert.assertEquals(tb1, iter.next());
            Assert.assertEquals(tb2, iter.next());
            Assert.assertEquals(tb3, iter.next());
        }
        Assert.assertEquals(tb1, m0.toArray()[0]);
        Assert.assertEquals(tb2, m0.toArray()[1]);
        Assert.assertEquals(tb3, m0.toArray()[2]);
        Assert.assertEquals(tb1, m0.toArray(new TestBean[0])[0]);
        Assert.assertEquals(tb2, m0.toArray(new TestBean[0])[1]);
        Assert.assertEquals(tb3, m0.toArray(new TestBean[0])[2]);
        response = m0.remove(tb1);
        Assert.assertEquals(true, response);
        response = m0.get(0);
        Assert.assertEquals(tb2, response);
        response = m0.size();
        Assert.assertEquals(2, response);
        response = m0.contains(tb2);
        Assert.assertEquals(true, response);
        response = m0.contains(tb3);
        Assert.assertEquals(true, response);
        response = m0.containsAll(m0_);
        Assert.assertEquals(true, response);
        response = m0.isEmpty();
        Assert.assertEquals(false, response);
        response = m0.addAll(m1);
        Assert.assertEquals(true, response);
        response = m0.size();
        Assert.assertEquals(4, response);
        response = m0.removeAll(m1);
        Assert.assertEquals(true, response);
        response = m0.size();
        Assert.assertEquals(2, response);
        response = m0.addAll(0, m2);
        Assert.assertEquals(true, response);
        response = m0.size();
        Assert.assertEquals(3, response);
        response = m0.get(0);
        Assert.assertEquals(tb1_, response);
        response = m0.retainAll(m2);
        Assert.assertEquals(true, response);
        response = m0.size();
        Assert.assertEquals(1, response);
        TestUtil.contentsSame(m0, m2);
        m0.add(1, tb2_);
        response = m0.get(0);
        Assert.assertEquals(tb1_, response);
        response = m0.get(1);
        Assert.assertEquals(tb2_, response);
        m0.set(1, tb1);
        response = m0.get(0);
        Assert.assertEquals(tb1_, response);
        response = m0.get(1);
        Assert.assertEquals(tb1, response);
        response = m0.size();
        Assert.assertEquals(2, response);
        response = m0.remove(0);
        Assert.assertEquals(tb1_, response);
        response = m0.size();
        Assert.assertEquals(1, response);
        response = m0.get(0);
        Assert.assertEquals(tb1, response);
        response = m0.indexOf(tb1);
        Assert.assertEquals(0, response);
        response = m0.lastIndexOf(tb1);
        Assert.assertEquals(0, response);
        response = m0.subList(0, 1);
        Assert.assertEquals(true, TestUtil.contentsSame(m0, (List<?>) response));
        iter = m0.listIterator();
        while (iter.hasNext()) {
            Assert.assertEquals(tb1, iter.next());
        }
        iter = m0.listIterator(0);
        while (iter.hasNext()) {
            Assert.assertEquals(tb1, iter.next());
        }
        m0.clear();
        response = m0.isEmpty();
        Assert.assertEquals(true, response);
        response = m0.add(null);
        Assert.assertEquals(true, response);
        response = m0.size();
        Assert.assertEquals(1, response);
        response = m0.add(null);
        Assert.assertEquals(true, response);
        response = m0.size();
        Assert.assertEquals(2, response);
    }
}
