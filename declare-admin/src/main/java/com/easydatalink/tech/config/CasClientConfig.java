package com.easydatalink.tech.config;

import javax.inject.Inject;
import javax.servlet.Filter;

import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.configuration.ConfigurationKeys;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.AssertionThreadLocalFilter;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import com.easydatalink.tech.filter.CasUserInfoFilter;
import com.easydatalink.tech.filter.XssFilter;

/**
 * CAS 客户端配置
 * @author Terry
 *
 */
@Configuration
public class CasClientConfig {
	
    @Inject
    private SpringCasAutoConfig autoConfig;
    
    private static boolean casEnable = true;

    public CasClientConfig() {
    }

    @Bean
    public SpringCasAutoConfig getSpringCasAutoConfig() {
        return new SpringCasAutoConfig();
    }

    /**
     * 用于实现单点登出功能
     */
    @Bean
    public ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> singleSignOutHttpSessionListener() {
        ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> listener = new ServletListenerRegistrationBean<>();
        listener.setEnabled(casEnable);
        listener.setListener(new SingleSignOutHttpSessionListener());
        listener.setOrder(1);
        return listener;
    }

    /**
     * 该过滤器用于实现单点登出功能，单点退出配置，一定要放在其他filter之前
     */
    @Bean
    public FilterRegistrationBean<Filter> logoutFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        LogoutFilter logoutFilter = new LogoutFilter(autoConfig.getCasServerUrlPrefix() + "logout?service=" + autoConfig.getServerName(), new SecurityContextLogoutHandler());
        filterRegistrationBean.setFilter(logoutFilter);
        filterRegistrationBean.setEnabled(casEnable);
        if (autoConfig.getSignOutFilters().size() > 0)
            filterRegistrationBean.setUrlPatterns(autoConfig.getSignOutFilters());
        else filterRegistrationBean.addUrlPatterns("/logout");

