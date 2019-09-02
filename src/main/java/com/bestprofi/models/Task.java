package com.bestprofi.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;
    @NotBlank(message = "URL is required")
    private String url;
    @NotBlank(message = "Parameter is required")
    private String parameter;
    @NotBlank(message = "Method is required")
    private String method;
    @NotBlank(message = "Cron Time is required")
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
