package com.jiashunx.app.masker.proxypool.core.util;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * 封装获取CloseableHttpClient方法.<br>
 * masker-proxypool的连接应用场景并不适合使用http连接池(每个代理请求都是不同的)<br>
 * 而CloseableHttpClient对象close时同时需要释放其连接池管理器.<br>
 * 因此自定义MaskerHttpClient来进行统一的http连接释放.
 * @author jiashunx
 * @date 2020/09/24
 */
public class MHttpClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(MHttpClientUtil.class);

    private static SSLContext sslContext = null;
    static {
        try {
            sslContext = SSLContext.getDefault();
        } catch (NoSuchAlgorithmException e) {
            if (logger.isErrorEnabled()) {
                logger.error("创建SSL连接失败", e);
            }
        }
    }

    public static MaskerHttpClient createMaskerHttpClient() {
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", new SSLConnectionSocketFactory(sslContext))
                .register("http", new PlainConnectionSocketFactory())
                .build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connManager)
                .setConnectionManagerShared(false)
                .setMaxConnTotal(1)
                .build();
        return new MaskerHttpClient(httpClient, connManager);
    }

    public static class MaskerHttpClient {
        private CloseableHttpClient httpClient;
        private HttpClientConnectionManager connManager;
        public MaskerHttpClient(CloseableHttpClient httpClient, HttpClientConnectionManager connManager) {
            this.httpClient = httpClient;
            this.connManager = connManager;
        }
        public CloseableHttpResponse execute(final HttpUriRequest request) throws IOException, ClientProtocolException {
            return httpClient.execute(request);
        }
        /**
         * 统一释放连接及对应连接池管理器.
         */
        public void release() {
            IOUtils.closeQuietly(httpClient);
            connManager.shutdown();
        }
    }

}
