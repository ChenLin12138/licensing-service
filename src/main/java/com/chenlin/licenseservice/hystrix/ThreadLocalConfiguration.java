package com.chenlin.licenseservice.hystrix;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisher;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;

/**
 * @author Chen Lin
 * @date 2019-09-12
 */

@Configuration
public class ThreadLocalConfiguration {

	// 默认required为true,flase表达此类中这个属性可以注入null
	@Autowired(required = false)
	private HystrixConcurrencyStrategy existingConcurrencyStrategy;

	@PostConstruct
	public void init() {
		// 因为要注册一个新的并发策略，所以要获取所有其他的Hystrix组件，然后重新设置Hystrix插件
		HystrixEventNotifier eventNotifier = HystrixPlugins.getInstance().getEventNotifier();
		HystrixMetricsPublisher metricsPublisher = HystrixPlugins.getInstance().getMetricsPublisher();
		HystrixPropertiesStrategy propertiesStrategy = HystrixPlugins.getInstance().getPropertiesStrategy();
		HystrixCommandExecutionHook hook = HystrixPlugins.getInstance().getCommandExecutionHook();
		//完成插件设置
		HystrixPlugins.reset();
		
		//将重置后的插件进行注册
		//这里注册的Strategy是我们自己定义的ThreadLocalAwareStrategy
		HystrixPlugins.getInstance()
				.registerConcurrencyStrategy(new ThreadLocalAwareStrategy(existingConcurrencyStrategy));
		HystrixPlugins.getInstance().registerEventNotifier(eventNotifier);
		HystrixPlugins.getInstance().registerMetricsPublisher(metricsPublisher);
		HystrixPlugins.getInstance().registerPropertiesStrategy(propertiesStrategy);
		HystrixPlugins.getInstance().registerCommandExecutionHook(hook);
	}
}
