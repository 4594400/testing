package com.progforce.scheduler.service;


import com.progforce.scheduler.dao.TaskDao;
import com.progforce.scheduler.dao.impl.TaskDaoJdbcImpl;
import com.progforce.scheduler.model.Task;

import java.time.LocalDate;
import java.util.List;

public class TaskService {
    private TaskDao taskDao = new TaskDaoJdbcImpl();

    public void checkExpiredTask(List<Task> task){
        for (Task t : task) {
            Task current = taskDao.getById(t.getId());
            if(current.getDeadline().toLocalDate().compareTo(LocalDate.now()) < 0 && !current.getStatus().equals("DONE")) {
                taskDao.updateStatus("EXPIRED", current.getId());
            }
        }
    }

    public void setStatusDone(int id){
        taskDao.updateStatus("DONE", id);
    }
}
