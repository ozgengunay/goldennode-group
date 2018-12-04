package com.goldennode.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
        response = m.put("key2", "\"val2\"");
        Assert.assertEquals(null, response);
        response = m.put("key3", "val3");
        Assert.assertEquals(null, response);
        response = m.get("key1");
        Assert.assertEquals("val1", response);
        response = m.get("key2");
        Assert.assertEquals("\"val2\"", response);
        response = m.get("key3");
        Assert.assertEquals("val3", response);
        response = m.size();
        Assert.assertEquals(3, response);
        response = m.containsKey("key1");
        Assert.assertEquals(true, response);
        response = m.containsKey("key4");
        Assert.assertEquals(false, response);
        response = m.keySet();
        Collection<String> expected = new HashSet<>();
        expected.add("key1");
        expected.add("key2");
        expected.add("key3");
        Assert.assertTrue(contentsSame(expected, (Set) response));
        response = m.values();
        Assert.assertEquals(3, ((Collection) response).size());
        expected = new ArrayList<>();
        expected.add("val1");
        expected.add("\"val2\"");
        expected.add("val3");
        Assert.assertTrue(contentsSame(expected, (List) response));
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
        Assert.assertEquals("\"val2\"", response);
        response = m.remove("key3");
        Assert.assertEquals("val3", response);
        response = m.isEmpty();
        Assert.assertEquals(true, response);
    }

    private boolean contentsSame(Collection<String> expected, Collection<String> actual) {
        if (expected.size() != actual.size())
            return false;
        for (String exp : expected) {
            if (!actual.contains(exp))
                return false;
        }
        return true;
    }
}
