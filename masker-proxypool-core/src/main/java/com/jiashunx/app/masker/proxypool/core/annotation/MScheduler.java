package com.jiashunx.app.masker.proxypool.core.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author jiashunx
 * @date 2020/09/24
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Component
public @interface MScheduler {

    /**
     * 是否开启调度.
     */
    boolean enabled() default true;

    /**
     * 首次执行延时.
     */
    long initialDelayMillis() default 10*1000L;

    /**
     * 调度间隔.
     */
    long delayMillis() default 60*1000L;

    /**
     * 固定延时(如: 此次执行完成后一分钟再执行).
     */
    boolean fixedDelay() default true;

    /**
     * 固定延时(如: 每隔一分钟执行).
     */
    boolean fixedRate() default false;

}
