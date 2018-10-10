package com.adks.dubbo.dao.admin.grade;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import com.adks.dbclient.dao.singaltanent.BaseDao;

@Component
public class GradeWorkReplyDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_grade_work_reply";
	}
	
	/**
	 * 
	 * @Title saveGradeWorkReply
	 * @Description：保存批改作业
	 * @author xrl
	 * @Date 2017年4月28日
	 * @param map
	 */
	public void saveGradeWorkReply(Map<String, Object> map){
		Object[] obj = new Object[]{map.get("workScore"),map.get("isCorrent"),map.get("correctDate"),map.get("correctId"),map.get("correntName"),map.get("gradeWorkReplyId")};
		String sql = "update adks_grade_work_reply set workScore=?,isCorrent=?,correctDate=?,correctId=?,correntName=? where gradeWorkReplyId=? ";
		mysqlClient.update(sql, obj);
	}
	
	/**
	 * 
	 * @Title delGradeWorkReplyByGradeWorkReplyId
	 * @Description:删除班级学员作业
	 * @author xrl
	 * @Date 2017年4月28日
	 * @param gradeWorkReplyId
	 */
	public void delGradeWorkReplyByGradeWorkReplyId(Integer gradeWorkReplyId){
		Object[] obj = new Object[]{gradeWorkReplyId};
		String sql = "delete from adks_grade_work_reply where gradeWorkReplyId=?";
		mysqlClient.update(sql, obj);
	}
	
	/**
	 * 
	 * @Title getGradeWorkReplyByUserIdAndGradeWorkId
	 * @Description：根据班级作业ID和用户ID获取班级学员提交作业
	 * @author xrl
	 * @Date 2017年5月20日
	 * @param map
	 * @return
	 */
	public Map<String, Object> getGradeWorkReplyByUserIdAndGradeWorkId(Integer gradeWorkId,Integer userId){
		Object[] obj = new Object[]{gradeWorkId,userId};
		String sql = "select * from adks_grade_work_reply where workId=? and studentId=? ";
		return mysqlClient.queryForMap(sql, obj);
	}
	
	/**
	 * 
	 * @Title getGradeWorkReplyByGradeIdAndUserId
	 * @Description：班级学员作业列表
	 * @author xrl
	 * @Date 2017年6月16日
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getGradeWorkReplyByGradeIdAndUserId(Map<String, Object> map){
		Object[] obj = new Object[]{map.get("gradeId"),map.get("userId")};
		String sql = " select * "
				+ "from adks_grade_work_reply gwr "
				+ "left join adks_grade_work gw on gw.gradeWorkId=gwr.workId "
				+ "where gw.gradeId=? and gwr.studentId=? ";
		return mysqlClient.queryForList(sql, obj);
	}

}
