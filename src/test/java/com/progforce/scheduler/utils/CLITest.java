package com.progforce.scheduler.utils;

import com.progforce.scheduler.model.Priority;
import com.progforce.scheduler.model.Task;
import com.progforce.scheduler.service.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CLITest {

    @Mock
    private PrintStream testOut;
    @Mock
    private InputStream testIn;

    @Mock
    TaskService taskService;


    /**
     * Make sure the CLI initially prints a welcome message
     */
    @Test
    public void testCLIWritesWelcomeMessage() {
        new CLI(testIn, testOut);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(testOut, atLeastOnce()).println(captor.capture());

        String message = captor.getValue();
        assertTrue("The CLI should initially print a welcome message", message.startsWith("*** WELCOME"));
    }

    @Test
    public void testSaveTask() {
        doNothing().when(taskService).save(anyObject());
        runCliWithInput("1", "NEW TASK", "2016-02-01", "2");
        verify(taskService).save(anyObject());
    }

    @Test
    public void testShowAllOfTasks() {
        List<Task> tasks = new ArrayList<>();
        Task task1 = new Task(1, "Task1", Date.valueOf("2015-12-12"), Priority.HIGH, "TODO");
        Task task2 = new Task(2, "Task2", Date.valueOf("2015-12-13"), Priority.URGENT, "DONE");
        tasks.add(task1);
        tasks.add(task2);

        when(taskService.getAll()).thenReturn(tasks);
        runCliWithInput("2");
        validateMockitoUsage();

        List<String> output = captureOutput();
        //System.out.println(output.toString());
        assertEquals("Should have 15 output calls", 15, output.size());
        verify(taskService, times(2)).getAll();
        assertEquals(2, taskService.getAll().size());
    }

    @Test
    public void testGetTaskById() {
        Task task = new Task(3, "Task3", Date.valueOf("2015-05-06"), Priority.HIGH, "EXPIRED");
        when(taskService.getById(anyInt())).thenReturn(task);
        assertEquals("Task3", taskService.getById(3).getName());
    }

    @Test
    public void testGetAllFinishedTasks() {
        List<Task> tasks = new ArrayList<>();
        Task task1 = new Task(1, "Task1", Date.valueOf("2015-12-10"), Priority.HIGH, "DONE");
        Task task2 = new Task(2, "Task2", Date.valueOf("2015-11-15"), Priority.URGENT, "DONE");
        tasks.add(task1);
        tasks.add(task2);
        when(taskService.getAllFinishedTasks()).thenReturn(tasks);
        runCliWithInput("2", "b");
        verify(taskService).getAllFinishedTasks();
        assertEquals(2, taskService.getAllFinishedTasks().size());
    }

    @Test
    public void testCheckExpiredTask() {
        doNothing().when(taskService).checkExpiredTask(anyList());
        runCliWithInput("2");
        verify(taskService).checkExpiredTask(anyList());
    }

    @Test
    public void testSetStatusDone() {
        doNothing().when(taskService).setStatusDone(anyInt());
        runCliWithInput("2", "a", "1");
        verify(taskService).setStatusDone(anyInt());
    }






    private List<String> captureOutput() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        // 15 times means we printed Welcome, the 'menu' screen, result, and the 'submenu' screen
        verify(testOut, atLeastOnce()).println(captor.capture());

        return captor.getAllValues();
    }

    private CLI runCliWithInput(String... inputLines) {
        StringBuilder builder = new StringBuilder();
        for (String line : inputLines) {
            builder.append(line).append(System.getProperty("line.separator"));
        }

        ByteArrayInputStream in = new ByteArrayInputStream(builder.toString().getBytes());
        CLI cli = new CLI(in, testOut);
        cli.setTaskService(taskService);
        cli.startEventLoop();

        return cli;
    }

}