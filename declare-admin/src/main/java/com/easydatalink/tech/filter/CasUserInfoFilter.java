package com.easydatalink.tech.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.easydatalink.tech.entity.auth.User;
import com.easydatalink.tech.service.auth.IUserManager;
import com.easydatalink.tech.utils.SessionUtils;
import com.easydatalink.tech.utils.SpringContextUtils;

/**
 * 
 * @author Terry
 *
 */
public class CasUserInfoFilter implements Filter {

    Logger logger =  LoggerFactory.getLogger(CasUserInfoFilter.class);
  
	private String loginPageUrl;
    private String serviceUrl;
	// 排除拦截
    private String excludes;
 	protected List<String> excludeList;
 	private PathMatcher pathMatcher = null;
	 	
	public void init(FilterConfig filterConfig) throws ServletException {
		excludes = filterConfig.getInitParameter("excludes");
		loginPageUrl=filterConfig.getInitParameter("casServerLogout");
		serviceUrl=filterConfig.getInitParameter("serverName");
 		if (StringUtils.isNotBlank(excludes)) {
 			String[] strs=excludes.split("\\|");
 			excludeList=new ArrayList<String>();
 			for (String string : strs) {
 				excludeList.add(string);
			}
 			pathMatcher = new AntPathMatcher();
 		}
	}

  private boolean matchExcludePath(String path) {
		if (excludeList != null) {
			for (String ignore : excludeList) {
				if (pathMatcher.match(ignore, path)) {
					return true;
				}
			}
		}
		return false;
	}
	public void doFilter(ServletRequest req, ServletResponse resp,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String loginName = request.getRemoteUser();//登录名
		if (matchExcludePath(request.getServletPath())){
			chain.doFilter(request, response);
        	return;
        }
		if (!"".equals(loginName)&&null!=loginName) {
			IUserManager userManager=SpringContextUtils.getBean("userManager");
			User user=userManager.findUserByLoginName(loginName);
			if(getLocalUser(request)==null){//用户SESSION不存在
				SessionUtils.setSessionUser(request, user);
			}
			chain.doFilter(request, response); 
		}else {
	        redirectToLoginPage(request, response);
		}
	}

	 /**
     * 获取Session
     * @param request
     * @return
     */
    private User getLocalUser(HttpServletRequest request) {
        User sessionUser = (User) SessionUtils.getSessionUser(request);
        return sessionUser == null ? null : sessionUser;
    }

	private void redirectToLoginPage(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
			request.getSession().invalidate();
			response.sendRedirect(this.loginPageUrl+URLEncoder.encode(serviceUrl,"UTF-8"));
	}

	public void destroy() {
	}

	public String getExcludes() {
		return excludes;
	}

	public void setExcludes(String excludes) {
		this.excludes = excludes;
	}

//	public static void main(String[] args) {
//		AntPathMatcher pathMatcher = new AntPathMatcher();
//		// test exact matching
//		System.out.println(pathMatcher.match("/webjars/*/*/*", "/webjars/aaaa/xxxx"));
//
//	}
}
