package ru.dspt.scheduler;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by dsptushkin on 30.04.17.
 */
public class ScheduledExecutor implements Scheduler {
    ExecutorService service;


    public ScheduledExecutor() {
        this.service = Executors.newCachedThreadPool();
    }

    @Override
    public void plan(LocalDateTime time, Callable<Object> callable) {

    }
}
