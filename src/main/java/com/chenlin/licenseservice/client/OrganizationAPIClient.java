package com.chenlin.licenseservice.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chenlin.licenseservice.model.Organization;

/**
 * @author Chen Lin
 * @date 2019-11-12
 */

@Component
public class OrganizationAPIClient {

	@Autowired
	OrganizationFeignClient organizationFeignClient;
	
	public Organization getOrganization(String organizationId) {
		
		Organization organization = null;	
		organization = organizationFeignClient.getOrganzationByFeign(organizationId);
		return organization;
	}
	
}
