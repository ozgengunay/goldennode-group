package com.goldennode.client;

import java.io.IOException;
import java.util.Map;

public interface HybridMap<K, V> extends Map<K, V>{

	
	//void moveAllDataTo(Storage storage);
	
	//void save(String name);
	
	//void populate(String name);
	
	String dump() throws IOException;
	
	String dump(String fileName) throws IOException;

	//void reconfigure(Options options);

    int cloudSize();

    boolean cloudIsEmpty();
	

	
}
