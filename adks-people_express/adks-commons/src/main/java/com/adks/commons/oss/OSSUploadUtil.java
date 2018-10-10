package com.adks.commons.oss;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.AppendObjectRequest;
import com.aliyun.oss.model.AppendObjectResult;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.GenericRequest;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;

/**
 * 
 * @ClassName: OSSUploadUtil
 * @Description: 阿里云OSS文件上传工具类
 * @author libo
 * 
 * OSSUploadUtil.uploadFile(File file, String fileType)//单文件上传，fileType:文件后缀名
 * OSSUploadUtil.updateFile(File file, String fileType, String oldUrl)//更新文件:只更新内容，不更新文件名和文件地址。
 * OSSUploadUtil.replaceFile(File file, String fileType, String oldUrl)//替换文件，删除源文件并上传新文件，文件名和地址也改变
 * OSSUploadUtil.deleteFile(List fileUrls) //删除多文件，根据文件url来自定获取其中的bucket和文件名，用于bucket和文件名可能存在不同的，循环调用deleteFile方法
 * OSSUploadUtil.deleteFile(String fileUrl) //删除单文件
 * OSSUploadUtil.deleteFiles(List fileUrls) //删除多文件，根据配置直接取删除多个文件，bucket和文件地址从配置中获取，用于多文件bucket和文件名都相同的
 * 
 */
public class OSSUploadUtil {
    private static Logger logger = LoggerFactory.getLogger(OSSUploadUtil.class);

    private static OSSConfig ossConfig = null;

    /**####################################上传文件start####################################**/
    /**
    * 
    * @MethodName: uploadFile
    * @Description: OSS单文件上传
    * @param file  
    * @param fileType 文件后缀
    * @return String 文件地址
    */
    public static String uploadFileNewName(File file, String fileType, String filePath) {
        //先实例化ossConfig对象
        ossConfig = OSSConfig.getOssConfigInstance();
        ossConfig.setFilePath(filePath);
        //UUID重新命名获取文件名(防止重复)
        String fileName = null;
        String rtsName = null;
        try {
            fileName = ossConfig.getFilePath() + UUID.randomUUID().toString().toUpperCase().replace("-", "") + "."
                    + fileType;
            rtsName = putObject(file, fileType, fileName);
        }
        catch (Exception oe) {
            oe.printStackTrace();
            logger.error("Failed to uploadFileNewName to oss :" + oe.getMessage());
        }
        finally {
            //        	 if(ossConfig !=null){
            //      		   ossConfig.ossClientShutDown();
            //      	   	}
        }

        return rtsName;
    }
    
    public static String uploadFileNewName(InputStream is, String fileType, String filePath) {
        //先实例化ossConfig对象
        ossConfig = OSSConfig.getOssConfigInstance();
        ossConfig.setFilePath(filePath);
        //UUID重新命名获取文件名(防止重复)
        String fileName = null;
        String rtsName = null;
        try {
            fileName = ossConfig.getFilePath() + UUID.randomUUID().toString().toUpperCase().replace("-", "") + "."
                    + fileType;
            rtsName = putObject(is, fileType, fileName);
        }
        catch (Exception oe) {
            oe.printStackTrace();
            logger.error("Failed to uploadFileNewName to oss :" + oe.getMessage());
        }
        finally {
            //        	 if(ossConfig !=null){
            //      		   ossConfig.ossClientShutDown();
            //      	   	}
        }

        return rtsName;
    }

    /**
     * 
     * @MethodName: uploadFile
     * @Description: OSS单文件上传
     * @param file  
     * @param fileType 文件后缀
     * @return String 文件地址
     */
    public static String uploadFileOldName(String filePath, String fileName) {
        //先实例化ossConfig对象
        ossConfig = OSSConfig.getOssConfigInstance();
        File file = new File(filePath);
        String fileType = null;
        String ruestStr = null;

        try {
            if (file.exists()) {
                //原文件名称上传（三分屏课件只能源文件夹导入）
                if (fileName.indexOf("\\") > 0) {
                    fileName = fileName.replace("\\", "/");
                }
                fileName = ossConfig.getFilePath() + fileName;
                fileType = file.getName().substring((file.getName().indexOf(".") + 1), file.getName().length());
                ruestStr = putObject(file, fileType, fileName);
            }

        }
        catch (Exception oe) {
            oe.printStackTrace();
            logger.error("Failed to uploadFileOldName to oss :" + oe.getMessage());
        }
        return ruestStr;
    }

