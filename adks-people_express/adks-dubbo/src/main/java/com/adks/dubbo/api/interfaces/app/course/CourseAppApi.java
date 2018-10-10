package com.adks.dubbo.api.interfaces.app.course;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.commons.Page;

public interface CourseAppApi {

	/**
	 * 课程列表(激活、审核通过)
	 * 
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getCourseListPage(Page<List<Map<String, Object>>> page);

	public Map<String, Object> getById(int id);

	public Page<List<Map<String, Object>>> getCourseListPageByCourseName(Page<List<Map<String, Object>>> page);

	/**
	 * 
	 * @Title getVideoServer
	 * @Description:获取播放地址
	 * @author xrl
	 * @Date 2017年5月13日
	 * @return
	 */
	public String getVideoServer();

	/**
	 * 根据讲师id获取课程列表
	 * 
	 * @param authorId
	 * @return
	 */
	public List<Map<String, Object>> getCourseListByAuthorId(int authorId);
}
