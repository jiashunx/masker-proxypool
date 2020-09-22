package com.jiashunx.app.masker.proxypool.core.util;

import com.jiashunx.app.masker.proxypool.core.collector.IProxyCollector;
import com.jiashunx.app.masker.proxypool.core.type.MProxySourceType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiashunx
 * @date 2020/09/20
 */
public class MHelper {

    public static final Map<MProxySourceType, IProxyCollector> COLLECTOR_MAP = new HashMap<>();

}
