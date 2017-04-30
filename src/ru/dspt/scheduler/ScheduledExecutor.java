package ru.dspt.scheduler;

import ru.dspt.scheduler.model.TaskContainer;

import java.time.*;
import java.util.concurrent.*;

/**
 * Created by dsptushkin on 30.04.17.
 */
public class ScheduledExecutor implements Scheduler {
    private ExecutorService service;
    PriorityBlockingQueue<TaskContainer> queue;

    public ScheduledExecutor() {
        this.service = Executors.newCachedThreadPool();
        queue = new PriorityBlockingQueue<>();
    }

    public void startScheduling() {
        Thread t = new Thread(new TimeIntervalBasedExecutor(queue, service));
//        t.setDaemon(true);
        t.start();
        t = new Thread(new TimeoutBasedExecutor(queue, service));
        t.setDaemon(true);
        t.start();
    }

    @Override
    public void plan(LocalDateTime time, Callable<Object> callable) {
        TaskContainer taskContainer = new TaskContainer(time, callable);
        System.out.println("Task planned on " + taskContainer.getTime());
        queue.offer(taskContainer);
    }
}
