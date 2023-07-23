package com.example.kampustesttask.config.quartz;

import com.example.kampustesttask.service.SchedulerService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Configuration
public class TaskConfig {

    private static final int ONE_SECOND = 1000;
    private static final int ONE_MINUTE = ONE_SECOND * 1000;
    private static final int ONE_HOUR = 60 * ONE_MINUTE;
    private static final int ONE_DAY = 24 * ONE_HOUR;

    @Autowired
    private SchedulerService schedulerService;

    @PostConstruct
    private void init() throws SchedulerException {
//        runReminderEmailJob();
    }

    private void runReminderEmailJob() throws SchedulerException {
        QuartzTimeInfo quartzTimeInfo = new QuartzTimeInfo();
        quartzTimeInfo.setRepeatInterval(ONE_MINUTE);
        schedulerService.schedule(ReminderEmailJob.class, quartzTimeInfo);
    }

}
