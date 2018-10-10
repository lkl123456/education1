package com.adks.dubbo.service.web.grade;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.dao.web.grade.GradeWorkReplyWebDao;
import com.adks.dubbo.dao.web.grade.GradeWorkWebDao;

@Service
public class GradeWorkReplyWebService extends BaseService<GradeWorkReplyWebDao>{

	@Autowired
	private GradeWorkReplyWebDao gradeWorkReplyWebDao;
	
	@Override
	protected GradeWorkReplyWebDao getDao() {
		return gradeWorkReplyWebDao;
	}
	
}
