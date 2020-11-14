### masker-proxypool

   - 项目简介：代理池API

   - 工程介绍：

      - masker-proxypool-core：代理池API核心包，引入到工程之后手工启动代理池即可调用相应API获取代理配置
      - masker-proxypool-starter：作为spring boot starter依赖加入到spring boot项目，添加依赖即可使用，无需手工启动代理池
      - masker-proxypool-demo：代理池demo（spring boot实现），依赖masker-proxypool-starter，发布rest接口获取代理

      ```text
      GET /proxy/http    获取http代理
      GET /proxy/https   获取https代理
      ```
   
   - 环境依赖：

      - JDK8+
   
   - 部署步骤：

      - 方式一：引入masker-proxypool-core依赖，然后手工启动代理池，然后调用相应API获取代理

      ```text
      1、引入依赖：
      <dependency>
        <groupId>io.github.jiashunx</groupId>
        <artifactId>masker-proxypool-core</artifactId>
        <version>1.2.2</version>
      </dependency>
      2、手工启动代理池：
      io.github.jiashunx.masker.proxypool.core.utils.MProxyInitializer.init();
      3、调用API获取代理：
      io.github.jiashunx.masker.proxypool.core.utils.MProxyPoolHolder.nextHttpProxy()
      ```

      - 方式二：spring boot工程引入masker-proxypool-starter依赖，然后调用相应API获取代理

      ```text
      1、引入依赖：
      <dependency>
        <groupId>io.github.jiashunx</groupId>
        <artifactId>masker-proxypool-starter</artifactId>
        <version>1.2.2</version>
      </dependency>
      2、调用API获取代理：
      io.github.jiashunx.masker.proxypool.core.utils.MProxyPoolHolder.nextHttpProxy()
      ```