        filterRegistrationBean.addInitParameter(ConfigurationKeys.CAS_SERVER_URL_PREFIX.getName(), autoConfig.getCasServerUrlPrefix());
        filterRegistrationBean.addInitParameter(ConfigurationKeys.SERVER_NAME.getName(), autoConfig.getServerName());
        filterRegistrationBean.setOrder(2);
        return filterRegistrationBean;
    }

    /**
     * 该过滤器用于实现单点登出功能，单点退出配置，一定要放在其他filter之前
     */
    @Bean
    public FilterRegistrationBean<Filter> singleSignOutFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        LogoutFilter logoutFilter = new LogoutFilter(autoConfig.getCasServerUrlPrefix() + "logout?service=" + autoConfig.getServerName(), new SecurityContextLogoutHandler());
        filterRegistrationBean.setFilter(logoutFilter);
        filterRegistrationBean.setEnabled(casEnable);
        if (autoConfig.getSignOutFilters().size() > 0)
            filterRegistrationBean.setUrlPatterns(autoConfig.getSignOutFilters());
        else filterRegistrationBean.addUrlPatterns("/*");

        filterRegistrationBean.addInitParameter(ConfigurationKeys.CAS_SERVER_URL_PREFIX.getName(), autoConfig.getCasServerUrlPrefix());
        filterRegistrationBean.addInitParameter(ConfigurationKeys.SERVER_NAME.getName(), autoConfig.getServerName());
        filterRegistrationBean.setOrder(3);
        return filterRegistrationBean;
    }

    /**
     * 该过滤器负责用户的认证工作
     */
    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilter() {
        FilterRegistrationBean<AuthenticationFilter> filterRegistration = new FilterRegistrationBean<AuthenticationFilter>();
        filterRegistration.setFilter(new AuthenticationFilter());
        filterRegistration.setEnabled(casEnable);
        if (autoConfig.getAuthFilters().size() > 0) filterRegistration.setUrlPatterns(autoConfig.getAuthFilters());
        else filterRegistration.addUrlPatterns("/*");
        //casServerLoginUrl:cas服务的登陆url
        filterRegistration.addInitParameter(ConfigurationKeys.CAS_SERVER_LOGIN_URL.getName(), autoConfig.getCasServerLoginUrl());
        //本项目登录ip+port
        filterRegistration.addInitParameter(ConfigurationKeys.SERVER_NAME.getName(), autoConfig.getServerName());
        filterRegistration.addInitParameter(ConfigurationKeys.USE_SESSION.getName(), autoConfig.isUseSession() ? "true" : "false");
        filterRegistration.addInitParameter(ConfigurationKeys.REDIRECT_AFTER_VALIDATION.getName(), autoConfig.isRedirectAfterValidation() ? "true" : "false");

        filterRegistration.addInitParameter(ConfigurationKeys.IGNORE_PATTERN.getName(),autoConfig.getIgnorePattern());

        filterRegistration.setOrder(4);
        return filterRegistration;
    }

    /**
     * 该过滤器负责对Ticket的校验工作
     */
    @Bean
    public FilterRegistrationBean<Cas20ProxyReceivingTicketValidationFilter> cas20ProxyReceivingTicketValidationFilter() {
        FilterRegistrationBean<Cas20ProxyReceivingTicketValidationFilter> filterRegistrationBean = new FilterRegistrationBean<Cas20ProxyReceivingTicketValidationFilter>();
        Cas20ProxyReceivingTicketValidationFilter ticketValidationFilter = new Cas20ProxyReceivingTicketValidationFilter();
        ticketValidationFilter.setServerName(autoConfig.getServerName());
        filterRegistrationBean.setFilter(ticketValidationFilter);
        filterRegistrationBean.setEnabled(casEnable);
        if (autoConfig.getValidateFilters().size() > 0)
            filterRegistrationBean.setUrlPatterns(autoConfig.getValidateFilters());
        else filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter(ConfigurationKeys.CAS_SERVER_URL_PREFIX.getName(), autoConfig.getCasServerUrlPrefix());
        filterRegistrationBean.addInitParameter(ConfigurationKeys.SERVER_NAME.getName(), autoConfig.getServerName());
        filterRegistrationBean.setOrder(5);
        return filterRegistrationBean;
    }

    /**
     * 该过滤器对HttpServletRequest请求包装， 可通过HttpServletRequest的getRemoteUser()方法获得登录用户的登录名
     */
    @Bean
    public FilterRegistrationBean<HttpServletRequestWrapperFilter> httpServletRequestWrapperFilter() {
        FilterRegistrationBean<HttpServletRequestWrapperFilter> filterRegistrationBean = new FilterRegistrationBean<HttpServletRequestWrapperFilter>();
        filterRegistrationBean.setFilter(new HttpServletRequestWrapperFilter());
        if (autoConfig.getRequestWrapperFilters().size() > 0) {
            filterRegistrationBean.setUrlPatterns(autoConfig.getRequestWrapperFilters());
        } else {
            filterRegistrationBean.addUrlPatterns("/login");
        }
        filterRegistrationBean.setOrder(6);
        return filterRegistrationBean;
    }

    /**
     * 该过滤器使得可以通过org.jasig.cas.client.util.AssertionHolder来获取用户的登录名。
     * 比如AssertionHolder.getAssertion().getPrincipal().getName()。
     * 这个类把Assertion信息放在ThreadLocal变量中，这样应用程序不在web层也能够获取到当前登录信息
     */
    @Bean
    public FilterRegistrationBean<AssertionThreadLocalFilter> assertionThreadLocalFilter() {
        FilterRegistrationBean<AssertionThreadLocalFilter> filterRegistration = new FilterRegistrationBean<AssertionThreadLocalFilter>();
        filterRegistration.setFilter(new AssertionThreadLocalFilter());
        filterRegistration.setEnabled(true);
        if (autoConfig.getAssertionFilters().size() > 0)
            filterRegistration.setUrlPatterns(autoConfig.getAssertionFilters());
        else
            filterRegistration.addUrlPatterns("/*");
        filterRegistration.setOrder(7);
        return filterRegistration;
    }
    
    /**
	 * 单点登录本地用户信息
	 * @return
	 */
	@Bean
	public FilterRegistrationBean<CasUserInfoFilter> localUserInfoFilter(){
		FilterRegistrationBean<CasUserInfoFilter> registrationBean = new FilterRegistrationBean<CasUserInfoFilter>();
		registrationBean.setFilter(new CasUserInfoFilter());
		registrationBean.addUrlPatterns("/*");
		registrationBean.setName("casUserInfoFilter");
		registrationBean.setOrder(8);
		registrationBean.addInitParameter(ConfigurationKeys.CAS_SERVER_URL_PREFIX.getName(), autoConfig.getCasServerUrlPrefix());
		registrationBean.addInitParameter(ConfigurationKeys.SERVER_NAME.getName(), autoConfig.getServerName());
		registrationBean.addInitParameter("excludes",autoConfig.getIgnorePattern());
		return registrationBean;
	}
	
	/**
	 * XSS攻击过滤
	 * @return
	 */
	public FilterRegistrationBean<XssFilter> xssFilter(){
		FilterRegistrationBean<XssFilter> registrationBean = new FilterRegistrationBean<XssFilter>();
		registrationBean.setFilter(new XssFilter());
		registrationBean.addUrlPatterns("/*");
		registrationBean.setName("xssFilter");
		registrationBean.setOrder(9);
		return registrationBean;
	}

}