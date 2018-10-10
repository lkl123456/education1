package com.adks.dubbo.service.web.course;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.commons.util.RedisConstant;
import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.api.data.course.Adks_course_sort;
import com.adks.dubbo.dao.web.course.CourseSortWebDao;
import com.adks.dubbo.util.CourseRedisUtil;
import com.alibaba.fastjson.JSONObject;

@Service
public class CourseSortWebService extends BaseService<CourseSortWebDao> {

    @Autowired
    private CourseSortWebDao courseDao;

    @Override
    protected CourseSortWebDao getDao() {
        return courseDao;
    }

    public List<Adks_course_sort> getAllChildeCourseSort(Map map) {
        return courseDao.getAllChildeCourseSort(map);
    }

    public List<Adks_course_sort> getCourseSortTreeDate(Map map) {
        List<Adks_course_sort> list = null;
        String result=CourseRedisUtil.getCourseSort(CourseRedisUtil.adks_course_sort_tree);
		if("".equals(result) || "Nodata".equals(result)){
			list = courseDao.getAllChildeCourseSort(map);
			CourseRedisUtil.addCourseSort(CourseRedisUtil.adks_course_sort_tree, list);
		}else{
			list=JSONObject.parseArray(result, Adks_course_sort.class);
		}
        return list;
    }

}
