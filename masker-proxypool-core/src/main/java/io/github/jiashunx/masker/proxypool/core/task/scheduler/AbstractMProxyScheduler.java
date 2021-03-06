package io.github.jiashunx.masker.proxypool.core.task.scheduler;

import io.github.jiashunx.masker.proxypool.core.exception.MProxyScheduleException;
import io.github.jiashunx.masker.proxypool.core.task.IMProxyScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

/**
 * 调度抽象类.
 * @author jiashunx
 */
public abstract class AbstractMProxyScheduler implements IMProxyScheduler {

    private static final Logger logger = LoggerFactory.getLogger(AbstractMProxyScheduler.class);

    @Override
    public void run() {
        String scheduleType = getProxyScheduleType();
        if (logger.isInfoEnabled()) {
            logger.info("proxy scheduler task start, scheduleType: {}", scheduleType);
        }
        try {
            execute();
            if (logger.isInfoEnabled()) {
                logger.info("proxy scheduler task finish, scheduleType: {}", scheduleType);
            }
        } catch (Throwable e) {
            if (logger.isErrorEnabled()) {
                logger.error("proxy scheduler task execute failed, scheduleType: {}", scheduleType, e);
            }
        }
    }

    /**
     * 调度执行逻辑.
     * @throws MProxyScheduleException MProxyScheduleException
     * @throws ExecutionException ExecutionException
     * @throws InterruptedException InterruptedException
     */
    protected abstract void execute() throws MProxyScheduleException, ExecutionException, InterruptedException;

}
