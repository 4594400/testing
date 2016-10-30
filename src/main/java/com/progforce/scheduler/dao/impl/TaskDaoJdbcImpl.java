package com.progforce.scheduler.dao.impl;


import com.progforce.scheduler.dao.TaskDao;
import com.progforce.scheduler.model.Priority;
import com.progforce.scheduler.model.Task;
import com.progforce.scheduler.utils.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDaoJdbcImpl implements TaskDao {

    DBConnector dbConnector = new DBConnector();

    @Override
    public void save(Task value) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO tasks (name, deadline, priority) VALUES (?,?,?)")) {
            ps.setString(1, value.getName());
            ps.setDate(2, value.getDeadline());
            ps.setString(3, value.getPriority().toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Exception occurred during the execution of the method: save");
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    public Task getById(int id) {
        Task task = null;
        try (PreparedStatement ps = dbConnector.getConnection().prepareStatement("SELECT * FROM tasks WHERE id=?")) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                task = createTask(resultSet);
            }
        } catch (SQLException e) {
            System.err.println("No task with id = " + id);
            throw new RuntimeException(e.getMessage());
        }
        return task;
    }


    @Override
    public List<Task> getAll() {
        List<Task> result = new ArrayList<>();
        try (Statement statement = dbConnector.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tasks");
            while (resultSet.next()) {
                Task task = createTask(resultSet);
                result.add(task);
            }
        } catch (SQLException e) {
            System.err.println("Exception occurred during the execution of the method: getAll");
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    @Override
    public void updateStatus(String status, int id) {
        try(PreparedStatement ps = dbConnector.getConnection().prepareStatement("UPDATE tasks SET status = ? WHERE id=?")) {
            ps.setString(1, status);
            ps.setInt(2, id);
            ps.execute();
        } catch (SQLException e) {
            System.err.println("Exception occurred during the execution of the method: updateStatus");
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Task> getAllFinishedTasks() {
        List<Task> result = new ArrayList<>();
        try (Statement statement = dbConnector.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tasks WHERE status = 'DONE'");
            while (resultSet.next()) {
                Task task = createTask(resultSet);
                result.add(task);
            }
        } catch (SQLException e) {
            System.err.println("Exception occurred during the execution of the method: getAllFinishedTasks");
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    private Task createTask(ResultSet resultSet) throws SQLException {
        Task task = new Task();
        task.setId(resultSet.getInt("id"));
        task.setName(resultSet.getString("name"));
        task.setDeadline(resultSet.getDate("deadline"));
        task.setPriority(Enum.valueOf(Priority.class, resultSet.getString("priority")));
        task.setStatus(resultSet.getString("status"));
        return task;
    }

}
