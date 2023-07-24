package com.example.kampustesttask.quartz;

import com.example.kampustesttask.service.ReminderService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReminderEmailJob implements Job {

    @Autowired
    private ReminderService reminderService;

    @Override
    public void execute(JobExecutionContext context) {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        QuartzTimeInfo quartzTimeInfo = (QuartzTimeInfo) jobDataMap.get(ReminderEmailJob.class.getSimpleName());
        reminderService.sendRemindNotifications();
    }
}
