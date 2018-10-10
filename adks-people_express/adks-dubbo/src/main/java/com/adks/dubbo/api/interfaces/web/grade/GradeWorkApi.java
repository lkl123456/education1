package com.adks.dubbo.api.interfaces.web.grade;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.grade.Adks_grade;
import com.adks.dubbo.api.data.grade.Adks_grade_course;
import com.adks.dubbo.api.data.grade.Adks_grade_exam;
import com.adks.dubbo.api.data.grade.Adks_grade_user;
import com.adks.dubbo.api.data.grade.Adks_grade_work;
import com.adks.dubbo.api.data.grade.Adks_grade_work_reply;
import com.adks.dubbo.commons.Page;

/**
 * 
 * ClassName GradeApi
 * @Description：班级API
 * @author xrl
 * @Date 2017年3月22日
 */
public interface GradeWorkApi {

	public Integer getGradeWorkCount(Integer gradeId);
	
	public Integer getGradeWorkUserNum(Integer gradeId,Integer userId);
	
	public List<Adks_grade_work_reply> getGradeWorkReplyByCon(Map map);
}
