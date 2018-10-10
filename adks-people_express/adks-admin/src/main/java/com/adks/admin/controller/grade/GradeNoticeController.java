package com.adks.admin.controller.grade;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adks.admin.util.FreemarkerUtil;
import com.adks.admin.util.LogUtil;
import com.adks.commons.oss.OSSUploadUtil;
import com.adks.commons.util.PropertiesFactory;
import com.adks.dubbo.api.data.news.Adks_news;
import com.adks.dubbo.api.interfaces.admin.grade.GradeNoticeApi;
import com.adks.dubbo.api.interfaces.admin.log.LogApi;
import com.adks.dubbo.api.interfaces.admin.role.UserroleApi;
import com.adks.dubbo.commons.Page;
import com.baidu.yun.push.Util.BaiduyunUtil;
import com.baidu.yun.push.Util.DeviceEnum;

@Controller
@RequestMapping(value = "/gradeNotice")
public class GradeNoticeController {

	private final Logger logger = LoggerFactory.getLogger(GradeNoticeController.class);

	@Autowired
	private GradeNoticeApi gradeNoticeApi;
	@Autowired
	private UserroleApi userroleApi;
	@Autowired
	private LogApi logApi;

	/**
	 * 
	 * @Title getGradeNoticeListJson
	 * @Description:班级公告分页列表
	 * @author xrl
	 * @Date 2017年4月26日
	 * @param gradeId
	 * @param newsTitle
	 * @param page
	 * @param rows
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "getGradeNoticeListJson", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public Page getGradeNoticeListJson(HttpServletRequest request, String gradeId, String newsTitle, Integer page,
			Integer rows) {
		logger.debug("@@@@@@@@加载班级公告列表");
		Page<List<Map<String, Object>>> paramPage = new Page<List<Map<String, Object>>>();
		Map likemap = new HashMap();
		if (newsTitle != null && newsTitle != "") {
			likemap.put("newsTitle", newsTitle);
		}
		Map userMap = (Map) request.getSession().getAttribute("user");
		Integer userId = Integer.parseInt(userMap.get("userId") + "");
		String orgCode = userMap.get("orgCode") + "";
		boolean isRoot = "1".equals(userMap.get("isSuper") + "") ? true : false;
		List<Map<String, Object>> userRole = userroleApi.getRoleListByUser(userId);
		Integer roleId = (Integer) userRole.get(0).get("roleId");
		if (!isRoot || (gradeId == null && "".equals(gradeId))) {
			if (roleId == 5 || "5".equals(roleId)) {
				likemap.put("userId", userId);
			} else {
				likemap.put("orgCode", orgCode);
			}
		}
		if (gradeId != null && !"".equals(gradeId)) {
			likemap.put("gradeId", gradeId);
		}
		paramPage.setMap(likemap);
		paramPage.setPageSize(rows);
		paramPage.setCurrentPage(page);

		paramPage = gradeNoticeApi.getGradeNoticeListPage(paramPage);
		return paramPage;
	}

	/**
	 * 
	 * @Title toAddGradeNotice
	 * @Description：添加/编辑班级
	 * @author xrl
	 * @Date 2017年4月26日
	 * @param gradeId
	 * @param newsId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toAddGradeNotice", method = RequestMethod.GET)
	public String toAddGradeNotice(Integer gradeId, Integer newsId, Model model) {
		String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
		Adks_news news = new Adks_news();
		if (newsId != null) {
			news = gradeNoticeApi.getGradeNoticeById(newsId);
			String content = null;
			if (news.getNewsContent() != null) {
				content = new String(news.getNewsContent());

			}
			model.addAttribute("content", content);
			model.addAttribute("ossResource", ossResource);
			model.addAttribute("news", news);
			return "/grade/editGradeNotice";
		} else {
			model.addAttribute("ossResource", ossResource);
			model.addAttribute("gradeId", gradeId);
			return "/grade/addGradeNotice";
		}
	}

	/**
	 * 
	 * @Title saveGradeNotice
	 * @Description:保存班级公告
	 * @author xrl
	 * @Date 2017年4月26日
	 * @param adksNews
	 * @param gradeId
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/saveGradeNotice", method = RequestMethod.POST, produces = { "text/html;charset=utf-8" })
	@ResponseBody
	public Map<String, Object> saveGradeNotice(Adks_news adksNews, Integer gradeId, HttpServletRequest request,
			HttpServletResponse response) {
		if (adksNews != null) {
			Map<String, Object> checkMap = new HashMap<>();
			if (gradeId != null) {
				checkMap.put("gradeId", gradeId);
			}
			if (adksNews.getNewsTitle() != null && adksNews.getNewsTitle() != "") {
				checkMap.put("newsTitle", adksNews.getNewsTitle());
			}
			if (adksNews.getNewsId() != null) {
				checkMap.put("newsId", adksNews.getNewsId());
			}
			Map<String, Object> newsMap = gradeNoticeApi.checkNewsName(checkMap);
			if (newsMap.size() <= 0) {
				Map userMap = (Map) request.getSession().getAttribute("user");
				Integer creatorId = Integer.parseInt(userMap.get("userId") + "");
				String creatorName = (String) userMap.get("username");
//				if (adksNews.getNewsId() == null) {
					try {
						Date date=new Date();
						adksNews.setCreateTime(date);
						adksNews.setGradeId(gradeId);
						adksNews.setCreatorId(creatorId);
						adksNews.setCreatorName(creatorName);
						adksNews.setSysType(1);
						if (adksNews.getNewsContent() != null) {
							if(adksNews.getNewsContent().length<=0){
								String p = "<p></p>";
								byte[] pbyte = p.getBytes();
								adksNews.setNewsContent(pbyte);
							}
							String htmlPath=FreemarkerUtil.getHtmlPath(adksNews.getNewsTitle(),creatorName,date,new String(adksNews.getNewsContent()),adksNews.getNewsHtmlAdress(),request);
							adksNews.setNewsHtmlAdress(htmlPath);
						}
						Integer dataId=gradeNoticeApi.saveGradeNotice(adksNews);
						//数据操作成功，保存操作日志
						if(dataId!=null&&dataId!=0){
							//添加/修改 
							if(adksNews.getNewsId()==null){  //添加
								logApi.saveLog(dataId,adksNews.getNewsTitle(),LogUtil.LOG_GRADENOTICE_MODULEID,LogUtil.LOG_ADD_TYPE,userMap);
							}else{  //修改
								logApi.saveLog(adksNews.getNewsId(),adksNews.getNewsTitle(),LogUtil.LOG_GRADENOTICE_MODULEID,LogUtil.LOG_UPDATE_TYPE,userMap);
							}
						}
						Map<String, Object> map = new HashMap<>();
						map.put("mesg", "succ");
						// 推送
						String jsonAndroid = "{\"title\":\"有新的班级消息\",\"description\":\"" + adksNews.getNewsTitle()
								+ "\",\"notification_builder_id\": 0, \"notification_basic_style\": 7,\"open_type\":0,\"url\": \"http://developer.baidu.com\",\"pkg_content\":\"\",\"custom_content\":{\"id\":\""
								+ adksNews.getNewsId() + "\",\"content\":\"" + adksNews.getContent()
								+ "\",\"method\":\"Class\"}}";
						BaiduyunUtil.sendBaiduyunMessage(jsonAndroid, DeviceEnum.Android.toString());
						return map;
					} catch (Exception e) {
						e.printStackTrace();
					}
//				} 
//				else {
//					try {
//						Date date=new Date();
//						adksNews.setCreatorId(creatorId);
//						adksNews.setCreatorName(creatorName);
//						adksNews.setSysType(1);
//						adksNews.setCreateTime(date);
//						if (adksNews.getNewsContent() != null && !"".equals(adksNews.getNewsContent())) {
//							FreemarkerUtil.getHtmlPath(adksNews.getNewsTitle(),creatorName,date,new String(adksNews.getNewsContent()),adksNews.getNewsHtmlAdress(),request);
//						}
//						gradeNoticeApi.saveGradeNotice(adksNews);
//						Map<String, Object> map = new HashMap<>();
//						map.put("mesg", "succ");
//						return map;
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
			} else {
				Map<String, Object> map = new HashMap<>();
				map.put("mesg", "sameName");
				return map;
			}

		}
		return null;
	}
	
	/**
	 * 
	 * @Title deleteGradeNotice
	 * @Description：删除班级公告
	 * @author xrl
	 * @Date 2017年5月20日
	 * @param newsIds
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/deleteGradeNotice", method = RequestMethod.GET)
	public void deleteGradeNotice(String newsIds, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/text;charset=utf-8");
		try {
			if (newsIds != null && newsIds.length() > 0) {
				Map map=(Map)request.getSession().getAttribute("user");
				String[] newsIdArr = newsIds.split(",");
				for (int i = 0; i < newsIdArr.length; i++) {
					Integer newsId = Integer.valueOf(newsIdArr[i]);
					gradeNoticeApi.deleteGradeNoticeByIds(newsId);
					//数据操作成功，保存操作日志
					if(newsId!=null){
						logApi.saveLog(newsId,null,LogUtil.LOG_GRADENOTICE_MODULEID, LogUtil.LOG_DELETE_TYPE, map);
					}
				}
			}
			PrintWriter pWriter = response.getWriter();
			pWriter.write("succ");
			pWriter.flush();
			pWriter.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
