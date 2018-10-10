package com.adks.dubbo.api.data.user;

import java.util.ArrayList;
import java.util.List;

import com.adks.dubbo.api.data.menu.Adks_menuVo;
import com.adks.dubbo.api.data.role.Adks_roleVo;

public class Adks_userVo extends Adks_user {
    private static final long serialVersionUID = 1L;

    private Boolean isSuper;

    private List<Adks_roleVo> roles = new ArrayList<Adks_roleVo>();

    private List<Adks_menuVo> userPermissionVos;

    public Boolean getIsSuper() {
        return isSuper;
    }

    public void setIsSuper(Boolean isSuper) {
        this.isSuper = isSuper;
    }

    public List<Adks_roleVo> getRoles() {
        return roles;
    }

    public void setRoles(List<Adks_roleVo> roles) {
        this.roles = roles;
    }

    public List<Adks_menuVo> getUserPermissionVos() {
        return userPermissionVos;
    }

    public void setUserPermissionVos(List<Adks_menuVo> userPermissionVos) {
        this.userPermissionVos = userPermissionVos;
    }

}
