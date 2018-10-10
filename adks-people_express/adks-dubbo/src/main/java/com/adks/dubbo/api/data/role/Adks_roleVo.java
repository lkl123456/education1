package com.adks.dubbo.api.data.role;

import java.util.ArrayList;
import java.util.List;

import com.adks.dubbo.api.data.menu.Adks_menuVo;

public class Adks_roleVo extends Adks_role {
    private static final long serialVersionUID = 1L;

    private List<Adks_menuVo> permisssionList = new ArrayList<Adks_menuVo>();

    public List<Adks_menuVo> getPermisssionList() {
        return permisssionList;
    }

    public void setPermisssionList(List<Adks_menuVo> permisssionList) {
        this.permisssionList = permisssionList;
    }

}
