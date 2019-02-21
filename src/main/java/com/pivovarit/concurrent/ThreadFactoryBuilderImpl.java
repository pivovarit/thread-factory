package com.pivovarit.concurrent;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;
public final class ThreadFactoryBuilderImpl implements ThreadFactories.ThreadFactoryBuilder {

    private final String nameFormat;

    private Boolean daemon = null;
    private UncaughtExceptionHandler uncaughtExceptionHandler = null;
    private ThreadFactory backingThreadFactory = null;

    ThreadFactoryBuilderImpl(String nameFormat) {
        this.nameFormat = nameFormat;
    }

    @Override
    public ThreadFactories.ThreadFactoryBuilder withDaemonThreads(boolean daemon) {
        this.daemon = daemon;
        return this;
    }

    @Override
    public ThreadFactories.ThreadFactoryBuilder withUncaughtExceptionHandler(UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.uncaughtExceptionHandler = Objects.requireNonNull(uncaughtExceptionHandler);
        return this;
    }

    @Override
    public ThreadFactories.ThreadFactoryBuilder fromThreadFactory(ThreadFactory backingThreadFactory) {
        this.backingThreadFactory = Objects.requireNonNull((backingThreadFactory));
        return this;
    }

    /**
     * @implNote Linux/OSX give all threads the same priority
     */
    @Override
    public ThreadFactory build() {
        final String nameFormat1 = nameFormat;
        final Boolean daemon1 = daemon;
        final UncaughtExceptionHandler uncaughtExceptionHandler1 = uncaughtExceptionHandler;
        final ThreadFactory backingThreadFactory1 = backingThreadFactory != null ? backingThreadFactory : Executors.defaultThreadFactory();
        final AtomicLong count = nameFormat1 != null ? new AtomicLong(0L) : null;
        return runnable -> {
            Thread thread = backingThreadFactory1.newThread(runnable);
            if (nameFormat1 != null) {
                thread.setName(ThreadFactoryBuilderImpl.format(nameFormat1, count.getAndIncrement()));
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

