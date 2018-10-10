package com.adks.dubbo.providers.web.course;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.api.data.course.Adks_course_sort;
import com.adks.dubbo.api.interfaces.web.course.CourseApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.web.course.CourseSortWebService;
import com.adks.dubbo.service.web.course.CourseWebService;
import com.adks.dubbo.service.web.search.SearchService;

public class CourseApiImpl implements CourseApi {
    @Autowired
    private SearchService searchService;

    @Autowired
    private CourseWebService courseService;

    @Autowired
    private CourseSortWebService courseSortWebService;

    //目前没有用到该方法
    @Override
    public List<Adks_course> getCourseListByOrgCode(String orgCode) {
        return courseService.getCourseListByOrgCode(orgCode);
    }

    //flag 1增加2减少
    public void updateCourseSortCourseNum(Integer courseSortId, Integer flag) {
        courseService.updateCourseSortCourseNum(courseSortId, flag);
    }

    public Adks_course getCourseById(Integer courseId) {
        return courseService.getCourseById(courseId);
    }

    //方法没有用到
    public Adks_course getCourseByName(String courseName) {
        return courseService.getCourseByName(courseName);
    }

    //得到首页最新课程
    public List<Adks_course> getTopNewCourseList(Map map) {
        return courseService.getTopNewCourseList(map);
    }
    //得到排行
    public List<Adks_course> getTopHotCourseList(Map map) {
        return courseService.getTopHotCourseList(map);
    }
    //专题班级的课程
    @Override
    public List<Adks_course> getCourseListByIds(Map map) {
        return courseService.getCourseListByIds(map);
    }
    //该方法目前没有使用到
    @Override
    public List<Adks_course_sort> getAllChildeCourseSort(Map map) {
        return courseSortWebService.getAllChildeCourseSort(map);
    }
    //课程分页
    @Override
    public Page<List<Adks_course>> getCoursesListWeb(Page<List<Adks_course>> page) {
        Page<List<Adks_course>> result = null;
        //        if (null != searchService.queryCourse(page)) {
        //            result = searchService.queryCourse(page);
        //        }
        //        else {
        result = courseService.getCoursesListWeb(page);
        //        }
        return result;
    }
    //得到所有的课程分类
    @Override
    public List<Adks_course_sort> getCourseSortTreeDate(Map map) {
        return courseSortWebService.getCourseSortTreeDate(map);
    }
    //课程一系列的课程
    @Override
    public List<Adks_course> getCourseListByLikedName(Map map) {
        return courseService.getCourseListByLikedName(map);
    }
    //讲师主讲课程
    @Override
    public List<Adks_course> getCourseListByAuthorAndLimitNum(Integer author,Map map) {
        return courseService.getCourseListByAuthorAndLimitNum(author,map);
    }

    @Override
    public void initData() {
        courseService.initData();
    }

    @Override
    public Integer getCourseUserLocation(Integer courseId, Integer gradeId, Integer userId) {
        return courseService.getCourseUserLocation(courseId, gradeId, userId);
    }

    @Override
    public Integer getCourseSuperUserLocation(Integer courseId, Integer userId) {
        return courseService.getCourseSuperUserLocation(courseId, userId);
    }

    public String getVideoServer(Integer courseId) {
        return courseService.getVideoServer(courseId);
    }

    public void updateCourseClickNum(Integer courseId) {
        courseService.updateCourseClickNum(courseId);
    }

    public String getCourseVideoRP(Integer courseId) {
        return courseService.getCourseVideoRP(courseId);
    }

    public void updateCourseChooseNum(Integer courseId, String type) {
        courseService.updateCourseChooseNum(courseId, type);
    }
}
