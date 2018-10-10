package com.adks.dubbo.service.app.grade;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.commons.util.RedisConstant;
import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.dao.app.grade.GradeAppDao;
import com.alibaba.fastjson.JSONObject;

@Service
public class GradeAppService extends BaseService<GradeAppDao> {

    @Autowired
    private GradeAppDao gradeDao;

    @Override
    protected GradeAppDao getDao() {
        return gradeDao;
    }

    /**
     * 根据gradeId获取班级信息
     * 
     * @param gradeId
     * @return
     */
    public Map<String, Object> getById(int id) {
        String gradeRedisName = RedisConstant.adks_app_grade_gradeId + id;
        Map<String, Object> map = null;
        map = gradeDao.getById(id);
        return map;
    }
}
