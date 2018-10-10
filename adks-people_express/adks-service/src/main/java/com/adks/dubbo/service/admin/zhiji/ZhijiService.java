package com.adks.dubbo.service.admin.zhiji;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.zhiji.Adks_rank;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.admin.zhiji.ZhijiDao;

@Service
public class ZhijiService extends BaseService<ZhijiDao>{

	@Autowired
	private ZhijiDao zhijiDao;
	
	public List<Map<String, Object>> getZhijisList() {
		return zhijiDao.getZhijisList();
	}

	@Override
	protected ZhijiDao getDao() {
		return zhijiDao;
	}
	
	public List<Adks_rank> getZhijisListByClass(Integer parentId){
		return zhijiDao.getZhijisListByClass(parentId);
	}

	public Adks_rank getZhijiById(Integer zhijiId){
		return zhijiDao.getZhijiById(zhijiId);
	}
	
	public void deleteZhijiByIds(String ids) {
		zhijiDao.deleteZhijiByIds(ids);
	}
	
	public Page<List<Map<String, Object>>> getZhijiListPage(Page<List<Map<String, Object>>> page){
		return zhijiDao.getZhijiListPage(page);
	}
	
	public Map<String, Object> getZhijiByName(String name){
		return zhijiDao.getZHijiByName(name);
	}
	
	public List<Adks_rank> getZhijisListAll(){
		return zhijiDao.getZhijisListAll();
	}
	
	public Map<String, Object> getZhiWuByName(String name) {
		return zhijiDao.getZhiWuByName(name);
	}
	public List<Adks_rank> getZhijiListByCon(Integer rankId) {
		return zhijiDao.getZhijiListByCon(rankId);
	}
	
}
