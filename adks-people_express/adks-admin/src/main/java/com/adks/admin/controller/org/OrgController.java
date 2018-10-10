package com.adks.admin.controller.org;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.ObjectUtils.Null;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.adks.admin.controller.log.LogController;
import com.adks.admin.util.LogUtil;
import com.adks.commons.oss.OSSUploadUtil;
import com.adks.commons.util.PropertiesFactory;
import com.adks.dubbo.api.data.log.Adks_log;
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.data.org.Adks_org_config;
import com.adks.dubbo.api.interfaces.admin.log.LogApi;
import com.adks.dubbo.api.interfaces.admin.org.OrgApi;
import com.adks.dubbo.commons.Page;

@Controller
@RequestMapping(value = "/org")
public class OrgController {
	private final Logger logger = LoggerFactory.getLogger(OrgController.class);

	@Autowired
	private OrgApi orgApi;
	@Autowired
	private LogApi logApi;

	private Integer parent;

	@RequestMapping({ "/orgList" })
	public String home(HttpServletRequest request, HttpServletResponse response, Model model, Integer parentId)
			throws IOException {
		logger.debug("进入 OrgController  orgList...");
		Map userMap=(Map)request.getSession().getAttribute("user");
		Integer userOrgId=MapUtils.getInteger(userMap, "orgId");
		boolean isFirst=false;
		if(parentId == null){
			isFirst=true;
		}
		boolean isSuperManager = "1".equals(userMap.get("isSuper")+"")?true:false;
		if (!isSuperManager) {
			parentId=parentId == null ?userOrgId:parentId;
		}else{
			parentId=parentId == null ?0:parentId;
		}
		Adks_org org = orgApi.getOrgById(parentId);
		Integer overParentId = 0;
		if (parentId != 0) {
			if (org != null && !isFirst) {
				overParentId = org.getParentId();
			}
			if(isFirst || (parentId == userOrgId && !isSuperManager)){
				overParentId=parentId;
			}
		}
		// 父级id
		model.addAttribute("parentId", parentId);
		model.addAttribute("parentName", org == null ? null : org.getName());
		// 上一个页面的父级if
		model.addAttribute("overParentId", overParentId);

		String ossPath = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
		model.addAttribute("ossResource", ossPath);
		return "org/orgList";
	}

	// 机构界面在使用
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getOrgListJson", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public Page getOrgListJson(HttpServletRequest request,HttpServletResponse response,Integer page, Integer rows, Integer parentId, String s_org_name) {
		Page<List<Map<String, Object>>> page_bean = null;
		Map userMap=(Map)request.getSession().getAttribute("user");
		try {
			page_bean = new Page<List<Map<String, Object>>>();
			page_bean.setCurrentPage(page);
			page_bean.setPageSize(rows);
			Map map = new HashMap<>();

			boolean isSuperManager = "1".equals(userMap.get("isSuper")+"")?true:false;
			if (!isSuperManager) {
				String orgCode = userMap.get("orgCode")+"";
				map.put("orgCode", orgCode);
			}

			if (parentId == null) {
				parentId = 0;
			}
			map.put("parentId", parentId);
			if (s_org_name != null) {
				s_org_name = java.net.URLDecoder.decode(s_org_name, "UTF-8");
				map.put("orgName", s_org_name);
			}
			page_bean.setMap(map);
			page_bean = orgApi.getOrgListPage(page_bean);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return page_bean;
	}

