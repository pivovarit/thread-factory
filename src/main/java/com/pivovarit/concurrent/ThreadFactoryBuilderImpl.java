package com.pivovarit.concurrent;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.Executors.*;

/**
 * @author Grzegorz Piwowarek
 */
final class ThreadFactoryBuilderImpl implements ThreadFactories.ThreadFactoryBuilder {

    private static final String DEFAULT_PATTERN = "thread-%d";

    private final String nameFormat;

    private Boolean daemon = null;
    private UncaughtExceptionHandler uncaughtExceptionHandler = null;
    private ThreadFactory base = null;

    ThreadFactoryBuilderImpl(String nameFormat) {
        this.nameFormat = (nameFormat != null) && !nameFormat.isEmpty() ? nameFormat : DEFAULT_PATTERN;
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
        this.base = Objects.requireNonNull((backingThreadFactory));
        return this;
    }

    /**
     * @implNote Linux/OSX give all threads the same priority
     */
    @Override
    public ThreadFactory build() {
        final ThreadFactory threadFactory = base != null ? base : defaultThreadFactory();
        final String nameFormat = this.nameFormat;

        final Boolean isDaemon = daemon;
        final UncaughtExceptionHandler uncaughtExceptionHandler = this.uncaughtExceptionHandler;

        return new ThreadFactory() {

            final AtomicInteger counter = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = threadFactory.newThread(runnable);
                thread.setName(String.format(Locale.ROOT, nameFormat, counter.getAndIncrement()));

                if (isDaemon != null) {
                    thread.setDaemon(isDaemon);
                }

                if (uncaughtExceptionHandler != null) {
                    thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
                }

                return thread;
            }
        };
    }
}

