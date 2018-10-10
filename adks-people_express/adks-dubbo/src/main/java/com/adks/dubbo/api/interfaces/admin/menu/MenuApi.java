package com.adks.dubbo.api.interfaces.admin.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.menu.Adks_menu;
import com.adks.dubbo.api.data.menu.Adks_menulink;
import com.adks.dubbo.commons.Page;

public interface MenuApi {

    //得到所有的菜单
    public List<Map<String, Object>> getMenusList();

    public Map<Map<String, Object>, ArrayList> getHashMapByRole(Integer roleId);

    public String getMenuLinksByuserId(Integer userId);

    public Map<String, Object> getMenuByName(String menuName);

    public Integer saveMenu(Adks_menu menu);

    public void deleteMenuById(String ids);

    public Adks_menu getMenuById(Integer menuId);

    //首页登录获取所有的菜单
    public List<Adks_menu> getMenuAll();

    //菜单分页
    public Page<List<Map<String, Object>>> getMenuListPage(Page<List<Map<String, Object>>> page);

    /*
     * 
     * 以下是menuLink的操作
     * 
     * 
     * */
    public Page<List<Map<String, Object>>> getMenuLinkListPage(Page<List<Map<String, Object>>> page);

    public void deleteMenuLinkById(String ids);

    public Integer saveMenuLink(Adks_menulink menuLink);

    public Map<String, Object> getMenuLinkByName(String menuLinkName);

    //首页登录获取所有的菜单
    public List<Adks_menulink> getMenuLinkAll();
    //根据角色id获取连接集合
    public List<Adks_menulink> getMenuLinkByRoleList(List<Integer> roleIdList);
}
