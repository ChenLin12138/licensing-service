package com.chenlin.licenseservice.repository;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.chenlin.licenseservice.model.Organization;

@Repository
class OrganizationRedisRepositoryImpl implements IOrganizationRedisRepository {

	private static final String HASH_NAME = "organization";
	
	private RedisTemplate<String, Organization> redisTemplate;
	private HashOperations<String, String, Organization> hashOperations;
	
	public OrganizationRedisRepositoryImpl() {
		super();
	}
	
	//Spring通过构造函数注入redisTemplate
	//为什么要私有化构造函数
	@Autowired
	private OrganizationRedisRepositoryImpl (RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	//用构造函数注入是为了，构造函数完成后，执行下面的方法。
	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}
	
	@Override
	public void saveOrganization(Organization org) {
		hashOperations.put(HASH_NAME, org.getId(), org);

	}

	@Override
	public void updateOrganization(Organization org) {
		hashOperations.put(HASH_NAME, org.getId(), org);

	}

	@Override
	public void deleteOrganization(String organizationId) {
		// TODO Auto-generated method stub
		hashOperations.delete(HASH_NAME, organizationId);

	}

	@Override
	public Organization findOrganization(String organizationId) {
		return (Organization) hashOperations.get(HASH_NAME, organizationId);
	}

}
