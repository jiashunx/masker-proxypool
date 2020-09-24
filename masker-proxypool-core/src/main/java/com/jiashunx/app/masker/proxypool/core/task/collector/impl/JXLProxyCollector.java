package com.jiashunx.app.masker.proxypool.core.task.collector.impl;

import com.jiashunx.app.masker.proxypool.core.exception.MProxyCollectException;
import com.jiashunx.app.masker.proxypool.core.model.MProxy;
import com.jiashunx.app.masker.proxypool.core.task.collector.AbstractMProxyCollector;
import com.jiashunx.app.masker.proxypool.core.type.MProxySourceType;
import com.jiashunx.app.masker.proxypool.core.type.MProxyType;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author jiashunx
 * @date 2020/09/16
 */
@Component
public class JXLProxyCollector extends AbstractMProxyCollector {

    private static final Logger logger = LoggerFactory.getLogger(JXLProxyCollector.class);

    private static final String BASE_URL = "http://ip.jiangxianli.com/?page=";

    @Override
    protected List<MProxy> get() throws MProxyCollectException {
        List<MProxy> proxyList = new ArrayList<>();
        for (int index = 1; index < 5; index++) {
            String url = BASE_URL + index;
            Optional.ofNullable(getDocument(url, null)).ifPresent(doc -> {
                try {
                    Elements tbodys = doc.select("body > div.layui-layout > div.layui-row > div.ip-tables > div.layui-form > table > tbody");
                    if (tbodys.isEmpty()) {
                        return;
                    }
                    Element tbody = tbodys.get(0);
                    Elements trs = tbody.children();
                    if (trs.isEmpty()) {
                        return;
                    }
                    proxyList.addAll(getProxies(trs));
                } catch (Exception e) {
                    if (logger.isErrorEnabled()) {
                        logger.error("parse proxy failed", e);
                    }
                }
            });
        }
        return proxyList;
    }

    private List<MProxy> getProxies(Elements trs) {
        List<MProxy> list = new LinkedList<>();
        for (Element tr: trs) {
            Elements tds = tr.children();
            if (tds.size() != 11) {
                continue;
            }
            String host = tds.get(0).childNode(0).toString();
            int port = Integer.parseInt(tds.get(1).childNode(0).toString());
            MProxyType proxyType = MProxyType.valueOf(tds.get(3).childNode(0).toString().toUpperCase());
            list.add(new MProxy(getProxySourceType(), proxyType, host, port));
        }
        return list;
    }

    /**
     * 获取代理数据源类型.
     */
    @Override
    public MProxySourceType getProxySourceType() {
        return MProxySourceType.JXL;
    }
}
