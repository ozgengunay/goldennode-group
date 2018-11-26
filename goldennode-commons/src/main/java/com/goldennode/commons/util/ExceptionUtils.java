package com.goldennode.commons.util;

public class ExceptionUtils {
    public static boolean hasCause(Throwable t, Class<? extends Throwable> cause) {
        while (t != null) {
            if (cause.isInstance(t)) {
                return true;
            }
            t = t.getCause();
        }
        return false;
    }

    public static void throwCauseIfThereIs(Throwable t, Class<? extends Throwable> cause) throws Throwable {
        while (t != null) {
            if (cause.isInstance(t)) {
                throw t;
            }
            t = t.getCause();
        }
    }

    public static String getStackTrace(Throwable caught) {
        String st = caught.toString();
        for (StackTraceElement ste : caught.getStackTrace()) {
            st += "\n" + ste.toString();
        }
        Throwable tmp = caught.getCause();
        while (tmp != null) {
            st += "\n" + "Caused by";
            for (StackTraceElement ste : tmp.getStackTrace()) {
                st += "\n" + ste.toString();
            }
            tmp = tmp.getCause();
        }
        return st;
    }
}
