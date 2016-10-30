package com.progforce.scheduler.utils;


import com.progforce.scheduler.dao.impl.TaskDaoJdbcImpl;
import com.progforce.scheduler.model.Priority;
import com.progforce.scheduler.model.Task;
import com.progforce.scheduler.service.TaskService;

import java.io.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class CLI {
    private static final String NEWLINE = System.getProperty("line.separator");
    private BufferedReader inReader;
    private PrintStream outStream;
    TaskDaoJdbcImpl taskDaoJdbc = new TaskDaoJdbcImpl();
    TaskService taskService = new TaskService();

    public CLI(InputStream inputStream, PrintStream outStream) {
        this.inReader = new BufferedReader(new InputStreamReader(inputStream));
        this.outStream = outStream;
        writeOutput("*** WELCOME! ***");
    }

    public void startEventLoop() {

        while (true) {
            printMainMenu();
            String input = getInput();
            //MAIN MENU
            switch (input) {
                case "1":
                    writeOutput("Input name of task:");
                    String nameOfTask = getInput();
                    writeOutput("Input deadline (Date format: YYYY-MM-DD):");
                    Date deadline;
                    try {
                        deadline = Date.valueOf(getInput());
                    } catch (IllegalArgumentException e) {
                        writeOutput("---> |Invalid Date format! Try again.|");
                        continue;
                    }
                    printPriorities();
                    Priority priority;
                    String levelOfPriority = getInput();
                    //SELECT PRIORITY
                    switch (levelOfPriority) {
                        case "1":
                            priority = Priority.URGENT;
                            break;
                        case "2":
                            priority = Priority.HIGH;
                            break;
                        case "3":
                            priority = Priority.MEDIUM;
                            break;
                        case "4":
                            priority = Priority.LOW;
                            break;
                        default:
                            writeOutput("---> |Invalid priority selection! Try again.|");
                            System.out.println();
                            continue;
                    }
                    taskDaoJdbc.save(new Task(nameOfTask, deadline, priority));
                    writeOutput("Task added!");
                    writeOutput(NEWLINE);
                    continue;
                case "2":
                    taskService.checkExpiredTask(taskDaoJdbc.getAll());
                    printTaskList(taskDaoJdbc.getAll());
                    writeOutput(NEWLINE);

                    boolean flag = true;
                    while (flag) {
                        printSubMenu();
                        String subInput = getInput();
                        // SUBMENU
                        switch (subInput) {
                            case "a":
                                writeOutput("Input Task ID:");
                                int id = 0;
                                try {
                                    id = Integer.parseInt(getInput());
                                } catch (NumberFormatException e) {
                                    writeOutput("---> |Invalid input! It is not a number. Try again.|");
                                    break;
                                }
                                taskService.setStatusDone(id);
                                printTaskList(taskDaoJdbc.getAll());
                                break;
                            case "b":
                                printTaskList(taskDaoJdbc.getAllFinishedTasks());
                                break;
                            case "c":
                                flag = false;
                                break;
                            default:
                                writeOutput("---> |Invalid input! Try again.| ");
                                break;
                        }
                    }
                default:
                    break;
            }

            if (Objects.equals(input, "0")) {
                writeOutput("*** BYE! ***");
                break;
            }
        }
    }

    public String getInput() {
        try {
            this.outStream.print("> ");
            return inReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("---> |Failed to read from input:| ", e);
        }
    }

    public void writeOutput(String str) {
        this.outStream.println(str);
    }

    public void printMainMenu() {
        writeOutput("   ---------- Select action: ----------");
        writeOutput("   1. ADD NEW TASK:      | Enter 1");
        writeOutput("   2. SHOW ALL OF TASKS: | Enter 2");
        writeOutput("   3. EXIT:              | Enter 0");
    }

    public void printSubMenu() {
        writeOutput("   --------------- Select action: ---------------");
        writeOutput("   a. SET \"DONE\" FOR TASK              | Enter a");
        writeOutput("   b. SHOW ALL FINISHED TASKS          | Enter b");
        writeOutput("   c. MAIN MENU                        | Enter c");
    }

    public void printPriorities() {
        writeOutput("   ---------- Select priority: ----------");
        writeOutput("   1. URGENT      | Enter 1;");
        writeOutput("   2. HIGH        | Enter 2;");
        writeOutput("   3. MEDIUM      | Enter 3;");
        writeOutput("   4. LOW         | Enter 4;");
    }

    public void printTaskList(List<Task> list) {
        StringBuilder result = new StringBuilder();
        printSeparator(result);
        result.append(String.format("%-10s%-30s%15s%15s%15s%n", "ID", "NAME", "DEADLINE", "PRIORITY", "STATUS"));
        printSeparator(result);
        for (Task task : list) {
            result.append(String.format("%-10d%-30s%15s%15s%15s%n", task.getId(), task.getName(), task.getDeadline(), task.getPriority(), task.getStatus()));
        }
        printSeparator(result);
        writeOutput(result.toString());
    }

    public void printSeparator(StringBuilder builder) {
        builder.append("-------------------------------------------------------------------------------------").append(NEWLINE);
    }
}
