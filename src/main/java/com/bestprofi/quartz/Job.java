package com.bestprofi.quartz;

import com.bestprofi.models.Task;
import com.bestprofi.services.ClientService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class Job extends QuartzJobBean implements InterruptableJob {
    @Autowired
    private ClientService clientService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        Task task = (Task) jobDataMap.get("task");
        clientService.sendPost(task);
    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {

    }
}
