package com.adks.dubbo.providers.app.grade;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.interfaces.app.grade.GradeAppApi;
import com.adks.dubbo.service.app.grade.GradeAppService;

public class GradeAppApiImpl implements GradeAppApi {

	@Autowired
	private GradeAppService gradeService;

	@Override
	public Map<String, Object> getById(int id) {
		return gradeService.getById(id);
	}


}
