# thread-factory

[![Build Status](https://travis-ci.org/pivovarit/thread-factory.svg?branch=master)](https://travis-ci.org/pivovarit/thread-factory)
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.pivovarit/thread-factory/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.pivovarit/thread-factory)

## Rationale

Custom thread pool naming is critical for debuggability. 

Unfortunately, relying on default naming schemes often leads to situations where most of our threads are named like:

[[https://github.com/pivovarit/thread-factory/blob/master/docs/img/threads.png|alt=octocat]]


## Basic API

- `ThreadFactories.prefixed(String prefix)`
- `ThreadFactories.prefixed(String prefix, ThreadFactory base)`
- `ThreadFactories.suffixed(String suffix)`
- `ThreadFactories.suffixed(String suffix, ThreadFactory base)`


- `ThreadFactories.builder(String nameFormat)`
    - `withDaemonThreads(boolean daemon)`
    - `withUncaughtExceptionHandler(UncaughtExceptionHandler handler)`
    - `fromThreadFactory(ThreadFactory backingThreadFactory)`
    - `build()`

### Maven Dependencies

    <dependency>
        <groupId>com.pivovarit</groupId>
        <artifactId>thread-factory</artifactId>
        <version>0.0.1</version>
    </dependency>


##### Gradle

    compile 'com.pivovarit:thread-factory:0.0.1'

### Examples



### Tips

### Dependencies

None - the library is implemented using core Java libraries.

## Version history

### [0.0.1](https://github.com/pivovarit/thread-factory/releases/tag/0.0.1) (23-01-2019)

* Initial project version

#### What if I don't want to add a new library just for the sake of prefixing threads?

That's reasonable. Just copy over the following code and use at will:

```
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author Grzegorz Piwowarek
 */
public class CustomThreadFactory implements ThreadFactory {

    private String prefix;
    private ThreadFactory defaultThreadFactory = Executors.defaultThreadFactory();

    CustomThreadFactory(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Thread newThread(Runnable task) {
        Thread thread = defaultThreadFactory.newThread(task);
        thread.setName(prefix + "-" + thread.getName());
        return thread;
    }
}
```

