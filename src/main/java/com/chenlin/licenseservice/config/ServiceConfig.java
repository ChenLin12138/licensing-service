package com.chenlin.licenseservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Chen Lin
 * @date 2019-08-24
 */

@Component
public class ServiceConfig {

//	@Value("${example.property}")
	@Value("exampleProperty")
	private String exampleProperty;
	
//	@Value("${redis.server}")
	@Value("localhost")
	private String redisServer;

//	@Value("${redis.port}")
	@Value("6379")
	private String redisPort;
	
	public String getExampleProperty() {
		// TODO Auto-generated method stub
		return exampleProperty;
	}

	public String getRedisServer(){
	    return redisServer;
	}

	public Integer getRedisPort(){
	    return new Integer( redisPort ).intValue();
	}

}
