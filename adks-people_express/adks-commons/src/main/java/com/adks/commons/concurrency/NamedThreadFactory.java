package com.adks.commons.concurrency;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * A ThreadFactory that uses user defined names for all created threads. This class implements the {@link ThreadFactory}.
 * @author panpanxu
 */
public class NamedThreadFactory implements ThreadFactory {

    /**
     * Thread prefix
     */
    private final String name;
    
    /**
     * thread factory used.
     */
    private final ThreadFactory tFactory = Executors.defaultThreadFactory();

    /**
     * Set the prefix of thread name for all created threads.<br>
     * @param threadName - prefix of thread name
     * @throws IllegalArgumentException - if threadName is null or empty
     */
    public NamedThreadFactory(String threadName) {
        if (threadName == null || threadName.isEmpty()) {
            throw new IllegalArgumentException("Thread name must not be null or empty");
        }
        this.name = threadName;
    }

    public Thread newThread(Runnable runnable) {
        Thread t = tFactory.newThread(runnable);
        String num = t.getName().split("-")[3];
        t.setName(name + "-" + num);
        return t;
    }
}