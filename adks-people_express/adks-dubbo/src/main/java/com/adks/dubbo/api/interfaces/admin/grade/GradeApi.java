package com.adks.dubbo.api.interfaces.admin.grade;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.grade.Adks_grade;
import com.adks.dubbo.api.data.grade.Adks_grade_user;
import com.adks.dubbo.commons.Page;

/**
 * 
 * ClassName GradeApi
 * @Description：班级API
 * @author xrl
 * @Date 2017年3月22日
 */
public interface GradeApi {

	/**
	 * 
	 * @Title getGradeListPage
	 * @Description:班级分页列表
	 * @author xrl
	 * @Date 2017年3月22日
	 * @param paramPage
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradeListPage(Page<List<Map<String, Object>>> paramPage);
	
	/**
	 * 
	 * @Title saveGrade
	 * @Description：保存班级
	 * @author xrl
	 * @Date 2017年3月22日
	 * @param grade
	 * @return
	 */
	public Integer saveGrade(Adks_grade grade);
	
	/**
	 * 
	 * @Title loadEditGradeFormData
	 * @Description：获取班级相关信息
	 * @author xrl
	 * @Date 2017年3月23日
	 * @param gradeId
	 * @return
	 */
	public Map<String, Object> loadEditGradeFormData(Integer gradeId);
	
	//得到所有的班级
	public List<Adks_grade> gradeTopCurrentList(Map map);
	
	/**
	 * 
	 * @Title getGradeByGradeId
	 * @Description:根据班级ID查询班级信息
	 * @author xrl
	 * @Date 2017年4月27日
	 * @param gradeId
	 * @return
	 */
	public Map<String, Object> getGradeByGradeId(Integer gradeId);
	
	/**
	 * 
	 * @Title delGradeByGradeIdId
	 * @Description:删除班级
	 * @author xrl
	 * @Date 2017年4月27日
	 * @param gradeId
	 */
	public void delGradeByGradeIdId(Integer gradeId);
	
	/**
	 * 
	 * @Title getGradesJson
	 * @Description:根据orgCode获取班级
	 * @author xrl
	 * @Date 2017年4月28日
	 * @param map
	 * @return
	 */
	public List<Adks_grade> getGradesJson(Map<String, Object> map);
	
	/**
	 * 
	 * @Title checkGradeName
	 * @Description:根据班级名称查询班级信息
	 * @author xrl
	 * @Date 2017年5月8日
	 * @param map
	 * @return
	 */
	public Map<String, Object> checkGradeName(Map<String, Object> map);
	
	/**
	 * 
	 * @Title updateGradeUserNum
	 * @Description：更新班级人数
	 * @author xrl
	 * @Date 2017年5月15日
	 * @param map
	 * @return
	 */
	public void updateGradeUserNum(Map<String,Object> paramValue,Map<String, Object> map);
}
