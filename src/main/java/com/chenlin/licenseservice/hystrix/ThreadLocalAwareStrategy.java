package com.chenlin.licenseservice.hystrix;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.chenlin.licenseservice.utils.UserContextHolder;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariable;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableLifecycle;
import com.netflix.hystrix.strategy.properties.HystrixProperty;

/**
 * @author Chen Lin
 * @date 2019-09-11
 */

//自定义一个并发策略，并把父线程的UserContext内容注入到方法策略中
public class ThreadLocalAwareStrategy extends HystrixConcurrencyStrategy {

	private HystrixConcurrencyStrategy strategy;

	public ThreadLocalAwareStrategy(HystrixConcurrencyStrategy strategy) {
		this.strategy = strategy;
	}

	// strategy不为空，调用本类的strategy，否则调用父类的
	// 我们可以看出下面所有的方法复写都是如果有子类调用子类的strategy,如果没有则调用父类
	@Override
	public BlockingQueue<Runnable> getBlockingQueue(int maxQueueSize) {

		return strategy != null ? strategy.getBlockingQueue(maxQueueSize) : super.getBlockingQueue(maxQueueSize);

	}

	@Override
	public <T> HystrixRequestVariable<T> getRequestVariable(HystrixRequestVariableLifecycle<T> rv) {
		return strategy != null ? strategy.getRequestVariable(rv) : super.getRequestVariable(rv);
	}

	@Override
	public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey, HystrixProperty<Integer> corePoolSize,
			HystrixProperty<Integer> maximumPoolSize, HystrixProperty<Integer> keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {

		return strategy != null
				? strategy.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue)
				: super.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	//将来UserContext注入到并发策略中
	@Override
	public<T> Callable<T> wrapCallable(Callable<T> callable){
		return strategy != null 
				? strategy
				.wrapCallable(new DelegatingUserContextCallable<T>(callable, UserContextHolder.getContext()))
				:super.wrapCallable(new DelegatingUserContextCallable<T>(callable, UserContextHolder.getContext()));
	}

}
