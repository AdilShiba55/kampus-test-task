package com.example.kampustesttask.quartz;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuartzTimeInfo implements Serializable {

    private int totalFireCount;
    private int remainingFireCount;
    private boolean runForever;
    private long repeatInterval;
    private long initialOffset;
    private String callbackData;

}
