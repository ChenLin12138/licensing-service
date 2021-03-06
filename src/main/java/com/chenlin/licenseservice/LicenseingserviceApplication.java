package com.chenlin.licenseservice;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import com.chenlin.licenseservice.config.ServiceConfig;
import com.chenlin.licenseservice.utils.UserContextInterceptor;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


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
//告知服务使用Sink接口中的定义通道来监听传入的消息
//@EnableBinding(Sink.class)
@EnableAutoConfiguration
@EnableSwagger2
public class LicenseingserviceApplication {
	
	//告知Spring Cloud创建一个支持Ribbon的RestTemplate
	//在Feign模式下，不需要此配置
//	@LoadBalanced
//	@Bean
//	public RestTemplate getRestTemplate() {
//		return new RestTemplate();
//	}
	
	private static final Logger logger = LoggerFactory.getLogger(LicenseingserviceApplication.class);
	
	@Autowired
	private ServiceConfig serviceConfig;
	
	//Http中的关联id出站时使用
	@LoadBalanced
	@Bean
	public RestTemplate getRestTemplate() {
		RestTemplate template = new RestTemplate();
		List<ClientHttpRequestInterceptor> interceptors = template.getInterceptors();
		if(interceptors==null) {
			template.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
		}else {
			interceptors.add(new UserContextInterceptor());
		}
		return template;
	}
	
	//每次收到来自input通道的消息，spring cloud stream调用此方法
//	@StreamListener(Sink.INPUT)
//	public void loggerSink(OrganizationChangeModel orgChange) {
//		logger.debug("Received an event for organization id {}",orgChange.getOrganizationId());
//	}
	
	//jedisConnectionFactory()方法设置到Redis服务器的实际数据库连接
	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		//你会发现其实这是两句废话，因为在JedisConnectionFactory中包含了一个属性StandaloneConfiguration
		//他的默认hostName就是localhost
		//默认port就是6379
		jedisConnectionFactory.getStandaloneConfiguration().setHostName(serviceConfig.getRedisServer());
		jedisConnectionFactory.getStandaloneConfiguration().setPort(serviceConfig.getRedisPort());
		return jedisConnectionFactory;
	}
	
	//redisTemplate()方法创建一个RedisTemplate，用于对Redis服务器质性进行操作
	@Bean
	public RedisTemplate<String, Object> redisTemplate(){
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(jedisConnectionFactory());
		return template;
	}
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(LicenseingserviceApplication.class, args);
	}

}
