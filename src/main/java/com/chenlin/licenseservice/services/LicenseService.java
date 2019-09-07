package com.chenlin.licenseservice.services;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chenlin.licenseservice.client.OrganizationDiscoveryClient;
import com.chenlin.licenseservice.client.OrganizationFeignClient;
import com.chenlin.licenseservice.config.ServiceConfig;
import com.chenlin.licenseservice.model.License;
import com.chenlin.licenseservice.model.Organization;
import com.chenlin.licenseservice.repository.LicenseRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

/**
 * @author Chen Lin
 * @date 2019-08-24
 */

@Service
public class LicenseService {
	@Autowired
	private LicenseRepository licenseRepository;

	@Autowired
	ServiceConfig config;
	
	@Autowired
    OrganizationDiscoveryClient organizationDiscoveryClient;
	
	@Autowired
	OrganizationFeignClient organizationFeignClient;

	public License getLicense(String organizationId, String licenseId) {
		License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
		return license.withComment(config.getExampleProperty());
	}

	public List<License> getLicenseByOrg(String organizationId) {
		return licenseRepository.findByOrganizationId(organizationId);
	}

	public void saveLicense(License license) {
		license.withId(UUID.randomUUID().toString());
		licenseRepository.save(license);
	}

	public void updateLicense(License license) {
		licenseRepository.save(license);
	}

	public void deleteLicense(License license) {
		licenseRepository.delete(license);
	}
//  普通模式
//	@HystrixCommand
	//延长超时时间模式
//	@HystrixCommand(commandProperties = {
//			@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="12000")
//	})
	//fallbackMothod属性定义了一个方法，如果来自Hystrix的调用失败，那么就会调用这个方法
	@HystrixCommand(fallbackMethod = "buildFallbackLicense")
	public License getLicense(String organizationId, String licenseId, String clientType) {
		randomlyRunlong();
		License license = getLicense(organizationId,licenseId);
		Organization organization = retrieveOrgInfo(organizationId, clientType);
		return license.withOrganizationId(organization.getId())
					  .withOrganizationName(organization.getContactName());
	}
	
	//在后备方法中，返回了一个硬编码的值
	//Fallback方法的名字必须和@HystrixCommand修饰的方法名一致
	//方法的参数和返回值也必须同@HystrixCommand修饰的方法一致
	private License buildFallbackLicense(String organizationId, String licenseId, String clientType){
		License license = new License();
		license.withId(licenseId)
		.withOrganizationId(organizationId)
		.withProductName("Sorry no licenseing info currently availabe");
		return license;
	}

	private Organization retrieveOrgInfo(String organizationId, String clientType) {
		
		Organization organization = null;
		
		switch(clientType) {
			case "discovery":
				System.out.println("I am using the discovery client");
				organization = organizationDiscoveryClient.getOrganizationByDiscovery(organizationId);
				break;
			case "ribbonresttemplate":
				organization = organizationDiscoveryClient.getOrganizationByRibbonRestTemplate(organizationId);
				break;
			case "feign":
				organization = organizationFeignClient.getOrganzationByFeign(organizationId);
				break;
			default:	
				organization = organizationFeignClient.getOrganzationByFeign(organizationId);
		}
		
		return organization; 
	}

	//提供1/3的概率运行耗时较长的数据库调用
	private void randomlyRunlong() {
		Random rand = new Random();
		int randomNum = rand.nextInt(3)+1;
		if(randomNum == 3) {
			sleep();
		}
	}

	//休眠11s
	//Hystrix默认休眠时间为1s
	private void sleep() {
		try {
			Thread.sleep(11000);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
