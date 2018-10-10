package com.adks.commons.test.freemarker;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Test;

import com.adks.commons.ftp.FtpApacheUtil;
import com.adks.commons.util.PropertiesFactory;

public class FtpApacheTest {
	
	@Test
	public void testFtpApache() throws Exception{
		
		System.out.println("****"+PropertiesFactory.getProperty("config.properties", "ftp.video.path"));
		FtpApacheUtil ftpApache = new FtpApacheUtil();
		
//		File file = new File("E:/test.mp4");
//		FileInputStream fileInputStream = new FileInputStream(file);
		boolean res = ftpApache.uploadFile(PropertiesFactory.getProperty("config.properties", "ftp.video.path"), "333333.mp4", "E:/test.mp4");
		
	}
}
