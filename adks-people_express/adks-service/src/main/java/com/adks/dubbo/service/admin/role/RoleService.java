package com.adks.dubbo.service.admin.role;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.role.Adks_role;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.admin.role.RoleDao;

@Service
public class RoleService extends BaseService<RoleDao>{

	@Autowired
	private RoleDao roleDao;
	
	@Override
	protected RoleDao getDao() {
		return roleDao;
	}
	
	public void deleteRoleByIds(String ids){
		roleDao.deleteRoleByIds(ids);
	}
	
	//获取角色分页
	public Page<List<Map<String, Object>>> getRoleListPage(Page<List<Map<String, Object>>> page){
		return roleDao.getRoleListPage(page);
	}

	//通过名称得到角色
	public Map<String, Object> getRoleByName(String roleName){
		return roleDao.getRoleByName(roleName);
	}
	
	public List<Adks_role> getRolesListAll(){
		return roleDao.getRolesListAll();
	}
	
	public Adks_role getRoleById(Integer roleId){
		return roleDao.getRoleById(roleId);
	}
}