    /**
     * @MethodName: uploadDir
     * @Description: OSS单文件夹（目录）上传
     * @param folder 文件夹（目录）路径
     * @return String  文件夹（目录）地址
    */
    public static String uploadDirIndex(String folder, String dirPath) {
        ossConfig = OSSConfig.getOssConfigInstance();
        ossConfig.setFilePath(dirPath);
        String New_Dir = null;
        try {
            //开始上传目录文件
            New_Dir = uploadDir(ossConfig, folder);
        }
        catch (Exception oe) {
            oe.printStackTrace();
            logger.error("Failed to uploadDirIndex to oss :" + oe.getMessage());
        }
        finally {
            //    	   if(ossConfig !=null){
            //    		   ossConfig.ossClientShutDown();
            //    	   }
        }
        return New_Dir;
    }

    /**
     * @MethodName: uploadDir
     * @Description: OSS单文件夹（目录）上传
     * @param folder 文件夹（目录）路径
     * @return String  文件夹（目录）地址
    */
    public static String uploadDir(OSSConfig ossConfig, String folder) {
        //先实例化ossConfig对象

        File file = new File(folder);
        //递归循环子目录名称
        String dynaDir = file.getName();
        //设置上传文件根目录
        if (ossConfig.getRootDir() == null) {
            ossConfig.setRootDir(dynaDir);
        }

        if (file.exists()) {

            File[] files = file.listFiles();

            if (files.length == 0) {
                logger.error("This directory is null");
            }
            else {
                for (File file2 : files) {

                    if (file2.isDirectory()) {
                        //递归遍历目录
                        uploadDir(ossConfig, file2.getAbsolutePath());
                    }
                    else {
                        //进入文件目录
                        String FPath = file2.getAbsolutePath();

                        if (!dynaDir.equals(ossConfig.getRootDir())) {
                            if (FPath.contains(dynaDir)) {
                                FPath = ossConfig.getRootDir() + "\\"
                                        + FPath.substring(FPath.indexOf(dynaDir), FPath.length());
                                logger.error("The child directory is " + FPath);
                            }
                            else {
                                FPath = ossConfig.getRootDir() + "\\" + dynaDir + "\\"
                                        + FPath.substring(FPath.indexOf(dynaDir), FPath.length());
                                logger.error("The child directory is " + FPath);
                            }
                        }
                        else {
                            FPath = FPath.substring(FPath.indexOf(dynaDir), FPath.length());
                            logger.error("The child directory is " + FPath);
                        }

                        //开始上传目录文件
                        uploadFileOldName(file2.getAbsolutePath(), FPath);
                    }
                }
            }
        }
        else {
            logger.error("The directory does not exist!");
        }

        return ossConfig.getFilePath() + ossConfig.getRootDir();
    }

    /**####################################上传文件end####################################**/

    /**
     * @Description: 断点上传文件到OSS文件服务器
     * @param content  文件流
     * @param key    上传为OSS文件服务器的唯一标识
     * @param position 位置
    */
    public String appendObjectFile(File file, String key, int position, String fileType) throws Exception {
        //先实例化ossConfig对象
        ossConfig = OSSConfig.getOssConfigInstance();

        AppendObjectResult appendObjectResult = null;

        try {
            // 续传 开始
            logger.error("AppendObject to oss start " + key);
            InputStream input = new FileInputStream(file);
            // 创建上传Object的Metadata
            ObjectMetadata meta = new ObjectMetadata();
            // 设置上传内容类型
            meta.setContentType(OSSUploadUtil.contentType(fileType));
            // 被下载时网页的缓存行为  
            meta.setCacheControl("no-cache");
            //创建追加请求
            AppendObjectRequest appendObjectRequest = new AppendObjectRequest(ossConfig.getBucketName(), key, input,
                    meta);
            //设置续传到文件的位置
            appendObjectRequest.setPosition(Long.valueOf(position));
            //讲请求追加到文件结果
            appendObjectResult = ossConfig.getOssClient().appendObject(appendObjectRequest);

            logger.error("AppendObject to oss end" + key);
        }
        catch (Exception oe) {
            oe.printStackTrace();
            logger.error("Failed to appendObjectFile to oss :" + oe.getMessage());
        }
        finally {
            if (ossConfig != null) {
                ossConfig.ossClientShutDown();
            }
        }

        return appendObjectResult.getNextPosition().toString();
    }

