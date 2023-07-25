package com.example.kampustesttask.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SchedulerService implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    private Scheduler scheduler;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public SchedulerService() {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        QuartzJobFactory jobFactory = new QuartzJobFactory(applicationContext);
        try {
            scheduler = schedulerFactory.getScheduler();
            scheduler.setJobFactory(jobFactory);
            scheduler.start();
            scheduler.getListenerManager().addTriggerListener(new QuartzTriggerListener(this));
        } catch (SchedulerException exception) {
            exception.printStackTrace();
        }
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
