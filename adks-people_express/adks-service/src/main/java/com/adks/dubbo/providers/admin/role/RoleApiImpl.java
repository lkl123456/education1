package com.adks.dubbo.providers.admin.role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.role.Adks_role;
import com.adks.dubbo.api.data.role.Adks_role_menu_link;
import com.adks.dubbo.api.interfaces.admin.role.RoleApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.admin.role.RoleMenuLinkService;
import com.adks.dubbo.service.admin.role.RoleService;

public class RoleApiImpl implements RoleApi{

	@Autowired
	private RoleService roleService;
	@Autowired
	private RoleMenuLinkService roleMenuLinkService;

	@Override
	public Integer saveRole(Adks_role role) {
		Integer flag=0;
		//rank
		Map<String,Object> insertColumnValueMap = new HashMap<String,Object>();
		insertColumnValueMap.put("roleName", role.getRoleName());
		insertColumnValueMap.put("displayRef", role.getDisplayRef());
		insertColumnValueMap.put("roleDes", role.getRoleDes());
		insertColumnValueMap.put("isGlob", role.getIsGlob());
		insertColumnValueMap.put("creatorId", role.getCreatorId());
		insertColumnValueMap.put("creatorName", role.getCreatorName());
		if(role.getRoleId()!=null){
			
			Map<String,Object> updateWhereConditionMap = new HashMap<String,Object>();
			updateWhereConditionMap.put("roleId", role.getRoleId());
			flag=roleService.update(insertColumnValueMap, updateWhereConditionMap);
			
		}else{
			insertColumnValueMap.put("createtime", role.getCreateTime());
			flag=roleService.insert(insertColumnValueMap);
		}
		return flag;
	}

	@Override
	public void deleteRoleByIds(String ids) {
		roleService.deleteRoleByIds(ids);
	}

	@Override
	public Page<List<Map<String, Object>>> getRoleListPage(Page<List<Map<String, Object>>> page) {
		return roleService.getRoleListPage(page);
	}

	@Override
	public Map<String, Object> getRoleByName(String roleName) {
		return roleService.getRoleByName(roleName);
	}
	public Adks_role getRoleById(Integer roleId){
		return roleService.getRoleById(roleId);
	}

	//以下是对角色菜单相关联操作
	@Override
	public Integer saveRoleMenuLinK(Adks_role_menu_link roleMenuLink) {
		Integer flag=0;
		//rank
		Map<String,Object> insertColumnValueMap = new HashMap<String,Object>();
		insertColumnValueMap.put("roleId", roleMenuLink.getRoleId());
		insertColumnValueMap.put("menuLinkId", roleMenuLink.getMenuLinkId());
		if(roleMenuLink.getRmId()!=null){
			
			Map<String,Object> updateWhereConditionMap = new HashMap<String,Object>();
			updateWhereConditionMap.put("rmId", roleMenuLink.getRmId());
			flag=roleMenuLinkService.update(insertColumnValueMap, updateWhereConditionMap);
			
		}else{
			flag=roleMenuLinkService.insert(insertColumnValueMap);
		}
		return flag;
	}

	public List<Adks_role> getRolesListAll(){
		return roleService.getRolesListAll();
	}
	
	public List<Adks_role_menu_link> getMenuLinkIdByRole(Integer roleId){
		return roleMenuLinkService.getMenuLinkIdByRole(roleId);
	}
	
	public Adks_role_menu_link getRoleMenuLinKByCon(Integer roleId,Integer menuLinkId){
		return roleMenuLinkService.getRoleMenuLinKByCon(roleId, menuLinkId);
	}
	
	public void deleteRoleMenuLinKById(Integer rmId){
		roleMenuLinkService.deleteRoleMenuLinKById(rmId);
	}
	
	public void deleteRoleMenuLinKByCon(Integer roleId,Integer menuLinkId){
		roleMenuLinkService.deleteRoleMenuLinKByCon(roleId,menuLinkId);
	}
}
