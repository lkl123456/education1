package com.adks.dubbo.dao.app.course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.course.Adks_course_sort;

@Repository
public class CourseSortAppDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_course_sort";
	}

	/**
	 * 获取分类列表 0为等级目录
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Map<String, Object>> getCourseSortByParent(int parentId) {
		String sql = "select "
				+ "courseSortId, courseSortName as name,courseSortCode as code, courseParentId as parent,courseParentName as parentName,courseSortImgPath as imageUrl "
				+ "from adks_course_sort where courseParentId=" + parentId;
		return mysqlClient.queryForList(sql, new Object[0]);
	}

	/**
	 * 根据id获取分类信息
	 * 
	 * @param id
	 * @return
	 */
	public Adks_course_sort getCourseSortById(int id, int orgId) {
		String sql = "select "
				+ "courseSortId, courseSortName,courseSortCode, courseParentId,courseParentName,courseNum ,orgId,orgCode,orgName,creatorId,creatorName,createtime "
				+ "from adks_course_sort where courseSortId=" + id + " and orgId=" + orgId;
		List<Adks_course_sort> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_course_sort>() {
			@Override
			public Adks_course_sort mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_course_sort courseSort = new Adks_course_sort();
				courseSort.setCourseSortId(rs.getInt("courseSortId"));
				courseSort.setCourseSortName(rs.getString("courseSortName"));
				courseSort.setCourseSortCode(rs.getString("courseSortCode"));
				courseSort.setCourseParentId(rs.getInt("courseParentId"));
				courseSort.setCourseParentName(rs.getString("courseParentName"));
				courseSort.setOrgId(rs.getInt("orgId"));
				courseSort.setOrgCode(rs.getString("orgCode"));
				courseSort.setOrgName(rs.getString("orgName"));
				courseSort.setCreatorId(rs.getInt("creatorId"));
				courseSort.setCreatorName(rs.getString("creatorName"));
				courseSort.setCreateTime(rs.getDate("createtime"));
				return courseSort;
			}
		});
		if (reslist == null || reslist.size() <= 0)
			return null;
		return reslist.get(0);
	}
}
