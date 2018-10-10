package com.adks.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.adks.commons.util.ComUtil;
import com.adks.commons.util.Constants;
import com.adks.dubbo.api.data.common.Adks_advertise;
import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.api.data.course.Adks_course_user;
import com.adks.dubbo.api.data.grade.Adks_grade_user;
import com.adks.dubbo.api.data.news.Adks_news;
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.data.org.Adks_org_config;
import com.adks.dubbo.api.data.user.Adks_user;
import com.adks.dubbo.api.data.version.Adks_version;
import com.adks.dubbo.api.interfaces.web.course.CourseApi;
import com.adks.dubbo.api.interfaces.web.course.CourseUserApi;
import com.adks.dubbo.api.interfaces.web.grade.GradeUserApi;
import com.adks.dubbo.api.interfaces.web.news.AdvertiseApi;
import com.adks.dubbo.api.interfaces.web.news.NewsApi;
import com.adks.dubbo.api.interfaces.web.org.OrgApi;
import com.adks.dubbo.api.interfaces.web.user.UserApi;

@Controller
@RequestMapping({ "/index" })
public class IndexController {

	@Autowired
	private OrgApi orgApi;

	@Resource
	private NewsApi newsApi;

	@Autowired
	private AdvertiseApi advertiseApi;

	@Autowired
	private CourseApi courseApi;

	@Autowired
	private CourseUserApi courseUserApi;
	@Autowired
	private UserApi userApi;

	@Autowired
	private GradeUserApi gradeUserApi;

