package com.pivovarit.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author Grzegorz Piwowarek
 */
class ThreadFactoryNameDecorator implements ThreadFactory {

    private static final String SEPARATOR = "-";
    private static final String EMPTY = "";

    private final ThreadFactory base;
    private final String prefix;
    private final String suffix;

    ThreadFactoryNameDecorator(String prefix) {
        this(Executors.defaultThreadFactory(), prefix, EMPTY);
    }

    ThreadFactoryNameDecorator(ThreadFactory threadFactory, String prefix) {
        this.base = threadFactory;
        this.prefix = prefix != null ? prefix : EMPTY;
        this.suffix = EMPTY;
    }

    ThreadFactoryNameDecorator(String prefix, String suffix) {
        this(Executors.defaultThreadFactory(), prefix, suffix);
    }

    ThreadFactoryNameDecorator(ThreadFactory threadFactory, String prefix, String suffix) {
        this.base = threadFactory;
        this.prefix = prefix != null ? prefix : EMPTY;
        this.suffix = suffix != null ? SEPARATOR + suffix : EMPTY;
    }

    @Override
    public Thread newThread(Runnable task) {
        Thread thread = base.newThread(task);
        thread.setName(prefix + SEPARATOR + thread.getName() + suffix);
        return thread;
    }
}
