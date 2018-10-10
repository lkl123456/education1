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
import com.adks.dubbo.api.data.news.Adks_news;
import com.adks.dubbo.api.data.news.Adks_news_sort;
import com.alibaba.fastjson.JSONObject;

/**
 * @author Administrator
 *
 */
@Lazy(false)
@Component
public class NewsRedisUtil {

	@Resource
	private RedisClient redisclient;
	public static RedisClient redisClient;

	@PostConstruct
	public void init() {
		NewsRedisUtil.redisClient = this.redisclient;
	}

	// 生存时间（最大）
	private static final int expireTimeMax = Integer
			.valueOf(PropertiesFactory.getProperty("redisConstant.properties", "expire_max_time"));
	// 生存时间（最小）
	private static final int expireTimeMin = Integer
			.valueOf(PropertiesFactory.getProperty("redisConstant.properties", "expire_min_time"));

	private static final String newsRedisPic = PropertiesFactory.getProperty("redisConstant.properties",
			"adks_new_pic");
	private static final String newsRedisDongtai = PropertiesFactory.getProperty("redisConstant.properties",
			"adks_new_dongtai");
	private static final String newsRedisZixun = PropertiesFactory.getProperty("redisConstant.properties",
			"adks_new_zixun");
	private static final String newsRedisGonggao = PropertiesFactory.getProperty("redisConstant.properties",
			"adks_new_gonggao");
	private static final String newsRedisNewest = PropertiesFactory.getProperty("redisConstant.properties",
			"adks_new_newest");

	private static final String newsSortRedis = PropertiesFactory.getProperty("redisConstant.properties",
			"adks_news_sort_tree");
	private static final String newsRedis = PropertiesFactory.getProperty("redisConstant.properties",
			"adks_news");

	/**
	 * 新增或修改
	 * 
	 * @param newsType
	 *            1:图片新闻;2培训动态;3新闻资讯;4通过公告;5最新新闻
	 * @param orgId
	 * @param map
	 * @return
	 */
	public static String addNews(String newsType, int orgId, List<Adks_news> list) {
		String result = "";
		if (newsType != null && list != null && list.size() > 0) {
			redisClient.setex(newsRedis+"_"+newsType+"_"+orgId, expireTimeMax, JSONObject.toJSONString(list));
//			switch (newsType) {
//			case 1:
//				result = redisClient.setex(newsRedisPic + orgId, expireTimeMax, JSONObject.toJSONString(list));
//				break;
//			case 2:
//				result = redisClient.setex(newsRedisDongtai + orgId, expireTimeMax, JSONObject.toJSONString(list));
//				break;
//			case 3:
//				result = redisClient.setex(newsRedisZixun + orgId, expireTimeMax, JSONObject.toJSONString(list));
//				break;
//			case 4:
//				result = redisClient.setex(newsRedisGonggao + orgId, expireTimeMax, JSONObject.toJSONString(list));
//				break;
//			case 5:
//				result = redisClient.setex(newsRedisNewest + orgId, expireTimeMax, JSONObject.toJSONString(list));
//				break;
//			}
			return result;
		}
		return null;
	}

	/**
	 * 新增或修改新闻分类
	 * 
	 * @param orgId
	 * @param list
	 * @return
	 */
	public static String addNewsSort(int orgId, List<Adks_news_sort> list) {
		if (list != null && list.size() > 0)
			return redisClient.setex(newsSortRedis + orgId, expireTimeMax, JSONObject.toJSONString(list));
		return null;
	}

