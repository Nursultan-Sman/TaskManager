package com.bestprofi.quartz;

import com.bestprofi.models.Task;
import com.bestprofi.repositories.TaskRepository;
import com.bestprofi.services.ClientService;
import com.bestprofi.services.SchedulerService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class Job extends QuartzJobBean implements InterruptableJob {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private SchedulerService schedulerService;
    @Autowired
    private Scheduler scheduler;

    private volatile Thread  thisThread;

    private volatile boolean isJobInterrupted = false;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        thisThread = Thread.currentThread();
        // bug for tomorrow: when activated job finishes work --> status should be "Activated", not "Created"
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        Task task = (Task) jobDataMap.get("task");
        JobKey jobKey = schedulerService.findJobKey("JobName"+task.getId());

        //3String prevStatus = task.getStatus();
        task.setStatus("Running");
        taskRepository.save(task);
        // this is for checking interrupt() function
//        while(!isJobInterrupted){
//            System.out.println("do something.....");
//        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Do something");

        //clientService.sendPost(task);

        if(isJobInterrupted) {
            task.setStatus("Interrupted");
        }else {
            try {
                if(scheduler.checkExists(jobKey)){
                    task.setStatus("Activated");
                }else{
                    task.setStatus("Created");
                }
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
        taskRepository.save(task);
    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        isJobInterrupted = true;
        if(thisThread != null){
            thisThread.interrupt();
        }
    }
}
