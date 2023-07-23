package com.example.kampustesttask.service;

import com.example.kampustesttask.config.quartz.QuartzTimeInfo;
import com.example.kampustesttask.config.quartz.UtQuartz;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class SchedulerService {

    private Scheduler scheduler;

    public SchedulerService() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
       this.scheduler = schedulerFactory.getScheduler();
    }

    @PostConstruct
    public void init() throws SchedulerException {
        scheduler.start();
    }

    @PreDestroy
    public void destroy() throws SchedulerException {
        scheduler.shutdown();
    }

    public void schedule(Class<? extends Job> jobClass, QuartzTimeInfo quartzTimeInfo) throws SchedulerException {
        JobDetail jobDetail = UtQuartz.getJobDetail(jobClass, quartzTimeInfo);
        Trigger trigger = UtQuartz.getTrigger(jobClass, quartzTimeInfo);
        scheduler.scheduleJob(jobDetail, trigger);
    }

}
