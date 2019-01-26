package com.pivovarit.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ThreadFactoryNameDecorator implements ThreadFactory {
    private final ThreadFactory defaultThreadFactory;
    private final String prefix;

    private ThreadFactoryNameDecorator(String prefix) {
        this(Executors.defaultThreadFactory(), prefix);
    }

    private ThreadFactoryNameDecorator(ThreadFactory threadFactory, String prefix) {
        this.defaultThreadFactory = threadFactory;
        this.prefix = prefix;
    }

    @Override
    public Thread newThread(Runnable task) {
        Thread thread = defaultThreadFactory.newThread(task);
        thread.setName(prefix + "-" + thread.getName());
        return thread;
    }
}
