package com.goldennode.client.service;

import com.goldennode.client.GoldenNodeException;
import com.goldennode.client.Response;
import com.goldennode.client.RestClient;

public class RegistrationServiceImpl implements RegistrationService{

    @Override
    public Credentials registerTempAccount() throws GoldenNodeException {
        
        Response response = RestClient.call("/register/temp", "POST", "x");
        
        System.out.println(response.getBody());
        return null;
    }
}
