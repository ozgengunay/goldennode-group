package com.goldennode.client;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;

public class TestHybridMap {

	private HybridMap<String, String> hybridMap = HybridMapFactoryImpl.newHybridMap(new Options(StorageOption.ONLY_CLOUD));
	private int batchSize = 10 * 1000;
	private int mapSize = 100 * 1000 * 1000;

	@Test
	public void test1() {
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		formatter.setDecimalFormatSymbols(symbols);
		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {

				int i = 0;
				Map<String, String> tmpMap = new HashMap<>();
				while (i++ < mapSize) {
					if (i == 0 || i % batchSize > 0) {
						String str = UUID.randomUUID().toString();
						tmpMap.put(str, ".");
					} else {
						hybridMap.putAll(tmpMap);
						tmpMap.clear();
					}
				}
			}
		});
		Thread th2 = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					System.gc();
					System.out.println("Runtime.getRuntime().maxMemory()   ="
							+ formatter.format(Runtime.getRuntime().maxMemory() / Options.MegaBytes));
					System.out.println("SystemFree   =" + formatter.format(Options.getSystemFree()));

					System.out.println("Map size=" + formatter.format(hybridMap.size()));
					System.out.println("Map cloud size=" + formatter.format(hybridMap.cloudSize()));

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
