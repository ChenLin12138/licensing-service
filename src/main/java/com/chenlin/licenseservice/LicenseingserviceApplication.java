package com.chenlin.licenseservice;

import javax.servlet.annotation.WebFilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
//访问http://<yourserver>:8080/refersh端点可以刷新spring自定义的属性
//自定义属性例如我们在serverConfig类中定义的${example.property}"
//但是不会刷新spring data定义的数据库配置
//@RefreshScope
//使得Springboot可以使用DiscoveryClient和Ribbon库
//这个注解与@EnableFeignClients在使用Ribbon的RestTemplate的时候并不需要
//@EnableDiscoveryClient
//此注解可以注释掉，springboot会根据classPath包含
//spring-cloud-starter-netflix-eureka-client自动以eureka client向
//eureka server注册改服务。
//@EnableEurekaClient
@EnableFeignClients
//通知Spring Cloud将要使用Hystrix服务
@EnableCircuitBreaker
//扫描@WebFilter注解，启动定义Filter
@ServletComponentScan(basePackages = "com.chenlin.licenseservice.*")
public class LicenseingserviceApplication {
	
	//告知Spring Cloud创建一个支持Ribbon的RestTemplate
	//在Feign模式下，不需要此配置
	@LoadBalanced
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(LicenseingserviceApplication.class, args);
	}

}
