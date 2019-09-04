package com.bestprofi.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Date startDate;
    private Date endDate;
    private String responseCode;
    private String responseMessage;
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public Log(){

    }

    public Log(Date startDate, Date endDate, String responseCode, String responseMessage, Task task) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.task = task;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
