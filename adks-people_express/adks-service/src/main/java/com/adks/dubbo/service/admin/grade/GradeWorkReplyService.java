package com.adks.dubbo.service.admin.grade;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.dao.admin.grade.GradeWorkReplyDao;
@Service
public class GradeWorkReplyService extends BaseService<GradeWorkReplyDao>{

	@Autowired
	private GradeWorkReplyDao gradeWorkReplyDao;
	
	@Override
	protected GradeWorkReplyDao getDao() {
		return gradeWorkReplyDao;
	}
	
	/**
	 * 
	 * @Title saveGradeWorkReply
	 * @Description：保存作业修改
	 * @author xrl
	 * @Date 2017年4月28日
	 * @param map
	 */
	public void saveGradeWorkReply(Map<String, Object> map){
		gradeWorkReplyDao.saveGradeWorkReply(map);
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
		gradeWorkReplyDao.delGradeWorkReplyByGradeWorkReplyId(gradeWorkReplyId);
	}
	
	/**
	 * 
	 * @Title getGradeWorkReplyByUserIdAndGradeWorkId
	 * @Description：根据班级作业ID和用户ID获取班级学员提交作业
	 * @author xrl
	 * @Date 2017年5月20日
	 * @param gradeWorkId
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getGradeWorkReplyByUserIdAndGradeWorkId(Integer gradeWorkId,Integer userId){
		return gradeWorkReplyDao.getGradeWorkReplyByUserIdAndGradeWorkId(gradeWorkId,userId);
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
		return gradeWorkReplyDao.getGradeWorkReplyByGradeIdAndUserId(map);
	}

}
