package com.easydatalink.tech.controller;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 抽取公用Controller
 * 
 * @author Terry
 *
 */
public class BaseController {

	//TODO 传递参数的转换
	@SuppressWarnings("unchecked")
	public Map<String, Object> convertToMap(List<Object> list) {
		Map<String, Object> map = Maps.newHashMap();
		for (Object ob : list) {
			Map<String, Object> param = (Map<String, Object>) ob;
			map.put(param.get("name") + "", param.get("value"));
		}
		return map;
	}
}
