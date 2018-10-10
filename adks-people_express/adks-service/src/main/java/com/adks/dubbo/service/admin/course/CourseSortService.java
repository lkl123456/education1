package com.adks.dubbo.service.admin.course;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.course.Adks_course_sort;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.admin.course.CourseSortDao;
import com.adks.dubbo.util.CourseRedisUtil;

@Service
public class CourseSortService extends BaseService<CourseSortDao> {

    @Autowired
    private CourseSortDao courseSortDao;

    @Override
    protected CourseSortDao getDao() {
        return courseSortDao;
    }

    public List<Adks_course_sort> getCourseSortByParent(Integer parentId) {
        return courseSortDao.getCourseSortByParent(parentId);
    }

    public Page<List<Map<String, Object>>> getCourseSortListPage(Page<List<Map<String, Object>>> page) {
        return courseSortDao.getCourseSortListPage(page);
    }

    public void deleteCourseSort(String courseSortCode) {
    	CourseRedisUtil.emptyCourseAll("sort_tree");
        courseSortDao.deleteCourseSort(courseSortCode);
    }

    public Adks_course_sort getCourseSortById(Integer courseSortId) {
        return courseSortDao.getCourseSortById(courseSortId);
    }

    public Adks_course_sort getCourseSortByName(String courseSortName) {
        return courseSortDao.getCourseSortByName(courseSortName);
    }

    public void updateNameUnifed(Integer courseSortId, String courseSortName) {
        courseSortDao.updateNameUnifed(courseSortId, courseSortName);
    }

    public List<Adks_course_sort> getCourseSortsListAll(Map map) {
        return courseSortDao.getCourseSortsListAll(map);
    }
    //得到父id为0的课程分类
  	public List<Adks_course_sort> getCourseSortParentIsZero(){
  		return courseSortDao.getCourseSortParentIsZero();
  	}
  	
  	public void deleteCourseSortRedis(){
  		CourseRedisUtil.emptyCourseAll("sort_tree");
  	}

}
