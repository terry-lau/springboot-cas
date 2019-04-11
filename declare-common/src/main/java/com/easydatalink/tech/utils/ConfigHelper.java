/**
 * 2011-4-12 下午08:07:53
 */
package com.easydatalink.tech.utils;

/**
 * @author bin_liu
 */
public class ConfigHelper {

	/**
	 * 配置文件的绝对路径
	 */
	private static String cfgPath;
	private static String worklassPath;
	private static String appHomePath;

	public String getAppHomePath() {
		return appHomePath;
	}

	public void setAppHomePath(String appHomePath) {
		ConfigHelper.appHomePath = appHomePath;
	}

	public String getCfgPath() {
		return cfgPath;
	}

	public void setCfgPath(String cfgPath) {
		ConfigHelper.cfgPath = cfgPath;
	}

	public String getWorklassPath() {
		return worklassPath;
	}

	public void setWorklassPath(String worklassPath) {
		ConfigHelper.worklassPath = worklassPath;
	}

	public ConfigHelper(Class<?> cls) {
		init(cls, "");
	}

	public ConfigHelper(Class<?> cls, String cfgDir) {
		init(cls, cfgDir);
	}

	public ConfigHelper(Object flagClass) {
		init(flagClass.getClass(), "");
	}

	private static void init(Class<?> cls, String cfgDir) {
		String osName = System.getProperty("os.name");
		String path = cls.getResource("").toString();
		System.out.println("path:" + path);
		if (osName.indexOf("Win") > -1) {
			path = path.substring(path.indexOf("/") + 1);
		} else {
			path = path.substring(path.indexOf("/"));
			path = path.replaceAll("%20", " ");
		}

		if (path.indexOf("classes") > -1) {
			worklassPath = path.substring(0, path.indexOf("classes") + 8);
			cfgPath = path.substring(0, path.indexOf("classes") + 8);
			cfgPath = StringHelper.isNull(cfgDir) ? cfgPath : cfgPath + cfgDir+ "/";
			//app_home setting
			if(cfgPath.indexOf("WEB-INF")>-1){
				appHomePath = cfgPath.substring(0, cfgPath.indexOf("WEB-INF") - 1);
			}else{
				appHomePath = cfgPath.substring(0, cfgPath.length() - 8);
			}
			
		} else {
			worklassPath = path.substring(0, path.indexOf("WEB-INF") + 8)+ "classes/";
			cfgPath = path.substring(0, path.indexOf("WEB-INF") + 8)+ "classes/" + cfgDir + "/";
			appHomePath = cfgPath.substring(0, cfgPath.indexOf("WEB-INF") - 1);
		}
		System.setProperty("app_home", appHomePath);
		System.out.println("cfgPath: " + cfgPath);
		System.out.println("app_home: " + appHomePath);
	}

}
