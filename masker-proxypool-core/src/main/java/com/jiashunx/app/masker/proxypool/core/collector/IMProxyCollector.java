package com.jiashunx.app.masker.proxypool.core.collector;

import com.jiashunx.app.masker.proxypool.core.type.MProxySourceType;

/**
 * 代理采集器接口.
 * @author jiashunx
 * @date 2020/09/16
 */
public interface IMProxyCollector {

    /**
     * 注册采集器.
     */
    void register();

    /**
     * 获取代理数据源类型.
     */
    MProxySourceType getProxySourceType();

}
