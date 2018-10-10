package com.adks.dubbo.service.admin.user;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.redis.RedisClient;
import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.admin.user.UserOnlineDao;
@Service
public class UserOnlineService extends BaseService<UserOnlineDao> {

	@Resource
	private RedisClient redisClient;
	@Autowired
	private UserOnlineDao userOnlineDao;
	
	@Override
	protected UserOnlineDao getDao() {
		return userOnlineDao;
	}
	
	/**
	 * 
	 * @Title getUserOnlineStatisticsListPage
	 * @Description：获取在线用户分页列表
	 * @author xrl
	 * @Date 2017年6月22日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getUserOnlineStatisticsListPage(Page<List<Map<String, Object>>> page){
		return userOnlineDao.getUserOnlineStatisticsListPage(page);
	}

}
