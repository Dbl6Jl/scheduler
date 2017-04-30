package ru.dspt.scheduler;

import ru.dspt.scheduler.model.TaskContainer;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Каждую секунду просыпаемся на случай того, что в начало очереди попала задача после планирования
 * Исполнителя задач по интервалу
 * Created by dsptushkin on 30.04.17.
 */
public class TimeoutBasedExecutor implements Runnable {
    private PriorityBlockingQueue<TaskContainer> queue;
    private ExecutorService service;

    public TimeoutBasedExecutor(PriorityBlockingQueue<TaskContainer> queue, ExecutorService service) {
        this.queue = queue;
        this.service = service;
    }

    @Override
    public void run() {
        while(true) {
            if(queue.peek().getTime().isAfter(LocalDateTime.now())) {
                service.submit(queue.poll().getTask());
            }
            try {
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.yield();
            }
        }
    }
}
