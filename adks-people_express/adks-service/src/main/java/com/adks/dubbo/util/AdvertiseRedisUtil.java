/**
 * 
 */
package com.adks.dubbo.util;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.adks.commons.util.PropertiesFactory;
import com.adks.dbclient.redis.RedisClient;
import com.adks.dubbo.api.data.common.Adks_advertise;
import com.alibaba.fastjson.JSONObject;

/**
 * @author Administrator
 *
 */
@Lazy(false)
@Component
public class AdvertiseRedisUtil {

	@Resource
	private RedisClient redisclient;
	public static RedisClient redisClient;

	@PostConstruct
	public void init() {
		AdvertiseRedisUtil.redisClient = this.redisclient;
	}

	// 生存时间（最大）
	private static final int expireTimeMax = Integer
			.valueOf(PropertiesFactory.getProperty("redisConstant.properties", "expire_max_time"));
	// 生存时间（最小）
	private static final int expireTimeMin = Integer
			.valueOf(PropertiesFactory.getProperty("redisConstant.properties", "expire_min_time"));

	private static final String advertise = PropertiesFactory.getProperty("redisConstant.properties", "adks_advertise");

	/**
	 * 新增或修改
	 * 
	 * @param orgId
	 * @param map
	 * @return
	 */
	public static String addAdvertise(int orgId, List<Adks_advertise> list) {
		return redisClient.setex(advertise + orgId, expireTimeMax, JSONObject.toJSONString(list));
	}

	/**
	 * 获取广告集合
	 * 
	 * @param orgId
	 * @param isSuper
	 *            true,false
	 * @return
	 */
	public static String getAdvertise(int orgId, boolean isSuper) {
		String result = "";
		String Nodata = "Nodata";
		if (isSuper) {
			redisClient.keys(advertise + "*");
		} else {
			if (redisClient.exists(advertise + orgId))
				result = redisClient.get(advertise + orgId);
			else
				redisClient.setex(advertise + orgId, expireTimeMin, Nodata);
		}
		return result;
	}

	/**
	 * 清空广告相关缓存，修改、删除时调用
	 * 
	 * @param org
	 * 
	 */
	public static void emptyAdvertise(String org) {
		if (StringUtils.isNotEmpty(org)) {
			String[] num = org.split(",");
			if (num != null && num.length > 0) {
				for (String str : num) {
					redisClient.delete(advertise + str);
				}
			}
		}
	}
}
