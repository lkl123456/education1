package com.adks.dubbo.providers.admin.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dbclient.mysql.MysqlClient;
import com.adks.dubbo.api.data.user.Adks_user;
import com.adks.dubbo.api.interfaces.admin.user.UserApi;
import com.adks.dubbo.commons.ApiResponse;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.admin.user.UserService;

public class UserApiImpl implements UserApi {

    @Autowired
    private MysqlClient mysqlClient;

    @Autowired
    private UserService userservice;

    @Override
    public Adks_user getUserInfo(String username) {
        Adks_user userInfo = userservice.getUserByUserName(username);
        return userInfo;
    }

    @Override
    public Page<List<Map<String, Object>>> getUserListPage(Page<List<Map<String, Object>>> paramPage) {
        return userservice.getUserListPage(paramPage);
    }

    @Override
    public Integer saveUser(Adks_user user) {
        Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
        insertColumnValueMap.put("userName", user.getUserName());
        insertColumnValueMap.put("userRealName", user.getUserRealName());
        insertColumnValueMap.put("orgId", user.getOrgId());
        insertColumnValueMap.put("orgName", user.getOrgName());
        insertColumnValueMap.put("orgCode", user.getOrgCode());
        insertColumnValueMap.put("userSex", "11".equals(user.getUserSex())?"1":user.getUserSex());
        insertColumnValueMap.put("userMail", user.getUserMail());
        insertColumnValueMap.put("userPhone", user.getUserPhone());
        insertColumnValueMap.put("userBirthday", user.getUserBirthday());
        insertColumnValueMap.put("userParty", user.getUserParty());
        insertColumnValueMap.put("cardNumer", user.getCardNumer());
        insertColumnValueMap.put("userNationality", user.getUserNationality());
        insertColumnValueMap.put("rankId", user.getRankId());
        insertColumnValueMap.put("rankName", user.getRankName());
        insertColumnValueMap.put("positionId", user.getPositionId());
        insertColumnValueMap.put("positionName", user.getPositionName());
        insertColumnValueMap.put("userStatus", user.getUserStatus()==null?1:user.getUserStatus());
        insertColumnValueMap.put("overdate", user.getOverdate());
        //SUNHENAN ADD 2017年4月10日17:15:24  保存头像
        insertColumnValueMap.put("headPhoto", user.getHeadPhoto());

        Integer i=0;
        if (user.getUserId() != null) {
            Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
            updateWhereConditionMap.put("userId", user.getUserId());
            i=userservice.update(insertColumnValueMap, updateWhereConditionMap);
            userservice.deleteRedis("id__"+user.getUserId()+",admin_id__"+user.getUserId()+",name__"+user.getUserName()+",admin_name__"+user.getUserName()+",map_id__"+user.getUserId()+",map_name__"+user.getUserName());
        }
        else {
            insertColumnValueMap.put("creatorId", user.getCreatorId());
            insertColumnValueMap.put("createdate", user.getCreatedate());
            insertColumnValueMap.put("creatorName", user.getCreatorName());
            insertColumnValueMap.put("userOnlineLong", user.getUserOnlineLong());
            insertColumnValueMap.put("userStudyLong", user.getUserStudyLong());
            insertColumnValueMap.put("userPassword", user.getUserPassword());
            i=userservice.insert(insertColumnValueMap);
        }
        return i;
    }

    @Override
    public Integer changeUser(Adks_user user, Integer handeType,String userName) {
        Map<String, Object> paramValue = new HashMap<String, Object>();
        Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
        if (handeType == 1) {
            //重置密码
            paramValue.put("userPassword", user.getUserPassword());
            updateWhereConditionMap.put("userId", user.getUserId());
        }
        else if (handeType == 2) {
            //停用
            paramValue.put("userStatus", 2);
            updateWhereConditionMap.put("userId", user.getUserId());
        }
        else if (handeType == 3) {
            //恢复
            paramValue.put("userStatus", 1);
            updateWhereConditionMap.put("userId", user.getUserId());
        }
        userservice.deleteRedis("id__"+user.getUserId()+",admin_id__"+user.getUserId()+",admin_name__"+userName+",name__"+userName+",map_id__"+user.getUserId()+",map_name__"+userName);
        return userservice.update(paramValue, updateWhereConditionMap);
    }

    @Override
    public void deleteUserByIds(String userIds,Map map) {
    	String[] ids=userIds.split(",");
    	for(int i=0;i<ids.length;i++){
    		if(ids[i]!=null && !"".equals(ids[i])){
    			String username=MapUtils.getString(map, ids[i]);
    			userservice.deleteRedis("id__"+ids[i]+",admin_id__"+ids[i]+",admin_name__"+username+",name__"+username+",map_id__"+ids[i]+",map_name__"+username);
    		}
    	}
        userservice.deleteUserByIds(userIds);
    }

    @Override
    public Map<String, Object> getUserInfoById(Integer arg0) {
        return userservice.getUserById(arg0);
    }

    @Override
    public ApiResponse<Map> loginByName(String username, String password) {
        return userservice.loginByName(username, password);
    }

    @Override
    public boolean checkUserName(String userName) {
        return userservice.checkUserName(userName);
    }

    /**
     * 
     * @Title getUserInfoByUserId
     * @Description：根据userId获取用户信息
     * @author xrl
     * @Date 2017年3月30日
     * @param userId
     * @return
     */
    @Override
    public Map<String, Object> getUserInfoByUserId(Integer userId) {
        return userservice.getUserInfoByUserId(userId);
    }

    /**
     * 
     * @Title getSelGradeHeadTeacherPage
     * @Description：获取班主任角色的用户
     * @author xrl
     * @Date 2017年3月30日
     * @param page
     * @return
     */
    @Override
    public Page<List<Map<String, Object>>> getSelGradeHeadTeacherPage(Page<List<Map<String, Object>>> page) {
        return userservice.getSelGradeHeadTeacherPage(page);
    }

	@Override
	public Integer updatePassword(Integer userId,String userName, String password) {
		deleteUserToRedis(userId,userName);
		return userservice.updatePassword(userId,password);
	}

	@Override
	public Adks_user getUserByName(String userName) {
		return userservice.getUserByName(userName);
	}

	@Override
	public boolean canDelUser(Integer userId) {
		return userservice.canDelUser(userId);
	}

	@Override
	public Map<String, Object> getUserInfoByPhone(String userPhone) {
		return userservice.getUserInfoByPhone(userPhone);
	}

	@Override
	public void deleteUserToRedis(Integer userId, String userName) {
		userservice.deleteRedis("id__"+userId+",admin_id__"+userId+",admin_name__"+userName+",name__"+userName+",map_id__"+userId+",map_name__"+userName);
	}
}
