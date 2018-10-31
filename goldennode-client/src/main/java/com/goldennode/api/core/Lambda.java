package com.goldennode.api.core;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Lambda {

    public static void main(String[] args) {

        //String prefix = "MR.";
        //System.out.println(process("Hi world", prefix::concat));
        //System.out.println(process2("Hi world", 5, String::substring));

        String[] names = { "Mr Sanjay", "Ms Trupti", "Dr John" };

        Lambda l=new Lambda();
        Arrays.sort(names, Comparator.comparing(l::realName).reversed());
        System.out.println(Arrays.toString(names));

    }

    public String realName(String name) {
        return name.split(" ")[1];
    }

    private static String process(String string, Function<String, String> processor) {
        return processor.apply(string);
    }

    private static String process2(String string, int i, BiFunction<String, Integer, String> processor) {
        return processor.apply(string, i);
    }

}
