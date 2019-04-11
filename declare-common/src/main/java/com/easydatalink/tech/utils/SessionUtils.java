package com.easydatalink.tech.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.util.WebUtils;

import com.easydatalink.tech.constants.Constants;


/**
 * 当前已登录用户Session
 * 
 * @author Terry
 */
public class SessionUtils {
    /**
     * 用户信息
     */
    public static final String SESSION_USER = Constants.USER;
    /**
     * 用户权限
     */
    public static final String SESSION_USER_PERMISSION = Constants.USERMENULIST;
    /**
     * 用户数据权限
     */
    public static final String DATA_AUTH = Constants.DATA_AUTH;

    public static Object getSessionUser(HttpServletRequest request) {
        return  WebUtils.getSessionAttribute(request, SESSION_USER);
    }

    public static void setSessionUser(HttpServletRequest request, Object sessionUser) {
        WebUtils.setSessionAttribute(request, SESSION_USER, sessionUser);
    }

    public static Object getSessionPermission(HttpServletRequest request) {
        return  WebUtils.getSessionAttribute(request, SESSION_USER_PERMISSION);
    }

    public static void setSessionPermission(HttpServletRequest request, Object dataPermission) {
        WebUtils.setSessionAttribute(request, SESSION_USER_PERMISSION, dataPermission);
    }

    public static String getDataPermission(HttpServletRequest request) {
        return (String) WebUtils.getSessionAttribute(request, DATA_AUTH);
    }

    public static void setSessionDataPermission(HttpServletRequest request, String dataPermission) {
        WebUtils.setSessionAttribute(request, DATA_AUTH, dataPermission);
    }
    
    public static void invalidate(HttpServletRequest request) {
        request.getSession().invalidate();
        setSessionUser(request, null);
        setSessionPermission(request, null);
        setSessionDataPermission(request, null);
    }
}
