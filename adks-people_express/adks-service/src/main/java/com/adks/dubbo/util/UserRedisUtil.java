/**
 * 
 */
package com.adks.dubbo.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.adks.commons.util.PropertiesFactory;
import com.adks.dbclient.redis.RedisClient;
import com.adks.dubbo.api.data.grade.Adks_grade_user;
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.data.org.Adks_org_config;
import com.adks.dubbo.api.data.user.Adks_user;
import com.adks.dubbo.api.data.user.Adks_user_online;
import com.alibaba.fastjson.JSONObject;

/**
 * @author Administrator
 *
 */
@Lazy(false)
@Component
public class UserRedisUtil {

	@Resource
	private RedisClient redisclient;
	public static RedisClient redisClient;

	@PostConstruct
	public void init() {
		UserRedisUtil.redisClient = this.redisclient;
	}

	// 生存时间（最大）
	private static final int expireTimeMax = Integer.valueOf(PropertiesFactory.getProperty("redisConstant.properties", "expire_max_time"));
	// 生存时间（最小）
	private static final int expireTimeMin = Integer.valueOf(PropertiesFactory.getProperty("redisConstant.properties", "expire_min_time"));

	//用户
	public static final String adks_user = PropertiesFactory.getProperty("redisConstant.properties","adks_user");
	//通过id得到用户
	public static final String adks_user_id = PropertiesFactory.getProperty("redisConstant.properties","adks_user_id");
	//通过id得到用户
	public static final String adks_user_map_id = PropertiesFactory.getProperty("redisConstant.properties","adks_user_map_id");
	//通过id得到用户
	public static final String adks_user_name = PropertiesFactory.getProperty("redisConstant.properties","adks_user_name");
	//通过id得到用户
	public static final String adks_user_map_name = PropertiesFactory.getProperty("redisConstant.properties","adks_user_map_name");
	//在线用户
	public static final String adks_userOnline = PropertiesFactory.getProperty("redisConstant.properties","adks_userOnline");
	//在线用户的用户名
	public static final String adks_userOnline_username = PropertiesFactory.getProperty("redisConstant.properties","adks_userOnline_username");
	//用户排序
	public static final String adks_user_sort = PropertiesFactory.getProperty("redisConstant.properties","adks_user_sort");
	//后台用户id，后台用户名称
	public static final String adks_user_admin_id = PropertiesFactory.getProperty("redisConstant.properties","adks_user_admin_id");
	public static final String adks_user_admin_name = PropertiesFactory.getProperty("redisConstant.properties","adks_user_admin_name");
	//首页排序用户添加
	public static String addUserSort(String redisName, String orgCode,List<Adks_grade_user> gradeUsers) {
		String result = "";
		if (redisName != null && gradeUsers != null ) {
			redisClient.setex(redisName+"_"+orgCode, expireTimeMin, JSONObject.toJSONString(gradeUsers));
			return result;
		}
		return null;
	}
	
	/**
	 * 新增或修改
	 * 
	 * @param redisName （时间最长）
	 *         根据用户名添加在线用户
	 * @param orgId
	 * @param map
	 * @return
	 */
	public static String addUserOnline(String redisName, String username,Adks_user_online userOnline) {
		String result = "";
		if (redisName != null && userOnline != null ) {
			redisClient.setex(redisName+"_"+username, expireTimeMin, JSONObject.toJSONString(userOnline));
			return result;
		}
		return null;
	}
	
	/**
	 * 新增或修改
	 * 
	 * @param redisName （时间最长）
	 *          添加用户
	 * @param orgId
	 * @param map
	 * @return
	 */
	public static String addUser(String redisName, String userId,Adks_user user) {
		String result = "";
		if (redisName != null && user != null ) {
			redisClient.setex(redisName+"_"+userId, expireTimeMin, JSONObject.toJSONString(user));
			return result;
		}
		return null;
	}
	/**
	 * 新增或修改
	 * 
	 * @param redisName （时间最长）
	 *          添加用户
	 * @param orgId
	 * @param map
	 * @return
	 */
	public static String addUserByName(String redisName, String userName,Adks_user user) {
		String result = "";
		if (redisName != null && user != null ) {
			redisClient.set(redisName+"_"+userName, JSONObject.toJSONString(user));
			return result;
		}
		return null;
	}
	public static String addUserSaveMap(String redisName, String userName,Map map) {
		String result = "";
		if (redisName != null && map != null && map.size()>0) {
			redisClient.set(redisName+"_"+userName, JSONObject.toJSONString(map));
			return result;
		}
		return null;
	}
	/**
	 * 得到单个机构
	 * 
	 * @param redisName
	 * @param orgId
	 * @param isSuper
	 *            true,false
	 * @return
	 */
	public static String getObject(String redisName, String userId) {
		String result = "";
		String Nodata = "Nodata";
		if(redisName!=null){
			if (redisClient.exists(redisName+"_"+userId))
				result = redisClient.get(redisName+"_"+userId);
			else
				redisClient.setex(redisName+"_"+userId, expireTimeMin, Nodata);
			return result;
		}
		return null;
	}

	/**
	 * 清空机构某单个redis的缓存
	 * 
	 * @param org_type
	 *            规则定义（orgId+","）
	 * 
	 */
	public static void emptyUser(String org_type) {
		if (StringUtils.isNotEmpty(org_type)) {
			String[] num = org_type.split(",");
			if (num != null && num.length > 0) {
				for (String str : num) {
					redisClient.delete(adks_user+str);
				}
			}
		}
	}
	/**
	 * 清空机构某单个redis的缓存
	 * 
	 * @param org_type
	 *            规则定义（orgId+","）
	 * 
	 */
	public static void emptyUserOnline(String org_type) {
		if (StringUtils.isNotEmpty(org_type)) {
			String[] num = org_type.split(",");
			if (num != null && num.length > 0) {
				for (String str : num) {
					redisClient.delete(adks_userOnline+str);
				}
			}
		}
	}
}
