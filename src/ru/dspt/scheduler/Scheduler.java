package ru.dspt.scheduler;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;

/**
 * На вход поступают пары (LocalDateTime, Callable).
 * Нужно реализовать систему, которая будет выполнять Callable для каждого пришедшего события в указанный LocalDateTime,
 * либо как можно скорее в случае если система перегружена и не успевает все выполнять (имеет беклог).
 * Задачи должны выполняться в порядке согласно значению LocalDateTime либо в порядке прихода события для равных LocalDateTime.
 * События могут приходить в произвольном порядке и добавление новых пар (LocalDateTime, Callable) может вызываться из разных потоков.
 * Created by dsptushkin on 30.04.17.
 */
public interface Scheduler {
    void plan(LocalDateTime time, Callable<Object> callable);
}
