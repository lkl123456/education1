package com.adks.dubbo.providers.admin.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.menu.Adks_menu;
import com.adks.dubbo.api.data.menu.Adks_menulink;
import com.adks.dubbo.api.interfaces.admin.menu.MenuApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.admin.menu.MenuLinkService;
import com.adks.dubbo.service.admin.menu.MenuService;

public class MenuApiImpl implements MenuApi {

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuLinkService menuLinkService;

    @Override
    public List<Map<String, Object>> getMenusList() {
        return menuService.getMenusList();
    }

    @Override
    public Map<Map<String, Object>, ArrayList> getHashMapByRole(Integer roleId) {
        return null;
    }

    @Override
    public String getMenuLinksByuserId(Integer userId) {
        return menuService.getMenuLinksByuserId(userId);
    }

    @Override
    public Integer saveMenu(Adks_menu menu) {
        Integer flag = 0;
        //menu
        Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
        insertColumnValueMap.put("menuName", menu.getMenuName());
        insertColumnValueMap.put("menuDesc", menu.getMenuDesc());
        insertColumnValueMap.put("orderNum", menu.getOrderNum());
        insertColumnValueMap.put("creatorId", menu.getCreatorId());
        insertColumnValueMap.put("creatorName", menu.getCreatorName());

        if (menu.getMenuId() != null) {
            Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
            updateWhereConditionMap.put("menuId", menu.getMenuId());
            flag = menuService.update(insertColumnValueMap, updateWhereConditionMap);
        }
        else {
            insertColumnValueMap.put("createtime", menu.getCreateTime());
            flag = menuService.insert(insertColumnValueMap);
        }
        return flag;
    }

    //首页登录获取所有的菜单
    public List<Adks_menu> getMenuAll() {
        return menuService.getMenuAll();
    }

    @Override
    public Page<List<Map<String, Object>>> getMenuListPage(Page<List<Map<String, Object>>> page) {
        return menuService.getMenuListPage(page);
    }

    public Adks_menu getMenuById(Integer menuId) {
        return menuService.getMenuById(menuId);
    }

    @Override
    public Map<String, Object> getMenuByName(String menuName) {
        return menuService.getMenuByName(menuName);
    }

    public void deleteMenuById(String ids) {
        menuService.deleteMenuById(ids);
    }

    /*
     * 
     * 以下是menuLink的操作
     * 
     * 
     * */

    @Override
    public Page<List<Map<String, Object>>> getMenuLinkListPage(Page<List<Map<String, Object>>> page) {
        return menuLinkService.getMenuLinkListPage(page);
    }

    @Override
    public void deleteMenuLinkById(String ids) {
        menuLinkService.deleteMenuLinkById(ids);
    }

    public Integer saveMenuLink(Adks_menulink menuLink) {
        Integer flag = 0;
        //menu
        Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
        insertColumnValueMap.put("menuLinkName", menuLink.getMenuLinkName());
        insertColumnValueMap.put("linkUrl", menuLink.getLinkUrl());
        insertColumnValueMap.put("mLinkDisplay", menuLink.getmLinkDisplay());
        insertColumnValueMap.put("orderNum", menuLink.getOrderNum());
        insertColumnValueMap.put("menuId", menuLink.getMenuId());
        insertColumnValueMap.put("creatorId", menuLink.getCreatorId());
        insertColumnValueMap.put("creatorName", menuLink.getCreatorName());

        if (menuLink.getMenuLinkId() != null) {
            Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
            updateWhereConditionMap.put("menuLinkId", menuLink.getMenuLinkId());
            flag = menuLinkService.update(insertColumnValueMap, updateWhereConditionMap);
        }
        else {
            insertColumnValueMap.put("createtime", menuLink.getCreateTime());
            flag = menuLinkService.insert(insertColumnValueMap);
        }
        return flag;
    }

    @Override
    public Map<String, Object> getMenuLinkByName(String menuLinkName) {
        return menuLinkService.getMenuLinkByName(menuLinkName);
    }

    //首页登录获取所有的菜单
    public List<Adks_menulink> getMenuLinkAll() {
        return menuLinkService.getMenuLinkAll();
    }

    @Override
    public List<Adks_menulink> getMenuLinkByRoleList(List<Integer> roleIdList) {
        return menuLinkService.getMenuLinkByRoleList(roleIdList);
    }

}
