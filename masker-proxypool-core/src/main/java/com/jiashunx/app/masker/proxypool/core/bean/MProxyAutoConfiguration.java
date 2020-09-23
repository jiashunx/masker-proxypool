package com.jiashunx.app.masker.proxypool.core.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author jiashunx
 * @date 2020/09/23
 */
@Configuration
@ComponentScan("com.jiashunx.app.masker.proxypool.core")
public class MProxyAutoConfiguration implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        if (applicationContext.getParent() == null) {
            applicationContext.getBean(MProxyInitializer.class).init();
        }
    }
}
