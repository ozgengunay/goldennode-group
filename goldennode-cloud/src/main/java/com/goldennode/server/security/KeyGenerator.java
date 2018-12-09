package com.goldennode.server.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class KeyGenerator {
    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keypair = keyPairGenerator.genKeyPair();
            return keypair;
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
    
    
    public static void main(String arg[]) {
        System.out.println("--"+KeyGenerator.generateKeyPair().getPublic().getEncoded()+"--");
        System.out.println("--"+KeyGenerator.generateKeyPair().getPrivate().getEncoded()+"--");
    }
}
