package io.github.jiashunx.masker.proxypool.core;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;

/**
 * jxl单元测试
 * @author jiashunx
 * @date 2020/09/24
 */
public class JXLTest {

    @Test
    public void test() throws Exception {
        InputStream inputStream = JXLTest.class.getResourceAsStream("/jxl.html");
        byte[] bytes = new byte[inputStream.available()];
        IOUtils.read(inputStream, bytes);
        String content = new String(bytes);
        Document doc = Jsoup.parse(content);
        Elements tbodys = doc.select("body > div.layui-layout > div.layui-row > div.ip-tables > div.layui-form > table > tbody");
        Assert.assertFalse(tbodys.isEmpty());
        Element tbody = tbodys.get(0);
        Elements trs = tbody.children();
        Assert.assertFalse(trs.isEmpty());
        for (Element tr: trs) {
            Elements tds = tr.children();
            Assert.assertEquals(11, tds.size());
        }
    }

}
