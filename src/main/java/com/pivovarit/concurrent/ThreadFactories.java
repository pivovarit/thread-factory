package com.pivovarit.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public final class ThreadFactories {
    private ThreadFactories() {
    }

    public ThreadFactory defaultWithPrefix(String prefix) {
        return Executors.defaultThreadFactory();
    }

    public ThreadFactory defaultWithSuffix(String suffix) {
        return Executors.defaultThreadFactory();
    }
}
