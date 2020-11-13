package com.jiashunx.app.masker.proxypool.starter.scheduler;

import com.jiashunx.app.masker.proxypool.core.exception.MProxyScheduleException;
import com.jiashunx.app.masker.proxypool.core.task.collector.AbstractMProxyCollector;
import com.jiashunx.app.masker.proxypool.core.task.scheduler.impl.MProxyDefaultScheduler;
import com.jiashunx.app.masker.proxypool.core.annotation.MScheduler;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author jiashunx
 */
@MScheduler
public class MProxySpringDefaultScheduler extends MProxyDefaultScheduler {

    @Resource
    private ApplicationContext applicationContext;

    @Override
    protected Map<String, AbstractMProxyCollector> getBeansOfMProxyCollector() {
        return applicationContext.getBeansOfType(AbstractMProxyCollector.class);
    }

    @Override
    protected void execute() throws MProxyScheduleException, ExecutionException, InterruptedException {
//        super.execute();
        // do nothing.
    }

    @Override
    public String getProxyScheduleType() {
        return "proxy-spring-default-scheduler";
    }

}
