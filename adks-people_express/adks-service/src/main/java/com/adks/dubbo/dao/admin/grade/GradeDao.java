package com.adks.dubbo.dao.admin.grade;

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
import com.adks.dubbo.api.data.grade.Adks_grade_user;
import com.adks.dubbo.api.data.news.Adks_news;
import com.adks.dubbo.commons.Page;

@Component
public class GradeDao extends BaseDao {

	
	@Override
	protected String getTableName() {
		return "adks_grade";
	}

	/**
	 * 
	 * @Title getGradeListPage
	 * @Description:班级列表分页
	 * @author xrl
	 * @Date 2017年3月22日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradeListPage(Page<List<Map<String, Object>>> page){
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); //limit 起始位置
		StringBuffer sqlbuffer = new StringBuffer(" select * from Adks_grade where 1=1 and orgId > 0 ");
		StringBuffer countsql = new StringBuffer("select count(gradeId) from Adks_grade where 1=1 and orgId > 0 ");
		
		Map map = page.getMap();
		if(map != null && map.size() > 0){
			//添加查询条件 
			//班级名模糊查询
			if(map.get("gradeName") != null){
				sqlbuffer.append(" and gradeName like '%" + map.get("gradeName") + "%'");
				countsql.append(" and gradeName like '%" + map.get("gradeName") + "%'");
			}
			//根据机构管理员权限
			if(map.get("orgCode") != null){
				sqlbuffer.append(" and orgCode like '%" + map.get("orgCode") + "%'");
				countsql.append(" and orgCode like '%" + map.get("orgCode") + "%'");
			}
			
			if(map.get("userId")!=null){
				sqlbuffer.append(" and headTeacherId=" + map.get("userId"));
				countsql.append(" and headTeacherId=" + map.get("userId"));
			}
		}
		
		//分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();
		List<Map<String, Object>> userlist = mysqlClient.queryForList(sql, new Object[0]);
		for (Map<String, Object> map2 : userlist) {
			//班级开始时间
			if(map2.get("startDate")!=null){
				map2.put("startDate_str", DateUtils.getDate2LStr(DateUtils.getStr2LDate(map2.get("startDate").toString())));
			}
			//班级结束时间
			if(map2.get("endDate")!=null){
				map2.put("endDate_str", DateUtils.getDate2LStr(DateUtils.getStr2LDate(map2.get("endDate").toString())));
			}
			//班级创建时间
			if(map2.get("createTime")!=null){
				map2.put("createTime_str", DateUtils.getDate2LStr(DateUtils.getStr2LDate(map2.get("createTime").toString())));
			}
//			if((Integer)map2.get("gradeState")==1){
//				map2.put("gradeState",19);
//			}else{
//				map2.put("gradeState",20);
//			}
		}
		//查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(userlist);
		return page;
	}

	/**
	 * 
	 * @Title getGradeById
	 * @Description：根据gradeId获取班级信息
	 * @author xrl
	 * @Date 2017年3月25日
	 * @param gradeId
	 * @return
	 */
	public Map<String, Object> getGradeById(Integer gradeId){
		String sql = " select * from adks_grade g where g.gradeId="+gradeId;
		return mysqlClient.queryForMap(sql, new Object[]{});
	}
	//暂时没有用到
	public List<Adks_grade> gradeTopCurrentList(Map map){
		String sql = "select SQL_NO_CACHE g.gradeId as gradeId,g.gradeName as gradeName,g.gradeState,g.startDate,g.endDate,g.userNum,u.orgName as orgName,"
				+ "g.gradeDesc,g.gradeImg from adks_grade as g,adks_user as u where  g.creatorId = u.userId and g.gradeState=1 and now()< g.endDate "
				+ "and now() > g.startDate order by g.gradeId desc ";
		if(map.get("orgId")!=null && !"".equals(map.get("orgId"))){
			sql+=" and orgId ="+map.get("orgId");
		}
		List<Adks_grade> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_grade>() {
			@Override
			public Adks_grade mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_grade grade=new Adks_grade();
				
				return grade;
			}});
		if(reslist == null || reslist.size()<=0){
			return null;
		}
		return reslist;
	}
	
	public Page<List<Adks_grade>> getGradeListWeb(Page<List<Adks_grade>> page) {
		Integer offset = null; //limit 起始位置
    	if(page.getCurrentPage()<=1){
    		offset=0;
    		page.setCurrentPage(1);
    	}else{
    		offset=(page.getCurrentPage() - 1) * page.getPageSize();
    	}
		StringBuffer sqlbuffer = new StringBuffer("select gradeId,gradeName ,gradeDesc ,gradeTarget,gradeState,startDate,endDate,gradeImg,"
				+ "requiredPeriod,optionalPeriod,workRequire,examRequire,certificateImg,eleSeal,isRegisit,headTeacherId,headTeacherName,userNum,"
				+ "requiredNum,optionalNum,orgId,orgName,orgCode,creatorName,createTime"
				+ " from adks_grade where 1=1 and gradeState=1 and isRegisit=1 ");
		StringBuffer countsql = new StringBuffer("select count(1) from adks_grade where 1=1 and gradeState=1 and isRegisit=1 ");
		Map map = page.getMap();
		if(map != null && map.size() > 0){
			//添加查询条件 。。
			if(map.get("gradeId") != null){
				sqlbuffer.append(" and gradeId="+map.get("gradeId"));
				countsql.append(" and gradeId="+map.get("gradeId"));
			}
			if(map.get("gradeName") != null){
				sqlbuffer.append(" and gradeName like '%"+map.get("gradeName")+"%'");
				countsql.append(" and gradeName like '%"+map.get("gradeName")+"%'");
			}
			if(map.get("orgCode") != null){
				sqlbuffer.append(" and orgCode like '%"+map.get("orgCode")+"%'");
				countsql.append(" and orgCode like '%"+map.get("orgCode")+"%'");
			}
		}
		sqlbuffer.append(" order by gradeId desc ");
		//分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();
		
		List<Adks_grade> gradeList = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_grade>() {
			@Override
			public Adks_grade mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_grade grade=new Adks_grade();
				grade.setGradeId(rs.getInt("gradeId"));
				grade.setGradeName(rs.getString("gradeName"));
				grade.setGradeDesc(rs.getString("gradeDesc"));
				grade.setGradeTarget(rs.getString("gradeTarget"));
				grade.setGradeState(rs.getInt("gradeState"));
				grade.setStartDate(rs.getDate("startDate"));
				grade.setEndDate(rs.getDate("endDate"));
				grade.setGradeImg(rs.getString("gradeImg"));
				grade.setOptionalPeriod(rs.getInt("optionalPeriod"));
				grade.setRequiredPeriod(rs.getInt("requiredPeriod"));
				grade.setWorkRequire(rs.getInt("workRequire"));
				grade.setExamRequire(rs.getInt("examRequire"));
				grade.setCertificateImg(rs.getString("certificateImg"));
				grade.setEleSeal(rs.getString("eleSeal"));
				grade.setIsRegisit(rs.getInt("isRegisit"));
				grade.setHeadTeacherId(rs.getInt("headTeacherId"));
				grade.setHeadTeacherName(rs.getString("headTeacherName"));
				grade.setUserNum(rs.getInt("userNum"));
				grade.setRequiredNum(rs.getInt("requiredNum"));
				grade.setOptionalNum(rs.getInt("optionalNum"));
				grade.setOrgId(rs.getInt("orgId"));
				grade.setOrgName(rs.getString("orgName"));
				grade.setOrgCode(rs.getString("orgCode"));
				grade.setCreateTime(rs.getDate("createTime"));
				grade.setCreatorName(rs.getString("creatorName"));
				return grade;
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
	
	/**
	 * 
	 * @Title delGradeByGradeIdId
	 * @Description：删除班级
	 * @author xrl
	 * @Date 2017年4月27日
	 * @param gradeId
	 */
	public void delGradeByGradeIdId(Integer gradeId){
		String sql = "delete from adks_grade where gradeId=" + gradeId;
        mysqlClient.update(sql, new Object[] {});
	}
	
	/**
	 * 
	 * @Title getGradesJson
	 * @Description
	 * @author xrl
	 * @Date 2017年4月28日
	 * @param map
	 * @return
	 */
	public List<Adks_grade> getGradesJson(Map<String, Object> map){
		String sql = "select * from adks_grade where 1=1";
		if(map != null && map.size() > 0){
			//添加查询条件 
			//orgCode查询
			if(map.get("orgCode") != null){
				sql+=" and orgCode like '%"+map.get("orgCode")+"%'";
			}
			if(map.get("userId")!=null){
				sql+="and headTeacherId="+map.get("userId");
			}
		}
		List<Adks_grade> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_grade>() {
			@Override
			public Adks_grade mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_grade grade=new Adks_grade();
				grade.setId(rs.getInt("gradeId"));
				grade.setName(rs.getString("gradeName"));
				grade.setText(rs.getString("gradeName"));
				return grade;
			}});
		return reslist;
	}
	
	/**
	 * 
	 * @Title updateGradeUserNum
	 * @Description：更新班级人数
	 * @author xrl
	 * @Date 2017年5月5日
	 * @param map
	 */
	public void updateGradeUserNum(Map<String, Object> map){
		String sql = "update adks_grade set userNum="+map.get("userNum")+" where gradeId=" + map.get("gradeId");
        mysqlClient.update(sql, new Object[] {});
	}
	
	/**
	 * 
	 * @Title checkGradeName
	 * @Description:检查班级名称在该机构下是否重复
	 * @author xrl
	 * @Date 2017年5月8日
	 * @param gradeId
	 * @return
	 */
	public Map<String, Object> checkGradeName(Map<String, Object> map){
		Object[] obj = new Object[]{};
		String sql = "select * from adks_grade where gradeName='"+map.get("gradeName")+"'";
		if(map.get("orgId")!=null){
			sql+=" and orgId="+map.get("orgId");
		}
		if(map.get("orgCode")!=null){
			sql+=" and orgCode='"+map.get("orgCode")+"'";
		}
		if(map.get("gradeId")!=null){
			sql+=" and gradeId!="+map.get("gradeId");
		}
		return mysqlClient.queryForMap(sql, obj);
	}
	
	/**
	 * 
	 * @Title updateGradeCourseNum
	 * @Description
	 * @author xrl
	 * @Date 2017年5月22日
	 * @param map
	 */
	public void updateGradeCourseNum(Map<String, Object> map){
		String sql = "update adks_grade set requiredNum="+map.get("requiredNum")+",optionalNum="+map.get("optionalNum")
		+" where gradeId=" + map.get("gradeId");
        mysqlClient.update(sql, new Object[] {});
	}
	
}
