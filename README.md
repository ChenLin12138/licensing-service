# licensing-service
Spring Cloud Learning licensing-service
## What Is It?
Spring cloud components learning.
## Spring Cloud components
### Spring boot 2.1.4.RELEASE
### Actuator
## Download & Installation
1. Download a Java SE Runtime Environment (JRE),release version 8 or later, from http://www.oracle.com/technetwork/java/javase/downloads/index.html
2. Download Apache maven
Download Apache maven 3.6.0 here
http://maven.apache.org/download.cgi
3. Set Java Home or JRE Home.
4. Set Maven Home.
5. Import project as Existing maven projects.
6. Use mvn clean install to install this project.
## Demo
本例主要目的在于利用spring cloud的actuator组件对服务情况进行获取。

- 查看微服务开放了哪些url查询节点
http://localhost:8080/actuator/
```json
{"_links":{"self":{"href":"http://localhost:8080/actuator","templated":false},"auditevents":{"href":"http://localhost:8080/actuator/auditevents","templated":false},"beans":{"href":"http://localhost:8080/actuator/beans","templated":false},"caches-cache":{"href":"http://localhost:8080/actuator/caches/{cache}","templated":true},"caches":{"href":"http://localhost:8080/actuator/caches","templated":false},"health-component":{"href":"http://localhost:8080/actuator/health/{component}","templated":true},"health":{"href":"http://localhost:8080/actuator/health","templated":false},"health-component-instance":{"href":"http://localhost:8080/actuator/health/{component}/{instance}","templated":true},"conditions":{"href":"http://localhost:8080/actuator/conditions","templated":false},"configprops":{"href":"http://localhost:8080/actuator/configprops","templated":false},"env":{"href":"http://localhost:8080/actuator/env","templated":false},"env-toMatch":{"href":"http://localhost:8080/actuator/env/{toMatch}","templated":true},"info":{"href":"http://localhost:8080/actuator/info","templated":false},"loggers":{"href":"http://localhost:8080/actuator/loggers","templated":false},"loggers-name":{"href":"http://localhost:8080/actuator/loggers/{name}","templated":true},"heapdump":{"href":"http://localhost:8080/actuator/heapdump","templated":false},"threaddump":{"href":"http://localhost:8080/actuator/threaddump","templated":false},"metrics-requiredMetricName":{"href":"http://localhost:8080/actuator/metrics/{requiredMetricName}","templated":true},"metrics":{"href":"http://localhost:8080/actuator/metrics","templated":false},"scheduledtasks":{"href":"http://localhost:8080/actuator/scheduledtasks","templated":false},"httptrace":{"href":"http://localhost:8080/actuator/httptrace","templated":false},"mappings":{"href":"http://localhost:8080/actuator/mappings","templated":false}}}
```
- 查询health节点获取服务状态，磁盘空间
根据上面查询到开放了
http://localhost:8080/actuator/health
```json
{
    "licenseId": "728f9a66-e29f-4f83-9891-7e6b98a298d9",
    "organizationId": "1",
    "productName": "Nokia",
    "licenseType": "Phone",
    "licenseMax": 12,
    "licenseAllocated": 1,
    "comment": "exampleProperty"
}
```
- POST请求
POST一个json Body类型为JSON(Application/json)
{
    "organizationId": "1",
    "productName": "Nokia",
    "licenseType": "Phone",
    "licenseMax": 12,
    "licenseAllocated": 1,
    "comment": "comment example"
}
![PostMan Post 范例](https://github.com/ChenLin12138/licensing-service/blob/master/demo/pic/LicensePostDemo.png)

结果会在数据库licenses表中发现这条数据


- Get请求
localhost:8080/v1/organizatons/1/licenses/728f9a66-e29f-4f83-9891-7e6b98a298d9
查询结果
```json
{
    "licenseId": "728f9a66-e29f-4f83-9891-7e6b98a298d9",
    "organizationId": "1",
    "productName": "Nokia",
    "licenseType": "Phone",
    "licenseMax": 12,
    "licenseAllocated": 1,
    "comment": "exampleProperty"
}
```
![PostMan Get 范例](https://github.com/ChenLin12138/licensing-service/blob/master/demo/pic/LicenseGetDemo.png)

```
2019-08-25 16:27:53.789  INFO 854 --- [           main] c.c.c.ConfigServicePropertySourceLocator : Fetching config from server at : http://localhost:8888
2019-08-25 16:27:53.946  INFO 854 --- [           main] c.c.c.ConfigServicePropertySourceLocator : Located environment: name=licenseingservice, profiles=[default], label=null, version=null, state=null
2019-08-25 16:27:53.946  INFO 854 --- [           main] b.c.PropertySourceBootstrapConfiguration : Located property source: CompositePropertySource {name='configService', propertySources=[MapPropertySource {name='file:///Users/zdm/eclipse-workspace/configuration-service/src/main/resources/licenseingservice/licenseingservice.properties'}]}
2019-08-25 16:27:53.951  INFO 854 --- [           main] c.c.l.LicenseingserviceApplication       : The following profiles are active: dev
2019-08-25 16:27:54.845  INFO 854 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data repositories in DEFAULT mode.
2019-08-25 16:27:54.896  INFO 854 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 44ms. Found 1 repository interfaces.
2019-08-25 16:27:55.210  INFO 854 --- [           main] o.s.cloud.context.scope.GenericScope     : BeanFactory id=d637f60d-8b63-3d3a-b4e9-632c8d8e79b5
2019-08-25 16:27:55.342  INFO 854 --- [           main] trationDelegate$BeanPostProcessorChecker : Bean 'org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration' of type [org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration$$EnhancerBySpringCGLIB$$d4433d8f] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
2019-08-25 16:27:55.370  INFO 854 --- [           main] trationDelegate$BeanPostProcessorChecker : Bean 'org.springframework.cloud.autoconfigure.ConfigurationPropertiesRebinderAutoConfiguration' of type [org.springframework.cloud.autoconfigure.ConfigurationPropertiesRebinderAutoConfiguration$$EnhancerBySpringCGLIB$$f05d408c] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
2019-08-25 16:27:55.702  INFO 854 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2019-08-25 16:27:55.728  INFO 854 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2019-08-25 16:27:55.728  INFO 854 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.17]
2019-08-25 16:27:55.826  INFO 854 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2019-08-25 16:27:55.826  INFO 854 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 1863 ms
2019-08-25 16:27:56.650  INFO 854 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2019-08-25 16:27:57.098  INFO 854 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2019-08-25 16:27:57.180  INFO 854 --- [           main] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [
```