package com.adks.dubbo.dao.admin.menu;

import java.sql.ResultSet;
import java.sql.SQLException;
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
public class MenuDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_menu";
	}
	
	public Adks_menu getMenuById(Integer menuId){
		String sql = "select menuId,menuName,menuDesc,orderNum,creatorId,creatorName,createTime"
				+ " from adks_menu where menuId="+menuId;
		List<Adks_menu> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_menu>() {
			@Override
			public Adks_menu mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_menu menu=new Adks_menu();
				menu.setMenuId(rs.getInt("menuId"));
				menu.setMenuName(rs.getString("menuName"));
				menu.setMenuDesc(rs.getString("menuDesc"));
				menu.setOrderNum(rs.getInt("orderNum"));
				menu.setCreatorId(rs.getInt("creatorId"));
				menu.setCreatorName(rs.getString("creatorName"));
				menu.setCreateTime(rs.getDate("createTime"));
				return menu;
			}});
		if(reslist==null || reslist.size()<=0)
			return null;
		return reslist.get(0);
	}
	
	public Page<List<Map<String, Object>>> getMenuListPage(Page<List<Map<String, Object>>> page) {
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); //limit 起始位置
		StringBuffer sqlbuffer = new StringBuffer("select menuId,menuName,menuDesc,orderNum,creatorId,creatorName,createTime"
				+ " from adks_menu  where 1=1 and menuId>0 ");
		StringBuffer countsql = new StringBuffer("select count(menuId) from adks_menu  where 1=1 and menuId>0 ");
		Map map = page.getMap();
		if(map != null && map.size() > 0){
			//添加查询条件 。。
			if(map.get("menuName") != null){
				sqlbuffer.append(" and menuName like '%" + map.get("menuName") + "%' ");
				countsql.append(" and menuName like '%" + map.get("menuName") + "%' ");
			}
		}
		
		sqlbuffer.append(" order by orderNum ");
		countsql.append(" order by orderNum ");
		
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
	
	public String getMenuLinksByuserId(Integer userId){
		String sql = "select distinct m.menuLinkId as menuId "
				+ "from adks_menulink m where m.menuLinkId in (select menuLinkId from adks_role_menu_link t where t.roleId in"
				+ "(select roleId from adks_userrole where userId="+userId+")"
				+ ") order by m.orderNum asc";
		List<Map<String, Object>> reslist = mysqlClient.queryForList(sql, new Object[0]);
		if(reslist == null || reslist.size() <= 0){
			return null;
		}
		String res="";
		for(int i=0;i<reslist.size();i++){
			res+=reslist.get(i).get("menuId")+",";
		}
		res=res.substring(0,res.length()-1);
		return res;
	}
	
	public List<Map<String,Object>> getMenusList() {
		String sql = "select menuId,menuName from adks_menu order by orderNum";
		List<Map<String, Object>> reslist = mysqlClient.queryForList(sql, new Object[0]);
		if(reslist == null || reslist.size() <= 0){
			return null;
		}
		return reslist;
	}
	//根据role得到menuLink
	public List<Map<String,Object>> getMenuLinkByRole(Integer roleId){
		String sql = " select m.menuLinkId as menuLinkId ,m.menuLinkName as menuLinkName,  m.linkUrl as linkUrl, m.orderNum, m.menuId as menuId "
				+ "from adks_menulink m where m.menuLinkId in (select menuLinkId from adks_role_menu_link t where t.roleId = "+roleId+" ) order by m.orderNum asc";
		
		List<Map<String, Object>> reslist = mysqlClient.queryForList(sql, new Object[0]);
		if(reslist == null || reslist.size() <= 0){
			return null;
		}
		return reslist;
	}

	public Map<String, Object> getMenuByName(String menuName) {
		String sql = "select menuId,menuName,menuDesc,orderNum,creatorId,creatorName,createTime"
				+ " from adks_menu  where menuName='"+menuName+"'";
		
		List<Map<String, Object>> reslist = mysqlClient.queryForList(sql, new Object[0]);
		if(reslist == null || reslist.size() <= 0){
			return null;
		}
		return reslist.get(0);
	}
	
	public void deleteMenuById(String ids){
		String sql = "delete from adks_menu where menuId in ( "+ids+" )";
		mysqlClient.update(sql, new Object[]{});
		
		String sql1 = "delete from adks_menulink where menuId in ( "+ids+" )";
		mysqlClient.update(sql1, new Object[]{});
	}
	public List<Adks_menu> getMenuAll(){
		String sql = "select menuId,menuName"
				+ " from adks_menu order by orderNum";
		List<Adks_menu> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_menu>() {
			@Override
			public Adks_menu mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_menu menu=new Adks_menu();
				menu.setMenuId(rs.getInt("menuId"));
				menu.setMenuname(rs.getString("menuName"));
				menu.setIcon("icon-"+menu.getMenuId());
				return menu;
			}});
		if(reslist==null || reslist.size()<=0)
			return null;
		return reslist;
	}
}
