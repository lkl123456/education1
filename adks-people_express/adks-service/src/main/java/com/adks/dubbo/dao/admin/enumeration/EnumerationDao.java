package com.adks.dubbo.dao.admin.enumeration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.adks.commons.util.DateUtils;
import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.enumeration.Adks_enumeration;
import com.adks.dubbo.api.data.enumeration.Adks_enumeration_value;
import com.adks.dubbo.commons.Page;

@Component
public class EnumerationDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_enumeration";
	}
	/*
	 * 返回所有的枚举以及枚举值
	 * */
	public List<Adks_enumeration> getEnAll(){
		String sql = "select enId,enName,enDisplay,enDesc,enumerationType,creatorId,creatorName,createTime from adks_enumeration";
		List<Adks_enumeration> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_enumeration>() {
			@Override
			public Adks_enumeration mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_enumeration en=new Adks_enumeration();
				en.setEnId(rs.getInt("enId"));
				en.setEnName(rs.getString("enName"));
				en.setEnDisplay(rs.getString("enDisplay"));
				en.setEnDesc(rs.getString("enDesc"));
				en.setEnumerationType(rs.getInt("enumerationType"));
				en.setCreatorId(rs.getInt("creatorId"));
				en.setCreatorName("creatorName");
				en.setCreateTime(rs.getDate("createTime"));
				en.setText(en.getEnName());
				return en;
			}});
		if(reslist==null || reslist.size()<=0)
			return null;
		return reslist;
	}
	public List<Adks_enumeration_value> getEnValAll(){
		String sql = "select enValueId as id,valName as text,valDisplay,valDesc,enId,creatorId,creatorName,createTime from adks_enumeration_value";
		List<Adks_enumeration_value> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_enumeration_value>() {
			@Override
			public Adks_enumeration_value mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_enumeration_value en=new Adks_enumeration_value();
				en.setId(rs.getInt("id"));
				en.setValName(rs.getString("text"));
				en.setValDisplay(rs.getString("valDisplay"));
				en.setValDesc(rs.getString("valDesc"));
				en.setEnId(rs.getInt("enId"));
				en.setCreatorId(rs.getInt("creatorId"));
				en.setCreatorName("creatorName");
				en.setCreateTime(rs.getDate("createTime"));
				en.setText(en.getValName());
				return en;
			}});
		if(reslist==null || reslist.size()<=0)
			return null;
		return reslist;
	}
	
	public List<Map<String,Object>> getEnumerationsList() {
		String sql = "select enId,enName,enDisplay,enDesc,enumerationType,creatorId,creatorName,createTime from adks_enumeration";
		List<Map<String, Object>> reslist = mysqlClient.queryForList(sql, new Object[0]);
		if(reslist == null || reslist.size() <= 0){
			return null;
		}
		return reslist;
	}
	
	public Adks_enumeration getEnumerationById(Integer id){
		String sql = "select enId,enName,enDisplay,enDesc,enumerationType,creatorId,creatorName,createTime from adks_enumeration where enId="+id;
		List<Adks_enumeration> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_enumeration>() {
			@Override
			public Adks_enumeration mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_enumeration en=new Adks_enumeration();
				en.setEnId(rs.getInt("enId"));
				en.setEnName(rs.getString("enName"));
				en.setEnDisplay(rs.getString("enDisplay"));
				en.setEnDesc(rs.getString("enDesc"));
				en.setEnumerationType(rs.getInt("enumerationType"));
				en.setCreatorId(rs.getInt("creatorId"));
				en.setCreatorName("creatorName");
				en.setCreateTime(rs.getDate("createTime"));
				return en;
			}});
		if(reslist==null || reslist.size()<=0)
			return null;
		return reslist.get(0);
	}
	
	public void deleteEnumerationByIds(String ids) {
		String sql = "delete from adks_enumeration where enId in ( "+ids+" )";
		mysqlClient.update(sql, new Object[]{});
	}
	
	public Page<List<Map<String, Object>>> getEnumerationListPage(Page<List<Map<String, Object>>> page){
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); //limit 起始位置
		StringBuffer sqlbuffer = new StringBuffer(" select enId,enName,enDisplay,enDesc,enumerationType,creatorId,creatorName"
				+ ",createTime from adks_enumeration where 1=1 and enId > 0 ");
		StringBuffer countsql = new StringBuffer("select count(enId) from adks_enumeration where 1=1 and enId > 0 ");
		Map map = page.getMap();
		if(map != null && map.size() > 0){
			//添加查询条件 。。
			if(map.get("enName") != null){
				sqlbuffer.append(" and (enName like '%" + map.get("enName") + "%' or enDisplay  like '%" + map.get("enName") + "%') ");
				countsql.append(" and (enName like '%" + map.get("enName") + "%' or enDisplay  like '%" + map.get("enName") + "%') ");
			}
		}
		
		//分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();
		List<Map<String, Object>> userlist = mysqlClient.queryForList(sql, new Object[0]);
		for (Map<String, Object> map2 : userlist) {
			if(map2.get("createTime") != null){
				map2.put("createTime", DateUtils.getDate2SStr(DateUtils.getStr2LDate(map2.get("createTime").toString())));
			}
		}
		//查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(userlist);
		return page;
	}
	
	public Map<String, Object> getEnumerationByName(String name){
		String sql = "select enId,enName,enDisplay,enDesc,enumerationType,creatorId,creatorName,createTime from adks_enumeration"
				+ " where enName like '%" + name +"%' ";
		List<Map<String, Object>> reslist = mysqlClient.queryForList(sql, new Object[0]);
		if(reslist == null || reslist.size() <= 0){
			return null;
		}
		return reslist.get(0);
	}
	
	/*
	 * 以下是对EnumerationValue的操作
	 * 
	 * */
	public void deleteEnumerationValueByIds(String ids) {
		String sql = "delete from adks_enumeration_value where enValueId in ( "+ids+" )";
		mysqlClient.update(sql, new Object[]{});
	}
	
	public Page<List<Map<String, Object>>> getEnumerationValueListPage(Page<List<Map<String, Object>>> page){
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); //limit 起始位置
		StringBuffer sqlbuffer = new StringBuffer(" select enValueId ,valName ,valDisplay,valDesc,enId,creatorId,creatorName,createTime from adks_enumeration_value"
				+ " where 1=1 and enValueId > 0 ");
		StringBuffer countsql = new StringBuffer("select count(enValueId) from adks_enumeration_value where 1=1 and enValueId > 0  ");
		Map map = page.getMap();
		if(map != null && map.size() > 0){
			//添加查询条件 。。
			if(map.get("valName") != null || map.get("enId")!=null){
				if(map.get("valName") != null){
					sqlbuffer.append(" and (valName like '%" + map.get("valName") + "%' or valDisplay  like '%" + map.get("valName") + "%') ");
					countsql.append(" and (valName like '%" + map.get("valName") + "%' or valDisplay  like '%" + map.get("valName") + "%') ");
				}
				if( map.get("enId")!=null){
					sqlbuffer.append(" and enId=" + map.get("enId"));
					countsql.append(" and enId=" + map.get("enId"));
				}
			}
		}
		
		//分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();
		List<Map<String, Object>> userlist = mysqlClient.queryForList(sql, new Object[0]);
		for (Map<String, Object> map2 : userlist) {
			if(map2.get("createTime") != null){
				map2.put("createTime", DateUtils.getDate2SStr(DateUtils.getStr2LDate(map2.get("createTime").toString())));
			}
		}
		//查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(userlist);
		return page;
	}
	
	public Map<String, Object> getEnumerationValueByName(String name){
		String sql = "select enId,enName,enDisplay,enDesc,enumerationType,creatorId,creatorName,createTime from adks_enumeration"
				+ " where enName='" + name +"' ";
		return mysqlClient.queryForMap(sql, new Object[]{});
	}
	
}