    /**####################################更新文件start####################################**/
    /**
     * 
     * @MethodName: updateFile
     * @Description: 更新文件:只更新内容，不更新文件名和文件地址。
     * 		(因为地址没变，可能存在浏览器原数据缓存，不能及时加载新数据，例如图片更新，请注意)
     * @param file
     * @param fileType
     * @param oldUrl
     * @return String
     */
    public static String updateFile(File file, String fileType, String oldUrl) {

        String fileName = getFileName(oldUrl);
        String rtsName = null;
        try {
            if (fileName == null) {
                return null;
            }

            rtsName = putObject(file, fileType, fileName);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            //       	 	if(ossConfig !=null){
            //    		   ossConfig.ossClientShutDown();
            //       	 	}
        }

        return rtsName;
    }
    public static String updateFile(InputStream is, String fileType, String oldUrl) {

        String fileName = getFileName(oldUrl);
        String rtsName = null;
        try {
            if (fileName == null) {
                return null;
            }

            rtsName = putObject(is, fileType, fileName);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            //       	 	if(ossConfig !=null){
            //    		   ossConfig.ossClientShutDown();
            //       	 	}
        }

        return rtsName;
    }

    /**
     * 
     * @MethodName: replaceFile
     * @Description: 替换文件:删除原文件并上传新文件，文件名和地址同时替换
     *     解决原数据缓存问题，只要更新了地址，就能重新加载数据)
     * @param file
     * @param fileType 文件后缀
     * @param oldUrl 需要删除的文件地址
     * @return String 文件地址
     */
    public static String replaceFile(File file, String fileType, String oldUrl, String filePath) {
        //先删除原文件
        boolean flag = deleteFile(oldUrl);

        if (!flag) {
            //更改文件的过期时间，让他到期自动删除。
        }
        return uploadFileNewName(file, fileType, filePath);
    }
    
    public static String replaceFile(InputStream is, String fileType, String oldUrl, String filePath) {
        //先删除原文件
        boolean flag = deleteFile(oldUrl);

        if (!flag) {
            //更改文件的过期时间，让他到期自动删除。
        }
        return uploadFileNewName(is, fileType, filePath);
    }

    /**####################################更新文件end####################################**/

    /**
     * 
     * @MethodName: deleteFile
     * @Description: 单文件删除
     * @param fileUrl 需要删除的文件url
     * @return boolean 是否删除成功
     */
    public static boolean deleteFile(String fileUrl) {
        //先实例化ossConfig对象
        ossConfig = OSSConfig.getOssConfigInstance();
        //根据url获取bucketName
        String bucketName = OSSUploadUtil.getBucketName(fileUrl);

        //根据url获取fileName
        String fileName = OSSUploadUtil.getFileName(fileUrl);

        if (bucketName == null || fileName == null) {
            return false;
        }

        try {
            GenericRequest request = new DeleteObjectsRequest(bucketName).withKey(fileName);
            ossConfig.getOssClient().deleteObject(request);
        }
        catch (Exception oe) {
            oe.printStackTrace();
            logger.error("Failed to deleteFile String oss :" + oe.getMessage());
            return false;
        }
        finally {
            //        	 if(ossConfig !=null){
            //      		   ossConfig.ossClientShutDown();
            //      	   }
        }
        return true;
    }

