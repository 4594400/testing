package com.progforce.scheduler;


import com.progforce.scheduler.utils.CLI;

public class Main {
    public static void main(String[] args) {
        CLI cli = new CLI(System.in, System.out);
        cli.startEventLoop();

    }

}
