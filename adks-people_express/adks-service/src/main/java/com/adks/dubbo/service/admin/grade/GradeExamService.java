package com.adks.dubbo.service.admin.grade;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.admin.grade.GradeExamDao;
/**
 * 
 * ClassName GradeExamService
 * @Description：班级考试
 * @author xrl
 * @Date 2017年4月1日
 */
@Service
public class GradeExamService extends BaseService<GradeExamDao> {

	@Override
	protected GradeExamDao getDao() {
		return gradeExamDao;
	}

	@Autowired
	private GradeExamDao gradeExamDao;
	
	/**
	 * 
	 * @Title getGradeExamListPage
	 * @Description：班级已选考试分页列表
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradeExamListPage(Page<List<Map<String, Object>>> page){
		return gradeExamDao.getGradeExamListPage(page);
	}
	
	/**
	 * 
	 * @Title getExamByExamId
	 * @Description：根据examId获取考试信息
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param map
	 * @return
	 */
	public Map<String, Object> getExamByExamId(Integer examId){
		return gradeExamDao.getExamByExamId(examId);
	}
	
	/**
	 * 
	 * @Title removeGradeExam
	 * @Description:移除班级考试
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param map
	 */
	public void removeGradeExam(Map<String, Object> map){
		gradeExamDao.removeGradeExam(map);
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
	public Page<List<Map<String, Object>>> getSelExamListPage(Page<List<Map<String, Object>>> page){
		return gradeExamDao.getSelExamListPage(page);
	}
	
	/**
	 * 
	 * @Title getGradeExamInfoListPage
	 * @Description：考试授权班级的分页列表
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradeExamInfoListPage(Page<List<Map<String, Object>>> page){
		return gradeExamDao.getGradeExamInfoListPage(page);
	}
	
	/**
	 * 
	 * @Title delGradeExamByGradeId
	 * @Description：根据gradeId删除班级考试
	 * @author xrl
	 * @Date 2017年4月27日
	 * @param gradeId
	 */
	public void delGradeExamByGradeId(Integer gradeId){
		gradeExamDao.delGradeExamByGradeId(gradeId);
	}
	
	/**
	 * 
	 * @Title getGradeExamListByGradeId
	 * @Description：根据班级ID查询班级考试
	 * @author xrl
	 * @Date 2017年5月5日
	 * @param gradeId
	 * @return
	 */
	public List<Map<String, Object>> getGradeExamListByGradeId(Integer gradeId){
		return gradeExamDao.getGradeExamListByGradeId(gradeId);
	}
}
