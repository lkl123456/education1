package com.adks.dubbo.api.interfaces.admin.question;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.question.Adks_question_sort;

public interface QuestionSortApi {

	public Integer saveQuestionSort(Adks_question_sort questionSort);

	public void deleteQuestionSortByIds(String ids);

	public Map<String, Object> getQuestionSortById(Integer id);

	public List<Map<String, Object>> getQuestionSortList(Integer parent, int orgId);

}
