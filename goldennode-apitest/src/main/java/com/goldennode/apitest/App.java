package com.goldennode.apitest;

import java.util.HashMap;
import java.util.Map;

import com.goldennode.client.HybridMap;
import com.goldennode.client.HybridMapFactoryImpl;
import com.goldennode.client.Options;
import com.goldennode.client.StorageOption;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        Options options = new Options();
        options.setStorageOption(StorageOption.ONLY_CLOUD);
        options.setMaxLocalEntries(1000);
        HybridMap<Integer, String> map = HybridMapFactoryImpl.newHybridMap(options);

        
       // map.put(1,"1");
        
        
        
        System.out.println(map.size());
        System.out.println(map.cloudSize());
        Map tmpMap;

        for (int j = 1; j <= 1; j++) {
            tmpMap = new HashMap();
            for (int i = 5 * (j - 1); i < 5 * j; i++) {
                tmpMap.put(i + 1, (i + 1) + ". value");

            }
            map.putAll(tmpMap);
        }

        System.out.println("map.cloudSize()=" + map.cloudSize());
        System.out.println("map.size()" + map.size());

    }
}
