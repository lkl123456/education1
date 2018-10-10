package com.adks.dubbo.service.web.grade;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.grade.Adks_grade_work_reply;
import com.adks.dubbo.dao.web.grade.GradeWorkWebDao;

@Service
public class GradeWorkWebService extends BaseService<GradeWorkWebDao>{

	@Autowired
	private GradeWorkWebDao gradeWorkDao;
	
	@Override
	protected GradeWorkWebDao getDao() {
		return gradeWorkDao;
	}
	
	public Integer getGradeWorkCount(Integer gradeId) {
		return gradeWorkDao.getGradeWorkCount(gradeId);
	}
	
	public Integer getGradeWorkUserNum(Integer gradeId, Integer userId) {
		return gradeWorkDao.getGradeWorkUserNum(gradeId, userId);
	}
	
	public List<Adks_grade_work_reply> getGradeWorkReplyByCon(Map map) {
		return gradeWorkDao.getGradeWorkReplyByCon(map);
	}
	
}
