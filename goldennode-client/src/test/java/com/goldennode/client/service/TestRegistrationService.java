package com.goldennode.client.service;

import org.junit.Test;
import com.goldennode.client.GoldenNodeException;

public class TestRegistrationService {
    @Test
    public void registerTempAccount() throws GoldenNodeException {
        new RegistrationServiceImpl().registerTempAccount();
        
        
    }
}
