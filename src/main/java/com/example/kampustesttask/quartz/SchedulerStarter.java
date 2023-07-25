package com.example.kampustesttask.quartz;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SchedulerStarter {

    private static final int ONE_SECOND = 1000;
    private static final int FIVE_SECONDS = ONE_SECOND * 5;
    private static final int ONE_MINUTE = ONE_SECOND * 1000;
    private static final int ONE_HOUR = 60 * ONE_MINUTE;
    private static final int ONE_DAY = 24 * ONE_HOUR;

    @Autowired
    private SchedulerService schedulerService;

    @PostConstruct
    private void init() throws SchedulerException {
        runReminderEmailJob();
    }

    private void runReminderEmailJob() throws SchedulerException {
        QuartzTimeInfo quartzTimeInfo = new QuartzTimeInfo();
        quartzTimeInfo.setRepeatInterval(FIVE_SECONDS);
        quartzTimeInfo.setRunForever(true);
        schedulerService.schedule(ReminderEmailJob.class, quartzTimeInfo);
    }

}