	/**
	 * 获取新闻集合
	 * 
	 * @param newsType
	 *            1:图片新闻;2培训动态;3新闻资讯;4通过公告;5最新新闻;6新闻分类
	 * @param orgId
	 * @param isSuper
	 *            true,false
	 * @return
	 */
	public static String getNews(String newsType, int orgId, boolean isSuper) {
		String result = "";
		String Nodata = "Nodata";
		if(newsType!=null){
			if (isSuper) {
				redisClient.keys(newsRedis+"_"+newsType+"_"+"*");
			} else {
				if (redisClient.exists(newsRedis +"_"+newsType+"_"+orgId))
					result = redisClient.get(newsRedis +"_"+newsType+"_"+orgId);
				else
					redisClient.setex(newsRedis +"_"+newsType+"_"+orgId, expireTimeMin, Nodata);
			}
			return result;
		}
//		if (newsType != 0) {
//			switch (newsType) {
//			case 1:
//				if (isSuper) {
//					redisClient.keys(newsRedisPic + "*");
//				} else {
//					if (redisClient.exists(newsRedisPic + orgId))
//						result = redisClient.get(newsRedisPic + orgId);
//					else
//						redisClient.setex(newsRedisPic + orgId, expireTimeMin, Nodata);
//				}
//				break;
//			case 2:
//				if (isSuper) {
//					redisClient.keys(newsRedisDongtai + "*");
//				} else {
//					if (redisClient.exists(newsRedisDongtai + orgId))
//						result = redisClient.get(newsRedisDongtai + orgId);
//					else
//						redisClient.setex(newsRedisDongtai + orgId, expireTimeMin, Nodata);
//				}
//				break;
//			case 3:
//				if (isSuper) {
//					redisClient.keys(newsRedisZixun + "*");
//				} else {
//					if (redisClient.exists(newsRedisZixun + orgId))
//						result = redisClient.get(newsRedisZixun + orgId);
//					else
//						redisClient.setex(newsRedisZixun + orgId, expireTimeMin, Nodata);
//				}
//				break;
//			case 4:
//				if (isSuper) {
//					redisClient.keys(newsRedisGonggao + "*");
//				} else {
//					if (redisClient.exists(newsRedisGonggao + orgId))
//						result = redisClient.get(newsRedisGonggao + orgId);
//					else
//						redisClient.setex(newsRedisGonggao + orgId, expireTimeMin, Nodata);
//				}
//				break;
//			case 5:
//				if (isSuper) {
//					redisClient.keys(newsRedisNewest + "*");
//				} else {
//					if (redisClient.exists(newsRedisNewest + orgId))
//						result = redisClient.get(newsRedisNewest + orgId);
//					else
//						redisClient.setex(newsRedisNewest + orgId, expireTimeMin, Nodata);
//				}
//				break;
//
//			case 6:
//				if (isSuper) {
//					redisClient.keys(newsSortRedis + "*");
//				} else {
//					if (redisClient.exists(newsSortRedis + orgId))
//						result = redisClient.get(newsSortRedis + orgId);
//					else
//						redisClient.setex(newsSortRedis + orgId, expireTimeMin, Nodata);
//				}
//				break;
//
//			}
//			return result;
//		}
		return null;
	}

	/**
	 * 清空新闻相关缓存，修改、删除时调用
	 * 
	 * @param org_type
	 *            规则定义（orgId+"_"+type）
	 *            type-1:图片新闻;2培训动态;3新闻资讯;4通过公告;5最新新闻;6新闻分类;删除多条记录用逗号拼接;
	 *            例org_type="1_2,2_3,3_1"
	 * 
	 */
	public static void emptyNews(String org_type) {
		if (StringUtils.isNotEmpty(org_type)) {
			String[] num = org_type.split(",");
			if (num != null && num.length > 0) {
				for (String str : num) {
					String[] o_t = str.split("_");
					if (o_t != null && o_t.length > 0) {
						int orgId = Integer.valueOf(o_t[0]);
						String type = String.valueOf(o_t[1]);
						redisClient.delete(newsRedis+"_"+type+"_"+orgId);
//						switch (type) {
//						case 1:
//							redisClient.delete(newsRedisPic + orgId);
//							break;
//						case 2:
//							redisClient.delete(newsRedisDongtai + orgId);
//							break;
//						case 3:
//							redisClient.delete(newsRedisZixun + orgId);
//							break;
//						case 4:
//							redisClient.delete(newsRedisGonggao + orgId);
//							break;
//						case 5:
//							redisClient.delete(newsRedisNewest + orgId);
//							break;
//						case 6:
//							redisClient.delete(newsSortRedis + orgId);
//							break;
//						}
					}
				}
			}
		}
	}
}
