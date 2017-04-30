package ru.dspt.scheduler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.Callable;

import static org.junit.Assert.*;

/**
 * Created by dsptushkin on 30.04.17.
 */
public class ScheduledExecutorTest {
    ScheduledExecutor executor;
    @org.junit.Before
    public void setUp() throws Exception {
        executor = new ScheduledExecutor();
    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void plan() throws Exception {
        System.out.println(executor.queue.size());
        executor.plan(LocalDateTime.now(), () -> {
            System.out.println("Test task executed");
            System.out.println(LocalDateTime.now());
            return null;
        });
        System.out.println(executor.queue.size());
        assertNotNull(executor.queue.size());

    }

}