package com.adks.dubbo.api.interfaces.admin.enumeration;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.enumeration.Adks_enumeration;
import com.adks.dubbo.api.data.enumeration.Adks_enumeration_value;
import com.adks.dubbo.commons.Page;

public interface EnumerationApi {
	
	public List<Adks_enumeration> getEnAll();
	public List<Adks_enumeration_value> getEnValAll();
	
	/*
	 * 以下是Enumeration的操作
	 * */
	public List<Map<String, Object>> getEnumerationsList();
	
	//报错修改枚举值
	public Integer saveEnumerationService(Adks_enumeration enumeration);
	
	//删除枚举值
	public void deleteEnumerationByIds(String ids);
	
	//查看枚举是否重名
	public Map<String, Object> getEnumerationByName(String name);
	
	//获取枚举分页
	public Page<List<Map<String, Object>>> getEnumerationListPage(Page<List<Map<String, Object>>> page);
	
	/*
	 * 以下是EnumerationValue的操作
	 * */
	
	//报错修改枚举值
	public Integer saveEnumerationValueService(Adks_enumeration_value enumerationValue);
	
	//删除枚举值
	public void deleteEnumerationValueByIds(String ids);
	
	//查看枚举值是否重名
	public Map<String, Object> getEnumerationValueByName(String name);
	
	//查看枚举是否重名
	public Adks_enumeration getEnumerationById(Integer enId);
	
	//获取枚举分页
	public Page<List<Map<String, Object>>> getEnumerationValueListPage(Page<List<Map<String, Object>>> page);
	
	/**
	 * 
	 * @Title getEnumerationValueById
	 * @Description：根据枚举值id获取枚举信息
	 * @author xrl
	 * @Date 2017年6月16日
	 * @param Id
	 * @return
	 */
	public Map<String, Object> getEnumerationValueById(Integer Id);
}
