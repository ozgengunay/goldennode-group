package com.goldennode.client;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import org.junit.Ignore;
import org.junit.Test;

public class HashMapMemoryTest {
    HashMap<String, String> map = new HashMap<>();

    @Test
    @Ignore
    public void test1() {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                int i = 0;
                while (i++ < 10000000) {
                    String str = new Integer(Math.abs(random.nextInt())).toString();
                    str = str.length() > 8 ? str.substring(0, 8) : str;
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
                    int memPerc = (int) (((double)(Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()))
                            / Runtime.getRuntime().maxMemory() * 100);
                    System.out.println("Runtime.getRuntime().maxMemory()   =" + formatter.format(Runtime.getRuntime().maxMemory()));
                    System.out.println("Runtime.getRuntime().totalMemory() =" + formatter.format(Runtime.getRuntime().totalMemory()));
                    System.out.println("Map size=" + formatter.format(map.size()));
                    System.out.println("Mem perc=" + formatter.format(memPerc) + "%");
                    System.out.println("Per item:" + formatter.format((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / map.size()));
                    System.out.println("Items:" + formatter.format(map.size()));
                    System.out.println("-------------------");
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
