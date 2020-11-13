package io.github.jiashunx.masker.proxypool.core.utils;

import io.github.jiashunx.masker.proxypool.core.annotation.MScheduler;
import io.github.jiashunx.masker.proxypool.core.exception.MProxyInitializeException;
import io.github.jiashunx.masker.proxypool.core.task.scheduler.AbstractMProxyScheduler;
import io.github.jiashunx.masker.proxypool.core.task.scheduler.impl.MProxyDefaultScheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 代理池初始化.
 * @author jiashunx
 */
public class MProxyInitializer {

    private static volatile boolean initialized = false;

    public static synchronized void init() {
        if (isInitialized()) {
            throw new MProxyInitializeException("masker-proxypool has initialized.");
        }
        synchronized (MProxyInitializer.class) {
            if (!isInitialized()) {
                MProxyHelper.threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool(new MDefaultThreadFactory());
                MProxyHelper.scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, new MDefaultThreadFactory());
                List<AbstractMProxyScheduler> schedulerList = getBeansOfMProxyScheduler();
                for (AbstractMProxyScheduler scheduler: schedulerList) {
                    MScheduler annotation = scheduler.getClass().getAnnotation(MScheduler.class);
                    if (annotation.fixedDelay()) {
                        MProxyHelper.scheduledThreadPoolExecutor.scheduleWithFixedDelay(scheduler, annotation.initialDelayMillis(), annotation.delayMillis(), TimeUnit.MILLISECONDS);
                    } else {
                        MProxyHelper.scheduledThreadPoolExecutor.scheduleAtFixedRate(scheduler, annotation.initialDelayMillis(), annotation.delayMillis(), TimeUnit.MILLISECONDS);
                    }
                }
                initialized = true;
            }
        }
    }

    /**
     * 获取调度任务.
     * @return List<AbstractMProxyScheduler>
     */
    private static List<AbstractMProxyScheduler> getBeansOfMProxyScheduler() {
        List<AbstractMProxyScheduler> schedulerList = new ArrayList<>();
        schedulerList.add(new MProxyDefaultScheduler());
        return schedulerList;
    }

    /**
     * @return boolean
     */
    public static synchronized boolean isInitialized() {
        return initialized;
    }

}
