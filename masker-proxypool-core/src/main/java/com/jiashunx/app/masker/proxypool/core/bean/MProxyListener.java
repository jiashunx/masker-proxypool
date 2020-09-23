package com.jiashunx.app.masker.proxypool.core.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author jiashunx
 * @date 2020/09/23
 */
@Component
public class MProxyListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        if (applicationContext.getParent() == null) {
            applicationContext.getBean(MProxyInitializer.class).init();
        }
    }
}
