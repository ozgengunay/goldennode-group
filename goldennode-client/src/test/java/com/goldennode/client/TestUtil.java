package com.goldennode.client;

import java.util.Collection;

public class TestUtil {
    public static boolean contentsSame(Collection<?> expected, Collection<?> actual) {
        if (expected.size() != actual.size())
            return false;
        for (Object exp : expected) {
            if (!actual.contains(exp))
                return false;
        }
        return true;
    }
}
