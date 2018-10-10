package com.adks.dubbo.service.admin.news;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.admin.news.FriendlylinkDao;

@Service
public class FriendlylinkService extends BaseService<FriendlylinkDao>{
	
	@Autowired
	private FriendlylinkDao friendlylinkDao;
	
	@Override
	protected FriendlylinkDao getDao() {
		return friendlylinkDao;
	}
	
	public Page<List<Map<String, Object>>> getFriendlylinkListPage(Page<List<Map<String, Object>>> page) {
		return friendlylinkDao.getFriendlylinkListPage(page);
	}
	

	public void deleteFriendlylinkByIds(String fdLinkIds) {
		friendlylinkDao.deleteFriendlylinkByIds(fdLinkIds);
	}

	public Map<String, Object> getFriendlylinkInfoById(Integer fdLinkId) {
		return friendlylinkDao.getFriendlylinkInfoById(fdLinkId);
	}
	
}
