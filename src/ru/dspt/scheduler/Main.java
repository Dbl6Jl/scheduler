package ru.dspt.scheduler;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * Основной класс для тестирования. JUnit требует количества потоков
 * Created by dsptushkin on 01.05.17.
 */
public class Main extends ScheduledExecutor {
    public static void main(String[] args) {
        Main main = new Main();
        Thread t = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                LocalDateTime planTime = LocalDateTime.now().plusSeconds(i%10);
                int finalI = i + 105;
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                    main.plan(planTime, () -> {
                        System.out.print("async task #" + finalI + " executed");
                        System.out.println(Thread.currentThread().toString() + "  " + planTime);
                        return null;
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        t.start();
        for (int i = 0; i < 105; i++) {
            LocalDateTime planTime = LocalDateTime.now().plusSeconds(i%10);
            int finalI = i;
            main.plan(planTime, () -> {
                System.out.print("Test task #" + finalI + " executed\t");
                System.out.println(Thread.currentThread().toString() + "  " + planTime);
                return null;
            });
        }
        main.startScheduling();
    }
}
