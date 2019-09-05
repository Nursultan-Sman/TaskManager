package com.bestprofi.quartz;

import com.bestprofi.models.Task;
import com.bestprofi.repositories.TaskRepository;
import com.bestprofi.services.ClientService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class Job extends QuartzJobBean implements InterruptableJob {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ClientService clientService;

    private volatile Thread  thisThread;

    private volatile boolean isJobInterrupted = false;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        thisThread = Thread.currentThread();
        // bug for tomorrow: when activated job finishes work --> status should be "Activated", not "Created"
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        Task task = (Task) jobDataMap.get("task");
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
        if(isJobInterrupted){
            task.setStatus("Interrupted");
        }else{
            task.setStatus("Created");
        }
        //clientService.sendPost(task);
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
