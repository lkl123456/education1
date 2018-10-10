package com.adks.dubbo.service.admin.exam;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.dao.admin.exam.ExamScoreDao;
@Service
public class ExamScoreService extends BaseService<ExamScoreDao> {

	@Autowired
	private ExamScoreDao examScoreDao;
	
	@Override
	protected ExamScoreDao getDao() {
		return examScoreDao;
	}
	
	/**
	 * 
	 * @Title removeGradeUserScore
	 * @Description：删除班级用户时，删除班级用户在班级中的考试记录
	 * @author xrl
	 * @Date 2017年5月5日
	 * @param map
	 */
	public void removeGradeUserScore(Map<String, Object> map){
		examScoreDao.removeGradeUserScore(map);
	}
	
	/**
	 * 
	 * @Title getExamScoreList
	 * @Description：获取学员考试详情
	 * @author xrl
	 * @Date 2017年5月20日
	 * @param examId
	 * @param userId
	 * @param gradeId
	 * @return
	 */
	public List<Map<String, Object>> getExamScoreList(Integer examId, Integer userId, Integer gradeId) {
		return examScoreDao.getExamScoreList(examId,userId,gradeId);
	}
	
	/**
	 * 
	 * @Title getExamScoreByGradeId
	 * @Description：获取班级学员学课信息
	 * @author xrl
	 * @Date 2017年6月16日
	 * @param gradeId
	 * @return
	 */
	public List<Map<String , Object>> getExamScoreByGradeIdAndUserId(Map<String, Object> map){
		return examScoreDao.getExamScoreByGradeIdAndUserId(map);
	}

}
