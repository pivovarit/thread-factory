package com.pivovarit.concurrent;

import java.util.Objects;
import java.util.concurrent.ThreadFactory;

/**
 * @author Grzegorz Piwowarek
 */
public final class ThreadFactories {
    private ThreadFactories() {
    }

    /**
     * Returns a default {@link ThreadFactory} creating threads with custom name prefix
     *
     * @param prefix
     *
     * @return
     *
     * @since 0.0.1
     */
    public static ThreadFactory prefixed(String prefix) {
        Objects.requireNonNull(prefix, "prefix can't be null");
        return new ThreadFactoryNameDecorator(prefix);
    }

    /**
     * TODO description
     *
     * @param prefix
     * @param threadFactory
     *
     * @return
     *
     * @since 0.0.1
     */
    public static ThreadFactory prefixed(String prefix, ThreadFactory threadFactory) {
        Objects.requireNonNull(threadFactory, "threadFactory can't be null");
        Objects.requireNonNull(prefix, "prefix can't be null");

        return new ThreadFactoryNameDecorator(threadFactory, prefix);
    }

    /**
     * TODO description
     *
     * @param suffix
     *
     * @return
     *
     * @since 0.0.1
     */
    public static ThreadFactory suffixed(String suffix) {
        Objects.requireNonNull(suffix, "suffix can't be null");

        return new ThreadFactoryNameDecorator("", suffix);
    }

    /**
     * TODO description
     *
     * @param suffix
     * @param threadFactory
     *
     * @return
     *
     * @since 0.0.1
     */
    public static ThreadFactory suffixed(String suffix, ThreadFactory threadFactory) {
        Objects.requireNonNull(threadFactory, "threadFactory can't be null");
        Objects.requireNonNull(suffix, "suffix can't be null");

        return new ThreadFactoryNameDecorator(threadFactory, "", suffix);
    }

    /**
     * TODO description
     *
     * @param nameFormat
     *
     * @return
     *
     * @since 0.0.1
     */
    public static ThreadFactory named(String nameFormat) {
        Objects.requireNonNull(nameFormat, "nameFormat can't be null");
        return builder(nameFormat).build();
    }

    /**
     * TODO description
     *
     * @param threadFactory
     * @param prefix
     * @param suffix
     *
     * @return
     *
     * @since 0.0.1
     */
    public static ThreadFactory named(ThreadFactory threadFactory, String prefix, String suffix) {
        Objects.requireNonNull(threadFactory, "threadFactory can't be null");
        Objects.requireNonNull(prefix, "prefix can't be null");
        Objects.requireNonNull(suffix, "suffix can't be null");

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
