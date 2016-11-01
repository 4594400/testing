package com.progforce.scheduler.service;

import com.progforce.scheduler.model.Task;

import java.util.List;


public interface TaskService {
    void save(Task value);
    Task getById(int id);
    List<Task> getAll();
    void updateStatus(String status, int id);
    List<Task> getAllFinishedTasks();
    void checkExpiredTask(List<Task> task);
    void setStatusDone(int id);
}
