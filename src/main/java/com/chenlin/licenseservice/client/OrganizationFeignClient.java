package com.chenlin.licenseservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chenlin.licenseservice.model.Organization;

/**
 * @author Chen Lin
 * @date 2019-09-07
 */
@Component
//标注需要远程调用的服务
@FeignClient("organizationservice")
public interface OrganizationFeignClient {
	//定义调用端点的路径和动作
	@RequestMapping(method = RequestMethod.GET, value = "/v1/organizations/{organizationId}", consumes = "application/json")
	//定义需要传入的参数
	Organization getOrganzationByFeign(@PathVariable("organizationId") String organizationId);
}
