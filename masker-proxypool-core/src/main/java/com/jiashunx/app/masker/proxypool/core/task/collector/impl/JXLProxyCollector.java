package com.jiashunx.app.masker.proxypool.core.task.collector.impl;

import com.jiashunx.app.masker.proxypool.core.exception.MProxyCollectException;
import com.jiashunx.app.masker.proxypool.core.model.MProxy;
import com.jiashunx.app.masker.proxypool.core.task.collector.AbstractMProxyCollector;
import com.jiashunx.app.masker.proxypool.core.type.MProxySourceType;
import com.jiashunx.app.masker.proxypool.core.type.MProxyType;
import com.jiashunx.app.masker.proxypool.core.util.MProxyHelper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author jiashunx
 */
@Component
public class JXLProxyCollector extends AbstractMProxyCollector {

    private static final Logger logger = LoggerFactory.getLogger(JXLProxyCollector.class);

    private static final String BASE_URL = "http://ip.jiangxianli.com/?page=";

    private static final String ELEMENT_LOCATOR = "body > div.layui-layout > div.layui-row > div.ip-tables > div.layui-form > table > tbody";

    private static class Collector implements Callable<List<MProxy>> {
        private String url;
        private Collector(String url) {
            this.url = url;
        }
        @Override
        public List<MProxy> call() {
            List<MProxy> proxyList = new LinkedList<>();
            try {
                Document document = getDocument(url, null, doc -> {
                    return doc != null && !doc.select(ELEMENT_LOCATOR).isEmpty();
                });
                if (document != null) {
                    Elements tbodys = document.select(ELEMENT_LOCATOR);
                    if (!tbodys.isEmpty()) {
                        Element tbody = tbodys.get(0);
                        Elements trs = tbody.children();
                        if (!trs.isEmpty()) {
                            for (Element tr: trs) {
                                Elements tds = tr.children();
                                if (tds.size() != 11) {
                                    continue;
                                }
                                String host = tds.get(0).childNode(0).toString();
                                int port = Integer.parseInt(tds.get(1).childNode(0).toString());
                                MProxyType proxyType = MProxyType.valueOf(tds.get(3).childNode(0).toString().toUpperCase());
                                proxyList.add(new MProxy(null, proxyType, host, port));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error("parse proxy failed", e);
                }
            }
            if (logger.isInfoEnabled()) {
                logger.info("load proxy from url: {}, count: {}", url, proxyList.size());
            }
            return proxyList;
        }
    }

    @Override
    protected List<MProxy> get() throws MProxyCollectException, ExecutionException, InterruptedException {
        List<MProxy> proxyList = new ArrayList<>();
        List<Future<List<MProxy>>> futures = new ArrayList<>();
        for (int index = 1; index < 10; index++) {
            String url = BASE_URL + index;
            futures.add(MProxyHelper.threadPoolExecutor.submit(new Collector(url)));
        }
        for (Future<List<MProxy>> future: futures) {
            proxyList.addAll(future.get());
        }
        proxyList.forEach(proxy -> {
            proxy.setSourceType(getProxySourceType());
        });
        return proxyList;
    }

    /**
     * 获取代理数据源类型.
     */
    @Override
    public MProxySourceType getProxySourceType() {
        return MProxySourceType.JXL;
    }
}
