package com.adks.dubbo.service.admin.exam;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.admin.exam.ExamPaperDao;

@Service
public class ExamPaperService extends BaseService<BaseDao> {

	private static final Logger logger = LoggerFactory.getLogger(ExamPaperService.class);

	@Autowired
	private ExamPaperDao examPaperDao;

	@Override
	protected ExamPaperDao getDao() {
		return examPaperDao;
	}

	public void deleteExamPaperByIds(String ids) {
		examPaperDao.deleteExamPaper(ids);
	}

	public Page<List<Map<String, Object>>> getExamPaperListPage(Page<List<Map<String, Object>>> page) {
		return examPaperDao.getExamPaperListPage(page);
	}

	public List<Map<String, Object>> getExamByPaperId(int paperId) {
		return examPaperDao.getExamByPaperId(paperId);
	}

}
