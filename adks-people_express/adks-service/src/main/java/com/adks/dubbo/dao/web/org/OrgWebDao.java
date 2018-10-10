package com.adks.dubbo.dao.web.org;

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
import com.adks.dubbo.commons.Page;

@Component
public class OrgWebDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_org";
	}
	public List<Adks_org> getOrgParentIdIsZero() {
		String sql = "select orgId as id,orgName as name,orgCode,parentId,parentName from Adks_org where "
				+ "parentId=0";
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
	
	public List<Adks_org> getOrgTopList(Map map) {
		String sql = "select  e.orgName,e.orgCode,e.avgXs,e.orgId from ( select av.orgCode,t1.orgId, t1.orgName,(sum(av.sum_s)/sum(av.count_user)) as avgXs from avgOrgXueShi3 av, "
				+ "( select org.orgName,org.parentId,org.orgId,org.orgCode from avgOrgXueShi3 av3,adks_org org where av3.orgCode=org.orgCode and org.orgCode like '%"+map.get("orgCode")+"%' and org.orgId <>"+map.get("orgId")+" )t1 "
				+ "where   av.orgCode like CONCAT(t1.orgCode,'%') GROUP BY t1.orgCode )e ORDER BY e.avgXs desc, e.orgId  limit 0,"+map.get("num");
		List<Adks_org> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_org>() {
			@Override
			public Adks_org mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_org org=new Adks_org();
				org.setOrgId(rs.getInt("orgId"));
				org.setOrgName(rs.getString("orgName"));
				org.setOrgCode(rs.getString("orgCode"));
				DecimalFormat df = new DecimalFormat("0.0");//格式化小数，不足的补0
				df.setRoundingMode(RoundingMode.FLOOR);  //取消四舍五入
				Double avgXs=rs.getDouble("avgXs")/Double.valueOf(2700);
				String aXs = df.format(avgXs);//返回的是String类型的
				org.setAvgXs(Float.valueOf(aXs));
				return org;
			}});
		if(reslist==null || reslist.size()<=0)
			return null;
		return reslist;
	}
	//目前没有使用到
	public Adks_org  getTopOrgAvgStudyTimeUserList(Map map){
		String sql = "select  e.orgName,e.orgCode,e.avgXs,e.orgId from ( select av.orgCode,t1.orgId, t1.orgName,((sum(av.sum_s)/sum(av.count_user))/2700) as avgXs from avgOrgXueShi3 av, "
				+ "( select org.orgName,org.parentId,org.orgId,org.orgCode from avgOrgXueShi3 av3,adks_org org where av3.orgCode=org.orgCode and org.orgCode like '%"+map.get("orgCode")+"%' and org.orgId <>"+map.get("orgId")+" )t1 "
				+ "where   av.orgCode like CONCAT(t1.orgCode,'%') GROUP BY t1.orgCode )e ORDER BY e.avgXs desc, e.orgId  limit 0,"+map.get("num");
		List<Adks_org> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_org>() {
			@Override
			public Adks_org mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_org org=new Adks_org();
				DecimalFormat df = new DecimalFormat("0.0");//格式化小数，不足的补0
				df.setRoundingMode(RoundingMode.FLOOR);  //取消四舍五入
				Double avgXs=rs.getDouble("avgXs")/Double.valueOf(2700);
				String aXs = df.format(avgXs);//返回的是String类型的
				org.setAvgXs(Float.valueOf(aXs));
				return org;
			}});
		if(reslist==null || reslist.size()<=0)
			return null;
		return reslist.get(0);
	}
}
