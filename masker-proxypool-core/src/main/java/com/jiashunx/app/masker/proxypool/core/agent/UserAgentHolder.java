package com.jiashunx.app.masker.proxypool.core.agent;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * User-Agent持有对象.
 * @author jiashunx
 * @date 2020/09/15
 */
public class UserAgentHolder {

    private static final Logger logger = LoggerFactory.getLogger(UserAgentHolder.class);

    private static List<String> userAgentList = null;
    private static int userAgentSize = 0;
    private static final AtomicLong COUNTER = new AtomicLong(0);

    static {
        String fileName = "UserAgent.txt";
        InputStream in = null;
        BufferedReader reader = null;
        try {
            in = UserAgentHolder.class.getClassLoader().getResourceAsStream(fileName);
            reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            Set<String> set = new HashSet<>();
            while ((line = reader.readLine()) != null) {
                set.add(line);
            }
            userAgentList = new ArrayList<>(set);
            userAgentSize = userAgentList.size();
            if (logger.isInfoEnabled()) {
                logger.info("加载classpath {}文件获取User-Agent配置成功", fileName);
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("加载classpath {}文件获取User-Agent配置失败", fileName, e);
            }
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(reader);
        }
    }

    private UserAgentHolder() {}

    /**
     * 获取下一个User-Agent配置.
     * @return 可用的User-Agent配置
     */
    public static String nextUserAgent() {
        long number = COUNTER.incrementAndGet();
        // 取模
        int index = (int) (number % userAgentSize);
        String userAgent = userAgentList.get(index);
        if (logger.isDebugEnabled()) {
            logger.debug("Load Agent: index={}, agent={}", index, userAgent);
        }
        return userAgent;
    }

}
