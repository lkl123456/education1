package com.adks.dubbo.service.admin.menu;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.menu.Adks_menulink;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.admin.menu.MenuLinkDao;

@Service
public class MenuLinkService extends BaseService<MenuLinkDao> {

    @Autowired
    private MenuLinkDao menuLinkDao;

    public Page<List<Map<String, Object>>> getMenuLinkListPage(Page<List<Map<String, Object>>> page) {
        return menuLinkDao.getMenuLinkListPage(page);
    }

    @Override
    protected MenuLinkDao getDao() {
        return menuLinkDao;
    }

    public void deleteMenuLinkById(String ids) {
        menuLinkDao.deleteMenuLinkById(ids);
    }

    public Map<String, Object> getMenuLinkByName(String menuLinkName) {
        return menuLinkDao.getMenuLinkByName(menuLinkName);
    }

    //首页登录获取所有的菜单
    public List<Adks_menulink> getMenuLinkAll() {
        return menuLinkDao.getMenuLinkAll();
    }

    public List<Adks_menulink> getMenuLinkByRoleList(List<Integer> roleIdList) {
        return menuLinkDao.getMenuLinkByRoleList(roleIdList);
    }
}
