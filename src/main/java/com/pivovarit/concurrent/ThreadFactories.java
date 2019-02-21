package com.pivovarit.concurrent;

import java.util.concurrent.ThreadFactory;

public final class ThreadFactories {
    private ThreadFactories() {
    }

    public ThreadFactory prefixed(String prefix) {
        return new ThreadFactoryNameDecorator(prefix);
    }

    public ThreadFactory prefixed(String prefix, ThreadFactory threadFactory) {
        return new ThreadFactoryNameDecorator(threadFactory, prefix);
    }

    public ThreadFactory suffixed(String suffix) {
        return new ThreadFactoryNameDecorator("", suffix);
    }

    public ThreadFactory suffixed(String suffix, ThreadFactory threadFactory) {
        return new ThreadFactoryNameDecorator(threadFactory, "", suffix);
    }

    public ThreadFactory named(String prefix, String suffix) {
        return new ThreadFactoryNameDecorator(prefix, suffix);
    }

    public ThreadFactory named(ThreadFactory threadFactory, String prefix, String suffix) {
        return new ThreadFactoryNameDecorator(threadFactory, prefix, suffix);
    }

    public ThreadFactoryBuilder builder(String nameFormat) {
        return new ThreadFactoryBuilderImpl(nameFormat);
    }

    public interface ThreadFactoryBuilder {
        ThreadFactoryBuilder withDaemonThreads(boolean daemon);

        ThreadFactoryBuilder withUncaughtExceptionHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler);

        ThreadFactoryBuilder fromThreadFactory(ThreadFactory backingThreadFactory);

        ThreadFactory build();
    }
}
