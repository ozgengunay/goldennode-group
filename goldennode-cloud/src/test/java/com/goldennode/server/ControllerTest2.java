package com.goldennode.server;

import java.util.UUID;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import com.goldennode.client.Response;
import com.goldennode.client.RestClient;
import com.goldennode.commons.util.GoldenNodeException;
import com.goldennode.testutils.GoldenNodeJunitRunner;

public class ControllerTest2 extends GoldenNodeJunitRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerTest2.class);

    @Test
    public void _01_put() throws GoldenNodeException {
        try {
            Response entity = RestClient.call("/goldennode/map/id/1/put/key/1", HttpMethod.POST.toString(), UUID.randomUUID().toString());
            System.out.println(entity.getResponseCode() + " " + entity.getResponse());
        } catch (com.goldennode.client.GoldenNodeException e) {
            e.printStackTrace();
        }
    }

     @Test
    public void _02_put() throws GoldenNodeException {
        try {
            Response entity = RestClient.call("/goldennode/map/id/1/put/key/2", HttpMethod.POST.toString(), UUID.randomUUID().toString());
            System.out.println(entity.getResponseCode() + " " + entity.getResponse());
        } catch (com.goldennode.client.GoldenNodeException e) {
            e.printStackTrace();
        }
    }

     @Test
    public void _03_get() throws GoldenNodeException {
        try {
            Response entity = RestClient.call("/goldennode/map/id/1/get/key/1", HttpMethod.GET.toString());
            System.out.println(entity.getResponseCode() + " " + entity.getResponse());
        } catch (com.goldennode.client.GoldenNodeException e) {
            e.printStackTrace();
        }
    }

     @Test
    public void _04_get() throws GoldenNodeException {
        try {
            Response entity = RestClient.call("/goldennode/map/id/1/keySet", HttpMethod.GET.toString());
            System.out.println(entity.getResponseCode() + " " + entity.getResponse());
        } catch (com.goldennode.client.GoldenNodeException e) {
            e.printStackTrace();
        }
    }
}
