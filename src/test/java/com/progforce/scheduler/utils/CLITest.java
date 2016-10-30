package com.progforce.scheduler.utils;

import com.progforce.scheduler.dao.TaskDao;
import com.progforce.scheduler.dao.impl.TaskDaoJdbcImpl;
import com.progforce.scheduler.service.TaskService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.*;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CLITest {

    @Mock
    private PrintStream testOut;
    @Mock
    private InputStream testIn;

    @Mock
    private TaskDao taskDaoJdbc = new TaskDaoJdbcImpl();
    @Mock
    private TaskService taskService = new TaskService();

    @InjectMocks
    private CLI cli;

    /**
     * Make sure the CLI initially prints a welcome message
     */
    @Test
    public void testCLIWritesWelcomeMessage() {
        new CLI(testIn, testOut);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(testOut, times(2)).println(captor.capture());

        String message = captor.getValue();
        assertTrue("The CLI should initially print a welcome message", message.startsWith("*** WELCOME"));
    }

    @Test
    public void testShowAllOfTasks() throws Exception {
        runCliWithInput("2");

        List<String> output = captureOutput();
        assertEquals("Should have 13 output calls", 13, output.size());
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