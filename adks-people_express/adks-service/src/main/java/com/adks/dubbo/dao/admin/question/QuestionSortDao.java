package com.adks.dubbo.dao.admin.question;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.adks.commons.util.DateUtils;
import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.commons.Page;

@Component
public class QuestionSortDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_question_sort";
	}

	public Map<String, Object> getQuestionSortById(Integer id) {
		StringBuilder querySqlSb = new StringBuilder(getQueryPrefix());
		querySqlSb.append(" and qtSortID = ?");
		return mysqlClient.queryForMap(querySqlSb.toString(), new Object[] { id });
	}

	public void deleteQuestionSort(String ids) {
		String sql = " delete from adks_question_sort ";
		if (ids.split(",").length > 1) {
			sql += " where qtSortID in (" + ids + ") ";
		} else {
			sql += " where qtSortID = " + ids;
		}
		mysqlClient.update(sql, new Object[] {});
	}

	public List<Map<String, Object>> getQuestionSortList(Integer parent, int orgId) {
		String sql = "select qtSortID as id,qsSortName as name,parentQsSortID as pId from adks_question_sort where 1=1 ";
		if (parent != null && !"".equals(parent)) {
			sql = sql + " and parentQsSortID = " + parent;
		}
		if (orgId != 0) {
			sql = sql + " and (orgId = " + orgId + " or orgId is null)";
		}
		List<Map<String, Object>> reslist = mysqlClient.queryForList(sql, new Object[0]);
		if (reslist == null || reslist.size() <= 0) {
			return null;
		}
		return reslist;
	}

}
