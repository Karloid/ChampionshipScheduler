package com.krld;

import java.util.List;

public class Util {
    public static void println(String msg) {
        System.out.println(msg);
    }

    public static void print(String s) {
        System.out.print(s);
    }

    public static <T> T getRandom(List<T> list) {
        return list.get((int) (list.size() * Math.random()));
    }
}
