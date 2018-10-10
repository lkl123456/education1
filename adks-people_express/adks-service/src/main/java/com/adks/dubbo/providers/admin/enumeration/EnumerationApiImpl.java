package com.adks.dubbo.providers.admin.enumeration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.enumeration.Adks_enumeration;
import com.adks.dubbo.api.data.enumeration.Adks_enumeration_value;
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.interfaces.admin.enumeration.EnumerationApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.admin.enumeration.EnumerationService;
import com.adks.dubbo.service.admin.enumeration.EnumerationValueService;

public class EnumerationApiImpl implements EnumerationApi{

	@Autowired
	private EnumerationService enumerationService;
	@Autowired
	private EnumerationValueService enumerationValueService;
	
	@Override
	public List<Map<String, Object>> getEnumerationsList() {
		return enumerationService.getEnumerationsList();
	}

	public Adks_enumeration getEnumerationById(Integer enId){
		return enumerationService.getEnumerationById(enId);
	}
	@Override
	public Integer saveEnumerationService(Adks_enumeration enumeration) {
		Integer flag=0;
		Map<String,Object> insertColumnValueMap = new HashMap<String,Object>();
		insertColumnValueMap.put("enName", enumeration.getEnName());
		insertColumnValueMap.put("enDesc", enumeration.getEnDesc());
		insertColumnValueMap.put("enDisplay", enumeration.getEnDisplay());
		insertColumnValueMap.put("enumerationType", enumeration.getEnumerationType());
		insertColumnValueMap.put("creatorId", enumeration.getCreatorId());
		insertColumnValueMap.put("creatorName", enumeration.getCreatorName());
		if(enumeration.getEnId()!=null){
			Map<String,Object> updateWhereConditionMap = new HashMap<String,Object>();
			updateWhereConditionMap.put("enId", enumeration.getEnId());
			flag=enumerationService.update(insertColumnValueMap, updateWhereConditionMap);
		}else{
			insertColumnValueMap.put("createTime", enumeration.getCreateTime());
			flag=enumerationService.insert(insertColumnValueMap);
		}
		return flag;
	}

	@Override
	public void deleteEnumerationByIds(String ids) {
		enumerationService.deleteEnumerationByIds(ids);
	}

	@Override
	public Map<String, Object> getEnumerationByName(String name) {
		return enumerationService.getEnumerationByName(name);
	}

	@Override
	public Page<List<Map<String, Object>>> getEnumerationListPage(Page<List<Map<String, Object>>> page) {
		return enumerationService.getEnumerationListPage(page);
	}

	/*
	 * 以下是对EnumerationValue的操作
	 * */
	@Override
	public Integer saveEnumerationValueService(Adks_enumeration_value enumerationValue) {
		Integer flag=0;
		Map<String,Object> insertColumnValueMap = new HashMap<String,Object>();
		insertColumnValueMap.put("valName", enumerationValue.getValName());
		insertColumnValueMap.put("valDisplay", enumerationValue.getValDisplay());
		insertColumnValueMap.put("valDesc", enumerationValue.getValDesc());
		insertColumnValueMap.put("enId", enumerationValue.getEnId());
		insertColumnValueMap.put("creatorId", enumerationValue.getCreatorId());
		insertColumnValueMap.put("creatorName", enumerationValue.getCreatorName());
		if(enumerationValue.getEnValueId()!=null){
			Map<String,Object> updateWhereConditionMap = new HashMap<String,Object>();
			updateWhereConditionMap.put("enValueId", enumerationValue.getEnValueId());
			flag=enumerationValueService.update(insertColumnValueMap, updateWhereConditionMap);
		}else{
			insertColumnValueMap.put("createTime", enumerationValue.getCreateTime());
			flag=enumerationValueService.insert(insertColumnValueMap);
		}
		return flag;
	}

	@Override
	public void deleteEnumerationValueByIds(String ids) {
		enumerationService.deleteEnumerationValueByIds(ids);
	}

	@Override
	public Map<String, Object> getEnumerationValueByName(String name) {
		return enumerationService.getEnumerationValueByName(name);
	}

	@Override
	public Page<List<Map<String, Object>>> getEnumerationValueListPage(Page<List<Map<String, Object>>> page) {
		return enumerationService.getEnumerationValueListPage(page);
	}

	@Override
	public List<Adks_enumeration> getEnAll() {
		return enumerationService.getEnAll();
	}

	@Override
	public List<Adks_enumeration_value> getEnValAll() {
		return enumerationService.getEnValAll();
	}

	@Override
	public Map<String, Object> getEnumerationValueById(Integer Id) {
		return enumerationValueService.getEnumerationValueById(Id);
	}


}
