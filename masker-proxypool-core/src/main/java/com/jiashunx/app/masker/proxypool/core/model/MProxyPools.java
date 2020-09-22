package com.jiashunx.app.masker.proxypool.core.model;

import com.jiashunx.app.masker.proxypool.core.type.MProxyType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 代理池持有对象.
 * @author jiashunx
 * @date 2020/09/16
 */
public class MProxyPools {

    private Map<MProxyType, MProxyPool> poolMap = new HashMap<>();

    public MProxyPools() {
        MProxyType[] typeArr = MProxyType.values();
        for (MProxyType proxyType: typeArr) {
            poolMap.put(proxyType, new MProxyPool(proxyType));
        }
    }

    /**
     * 更新代理池.
     */
    public void updateProxyPool(MProxyPool proxyPool) {
        MProxyPool pool = Objects.requireNonNull(proxyPool);
        poolMap.put(pool.getProxyType(), pool);
    }

    /**
     * 获取指定类型代理池.
     */
    public MProxyPool getProxyPool(MProxyType proxyType) {
        return poolMap.get(Objects.requireNonNull(proxyType));
    }

}
