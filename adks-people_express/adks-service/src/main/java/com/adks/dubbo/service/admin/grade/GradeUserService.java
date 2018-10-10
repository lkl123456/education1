package com.adks.dubbo.service.admin.grade;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.commons.util.RedisConstant;
import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.grade.Adks_grade_user;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.admin.grade.GradeUserDao;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * ClassName GradeUserService
 * @Description
 * @author xrl
 * @Date 2017年3月30日
 */
@Service
public class GradeUserService extends BaseService<GradeUserDao> {

    @Autowired
    private GradeUserDao gradeUserDao;

    @Override
    protected GradeUserDao getDao() {
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
    public Map<String, Object> getGradeUserByUserId(Map<String, Object> map) {
        return gradeUserDao.getGradeUserByUserId(map);
    }
    
    /**
     * 
     * @Title delGradeUserByGradeId
     * @Description：删除班级时，根据班级ID删除班级用户
     * @author xrl
     * @Date 2017年5月5日
     * @param gradeId
     */
    public void delGradeUserByGradeId(Integer gradeId){
    	gradeUserDao.delGradeUserByGradeId(gradeId);
    }
    
    /**
     * 
     * @Title removeGradeUsers
     * @Description:删除班级用户
     * @author xrl
     * @Date 2017年5月5日
     * @param map
     */
    public void removeGradeUsers(Map<String, Object> map){
    	gradeUserDao.removeGradeUsers(map);
    }
    
    /**
     * 
     * @Title gradeUserService
     * @Description：获取班级学员列表
     * @author xrl
     * @Date 2017年5月19日
     * @param gradeId
     * @return
     */
    public List<Map<String, Object>> getGradeUserList(Integer gradeId){
    	return gradeUserDao.getGradeUserList(gradeId);
    }
    
    /**
     * 
     * @Title updateAllGradeUserGraduate
     * @Description:更新班级用户结业状态
     * @author xrl
     * @Date 2017年5月20日
     * @param gradeId
     * @param userId
     */
    public void updateAllGradeUserGraduate(Integer gradeId, Integer userId){
    	gradeUserDao.updateAllGradeUserGraduate(gradeId,userId);
    }
    
    public void updateGradeUserCredit(Map<String, Object> map){
    	gradeUserDao.updateGradeUserCredit(map);
    }
    
    /**
     * 
     * @Title getGradeStudyListPage
     * @Description：获取班级学习统计列表
     * @author xrl
     * @Date 2017年6月14日
     * @param map
     * @return
     */
    public Page<List<Map<String, Object>>> getGradeStudyListPage(Page<List<Map<String, Object>>> page){
    	return gradeUserDao.getGradeStudyListPage(page);
    }
    
    /**
     * 
     * @Title getGradeUserByGradeUserId
     * @Description：根据gradeUserId获取班级用户信息
     * @author xrl
     * @Date 2017年6月15日
     * @param gradeUserId
     * @return
     */
    public Map<String, Object> getGradeUserByGradeUserId(Integer gradeUserId){
    	return gradeUserDao.getGradeUserByGradeUserId(gradeUserId);
    }
    
    /**
     * 
     * @Title getGradeUserByUserIdAndGradeId
     * @Description:根据userId和gradeId获取班级学员信息
     * @author xrl
     * @Date 2017年6月15日
     * @param map
     * @return
     */
    public Map<String, Object> getGradeUserByUserIdAndGradeId(Map<String, Object> map){
    	return gradeUserDao.getGradeUserByUserIdAndGradeId(map);
    }
    
    /**
     * 
     * @Title addGradeUserCredit
     * @Description：更新班级用户必修或选修学时
     * @author xrl
     * @Date 2017年6月15日
     * @param map
     */
    public void addGradeUserCredit(Map<String, Object> map){
    	gradeUserDao.addGradeUserCredit(map);
    }
    
    /**
     * 
     * @Title getGradePerformanceListPage
     * @Description:班级学员成绩分页列表
     * @author xrl
     * @Date 2017年6月16日
     * @param page
     * @return
     */
    public Page<List<Map<String, Object>>> getGradePerformanceListPage(Page<List<Map<String, Object>>> page){
    	return gradeUserDao.getGradePerformanceListPage(page);
    }
    
    /**
     * 
     * @Title getGradeStudyStatisticsList
     * @Description：获取班级学习详情列表
     * @author xrl
     * @Date 2017年6月20日
     * @param map
     * @return
     */
    public List<Map<String, Object>> getGradeStudyStatisticsList(Map<String, Object> map){
    	return gradeUserDao.getGradeStudyStatisticsList(map);
    }
    
    /**
     * 
     * @Title getGradePerformanceList
     * @Description：获取班级成绩统计列表
     * @author xrl
     * @Date 2017年6月20日
     * @param map
     * @return
     */
    public List<Map<String, Object>> getGradePerformanceList(Map<String, Object> map){
    	return gradeUserDao.getGradePerformanceList(map);
    }
    
    public Integer getUserCount(Integer gradeId){
    	return gradeUserDao.getUserCount(gradeId);
    }
}
