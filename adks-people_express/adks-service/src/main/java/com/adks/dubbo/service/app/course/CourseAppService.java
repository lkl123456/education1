package com.adks.dubbo.service.app.course;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.commons.util.RedisConstant;
import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.app.course.CourseAppDao;
import com.adks.dubbo.util.CourseRedisUtil;
import com.alibaba.fastjson.JSONObject;

@Service
public class CourseAppService extends BaseService<CourseAppDao> {

	@Autowired
	private CourseAppDao courseDao;

	@Override
	protected CourseAppDao getDao() {
		return courseDao;
	}

	/**
	 * 课程列表(激活、审核通过)
	 * 
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getCourseListPage(Page<List<Map<String, Object>>> page) {
		return courseDao.getCourseListPage(page);
	}

	/**
	 * 根据id获取课程
	 * 
	 * @param authorId
	 * @return
	 */
	public Map<String, Object> getCourseById(int id) {
		Map<String, Object> map = null;
		map = courseDao.getCourseById(id);
		return map;
	}

	public Page<List<Map<String, Object>>> getCourseListPageByCourseName(Page<List<Map<String, Object>>> page) {
		return courseDao.getCourseListPageByCourseName(page);
	}

	/**
	 * 
	 * @Title getVideoServer
	 * @Description
	 * @author xrl
	 * @Date 2017年5月13日
	 * @return
	 */
	public String getVideoServer() {
		return courseDao.getVideoServer();
	}

	/**
	 * 根据讲师id获取课程列表
	 * 
	 * @param authorId
	 * @return
	 */
	public List<Map<String, Object>> getCourseListByAuthorId(int authorId) {
		return courseDao.getCourseListByAuthorId(authorId);
	}
}
