package com.bestprofi.models;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String description;
    private String url;
    private String parameter;
    private String method;
    private String cronTime;
    private String status;
    @OneToMany(targetEntity = Logs.class, mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Logs> logs;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCronTime() {
        return cronTime;
    }

    public void setCronTime(String cronTime) {
        this.cronTime = cronTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Logs> getLogs() {
        return logs;
    }

    public void setLogs(Set<Logs> logs) {
        this.logs = logs;
    }
}
