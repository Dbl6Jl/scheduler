package ru.dspt.scheduler.model;

import java.util.Comparator;

/**
 * Created by dsptushkin on 30.04.17.
 */
public class TaskComparator implements Comparator<TaskContainer> {
    @Override
    public int compare(TaskContainer o1, TaskContainer o2) {
        return o1.getTime().compareTo(o2.getTime());
    }
}
