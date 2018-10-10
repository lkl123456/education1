package com.adks.dubbo.service.web.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.user.Adks_user;
import com.adks.dubbo.dao.web.user.UserWebDao;
import com.adks.dubbo.util.UserRedisUtil;
import com.alibaba.fastjson.JSONObject;

@Service
public class UserWebService extends BaseService<UserWebDao> {
	
	@Autowired
	private UserWebDao userDao;

	@Override
	protected UserWebDao getDao() {
		return userDao;
	}
	
	/**
	 * 根据ID 获取用户信息
	 * @param userId
	 * @return
	 */
	public Adks_user getUserInfoById(Integer userId) {
		Adks_user user=null;
		String result=UserRedisUtil.getObject(UserRedisUtil.adks_user_id, userId+"");
		if("".equals(result) || "Nodata".equals(result)){
			user =userDao.getUserInfoById(userId);
			UserRedisUtil.addUser(UserRedisUtil.adks_user_id, userId+"", user);
		}else{
			user=JSONObject.parseObject(result, Adks_user.class);
		}
		return user;
	}
	
	
	//通过登录用户名得到用户
	public Adks_user getUserInfo(String username) {
		Adks_user user=null;
		String result=UserRedisUtil.getObject(UserRedisUtil.adks_user_name, username);
		if("".equals(result) || "Nodata".equals(result)){
			user =userDao.getUserInfo(username);
			UserRedisUtil.addUserByName(UserRedisUtil.adks_user_name, username, user);
		}else{
			user=JSONObject.parseObject(result, Adks_user.class);
		}
		return user;
	}
	/*根据电话号码得到用户*/
	public Adks_user checkUserCellPhone(String userPhone) {
		return userDao.checkUserCellPhone(userPhone);
	}
	/*根据用户id得到密码*/
	public String getUserPassWordByUserId(Integer userId) {
		return userDao.getUserPassWordByUserId(userId);
	}
	//根据身份证号查询用户
	public Adks_user getUserByCard(String cardNumber) {
		return userDao.getUserByCard(cardNumber);
	}
	public String getNationalityName(Integer id) {
		return userDao.getNationalityName(id);
	}
	
	public boolean deleteMyCollectionById(Integer userConId) {
		return userDao.deleteMyCollectionById(userConId);
	}
	public Map<String,Object> checkUserCard(String card) {
		return userDao.checkUserCard(card);
	}
	public void changePwd(String userCellphone) {
		userDao.changePwd(userCellphone);
	}
	
	/**
	 * 
	 * @Title saveUserToRedis
	 * @Description:将用户保存在session中
	 * @author xrl
	 * @Date 2017年5月19日
	 * @param user
	 */
	public void saveUserToRedis(Adks_user user){
		UserRedisUtil.addUserByName(UserRedisUtil.adks_user_name, user.getUserName(), user);
	}
	public void deleteUserToRedis(String username) {
		UserRedisUtil.emptyUser("name__"+username);
	}
	public void deleteUserToRedisByUserId(String userId){
		Adks_user user=userDao.getUserInfoById(Integer.parseInt(userId));
		UserRedisUtil.emptyUser("id__"+userId+",map_id__"+userId+",name__"+user.getUserName()+",map_name__"+user.getUserName()+""
				+ ",admin_id__"+user.getUserId()+",admin_name__"+user.getUserName());
	}
	public void deleteUserByName(String redisName){
		UserRedisUtil.emptyUser(redisName);
	}
}
