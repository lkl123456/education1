package com.adks.dubbo.service.web.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.user.Adks_user;
import com.adks.dubbo.api.data.user.Adks_user_online;
import com.adks.dubbo.commons.ApiResponse;
import com.adks.dubbo.commons.ApiRetCode;
import com.adks.dubbo.dao.web.user.UserOnLineWebDao;
import com.adks.dubbo.util.Constants.AdminStatus;

@Service
public class UserOnLineWebService extends BaseService<UserOnLineWebDao> {
	
	@Autowired
	private UserOnLineWebDao userOnLineDao;

	@Override
	protected UserOnLineWebDao getDao() {
		return userOnLineDao;
	}
	
	public Adks_user getUserInfo(String username) {
		return userOnLineDao.getUserInfo(username);
	}
	
	public Adks_user_online getUserOnLineByName(String username) {
		return userOnLineDao.getUserOnLineByName(username);
	}

	public void deleteUserOnLine(Integer userId) {
		userOnLineDao.deleteUserOnLine(userId);
	}
	
	/**
	 * 
	 * @Title deleteUserOnLineBeforeNow
	 * @Description：清除当前时间之前的用户登录信息
	 * @author xrl
	 * @Date 2017年6月27日
	 * @param date
	 */
	public void deleteUserOnLineBeforeNow(Map<String, Object> map){
		userOnLineDao.deleteUserOnLineBeforeNow(map);
	}
}
