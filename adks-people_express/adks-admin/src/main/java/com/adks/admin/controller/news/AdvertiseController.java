package com.adks.admin.controller.news;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
import com.adks.admin.util.LogUtil;
import com.adks.commons.oss.OSSUploadUtil;
import com.adks.commons.util.PropertiesFactory;
import com.adks.dubbo.api.data.common.Adks_advertise;
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.interfaces.admin.log.LogApi;
import com.adks.dubbo.api.interfaces.admin.news.AdvertiseApi;
import com.adks.dubbo.api.interfaces.admin.org.OrgApi;
import com.adks.dubbo.commons.Page;

@Controller
@RequestMapping(value = "/advertise")
public class AdvertiseController {
	private final Logger logger = LoggerFactory.getLogger(NewsController.class);

	@Resource
	private AdvertiseApi advertiseApi;

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
	 * 页面跳转，进入新闻管理界面，仅仅是页面跳转，没有任何业务逻辑
	 * 
	 * @return String
	 * @date 2017-03-08
	 */
	@RequestMapping({ "/toAdvertiseList" })
	public String advertiseList(Model model) {
		logger.debug("进入advertiseList controller...");
		model.addAttribute("org_sn_name", org_sn_name);

		String ossPath = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
		model.addAttribute("ossResource", ossPath);

		return "/news/advertiseList";
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
	@RequestMapping(value = "/getAdvertiseListJson", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public Page getAdvertiseListJson(HttpServletRequest request, HttpServletResponse response, Integer page,
			Integer rows, Integer orgId) {

		Page<List<Map<String, Object>>> paramPage = new Page<List<Map<String, Object>>>();
		// 模糊查询 的参数
		Map userMap = (Map) request.getSession().getAttribute("user");

		Map likemap = new HashMap();
		boolean isSuperManager = "1".equals(userMap.get("isSuper") + "") ? true : false;
		if (!isSuperManager) {
			String orgCode = userMap.get("orgCode") + "";
			likemap.put("orgCode", orgCode);
		}
		if (orgId != null && !"".equals(orgId) && orgId != 0) {
			Adks_org org = orgApi.getOrgById(orgId);
			likemap.remove("orgCode");
			likemap.put("orgCode", org.getOrgCode());
		} else if (orgId != null && !"".equals(orgId) && orgId == 0) {
			likemap.remove("orgCode");
			likemap.put("orgCode", "0A");
		}
		paramPage.setMap(likemap);
		paramPage.setPageSize(rows);
		paramPage.setCurrentPage(page);

		// 调用dubbo服务获取map数据
		Page<List<Map<String, Object>>> pageNew = advertiseApi.getAdvertiseListPage(paramPage);
		List<Map<String, Object>> aList = pageNew.getRows();
		for (Map<String, Object> map : aList) {
//			System.out.println(map.toString());
			Date date = (Date) map.get("createTime");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String createTimeStr = sdf.format(date);
			map.put("createTimeStr", createTimeStr);
		}

		return pageNew;
	}

	/**
	 * 保存、修改广告位
	 * 
	 * @return 2017-03-08
	 */
	@RequestMapping(value = "/saveAdvertise", method = RequestMethod.POST)
	public void saveAdvertise(Adks_advertise adksAdvertise, Integer orgId, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "adImgPathFile", required = false) MultipartFile adImgPathFile) {
		response.setContentType("text/html;charset=utf-8");
		String newsImgPath = PropertiesFactory.getProperty("ossConfig.properties", "oss.NewsImg_Path");
		String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
		if (adksAdvertise != null) {
			if (adImgPathFile != null) {
				if (StringUtils.isNotEmpty(adImgPathFile.getOriginalFilename())) {
					// 获取文件类型
					try {
						String fileType = adImgPathFile.getContentType();
						String[] types = fileType.split("/");
						// 将MultipartFile转换成文件流
						// CommonsMultipartFile cf = (CommonsMultipartFile)
						// adImgPathFile;
						// DiskFileItem fi = (DiskFileItem) cf.getFileItem();
						// File file = fi.getStoreLocation();
						InputStream is = adImgPathFile.getInputStream();
						if (adksAdvertise.getAdImgPath() == null || "".equals(adksAdvertise.getAdImgPath())) {
							// 没有图片时上传图片
							String new_Path = OSSUploadUtil.uploadFileNewName(is, types[1], newsImgPath);
							String[] paths = new_Path.split("/");
							String code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
							adksAdvertise.setAdImgPath(code);
						} else if (adksAdvertise.getAdImgPath() != null && !"".equals(adksAdvertise.getAdImgPath())) {
							// 覆盖原有图片上传，更新文件只更新文件内容，不更新文件名称和文件路径
							// String new_Path = OSSUploadUtil.updateFile(is,
							// types[1],ossResource +
							// adksAdvertise.getAdImgPath());
							String new_Path = OSSUploadUtil.replaceFile(is, types[1],
									ossResource + adksAdvertise.getAdImgPath(), newsImgPath);
							String[] paths = new_Path.split("/");
							String code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
							adksAdvertise.setAdImgPath(code);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			adksAdvertise.setCreateTime(new Date());
			Map userMap = (Map) request.getSession().getAttribute("user");
			Integer creatorId = Integer.parseInt(userMap.get("userId") + "");
			String creatorName = userMap.get("username") + "";
			adksAdvertise.setCreatorId(creatorId);
			adksAdvertise.setCreatorName(creatorName);
			if (adksAdvertise.getOrgId() != null && !"".equals(adksAdvertise.getOrgId())) {
				Adks_org org = null;
				if (orgId != null) {
					org = orgApi.getOrgById(orgId);
				} else {
					org = orgApi.getOrgById(adksAdvertise.getOrgId());
				}
				adksAdvertise.setOrgId(org.getOrgId());
				adksAdvertise.setOrgName(org.getOrgName());
				adksAdvertise.setOrgCode(org.getOrgCode());
			}
			try {
				Integer dataId=advertiseApi.saveAdvertise(adksAdvertise);
				//数据操作成功，保存操作日志
				if(dataId!=null&&dataId!=0){
					//添加/修改 
					if(adksAdvertise.getAdId()==null){  //添加
						logApi.saveLog(dataId,adksAdvertise.getOrgName(),LogUtil.LOG_ADVERTISE_MODULEID,LogUtil.LOG_ADD_TYPE,userMap);
					}else{  //修改
						logApi.saveLog(adksAdvertise.getAdId(),adksAdvertise.getOrgName(),LogUtil.LOG_ADVERTISE_MODULEID,LogUtil.LOG_UPDATE_TYPE,userMap);
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
	 * 删除广告位
	 * 
	 * @param newsIds
	 * @param request
	 * @param response
	 *            2017-03-08
	 */
	@RequestMapping(value = "/deleteAdvertise", method = RequestMethod.GET)
	public void deleteAdvertise(String adIds, String orgIds, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/text;charset=utf-8");
		try {
			System.out.println(adIds + "=======================");
			if (adIds != null && adIds.length() > 0) {
				String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
				Map map=(Map)request.getSession().getAttribute("user");
				String[] adIdArr = adIds.split(",");
				for (int i = 0; i < adIdArr.length; i++) {
					Integer adId = Integer.valueOf(adIdArr[i]);
					System.out.println(adId);
					Map<String, Object> adMap = advertiseApi.getAdvertiseInfoById(adId);
					if (adMap != null) {
						String adImgPath = (String) adMap.get("adImgPath");
						if (adImgPath != null) {
							String adImgPathPath = ossResource+adImgPath;
							OSSUploadUtil.deleteFile(adImgPathPath);
						}
					}
					//数据操作成功，保存操作日志
					if(adId!=null){
						logApi.saveLog(adId,null,LogUtil.LOG_ADVERTISE_MODULEID, LogUtil.LOG_DELETE_TYPE, map);
					}
				}
				Map<String, Object> userMap = (Map<String, Object>) request.getSession().getAttribute("user");
				Map<String, Object> pMap = new HashMap<String, Object>();
				pMap.put("delAdIds", adIds);
				pMap.put("delOrgIds", orgIds);
				pMap.put("orgId", MapUtils.getIntValue(userMap, "orgId"));
				if (adIds != null && adIds.length() > 0) {
					advertiseApi.deleteAdvertiseByIds(pMap);
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