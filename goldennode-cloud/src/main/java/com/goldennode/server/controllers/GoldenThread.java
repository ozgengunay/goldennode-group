package com.goldennode.server.controllers;

import java.lang.reflect.Field;

public class GoldenThread extends Thread {
    public GoldenThread(long id, Runnable r) {
        super(r);
        try {
            Field field = getClass().getSuperclass().getDeclaredField("tid");
            field.setAccessible(true);
            field.setLong(this, id);
            setName("Thread-" + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
