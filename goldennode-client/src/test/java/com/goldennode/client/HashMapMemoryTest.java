package com.goldennode.client;

import java.util.HashMap;
import java.util.Random;
import org.junit.Test;

public class HashMapMemoryTest {
    HashMap<String, String> map = new HashMap<>();

    @Test
    public void test1() {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                int i = 0;
                while (i++ < 10000000) {
                    String str = new Integer(Math.abs(random.nextInt())).toString();
                    str = str.length() > 7 ? str.substring(0, 7) : str;
                    map.put(str, str + "_str");
                    // System.out.println(str);
                }
            }
        });
        Thread th2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.gc();
                    int memPerc = (int) (((double) (Runtime.getRuntime().maxMemory() - (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()))
                            / Runtime.getRuntime().maxMemory()) * 100);
                    System.out.println("Map size=" + map.size());
                    System.out.println("mem perc=" + memPerc + "%");
                    System.out.println("Per item:" + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / map.size());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        th.start();
        th2.start();
        try {
            th2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
