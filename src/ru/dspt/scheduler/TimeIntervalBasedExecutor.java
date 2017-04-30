package ru.dspt.scheduler;

import ru.dspt.scheduler.model.TaskContainer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

import static java.time.temporal.ChronoUnit.MILLIS;

/**
 * Created by dsptushkin on 30.04.17.
 */
public class TimeIntervalBasedExecutor implements Runnable {
    private ExecutorService service;
    final PriorityBlockingQueue<TaskContainer> queue;

    public TimeIntervalBasedExecutor(PriorityBlockingQueue<TaskContainer> queue, ExecutorService service) {
        this.service = service;
        this.queue = queue;
    }

    @Override
    public void run() {
        while(true) {
            try {
                System.out.println("Планирование таймаута. Размер очереди: " + queue.size());
                if (queue.isEmpty()) {
                    System.out.println("очередь пуста. завершение работы");
//                try {
//                    TimeUnit.SECONDS.sleep(5);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                    break;
                } else {
                    while (!queue.isEmpty() && queue.peek().getTime().isBefore(LocalDateTime.now())) {
                        System.out.println("ЗАПЛАНИРОВАННОЕ ИСПОЛНЕНИЕ " + LocalDateTime.now());
                        System.out.println("Время следующей запланированной задачи: " + queue.peek().getTime());
                        System.out.println("queue is not empty, executing task with planned time:" + queue.peek().getTime() +
                                "\n time : " + LocalDateTime.now());
                        service.submit(queue.poll().getTask());
                    }
                    System.out.println("Расчет времени сна...");
                    //todo: выяснить, надо ли ждать, пока очередь опять наполнят, или завершить программу
                    if(queue.isEmpty()) {
                        service.shutdown();
                        break;
                    }
                    LocalDateTime nextTaskTime = queue.peek().getTime();
                    LocalDateTime now = LocalDateTime.now();
                    System.out.println("Расчет времени сна до " + nextTaskTime);
                    System.out.println("Расчет времени сна:" + MILLIS.between(now, nextTaskTime));
                    System.out.println(MILLIS.between(now, nextTaskTime));
                    long timeout = MILLIS.between(LocalDateTime.now(), nextTaskTime);
                    System.out.println("next queue task in " + timeout + " millis");
                    try {
                        System.out.println("Засыпаем");
                        TimeUnit.MILLISECONDS.sleep(timeout);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}
