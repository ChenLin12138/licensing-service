package com.chenlin.licenseservice.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chenlin.licenseservice.model.License;
import com.chenlin.licenseservice.services.LicenseService;

@RestController
@RequestMapping(value = "/v1/organizatons/{organizationId}/licenses")
public class LicenseServiceController {

	@Autowired
	private LicenseService licenseService;

	private static final Logger logger = LoggerFactory.getLogger(LicenseServiceController.class);

	@RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
	public License getLicenses(@PathVariable("organizationId") String organizationId,
			@PathVariable("licenseId") String licenseId) {

		return licenseService.getLicense(organizationId, licenseId);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public void saveLicense(@RequestBody License license) {
		licenseService.saveLicense(license);
	}

	@RequestMapping(value = "/{licenseId}/{clientType}", method = RequestMethod.GET)
	public License getLicensesWithClient(@PathVariable("organizationId") String organizationId,
			@PathVariable("licenseId") String licenseId, @PathVariable("clientType") String clientType) {
		return licenseService.getLicense(organizationId, licenseId, clientType);

	}

}
