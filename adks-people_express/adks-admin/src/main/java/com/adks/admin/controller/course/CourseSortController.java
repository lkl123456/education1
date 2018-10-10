package com.adks.admin.controller.course;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.adks.admin.controller.enumeration.EnumerationController;
import com.adks.admin.util.LogUtil;
import com.adks.commons.oss.OSSUploadUtil;
import com.adks.commons.util.PropertiesFactory;
import com.adks.dubbo.api.data.course.Adks_course_sort;
import com.adks.dubbo.api.data.news.Adks_news_type;
import com.adks.dubbo.api.interfaces.admin.course.CourseApi;
import com.adks.dubbo.api.interfaces.admin.course.CourseSortApi;
import com.adks.dubbo.api.interfaces.admin.log.LogApi;
import com.adks.dubbo.api.interfaces.admin.org.OrgApi;
import com.adks.dubbo.commons.Page;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping(value = "/courseSort")
public class CourseSortController {

	private static final Logger logger = LoggerFactory.getLogger(EnumerationController.class);

	private static final int BUFFER_SIZE = 100 * 1024;
	@Autowired
	private CourseSortApi courseSortApi;
	@Autowired
	private CourseApi courseApi;
	@Autowired
	private OrgApi orgApi;
	@Autowired
	private LogApi logApi;

	@RequestMapping(value = "/courseSortList")
	public String home(HttpServletRequest request, HttpServletResponse response, Model model, Integer orgId,
			Integer parentId) throws IOException {
		logger.debug("进入 OrgController  orgList...");
		if (parentId == null) {
			parentId = 0;
		}
		String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
		model.addAttribute("ossResource", ossResource);
		// Adks_org org=orgApi.getOrgById(orgId);
		// 父级id
		// model.addAttribute("orgId", org==null?null:org.getId());
		// model.addAttribute("parentName", org==null?null:org.getName());
		return "/course/courseSortList";
	}

