package com.adks.dubbo.dao.admin.exam;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.exam.Adks_exam_paper;
import com.adks.dubbo.commons.Page;

@Component
public class ExamPaperDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_exam_paper";
	}

	public void deleteExamPaper(String ids) {
		String sql = " delete from adks_exam_paper ";
		if (ids.split(",").length > 1) {
			sql += " where id in (" + ids + ") ";
		} else {
			sql += " where id = " + ids;
		}
		mysqlClient.update(sql, new Object[] {});
	}

	public Page<List<Map<String, Object>>> getExamPaperListPage(Page<List<Map<String, Object>>> page) {
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); // limit
																			// 起始位置

		StringBuffer sqlbuffer = new StringBuffer(
				"SELECT ap.paperName,ifnull(ap.score,0)as score,ifnull(ap.qsNum,0) as qsNum,ifnull(ap.danxuanNum,0) as danxuanNum,ifnull(ap.danxuanScore,0) as danxuanScore,ifnull(ap.duoxuanNum,0) as duoxuanNum,ifnull(ap.duoxuanScore,0) as duoxuanScore,ifnull(ap.panduanNum,0) as panduanNum,ifnull(ap.panduanScore,0) as panduanScore,ifnull(ap.tiankongNum,0) as tiankongNum,ifnull(ap.tiankongScore,0) as tiankongScore,ifnull(ap.wendaNum,0) as wendaNum,ifnull(ap.wendaScore,0) as wendaScore,ap.paperId,ep.examId,ep.id "
				+ "FROM adks_paper ap,adks_exam_paper ep WHERE ap.paperId = ep.paperId ");
		StringBuffer countsql = new StringBuffer(
				"select count(1)  FROM adks_paper ap,adks_exam_paper ep WHERE ap.paperId = ep.paperId ");
		Map map = page.getMap();
		if (map != null && map.size() > 0) {
			if (map.get("examId") != null) {
				sqlbuffer.append(" and ep.examId = " + map.get("examId"));
				countsql.append(" and ep.examId = " + map.get("examId"));
			}
			// 添加查询条件 。。
			if (map.get("paperName") != null) {
				sqlbuffer.append(" and ap.paperName like '%" + map.get("paperName") + "%'");
				countsql.append(" and ap.paperName like '%" + map.get("paperName") + "%'");
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
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getExamByPaperId(int paperId){
		String sql = "select examId from adks_exam_paper where paperId = "+paperId;
		return mysqlClient.queryList(sql);
	}
}
