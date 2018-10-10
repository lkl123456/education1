package com.adks.dubbo.dao.admin.exam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.adks.commons.util.DateUtils;
import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.exam.Adks_exam;
import com.adks.dubbo.commons.Page;

@Component
public class ExamDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_exam";
	}

	public void deleteExam(String examIds) {
		String sql = " delete from adks_exam ";
		if (examIds.split(",").length > 1) {
			sql += " where examId in (" + examIds + ") ";
		} else {
			sql += " where examId = " + examIds;
		}
		mysqlClient.update(sql, new Object[] {});
	}

	public Adks_exam getExamById(Integer examId) {
		String sql = "select * from adks_exam where examId=" + examId;
		List<Adks_exam> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_exam>() {
			@Override
			public Adks_exam mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_exam exam = new Adks_exam();
				exam.setExamId(rs.getInt("examId"));
				exam.setExamName(rs.getString("examName"));
				exam.setExamDesc(rs.getString("examDesc"));
				exam.setExamDate(rs.getInt("examDate"));
				exam.setStartDate(rs.getDate("startDate"));
				exam.setEndDate(rs.getDate("endDate"));
				exam.setScoreSum(rs.getInt("scoreSum"));
				exam.setPassScore(rs.getInt("passScore"));
				exam.setExamTimes(rs.getInt("examTimes"));
				exam.setOrgId(rs.getInt("orgId"));
				exam.setOrgName(rs.getString("orgName"));
				exam.setCreatorId(rs.getInt("creatorId"));
				exam.setCreatorName(rs.getString("creatorName"));
				exam.setCreateTime(rs.getDate("createTime"));
				return exam;
			}
		});
		if (reslist == null || reslist.size() <= 0)
			return null;
		return reslist.get(0);
	}

	public Page<List<Map<String, Object>>> getExamListPage(Page<List<Map<String, Object>>> page) {
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); // limit
																			// 起始位置
		StringBuffer sqlbuffer = new StringBuffer("select * from adks_exam where 1=1 ");
		StringBuffer countsql = new StringBuffer("select count(1) from adks_exam where 1=1 ");
		Map map = page.getMap();
		if (map != null && map.size() > 0) {
			// 添加查询条件 。。
			// 试卷名称
			if (map.get("examName") != null) {
				sqlbuffer.append(" and examName like '%" + map.get("examName") + "%'");
				countsql.append(" and examName like '%" + map.get("examName") + "%'");
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
			if (map2.get("startDate") != null) {
				map2.put("startDate_str",
						DateUtils.getDate2LStr(DateUtils.getStr2LDate(map2.get("startDate").toString())));
			}
			if (map2.get("endDate") != null) {
				map2.put("endDate_str", DateUtils.getDate2LStr(DateUtils.getStr2LDate(map2.get("endDate").toString())));
			}
		}
		// 查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(userlist);
		return page;
	};

	public Map<String, Object> getExamByName(Map<String, Object> map) {
		Object[] obj = new Object[] {};
		String sql = "select * from adks_exam where examName = '" + map.get("examName") + "'";
		if (map.get("examId") != null) {
			sql += " and examId!=" + map.get("examId");
		}
		return mysqlClient.queryForMap(sql, obj);
	}
}
