package com.pivovarit.concurrent;

import java.util.Objects;
import java.util.concurrent.ThreadFactory;

/**
 * @author Grzegorz Piwowarek
 * @since 0.0.1
 */
public final class ThreadFactories {
    private ThreadFactories() {
    }

    /**
     * Returns a default {@link ThreadFactory} creating threads with names decorated with a given prefix.
     * For example, if a name assigned by the thread factory is "name" and provided prefix is "prefix", the resulting name will be "prefix-name"
     *
     * @param prefix a custom String to be appended before the default name
     *
     * @return a customized ThreadFactory instance
     *
     * @since 0.0.1
     */
    public static ThreadFactory prefixed(String prefix) {
        Objects.requireNonNull(prefix, "prefix can't be null");
        return new ThreadFactoryNameDecorator(prefix);
    }

    /**
     * Returns a provided {@link ThreadFactory} creating threads with names decorated with a given prefix.
     * For example, if a name assigned by the thread factory is "name" and provided prefix is "prefix", the resulting name will be "prefix-name"
     *
     * @param prefix        a custom String to be appended before the default name
     * @param threadFactory a ThreadFactory instance to be used as a base
     *
     * @return a customized ThreadFactory instance

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
     * @param suffix a custom String to be appended after the default name
     *
     * @return a customized ThreadFactory instance

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
     * @param suffix        a custom String to be appended after the default name
     * @param threadFactory a ThreadFactory instance to be used as a base
     *
     * @return a customized ThreadFactory instance

     *
     * @since 0.0.1
     */
    public static ThreadFactory suffixed(String suffix, ThreadFactory threadFactory) {
        Objects.requireNonNull(threadFactory, "threadFactory can't be null");
        Objects.requireNonNull(suffix, "suffix can't be null");

        return new ThreadFactoryNameDecorator(threadFactory, null, suffix);
    }

    /**
     * @param nameFormat
     *
     * @return a customized ThreadFactory instance

     *
     * @since 0.0.1
     */
    public static ThreadFactoryBuilder builder(String nameFormat) {
        return new ThreadFactoryBuilderImpl(nameFormat);
    }

    /**
     * @since 0.0.1
     */
    public interface ThreadFactoryBuilder {
        /**
         * @param daemon
         *
         * @return
         *
         * @since 0.0.1
         */
        ThreadFactoryBuilder withDaemonThreads(boolean daemon);

        /**
         * @param uncaughtExceptionHandler
         *
         * @return
         *
         * @since 0.0.1
         */
        ThreadFactoryBuilder withUncaughtExceptionHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler);

        /**
         * @param backingThreadFactory a ThreadFactory instance to be used as a base
         *
         * @return
         *
         * @since 0.0.1
         */
        ThreadFactoryBuilder fromThreadFactory(ThreadFactory backingThreadFactory);

        /**
         * Returns a new thread factory based on settings provided during building
         *
         * @return the fully constructed {@link ThreadFactory}
         *
         * @since 0.0.1
         */
        ThreadFactory build();
    }
}
