package com.adks.dubbo.service.admin.enumeration;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.enumeration.Adks_enumeration;
import com.adks.dubbo.api.data.enumeration.Adks_enumeration_value;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.admin.enumeration.EnumerationDao;

@Service
public class EnumerationService extends BaseService<EnumerationDao>{

	@Autowired
	private EnumerationDao enumerationDao;
	
	
	/*
	 * 返回所有的枚举以及枚举值
	 * */
	public List<Adks_enumeration> getEnAll(){
		return enumerationDao.getEnAll();
	}
	public List<Adks_enumeration_value> getEnValAll(){
		return enumerationDao.getEnValAll();
	}
	
	/*
	 * 以下是对Enumerations的操作
	 * */
	public List<Map<String, Object>> getEnumerationsList() {
		return enumerationDao.getEnumerationsList();
	}

	public Adks_enumeration getEnumerationById(Integer enumerationId){
		return enumerationDao.getEnumerationById(enumerationId);
	}
	
	public void deleteEnumerationByIds(String ids) {
		enumerationDao.deleteEnumerationByIds(ids);
	}
	
	public Page<List<Map<String, Object>>> getEnumerationListPage(Page<List<Map<String, Object>>> page){
		return enumerationDao.getEnumerationListPage(page);
	}
	
	public Map<String, Object> getEnumerationByName(String name){
		return enumerationDao.getEnumerationByName(name);
	}

	@Override
	protected EnumerationDao getDao() {
		return enumerationDao;
	}
	
	/*
	 * 以下是对EnumerationValue的操作
	 * */
	
	public void deleteEnumerationValueByIds(String ids) {
		enumerationDao.deleteEnumerationValueByIds(ids);
	}
	
	public Page<List<Map<String, Object>>> getEnumerationValueListPage(Page<List<Map<String, Object>>> page){
		return enumerationDao.getEnumerationValueListPage(page);
	}
	
	public Map<String, Object> getEnumerationValueByName(String name){
		return enumerationDao.getEnumerationValueByName(name);
	}
}