	@RequestMapping(value = "/index.do")
	public String index(HttpServletRequest request, HttpServletResponse response, Integer orgId, Model model,RedirectAttributes  ra) {
		if (orgId == null) {// 如果为空就查明航局的
			orgId = 1;
		}
		// 是否登录，如果登录了设置isLogin=1，弹出层
		// model.addAttribute("isLogin", 1);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgId", orgId);
		// 首页焦点图——图片新闻
		map.put("num", 3);
		map.remove("newsSortType");
//		map.put("newsSortType", Constants.TPXW);
		map.put("newsSortType","tpxw");
		List<Adks_news> tpxwList = newsApi.getTopCmsArticleList(map);
		model.addAttribute("tpxwList", tpxwList);
		/* //通知公告 */
		map.remove("newsSortType");
		map.put("newsSortType", "tzgg");
		List<Adks_news> tzggList = newsApi.getTopCmsArticleList(map);
		model.addAttribute("tzggList", tzggList);
		// 新闻资讯
		map.put("num", 7);
		map.remove("newsSortType");
		map.put("newsSortType", "xwzx");
		List<Adks_news> xwzxList = newsApi.getTopCmsArticleList(map);
		String xwzxContent = null;
		if (xwzxList != null && xwzxList.size() > 0) {
			if (xwzxList.get(0).getNewsContent() != null) {
				xwzxContent = new String(xwzxList.get(0).getNewsContent());
			}
		}
		if (xwzxContent != null) {
			model.addAttribute("xwzxContent", xwzxContent == null ? null : xwzxContent.replaceAll("</?[^>]+>", ""));
		}
		model.addAttribute("xwzxList", xwzxList);
		// 培训动态
		map.remove("newsSortType");
		map.put("newsSortType","pxdt");
		List<Adks_news> pxdtList = newsApi.getTopCmsArticleList(map);
		String pxdtContent = null;
		if (pxdtList != null && pxdtList.size() > 0) {
			if (pxdtList.get(0).getNewsContent() != null) {
				pxdtContent = new String(pxdtList.get(0).getNewsContent());
			}
		}
		model.addAttribute("pxdtContent", pxdtContent == null ? null : pxdtContent.replaceAll("</?[^>]+>", ""));
		model.addAttribute("pxdtList", pxdtList);
		model.addAttribute("orgId", orgId);
		// 判断用户是否登录获取学时
		// session中如果用户已登录,在首页显示用户在平台上的总学时,gradeuser表的改用户学时之和
		Adks_user user = (Adks_user) request.getSession().getAttribute("user");
		if (null != user && null != user.getUserId()) {
			try {

				String userTotleXS = gradeUserApi.getUserTotleXS(user.getUserId());
				if (ComUtil.isNullOrEmpty(userTotleXS)) {
					userTotleXS = "0.0";
				}
				model.addAttribute("xs_totle_str", userTotleXS);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		List<Adks_advertise> advertLists = advertiseApi.getAdvertiseByCon(map);
		List<Adks_advertise> advertLists1 = new ArrayList<Adks_advertise>();
		List<Adks_advertise> advertLists2 = new ArrayList<Adks_advertise>();
		for (Adks_advertise adks_advertise : advertLists) {
			if (adks_advertise.getAdLocation() == 1) {
				advertLists1.add(adks_advertise);
			} else if (adks_advertise.getAdLocation() == 2) {
				advertLists2.add(adks_advertise);
			}
		}
		model.addAttribute("advertLists1", advertLists1);
		model.addAttribute("advertLists2", advertLists2);

		// 获取1.2广告位
		return "/index";
	}

	/**
	 * @Title:
	 * @Description:
	 * @Company: 广告位
	 * 
	 * @Version:
	 */
	@RequestMapping(value = "/indexAdvert.do")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model, Integer adLocation,
			Integer orgId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("adLocation", adLocation);
		map.put("orgId", orgId);
		List<Adks_advertise> advertList = advertiseApi.getAdvertiseByCon(map);
		model.addAttribute("advert", advertList);
		model.addAttribute("adLocation", adLocation);
		return "/index/indexAdvert";
	}

	/**
	 * @Title: indexNewCourseNcommandCourse
	 * @Description: 首页：本周新上,主编推荐,大家正在看
	 * @param @param
	 *            request
	 * @param @param
	 *            response
	 * @param @return
	 */
	@RequestMapping(value = "/indexNewCourseNcommandCourse.do")
	public String indexNewCourseNcommandCourse(HttpServletRequest request, HttpServletResponse response, Integer orgId,
			Model model) {
		List<Adks_course> courselist = null;
		Map<String, Object> map = new HashMap<String, Object>();
		// top5最新课程
		map.put("num", 5);
		if(orgId == null){
			orgId=1;
		}
		if (orgId != null) {
			Adks_org org = orgApi.getOrgById(orgId);
			if (org != null) {
				String orgCode=org.getOrgCode();
				String[] orgCodes=orgCode.split("A");
				if(orgCodes.length>2){
					orgCode=orgCodes[0]+"A"+orgCodes[1]+"A";
				}
				map.put("orgCode", orgCode);
			}
		}
		courselist = courseApi.getTopNewCourseList(map);
		model.addAttribute("topNewCourseList", courselist);
		// 推荐课程===目前页面还没有使用
		map.put("isRecommend", 1);
		map.remove("num");
		courselist = courseApi.getTopNewCourseList(map);
		model.addAttribute("topTJCourseList", courselist);

		// top15大家正在看
		map.put("num", 15);
		List<Adks_course_user> courseUserlist = courseUserApi.getTopCourseUserList(map);
		model.addAttribute("topCourseUserList", courseUserlist);
		return "/index/indexNewCourseNcommandCourse";
	}

	@RequestMapping(value = "/indexRankInfo.do")
	public String indexRankInfo(HttpServletRequest request, HttpSession session, HttpServletResponse response,
			Integer orgId, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (orgId == null) {
			orgId = 1;
		}
		List<Adks_course> courselist = null;
		// top5热门课程
		map.put("num", 20);
		Adks_org org = orgApi.getOrgById(orgId);
		if (org != null) {
			String orgCode=org.getOrgCode();
			String[] orgCodes=orgCode.split("A");
			if(orgCodes.length>2){
				orgCode=orgCodes[0]+"A"+orgCodes[1]+"A";
			}
			map.put("orgCode", orgCode);
		}
		courselist = courseApi.getTopHotCourseList(map);
		if(courselist.size()>5){
			model.addAttribute("length3", 4);
		}else{
			model.addAttribute("length3", courselist.size());
		}
		model.addAttribute("topHotCourseList", courselist);
		String orgCode1=MapUtils.getString(map,"orgCode");
		String[] orgCodes1=orgCode1.split("A");
		if(orgCodes1.length>2){
			map.put("orgId", orgCodes1[1]);
		}else{
			map.put("orgId", orgId);
		}
		
		// 单位排名
		map.put("num", 20);//首页只显示前五个，为了更多的数据一直
		// 机构列表
		List<Adks_org> orgList = orgApi.getOrgTopList(map);
		if(orgList!=null && orgList.size()>0){
			model.addAttribute("orgStudyTimelist", orgList);
			if(orgList.size()>5){
				model.addAttribute("length1", 4);
			}else{
				model.addAttribute("length1", orgList.size());
			}
		}

		// 学员班级课程学时排名
		model.addAttribute("orgName", org.getOrgName());
		map.remove("orgCode");
		if (org != null) {
			String orgCode=org.getOrgCode();
			String[] orgCodes=orgCode.split("A");
			if(orgCodes.length>2){
				orgCode=orgCodes[0]+"A"+orgCodes[1]+"A";
			}
			map.put("orgCode", orgCode);
		}
		List<Adks_grade_user> userStudyTimelist = gradeUserApi.getTopCourseStudyTimeUserList(map);
		if(userStudyTimelist!=null){
			if(userStudyTimelist.size()>5){
				model.addAttribute("length2", 4);
			}else{
				model.addAttribute("length2", userStudyTimelist.size());
			}
		}
		model.addAttribute("userStudyTimelist", userStudyTimelist);
		model.addAttribute("orgId", orgId == null ? 1 : orgId);
		return "/index/indexRankInfo";
	}

	// 页面下载download
	@RequestMapping(value = "/download.do")
	public String download(HttpServletRequest request, HttpSession session, HttpServletResponse response, Integer orgId,
			Model model) {
		model.addAttribute("orgId", orgId);
		return "/download/download";
	}

	// 学习支持
	@RequestMapping(value = "/contactUs.do")
	public String contactUs(HttpServletRequest request, HttpSession session, HttpServletResponse response,
			Integer orgId, Model model) {
		model.addAttribute("mainFlag", "contactUs");
		if (orgId == null) {
			orgId = 1;
		}
		model.addAttribute("orgId", orgId);
		return "/index/mainIndex";
	}

	// 学习支持
	@RequestMapping(value = "/toContactUs.do")
	public String toContactUs(HttpServletRequest request, HttpSession session, HttpServletResponse response,
			Integer orgId, Model model) {
		if (orgId == null) {
			orgId = 1;
		}
		model.addAttribute("orgId", orgId);
		return "/index/contactUsIndex";
	}

	// 学习指南
	@RequestMapping(value = "/onlineHelp.do")
	public String onlineHelp(HttpServletRequest request, HttpSession session, HttpServletResponse response, String flag,
			Integer orgId, Model model) {
		model.addAttribute("mainFlag", flag);
		if (orgId == null) {
			orgId = 1;
		}
		model.addAttribute("orgId", orgId);
		return "/index/mainIndex";
	}

	// 学习支持
	@RequestMapping(value = "/toOnlineHelp.do")
	public String toOnlineHelp(HttpServletRequest request, HttpSession session, HttpServletResponse response,
			String flag, Integer orgId, Model model) {
		model.addAttribute("mainFlag", flag);
		Adks_news news = null;
		String content = "";
		Integer newsId = null;
		if (ComUtil.isNotNullOrEmpty(flag)) {
			if ("xxlc".equals(flag)) {
				newsId = Constants.XXLC;
				model.addAttribute("helptitle", "学习流程");
			} else if ("czzn".equals(flag)) {
				newsId = Constants.CZZN;
				model.addAttribute("helptitle", "操作指南");
			} else if ("cjwt".equals(flag)) {
				newsId = Constants.CJWT;
				model.addAttribute("helptitle", "常见问题");
			} else if ("kqbd".equals(flag)) {
				newsId = Constants.KQBD;
				model.addAttribute("helptitle", "考前必读");
			}
			news = newsApi.getNewsById(newsId);
			if (news != null && news.getNewsContent() != null) {
				content = new String(news.getNewsContent());
			}
		}
		model.addAttribute("cmsArticle", news);
		model.addAttribute("content", content);
		if (orgId == null) {
			orgId = 1;
		}
		model.addAttribute("orgId", orgId);
		return "/index/onlineHelp";
	}

	// 统计更多
	@RequestMapping(value = "/phIndex.do")
	public String phIndex(HttpServletRequest request, HttpSession session, HttpServletResponse response, Integer orgId,
			Model model) {
		model.addAttribute("mainFlag", "phIndex");
		if (orgId == null) {
			orgId = 1;
		}
		model.addAttribute("orgId", orgId);
		return "/index/mainIndex";
	}

	// 统计更多
	@RequestMapping(value = "/toPhIndex.do")
	public String toPhIndex(HttpServletRequest request, HttpSession session, HttpServletResponse response,
			Integer orgId, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (orgId == null) {
			orgId = 1;
		}
		List<Adks_course> courselist = null;
		Adks_org org1=orgApi.getOrgById(orgId);
		map.put("num", 20);
		if (org1 != null) {
			String orgCode=org1.getOrgCode();
			String[] orgCodes=orgCode.split("A");
			if(orgCodes.length>2){
				orgCode=orgCodes[0]+"A"+orgCodes[1]+"A";
			}
			map.put("orgCode", orgCode);
		}
		courselist = courseApi.getTopHotCourseList(map);
		model.addAttribute("topHotCourseList", courselist);
		// 单位排名
		// String orgN = (String) session.getAttribute("logoType");
		/*
		 * Integer orgNnum = 0; if(orgN == "kgj" || "kgj".equals(orgN)){ orgNnum
		 * = 1;//机构父级的id }else if(orgN == "mhj" || "mhj".equals(orgN)){ orgNnum
		 * = 977; }else{ orgNnum = 977; }
		 */
		String orgCode1=MapUtils.getString(map,"orgCode");
		String[] orgCodes1=orgCode1.split("A");
		if(orgCodes1.length>2){
			map.put("orgId", orgCodes1[1]);
		}else{
			map.put("orgId", orgId);
		}
		// 机构列表
		List<Adks_org> orgList = orgApi.getOrgTopList(map);
		if(orgList!=null && orgList.size()>0){
			model.addAttribute("orgStudyTimelist", orgList);
			if(orgList.size()>5){
				model.addAttribute("length1", 4);
			}else{
				model.addAttribute("length1", orgList.size());
			}
		}
		// 学员班级课程学时排名
		Adks_org org = orgApi.getOrgById(orgId);
		model.addAttribute("orgName", org.getOrgName());
		map.remove("orgCode");
		if (org != null) {
			String orgCode=org.getOrgCode();
			String[] orgCodes=orgCode.split("A");
			if(orgCodes.length>2){
				orgCode=orgCodes[0]+"A"+orgCodes[1]+"A";
			}
			map.put("orgCode", orgCode);
		}
		List<Adks_grade_user> userStudyTimelist = gradeUserApi.getTopCourseStudyTimeUserList(map);
		model.addAttribute("userStudyTimelist", userStudyTimelist);
		model.addAttribute("orgId", orgId == null ? 1 : orgId);
		return "/index/phIndex";
	}

	@RequestMapping(value = "/top.do")
	public String top(HttpServletRequest request, HttpSession session, HttpServletResponse response, Integer orgId,
			Model model) {
		if (orgId == null) {
			orgId = 1;
		}
		// 获取banner图
		Adks_org_config orgConfig = orgApi.getOrgConfigByOrgId(orgId);
		model.addAttribute("orgConfig", orgConfig);// 机构配置信息，首页上边的banner图地址
		model.addAttribute("orgId", orgId);
		return "/index/top";
	}

	@RequestMapping(value = "/footer.do")
	public String footer(HttpServletRequest request, HttpSession session, HttpServletResponse response, Integer orgId,
			Model model) {
		if (orgId == null) {
			orgId = 1;
		}
		// 获取banner图
		Adks_org_config orgConfig = orgApi.getOrgConfigByOrgId(orgId);
		model.addAttribute("orgConfig", orgConfig);// 机构配置信息，首页上边的banner图地址
		model.addAttribute("orgId", orgId);
		return "/index/footer";
	}

	// 到个人中心
	@RequestMapping(value = "/centerIndex.do")
	public String centerIndex(HttpServletRequest request, HttpSession session, HttpServletResponse response,
			Integer orgId, Model model) {
		model.addAttribute("mainFlag", "centerIndex");
		if (orgId == null) {
			orgId = 1;
		}
		model.addAttribute("orgId", orgId);
		return "/index/mainIndex";
	}

	// 到个人中心
	@RequestMapping(value = "/toCenter.do")
	public String toCenter(HttpServletRequest request, HttpSession session, HttpServletResponse response, Integer orgId,
			String flag, Model model) {
		if (orgId == null) {
			orgId = 1;
		}
		model.addAttribute("orgId", orgId);
		model.addAttribute("flag", flag);
		return "/user/centerIndex";
	}

	/* 判断身份证号是否存在其他用户 */
	@RequestMapping(value = "/checkUserCardIndex.do")
	@ResponseBody
	public void checkUserCardIndex(String cardNumer, Integer userId, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter pw = null;
		String result = "error";
		try {
			pw = response.getWriter();
			Map<String, Object> user = userApi.checkUserCard(cardNumer);// 返回“yes”
																		// 可用，“no”
																		// 不可用、
			if (user == null) {
				result = "succ";
			} else if (user != null && userId.equals(user.get("userId"))) {
				result = "succ";
			} else {
				result = "error";
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.addHeader("content-type", "text/html");
			pw.write(result);
			pw.flush();
			pw.close();
		}
	}

	// 找回密码
	@RequestMapping(value = "/findPwd.do")
	public String findPwd(HttpServletRequest request, HttpServletResponse response, Integer orgId, Model model) {
		model.addAttribute("orgId", orgId);
		model.addAttribute("mainFlag", "findPwd");
		return "/index/mainIndex";
	}

	@RequestMapping(value = "/toFindPwd.do")
	public String toFindPwd(HttpServletRequest request, HttpServletResponse response, Integer orgId, Model model) {
		model.addAttribute("orgId", orgId);
		return "/index/findPwd";
	}

	/*
	 * 查看手机号码是否跟用户相关联
	 */
	@RequestMapping(value = "/checkUserCellPhoneIndex.do")
	@ResponseBody
	public void checkUserCellPhone(String cellphone, String realname, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter pw = null;
		String result = "error";
		try {
			pw = response.getWriter();
			Adks_user user = userApi.checkUserCellPhone(cellphone);// 返回“yes”
																	// 可用，“no”
																	// 不可用
			if (user == null) {
				result = "nouser";
			}
			if (user != null && (realname.equals(user.getUserRealName()) || realname.equals(user.getUserName()))) {
				result = "succ";
			}
			if (user != null && !realname.equals(user.getUserRealName()) && !realname.equals(user.getUserName())) {
				result = "nocheckUser";
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.addHeader("content-type", "text/html");
			pw.write(result);
			pw.flush();
			pw.close();
		}
	}

	/**
	 * 重置密码
	 * 
	 */
	@RequestMapping(value = "/changPwd.do")
	@ResponseBody
	public void changPwd(String userCellphone, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter pw = null;
		String result = "succ";
		try {
			pw = response.getWriter();
			userApi.changePwd(userCellphone);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.addHeader("content-type", "text/html");
			pw.write(result);
			pw.flush();
			pw.close();
		}
	}

	/**
	 * 
	 * @Title downloadApp
	 * @Description:转移到手机扫码下载APP页面
	 * @author xrl
	 * @Date 2017年5月24日
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/downloadApp.do")
	public String downloadApp(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "/download/appDownload";
	}
	
	/**
	 * 
	 * @Title indexFloatNews
	 * @Description:获取飘窗
	 * @author xrl
	 * @Date 2017年6月14日
	 * @param orgId
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/indexFloat.do")
	public void indexFloatNews(Integer orgId,HttpServletRequest request, HttpServletResponse response)  throws IOException{
		Map map = new HashMap();
		map.put("orgId",orgId);
		map.put("adLocation",5);
		Adks_advertise advertise=advertiseApi.getAdvertiseInfoByOrgIdAndLocation(map);
		if(advertise==null){
			advertise=new Adks_advertise();
			advertise.setAdHref("");
			advertise.setAdImgPath("");
		}
		response.getWriter().write(advertise.getAdImgPath()+ "$" + advertise.getAdHref());
	}
}
