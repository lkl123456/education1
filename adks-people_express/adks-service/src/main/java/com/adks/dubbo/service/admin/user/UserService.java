package com.adks.dubbo.service.admin.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.commons.util.Constants;
import com.adks.commons.util.PropertiesFactory;
import com.adks.commons.util.RedisConstant;
import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dbclient.redis.RedisClient;
import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.org.Adks_org_config;
import com.adks.dubbo.api.data.role.Adks_role;
import com.adks.dubbo.api.data.user.Adks_user;
import com.adks.dubbo.commons.ApiResponse;
import com.adks.dubbo.commons.ApiRetCode;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.UserDao;
import com.adks.dubbo.dao.admin.org.OrgDao;
import com.adks.dubbo.dao.admin.role.RoleDao;
import com.adks.dubbo.service.web.user.UserWebService;
import com.adks.dubbo.util.Constants.AdminStatus;
import com.adks.dubbo.util.OrgRedisUtil;
import com.adks.dubbo.util.UserRedisUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Service
public class UserService extends BaseService<BaseDao> {

    @Resource
    private RedisClient redisClient;

    private static final Logger logger = LoggerFactory.getLogger(UserWebService.class);
    @Autowired
    private UserDao userdao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private OrgDao orgDao;

    @Override
    protected UserDao getDao() {
        return userdao;
    }

    public boolean checkUserName(String userName) {
        return userdao.checkUserName(userName);
    }

    public Page<List<Map<String, Object>>> getUserListPage(Page<List<Map<String, Object>>> page) {
        return userdao.getUserListPage(page);
    }

