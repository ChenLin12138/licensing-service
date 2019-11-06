package com.chenlin.licenseservice.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @author Chen Lin
 * @date 2019-09-15
 */
//为出站消息添加关联id
public class FeignUserContextInterceptor implements RequestInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(FeignUserContextInterceptor.class);
	
	@Override
	public void apply(RequestTemplate template) {
		//以上方法不work，从RequestContextHolder取不出任何东西，很可能这是因为Interceptor是在一个线程中
		//这个RequestContextHolder是在主线程中。我没有把他从主线程注入子线程。
//		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//		HttpServletRequest request = attributes.getRequest();
//		String corellationId = request.getHeader(UserContext.CORRELATION_ID);
//		throw new RuntimeException("I'm exception");
		logger.debug("Licensing-service Correlation id:{}",UserContextHolder.getContext().getCorrelationId());
		template.header(UserContext.CORRELATION_ID, UserContextHolder.getContext().getCorrelationId());
		template.header(UserContext.AUTHORIZATION, UserContextHolder.getContext().getAuthorization());
	}

}
