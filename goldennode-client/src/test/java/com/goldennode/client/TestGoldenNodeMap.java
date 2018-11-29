package com.goldennode.client;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

public class TestGoldenNodeMap {
    @Test
    public void test1() {
        Map<String, TestBean> m0 = new GoldenNodeMap<>("map0");
        m0.put("1", new TestBean("String1", 1));
        TestBean tb = m0.get("1");
        System.out.println(tb.toString());
        Map<String, String> m = new GoldenNodeMap<>("map1");// new HashMap<String, String>();
        Object response;
        m.clear();
        response = m.isEmpty();
        Assert.assertEquals(true, response);
        response = m.put("key1", "val1");
        Assert.assertEquals(null, response);
        response = m.put("key2", "val2");
        Assert.assertEquals(null, response);
        response = m.put("key3", "val3");
        Assert.assertEquals(null, response);
        response = m.get("key1");
        Assert.assertEquals("val1", response);
        response = m.get("key2");
        Assert.assertEquals("val2", response);
        response = m.get("key3");
        Assert.assertEquals("val3", response);
        response = m.size();
        Assert.assertEquals(3, response);
        response = m.containsKey("key1");
        Assert.assertEquals(true, response);
        response = m.containsKey("key4");
        Assert.assertEquals(false, response);
        //response = m.keySet();
        //Assert.assertEquals(3, ((Set) response).size());
        //Assert.assertEquals("key1", ((Set) response).iterator().next());
        response = m.values();
        Assert.assertEquals(3, ((Collection) response).size());
        Assert.assertEquals("val1", ((Collection) response).iterator().next());
        response = m.entrySet();
        Assert.assertEquals("key1", ((Set<Entry<String, String>>) response).iterator().next().getKey());
        Assert.assertEquals("val1", ((Set<Entry<String, String>>) response).iterator().next().getValue());
        Assert.assertEquals(3, ((Set) response).size());
        response = m.containsValue("val1");
        Assert.assertEquals(true, response);
        response = m.containsValue("val4");
        Assert.assertEquals(false, response);
        response = m.isEmpty();
        Assert.assertEquals(false, response);
        response = m.remove("key1");
        Assert.assertEquals("val1", response);
        response = m.remove("key2");
        Assert.assertEquals("val2", response);
        response = m.remove("key3");
        Assert.assertEquals("val3", response);
        response = m.isEmpty();
        Assert.assertEquals(true, response);
    }
}
