package io.github.jiashunx.masker.proxypool.starter;

import io.github.jiashunx.masker.proxypool.core.annotation.MScheduler;
import io.github.jiashunx.masker.proxypool.core.exception.MProxyInitializeException;
import io.github.jiashunx.masker.proxypool.core.task.IMProxyScheduler;
import io.github.jiashunx.masker.proxypool.core.utils.MDefaultThreadFactory;
import io.github.jiashunx.masker.proxypool.starter.util.MSpringProxyHelper;
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
                io.github.jiashunx.masker.proxypool.core.utils.MProxyInitializer.init();
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
