package com.jiashunx.app.masker.proxypool.starter;

import com.jiashunx.app.masker.proxypool.core.annotation.MScheduler;
import com.jiashunx.app.masker.proxypool.core.exception.MProxyInitializeException;
import com.jiashunx.app.masker.proxypool.core.task.IMProxyScheduler;
import com.jiashunx.app.masker.proxypool.core.util.MDefaultThreadFactory;
import com.jiashunx.app.masker.proxypool.starter.util.MSpringProxyHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 初始化类.
 * @author jiashunx
 */
@Component
public class MProxyInitializer {

    @Resource
    private ApplicationContext applicationContext;

    private static volatile boolean initialized = false;

    public synchronized void init() {
        if (isInitialized()) {
            throw new MProxyInitializeException("masker-proxypool has initialized.");
        }
        synchronized (MProxyInitializer.class) {
            if (!isInitialized()) {
                MSpringProxyHelper.threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool(new MDefaultThreadFactory());
                Map<String, IMProxyScheduler> schedulerMap = applicationContext.getBeansOfType(IMProxyScheduler.class);
                MSpringProxyHelper.scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(schedulerMap.size(), new MDefaultThreadFactory());
                for (Map.Entry<String, IMProxyScheduler> entry: schedulerMap.entrySet()) {
                    IMProxyScheduler scheduler = entry.getValue();
                    MScheduler annotation = scheduler.getClass().getAnnotation(MScheduler.class);
                    if (annotation.fixedDelay()) {
                        MSpringProxyHelper.scheduledThreadPoolExecutor.scheduleWithFixedDelay(scheduler, annotation.initialDelayMillis(), annotation.delayMillis(), TimeUnit.MILLISECONDS);
                    } else {
                        MSpringProxyHelper.scheduledThreadPoolExecutor.scheduleAtFixedRate(scheduler, annotation.initialDelayMillis(), annotation.delayMillis(), TimeUnit.MILLISECONDS);
                    }
                }
                // 代理池初始化
                com.jiashunx.app.masker.proxypool.core.util.MProxyInitializer.init();
                initialized = true;
            }
        }

    }

    /**
     * @return boolean
     */
    public static synchronized boolean isInitialized() {
        return initialized;
    }

}
