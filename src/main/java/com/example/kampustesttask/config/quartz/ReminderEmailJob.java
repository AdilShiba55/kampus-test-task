package com.example.kampustesttask.config.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
public class ReminderEmailJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String point = "";
    }
}
