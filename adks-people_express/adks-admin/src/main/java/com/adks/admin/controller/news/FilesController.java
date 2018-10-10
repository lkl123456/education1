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
import com.adks.dubbo.api.data.common.Adks_files;
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.interfaces.admin.grade.GradeApi;
import com.adks.dubbo.api.interfaces.admin.log.LogApi;
import com.adks.dubbo.api.interfaces.admin.news.FilesApi;
import com.adks.dubbo.api.interfaces.admin.org.OrgApi;
import com.adks.dubbo.commons.Page;

@Controller
@RequestMapping(value = "/files")
public class FilesController {

	private final Logger logger = LoggerFactory.getLogger(FilesController.class);

	@Resource
	private FilesApi filesApi;

	private String org_sn_name;

	@Autowired
	private OrgApi orgApi;
	@Autowired
	private GradeApi gradeApi;
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
	 * 页面跳转，进入教参管理，仅仅是页面跳转，没有任何业务逻辑
	 * 
	 * @return String
	 * @date 2017-03-08
	 */
	@RequestMapping({ "/toFilesList" })
	public String filesList(Model model) {
		logger.debug("进入filesList controller...");
		model.addAttribute("org_sn_name", org_sn_name);
		return "/news/filesList";
	}

	/**
	 * 教参管理主界面，获取教参闻列表json数据
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
	@RequestMapping(value = "/getFilesListJson", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public Page getFilesListJson(HttpServletRequest request,HttpServletResponse response,String filesName, Integer page, Integer rows, Integer orgId) {
		Page<List<Map<String, Object>>> paramPage = new Page<List<Map<String, Object>>>();
		try {
			// 模糊查询 的参数
			Map userMap=(Map)request.getSession().getAttribute("user");
			
			Map likemap = new HashMap();
			if (filesName != null && !"".equals(filesName)) {
				filesName = java.net.URLDecoder.decode(filesName, "UTF-8");
				likemap.put("filesName", filesName);
			}
			boolean isSuperManager ="1".equals(userMap.get("isSuper")+"")?true:false;
			if (!isSuperManager) {
				String orgCode =userMap.get("orgCode")+"";
				likemap.put("orgCode", orgCode);
			}
			if (orgId != null && !"".equals(orgId) && orgId!=0) {
				Adks_org org = orgApi.getOrgById(orgId);
				likemap.remove("orgCode");
				likemap.put("orgCode", org.getOrgCode());
			}else if(orgId != null && !"".equals(orgId) && orgId ==0){
				likemap.remove("orgCode");
				likemap.put("orgCode", "0A");
			}
			paramPage.setMap(likemap);
			paramPage.setPageSize(rows);
			paramPage.setCurrentPage(page);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 调用dubbo服务获取map数据
		Page<List<Map<String, Object>>> pageNew = filesApi.getFilesListPage(paramPage);
		List<Map<String, Object>> aList = pageNew.getRows();
		for (Map<String, Object> map : aList) {
			Date date = (Date) map.get("createTime");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String createTimeStr = sdf.format(date);
			map.put("createTimeStr", createTimeStr);
		}
		return pageNew;
	}

	/**
	 * 保存教参
	 * 
	 * @return 2017-03-09
	 */
	@RequestMapping(value = "/saveFiles", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveFiles(Adks_files adksFiles,Integer orgId,HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "filesUrlFile", required = false) MultipartFile filesUrlFile) {
		response.setContentType("text/html;charset=utf-8");
		String downloadPath = PropertiesFactory.getProperty("ossConfig.properties", "oss.Download_Path");
		String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
		if (adksFiles != null) {
			Map<String,Object> checkMap=new HashMap<String,Object>();
			if(adksFiles.getGradeId()!=null){
				checkMap.put("gradeId", adksFiles.getGradeId());
			}
			if(adksFiles.getFilesId()!=null){
				checkMap.put("filesId", adksFiles.getFilesId());
			}
			checkMap.put("filesName", adksFiles.getFilesName());
			Map<String, Object> files=filesApi.checkGradeFilesName(checkMap);
			if(files.size()<=0){
				if (filesUrlFile != null) {
					if (StringUtils.isNotEmpty(filesUrlFile.getOriginalFilename())) {
						try {
							// 获取文件类型
							String fileType = filesUrlFile.getOriginalFilename().substring(
									(filesUrlFile.getOriginalFilename().lastIndexOf(".") + 1),
									filesUrlFile.getOriginalFilename().length());
							// 将MultipartFile转换成文件流
							//CommonsMultipartFile cf = (CommonsMultipartFile) filesUrlFile;
							//DiskFileItem fi = (DiskFileItem) cf.getFileItem();
							//File file = fi.getStoreLocation();
							InputStream is=filesUrlFile.getInputStream();
							if (adksFiles.getFilesUrl() == null || "".equals(adksFiles.getFilesUrl())) {
								// 没有图片时上传图片
								String new_Path = OSSUploadUtil.uploadFileNewName(is, fileType, downloadPath);
								String[] paths = new_Path.split("/");
								String code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
								adksFiles.setFilesUrl(code);
							} else if (adksFiles.getFilesUrl() != null && !"".equals(adksFiles.getFilesUrl())) {
								// 覆盖原有图片上传，更新文件只更新文件内容，不更新文件名称和文件路径
								String new_Path = OSSUploadUtil.replaceFile(is, fileType, ossResource + adksFiles.getFilesUrl(), downloadPath);
								String[] paths = new_Path.split("/");
								String code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
								adksFiles.setFilesUrl(code);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						if (adksFiles.getFilesId() != 0) {
							Map<String, Object> map = filesApi.getFilesInfoById(adksFiles.getFilesId());
							adksFiles.setFilesUrl(MapUtils.getString(map, "filesUrl"));
						}
					}
				}
				adksFiles.setCreateTime(new Date());
				Map userMap=(Map)request.getSession().getAttribute("user");
				Integer creatorId = Integer.parseInt(userMap.get("userId")+"");
				String creatorName = userMap.get("username")+"";
				adksFiles.setCreatorId(creatorId);
				adksFiles.setCreatorName(creatorName);
				if (orgApi!= null && !"".equals(orgApi)) {
					Adks_org org = orgApi.getOrgById(orgId);
					adksFiles.setOrgId(orgId);
					adksFiles.setOrgName(org.getOrgName());
					adksFiles.setOrgCode(org.getOrgCode());
				}
				if(adksFiles.getGradeId()!=null&&!"".equals(adksFiles.getGradeId())){
					Map<String, Object> grade=gradeApi.getGradeByGradeId(adksFiles.getGradeId());
					adksFiles.setGradeId((Integer) grade.get("gradeId"));
					adksFiles.setGradeName((String) grade.get("gradeName"));
				}
				
				Integer dataId=filesApi.saveFiles(adksFiles);
				//数据操作成功，保存操作日志
				if(dataId!=null&&dataId!=0){
					//添加/修改 
					if(adksFiles.getFilesId()==null){  //添加
						logApi.saveLog(dataId,adksFiles.getFilesName(),LogUtil.LOG_DATUM_MODULEID,LogUtil.LOG_ADD_TYPE,userMap);
					}else{  //修改
						logApi.saveLog(adksFiles.getFilesId(),adksFiles.getFilesName(),LogUtil.LOG_DATUM_MODULEID,LogUtil.LOG_UPDATE_TYPE,userMap);
					}
				}
				Map<String, Object> resMap = new HashMap<String,Object>();
				resMap.put("mesg", "succ");
				return resMap;
			}else{
				Map<String, Object> resMap = new HashMap<String,Object>();
				resMap.put("mesg", "sameName");
				return resMap;
			}
		}
		return null;
	}

	/**
	 * 删除教参
	 * 
	 * @param filesIds
	 * @param request
	 * @param response
	 *            2017-03-09
	 */
	@RequestMapping(value = "/deleteFiles", method = RequestMethod.GET)
	public void deleteFiles(String filesIds, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/text;charset=utf-8");
		String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
		try {
			System.out.println(filesIds + "=======================");
			Map map=(Map)request.getSession().getAttribute("user");
			if (filesIds != null && filesIds.length() > 0) {
				String[] filesIdArr = filesIds.split(",");
				for (int i = 0; i < filesIdArr.length; i++) {
					Integer filesId = Integer.valueOf(filesIdArr[i]);
					System.out.println(filesId);
					Map<String, Object> filesMap = filesApi.getFilesInfoById(filesId);
					if (filesMap != null) {
						String filesUrl = (String) filesMap.get("filesUrl");
						if (filesUrl != null) {
							String filesUrlPath = ossResource + filesUrl;
							OSSUploadUtil.deleteFile(filesUrlPath);
						}
					}
					//数据操作成功，保存操作日志
					if(filesId!=null){
						logApi.saveLog(filesId,null,LogUtil.LOG_DATUM_MODULEID, LogUtil.LOG_DELETE_TYPE, map);
					}
				}
				filesApi.deleteFilesByIds(filesIds);
			}
			PrintWriter pWriter = response.getWriter();
			pWriter.write("succ");
			pWriter.flush();
			pWriter.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @Title checkGradeFilesName
	 * @Description:检查班级资料名在该班级中的唯一性
	 * @author xrl
	 * @Date 2017年5月9日
	 * @param filesName
	 * @param gradeId
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/checkGradeFilesName", method = RequestMethod.POST)
	public void checkGradeFilesName(String filesName,Integer gradeId,HttpServletRequest request,HttpServletResponse response) {
		response.setContentType("application/text;charset=utf-8");
		String result = "";
		try {
			Map<String, Object> selMap = new HashMap<String, Object>();
			filesName = java.net.URLDecoder.decode(filesName, "UTF-8");
			selMap.put("filesName", filesName);
			if(gradeId!=null&&!"".equals(gradeId)){
				selMap.put("gradeId", gradeId);
			}
			//暂时注掉
			Map map=(Map)request.getSession().getAttribute("user");
//			Integer orgId = (Integer)map.get("orgId");
			String orgCode = (String)map.get("orgCode");
//			selMap.put("orgId", orgId);
			selMap.put("orgCode", orgCode);
			Map<String, Object> gradeMap = filesApi.checkGradeFilesName(selMap);
			PrintWriter pWriter = response.getWriter();
			if (gradeMap.size() > 0) {
				pWriter.write("succ");
			} else {
				pWriter.write("false");
			}
			pWriter.flush();
			pWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
