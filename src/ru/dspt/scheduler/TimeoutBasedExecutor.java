package ru.dspt.scheduler;

import ru.dspt.scheduler.model.TaskContainer;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;
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
            System.out.println("Ежесекундное исполнение");
            while(!queue.isEmpty() && queue.peek().getTime().isBefore(LocalDateTime.now())) {
                service.submit(queue.poll().getTask());
            }
            try {
                Thread.sleep(1000);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}
