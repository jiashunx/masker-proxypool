package io.github.jiashunx.masker.proxypool.starter.scheduler;

import io.github.jiashunx.masker.proxypool.core.exception.MProxyScheduleException;
import io.github.jiashunx.masker.proxypool.core.task.collector.AbstractMProxyCollector;
import io.github.jiashunx.masker.proxypool.core.task.scheduler.impl.MProxyDefaultScheduler;
import io.github.jiashunx.masker.proxypool.core.annotation.MScheduler;
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
