package com.progforce.scheduler.service.impl;


import com.progforce.scheduler.dao.TaskDao;
import com.progforce.scheduler.dao.impl.TaskDaoJdbcImpl;
import com.progforce.scheduler.model.Task;
import com.progforce.scheduler.service.TaskService;

import java.time.LocalDate;
import java.util.List;

public class TaskServiceImpl implements TaskService {
    private TaskDao taskDao = new TaskDaoJdbcImpl();

    @Override
    public void save(Task value) {
        taskDao.save(value);
    }

    @Override
    public Task getById(int id) {
        return taskDao.getById(id);
    }

    @Override
    public List<Task> getAll() {
        return taskDao.getAll();
    }

    @Override
    public void updateStatus(String status, int id) {
        taskDao.updateStatus(status, id);
    }

    @Override
    public List<Task> getAllFinishedTasks() {
        return taskDao.getAllFinishedTasks();
    }

    @Override
    public void checkExpiredTask(List<Task> task) {
        for (Task t : task) {
            Task current = taskDao.getById(t.getId());
            if (current.getDeadline().toLocalDate().compareTo(LocalDate.now()) < 0 && !current.getStatus().equals("DONE")) {
                taskDao.updateStatus("EXPIRED", current.getId());
            }
        }
    }

    @Override
    public void setStatusDone(int id) {
        taskDao.updateStatus("DONE", id);
    }
}
