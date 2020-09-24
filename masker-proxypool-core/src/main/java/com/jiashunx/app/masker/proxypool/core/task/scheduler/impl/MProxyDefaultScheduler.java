package com.jiashunx.app.masker.proxypool.core.task.scheduler.impl;

import com.jiashunx.app.masker.proxypool.core.annotation.Scheduler;
import com.jiashunx.app.masker.proxypool.core.exception.MProxyScheduleException;
import com.jiashunx.app.masker.proxypool.core.task.IMProxyCollector;
import com.jiashunx.app.masker.proxypool.core.task.scheduler.AbstractMProxyScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author jiashunx
 * @date 2020/09/24
 */
@Scheduler
public class MProxyDefaultScheduler extends AbstractMProxyScheduler {

    @Resource
    private ApplicationContext applicationContext;

    @Override
    protected void execute() throws MProxyScheduleException {
        Map<String, IMProxyCollector> collectorMap = applicationContext.getBeansOfType(IMProxyCollector.class);
    }

    @Override
    public String getProxyScheduleType() {
        return "proxy-default-scheduler";
    }
}
