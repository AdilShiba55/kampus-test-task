package com.example.kampustesttask.config.quartz;

import lombok.Data;

@Data
public class QuartzTimeInfo {

    private int totalFireCount;
    private boolean runForever;
    private long repeatInterval;
    private long initialOffset;
    private String callbackData;

}
