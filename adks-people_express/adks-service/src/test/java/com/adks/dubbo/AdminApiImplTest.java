package com.adks.dubbo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.adks.dubbo.api.interfaces.admin.grade.GradeApi;
import com.adks.dubbo.commons.Page;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class AdminApiImplTest {
	
	@Autowired
	private GradeApi gradeApi;
	
	@Before
	public void before() {
		
	}
	
	@After
	public void after() {
		
	}
	
	@Test
	public void testGetAdminById() {
		Page<List<Map<String, Object>>> paramPage = new Page<List<Map<String, Object>>>();
	 	//模糊查询 的参数
	   	Map likemap = new HashMap();
	   	likemap.put("gradeName", "民航");
	   	paramPage.setMap(likemap);
	   	paramPage.setPageSize(15);
	   	paramPage.setCurrentPage(1);
	   	paramPage= gradeApi.getGradeListPage(paramPage);
		Assert.assertNotNull(paramPage);
		
	}
}
