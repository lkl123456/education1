package com.adks.admin.controller.news;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.adks.admin.util.Constants;
import com.adks.admin.util.FreemarkerUtil;
import com.adks.admin.util.LogUtil;
import com.adks.commons.oss.OSSUploadUtil;
import com.adks.commons.util.ComUtil;
import com.adks.commons.util.PropertiesFactory;
import com.adks.dubbo.api.data.news.Adks_news;
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.interfaces.admin.log.LogApi;
import com.adks.dubbo.api.interfaces.admin.news.NewsApi;
import com.adks.dubbo.api.interfaces.admin.news.NewsSortApi;
import com.adks.dubbo.api.interfaces.admin.news.SysNoticeApi;
import com.adks.dubbo.api.interfaces.admin.org.OrgApi;
import com.adks.dubbo.commons.Page;

@Controller
@RequestMapping(value = "/news")
public class NewsController {
	private final Logger logger = LoggerFactory.getLogger(NewsController.class);

	@Resource
	private NewsApi newsApi;

	@Autowired
	private OrgApi orgApi;

	private String org_sn_name;

	@Autowired
	private NewsSortApi newsSortApi;
	@Autowired
	private SysNoticeApi sysNoticeApi;
	@Autowired
	private LogApi logApi;

	@ModelAttribute
	public void beforec(HttpServletRequest request) {
		String orgunameValue = request.getParameter("orgunameValue");
		org_sn_name = (String) request.getSession().getAttribute(Constants.SESSION_USER_ORG_SN_NAME);
		if (orgunameValue != null && !"".equals(orgunameValue)) {
			org_sn_name = orgunameValue;
		}
		System.out.println("优先执行的获取到的    org_sn_name是：" + org_sn_name);
	}

	/**
	 * 页面跳转，进入新闻管理界面，仅仅是页面跳转，没有任何业务逻辑
	 * 
	 * @return String
	 * @date 2017-03-08
	 */
	@RequestMapping({ "/toNewsList" })
	public String newsList(Model model) {
		logger.debug("进入newsList controller...");
		model.addAttribute("org_sn_name", org_sn_name);
		String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
		model.addAttribute("ossResource", ossResource);
		return "/news/newsList";
	}

	/**
	 * 页面跳转，进入新闻添加页面，仅仅是页面跳转，没有任何业务逻辑
	 * 
	 * @return String 2017年4月17日
	 */
	@RequestMapping({ "/addNews" })
	public String addNews(Model model) {
		logger.debug("进入newsList controller...");
		model.addAttribute("org_sn_name", org_sn_name);
		String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
		model.addAttribute("ossResource", ossResource);
		return "/news/addNews";
	}

