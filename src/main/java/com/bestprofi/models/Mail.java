package com.bestprofi.models;

public class Mail {
    private String[] to;
    private String subject;
    private String body;
    private String cc;

    public Mail() {
    }
    public Mail(String subject, String body, String cc) {
        this.subject = subject;
        this.body = body;
        this.cc = cc;
    }

    public Mail(String[] to, String subject, String body, String cc) {
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.cc = cc;
    }

    public String[] getTo() {
        return to;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }
}
