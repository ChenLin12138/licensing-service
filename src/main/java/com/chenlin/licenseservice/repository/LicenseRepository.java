package com.chenlin.licenseservice.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chenlin.licenseservice.model.License;

/**
 * @author Chen Lin
 * @date 2019-08-24
 */

//告诉Spring Boot这是一个JPA类
@Repository
//定义扩展自spring的CrudRepo
public interface LicenseRepository extends CrudRepository<License, String>{
	
	//查询语句被spring解析成select from 语句
	public List<License> findByOrganizationId(String organizationId);
	
	public License findByOrganizationIdAndLicenseId(String organizationId, String licenseId);
	
	
}
