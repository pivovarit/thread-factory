package com.pivovarit.concurrent;

import java.util.concurrent.ThreadFactory;

public final class ThreadFactories {
    private ThreadFactories() {
    }

    /**
     * @param prefix
     *
     * @return
     *
     * @since 0.0.1
     */
    public ThreadFactory prefixed(String prefix) {
        return new ThreadFactoryNameDecorator(prefix);
    }

    /**
     * @param prefix
     * @param threadFactory
     *
     * @return
     *
     * @since 0.0.1
     */
    public ThreadFactory prefixed(String prefix, ThreadFactory threadFactory) {
        return new ThreadFactoryNameDecorator(threadFactory, prefix);
    }

    /**
     * @param suffix
     *
     * @return
     *
     * @since 0.0.1
     */
    public ThreadFactory suffixed(String suffix) {
        return new ThreadFactoryNameDecorator("", suffix);
    }

    /**
     * @param suffix
     * @param threadFactory
     *
     * @return
     *
     * @since 0.0.1
     */
    public ThreadFactory suffixed(String suffix, ThreadFactory threadFactory) {
        return new ThreadFactoryNameDecorator(threadFactory, "", suffix);
    }

    /**
     * @param prefix
     * @param suffix
     *
     * @return
     *
     * @since 0.0.1
     */
    public ThreadFactory named(String prefix, String suffix) {
        return new ThreadFactoryNameDecorator(prefix, suffix);
    }

    /**
     * @param threadFactory
     * @param prefix
     * @param suffix
     *
     * @return
     *
     * @since 0.0.1
     */
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
