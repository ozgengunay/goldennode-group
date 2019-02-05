package com.goldennode.client.service;

import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goldennode.client.GoldenNodeException;
import com.goldennode.client.Response;
import com.goldennode.client.RestClient;

public class RegistrationServiceImpl implements RegistrationService {
    @Override
    public Credentials registerTempAccount() throws GoldenNodeException {
        Response response = RestClient.call("/register/temp", "POST", "x", false);
        ObjectMapper om = new ObjectMapper();
        JsonNode node;
        try {
            node = om.readTree(response.getBody());
            return om.treeToValue(node, Credentials.class);
        } catch (IOException e) {
            throw new GoldenNodeException(e);
        }
    }
}
