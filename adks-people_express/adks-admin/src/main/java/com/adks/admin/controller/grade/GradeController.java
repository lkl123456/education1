package com.adks.admin.controller.grade;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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

import com.adks.admin.util.LogUtil;
import com.adks.commons.oss.OSSUploadUtil;
import com.adks.commons.util.DateUtils;
import com.adks.commons.util.PropertiesFactory;
import com.adks.dubbo.api.data.grade.Adks_grade;
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.interfaces.admin.grade.GradeApi;
import com.adks.dubbo.api.interfaces.admin.log.LogApi;
import com.adks.dubbo.api.interfaces.admin.org.OrgApi;
import com.adks.dubbo.api.interfaces.admin.role.UserroleApi;
import com.adks.dubbo.commons.Page;

/**
 * 
 * ClassName GradeController
 * 
 * @Description：班级
 * @author xrl
 * @Date 2017年3月22日
 */
@Controller
@RequestMapping(value = "/grade")
public class GradeController {

	private final Logger logger = LoggerFactory.getLogger(GradeController.class);
	@Resource
	private GradeApi gradeApi;
	@Autowired
	private UserroleApi userroleApi;
	@Autowired
	private OrgApi orgApi;
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
	@RequestMapping({ "/toGradeList" })
	public String toGradeList(Model model) {
		logger.debug("@@@@@@跳转到班级列表界面");
		String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
		model.addAttribute("ossResource", ossResource);
		return "/grade/gradeList";
	}

