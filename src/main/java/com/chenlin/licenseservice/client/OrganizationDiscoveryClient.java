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

	@Autowired
	private DiscoveryClient discoveryClient;

	public Organization getOrganization(String organizationId) {
		RestTemplate restTemplate = new RestTemplate();
		// 获取organizationservice的Instance列表
		List<ServiceInstance> instances = discoveryClient.getInstances("organizationservice");
		if (instances.size() == 0) {
			return null;
		}
		//产生一个调用的url
		//例如：localhost:8081/v1/organizations/1
		String serviceUri = String.format("%s/v1/organizations/%s", instances.get(0).getUri().toString(),
				organizationId);
		
		ResponseEntity<Organization> restExchange = restTemplate.exchange(serviceUri, HttpMethod.GET, null,
				Organization.class, organizationId);
		
		return restExchange.getBody();
	}

}
