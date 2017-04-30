package ru.dspt.scheduler;

import java.time.LocalDateTime;

/**
 * Основной класс для тестирования. JUnit не позволяет работать с потоками
 * Created by dsptushkin on 01.05.17.
 */
public class Main extends ScheduledExecutor {
    public static void main(String[] args) {
        Main main = new Main();
        for (int i = 0; i < 5; i++) {
            LocalDateTime planTime = LocalDateTime.now().plusSeconds(i);
            int finalI = i;
            main.plan(planTime, () -> {
                System.out.print("Test task #" + finalI + " executed\t");
                System.out.println(Thread.currentThread().toString() + "  " + planTime);
                return new Object();
            });
        }
        main.startScheduling();
    }
}
