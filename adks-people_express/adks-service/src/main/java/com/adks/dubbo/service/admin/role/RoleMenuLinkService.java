package com.adks.dubbo.service.admin.role;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.role.Adks_role_menu_link;
import com.adks.dubbo.dao.admin.role.RoleMenuLinkDao;

@Service
public class RoleMenuLinkService extends BaseService<RoleMenuLinkDao>{

	@Autowired
	private RoleMenuLinkDao roleMenuLinkDao;

	@Override
	protected RoleMenuLinkDao getDao() {
		return roleMenuLinkDao;
	}
	
	public List<Adks_role_menu_link> getMenuLinkIdByRole(Integer roleId){
		return roleMenuLinkDao.getMenuLinkIdByRole(roleId);
	}
	
	public Adks_role_menu_link getRoleMenuLinKByCon(Integer roleId,Integer menuLinkId){
		return roleMenuLinkDao.getRoleMenuLinKByCon(roleId, menuLinkId);
	}
	
	public void deleteRoleMenuLinKById(Integer rmId){
		roleMenuLinkDao.deleteRoleMenuLinKById(rmId);
	}
	
	public void deleteRoleMenuLinKByCon(Integer roleId,Integer menuLinkId){
		roleMenuLinkDao.deleteRoleMenuLinKByCon(roleId,menuLinkId);
	}
}
