package com.adks.dubbo.dao.app.grade;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.commons.Page;

@Repository
public class GradeCourseAppDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_grade_course";
	}

	/**
	 * 根据班级获取全部课程列表
	 * 
	 * @param page
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Page<List<Map<String, Object>>> getCoursePageByGradeId(Page<List<Map<String, Object>>> page) {
		Integer offset = page.getStartLimit(); // limit
		String sqlbuffer = "select * from adks_grade_course gc where 1=1 ";
		StringBuilder countsql = new StringBuilder("select count(1) from adks_grade_course gc where 1=1 ");
		Map map = page.getMap();
		if (map != null && map.size() > 0) {
			// 添加查询条件
			if (map.get("gradeId") != null) {
				sqlbuffer += " and gc.gradeId =" + map.get("gradeId");
				countsql.append(" and gc.gradeId =" + map.get("gradeId"));
			}
			if (map.get("status") != null) {
				sqlbuffer += " and gc.gcState = " + map.get("status");
				countsql.append(" and gc.gcState = " + map.get("status"));
			}
		}
		sqlbuffer += " and now() > g.endDate order by gu.createTime desc";
		// 分页
		sqlbuffer += " limit " + offset + ", " + page.getPageSize();
		List<Map<String, Object>> gradeUserList = mysqlClient.queryForList(sqlbuffer, new Object[0]);
		// 查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(gradeUserList);
		return page;
	}
}