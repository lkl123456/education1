package com.adks.dubbo.dao.app.course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.course.Adks_course_user;
import com.adks.dubbo.commons.Page;

@Repository
public class CourseUserAppDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_course_user";
	}

	/**
	 * 根据userId和courseId获取详细信息
	 * 
	 * @param userId
	 * @param courseId
	 * @return
	 */
	public Adks_course_user getCourseUser(int userId, int courseId) {
		Object[] obj = new Object[] { userId, courseId };
		String sql = "select "
				+ "courseUserId, userId,courseId, orgId,courseName,courseCode,courseCwType ,courseImg,authorId,studyCourseTime,courseDuration,courseDurationLong,studyAllTimeLong,studyAllTime,lastStudyDate,lastPosition,isOver,gradeId,gradeName,isCollection,xkDate "
				+ "from adks_course_user where 1=1 and userId=? and courseId=?";
		List<Adks_course_user> reslist = mysqlClient.query(sql, obj, new RowMapper<Adks_course_user>() {
			@Override
			public Adks_course_user mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_course_user courseUser = new Adks_course_user();
				courseUser.setCourseUserId(rs.getInt("courseUserId"));
				courseUser.setUserId(rs.getInt("userId"));
				courseUser.setCourseId(rs.getInt("courseId"));
				courseUser.setOrgId(rs.getInt("orgId"));
				courseUser.setCourseName(rs.getString("courseName"));
				courseUser.setCourseCode(rs.getString("courseCode"));
				courseUser.setCourseCwType(rs.getInt("courseCwType"));
				courseUser.setCourseImg(rs.getString("courseImg"));
				courseUser.setAuthorId(rs.getInt("authorId"));
				courseUser.setStudyCourseTime(rs.getInt("studyCourseTime"));
				courseUser.setCourseDuration(rs.getInt("courseDuration"));
				courseUser.setCourseDurationLong(rs.getString("courseDurationLong"));
				courseUser.setStudyAllTimeLong(rs.getInt("studyAllTimeLong"));
				courseUser.setStudyAllTime(rs.getString("studyAllTime"));
				courseUser.setLastStudyDate(rs.getDate("lastStudyDate"));
				courseUser.setLastPosition(rs.getInt("lastPosition"));
				courseUser.setIsOver(rs.getInt("isOver"));
				courseUser.setGradeId(rs.getInt("gradeId"));
				courseUser.setGradeName(rs.getString("gradeName"));
				courseUser.setIsCollection(rs.getInt("isCollection"));
				courseUser.setXkDate(rs.getDate("xkDate"));
				return courseUser;
			}
		});
		if (reslist == null || reslist.size() <= 0)
			return null;
		return reslist.get(0);
	}

	/**
	 * 根据userId获取未学完的课程信息
	 * 
	 * @param map
	 * @return
	 */
	public List<Adks_course_user> getCourseListByUserId(int userId) {
		String sql = "select "
				+ "courseUserId, userId,courseId, orgId,courseName,courseCode,courseCwType ,courseImg,authorId,studyCourseTime,courseDuration,courseDurationLong,studyAllTimeLong,studyAllTime,lastStudyDate,lastPosition,isOver,gradeId,gradeName,isCollection "
				+ "from adks_course_user where 1=1 and isOver=2 ";
		if (userId != 0) {
			sql += "and user=" + userId;
		}
		List<Adks_course_user> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_course_user>() {
			@Override
			public Adks_course_user mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_course_user courseUser = new Adks_course_user();
				courseUser.setCourseUserId(rs.getInt("courseUserId"));
				courseUser.setUserId(rs.getInt("userId"));
				courseUser.setCourseId(rs.getInt("courseId"));
				courseUser.setOrgId(rs.getInt("orgId"));
				courseUser.setCourseName(rs.getString("courseName"));
				courseUser.setCourseCode(rs.getString("courseCode"));
				courseUser.setCourseCwType(rs.getInt("courseCwType"));
				courseUser.setCourseImg(rs.getString("courseImg"));
				courseUser.setAuthorId(rs.getInt("authorId"));
				courseUser.setStudyCourseTime(rs.getInt("studyCourseTime"));
				courseUser.setCourseDuration(rs.getInt("courseDuration"));
				courseUser.setCourseDurationLong(rs.getString("courseDurationLong"));
				courseUser.setStudyAllTimeLong(rs.getInt("studyAllTimeLong"));
				courseUser.setStudyAllTime(rs.getString("studyAllTime"));
				courseUser.setLastStudyDate(rs.getDate("lastStudyDate"));
				courseUser.setLastPosition(rs.getInt("lastPosition"));
				courseUser.setIsOver(rs.getInt("isOver"));
				courseUser.setGradeId(rs.getInt("gradeId"));
				courseUser.setGradeName(rs.getString("gradeName"));
				courseUser.setIsCollection(rs.getInt("isCollection"));
				return courseUser;
			}
		});
		if (reslist == null || reslist.size() <= 0) {
			return null;
		}
		return reslist;
	}

	/**
	 * 根据userId、gradeId、status、isOver获取课程数 status：1必修；2选修 isOver：1已学完；2未学完
	 * 
	 * @param map
	 * @return
	 */
	public int getCourseCountByStatus(Map<String, Object> map) {
		String sql = "select count(1) from adks_grade_course gc ,adks_course_user cu "
				+ "where 1=1 gc.courseId = cu.courseId";
		if (map.get("userId") != null) {
			sql += " and cu.userId = " + map.get("userId");
		}
		if (map.get("gradeId") != null) {
			sql += " and gc.gradeId=" + map.get("gradeId");
		}
		if (map.get("status") != null) {
			sql += " and gc.gcState = " + map.get("status");
		}
		if (map.get("isOver") != null) {
			sql += " and cu.isOver =" + map.get("isOver");
		}

		return mysqlClient.queryforInt(sql, new Object[0]);
	}

	/**
	 * 获取全部课程列表
	 * 
	 * @param page
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Page<List<Map<String, Object>>> getCoursePage(Page<List<Map<String, Object>>> page) {
		Integer offset = page.getStartLimit(); // limit
		String sqlbuffer = "select "
				+ "cu.courseName,c.courseTimeLong as durationTime,cu.courseImg as courseImgPath,cu.courseId,cu.courseCwType as courseType,c.authorName,CONCAT(FORMAT((cu.studyAllTimeLong/c.courseDuration)*100,0),'%') as percentStr  "
				+ "from adks_grade_course gc ,adks_course_user cu,adks_grade g,adks_course c  "
				+ "where 1=1 and gc.courseId = cu.courseId and g.gradeId = gc.gradeId and gc.courseId = c.courseId ";
		StringBuilder countsql = new StringBuilder(
				"select count(1) from adks_grade_course gc ,adks_course_user cu,adks_grade g,adks_course c  "
						+ "where 1=1 and  gc.courseId = cu.courseId and g.gradeId = gc.gradeId and gc.courseId = c.courseId ");
		Map map = page.getMap();
		if (map != null && map.size() > 0) {
			// 添加查询条件
			if (map.get("userId") != null) {
				sqlbuffer += " and cu.userId =" + map.get("userId");
				countsql.append(" and cu.userId =" + map.get("userId"));
			}
			if (map.get("courseName") != null && !"".equals(map.get("courseName"))) {
				sqlbuffer += " and cu.courseName like '%" + map.get("courseName") + "%'";
				countsql.append(" and cu.courseName like '%" + map.get("courseName") + "%'");
			}
			if (map.get("gradeId") != null) {
				sqlbuffer += " and gc.gradeId =" + map.get("gradeId");
				countsql.append(" and gc.gradeId =" + map.get("gradeId"));
			}
			if (map.get("isOver") != null) {
				sqlbuffer += " and cu.isOver =" + map.get("isOver");
				countsql.append(" and cu.isOver =" + map.get("isOver"));
			}
			if (map.get("status") != null) {
				sqlbuffer += " and gc.gcState = " + map.get("status");
				countsql.append(" and gc.gcState = " + map.get("status"));
			}
		}
		if (map.get("dq") != null)
			sqlbuffer += " and now() < g.endDate and now()>g.startDate ";
		// 分页
		sqlbuffer += " order by gc.createTime desc limit " + offset + ", " + page.getPageSize();

		List<Map<String, Object>> gradeUserList = mysqlClient.queryForList(sqlbuffer, new Object[0]);
		// 查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(gradeUserList);
		return page;
	}

	/**
	 * 获取历史培训班的详细信息
	 * 
	 * @param page
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Page<List<Map<String, Object>>> getHistoryGradeCoursePage(Page<List<Map<String, Object>>> page) {
		Integer offset = page.getStartLimit(); // limit
		String sqlbuffer = "select cu.courseId,cu.courseName,cu.lastStudyDate,CONCAT(FORMAT((cu.studyAllTimeLong/c.courseDuration)*100,0),'%') as percentStr "
				+ "from adks_course_user cu ,adks_grade g,adks_course c,adks_grade_course gc "
				+ "where 1=1 and gc.courseId = c.courseId and g.gradeId = gc.gradeId and cu.courseId =c.courseId ";

		StringBuilder countsql = new StringBuilder(
				"select count(1) from adks_course_user cu ,adks_grade g,adks_course c,adks_grade_course gc "
						+ "where 1=1 and gc.courseId = c.courseId and g.gradeId = gc.gradeId and cu.courseId =c.courseId ");
		// #############
		Map map = page.getMap();
		if (map != null && map.size() > 0) {
			// 添加查询条件
			if (map.get("userId") != null) {
				sqlbuffer += "and cu.userId =" + map.get("userId");
				countsql.append(" and cu.userId =" + map.get("userId"));
			}
			if (map.get("gradeId") != null) {
				sqlbuffer += " and cu.gradeId =" + map.get("gradeId");
				countsql.append(" and cu.gradeId =" + map.get("gradeId"));
			}
			if (map.get("status") != null) {
				sqlbuffer += " and gc.gcState = " + map.get("status");
				countsql.append(" and gc.gcState = " + map.get("status"));
			}
		}
		// 分页
		sqlbuffer += " and g.endDate<NOW() order by gc.createTime desc limit " + offset + ", " + page.getPageSize();
		// #################

		List<Map<String, Object>> gradeUserList = mysqlClient.queryForList(sqlbuffer, new Object[0]);
		// 查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(gradeUserList);
		return page;
	}

	/**
	 * 获取班级全部课程
	 * 
	 * @param page
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Page<List<Map<String, Object>>> getGradeCoursePage(Page<List<Map<String, Object>>> page) {
		Integer offset = page.getStartLimit(); // limit
		String sqlbuffer = "select "
				+ "c.courseName,'0%' as percentStr,c.courseTimeLong as durationTime,c.coursePic as courseImgPath,c.courseId,c.courseType as courseType,c.authorName "
				+ "from adks_grade_course gc,adks_course c,adks_grade g "
				+ "where 1=1 and gc.courseId = c.courseId and g.gradeId = gc.gradeId and c.isAudit = 1 and c.isRecommend = 1 ";

		StringBuilder countsql = new StringBuilder(
				"select count(1) from adks_grade_course gc,adks_course c,adks_grade g "
						+ "where 1=1 and gc.courseId = c.courseId and g.gradeId = gc.gradeId and c.isAudit = 1 and c.isRecommend = 1 ");
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
			// 获取未学的课程
			if (map.get("userId") != null) {
				sqlbuffer += " and gc.courseId not in (select cu.courseId from adks_course_user cu where cu.userId ="
						+ map.get("userId") + ") ";
				countsql.append(" and gc.courseId not in (select cu.courseId from adks_course_user cu where cu.userId ="
						+ map.get("userId") + ") ");
			}
		}
		// 历史培训班的全部课程
		if (map.get("dq") != null) {
			sqlbuffer += " and now() < g.endDate and now()>g.startDate ";
		}

		// 分页
		sqlbuffer += " order by gc.createTime desc limit " + offset + ", " + page.getPageSize();
		List<Map<String, Object>> gradeUserList = mysqlClient.queryForList(sqlbuffer, new Object[0]);
		// 查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(gradeUserList);
		return page;
	}

	/**
	 * 查询推荐课程
	 * 
	 * @param page
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Page<List<Map<String, Object>>> getRecommendCoursePage(Page<List<Map<String, Object>>> page) {
		Integer offset = page.getStartLimit(); // limit
		String sqlbuffer = "select ac.*,ifnull(acu.isCollection,2) as iscollected from "
				+ "(select c.courseId,c.courseName as name,c.authorName,c.authorId,c.createtime as createDate,c.courseType,c.orgId,c.courseTimeLong as durationTime,c.coursePic as pathImg "
				+ "from adks_course c where c.isRecommend = 1  and c.isAudit = 1  ";
		StringBuilder countsql = new StringBuilder("select count(1) from "
				+ "(select c.courseId,c.courseName as name,c.authorName,c.authorId,c.createtime as createDate,c.orgId,c.courseTimeLong as durationTime,c.coursePic as pathImg "
				+ "from adks_course c where c.isRecommend = 1  and c.isAudit = 1 ");
		Map map = page.getMap();
		if (map != null && map.size() > 0) {
			if (map.get("orgId") != null) {
				sqlbuffer += "and (c.courseBelong = 1 or (c.orgId =" + map.get("orgId")
						+ " and c.courseBelong != 2))) ac left JOIN (select cu.isCollection,cu.courseId from adks_course_user cu where 1=1 ";
				countsql.append("and c.courseBelong = 1 or (c.orgId =" + map.get("orgId")
						+ " and (c.courseBelong != 2)))  ac left JOIN (select cu.isCollection,cu.courseId from adks_course_user cu where 1=1 ");
			}
			if (map.get("userId") != null) {
				sqlbuffer += " and cu.userId =" + map.get("userId");
				countsql.append(" and cu.userId =" + map.get("userId"));
			}
			sqlbuffer += " ) as acu on ac.courseId = acu.courseId where 1=1";
			countsql.append(") as acu on ac.courseId = acu.courseId where 1=1");
			if (map.get("orgId") != null) {
				sqlbuffer += " and ac.orgId =" + map.get("orgId");
				countsql.append(" and ac.orgId =" + map.get("orgId"));
			}
		}

		sqlbuffer += " order by ac.createDate desc";
		// 分页
		sqlbuffer += " limit " + offset + ", " + page.getPageSize();
		List<Map<String, Object>> gradeUserList = mysqlClient.queryForList(sqlbuffer, new Object[0]);
		// 查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(gradeUserList);
		return page;
	}

	/**
	 * 获取微课程列表
	 * 
	 * @param page
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Page<List<Map<String, Object>>> getWeiCoursePage(Page<List<Map<String, Object>>> page) {
		Integer offset = page.getStartLimit(); // limit
		String sqlbuffer = "select "
				+ "c.courseId,c.courseName as name,c.authorName,c.courseType,c.authorId,c.createtime as createDate,c.courseTimeLong as durationTime,cu.isCollection as iscollected,c.coursePic as pathImg "
				+ "from adks_course_user cu,adks_course c where 1=1 and c.courseId=cu.courseId ";
		StringBuilder countsql = new StringBuilder(
				"select count(1) from adks_course_user cu,adks_course c where 1=1 and c.courseId=cu.courseId ");
		Map map = page.getMap();
		if (map != null && map.size() > 0) {

			// 添加查询条件
			if (map.get("userId") != null) {
				sqlbuffer += " and cu.userId =" + map.get("userId");
				countsql.append(" and cu.userId =" + map.get("userId"));
			}
			if (map.get("orgId") != null) {
				sqlbuffer += " and  (c.courseBelong = 1 or (c.orgId =" + map.get("orgId")
						+ " and c.courseBelong != 2)) ";
				countsql.append(
						" and (c.courseBelong = 1 or (c.orgId =" + map.get("orgId") + " and c.courseBelong != 2)) ");
			}
		}
		sqlbuffer += " and c.courseType=683 order by c.createtime desc";
		// 分页
		sqlbuffer += " limit " + offset + ", " + page.getPageSize();
		List<Map<String, Object>> gradeUserList = mysqlClient.queryForList(sqlbuffer, new Object[0]);
		// 查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(gradeUserList);
		return page;
	}

	/**
	 * 获取班级全部课程信息
	 * 
	 * @param page
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Page<List<Map<String, Object>>> getAllGradeCoursePage(Page<List<Map<String, Object>>> page) {
		Map map = page.getMap();
		String sqlbuffer = "select a.courseId,CONCAT(FORMAT((ifnull(s.studyAllTimeLong,0)/a.courseDuration)*100,0),'%') as percentStr,a.courseTimeLong as durationTime,a.coursePic as courseImgPath,a.courseName,a.authorName,a.courseType "
				+ "from(select gc.courseId,c.coursePic,c.courseName,c.authorName,c.courseType,c.courseDuration,c.courseTimeLong from adks_grade g,adks_grade_course gc ,adks_course c where g.gradeId= gc.gradeId and gc.courseId = c.courseId and g.gradeId="
				+ map.get("gradeId") + " and gc.gcState = " + map.get("state") + ") as a "
				+ "left JOIN (select cu.courseId,cu.studyAllTimeLong from adks_grade g,adks_course_user cu where g.gradeId="
				+ map.get("gradeId") + " and cu.userId = " + map.get("userId") + ") as s "
				+ "on a.courseId = s.courseId ";
		StringBuilder countsql = new StringBuilder(
				"select count(1) from(select gc.courseId,c.coursePic,c.courseName,c.authorName,c.courseType,c.courseDuration,c.courseTimeLong from adks_grade g,adks_grade_course gc ,adks_course c where g.gradeId= gc.gradeId and gc.courseId = c.courseId and g.gradeId="
						+ map.get("gradeId") + " and gc.gcState = " + map.get("state") + ") as a "
						+ "left JOIN (select cu.courseId,cu.studyAllTimeLong from adks_grade g,adks_course_user cu where g.gradeId="
						+ map.get("gradeId") + " and cu.userId = " + map.get("userId") + ") as s "
						+ "on a.courseId = s.courseId ");
		Integer offset = page.getStartLimit(); // limit
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
