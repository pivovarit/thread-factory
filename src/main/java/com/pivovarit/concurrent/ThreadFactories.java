package com.pivovarit.concurrent;

import java.util.concurrent.ThreadFactory;

/**
 * @author Grzegorz Piwowarek
 */
public final class ThreadFactories {
    private ThreadFactories() {
    }

    /**
     * TODO description
     * @param prefix
     *
     * @return
     *
     * @since 0.0.1
     */
    public static ThreadFactory prefixed(String prefix) {
        return new ThreadFactoryNameDecorator(prefix);
    }

    /**
     * TODO description
     * @param prefix
     * @param threadFactory
     *
     * @return
     *
     * @since 0.0.1
     */
    public static ThreadFactory prefixed(String prefix, ThreadFactory threadFactory) {
        return new ThreadFactoryNameDecorator(threadFactory, prefix);
    }

    /**
     * TODO description
     * @param suffix
     *
     * @return
     *
     * @since 0.0.1
     */
    public static ThreadFactory suffixed(String suffix) {
        return new ThreadFactoryNameDecorator("", suffix);
    }

    /**
     * TODO description
     * @param suffix
     * @param threadFactory
     *
     * @return
     *
     * @since 0.0.1
     */
    public static ThreadFactory suffixed(String suffix, ThreadFactory threadFactory) {
        return new ThreadFactoryNameDecorator(threadFactory, "", suffix);
    }

    /**
     * TODO description
     * @param prefix
     * @param suffix
     *
     * @return
     *
     * @since 0.0.1
     */
    public static ThreadFactory named(String nameFormat) {
        return builder(nameFormat).build();
    }

    /**
     * TODO description
     * @param threadFactory
     * @param prefix
     * @param suffix
     *
     * @return
     *
     * @since 0.0.1
     */
    public static ThreadFactory named(ThreadFactory threadFactory, String prefix, String suffix) {
        return new ThreadFactoryNameDecorator(threadFactory, prefix, suffix);
    }

    public static ThreadFactoryBuilder builder(String nameFormat) {
        return new ThreadFactoryBuilderImpl(nameFormat);
    }

    public interface ThreadFactoryBuilder {
        ThreadFactoryBuilder withDaemonThreads(boolean daemon);

        ThreadFactoryBuilder withUncaughtExceptionHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler);

        ThreadFactoryBuilder fromThreadFactory(ThreadFactory backingThreadFactory);

        ThreadFactory build();
    }
}