    /**
     * 根据用户名查询用户信息(登录名)
     * 
     * @return Adks_user
     * 2017年4月18日
     */
    public Adks_user getUserByUserName(String userName) {
        Adks_user user = null;
        Map<String, Object> map=null;
        boolean isOne=true;
    	String result=UserRedisUtil.getObject(UserRedisUtil.adks_user_admin_name, userName);
    	if("".equals(result) || "Nodata".equals(result)){
    		map = userdao.getUserByUserName(userName);
    		UserRedisUtil.addUserSaveMap(UserRedisUtil.adks_user_admin_name, userName, map);
    	}else{
    		isOne=false;
    		map=JSONObject.parseObject(result, Map.class);
    	}
        try {
            if (map.size() != 0) {
                String overdate = "", createdate = "";
                if (null != map.get("overdate")) {
                	if(isOne){
                		overdate = (String) map.get("overdate").toString();
                	}else{
                		long sd=Long.parseLong(map.get("overdate").toString());  
                        Date dat=new Date(sd);  
                        GregorianCalendar gc = new GregorianCalendar();   
                        gc.setTime(dat);  
                        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
                        overdate=format.format(gc.getTime()); 
                	}
                }
                if (null != map.get("createdate")) {
                	if(isOne){
                		createdate = (String) map.get("createdate").toString();
                	}else{
                		long sd=Long.parseLong(map.get("createdate").toString());  
                        Date dat=new Date(sd);  
                        GregorianCalendar gc = new GregorianCalendar();   
                        gc.setTime(dat);  
                        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
                        createdate=format.format(gc.getTime()); 
                	}
                }
                map.remove("overdate");
                map.remove("createdate");
                user = new Adks_user();
                user.setOverdate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(overdate));
                user.setCreatedate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createdate));
                Integer status = Integer.parseInt(map.get("userStatus") == null ? "2" : (map.get("userStatus") + ""));
                map.remove("userStatus");
                BeanUtils.populate(user, map);
                user.setUserStatus(status);
                if(!isOne){
                	user.setUserName(map.get("username")+"");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public void deleteUserByIds(String userIds) {
        userdao.deleteUserByIds(userIds);
    }

    public Map<String, Object> getUserById(Integer arg0) {
    	Map<String, Object> map=null;
    	String result=UserRedisUtil.getObject(UserRedisUtil.adks_user_admin_id, arg0+"");
    	if("".equals(result) || "Nodata".equals(result)){
    		map=userdao.getUserById(arg0);
    		UserRedisUtil.addUserSaveMap(UserRedisUtil.adks_user_admin_id, arg0+"", map);
    	}else{
    		map=JSONObject.parseObject(result, Map.class);
    	}
        return map;
    }

    public ApiResponse<Map> loginByName(String username, String password) {

        Map<String, Object> userMap = userdao.getUserByUserName(username);
        if (userMap == null || userMap.size() == 0) {
            logger.info("admin login_failed no admin , username:{}, password:{},", username, password);
            return ApiResponse.fail(ApiRetCode.NO_DATA, "管理员不存在!");
        }

        // 1. 验证用户名密码
        String passwordDB = MapUtils.getString(userMap, "userPassword");
        if (!password.equals(passwordDB)) {
            logger.info("admin login_failed invalid password, username:{}", username);
            return ApiResponse.fail(ApiRetCode.PARAMETER_ERROR, "用户名或密码错误");
        }

        //2.状态: 1代表激活，2代表停用)  
        if (MapUtils.getIntValue(userMap, "userStatus") != AdminStatus.USER_VALID_STATUS) {
            logger.info("admin login_failed , userStatus is 2 or null", username);
            return ApiResponse.fail(ApiRetCode.PARAMETER_ERROR, "用户状态无效: ");
        }
        //3.是否拥有后台权限
        List<Map<String, Object>>  userRoleMap = new ArrayList();
        if (userMap != null) {
            userRoleMap = userdao.getUserRoleByUserId(MapUtils.getInteger(userMap, "userId"));
            if (userRoleMap == null || userRoleMap.size() == 0) {
                logger.info("admin login_failed , user role is null ", username);
                return ApiResponse.fail(ApiRetCode.PARAMETER_ERROR, "对不起、该账号暂无后台操作权限！");
            }
        }
        // 4. 生成access_token, refresh_token
        /*String accessToken = UUIDUtil.generateUUID();
        if(org.apache.commons.lang.StringUtils.isNotEmpty(username)){
        	setRedisTokenUser(accessToken,username);
        }*/

        //		//4.根据用户权限，获取菜单列表
        //		if(userRoleMap!=null||userRoleMap.size()!=0){
        //			Integer userRole=MapUtils.getInteger(userRoleMap, "role");
        //			//获取一级目录
        //			Map<String, Object> menu=userdao.getFirstMenu();
        //			//根据一级目录及用户权限获取二级目录
        //			Map<String, Object> menuLink=userdao.get
        //		}

        // 5. 使该用户的这个客户端token失效
        //如果 客户端不存在 cookieId的cookie   -1 退出时，直接失效
        // 6. 创建token

        //logger.debug("login_success, username:{}, accessToken:{}, refreshToken:{}", username);
        //OAuthResponse resp = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK).setAccessToken(accessToken).setRefreshToken(refreshToken)
        //.setExpiresIn(expiresIn).buildJSONMessage();

        Map map = new HashMap();
        map.put("username", username);
        map.put("userId", MapUtils.getIntValue(userMap, "userId"));
        if(MapUtils.getString(userMap, "orgId")!=null&&MapUtils.getString(userMap, "orgId")!=""){
        	map.put("orgId", MapUtils.getInteger(userMap, "orgId"));
        }
        if(MapUtils.getString(userMap, "orgCode")!=null&&MapUtils.getString(userMap, "orgCode")!=""){
        	map.put("orgCode", MapUtils.getString(userMap, "orgCode"));
        }
        if(MapUtils.getString(userMap, "orgName")!=null&&MapUtils.getString(userMap, "orgName")!=""){
        	map.put("orgName", MapUtils.getString(userMap, "orgName"));
        }
        if(MapUtils.getString(userMap, "headPhoto")!=null&&MapUtils.getString(userMap, "headPhoto")!=""){
        	map.put("headPhoto", MapUtils.getString(userMap, "headPhoto"));
        }
        if (userRoleMap != null || userRoleMap.size() != 0) {
            map.put("userRoleMap", userRoleMap);
        }
        //是否是超级管理员
        boolean isSuperManager = isSuperManager(MapUtils.getIntValue(userMap, "userId"));
        Integer isSuper = 2;
        if (isSuperManager) {
            isSuper = 1;
        }
        map.put("isSuper", isSuper);
        Adks_role role=roleDao.getRoleById((Integer)userRoleMap.get(0).get("roleId"));
        map.put("roleName",role.getRoleName());
        userMap.put("username", username);
        userMap.put("isSuper", isSuper);
        //登录成功，放入redis中
        //redisClient.set(RedisConstant.adks_people_express_user.concat("username_admin_").concat(username),JSONObject.toJSONString(userMap));
        UserRedisUtil.addUserSaveMap(UserRedisUtil.adks_user_admin_name, username, userMap);
        UserRedisUtil.addUserSaveMap(UserRedisUtil.adks_user_admin_id, map.get("userId")+"", userMap);
        //获取机构配置信息
        List<Adks_org_config> orgConfigList=new ArrayList<>();
        String result=OrgRedisUtil.getObjectParamOne(OrgRedisUtil.adks_org_config_List);
        if("".equals(result) || "Nodata".equals(result)){
        	orgConfigList=orgDao.getOrgConfig();
         	for (Adks_org_config adks_org_config : orgConfigList) {
         		if(adks_org_config.getOrgLogoPath()!=null){
         			adks_org_config.setOrgLogoPath(adks_org_config.getOrgLogoPath().trim());
         		}
     		}
         	OrgRedisUtil.addOrgConfigListParentIdZero(OrgRedisUtil.adks_org_config_List, orgConfigList);
        }else{
        	orgConfigList=JSONObject.parseArray(result, Adks_org_config.class);
        }
        map.put("orgConfigList",orgConfigList);
        return ApiResponse.success("登录成功", map);
    }

    public boolean isSuperManager(Integer userId) {
        return userdao.isSuperManager(userId);
    }

    /**
     * 新增登录失败次数，LOCK_USER_LOGIN_PERIOD时间后过期
     * @param username
     */
    //暂时没有用到
    private void setRedisTokenUser(String accessToken, String userId) {

       // String clientMaxage = Constants.CLIENT_MAXAGE;
       // String redisKey = LOGIN_TOKEN_REDIS_KEY_TPL + accessToken;
       // redisClient.setex(redisKey, Integer.parseInt(clientMaxage), userId);

    }

    public Map<String, Object> getUserInfoByUserId(Integer userId) {
    	Map<String, Object> map=null;
    	String result=UserRedisUtil.getObject(UserRedisUtil.adks_user_admin_id, userId+"");
    	if("".equals(result) || "Nodata".equals(result)){
    		map=userdao.getUserById(userId);
    		UserRedisUtil.addUserSaveMap(UserRedisUtil.adks_user_admin_id, userId+"", map);
    	}else{
    		map=JSONObject.parseObject(result, Map.class);
    	}
        return map;
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
    public Page<List<Map<String, Object>>> getSelGradeHeadTeacherPage(Page<List<Map<String, Object>>> page) {
        return userdao.getSelGradeHeadTeacherPage(page);
    }

    public Adks_user getUserByName(String username) {
    	Map<String, Object> userMap=null;
    	String result=UserRedisUtil.getObject(UserRedisUtil.adks_user_admin_name, username);
    	if("".equals(result) || "Nodata".equals(result)){
    		userMap = userdao.getUserByUserName(username);
    		UserRedisUtil.addUserSaveMap(UserRedisUtil.adks_user_admin_name, username, userMap);
    	}else{
    		userMap=JSONObject.parseObject(result, Map.class);
    	}
        if (userMap != null && userMap.size() > 0) {
            Adks_user userInfo = new Adks_user();
            userInfo.setUserId(Integer.parseInt(userMap.get("userId") + ""));
            userInfo.setUserName(userMap.get("userName") + "");
            userInfo.setUserPassword(userMap.get("userPassword") + "");
            userInfo.setUserStatus(Integer.parseInt(userMap.get("userStatus") + ""));
            userInfo.setOrgId(Integer.parseInt(userMap.get("orgId") + ""));
            userInfo.setOrgName(userMap.get("orgName") + "");
            userInfo.setOrgCode(userMap.get("orgCode") + "");
            return userInfo;
        }
        return null;
    }

    public Integer updatePassword(Integer userId, String password) {
    	
        return userdao.updatePassword(userId, password);
    }

    public boolean canDelUser(Integer userId) {
        return userdao.canDelUser(userId);
    }
    public Map<String, Object> getUserInfoByPhone(String userPhone) {
		return userdao.getUserInfoByPhone(userPhone);
	}
    
    public void deleteRedis(String redisName){
    	UserRedisUtil.emptyUser(redisName);
    }
}
