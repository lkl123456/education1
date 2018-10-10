package com.adks.dubbo.service.admin.question;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.question.Adks_question;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.admin.question.QuestionDao;

@Service
public class QuestionService extends BaseService<BaseDao> {

	private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);

	@Autowired
	private QuestionDao questiondao;

	@Override
	protected QuestionDao getDao() {
		return questiondao;
	}

	public Map<String, Object> getQuestionById(Integer id) {
		return questiondao.getQuestionById(id);
	}

	public Map<String, Object> getQuestionByName(Map<String, Object> map) {
		return questiondao.getQuestionByName(map);
	}

	public List<Map<String, Object>> getRandomQuestionPaper(String qsSortCode, int orgId) {
		return questiondao.getRandomQuestionPaper(qsSortCode, orgId);
	}

	public List<Map<String, Object>> getRandomQuestion(Integer questionType, Integer num) {
		return questiondao.getRandomQuestion(questionType, num);
	}

	public void deleteQuestionByIds(String ids) {
		questiondao.deleteQuestion(ids);
	}

	public Page<List<Map<String, Object>>> getQuestionListPage(Page<List<Map<String, Object>>> paramPage) {
		return questiondao.getQuestionListPage(paramPage);
	}

	public List<Map<String, Object>> getQuestionByqsSortId(int qsSortId) {
		return questiondao.getQuestionByqsSortId(qsSortId);
	}

	public Adks_question getById(Integer id) {
		return questiondao.getById(id);
	}
}
