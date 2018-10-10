package com.adks.dubbo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.adks.dbclient.dao.singaltanent.BaseDao;

@Component
public class AdminDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "admin";
	}

	/**
	 * 根据用户名获取管理员信息
	 * @param user_name
	 * @return
	 */
	public Map<String, Object> getAdminByUserName(String user_name){
		Object[] obj = new Object[]{user_name};
		String sql = " select id,user_name,password,status,admin_type from admin where user_name=? ";
		return mysqlClient.queryForMap(sql, obj);
	}
	
	/**
	 * 删除管理员信息
	 * @param sql
	 * @param objs
	 */
	public void deleteAdmin(String ids){
		String sql = " delete from admin ";
		if(ids.split(",").length > 1){
			sql += " where id in (" + ids + ") ";
		}else{
			sql += " where id = " + ids;
		}
		mysqlClient.update(sql, new Object[]{});
	}
	
	
	/**
	 * @title: getAdminByNamePwd 
	 * @description: TODO(查询管理员 信息) 
	 * @author:harry
	 * @param userName
	 * @param passWord
	 * @param 设定文件 
	 * @return Map<String,Object>    返回类型 
	 * @throws 
	*/
	public Map<String,Object> getAdminByNamePwd(String username){
		
		Object[] obj = new Object[]{username};
		String sql = " select id,user_name,password,status,admin_type,org_id  "
				+ " from admin  where user_name=? ";
		return mysqlClient.queryForMap(sql, obj);
		
	}
	
	/**
	 * 根据用户id获取用户关联的权限菜单信息
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getOperationsByUserId(Integer userId) {
		String sql = "select t3.operation_value "
				+ " from admin_role t1 left join role_operation t2 on t1.role_id = t2.role_id "
				+ " left join operation t3 on t2.operation_id = t3.id "
				+ " where t1.admin_id = ?";

		Object[] obj = new Object[]{userId};
		return mysqlClient.queryForList(sql, obj);
	}
}
