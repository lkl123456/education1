package com.adks.dubbo.dao.admin.role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.data.role.Adks_userrole;

@Component
public class UserRoleDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_userrole";
	}
	
	/**
	 * 自定义方法，获取部门dept json数据，拼接给zTree使用
	 * @param parent
	 * @return
	 */
	public List<Map<String, Object>> getRoleListByUser(Integer userId) {
		String sql = "select urid,userId,roleId from adks_userrole where userId="+userId;
		List<Map<String, Object>> reslist = mysqlClient.queryForList(sql, new Object[0]);
		if(reslist == null || reslist.size() <= 0){
			return null;
		}
		return reslist;
	}
	
	//根据用户id删除响应的角色
	public void deleteUserRoleByUserId(String ids){
		String sql = "delete from adks_userrole where userId in ( "+ids+" )";
		mysqlClient.update(sql, new Object[]{});
	}
	
	public boolean getUserRoleListByRole(Integer roleId){
		String sql = "select urid,userId,roleId from adks_userrole where roleId="+roleId;
		List<Map<String, Object>> reslist = mysqlClient.queryForList(sql, new Object[0]);
		if(reslist == null || reslist.size() <= 0){
			return true;
		}
		return false;
	}
	//取消用户角色关联
	public void deleteUserRoleByCon(Integer roleId,Integer userId){
		String sql = "delete from adks_userrole where userId="+userId+" and roleId="+roleId;
		mysqlClient.update(sql, new Object[]{});
	}
	public Adks_userrole getUserRoleByCon(Integer roleId,Integer userId){
		String sql = "select urid,userId,roleId from adks_userrole where userId="+userId+" and roleId="+roleId;
		List<Adks_userrole> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_userrole>() {
			@Override
			public Adks_userrole mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_userrole userrole=new Adks_userrole();
				userrole.setRoleId(rs.getInt("roleId"));
				userrole.setUserId(rs.getInt("userId"));
				userrole.setUrid(rs.getInt("urid"));
				return userrole;
			}});
		if(reslist==null || reslist.size()<=0){
			return null;
		}
		return reslist.get(0);
	}
	//判断用户的权限是否是全局
	public Integer isGlobRole(Integer userId){
		String sql = "select isGlob from adks_role where roleId in(select roleId from adks_userrole where userId="+userId+")";
		List<Map<String, Object>> reslist = mysqlClient.queryForList(sql, new Object[0]);
		if(reslist == null || reslist.size() <= 0){
			return 2;
		}
		boolean flag=false;
		for(Map<String, Object> map:reslist){
			String isGlob=map.get("isGlob")+"";
			if("1".equals(isGlob)){
				flag=true;
				break;
			}
		}
		if(flag)
			return 1;
		return 2;
	}
}
