package com.jiashunx.app.masker.proxypool.core.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 自定义线程工厂类.
 * @author jiashunx
 */
public class MDefaultThreadFactory implements ThreadFactory {

    private static final AtomicLong poolNumber = new AtomicLong(1);
    private final ThreadGroup group;
    private final AtomicLong threadNumber = new AtomicLong(1);
    private final String namePrefix;

    public MDefaultThreadFactory() {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        namePrefix = "masker-proxypool-" + poolNumber.getAndIncrement() + "-thread-";
    }

    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
        if (t.isDaemon())
            t.setDaemon(false);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }

}
