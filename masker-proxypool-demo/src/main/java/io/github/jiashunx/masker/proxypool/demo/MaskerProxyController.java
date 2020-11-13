package io.github.jiashunx.masker.proxypool.demo;

import io.github.jiashunx.masker.proxypool.core.model.MProxy;
import io.github.jiashunx.masker.proxypool.core.type.MProxyType;
import io.github.jiashunx.masker.proxypool.core.utils.MProxyPoolHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiashunx
 */
@RestController
public class MaskerProxyController {

    /**
     * @return MProxy
     */
    @GetMapping("/proxy/http")
    public MProxy getHttpProxy() {
        return MProxyPoolHolder.nextProxy(MProxyType.HTTP);
    }

    /**
     * @return MProxy
     */
    @GetMapping("/proxy/https")
    public MProxy getHttpsProxy() {
        return MProxyPoolHolder.nextProxy(MProxyType.HTTPS);
    }

}
