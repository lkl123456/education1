package com.adks.dubbo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.adks.dubbo.service.admin.menu.MenuService;

public class MenuUtil {

    private static MenuService menuService;

    static {
        ApplicationContext ac = new FileSystemXmlApplicationContext("classpath:applicationContext.xml");
        menuService = ac.getBean(MenuService.class);
    }

    //role
    public static Map<Integer, Map<String, Object>> rolesMap = new HashMap<Integer, Map<String, Object>>();

    //role
    public static List<Map<String, Object>> rolesList = new ArrayList<Map<String, Object>>();

    // 所有的菜单
    public static Map<Integer, Map<String, Object>> menusMap = new HashMap<Integer, Map<String, Object>>();

    //menu
    public static List<Map<String, Object>> menusList = new ArrayList<Map<String, Object>>();

    // ��ȡϵͳ�����еĶ����˵�l��
    public static Map<Integer, Map<String, Object>> menuLinksMap = new HashMap<Integer, Map<String, Object>>();

    public static List<Map<String, Object>> menuLinksList = new ArrayList<Map<String, Object>>();

    // ��ȡϵͳ�����еĽ�ɫ �˵���Ϣ
    public static Map<Integer, Map> roleMenuHashMap = new LinkedHashMap<Integer, Map>();

    public static List<Map<String, Object>> getMenusList() {

        if (menusList == null || menusList.size() <= 0) {
            setMenusList();
        }
        return menusList;

    }

    public static void setMenusList() {
        System.out.println("setMenusList before3232:" + menusList);
        if (menusList == null || menusList.size() <= 0) {
            System.out.println("setMenusList before:" + menusList);
            menusList = menuService.getMenusList();
            System.out.println("setMenusList after:" + menusList);
        }
    }

    public static Map<Map<String, Object>, ArrayList> getHashMapByRole(Integer roleId) {
        Map menuMap = null;
        System.out.println("menuUtil  getHashMapByRole:" + roleId);
        if (Utils.isNullOrEmpty(roleMenuHashMap.get(roleId))) {
            setHashMap(roleId);
        }
        menuMap = roleMenuHashMap.get(roleId);
        return menuMap;
    }

    public static void setHashMap(Integer roleId) {
        //根据角色id得到所有的menuLink
        System.out.println("menuService:" + menuService);
        ArrayList<Map<String, Object>> menuLinkListTemp = (ArrayList<Map<String, Object>>) menuService
                .getMenuLinkByRole(roleId);

        Map mapTemp = new LinkedHashMap();

        if (Utils.isNullOrEmpty(menusList)) {
            setMenusList();
        }

        if (Utils.isNotNullOrEmpty(menuLinkListTemp) && Utils.isNotNullOrEmpty(menusList)) {
            for (Map<String, Object> _menu : menusList) {
                ArrayList<Map<String, Object>> menuLinkList = new ArrayList();
                for (Map<String, Object> menuLink : menuLinkListTemp) {
                    Integer menuLinkId = Integer.parseInt(menuLink.get("menuLinkId") + "");
                    Integer menuId = Integer.parseInt(_menu.get("menuId") + "");

                    if (null == menuLink || menuLinkId < 1 || null == _menu || menuId < 1) {
                        continue;
                    }
                    else {
                        Integer menuLinkMenuId = Integer.parseInt(menuLink.get("menuId") + "");
                        if (menuId == menuLinkMenuId) {
                            menuLinkList.add(menuLink);
                        }
                    }
                }
                if (Utils.isNotNullOrEmpty(menuLinkList)) {
                    mapTemp.put(_menu, menuLinkList);
                }
            }
        }
        roleMenuHashMap.put(roleId, mapTemp);
    }

}
