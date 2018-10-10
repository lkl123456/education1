package com.adks.dubbo.dao.admin.question;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.adks.commons.util.DateUtils;
import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.question.Adks_question;
import com.adks.dubbo.commons.Page;

@Component
public class QuestionDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_question";
	}

	public void deleteQuestion(String ids) {
		System.out.println("*********" + ids);
		String sql = " delete from adks_question ";
		if (ids.split(",").length > 1) {
			sql += " where questionId in (" + ids + ") ";
		} else {
			sql += " where questionId = " + ids;
		}
		mysqlClient.update(sql, new Object[] {});
	}

	public List<Map<String, Object>> getRandomQuestionPaper(String qsSortCode, int orgId) {
		StringBuilder querySqlSb = new StringBuilder(
				"select aq.questionType,count(*) questionTotal from adks_question aq where 1=1 ");
		if (qsSortCode != null && !"".equals(qsSortCode)) {
			querySqlSb.append(" and qsSortCode like '%" + qsSortCode + "%'");
		}
		if (orgId != 0) {
			querySqlSb.append(" and orgId =" + orgId);
		}
		querySqlSb.append(" GROUP BY aq.questionType");
		return mysqlClient.queryForList(querySqlSb.toString(), new Object[] {});
	}

	public List<Map<String, Object>> getRandomQuestion(Integer questionType, Integer num) {
		StringBuilder querySqlSb = new StringBuilder(
				"SELECT aq.questionType,aq.questionId FROM adks_question aq WHERE 1=1 ");
		if (questionType != null) {
			querySqlSb.append(" AND aq.questionType=" + questionType);
		}
		querySqlSb.append(" limit 0,?");
		return mysqlClient.queryForList(querySqlSb.toString(), new Object[] { num });
	}

	public Map<String, Object> getQuestionById(Integer id) {
		StringBuilder querySqlSb = new StringBuilder(getQueryPrefix());
		querySqlSb.append(" and questionId = ?");
		return mysqlClient.queryForMap(querySqlSb.toString(), new Object[] { id });
	}

	public Map<String, Object> getQuestionByName(Map<String, Object> map) {
		Object[] obj = new Object[] {};
		String sql = "select * from adks_question where questionName = '" + map.get("questionName") + "'";
		if (map.get("questionId") != null) {
			sql += " and questionId!=" + map.get("questionId");
		}
		return mysqlClient.queryForMap(sql, obj);
	}

	public Page<List<Map<String, Object>>> getQuestionListPage(Page<List<Map<String, Object>>> paramPage) {
		Integer offset = (paramPage.getCurrentPage() - 1) * paramPage.getPageSize(); // limit
																						// 起始位置
		StringBuffer sqlbuffer = new StringBuffer("select * from adks_question where 1=1 ");
		StringBuffer countsql = new StringBuffer("select count(1) from adks_question where 1=1 ");
		Map map = paramPage.getMap();
		if (map != null && map.size() > 0) {
			// 添加查询条件 。。
			// 试题名称
			if (map.get("questionName") != null && !"".equals(MapUtils.getString(map, "questionName"))) {
				sqlbuffer.append(" and questionName like '%" + map.get("questionName") + "%'");
				countsql.append(" and questionName like '%" + map.get("questionName") + "%'");
			}
			// 课程名称
			if (map.get("courseName") != null && !"".equals(MapUtils.getString(map, "courseName"))) {
				sqlbuffer.append(" and courseName like '%" + map.get("courseName") + "%'");
				countsql.append(" and courseName like '%" + map.get("courseName") + "%'");
			}
			// 分值
			if (map.get("questionValue_start") != null) {
				sqlbuffer.append(" and questionValue >=" + map.get("questionValue_start"));
				countsql.append(" and questionValue >=" + map.get("questionValue_start"));
			}
			if (map.get("questionValue_end") != null) {
				sqlbuffer.append(" and questionValue <=" + map.get("questionValue_end"));
				countsql.append(" and questionValue <=" + map.get("questionValue_end"));
			}
			// 试题类型（1.单选；2.多选；3.判断；4.填空；5.问答）
			if (map.get("questionType") != null && !map.get("questionType").toString().equals("")) {
				sqlbuffer.append(" and questionType =" + map.get("questionType"));
				countsql.append(" and questionType =" + map.get("questionType"));
			}
			// 树节点ID
			if (map.get("qsSortCode") != null) {
				sqlbuffer.append(" and qsSortCode like '%" + map.get("qsSortCode") + "%'");
				countsql.append(" and qsSortCode like '%" + map.get("qsSortCode") + "%'");
			}
			// 树节点ID
			if (map.get("orgId") != null) {
				sqlbuffer.append(" and orgId =" + map.get("orgId"));
				countsql.append(" and orgId =" + map.get("orgId"));
			}
		}

		// 分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(paramPage.getPageSize());
		String sql = sqlbuffer.toString();
		List<Map<String, Object>> userlist = mysqlClient.queryForList(sql, new Object[0]);
		for (Map<String, Object> map2 : userlist) {
			if (map2.get("createTime") != null) {
				map2.put("createTime_str",
						DateUtils.getDate2SStr(DateUtils.getStr2LDate(map2.get("createTime").toString())));
			}
		}
		// 查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		paramPage.setTotal(totalcount);
		paramPage.setRows(userlist);
		return paramPage;
	};

	public List<Map<String, Object>> getQuestionByqsSortId(int qsSortId) {
		StringBuilder querySqlSb = new StringBuilder("select * from adks_question aq where 1=1 ");
		if (qsSortId != 0) {
			querySqlSb.append(" and aq.qtSortId =" + qsSortId);
		}
		return mysqlClient.queryForList(querySqlSb.toString(), new Object[] {});
	}

	public Adks_question getById(Integer id) {
		String sql = new String("select * FROM adks_question where questionId = " + id);
		List<Adks_question> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_question>() {
			@Override
			public Adks_question mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_question question = new Adks_question();
				question.setQuestionId(rs.getInt("questionId"));
				question.setQuestionName(rs.getString("questionName"));
				question.setQuestionType(rs.getInt("questionType"));
				question.setQuestionValue(rs.getInt("questionValue"));
				question.setOptionA(rs.getString("optionA"));
				question.setOptionB(rs.getString("optionB"));
				question.setOptionC(rs.getString("optionC"));
				question.setOptionD(rs.getString("optionD"));
				question.setOptionE(rs.getString("optionE"));
				question.setOptionF(rs.getString("optionF"));
				question.setOptionG(rs.getString("optionG"));
				question.setOptionH(rs.getString("optionH"));
				question.setAnwsers(rs.getString("anwsers"));
				question.setQtSortId(rs.getInt("qtSortId"));
				question.setQsSortName(rs.getString("qsSortName"));
				question.setQsSortCode(rs.getString("qsSortCode"));
				question.setCourseId(rs.getInt("courseId"));
				question.setCourseName(rs.getString("courseName"));
				question.setOrgId(rs.getInt("orgId"));
				question.setCreatorId(rs.getInt("creatorId"));
				question.setCreatorName(rs.getString("creatorName"));
				question.setCreateTime(rs.getDate("createTime"));
				return question;
			}
		});
		if (reslist == null || reslist.size() <= 0)
			return null;
		return reslist.get(0);
	}
}
