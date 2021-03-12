package com.goldennode.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestGoldenNodeSet {
    ObjectMapper om = new ObjectMapper();

    @Test
    public void test1() {
        Object response;
        Set<TestBean> m0 = new GoldenNodeSet<>("list0");
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
        m2.add(tb3);
        Collection<TestBean> iteratorContents = new ArrayList<TestBean>();
        iteratorContents.add(null);
        iteratorContents.add(tb1);
        iteratorContents.add(tb2);
        iteratorContents.add(tb3);
        response = m0.add(null);
        Assert.assertEquals(true, response);
        response = m0.add(tb1);
        Assert.assertEquals(true, response);
        response = m0.add(tb2);
        Assert.assertEquals(true, response);
        response = m0.add(tb3);
        Assert.assertEquals(true, response);
        response = m0.contains(tb1);
        Assert.assertEquals(true, response);
        response = m0.contains(tb2);
        Assert.assertEquals(true, response);
        response = m0.contains(tb3);
        Assert.assertEquals(true, response);
        response = m0.size();
        Assert.assertEquals(4, response);
        response = m0.contains(tb1);
        Assert.assertEquals(true, response);
        response = m0.contains(tb2);
        Assert.assertEquals(true, response);
        response = m0.contains(tb3);
        Assert.assertEquals(true, response);
        Iterator<TestBean> iter = m0.iterator();
        while (iter.hasNext()) {
            Assert.assertTrue(iteratorContents.contains(iter.next()));
        }
        for (Object tb : m0.toArray()) {
            Assert.assertTrue(iteratorContents.contains(tb));
        }
        for (TestBean tb : m0.toArray(new TestBean[0])) {
            Assert.assertTrue(iteratorContents.contains(tb));
        }
        response = m0.remove(tb1);
        Assert.assertEquals(true, response);
        response = m0.contains(tb2);
        Assert.assertEquals(true, response);
        response = m0.size();
        Assert.assertEquals(3, response);
        response = m0.contains(tb3);
        Assert.assertEquals(true, response);
        response = m0.containsAll(m0_);
        Assert.assertEquals(true, response);
        response = m0.isEmpty();
        Assert.assertEquals(false, response);
        response = m0.addAll(m1);
        Assert.assertEquals(true, response);
        response = m0.size();
        Assert.assertEquals(5, response);
        response = m0.removeAll(m1);
        Assert.assertEquals(true, response);
        response = m0.size();
        Assert.assertEquals(3, response);
        response = m0.retainAll(m2);
        Assert.assertEquals(true, response);
        response = m0.size();
        Assert.assertEquals(1, response);
        response = m0.contains(tb3);
        Assert.assertEquals(true, response);
        m0.clear();
        response = m0.isEmpty();
        Assert.assertEquals(true, response);
        response = m0.add(null);
        Assert.assertEquals(true, response);
        response = m0.size();
        Assert.assertEquals(1, response);
        response = m0.add(null);
        Assert.assertEquals(false, response);
        response = m0.size();
        Assert.assertEquals(1, response);
    }
}
