package com.goldennode.apitest;

import java.io.IOException;
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
        
        HybridMap<Integer, String> map = HybridMapFactoryImpl.newHybridMap();
        Map tmpMap;
        for (int j = 1; j <= 10; j++) {
            tmpMap = new HashMap();
            for (int i = 5000 * (j - 1); i < 5000 * j; i++) {
                tmpMap.put(i + 1, (i + 1) + ". value");
            }
            map.putAll(tmpMap);
            System.out.println("putting j=" +j);
            System.out.println("map.cloudSize()=" + map.cloudSize());
            System.out.println("map.size()=" + map.size());
            System.out.println("free=" + Options.getSystemFree());
        }
        
        try {
          System.out.println(map.dump());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
    }
    public static void main_() {
        Options options = new Options();
        options.setStorageOption(StorageOption.MIXTURE);
        options.setFreeToTotalMemoryRatio(.80);
        //options.setMaxLocalEntries(9000);
        HybridMap<Integer, String> map = HybridMapFactoryImpl.newHybridMap(options);
        Map tmpMap;
        for (int j = 1; j <= 40000; j++) {
            tmpMap = new HashMap();
            for (int i = 5000 * (j - 1); i < 5000 * j; i++) {
                tmpMap.put(i + 1, (i + 1) + ". value");
            }
            map.putAll(tmpMap);
            System.out.println("putting j=" +j);
            System.out.println("map.cloudSize()=" + map.cloudSize());
            System.out.println("map.size()=" + map.size());
            System.out.println("free=" + Options.getSystemFree());
        }
        
        
    }
}
