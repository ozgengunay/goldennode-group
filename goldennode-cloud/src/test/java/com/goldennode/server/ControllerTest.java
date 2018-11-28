package com.goldennode.server;

import java.util.UUID;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import com.goldennode.commons.util.GoldenNodeException;
import com.goldennode.commons.util.RestClient;
import com.goldennode.testutils.GoldenNodeJunitRunner;

public class ControllerTest extends GoldenNodeJunitRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerTest.class);

   @Test
    public void _01_put() throws GoldenNodeException {
        RestClient.call("/goldennode/map/id/1/put/key/1", HttpMethod.POST, UUID.randomUUID().toString());
    }

    //@Test
    public void _02_put() throws GoldenNodeException {
        RestClient.call("/goldennode/map/id/1/put/key/2", HttpMethod.POST, UUID.randomUUID().toString());
    }

    @Test
    public void _03_get() throws GoldenNodeException {
        RestClient.call("/goldennode/map/id/1/get/key/1", HttpMethod.GET);
    }

    //@Test
    public void _04_get() throws GoldenNodeException {
        RestClient.call("/goldennode/map/id/1/keySet", HttpMethod.GET);
    }
}
