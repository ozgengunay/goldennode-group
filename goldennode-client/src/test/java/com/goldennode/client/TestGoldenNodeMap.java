package com.goldennode.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class TestGoldenNodeMap {
    ObjectMapper om = new ObjectMapper();

    public Object extractObject(String value) throws IOException, ClassNotFoundException {
        JsonNode node = om.readTree(value);
        String className = node.get("className").asText();
        JsonNode objectNode = node.get("object");
        Class<?> c = Class.forName(className);
        return om.treeToValue(objectNode, c);
    }

    public String encapObject(Object value) throws JsonProcessingException {
        JsonNode newNode = om.createObjectNode();
        ((ObjectNode) newNode).put("className", value.getClass().getName());
        ((ObjectNode) newNode).set("object", om.valueToTree(value));
        return om.writeValueAsString(newNode);
    }

    @Test
    public void test1() {
        Object response;
        Map<String, TestBean> m0 = new GoldenNodeMap<>("map0");
        m0.clear();
        response = m0.isEmpty();
        Assert.assertEquals(true, response);
        TestBean tb1 = new TestBean("String1", 1);
        TestBean tb11 = new TestBean("String11", 11);
        TestBean tb2 = new TestBean("String2", 2);
        TestBean tb3 = new TestBean("~!@#$%^&*()_+{}:\"|<>?>[];'\\,./", 3);
        response = m0.put("key0", null);
        Assert.assertEquals(null, response);
        response = m0.put("key1", tb1);
        Assert.assertEquals(null, response);
        response = m0.put("key1", tb11);
        Assert.assertEquals(tb1, response);
        response = m0.put("key2", tb2);
        Assert.assertEquals(null, response);
        response = m0.put("key3", tb3);
        Assert.assertEquals(null, response);
        response = m0.get("key0");
        Assert.assertEquals(null, response);
        response = m0.get("key1");
        Assert.assertEquals(tb11, response);
        response = m0.get("key2");
        Assert.assertEquals(tb2, response);
        response = m0.get("key3");
        Assert.assertEquals(tb3, response);
        response = m0.size();
        Assert.assertEquals(4, response);
        response = m0.containsKey("key0");
        Assert.assertEquals(true, response);
        response = m0.containsKey("key1");
        Assert.assertEquals(true, response);
        response = m0.containsKey("key4");
        Assert.assertEquals(false, response);
        response = m0.keySet();
        Collection<String> expectedKey = new HashSet<>();
        expectedKey.add("key0");
        expectedKey.add("key1");
        expectedKey.add("key2");
        expectedKey.add("key3");
        Assert.assertTrue(contentsSame(expectedKey, (Set) response));
        response = m0.values();
        Assert.assertEquals(4, ((Collection) response).size());
        Collection<TestBean> expectedValue = new HashSet<>();
        expectedValue = new ArrayList<>();
        expectedValue.add(null);
        expectedValue.add(tb11);
        expectedValue.add(tb2);
        expectedValue.add(tb3);
        Assert.assertTrue(contentsSame(expectedValue, (List) response));
        response = m0.entrySet();
        Assert.assertEquals("key1", ((Set<Entry<String, String>>) response).iterator().next().getKey());
        Assert.assertEquals(tb11, ((Set<Entry<String, String>>) response).iterator().next().getValue());
        Assert.assertEquals(4, ((Set) response).size());
        response = m0.containsValue(tb11);
        Assert.assertEquals(true, response);
        response = m0.containsValue(tb2);
        Assert.assertEquals(true, response);
        response = m0.containsValue(new TestBean("N/A", -1));
        Assert.assertEquals(false, response);
        response = m0.containsValue(new TestBean("~!@#$%^&*()_+{}:\"|<>?>[];'\\,./", 3));
        Assert.assertEquals(true, response);
        response = m0.isEmpty();
        Assert.assertEquals(false, response);
        response = m0.remove("key0");
        Assert.assertEquals(null, response);
        response = m0.remove("key1");
        Assert.assertEquals(tb11, response);
        response = m0.remove("key2");
        Assert.assertEquals(tb2, response);
        response = m0.remove("key3");
        Assert.assertEquals(tb3, response);
        response = m0.isEmpty();
        Assert.assertEquals(true, response);
        Map<String, String> m1 = new GoldenNodeMap<>("map1");
        m1.clear();
        response = m1.isEmpty();
        Assert.assertEquals(true, response);
        response = m1.put("key1", "val1");
        Assert.assertEquals(null, response);
        response = m1.put("key2", "\"val2\"");
        Assert.assertEquals(null, response);
        response = m1.put("key3", "val3");
        Assert.assertEquals(null, response);
        response = m1.get("key1");
        Assert.assertEquals("val1", response);
        response = m1.get("key2");
        Assert.assertEquals("\"val2\"", response);
        response = m1.get("key3");
        Assert.assertEquals("val3", response);
        response = m1.size();
        Assert.assertEquals(3, response);
        response = m1.containsKey("key1");
        Assert.assertEquals(true, response);
        response = m1.containsKey("key4");
        Assert.assertEquals(false, response);
        response = m1.keySet();
        Collection<String> expected = new HashSet<>();
        expected.add("key1");
        expected.add("key2");
        expected.add("key3");
        Assert.assertTrue(contentsSame(expected, (Set) response));
        response = m1.values();
        Assert.assertEquals(3, ((Collection) response).size());
        expected = new ArrayList<>();
        expected.add("val1");
        expected.add("\"val2\"");
        expected.add("val3");
        Assert.assertTrue(contentsSame(expected, (List) response));
        response = m1.entrySet();
        Assert.assertEquals("key1", ((Set<Entry<String, String>>) response).iterator().next().getKey());
        Assert.assertEquals("val1", ((Set<Entry<String, String>>) response).iterator().next().getValue());
        Assert.assertEquals(3, ((Set) response).size());
        response = m1.containsValue("val1");
        Assert.assertEquals(true, response);
        response = m1.containsValue("val4");
        Assert.assertEquals(false, response);
        response = m1.isEmpty();
        Assert.assertEquals(false, response);
        response = m1.remove("key1");
        Assert.assertEquals("val1", response);
        response = m1.remove("key2");
        Assert.assertEquals("\"val2\"", response);
        response = m1.remove("key3");
        Assert.assertEquals("val3", response);
        response = m1.isEmpty();
        Assert.assertEquals(true, response);
        Map<String, Integer> m2 = new GoldenNodeMap<>("map2");
        m2.clear();
        response = m2.isEmpty();
        Assert.assertEquals(true, response);
        response = m2.put("key1", 1);
        Assert.assertEquals(null, response);
        response = m2.get("key1");
        Assert.assertEquals(1, response);
        Map<String, Boolean> m3 = new GoldenNodeMap<>("map3");
        m3.clear();
        response = m3.isEmpty();
        Assert.assertEquals(true, response);
        response = m3.put("key1", true);
        Assert.assertEquals(null, response);
        response = m3.get("key1");
        Assert.assertEquals(true, response);
        Map<String, Double> m4 = new GoldenNodeMap<>("map4");
        m4.clear();
        response = m4.isEmpty();
        Assert.assertEquals(true, response);
        response = m4.put("key1", 3.444554);
        Assert.assertEquals(null, response);
        response = m4.get("key1");
        Assert.assertEquals(3.444554, response);
        Map<String, Date> m5 = new GoldenNodeMap<>("map5");
        m5.clear();
        response = m5.isEmpty();
        Assert.assertEquals(true, response);
        long time = new Date().getTime();
        response = m5.put("key1", new Date());
        Assert.assertEquals(null, response);
        response = m5.get("key1");
        Assert.assertEquals(time, ((Date) response).getTime());
    }

    private boolean contentsSame(Collection expected, Collection actual) {
        if (expected.size() != actual.size())
            return false;
        for (Object exp : expected) {
            if (!actual.contains(exp))
                return false;
        }
        return true;
    }
}
