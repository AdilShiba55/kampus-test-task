package com.example.kampustesttask.quartz;

import lombok.SneakyThrows;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;

public class QuartzTriggerListener implements org.quartz.TriggerListener {

    private SchedulerService schedulerService;

    public QuartzTriggerListener(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @Override
    public String getName() {
        return QuartzTriggerListener.class.getSimpleName();
    }

    @SneakyThrows
    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        String timerId = trigger.getKey().getName();
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        QuartzTimeInfo quartzTimeInfo = (QuartzTimeInfo) jobDataMap.get(timerId);

        if(quartzTimeInfo.isRunForever()) {
            int remainingFireCount = quartzTimeInfo.getRemainingFireCount();
            if(remainingFireCount > 0) {
                quartzTimeInfo.setRemainingFireCount(remainingFireCount - 1);
            }
        }

        schedulerService.updateTimer(timerId, quartzTimeInfo);
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {

    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {

    }
}
