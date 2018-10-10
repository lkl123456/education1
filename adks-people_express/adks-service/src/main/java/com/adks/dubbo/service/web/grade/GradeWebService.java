package com.adks.dubbo.service.web.grade;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.commons.util.RedisConstant;
import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.grade.Adks_grade;
import com.adks.dubbo.api.data.grade.Adks_grade_work;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.web.grade.GradeWebDao;
import com.alibaba.fastjson.JSONObject;

@Service
public class GradeWebService extends BaseService<GradeWebDao> {
    @Autowired
    private SolrServer solrServer;

    @Autowired
    private GradeWebDao gradeDao;

    @Override
    protected GradeWebDao getDao() {
        return gradeDao;
    }

    public List<Adks_grade> gradeTopCurrentList(Map map) {
        return gradeDao.gradeTopCurrentList(map);
    }

    //前台班级分页
    public Page<List<Adks_grade>> getGradeListWeb(Page<List<Adks_grade>> page) {
        return gradeDao.getGradeListWeb(page);
    }

    public Page<List<Adks_grade>> getGradeCurrentList(Page<List<Adks_grade>> page) {
        return gradeDao.getGradeCurrentList(page);
    }

    //历史培训班
    public Page<List<Adks_grade>> getGradeOverList(Page<List<Adks_grade>> page) {
        return gradeDao.getGradeOverList(page);
    }

    //获取班级 所在的 年份 
    public List<String> getGradeYears(Integer userId) {
        return gradeDao.getGradeYears(userId);
    }

    public List<Map<String, Object>> getGradeUserRecordList(Integer userId, String selYear) {
        return gradeDao.getGradeUserRecordList(userId, selYear);
    }

    //查看用户所在的班级
    public List<Adks_grade> registerGradeListByUserid(Integer userId) {
        List<Adks_grade> list = null;
        list = gradeDao.registerGradeListByUserid(userId);
        return list;
    }

    public Adks_grade getGradeById(Integer gradeId) {
        return gradeDao.getGradeById(gradeId);
    }

    public Integer getUserRanking(Integer gradeId, Integer userId) {
        return gradeDao.getUserRanking(gradeId, userId);
    }

    public Page<List<Adks_grade_work>> getGradeWorkByCon(Page<List<Adks_grade_work>> page) {
        return gradeDao.getGradeWorkByCon(page);
    }

    //获取班级论文详情信息
    public Adks_grade_work gradeThesisInfo(Map map) {
        return gradeDao.gradeThesisInfo(map);
    }

    public void initData() {
        List<Adks_grade> list = gradeDao.queryAll();
        try {
            for (Adks_grade grade : list) {
                if (0 != grade.getIsRegisit()) {

                    SolrInputDocument document = new SolrInputDocument();
                    document.setField("id", "gradeId_" + grade.getGradeId());
                    document.setField("gradeId", grade.getGradeId());
                    document.setField("orgId", grade.getOrgId());
                    document.setField("gradeName", grade.getGradeName());
                    document.setField("gradeImg", grade.getGradeImg());
                    document.setField("isRegisit", grade.getIsRegisit());
                    document.setField("startDate", grade.getStartDate());
                    document.setField("endDate", grade.getEndDate());
                    document.setField("userNum", grade.getUserNum());
                    document.setField("gradeDesc", grade.getGradeDesc());
                    document.setField("longTime", grade.getCreateTime().getTime());
                    document.setField("objectType", 3);
                    //写入索引库
                    solrServer.add(document);

                }
            }
            solrServer.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
