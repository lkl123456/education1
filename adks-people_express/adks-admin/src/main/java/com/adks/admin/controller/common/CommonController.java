package com.adks.admin.controller.common;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Repository;

import com.adks.dubbo.api.data.user.Adks_userVo;

@Repository
public class CommonController {

    /**
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     * @Title: setSession
     * @Description: TODO(将一些数据放到ShiroSession中,以便于其它地方使用)
     * @param @param key
     * @param @param value    设定文件
     * @return void    返回类型
     * @throws
     */
    public void setSession(Object key, Object value) {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            System.out.println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
            if (null != session) {
                session.setAttribute(key, value);
            }
        }
    }

    /**
     * 获取session中用户信息
     * @Title: getUserBySession
     * @Description: TODO(获取session中用户信息)
     * @param @return    设定文件
     * @return BrUserVo    返回类型
     * @throws
     */
    public Adks_userVo getUserBySession() {
        Adks_userVo user = null;
        Subject subject = SecurityUtils.getSubject();
        if (null != subject) {
            user = (Adks_userVo) subject.getPrincipals().asList().get(0);
        }
        return user;
    }

}