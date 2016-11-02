package com.progforce.scheduler.service;

import com.progforce.scheduler.model.Task;

import java.util.List;


public interface TaskService {
    void save(Task value);
    List<Task> getAll();
    List<Task> getAllFinishedTasks();
    void checkExpiredTask(List<Task> task);
    void setStatusDone(int id);
}
