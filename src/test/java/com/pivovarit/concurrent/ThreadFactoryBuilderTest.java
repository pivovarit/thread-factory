package com.pivovarit.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import static org.assertj.core.api.Assertions.assertThat;

class ThreadFactoryBuilderTest {

    @Test
    void shouldOverrideName_defaultFactory() {
        // given
        String name = "name";
        ThreadFactory tf = ThreadFactories.builder(name + "%d").build();

        // when
        Thread thread1 = tf.newThread(() -> {});
        Thread thread2 = Executors.defaultThreadFactory().newThread(() -> {});

        assertThat(thread1.getName())
          .contains(name)
          .endsWith("0");

        // then
        assertThat(thread1.isDaemon()).isEqualTo(thread2.isDaemon());
        assertThat(thread1.getUncaughtExceptionHandler()).isEqualTo(thread2.getUncaughtExceptionHandler());
    }

    @Test
    void shouldOverrideDaemon_defaultFactory() {
        // given
        String name = "name";
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = (t, e) -> { };
        ThreadFactory tf = ThreadFactories.builder(name + "%d")
          .withDaemonThreads(true)
          .withUncaughtExceptionHandler(uncaughtExceptionHandler)
          .build();

        // when
        Thread thread1 = tf.newThread(() -> {});

        assertThat(thread1.getName())
          .contains(name)
          .endsWith("0");

        // then
        assertThat(thread1.isDaemon()).isTrue();
        assertThat(thread1.getUncaughtExceptionHandler()).isEqualTo(uncaughtExceptionHandler);
    }

    @Test
    void shouldIncrementName() {
        // given
        ThreadFactory tf = ThreadFactories.builder("%d").build();

        // when
        Thread thread1 = tf.newThread(() -> {});
        Thread thread2 = tf.newThread(() -> {});

        // then
        assertThat(thread1.getName()).isEqualTo("0");
        assertThat(thread2.getName()).isEqualTo("1");
    }

    @Test
    void shouldUseProvidedThreadFactoryAsBase() {
        // given
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = (t, e) -> { };

        ThreadFactory tf = ThreadFactories.builder("%d")
          .withDaemonThreads(true)
          .withUncaughtExceptionHandler(uncaughtExceptionHandler)
          .build();

        // when
        ThreadFactory derived = ThreadFactories.builder("%d")
          .fromThreadFactory(tf)
          .build();

        // then
        assertThat(derived.newThread(() -> {}).getUncaughtExceptionHandler()).isEqualTo(uncaughtExceptionHandler);
    }
}