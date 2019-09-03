package com.chenlin.licenseservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//访问http://<yourserver>:8080/refersh端点可以刷新spring自定义的属性
//自定义属性例如我们在serverConfig类中定义的${example.property}"
//但是不会刷新spring data定义的数据库配置
//@RefreshScope
//使得Springboot可以使用DiscoveryClient和Ribbon库
@EnableDiscoveryClient
//此注解可以注释掉，springboot会根据classPath包含
//spring-cloud-starter-netflix-eureka-client自动以eureka client向
//eureka server注册改服务。
//@EnableEurekaClient
public class LicenseingserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(LicenseingserviceApplication.class, args);
	}

}
