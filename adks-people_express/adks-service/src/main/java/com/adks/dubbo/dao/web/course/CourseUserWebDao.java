package com.adks.dubbo.dao.web.course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.adks.commons.util.ComUtil;
import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.api.data.course.Adks_course_user;
import com.adks.dubbo.api.data.grade.Adks_grade_course;
import com.adks.dubbo.api.data.grade.Adks_grade_user;
import com.adks.dubbo.commons.Page;

@Repository
public class CourseUserWebDao extends BaseDao {

    @Override
    protected String getTableName() {
        return "adks_course_user";
    }
    
    public List<Adks_course_user> getCourseUserByCon(Map map) {
		String sql="select gc.courseName,cu.xkDate,cu.courseDuration,cu.courseDurationLong,cu.studyAllTimeLong "
				+ "from adks_grade_course gc,adks_course_user cu "
				+ "where gc.courseId=cu.courseId and cu.userId="+map.get("userId")+" and gc.gcState="+map.get("gcState")+" and gc.gradeId="+map.get("gradeId");
		List<Adks_course_user> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_course_user>() {
            @Override
            public Adks_course_user mapRow(ResultSet rs, int rowNum) throws SQLException {
                Adks_course_user courseUser = new Adks_course_user();
                courseUser.setCourseName(rs.getString("courseName"));
                courseUser.setXkDate(rs.getDate("xkDate"));
                courseUser.setCourseDuration(rs.getInt("courseDuration"));
                courseUser.setStudyAllTimeLong(rs.getInt("studyAllTimeLong"));
                courseUser.setCourseDurationLong(rs.getString("courseDurationLong"));
                return courseUser;
            }
        });
        if (reslist == null || reslist.size() <= 0) {
            return null;
        }
        return reslist;
	}
    
    public Adks_course_user getCourseUserByGradeIdCourseIdAndUserIdforCourseJD(Map map) {
//    	String sql = "select cu.courseUserId,cu.courseDuration,cu.studyAllTimeLong from adks_course_user cu where  "
//    			+ "cu.gradeId="+map.get("gradeId")+" and cu.userId="+map.get("userId")+"  and cu.courseId="+map.get("courseId");
    	String sql = "select cu.courseUserId,cu.courseDuration,cu.studyAllTimeLong from adks_course_user cu where  "
    			+"cu.userId="+map.get("userId")+"  and cu.courseId="+map.get("courseId");
        List<Adks_course_user> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_course_user>() {
            @Override
            public Adks_course_user mapRow(ResultSet rs, int rowNum) throws SQLException {
                Adks_course_user courseUser = new Adks_course_user();
                courseUser.setCourseUserId(rs.getInt("courseUserId"));
                courseUser.setCourseDuration(rs.getInt("courseDuration"));
                courseUser.setStudyAllTimeLong(rs.getInt("studyAllTimeLong"));
                return courseUser;
            }
        });
        if (reslist == null || reslist.size() <= 0) {
            return null;
        }
        return reslist.get(0);
	}
    
    //首页大家正在看
    public List<Adks_course_user> getTopCourseUserList(Map map) {
        String sql = "select acu.courseUserId, acu.userId,acu.userName,acu.courseId, acu.orgId,acu.courseName,acu.courseCode,acu.courseCwType ,acu.courseImg,acu.authorId,acu.studyCourseTime,"
        		+ "acu.courseDuration,acu.courseDurationLong,acu.studyAllTimeLong,acu.studyAllTime,acu.lastStudyDate,acu.lastPosition,acu.isOver,acu.gradeId,acu.gradeName,acu.isCollection "
        		+ "from adks_course_user acu,adks_user u where 1=1 and acu.userId=u.userId ";
        if (map.get("orgCode") != null && !"".equals(map.get("orgCode"))) {
            sql += " and u.orgCode like '%" + map.get("orgCode") + "%'";
        }
        if (map.get("num") != null && !"".equals(map.get("num"))) {
            sql += " order by acu.lastStudyDate desc limit 0," + map.get("num");
        }
        List<Adks_course_user> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_course_user>() {
            @Override
            public Adks_course_user mapRow(ResultSet rs, int rowNum) throws SQLException {
                Adks_course_user courseUser = new Adks_course_user();
                courseUser.setCourseUserId(rs.getInt("courseUserId"));
                courseUser.setUserId(rs.getInt("userId"));
                courseUser.setUserName(rs.getString("userName"));
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
                courseUser.setStudyAllTime(rs.getString("studyAllTime"));
                courseUser.setStudyAllTimeLong(rs.getInt("studyAllTimeLong"));
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

    public Map<String, Object> getStudyCourseTime(Integer userId) {
        String sql = "SELECT SUM(studyAllTimeLong) as studyAllTimeLong,SUM(courseDuration) as courseDuration  FROM adks_course_user WHERE 1=1 AND userId = ?";
        return mysqlClient.queryForMap(sql, new Object[] { userId });
    }
    
    public Page<List<Adks_course_user>> getCourseUserByUserId(Page<List<Adks_course_user>> page) {
    	Integer offset = null; //limit 起始位置
    	if(page.getCurrentPage()<=1){
    		offset=0;
    		page.setCurrentPage(1);
    	}else{
    		offset=(page.getCurrentPage() - 1) * page.getPageSize();
    	}
		StringBuffer sqlbuffer = new StringBuffer("select cu.courseUserId,cu.courseId,cu.courseName,cu.isOver,cu.xkdate "
				+ "from adks_course_user cu where 1=1 ");
		StringBuffer countsql = new StringBuffer("select count(1) from adks_course_user cu where 1=1 ");
		Map map = page.getMap();
		if(map != null && map.size() > 0){
			//添加查询条件 。。
			if(map.get("userId") != null){
				sqlbuffer.append(" and cu.userId="+map.get("userId"));
				countsql.append(" and cu.userId="+map.get("userId"));
			}
			if(map.get("selYear") != null){
				sqlbuffer.append(" and year(cu.xkdate)="+map.get("selYear"));
				countsql.append(" and year(cu.xkdate)="+map.get("selYear"));
			}
		}
		sqlbuffer.append(" order by cu.lastStudyDate desc");
		countsql.append(" order by cu.lastStudyDate desc");
		//分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();
		
		List<Adks_course_user> coursesList = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_course_user>() {
			@Override
			public Adks_course_user mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_course_user courseuser=new Adks_course_user();
				courseuser.setCourseUserId(rs.getInt("courseUserId"));
				courseuser.setCourseId(rs.getInt("courseId"));
				courseuser.setCourseName(rs.getString("courseName"));
				courseuser.setIsOver(rs.getInt("isOver"));
				courseuser.setXkDate(rs.getDate("xkdate"));
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

    public Page<List<Adks_course_user>> getUserCourseViewByUserId(Page<List<Adks_course_user>> page) {
    	Integer offset = null; //limit 起始位置
    	if(page.getCurrentPage()<=1){
    		offset=0;
    		page.setCurrentPage(1);
    	}else{
    		offset=(page.getCurrentPage() - 1) * page.getPageSize();
    	}
		StringBuffer sqlbuffer = new StringBuffer("select courseUserId,courseId,courseName,userId,lastStudyDate,lastPosition,isOver,xkDate,isCollection,"
				+ "courseDuration,courseCwType,courseCode from adks_course_user  where 1=1 ");
		StringBuffer countsql = new StringBuffer("select count(1) from adks_course_user  where 1=1 ");
		Map map = page.getMap();
		if(map != null && map.size() > 0){
			//添加查询条件 。。
			if(map.get("userId") != null){
				sqlbuffer.append(" and userId="+map.get("userId"));
				countsql.append(" and userId="+map.get("userId"));
			}
			
		}
		sqlbuffer.append(" order by lastStudyDate desc");
		countsql.append(" order by lastStudyDate desc");
		//分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();
		
		List<Adks_course_user> coursesList = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_course_user>() {
			@Override
			public Adks_course_user mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_course_user courseuser=new Adks_course_user();
				courseuser.setCourseUserId(rs.getInt("courseUserId"));
				courseuser.setCourseId(rs.getInt("courseId"));
				courseuser.setCourseName(rs.getString("courseName"));
				courseuser.setUserId(rs.getInt("userId"));
				courseuser.setLastStudyDate(rs.getDate("lastStudyDate"));
				courseuser.setLastPosition(rs.getInt("lastPosition"));
				courseuser.setIsCollection(rs.getInt("isCollection"));
				courseuser.setIsOver(rs.getInt("isOver"));
				courseuser.setXkDate(rs.getDate("xkdate"));
				courseuser.setCourseDuration(rs.getInt("courseDuration"));
				courseuser.setCourseCwType(rs.getInt("courseCwType"));
				courseuser.setCourseCode(rs.getString("courseCode"));
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
    
    /**
     * 
     * @Title getCourseUserByCourseIdAndUserId
     * @Description：根据课程ID和班级ID获取用户课程记录
     * @author xrl
     * @Date 2017年5月11日
     * @param courseId
     * @param userId
     * @return
     */
    public Adks_course_user getCourseUserByCourseIdAndUserId(Integer courseId,Integer userId){
    	 String sql = "select * "
    	 		+ "from adks_course_user "
    	 		+ "where courseId="+courseId+" and userId="+userId;
         List<Adks_course_user> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_course_user>() {
             @Override
             public Adks_course_user mapRow(ResultSet rs, int rowNum) throws SQLException {
                 Adks_course_user courseUser = new Adks_course_user();
                 courseUser.setCourseUserId(rs.getInt("courseUserId"));
                 courseUser.setUserId(rs.getInt("userId"));
                 courseUser.setUserName(rs.getString("userName"));
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
                 courseUser.setXkDate(rs.getDate("xkdate"));
                 courseUser.setGrades(rs.getString("grades"));
                 return courseUser;
             }
         });
         if (reslist == null || reslist.size() <= 0) {
             return null;
         }
         return reslist.get(0);
    }
    
    /**
     * 
     * @Title getGradeUser
     * @Description：根据gradeId和userId获取班级用户信息
     * @author xrl
     * @Date 2017年5月12日
     * @param userId
     * @param gradeId
     * @return
     */
    public Adks_grade_user getGradeUser(Integer gradeId,Integer userId){
    	String sql = "select * "
    	 		+ "from adks_grade_user "
    	 		+ "where gradeId="+gradeId+" and userId="+userId;
         List<Adks_grade_user> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_grade_user>() {
             @Override
             public Adks_grade_user mapRow(ResultSet rs, int rowNum) throws SQLException {
            	 Adks_grade_user gradeUser = new Adks_grade_user();
            	 gradeUser.setGradeUserId(rs.getInt("gradeUserId"));
            	 gradeUser.setIsGraduate(rs.getInt("isGraduate"));
            	 gradeUser.setRequiredPeriod(rs.getDouble("requiredPeriod"));
            	 gradeUser.setOptionalPeriod(rs.getDouble("optionalPeriod"));
            	 gradeUser.setIsCertificate(rs.getInt("isCertificate"));
            	 gradeUser.setGradeId(rs.getInt("gradeId"));
            	 gradeUser.setGradeName(rs.getString("gradeName"));
            	 gradeUser.setUserId(rs.getInt("userId"));
            	 gradeUser.setUserName(rs.getString("userName"));
            	 gradeUser.setOrgId(rs.getInt("orgId"));
            	 gradeUser.setOrgName(rs.getString("orgName"));
            	 gradeUser.setCreatorId(rs.getInt("creatorId"));
            	 gradeUser.setCreatorName(rs.getString("creatorName"));
            	 gradeUser.setCreateTime(rs.getDate("createTime"));
                 return gradeUser;
             }
         });
         if (reslist == null || reslist.size() <= 0) {
             return null;
         }
         return reslist.get(0);
    }
    
    /**
     * 
     * @Title getGradeCourse
     * @Description：根据gradeId和courseId获取班级课程信息
     * @author xrl
     * @Date 2017年5月12日
     * @param gradeId
     * @param courseId
     * @return
     */
    public Adks_grade_course getGradeCourse(Integer gradeId,Integer courseId){
    	String sql = "select * "
    	 		+ "from adks_grade_course "
    	 		+ "where gradeId="+gradeId+" and courseId="+courseId;
         List<Adks_grade_course> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_grade_course>() {
             @Override
             public Adks_grade_course mapRow(ResultSet rs, int rowNum) throws SQLException {
            	 Adks_grade_course gradeCourse = new Adks_grade_course();
            	 gradeCourse.setGradeCourseId(rs.getInt("gradeCourseId"));
            	 gradeCourse.setRankNum(rs.getInt("rankNum"));
            	 gradeCourse.setGcState(rs.getInt("gcState"));
            	 gradeCourse.setCredit(rs.getDouble("credit"));
            	 gradeCourse.setGradeId(rs.getInt("gradeId"));
            	 gradeCourse.setGradeName(rs.getString("gradeName"));
            	 gradeCourse.setCourseId(rs.getInt("courseId"));
            	 gradeCourse.setCourseCode(rs.getString("courseCode"));
            	 gradeCourse.setCourseName(rs.getString("courseName"));
            	 gradeCourse.setCourseCatalogId(rs.getInt("courseCatalogId"));
            	 gradeCourse.setCourseCatalogName(rs.getString("courseCatalogName"));
            	 gradeCourse.setCourseCatalogCode(rs.getString("courseCatalogCode"));
            	 gradeCourse.setCwType(rs.getInt("cwType"));
            	 gradeCourse.setCreatorId(rs.getInt("creatorId"));
            	 gradeCourse.setCreatorName(rs.getString("creatorName"));
            	 gradeCourse.setCreateTime(rs.getDate("createTime"));
                 return gradeCourse;
             }
         });
         if (reslist == null || reslist.size() <= 0) {
             return null;
         }
         return reslist.get(0);
    }
    
    /**
     * 
     * @Title getGradeList
     * @Description:根据userId获取班级ID
     * @author xrl
     * @Date 2017年5月13日
     * @param userId
     * @return
     */
    public List<String> getGradeList(String userId) {
        String sql = "select g.gradeId from adks_grade_user gu,adks_grade g "
        		+ "where g.gradeState=1 and now() > g.startDate and now()< g.endDate and g.gradeId=gu.gradeId and gu.userId="+userId;
        List<String> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int arg1) throws SQLException {
				String gradeId=rs.getString("gradeId");
				return gradeId;
			}});
        if (reslist == null || reslist.size() <= 0) {
            return null;
        }
        return reslist;
    }
    
    /**
     * 
     * @Title updateGradeUser
     * @Description：根据gradeUserId更新班级用户学时
     * @author xrl
     * @Date 2017年5月13日
     * @param map
     */
    public void updateGradeUser(Map<String, Object> map) {
        
    	if(map.get("requiredPeriod")!=null){
        	String sql="update adks_grade_user set requiredPeriod="+map.get("requiredPeriod")+" where gradeUserId="+map.get("gradeUserId");
        	mysqlClient.update(sql, new Object[] {});
        }else if(map.get("optionalPeriod")!=null){
        	String sql="update adks_grade_user set optionalPeriod="+map.get("optionalPeriod")+" where gradeUserId="+map.get("gradeUserId");
        	mysqlClient.update(sql, new Object[] {});
        }
    }
    
    /**
     * 
     * @Title getCourseUserByCourseIdAndUserId
     * @Description
     * @author xrl
     * @Date 2017年6月3日
     * @param map
     * @return
     */
    public Adks_course_user getCourseUserByCourseIdAndUserId(Map<String, Object> map){
   	 String sql = "select grades,isOver,studyAllTimeLong "
   	 		+ "from adks_course_user where courseId="+map.get("courseId")+" and userId="+map.get("userId")+" limit 1 ";
        List<Adks_course_user> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_course_user>() {
            @Override
            public Adks_course_user mapRow(ResultSet rs, int rowNum) throws SQLException {
                Adks_course_user courseUser = new Adks_course_user();
                courseUser.setIsOver(rs.getInt("isOver"));
                courseUser.setStudyAllTimeLong(rs.getInt("studyAllTimeLong"));
                courseUser.setGrades(rs.getString("grades"));
                return courseUser;
            }
        });
        if (reslist == null || reslist.size() <= 0) {
            return null;
        }
        return reslist.get(0);
   }
   
   public void updateCourseUserForGrades(Map<String, Object> map){
	   String sql="update adks_course_user set grades='"+map.get("grades")+"' "
      			+ "where courseId="+map.get("courseId")+" and userId="+map.get("userId");
      	mysqlClient.update(sql, new Object[] {});
   }
}
