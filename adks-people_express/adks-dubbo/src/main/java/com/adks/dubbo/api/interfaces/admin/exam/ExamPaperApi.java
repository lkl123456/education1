package com.adks.dubbo.api.interfaces.admin.exam;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.exam.Adks_exam_paper;
import com.adks.dubbo.commons.Page;

public interface ExamPaperApi {
	
	public Integer saveExamPaper(Adks_exam_paper examPaper);
	
	public void deleteExamPaperByIds(String ids);
	
	public Page<List<Map<String, Object>>> getExamPaperListPage(Page<List<Map<String, Object>>> page);

	public List<Map<String,Object>> getExamByPaperId(int paperId);
}
