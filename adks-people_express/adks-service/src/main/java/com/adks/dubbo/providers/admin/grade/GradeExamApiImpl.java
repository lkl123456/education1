package com.adks.dubbo.providers.admin.grade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.interfaces.admin.grade.GradeExamApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.admin.grade.GradeExamService;

/**
 * 
 * ClassName GradeExamApiImpl
 * 
 * @Description：班级考试Api实现
 * @author xrl
 * @Date 2017年4月1日
 */
public class GradeExamApiImpl implements GradeExamApi {

	@Autowired
	private GradeExamService gradeExamService;

	/**
	 * 
	 * @Title getGradeExamListPage
	 * @Description：班级已选考试分页列表
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param page
	 * @return
	 */
	@Override
	public Page<List<Map<String, Object>>> getGradeExamListPage(Page<List<Map<String, Object>>> page) {
		return gradeExamService.getGradeExamListPage(page);
	}

	/**
	 * 
	 * @Title saveGradeExam
	 * @Description：添加班级考试
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param insertColumnValueMap
	 */
	@Override
	public Integer saveGradeExam(Map<String, Object> insertColumnValueMap) {
		Integer dataId=gradeExamService.insert(insertColumnValueMap);
		return dataId;
	}

	/**
	 * 
	 * @Title removeGradeExam
	 * @Description：移除班级考试
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param map
	 */
	@Override
	public void removeGradeExam(Map<String, Object> map) {
		gradeExamService.removeGradeExam(map);
	}

	/**
	 * 
	 * @Title getExamByExamId
	 * @Description：根据examId获取考试信息
	 * @author xrl
	 * @Date 2017年4月1日
	 * @return
	 */
	@Override
	public Map<String, Object> getExamByExamId(Integer examId) {
		return gradeExamService.getExamByExamId(examId);
	}

	/**
	 * 
	 * @Title getGradeExamByGradeId
	 * @Description：班级已选考试
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param gradeId
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getGradeExamByGradeId(Integer gradeId) {
		Map<String, Object> queryColumnValueMap = new HashMap<>();
		queryColumnValueMap.put("gradeId", gradeId);
		List<String> returnList = new ArrayList<>();
		returnList.add("examId");
		return gradeExamService.query(queryColumnValueMap, returnList, null);
	}

	/**
	 * 
	 * @Title getSelExamListPage
	 * @Description：班级未选考试
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param page
	 * @return
	 */
	@Override
	public Page<List<Map<String, Object>>> getSelExamListPage(Page<List<Map<String, Object>>> page) {
		return gradeExamService.getSelExamListPage(page);
	}

	/**
	 * 
	 * @Title getGradeExamInfoListPage
	 * @Description：考试授权的所有班级
	 * @author shn
	 * @Date 2017年4月14日11:49:17
	 * @param page
	 * @return
	 */
	@Override
	public Page<List<Map<String, Object>>> getGradeExamInfoListPage(Page<List<Map<String, Object>>> page) {
		return gradeExamService.getGradeExamInfoListPage(page);
	}

}
