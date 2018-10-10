package com.adks.dubbo.util;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.adks.commons.util.PropertiesFactory;
import com.adks.dbclient.redis.RedisClient;
import com.adks.dubbo.api.data.grade.Adks_grade_course;
import com.adks.dubbo.api.data.paper.Adks_paper_question;
import com.alibaba.fastjson.JSONObject;

@Lazy(false)
@Component
public class ExamPaperRedisUtil {

	@Resource
	private RedisClient redisclient;
	public static RedisClient redisClient;

	@PostConstruct
	public void init() {
		ExamPaperRedisUtil.redisClient = this.redisclient;
	}
	
	// 生存时间（最大）
	private static final int expireTimeMax = Integer.valueOf(PropertiesFactory.getProperty("redisConstant.properties", "expire_max_time"));
	// 生存时间（最小）
	private static final int expireTimeMin = Integer.valueOf(PropertiesFactory.getProperty("redisConstant.properties", "expire_min_time"));
	//班级考试
	private static final String gradeExamPaperReids = String.valueOf(PropertiesFactory.getProperty("redisConstant.properties", "adks_grade_exampaper"));
	
	/**
	 * 
	 * @Title addExamPaper
	 * @Description:新增/更新  试卷中试题集合
	 * @author xrl
	 * @Date 2017年6月13日
	 * @param paperId
	 * @param list
	 * @return
	 */
	public static String addExamPaper(int paperId,List<Adks_paper_question> list) {
		String result="";
		if(paperId!=0&&list != null && list.size() > 0){
			result =redisClient.setex(gradeExamPaperReids+paperId, expireTimeMax, JSONObject.toJSONString(list));
		}
		return result;
	}
	
	/**
	 * 
	 * @Title getExamPaper
	 * @Description：获取试卷中的试题集合
	 * @author xrl
	 * @Date 2017年6月13日
	 * @param paperId
	 * @return
	 */
	public static String getExamPaper(int paperId) {
		String result="";
		String Nodata = "Nodata";
		if (redisClient.exists(gradeExamPaperReids + paperId)){
			result = redisClient.get(gradeExamPaperReids + paperId);
		}else{
			redisClient.setex(gradeExamPaperReids + paperId, expireTimeMax, Nodata);
		}
		return result;
	}
	
	/**
	 * 
	 * @Title delExamPaper
	 * @Description:删除试卷
	 * @author xrl
	 * @Date 2017年6月13日
	 * @param paperId
	 * @return
	 */
	public static boolean delExamPaper(int paperId) {
		boolean result=false;
		if (redisClient.exists(gradeExamPaperReids + paperId)){
			redisClient.delete(gradeExamPaperReids + paperId);
			result=true;
		}
		return result;
	}
	
}
