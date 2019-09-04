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
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        Task task = (Task) jobDataMap.get("task");
        task.setStatus("Running...");
        try {
            thisThread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        taskRepository.save(task);
        // this is for checking interrupt() function
//        while(!isJobInterrupted){
//            System.out.println("do something.....");
//        }
        clientService.sendPost(task);
        task.setStatus("Created...");
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
