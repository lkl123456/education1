package com.adks.dubbo.dao.admin.role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.data.role.Adks_role_menu_link;

@Component
public class RoleMenuLinkDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_role_menu_link";
	}
	
	public List<Adks_role_menu_link> getMenuLinkIdByRole(Integer roleId){
		String sql = "select rmId,menuLinkId,roleId"
				+ " from adks_role_menu_link  where roleId="+roleId;
		List<Adks_role_menu_link> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_role_menu_link>() {
			@Override
			public Adks_role_menu_link mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_role_menu_link org=new Adks_role_menu_link();
				org.setRmId(rs.getInt("rmId"));
				org.setRoleId(rs.getInt("roleId"));
				org.setMenuLinkId(rs.getInt("menuLinkId"));
				return org;
			}});
		if(reslist==null || reslist.size()<=0)
			return null;
		return reslist;
	}
	
	public Adks_role_menu_link getRoleMenuLinKByCon(Integer roleId,Integer menuLinkId){
		String sql = "select rmId,menuLinkId,roleId"
				+ " from adks_role_menu_link  where roleId="+roleId+" and menuLinkId="+menuLinkId;
		List<Adks_role_menu_link> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_role_menu_link>() {
			@Override
			public Adks_role_menu_link mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_role_menu_link org=new Adks_role_menu_link();
				org.setRmId(rs.getInt("rmId"));
				org.setRoleId(rs.getInt("roleId"));
				org.setMenuLinkId(rs.getInt("menuLinkId"));
				return org;
			}});
		if(reslist==null || reslist.size()<=0)
			return null;
		return reslist.get(0);
	}

	public void deleteRoleMenuLinKById(Integer rmId){
		String sql = "delete from adks_role_menu_link  where rmId="+rmId;
		mysqlClient.update(sql, new Object[]{});
	}
	
	public void deleteRoleMenuLinKByCon(Integer roleId,Integer menuLinkId){
		String sql = "delete from adks_role_menu_link  where roleId="+roleId+" and menuLinkId="+menuLinkId;
		mysqlClient.update(sql, new Object[]{});
	}
}
