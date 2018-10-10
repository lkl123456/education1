package com.adks.dubbo.providers.admin.paper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dbclient.mysql.MysqlClient;
import com.adks.dubbo.api.data.paper.Adks_paper;
import com.adks.dubbo.api.data.paper.Adks_paper_question;
import com.adks.dubbo.api.interfaces.admin.paper.PaperQuestionApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.admin.paper.PaperQuestionService;
import com.adks.dubbo.service.admin.paper.PaperService;
import com.adks.dubbo.service.admin.question.QuestionService;

public class PaperQuestionApiImpl implements PaperQuestionApi {

	@Autowired
	private MysqlClient mysqlClient;
	@Autowired
	private PaperQuestionService paperQuestionService;
	@Autowired
	private PaperService paperService;
	@Autowired
	private QuestionService questionService;

	@Override
	public Page<List<Map<String, Object>>> getPaperQuestionListPage(Page<List<Map<String, Object>>> page) {
		return paperQuestionService.getPaperQuestionListPage(page);
	}

	@Override
	public Integer savePaperQuestion(Adks_paper_question paperQuestion) {
		Integer flag = 0;
		Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
		try {
			insertColumnValueMap.put("paperId", paperQuestion.getPaperId());
			insertColumnValueMap.put("qsId", paperQuestion.getQsId());
			insertColumnValueMap.put("score", paperQuestion.getScore());
			if (paperQuestion.getPaperQsId() != null) {
				Map<String, Object> parmMap = paperQuestionService.getPaperQuestionById(paperQuestion.getPaperQsId());

				Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
				updateWhereConditionMap.put("paperQsId", paperQuestion.getPaperQsId());
				flag = paperQuestionService.update(insertColumnValueMap, updateWhereConditionMap);

				int oldScore = MapUtils.getIntValue(parmMap, "score");
				updatePaper(paperQuestion.getPaperId(), paperQuestion.getScore() - oldScore,
						paperQuestion.getQuestionType(), "update");
			} else {
				flag = paperQuestionService.insert(insertColumnValueMap);

				updatePaper(paperQuestion.getPaperId(), paperQuestion.getScore(), paperQuestion.getQuestionType(),
						"add");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public void deletePaperQuestionByIds(String ids) {
		if (ids.split(",").length > 1) {
			String paperQsIds[] = ids.split(",");
			for (int i = 0; i < paperQsIds.length; i++) {
				Map<String, Object> paperQuestionMap = paperQuestionService
						.getPaperQuestionById(Integer.parseInt(paperQsIds[i]));
				if (paperQuestionMap != null) {
					// Map<String,Object> questionMap
					// =questionService.getQuestionById(MapUtils.getIntValue(paperQuestionMap,"qsId"));
					updatePaper(MapUtils.getIntValue(paperQuestionMap, "paperId"),
							MapUtils.getIntValue(paperQuestionMap, "score"),
							MapUtils.getIntValue(paperQuestionMap, "questionType"), "min");
				}
			}
		} else {
			Map<String, Object> paperQuestionMap = paperQuestionService.getPaperQuestionById(Integer.parseInt(ids));
			if (paperQuestionMap != null) {
				// Map<String,Object> questionMap
				// =questionService.getQuestionById(MapUtils.getIntValue(paperQuestionMap,"qsId"));
				updatePaper(MapUtils.getIntValue(paperQuestionMap, "paperId"),
						MapUtils.getIntValue(paperQuestionMap, "score"),
						MapUtils.getIntValue(paperQuestionMap, "questionType"), "min");
			}
		}
		paperQuestionService.deletePaperQuestionByIds(ids);

	}

	public void updatePaper(Integer paperId, Integer score, Integer questionType, String type) {
		Adks_paper paperParm = paperService.getPaperById(paperId);
		if (paperParm != null) {

			if (type.equals("add")) {
				if (questionType == 1) {
					paperParm.setDanxuanNum(paperParm.getDanxuanNum() + 1);
					paperParm.setDanxuanScore(paperParm.getDanxuanScore() + score);
				} else if (questionType == 2) {
					paperParm.setDuoxuanNum(paperParm.getDuoxuanNum() + 1);
					paperParm.setDuoxuanScore(paperParm.getDuoxuanScore() + score);
				} else if (questionType == 3) {
					paperParm.setPanduanNum(paperParm.getPanduanNum() + 1);
					paperParm.setPanduanScore(paperParm.getPanduanScore() + score);
				} else if (questionType == 4) {
					paperParm.setTiankongNum(paperParm.getTiankongNum() + 1);
					paperParm.setTiankongScore(paperParm.getTiankongScore() + score);
				} else if (questionType == 5) {
					paperParm.setWendaNum(paperParm.getWendaNum() + 1);
					paperParm.setWendaScore(paperParm.getWendaScore() + score);
				}
				paperParm.setQsNum(paperParm.getQsNum() + 1);
				paperParm.setScore(paperParm.getScore() + score);
			} else if (type.equals("min")) {
				if (questionType == 1) {
					paperParm.setDanxuanNum(paperParm.getDanxuanNum() - 1);
					paperParm.setDanxuanScore(paperParm.getDanxuanScore() - score);
				} else if (questionType == 2) {
					paperParm.setDuoxuanNum(paperParm.getDuoxuanNum() - 1);
					paperParm.setDuoxuanScore(paperParm.getDuoxuanScore() - score);
				} else if (questionType == 3) {
					paperParm.setPanduanNum(paperParm.getPanduanNum() - 1);
					paperParm.setPanduanScore(paperParm.getPanduanScore() - score);
				} else if (questionType == 4) {
					paperParm.setTiankongNum(paperParm.getTiankongNum() - 1);
					paperParm.setTiankongScore(paperParm.getTiankongScore() - score);
				} else if (questionType == 5) {
					paperParm.setWendaNum(paperParm.getWendaNum() - 1);
					paperParm.setWendaScore(paperParm.getWendaScore() - score);
				}
				paperParm.setQsNum(paperParm.getQsNum() - 1);
				paperParm.setScore(paperParm.getScore() - score);
			} else if (type.equals("update")) {
				if (questionType == 1) {
					paperParm.setDanxuanScore(paperParm.getDanxuanScore() + score);
				} else if (questionType == 2) {
					paperParm.setDuoxuanScore(paperParm.getDuoxuanScore() + score);
				} else if (questionType == 3) {
					paperParm.setPanduanScore(paperParm.getPanduanScore() + score);
				} else if (questionType == 4) {
					paperParm.setTiankongScore(paperParm.getTiankongScore() + score);
				} else if (questionType == 5) {
					paperParm.setWendaScore(paperParm.getWendaScore() + score);
				}
				paperParm.setScore(paperParm.getScore() + score);
			}
			paperService.savePaper(paperParm);
		}
	}

	@Override
	public Map<String, Object> getPaperQuestionById(Integer id) {
		return paperQuestionService.getPaperQuestionById(id);
	}

	@Override
	public List<Adks_paper_question> getPaperQuestionListByPaperId(Integer paperId) {
		return paperQuestionService.getPaperQuestionListByPaperId(paperId);
	}
}
