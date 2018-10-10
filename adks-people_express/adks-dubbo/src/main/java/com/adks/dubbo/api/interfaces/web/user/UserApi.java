package com.adks.dubbo.api.interfaces.web.user;

import java.util.Date;
import java.util.Map;

import com.adks.dubbo.api.data.user.Adks_user;
import com.adks.dubbo.api.data.user.Adks_user_online;
import com.adks.dubbo.commons.ApiResponse;

public interface UserApi {
	
	/**
	 * 根据id获取用户信息
	 * @param userId
	 * @return
	 */
	Adks_user getUserInfoById(Integer userId);
	
	/**
	 * 查询用户信息
	 * @param username
	 * @return
	 */
	Adks_user getUserInfo(String username);
	
	public Adks_user_online getUserOnLineByName(String username);
	//保存在线用户的信息
	public void saveUserOnLine(Adks_user_online userOnLine);
	//移除在线用户的信息
	public void deleteUserOnLine(Integer userId);
	
	//根据电话号码得到用户
	public Adks_user checkUserCellPhone(String userPhone);

	public boolean updateUser(Adks_user user);
	
	//根据用户id获取密码
	public String getUserPassWordByUserId(Integer userId);
	
	//修改用户密码
	public boolean saveUserPassWordByUserId(Integer userId,String password);
	
	//通过身份证号得到用户
	public Adks_user getUserByCard(String cardNumber);

	//根据名族id得到名族的名称
	public String getNationalityName(Integer id);
	//移除课程收藏
	public boolean deleteMyCollectionById(Integer userConId);
	
	public Map<String,Object> checkUserCard(String card);
	
	//修改密码
	public void changePwd(String userCellphone);
	
	/**
	 * 
	 * @Title saveUserToRedis
	 * @Description:将用户保存在session中
	 * @author xrl
	 * @Date 2017年5月19日
	 * @param user
	 */
	public void saveUserToRedis(Adks_user user);
	
	public void deleteUserToRedis(String userName,Integer userId);
	
	/**
	 * 
	 * @Title deleteUserOnLineBeforeNow
	 * @Description：清除当前时间之前的用户登录信息
	 * @author xrl
	 * @Date 2017年6月27日
	 * @param map
	 */
	public void deleteUserOnLineBeforeNow(Map<String, Object> map);
}
