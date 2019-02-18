package com.goldennode.client.service;

import com.goldennode.client.GoldenNodeException;

public interface RegistrationService {
    public Credentials registerTempAccount() throws GoldenNodeException;
}
