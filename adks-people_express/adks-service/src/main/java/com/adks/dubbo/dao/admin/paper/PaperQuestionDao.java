package com.adks.dubbo.dao.admin.paper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.data.paper.Adks_paper_question;
import com.adks.dubbo.commons.Page;

@Component
public class PaperQuestionDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_paper_question";
	}

	public void deletePaperQuestion(String ids) {
		String sql = " delete from adks_paper_question ";
		if (ids.split(",").length > 1) {
			sql += " where paperQsId in (" + ids + ") ";
		} else {
			sql += " where paperQsId = " + ids;
		}
		mysqlClient.update(sql, new Object[] {});
	}

	public Map<String, Object> getPaperQuestionById(Integer id) {
		StringBuilder querySqlSb = new StringBuilder(
				"select apq.paperQsId,apq.qsId,apq.paperId,aq.questionName,aq.courseName,aq.questionType,apq.score from adks_question aq,adks_paper_question apq where aq.questionId = apq.qsId ");
		querySqlSb.append(" and apq.paperQsId = ?");
		return mysqlClient.queryForMap(querySqlSb.toString(), new Object[] { id });
	}

	public Page<List<Map<String, Object>>> getPaperQuestionListPage(Page<List<Map<String, Object>>> page) {
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); // limit
																			// 起始位置

		StringBuffer sqlbuffer = new StringBuffer(
				"select apq.paperQsId,aq.questionName,aq.courseName,aq.questionType,apq.score from adks_question aq,adks_paper_question apq where aq.questionId = apq.qsId ");
		StringBuffer countsql = new StringBuffer(
				"select count(1) from adks_question aq,adks_paper_question apq where aq.questionId = apq.qsId ");
		Map map = page.getMap();
		if (map != null && map.size() > 0) {
			// 添加查询条件 。。
			if (map.get("paperId") != null) {
				sqlbuffer.append(" and apq.paperId =" + map.get("paperId"));
				countsql.append(" and apq.paperId =" + map.get("paperId"));
			}
		}

		// 分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();
		List<Map<String, Object>> userlist = mysqlClient.queryForList(sql, new Object[0]);
		// 查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(userlist);
		return page;
	};

	/**
	 * 获取试卷下全部题目
	 * 
	 * @param paperId
	 * @return
	 */
	public List<Adks_paper_question> getPaperQuestionListByPaperId(Integer paperId) {
		String sql = "select paperQsId,paperId,qsId,score from adks_paper_question where paperId=" + paperId;
		List<Adks_paper_question> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_paper_question>() {
			@Override
			public Adks_paper_question mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_paper_question paper_question = new Adks_paper_question();
				paper_question.setPaperQsId(rs.getInt("paperQsId"));
				paper_question.setPaperId(rs.getInt("paperId"));
				paper_question.setQsId(rs.getInt("qsId"));
				paper_question.setScore(rs.getInt("score"));
				return paper_question;
			}
		});
		return reslist;
	}
}
