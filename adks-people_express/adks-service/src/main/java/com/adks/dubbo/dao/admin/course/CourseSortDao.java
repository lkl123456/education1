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
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.commons.Page;

@Repository
public class CourseSortDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_course_sort";
	}
	//课程分类的树
	public List<Adks_course_sort> getCourseSortsListAll(Map map){
		String sql = "select cs1.courseSortId, cs1.courseSortName,cs1.courseSortCode, cs1.courseParentId,cs1.courseParentName,count(cs2.courseSortId) as childId "
				+ "from adks_course_sort cs1 LEFT JOIN adks_course_sort cs2 on cs1.courseSortId=cs2.courseParentId where 1=1 ";
		if(map.get("courseSortName")!=null && !"".equals(map.get("courseSortName"))){
			sql+=" and cs1.courseSortName like '%"+map.get("courseSortName")+"%'";
		}
		if(map.get("parentId")!=null && !"".equals(map.get("parentId"))){
			sql+=" and cs1.courseParentId ="+map.get("parentId");
		}
		sql+=" group by cs1.courseSortId";
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
				courseSort.setCourseParentName(rs.getString("courseParentName"));
				Integer childId=rs.getInt("childId");
				if(childId>0){
					courseSort.setIsParent(true);
				}else{
					courseSort.setIsParent(false);
				}
				return courseSort;
			}});
		if(reslist == null || reslist.size()<=0){
			return null;
		}
		return reslist;
	}
	
	public Adks_course_sort getCourseSortByName(String courseSortName){
		String sql = "select courseSortId, courseSortName,courseSortCode, courseParentId,courseParentName,courseNum ,orgId,orgCode,orgName,creatorId,creatorName,createtime from adks_course_sort where courseSortName='"+courseSortName+"'";
		List<Adks_course_sort> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_course_sort>() {
			@Override
			public Adks_course_sort mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_course_sort courseSort=new Adks_course_sort();
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
			}});
		if(reslist == null || reslist.size()<=0){
			return null;
		}
		return reslist.get(0);
	}

	public Adks_course_sort getCourseSortById(Integer courseSortId) {
		String sql = "select courseSortId, courseSortName,courseSortCode, courseParentId,courseParentName,courseNum ,orgId,orgCode,orgName,creatorId,creatorName,createtime from adks_course_sort where courseSortId="+courseSortId;
		List<Adks_course_sort> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_course_sort>() {
			@Override
			public Adks_course_sort mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_course_sort courseSort=new Adks_course_sort();
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
			}});
		if(reslist == null || reslist.size()<=0){
			return null;
		}
		return reslist.get(0);
	}
	
	public List<Adks_course_sort> getCourseSortByParent(Integer parentId) {
		String sql = "select courseSortId, courseSortName,courseSortCode, courseParentId,courseParentName,courseNum ,orgId,orgCode,orgName,creatorId,creatorName,createtime from adks_course_sort where parentId="+parentId;
		List<Adks_course_sort> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_course_sort>() {
			@Override
			public Adks_course_sort mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_course_sort courseSort=new Adks_course_sort();
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
			}});
		return reslist;
	}

	public Page<List<Map<String, Object>>> getCourseSortListPage(Page<List<Map<String, Object>>> page) {
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); //limit 起始位置
		StringBuffer sqlbuffer = new StringBuffer(" select courseSortId, courseSortName,courseSortCode, courseParentId,courseParentName, courseNum "
				+ ",orgId,orgName,orgCode,creatorId,creatorName,createtime,courseSortImgpath from adks_course_sort where 1=1 and courseSortId > 0 ");
		StringBuffer countsql = new StringBuffer("select count(courseSortId) from adks_course_sort where 1=1 and courseSortId > 0 ");
		Map map = page.getMap();
		if(map != null && map.size() > 0){
			//添加查询条件 。。
			if(map.get("courseSortName") != null && !"".equals(map.get("courseSortName"))){
				sqlbuffer.append(" and courseSortName like '%" + map.get("courseSortName") + "%'");
				countsql.append(" and courseSortName like '%" + map.get("courseSortName") + "%'");
			}
			if(map.get("courseParentId") != null){
				sqlbuffer.append(" and courseParentId=" + map.get("courseParentId"));
				countsql.append(" and courseParentId=" + map.get("courseParentId"));
			}
			if(map.get("courseSortCode") != null && !"".equals(map.get("courseSortCode"))){
				sqlbuffer.append(" and courseSortCode like '%" + map.get("courseSortCode") + "%'");
				countsql.append(" and courseSortCode like '%" + map.get("courseSortCode") + "%'");
			}
			if(map.get("courseSortId") != null && !"".equals(map.get("courseSortId"))){
				sqlbuffer.append(" and courseSortId <> " + map.get("courseSortId"));
				countsql.append(" and courseSortId <> " + map.get("courseSortId"));
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

	public void deleteCourseSort(String courseSortCode) {
		String sql = "delete from adks_course_sort where courseSortCode like '"+courseSortCode+"%'";
		mysqlClient.update(sql, new Object[]{});
	}
	
	public void updateNameUnifed(Integer courseSortId,String courseSortName){
		//同步名称到课程分类的子级
		String sql = "update adks_course_sort set courseParentName='"+courseSortName+"' where courseParentId="+courseSortId;
		mysqlClient.update(sql, new Object[]{});
		//同步名称到课程
		String sql1 = "update adks_course set courseSortName='"+courseSortName+"' where courseSortId="+courseSortId;
		mysqlClient.update(sql1, new Object[]{});
	}
	//得到父id为0的课程分类
  	public List<Adks_course_sort> getCourseSortParentIsZero(){
  		String sql = "select courseSortId, courseSortName,courseSortCode, courseParentId,courseParentName from adks_course_sort where courseParentId=0";
		List<Adks_course_sort> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_course_sort>() {
			@Override
			public Adks_course_sort mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_course_sort courseSort=new Adks_course_sort();
				courseSort.setId(rs.getInt("courseSortId"));
				courseSort.setCourseSortId(rs.getInt("courseSortId"));
				courseSort.setCourseSortName(rs.getString("courseSortName"));
				courseSort.setName(rs.getString("courseSortName"));
				courseSort.setText(rs.getString("courseSortName"));
				courseSort.setCourseSortCode(rs.getString("courseSortCode"));
				courseSort.setCourseParentId(rs.getInt("courseParentId"));
				courseSort.setCourseParentName(rs.getString("courseParentName"));
				return courseSort;
			}});
		return reslist;
  	}
	
}
