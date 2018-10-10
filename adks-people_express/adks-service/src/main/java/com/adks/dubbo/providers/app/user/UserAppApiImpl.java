package com.adks.dubbo.providers.app.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.user.Adks_user;
import com.adks.dubbo.api.interfaces.app.user.UserAppApi;
import com.adks.dubbo.service.app.user.UserAppService;

public class UserAppApiImpl implements UserAppApi {

    @Autowired
    private UserAppService userservice;

    /**
     * 根据用户名查询用户信息
     * 
     * @param username
     * @return
     */
    @Override
    public Adks_user getUserByUserName(String username) {
        return userservice.getUserByName(username);
    }

    @Override
    public Map<String, Object> getUserByUserId(int userId) {
        return userservice.getUserByUserId(userId);
    }

    @Override
    public boolean saveUser(Adks_user user) {
        int resultCode = 0;
        Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
        insertColumnValueMap.put("userRealName", user.getUserRealName());
        insertColumnValueMap.put("userSex", user.getUserSex());
        insertColumnValueMap.put("userMail", user.getUserMail());
        insertColumnValueMap.put("userPhone", user.getUserPhone());
        insertColumnValueMap.put("headPhoto", user.getHeadPhoto());

        if (user.getUserId() != null) {
            Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
            updateWhereConditionMap.put("userId", user.getUserId());
            resultCode = userservice.update(insertColumnValueMap, updateWhereConditionMap);
            userservice.setUserConfigToRedis(user.getUserId());
        }
        else {
            resultCode = userservice.insert(insertColumnValueMap);
            userservice.setUserConfigToRedis(resultCode);
        }

        if (resultCode > 0)
            return true;
        return false;

    }

}
