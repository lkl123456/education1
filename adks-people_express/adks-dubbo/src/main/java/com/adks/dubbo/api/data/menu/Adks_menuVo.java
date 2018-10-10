package com.adks.dubbo.api.data.menu;

import java.util.ArrayList;
import java.util.List;

/** 
* @ClassName: Adks_menuVo 
* @Description: TODO(用来封装带下级菜单的父菜单) 
* @date 2017年4月19日 下午2:48:29 
*  
*/
public class Adks_menuVo extends Adks_menu {
    private static final long serialVersionUID = 1L;

    private List<Adks_menulink> childrens = new ArrayList<Adks_menulink>();

    public List<Adks_menulink> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<Adks_menulink> childrens) {
        this.childrens = childrens;
    }
}
