package com.chenlin.licenseservice.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.chenlin.licenseservice.model.Organization;
import com.chenlin.licenseservice.repository.IOrganizationRedisRepository;
import com.chenlin.licenseservice.utils.UserContextHolder;

/**
 * @author Chen Lin
 * @date 2019-11-09
 */

@Component
public class OrganizationFeignAPIClient {
	
	private static final Logger logger = LoggerFactory.getLogger(OrganizationFeignAPIClient.class);
	
	@Autowired
	OrganizationFeignClient organizationFeignClient;
	
	@Autowired
	IOrganizationRedisRepository orgRedisRepo;
	
	private Organization checkRedisCache(String organizationId) {
		try {
			return orgRedisRepo.findOrganization(organizationId);
		}catch(Exception ex) {
			logger.error("Error encountered while trying to retrieve organization {} check Redis Cache.Excetion {}", organizationId, ex);
			return null;
		}
	}
	
	private void cacheOrganizationObject (Organization org) {
		try {
			orgRedisRepo.saveOrganization(org);
		}catch (Exception ex) {
			logger.error("Unable to cache organzation {} in Redis. exception {}", org.getId(), ex);
		}
	}
	
	public Organization getOrganzation(String organizationId) {
		logger.debug("In Licensing Service.getOrganzation: {}", UserContextHolder.getContext().getCorrelationId());
		Organization org = checkRedisCache(organizationId);
		if (org != null) {
			logger.debug("I have successfully retrived an organization {} from the redis cache : {}.", organizationId, org);
			return org;
		}
		
		logger.debug("Unable to locate organization from the redis cache : {}.",organizationId);
		
		org = organizationFeignClient.getOrganzationByFeign(organizationId);
		
		if(org != null) {
			cacheOrganizationObject(org);
		}
		
		return org;
	}
}
