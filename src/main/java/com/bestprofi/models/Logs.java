package com.bestprofi.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Logs {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Date eventDate;
    private String logLevel;
    private String logger;
    private String message;
    private String exception;
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

}
