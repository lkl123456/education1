package com.adks.dubbo.service.web.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.user.Adks_usercollection;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.web.user.UserOnLineWebDao;
import com.adks.dubbo.dao.web.user.UsercollectionWebDao;

@Service
public class UsercollectionWebService extends BaseService<UsercollectionWebDao> {

	@Autowired
	private UsercollectionWebDao usercollectionDao;
	
	@Override
	protected UsercollectionWebDao getDao() {
		return usercollectionDao;
	}
	
	public Page<List<Adks_usercollection>> myCollectionList(Page<List<Adks_usercollection>> page) {
		return usercollectionDao.myCollectionList(page);
	}

}
