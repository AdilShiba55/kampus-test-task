package com.example.kampustesttask.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UtError {

    public static Map<String, List<String>> getMap(List<String> errors) {
        Map<String, List<String>> map = new HashMap<>();
        map.put("errors", errors);
        return map;
    }
    public static Map<String, List<String>> getMap(String error) {
        return getMap(Arrays.asList(error));
    }

}