    /**
     * 
     * @MethodName: batchDeleteFiles
     * @Description: 批量文件删除(较快)：适用于相同endPoint和BucketName
     * @param fileUrls 需要删除的文件url集合
     * @return int 成功删除的个数
     */
    public static int deleteFile(List<String> fileUrls) {
        //先实例化ossConfig对象
        ossConfig = OSSConfig.getOssConfigInstance();
        //成功删除的个数
        int deleteCount = 0;
        //根据url获取bucketName
        String bucketName = OSSUploadUtil.getBucketName(fileUrls.get(0));
        //根据url获取fileName
        List<String> fileNames = OSSUploadUtil.getFileName(fileUrls);
        if (bucketName == null || fileNames.size() <= 0) {
            return 0;
        }
        try {
            DeleteObjectsRequest request = new DeleteObjectsRequest(bucketName).withKeys(fileNames);
            DeleteObjectsResult result = ossConfig.getOssClient().deleteObjects(request);
            deleteCount = result.getDeletedObjects().size();
        }
        catch (OSSException oe) {
            oe.printStackTrace();
            logger.error("Failed to deleteFile List OSSException :" + oe.getMessage());
            throw new RuntimeException("OSS服务异常:", oe);
        }
        catch (ClientException ce) {
            ce.printStackTrace();
            logger.error("Failed to deleteFile List ClientException :" + ce.getMessage());
            throw new RuntimeException("OSS客户端异常:", ce);
        }
        finally {
            //        	 if(ossConfig !=null){
            //      		   ossConfig.ossClientShutDown();
            //      	   }
        }
        return deleteCount;

    }

    /**
     * 
     * @MethodName: batchDeleteFiles
     * @Description: 批量文件删除(较慢)：适用于不同endPoint和BucketName
     * @param fileUrls 需要删除的文件url集合
     * @return int 成功删除的个数
     */
    public static int deleteFiles(List<String> fileUrls) {
        int count = 0;
        for (String url : fileUrls) {
            if (deleteFile(url)) {
                count++;
            }
        }

        return count;
    }

