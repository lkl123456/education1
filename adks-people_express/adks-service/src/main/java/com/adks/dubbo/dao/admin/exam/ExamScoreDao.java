package com.adks.dubbo.dao.admin.exam;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.adks.dbclient.dao.singaltanent.BaseDao;
@Component
public class ExamScoreDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_exam_score";
	}
	
	/**
	 * 
	 * @Title removeGradeUserScore
	 * @Description：删除班级用户时，删除班级用户在班级中的考试记录
	 * @author xrl
	 * @Date 2017年5月5日
	 * @param map
	 */
	public void removeGradeUserScore(Map<String, Object> map) {
		Object[] obj = new Object[]{map.get("gradeId"),map.get("userId"),map.get("examId")};
		String sql = "delete from adks_exam_score "
				+ "where gradeId=? and userId=? and examId=? ";
		mysqlClient.update(sql, obj);
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
	public List<Map<String, Object>> getExamScoreList(Integer examId, Integer userId, Integer gradeId){
		Object[] obj = new Object[]{examId,userId,gradeId};
		String sql = " select * from adks_exam_score where examId="+examId+" and userId="+userId+" and gradeId="+gradeId;
		return mysqlClient.queryForList(sql,obj);
	}
	
	/**
	 * 
	 * @Title getExamScoreByGradeId
	 * @Description
	 * @author xrl
	 * @Date 2017年6月16日
	 * @param gradeId
	 * @return
	 */
	public List<Map<String, Object>> getExamScoreByGradeIdAndUserId(Map<String, Object> map){
		Object[] obj = new Object[]{map.get("gradeId"),map.get("userId")};
		String sql = " select * from adks_exam_score where gradeId=? and userId=? ";
		return mysqlClient.queryForList(sql, obj);
	}

}
