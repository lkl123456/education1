package com.adks.app.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

/*
 * @author:lxh
 * @see:该类用于加载 系统运行时配置信息 主要是 
 * @date:Aug 30, 2012 
 */
public class BaseFrameworkConfig {
	
	protected final  Log logger = LogFactory.getLog(BaseFrameworkConfig.class);
	
	private Resource location;

	/**
	 * @see 得到系统运行时配置信息，对应的是/WEB-INF/config/systemGlobals.properties文件
	 * @return 返回systemGlobals.properties文件的Properties对象
	 */
	public Properties getConfig() {
		Properties props = null;

		try {
        	props = new Properties();
        	//logger.info("loading properties file :" + location.getFilename());
        	InputStream is = new FileInputStream(location.getFile());;
        	//new FileInputStream(location.getFile());;
			props.load( is );
			is.close();
		} catch (IOException e) {
		    e.printStackTrace();
			logger.info("Unable to load " +  location.getFilename(), e);
			return null;
		}
		return props;
	}

	public Resource getLocation() {
		return location;
	}

	public void setLocation(Resource location) {
		this.location = location;
	}
}
