package com.jiashunx.app.masker.proxypool.demo;

import com.jiashunx.app.masker.proxypool.core.model.MProxy;
import com.jiashunx.app.masker.proxypool.core.type.MProxyType;
import com.jiashunx.app.masker.proxypool.core.util.MProxyPoolHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiashunx
 * @date 2020/09/24
 */
@RestController
public class MaskerProxyController {

    @GetMapping("/proxy/http")
    public MProxy getHttpProxy() {
        return MProxyPoolHolder.nextProxy(MProxyType.HTTP);
    }

    @GetMapping("/proxy/https")
    public MProxy getHttpsProxy() {
        return MProxyPoolHolder.nextProxy(MProxyType.HTTPS);
    }

}