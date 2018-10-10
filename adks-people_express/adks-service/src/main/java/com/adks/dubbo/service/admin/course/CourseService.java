package com.adks.dubbo.service.admin.course;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.commons.util.RedisConstant;
import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.admin.course.CourseDao;
import com.adks.dubbo.util.CourseRedisUtil;
import com.alibaba.fastjson.JSONObject;

@Service
public class CourseService extends BaseService<CourseDao> {

    @Autowired
    private CourseDao courseDao;

    @Override
    protected CourseDao getDao() {
        return courseDao;
    }

    public List<Adks_course> getCourseListByOrgCode(String orgCode) {
        return courseDao.getCourseListByOrgCode(orgCode);
    }

    public boolean courseBycourseSort(Integer courseSortId) {
        return courseDao.courseBycourseSort(courseSortId);
    }

    public void deleteCourse(String courseIds,boolean isBelong) {
        courseDao.deleteCourse(courseIds);
        //清除所有的最新课程缓存
        //if(isBelong){
        	CourseRedisUtil.emptyCourseAll("new,rank,about,author_course");
        //}
    }

    //清除缓存
    public void  deleteRedis(String types){
    	CourseRedisUtil.emptyCourseAll(types);
    }
    
    public Page<List<Map<String, Object>>> getCourseListPage(Page<List<Map<String, Object>>> page) {
        return courseDao.getCourseListPage(page);
    }

    public void courseUpdateData(Integer courseId, Integer flag, String result) {
        courseDao.courseUpdateData(courseId, flag, result);
    }

    public Adks_course getCourseById(Integer courseId) {
        return courseDao.getCourseById(courseId);
    }

    public Adks_course getCourseByName(String courseName) {
        return courseDao.getCourseByName(courseName);
    }

    public void updateCourseSortCourseNum(Integer courseSortId, Integer flag) {
        courseDao.updateCourseSortCourseNum(courseSortId, flag);
    }

    public void checkCourseNametoTabs(Integer courseId, String courseName) {
    	//除了课程分类其余的全要清除
    	CourseRedisUtil.emptyCourseAll("new,rank,register_grade_course,gradeCourse,about,author_course");
        courseDao.checkCourseNametoTabs(courseId, courseName);
    }

    public boolean canDelCourse(Integer courseId) {
        return courseDao.canDelCourse(courseId);
    }
    
    /**
     * 
     * @Title getCourseStatisticsListPage
     * @Description：获取课程统计分页列表
     * @author xrl
     * @Date 2017年6月19日
     * @param page
     * @return
     */
    public Page<List<Map<String, Object>>> getCourseStatisticsListPage(Page<List<Map<String, Object>>> page){
    	return courseDao.getCourseStatisticsListPage(page);
    }
    
    /**
     * 
     * @Title getCourseStatisticsList
     * @Description
     * @author xrl
     * @Date 2017年6月19日
     * @param map
     * @return
     */
    public List<Adks_course> getCourseStatisticsList(Map<String, Object> map){
    	return courseDao.getCourseStatisticsList(map);
    }
}
