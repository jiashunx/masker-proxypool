package com.jiashunx.app.masker.proxypool.core.annotation;

import java.lang.annotation.*;

/**
 * @author jiashunx
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MScheduler {

    /**
     * 是否开启调度.
     * @return boolean
     */
    boolean enabled() default true;

    /**
     * 首次执行延时.
     * @return long
     */
    long initialDelayMillis() default 10*1000L;

    /**
     * 调度间隔.
     * @return long
     */
    long delayMillis() default 60*1000L;

    /**
     * 固定延时(如: 此次执行完成后一分钟再执行).
     * @return boolean
     */
    boolean fixedDelay() default true;

    /**
     * 固定延时(如: 每隔一分钟执行).
     * @return boolean
     */
    boolean fixedRate() default false;

}
