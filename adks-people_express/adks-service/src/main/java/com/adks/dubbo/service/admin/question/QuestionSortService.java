package com.adks.dubbo.service.admin.question;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.dao.admin.question.QuestionSortDao;

@Service
public class QuestionSortService extends BaseService<BaseDao> {

	private static final Logger logger = LoggerFactory.getLogger(QuestionSortService.class);

	@Autowired
	private QuestionSortDao questionSortdao;

	@Override
	protected QuestionSortDao getDao() {
		return questionSortdao;
	}

	public Map<String, Object> getQuestionSortById(Integer id) {
		return questionSortdao.getQuestionSortById(id);
	}

	public void deleteQuestionByIds(String ids) {
		questionSortdao.deleteQuestionSort(ids);
	}

	public List<Map<String, Object>> getQuestionSortList(Integer parent, int orgId) {
		return questionSortdao.getQuestionSortList(parent, orgId);
	}

}
