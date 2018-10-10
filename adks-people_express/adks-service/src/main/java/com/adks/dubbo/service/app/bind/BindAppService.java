package com.adks.dubbo.service.app.bind;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.user.Adks_user_bind;
import com.adks.dubbo.dao.app.bind.BindAppDao;

@Service
public class BindAppService extends BaseService<BindAppDao> {

	@Autowired
	private BindAppDao bindDao;

	@Override
	protected BindAppDao getDao() {
		return bindDao;
	}

	public List<Adks_user_bind> getByUserId(int userId) {
		return bindDao.getByUserId(userId);
	}

	public void deleteUserByIds(String userIds) {
		bindDao.deleteUserByIds(userIds);
	}

	public void deleteByRemoteUserIdAndUserId(String remoteUserId, int userId) {
		bindDao.deleteByRemoteUserIdAndUserId(remoteUserId, userId);
	}
}
