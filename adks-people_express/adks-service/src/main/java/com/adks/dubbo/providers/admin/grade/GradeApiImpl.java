package com.adks.dubbo.providers.admin.grade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.commons.oss.OSSUploadUtil;
import com.adks.commons.util.PropertiesFactory;
import com.adks.dubbo.api.data.grade.Adks_grade;
import com.adks.dubbo.api.interfaces.admin.grade.GradeApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.admin.grade.GradeCourseService;
import com.adks.dubbo.service.admin.grade.GradeExamService;
import com.adks.dubbo.service.admin.grade.GradeService;
import com.adks.dubbo.service.admin.grade.GradeUserService;
import com.adks.dubbo.service.admin.grade.GradeWorkService;
import com.adks.dubbo.service.admin.news.NewsService;

/**
 * 
 * ClassName GradeApiImpl
 * @Description：班级API实现
 * @author xrl
 * @Date 2017年3月22日
 */
public class GradeApiImpl implements GradeApi {

    @Autowired
    private GradeService gradeService;

    @Autowired
    private GradeUserService gradeUserService;

    @Autowired
    private GradeWorkService gradeWorkService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private GradeCourseService gradeCourseService;

    @Autowired
    private GradeExamService gradeExamService;

    /**
     * 
     * @Title getGradeListPage
     * @Description：班级分页列表
     * @author xrl
     * @Date 2017年3月22日
     * @param paramPage
     * @return
     */
    @Override
    public Page<List<Map<String, Object>>> getGradeListPage(Page<List<Map<String, Object>>> paramPage) {
        return gradeService.getGradeListPage(paramPage);
    }

    /**
     * 
     * @Title saveGrade
     * @Description：保存班级
     * @author xrl
     * @Date 2017年3月22日
     * @param grade
     * @return
     */
    @Override
    public Integer saveGrade(Adks_grade grade) {
        String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
        Integer gId = null;
        try {
            Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
            insertColumnValueMap.put("gradeName", grade.getGradeName());
            insertColumnValueMap.put("gradeDesc", grade.getGradeDesc());
            insertColumnValueMap.put("gradeTarget", grade.getGradeTarget());
            insertColumnValueMap.put("gradeState", grade.getGradeState());
            insertColumnValueMap.put("startDate", grade.getStartDate());
            insertColumnValueMap.put("endDate", grade.getEndDate());
            insertColumnValueMap.put("gradeDesc", grade.getGradeDesc());
            insertColumnValueMap.put("gradeImg", grade.getGradeImg());
            insertColumnValueMap.put("requiredPeriod", grade.getRequiredPeriod());
            insertColumnValueMap.put("optionalPeriod", grade.getOptionalPeriod());
            insertColumnValueMap.put("workRequire", grade.getWorkRequire());
            insertColumnValueMap.put("examRequire", grade.getExamRequire());
            insertColumnValueMap.put("certificateImg", grade.getCertificateImg());
            insertColumnValueMap.put("eleSeal", grade.getEleSeal());
            insertColumnValueMap.put("isRegisit", grade.getIsRegisit());
            insertColumnValueMap.put("userNum", grade.getUserNum());
            insertColumnValueMap.put("optionalNum", grade.getOptionalNum());
            insertColumnValueMap.put("requiredNum", grade.getRequiredNum());
            insertColumnValueMap.put("orgId", grade.getOrgId());
            insertColumnValueMap.put("orgName", grade.getOrgName());
            insertColumnValueMap.put("orgCode", grade.getOrgCode());
            insertColumnValueMap.put("creatorId", grade.getCreatorId());
            insertColumnValueMap.put("creatorName", grade.getCreatorName());
            insertColumnValueMap.put("createTime", grade.getCreateTime());
            insertColumnValueMap.put("graduationDesc",grade.getGraduationDesc());
            if (grade.getGradeId() != null) {
                Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
                updateWhereConditionMap.put("gradeId", grade.getGradeId());
                gradeService.update(insertColumnValueMap, updateWhereConditionMap);
                gId = grade.getGradeId();
            }else{
                gId = gradeService.insert(insertColumnValueMap);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return gId;
    }

    /**
     * 
     * @Title loadEditGradeFormData
     * @Description：获取班级信息
     * @author xrl
     * @Date 2017年3月25日
     * @param gradeId
     * @return
     */
    @Override
    public Map<String, Object> loadEditGradeFormData(Integer gradeId) {
        return gradeService.getGradeById(gradeId);
    }

    public List<Adks_grade> gradeTopCurrentList(Map map) {
        return gradeService.gradeTopCurrentList(map);
    }

    /**
     * 
     * @Title getGradeByGradeId
     * @Description：根据班级ID获取班级信息
     * @author xrl
     * @Date 2017年4月27日
     * @param gradeId
     * @return
     */
    @Override
    public Map<String, Object> getGradeByGradeId(Integer gradeId) {
        return gradeService.getGradeById(gradeId);
    }

    /**
     * 
     * @Title delGradeByGradeIdId
     * @Description：删除班级
     * @author xrl
     * @Date 2017年4月27日
     * @param gradeId
     */
    @Override
    public void delGradeByGradeIdId(Integer gradeId) {
        newsService.delGradeNoticeByGradeId(gradeId); //删除班级公告
        //删除班级用户提交作业——未做
        gradeWorkService.delGradeWorkByGradeId(gradeId); //删除班级作业
        gradeExamService.delGradeExamByGradeId(gradeId); //删除班级考试
        gradeCourseService.delGradeCourseByGradeId(gradeId); //删除班级课程
        gradeUserService.delGradeUserByGradeId(gradeId); //删除班级用户
        gradeService.delGradeByGradeIdId(gradeId); //删除班级
    }

    /**
     * 
     * @Title getGradesJson
     * @Description:根据orgCode获取班级
     * @author xrl
     * @Date 2017年4月28日
     * @param map
     * @return
     */
    @Override
    public List<Adks_grade> getGradesJson(Map<String, Object> map) {
        return gradeService.getGradesJson(map);
    }

    /**
     * 
     * @Title checkGradeName
     * @Description:检查班级名称在该机构下是否重复
     * @author xrl
     * @Date 2017年5月8日
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> checkGradeName(Map<String, Object> map) {
        return gradeService.checkGradeName(map);
    }

    /**
     * 
     * @Title updateGradeUserNum
     * @Description:更新班级用户人数
     * @author xrl
     * @Date 2017年5月15日
     * @param map
     */
    @Override
    public void updateGradeUserNum(Map<String, Object> paramValue,Map<String, Object> updateWhereConditionMap) {
        gradeService.update(paramValue, updateWhereConditionMap);
    }
}
