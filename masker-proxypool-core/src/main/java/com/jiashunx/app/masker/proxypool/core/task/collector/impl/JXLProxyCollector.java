package com.jiashunx.app.masker.proxypool.core.task.collector.impl;

import com.jiashunx.app.masker.proxypool.core.task.collector.AbstractMProxyCollector;
import com.jiashunx.app.masker.proxypool.core.type.MProxySourceType;
import org.springframework.stereotype.Component;

/**
 * @author jiashunx
 * @date 2020/09/16
 */
@Component
public class JXLProxyCollector<String> extends AbstractMProxyCollector<String> {

    @Override
    protected String get() {
        return null;
    }

    /**
     * 获取代理数据源类型.
     */
    @Override
    public MProxySourceType getProxySourceType() {
        return MProxySourceType.JXL;
    }
}
