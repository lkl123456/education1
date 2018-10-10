package com.adks.dubbo.api.interfaces.web.course;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.api.data.course.Adks_course_sort;
import com.adks.dubbo.commons.Page;

public interface CourseApi {

    public List<Adks_course> getCourseListByOrgCode(String orgCode);

    public Adks_course getCourseById(Integer courseId);

    public Adks_course getCourseByName(String courseName);

    //得到首页的课程list
    public List<Adks_course> getTopNewCourseList(Map map);

    public List<Adks_course> getTopHotCourseList(Map map);

    //根据id得到课程
    public List<Adks_course> getCourseListByIds(Map map);

    /**
     * 根据课程分类id查询所有子分类不包括父节点本身
     */
    public List<Adks_course_sort> getAllChildeCourseSort(Map map);

    /**
     * 前台分页信息
     */
    public Page<List<Adks_course>> getCoursesListWeb(Page<List<Adks_course>> page);

    /**
     * 得到所有的课程分类信息
     */
    public List<Adks_course_sort> getCourseSortTreeDate(Map map);

    /*
     * 课程详情页里的 系列课程 
     * */
    public List<Adks_course> getCourseListByLikedName(Map map);

    //获取讲师课程，限制个数，课程详情页使用
    public List<Adks_course> getCourseListByAuthorAndLimitNum(Integer author,Map map);

    public void initData();
    
    //得到课程播放的初始值
    public Integer getCourseUserLocation(Integer courseId,Integer gradeId,Integer userId);
    //得到课程播放的初始值,课程超市
    public Integer getCourseSuperUserLocation(Integer courseId,Integer userId);
    
    public String getVideoServer(Integer courseId);
    //更新课程的点击量
    public void updateCourseClickNum(Integer courseId);
    
    //得到课程的码流
    public String getCourseVideoRP(Integer courseId);
    //修改课程的收藏量
    public void updateCourseChooseNum( Integer courseId,String type);
}
