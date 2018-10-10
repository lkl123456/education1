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
import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.api.data.course.Adks_course_sort;
import com.adks.dubbo.api.data.course.Adks_course_user;
import com.adks.dubbo.api.data.grade.Adks_grade_course;
import com.alibaba.fastjson.JSONObject;

/**
 * @author Administrator
 *
 */
@Lazy(false)
@Component
public class CourseRedisUtil {

	@Resource
	private RedisClient redisclient;
	public static RedisClient redisClient;

	@PostConstruct
	public void init() {
		CourseRedisUtil.redisClient = this.redisclient;
	}

	// 生存时间（最大）
	private static final int expireTimeMax = Integer.valueOf(PropertiesFactory.getProperty("redisConstant.properties", "expire_max_time"));
	// 生存时间（最小）
	private static final int expireTimeMin = Integer.valueOf(PropertiesFactory.getProperty("redisConstant.properties", "expire_min_time"));

	//最新课程(首页)
	public static final String adks_course_new = PropertiesFactory.getProperty("redisConstant.properties","adks_course_new");
	//大家正在看
	public static final String adks_course_looking = PropertiesFactory.getProperty("redisConstant.properties","adks_course_looking");
	//课程排行
	public static final String adks_course_rank = PropertiesFactory.getProperty("redisConstant.properties","adks_course_rank");
	//课程左侧分类树
	public static final String adks_course_sort_tree = PropertiesFactory.getProperty("redisConstant.properties","adks_course_sort_tree");
	//#专题班级课程
	public static final String adks_course_register_grade_course = PropertiesFactory.getProperty("redisConstant.properties","adks_course_register_grade_course");
	//#课程的相关课程
	public static final String adks_course_about = PropertiesFactory.getProperty("redisConstant.properties","adks_course_about");
	//#讲师主讲
	public static final String adks_course_author_course = PropertiesFactory.getProperty("redisConstant.properties","adks_course_author_course");
	//课程
	public static final String adks_course = PropertiesFactory.getProperty("redisConstant.properties","adks_course");
	
	/**
	 * 新增或修改
	 * 
	 * @param redisName （时间最短）
	 *            1.最新课程，3.课程排行
	 * @param orgId
	 * @param map
	 * @return
	 */
	public static String addCourse(String redisName, String orgCode, List<Adks_course> list) {
		String result = "";
		if (redisName != null && list != null && list.size() > 0) {
			redisClient.setex(redisName+"_"+orgCode, expireTimeMin, JSONObject.toJSONString(list));
			return result;
		}
		return null;
	}
	
	/**
	 * 新增或修改
	 * 
	 * @param redisName （时间最长）
	 *          讲师主讲，相关课程
	 * @param orgId
	 * @param map
	 * @return
	 */
	public static String addCourseTimeLong(String redisName, String orgCode, List<Adks_course> list) {
		String result = "";
		if (redisName != null && list != null && list.size() > 0) {
			redisClient.setex(redisName+"_"+orgCode, expireTimeMax, JSONObject.toJSONString(list));
			return result;
		}
		return null;
	}
	
	/**
	 * 新增或修改
	 * 
	 * @param redisName （时间最短）
	 *          大家都在看
	 * @param orgId
	 * @param map
	 * @return
	 */
	public static String addCourseUser(String redisName, String orgCode, List<Adks_course_user> list) {
		String result = "";
		if (redisName != null && list != null && list.size() > 0) {
			redisClient.setex(redisName+"_"+orgCode, expireTimeMin, JSONObject.toJSONString(list));
			return result;
		}
		return null;
	}
	
	/**
	 * 新增或修改课程树
	 * 
	 * @param redisName （时间最短）
	 *           课程分类
	 * @param orgId
	 * @param map
	 * @return
	 */
	public static String addCourseSort(String redisName, List<Adks_course_sort> list) {
		String result = "";
		if (redisName != null && list != null && list.size() > 0) {
			redisClient.setex(redisName, expireTimeMax, JSONObject.toJSONString(list));
			return result;
		}
		return null;
	}
	