	@RequestMapping(value = "/courseSortListPage")
	@ResponseBody
	public Page<List<Map<String, Object>>> courseSortListPage(HttpServletRequest request,HttpServletResponse response,String parentFirstCode, Integer parentId,
			Integer parentFirstId,String s_courseSort_name, Integer page, Integer rows) {
		Page<List<Map<String, Object>>> page_bean = null;
		try {
			Map userMap=(Map)request.getSession().getAttribute("user");
			
			page_bean = new Page<List<Map<String, Object>>>();
			page_bean.setCurrentPage(page);
			page_bean.setPageSize(rows);
			Map map = new HashMap<>();
			if (s_courseSort_name != null && !"".equals(s_courseSort_name)) {
				s_courseSort_name = java.net.URLDecoder.decode(s_courseSort_name, "UTF-8");
				map.put("courseSortName", s_courseSort_name);
			}
			if (parentId != null && parentId == 0) {
				parentId = null;
				parentFirstCode = "0A";
			}
			if (parentFirstCode != null && !"".equals(parentFirstCode)) {
				map.put("courseSortCode", parentFirstCode);
			}
			if (parentId != null) {
				map.put("courseParentId", parentId);
			}
			if (parentFirstId != null) {
				map.put("courseSortId", parentFirstId);
			}
			page_bean.setMap(map);
			page_bean = courseSortApi.getCourseSortListPage(page_bean);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return page_bean;
	}

	@RequestMapping(value = "/addCourse")
	public String addCourse() {
		return "/course/addCourse";
	}

	@RequestMapping(value = "/delCourseSort", method = RequestMethod.POST)
	public void delCourseSort(HttpServletRequest request, HttpServletResponse response, String ids) {
		response.setContentType("application/text;charset=utf-8");
		String result = "";
		try {
			if (ids != null) {
				// 课程分类下若有课程不可被删除
				String[] id = ids.split(",");
				boolean flag = true;
				for (String courseSortId : id) {
					boolean ok = courseApi.courseBycourseSort(Integer.parseInt(courseSortId));
					if (ok) {
						flag = false;
						break;
					}
				}
				// 删除课程分类
				if (!flag) {// 不能删除
					result = "hascourse";
				} else {// 可以删除，并且删除子分类
					for (String courseSortId : id) {
						Adks_course_sort courseSort = courseSortApi.getCourseSortById(Integer.parseInt(courseSortId));
						if (courseSort != null) {
							courseSortApi.deleteCourseSort(courseSort.getCourseSortCode());
						}
					}
					result = "succ";
				}
			}
			PrintWriter pWriter = response.getWriter();
			pWriter.write(result);
			pWriter.flush();
			pWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存课程分类
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/saveCourseSort", produces = { "text/html;charset=utf-8" })
	@ResponseBody
	public void saveCourseSort(HttpServletRequest request, HttpServletResponse response, Adks_course_sort courseSort,
			@RequestParam(value = "courseSortImgfile", required = false) MultipartFile courseSortImgfile)
			throws IOException {
		response.setContentType("text/html;charset=utf-8");

		String courseSortImgPath = PropertiesFactory.getProperty("ossConfig.properties", "oss.CourseImg_Path");
		String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");

		String message = "error";
		try {
			if (courseSort != null) {
				boolean flag = true;
				// 网校唯一标识
				if (courseSort.getOrgName() != null) {
					Adks_course_sort temp = courseSortApi.getCourseSortByName(courseSort.getCourseSortName());
					if (temp != null && ((courseSort.getCourseSortId() != null
							&& courseSort.getCourseSortId() != temp.getCourseSortId())
							|| (courseSort.getCourseSortId() == null))) {
						message = "snnameExists";
						flag = false;
					}
				}
				if (courseSortImgfile != null) {
					if (StringUtils.isNotEmpty(courseSortImgfile.getOriginalFilename())) {
						String fileType = courseSortImgfile.getContentType();
						String[] types = fileType.split("/");
						// 将MultipartFile转换成文件流
						//CommonsMultipartFile cf = (CommonsMultipartFile) courseSortImgfile;
						//DiskFileItem fi = (DiskFileItem) cf.getFileItem();
						//File file = fi.getStoreLocation();
						InputStream is=courseSortImgfile.getInputStream();
						if (courseSort.getCourseSortImgpath() == null || "".equals(courseSort.getCourseSortImgpath())) {
							// 没有图片时上传图片
							String courseSort_Path = OSSUploadUtil.uploadFileNewName(is, types[1], courseSortImgPath);
							String[] paths = courseSort_Path.split("/");
							String code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
							courseSort.setCourseSortImgpath(code);
						} else if (courseSort.getCourseSortImgpath() != null
								&& !"".equals(courseSort.getCourseSortImgpath())) {
							// 覆盖原有图片上传，更新文件只更新文件内容，不更新文件名称和文件路径
							//String courseSort_Path = OSSUploadUtil.updateFile(is, types[1],ossResource + courseSort.getCourseSortImgpath());
							String courseSort_Path = OSSUploadUtil.replaceFile(is, types[1], ossResource + courseSort.getCourseSortImgpath(), courseSortImgPath);
							String[] paths = courseSort_Path.split("/");
							String code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
							courseSort.setCourseSortImgpath(code);
						}
					}
				}
				if (flag) {
					if (courseSort.getCourseParentId() == null) {
						courseSort.setCourseParentId(0);
					}
					if (courseSort.getCourseSortId() != null) {
						// 查看名称是否修改，若是需要同步到课程表
						Adks_course_sort cs = courseSortApi.getCourseSortById(courseSort.getCourseSortId());
						if (!courseSort.getCourseSortName().equals(cs.getCourseSortName())) {
							courseSortApi.updateNameUnifed(courseSort.getCourseSortId(),
									courseSort.getCourseSortName());
						}
					} else {
						Map userMap=(Map)request.getSession().getAttribute("user");
						Integer userId = Integer.parseInt(userMap.get("userId")+"");
						String username = userMap.get("username")+"";
						courseSort.setCreatorId(userId);
						courseSort.setCreatorName(username);
						courseSort.setCreateTime(new Date());
						courseSort.setCourseNum(0);
					}
					Integer dataId=courseSortApi.saveCourseSort(courseSort);
					if(dataId!=null&&dataId!=0){
						Map map=(Map)request.getSession().getAttribute("user");
						//添加/修改 
						if(courseSort.getCourseSortId()==null){  //添加
							logApi.saveLog(dataId,courseSort.getCourseSortName(),LogUtil.LOG_AUTHOR_MODULEID,LogUtil.LOG_ADD_TYPE,map);
						}else{  //修改
							logApi.saveLog(courseSort.getCourseSortId(),courseSort.getCourseSortName(),LogUtil.LOG_AUTHOR_MODULEID,LogUtil.LOG_UPDATE_TYPE,map);
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
	 * 使用plupload上传文件
	 * 
	 * @param file
	 *            文件对象
	 * @param name
	 *            文件名称
	 * @param chunk
	 *            数据块序号
	 * @param chunks
	 *            数据块总数
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/plupload", method = RequestMethod.POST)
	public JSONObject plupload(String name, @RequestParam MultipartFile file, HttpServletRequest request,
			HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		try {
			// 检查文件目录，不存在则创建
			String relativePath = "/plupload/files/";
			String realPath = session.getServletContext().getRealPath("/");
			File folder = new File(realPath + relativePath);
			logger.info("上传路径：" + folder.getAbsolutePath());
			if (!folder.exists()) {
				folder.mkdirs();
			}
			// 目标文件
			File destFile = new File(folder, name);
			// 文件已存上传了同名的文件）
			if (destFile.exists()) {
				logger.info("=======此文件已经存在====");
				jsonObject.put("filePath", relativePath + name);
				return jsonObject;
			}
			// 上传文件
			uploadFile(file.getInputStream(), destFile);
			jsonObject.put("filePath", relativePath + name);
			return jsonObject;
		} catch (IOException e) {
			logger.error(e.getMessage());
			return new JSONObject();
		}

	}

	/*
	 * 合并上传的文件
	 */
	private void uploadFile(InputStream in, File destFile) {
		OutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(destFile), BUFFER_SIZE);
			in = new BufferedInputStream(in, BUFFER_SIZE);

			int len = 0;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	}

	//得到课程分类的根分类，现在课程分类不跟机构相关
	@RequestMapping(value = "/getCourseSortTypeJson", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public List<Adks_course_sort> getNewsTypeJson() {
		List<Adks_course_sort> courseSrotList = courseSortApi.getCourseSortParentIsZero();
		List<Adks_course_sort> csList=new ArrayList<Adks_course_sort>();
		if(courseSrotList!=null && courseSrotList.size()>0){
			Adks_course_sort cs=new Adks_course_sort();
			cs.setId(0);
			cs.setName("全部");
			cs.setText("全部");
			cs.setCourseSortCode("0A");
			cs.setChildren(courseSrotList);
			csList.add(cs);
		}
		return csList;
	}
	
}
