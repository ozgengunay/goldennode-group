package com.goldennode.client;

public class Options {

    public static final int MegaBytes = 1024*1024;

    private Integer maxLocalEntries;
    private StorageOption storageOption = StorageOption.MIXTURE;
    private double freeToTotalMemoryRatio = 0.1;
    private long minLocalFreeMemory = (long) (getMaxMemory() * freeToTotalMemoryRatio);

    public static long getSystemFree() {
        long freeMemory = Runtime.getRuntime().freeMemory() / Options.MegaBytes;
        long totalMemory = Runtime.getRuntime().totalMemory() / Options.MegaBytes;
        long maxMemory = Runtime.getRuntime().maxMemory() / Options.MegaBytes;
        long usedMemory = (totalMemory - freeMemory);
        long systemFree = maxMemory - usedMemory;
        return systemFree;
    }
    
    static long getMaxMemory() {
        return Runtime.getRuntime().maxMemory() / MegaBytes;
    }

    public double getFreeToTotalMemoryRatio() {
        return freeToTotalMemoryRatio;
    }

    public void setFreeToTotalMemoryRatio(double freeToTotalMemoryRatio) {
        this.freeToTotalMemoryRatio = freeToTotalMemoryRatio;
        this.minLocalFreeMemory = (long) (getMaxMemory() * freeToTotalMemoryRatio);
        int a=0;
    }

    public Integer getMaxLocalEntries() {
        return maxLocalEntries;
    }

    public void setMaxLocalEntries(int maxLocalEntries) {
        this.maxLocalEntries = maxLocalEntries;
    }

    public long getMinLocalFreeMemory() {
        return minLocalFreeMemory;
    }

    public StorageOption getStorageOption() {
        return storageOption;
    }

    public void setStorageOption(StorageOption storageOption) {
        this.storageOption = storageOption;
    }

}
