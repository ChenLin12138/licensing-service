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
	
	public String getExampleProperty() {
		// TODO Auto-generated method stub
		return exampleProperty;
	}

}