	/**
	 * 新闻管理主界面，获取新闻列表json数据
	 * 
	 * @param page
	 *            :分页参数
	 * @param rows
	 *            :分页参数
	 * @param orgunameValue
	 *            : 超管选择的分校 uname-sn
	 * @return page
	 * @date 2017-03-08
	 */
	@RequestMapping(value = "/getNewsListJson", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public Page getNewsListJson(HttpServletRequest request, HttpServletResponse response, String s_news_name,
			String orgCode, Integer page, Integer rows, Integer newsSortId, Integer newsSortType) {

		Page<List<Map<String, Object>>> paramPage = new Page<List<Map<String, Object>>>();
		// 模糊查询 的参数
		// 调用dubbo服务获取map数据
		Page<List<Map<String, Object>>> pageNew = null;
		try {
			Map userMap = (Map) request.getSession().getAttribute("user");
			Map likemap = new HashMap();
			boolean isSuperManager = "1".equals(userMap.get("isSuper") + "") ? true : false;
			if (!isSuperManager) {
				String orgCode1 = userMap.get("orgCode") + "";
				likemap.put("orgCode", orgCode1);
			}
			if (s_news_name != null && !"".equals(s_news_name)) {
				s_news_name = java.net.URLDecoder.decode(s_news_name, "UTF-8");
				likemap.put("newsTitle", s_news_name);
			}
			if (newsSortId != null && !"".equals(newsSortId)) {
				likemap.put("newsSortId", newsSortId);
			}
			// if (newsSortType != null && !"".equals(newsSortType)) {
			// likemap.put("newsSortType", newsSortType);
			// }
			if (orgCode != null && !"".equals(orgCode)) {
				likemap.put("orgCode", orgCode);
			}

			paramPage.setMap(likemap);
			paramPage.setPageSize(rows);
			paramPage.setCurrentPage(page);
			pageNew = newsApi.getNewsListPage(paramPage);
			List<Map<String, Object>> aList = pageNew.getRows();
			for (Map<String, Object> map : aList) {
				// System.out.println(map.toString());
				Date date = (Date) map.get("createTime");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String createTimeStr = sdf.format(date);
				map.put("createTimeStr", createTimeStr);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return pageNew;
	}

	/**
	 * 保存新闻
	 * 
	 * @return 2017-03-08
	 */
	@RequestMapping(value = "/saveNews", method = RequestMethod.POST)
	public void saveNews(Adks_news adksNews, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "newsFocusPicFile", required = false) MultipartFile newsFocusPicFile,
			String content) {
		response.setContentType("text/html;charset=utf-8");
		String newsImgPath = PropertiesFactory.getProperty("ossConfig.properties", "oss.NewsImg_Path");
		String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
		if (adksNews != null) {
			Map<String, Object> checkMap = new HashMap<>();
			if (adksNews.getNewsTitle() != null && adksNews.getNewsTitle() != "") {
				checkMap.put("newsTitle", adksNews.getNewsTitle());
			}
			if (adksNews.getNewsId() != null) {
				checkMap.put("newsId", adksNews.getNewsId());
			}
			if (adksNews.getOrgId() != null) {
				checkMap.put("orgId", adksNews.getOrgId());
			}
			try {
				PrintWriter pWriter = response.getWriter();
				Map<String, Object> map = new HashMap<>();
				Map<String, Object> newsMap = sysNoticeApi.checkNewsName(checkMap);
				if (newsMap.size() <= 0) {
					adksNews.setCreateTime(new Date());
					Map userMap = (Map) request.getSession().getAttribute("user");
					Integer userId = Integer.parseInt(userMap.get("userId") + "");
					String username = userMap.get("username") + "";
					Integer orgId = (Integer) userMap.get("orgId");
					adksNews.setCreatorId(userId);
					adksNews.setCreatorName(username);
					Adks_org org = orgApi.getOrgById(orgId);
					adksNews.setOrgId(orgId);
					adksNews.setOrgCode(org.getOrgCode());
					adksNews.setOrgName(org.getOrgName());
					if (adksNews.getNewsSortId() != null) {
						Map<String, Object> nsMap = newsSortApi.getNewsSortById(adksNews.getNewsSortId());
						if (nsMap != null) {
							String newsSortName = (String) nsMap.get("newsSortName");
							adksNews.setNewsSortName(newsSortName);
							// Integer orgId = (Integer) nsMap.get("orgId");
						}
					}
					if (StringUtils.isNotEmpty(content)) {
						content = content.substring(1, content.length());
						adksNews.setNewsContent(content.getBytes());
					}
					Date date = new Date();
					adksNews.setCreateTime(date);

					if (adksNews.getCreateTime() != null)
						date = adksNews.getCreateTime();

					if (adksNews.getNewsContent() != null && ComUtil.isNotNullOrEmpty(content)) {
						String html = "";
						if (ComUtil.isNotNullOrEmpty(adksNews.getNewsHtmlAdress())) {
							html = adksNews.getNewsHtmlAdress();
						}
						String htmlPath = FreemarkerUtil.getHtmlPath(adksNews.getNewsTitle(), username, date, content,
								html, request);
						adksNews.setNewsHtmlAdress(htmlPath);
					}
					if (newsFocusPicFile != null) {
						if (StringUtils.isNotEmpty(newsFocusPicFile.getOriginalFilename())) {
							try {
								String fileType = newsFocusPicFile.getContentType();
								if (!"".equals(fileType) && !"tmp".equals(fileType)) {
									String[] types = fileType.split("/");
									// 将MultipartFile转换成文件流
									// CommonsMultipartFile cf =
									// (CommonsMultipartFile)
									// newsFocusPicFile;
									// DiskFileItem fi = (DiskFileItem)
									// cf.getFileItem();
									// File file = fi.getStoreLocation();
									InputStream is = newsFocusPicFile.getInputStream();
									if (adksNews.getNewsFocusPic() == null || "".equals(adksNews.getNewsFocusPic())) {
										// 没有图片时上传图片
										String new_Path = OSSUploadUtil.uploadFileNewName(is, types[1], newsImgPath);
										String[] paths = new_Path.split("/");
										String code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
										adksNews.setNewsFocusPic(code);
									} else if (adksNews.getNewsFocusPic() != null
											&& !"".equals(adksNews.getNewsFocusPic())) {
										// 覆盖原有图片上传，更新文件只更新文件内容，不更新文件名称和文件路径
										String new_Path = OSSUploadUtil.updateFile(is, types[1],
												ossResource + adksNews.getNewsFocusPic());
										String[] paths = new_Path.split("/");
										String code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
										adksNews.setNewsFocusPic(code);
									}
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					Integer dataId=newsApi.saveNews(adksNews);
					//数据操作成功，保存操作日志
					if(dataId!=null&&dataId!=0){
						//添加/修改 
						if(adksNews.getNewsId()==null){  //添加
							logApi.saveLog(dataId,adksNews.getNewsTitle(),LogUtil.LOG_NEWS_MODULEID,LogUtil.LOG_ADD_TYPE,map);
						}else{  //修改
							logApi.saveLog(adksNews.getNewsId(),adksNews.getNewsTitle(),LogUtil.LOG_NEWS_MODULEID,LogUtil.LOG_UPDATE_TYPE,map);
						}
					}
					map.put("success", true);
				} else {
					map.put("mesg", "sameName");
				}
				pWriter.write(map.toString());
				pWriter.flush();
				pWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * 删除新闻
	 * 
	 * @param newsIds
	 * @param request
	 * @param response
	 *            2017-03-08
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteNews", method = RequestMethod.GET)
	public void deleteNews(String newsIds, String orgId_types, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("application/text;charset=utf-8");
		try {
			if (newsIds != null && newsIds.length() > 0) {
				String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
				Map<String, Object> userMap = (Map<String, Object>) request.getSession().getAttribute("user");
				String[] newsIdArr = newsIds.split(",");
				for (int i = 0; i < newsIdArr.length; i++) {
					Integer newsId = Integer.valueOf(newsIdArr[i]);
					Map<String, Object> newsMap = newsApi.getNewsInfoById(newsId);
					if (newsMap != null) {
						String newsFocusPic = (String) newsMap.get("newsFocusPic");
						if (newsFocusPic != null && !"".equals(newsFocusPic)) {
							OSSUploadUtil.deleteFile(ossResource + newsFocusPic);
						}
						String newsHtmlAdress = (String) newsMap.get("newsHtmlAdress");
						if (newsHtmlAdress != null && !"".equals(newsHtmlAdress)) {
							OSSUploadUtil.deleteFile(ossResource + newsHtmlAdress);
						}
					}
					//数据操作成功，保存操作日志
					if(newsId!=null){
						logApi.saveLog(newsId,null,LogUtil.LOG_NEWS_MODULEID, LogUtil.LOG_DELETE_TYPE, userMap);
					}
				}

				Map<String, Object> pMap = new HashMap<String, Object>();
				pMap.put("delNewsIds", newsIds);
				pMap.put("delOrgId_types", orgId_types);
				pMap.put("orgId", MapUtils.getIntValue(userMap, "orgId"));
				pMap.put("num", 20);
				if (newsIds != null && newsIds.length() > 0) {
					newsApi.deleteNewsByIds(pMap);
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

	/*
	 * 到新闻添加的页面
	 */
	@RequestMapping(value = "/toAddNews", method = RequestMethod.GET)
	public String toAddNews(Integer newsSortType, Integer newsSortId, Integer newsId, Model model) {
		Adks_news news = new Adks_news();
		if (newsId == null && newsSortId != null) {
			Map<String, Object> newsSort = newsSortApi.getNewsSortById(newsSortId);
			model.addAttribute("newsSortId", newsSort.get("newsSortId"));
			model.addAttribute("newsSortName", newsSort.get("newsSortName"));
			model.addAttribute("orgId", newsSort.get("orgId"));
			model.addAttribute("newsSortType", newsSortType);
			if (newsSort.get("orgId") != null && !"".equals(newsSort.get("orgId"))) {
				Adks_org org = orgApi.getOrgById(Integer.parseInt(newsSort.get("orgId") + ""));
				model.addAttribute("orgName", org.getOrgName());
				model.addAttribute("orgCode", org.getOrgCode());
			}
		}
		if (newsId != null) {
			news = newsApi.getNewsById(newsId);
			String content = null;
			if (news.getNewsContent() != null) {
				content = new String(news.getNewsContent());

			}
			model.addAttribute("content", content);
			Adks_org org = orgApi.getOrgById(news.getOrgId());
			model.addAttribute("orgName", org.getOrgName());
			model.addAttribute("orgCode", org.getOrgCode());
			model.addAttribute("newsSortId", news.getNewsSortId());
			model.addAttribute("newsSortName", news.getNewsSortName());
			model.addAttribute("newsSortType", news.getNewsSortType());
			model.addAttribute("orgId", news.getOrgId());
		}
		String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
		model.addAttribute("ossResource", ossResource);
		model.addAttribute("news", news);
		return "/news/editNews";
	}
}
