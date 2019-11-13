package com.chenlin.licenseservice.services;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chenlin.licenseservice.client.OrganizationDiscoveryClient;
import com.chenlin.licenseservice.client.OrganizationFeignAPIClient;
import com.chenlin.licenseservice.config.ServiceConfig;
import com.chenlin.licenseservice.model.License;
import com.chenlin.licenseservice.model.Organization;
import com.chenlin.licenseservice.repository.LicenseRepository;
import com.chenlin.licenseservice.utils.UserContextHolder;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

/**
 * @author Chen Lin
 * @date 2019-08-24
 */

@Service
public class LicenseService {
	
	private static final Logger logger = LoggerFactory.getLogger(LicenseService.class);
	
	@Autowired
	private LicenseRepository licenseRepository;

	@Autowired
	ServiceConfig config;

	@Autowired
	OrganizationDiscoveryClient organizationDiscoveryClient;

	@Autowired
	OrganizationFeignAPIClient organizationFeignAPIClient;

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
	// 延长超时时间模式
//	@HystrixCommand(commandProperties = {
//			@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="12000")
//	})
	// fallbackMothod属性定义了一个方法，如果来自Hystrix的调用失败，那么就会调用这个方法
	//threadPoolKey属性定义线程池的唯一名称
	//coreSize定义线程池中核心线程数量（书中说是最大数量，懒得查资料）
	//maxQueueSize用于定义线程池当前队列，它可以对传入的请求进行队列排序
	//circuitBreaker.requestVolumeThreshold:时间窗口内连续调用的次数：10次
	//circuitBreaker.errorThresholdPercentage：75%的错误率将启动断路器
	//circuitBreaker.sleepWindowInMilliseconds：在断路器启动后，7秒后发送另外一个请求验证短路的服务状态
	//metrics.rollingStats.timeInMilliseconds：时间窗口长度为15秒
	//metrics.rollingStats.numBuckets：时间窗口内统计的次数，15秒内统计3次
	@HystrixCommand(fallbackMethod = "buildFallbackLicense", 
			threadPoolKey = "licenseByOrgThreadPool",
			threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value="30"),
					@HystrixProperty(name = "maxQueueSize", value="10")},
			commandProperties = {
					@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
					@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75"),
					@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"),
					@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
					@HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5")
			}
	)
	public License getLicense(String organizationId, String licenseId, String clientType) {
		logger.debug("getLicense Correlation id:{}",UserContextHolder.getContext().getCorrelationId());
//		randomlyRunlong();
		License license = getLicense(organizationId, licenseId);
		Organization organization = retrieveOrgInfo(organizationId, clientType);
		return license.withOrganizationId(organization.getId()).withOrganizationName(organization.getContactName());
	}

	// 在后备方法中，返回了一个硬编码的值
	// Fallback方法的名字必须和@HystrixCommand修饰的方法名一致
	// 方法的参数和返回值也必须同@HystrixCommand修饰的方法一致
	private License buildFallbackLicense(String organizationId, String licenseId, String clientType, Throwable throwable) {
		License license = new License();
		license.withId(licenseId).withOrganizationId(organizationId)
				.withProductName("Sorry no licenseing info currently availabe");
		//捕获getLicense中的异常在此抛出
		logger.error("getLicense error:{}", throwable.fillInStackTrace());
		return license;
	}

	private Organization retrieveOrgInfo(String organizationId, String clientType) {

		Organization organization = null;

		switch (clientType) {
		case "discovery":
			organization = organizationDiscoveryClient.getOrganizationByDiscovery(organizationId);
			break;
		case "ribbonresttemplate":
			organization = organizationDiscoveryClient.getOrganizationByRibbonRestTemplate(organizationId);
			break;
		case "feign":
			organization = organizationFeignAPIClient.getOrganzation(organizationId);
			break;
		default:
			organization = organizationFeignAPIClient.getOrganzation(organizationId);
		}

		return organization;
	}

	// 提供1/3的概率运行耗时较长的数据库调用
	private void randomlyRunlong() {
		Random rand = new Random();
		int randomNum = rand.nextInt(3) + 1;
		if (randomNum == 3) {
			sleep();
		}
	}

	// 休眠11s
	// Hystrix默认休眠时间为1s
	private void sleep() {
		try {
			Thread.sleep(11000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
