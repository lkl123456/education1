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
import org.springframework.web.bind.annotation.ResponseBody;

import com.adks.commons.util.ComUtil;
import com.adks.commons.util.Constants;
import com.adks.commons.util.Des;
import com.adks.dubbo.api.data.author.Adks_author;
import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.api.data.course.Adks_course_sort;
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.data.user.Adks_user;
import com.adks.dubbo.api.interfaces.web.author.AuthorApi;
import com.adks.dubbo.api.interfaces.web.course.CourseApi;
import com.adks.dubbo.api.interfaces.web.org.OrgApi;
import com.adks.dubbo.commons.Page;

@Controller
@RequestMapping({"/course"})
public class CourseController {

	@Autowired
	private CourseApi courseApi;
	@Autowired
	private OrgApi orgApi;
	@Autowired
	private AuthorApi authorApi;
	
	/**
	 * 转到 课程超市 页面 
	 * @Description:
	 * @param mv
	 * @return    
	 * @date 2014-8-24 下午03:53:59
	 */
	@RequestMapping(value="/courseIndex.do")
	public String toRegisterGradeList(Model model,Integer orgId){
		model.addAttribute("orgId", orgId);
		model.addAttribute("mainFlag", "courseIndex");//标示:进入课程中心
		return "/index/mainIndex";
	}
	
	@RequestMapping(value="/toCourseList.do")
	public String toCourseList(Model model,Integer orgId){
		model.addAttribute("orgId", orgId);
		return "/course/courseIndex";
	}
	
	//得到课程分页
	@RequestMapping(value = "/getCoursesList.do")
	public String getCoursesList(Page page,HttpSession session,HttpServletRequest request, HttpServletResponse response,Model model,String searchKeyValue
			,String courseSortCode,Integer orgId,Integer courseType,String showStyle) {
		try {
			Adks_user user = (Adks_user) request.getSession().getAttribute("user");
			if(user!=null){
				model.addAttribute("userId", user.getUserId());
			}else{
				model.addAttribute("userId", "");
			}
			Map map = new HashMap();
			//String ccName = "课程中心";
			if (ComUtil.isNotNullOrEmpty(courseSortCode)) { // 暂时用 courseCatalogId  in（，，，） 以后修改为 code查询
				map.put("courseSortCode", courseSortCode);
			}
			if (ComUtil.isNotNullOrEmpty(searchKeyValue)) {
				//searchKeyValue = java.net.URLDecoder.decode(searchKeyValue, "UTF-8");
				searchKeyValue=Des.getDesInstance().strDec(searchKeyValue, "adks1","adks2","adks3");
				map.put("searchKeyValue", searchKeyValue);
			}
			if (ComUtil.isNotNullOrEmpty(courseType)) {
				map.put("courseType", courseType);
			}
			if (page == null) {
				page = new Page();
			}
			if(orgId!=null){
				Adks_org org=orgApi.getOrgById(orgId);
				if(org!=null){
					String orgCode=org.getOrgCode();
					String[] orgCodes=orgCode.split("A");
					if(orgCodes.length>2){
						orgCode=orgCodes[0]+"A"+orgCodes[1]+"A";
					}
					map.put("orgCode", orgCode);
				}
			}
			page.setPageSize(Constants.PAGE_SIZE_SIXTEEN.intValue());
			page.setMap(map);
			page = courseApi.getCoursesListWeb(page);
			model.addAttribute("page", page);
			model.addAttribute("searchKeyValue", searchKeyValue);
			model.addAttribute("courseSortCode", courseSortCode);
			model.addAttribute("courseType", courseType);
			
			if(ComUtil.isNullOrEmpty(showStyle)){
				showStyle = "style1";
			}
			model.addAttribute("showStyle", showStyle);
			//mv.addObject("ccName", ccName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("orgId", orgId);
		return "/course/courseList";
	}
	
	/**
	 *得到所有的课程分类
	 */
	@RequestMapping(value = "/getCourseSortTreeDate.do")
	@ResponseBody
	public List<Adks_course_sort> getCourseSortTreeDate(HttpServletRequest request, HttpServletResponse response){
		Map map=new HashMap<>();
		List<Adks_course_sort> allCourseSort=courseApi.getCourseSortTreeDate(map);
		return allCourseSort;
	}
	
	/**
	 * 转到 mainIndex再到课程详情页面
	 */
	@RequestMapping(value="/courseInfo.do")
	public String courseInfo(Model model,Integer courseId,Integer orgId){
		model.addAttribute("orgId", orgId);
		model.addAttribute("courseId", courseId);
		model.addAttribute("mainFlag", "courseInfo");//标示:进入课程中心
		return "/index/mainIndex";
	}
	/**
	 * 转到 课程详情页面
	 */
	@RequestMapping(value="/toCourseInfo.do")
	public String toCourseInfo(Model model,Integer courseId,Integer orgId){
		Adks_course course = courseApi.getCourseById(courseId);	//课程信息
		Adks_author author = authorApi.getAuthorById(course.getAuthorId()); //讲师信息
		//课程被收藏次数
		//Integer chooseNum = userDao.getCourseColectionedNum(courseId);
		//course.setChooseNum(chooseNum);
		//系列课程 <根据当前课程名字，截取字符串，去掉最后三位，去查询相关的课程>
		String _courseName = course.getCourseName();
		if(course.getCourseName().length() > 4){
			_courseName = course.getCourseName().substring(0, course.getCourseName().length()-4);
		}
		Map map=new HashMap<>();
		map.put("courseId", courseId);
		map.put("courseName", _courseName);
		if(orgId==null){
			orgId=1;
		}
		Adks_org org=orgApi.getOrgById(orgId);
		if(org!=null){
			String orgCode=org.getOrgCode();
			String[] orgCodes=orgCode.split("A");
			if(orgCodes.length>2){
				orgCode=orgCodes[0]+"A"+orgCodes[1]+"A";
			}
			map.put("orgCode", orgCode);
		}
		List<Adks_course> nameLikedCourseList = courseApi.getCourseListByLikedName(map);
		
		//讲师主讲课程
		List<Adks_course> authorCourseList = courseApi.getCourseListByAuthorAndLimitNum(course.getAuthorId(),map);
		
		if(authorCourseList!=null && authorCourseList.size()>2){
			model.addAttribute("length", 1);
		}else{
			model.addAttribute("length", authorCourseList.size());
		}
		
		model.addAttribute("course",course);
		model.addAttribute("author",author);
		model.addAttribute("nameLikedCourseList",nameLikedCourseList);
		model.addAttribute("authorCourseList",authorCourseList);
		model.addAttribute("orgId", orgId);
		return "/course/courseInfo";
	}
}
