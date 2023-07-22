package com.example.kampustesttask.util;

import java.util.HashMap;
import java.util.Map;

public class UtMap {

    public static Map<String, Object> toMap(Object... args) {
        Map<String, Object> map = new HashMap<>();
        if (args == null || args.length == 0) {
            return map;
        }

        if (args.length % 2 != 0) {
            throw new IllegalArgumentException("кол-во аргументов должно быть четным");
        }

        for (int i = 0; i < args.length; i += 2) {
            map.put(args[i].toString(), args[i + 1]);
        }

        return map;
    }

}
