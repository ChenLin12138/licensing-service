package com.chenlin.licenseservice.events.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import com.chenlin.licenseservice.events.models.OrganizationChangeModel;
import com.chenlin.licenseservice.repository.IOrganizationRedisRepository;

/**
 * @author Chen Lin
 * @date 2019-10-28
 */

@EnableBinding(Sink.class)
public class OrganizationChangeHandler {

	@Autowired
	private IOrganizationRedisRepository organizationRedisRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(OrganizationChangeHandler.class);
	
	
	@StreamListener(Sink.INPUT)
	public void loggerSink(OrganizationChangeModel orgChange) {
		logger.debug("Received an event for organization id {}",orgChange.getOrganizationId());
		switch (orgChange.getAction()) {
        case "GET":
            logger.debug("Received a GET event from the organization service for organization id {}", orgChange.getOrganizationId());
            break;
        case "SAVE":
            logger.debug("Received a SAVE event from the organization service for organization id {}", orgChange.getOrganizationId());
            break;
        case "UPDATE":
            logger.debug("Received a UPDATE event from the organization service for organization id {}", orgChange.getOrganizationId());
            organizationRedisRepository.deleteOrganization(orgChange.getOrganizationId());
            break;
        case "DELETE":
            logger.debug("Received a DELETE event from the organization service for organization id {}", orgChange.getOrganizationId());
            organizationRedisRepository.deleteOrganization(orgChange.getOrganizationId());
            break;
        default:
            logger.error("Received an UNKNOWN event from the organization service of type {}", orgChange.getType());
            break;
		}
	}
}
