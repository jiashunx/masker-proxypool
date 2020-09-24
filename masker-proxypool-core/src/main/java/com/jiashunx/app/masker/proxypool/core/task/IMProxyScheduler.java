package com.jiashunx.app.masker.proxypool.core.task;

/**
 * 代理调度接口.
 * @author jiashunx
 * @date 2020/09/24
 */
public interface IMProxyScheduler extends Runnable {

    /**
     * 获取调度类型.
     */
    String getProxyScheduleType();

}
