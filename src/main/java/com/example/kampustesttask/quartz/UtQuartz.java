package com.example.kampustesttask.quartz;

import org.quartz.*;

import java.util.Date;

public class UtQuartz {

    public static JobDetail getJobDetail(Class<? extends Job> clazz, QuartzTimeInfo quartzTimeInfo) {

        JobDataMap data = new JobDataMap();
        data.put(clazz.getSimpleName(), quartzTimeInfo);

        return JobBuilder
                .newJob(clazz)
                .withIdentity(clazz.getSimpleName())
                .setJobData(data)
                .build();
    }

    public static Trigger getTrigger(Class<? extends Job> clazz, QuartzTimeInfo quartzTimeInfo) {
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder
                .simpleSchedule()
                .withIntervalInMilliseconds(quartzTimeInfo.getRepeatInterval());

        if(quartzTimeInfo.isRunForever()) {
            simpleScheduleBuilder = simpleScheduleBuilder.repeatForever();
        } else {
            simpleScheduleBuilder = simpleScheduleBuilder.withRepeatCount(quartzTimeInfo.getTotalFireCount() - 1);
        }

        return TriggerBuilder
                .newTrigger()
                .withIdentity(clazz.getSimpleName())
                .withSchedule(simpleScheduleBuilder)
                .startAt(new Date(System.currentTimeMillis() + quartzTimeInfo.getInitialOffset()))
                .build();


    }

}
