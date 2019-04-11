package com.easydatalink.tech.controller.common;

import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.easydatalink.tech.entity.base.BaseDictData;
import com.easydatalink.tech.service.common.DictionaryHelper;

/**
 * 公用类
 * @author Terry
 *
 */
@RestController
public class CommonController {

	/**
	 * 获取系统字典JSON
	 * @return
	 */
	@ApiOperation(value="获取系统字典JSON")
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/dict", method = { RequestMethod.GET,RequestMethod.POST })
	public Object dict() {
		ServletContext content = DictionaryHelper.getServletContext();
		StringBuffer jsData = new StringBuffer();
		jsData.append("var ");
		Map<String, List<BaseDictData>> dictData=(Map<String, List<BaseDictData>>) content.getAttribute("dictData");
		for (String key : dictData.keySet()) {
			jsData.append(key).append("={list:{");
			List<BaseDictData> list = dictData.get(key);
			for (int i =0; i < list.size(); i++) {
				BaseDictData deta = list.get(i);
				if (i == (list.size()-1)) {
					jsData.append("'"+deta.getDictCode()+"':'"+deta.getDictName()+"'");
				} else {
					jsData.append("'"+deta.getDictCode()+"':'"+deta.getDictName()+"',");
				}
			}
			jsData.append("}};"); 
		}
		return jsData.substring(0, jsData.length() - 1);  
	}
	
	/**
	 * 根据字典编码获取字典列表
	 * @param request
	 * @param response
	 * @param code
	 */
	@ApiOperation(value="根据字典编码获取字典列表")
	@RequestMapping(value ="/queryBaseDictDataByCode", method = { RequestMethod.GET,RequestMethod.POST })
	public Object queryBaseDictDataByCode(HttpServletRequest request, HttpServletResponse response, String code){
		List<BaseDictData> dictList = DictionaryHelper.getDictDataByDictTypeCode(code);
		Map<String,Object> reqMap=new HashMap<String,Object>();
        reqMap.put("status", "sucess");
        reqMap.put("dictList", dictList);
        return reqMap;
	}
}
