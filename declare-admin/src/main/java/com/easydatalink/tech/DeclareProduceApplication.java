package com.easydatalink.tech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;

import com.fr.web.ReportServlet;

/**
 * 申报表生成程序入口
 * @author Terry
 *
 */
@SpringBootApplication
@ComponentScan(basePackages={"com.easydatalink.tech"})
@DependsOn("springContextUtils")
public class DeclareProduceApplication extends SpringBootServletInitializer {
	/**
	 * war打包
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DeclareProduceApplication.class);
	}
	/**
	 * 帆软注册
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public ServletRegistrationBean<ReportServlet>  servletRegistrationBean(){
		return new ServletRegistrationBean(new ReportServlet(),"/ReportServer");
	}
	
	public static void main(String[] args) {
		SpringApplication.run(DeclareProduceApplication.class, args);
	}
}
