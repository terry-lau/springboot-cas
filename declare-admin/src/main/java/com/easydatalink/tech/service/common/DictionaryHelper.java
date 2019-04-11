package com.easydatalink.tech.service.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.easydatalink.tech.entity.base.BaseDictData;
import com.easydatalink.tech.entity.base.BaseDictType;
import com.easydatalink.tech.service.base.IBaseDictDataManager;
import com.easydatalink.tech.service.base.IBaseDictTypeManager;
import com.easydatalink.tech.utils.SpringContextUtils;

/**
 * 字典信息获取
 * @author Terry
 *
 */
@Component
public class DictionaryHelper {
	/**
	 * 字典
	 */
	private static Map<String, List<BaseDictData>> dictData = new HashMap<String, List<BaseDictData>>();
	
	@Autowired
	private static ServletContext servletContext;
	/**
	 * 加载字典数据
	 * @return
	 */
	public static Map<String, List<BaseDictData>> load() {
		//添加字典表信息
		IBaseDictTypeManager typeManager = SpringContextUtils.getBean(IBaseDictTypeManager.class);
		IBaseDictDataManager detaManager = SpringContextUtils.getBean(IBaseDictDataManager.class);
		List<BaseDictType> types = typeManager.getAll();
		if(types!=null && types.size()>0){
			for(int i=0;i<types.size();i++){
				BaseDictType ent = types.get(i);
				List<BaseDictData> detas = detaManager.getByDictTypeId(ent.getId());
				dictData.put(ent.getDicttypeCode(), detas);
			}
		}	
		return dictData;
	}
	
	/**
	 * 根据字典主档编号获取字典明细
	 * @param dicttypecode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static  List<BaseDictData>  getDictDataByDictTypeCode(String dicttypecode){
		Map<String, List<BaseDictData>> dictData=(Map<String, List<BaseDictData>>) servletContext.getAttribute("dictData");
		Iterator<String>  it=dictData.keySet().iterator();
		List<BaseDictData> dictDataList=null;
		while(it.hasNext()){
			String dictTypeCode=it.next();
			if(dictTypeCode.equals(dicttypecode)){
				dictDataList=dictData.get(dicttypecode);
				break;
			}
		}
		return dictDataList;
	}
	
	/**
	 * 根据字典主档和字典明细编码获取字典名称
	 * @param dicttypecode
	 * @param dictCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getDictDataName(String dicttypecode,String dictCode){
		Map<String, List<BaseDictData>> dictData=(Map<String, List<BaseDictData>>) servletContext.getAttribute("dictData");
		Iterator<String>  it=dictData.keySet().iterator();
		String dictDataName="";
		while(it.hasNext()){
			String dictTypeCode=it.next();
			if(dictTypeCode.equals(dicttypecode)){
				List<BaseDictData> dictDataList=dictData.get(dicttypecode);
				for (BaseDictData baseDictData : dictDataList) {
					if(baseDictData.getDictCode().equals(dictCode)){
						dictDataName=baseDictData.getDictName();
						break;
					}
				}
			}
		}
		return dictDataName;
	}
	public static ServletContext getServletContext() {
		return servletContext;
	}
	public static void setServletContext(ServletContext servletContext) {
		DictionaryHelper.servletContext = servletContext;
	}
	
}
