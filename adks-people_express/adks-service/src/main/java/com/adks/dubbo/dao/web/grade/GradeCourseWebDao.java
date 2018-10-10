package com.adks.dubbo.dao.web.grade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.adks.commons.util.DateUtils;
import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.course.Adks_course_user;
import com.adks.dubbo.api.data.grade.Adks_grade;
import com.adks.dubbo.api.data.grade.Adks_grade_course;
import com.adks.dubbo.api.data.grade.Adks_grade_user;
import com.adks.dubbo.api.data.news.Adks_news;
import com.adks.dubbo.commons.Page;

@Repository
public class GradeCourseWebDao extends BaseDao {

	
	@Override
	protected String getTableName() {
		return "adks_grade_course";
	}
	//未开始
	public Page<List<Adks_course_user>> gradeCourseListNoStudy(Page<List<Adks_course_user>> page) {
		Integer offset = null; //limit 起始位置
    	if(page.getCurrentPage()<=1){
    		offset=0;
    		page.setCurrentPage(1);
    	}else{
    		offset=(page.getCurrentPage() - 1) * page.getPageSize();
    	}
		StringBuffer sqlbuffer = new StringBuffer("select gc.gradeCourseId,gc.courseId,gc.courseName,gc.gcState,c.courseType as courseCwType,c.courseCode,"
				+ "c.courseTimeLong,c.coursePic as courseImg,c.authorName from adks_grade_course gc, adks_course c where gc.courseId=c.courseId "
				+ "and courseStatus=1 and isAudit=1 ");
		StringBuffer countsql = new StringBuffer("select count(1) from adks_grade_course gc , adks_course c where gc.courseId=c.courseId and courseStatus=1 and isAudit=1 ");
		Map map = page.getMap();
		if(map != null && map.size() > 0){
			//添加查询条件 。。
			if(map.get("gradeId") != null && !"".equals(map.get("gradeId"))){
				sqlbuffer.append(" and  gc.gradeId="+map.get("gradeId"));
				countsql.append(" and gc.gradeId="+map.get("gradeId"));
			}
			if(map.get("gcState") != null && !"".equals(map.get("gcState"))){
				sqlbuffer.append(" and gc.gcState="+map.get("gcState"));
				countsql.append(" and gc.gcState="+map.get("gcState"));
			}
			if(map.get("userId") != null && !"".equals(map.get("userId"))){
				sqlbuffer.append(" and gc.courseId not in (select cu.courseId from adks_course_user cu where "
						+" cu.userId="+map.get("userId")+" )");
				countsql.append(" and gc.courseId not in (select cu.courseId from adks_course_user cu where "
						+" cu.userId="+map.get("userId")+" )");
			}
		}
		
		sqlbuffer.append("  order by gc.gradeCourseId desc");
		//分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();
		
		List<Adks_course_user> coursesList = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_course_user>() {
			@Override
			public Adks_course_user mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_course_user courseuser=new Adks_course_user();
				courseuser.setGradeCourseId(rs.getInt("gradeCourseId"));
				courseuser.setCourseId(rs.getInt("courseId"));
				courseuser.setCourseName(rs.getString("courseName"));
				courseuser.setGcState(rs.getInt("gcState"));
				courseuser.setCourseCwType(rs.getInt("courseCwType"));
				courseuser.setCourseCode(rs.getString("courseCode"));
				courseuser.setCourseTimeLong(rs.getString("courseTimeLong"));
				courseuser.setCourseImg(rs.getString("courseImg"));
				courseuser.setAuthorName(rs.getString("authorName"));
				return courseuser;
			}});
		
		//查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setTotalRecords(totalcount);
		if(totalcount%page.getPageSize()==0){
			page.setTotalPage(totalcount/page.getPageSize());
			page.setTotalPages(totalcount/page.getPageSize());
		}else{
			page.setTotalPage(totalcount/page.getPageSize()+1);
			page.setTotalPages(totalcount/page.getPageSize()+1);
		}
		
		page.setRows(coursesList);
		return page;
	}
	//班级课程已学完或是学习中
	public Page<List<Adks_course_user>> gradeCourseUserList(Page<List<Adks_course_user>> page) {
		Integer offset = null; //limit 起始位置
    	if(page.getCurrentPage()<=1){
    		offset=0;
    		page.setCurrentPage(1);
    	}else{
    		offset=(page.getCurrentPage() - 1) * page.getPageSize();
    	}
		StringBuffer sqlbuffer = new StringBuffer("select gradeCourseId,courseId,courseName,courseCwType,courseCode,courseTimeLong,courseImg ,authorName,gcState  from "
				+ " gradeCourseXueWan2 where 1=1 ");
		
		StringBuffer countsql = new StringBuffer("select count(1) from "
				+ " gradeCourseXueWan2 where 1=1 ");
		Map map = page.getMap();
		if(map != null && map.size() > 0){
			//添加查询条件 。。
			if(map.get("gradeId") != null && !"".equals(map.get("gradeId"))){
				sqlbuffer.append(" and  gradeId="+map.get("gradeId"));
				countsql.append(" and gradeId="+map.get("gradeId"));
			}
			if(map.get("userId") != null && !"".equals(map.get("userId"))){
				sqlbuffer.append(" and userId="+map.get("userId"));
				countsql.append(" and userId="+map.get("userId"));
			}
			if(map.get("gcState") != null && !"".equals(map.get("gcState"))){
				sqlbuffer.append(" and gcState="+map.get("gcState"));
				countsql.append(" and gcState="+map.get("gcState"));
			}
			if(map.get("studyed") != null && "5".equals(map.get("studyed"))){
				sqlbuffer.append(" and isOver=2 ");
				countsql.append(" and isOver=2 ");
			}
			if(map.get("studyed") != null && "6".equals(map.get("studyed"))){
				sqlbuffer.append(" and isOver=1 ");
				countsql.append(" and isOver=1 ");
			}
			
		}
		sqlbuffer.append(" order by gradeCourseId desc ");
		//分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();
		
		List<Adks_course_user> coursesList = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_course_user>() {
				@Override
				public Adks_course_user mapRow(ResultSet rs, int rowNum) throws SQLException {
					Adks_course_user courseuser=new Adks_course_user();
					courseuser.setGradeCourseId(rs.getInt("gradeCourseId"));
					courseuser.setCourseId(rs.getInt("courseId"));
					courseuser.setCourseName(rs.getString("courseName"));
					courseuser.setGcState(rs.getInt("gcState"));
					courseuser.setCourseCwType(rs.getInt("courseCwType"));
					courseuser.setCourseCode(rs.getString("courseCode"));
					courseuser.setCourseTimeLong(rs.getString("courseTimeLong"));
					courseuser.setCourseImg(rs.getString("courseImg"));
					courseuser.setAuthorName(rs.getString("authorName"));
					return courseuser;
				}});
		
		//查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setTotalRecords(totalcount);
		if(totalcount%page.getPageSize()==0){
			page.setTotalPage(totalcount/page.getPageSize());
			page.setTotalPages(totalcount/page.getPageSize());
		}else{
			page.setTotalPage(totalcount/page.getPageSize()+1);
			page.setTotalPages(totalcount/page.getPageSize()+1);
		}
		
		page.setRows(coursesList);
		return page;
	}

	//得到班级课程全部、必修、选修
	public Page<List<Adks_course_user>> getAllGradeCourseList(Page<List<Adks_course_user>> page) {
		Integer offset = null; //limit 起始位置
    	if(page.getCurrentPage()<=1){
    		offset=0;
    		page.setCurrentPage(1);
    	}else{
    		offset=(page.getCurrentPage() - 1) * page.getPageSize();
    	}
		StringBuffer sqlbuffer = new StringBuffer("select gc.gradeCourseId,gc.courseId,gc.courseName,gc.gcState,c.courseType as courseCwType,c.courseCode"
				+ ",c.courseTimeLong,c.coursePic as courseImg,c.authorName from adks_grade_course gc , adks_course c "
				+ "where gc.courseId=c.courseId  and  courseStatus=1 and isAudit=1 ");
		StringBuffer countsql = new StringBuffer("select count(1) from adks_grade_course gc , adks_course c "
				+ "where gc.courseId=c.courseId  and  courseStatus=1 and isAudit=1 ");
		Map map = page.getMap();
		if(map != null && map.size() > 0){
			//添加查询条件 。。
			if(map.get("gradeId") != null && !"".equals(map.get("gradeId"))){
				sqlbuffer.append(" and  gc.gradeId="+map.get("gradeId"));
				countsql.append(" and gc.gradeId="+map.get("gradeId"));
			}
			if(map.get("gcState") != null && !"".equals(map.get("gcState"))){
				sqlbuffer.append(" and gc.gcState="+map.get("gcState"));
				countsql.append(" and gc.gcState="+map.get("gcState"));
			}
			if(map.get("courseSortCode") != null && !"".equals(map.get("courseSortCode"))){
				sqlbuffer.append(" and c.courseSortCode like '%"+map.get("courseSortCode")+"%' ");
				countsql.append(" and c.courseSortCode like '%"+map.get("courseSortCode")+"%' ");
			}
		}
		sqlbuffer.append("  order by gc.gcState asc,gc.createTime desc");
		//分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();
		
		List<Adks_course_user> coursesList = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_course_user>() {
			@Override
			public Adks_course_user mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_course_user courseuser=new Adks_course_user();
				courseuser.setGradeCourseId(rs.getInt("gradeCourseId"));
				courseuser.setCourseId(rs.getInt("courseId"));
				courseuser.setCourseName(rs.getString("courseName"));
				courseuser.setGcState(rs.getInt("gcState"));
				courseuser.setCourseCwType(rs.getInt("courseCwType"));
				courseuser.setCourseCode(rs.getString("courseCode"));
				courseuser.setCourseTimeLong(rs.getString("courseTimeLong"));
				courseuser.setCourseImg(rs.getString("courseImg"));
				courseuser.setAuthorName(rs.getString("authorName"));
				return courseuser;
			}});
		
		//查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setTotalRecords(totalcount);
		if(totalcount%page.getPageSize()==0){
			page.setTotalPage(totalcount/page.getPageSize());
			page.setTotalPages(totalcount/page.getPageSize());
		}else{
			page.setTotalPage(totalcount/page.getPageSize()+1);
			page.setTotalPages(totalcount/page.getPageSize()+1);
		}
		
		page.setRows(coursesList);
		return page;
	}

	//专题 获取班级课程集合
	public List<Adks_grade_course> getGradeCourseByGradeId(Map map){
		String sql = "select gc.gradeCourseId ,gc.rankNum ,gc.gcState,gc.Credit,gc.gradeId,gc.gradeName,gc.courseId,gc.courseCode,gc.courseName,"
				+ "gc.courseCatalogId,gc.courseCatalogName,gc.courseCatalogCode,gc.cwType,gc.creatorId,gc.creatorName,gc.createTime "
				+ "from adks_grade_course gc,adks_course c where gc.courseId=c.courseId and c.isAudit=1 and c.courseStatus=1 ";
		if(map.get("gradeId")!=null && !"".equals(map.get("gradeId"))){
			sql+=" and gc.gradeId ="+map.get("gradeId");
		}
		if(map.get("gcState")!=null && !"".equals(map.get("gcState"))){
			sql+=" and gc.gcState ="+map.get("gcState");
		}
		sql+=" order by gc.rankNum desc ";
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
	 * @Title getGradeCourseByCourseAndGradeId
	 * @Description
	 * @author xrl
	 * @Date 2017年6月3日
	 * @param map
	 * @return
	 */
	public Adks_grade_course getGradeCourseByCourseAndGradeId(Map map) {
    	String sql = "select * from adks_grade_course where gradeId="+map.get("gradeId")+" and courseId="+map.get("courseId");
        List<Adks_grade_course> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_grade_course>() {
            @Override
            public Adks_grade_course mapRow(ResultSet rs, int rowNum) throws SQLException {
            	Adks_grade_course gradeCourse = new Adks_grade_course();
            	gradeCourse.setCredit(rs.getDouble("credit"));
                return gradeCourse;
            }
        });
        if (reslist == null || reslist.size() <= 0) {
            return null;
        }
        return reslist.get(0);
	}
	
}
