package com.bestprofi.services;

import com.bestprofi.models.Task;
import com.bestprofi.quartz.Job;
import com.bestprofi.repositories.TaskRepository;
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
    private TaskRepository taskRepository;
    @Autowired
    private Scheduler scheduler;

    public void activateTask(Task task) {

        JobKey jobKey = new JobKey("JobName" + task.getId() ,"group" + task.getId());

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("task", task);

        JobDetail jobDetail = JobBuilder.newJob(Job.class)
                .withIdentity(jobKey)
                .usingJobData(jobDataMap)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("TriggerName" + task.getId(), "group" + task.getId())
                .withSchedule(CronScheduleBuilder.cronSchedule(task.getCronTime())).build();

        try {
            if (!scheduler.isStarted()) {
                scheduler.start();
            }
            if (!scheduler.checkExists(jobKey)) {
                scheduler.scheduleJob(jobDetail, trigger);
                task.setStatus("Activated");
                taskRepository.save(task);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void deactivateTask(Task task)  {
        try {
            JobKey jobKey = findJobKey("JobName" + task.getId());
            List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
            List<TriggerKey> keys = new ArrayList<>();
            for (Trigger trigger : triggers) {
                keys.add(trigger.getKey());
            }
            scheduler.unscheduleJobs(keys);
            task.setStatus("Deactivated");
            taskRepository.save(task);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void startTask(Task task) {
        try {
            JobKey jobKey = new JobKey("OnceJobName" + task.getId() ,"group" + task.getId());

            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("task", task);

            JobDetail jobDetail = JobBuilder.newJob(Job.class)
                    .withIdentity(jobKey)
                    .usingJobData(jobDataMap)
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("OnceTriggerName" + task.getId(), "group" + task.getId())
                    .startNow().build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void interruptJob(Task task){
        try {
            JobKey jobKey = findJobKey("OnceJobName" + task.getId());
            /*if(jobKey == null){
                jobKey = findJobKey("JobName"+task.getId());
            }*/
            scheduler.interrupt(jobKey);
            task.setStatus("Interrupted");
            taskRepository.save(task);
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
