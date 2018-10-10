package com.adks.dubbo.util;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.adks.commons.util.PropertiesFactory;
import com.adks.dbclient.redis.RedisClient;
import com.adks.dubbo.api.data.grade.Adks_grade_course;
import com.adks.dubbo.api.data.news.Adks_news;
import com.alibaba.fastjson.JSONObject;

@Lazy(false)
@Component
public class GradeRedisUtil {

	@Resource
	private RedisClient redisclient;
	public static RedisClient redisClient;

	@PostConstruct
	public void init() {
		GradeRedisUtil.redisClient = this.redisclient;
	}
	
	// 生存时间（最大）
	private static final int expireTimeMax = Integer.valueOf(PropertiesFactory.getProperty("redisConstant.properties", "expire_max_time"));
	// 生存时间（最小）
	private static final int expireTimeMin = Integer.valueOf(PropertiesFactory.getProperty("redisConstant.properties", "expire_min_time"));
	//班级
	private static final String gradeReids = String.valueOf(PropertiesFactory.getProperty("redisConstant.properties", "adks_grade"));
	//班级课程-全部
	private static final String gradeAllCourseReids = String.valueOf(PropertiesFactory.getProperty("redisConstant.properties", "adks_grade_course_all"));
	//班级课程-必修
	private static final String gradeRequiredCourseReids = String.valueOf(PropertiesFactory.getProperty("redisConstant.properties", "adks_grade_course_required"));
	//班级课程-选修
	private static final String gradeOptionalCourseReids = String.valueOf(PropertiesFactory.getProperty("redisConstant.properties", "adks_grade_course_optional"));
	//班级考试
	private static final String gradeExamPaperReids = String.valueOf(PropertiesFactory.getProperty("redisConstant.properties", "adks_grade_exampaper"));
	
	/**
	 * 
	 * @Title addGradeCourse
	 * @Description
	 * @author xrl
	 * @Date 2017年6月2日
	 * @param type  全部：1    必修：2    选修：3
	 * @param gradeId  班级Id
	 * @param list   班级课程
	 * @return
	 */
	public static String addGradeCourse(int type, int gradeId,List<Adks_grade_course> list) {
		String result = "";
		if (type != 0 && list != null && list.size() > 0) {
			switch (type) {
			case 1:
				result = redisClient.setex(gradeAllCourseReids + gradeId, expireTimeMax, JSONObject.toJSONString(list));
				break;
			case 2:
				result = redisClient.setex(gradeRequiredCourseReids + gradeId, expireTimeMax, JSONObject.toJSONString(list));
				break;
			case 3:
				result = redisClient.setex(gradeOptionalCourseReids + gradeId, expireTimeMax, JSONObject.toJSONString(list));
				break;
			}
			return result;
		}
		return null;
	}
	
	/**
	 * 
	 * @Title getGradeCourse
	 * @Description：获取的单个班级课程列表
	 * @author xrl
	 * @Date 2017年6月7日
	 * @param redisName
	 * @return
	 */
	public static String getGradeCourse(int type, int gradeId){
		String result = "";
		String Nodata = "Nodata";
		if (type!=0&&gradeId!=0) {
			switch (type) {
			case 1:
				result = redisClient.get(gradeAllCourseReids + gradeId);
				break;
			case 2:
				result = redisClient.get(gradeRequiredCourseReids + gradeId);
				break;
			case 3:
				result = redisClient.get(gradeOptionalCourseReids + gradeId);
				break;
			}
			if(result!=null){
				return result;
			}
		}
		return null;
	}
	
}
