package com.adks.dubbo.service.web.grade;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.commons.util.RedisConstant;
import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.course.Adks_course_user;
import com.adks.dubbo.api.data.grade.Adks_grade_course;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.web.grade.GradeCourseWebDao;
import com.adks.dubbo.util.CourseRedisUtil;
import com.alibaba.fastjson.JSONObject;

@Service
public class GradeCourseWebService extends BaseService<GradeCourseWebDao> {

    @Autowired
    private GradeCourseWebDao gradeCourseDao;

    @Override
    protected GradeCourseWebDao getDao() {
        return gradeCourseDao;
    }

    //获取专题班级课程集合
    public List<Adks_grade_course> getGradeCourseByGradeId(Map map) {
        List<Adks_grade_course> list = null;
        Integer gradeId=MapUtils.getInteger(map, "gradeId");
        String result=CourseRedisUtil.getGradeCourse(CourseRedisUtil.adks_course_register_grade_course, gradeId);
        if("".equals(result) || "Nodata".equals(result)){
        	list = gradeCourseDao.getGradeCourseByGradeId(map);
        	CourseRedisUtil.addGradeCourse(CourseRedisUtil.adks_course_register_grade_course, gradeId, list);
        }else{
        	list=JSONObject.parseArray(result, Adks_grade_course.class);
        }
        return list;
    }

    //得到班级课程
    public Page<List<Adks_course_user>> getAllGradeCourseList(Page<List<Adks_course_user>> page) {
        return gradeCourseDao.getAllGradeCourseList(page);
    }

    public Page<List<Adks_course_user>> gradeCourseUserList(Page<List<Adks_course_user>> page) {
        return gradeCourseDao.gradeCourseUserList(page);
    }

    public Page<List<Adks_course_user>> gradeCourseListNoStudy(Page<List<Adks_course_user>> page) {
        return gradeCourseDao.gradeCourseListNoStudy(page);
    }
    
    public Adks_grade_course getGradeCourseByCourseAndGradeId(Map<String, Object> map){
    	return gradeCourseDao.getGradeCourseByCourseAndGradeId(map);
    }
}
