package com.adks.dubbo.dao.web.course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.adks.commons.util.DateUtils;
import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.api.data.course.Adks_course_sort;
import com.adks.dubbo.commons.Page;

@Repository
public class CourseSortWebDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_course_sort";
	}
	
	public List<Adks_course_sort> getAllChildeCourseSort(Map map) {
		String sql = "select courseSortId, courseSortName,courseSortCode, courseParentId,courseParentName,orgId,orgCode,orgName from adks_course_sort where courseSortId != 0 ";
		if(map.get("courseSortName")!=null && !"".equals(map.get("courseSortName"))){
			sql+=" and courseSortName like '%"+map.get("courseSortName")+"%'";
		}
		if(map.get("courseParentId")!=null && !"".equals(map.get("courseParentId"))){
			sql+=" and courseParentId="+map.get("courseParentId");
		}
		List<Adks_course_sort> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_course_sort>() {
			@Override
			public Adks_course_sort mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_course_sort courseSort=new Adks_course_sort();
				courseSort.setId(rs.getInt("courseSortId"));
				courseSort.setCourseSortId(rs.getInt("courseSortId"));
				courseSort.setCourseSortName(rs.getString("courseSortName"));
				courseSort.setName(rs.getString("courseSortName"));
				courseSort.setCourseSortCode(rs.getString("courseSortCode"));
				courseSort.setCourseParentId(rs.getInt("courseParentId"));
				courseSort.setPId(rs.getInt("courseParentId"));
				courseSort.setCourseParentName(rs.getString("courseParentName"));
				courseSort.setOrgId(rs.getInt("orgId"));
				courseSort.setOrgCode(rs.getString("orgCode"));
				courseSort.setOrgName(rs.getString("orgName"));
				return courseSort;
			}});
		if(reslist == null || reslist.size()<=0){
			return null;
		}
		return reslist;
	}
}
