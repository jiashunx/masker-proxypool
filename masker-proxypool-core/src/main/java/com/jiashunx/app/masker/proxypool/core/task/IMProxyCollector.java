package com.jiashunx.app.masker.proxypool.core.task;

import com.jiashunx.app.masker.proxypool.core.type.MProxySourceType;

import java.util.concurrent.Callable;

/**
 * 代理采集器接口.
 * @author jiashunx
 * @date 2020/09/16
 */
public interface IMProxyCollector<T> extends Callable<T> {

    /**
     * 获取代理数据源类型.
     */
    MProxySourceType getProxySourceType();

}
