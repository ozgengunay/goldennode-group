package com.goldennode.client;

import java.util.Map;

public interface HybridMap<K, V> extends Map<K, V>{

	
	void moveAllDataTo(Storage storage);
	
	void save(String name);
	
	void populate(String name);

	void reconfigure(Options options);
}
