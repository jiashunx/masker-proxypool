### masker-proxypool

- masker-proxypool-core: 代理池核心包，引入之后即可调用API获取代理
- masker-proxypool-demo：代理池demo，依赖masker-proxypool-core，发布Rest接口获取代理

#### 项目直接作为基础组件集成到SpringBoot项目即可，代理采集自动扫描并启动

#### 如何引用：
```
项目pom.xml添加jiashunx/mvn-repo的repository配置
<repositories>
  <repository>
    <id>github</id>
    <name>GitHub jiashunx Apache Maven Packages</name>
    <url>https://maven.pkg.github.com/jiashunx/mvn-repo</url>
  </repository>
</repositories>
项目pom.xml文件添加依赖
<dependency>
  <groupId>com.jiashunx.app</groupId>
  <artifactId>masker-proxypool-core</artifactId>
  <version>1.0.0</version>
</dependency> 
```

#### 如何调用：
```
com.jiashunx.app.masker.proxypool.core.util.MProxyPoolHolder.nextProxy(com.jiashunx.app.masker.proxypool.core.type.MProxyType)
```
