package com.adks.dubbo.service.web.grade;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.commons.util.RedisConstant;
import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.api.data.grade.Adks_grade_user;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.web.grade.GradeUserWebDao;
import com.adks.dubbo.util.CourseRedisUtil;
import com.adks.dubbo.util.UserRedisUtil;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * ClassName GradeUserService
 * @Description
 * @author xrl
 * @Date 2017年3月30日
 */
@Service
public class GradeUserWebService extends BaseService<GradeUserWebDao> {

    @Autowired
    private GradeUserWebDao gradeUserDao;

    @Override
    protected GradeUserWebDao getDao() {
        return gradeUserDao;
    }

    public Page<List<Map<String, Object>>> getGradeUserListPage(Page<List<Map<String, Object>>> page) {
        Page<List<Map<String, Object>>> tempPage = gradeUserDao.getGradeUserListPage(page);
        //对结果拼接去user表获取用户信息
        return tempPage;
    }

    /**
     * 
     * @Title getGradeHeadTeacherPage
     * @Description：已添加到该班级的班主任
     * @author xrl
     * @Date 2017年3月30日
     * @param page
     * @return
     */
    public Page<List<Map<String, Object>>> getGradeHeadTeacherPage(Page<List<Map<String, Object>>> page) {
        Page<List<Map<String, Object>>> tempPage = gradeUserDao.getGradeHeadTeacherPage(page);
        return tempPage;
    }

    /**
     * 
     * @Title getHeadTeacherIdByGradeId
     * @Description：获取班级已添加的班主任的userId
     * @author xrl
     * @Date 2017年3月30日
     * @param gradeId
     * @return
     */
    public Map<String, Object> getHeadTeacherIdByGradeId(Integer gradeId) {
        return gradeUserDao.getHeadTeacherIdByGradeId(gradeId);
    }

    /**
     * 
     * @Title updateGradeHeadTeacher
     * @Description：添加班级班主任
     * @author xrl
     * @Date 2017年3月30日
     * @param map
     */
    public void updateGradeHeadTeacher(Map<String, Object> map) {
        gradeUserDao.updateGradeHeadTeacher(map);
    }

    /**
     * 
     * @Title getGradeUserByUserId
     * @Description:根据userId查询班级用户信息
     * @author xrl
     * @Date 2017年3月30日
     * @param gradeId
     * @return
     */
    public Map<String, Object> getGradeUserByUserId(Integer gradeId) {
        return gradeUserDao.getGradeUserByUserId(gradeId);
    }

    public List<Adks_grade_user> getTopCourseStudyTimeUserList(Map map) {
        List<Adks_grade_user> list = null;
        if(map.get("orgCode")!=null && !"".equals(map.get("orgCode"))){
        	String orgCode=MapUtils.getString(map, "orgCode");
    		String result=CourseRedisUtil.getCourse(UserRedisUtil.adks_user_sort, orgCode, false);
    		if("".equals(result) || "Nodata".equals(result)){
    			list = gradeUserDao.getTopCourseStudyTimeUserList(map);
    			UserRedisUtil.addUserSort(UserRedisUtil.adks_user_sort, orgCode, list);
    		}else{
    			list=JSONObject.parseArray(result, Adks_grade_user.class);
    		}
        }
        return list;
    }
    //学员已获得分数，学时，论文，考试，讨论分数
    public Adks_grade_user getUserStudysFromGradeUser(Integer userId, Integer gradeId) {
		return gradeUserDao.getUserStudysFromGradeUser(userId,gradeId);
	}
    
    public String getUserTotleXS(Integer id) {
		return gradeUserDao.getUserTotleXS(id);
	}
    
    public Adks_grade_user getGradeUserByCon(Integer gradeId, Integer userId) {
		return gradeUserDao.getGradeUserByCon(gradeId,userId);
	}
    
    public void updateGradeUserForCredit(Map<String, Object> map){
    	gradeUserDao.updateGradeUserForCredit(map);
    }
}
