package com.jiashunx.app.masker.proxypool.core.bean;

import com.jiashunx.app.masker.proxypool.core.annotation.MScheduler;
import com.jiashunx.app.masker.proxypool.core.exception.MProxyInitializeException;
import com.jiashunx.app.masker.proxypool.core.task.IMProxyScheduler;
import com.jiashunx.app.masker.proxypool.core.util.MDefaultThreadFactory;
import com.jiashunx.app.masker.proxypool.core.util.MProxyHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 初始化类.
 * @author jiashunx
 * @date 2020/09/23
 */
@Component
public class MProxyInitializer {

    @Resource
    private ApplicationContext applicationContext;

    private static boolean initialized = false;

    public synchronized void init() {
        if (initialized) {
            throw new MProxyInitializeException("masker-proxypool has initialized.");
        }
        initialized = true;
        MProxyHelper.threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool(new MDefaultThreadFactory());
        Map<String, IMProxyScheduler> schedulerMap = applicationContext.getBeansOfType(IMProxyScheduler.class);
        MProxyHelper.scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(schedulerMap.size(), new MDefaultThreadFactory());
        for (Map.Entry<String, IMProxyScheduler> entry: schedulerMap.entrySet()) {
            IMProxyScheduler scheduler = entry.getValue();
            MScheduler annotation = scheduler.getClass().getAnnotation(MScheduler.class);
            if (annotation.fixedDelay()) {
                MProxyHelper.scheduledThreadPoolExecutor.scheduleWithFixedDelay(scheduler, annotation.initialDelayMillis(), annotation.delayMillis(), TimeUnit.MILLISECONDS);
            } else {
                MProxyHelper.scheduledThreadPoolExecutor.scheduleAtFixedRate(scheduler, annotation.initialDelayMillis(), annotation.delayMillis(), TimeUnit.MILLISECONDS);
            }
        }
    }

    public static synchronized boolean isInitialized() {
        return initialized;
    }

}
