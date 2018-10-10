package com.adks.dubbo.service.admin.grade;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.grade.Adks_grade;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.admin.grade.GradeWorkDao;

import org.springframework.stereotype.Service;

@Service
public class GradeWorkService extends BaseService<GradeWorkDao> {

	@Autowired
	private GradeWorkDao gradeWorkDao;
	
	@Override
	protected GradeWorkDao getDao() {
		return gradeWorkDao;
	}
	
	/**
	 * 
	 * @Title getGradesJson
	 * @Description：获取班级树信息
	 * @author xrl
	 * @Date 2017年3月29日
	 * @param orgId
	 * @return
	 */
	public List<Adks_grade> getGradesJson(Map<String, Object> map){
		return gradeWorkDao.getGradesJson(map);
	}

	/**
	 * 
	 * @Title getGradeWorkListPage
	 * @Description：获取班级作业分页列表
	 * @author xrl
	 * @Date 2017年3月25日
	 * @param paramPage
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradeWorkListPage(Page<List<Map<String, Object>>> paramPage) {
		return gradeWorkDao.getGradeWorkListPage(paramPage);
	}
	
	/**
	 * 
	 * @Title deleteGradeWorkByIds
	 * @Description：删除班级作业
	 * @author xrl
	 * @Date 2017年4月26日
	 * @param ids
	 */
	public void deleteGradeWorkByIds(String ids){
		gradeWorkDao.deleteGradeWorkByIds(ids);
	}
	
	/**
	 * 
	 * @Title delGradeWorkByGradeId
	 * @Description:删除班级作业（论文）
	 * @author xrl
	 * @Date 2017年4月27日
	 * @param gradeId
	 */
	public void delGradeWorkByGradeId(Integer gradeId){
		gradeWorkDao.delGradeWorkByGradeId(gradeId);
	}
	
	/**
	 * 
	 * @Title getGradeWorkReplyListPage
	 * @Description：班级学员提交作业分页列表
	 * @author xrl
	 * @Date 2017年4月28日
	 * @param paramPage
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradeWorkReplyListPage(Page<List<Map<String, Object>>> paramPage) {
		return gradeWorkDao.getGradeWorkReplyListPage(paramPage);
	}
	
	/**
	 * 
	 * @Title removeGradeWorkReply
	 * @Description:删除班级时，删除班级学员提交过的作业
	 * @author xrl
	 * @Date 2017年5月5日
	 * @param map
	 */
	public void removeGradeWorkReply(Map<String, Object> map){
		gradeWorkDao.removeGradeWorkReply(map);
	}
	
	/**
	 * 
	 * @Title getGradeWotkByGradeId
	 * @Description：根据班级ID获取作业ID
	 * @author xrl
	 * @Date 2017年5月5日
	 * @param gradeId
	 * @return
	 */
	public List<Map<String, Object>> getGradeWotkListByGradeId(Integer gradeId){
		return gradeWorkDao.getGradeWotkListByGradeId(gradeId);
	}
	
	/**
	 * 
	 * @Title checkWorkTitle
	 * @Description：检查班级作业名在该班级中是否重复
	 * @author xrl
	 * @Date 2017年5月9日
	 * @param map
	 * @return
	 */
	public Map<String, Object> checkWorkTitle(Map<String, Object> map){
		return gradeWorkDao.checkWorkTitle(map);
	}
	
	/**
	 * 
	 * @Title getGradeWorkListByGradeId
	 * @Description:获取班级列表
	 * @author xrl
	 * @Date 2017年5月20日
	 * @param gradeId
	 * @return
	 */
	public List<Map<String, Object>> getGradeWorkListByGradeId(Integer gradeId){
		return gradeWorkDao.getGradeWorkListByGradeId(gradeId);
	}
}
