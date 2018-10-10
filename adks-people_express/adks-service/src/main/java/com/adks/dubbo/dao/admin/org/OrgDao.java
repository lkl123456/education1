package com.adks.dubbo.dao.admin.org;

import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.adks.commons.util.DateUtils;
import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dbclient.mysql.MysqlClient;
import com.adks.dubbo.api.data.menu.Adks_menu;
import com.adks.dubbo.api.data.menu.Adks_menulink;
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.data.org.Adks_org_config;
import com.adks.dubbo.commons.Page;

@Component
public class OrgDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_org";
	}
	//机构是同步机构表跟用户表的机构名称
	public void checkOrgNameToUser(Integer orgId,String orgName){
		String sql = "update Adks_user set orgName = '"+orgName+"' where orgId ="+orgId;
		mysqlClient.update(sql, new Object[]{});
		String sql1 = "update Adks_grade set orgName = '"+orgName+"' where orgId ="+orgId;
		mysqlClient.update(sql1, new Object[]{});
		String sql2 = "update Adks_course set orgName = '"+orgName+"' where orgId ="+orgId;
		mysqlClient.update(sql2, new Object[]{});
		String sql3 = "update Adks_course_sort set orgName = '"+orgName+"' where orgId ="+orgId;
		mysqlClient.update(sql3, new Object[]{});
		String sql4 = "update Adks_news set orgName = '"+orgName+"' where orgId ="+orgId;
		mysqlClient.update(sql4, new Object[]{});
		String sql5 = "update Adks_author set orgName = '"+orgName+"' where orgId ="+orgId;
		mysqlClient.update(sql5, new Object[]{});
		String sql6 = "update Adks_news_sort set orgName = '"+orgName+"' where orgId ="+orgId;
		mysqlClient.update(sql6, new Object[]{});
		String sql7 = "update Adks_org set parentName = '"+orgName+"' where parentId ="+orgId;
		mysqlClient.update(sql7, new Object[]{});
		String sql8 = "update Adks_org_config set orgName = '"+orgName+"' where orgId ="+orgId;
		mysqlClient.update(sql8, new Object[]{});
	}
	
	public boolean checkOrgHasUser(String orgCode){
		boolean falg=false;
		if(!falg){
			String sql = "select userId from Adks_user where orgCode like  '"+orgCode+"%' ";
			List<Map<String, Object>> reslist = mysqlClient.queryForList(sql, new Object[0]);
			if(reslist == null || reslist.size() <= 0){
				falg=true;
			}else {
				falg=false;
			}
		}
		if(falg){
			String sql1 = "select courseId from Adks_course where orgCode like  '"+orgCode+"%' ";
			List<Map<String, Object>> reslist1 = mysqlClient.queryForList(sql1, new Object[0]);
			if(reslist1 == null || reslist1.size() <= 0){
				falg=true;
			}else{
				falg=false;
			}
		}
		if(falg){
			String sql1 = "select courseSortId from Adks_course_sort where orgCode like  '"+orgCode+"%' ";
			List<Map<String, Object>> reslist1 = mysqlClient.queryForList(sql1, new Object[0]);
			if(reslist1 == null || reslist1.size() <= 0){
				falg=true;
			}else{
				falg=false;
			}
		}
		if(falg){
			String sql1 = "select gradeId from Adks_grade where orgCode like  '"+orgCode+"%' ";
			List<Map<String, Object>> reslist1 = mysqlClient.queryForList(sql1, new Object[0]);
			if(reslist1 == null || reslist1.size() <= 0){
				falg=true;
			}else{
				falg=false;
			}
		}
		if(falg){
			String sql1 = "select authorId from Adks_author where orgCode like  '"+orgCode+"%' ";
			List<Map<String, Object>> reslist1 = mysqlClient.queryForList(sql1, new Object[0]);
			if(reslist1 == null || reslist1.size() <= 0){
				falg=true;
			}else{
				falg=false;
			}
		}
		if(falg){
			String sql1 = "select newsSortId from Adks_news_sort where orgCode like  '"+orgCode+"%' ";
			List<Map<String, Object>> reslist1 = mysqlClient.queryForList(sql1, new Object[0]);
			if(reslist1 == null || reslist1.size() <= 0){
				falg=true;
			}else{
				falg=false;
			}
		}
		if(falg){
			String sql1 = "select newsId from Adks_news where orgCode like  '"+orgCode+"%' ";
			List<Map<String, Object>> reslist1 = mysqlClient.queryForList(sql1, new Object[0]);
			if(reslist1 == null || reslist1.size() <= 0){
				falg=true;
			}else{
				falg=false;
			}
		}
		return falg;
	}
	
	public List<Adks_org> getOrgsListByParent(Integer parentId){
		String sql = "select orgId, orgCode from Adks_org where parentId="+parentId;
		List<Adks_org> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_org>() {
			@Override
			public Adks_org mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_org org=new Adks_org();
				org.setOrgId(rs.getInt("orgId"));
				org.setOrgCode(rs.getString("orgCode"));
				return org;
			}});
		return reslist;
	}
	public List<Adks_org> getOrgsListAll(String orgCode){
		String sql = "select orgId as id,orgName as name,orgCode,parentId,parentName,creatorId,creatorName,createtime,usernum,orgstudylong from Adks_org";
		if(orgCode!=null && !"".equals(orgCode)){
			sql+=" where orgCode like '"+orgCode+"%' ";
		}
		List<Adks_org> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_org>() {
			@Override
			public Adks_org mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_org org=new Adks_org();
				org.setId(rs.getInt("id"));
				org.setName(rs.getString("name"));
				org.setOrgCode(rs.getString("orgCode"));
				org.setParentId(rs.getInt("parentId"));
				org.setParentName(rs.getString("parentName"));
				org.setCreatorId(rs.getInt("creatorId"));
				org.setCreatorName(rs.getString("creatorName"));
				org.setCreatetime(rs.getDate("createtime"));
				org.setUsernum(rs.getInt("usernum"));
				org.setOrgstudylong(rs.getInt("orgstudylong"));
				return org;
			}});
		return reslist;
	}
	//异步获取机构，上一个方法将舍弃
	 public List<Adks_org> getOrgsListAll2(Integer parentId) {
		 String sql = "select o.orgId, o.orgName as name,o.orgCode,o.parentId,o.parentName,count(p.orgId)as childId from Adks_org o LEFT JOIN adks_org p on o.orgId=p.parentId "
					+ "where o.parentId="+parentId+" GROUP BY o.orgId";
			List<Adks_org> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_org>() {
				@Override
				public Adks_org mapRow(ResultSet rs, int rowNum) throws SQLException {
					Adks_org org=new Adks_org();
					org.setId(rs.getInt("orgId"));
					org.setOrgId(rs.getInt("orgId"));
					org.setOrgCode(rs.getString("orgCode"));
					org.setName(rs.getString("name"));
					org.setOrgName(rs.getString("name"));
					org.setParentId(rs.getInt("parentId"));
					org.setParentName(rs.getString("parentName"));
					Integer childId=rs.getInt("childId");
					if(childId>0){
						org.setIsParent(true);
					}else{
						org.setIsParent(false);
					}
					return org;
				}});
			return reslist;
	}
	public List<Map<String,Object>> getOrgsList() {
		String sql = "select orgId,orgName as name,code,parentId,parentName,creator,creatorName,createtime from Adks_org where deleted=2";
		List<Map<String, Object>> reslist = mysqlClient.queryForList(sql, new Object[0]);
		if(reslist == null || reslist.size() <= 0){
			return null;
		}
		return reslist;
	}
	
	public List<Adks_org> getOrgsListByClass(Integer parentId){
		String sql = "select o.orgId, o.orgName as name,o.orgCode,o.parentId,o.parentName,count(p.orgId)as childId from Adks_org o LEFT JOIN adks_org p on o.orgId=p.parentId "
				+ "where o.parentId="+parentId+" GROUP BY o.orgId";
		/*String sql = "select orgId as id,orgName as name,orgCode,parentId,parentName,creatorId,creatorName,createtime,usernum,orgstudylong from Adks_org ";
		if(orgCode!=null && !"".equals(orgCode)){
			sql+=" where orgCode like '"+orgCode+"%'";
		}*/
		List<Adks_org> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_org>() {
			@Override
			public Adks_org mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_org org=new Adks_org();
				org.setId(rs.getInt("orgId"));
				org.setOrgId(rs.getInt("orgId"));
				org.setName(rs.getString("name"));
				org.setOrgName(rs.getString("name"));
				org.setOrgCode(rs.getString("orgCode"));
				org.setParentId(rs.getInt("parentId"));
				org.setParentName(rs.getString("parentName"));
				Integer childId=rs.getInt("childId");
				if(childId>0){
					org.setIsParent(true);
				}else{
					org.setIsParent(false);
				}
				return org;
			}});
		return reslist;
	}
	
	public Adks_org getOrgById(Integer orgId){
		String sql = "select orgId as id,orgName as name,orgCode,parentId,parentName,creatorId,creatorName,createtime,usernum,orgstudylong from Adks_org where orgId="+orgId;
		List<Adks_org> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_org>() {
			@Override
			public Adks_org mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_org org=new Adks_org();
				org.setOrgId(rs.getInt("id"));
				org.setId(rs.getInt("id"));
				org.setName(rs.getString("name"));
				org.setOrgName(rs.getString("name"));
				org.setOrgCode(rs.getString("orgCode"));
				org.setParentId(rs.getInt("parentId"));
				org.setParentName(rs.getString("parentName"));
				org.setCreatorId(rs.getInt("creatorId"));
				org.setCreatorName(rs.getString("creatorName"));
				org.setCreatetime(rs.getDate("createtime"));
				org.setUsernum(rs.getInt("usernum"));
				org.setOrgstudylong(rs.getInt("orgstudylong"));
				return org;
			}});
		if(reslist==null || reslist.size()<=0)
			return null;
		return reslist.get(0);
	}
	
	public void deleteOrgByIds(String ids) {
		String sql = "delete from Adks_org where orgId in ( "+ids+" )";
		mysqlClient.update(sql, new Object[]{});
	}
	
	public Page<List<Map<String, Object>>> getOrgListPage(Page<List<Map<String, Object>>> page){
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); //limit 起始位置
		StringBuffer sqlbuffer = new StringBuffer(" select orgId,orgName,orgCode,parentId,parentName,creatorId,creatorName"
				+ ",createtime,usernum,orgstudylong from Adks_org where 1=1 and orgId > 0 ");
		StringBuffer countsql = new StringBuffer("select count(orgId) from Adks_org where 1=1 and orgId > 0 ");
		Map map = page.getMap();
		if(map != null && map.size() > 0){
			//添加查询条件 。。
			if(map.get("orgName") != null){
				sqlbuffer.append(" and orgName like '%" + map.get("orgName") + "%'");
				countsql.append(" and orgName like '%" + map.get("orgName") + "%'");
			}
			if(map.get("parentId") != null){
				sqlbuffer.append(" and parentId=" + map.get("parentId"));
				countsql.append(" and parentId=" + map.get("parentId"));
			}
			if(map.get("orgCode") != null){
				sqlbuffer.append(" and orgCode like '%" + map.get("orgCode") + "%'");
				countsql.append(" and orgCode like '%" + map.get("orgCode") + "%'");
			}
		}
		
		//分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();
		List<Map<String, Object>> userlist = mysqlClient.queryForList(sql, new Object[0]);
		for (Map<String, Object> map2 : userlist) {
			if(map2.get("createtime") != null){
				map2.put("createtime", DateUtils.getDate2LStr(DateUtils.getStr2LDate(map2.get("createtime").toString())));
			}
		}
		//查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(userlist);
		return page;
	}
	
	public Map<String, Object> getOrgByName(String name){
		String sql = "select orgId,orgName,orgCode,parentId,parentName,creatorId,creatorName,createtime,usernum,orgstudylong from Adks_org"
				+ " where orgName='" + name +"' ";
		return mysqlClient.queryForMap(sql, new Object[]{});
	}
	
	public List<Adks_org> getOrgTopList(Map map) {
		String sql = "select orgId as id,orgName as name,orgCode,parentId,parentName from Adks_org where "
				+ "parentId="+map.get("orgId") +" limit 0,"+map.get("num");
		List<Adks_org> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_org>() {
			@Override
			public Adks_org mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_org org=new Adks_org();
				org.setOrgId(rs.getInt("id"));
				org.setId(rs.getInt("id"));
				org.setName(rs.getString("name"));
				org.setOrgName(rs.getString("name"));
				org.setOrgCode(rs.getString("orgCode"));
				org.setParentId(rs.getInt("parentId"));
				org.setParentName(rs.getString("parentName"));
				return org;
			}});
		if(reslist==null || reslist.size()<=0)
			return null;
		return reslist;
	}
	public Adks_org  getTopOrgAvgStudyTimeUserList(Map map){
		String sql = "select  ROUND((t1.sum_s/t1.count_user)/100,1) as avgXs "
				+ "from (select count(u1.userId) count_user,(sum(gu.requiredPeriod)+sum(gu.optionalPeriod)) sum_s  "
				+ "from adks_user u1 LEFT JOIN adks_grade_user gu on u1.userId = gu.userId  "
				+ "where u1.ORGCODE like CONCAT('"+map.get("orgCode")+"','%' ))  t1";
		List<Adks_org> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_org>() {
			@Override
			public Adks_org mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_org org=new Adks_org();
				org.setAvgXs(rs.getFloat("avgXs"));
				return org;
			}});
		if(reslist==null || reslist.size()<=0)
			return null;
		return reslist.get(0);
	}
	public void updateOrgUserNum(String orgCode,Integer falg) {
		if(falg==1){
			String sql="update adks_org set usernum=usernum+1 where orgCode like '%"+orgCode+"%' ";
	    	mysqlClient.update(sql, null);
		}else{
			String sql="update adks_org set usernum=usernum-1 where orgCode like '%"+orgCode+"%' and usernum is not null and usernum <> 0";
	    	mysqlClient.update(sql, null);
		}
	}
	
	/**
	 * 
	 * @Title getOrgConfig
	 * @Description
	 * @author xrl
	 * @Date 2017年5月15日
	 * @return
	 */
	public List<Adks_org_config> getOrgConfig(){
		String sql = "select * from adks_org_config oc,adks_org o where oc.orgId=o.orgId and o.parentId=0 ";
		List<Adks_org_config> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_org_config>() {
			@Override
			public Adks_org_config mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_org_config orgConfig=new Adks_org_config();
				orgConfig.setOrgName(rs.getString("orgName"));
				orgConfig.setOrgUrl(rs.getString("orgUrl"));
				orgConfig.setOrgLogoPath(rs.getString("orgLogoPath"));
				return orgConfig;
			}});
		if(reslist==null || reslist.size()<=0)
			return null;
		return reslist;
	}
	
	public Page<List<Map<String, Object>>> getOrgStatisticsListJson(Page<List<Map<String, Object>>> page){
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); //limit 起始位置
		StringBuffer sqlbuffer = new StringBuffer("select gu.userId,gu.userName,sum(gu.requiredPeriod) as requiredPeriod,sum(gu.optionalPeriod) as optionalPeriod from adks_grade_user gu ,adks_org o where gu.orgId=o.orgId ");
		StringBuffer countsql = new StringBuffer("select count(DISTINCT gu.userId) from adks_grade_user gu ,adks_org o where gu.orgId=o.orgId ");
		Map map = page.getMap();
		if(map != null && map.size() > 0){
			if(map.get("orgCode") != null && !"".equals(map.get("orgCode"))){
				sqlbuffer.append(" and  o.orgCode like '%" + map.get("orgCode")+"%'");
				countsql.append(" and  o.orgCode like '%" + map.get("orgCode")+"%'");
			}
			if(map.get("userName") != null && !"".equals(map.get("userName"))){
				sqlbuffer.append(" and  gu.userName like '%" + map.get("userName")+"%'");
				countsql.append(" and  gu.userName like '%" + map.get("userName")+"%'");
			}
		}
		sqlbuffer.append(" group by gu.userId");
		//分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();
		List<Map<String, Object>> userlist = mysqlClient.queryForList(sql, new Object[0]);
		if(userlist!=null && userlist.size()>0){
			for(Map<String, Object> map2 :userlist){
				if(map2.get("requiredPeriod") != null && !"".equals(map2.get("requiredPeriod"))){
					DecimalFormat df = new DecimalFormat("0.0");//格式化小数，不足的补0
					df.setRoundingMode(RoundingMode.FLOOR);  //取消四舍五入
					Double sumUserStudytime=(Double)map2.get("requiredPeriod")/Double.valueOf(2700);
					String suStudyTime = df.format(sumUserStudytime);//返回的是String类型的
					map2.put("requiredPeriod", suStudyTime);
				}
				if(map2.get("optionalPeriod") != null && !"".equals(map2.get("optionalPeriod"))){
					DecimalFormat df = new DecimalFormat("0.0");//格式化小数，不足的补0
					df.setRoundingMode(RoundingMode.FLOOR);  //取消四舍五入
					Double sumUserStudytime=(Double)map2.get("optionalPeriod")/Double.valueOf(2700);
					String suStudyTime = df.format(sumUserStudytime);//返回的是String类型的
					map2.put("optionalPeriod", suStudyTime);
				}
			}
		}
		//查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(userlist);
		return page;
	}
	
	public List<Map<String, Object>> getOrgStudyList(String orgCode){
		String sql = "select t1.count_user, t1.sum_s, t2.count_g from ("
				+ "select count(so.userId) count_user,(sum(so.requiredPeriod)+sum(so.optionalPeriod)) sum_s from studyOrg so where so.orgCode like '%"+orgCode+"%')t1,"
				+ "(select count(so1.userId) count_g from studyOrg so1 where so1.orgCode like '%"+orgCode+"%' and so1.isGraduate=1)t2";
		List<Map<String, Object>> reslist = mysqlClient.queryForList(sql, new Object[]{});
		if(reslist == null || reslist.size() <= 0){
			return null;
		}
		return reslist;
	}
}