	/**
	 * 新增或修改专题班级课程
	 * 
	 * @param redisName （时间最短）
	 *            1.最新课程，3.课程排行
	 * @param orgId
	 * @param map
	 * @return
	 */
	public static String addGradeCourse(String redisName,Integer gradeId, List<Adks_grade_course> list) {
		String result = "";
		if (redisName != null && list != null && list.size() > 0) {
			redisClient.setex(redisName+"_"+gradeId, expireTimeMax, JSONObject.toJSONString(list));
			return result;
		}
		return null;
	}

	/**
	 * 新增或修改课程分类
	 * 
	 * @param orgId
	 * @param list
	 * @return
	 */
	public static String addCourseSort(int orgId, List<Adks_course_sort> list) {
		if (list != null && list.size() > 0)
			return redisClient.setex(adks_course_sort_tree + orgId, expireTimeMax, JSONObject.toJSONString(list));
		return null;
	}

	/**
	 * 获取课程集合
	 * 
	 * @param redisName
	 *             1.最新课程，2大家正在看，3.课程排行
	 * @param orgId
	 * @param isSuper
	 *            true,false
	 * @return
	 */
	public static String getCourse(String redisName, String orgCode, boolean isSuper) {
		String result = "";
		String Nodata = "Nodata";
		if(redisName!=null){
			if (isSuper) {
				Set<String> reSet=redisClient.keys(redisName+"_"+"*");
				if(reSet!=null && reSet.size()>0){
					String reName=reSet.iterator().next();
					result = redisClient.get(reName);
				}
			} else {
				if (redisClient.exists(redisName+"_"+orgCode))
					result = redisClient.get(redisName+"_"+orgCode);
				else
					redisClient.setex(redisName+"_"+orgCode, expireTimeMin, Nodata);
			}
			return result;
		}
		return null;
	}
	/**
	 * 获取课程分类集合
	 * 
	 * @param redisName
	 *            
	 * @param orgId
	 * @param isSuper
	 *            true,false
	 * @return
	 */
	public static String getCourseSort(String redisName) {
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
	 * 获取专题班级课程集合
	 * 
	 * @param redisName
	 *            
	 * @param orgId
	 * @param isSuper
	 *            true,false
	 * @return
	 */
	public static String getGradeCourse(String redisName,Integer gradeId) {
		String result = "";
		String Nodata = "Nodata";
		if(redisName!=null){
			if (redisClient.exists(redisName+"_"+gradeId))
				result = redisClient.get(redisName+"_"+gradeId);
			else
				redisClient.setex(redisName+"_"+gradeId, expireTimeMax, Nodata);
			return result;
		}
		return null;
	}

	/**
	 * 清空新闻相关缓存，修改、删除时调用
	 * 
	 * @param org_type
	 *            规则定义（orgId+"_"+type）
	 *            type-new:最新课程;looking大家正在看;rank课程排行;sort_tree分类树;register_grade_course专题班级课程;
	 *            userLook个人中心观看记录;collect个人中心我的收藏；删除多条记录用逗号拼接;
	 *            例org_type="1-new,2-looking,3-rank"
	 * 
	 */
	public static void emptyCourse(String org_type) {
		if (StringUtils.isNotEmpty(org_type)) {
			String[] num = org_type.split(",");
			if (num != null && num.length > 0) {
				for (String str : num) {
					String[] o_t = str.split("-");
					if (o_t != null && o_t.length > 0) {
						String orgId = String.valueOf(o_t[0]);
						String type = String.valueOf(o_t[1]);
						redisClient.delete(adks_course+type+"__"+orgId);
					}
				}
			}
		}
	}
	/**
	 * 直接删除某一个模块（例如最新课程）
	 * 
	 * @param redisType
	 *        new:最新课程;looking大家正在看;rank课程排行;sort_tree分类树;register_grade_course专题班级课程;
	 *            userLook个人中心观看记录;collect个人中心我的收藏 ;删除多条记录用逗号拼接;
	 * 
	 */
	public static void emptyCourseAll(String redisType) {
		if (StringUtils.isNotEmpty(redisType)) {
			String[] num = redisType.split(",");
			if (num != null && num.length > 0) {
				for (String str : num) {
					Set<String> keys=redisClient.keys(adks_course+str+"*");
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
