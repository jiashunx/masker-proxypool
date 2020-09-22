package com.jiashunx.app.masker.proxypool.demo;

import com.jiashunx.app.masker.proxypool.core.collector.IProxyCollector;
import com.jiashunx.app.masker.proxypool.core.collector.MProxyCollectScheduler;
import com.jiashunx.app.masker.proxypool.core.util.DefaultThreadFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 服务启动后的初始化.
 * @author jiashunx
 * @date 2020/09/15
 */
@Component
public class MaskerProxyPoolRunner implements ApplicationRunner {

    private ThreadPoolExecutor threadPoolExecutor = null;
    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = null;

    @Resource
    private ApplicationContext applicationContext;

    /**
     * Callback used to run the bean.
     *
     * @param args incoming application arguments
     * @throws Exception on error
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(applicationContext);
        Map<String, IProxyCollector> collectorMap = applicationContext.getBeansOfType(IProxyCollector.class);
        for (Map.Entry<String, IProxyCollector> entry: collectorMap.entrySet()) {
            IProxyCollector collector = entry.getValue();
            collector.register();
        }
        int poolSize = collectorMap.size() + 1;
        threadPoolExecutor = new ThreadPoolExecutor(1, 5, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1000), new DefaultThreadFactory());
        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, new DefaultThreadFactory());
        scheduledThreadPoolExecutor.scheduleWithFixedDelay(new MProxyCollectScheduler(), 10L, 60L, TimeUnit.SECONDS);
        // 创建采集线程池
        // 定时触发采集任务，多线程并发采集数据，然后合并采集结果。
        //
    }

}
