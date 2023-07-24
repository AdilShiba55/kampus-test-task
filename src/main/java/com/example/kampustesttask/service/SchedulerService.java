package com.example.kampustesttask.service;

import com.example.kampustesttask.quartz.QuartzTimeInfo;
import com.example.kampustesttask.quartz.QuartzTriggerListener;
import com.example.kampustesttask.quartz.ReminderEmailJob;
import com.example.kampustesttask.quartz.UtQuartz;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        scheduler.getListenerManager().addTriggerListener(new QuartzTriggerListener(this));
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

    public List<QuartzTimeInfo> getActiveTimers() throws SchedulerException {
        return scheduler.getJobKeys(GroupMatcher.anyGroup())
                .stream()
                .map(jobKey -> getActiveTimer(jobKey.getName()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public QuartzTimeInfo getActiveTimer(String timerId) {
        try {
            JobDetail jobDetail = scheduler.getJobDetail(new JobKey(timerId));
            return (QuartzTimeInfo) jobDetail.getJobDataMap().get(timerId);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateTimer(String timerId, QuartzTimeInfo quartzTimeInfo) throws SchedulerException {
        JobDetail jobDetail = scheduler.getJobDetail(new JobKey(timerId));
        if(jobDetail != null) {
            jobDetail.getJobDataMap().put(timerId, quartzTimeInfo);
        }
    }

    public boolean deleteTimer(String timerId) throws SchedulerException {
        return scheduler.deleteJob(new JobKey(timerId));
    }

}
