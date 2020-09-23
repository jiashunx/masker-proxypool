package com.jiashunx.app.masker.proxypool.core.collector.impl;

import com.jiashunx.app.masker.proxypool.core.collector.AbstractMProxyCollector;
import com.jiashunx.app.masker.proxypool.core.type.MProxySourceType;
import org.springframework.stereotype.Component;

/**
 * @author jiashunx
 * @date 2020/09/16
 */
@Component
public class JXLProxyCollector extends AbstractMProxyCollector {

    /**
     * 获取代理数据源类型.
     */
    @Override
    public MProxySourceType getProxySourceType() {
        return MProxySourceType.JXL;
    }
}
