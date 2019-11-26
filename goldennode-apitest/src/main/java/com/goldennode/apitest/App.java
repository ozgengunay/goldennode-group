package com.goldennode.apitest;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
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
        options.setStorageOption(StorageOption.MIXTURE);
        options.setFreeToTotalMemoryRatio(.5);
        //options.setMaxLocalEntries(9000);
        HybridMap<Integer, String> map = HybridMapFactoryImpl.newHybridMap(options);
        Map tmpMap;
        for (int j = 1; j <= 1000; j++) {
            tmpMap = new HashMap();
            for (int i = 10000 * (j - 1); i < 100000 * j; i++) {
                tmpMap.put(i + 1, (i + 1) + ". value");
            }
            map.putAll(tmpMap);
            System.out.println("putting j=" +j);
            System.out.println("map.cloudSize()=" + map.cloudSize());
            System.out.println("map.size()=" + map.size());
        }
        
        
    }
}
