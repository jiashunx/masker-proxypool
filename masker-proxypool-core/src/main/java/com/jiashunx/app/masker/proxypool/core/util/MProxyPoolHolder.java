package com.jiashunx.app.masker.proxypool.core.util;

import com.jiashunx.app.masker.proxypool.core.model.MProxy;
import com.jiashunx.app.masker.proxypool.core.model.MProxyPool;
import com.jiashunx.app.masker.proxypool.core.type.MProxyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 代理池持有对象
 * @author jiashunx
 * @date 2020/09/24
 */
public class MProxyPoolHolder {

    private static final Logger logger = LoggerFactory.getLogger(MProxyPoolHolder.class);

    private static final MProxyPoolHolder INSTANCE = new MProxyPoolHolder();

    private Map<MProxyType, MProxyPool> poolMap = new ConcurrentHashMap<>();

    private MProxyPoolHolder() {
        MProxyType[] proxyTypes = MProxyType.values();
        for (MProxyType proxyType: proxyTypes) {
            poolMap.put(proxyType, new MProxyPool(proxyType));
        }
    }

    /**
     * 获取指定类型代理池.
     */
    public static MProxyPool getProxyPool(MProxyType proxyType) {
        if (proxyType == null) {
            throw new NullPointerException();
        }
        return INSTANCE.poolMap.get(proxyType);
    }

    /**
     * 更新指定类型代理池.
     */
    public static void setProxyPool(MProxyType proxyType, MProxyPool proxyPool) {
        if (proxyType == null || proxyPool == null) {
            throw new NullPointerException();
        }
        if (proxyType != proxyPool.getProxyType()) {
            throw new IllegalArgumentException();
        }
        INSTANCE.poolMap.put(proxyType, proxyPool);
        if (logger.isInfoEnabled()) {
            logger.info("refresh proxy pool, proxyType: {}, pool size: {}", proxyType, proxyPool.getPoolSize());
        }
    }

    /**
     * 获取指定类型代理.
     */
    public static MProxy nextProxy(MProxyType proxyType) {
        return getProxyPool(proxyType).nextProxy();
    }

}