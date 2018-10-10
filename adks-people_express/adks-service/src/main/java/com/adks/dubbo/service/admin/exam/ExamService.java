package com.adks.dubbo.service.admin.exam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.exam.Adks_exam;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.admin.exam.ExamDao;

@Service
public class ExamService extends BaseService<BaseDao> {

	private static final Logger logger = LoggerFactory.getLogger(ExamService.class);

	@Autowired
	private ExamDao examDao;

	@Override
	protected ExamDao getDao() {
		return examDao;
	}

	public Adks_exam getExamById(Integer examId) {
		return examDao.getExamById(examId);
	}

	public void deleteExamByIds(String examIds) {
		examDao.deleteExam(examIds);
	}

	public Page<List<Map<String, Object>>> getExamListPage(Page<List<Map<String, Object>>> page) {
		return examDao.getExamListPage(page);
	}

	public Integer savePaper(Adks_exam exam) {
		Integer flag = 0;
		Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
		try {
			insertColumnValueMap.put("examName", exam.getExamName());
			insertColumnValueMap.put("examDesc", exam.getExamDesc());
			insertColumnValueMap.put("examDate", exam.getExamDate());
			insertColumnValueMap.put("startDate", exam.getStartDate());
			insertColumnValueMap.put("endDate", exam.getEndDate());
			insertColumnValueMap.put("scoreSum", exam.getScoreSum());
			insertColumnValueMap.put("passScore", exam.getPassScore());
			insertColumnValueMap.put("examTimes", exam.getExamTimes());
			insertColumnValueMap.put("orgId", exam.getOrgId());
			insertColumnValueMap.put("orgName", exam.getOrgName());
			insertColumnValueMap.put("creatorId", exam.getCreatorId());
			insertColumnValueMap.put("creatorName", exam.getCreatorName());
			if (exam.getExamId() != null) {
				Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
				updateWhereConditionMap.put("examId", exam.getExamId());
				flag = examDao.update(insertColumnValueMap, updateWhereConditionMap);
			} else {
				insertColumnValueMap.put("createTime", exam.getCreateTime());
				flag = examDao.insert(insertColumnValueMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public Map<String, Object> getExamByName(Map<String, Object> map) {
		return examDao.getExamByName(map);
	}
}
