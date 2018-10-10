package com.adks.dubbo.service.admin.paper;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.paper.Adks_paper_question;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.admin.paper.PaperQuestionDao;

@Service
public class PaperQuestionService extends BaseService<BaseDao> {

	private static final Logger logger = LoggerFactory.getLogger(PaperQuestionService.class);

	@Autowired
	private PaperQuestionDao paperQuestionDao;

	@Override
	protected PaperQuestionDao getDao() {
		return paperQuestionDao;
	}

	public Map<String, Object> getPaperQuestionById(Integer id) {
		return paperQuestionDao.getPaperQuestionById(id);
	}

	public void deletePaperQuestionByIds(String ids) {
		paperQuestionDao.deletePaperQuestion(ids);
	}

	public Page<List<Map<String, Object>>> getPaperQuestionListPage(Page<List<Map<String, Object>>> page) {
		return paperQuestionDao.getPaperQuestionListPage(page);
	}

	public List<Adks_paper_question> getPaperQuestionListByPaperId(Integer paperId) {
		return paperQuestionDao.getPaperQuestionListByPaperId(paperId);
	}

}
