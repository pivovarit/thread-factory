package com.pivovarit.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

class ThreadFactoryNameDecorator implements ThreadFactory {

    private final ThreadFactory base;
    private final String prefix;
    private final String suffix;

    ThreadFactoryNameDecorator(String prefix) {
        this(Executors.defaultThreadFactory(), prefix, "");
    }

    ThreadFactoryNameDecorator(ThreadFactory threadFactory, String prefix) {
        this.base = threadFactory;
        this.prefix = prefix != null ? prefix : "";
        this.suffix = "";
    }

    ThreadFactoryNameDecorator(String prefix, String suffix) {
        this(Executors.defaultThreadFactory(), prefix, suffix);
    }

    ThreadFactoryNameDecorator(ThreadFactory threadFactory, String prefix, String suffix) {
        this.base = threadFactory;
        this.prefix = prefix != null ? prefix : "";
        this.suffix = suffix != null ? suffix : "";
    }

    @Override
    public Thread newThread(Runnable task) {
        Thread thread = base.newThread(task);
        thread.setName(prefix + "-" + thread.getName() + "-" + suffix);
        return thread;
    }
}
