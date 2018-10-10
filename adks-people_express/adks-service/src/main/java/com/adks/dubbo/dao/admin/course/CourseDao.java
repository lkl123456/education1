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
import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.api.data.course.Adks_course_sort;
import com.adks.dubbo.commons.Page;

@Repository
public class CourseDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_course";
	}
	public void checkCourseNametoTabs(Integer courseId,String courseName){
		String sql = "update Adks_course_user set courseName = '"+courseName+"' where courseId ="+courseId;
		mysqlClient.update(sql, new Object[]{});
		String sql1 = "update Adks_grade_course set courseName = '"+courseName+"' where courseId ="+courseId;
		mysqlClient.update(sql1, new Object[]{});
    }
	public List<Adks_course> getCourseListByOrgCode(String orgCode) {
		String sql = "select courseId, courseName,courseCode, courseType,coursePic ,courseDes,authorId,authorName,courseSortId,courseSortName,courseSortCode,courseDuration,"
				+ "courseTimeLong,courseStatus,courseCollectNum,courseClickNum,orgId,orgName,isAudit,courseBelong,isRecommend"
				+ ",courseStream,creatorId,creatorName,createtime,courseStudiedLong"
				+ " from adks_course where orgCode like '"+orgCode+"%'";
		List<Adks_course> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_course>() {
			@Override
			public Adks_course mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_course courseSort=new Adks_course();
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
			}});
		if(reslist == null || reslist.size()<=0){
			return null;
		}
		return reslist;
	}
	
	public boolean courseBycourseSort(Integer courseSortId){
		String sql="select courseSortCode from adks_course_sort where courseSortId="+courseSortId;
		List<Map<String, Object>> reslist = mysqlClient.queryForList(sql, new Object[0]);
		if(reslist == null || reslist.size() <= 0){
			return false;
		}
		String courseSortCode=reslist.get(0).get("courseSortCode")+"";
		String sql1 = "select courseId from adks_course where courseSortCode like '"+courseSortCode+"%'"; 
		List<Map<String, Object>> reslist1 = mysqlClient.queryForList(sql1, new Object[0]);
		if(reslist1 == null || reslist1.size() <= 0){
			return false;
		}
		return true;
	}
	
	public void deleteCourse(String courseIds){
		String sql = "delete from Adks_course where courseId in ("+courseIds+")";
		mysqlClient.update(sql, new Object[]{});
	}
	//更新课程分类的课程量
	public void updateCourseSortCourseNum(Integer courseSortId,Integer flag){
		if(flag ==1){
			String sql = "update Adks_course_sort set courseNum=courseNum+1 where courseSortId="+courseSortId;
			mysqlClient.update(sql, new Object[]{});
		}else if(flag==2){
			String sql = "update Adks_course_sort set courseNum=courseNum-1 where courseSortId="+courseSortId +" and courseNum is not null and courseNum<>0";
			mysqlClient.update(sql, new Object[]{});
		}
	}
	public Page<List<Map<String, Object>>> getCourseListPage(Page<List<Map<String, Object>>> page){
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); //limit 起始位置
		StringBuffer sqlbuffer = new StringBuffer(" select courseId, courseName,courseCode, courseType,coursePic ,courseDes,authorId,authorName,courseSortId,courseSortName,courseSortCode,courseDuration,"
				+ "courseTimeLong,courseStatus,courseCollectNum,courseClickNum,orgId,orgName,isAudit,courseBelong,isRecommend"
				+ ",courseStream,creatorId,creatorName,createtime,courseStudiedLong"
				+ " from adks_course where 1=1 and courseId > 0 ");
		StringBuffer countsql = new StringBuffer("select count(courseId) from Adks_course where 1=1 and courseId > 0 ");
		Map map = page.getMap();
		if(map != null && map.size() > 0){
			//添加查询条件 。。
			if(map.get("courseName") != null && !"".equals(map.get("courseName"))){
				sqlbuffer.append(" and courseName like '%" + map.get("courseName") + "%'");
				countsql.append(" and courseName like '%" + map.get("courseName") + "%'");
			}
			if(map.get("orgCode") != null && !"".equals(map.get("orgCode"))){
				sqlbuffer.append(" and orgCode like '%" + map.get("orgCode") + "%'");
				countsql.append(" and orgCode like '%" + map.get("orgCode") + "%'");
			}
			if(map.get("courseSortCode") != null && !"".equals(map.get("courseSortCode"))){
				sqlbuffer.append(" and courseSortCode like '%" + map.get("courseSortCode") + "%'");
				countsql.append(" and courseSortCode like '%" + map.get("courseSortCode") + "%'");
			}
		}
		
		//分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();
		List<Map<String, Object>> userlist = mysqlClient.queryForList(sql, new Object[0]);
		for (Map<String, Object> map2 : userlist) {
			if(map2.get("createtime") != null){
				map2.put("createtime", DateUtils.getDate2SStr(DateUtils.getStr2LDate(map2.get("createtime").toString())));
			}
		}
		//查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(userlist);
		return page;
	}
	public void courseUpdateData(Integer courseId, Integer flag, String result) {
		//1审核状态 1.审核通过；2.审核未通过；3.待审核
		if(flag==1){
			String sql = "update adks_course set isAudit="+result+" where courseId="+courseId;
			mysqlClient.update(sql, new Object[]{});
		}else if(flag==2){//是否推荐 1.推荐；2不推荐；
			String sql = "update adks_course set isRecommend="+result+" where courseId="+courseId;
			mysqlClient.update(sql, new Object[]{});
		}else if(flag==3){//课程状态，1激活2冻结
			String sql = "update adks_course set courseStatus="+result+" where courseId="+courseId;
			mysqlClient.update(sql, new Object[]{});
		}
	}
	public Adks_course getCourseById(Integer courseId){
		String sql = "select courseId, courseName,courseCode, courseType,coursePic ,courseDes,authorId,authorName,courseSortId,courseSortName,courseSortCode,courseDuration,"
				+ "courseTimeLong,courseStatus,courseCollectNum,courseClickNum,orgId,orgName,orgCode,isAudit,courseBelong,isRecommend"
				+ ",courseStream,creatorId,creatorName,createtime,courseStudiedLong"
				+ " from adks_course where courseId ="+courseId;
		List<Adks_course> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_course>() {
			@Override
			public Adks_course mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_course courseSort=new Adks_course();
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
				courseSort.setOrgCode(rs.getString("orgCode"));
				courseSort.setOrgName(rs.getString("orgName"));
				courseSort.setCourseStream(rs.getString("courseStream"));
				courseSort.setCreatorId(rs.getInt("creatorId"));
				courseSort.setCreatorName(rs.getString("creatorName"));
				courseSort.setCreateTime(rs.getDate("createtime"));
				return courseSort;
			}});
		if(reslist == null || reslist.size()<=0){
			return null;
		}
		return reslist.get(0);
	}
	public Adks_course getCourseByName(String courseName){
		String sql = "select courseId, courseName,courseCode, courseType,coursePic ,courseDes,authorId,authorName,courseSortId,courseSortName,courseSortCode,courseDuration,"
				+ "courseTimeLong,courseStatus,courseCollectNum,courseClickNum,orgId,orgName,isAudit,courseBelong,isRecommend"
				+ ",courseStream,creatorId,creatorName,createtime,courseStudiedLong"
				+ " from adks_course where courseName ='"+courseName+"'";
		List<Adks_course> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_course>() {
			@Override
			public Adks_course mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_course courseSort=new Adks_course();
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
			}});
		if(reslist == null || reslist.size()<=0){
			return null;
		}
		return reslist.get(0);
	}
	//得到首页的课程分类
	public List<Adks_course> getTopNewCourseList(Map map){
		String sql = "select courseId, courseName,courseCode, courseType,coursePic ,courseDes,authorId,authorName,courseSortId,courseSortName,courseSortCode,courseDuration,"
				+ "courseTimeLong,courseStatus,courseCollectNum,courseClickNum,orgId,orgName,isAudit,courseBelong,isRecommend"
				+ ",courseStream,creatorId,creatorName,createtime,courseStudiedLong"
				+ " from adks_course where 1=1 ";
		sql+=" and isAudit=1 and courseBelong=1 and courseStatus=1 ";
		if(map.get("orgId")!=null && !"".equals(map.get("orgId"))){
			sql+=" and orgId="+map.get("orgId");
		}
		if(map.get("isRecommend")!=null && !"".equals(map.get("isRecommend"))){
			sql+=" and isRecommend="+map.get("isRecommend");
		}
		if(map.get("num")!=null && !"".equals(map.get("num"))){
			sql+=" order by createtime desc limit 0,"+map.get("num");
		}
		
		List<Adks_course> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_course>() {
			@Override
			public Adks_course mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_course courseSort=new Adks_course();
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
			}});
		if(reslist == null || reslist.size()<=0){
			return null;
		}
		return reslist;
	}
	public List<Adks_course> getTopHotCourseList(Map map){
		String sql = "select courseId, courseName,courseCode, courseType,coursePic ,courseDes,authorId,authorName,courseSortId,courseSortName,courseSortCode,courseDuration,"
				+ "courseTimeLong,courseStatus,courseCollectNum,courseClickNum,orgId,orgName,isAudit,courseBelong,isRecommend"
				+ ",courseStream,creatorId,creatorName,createtime,courseStudiedLong"
				+ " from adks_course where 1=1 ";
		sql+=" and isAudit=1 and courseBelong=1 and courseStatus=1 ";
		if(map.get("orgId")!=null && !"".equals(map.get("orgId"))){
			sql+=" and orgId="+map.get("orgId");
		}
		if(map.get("isRecommend")!=null && !"".equals(map.get("isRecommend"))){
			sql+=" and isRecommend="+map.get("isRecommend");
		}
		if(map.get("num")!=null && !"".equals(map.get("num"))){
			sql+=" order by courseClickNum desc limit 0,"+map.get("num");
		}
		
		List<Adks_course> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_course>() {
			@Override
			public Adks_course mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_course courseSort=new Adks_course();
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
			}});
		if(reslist == null || reslist.size()<=0){
			return null;
		}
		return reslist;
	}
	public boolean canDelCourse(Integer courseId) {
		String sql="select courseId from adks_grade_course where courseId="+courseId;
		List<Map<String,Object>> list=mysqlClient.queryForList(sql, null);
		if(list!=null && list.size()>0){
			return false;
		}
		String sql1="select courseId from adks_course_user where courseId="+courseId;
		List<Map<String,Object>> list1=mysqlClient.queryForList(sql1, null);
		if(list1!=null && list1.size()>0){
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @Title getCourseStatisticsListPage
	 * @Description：获取课程统计分页列表
	 * @author xrl
	 * @Date 2017年6月19日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getCourseStatisticsListPage(Page<List<Map<String, Object>>> page){
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); //limit 起始位置
		StringBuffer sqlbuffer = new StringBuffer(" select courseId,courseName,courseType,courseSortId,courseSortName,"
				+ "courseCollectNum,courseClickNum,orgId,orgName,"
				+ "createtime,courseStudiedLong "
				+ " from adks_course where 1=1 and courseId > 0 ");
		StringBuffer countsql = new StringBuffer("select count(courseId) from Adks_course where 1=1 and courseId > 0 ");
		Map map = page.getMap();
		if(map != null && map.size() > 0){
			//添加查询条件 。。
			if(map.get("courseName") != null && !"".equals(map.get("courseName"))){
				sqlbuffer.append(" and courseName like '%" + map.get("courseName") + "%'");
				countsql.append(" and courseName like '%" + map.get("courseName") + "%'");
			}
			if(map.get("orgCode") != null && !"".equals(map.get("orgCode"))){
				sqlbuffer.append(" and orgCode like '" + map.get("orgCode") + "%'");
				countsql.append(" and orgCode like '" + map.get("orgCode") + "%'");
			}
			if(map.get("courseSortCode") != null && !"".equals(map.get("courseSortCode"))){
				sqlbuffer.append(" and courseSortCode like '" + map.get("courseSortCode") + "%'");
				countsql.append(" and courseSortCode like '" + map.get("courseSortCode") + "%'");
			}
		}
		
		//分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();
		List<Map<String, Object>> userlist = mysqlClient.queryForList(sql, new Object[0]);
		//查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(userlist);
		return page;
	}
	
	/**
	 * 
	 * @Title getCourseStatisticsList
	 * @Description
	 * @author xrl
	 * @Date 2017年6月19日
	 * @param map
	 * @return
	 */
	public List<Adks_course> getCourseStatisticsList(Map<String, Object> map){
		Object[] obj = new Object[]{map.get("orgCode"),map.get("courseSortCode")};
		String sql = "select * from adks_course where 1=1 ";
		if(map.get("orgCode")!=null && !"".equals(map.get("orgCode"))){
			sql+=" and orgCode like '%"+map.get("orgCode")+"%'";
		}
		if(map.get("courseSortCode")!=null && !"".equals(map.get("courseSortCode"))){
			sql+=" and courseSortCode like '%"+map.get("courseSortCode")+"%'";
		}
		List<Adks_course> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_course>() {
			@Override
			public Adks_course mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_course courseSort=new Adks_course();
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
			}});
		if(reslist == null || reslist.size()<=0){
			return null;
		}
		return reslist;
	}
}
