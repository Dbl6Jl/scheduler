package ru.dspt.scheduler;

import org.junit.Test;

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

    @Test
    public void testStartScheduling() throws Exception {
        System.out.println("queue size: " + executor.queue.size());
        for (int i = 0; i < 5; i++) {
            executor.plan(LocalDateTime.now(), () -> {
                System.out.print("Test task executed\t");
                System.out.println(Thread.currentThread().toString() + "  " + LocalDateTime.now());
                return null;
            });
        }
        executor.startScheduling();
        System.out.println("queue size: " + executor.queue.size());
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
        assertEquals(1, executor.queue.size());

    }

}