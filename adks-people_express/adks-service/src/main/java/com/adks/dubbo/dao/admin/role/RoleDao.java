package com.adks.dubbo.dao.admin.role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.adks.commons.util.DateUtils;
import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.data.role.Adks_role;
import com.adks.dubbo.commons.Page;

@Component
public class RoleDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_role";
	}
	
	public void deleteRoleByIds(String ids){
		String sql = "delete from adks_role  where roleId in("+ids+")";
		mysqlClient.update(sql, new Object[]{});
	}
	
	public Adks_role getRoleById(Integer roleId){
		String sql = "select roleId,roleName,isGlob from Adks_role where roleId="+roleId;
		List<Adks_role> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_role>() {
			@Override
			public Adks_role mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_role role=new Adks_role();
				role.setRoleId(rs.getInt("roleId"));
				role.setRoleName(rs.getString("roleName"));
				role.setIsGlob(rs.getInt("isGlob"));
				return role;
			}});
		if(reslist ==null || reslist.size()<=0){
			return null;
		}
		return reslist.get(0);
	}
	
	//通过名称得到角色
	public Map<String, Object> getRoleByName(String roleName){
		String sql = "select roleId,roleName,displayRef,roleDes,isGlob,creatorId,creatorName,createTime from adks_role  "
				+ " where roleName='" + roleName +"' ";
		return mysqlClient.queryForMap(sql, new Object[]{});
	}
	
	//获取角色分页
	public Page<List<Map<String, Object>>> getRoleListPage(Page<List<Map<String, Object>>> page){
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); //limit 起始位置
		StringBuffer sqlbuffer = new StringBuffer("select roleId,roleName,displayRef,roleDes,isGlob,creatorId,creatorName,createTime from adks_role  where 1=1 and roleId > 0 ");
		StringBuffer countsql = new StringBuffer("select count(roleId) from adks_role where 1=1 and roleId > 0 ");
		Map map = page.getMap();
		if(map != null && map.size() > 0){
			//添加查询条件 。。
			if(map.get("roleName") != null){
				sqlbuffer.append(" and roleName like '%" + map.get("roleName") + "%'");
				countsql.append(" and roleName like '%" + map.get("roleName") + "%'");
			}
			if(map.get("isGlob") != null){
				sqlbuffer.append(" and isGlob =" + map.get("isGlob"));
				countsql.append(" and isGlob =" + map.get("isGlob"));
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
	
	public List<Adks_role> getRolesListAll(){
		String sql = "select roleId as id,roleName as name from Adks_role";
		List<Adks_role> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_role>() {
			@Override
			public Adks_role mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_role role=new Adks_role();
				role.setId(rs.getInt("id"));
				role.setName(rs.getString("name"));
				role.setText(rs.getString("name"));
				return role;
			}});
		return reslist;
	}
	
}
