package com.adks.dubbo.providers.admin.question;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dbclient.mysql.MysqlClient;
import com.adks.dubbo.api.data.question.Adks_question;
import com.adks.dubbo.api.interfaces.admin.question.QuestionApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.admin.question.QuestionService;
import com.adks.dubbo.service.admin.question.QuestionSortService;

public class QuestionApiImpl implements QuestionApi {

	@Autowired
	private MysqlClient mysqlClient;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private QuestionSortService questionSortService;

	@Override
	public Page<List<Map<String, Object>>> getQuestionListPage(Page<List<Map<String, Object>>> paramPage) {
		return questionService.getQuestionListPage(paramPage);
	}

	@Override
	public List<Map<String, Object>> getRandomQuestionPaper(String qsSortCode, int orgId) {
		return questionService.getRandomQuestionPaper(qsSortCode, orgId);
	}

	public List<Map<String, Object>> getRandomQuestion(Integer questionType, Integer num) {
		return questionService.getRandomQuestion(questionType, num);
	}

	@Override
	public Integer saveQuestion(Adks_question question) {
		Integer flag = 0;
		Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
		try {
			insertColumnValueMap.put("questionName", question.getQuestionName());
			insertColumnValueMap.put("questionType", question.getQuestionType());
			insertColumnValueMap.put("questionValue", question.getQuestionValue());
			if (question.getQuestionType() == 1 || question.getQuestionType() == 2) {
				insertColumnValueMap.put("optionA", question.getOptionA());
				insertColumnValueMap.put("optionB", question.getOptionB());
				insertColumnValueMap.put("optionC", question.getOptionC());
				insertColumnValueMap.put("optionD", question.getOptionD());
				insertColumnValueMap.put("optionE", question.getOptionE());
				insertColumnValueMap.put("optionF", question.getOptionF());
				insertColumnValueMap.put("optionG", question.getOptionG());
				insertColumnValueMap.put("optionH", question.getOptionH());
			} else if (question.getQuestionType() == 3) {
				insertColumnValueMap.put("optionA", question.getOptionA());
				insertColumnValueMap.put("optionB", question.getOptionB());
			}

			insertColumnValueMap.put("anwsers", question.getAnwsers());
			insertColumnValueMap.put("qtSortId", question.getQtSortId());
			insertColumnValueMap.put("qsSortName", question.getQsSortName());
			insertColumnValueMap.put("qsSortCode", question.getQsSortCode());
			insertColumnValueMap.put("courseId", question.getCourseId());
			insertColumnValueMap.put("courseName", question.getCourseName());
			insertColumnValueMap.put("orgId", question.getOrgId());
			insertColumnValueMap.put("creatorId", question.getCreatorId());
			insertColumnValueMap.put("creatorName", question.getCreatorName());
			insertColumnValueMap.put("createTime", question.getCreateTime());
			if (question.getQuestionId() != null) {
				Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
				updateWhereConditionMap.put("questionId", question.getQuestionId());
				flag = questionService.update(insertColumnValueMap, updateWhereConditionMap);
			} else {
				flag = questionService.insert(insertColumnValueMap);
			}
			updateQuestionSortQsNum(question.getQtSortId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public void updateQuestionSortQsNum(Integer qtSortID) {
		Map<String, Object> questionSort = questionSortService.getQuestionSortById(qtSortID);
		if (questionSort != null) {
			int parentQsSortID = MapUtils.getIntValue(questionSort, "parentQsSortID");
			Map<String, Object> insertColumnValue = new HashMap<String, Object>();
			insertColumnValue.put("qsNum", MapUtils.getIntValue(questionSort, "qsNum") + 1);
			Map<String, Object> updateWhereCondition = new HashMap<String, Object>();
			updateWhereCondition.put("qtSortID", MapUtils.getIntValue(questionSort, "qtSortID"));
			questionSortService.update(insertColumnValue, updateWhereCondition);
			if (parentQsSortID != 0) {
				updateQuestionSortQsNum(parentQsSortID);
			}
		}
	}

	@Override
	public void deleteQuestionByIds(String ids) {
		questionService.deleteQuestionByIds(ids);
	}

	@Override
	public Map<String, Object> getQuestionById(Integer id) {
		return questionService.getQuestionById(id);
	}

	@Override
	public Map<String, Object> getQuestionByName(Map<String, Object> map) {
		return questionService.getQuestionByName(map);
	}

	@Override
	public List<Map<String, Object>> getQuestionByqsSortId(int qsSortId) {
		return questionService.getQuestionByqsSortId(qsSortId);
	}

	@Override
	public Adks_question getById(Integer id) {
		return questionService.getById(id);
	}

}
