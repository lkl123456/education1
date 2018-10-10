package com.adks.dubbo.service.app.user;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.app.user.UserCollectionAppDao;

@Service
public class UserCollectionAppService extends BaseService<UserCollectionAppDao> {

	@Autowired
	private UserCollectionAppDao UserCollectionDao;

	@Override
	protected UserCollectionAppDao getDao() {
		return UserCollectionDao;
	}

	/**
	 * 删除收藏
	 * 
	 * @param userId
	 * @param courseId
	 * @return
	 */
	public int deleteCollection(int id) {
		return UserCollectionDao.deleteCollection(id);
	}

	/**
	 * 收藏列表
	 * 
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getCollectionPage(Page<List<Map<String, Object>>> page) {
		return UserCollectionDao.getCollectionPage(page);
	}

	/**
	 * 删除收藏
	 * 
	 * @param userId
	 * @param courseId
	 * @return
	 */
	public Map<String, Object> getByUCId(int userId, int courseId) {
		return UserCollectionDao.getUCId(userId, courseId);
	}
}
