package com.adks.dubbo.providers.web.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dbclient.mysql.MysqlClient;
import com.adks.dubbo.api.data.user.Adks_user;
import com.adks.dubbo.api.data.user.Adks_user_online;
import com.adks.dubbo.api.interfaces.web.user.UserApi;
import com.adks.dubbo.commons.ApiResponse;
import com.adks.dubbo.service.web.user.UserOnLineWebService;
import com.adks.dubbo.service.web.user.UserWebService;

public class UserApiImpl implements UserApi {
	
	@Autowired
	private MysqlClient mysqlClient;
	
	@Autowired
	private UserWebService userWebService;
	
	@Autowired
	private UserOnLineWebService userOnLineService;

	@Override
	public Adks_user getUserInfoById(Integer userId) {
		return userWebService.getUserInfoById(userId);
	}

	@Override
	public Adks_user getUserInfo(String username) {
		return userWebService.getUserInfo(username);
	}

	@Override
	public Adks_user_online getUserOnLineByName(String username) {
		return userOnLineService.getUserOnLineByName(username);
	}

	@Override
	public void saveUserOnLine(Adks_user_online userOnLine) {
		 Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
	        insertColumnValueMap.put("sessionId", userOnLine.getSessionId());
	        insertColumnValueMap.put("lastCheckDate", userOnLine.getLastCheckDate());
	        insertColumnValueMap.put("ipAddress", userOnLine.getIpAddress());
 	        insertColumnValueMap.put("region", userOnLine.getRegion());
 	        insertColumnValueMap.put("operator", userOnLine.getOperator());

	        if (userOnLine.getUserOnlineId() != null) {
	            Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
	            updateWhereConditionMap.put("userId", userOnLine.getUserId());
	            userOnLineService.update(insertColumnValueMap, updateWhereConditionMap);
	        }else {
	        	insertColumnValueMap.put("userName", userOnLine.getUserName());
	        	insertColumnValueMap.put("orgId", userOnLine.getOrgId());
	 	        insertColumnValueMap.put("orgName", userOnLine.getOrgName());
	 	        insertColumnValueMap.put("orgCode", userOnLine.getOrgCode());
	 	        insertColumnValueMap.put("userId", userOnLine.getUserId());
	            userOnLineService.insert(insertColumnValueMap);
	        }
	}

	@Override
	public void deleteUserOnLine(Integer userId) {
		userOnLineService.deleteUserOnLine(userId);
	}

	@Override
	public Adks_user checkUserCellPhone(String userPhone) {
		return userWebService.checkUserCellPhone(userPhone);
	}

	@Override
	public boolean updateUser(Adks_user user) {
		Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
		if(user.getUserRealName()!=null && !"".equals(user.getUserRealName())){
			 insertColumnValueMap.put("userRealName", user.getUserRealName());
		}
		if(user.getUserSex()!=null && !"".equals(user.getUserSex())){
			insertColumnValueMap.put("userSex", user.getUserSex());
		}
		insertColumnValueMap.put("userPhone", user.getUserPhone());
		if(user.getRankId()!=null && !"".equals(user.getRankId())){
			insertColumnValueMap.put("rankId", user.getRankId());
			insertColumnValueMap.put("rankName", user.getRankName());
		}
		if(user.getHeadPhoto()!=null && !"".equals(user.getHeadPhoto())){
			insertColumnValueMap.put("headPhoto", user.getHeadPhoto());
		}
		if(user.getCardNumer()!=null && !"".equals(user.getCardNumer())){
			insertColumnValueMap.put("cardNumer", user.getCardNumer());
		}
		insertColumnValueMap.put("cardNumer", user.getCardNumer());
		insertColumnValueMap.put("positionId", user.getPositionId());
		insertColumnValueMap.put("positionName", user.getPositionName());
        Integer i=0;
        if (user.getUserId() != null) {
            Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
            updateWhereConditionMap.put("userId", user.getUserId());
            i=userWebService.update(insertColumnValueMap, updateWhereConditionMap);
        }
        //清除缓存
        userWebService.deleteUserByName("id__"+user.getUserId()+",map_id__"+user.getUserId()+",name__"+user.getUserName()+","
        		+ "map_name__"+user.getUserName()+",admin_id__"+user.getUserId()+",admin_name__"+user.getUserName());
        if(i!=0){
        	return true;
        }else{
        	return false;
        }
	}

	@Override
	public String getUserPassWordByUserId(Integer userId) {
		return userWebService.getUserPassWordByUserId(userId);
	}

	@Override
	public boolean saveUserPassWordByUserId(Integer userId, String password) {
		Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
        insertColumnValueMap.put("userPassword", password);
        Integer i=0;
        if (userId != null) {
            Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
            updateWhereConditionMap.put("userId",userId);
            i=userWebService.update(insertColumnValueMap, updateWhereConditionMap);
            //清除缓存,包含id、name
            userWebService.deleteUserToRedisByUserId(userId+"");
        }
        if(i!=0){
        	return true;
        }else{
        	return false;
        }
	}

	@Override
	public Adks_user getUserByCard(String cardNumber) {
		return userWebService.getUserByCard(cardNumber);
	}

	@Override
	public String getNationalityName(Integer id) {
		return userWebService.getNationalityName(id);
	}

	@Override
	public boolean deleteMyCollectionById(Integer userConId) {
		return userWebService.deleteMyCollectionById(userConId);
	}

	@Override
	public Map<String,Object> checkUserCard(String card) {
		return userWebService.checkUserCard(card);
	}

	@Override
	public void changePwd(String userCellphone) {
		userWebService.changePwd(userCellphone);
		Adks_user user=userWebService.checkUserCellPhone(userCellphone);
		userWebService.deleteUserByName("id__"+user.getUserId()+",map_id__"+user.getUserId()+",name__"+user.getUserName()+",map_name__"+user.getUserName()
			+",admin_id__"+user.getUserId()+",admin_name__"+user.getUserName());
	}

	/**
	 * 
	 * @Title saveUserToRedis
	 * @Description:将用户保存在session中
	 * @author xrl
	 * @Date 2017年5月19日
	 * @param user
	 */
	@Override
	public void saveUserToRedis(Adks_user user) {
		userWebService.saveUserToRedis(user);
	}
	
	@Override
	public void deleteUserToRedis(String username,Integer userId) {
		userWebService.deleteUserByName("id__"+userId+",map_id__"+userId+",name__"+username+",map_name__"+username
		+",admin_id__"+userId+",admin_name__"+username);
	}

	@Override
	public void deleteUserOnLineBeforeNow(Map<String, Object> map) {
		userOnLineService.deleteUserOnLineBeforeNow(map);
	}

}
