package com.chenlin.licenseservice.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chenlin.licenseservice.client.OrganizationDiscoveryClient;
import com.chenlin.licenseservice.config.ServiceConfig;
import com.chenlin.licenseservice.model.License;
import com.chenlin.licenseservice.model.Organization;
import com.chenlin.licenseservice.repository.LicenseRepository;

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

	public License getLicense(String organizationId, String licenseId, String clientType) {
		License license = getLicense(organizationId,licenseId);
		Organization organization = retrieveOrgInfo(organizationId, clientType);
		return license.withOrganizationId(organization.getId())
					  .withOrganizationName(organization.getContactName());
	}

	private Organization retrieveOrgInfo(String organizationId, String clientType) {
		
		Organization organization = null;
		
		switch(clientType) {
			case "discovery":
				System.out.println("I am using the discovery client");
				organization = organizationDiscoveryClient.getOrganization(organizationId);
				break;
			default:	
//				organization = organizationRestClient.getOrganization(organizationId);
		}
		
		return organization; 
	}

	/*
	 * 下面省略了其他代码，如果编译无法通过请喊作者补全
	 * 但是作者任何他不会上传编译无法通过的代码
	 */

}
