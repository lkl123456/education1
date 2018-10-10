package com.adks.dubbo.api.interfaces.app.grade;

import java.util.Map;

public interface GradeAppApi {

	/**
	 * 根据gradeId获取班级信息
	 * 
	 * @param gradeId
	 * @return
	 */
	public Map<String, Object> getById(int id);

}
