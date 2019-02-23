# thread-factory

[![Build Status](https://travis-ci.org/pivovarit/thread-factory.svg?branch=master)](https://travis-ci.org/pivovarit/thread-factory)
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.pivovarit/thread-factory/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.pivovarit/thread-factory)

## Rationale

## Basic API

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

### (0.0.1)[https://github.com/pivovarit/thread-factory/releases/tag/0.0.1] (23-01-2019)

* Initial project version

#### What if I don't want to add a new library just for the sake of using one class?

That's reasonable. Just copy over the following code:

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

