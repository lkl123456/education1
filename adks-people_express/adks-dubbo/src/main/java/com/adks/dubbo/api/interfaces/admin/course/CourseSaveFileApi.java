package com.adks.dubbo.api.interfaces.admin.course;

import com.adks.dubbo.api.data.course.Adks_course;

public interface CourseSaveFileApi {
	//三分屏解压上传阿里云
	public String zipFileToAliyun(String courseCode,Adks_course course,String courseWareFileFileName);
}
