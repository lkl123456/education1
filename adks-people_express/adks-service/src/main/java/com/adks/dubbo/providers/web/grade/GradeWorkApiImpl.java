package com.adks.dubbo.providers.web.grade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.grade.Adks_grade;
import com.adks.dubbo.api.data.grade.Adks_grade_course;
import com.adks.dubbo.api.data.grade.Adks_grade_exam;
import com.adks.dubbo.api.data.grade.Adks_grade_work;
import com.adks.dubbo.api.data.grade.Adks_grade_work_reply;
import com.adks.dubbo.api.interfaces.web.grade.GradeApi;
import com.adks.dubbo.api.interfaces.web.grade.GradeWorkApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.web.grade.GradeWebService;
import com.adks.dubbo.service.web.grade.GradeWorkWebService;

/**
 * 
 *班级作业的api
 */
public class GradeWorkApiImpl implements GradeWorkApi {

	@Autowired
	private GradeWorkWebService gradeWorkService;

	@Override
	public Integer getGradeWorkCount(Integer gradeId) {
		return gradeWorkService.getGradeWorkCount(gradeId);
	}

	@Override
	public Integer getGradeWorkUserNum(Integer gradeId, Integer userId) {
		return gradeWorkService.getGradeWorkUserNum(gradeId, userId);
	}

	@Override
	public List<Adks_grade_work_reply> getGradeWorkReplyByCon(Map map) {
		return gradeWorkService.getGradeWorkReplyByCon(map);
	}
	

}
