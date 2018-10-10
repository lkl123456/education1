package com.adks.web.controller.course;

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

import com.adks.commons.util.TimeUtils;
import com.adks.dubbo.api.data.course.Adks_course_user;
import com.adks.dubbo.api.data.user.Adks_user;
import com.adks.dubbo.api.interfaces.web.course.CourseUserApi;
import com.adks.dubbo.commons.Page;

@Controller
@RequestMapping({"/courseview"})
public class CourseUserController {

	@Autowired
	private CourseUserApi courseUserApi;
	
	//学生观看记录
	@RequestMapping(value = "/getUserCourseViewList.do")
	public String getUserCourseViewList(Page<List<Adks_course_user>> page,HttpSession session,HttpServletRequest request, HttpServletResponse response,Model model) {
		
		Adks_user user=(Adks_user)session.getAttribute("user");
		if(null == page){
			page = new Page<List<Adks_course_user>>();
		}
		Map map = new HashMap();
		map.put("userId", user.getUserId());
		page.setMap(map);
		page = courseUserApi.getUserCourseViewByUserId(page);
		List<Adks_course_user> list=page.getRows();
		if(list!=null && list.size()>0){
			for(Adks_course_user cu:list){
				if(cu.getLastPosition()==null || "".equals(cu.getLastPosition())){
					cu.setLastPosition(0);
				}
				cu.setLastPositionStr(TimeUtils.secToTime(cu.getLastPosition()));
			}
		}
		model.addAttribute("page", page);
		return "/user/userCourseViewList";
	}
}
