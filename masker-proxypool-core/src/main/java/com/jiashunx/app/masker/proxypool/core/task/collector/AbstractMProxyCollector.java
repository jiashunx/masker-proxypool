package com.jiashunx.app.masker.proxypool.core.task.collector;

import com.jiashunx.app.masker.proxypool.core.agent.MUserAgentHolder;
import com.jiashunx.app.masker.proxypool.core.bean.MProxyInitializer;
import com.jiashunx.app.masker.proxypool.core.exception.MProxyCollectException;
import com.jiashunx.app.masker.proxypool.core.exception.MProxyException;
import com.jiashunx.app.masker.proxypool.core.exception.MProxyScheduleException;
import com.jiashunx.app.masker.proxypool.core.model.MProxy;
import com.jiashunx.app.masker.proxypool.core.util.MProxyPoolHolder;
import com.jiashunx.app.masker.proxypool.core.task.IMProxyCollector;
import com.jiashunx.app.masker.proxypool.core.type.MProxySourceType;
import com.jiashunx.app.masker.proxypool.core.type.MProxyType;
import com.jiashunx.app.masker.proxypool.core.util.HttpClientUtil;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * 代理采集抽象类.
 * @author jiashunx
 * @date 2020/09/16
 */
public abstract class AbstractMProxyCollector implements IMProxyCollector<List<MProxy>> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractMProxyCollector.class);

    /**
     * 采集失败次数.
     */
    private static final ThreadLocal<Integer> proxyFailures = ThreadLocal.withInitial(() -> 0);

    /**
     * 各线程HttpGet对象复用, 减少GC.
     */
    private static ThreadLocal<HttpGet> localHttpGet = ThreadLocal.withInitial(() -> new HttpGet());

    /**
     * 代理采集返回结果.
     * @return
     * @throws MProxyException
     */
    protected abstract List<MProxy> get() throws MProxyCollectException;

    @Override
    public List<MProxy> call() {
        MProxySourceType sourceType = getProxySourceType();
        if (logger.isInfoEnabled()) {
            logger.info("proxy collector task start, proxySourceType: {}", sourceType);
        }
        List<MProxy> proxyList = null;
        try {
            proxyList = get();
            if (logger.isInfoEnabled()) {
                logger.info("proxy collector task finish, proxySourceType: {}", sourceType);
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("proxy collector task failed, proxySourceType: {}", sourceType, e);
            }
        }
        if (proxyList == null) {
            proxyList = new ArrayList<>(0);
        }
        return proxyList;
    }

    /**
     * 获取url请求数据并使用Jsoup解析.
     */
    protected Document getDocument(String url, String referrer) throws MProxyCollectException {
        proxyFailures.set(0);
        for (int retry = 3, failure = proxyFailures.get(); failure < retry;) {
            MProxyType proxyType = null;
            try {
                proxyType = url.indexOf("https") == 0 ? MProxyType.HTTPS : MProxyType.HTTP;
                return get(url, referrer, proxyType);
            } catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error("load proxy failed, error reason: {}", e.getMessage());
                }
            } finally {
                proxyFailures.set(++failure);
                if (failure >= retry) {
                    if (logger.isWarnEnabled()) {
                        logger.warn("load proxy failed for {} times, retry and use no proxy", failure);
                    }
                }
            }
        }
        Document document = null;
        try {
            document = get(url, referrer, null);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("load proxy failed", e);
            }
        }
        return document;
    }

    private Document get(String url, String referrer, MProxyType proxyType) throws MProxyCollectException {
        if (StringUtils.isEmpty(referrer)) {
            referrer = url;
        }
        String userAgent = (String) MUserAgentHolder.nextUserAgent();
        HttpClientUtil.MaskerHttpClient httpClient = HttpClientUtil.createMaskerHttpClient();
        HttpGet httpGet = localHttpGet.get();
        // 释放连接
        httpGet.releaseConnection();
        if (!StringUtils.isEmpty(userAgent)) {
            httpGet.setHeader("User-Agent", userAgent);
        }
        RequestConfig.Builder configBuilder = RequestConfig.custom().setConnectionRequestTimeout(3000).setSocketTimeout(3000);
        MProxy proxy = null;
        if (proxyType != null) {
            // 找到可用代理, 使用可用代理进行请求.
            proxy = MProxyPoolHolder.nextProxy(proxyType);
            if (proxy != null) {
                configBuilder.setProxy(new HttpHost(proxy.getIp(), proxy.getPort()));
            }
        }
        if (logger.isInfoEnabled()) {
            logger.info("url: {}, referrer url: {}, use proxy: {}", url, referrer, proxy);
        }
        CloseableHttpResponse response = null;
        try {
            httpGet.setConfig(configBuilder.build());
            httpGet.setURI(new URI(url));
            response = httpClient.execute(httpGet);
            return Jsoup.parse(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            throw new MProxyCollectException(e);
        } finally {
            IOUtils.closeQuietly(response);
            httpClient.release();
        }
    }

}
