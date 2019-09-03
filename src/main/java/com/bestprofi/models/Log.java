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
    private String response;
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public Log(){

    }

    public Log(Date startDate, Date endDate, String response, Task task) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.response = response;
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

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
