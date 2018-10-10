package com.adks.dubbo.providers.web.grade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.grade.Adks_grade;
import com.adks.dubbo.api.data.grade.Adks_grade_course;
import com.adks.dubbo.api.data.grade.Adks_grade_work;
import com.adks.dubbo.api.data.grade.Adks_grade_work_reply;
import com.adks.dubbo.api.interfaces.web.grade.GradeApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.web.grade.ExamScoreAnswerWebService;
import com.adks.dubbo.service.web.grade.ExamScoreWebService;
import com.adks.dubbo.service.web.grade.GradeWebService;
import com.adks.dubbo.service.web.grade.GradeWorkReplyWebService;

/**
 * 
 * ClassName GradeApiImpl
 * 
 * @Description：班级API实现
 * @author xrl
 * @Date 2017年3月22日
 */
public class GradeApiImpl implements GradeApi {

    @Autowired
    private GradeWebService gradeService;

    @Autowired
    private GradeWorkReplyWebService gradeWorkReplyService;

    @Autowired
    private ExamScoreWebService examScoreService;

    @Autowired
    private ExamScoreAnswerWebService examScoreAnswerService;

    public List<Adks_grade> gradeTopCurrentList(Map map) {
        return gradeService.gradeTopCurrentList(map);
    }

    @Override
    public Page<List<Adks_grade>> getGradeListWeb(Page<List<Adks_grade>> page) {
        return gradeService.getGradeListWeb(page);
    }

    // 根据条件查询班级的课程
    @Override
    public List<Adks_grade_course> getGradeCourseByCon(Map map) {
        return null;
    }

    @Override
    public Page<List<Adks_grade>> getGradeCurrentList(Page<List<Adks_grade>> page) {
        return gradeService.getGradeCurrentList(page);
    }

    @Override
    public Page<List<Adks_grade>> getGradeOverList(Page<List<Adks_grade>> page) {
        return gradeService.getGradeOverList(page);
    }

    @Override
    public List<String> getGradeYears(Integer userId) {
        return gradeService.getGradeYears(userId);
    }

    @Override
    public List<Map<String, Object>> getGradeUserRecordList(Integer userId, String selYear) {
        return gradeService.getGradeUserRecordList(userId, selYear);
    }

    @Override
    public List<Adks_grade> registerGradeListByUserid(Integer userId) {
        return gradeService.registerGradeListByUserid(userId);
    }

    @Override
    public Adks_grade getGradeById(Integer gradeId) {
        return gradeService.getGradeById(gradeId);
    }

    @Override
    public Integer getUserRanking(Integer gradeId, Integer userId) {
        return gradeService.getUserRanking(gradeId, userId);
    }

    @Override
    public Page<List<Adks_grade_work>> getGradeWorkByCon(Page<List<Adks_grade_work>> page) {
        return gradeService.getGradeWorkByCon(page);
    }

    // 获取班级论文详情信息
    @Override
    public Adks_grade_work gradeThesisInfo(Map map) {
        return gradeService.gradeThesisInfo(map);
    }

    // 保存学生班级论文
    @Override
    public Integer saveGradeWorkStudent(Adks_grade_work_reply gwr) {
        Integer flag = 0;
        // org
        Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
        insertColumnValueMap.put("workAnswer", gwr.getWorkAnswer());
        insertColumnValueMap.put("submitFilePath", gwr.getSubmitFilePath());
        insertColumnValueMap.put("submitDate", gwr.getSubmitDate());
        insertColumnValueMap.put("workId", gwr.getWorkId());
        insertColumnValueMap.put("workTitle", gwr.getWorkTitle());
        insertColumnValueMap.put("studentId", gwr.getStudentId());
        insertColumnValueMap.put("studentName", gwr.getStudentName());

        if (gwr.getGradeWorkReplyId() != null) {
            Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
            updateWhereConditionMap.put("gradeWorkReplyId", gwr.getGradeWorkReplyId());
            flag = gradeWorkReplyService.update(insertColumnValueMap, updateWhereConditionMap);

        }
        else {
            if (gwr.getIsCorrent() != null) {
                insertColumnValueMap.put("isCorrent", gwr.getIsCorrent());
            }
            flag = gradeWorkReplyService.insert(insertColumnValueMap);
        }
        return flag;
    }

    @Override
    public void initData() {
        gradeService.initData();

    }
}
