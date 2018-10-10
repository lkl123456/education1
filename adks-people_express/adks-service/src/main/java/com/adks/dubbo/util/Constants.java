package com.adks.dubbo.util;

/** 
 * @ClassName: Constants 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author harry 
 * @date 2017年1月4日 下午5:29:11 
 *  
*/
public class Constants {
    // 默认分页大小
    public static final int DEFAULT_PAGE_SIZE = 10;

    // SHA-1签名密钥
    public static final String SHA1_SECRET_KEY = PropertiesFactory.getProperty(PropertiesFactory.CONFIG_PROPERTIES,
            "sha1.secret.key");

    // 客户端在服务器上的有效时长
    public static final String CLIENT_MAXAGE = PropertiesFactory.getProperty(PropertiesFactory.CONFIG_PROPERTIES,
            "client.redis.maxAge");

    // 平台服务名称
    public static final String PLATFORM_SERVICE_NAME = "gclc";

    /**
     * 登录相关配置
     * @author harry
     */
    public static class AdminLoginConfig {
        // 登录失败次数限制，在LOCK_USER_LOGIN_PERIOD时间内如果登录失败次数超了，则在LOCK_USER_LOGIN_PERIOD时间内不让用户登录
        public static final int LOGIN_FAILED_TIMES_LIMIT = 5;

        // 登录失败次数超了之后会冻结用户登录操作的时间(单位：秒)
        public static final int LOCK_USER_LOGIN_PERIOD = 3600;
    }

    /**
     * 用户状态
     * @author harry
     */
    public static class AdminStatus {
        /**
         * 0、未激活
         */
        public static final int USER_NONACTIVATED_STATUS = 0;

        /**
         *  1、激活
         */
        public static final int USER_VALID_STATUS = 1;

        /**
         * 2、停用
         */
        public static final int USER_FREEZE_STATUS = 2;
    }
}