	/**
	 * 
	 * @Title getDeptsJson
	 * @Description：根据orgCode获取机构
	 * @author xrl
	 * @Date 2017年4月28日
	 * @param OrgCode
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getGradesJson", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public List<Adks_grade> getDeptsJson(Integer orgId, HttpServletRequest request, HttpServletResponse response) {
		Adks_org org = orgApi.getOrgById(orgId);
		Map likemap = new HashMap();
		if (org != null) {
			likemap.put("orgCode", org.getOrgCode());
		}
		Map userMap = (Map) request.getSession().getAttribute("user");
		Integer userId = Integer.parseInt(userMap.get("userId") + "");
		String orgCode = userMap.get("orgCode")+"";
		boolean isRoot = "1".equals(userMap.get("isSuper") + "") ? true : false;
		List<Map<String, Object>> userRole = userroleApi.getRoleListByUser(userId);
		Integer roleId = (Integer) userRole.get(0).get("roleId");
		if (!isRoot) {
			if(roleId==5||"5".equals(roleId)){
				likemap.put("userId", userId);
			}else{
				likemap.put("orgCode", orgCode);
			}
		}
		List<Adks_grade> maps = gradeApi.getGradesJson(likemap);
		return maps;
	}

	/**
	 * 
	 * @Title getGradesList
	 * @Description:班级列表主页面，获取列表json数据
	 * @author xrl
	 * @Date 2017年3月22日
	 * @param gradeName
	 * @param page
	 * @param rows
	 * @param categoryCode
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getGradeListJson", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public Page getGradeListJson(HttpServletRequest request, HttpServletResponse response, String gradeName,
			Integer page, Integer rows, Model model) {
		System.out.println("gradeName:" + gradeName + "page:" + page + "rows:" + rows);
		Page<List<Map<String, Object>>> paramPage = new Page<List<Map<String, Object>>>();
		try {
			// 模糊查询 的参数
			Map likemap = new HashMap();
			if (gradeName != null && !"".equals(gradeName)) {
				gradeName = java.net.URLDecoder.decode(gradeName, "UTF-8");
				likemap.put("gradeName", gradeName);
			}
			Map userMap = (Map) request.getSession().getAttribute("user");
			// Integer userId = ObjectUtil.getUserIde(redisCient.get("user"));
			// String orgCode = ObjectUtil.getOrgCode(redisCient.get("user"));
			// boolean isRoot =
			// ObjectUtil.idSuperManager(redisCient.get("user"));
			Integer userId = Integer.parseInt(userMap.get("userId") + "");
			String orgCode = userMap.get("orgCode") + "";
			boolean isRoot = "1".equals(userMap.get("isSuper") + "") ? true : false;
			List<Map<String, Object>> userRole = userroleApi.getRoleListByUser(userId);
			Integer roleId = (Integer) userRole.get(0).get("roleId");
			if (!isRoot) {
				if (roleId == 5 || "5".equals(roleId)) {
					likemap.put("userId", userId);
				} else {
					likemap.put("orgCode", orgCode);
				}
			}
			paramPage.setMap(likemap);
			paramPage.setPageSize(rows);
			paramPage.setCurrentPage(page);

			paramPage = gradeApi.getGradeListPage(paramPage);
			model.addAttribute("ossResource", PropertiesFactory.getProperty("ossConfig.properties", "oss.resource"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return paramPage;
	}

	/**
	 * 
	 * @Title saveGrade
	 * @Description：保存班级
	 * @author xrl
	 * @Date 2017年3月22日
	 * @param grade
	 * @param begin_time_str
	 * @param end_time_str
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveGrade", method = RequestMethod.POST, produces = { "text/html;charset=utf-8" })
	@ResponseBody
	public Map<String, Object> saveGrade(Adks_grade grade, String s_startDate_str, String s_endDate_str,
			HttpServletRequest request,
			@RequestParam(value = "gradeImgfile", required = false) MultipartFile gradeImgfile,
			@RequestParam(value = "certificateImgfile", required = false) MultipartFile certificateImgfile,
			@RequestParam(value = "eleSealfile", required = false) MultipartFile eleSealfile) {
		// 班级图片存储地址
		String gradeImgPath = PropertiesFactory.getProperty("ossConfig.properties", "oss.GradeImg_Path");
		String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
		if (grade != null && grade.getGradeName() != null) {
			Map map = (Map) request.getSession().getAttribute("user");
			Integer orgId = (Integer) map.get("orgId");
			String orgCode = (String) map.get("orgCode");
			Map<String, Object> checkMap=new HashMap<String,Object>();
			if(grade.getGradeId()!=null){
				checkMap.put("gradeId", grade.getGradeId());
			}
			if(orgCode!=null){
				checkMap.put("orgCode", orgCode);
			}
			checkMap.put("gradeName", grade.getGradeName());
			Map<String, Object> gradeMap=gradeApi.checkGradeName(checkMap);
			if(gradeMap.size()<=0){
				// 上传班级图片
				if (gradeImgfile != null) {
					if (StringUtils.isNotEmpty(gradeImgfile.getOriginalFilename())) {
						// 将MultipartFile转换成文件流
						// CommonsMultipartFile cf = (CommonsMultipartFile)
						// gradeImgfile;
						// DiskFileItem fi = (DiskFileItem) cf.getFileItem();
						// File file = fi.getStoreLocation();
						try {
							InputStream GradeImgIs = gradeImgfile.getInputStream();
							// 获取文件类型
							String fileType = gradeImgfile.getOriginalFilename();
							fileType = fileType.substring(fileType.lastIndexOf(".") + 1, fileType.length());

							if (grade.getGradeImg() == null || "".equals(grade.getGradeImg())) {
								// 上传图片
								String new_Path = OSSUploadUtil.uploadFileNewName(GradeImgIs, fileType, gradeImgPath);
								String[] paths = new_Path.split("/");
								String code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
								grade.setGradeImg(code);
							} else if (grade.getGradeImg() != null && !"".equals(grade.getGradeImg())) {
								String new_Path = OSSUploadUtil.replaceFile(GradeImgIs, fileType,ossResource + grade.getGradeImg(),gradeImgPath);
								String[] paths = new_Path.split("/");
								String code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
								grade.setGradeImg(code);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						if (grade.getGradeId() != null && grade.getGradeId() != 0)
							grade.setGradeImg((String) gradeApi.getGradeByGradeId(grade.getGradeId()).get("gradeImg"));
					}
				}
				// 上传证书图片
				if (certificateImgfile != null) {
					if (StringUtils.isNotEmpty(certificateImgfile.getOriginalFilename())) {
						// 将MultipartFile转换成文件流
						// CommonsMultipartFile cf = (CommonsMultipartFile)
						// certificateImgfile;
						// DiskFileItem fi = (DiskFileItem) cf.getFileItem();
						// File file = fi.getStoreLocation();
						try {
							InputStream is = certificateImgfile.getInputStream();
							// 获取文件类型
							String fileType = certificateImgfile.getOriginalFilename();
							fileType = fileType.substring(fileType.lastIndexOf(".") + 1, fileType.length());

							if (grade.getCertificateImg() == null || "".equals(grade.getCertificateImg())) {
								// 上传图片
								String new_Path = OSSUploadUtil.uploadFileNewName(is, fileType, gradeImgPath);
								String[] paths = new_Path.split("/");
								String code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
								grade.setCertificateImg(code);
							} else if (grade.getCertificateImg() != null && !"".equals(grade.getCertificateImg())) {
								String new_Path = OSSUploadUtil.replaceFile(is, fileType, ossResource + grade.getGradeImg(),gradeImgPath);
								String[] paths = new_Path.split("/");
								String code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
								grade.setCertificateImg(code);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						if (grade.getGradeId() != null && grade.getGradeId() != 0)
							grade.setCertificateImg(
									(String) gradeApi.getGradeByGradeId(grade.getGradeId()).get("certificateImg"));
					}
				}
				// 上传电子章图片
				if (eleSealfile != null) {
					if (StringUtils.isNotEmpty(eleSealfile.getOriginalFilename())) {
						// 将MultipartFile转换成文件流
						// CommonsMultipartFile cf = (CommonsMultipartFile)
						// eleSealfile;
						// DiskFileItem fi = (DiskFileItem) cf.getFileItem();
						// File file = fi.getStoreLocation();
						try {
							InputStream is = eleSealfile.getInputStream();
							// 获取文件类型
							String fileType = eleSealfile.getOriginalFilename();
							fileType = fileType.substring(fileType.lastIndexOf(".") + 1, fileType.length());

							if (grade.getEleSeal() == null || "".equals(grade.getEleSeal())) {
								// 上传图片
								String new_Path = OSSUploadUtil.uploadFileNewName(is, fileType, gradeImgPath);
								String[] paths = new_Path.split("/");
								String code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
								grade.setEleSeal(code);
							} else if (grade.getEleSeal() != null && !"".equals(grade.getEleSeal())) {
								String new_Path = OSSUploadUtil.replaceFile(is, fileType, ossResource + grade.getGradeImg(),gradeImgPath);
								String[] paths = new_Path.split("/");
								String code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
								grade.setEleSeal(code);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						if (grade.getGradeId() != null && grade.getGradeId() != 0)
							grade.setEleSeal((String) gradeApi.getGradeByGradeId(grade.getGradeId()).get("eleSeal"));
					}
				}
				if (s_startDate_str != null) {
					grade.setStartDate(DateUtils.getStr2LDate(s_startDate_str));
				}
				if (s_endDate_str != null) {
					grade.setEndDate(DateUtils.getStr2LDate(s_endDate_str));
				}
				grade.setCreateTime(new Date());

				
				Integer creatorId = Integer.parseInt(map.get("userId") + "");
				String creatorName = (String) map.get("username");
				String orgName = (String) map.get("orgName");
				grade.setCreatorId(creatorId);
				grade.setCreatorName(creatorName);
				grade.setIsRegisit(1);
				grade.setOrgId(orgId);
				grade.setOrgName(orgName);
				grade.setOrgCode(orgCode);
				grade.setUserNum(0);
				grade.setOptionalNum(0);
				grade.setRequiredNum(0);
				if(grade.getRequiredPeriod()!=null&&grade.getOptionalPeriod()!=null){
					grade.setGraduationDesc("必修学时需修满："+grade.getRequiredPeriod()+";选修学时需修满："+grade.getOptionalPeriod());
				}else if(grade.getRequiredPeriod()!=null){
					grade.setGraduationDesc("必修学时需修满："+grade.getRequiredPeriod());
				}else if(grade.getOptionalPeriod()!=null){
					grade.setGraduationDesc("选修学时需修满："+grade.getOptionalPeriod());
				}else if(grade.getRequiredPeriod()==null&&grade.getOptionalPeriod()==null){
					grade.setGraduationDesc(grade.getGraduationDesc());
				}
				// if(grade.getGradeState()==19){
				// grade.setGradeState(1);
				// }else{
				// grade.setGradeState(2);
				// }
				Integer gid = gradeApi.saveGrade(grade);
				//数据操作成功，保存操作日志
				if(gid!=null&&gid!=0){
					//添加/修改 
					if(grade.getGradeId()==null){  //添加
						logApi.saveLog(gid, grade.getGradeName(),LogUtil.LOG_GRADE_MODULEID,LogUtil.LOG_ADD_TYPE,map);
					}else{  //修改
						logApi.saveLog(grade.getGradeId(), grade.getGradeName(),LogUtil.LOG_GRADE_MODULEID,LogUtil.LOG_UPDATE_TYPE,map);
					}
				}
				Map<String, Object> resMap = new HashMap<>();
				resMap.put("mesg", "succ");
				resMap.put("gId", gid);
				return resMap;
			}else{
				Map<String, Object> resMap = new HashMap<>();
				resMap.put("mesg", "sameGradeName");
				return resMap;
			}
			
		}
		return null;

	}

	/**
	 * 
	 * @Title delGrade
	 * @Description：删除班级
	 * @author xrl
	 * @Date 2017年4月27日
	 * @param gradeIds
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/delGrade", method = RequestMethod.GET)
	public void delGrade(String gradeIds, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/text;charset=utf-8");
		try {
			if (gradeIds != null && gradeIds.length() > 0) {
				String[] gradeIdArr = gradeIds.split(",");
				Map map = (Map) request.getSession().getAttribute("user");
				for (int i = 0; i < gradeIdArr.length; i++) {
					Integer gradeId = Integer.valueOf(gradeIdArr[i]);
					gradeApi.delGradeByGradeIdId(gradeId);
					//数据操作成功，保存操作日志
					if(gradeId!=null){
						logApi.saveLog(gradeId,null,LogUtil.LOG_GRADE_MODULEID, LogUtil.LOG_DELETE_TYPE, map);
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

	/**
	 * 
	 * @Title checkGradeName
	 * @Description
	 * @author xrl
	 * @Date 2017年5月8日
	 * @param gradeName
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/checkGradeName", method = RequestMethod.POST)
	public void checkGradeName(String gradeName,Integer gradeId,HttpServletRequest request,HttpServletResponse response) {
		response.setContentType("application/text;charset=utf-8");
		String result = "";
		try {
			Map<String, Object> selMap = new HashMap<String, Object>();
			gradeName = java.net.URLDecoder.decode(gradeName, "UTF-8");
			selMap.put("gradeName", gradeName);
			if(gradeId!=null&&!"".equals(gradeId)){
				selMap.put("gradeId", gradeId);
			}
			//暂时注掉
			Map map=(Map)request.getSession().getAttribute("user");
//			Integer orgId = (Integer)map.get("orgId");
			String orgCode = (String)map.get("orgCode");
//			selMap.put("orgId", orgId);
			selMap.put("orgCode", orgCode);
			Map<String, Object> gradeMap = gradeApi.checkGradeName(selMap);
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

	/**
	 * 
	 * @Title toConfigureGradeCourse
	 * @Description：跳转到 配置培训班页面，配置班级课程
	 * @author xrl
	 * @Date 2017年3月23日
	 * @param gradeId
	 * @param model
	 * @return
	 */
	@RequestMapping({ "/toConfigureGradeCourse" })
	public String toConfigureGradeCourse(Integer gradeId, Model model) {
		logger.debug("配置课程..." + gradeId);
		Map<String, Object> grade = gradeApi.loadEditGradeFormData(gradeId);
		model.addAttribute("grade", grade);
		return "grade/configureGradeCourse";
	}

