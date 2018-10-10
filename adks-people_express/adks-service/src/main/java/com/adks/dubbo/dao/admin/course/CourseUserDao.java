package com.adks.dubbo.dao.admin.course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.adks.commons.util.DateUtils;
import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.course.Adks_course_sort;
import com.adks.dubbo.api.data.course.Adks_course_user;
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.commons.Page;

@Repository
public class CourseUserDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_course_user";
	}
	//课程分类的树
	public List<Adks_course_user> getTopCourseUserList(Map map){
		String sql = "select courseUserId, userId,courseId, orgId,courseName,courseCode,courseCwType ,courseImg,authorId,studyCourseTime,courseDuration,courseDurationLong,"
				+ "studyAllTimeLong,studyAllTime,lastStudyDate,lastPosition,isOver,gradeId,gradeName,isCollection from adks_course_user where 1=1 ";
		if(map.get("orgId")!=null && !"".equals(map.get("orgId"))){
			sql+=" and orgId ="+map.get("orgId");
		}
		if(map.get("num")!=null && !"".equals(map.get("num"))){
			sql+=" order by lastStudyDate desc limit 0,"+map.get("num");
		}
		List<Adks_course_user> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_course_user>() {
			@Override
			public Adks_course_user mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_course_user courseUser=new Adks_course_user();
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
				courseUser.setStudyAllTime(rs.getString("studyAllTimeLong"));
				courseUser.setStudyAllTimeLong(rs.getInt("studyAllTimeLong"));
				courseUser.setLastStudyDate(rs.getDate("lastStudyDate"));
				courseUser.setLastPosition(rs.getInt("lastPosition"));
				courseUser.setIsOver(rs.getInt("isOver"));
				courseUser.setGradeId(rs.getInt("gradeId"));
				courseUser.setGradeName(rs.getString("gradeName"));
				courseUser.setIsCollection(rs.getInt("isCollection"));
				return courseUser;
			}});
		if(reslist == null || reslist.size()<=0){
			return null;
		}
		return reslist;
	}
	
	/**
	 * 
	 * @Title getCourseUserByUserIdAndCourseId
	 * @Description
	 * @author xrl
	 * @Date 2017年6月3日
	 * @param map
	 * @return
	 */
	public Map<String, Object> getCourseUserByUserIdAndCourseId(Map<String, Object> map){
		String sql = " select * from adks_course_user where courseId="+map.get("courseId")+" and userId="+map.get("userId");
		return mysqlClient.queryForMap(sql, new Object[]{});
	}
	
	/**
	 * 
	 * @Title updateCourseUserForGrades
	 * @Description
	 * @author xrl
	 * @Date 2017年6月3日
	 * @param map
	 * @return
	 */
	public void updateCourseUserForGrades(Map<String, Object> map){
		String sql = "update adks_course_user set grades='"+map.get("grades")+"' where courseId="+map.get("courseId")+" and userId="+map.get("userId");
		mysqlClient.update(sql, null);
	}
	
	
	public List<Map<String, Object>> getCourseUserByCourseIds(Map<String, Object> map){
		String sql = " select courseId from adks_course_user where userId="+map.get("userId")+" and isOver=1"+" and courseId in("+map.get("courseIds")+")";
		return mysqlClient.queryForList(sql, new Object[]{});
	}
}
