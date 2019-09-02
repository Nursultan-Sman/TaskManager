package com.bestprofi.services;

import com.bestprofi.models.Task;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SchedulerService {

    @Autowired
    private Scheduler scheduler;

    public void activateTask(Task task) {
        JobKey jobKey = new JobKey("MailJobName" + task.getId() ,"group" + task.getId());

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("task", task);

        JobDetail jobDetail = JobBuilder.newJob(Job.class)
                .withIdentity(jobKey)
                .usingJobData(jobDataMap)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("MailTriggerName" + task.getId(), "group" + task.getId())
                .withSchedule(CronScheduleBuilder.cronSchedule(task.getCronTime())).build();

        try {
            if (!scheduler.isStarted())
                scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void deActivateTask(Task task)  {
        try {
            JobKey jobKey = findJobKey("MailJobName" + task.getId());
            List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
            List<TriggerKey> keys = new ArrayList<>();
            for (Trigger trigger : triggers) {
                keys.add(trigger.getKey());
            }
            scheduler.unscheduleJobs(keys);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public JobKey findJobKey(String jobName) throws SchedulerException {
        // Check running jobs first
        for (JobExecutionContext runningJob : scheduler.getCurrentlyExecutingJobs()) {
            if (Objects.equals(jobName, runningJob.getJobDetail().getKey().getName())) {
                return runningJob.getJobDetail().getKey();
            }
        }
        // Check all jobs if not found
        for (String groupName : scheduler.getJobGroupNames()) {
            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                if (Objects.equals(jobName, jobKey.getName())) {
                    return jobKey;
                }
            }
        }
        return null;
    }
}
