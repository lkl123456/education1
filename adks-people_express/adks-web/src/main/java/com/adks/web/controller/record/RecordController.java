package com.adks.web.controller.record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adks.commons.util.ComUtil;
import com.adks.dubbo.api.data.course.Adks_course_user;
import com.adks.dubbo.api.data.user.Adks_user;
import com.adks.dubbo.api.interfaces.web.course.CourseUserApi;
import com.adks.dubbo.api.interfaces.web.grade.GradeApi;
import com.adks.dubbo.api.interfaces.web.user.UserApi;
import com.adks.dubbo.commons.Page;

@Controller
@RequestMapping({"/record"})
public class RecordController {

	@Autowired
	private UserApi userApi;
	@Autowired
	private GradeApi gradeApi;
	@Autowired
	private CourseUserApi courseUserApi;
	
	@RequestMapping(value = "/recordIndex.do")
	public String index(Page<List<Adks_course_user>> page,HttpSession session,HttpServletRequest request, HttpServletResponse response,String selYear,Model model) {
		List<String> yearsList=new ArrayList<String>();
		Adks_user user=(Adks_user)session.getAttribute("user");
		if(user!=null){
			yearsList=gradeApi.getGradeYears(user.getUserId());
		}
		if(ComUtil.isNullOrEmpty(selYear) && ComUtil.isNotNullOrEmpty(yearsList) ){
			selYear=yearsList.get(0).toString();
		}
		
		List<Map<String, Object>>  recordsList=gradeApi.getGradeUserRecordList(user.getUserId(), selYear);
		model.addAttribute("recordsList", recordsList);
		
		//课程学习记录
		if(null == page){
			page = new Page<List<Adks_course_user>>();
		}
		Map map = new HashMap();
		map.put("userId", user.getUserId());
		map.put("selYear", selYear);
		page.setMap(map);
		page = courseUserApi.getCourseUserByUserId(page);
		Adks_user user1=userApi.getUserInfoById(user.getUserId());
		model.addAttribute("user",user1);
		String ationalityName  = "";
		model.addAttribute("page",page);
		model.addAttribute("ationalityName",ationalityName);
		
		model.addAttribute("selYear", selYear);
		model.addAttribute("yearsList", yearsList);
		return "/record/recordIndex";
	}
}
