# spring-boot-native-samples
## Preview

| Feature                                | Executable Size | Memory Size | Startup Time |
|----------------------------------------|-----------------|-------------|--------------|
| spring-boot-native-samples-web         | 85MB            | 27MB        | 0.123        |
| spring-boot-native-samples-jpa-jdbc    | 99MB            | 44MB        | 0.599        |
| spring-boot-native-samples-httpclient5 | 85MB            | 27MB        | 0.201        |



## Package
```
mvn clean -DskipTests=true -Pnative native:compile
```

运行时参数指定：
```
./xxx -Xms50m -Xmx50m -Xmn50m
```
## spring-boot-native-samples-web
Basic Spring Boot Web


## Summary
当前Java开发者众多，但是由于Java服务一直面向于长期运行，启动速度慢，占用内存大，每个Pod或者虚拟机都需要安装JDK, 不太适合云原生时代。
GraalVM是Oracle Lab出品，其目标就是可以编译成本地运行服务，性能高，启动速度快，但是编译成本地服务要求对反射比较高，需要告知GraalVM哪些类或方法需要反射。
我整理了当前Web开发中常用的组件适合Native项目，大家可以借鉴，说的直白一些，当前熟悉Java开发者可以跟得上云原生时代语言特性

GraalVM已经支持的组件或者框架：
https://www.graalvm.org/native-image/libraries-and-frameworks/

我的目标是常用的开发组件：
- web
- jdbc-driver
- jdbc connection pool
- redis
- kafka
- elastic-search-client
- http client
- rpc(thrift, dubbo)
- config

我理想中的架构模型，apisix作为网关，提供限流（Service, Endpoint, URL, QueryString, http header, uid ...），认证鉴权(auth, authz)，
协议转换(http->dubbo, http->thrift)，路由，降级，服务编排等特性。

每个具体的服务中上述的网关特性可以不要重复集成，每个服务流量大可以由kubernetes快速弹性扩缩容。

每个服务有一个普通jar包实例来作为对jvm监控，链路追踪等，编译为native的作为快速弹性扩缩容的副本，要足够轻量。

虽然对于公有云服务来说，内存是及其廉价的，昂贵的是计算资源，但是如果服务能在秒级快速扩缩容，在很大程度上节约大量资源。

上述的组件显然不能满足企业级使用，但是常见的微服务能满足80%场景，有的是老服务无法升级，或者使用了大量的第三方包组件等，可以把这些服务提出出一个大杂烩项目，由这个大杂烩项目运行无法native的组件。

我个人Spring Cloud体系









