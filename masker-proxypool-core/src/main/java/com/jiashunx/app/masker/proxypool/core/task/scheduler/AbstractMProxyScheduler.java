package com.jiashunx.app.masker.proxypool.core.task.scheduler;

import com.jiashunx.app.masker.proxypool.core.bean.MProxyInitializer;
import com.jiashunx.app.masker.proxypool.core.exception.MProxyScheduleException;
import com.jiashunx.app.masker.proxypool.core.task.IMProxyScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 调度抽象类.
 * @author jiashunx
 * @date 2020/09/24
 */
public abstract class AbstractMProxyScheduler implements IMProxyScheduler {

    private static final Logger logger = LoggerFactory.getLogger(AbstractMProxyScheduler.class);

    @Override
    public void run() {
        if (!MProxyInitializer.isInitialized()) {
            throw new MProxyScheduleException("masker-proxypool doesn't initialized.");
        }
        String scheduleType = getProxyScheduleType();
        if (logger.isInfoEnabled()) {
            logger.info("proxy scheduler start, scheduleType: {}", scheduleType);
        }
        try {
            execute();
        } catch (Throwable e) {
            if (logger.isErrorEnabled()) {
                logger.error("proxy scheduler execute failed, scheduleType: {}", scheduleType, e);
            }
        }
    }

    /**
     * 调度执行逻辑.
     * @throws MProxyScheduleException
     */
    protected abstract void execute() throws MProxyScheduleException;

}
