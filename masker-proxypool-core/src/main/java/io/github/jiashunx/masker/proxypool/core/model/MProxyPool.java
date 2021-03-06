package io.github.jiashunx.masker.proxypool.core.model;

import io.github.jiashunx.masker.proxypool.core.type.MProxyType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 代理池(代理池对象创建时固定).
 * @author jiashunx
 */
public class MProxyPool {

    /**
     * 代理池中代理类型.
     */
    private MProxyType proxyType;

    /**
     * 代理列表.
     */
    private List<MProxy> proxyList;

    /**
     * 计数器.
     */
    private AtomicLong counter;
    /**
     * 代理池大小.
     */
    private int poolSize;

    public MProxyPool(MProxyType proxyType) {
        this(proxyType, new ArrayList<>());
    }

    public MProxyPool(MProxyType proxyType, List<MProxy> proxyList) {
        this.proxyType = Objects.requireNonNull(proxyType);
        this.proxyList = Objects.requireNonNull(proxyList);
        this.counter = new AtomicLong(0);
        this.poolSize = proxyList.size();
    }

    /**
     * 获取代理.
     * @return MProxy
     */
    public MProxy nextProxy() {
        if (poolSize <= 0) {
            return null;
        }
        long number = counter.incrementAndGet();
        int index = (int) (number % poolSize);
        return proxyList.get(index);
    }

    /**
     * @return MProxyType
     */
    public MProxyType getProxyType() {
        return proxyType;
    }

    /**
     * @return int
     */
    public int getPoolSize() {
        return poolSize;
    }
}
