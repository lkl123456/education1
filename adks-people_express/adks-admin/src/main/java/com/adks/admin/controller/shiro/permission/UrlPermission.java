package com.adks.admin.controller.shiro.permission;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.util.PatternMatcher;

public class UrlPermission implements Permission {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public UrlPermission() {
    }

    public UrlPermission(String url) {
        this.url = url;
    }

    /** 
    * <p>Title: implies</p> 
    * <p>Description: 此方法用来判断当前角色拥有的权限操作跟现有访问的权限操作是否一致</p> 
    * @param p
    * @return 
    * @see org.apache.shiro.authz.Permission#implies(org.apache.shiro.authz.Permission) 
    */
    public boolean implies(Permission p) {
        if (!(p instanceof UrlPermission))
            return false;
        UrlPermission up = (UrlPermission) p;
        // /userLogin/userTologin
        PatternMatcher patternMatcher = new AntPathMatcher();
        /*
         *此处输出的信息为 当前访问的url与  用户拥有的权限 是否一致
         */
        System.out.println(this.getUrl() + "," + up.getUrl() + "," + patternMatcher.matches(this.getUrl(), up.getUrl()));
        return patternMatcher.matches(this.getUrl(), up.getUrl());
    }
}