	/**
	 * 获取机构列表json数据,combotree
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/getOrgsJson", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public List<Adks_org> getUsersJson(HttpServletRequest request,HttpServletResponse response,Integer parentId) throws IOException {
		Map userMap=(Map)request.getSession().getAttribute("user");
		boolean isSuperManager = "1".equals(userMap.get("isSuper")+"")?true:false;
		 List<Adks_org> maps=new ArrayList<Adks_org>();
		List<Adks_org> list = null;
        if(parentId==null){
        	if(isSuperManager){
        		parentId=0;
        	}else{
        		parentId=Integer.parseInt(userMap.get("orgId")+"");
        	}
        }
        list=orgApi.getOrgsListByClass(parentId);
        if(parentId==0){
        	Adks_org org=new Adks_org();
        	org.setId(0);
        	org.setName("机构");
        	org.setText("机构");
        	org.setOrgId(0);
        	org.setOrgCode("0A");
        	org.setOrgName("机构");
        	org.setChildren(list);
        	maps.add(org);
        }else if(parentId==Integer.parseInt(userMap.get("orgId")+"")){
        	Adks_org org=orgApi.getOrgById(parentId);
        	org.setId(org.getOrgId());
        	org.setText(org.getOrgName());
        	org.setName(org.getOrgName());
        	org.setChildren(list);
        	maps.add(org);
        }else{
        	maps=list;
        }
		return maps;
	}

	/**
	 * 保存org
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/saveOrg", produces = { "text/html;charset=utf-8" })
	@ResponseBody
	public void saveOrgJson(HttpServletRequest request, HttpServletResponse response, Adks_org org) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String message = "error";
		try {
			if (org != null) {
				boolean flag = true;
				// 网校唯一标识
				if (org.getOrgName() != null) {
					Map<String, Object> temp = orgApi.getOrgByName(org.getOrgName());
					if (temp != null && temp.size() > 0
							&& ((org.getOrgId() != null && org.getOrgId() != MapUtils.getIntValue(temp, "orgId"))
									|| (org.getOrgId() == null))) {
						message = "snnameExists";
						flag = false;
					}
				}
				if (flag) {
					if (org.getParentId() == null) {
						org.setParentId(0);
					}
					if (org.getOrgId() != null) {
						// 查看机构的名称是否已经修改，若是要同步到用户
						Adks_org orgBefore = orgApi.getOrgById(org.getOrgId());
						if (!orgBefore.getOrgName().equals(org.getOrgName())) {
							orgApi.checkOrgNameToUser(org.getOrgId(), org.getOrgName());
						}
					} else {
						Map userMap=(Map)request.getSession().getAttribute("user");
						Integer userId = Integer.parseInt(userMap.get("userId")+"");
						String username = userMap.get("username")+"";
						org.setCreatorId(userId);
						org.setCreatorName(username);
						org.setCreatetime(new Date());
						org.setUsernum(0);
						org.setOrgstudylong(0);
					}
					Integer dataId=orgApi.saveOrg(org);
					//数据操作成功，保存操作日志
					if(dataId!=null&&dataId!=0){
						Map map=(Map)request.getSession().getAttribute("user");
						//添加/修改
						if(org.getOrgId()==null){
							logApi.saveLog(dataId, org.getOrgName(), LogUtil.LOG_ORG_MODULEID, LogUtil.LOG_ADD_TYPE, map);
						}else{
							logApi.saveLog(org.getOrgId(), org.getOrgName(), LogUtil.LOG_ORG_MODULEID, LogUtil.LOG_UPDATE_TYPE, map);
						}
					}
					message = "succ";
				}
			}
			PrintWriter pWriter = response.getWriter();
			pWriter.write(message.toString());
			pWriter.flush();
			pWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除机构信息
	 * 
	 * @param request
	 * @param response
	 * @param ids
	 */
	@RequestMapping(value = "/delOrg", method = RequestMethod.POST)
	public void delOrg(HttpServletRequest request, HttpServletResponse response, String ids) {
		response.setContentType("application/text;charset=utf-8");
		try {
			PrintWriter pWriter = response.getWriter();
			if (ids != null) {
				boolean flag = true;
				String[] id = ids.split(",");
				String  parentIdsRedis="";
				for (String orgId : id) {
					Integer oId = Integer.parseInt(orgId);
					Adks_org org = orgApi.getOrgById(oId);
					parentIdsRedis+=org.getParentId()+",";
					flag = orgApi.checkOrgHasUser(org.getOrgCode());
					if (!flag) {
						break;
					}
				}
				parentIdsRedis+=ids;
				if (flag) {
					String orgIds = "";
					for (String orgId : id) {
						Integer oId = Integer.parseInt(orgId);
						List<Adks_org> orgLists = orgApi.getOrgsListByParent(oId);
						if (orgLists != null) {
							for (Adks_org org : orgLists) {
								if (StringUtils.isNotEmpty(orgIds))
									orgIds += "," + org.getOrgId();
								else
									orgIds += org.getOrgId();
							}
						}

						if (StringUtils.isNotEmpty(orgIds))
							orgIds += "," + oId;
						else
							orgIds += oId;

					}
					if (!"".equals(orgIds)) {
						orgIds.substring(0, orgIds.length() - 1);
						//清除首页排行的缓存
						orgApi.deleteOrgTopRedis(parentIdsRedis);
						orgApi.deleteOrgByIds(orgIds);
						orgApi.deleteOrgCinfig(orgIds);
						Map map=(Map)request.getSession().getAttribute("user");
						String[] orgIdss=orgIds.split(",");
						LogController util=new LogController();
						for (String orgId : orgIdss) {
							//记录操作信息
							logApi.saveLog(Integer.valueOf(orgId), null, LogUtil.LOG_ORG_MODULEID, LogUtil.LOG_DELETE_TYPE, map);
						}
					}
					pWriter.write("succ");
				} else {
					pWriter.write("hasUser");
				}
			}
			pWriter.flush();
			pWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查看机构配置信息
	 * 
	 * @param request
	 * @param response
	 * @param ids
	 */
	@RequestMapping(value = "/getOrgConfig", method = RequestMethod.POST)
	@ResponseBody
	public Adks_org_config getOrgConfig(Integer orgId) {
		 Adks_org_config orgConfig = orgApi.getOrgConfigByOrgId(orgId);
		return orgConfig;
	}

	/**
	 * 删除机构配置信息（目前没有使用到）
	 * 
	 * @param request
	 * @param response
	 * @param ids
	 */
	@RequestMapping(value = "/delOrgConfig", method = RequestMethod.POST)
	public void delOrgConfig(HttpServletRequest request, HttpServletResponse response, String ids) {
		response.setContentType("application/text;charset=utf-8");
		try {
			if (ids != null) {
				orgApi.deleteOrgCinfig(ids);
				String[] orgConfigIds=ids.split(",");
				Map map=(Map)request.getSession().getAttribute("user");
				for (String orgConfigId : orgConfigIds) {
					//数据操作成功，保存操作日志
					logApi.saveLog(Integer.valueOf(orgConfigId), null, LogUtil.LOG_ORGCONFIG_MODULEID, LogUtil.LOG_DELETE_TYPE, map);
				}
			}
			PrintWriter pWriter = response.getWriter();
			pWriter.write("succ");
			pWriter.flush();
			pWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存机构配置信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/saveOrgConfig", produces = { "text/html;charset=utf-8" })
	@ResponseBody
	public void saveOrgConfigJson(HttpServletRequest request, HttpServletResponse response, Adks_org_config orgConfig,
			@RequestParam(value = "orgLogoPathfile", required = false) MultipartFile orgLogoPathfile,
			@RequestParam(value = "orgBannerfile", required = false) MultipartFile orgBannerfile) throws IOException {
		response.setContentType("text/html;charset=utf-8");

		// 机构Logo图片存储地址
		String orgImgPath = PropertiesFactory.getProperty("ossConfig.properties", "oss.OrgImg_Path");
		String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");

		String message = "error";
		try {
			if (orgConfig != null) {

				// 上传机构Logo
				if (orgLogoPathfile != null) {
					if (StringUtils.isNotEmpty(orgLogoPathfile.getOriginalFilename())) {
						// 将MultipartFile转换成文件流
						//CommonsMultipartFile cf = (CommonsMultipartFile) orgLogoPathfile;
						//DiskFileItem fi = (DiskFileItem) cf.getFileItem();
						//File file = fi.getStoreLocation();
						InputStream is=orgLogoPathfile.getInputStream();
						String type = orgLogoPathfile.getOriginalFilename();
						type = type.substring(type.lastIndexOf(".") + 1, type.length());
						if (orgConfig.getOrgLogoPath() == null || "".equals(orgConfig.getOrgLogoPath())) {
							// 上传图片
							String new_Path = OSSUploadUtil.uploadFileNewName(is, type, orgImgPath);
							String[] paths = new_Path.split("/");
							String code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
							orgConfig.setOrgLogoPath(code);
						} else if (orgConfig.getOrgLogoPath() != null && !"".equals(orgConfig.getOrgLogoPath())) {
							String new_Path = OSSUploadUtil.replaceFile(is, type, ossResource + orgConfig.getOrgLogoPath(), orgImgPath);
							//String new_Path = OSSUploadUtil.updateFile(is, type,ossResource + orgConfig.getOrgLogoPath());
							String[] paths = new_Path.split("/");
							String code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
							orgConfig.setOrgLogoPath(code);
						}
					} 
				}
				// 首页Banner
				if (orgBannerfile != null) {
					if (StringUtils.isNotEmpty(orgBannerfile.getOriginalFilename())) {
						// 将MultipartFile转换成文件流
						//CommonsMultipartFile cf = (CommonsMultipartFile) orgBannerfile;
						//DiskFileItem fi = (DiskFileItem) cf.getFileItem();
						//File file = fi.getStoreLocation();
						InputStream is=orgBannerfile.getInputStream();
						String type = orgBannerfile.getOriginalFilename();
						type = type.substring(type.lastIndexOf(".") + 1, type.length());
						if (orgConfig.getOrgBanner() == null || "".equals(orgConfig.getOrgBanner())) {
							// 上传图片
							String new_Path = OSSUploadUtil.uploadFileNewName(is, type, orgImgPath);
							String[] paths = new_Path.split("/");
							String code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
							orgConfig.setOrgBanner(code);
						} else if (orgConfig.getOrgBanner() != null && !"".equals(orgConfig.getOrgBanner())) {
							//String new_Path = OSSUploadUtil.updateFile(is, type,ossResource + orgConfig.getOrgBanner());
							String new_Path = OSSUploadUtil.replaceFile(is, type, ossResource + orgConfig.getOrgBanner(), orgImgPath);
							String[] paths = new_Path.split("/");
							String code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
							orgConfig.setOrgBanner(code);
						}
					}
				}
				boolean flag = true;
				if (flag) {
					if (orgConfig.getOrgId() != null) {
						Adks_org org=orgApi.getOrgById(orgConfig.getOrgId());
						orgConfig.setOrgName(org.getOrgName());
						Integer dataId=orgApi.saveOrgConfig(orgConfig);
						//数据操作成功，保存操作日志
						if(dataId!=null&&dataId!=0){
							Map map=(Map)request.getSession().getAttribute("user");
							logApi.saveLog(dataId, null, LogUtil.LOG_ORGCONFIG_MODULEID, LogUtil.LOG_UPDATE_TYPE, map);
						}
						message = "succ";
					}
				}
			}
			PrintWriter pWriter = response.getWriter();
			pWriter.write(message.toString());
			pWriter.flush();
			pWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
