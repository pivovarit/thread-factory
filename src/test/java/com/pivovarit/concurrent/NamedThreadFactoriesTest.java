package com.pivovarit.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NamedThreadFactoriesTest {

    private static final Runnable NOOP = () -> { };

    @Test
    void shouldPrefixAndThrowException_defaultFactory() {
        assertThatThrownBy(() -> ThreadFactories.prefixed(null)).isInstanceOf(NullPointerException.class);
    }


    @Test
    void shouldPrefixAndThrowException_customFactory() {
        assertThatThrownBy(() -> ThreadFactories.prefixed(null, r -> new Thread())).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldPrefixThreadName_defaultFactory() {
        // given
        final String prefix = "foo";
        final ThreadFactory tf = ThreadFactories.prefixed(prefix);

        // when
        Thread thread = tf.newThread(NOOP);

        // then
        assertThat(thread.getName()).startsWith(prefix);
    }

    @Test
    void shouldSuffixThreadName_customFactory() {
        // given
        final String suffix = "foo";
        final String name = "name";

        ThreadFactory tf = ThreadFactories.suffixed(suffix, r -> new Thread(name));

        // when
        Thread thread = tf.newThread(NOOP);

        // then
        assertThat(thread.getName()).isEqualTo(name + "-" + suffix);
    }

    @Test
    void shouldSuffixAndThrowException_defaultFactory() {
        assertThatThrownBy(() -> ThreadFactories.suffixed(null)).isInstanceOf(NullPointerException.class);
    }


    @Test
    void shouldSuffixAndThrowException_customFactory() {
        assertThatThrownBy(() -> ThreadFactories.suffixed(null, r -> new Thread())).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldSuffixThreadName_defaultFactory() {
        // given
        final String suffix = "foo";
        final ThreadFactory tf = ThreadFactories.suffixed(suffix);

        // when
        Thread thread = tf.newThread(NOOP);

        // then
        assertThat(thread.getName()).endsWith(suffix);
    }

    @Test
    void shouldPrefixThreadName_customFactory() {
        // given
        final String prefix = "foo";
        final String name = "name";

        ThreadFactory tf = ThreadFactories.prefixed(prefix, r -> new Thread(name));

        // when
        Thread thread = tf.newThread(NOOP);

        // then
        assertThat(thread.getName()).isEqualTo(prefix + "-" + name);
    }
}