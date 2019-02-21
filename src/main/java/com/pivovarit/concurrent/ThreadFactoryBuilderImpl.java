package com.pivovarit.concurrent;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Grzegorz Piwowarek
 */
final class ThreadFactoryBuilderImpl implements ThreadFactories.ThreadFactoryBuilder {

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
        final String nameFormat = this.nameFormat;
        final Boolean isDaemon = daemon;
        final UncaughtExceptionHandler uncaughtExceptionHandler = this.uncaughtExceptionHandler;
        final ThreadFactory threadFactory = backingThreadFactory != null ? backingThreadFactory : Executors
          .defaultThreadFactory();

        return new ThreadFactory() {

            final AtomicInteger counter = nameFormat != null ? new AtomicInteger(0) : null;

            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = threadFactory.newThread(runnable);
                if (nameFormat != null) {
                    thread.setName(ThreadFactoryBuilderImpl.format(nameFormat, counter.getAndIncrement()));
                }

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

    private static String format(String format, Object... args) {
        return String.format(Locale.ROOT, format, args);
    }
}

