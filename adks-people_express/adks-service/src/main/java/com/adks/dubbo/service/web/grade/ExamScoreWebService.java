package com.adks.dubbo.service.web.grade;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.exam.Adks_exam_score;
import com.adks.dubbo.dao.web.grade.ExamScoreWebDao;

@Service
public class ExamScoreWebService extends BaseService<ExamScoreWebDao> {

    @Autowired
    private ExamScoreWebDao examScoreWebDao;

    @Override
    protected ExamScoreWebDao getDao() {
        return examScoreWebDao;
    }

    public void deleteExamScore(int examScoreId) {
        examScoreWebDao.deleteExamScore(examScoreId);
    }

    public List<Adks_exam_score> getExamScoreByCon(Map map) {
        return examScoreWebDao.getExamScoreByCon(map);
    }
}
