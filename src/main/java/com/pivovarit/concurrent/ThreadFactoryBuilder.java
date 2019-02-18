package com.pivovarit.concurrent;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public final class ThreadFactoryBuilder {

    private final String nameFormat;

    private Boolean daemon = null;
    private UncaughtExceptionHandler uncaughtExceptionHandler = null;
    private ThreadFactory backingThreadFactory = null;

    public ThreadFactoryBuilder(String nameFormat) {
        this.nameFormat = nameFormat;
    }

    public ThreadFactoryBuilder setDaemon(boolean daemon) {
        this.daemon = daemon;
        return this;
    }

    public ThreadFactoryBuilder setUncaughtExceptionHandler(UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.uncaughtExceptionHandler = Objects.requireNonNull(uncaughtExceptionHandler);
        return this;
    }

    public ThreadFactoryBuilder setThreadFactory(ThreadFactory backingThreadFactory) {
        this.backingThreadFactory = Objects.requireNonNull((backingThreadFactory));
        return this;
    }

    public ThreadFactory build() {
        final String nameFormat1 = nameFormat;
        final Boolean daemon1 = daemon;
        final UncaughtExceptionHandler uncaughtExceptionHandler1 = uncaughtExceptionHandler;
        final ThreadFactory backingThreadFactory1 = backingThreadFactory != null ? backingThreadFactory : Executors.defaultThreadFactory();
        final AtomicLong count = nameFormat1 != null ? new AtomicLong(0L) : null;
        return runnable -> {
            Thread thread = backingThreadFactory1.newThread(runnable);
            if (nameFormat1 != null) {
                thread.setName(ThreadFactoryBuilder.format(nameFormat1, count.getAndIncrement()));
            }

            if (daemon1 != null) {
                thread.setDaemon(daemon1);
            }

            if (uncaughtExceptionHandler1 != null) {
                thread.setUncaughtExceptionHandler(uncaughtExceptionHandler1);
            }

            return thread;
        };
    }


    private static String format(String format, Object... args) {
        return String.format(Locale.ROOT, format, args);
    }
}

