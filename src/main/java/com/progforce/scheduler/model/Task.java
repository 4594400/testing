package com.progforce.scheduler.model;


import java.sql.Date;

public class Task {
    private Integer id;
    private String name;
    private Date deadline;
    private Priority priority;
    private String status;

    public Task() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Task(String name, Date deadline, Priority priority) {
        this.name = name;
        this.deadline = deadline;
        this.priority = priority;
    }


}
