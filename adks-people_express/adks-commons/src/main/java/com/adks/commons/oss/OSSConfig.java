package com.adks.commons.oss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adks.commons.util.PropertiesFactory;
import com.aliyun.oss.OSSClient;

/**
 * @ClassName: OSSConfig
 * @Description: OSS配置类
 * @author libo
 */
public class OSSConfig {

    private static Logger logger = LoggerFactory.getLogger(OSSConfig.class);

    private static OSSConfig ossConfigInstance = null;

    private static OSSClient ossClient = null;//客户端操作对象

    private static String EndPoint; //连接区域地址

    private static String AccessKeyId; //连接keyId

    private static String AccessKeySecret; //连接秘钥

    private static String BucketName; //需要存储的 BucketName

    private static String FilePath; //保存路径

    private static String RootDir; //文件夹上传根目录

    public static OSSConfig getOssConfigInstance() {

        if (ossConfigInstance == null) {

            ossConfigInstance = new OSSConfig();
        }

        return ossConfigInstance;
    }

    public OSSConfig() {

        try {
            //正式环境读取配置文件
            this.AccessKeyId = PropertiesFactory.getProperty("ossConfig.properties", "oss.AccessKeyId");
            this.AccessKeySecret = PropertiesFactory.getProperty("ossConfig.properties", "oss.AccessKeySecret");
            this.EndPoint = PropertiesFactory.getProperty("ossConfig.properties", "oss.EndPoint");
            this.BucketName = PropertiesFactory.getProperty("ossConfig.properties", "oss.BucketName");

            //初始化 ossClient
            ossClientInit();
        }
        catch (Exception e) {
            logger.error("Failed to OSSConfig Constructor:" + e.getMessage());
        }
    }

    /**
     * 初始化 ossClient
     * @return
     * @throws Exception
     */
    public void ossClientInit() throws Exception {
        try {
            if (ossClient == null) {
                ossClient = new OSSClient(EndPoint, AccessKeyId, AccessKeySecret);
            }
        }
        catch (Exception e) {
            logger.error("Failed to ossClientInit :" + e.getMessage());
        }
    }

    /**
     * {方法功能中文描述}
     * 
     * @return
     * @see 
     * @author: Author
     */
    public static OSSClient createOSSClient() {
        if (null == ossClient) {
            ossClient = new OSSClient(EndPoint, AccessKeyId, AccessKeySecret);
            logger.info("First CreateOSSClient success.");
        }
        return ossClient;
    }

    public String getEndPoint() {
        return EndPoint;
    }

    public void setEndPoint(String endPoint) {
        EndPoint = endPoint;
    }

    public String getAccessKeyId() {
        return AccessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        AccessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return AccessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        AccessKeySecret = accessKeySecret;
    }

    public String getBucketName() {
        return BucketName;
    }

    public void setBucketName(String bucketName) {
        BucketName = bucketName;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    public OSSClient getOssClient() {
        //如果OssClient为空 则重建
        if (ossClient == null) {
            try {
                ossClientInit();
            }
            catch (Exception e) {
                logger.error("Failed to getOssClient :" + e.getMessage());
            }
        }
        return ossClient;
    }

    public void setOssClient(OSSClient ossClient) {
        this.ossClient = ossClient;
    }

    public void ossClientShutDown() {
        if (ossClient != null) {
            this.ossClient.shutdown();
        }
        else {
            System.out.println("ossClient is  null");
        }
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public String getRootDir() {
        return RootDir;
    }

    public void setRootDir(String rootDir) {
        RootDir = rootDir;
    }

}
