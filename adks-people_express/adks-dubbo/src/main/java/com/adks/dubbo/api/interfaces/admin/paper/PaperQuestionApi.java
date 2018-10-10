package com.adks.dubbo.api.interfaces.admin.paper;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.paper.Adks_paper_question;
import com.adks.dubbo.commons.Page;

public interface PaperQuestionApi {

	public Integer savePaperQuestion(Adks_paper_question paperQuestion);

	public void deletePaperQuestionByIds(String ids);

	public Map<String, Object> getPaperQuestionById(Integer id);

	/**
	 * 获取试题列表分页数据
	 * 
	 * @param paramPage
	 * @return
	 */
	public Page<List<Map<String, Object>>> getPaperQuestionListPage(Page<List<Map<String, Object>>> page);

	public List<Adks_paper_question> getPaperQuestionListByPaperId(Integer paperId);
}
