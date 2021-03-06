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
import com.chenlin.licenseservice.utils.UserContextHolder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="License")
@RestController
@RequestMapping(value = "/v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {

	@Autowired
	private LicenseService licenseService;

	private static final Logger logger = LoggerFactory.getLogger(LicenseServiceController.class);

	@ApiOperation(value = "查询License信息" ,  notes="通过organizationId和licenseId获取指定的License信息，默认使用Feign方式")
	@RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
	public License getLicenses(@PathVariable("organizationId") String organizationId,
			@PathVariable("licenseId") String licenseId) {

		return licenseService.getLicense(organizationId, licenseId);
	}
	
	@ApiOperation(value = "添加Licnese信息" ,  notes="为指定的organizationId添加License")
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public void saveLicense(@RequestBody License license) {
		licenseService.saveLicense(license);
	}
	
	@ApiOperation(value = "以指定方法查询License信息" ,  notes="通过organizationId和licenseId获取指定的License信息，默认使用Feign方式，通过clientType控制程序使用feign,discovery,ribbonresttemplate方式远程调用Organization服务")
	@RequestMapping(value = "/{licenseId}/{clientType}", method = RequestMethod.GET)
	public License getLicensesWithClient(@PathVariable("organizationId") String organizationId,
			@PathVariable("licenseId") String licenseId, @PathVariable("clientType") String clientType) {
		logger.debug("getLicense Correlation id:{}",UserContextHolder.getContext().getCorrelationId());
		return licenseService.getLicense(organizationId, licenseId, clientType);

	}

}
