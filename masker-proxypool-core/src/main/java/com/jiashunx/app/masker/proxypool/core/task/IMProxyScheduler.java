package com.jiashunx.app.masker.proxypool.core.task;

/**
 * 代理调度接口.
 * @author jiashunx
 */
public interface IMProxyScheduler extends Runnable {

    /**
     * 获取调度类型.
     * @return String
     */
    String getProxyScheduleType();

}
