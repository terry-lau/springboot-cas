/**
 *2016年12月12日 上午2:22:42
 */
package com.easydatalink.tech.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

/**
 * @author: liubin
 *
 */
public class Log4jManager {

	private static String log4jFile;

	private static boolean inited;

	private static final String PORT_LOGGER_NAME = "declareport";

	private static final String EX_LOGGER_NAME = "ex";

	public static void init(Class<?> cls) {
		init(cls, "");
	}

	public static void init(Class<?> cls, String cfgDir) {
		ConfigHelper cfg = new ConfigHelper(cls, cfgDir);
		log4jFile = cfg.getCfgPath() + "log4j.properties";
		PropertyConfigurator.configureAndWatch(log4jFile, 60 * 1000);
		System.out.println("------>log4j init<---------");
		inited = true;
	}

	public static Log getPortLogger() {
		check();
		return LogFactory.getLog(PORT_LOGGER_NAME);
	}

	public static void logError(String out, Throwable e) {
		check();
		Log ex = LogFactory.getLog(EX_LOGGER_NAME);
		ex.error(out + StringHelper.getStackInfo(e));
	}

	public static void logError(Throwable e) {
		check();
		Log ex = LogFactory.getLog(EX_LOGGER_NAME);
		ex.error(StringHelper.getStackInfo(e));
	}

	public static void logError(String out) {
		check();
		Log ex = LogFactory.getLog(EX_LOGGER_NAME);
		ex.error(out);
	}

	public static Log getLog(Class<?> cls) {
		check();
		return LogFactory.getLog(cls);
	}

	public static Log getLog(String logName) {
		check();
		return LogFactory.getLog(logName);
	}

	private static void check() {
		if (!inited)
			throw new IllegalStateException(
					"log4j mananger is not init.....pls init first....eg:Log4jManager.init(Xxx.class)");
	}

}
