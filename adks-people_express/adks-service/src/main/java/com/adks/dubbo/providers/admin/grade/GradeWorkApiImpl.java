package com.adks.dubbo.providers.admin.grade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.common.util.Hash;
import org.springframework.beans.factory.annotation.Autowired;

import com.adks.commons.oss.OSSUploadUtil;
import com.adks.commons.util.PropertiesFactory;
import com.adks.dubbo.api.data.grade.Adks_grade;
import com.adks.dubbo.api.data.grade.Adks_grade_work;
import com.adks.dubbo.api.data.grade.Adks_grade_work_reply;
import com.adks.dubbo.api.interfaces.admin.grade.GradeWorkApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.admin.grade.GradeWorkReplyService;
import com.adks.dubbo.service.admin.grade.GradeWorkService;

public class GradeWorkApiImpl implements GradeWorkApi {

	@Autowired
	private GradeWorkService gradeWorkService;
	@Autowired
	private GradeWorkReplyService gradeWorkReplyService;
	
	/**
	 * 
	 * @Title getGradesJson
	 * @Description:获取班级树信息
	 * @author xrl
	 * @Date 2017年3月25日
	 * @param orgId
	 * @return
	 */
	@Override
	public List<Adks_grade> getGradesJson(Map<String, Object> map) {
		return gradeWorkService.getGradesJson(map);
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
	@Override
	public Page<List<Map<String, Object>>> getGradeWorkListPage(Page<List<Map<String, Object>>> paramPage) {
		return gradeWorkService.getGradeWorkListPage(paramPage);
	}

	/**
	 * 
	 * @Title saveGradeWork
	 * @Description：保存班级作业
	 * @author xrl
	 * @Date 2017年4月2日
	 * @param gradeWork
	 */
	@Override
	public Integer saveGradeWork(Adks_grade_work gradeWork) {
		Map<String,Object> insertColumnValueMap = new HashMap<String,Object>();
		insertColumnValueMap.put("gradeId",gradeWork.getGradeId());
		insertColumnValueMap.put("gradeName",gradeWork.getGradeName());
		insertColumnValueMap.put("workTitle",gradeWork.getWorkTitle());
		insertColumnValueMap.put("workContent",gradeWork.getWorkContent());
		insertColumnValueMap.put("maxSize",gradeWork.getMaxSize());
		insertColumnValueMap.put("leastSize",gradeWork.getLeastSize());
		insertColumnValueMap.put("startDate",gradeWork.getStartDate());
		insertColumnValueMap.put("endDate",gradeWork.getEndDate());
		insertColumnValueMap.put("releaseState",gradeWork.getReleaseState());
		insertColumnValueMap.put("allowFile",gradeWork.getAllowFile());
		insertColumnValueMap.put("filePath",gradeWork.getFilePath());
		insertColumnValueMap.put("creatorId",gradeWork.getCreatorId());
		insertColumnValueMap.put("creatorName",gradeWork.getCreatorName());
		insertColumnValueMap.put("createTime",gradeWork.getCreateTime());
		Integer gradeWorkId=0;
		if(gradeWork.getGradeWorkId()!= null){
			Map<String,Object> updateWhereConditionMap = new HashMap<String,Object>();
			updateWhereConditionMap.put("gradeWorkId", gradeWork.getGradeWorkId());
			gradeWorkId=gradeWorkService.update(insertColumnValueMap, updateWhereConditionMap);
		}else{
			gradeWorkId=gradeWorkService.insert(insertColumnValueMap);
		}
		return gradeWorkId;
	}

	/**
	 * 
	 * @Title deleteGradeWorkByIds
	 * @Description：删除班级作业
	 * @author xrl
	 * @Date 2017年4月26日
	 * @param ids
	 */
	@Override
	public void deleteGradeWorkByIds(String ids) {
		gradeWorkService.deleteGradeWorkByIds(ids);
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
	@Override
	public Page<List<Map<String, Object>>> getGradeWorkReplyListPage(Page<List<Map<String, Object>>> paramPage) {
		return gradeWorkService.getGradeWorkReplyListPage(paramPage);
	}

	/**
	 * 
	 * @Title saveGradeWorkReply
	 * @Description:保存作业批改
	 * @author xrl
	 * @Date 2017年4月28日
	 * @param map
	 */
	@Override
	public void saveGradeWorkReply(Map<String, Object> map) {
		gradeWorkReplyService.saveGradeWorkReply(map);
	}

	/**
	 * 
	 * @Title delGradeWorkReplyByGradeWorkReplyId
	 * @Description:删除班级学员作业
	 * @author xrl
	 * @Date 2017年4月28日
	 * @param gradeWorkReplyId
	 */
	@Override
	public void delGradeWorkReplyByGradeWorkReplyId(Integer gradeWorkReplyId) {
		gradeWorkReplyService.delGradeWorkReplyByGradeWorkReplyId(gradeWorkReplyId);
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
	@Override
	public Map<String, Object> checkWorkTitle(Map<String, Object> map) {
		return gradeWorkService.checkWorkTitle(map);
	}

	/**
	 * 
	 * @Title getGradeWorkListByGradeId
	 * @Description：获取班级作业
	 * @author xrl
	 * @Date 2017年5月20日
	 * @param gradeId
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getGradeWorkListByGradeId(Integer gradeId) {
		return gradeWorkService.getGradeWorkListByGradeId(gradeId);
	}

	/**
	 * 
	 * @Title getGradeWorkReplyByUserId
	 * @Description：根据班级作业ID和用户ID获取班级学员提交作业
	 * @author xrl
	 * @Date 2017年5月20日
	 * @param gradeWorkId
	 * @param userId
	 * @return
	 */
	@Override
	public Map<String, Object> getGradeWorkReplyByUserIdAndGradeWorkId(Integer gradeWorkId, Integer userId) {
		return gradeWorkReplyService.getGradeWorkReplyByUserIdAndGradeWorkId(gradeWorkId,userId);
	}

	@Override
	public List<Map<String, Object>> getGradeWorkReplyByGradeIdAndUserId(Map<String, Object> map) {
		return gradeWorkReplyService.getGradeWorkReplyByGradeIdAndUserId(map);
	}
	
}
