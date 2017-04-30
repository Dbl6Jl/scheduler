package ru.dspt.scheduler;

import ru.dspt.scheduler.model.TaskContainer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by dsptushkin on 30.04.17.
 */
public class TimeIntervalBasedExecutor implements Runnable {
    private ExecutorService service;
    PriorityBlockingQueue<TaskContainer> queue;

    public TimeIntervalBasedExecutor(PriorityBlockingQueue<TaskContainer> queue, ExecutorService service) {
        this.service = service;
        this.queue = queue;
    }

    @Override
    public void run() {
        while(true) {
            if(queue.isEmpty()) {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                while(!queue.isEmpty()) {
                    if(queue.peek().getTime().isBefore(LocalDateTime.now())) {
                        System.out.println("queue is not empty, executing task with planned time:" + queue.peek().getTime() +
                                "\n time : " + LocalDateTime.now());
                        service.submit(queue.poll().getTask());
                    } else {
                        break;
                    }
                }
                Duration duration = Duration.between(LocalDateTime.now(), queue.peek().getTime());
                System.out.println("queue is not empty, next task in ");
                long timeout = duration.get(ChronoUnit.MILLIS);
                System.out.println("next queue task in " + timeout + " millis");
                try {
                    TimeUnit.MILLISECONDS.sleep(timeout);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
