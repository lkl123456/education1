package com.adks.admin.controller.news;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adks.admin.util.Constants;
import com.adks.admin.util.LogUtil;
import com.adks.commons.util.PYUtil;
import com.adks.dubbo.api.data.news.Adks_news_sort;
import com.adks.dubbo.api.data.news.Adks_news_type;
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.interfaces.admin.log.LogApi;
import com.adks.dubbo.api.interfaces.admin.news.NewsSortApi;
import com.adks.dubbo.api.interfaces.admin.org.OrgApi;
import com.adks.dubbo.commons.Page;

@Controller
@RequestMapping(value = "/newsSort")
public class NewsSortController {

	private final Logger logger = LoggerFactory.getLogger(NewsSortController.class);

	@Autowired
	private NewsSortApi newsSortApi;

	private String org_sn_name;

	@Autowired
	private OrgApi orgApi;
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
	 * 页面跳转，进入新闻分类管理界面，仅仅是页面跳转，没有任何业务逻辑
	 * 
	 * @return String
	 * @date 2017-03-08
	 */
	@RequestMapping({ "/toNewsSortList" })
	public String newsSortList(Model model) {
		logger.debug("进入newsSortList controller...");
		model.addAttribute("org_sn_name", org_sn_name);
		return "/news/newsSortList";
	}

