package com.adks.dubbo.service.web.grade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.dao.web.grade.ExamScoreAnswerWebDao;

@Service
public class ExamScoreAnswerWebService extends BaseService<ExamScoreAnswerWebDao> {

	@Autowired
	private ExamScoreAnswerWebDao examScoreAnswerWebDao;

	@Override
	protected ExamScoreAnswerWebDao getDao() {
		return examScoreAnswerWebDao;
	}

	public void deleteExamScoreAnswer(int examScoreId) {
		examScoreAnswerWebDao.deleteExamScoreAnswer(examScoreId);
	}

}
