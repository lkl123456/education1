package com.adks.dubbo.service.app.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.user.Adks_user;
import com.adks.dubbo.dao.app.user.UserAppDao;
import com.adks.dubbo.util.UserRedisUtil;
import com.alibaba.fastjson.JSONObject;

@Service
public class UserAppService extends BaseService<BaseDao> {

    @Autowired
    private UserAppDao userdao;

    @Override
    protected UserAppDao getDao() {
        return userdao;
    }

    /**
     * 根据用户名查询用户信息
     * 
     * @param username
     * @return
     */
    public Adks_user getUserByName(String username) {
        Adks_user user = null;
        String result=UserRedisUtil.getObject(UserRedisUtil.adks_user_name, username);
        if("".equals(result) || "Nodata".equals(result)){
        	 user = userdao.getUserByName(username);
        	 UserRedisUtil.addUserByName(UserRedisUtil.adks_user_name, username, user);
        }else{
        	user=JSONObject.parseObject(result, Adks_user.class);
        }
        return user;
    }

    /**
     * 根据userId获取用户信息
     * 
     * @param userId
     * @return
     */
    public Map<String, Object> getUserByUserId(int userId) {
        Map<String, Object> map = null;
        String result=UserRedisUtil.getObject(UserRedisUtil.adks_user_map_id, userId+"");
        if("".equals(result) || "Nodata".equals(result)){
        	map = userdao.getUserByUserId(userId);
        	UserRedisUtil.addUserSaveMap(UserRedisUtil.adks_user_map_id, userId+"", map);
        }else{
        	map=JSONObject.parseObject(result, Map.class);
        }
        return map;
    }

    public void setUserConfigToRedis(int userId) {
        Map<String, Object> map = userdao.getUserByUserId(userId);
        //UserRedisUtil.addUserSaveMap(UserRedisUtil.adks_user_map_id, userId+"", map);
        UserRedisUtil.emptyUser("id__"+map.get("userId")+",map_id__"+map.get("userId")+",name__"+map.get("name")+",map_name__"+map.get("name"));
    }
}
