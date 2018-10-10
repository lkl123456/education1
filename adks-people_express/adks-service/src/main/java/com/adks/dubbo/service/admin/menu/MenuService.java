package com.adks.dubbo.service.admin.menu;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.menu.Adks_menu;
import com.adks.dubbo.api.data.menu.Adks_menulink;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.admin.menu.MenuDao;

@Service
public class MenuService extends BaseService<MenuDao>{
	
	@Autowired
	private MenuDao menuDao;
	
	public List<Map<String,Object>> getMenusList() {
		return menuDao.getMenusList();
	}
	//根据role得到menuLink
	public List<Map<String,Object>> getMenuLinkByRole(Integer roleId){
		return menuDao.getMenuLinkByRole(roleId);
	}
	@Override
	protected MenuDao getDao() {
		return menuDao;
	}
	public String getMenuLinksByuserId(Integer userId){
		return menuDao.getMenuLinksByuserId(userId);
	}
	public Page<List<Map<String, Object>>> getMenuListPage(Page<List<Map<String, Object>>> page) {
		return menuDao.getMenuListPage(page);
	}
	
	public Map<String, Object> getMenuByName(String menuName) {
		return menuDao.getMenuByName(menuName);
	}
	
	public void deleteMenuById(String ids){
		menuDao.deleteMenuById(ids);
	}
	
	public Adks_menu getMenuById(Integer menuId){
		return menuDao.getMenuById(menuId);
	}
	
	public List<Adks_menu> getMenuAll(){
		return menuDao.getMenuAll();
	}
}
