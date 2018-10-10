package com.adks.dubbo.api.interfaces.admin.role;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.role.Adks_role;
import com.adks.dubbo.api.data.role.Adks_role_menu_link;
import com.adks.dubbo.commons.Page;

public interface RoleApi {
	
	public Integer saveRole(Adks_role role);
	
	public void deleteRoleByIds(String ids);
	
	//获取角色分页
	public Page<List<Map<String, Object>>> getRoleListPage(Page<List<Map<String, Object>>> page);
	
	//通过名称得到角色
	public Map<String, Object> getRoleByName(String roleName);
	
	//通过Id得到角色
	public Adks_role getRoleById(Integer roleId);
	
	//以下是角色菜单相关联的操作
	
	public Integer saveRoleMenuLinK(Adks_role_menu_link roleMenuLink);
	
	public List<Adks_role> getRolesListAll();
	
	public List<Adks_role_menu_link> getMenuLinkIdByRole(Integer roleId);
	
	public Adks_role_menu_link getRoleMenuLinKByCon(Integer roleId,Integer menuLinkId);
	
	public void deleteRoleMenuLinKById(Integer rmId);
	
	public void deleteRoleMenuLinKByCon(Integer roleId,Integer menuLinkId);
	
}
