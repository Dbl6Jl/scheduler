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
    private final PriorityBlockingQueue<TaskContainer> queue;

    public TimeIntervalBasedExecutor(PriorityBlockingQueue<TaskContainer> queue, ExecutorService service) {
        this.service = service;
        this.queue = queue;
    }

    @Override
    public void run() {
        while(true) {
            System.out.println("Планирование таймаута. Размер очереди: " + queue.size());
            if (!queue.isEmpty()) {
                while (!queue.isEmpty() && queue.peek().getTime().isBefore(LocalDateTime.now())) {
                    System.out.println("ЗАПЛАНИРОВАННОЕ ИСПОЛНЕНИЕ " + LocalDateTime.now());
                    System.out.println("Время следующей запланированной задачи: " + queue.peek().getTime());
                    service.submit(queue.poll().getTask());
                    Thread.yield();
                }
                //todo: выяснить, надо ли ждать, пока очередь опять наполнят, или завершить программу
                if(queue.isEmpty()) {
//                        service.shutdown();
                    break;
                }
                LocalDateTime nextTaskTime = queue.peek().getTime();
                LocalDateTime now = LocalDateTime.now();
                long timeout = MILLIS.between(LocalDateTime.now(), nextTaskTime);
                //если 0, с момента завершения внутреннего while накидали задач на сейчас/в прошлое
                System.out.println("Расчет времени сна: " + MILLIS.between(now, nextTaskTime) + "мс");
                try {
                    TimeUnit.MILLISECONDS.sleep(timeout);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
