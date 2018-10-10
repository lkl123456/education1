package com.adks.dubbo.dao.admin.paper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.adks.commons.util.DateUtils;
import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.data.paper.Adks_paper;
import com.adks.dubbo.commons.Page;

@Component
public class PaperDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_paper";
	}

	public void deletePaper(String ids) {
		String sql = " delete from adks_paper ";
		if (ids.split(",").length > 1) {
			sql += " where paperId in (" + ids + ") ";
		} else {
			sql += " where paperId = " + ids;
		}
		mysqlClient.update(sql, new Object[] {});
	}

	/*
	 * public Map<String, Object> getPaperById(Integer id){ StringBuilder
	 * querySqlSb = new StringBuilder(getQueryPrefix()); querySqlSb.append(
	 * " and paperId = ?"); return
	 * mysqlClient.queryForMap(querySqlSb.toString(), new Object[]{id}); }
	 */
	public Adks_paper getPaperById(Integer id) {
		String sql = "select * from adks_paper where paperId=" + id;
		List<Adks_paper> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_paper>() {
			@Override
			public Adks_paper mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_paper paper = new Adks_paper();
				paper.setPaperId(rs.getInt("paperId"));
				paper.setPaperName(rs.getString("paperName"));
				paper.setScore(rs.getInt("score"));
				paper.setQsNum(rs.getInt("qsNum"));
				paper.setDanxuanNum(rs.getInt("danxuanNum"));
				paper.setDanxuanScore(rs.getInt("danxuanScore"));
				paper.setDuoxuanNum(rs.getInt("duoxuanNum"));
				paper.setDuoxuanScore(rs.getInt("duoxuanScore"));
				paper.setPanduanNum(rs.getInt("panduanNum"));
				paper.setPanduanScore(rs.getInt("panduanScore"));
				paper.setTiankongNum(rs.getInt("tiankongNum"));
				paper.setTiankongScore(rs.getInt("tiankongScore"));
				paper.setWendaNum(rs.getInt("wendaNum"));
				paper.setWendaScore(rs.getInt("wendaScore"));
				paper.setPaperHtmlAdress(rs.getString("paperHtmlAdress"));
				paper.setPaperType(rs.getInt("paperType"));
				paper.setOrgId(rs.getInt("orgId"));
				paper.setOrgName(rs.getString("orgName"));
				paper.setCreatorId(rs.getInt("creatorId"));
				paper.setCreatorName(rs.getString("creatorName"));
				paper.setCreateTime(rs.getDate("createTime"));
				return paper;
			}
		});
		if (reslist == null || reslist.size() <= 0)
			return null;
		return reslist.get(0);
	}

	public Page<List<Map<String, Object>>> getPaperListPage(Page<List<Map<String, Object>>> page) {
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); // limit
																			// 起始位置
		StringBuffer sqlbuffer = new StringBuffer("select * from adks_paper where 1=1 ");
		StringBuffer countsql = new StringBuffer("select count(1) from adks_paper where 1=1 ");
		Map map = page.getMap();
		if (map != null && map.size() > 0) {
			// 添加查询条件 。。
			// 试卷名称
			if (map.get("paperName") != null) {
				sqlbuffer.append(" and paperName like '%" + map.get("paperName") + "%'");
				countsql.append(" and paperName like '%" + map.get("paperName") + "%'");
			}
			if (map.get("orgId") != null) {
				sqlbuffer.append(" and orgId =" + map.get("orgId"));
				countsql.append(" and orgId =" + map.get("orgId"));
			}
		}

		// 分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
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
		page.setTotal(totalcount);
		page.setRows(userlist);
		return page;
	};

	public Map<String, Object> getPaperByName(Map<String, Object> map) {
		Object[] obj = new Object[] {};
		String sql = "select * from adks_paper where paperName='" + map.get("paperName") + "'";
		if (map.get("paperId") != null) {
			sql += " and paperId!=" + map.get("paperId");
		}
		return mysqlClient.queryForMap(sql, obj);
	}
}
