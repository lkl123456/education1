package com.adks.dubbo.service.app.course;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.course.Adks_course_sort;
import com.adks.dubbo.dao.app.course.CourseSortAppDao;

@Service
public class CourseSortAppService extends BaseService<CourseSortAppDao> {

	@Autowired
	private CourseSortAppDao courseSortDao;

	@Override
	protected CourseSortAppDao getDao() {
		return courseSortDao;
	}

	/**
	 * 获取分类列表 0为等级目录
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Map<String, Object>> getCourseSortByParent(int parentId) {
		return courseSortDao.getCourseSortByParent(parentId);
	}

	/**
	 * 根据id获取分类信息
	 * 
	 * @param id
	 * @return
	 */
	public Adks_course_sort getCourseSortById(int id, int orgId) {
		return courseSortDao.getCourseSortById(id, orgId);
	}
}
