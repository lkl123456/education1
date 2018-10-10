package com.adks.dubbo.service.web.zhiji;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.zhiji.Adks_rank;
import com.adks.dubbo.dao.web.zhiji.ZhijiWebDao;

@Service
public class ZhijiWebService extends BaseService<ZhijiWebDao>{

	@Autowired
	private ZhijiWebDao zhijiDao;
	
	@Override
	protected ZhijiWebDao getDao() {
		return zhijiDao;
	}
	
	public List<Adks_rank> getZhijiListAll() {
		return zhijiDao.getZhijiListAll();
	}
	public Map<String, Object> getZHijiByName(String name) {
		return zhijiDao.getZHijiByName(name);
	}
	public List<Adks_rank> getZhijiListByCon(Integer rankId) {
		return zhijiDao.getZhijiListByCon(rankId);
	}
}
