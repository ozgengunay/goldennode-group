package com.goldennode.client;

public class HybridMapFactoryImpl {

    public static <K, V> HybridMap<K, V> newHybridMap(Options options) {
        return new HybridMapImpl<>(options);
    }
    
    public static <K, V> HybridMap<K, V> newHybridMap() {
        return new HybridMapImpl<>(new Options());
    }
}
