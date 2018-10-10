package com.adks.dubbo.dao.app.course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.commons.Page;

@Repository
public class CourseAppDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_course";
	}

	/**
	 * 课程列表
	 * 
	 * @param page
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Page<List<Map<String, Object>>> getCourseListPage(Page<List<Map<String, Object>>> page) {
		Integer offset = page.getStartLimit(); // limit
												// 起始位置
		StringBuffer sqlbuffer = new StringBuffer(
				"select ac.*,acu.iscollected from (select c.courseId, c.courseName as name,c.courseType,c.coursePic as pathImg,c.authorId as authorId,c.authorName as authorName,c.courseTimeLong as durationTime,c.createtime as createDate "
						+ "from adks_course c where 1=1 and c.courseStatus = 1 and c.isAudit =1 ");
		StringBuffer countsql = new StringBuffer(
				"select count(1) from (select c.courseId, c.courseName as name,c.courseType,c.coursePic as pathImg,c.authorId as authorId,c.authorName as authorName,c.courseTimeLong as durationTime,c.createtime as createDate "
						+ "from adks_course c where 1=1 and c.courseStatus = 1 and c.isAudit =1 ");
		Map map = page.getMap();
		if (map != null && map.size() > 0) {

			if (map.get("orgId") != null) {
				sqlbuffer.append(
						" and  (c.courseBelong = 1 or (c.orgId =" + map.get("orgId") + " and c.courseBelong != 2)) ");
				countsql.append(
						" and (c.courseBelong = 1 or (c.orgId =" + map.get("orgId") + " and c.courseBelong != 2)) ");
			}
			// 添加查询条件 。。
			if (map.get("courseSortCode") != null && !"".equals(map.get("courseSortCode"))) {
				sqlbuffer.append(" and c.courseSortCode like '%" + map.get("courseSortCode") + "%'");
				countsql.append(" and c.courseSortCode like '%" + map.get("courseSortCode") + "%'");
			}
			sqlbuffer.append(
					" ) ac left JOIN (select cu.courseId,cu.isCollection as iscollected from adks_course_user cu where 1=1 ");
			countsql.append(
					" ) ac left JOIN (select cu.courseId,cu.isCollection as iscollected from adks_course_user cu where 1=1 ");
			if (map.get("userId") != null) {
				sqlbuffer.append(" and cu.userId =" + map.get("userId"));
				countsql.append(" and cu.userId =" + map.get("userId"));
			}
			sqlbuffer.append(" ) acu on ac.courseId = acu.courseId ");
			countsql.append(" ) acu on ac.courseId = acu.courseId ");
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
	}

	/**
	 * 获取最新课程（审核通过、公开、激活、时间倒序）
	 * 
	 * @param map
	 * @return
	 */
	public List<Adks_course> getTopNewCourseList(Map<String, Object> map) {
		String sql = "select "
				+ "courseId, courseName,courseCode, courseType,coursePic ,courseDes,authorId,authorName,courseSortId,courseSortName,courseSortCode,courseDuration,courseTimeLong,courseStatus,courseCollectNum,courseClickNum,orgId,orgName,isAudit,courseBelong,isRecommend,courseStream,creatorId,creatorName,createtime,courseStudiedLong"
				+ "from adks_course where 1=1 ";
		if (map.get("orgId") != null && !"".equals(map.get("orgId"))) {
			sql += " and orgId=" + map.get("orgId");
		}
		if (map.get("isAudit") != null && !"".equals(map.get("isAudit"))) {
			sql += " and isAudit=" + map.get("isAudit");
		}
		if (map.get("isRecommend") != null && !"".equals(map.get("isRecommend"))) {
			sql += " and isRecommend=" + map.get("isRecommend");
		}
		if (map.get("courseBelong") != null && !"".equals(map.get("courseBelong"))) {
			sql += " and courseBelong=" + map.get("courseBelong");
		}
		if (map.get("courseStatus") != null && !"".equals(map.get("courseStatus"))) {
			sql += " and courseStatus=" + map.get("courseStatus");
		}
		if (map.get("num") != null && !"".equals(map.get("num"))) {
			sql += " order by createtime desc limit 0," + map.get("num");
		}

		List<Adks_course> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_course>() {
			@Override
			public Adks_course mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_course courseSort = new Adks_course();
				courseSort.setCourseId(rs.getInt("courseId"));
				courseSort.setCourseName(rs.getString("courseName"));
				courseSort.setCourseCode(rs.getString("courseCode"));
				courseSort.setCourseSortId(rs.getInt("courseSortId"));
				courseSort.setCourseSortName(rs.getString("courseSortName"));
				courseSort.setCourseSortCode(rs.getString("courseSortCode"));
				courseSort.setCourseType(rs.getInt("courseType"));
				courseSort.setCoursePic(rs.getString("coursePic"));
				courseSort.setCourseDes(rs.getString("courseDes"));
				courseSort.setAuthorId(rs.getInt("authorId"));
				courseSort.setAuthorName(rs.getString("authorName"));
				courseSort.setCourseDuration(rs.getInt("courseDuration"));
				courseSort.setCourseTimeLong(rs.getString("courseTimeLong"));
				courseSort.setCourseStatus(rs.getInt("courseStatus"));
				courseSort.setCourseCollectNum(rs.getInt("courseCollectNum"));
				courseSort.setCourseClickNum(rs.getInt("courseClickNum"));
				courseSort.setIsAudit(rs.getInt("isAudit"));
				courseSort.setCourseBelong(rs.getInt("courseBelong"));
				courseSort.setIsRecommend(rs.getInt("isRecommend"));
				courseSort.setOrgId(rs.getInt("orgId"));
				courseSort.setOrgName(rs.getString("orgName"));
				courseSort.setCourseStream(rs.getString("courseStream"));
				courseSort.setCreatorId(rs.getInt("creatorId"));
				courseSort.setCreatorName(rs.getString("creatorName"));
				courseSort.setCreateTime(rs.getDate("createtime"));
				return courseSort;
			}
		});
		if (reslist == null || reslist.size() <= 0) {
			return null;
		}
		return reslist;
	}

	/**
	 * 根据讲师id获取课程列表
	 * 
	 * @param authorId
	 * @return
	 */
	public List<Map<String, Object>> getCourseListByAuthorId(int authorId) {
		String sql = "select courseId, courseName from adks_course where 1=1 and courseStatus = 1 and isAudit =1 ";
		if (authorId != 0) {
			sql += " and authorId=" + authorId;
		}
		sql += " order by createtime desc ";

		return mysqlClient.queryForList(sql, new Object[0]);
	}

	/**
	 * 根据id获取课程
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> getCourseById(int id) {
		String sql = "select "
				+ "courseId, courseName,courseCode, courseType,coursePic ,courseDes,authorId,authorName,courseSortId,courseSortName,courseSortCode,courseDuration,courseTimeLong,courseStatus,courseCollectNum,courseClickNum,orgId,orgName,isAudit,courseBelong,isRecommend,courseStream,creatorId,creatorName,createtime,courseStudiedLong "
				+ "from adks_course where 1=1 and courseId=" + id;
		return mysqlClient.queryForMap(sql, new Object[0]);
	}

	/**
	 * 搜索课程列表
	 * 
	 * @param page
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Page<List<Map<String, Object>>> getCourseListPageByCourseName(Page<List<Map<String, Object>>> page) {
		Integer offset = page.getStartLimit(); // limit
												// 起始位置
		String sqlbuffer = "select ac.*,ifnull(acu.isCollection,2) as iscollected from ("
				+ "select c.courseId,c.courseName as name,c.authorName,c.authorId,c.createtime as createDate,c.courseType,c.orgId,c.courseTimeLong as durationTime,c.coursePic as pathImg "
				+ "from adks_course c where c.isAudit = 1 and c.courseStatus =1 ";
		StringBuilder countsql = new StringBuilder("select count(1) from ("
				+ "select c.courseId,c.courseName as name,c.authorName,c.authorId,c.createtime as createDate,c.orgId,c.courseTimeLong as durationTime,c.coursePic as pathImg "
				+ "from adks_course c where c.isAudit = 1 and c.courseStatus =1 ");
		Map map = page.getMap();
		if (map != null && map.size() > 0) {

			if (map.get("orgId") != null) {
				sqlbuffer += " and (c.courseBelong = 1 or (c.orgId = " + map.get("orgId")
						+ " and c.courseBelong != 2))";
				countsql.append(
						" and (c.courseBelong = 1 or (c.orgId = " + map.get("orgId") + " and c.courseBelong != 2))");
			}
			sqlbuffer += " ) ac left JOIN (select cu.isCollection,cu.courseId from adks_course_user cu where 1=1";
			countsql.append(" ) ac left JOIN (select cu.isCollection,cu.courseId from adks_course_user cu where 1=1");
			if (map.get("userId") != null) {
				sqlbuffer += " and cu.userId =" + map.get("userId");
				countsql.append(" and cu.userId =" + map.get("userId"));
			}
			sqlbuffer += " ) as acu on ac.courseId = acu.courseId where 1=1";
			countsql.append(") as acu on ac.courseId = acu.courseId where 1=1");
			// 添加查询条件 。。
			if (map.get("orgId") != null) {
				sqlbuffer += " and ac.orgId =" + map.get("orgId");
				countsql.append(" and ac.orgId =" + map.get("orgId"));
			}
			if (map.get("courseId") != null && !"".equals(map.get("courseId"))) {
				sqlbuffer += " and ac.courseId =" + map.get("courseId");
				countsql.append(" and ac.courseId =" + map.get("courseId"));
			}
			if (map.get("courseName") != null && !"".equals(map.get("courseName"))) {
				sqlbuffer += " and ac.name like '%" + map.get("courseName") + "%'";
				countsql.append(" and ac.name like '%" + map.get("courseName") + "%'");
			}

		}

		// 分页
		sqlbuffer += " limit " + offset + "," + page.getPageSize();
		String sql = sqlbuffer.toString();
		List<Map<String, Object>> userlist = mysqlClient.queryForList(sql, new Object[0]);

		// 查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(userlist);
		return page;
	}

	/**
	 * 
	 * @Title getVideoServer
	 * @Description
	 * @author xrl
	 * @Date 2017年5月13日
	 * @param courseId
	 * @return
	 */
	public String getVideoServer() {
		String sql = "select url from adks_heartbeat where state='alive'";
		List<Map<String, Object>> list = mysqlClient.queryForList(sql, null);
		if (list != null && list.size() > 0) {
			// 随机分配
			Integer index = new Random().nextInt(list.size());
			return list.get(index).get("url") + "";
		}
		return null;
	}
}
