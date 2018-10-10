package com.adks.dubbo.providers.admin.exam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dbclient.mysql.MysqlClient;
import com.adks.dubbo.api.data.exam.Adks_exam_paper;
import com.adks.dubbo.api.interfaces.admin.exam.ExamPaperApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.admin.exam.ExamPaperService;

public class ExamPaperApiImpl implements ExamPaperApi {

	@Autowired
	private MysqlClient mysqlClient;
	@Autowired
	private ExamPaperService examPaperService;

	@Override
	public Page<List<Map<String, Object>>> getExamPaperListPage(Page<List<Map<String, Object>>> page) {
		return examPaperService.getExamPaperListPage(page);
	}

	@Override
	public Integer saveExamPaper(Adks_exam_paper examPaper) {
		Integer flag = 0;
		Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
		try {
			insertColumnValueMap.put("paperId", examPaper.getPaperId());
			insertColumnValueMap.put("examId", examPaper.getExamId());
			insertColumnValueMap.put("paperName", examPaper.getPaperName());
			insertColumnValueMap.put("paperHtmlAdress", examPaper.getPaperHtmlAdress());
			if (examPaper.getId() != null) {
				Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
				updateWhereConditionMap.put("id", examPaper.getId());
				flag = examPaperService.update(insertColumnValueMap, updateWhereConditionMap);
			} else {
				flag = examPaperService.insert(insertColumnValueMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public void deleteExamPaperByIds(String ids) {
		examPaperService.deleteExamPaperByIds(ids);
	}

	@Override
	public List<Map<String, Object>> getExamByPaperId(int paperId) {
		return examPaperService.getExamByPaperId(paperId);
	}
}