	/**
	 * 
	 * @Title toConfigureGradeUser
	 * @Description：跳转到 配置培训班页面，配置班级学员
	 * @author xrl
	 * @Date 2017年3月23日
	 * @param gradeId
	 * @param model
	 * @return
	 */
	@RequestMapping({ "/toConfigureGradeUser" })
	public String toConfigureGradeUser(Integer gradeId, Model model) {
		logger.debug("配置学员..." + gradeId);
		Map<String, Object> grade = gradeApi.loadEditGradeFormData(gradeId);
		model.addAttribute("grade", grade);
		return "grade/configureGradeUser";
	}

	/**
	 * 
	 * @Title toConfigureGradeExam
	 * @Description：跳转到 配置培训班页面，配置班级考试
	 * @author xrl
	 * @Date 2017年3月23日
	 * @param gradeId
	 * @param model
	 * @return
	 */
	@RequestMapping({ "/toConfigureGradeExam" })
	public String toConfigureGradeExam(Integer gradeId, Model model) {
		logger.debug("配置考试..." + gradeId);
		Map<String, Object> grade = gradeApi.loadEditGradeFormData(gradeId);
		model.addAttribute("grade", grade);
		return "grade/configureGradeExam";
	}

	/**
	 * 
	 * @Title toConfigureGradeHeadTeacher
	 * @Description:跳转到 配置培训班页面，配置班级班主任
	 * @author xrl
	 * @Date 2017年3月25日
	 * @param gradeId
	 * @param model
	 * @return
	 */
	@RequestMapping({ "/toConfigureGradeHeadTeacher" })
	public String toConfigureGradeHeadTeacher(Integer gradeId, Model model) {
		logger.debug("配置班主任..." + gradeId);
		Map<String, Object> grade = gradeApi.loadEditGradeFormData(gradeId);
		model.addAttribute("grade", grade);
		return "grade/configureGradeHeadTeacher";
	}

	/**
	 * 
	 * @Title toGradeWorkList
	 * @Description：仅页面跳转
	 * @author xrl
	 * @Date 2017年3月25日
	 * @return
	 */
	@RequestMapping({ "/toGradeWorkList" })
	public String toGradeWorkList() {
		logger.debug("@@@@@@跳转到班级作业列表界面");
		return "/grade/gradeWorkList";
	}

	/**
	 * 
	 * @Title toGradeWorkList
	 * @Description：仅页面跳转
	 * @author xrl
	 * @Date 2017年6月26日
	 * @return
	 */
	@RequestMapping({ "/toGradeNoticeList" })
	public String toGradeNoticeList() {
		logger.debug("@@@@@@跳转到班级公告列表界面");
		return "/grade/gradeNoticeList";
	}
}