    /**
     * 
     * @MethodName: putObject
     * @Description: 上传文件
     * @param file
     * @param fileType
     * @param fileName
     * @return String
     */
    private static String putObject(File file, String fileType, String fileName) {
        //先实例化ossConfig对象
        ossConfig = OSSConfig.getOssConfigInstance();
        //默认null
        String url = null;
        try {
            InputStream input = new FileInputStream(file);
            // 创建上传Object的Metadata
            ObjectMetadata meta = new ObjectMetadata();
            // 设置上传内容类型
            meta.setContentType(OSSUploadUtil.contentType(fileType));
            // 被下载时网页的缓存行为  
            meta.setCacheControl("no-cache");
            // 设置上传MD5校验
            String md5 = BinaryUtil.toBase64String(BinaryUtil.calculateMd5(ossConfig.getAccessKeySecret().getBytes()));
            //创建上传请求
            PutObjectRequest request = new PutObjectRequest(ossConfig.getBucketName(), fileName, input, meta);

            ossConfig.getOssClient().putObject(request);
            //设置Object权限
            ossConfig.getOssClient().setObjectAcl(ossConfig.getBucketName(), fileName,
                    CannedAccessControlList.PublicRead);

            //上传成功再返回的文件路径
            url = ossConfig.getEndPoint().replaceFirst("http://", "http://" + ossConfig.getBucketName() + ".") + "/"
                    + fileName;
        }
        catch (OSSException oe) {
            oe.printStackTrace();
            return null;
        }
        catch (ClientException ce) {
            ce.printStackTrace();
            return null;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return url;
    }
    
    private static String putObject(InputStream input, String fileType, String fileName) {
        //先实例化ossConfig对象
        ossConfig = OSSConfig.getOssConfigInstance();
        //默认null
        String url = null;
        try {
            //InputStream input = new FileInputStream(file);
            // 创建上传Object的Metadata
            ObjectMetadata meta = new ObjectMetadata();
            // 设置上传内容类型
            meta.setContentType(OSSUploadUtil.contentType(fileType));
            // 被下载时网页的缓存行为  
            meta.setCacheControl("no-cache");
            // 设置上传MD5校验
            String md5 = BinaryUtil.toBase64String(BinaryUtil.calculateMd5(ossConfig.getAccessKeySecret().getBytes()));
            //创建上传请求
            PutObjectRequest request = new PutObjectRequest(ossConfig.getBucketName(), fileName, input, meta);

            ossConfig.getOssClient().putObject(request);
            //设置Object权限
            ossConfig.getOssClient().setObjectAcl(ossConfig.getBucketName(), fileName,
                    CannedAccessControlList.PublicRead);

            //上传成功再返回的文件路径
            url = ossConfig.getEndPoint().replaceFirst("http://", "http://" + ossConfig.getBucketName() + ".") + "/"
                    + fileName;
        }
        catch (OSSException oe) {
            oe.printStackTrace();
            return null;
        }
        catch (ClientException ce) {
            ce.printStackTrace();
            return null;
        }
        
        return url;
    }

    /**
     * 
     * @MethodName: contentType
     * @Description: 获取文件类型
     * @param FileType
     * @return String
     */
    private static String contentType(String fileType) {
        fileType = fileType.toLowerCase();
        String contentType = "";

        if (fileType.equals("bmp")) {
            contentType = "image/bmp";
        }
        else if (fileType.equals("gif")) {
            contentType = "image/gif";
        }
        else if (fileType.equals("png")) {
            contentType = "image/png";
        }
        else if (fileType.equals("jpeg") || fileType.equals("jpg")) {
            contentType = "image/jpeg";
        }
        else if (fileType.equals("html")) {
            contentType = "text/html";
        }
        else if (fileType.equals("txt")) {
            contentType = "text/plain";
        }
        else if (fileType.equals("css")) {
            contentType = "text/css";
        }
        else if (fileType.equals("js")) {
            contentType = "text/javascript";
        }
        else if (fileType.equals("vsd")) {
            contentType = "application/vnd.visio";
        }
        else if (fileType.equals("ppt") || fileType.equals("pptx")) {
            contentType = "application/vnd.ms-powerpoint";
        }
        else if (fileType.equals("doc") || fileType.equals("docx")) {
            contentType = "application/msword";
        }
        else if (fileType.equals("xml")) {
            contentType = "application/xml";
        }
        else if (fileType.equals("mp3")) {
            contentType = "audio/mpeg";
        }
        else if (fileType.equals("mp4")) {
            contentType = "video/mp4";
        }
        else if (fileType.equals("flv")) {
            contentType = "video/flv";
        }
        else if (fileType.equals("zip")) {
            contentType = "application/x-zip-compressed";
        }
        else {
            contentType = "application/octet-stream";
        }

        return contentType;
    }

    /**
     * 
     * @MethodName: getBucketName
     * @Description: 根据url获取bucketName
     * @param fileUrl 文件url
     * @return String bucketName
     */
    private static String getBucketName(String fileUrl) {
        String http = "http://";
        String https = "https://";
        int httpIndex = fileUrl.indexOf(http);
        int httpsIndex = fileUrl.indexOf(https);
        int startIndex = 0;
        if (httpIndex == -1) {
            if (httpsIndex == -1) {
                return null;
            }
            else {
                startIndex = httpsIndex + https.length();
            }
        }
        else {
            startIndex = httpIndex + http.length();
        }
        int endIndex = fileUrl.indexOf(".oss-");
        return fileUrl.substring(startIndex, endIndex);
    }

    /** 
     * 删除Bucket  
     * @param bucketName bucket名称 
     *
     * */
    public static final void deleteBucket(String bucketName) {
        //先实例化ossConfig对象
        ossConfig = OSSConfig.getOssConfigInstance();

        try {
            ossConfig.getOssClient().deleteBucket(bucketName);
        }
        finally {
            logger.error("delete Bucket " + bucketName + "success");
            ossConfig.ossClientShutDown();
        }

    }

    /**
     * 
     * @MethodName:  getFileName
     * @Description: 根据url获取fileName
     * @param fileUrl 文件url
     * @return String fileName
     */
    private static String getFileName(String fileUrl) {
        String str = "aliyuncs.com/";
        int beginIndex = fileUrl.indexOf(str);
        if (beginIndex == -1) {
            return null;
        }

        return fileUrl.substring(beginIndex + str.length());
    }

    /**
     * 
     * @MethodName: getFileName
     * @Description: 根据url获取fileNames集合
     * @param fileUrl 文件url
     * @return List<String>  fileName集合
     */
    private static List<String> getFileName(List<String> fileUrls) {
        List<String> names = new ArrayList<String>();
        for (String url : fileUrls) {
            names.add(getFileName(url));
        }
        return names;
    }

    /**
     * 
     * @MethodName: getEndPoint
     * @Description: 根据上传地址EndPoint
     */
    public static String getEndPoint() {

        return ossConfig.getEndPoint();
    }
}
