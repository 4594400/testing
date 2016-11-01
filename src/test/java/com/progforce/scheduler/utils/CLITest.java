package com.progforce.scheduler.utils;

import com.progforce.scheduler.dao.TaskDao;
import com.progforce.scheduler.dao.impl.TaskDaoJdbcImpl;
import com.progforce.scheduler.model.Priority;
import com.progforce.scheduler.model.Task;
import com.progforce.scheduler.service.TaskService;
import org.junit.Before;
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

    @Mock
    TaskDao taskDao;

    @InjectMocks
    CLI cli;

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
        /*List<Task> list = new ArrayList<>();
        Task task = new Task("Task1", Date.valueOf("2015-12-12"), Priority.HIGH);
        list.add(task);*/

        //when(cli.taskDaoJdbc.getAll()).thenReturn(new ArrayList<>());
        doNothing().when(cli.taskDao.getAll());
        doNothing().when(taskService).checkExpiredTask(anyList());
        runCliWithInput("2");
        validateMockitoUsage();

        List<String> output = captureOutput();
        System.out.println(output.toString());
        verify(taskDao).getAll();
        //verify(testOut, atLeastOnce()).println(captor.capture());
        assertEquals("Should have 16 output calls", 16, output.size());

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
        cli.startEventLoop();

        return cli;
    }

}