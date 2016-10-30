package com.progforce.scheduler.dao;


import com.progforce.scheduler.model.Task;

import java.util.List;

public interface TaskDao extends DAO<Task>{
    void updateStatus(String status, int id);
    List<Task> getAllFinishedTasks();

}
