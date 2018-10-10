package com.adks.dubbo.providers.admin.question;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dbclient.mysql.MysqlClient;
import com.adks.dubbo.api.data.question.Adks_question_sort;
import com.adks.dubbo.api.interfaces.admin.question.QuestionSortApi;
import com.adks.dubbo.service.admin.question.QuestionSortService;

public class QuestionSortApiImpl implements QuestionSortApi {

	@Autowired
	private MysqlClient mysqlClient;

	@Autowired
	private QuestionSortService questionSortService;

	@Override
	public Integer saveQuestionSort(Adks_question_sort questionSort) {
		Integer flag = 0;
		Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
		System.out.println("********" + questionSort.getQsSortName());
		try {
			insertColumnValueMap.put("qsSortName", questionSort.getQsSortName());
			// insertColumnValueMap.put("qsSortCode",
			// questionSort.getQsSortCode());
			insertColumnValueMap.put("parentQsSortID", questionSort.getParentQsSortID());
			insertColumnValueMap.put("parentQsSortName", questionSort.getParentQsSortName());
			insertColumnValueMap.put("orgId", questionSort.getOrgId());
			insertColumnValueMap.put("orgName", questionSort.getOrgName());
			insertColumnValueMap.put("creatorId", questionSort.getCreatorId());
			insertColumnValueMap.put("creatorName", questionSort.getCreatorName());
			if (questionSort.getQtSortID() != null) {
				insertColumnValueMap.put("qsNum", questionSort.getQsNum());
				Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
				updateWhereConditionMap.put("qtSortID", questionSort.getQtSortID());
				flag = questionSortService.update(insertColumnValueMap, updateWhereConditionMap);
			} else {
				insertColumnValueMap.put("createTime", questionSort.getCreateTime());
				insertColumnValueMap.put("qsNum", 0);
				flag = questionSortService.insert(insertColumnValueMap);
				if (questionSort.getParentQsSortID() == 0) {
					insertColumnValueMap.put("qsSortCode", "0A" + flag + "A");
				} else {
					Map<String, Object> parentQuestionSort = questionSortService
							.getQuestionSortById(questionSort.getParentQsSortID());
					if (parentQuestionSort != null && MapUtils.getString(parentQuestionSort, "qsSortCode") != null) {
						insertColumnValueMap.put("qsSortCode",
								MapUtils.getString(parentQuestionSort, "qsSortCode") + flag + "A");
					}
				}
				Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
				updateWhereConditionMap.put("qtSortID", flag);
				questionSortService.update(insertColumnValueMap, updateWhereConditionMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public void deleteQuestionSortByIds(String ids) {
		questionSortService.deleteQuestionByIds(ids);
	}

	@Override
	public Map<String, Object> getQuestionSortById(Integer id) {
		return questionSortService.getQuestionSortById(id);
	}

	@Override
	public List<Map<String, Object>> getQuestionSortList(Integer parent, int orgId) {
		// return questionSortService.getQuestionSortList(parent);
		List<Map<String, Object>> list = questionSortService.getQuestionSortList(parent, orgId);
		if (list != null && list.size() > 0) {
			for (Map<String, Object> questionSort : list) {
				questionSort.put("text", MapUtils.getString(questionSort, "name"));

				List<Map<String, Object>> child = questionSortService
						.getQuestionSortList(MapUtils.getIntValue(questionSort, "id"), orgId);
				if (child != null) {
					for (Map<String, Object> questionSortChild : child) {
						questionSortChild.put("text", MapUtils.getString(questionSortChild, "name"));
						List<Map<String, Object>> grandson = questionSortService
								.getQuestionSortList(MapUtils.getIntValue(questionSortChild, "id"), orgId);
						if (grandson != null) {
							for (Map<String, Object> questionSortGrandson : grandson) {
								questionSortGrandson.put("text", MapUtils.getString(questionSortGrandson, "name"));
							}
							questionSortChild.put("children", grandson);
						}
					}
					questionSort.put("children", child);
				}

			}
		}
		return list;
	}

}