	/**
	 * 新闻分类管理主界面，获取新闻分类列表json数据
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
	@RequestMapping(value = "/getNewsSortListJson", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public Page getNewsSortListJson(HttpServletRequest request, HttpServletResponse response, String s_newsSort_name,
			Integer page, Integer rows, Integer orgId, Integer newsSortType) {

		Page<List<Map<String, Object>>> paramPage = new Page<List<Map<String, Object>>>();
		// 模糊查询 的参数
		// 调用dubbo服务获取map数据
		Page<List<Map<String, Object>>> pageNew = null;
		try {
			Map likemap = new HashMap();
			if (s_newsSort_name != null && !"".equals(s_newsSort_name)) {
				s_newsSort_name = java.net.URLDecoder.decode(s_newsSort_name, "UTF-8");
				likemap.put("newsSortName", s_newsSort_name);
			}
			Map userMap = (Map) request.getSession().getAttribute("user");

//			boolean isSuperManager = "1".equals(userMap.get("isSuper") + "") ? true : false;
//			if (!isSuperManager) {
//				String orgCode = userMap.get("orgCode") + "";
//				likemap.put("orgCode", orgCode);
//			}
//			if (orgId != null && !"".equals(orgId) && orgId != 0) {
//				Adks_org org = orgApi.getOrgById(orgId);
//				likemap.remove("orgCode");
//				likemap.put("orgCode", org.getOrgCode());
//			} else if (orgId != null && !"".equals(orgId) && orgId == 0) {
//				likemap.remove("orgCode");
//				likemap.put("orgCode", "0A");
//			}
//			if (newsSortType != null) {
//				likemap.put("newsSortType", newsSortType);
//			}
			paramPage.setMap(likemap);
			paramPage.setPageSize(rows);
			paramPage.setCurrentPage(page);

			pageNew = newsSortApi.getNewsSortListPage(paramPage);
			List<Map<String, Object>> aList = pageNew.getRows();
			for (Map<String, Object> map : aList) {
				// System.out.println(map.toString());
				Date date = (Date) map.get("createTime");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String createTimeStr = sdf.format(date);
				map.put("createTimeStr", createTimeStr);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return pageNew;
	}

	/**
	 * 获取新闻所有分类
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getNewsSortList", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public List<Adks_news_type> getNewsSortList(HttpServletRequest request, HttpServletResponse response) {
		List<Adks_news_type> newsTypeListP = getNewsType();
		Map userMap = (Map) request.getSession().getAttribute("user");
		Map map = new HashMap();
//		boolean isSuperManager = "1".equals(userMap.get("isSuper") + "") ? true : false;
//		if (!isSuperManager) {
//			String orgCode = userMap.get("orgCode") + "";
//			map.put("orgCode", orgCode);
//		}
		List<Adks_news_sort> list = newsSortApi.getNewsSortList(map);
		if (list != null && list.size() >= 1) {
			//List<Adks_news_type> childList = newsTypeListP.get(0).getChildren();// 所有的新闻类型
			for (Adks_news_type newsType : newsTypeListP) {
				List<Adks_news_type> childClList = new ArrayList<Adks_news_type>();
				for (Adks_news_sort newsSort : list) {
					Adks_news_type ns = new Adks_news_type();
					ns.setId(newsSort.getId());
					ns.setName(newsSort.getName());
					ns.setText(newsSort.getName());
					ns.setOrgId(newsSort.getOrgId());
					ns.setOrgCode(newsSort.getOrgCode());
					ns.setOrgName(newsSort.getOrgName());
					ns.setNewsSortType(newsType.getId());
					childClList.add(ns);
				}
				newsType.setChildren(childClList);
			}
		}
		return newsTypeListP;
	}

	/**
	 * 获取新闻所有分类名称
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getNewsSortNameList", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public List<Adks_news_sort> getNewsSortNameList() {
		Map mapResult = new HashMap();
		List<Adks_news_sort> list = newsSortApi.getNewsSortList(mapResult);
		List<Adks_news_sort> newList = new ArrayList<Adks_news_sort>();
		for (Adks_news_sort map : list) {
			Adks_news_sort ans = new Adks_news_sort();
			// System.out.println(map.toString());
			ans.setNewsSortName(map.getNewsSortName());
			newList.add(ans);
		}
		return newList;
	}

	/**
	 * 保存新闻分类
	 * 
	 * @return 2017-03-08
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveNewsSort", method = RequestMethod.POST)
	public void saveNewsSort(Adks_news_sort adksNewsSort, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		if (adksNewsSort != null) {
			adksNewsSort.setCreateTime(new Date());
			Map<String, Object> userMap = (Map<String, Object>) request.getSession().getAttribute("user");
			Integer userId = Integer.parseInt(userMap.get("userId") + "");
			String username = userMap.get("username") + "";
			Integer orgId = (Integer)userMap.get("orgId");
			adksNewsSort.setCreatorId(userId);
			adksNewsSort.setCreatorName(username);
			adksNewsSort.setNewsSortType(PYUtil.converterToFirstSpell(adksNewsSort.getNewsSortName()).toLowerCase());
			if (adksNewsSort.getOrgId() != null && !"".equals(adksNewsSort.getOrgId())) {
				Adks_org org = orgApi.getOrgById(orgId);
				adksNewsSort.setOrgId(orgId);
				adksNewsSort.setOrgName(org.getOrgName());
				adksNewsSort.setOrgCode(org.getOrgCode());
			}
			try {
				Integer dataId=newsSortApi.saveNewsSort(adksNewsSort);
				//数据操作成功，保存操作日志
				if(dataId!=null&&dataId!=0){
					//添加/修改 
					if(adksNewsSort.getNewsSortId()==null){  //添加
						logApi.saveLog(dataId,adksNewsSort.getNewsSortName(),LogUtil.LOG_NEWSSORT_MODULEID,LogUtil.LOG_ADD_TYPE,userMap);
					}else{  //修改
						logApi.saveLog(adksNewsSort.getNewsSortId(),adksNewsSort.getNewsSortName(),LogUtil.LOG_NEWSSORT_MODULEID,LogUtil.LOG_UPDATE_TYPE,userMap);
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

	/**
	 * 删除新闻分类
	 * 
	 * @param newsSortIds
	 * @param request
	 * @param response
	 *            2017-03-08
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteNewsSort", method = RequestMethod.GET)
	public void deleteNewsSort(String newsSortIds, String orgIds, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("application/text;charset=utf-8");
		Map<String, Object> userMap = (Map<String, Object>) request.getSession().getAttribute("user");
		Map<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("delNewsSortIds", newsSortIds);
//		pMap.put("delOrgIds", orgIds);
//		pMap.put("orgId", MapUtils.getIntValue(userMap, "orgId"));
//		pMap.put("orgCode", MapUtils.getString(userMap, "orgCode"));

		try {
			if (newsSortIds != null && newsSortIds.length() > 0) {
				newsSortApi.deleteNewsSortByIds(pMap);
				String[] nsIds=newsSortIds.split(",");
				for (String nsId : nsIds) {
					//数据操作成功，保存操作日志
					if(nsId!=null){
						logApi.saveLog(Integer.valueOf(nsId),null,LogUtil.LOG_NEWSSORT_MODULEID, LogUtil.LOG_DELETE_TYPE, userMap);
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

	/*
	 * 得到新闻类型树
	 * 
	 */
	@RequestMapping(value = "/getNewsTypeJson", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public List<Adks_news_sort> getNewsTypeJson() {
		//List<Adks_news_type> newsTypeListP = getNewsType();
		Map<String, Object> map=new HashMap<>();
		List<Adks_news_sort> newsTypeListP = newsSortApi.getNewsSortList(map);
		return newsTypeListP;
	}

	public List<Adks_news_type> getNewsType() {
		List<Adks_news_type> newsTypeListP = new ArrayList<Adks_news_type>();
		Adks_news_type newsTypeP = new Adks_news_type();
		List<Adks_news_type> newsTypeList = new ArrayList<Adks_news_type>();
		newsTypeP.setId(null);
		newsTypeP.setName("全部");
		newsTypeP.setText("全部");
		newsTypeListP.add(newsTypeP);
		//newsTypeP.setChildren(newsTypeList);

//		Adks_news_type newsType = new Adks_news_type();
//		newsType.setId(15);
//		newsType.setName("图片新闻");
//		newsType.setText("图片新闻");
//		newsTypeList.add(newsType);
//		Adks_news_type newsType1 = new Adks_news_type();
//		newsType1.setId(16);
//		newsType1.setName("通知公告");
//		newsType1.setText("通知公告");
//		newsTypeList.add(newsType1);
//		Adks_news_type newsType2 = new Adks_news_type();
//		newsType2.setId(17);
//		newsType2.setName("培训动态");
//		newsType2.setText("培训动态");
//		newsTypeList.add(newsType2);
//		Adks_news_type newsType3 = new Adks_news_type();
//		newsType3.setId(18);
//		newsType3.setName("新闻资讯");
//		newsType3.setText("新闻资讯");
//		newsTypeList.add(newsType3);
		return newsTypeListP;
	}
}
