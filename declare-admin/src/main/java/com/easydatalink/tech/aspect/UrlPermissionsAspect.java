package com.easydatalink.tech.aspect;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.easydatalink.tech.annotation.UrlPermissions;
import com.easydatalink.tech.constants.Constants;
import com.easydatalink.tech.http.HttpResult;
import com.easydatalink.tech.http.HttpStatus;


/**
 * 菜单权限校验
 * @author Terry
 *
 */
@Aspect  
@Component
public class UrlPermissionsAspect {
	 //本地异常日志记录对象  
    private  static  final Logger logger = LoggerFactory.getLogger(UrlPermissionsAspect.class);
    
    //Controller层切点  
    @Pointcut("@annotation(com.easydatalink.tech.annotation.UrlPermissions)")  
    public  void UrlPermissionsController() {  
    } 
    /**
     * url 权限控制校验
     * @param joinPoint
     */
    @SuppressWarnings("unchecked")
	@Before("UrlPermissionsController()")  
    public  void doBefore(JoinPoint joinPoint) {
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();  
    	HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();  
//    	try {
//    		 String menuCode=getControllerMethodDescription(joinPoint);
//             List<Menu> menuList=(List<Menu>) request.getSession().getAttribute(Constants.USERMENULIST);
//             boolean flag=false;
//             response.setContentType("application/json;charset=UTF-8");
//             PrintWriter writer = response.getWriter();
//             if (menuList==null||menuList.size()==0) {
//                  response.setStatus(HttpStatus.SC_OK);
//                  writer.write(JSON.toJSONString(HttpResult.error(HttpStatus.SC_FORBIDDEN, "当前用户没有访问权限")));
//                  writer.flush();
//                  writer.close();
//     		 }else {
//     			if ("".equals(menuCode)) {
//                     response.setStatus(HttpStatus.SC_OK);
//                     writer.write(JSON.toJSONString(HttpResult.error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "服务配置错误")));
//                     writer.flush();
//                     writer.close();
//     			}else {
//     				 if (menuCode.contains(",")) {//针对共享菜单
//     					String[] menuCodes=menuCode.split(",");
//     					for (String string : menuCodes) {
//     						 for (Menu menu : menuList) {
//     							 List<Menu> subMenus=menu.getChildren();
//     							 for (Menu subMenu : subMenus) {
//     								 if (string.equals(subMenu.getMenuCode())) {
//     									 flag=true;
//     								 }
//     							 }
//     						 }
//						}
// 					 }else{
// 						 for (Menu menu : menuList) {
// 							 List<Menu> subMenus=menu.getChildren();
// 							 for (Menu subMenu : subMenus) {
// 								 if (menuCode.equals(subMenu.getMenuCode())) {
// 									 flag=true;
// 								 }
// 							 }
// 						 }
// 					 }
//     				 if (!flag) {
//	 	                 response.setStatus(HttpStatus.SC_OK);
//	 	                 writer.write(JSON.toJSONString(HttpResult.error(HttpStatus.SC_FORBIDDEN, "当前用户没有访问权限")));
//	 	                 writer.flush();
//	 	                 writer.close();
//     				}
//     			}
//     		}
//		} catch (Exception e) {
//			 try {
//				response.setContentType("application/json;charset=UTF-8");
//                response.setStatus(HttpStatus.SC_OK);
//                PrintWriter writer = response.getWriter();
//                writer.write(JSON.toJSONString(HttpResult.error(HttpStatus.SC_NOT_FOUND, "功能不存在")));
//                writer.flush();
//                writer.close();
//			} catch (IOException e1) {
//				logger.error(e1.toString());
//			}
//		}
     }
    /** 
     * 获取注解中对方法的描述信息 用于Controller层注解 
     * @param joinPoint 切点 
     * @return 方法描述 
     * @throws Exception 
     */  
	public  static String getControllerMethodDescription(JoinPoint joinPoint) {
    	   String menuCode = "";  
    	 try {
    		 String targetName = joinPoint.getTarget().getClass().getName();  
    	        String methodName = joinPoint.getSignature().getName();  
    	        Object[] arguments = joinPoint.getArgs();  
    	        Class<?> targetClass = Class.forName(targetName);  
    	        Method[] methods = targetClass.getMethods();  
    	     
    	        for (Method method : methods) {  
    	             if (method.getName().equals(methodName)) {  
    	                Class<?>[] clazzs = method.getParameterTypes();  
    	                if (clazzs.length == arguments.length) {  
    	                	menuCode = method.getAnnotation(UrlPermissions.class).value();  
    	                    break;  
    	                }  
    	            }  
    	        }  
    	       return menuCode;  
		} catch (Exception e) {
			return menuCode;
		}
    }  
}
