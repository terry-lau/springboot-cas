package com.easydatalink.tech.config;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.easydatalink.tech.listener.AppContextInitListener;

/**
 * 系统监听配置
 * @author Terry
 *
 */
@Configuration
public class ListenerConfig {

	@Bean
	public ServletListenerRegistrationBean<AppContextInitListener> appContextInitListenerBean(){
		ServletListenerRegistrationBean<AppContextInitListener> appContextInitListener = new ServletListenerRegistrationBean<AppContextInitListener>(new AppContextInitListener());
		appContextInitListener.setOrder(1000000000);
		return appContextInitListener;
	}
}
