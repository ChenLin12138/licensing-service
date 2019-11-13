package com.chenlin.licenseservice.repository;

import com.chenlin.licenseservice.model.Organization;

/**
 * @author Chen Lin
 * @date 2019-11-09
 */

public interface IOrganizationRedisRepository {

	void saveOrganization (Organization org);
	void updateOrganization (Organization org);
	void deleteOrganization (String organizationId);
	Organization findOrganization (String organizationId);
}
