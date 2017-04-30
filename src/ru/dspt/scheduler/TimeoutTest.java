package ru.dspt.scheduler;

/**
 * Created by dsptushkin on 01.05.17.
 */
public class TimeoutTest implements Runnable {
    @Override
    public void run() {
        while(true) {
            System.out.println("executed");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread t = new Thread(new TimeoutTest());
        t.start();
    }
}
