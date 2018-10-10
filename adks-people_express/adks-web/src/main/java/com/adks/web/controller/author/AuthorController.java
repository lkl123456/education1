package com.adks.web.controller.author;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adks.dubbo.api.data.author.Adks_author;
import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.interfaces.web.author.AuthorApi;
import com.adks.dubbo.api.interfaces.web.course.CourseApi;
import com.adks.dubbo.api.interfaces.web.org.OrgApi;
import com.adks.dubbo.commons.Page;

@Controller
@RequestMapping({"/author"})
public class AuthorController {
	
	@Autowired
	private AuthorApi authorApi;
	@Autowired
	private CourseApi courseApi;
	@Autowired
	private OrgApi orgApi;
	/**
	 * 转到讲师 页面 
	 * @Description:
	 * @param mv
	 * @return    
	 * @date 2014-8-24 下午03:53:59
	 */
	@RequestMapping(value="/authorInfo.do")
	public String authorInfo(Model model,Integer orgId,Integer authorId){
		model.addAttribute("orgId", orgId);
		model.addAttribute("authorId", authorId);
		model.addAttribute("mainFlag", "authorInfo");//标示:进入课程中心
		return "/index/mainIndex";
	}
	
	
	@RequestMapping(value="/toAuthorInfo.do")
	public String toAuthorInfo(Model model,Integer orgId,Integer authorId){
		Adks_author author = authorApi.getAuthorById(authorId); //讲师信息
		//该讲师的课程
		Map map = new HashMap<>();
		map.put("authorId", authorId);
		if(orgId==null){
			orgId=1;
		}
		Adks_org org=orgApi.getOrgById(orgId);
		if(org!=null){
			map.put("orgCode", org.getOrgCode());
		}
		List<Adks_course> authorCourseList = courseApi.getCourseListByAuthorAndLimitNum(authorId,map);
		//热门讲师 5位
		map.put("num", 5);
		List<Adks_author> topHotAuthorList = authorApi.getTopHotAuthorList(map);
		
		model.addAttribute("author",author);
		model.addAttribute("topHotAuthorList",topHotAuthorList);
		model.addAttribute("authorCourseList",authorCourseList);
		model.addAttribute("orgId", orgId);
		return "/author/authorInfo";
	}
	
}
