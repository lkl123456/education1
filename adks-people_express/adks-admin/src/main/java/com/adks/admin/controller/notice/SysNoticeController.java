package com.adks.admin.controller.notice;

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
import com.adks.commons.util.PropertiesFactory;
import com.adks.dubbo.api.data.news.Adks_news;
import com.adks.dubbo.api.interfaces.admin.log.LogApi;
import com.adks.dubbo.api.interfaces.admin.news.SysNoticeApi;
import com.adks.dubbo.commons.Page;
import com.baidu.yun.push.Util.BaiduyunUtil;
import com.baidu.yun.push.Util.DeviceEnum;

@Controller
@RequestMapping(value = "/sysNotice")
public class SysNoticeController {

	private final Logger logger = LoggerFactory.getLogger(SysNoticeController.class);

	@Autowired
	private SysNoticeApi sysNoticeApi;
	@Autowired
	private LogApi logApi;

	/**
	 * 
	 * @Title toGradeList
	 * @Description：页面跳转，仅仅是页面跳转，没有任何业务逻辑
	 * @author xrl
	 * @Date 2017年3月22日
	 * @return
	 */
	@RequestMapping({ "/toSysNoticeList" })
	public String toGradeList(Model model) {
		logger.debug("@@@@@@跳转到班级列表界面");
		String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
		model.addAttribute("ossResource", ossResource);
		return "/notice/SysNoticeList";
	}

