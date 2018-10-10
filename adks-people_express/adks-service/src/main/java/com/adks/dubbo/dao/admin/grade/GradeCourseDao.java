package com.adks.dubbo.dao.admin.grade;

import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.grade.Adks_grade_course;
import com.adks.dubbo.commons.Page;

@Component
public class GradeCourseDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_grade_course";
	}
	
	/**
	 * 
	 * @Title getCourseListPage
	 * @Description：班级未选课程分页列表
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getCourseListPage(Page<List<Map<String, Object>>> page){
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); //limit 起始位置
		String sqlbuffer = "select courseId,courseName,courseType,courseSortName,courseTimeLong,courseDuration,courseStatus,createTime "
				+" from adks_course "
				+" where 1=1 ";
		StringBuilder countsql = new StringBuilder("select count(1) from adks_course where 1=1 ");
		Map map = page.getMap();
		if(map != null && map.size() > 0){
			//添加查询条件 。。
			if(map.get("courseName") != null){
				sqlbuffer += " and courseName like '%"+map.get("courseName")+"%'";
				countsql.append(" and courseName like '%"+map.get("courseName")+"%'");
			}
			if(map.get("courseIds") != null){
				sqlbuffer += " and courseId not in ("+map.get("courseIds")+")";
				countsql.append(" and courseId not in ("+map.get("courseIds")+")");
			}
			if(map.get("orgCode")!=null){
				sqlbuffer += " and (orgCode like '"+map.get("orgCode")+"%' or courseBelong=1 ) ";
				countsql.append(" and (orgCode like '"+map.get("orgCode")+"%' or courseBelong=1 ) ");
			}
			if(map.get("courseSortIds") != null){
				sqlbuffer += " and courseSortCode like '"+map.get("courseSortIds")+"%'";
				countsql.append(" and courseSortCode like '"+map.get("courseSortIds")+"%'");
			}
		}
		//分页
		sqlbuffer += " limit " + offset +", " + page.getPageSize();
		List<Map<String, Object>> courseList = mysqlClient.queryForList(sqlbuffer, new Object[0]);
		for (Map<String, Object> course : courseList) {
			if(course.get("courseDuration")!=null&&course.get("courseDuration")!=""){
				Integer hours=(Integer)course.get("courseDuration");
				Double hourse = Double.valueOf(hours)/Double.valueOf(2700);
				DecimalFormat df = new DecimalFormat("0.0");//格式化小数，不足的补0
				df.setRoundingMode(RoundingMode.FLOOR);  //取消四舍五入
				String credit = df.format(hourse);//返回的是String类型的
				course.put("credit", credit);
			}
		}
		//查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(courseList);
		return page;
	}
	
	/**
	 * 
	 * @Title getGradeCourseListPage
	 * @Description：班级已选课程列表
	 * @author xrl
	 * @Date 2017年3月31日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradeCourseListPage(Page<List<Map<String, Object>>> page){
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); //limit 起始位置
		String sqlbuffer = "select t1.courseId,t1.courseName,t1.courseTimeLong,t1.courseStatus,t1.courseSortName,t1.courseType as cwType,"
				+ "t2.gradeId,t2.gradeName,t2.createTime,t2.credit,t2.gcState "
				+" from adks_course t1,adks_grade_course t2 "
				+" where 1=1 and t1.courseId = t2.courseId ";
		StringBuilder countsql = new StringBuilder("select count(1) from adks_course t1,adks_grade_course t2 where 1=1 and  t1.courseId = t2.courseId ");
		Map map = page.getMap();
		if(map != null && map.size() > 0){
			//添加查询条件 。。
			if(map.get("courseName") != null){
				sqlbuffer += " and t1.courseName like '%"+map.get("courseName")+"%'";
				countsql.append(" and t1.courseName like '%"+map.get("courseName")+"%'");
			}
			if(map.get("gradeId") != null){
				sqlbuffer += " and t2.gradeId="+map.get("gradeId");
				countsql.append(" and t2.gradeId="+map.get("gradeId"));
			}
		}
		//分页
		sqlbuffer += " limit " + offset +", " + page.getPageSize();
		List<Map<String, Object>> gradeCourseList = mysqlClient.queryForList(sqlbuffer, new Object[0]);
		for (Map<String, Object> gradeCourse : gradeCourseList) {
			if(gradeCourse.get("credit")!=null&&gradeCourse.get("credit")!=""){
				Double hours=(Double)gradeCourse.get("credit");
				Double hourse = hours/Double.valueOf(2700);
				DecimalFormat df = new DecimalFormat("0.0");//格式化小数，不足的补0
				df.setRoundingMode(RoundingMode.FLOOR);  //取消四舍五入
				String credit = df.format(hourse);//返回的是String类型的
				gradeCourse.put("credit",Double.valueOf(credit));
			}
		}
		//查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(gradeCourseList);
		return page;
	}
	
	/**
	 * 
	 * @Title getCourseByCourseId
	 * @Description：根据courseId获取课程信息
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param courseId
	 * @return
	 */
	public Map<String, Object> getCourseByCourseId(Integer courseId){
		Object[] obj = new Object[]{courseId};
		String sql = "select courseId,courseName,courseCode,courseType,courseSortId,courseSortName,courseSortCode,courseDuration "
				+ "from adks_course where courseId=? ";
		return mysqlClient.queryForMap(sql, obj);
	}

	/**
	 * 
	 * @Title removeGradeCourse
	 * @Description：移除班级课程
	 * @author xrl
	 * @Date 2017年3月31日
	 * @param map
	 */
	public void removeGradeCourse(Map<String, Object> map){
		Object[] obj = new Object[]{map.get("gradeId"),map.get("courseId")};
		String sql = "delete from adks_grade_course where gradeId=? and courseId=?";
		mysqlClient.update(sql, obj);
	}
	
	/**
	 * 
	 * @Title delGradeCourseByGradeId
	 * @Description：根据gradeId删除班级Id
	 * @author xrl
	 * @Date 2017年4月27日
	 * @param gradeId
	 */
	public void delGradeCourseByGradeId(Integer gradeId){
		Object[] obj = new Object[]{gradeId};
		String sql = "delete from adks_grade_course where gradeId=? ";
		mysqlClient.update(sql, obj);
	}
	
	public void setGradeCoursesType(Map<String, Object> map){
		Object[] obj = new Object[]{map.get("gcState"),map.get("gradeId"),map.get("courseId")};
		String sql = "update adks_grade_course set gcState=? where gradeId=? and courseId=? ";
		mysqlClient.update(sql, obj);
	}
	
	/**
	 * 
	 * @Title getGradeCourseByGradeId
	 * @Description：获取班级课程集合
	 * @author xrl
	 * @Date 2017年5月22日
	 * @param map
	 * @return
	 */
	public List<Adks_grade_course> getGradeCourseByGradeId(String gradeId){
		String sql = "select gradeCourseId ,rankNum ,gcState,Credit,gradeId,gradeName,courseId,courseCode,"
				+ "courseName,courseCatalogId,courseCatalogName,courseCatalogCode,cwType,creatorId,creatorName,createTime from adks_grade_course where  1=1 ";
		if(gradeId!=null && !"".equals(gradeId)){
			sql+=" and gradeId ="+gradeId;
		}
		sql+=" order by rankNum desc ";
		List<Adks_grade_course> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_grade_course>() {
			@Override
			public Adks_grade_course mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_grade_course gradeCourse=new Adks_grade_course();
				gradeCourse.setCourseId(rs.getInt("courseId"));
				gradeCourse.setCourseName(rs.getString("courseName"));
				gradeCourse.setCourseCode(rs.getString("courseCode"));
				gradeCourse.setGradeId(rs.getInt("gradeId"));
				gradeCourse.setGradeName(rs.getString("gradeName"));
				gradeCourse.setGradeCourseId(rs.getInt("gradeCourseId"));
				gradeCourse.setCourseCatalogId(rs.getInt("courseCatalogId"));
				gradeCourse.setCourseCatalogName(rs.getString("courseCatalogName"));
				gradeCourse.setCourseCatalogCode(rs.getString("courseCatalogCode"));
				gradeCourse.setRankNum(rs.getInt("rankNum"));
				gradeCourse.setGcState(rs.getInt("gcState"));
				gradeCourse.setCwType(rs.getInt("cwType"));
				gradeCourse.setCredit(rs.getDouble("Credit"));
				gradeCourse.setCreatorId(rs.getInt("creatorId"));
				gradeCourse.setCreatorName(rs.getString("creatorName"));
				gradeCourse.setCreateTime(rs.getDate("createTime"));
				return gradeCourse;
			}});
		if(reslist == null || reslist.size()<=0){
			return null;
		}
		return reslist;
	}
	
	/**
	 * 
	 * @Title getGradeCourseNum
	 * @Description：获取班级选修、必修课程数量
	 * @author xrl
	 * @Date 2017年6月27日
	 * @param map
	 * @return
	 */
	public Map<String, Object> getGradeCourseNum(Map<String, Object> map){
		Object[] obj = new Object[]{map.get("gradeId")};
		String sql = "select gc.gradeId,SUM(gc.gcState=1) as requiredNum,SUM(gc.gcState=2) as optionalNum "
				+ "from adks_grade_course gc where gradeId=? ";
		return mysqlClient.queryForMap(sql, obj);
	}
	
	/**
	 * 
	 * @Title getGradeCourseCredits
	 * @Description
	 * @author xrl
	 * @Date 2017年6月12日
	 * @param map
	 * @return
	 */
	public Double getGradeCourseCredits(Map<String, Object> map){
		String sql="select sum(credit) as credit from adks_grade_course "
				+ "where gradeId="+map.get("gradeId")+" and gcState="+map.get("gcState")+" and courseId in("+map.get("cids")+") ";
		List<Map<String,Object>> list=mysqlClient.queryForList(sql,null);
		if(list!=null && list.size()>0){
			return (Double)list.get(0).get("credit");
		}else{
			return 0.0;
		}
	}
	
	/**
	 * 
	 * @Title getGradeCourseCredit
	 * @Description
	 * @author xrl
	 * @Date 2017年6月12日
	 * @param map
	 * @return
	 */
	public Double getGradeCourseCredit(Map<String, Object> map){
		Object[] obj = new Object[]{map.get("gradeId"),map.get("gcState")};
		String sql="select sum(credit) as credit from adks_grade_course "
				+ "where gradeId=? and gcState=? ";
		List<Map<String,Object>> list=mysqlClient.queryForList(sql, obj);
		if(list!=null && list.size()>0){
			return (Double)list.get(0).get("credit");
		}else{
			return 0.0;
		}
	}
	
	/**
	 * 
	 * @Title getGradeUserCoursePage
	 * @Description:获取班级学员课程分页列表
	 * @author xrl
	 * @Date 2017年6月15日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradeUserCoursePage(Page<List<Map<String, Object>>> page){
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); //limit 起始位置
		String sqlbuffer = "SELECT gc.gradeId,gc.gradeCourseId,gc.courseId,gc.courseName,gc.gcState,ROUND(gc.Credit/2700,1) as credit,IFNULL(cu.isOver,0) as isOver,gc.courseCatalogName,gu.userId "
				+"from adks_course c "
				+"LEFT JOIN adks_grade_course gc ON gc.courseId=c.courseId "
				+ "LEFT JOIN adks_grade_user gu ON gu.gradeId=gc.gradeId "
				+ "LEFT JOIN adks_course_user cu ON gc.courseId=cu.courseId and c.courseId=cu.courseId and cu.userId=gu.userId "
				+" where 1=1 ";
		StringBuilder countsql = new StringBuilder("SELECT count(1) "
				+ "from adks_course c "
				+ "LEFT JOIN adks_grade_course gc ON gc.courseId=c.courseId "
				+ "LEFT JOIN adks_grade_user gu ON gu.gradeId=gc.gradeId "
				+ "LEFT JOIN adks_course_user cu ON gc.courseId=cu.courseId and c.courseId=cu.courseId and cu.userId=gu.userId "
				+ "where 1=1");
		Map map = page.getMap();
		if(map != null && map.size() > 0){
			//添加查询条件 。。
			if(map.get("courseName") != null){
				sqlbuffer += " and gc.courseName like '%"+map.get("courseName")+"%'";
				countsql.append(" and gc.courseName like '%"+map.get("courseName")+"%'");
			}
			if(map.get("userId") != null){
				sqlbuffer += " and gu.userId="+map.get("userId");
				countsql.append(" and gu.userId="+map.get("userId"));
			}
			if(map.get("gradeId")!=null){
				sqlbuffer += " and gc.gradeId="+map.get("gradeId");
				countsql.append(" and gc.gradeId="+map.get("gradeId"));
			}
		}
		//分页
		sqlbuffer += " limit " + offset +", " + page.getPageSize();
		List<Map<String, Object>> courseList = mysqlClient.queryForList(sqlbuffer, new Object[0]);
		//查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(courseList);
		return page;
	}
	
	/**
	 * 
	 * @Title getGradeCourseByGradeIdAndCourseId
	 * @Description:根据gradeId和courseId获取班级课程信息
	 * @author xrl
	 * @Date 2017年6月15日
	 * @param map
	 * @return
	 */
	public Map<String, Object> getGradeCourseByGradeIdAndCourseId(Map<String, Object> map){
		Object[] obj = new Object[]{map.get("gradeId"),map.get("courseId")};
		String sql = "select * from adks_grade_course where gradeId=? and courseId=?";
		return mysqlClient.queryForMap(sql, obj);
	}
	
	/**
	 * 
	 * @Title getGradeUserCourseList
	 * @Description：获取班级学员学课信息
	 * @author xrl
	 * @Date 2017年6月16日
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getGradeUserCourseList(Map<String,Object> map){
		Object[] obj = new Object[]{map.get("gradeId"),map.get("userId"),map.get("gcState")};
		String sql = " select cu.courseName,cu.isOver,cu.courseDuration,cu.studyAllTimeLong,cu.xkDate,gc.credit,gc.gcState "
				+ "from adks_course_user cu "
				+ "LEFT JOIN adks_grade_course gc on gc.courseId=cu.courseId "
				+ "LEFT JOIN adks_grade_user gu on gu.gradeId=gc.gradeId and gu.userId=cu.userId "
				+ "where gu.gradeId=? and cu.userId=? and gcState=? ";
		return mysqlClient.queryForList(sql,obj);
	}
}
