package com.adks.dubbo.service.admin.enumeration;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.enumeration.Adks_enumeration;
import com.adks.dubbo.api.data.enumeration.Adks_enumeration_value;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.admin.enumeration.EnumerationValueDao;

@Service
public class EnumerationValueService extends BaseService<EnumerationValueDao>{

	@Autowired
	private EnumerationValueDao enumerationValueDao;
	
	/*
	 * 以下是对EnumerationValue的操作
	 * */
	
	public void deleteEnumerationValueByIds(String ids) {
		enumerationValueDao.deleteEnumerationValueByIds(ids);
	}
	
	public Page<List<Map<String, Object>>> getEnumerationValueListPage(Page<List<Map<String, Object>>> page){
		return enumerationValueDao.getEnumerationValueListPage(page);
	}
	
	public Map<String, Object> getEnumerationValueByName(String name){
		return enumerationValueDao.getEnumerationValueByName(name);
	}

	@Override
	protected EnumerationValueDao getDao() {
		return enumerationValueDao;
	}
	
	/**
	 * 
	 * @Title getEnumerationValueById
	 * @Description：根据枚举值id获取枚举信息
	 * @author xrl
	 * @Date 2017年6月16日
	 * @param Id
	 * @return
	 */
	public Map<String, Object> getEnumerationValueById(Integer Id){
		return enumerationValueDao.getEnumerationValueById(Id);
	}
}
