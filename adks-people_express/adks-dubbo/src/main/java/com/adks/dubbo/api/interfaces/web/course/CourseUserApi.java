package com.adks.dubbo.api.interfaces.web.course;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.course.Adks_course_user;
import com.adks.dubbo.api.data.course.CourseJson;
import com.adks.dubbo.commons.Page;

public interface CourseUserApi {

    /**
     * 得到首页的正在看课程
     * @param map
     * @return
     */
    public List<Adks_course_user> getTopCourseUserList(Map map);
    
    /**
     * 获得用户学习课程时间
     * @param userId
     * @return
     */
    public String getStudyCourseTime(Integer userId,String username);

    /**
     * 获取课程超市学习分页记录
     * @param page
     * @return
     */
    public Page<List<Adks_course_user>> getCourseUserByUserId(Page<List<Adks_course_user>> page);
    
    /**
     * 课程超市浏览记录
     * @param page
     * @return
     */
    public Page<List<Adks_course_user>> getUserCourseViewByUserId(Page<List<Adks_course_user>> page);
    
    /**
     * 得到班级课程用户的学习记录
     * @param map
     * @return
     */
    public Adks_course_user getCourseUserByGradeIdCourseIdAndUserIdforCourseJD(Map map);
    
    /**
     * 得到班级课程的学习进度（档案）
     * @param map
     * @return
     */
    public List<Adks_course_user> getCourseUserByCon(Map map);
    
    /**
     * 批量更新-用户学习进度
     * @param courseJsonMap 用户记录对象Map
     */
    public void updateUserStudySpeed(Map<String, CourseJson> courseJsonMap);
    
    /**
     * 
     * @Title getCourseUserByCourseIdAndUserId
     * @Description：根据userId和courseId获取学习记录
     * @author xrl
     * @Date 2017年6月3日
     * @param map
     * @return
     */
    public Adks_course_user getCourseUserByCourseIdAndUserId(Map<String, Object> map);
    
    /**
     * 
     * @Title updateCourseUserForGrades
     * @Description
     * @author xrl
     * @Date 2017年6月3日
     * @param map
     */
    public void updateCourseUserForGrades(Map<String, Object> map);
    
}
