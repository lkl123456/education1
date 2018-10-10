package com.adks.dubbo.api.interfaces.admin.question;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.question.Adks_question;
import com.adks.dubbo.commons.Page;

public interface QuestionApi {

	public Integer saveQuestion(Adks_question question);

	public void deleteQuestionByIds(String ids);

	public Map<String, Object> getQuestionById(Integer id);

	public Map<String, Object> getQuestionByName(Map<String, Object> map);

	public List<Map<String, Object>> getRandomQuestionPaper(String qsSortCode, int orgId);

	public List<Map<String, Object>> getRandomQuestion(Integer questionType, Integer num);

	/**
	 * 获取试题列表分页数据
	 * 
	 * @param paramPage
	 * @return
	 */
	public Page<List<Map<String, Object>>> getQuestionListPage(Page<List<Map<String, Object>>> paramPage);

	public List<Map<String, Object>> getQuestionByqsSortId(int qsSortId);

	public Adks_question getById(Integer id);

}
