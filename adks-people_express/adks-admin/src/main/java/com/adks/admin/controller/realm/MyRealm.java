package com.adks.admin.controller.realm;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.menu.Adks_menulink;
import com.adks.dubbo.api.data.operation.Adks_operation;
import com.adks.dubbo.api.data.role.Adks_roleVo;
import com.adks.dubbo.api.data.user.Adks_user;
import com.adks.dubbo.api.data.user.Adks_userVo;
import com.adks.dubbo.api.interfaces.admin.menu.MenuApi;
import com.adks.dubbo.api.interfaces.admin.operation.OperaApi;
import com.adks.dubbo.api.interfaces.admin.role.RoleApi;
import com.adks.dubbo.api.interfaces.admin.role.UserroleApi;
import com.adks.dubbo.api.interfaces.admin.user.UserApi;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private RoleApi roleApi;

    @Autowired
    private UserroleApi userroleApi;

    @Autowired
    private MenuApi menuApi;

    @Autowired
    private UserApi userApi;

    @Autowired
    private OperaApi operaApi;

    private static Logger logger = LoggerFactory.getLogger(MyRealm.class);

    /**
     * 授权 为当前登录的Subject授予角色和权限
     * @param principals 身份
     * @see 经测试:本例中该方法的调用时机为需授权资源被访问时
     * @see 经测试:并且每次访问需授权资源时都会执行该方法中的逻辑,这表明本例中默认并未启用AuthorizationCache
     * @see 个人感觉若使用了Spring3.1开始提供的ConcurrentMapCache支持, 则可灵活决定是否启用AuthorizationCache
     * @see 比如说这里从数据库获取权限信息时,先去访问Spring3.1提供的缓存,而不使用Shior提供的AuthorizationCache
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行授权方法");
        //角色名称的集合
        List<String> roleList = new ArrayList<String>();
        //访问权限的名称集合
        List<String> permissionList = new ArrayList<String>();
        //访问权限id的集合
        List<Integer> permissionIdList = new ArrayList<>();
        //角色id的集合
        List<Integer> roleIdList = new ArrayList<Integer>();
        Adks_userVo user = (Adks_userVo) principals.getPrimaryPrincipal();
        if (null != user) {
            List<Adks_roleVo> list = userroleApi.getRoleListByUserId(user.getUserId());
            user.setRoles(list);
            // 实体类User中包含有用户角色的实体类信息
            if (user.getRoles().size() > 0) {
                //遍历用户所有的角色
                for (Adks_roleVo role : user.getRoles()) {
                    roleList.add(role.getRoleName());
                    if (null != role && null != role.getRoleId()) {
                        roleIdList.add(role.getRoleId());
                    }
                }
                //根据角色id集合获取权限
                List<Adks_menulink> menuList = menuApi.getMenuLinkByRoleList(roleIdList);
                for (Adks_menulink adks_menulink : menuList) {
                    if (null != adks_menulink.getLinkUrl()) {
                        //判断如果权限的访问路径不为空，就讲其放入到权限的集合中
                        permissionList.add(adks_menulink.getLinkUrl());
                    }
                    permissionIdList.add(adks_menulink.getMenuLinkId());
                }
                //根据权限的id集合获取角色拥有的操作
                List<Adks_operation> operations = operaApi.getOperationByMenuLik(permissionIdList);
                for (Adks_operation adks_operation : operations) {
                    //将操作的访问路径追加到permissionList中，
                    permissionList.add(adks_operation.getOperationDescribe());
                }
            }
        }
        else {
            throw new AuthorizationException();
        }

        // 为当前用户设置角色和权限
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
        simpleAuthorInfo.addRoles(roleList);
        simpleAuthorInfo.addStringPermissions(permissionList);
        return simpleAuthorInfo;
    }

    /**
     * 认证 验证当前登录的Subject
     * 
     * @see 经测试:本例中该方法的调用时机为LoginController.login()方法中执行Subject.login()时
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行认证方法");
        String username = token.getPrincipal().toString();
        String password = new String((char[]) token.getCredentials());
        Adks_user adks_user = userApi.getUserInfo(username);

        if (adks_user == null)
            throw new UnknownAccountException("用户名或者密码出错");
        if (!adks_user.getUserPassword().equals(password))
            throw new IncorrectCredentialsException("用户名或者密码出错");
       
        Adks_userVo user = new Adks_userVo();
        try {
            BeanUtils.copyProperties(user, adks_user);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getUserPassword(), this.getName());
        info.setCredentialsSalt(ByteSource.Util.bytes(user.getUserName()));
        return info;
    }

    /**
     * 更新授权信息缓存
     */
    @Override
    protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("清除授权的缓存");
        /*
         * Cache c = this.getAuthorizationCache(); Set<Object> keys = c.keys(); for (Object o : keys) { System.out.println("授权缓存:" + o + "-----" + c.get(o) + "----------"); }
         */

        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    protected void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        System.out.println("清除认证的缓存");
        /*
         * Cache c = this.getAuthenticationCache(); Set<Object> keys = c.keys(); for (Object o : keys) { System.out.println("认证缓存:" + o + "----------" + c.get(o) + "----------"); }
         */
        Adks_userVo user = ((Adks_userVo) principals.getPrimaryPrincipal());
        SimplePrincipalCollection spc = new SimplePrincipalCollection(user.getUserName(), getName());
        super.clearCachedAuthenticationInfo(spc);
    }
}
