package com.chenlin.licenseservice.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.chenlin.licenseservice.model.Organization;

/**
 * @author Chen Lin
 * @date 2019-08-31
 */

@Component
public class OrganizationDiscoveryClient {

	//仅在getOrganizationByDiscovery获取Instance中使用
	//getOrganizationByRibbonRestTemplate不需要获取instance实例，也不需要这个属性
	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired
	RestTemplate ribbonRestTemplate;

	public Organization getOrganizationByDiscovery(String organizationId) {
		RestTemplate restTemplate = new RestTemplate();
		// 获取organizationservice的Instance列表
		List<ServiceInstance> instances = discoveryClient.getInstances("organizationservice");
		if (instances.size() == 0) {
			return null;
		}
		// 产生一个调用的url
		// 例如：localhost:8081/v1/organizations/1
		String serviceUri = String.format("%s/v1/organizations/%s", instances.get(0).getUri().toString(),
				organizationId);

		ResponseEntity<Organization> restExchange = restTemplate.exchange(serviceUri, HttpMethod.GET, null,
				Organization.class, organizationId);

		return restExchange.getBody();
	}

	public Organization getOrganizationByRibbonRestTemplate(String organizationId) {

		//这段代码与上面的getOrganizationByDiscovery非常相似但是他们有以下的不同。
		//http://organizationservice/v1/organizations/{organizationId}中的organizationservice是
		//Eureka中注册的服务名，在这里使用服务名替代了之前的instance名，所以具体使用哪个实例来处理我们的请求
		//这件事情交给了Eureka而不再是客户端程序来处理。服务实例之间可轮询执行。
		//实际服务位置和端口与开发人员完全隔离
		ResponseEntity<Organization> restExchange = ribbonRestTemplate.exchange(
				"http://organizationservice/v1/organizations/{organizationId}", HttpMethod.GET, null,
				Organization.class, organizationId);

		return restExchange.getBody();
	}

}
