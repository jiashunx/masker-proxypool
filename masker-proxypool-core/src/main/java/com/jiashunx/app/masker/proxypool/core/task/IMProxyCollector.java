package com.jiashunx.app.masker.proxypool.core.task;

import com.jiashunx.app.masker.proxypool.core.type.MProxySourceType;

import java.util.concurrent.Callable;

/**
 * 代理采集器接口.
 * @author jiashunx
 */
public interface IMProxyCollector<T> extends Callable<T> {

    /**
     * 获取代理数据源类型.
     * @return MProxySourceType
     */
    MProxySourceType getProxySourceType();

}
