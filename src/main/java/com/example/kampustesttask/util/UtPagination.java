package com.example.kampustesttask.util;

public class UtPagination {

    public static Integer getPagination(Integer pageNum, Integer pageSize) {
        return (pageNum - 1) * pageSize;
    }

}
