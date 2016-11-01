package com.progforce.scheduler.utils;

import com.progforce.scheduler.dao.TaskDao;
import com.progforce.scheduler.model.Priority;
import com.progforce.scheduler.model.Task;
import com.progforce.scheduler.service.TaskService;
import com.progforce.scheduler.service.impl.TaskServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.*;
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


    public TaskService getTaskService() {
        return taskService;
    }

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
    public void testShowAllOfTasks() throws Exception {
        List<Task> tasks = new ArrayList<>();
        Task task1 = new Task(1, "Task1", Date.valueOf("2015-12-12"), Priority.HIGH, "TODO");
        Task task2 = new Task(2, "Task2", Date.valueOf("2015-12-13"), Priority.URGENT, "DONE");
        tasks.add(task1);
        tasks.add(task2);

        when(taskService.getAll()).thenReturn(tasks);
        runCliWithInput("2");
        validateMockitoUsage();

        List<String> output = captureOutput();
        assertEquals("Should have 15 output calls", 15, output.size());
        verify(taskService, times(2)).getAll();
        assertEquals(2, taskService.getAll().size());

    }

    private List<String> captureOutput() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        // 9 times means we printed Welcome, the input prompt twice, and the 'help' screen
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
        cli.setTaskService(getTaskService());
        cli.startEventLoop();

        return cli;
    }

}