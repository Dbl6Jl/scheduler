package ru.dspt.scheduler.model;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;

/**
 * Created by dsptushkin on 30.04.17.
 */
public class TaskContainer {
    private LocalDateTime time;
    private Callable<Object> task;

    public TaskContainer() {
    }

    public TaskContainer(LocalDateTime time, Callable<Object> task) {
        this.time = time;
        this.task = task;
    }

    public Callable<Object> getTask() {
        return task;
    }

    public void setTask(Callable<Object> task) {
        this.task = task;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
