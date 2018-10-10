package com.adks.dubbo.api.interfaces.admin.grade;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.commons.Page;

/**
 * 
 * ClassName GradeExam
 * 
 * @Description：班级考试Api
 * @author xrl
 * @Date 2017年4月1日
 */
public interface GradeExamApi {

	/**
	 * 
	 * @Title getGradeExamListPage
	 * @Description：班级已选考试分页列表
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradeExamListPage(Page<List<Map<String, Object>>> page);

	/**
	 * 
	 * @Title getSelExamListPage
	 * @Description：班级未选考试
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getSelExamListPage(Page<List<Map<String, Object>>> page);

	/**
	 * 
	 * @Title getExamByExamId
	 * @Description：根据examId获取考试信息
	 * @author xrl
	 * @Date 2017年4月1日
	 * @return
	 */
	public Map<String, Object> getExamByExamId(Integer examId);

	/**
	 * 
	 * @Title saveGradeExam
	 * @Description：添加班级考试
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param map
	 */
	public Integer saveGradeExam(Map<String, Object> map);

	/**
	 * 
	 * @Title removeGradeExam
	 * @Description：移除班级考试
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param map
	 */
	public void removeGradeExam(Map<String, Object> map);

	/**
	 * 
	 * @Title getGradeExamByGradeId
	 * @Description:班级已选考试
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param gradeId
	 * @return
	 */
	public List<Map<String, Object>> getGradeExamByGradeId(Integer gradeId);

	/**
	 * @param page
	 * @return
	 */
	Page<List<Map<String, Object>>> getGradeExamInfoListPage(Page<List<Map<String, Object>>> page);
}
