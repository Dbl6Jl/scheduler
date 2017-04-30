package ru.dspt.scheduler;

import ru.dspt.scheduler.model.TaskContainer;

import java.time.LocalDateTime;
import java.util.PriorityQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by dsptushkin on 30.04.17.
 */
public class ScheduledExecutor implements Scheduler {
    ExecutorService service;
    PriorityQueue<TaskContainer> queue;

    public ScheduledExecutor() {
        this.service = Executors.newCachedThreadPool();
        queue = new PriorityQueue<>();
    }

    @Override
    public void plan(LocalDateTime time, Callable<Object> callable) {
        TaskContainer taskContainer = new TaskContainer(time, callable);
        System.out.println("Task planned on " + taskContainer.getTime());
        queue.offer(taskContainer);
    }
}
