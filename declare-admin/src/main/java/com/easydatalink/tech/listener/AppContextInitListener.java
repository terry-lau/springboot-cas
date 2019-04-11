package com.easydatalink.tech.listener;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.easydatalink.tech.entity.base.BaseDictData;
import com.easydatalink.tech.service.common.DictionaryHelper;

@WebListener
public class AppContextInitListener implements ServletContextListener,HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		 // 初始化数据字典
        Map<String, List<BaseDictData>> dictData = DictionaryHelper.load();
        context.setAttribute("dictData", dictData);
		System.out.println("listener start");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
