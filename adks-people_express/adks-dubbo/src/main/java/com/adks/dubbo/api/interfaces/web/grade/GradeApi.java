package com.adks.dubbo.api.interfaces.web.grade;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.grade.Adks_grade;
import com.adks.dubbo.api.data.grade.Adks_grade_course;
import com.adks.dubbo.api.data.grade.Adks_grade_work;
import com.adks.dubbo.api.data.grade.Adks_grade_work_reply;
import com.adks.dubbo.commons.Page;

/**
 * 
 * ClassName GradeApi
 * 
 * @Description：班级API
 * @author xrl
 * @Date 2017年3月22日
 */
public interface GradeApi {

    // 得到所有的班级
    public List<Adks_grade> gradeTopCurrentList(Map map);

    // 查询用户所在的班级
    public List<Adks_grade> registerGradeListByUserid(Integer userId);

    public Adks_grade getGradeById(Integer gradeId);

    // 获取前台班级分页
    public Page<List<Adks_grade>> getGradeListWeb(Page<List<Adks_grade>> page);

    // 根据条件得到班级的课程
    public List<Adks_grade_course> getGradeCourseByCon(Map map);

    // 获取用户的当前班级
    public Page<List<Adks_grade>> getGradeCurrentList(Page<List<Adks_grade>> page);

    // 获取历史培训班
    public Page<List<Adks_grade>> getGradeOverList(Page<List<Adks_grade>> page);

    /**
     * 获取班级 所在的 年份
     */
    public List<String> getGradeYears(Integer userId);

    // 获取 用户 在 指定年份 的 班级中的 档案信息
    public List<Map<String, Object>> getGradeUserRecordList(Integer userId, String selYear);

    public Integer getUserRanking(Integer gradeId, Integer userId);

    // 获取班级论文分页信息
    public Page<List<Adks_grade_work>> getGradeWorkByCon(Page<List<Adks_grade_work>> page);

    // 获取班级论文详情信息
    public Adks_grade_work gradeThesisInfo(Map map);

    public Integer saveGradeWorkStudent(Adks_grade_work_reply gwr);

    public void initData();

}
