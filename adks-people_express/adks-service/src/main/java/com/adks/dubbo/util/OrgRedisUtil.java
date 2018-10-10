/**
 * 
 */
package com.adks.dubbo.util;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.adks.commons.util.PropertiesFactory;
import com.adks.dbclient.redis.RedisClient;
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.data.org.Adks_org_config;
import com.alibaba.fastjson.JSONObject;

/**
 * @author Administrator
 *
 */
@Lazy(false)
@Component
public class OrgRedisUtil {

	@Resource
	private RedisClient redisclient;
	public static RedisClient redisClient;

	@PostConstruct
	public void init() {
		OrgRedisUtil.redisClient = this.redisclient;
	}

	// 生存时间（最大）
	private static final int expireTimeMax = Integer.valueOf(PropertiesFactory.getProperty("redisConstant.properties", "expire_max_time"));
	// 生存时间（最小）
	private static final int expireTimeMin = Integer.valueOf(PropertiesFactory.getProperty("redisConstant.properties", "expire_min_time"));

	//机构
	public static final String adks_org = PropertiesFactory.getProperty("redisConstant.properties","adks_org");
	//通过id得到机构
	public static final String adks_org_id = PropertiesFactory.getProperty("redisConstant.properties","adks_org_id");
	//机构配置
	public static final String adks_org_config_id = PropertiesFactory.getProperty("redisConstant.properties","adks_org_config_id");
	//机构配置集合
	public static final String adks_org_config_List = PropertiesFactory.getProperty("redisConstant.properties","adks_org_config_List");
	//首页机构排行
	public static final String adks_org_topList = PropertiesFactory.getProperty("redisConstant.properties","adks_org_topList");
	
	
	/**
	 * 新增或修改
	 * 
	 * @param redisName （时间最长）
	 *         机构配置  
	 * @param orgId
	 * @param map
	 * @return
	 */
	public static String addOrgConfig(String redisName, Integer orgId,Adks_org_config orgConfig) {
		String result = "";
		if (redisName != null && orgConfig != null ) {
			redisClient.setex(redisName+"_"+orgId, expireTimeMax, JSONObject.toJSONString(orgConfig));
			return result;
		}
		return null;
	}
	
	/**
	 * 新增或修改
	 * 
	 * @param redisName （时间最长）
	 *          首页机构排行 
	 * @param orgId
	 * @param map
	 * @return
	 */
	public static String addTopOrgList(String redisName, Integer orgId,List<Adks_org> list) {
		String result = "";
		if (redisName != null && list != null  && list.size()>0) {
			redisClient.setex(redisName+"_"+orgId, expireTimeMax, JSONObject.toJSONString(list));
			return result;
		}
		return null;
	}
	//添加parentId=0的机构配置的集合
	public static String addOrgConfigListParentIdZero(String redisName,List<Adks_org_config> list) {
		String result = "";
		if (redisName != null && list != null  && list.size()>0) {
			redisClient.setex(redisName, expireTimeMax, JSONObject.toJSONString(list));
			return result;
		}
		return null;
	}
	/**
	 * 新增或修改
	 * 
	 * @param redisName （时间最长）
	 *           
	 * @param orgId
	 * @param map
	 * @return
	 */
	public static String addOrg(String redisName, Integer orgId,Adks_org org) {
		String result = "";
		if (redisName != null && org != null ) {
			redisClient.setex(redisName+"_"+orgId, expireTimeMax, JSONObject.toJSONString(org));
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
	public static String getObject(String redisName, Integer orgId) {
		String result = "";
		String Nodata = "Nodata";
		if(redisName!=null){
			if (redisClient.exists(redisName+"_"+orgId))
				result = redisClient.get(redisName+"_"+orgId);
			else
				redisClient.setex(redisName+"_"+orgId, expireTimeMax, Nodata);
			return result;
		}
		return null;
	}
	public static String getObjectParamOne(String redisName) {
		String result = "";
		String Nodata = "Nodata";
		if(redisName!=null){
			if (redisClient.exists(redisName))
				result = redisClient.get(redisName);
			else
				redisClient.setex(redisName, expireTimeMax, Nodata);
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
	public static void emptyObject(String org_type) {
		if (StringUtils.isNotEmpty(org_type)) {
			String[] num = org_type.split(",");
			if (num != null && num.length > 0) {
				for (String str : num) {
					redisClient.delete(adks_org+str);
				}
			}
		}
	}
	/**
	 * 清空机构的所有缓存
	 * 
	 * @param redisType
	 * 
	 */
	public static void emptyObjectAll(String redisType) {
		if (StringUtils.isNotEmpty(redisType)) {
			String[] num = redisType.split(",");
			if (num != null && num.length > 0) {
				for (String str : num) {
					Set<String> keys=redisClient.keys(adks_org+str+"*");
					if(keys!=null && keys.size()>0){
						Iterator<String> ir=keys.iterator();
						while(ir.hasNext()){
							redisClient.delete(ir.next());
						}
					}
				}
			}
		}
	}
}
