package com.adks.dubbo.dao.web.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.grade.Adks_grade;
import com.adks.dubbo.api.data.user.Adks_usercollection;
import com.adks.dubbo.commons.Page;

@Component
public class UsercollectionWebDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_usercollection";
	}
	
	public Page<List<Adks_usercollection>> myCollectionList(Page<List<Adks_usercollection>> page) {
		Integer offset = null; //limit 起始位置
    	if(page.getCurrentPage()<=1){
    		offset=0;
    		page.setCurrentPage(1);
    	}else{
    		offset=(page.getCurrentPage() - 1) * page.getPageSize();
    	}
		StringBuffer sqlbuffer = new StringBuffer("select uc.userConId,uc.courseId,uc.courseCode,uc.courseName,uc.userId,uc.authorId,uc.authorName,uc.createDate,"
				+ "uc.publishDate,uc.courseType,uc.courseDuration,uc.courseSortName,uc.courseImg,t2.lastposition as studyTimeLong "
				+ " from adks_usercollection uc left join adks_course_user t2 on ((uc.courseId=t2.courseId and uc.userId=t2.userId) or t2.userid is null) "
				+ "where 1=1 ");
		StringBuffer countsql = new StringBuffer("select count(1) from adks_usercollection uc left join adks_course_user t2 on ((uc.courseId=t2.courseId and uc.userId=t2.userId) or t2.userid is null) "
				+ "where 1=1");
		Map map = page.getMap();
		if(map != null && map.size() > 0){
			//添加查询条件 。。
			if(map.get("userId") != null){
				sqlbuffer.append(" and uc.userId="+map.get("userId"));
				countsql.append(" and uc.userId="+map.get("userId"));
			}
		}
		sqlbuffer.append(" order by uc.userConId desc ");
		//分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();
		
		List<Adks_usercollection> gradeList = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_usercollection>() {
			@Override
			public Adks_usercollection mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_usercollection uc=new Adks_usercollection();
				uc.setUserConId(rs.getInt("userConId"));
				uc.setCourseId(rs.getInt("courseId"));
				uc.setCourseName(rs.getString("courseName"));
				uc.setCourseCode(rs.getString("courseCode"));
				uc.setUserId(rs.getInt("userId"));
				uc.setAuthorId(rs.getInt("authorId"));
				uc.setAuthorName(rs.getString("authorName"));
				uc.setCreateDate(rs.getDate("createDate"));
				uc.setPublishDate(rs.getDate("publishDate"));
				uc.setCourseType(rs.getInt("courseType"));
				uc.setCourseDuration(rs.getInt("courseDuration"));
				uc.setCourseSortName(rs.getString("courseSortName"));
				uc.setCourseImg(rs.getString("courseImg"));
				uc.setStudyTimeLong(rs.getInt("studyTimeLong"));
				return uc;
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
		
		page.setRows(gradeList);
		return page;
	}

}
