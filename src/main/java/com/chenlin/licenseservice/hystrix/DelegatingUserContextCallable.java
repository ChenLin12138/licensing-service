package com.chenlin.licenseservice.hystrix;

import java.util.concurrent.Callable;

import com.chenlin.licenseservice.utils.UserContext;
import com.chenlin.licenseservice.utils.UserContextHolder;

/**
 * @author Chen Lin
 * @date 2019-09-12
 */

public class DelegatingUserContextCallable<T> implements Callable {

	private final Callable<T> delegate;
	private UserContext originalUserContext;
	
	//原始的Callable类将被传递到自定义的Callable类
	//自定义Callable将调用Hystrix保护的代码和来自父线程的UserContext
	public DelegatingUserContextCallable(Callable<T> delegate, UserContext userContext) {
		this.delegate = delegate;
		this.originalUserContext = userContext;
	}
	
	//call方法在被@HystrixCommand注解保护的方法之前调用
	//该方法加入了私货originalUserContext
	@Override
	public T call() throws Exception {
		//已设置UserContext，存储UserContext的ThreadLocal变量与运行受
		//Hystrix保护的方法的线程相互关联
		UserContextHolder.setContext(originalUserContext);
		try {
			//UserContext设置后，在Hystrix保护的方法上调用call
			return delegate.call();
		}finally {
			this.originalUserContext = null;
		}
	}
	
	public static <T> Callable<T> create(Callable<T> delegate, UserContext userContext){
		return new DelegatingUserContextCallable<T>(delegate, userContext);
	}

}