	/**
	 * 系统公告分页列表
	 * 
	 * @param request
	 * @param newsTitle
	 * @param page
	 * @param rows
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "getSysNoticeListJson", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public Page getSysNoticeListJson(HttpServletRequest request, String newsTitle, Integer page, Integer rows) {
		logger.debug("@@@@@@@@加载班级公告列表");
		Page<List<Map<String, Object>>> paramPage = new Page<List<Map<String, Object>>>();
		Map likemap = new HashMap();
		if (newsTitle != null && newsTitle != "") {
			likemap.put("newsTitle", newsTitle);
		}
		paramPage.setMap(likemap);
		paramPage.setPageSize(rows);
		paramPage.setCurrentPage(page);

		paramPage = sysNoticeApi.getSysNoticeListPage(paramPage);
		return paramPage;
	}

	/**
	 * 添加/编辑系统公告
	 * 
	 * @param newsId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toAddSysNotice", method = RequestMethod.GET)
	public String toAddGradeNotice(Integer newsId, Model model) {
		String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
		Adks_news news = new Adks_news();
		if (newsId != null) {
			news = sysNoticeApi.getSysNoticeById(newsId);
			String content = null;
			if (news.getNewsContent() != null) {
				content = new String(news.getNewsContent());

			}
			model.addAttribute("content", content);
			model.addAttribute("ossResource", ossResource);
			model.addAttribute("news", news);
			return "/notice/editSysNotice";
		} else {
			model.addAttribute("ossResource", ossResource);
			return "/notice/addSysNotice";
		}
	}

	/**
	 * 保存系统公告
	 * 
	 * @param adksNews
	 * @param gradeId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/saveSysNotice", method = RequestMethod.POST, produces = { "text/html;charset=utf-8" })
	@ResponseBody
	public Map<String, Object> saveGradeNotice(Adks_news adksNews, HttpServletRequest request,
			HttpServletResponse response) {
		if (adksNews != null) {
			Map<String, Object> checkMap = new HashMap<>();
			if (adksNews.getNewsTitle() != null && adksNews.getNewsTitle() != "") {
				checkMap.put("newsTitle", adksNews.getNewsTitle());
			}
			if (adksNews.getNewsId() != null) {
				checkMap.put("newsId", adksNews.getNewsId());
			}
			Map<String, Object> newsMap = sysNoticeApi.checkNewsName(checkMap);
			if (newsMap.size() <= 0) {
				Map userMap = (Map) request.getSession().getAttribute("user");
				Integer creatorId = Integer.parseInt(userMap.get("userId") + "");
				String creatorName = (String) userMap.get("username");
				if (adksNews.getNewsId() == null) {
					try {
						Date date=new Date();
						adksNews.setCreateTime(date);
						adksNews.setCreatorId(creatorId);
						adksNews.setCreatorName(creatorName);
						adksNews.setSysType(2);
						if (adksNews.getNewsContent() != null) {
							if(adksNews.getNewsContent().length<=0){
								String p = "<p></p>";
								byte[] pbyte = p.getBytes();
								adksNews.setNewsContent(pbyte);
							}
							String htmlPath=FreemarkerUtil.getHtmlPath(adksNews.getNewsTitle(),creatorName,date,new String(adksNews.getNewsContent()),adksNews.getNewsHtmlAdress(),request);
							adksNews.setNewsHtmlAdress(htmlPath);
						}
						Integer dataId=sysNoticeApi.saveSysNotice(adksNews);
						//数据操作成功，保存操作日志
						logApi.saveLog(dataId,adksNews.getNewsTitle(),LogUtil.LOG_SYSTEMNOTICE_MODULEID,LogUtil.LOG_ADD_TYPE,userMap);
						Map<String, Object> map = new HashMap<>();
						map.put("mesg", "succ");
						// 推送
						String jsonAndroid = "{\"title\":\"有新的系统消息\",\"description\":\"" + adksNews.getNewsTitle()
								+ "\",\"notification_builder_id\": 0, \"notification_basic_style\": 7,\"open_type\":0,\"url\": \"http://developer.baidu.com\",\"pkg_content\":\"\",\"custom_content\":{\"id\":\""
								+ adksNews.getNewsId() + "\",\"content\":\"" + adksNews.getContent()
								+ "\",\"method\":\"System\"}}";
						BaiduyunUtil.sendBaiduyunMessage(jsonAndroid, DeviceEnum.Android.toString());
						return map;
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					try {
						Date date=new Date();
						adksNews.setCreatorId(creatorId);
						adksNews.setCreatorName(creatorName);
						adksNews.setSysType(2);
						adksNews.setCreateTime(date);
						if (adksNews.getNewsContent() != null && !"".equals(adksNews.getNewsContent())) {
							FreemarkerUtil.getHtmlPath(adksNews.getNewsTitle(),creatorName,date,new String(adksNews.getNewsContent()),adksNews.getNewsHtmlAdress(),request);
						}
						System.out.println(new String(adksNews.getNewsContent()));
						sysNoticeApi.saveSysNotice(adksNews);
						//数据操作成功，保存操作日志
						logApi.saveLog(adksNews.getNewsId(),adksNews.getNewsTitle(),LogUtil.LOG_SYSTEMNOTICE_MODULEID,LogUtil.LOG_UPDATE_TYPE,userMap);
						Map<String, Object> map = new HashMap<>();
						map.put("mesg", "succ");
						return map;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

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
	 * @Title deleteSysNotice
	 * @Description：删除系统公告
	 * @author xrl
	 * @Date 2017年5月20日
	 * @param newsIds
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/deleteSysNotice", method = RequestMethod.GET)
	public void deleteSysNotice(String newsIds, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/text;charset=utf-8");
		try {
			if (newsIds != null && newsIds.length() > 0) {
				String[] newsIdArr = newsIds.split(",");
				Map map=(Map)request.getSession().getAttribute("user");
				for (int i = 0; i < newsIdArr.length; i++) {
					Integer newsId = Integer.valueOf(newsIdArr[i]);
					sysNoticeApi.deleteSysNotice(newsId);
					//数据操作成功，保存操作日志
					if(newsId!=null){
						logApi.saveLog(newsId,null,LogUtil.LOG_SYSTEMNOTICE_MODULEID, LogUtil.LOG_DELETE_TYPE, map);
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
