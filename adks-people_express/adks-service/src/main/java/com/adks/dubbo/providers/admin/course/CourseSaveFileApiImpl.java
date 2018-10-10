package com.adks.dubbo.providers.admin.course;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

import com.adks.commons.oss.OSSUploadUtil;
import com.adks.commons.util.PropertiesFactory;
import com.adks.commons.zip.ZipToFile;
import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.api.interfaces.admin.course.CourseSaveFileApi;

public class CourseSaveFileApiImpl implements CourseSaveFileApi {

	 private static AtomicInteger count = new AtomicInteger(0);
	 
	@Override
	//三分屏解压上传阿里云
	public String zipFileToAliyun(String courseCode,Adks_course course,String courseWareFileFileName){
		System.out.println("++++++++++++++++++++++++++++++++++??????????????????count:"+count.get());
		count.getAndIncrement();
		String zip_Path = PropertiesFactory.getProperty("config.properties","base.path.course.zip");
		System.out.println("========================zipFileToAliyun zip_Path="+zip_Path);
		String unzip_Path = PropertiesFactory.getProperty("config.properties","base.path.course.unzip");
		System.out.println("========================zipFileToAliyun unzip_Path="+unzip_Path);
		String dirzip_Path = PropertiesFactory.getProperty("config.properties","base.path.dir.zip");
		System.out.println("========================zipFileToAliyun dirzip_Path="+dirzip_Path);
		String dirunzip_Path = PropertiesFactory.getProperty("config.properties","base.path.dir.unzip");
		System.out.println("========================zipFileToAliyun dirunzip_Path="+dirunzip_Path);
		String scormPath = PropertiesFactory.getProperty("ossConfig.properties","oss.Scorm_Path");
		System.out.println("========================zipFileToAliyun scormPath="+scormPath);
		boolean zipPath = ZipToFile.zipToFile(courseCode.trim(), zip_Path.trim(),unzip_Path.trim());
		System.out.println("========================zipFileToAliyun zipPath="+zipPath);
		String code="";
		if (zipPath) {
			// 上传到阿里云OSS
			if (courseWareFileFileName != null && !"".equals(courseWareFileFileName)) {
				if (course.getCourseCode() == null || "".equals(course.getCourseCode())) { // 第一次添加
					File file = new File(dirunzip_Path + courseCode);
					if (file.exists()) {
						String path_new = OSSUploadUtil.uploadDirIndex(dirunzip_Path + courseCode, scormPath);
						//File f = new File(dirunzip_Path + courseCode);
						File fi = new File(dirzip_Path + courseCode + ".zip");
						// boolean uzipDir=new
						// DeleteDir().deleteDir(f);
						boolean zip = false;
						if (fi.exists()) {
							zip = fi.delete();
						}
						if (path_new != null && !"".equals(path_new)) {
							code=courseCode;
						}
					} else {
						System.out.println("==============Zip File does not exist");
					}

				} else if (course.getCourseCode() != null
						&& !"".equals(course.getCourseCode())) { // 更改
					File file = new File(dirunzip_Path + courseCode);
					if (file.exists()) {
						String path_update = OSSUploadUtil.uploadDirIndex(dirunzip_Path + courseCode, scormPath);
						File fi = new File(dirzip_Path + courseCode + ".zip");
						boolean zip = false;
						if (fi.exists()) {
							zip = fi.delete();
						}
						if (path_update != null && !"".equals(path_update)) {
							code=courseCode;
						}
					} else {
						System.out.println("==============Zip File does not exist");
					}
				}
			}
		} else {
			code="";
		}
		return courseCode;
	}


}
