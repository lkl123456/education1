package com.baidu.ueditor.upload;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.adks.commons.oss.OSSUploadUtil;
import com.adks.commons.util.PropertiesFactory;
import com.baidu.ueditor.define.State;
import com.qikemi.packages.alibaba.aliyun.oss.properties.OSSClientProperties;
import com.qikemi.packages.utils.SystemUtil;

/**
 * 同步上传文件到阿里云OSS<br>
 * 
 * @author XieXianbin me@xiexianbin.cn
 */
public class Uploader {
    private static Logger logger = Logger.getLogger(Uploader.class);

    private HttpServletRequest request = null;

    private Map<String, Object> conf = null;

    public Uploader(HttpServletRequest request, Map<String, Object> conf) {
        this.request = request;
        this.conf = conf;
    }

    public final State doExec() {
        String filedName = (String) this.conf.get("fieldName");
        State state = null;

        if ("true".equals(this.conf.get("isBase64"))) {
            state = Base64Uploader.save(this.request.getParameter(filedName), this.conf);
        }
        else {
            state = BinaryUploader.save(this.request, this.conf);
            JSONObject stateJson = new JSONObject(state.toJSONString());
            // 判别云同步方式
            if (OSSClientProperties.useStatus) {
                //获取当前上传图片的地址
                String newsImgPath = PropertiesFactory.getProperty("ossConfig.properties", "oss.NewsImg_Path");
                //根据上传的图片地址读取图片信息
                File file = new File(this.conf.get("rootPath") + stateJson.getString("url"));
                //获取当前上传文件的格式
                String fileType = stateJson.getString("type").substring(1, stateJson.getString("type").length());
                //上传的阿里云并返回阿里云地址
                String urlPath = OSSUploadUtil.uploadFileNewName(file, fileType, newsImgPath);
                //                url = url.replace(url, OSSUploadUtil.getEndPoint());
                String[] paths = urlPath.split("/");
                String code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
                state.putInfo("url", code);
            }
            else {
                state.putInfo("url", SystemUtil.getProjectName() + stateJson.getString("url"));
            }
        }
        /*
         * { "state": "SUCCESS", "title": "1415236747300087471.jpg", "original":
         * "a.jpg", "type": ".jpg", "url":
         * "/upload/image/20141106/1415236747300087471.jpg", "size": "18827" }
         */
        logger.debug(state.toJSONString());
        return state;
    }
}
