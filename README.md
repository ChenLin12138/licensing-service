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
https://github.com/ChenLin12138/licensing-service/blob/master/demo/pic/LicensePostDemo.png
结果会在数据库licenses表中发现这条数据


- Get请求
localhost:8080/v1/organizatons/1/licenses/728f9a66-e29f-4f83-9891-7e6b98a298d9
查询结果
```json
{"licenseId":"728f9a66-e29f-4f83-9891-7e6b98a298d9","organizationId":"1","productName":"Nokia","licenseType":"Phone","licenseMax":12,"licenseAllocated":1,"comment":"exampleProperty"}
```
https://github.com/ChenLin12138/licensing-service/blob/master/demo/pic/LicenseGetDemo.png