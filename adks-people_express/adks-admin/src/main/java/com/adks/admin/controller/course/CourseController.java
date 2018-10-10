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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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
import com.adks.commons.ftp.FtpApacheUtil;
import com.adks.commons.oss.OSSUploadUtil;
import com.adks.commons.util.FileTools;
import com.adks.commons.util.FileUtil;
import com.adks.commons.util.FormatDate;
import com.adks.commons.util.PropertiesFactory;
import com.adks.dubbo.api.data.author.Adks_author;
import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.api.data.course.Adks_course_sort;
import com.adks.dubbo.api.interfaces.admin.author.AuthorApi;
import com.adks.dubbo.api.interfaces.admin.course.CourseApi;
import com.adks.dubbo.api.interfaces.admin.course.CourseSaveFileApi;
import com.adks.dubbo.api.interfaces.admin.course.CourseSortApi;
import com.adks.dubbo.api.interfaces.admin.log.LogApi;
import com.adks.dubbo.commons.Page;
import com.alibaba.fastjson.JSONObject;
import com.baidu.yun.push.Util.BaiduyunUtil;
import com.baidu.yun.push.Util.DeviceEnum;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Controller
@RequestMapping(value = "/course")
public class CourseController {

	private static final Logger logger = LoggerFactory.getLogger(EnumerationController.class);

	private static final int BUFFER_SIZE = 100 * 1024;
	@Autowired
	private CourseApi courseApi;
	@Autowired
	private AuthorApi authorApi;
	@Autowired
	private CourseSortApi courseSortApi;
	@Autowired
	private CourseSaveFileApi csFileApi;
	@Autowired
	private LogApi logApi;

	@RequestMapping(value = "/courseList")
	public String manager(Model model) {
		logger.debug("进入 CourseController  courseList...");
		return "/course/courseList";
	}

	// 添加课程页面
	@RequestMapping(value = "/toAddCourse")
	public String getCourseCategory(Integer courseSortId, Integer authorId, String authorName, Integer courseId,
			Model model) {
		try {
			if (authorName != null && !"".equals(authorName)) {
				authorName = java.net.URLDecoder.decode(authorName, "UTF-8");
			}

			if (courseSortId != null) {
				Adks_course_sort courseSort = courseSortApi.getCourseSortById(courseSortId);
				model.addAttribute("courseSort", courseSort);
				if (courseId == null) {
					Adks_course course = new Adks_course();
					course.setCourseSortId(courseSort.getCourseSortId());
					course.setCourseSortName(courseSort.getCourseSortName());
					course.setCourseSortCode(courseSort.getCourseSortCode());
					course.setAuthorId(authorId);
					course.setAuthorName(authorName);
					if (authorId != null) {
						Adks_author author = authorApi.getAuthorById(authorId);
						course.setAuthorId(author.getAuthorId());
						course.setAuthorName(author.getAuthorName());
					}
					model.addAttribute("course", course);
				}
			}
			if (courseId != null) {
				Adks_course course = courseApi.getCourseById(courseId);
				if (authorId != null) {
					Adks_author author = authorApi.getAuthorById(authorId);
					course.setAuthorId(author.getAuthorId());
					course.setAuthorName(author.getAuthorName());
				}
				model.addAttribute("course", course);
			}
			String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
			model.addAttribute("ossResource", ossResource);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "/course/addCourse";
	}

	// 添加讲师页面
	@RequestMapping(value = "/toAuthorCourse")
	public String toAuthorCourse(Integer orgId,String orgCode, Integer courseId, Integer courseSortId, Model model) {
		if (orgCode != null && !"".equals(orgCode)) {
			model.addAttribute("orgCode", orgCode);
		}
		if (orgId != null && !"".equals(orgId)) {
			model.addAttribute("orgId", orgId);
		}
		if (courseId != null) {
			model.addAttribute("courseId", courseId);
		}
		if (courseSortId != null) {
			model.addAttribute("courseSortId", courseSortId);
		}
		return "/course/toAddAuthorList";
	}

	/**
	 * 课程界面的，分类树 courseId课程id flag 1审核状态，2是否推荐，3课程状态 result标记修改到数据库的数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "/courseUpdateData")
	@ResponseBody
	public void courseUpdateData(HttpServletRequest request, HttpServletResponse response, String courseIds,
			Integer flag, String result) {
		try {
			if (courseIds != null && !"".equals(courseIds)) {
				if (result == null || "".equals(result)) {
					result = "1";
				}
				String[] courseId = courseIds.split(",");
				for (String id : courseId) {
					courseApi.courseUpdateData(Integer.parseInt(id), flag, result);
				}
			}
			PrintWriter pWriter = response.getWriter();
			pWriter.write("succ");
			pWriter.flush();
			pWriter.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 课程界面的课程分类树，现在跟机构没有关系
	 * 
	 * @param parent
	 *            :父部门id
	 * @return
	 */
	@RequestMapping(value = "/getCourseSortListJson", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public List<Adks_course_sort> getCourseSortListJson(HttpServletRequest request, HttpServletResponse response,
			String orgCode, String courseSortName,Integer parentId) {
        List<Adks_course_sort> maps=new ArrayList<Adks_course_sort>();
        // dubbo获取 部门机构树json数据
        List<Adks_course_sort> listCourseSort=null;
        if(parentId==null){
        	parentId=0;
        }
        Map map = new HashMap<>();
		if (courseSortName != null && !"".equals(courseSortName)) {
			try {
				courseSortName = java.net.URLDecoder.decode(courseSortName, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			map.put("courseSortName", courseSortName);
		}
		map.put("parentId", parentId);
        listCourseSort = courseSortApi.getCourseSortsListAll(map);
        if(parentId==0){
        	Adks_course_sort cs=new Adks_course_sort();
        	cs.setId(0);
        	cs.setName("全部");
        	cs.setText("全部");
        	cs.setCourseSortCode("0A");
        	cs.setChildren(listCourseSort);
        	maps.add(cs);
        }else{
        	maps=listCourseSort;
        }
        return maps;
	}

	@RequestMapping(value = "/courseListPage")
	@ResponseBody
	public Page<List<Map<String, Object>>> getCourseList(HttpServletRequest request, HttpServletResponse response,
			Integer orgId, String courseSortCode, String s_course_name, Integer page, Integer rows) {
		Page<List<Map<String, Object>>> page_bean = null;
		try {
			Map userMap = (Map) request.getSession().getAttribute("user");
			page_bean = new Page<List<Map<String, Object>>>();
			page_bean.setCurrentPage(page);
			page_bean.setPageSize(rows);
			Map map = new HashMap<>();
			if (s_course_name != null) {
				s_course_name = java.net.URLDecoder.decode(s_course_name, "UTF-8");
				map.put("courseName", s_course_name);
			}
			boolean isSuperManager = "1".equals(userMap.get("isSuper") + "") ? true : false;
			if (isSuperManager) {
				map.put("orgCode", "0A");
			} else {
				String orgCode = userMap.get("orgCode") + "";
				String[] orgCodes=orgCode.split("A");
				map.put("orgCode", orgCode);
			}
			if (courseSortCode != null && !"".equals(courseSortCode)) {
				map.put("courseSortCode", courseSortCode);
			}
			page_bean.setMap(map);
			page_bean = courseApi.getCourseListPage(page_bean);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return page_bean;
	}

	@RequestMapping(value = "/saveCourse", produces = { "text/html;charset=utf-8" })
	@ResponseBody
	public void addCourse(HttpServletRequest request, HttpServletResponse response, Adks_course course,
			@RequestParam(value = "courseImgfile", required = false) MultipartFile courseImgfile,
			@RequestParam(value = "coursefile", required = false) MultipartFile coursefile) throws IOException {
		response.setContentType("text/html;charset=utf-8");

		// 课程图片存储地址
		String courseImgPath = PropertiesFactory.getProperty("ossConfig.properties", "oss.CourseImg_Path");
		String videoPath = PropertiesFactory.getProperty("ossConfig.properties", "oss.Video_Path");
		String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");

		String message = "error";
		try {
			if (course != null) {
				boolean flag = true;
				boolean flag1 = true;
				// 网校唯一标识
				if (course.getCourseName() != null) {
					Adks_course temp = courseApi.getCourseByName(course.getCourseName());
					if (temp != null && ((course.getCourseId() != null && !temp.getCourseId().equals(course.getCourseId()))
							|| (course.getCourseId() == null))) {
						message = "snnameExists";
						flag = false;
					}
				}
				if (flag) {
					// 上传课件
					if (StringUtils.isNotEmpty(coursefile.getOriginalFilename())) {
						if (coursefile != null) {
							InputStream is = coursefile.getInputStream();
							// 获取文件类型
							String fileType = coursefile.getOriginalFilename();
							fileType = fileType.substring(fileType.lastIndexOf(".") + 1, fileType.length());
							if (course.getCourseType() == 170) {// 表示是三分屏，三分屏上传到gxwj,然后解压上传到oss
								if (!"zip".equals(fileType)) {
									flag1 = false;
									message = "fileError";
								}
							} else {
								// 如果是MP4上传到阿里云
								if (!"mp4".equalsIgnoreCase(fileType)) {
									flag1 = false;
									message = "fileError";
								}
							}
						}
					}
				}
				if (flag && flag1) {
					if (StringUtils.isNotEmpty(courseImgfile.getOriginalFilename())) {
						// 上传课程图片
						if (courseImgfile != null) {
							// 将MultipartFile转换成文件流
							// CommonsMultipartFile cf = (CommonsMultipartFile)
							// courseImgfile;
							// DiskFileItem fi = (DiskFileItem)
							// cf.getFileItem();
							// File file = fi.getStoreLocation();
							InputStream is = courseImgfile.getInputStream();
							// 获取文件类型
							String fileType = courseImgfile.getOriginalFilename();
							fileType = fileType.substring(fileType.lastIndexOf(".") + 1, fileType.length());

							if (course.getCoursePic() == null || "".equals(course.getCoursePic())) {
								// 上传图片
								String new_Path = OSSUploadUtil.uploadFileNewName(is, fileType, courseImgPath);
								String[] paths = new_Path.split("/");
								String code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
								course.setCoursePic(code);
							} else if (course.getCoursePic() != null && !"".equals(course.getCoursePic())) {
								String new_Path = OSSUploadUtil.updateFile(is, fileType,
										ossResource + course.getCoursePic());
								String[] paths = new_Path.split("/");
								String code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
								course.setCoursePic(code);
							}
						}
					}
					boolean errors = false;
					// 上传课件
					if (StringUtils.isNotEmpty(coursefile.getOriginalFilename())) {
						if (coursefile != null) {
							InputStream is = coursefile.getInputStream();

							// 将MultipartFile转换成文件流
							/*
							 * CommonsMultipartFile cf = (CommonsMultipartFile)
							 * courseImgfile; DiskFileItem fi = (DiskFileItem)
							 * cf.getFileItem(); File fileOr =
							 * fi.getStoreLocation();
							 */
							// 获取文件类型
							String fileType = coursefile.getOriginalFilename();
							fileType = fileType.substring(fileType.lastIndexOf(".") + 1, fileType.length());
							if (course.getCourseType() == 170) {// 表示是三分屏，ftp三分屏上传到gxwj,然后解压上传到oss
								SimpleDateFormat fmt = new SimpleDateFormat("yyMMdd");
								String courseCode = "QS" + fmt.format(new Date()) + System.currentTimeMillis(); // 课程名;
								if(course.getCourseCode()!=null && !"".equals(course.getCourseCode())){
									courseCode=course.getCourseCode();
								}
								if(course.getCourseCode()!=null && !"".equals(course.getCourseCode())){
									courseCode=course.getCourseCode();
								}
								
								String sessionID = request.getSession().getId();
								File theDefaultUploadDir = null;
								String webRootPath = request.getServletContext().getRealPath("/");
								webRootPath = webRootPath.replaceAll("\\\\", "/");

								String defaultUploadDir = webRootPath + "tempUploadDir" + "/" + sessionID;
								defaultUploadDir = defaultUploadDir.replaceAll("\\\\", "/");

								try {
									theDefaultUploadDir = new File(defaultUploadDir);// FIle对象
									if (!theDefaultUploadDir.exists()) {
										if (!theDefaultUploadDir.mkdirs()) {
											errors = true;
											System.out.println("===============delete theDefaultUploadDir error===============");
											message = "errorOnFile";
										}
									} else {
										FileTools.removeSonFile(theDefaultUploadDir);
									}
									if (!errors && coursefile != null) {
										String courseWareFileFileName = coursefile.getOriginalFilename();
										File courseload = new File(defaultUploadDir + "/" + courseWareFileFileName);
										FileUtil.copyFile(coursefile.getInputStream(), courseload); // 用于测试,将本次session期间所有文件保存到同一个文件夹里：theDefaultUploadDir
										System.out.println("-----------courseDuration--------------");
										// 上传时读取课程时长
										getCourseDuration(course, defaultUploadDir + "/" + courseWareFileFileName,defaultUploadDir);

										/**
										 * 下面进行ZIP文件的上传
										 */
										System.out.println("-----------zip upload--------------");
										String ftpScormPath = PropertiesFactory.getProperty("config.properties","base.scormPath");
										if (ftpScormPath != null && !"".equals(ftpScormPath)) {
											FtpApacheUtil ftpAppUtil = new FtpApacheUtil();
											String mainPath = ftpScormPath;
											//上传到服务器路径
//											mainPath = "/SCORMKC" + mainPath; 
											File file = new File(defaultUploadDir + "/" + courseWareFileFileName);
											File file1 = new File(defaultUploadDir + "/" + courseCode + ".zip");
											
											FileUtil.copyFile(file, file1);
											file.renameTo(file1);
											//ftpAppUtil.scormUpload(mainPath, courseCode, file1);
											ftpAppUtil.scormUploadSan(mainPath, courseCode, file1);
											// 删除目录
											//FileTools.removeFile(theDefaultUploadDir);

										} else {
											/*
											 * String outPutDirectory =
											 * webRootPath +
											 * "defaultCourseWareDepot/" +
											 * courseCode; if
											 * (ZipUtil.unZip(fileOr,
											 * outPutDirectory)) {
											 * FileTools.removeFile(fileOr); }
											 */
										}
										System.out.println("============zipFileToAliyun begin==============");
										//String code=courseApi.zipFileToAliyun(courseCode, course, courseWareFileFileName);
										String code=csFileApi.zipFileToAliyun(courseCode, course, courseWareFileFileName);
										System.out.println("============zipFileToAliyun end==============");
										System.out.println("============zipFileToAliyun end==============  code:"+code);
										if(code ==null || "".equals(code)){
											System.out.println("===============delete scormUpload error===============");
											errors=true;
											message = "errorOnFile";
										}else{
											course.setCourseCode(code);
											errors=false;
										}
										
									} else {
										FileTools.removeFile(theDefaultUploadDir);
										System.out.println("===============delete coursefile1 error===============");
										errors = true;
										message = "errorOnFile";
									}
								} catch (IOException e) {
									FileTools.removeFile(theDefaultUploadDir);
									System.out.println("===============delete coursefile2 error===============");
									errors = true;
									message = "errorOnFile";
								} catch (Exception e) {
									FileTools.removeFile(theDefaultUploadDir);
									System.out.println("===============delete coursefile3 error===============");
									errors = true;
									message = "errorOnFile";
								} finally {
									FileTools.removeFile(theDefaultUploadDir);
									System.out.println("===============saveCourse errors===============errors:"+errors);
									if (errors) {
										message = "errorOnFile";
									}
								}
							} else {
								// 如果是MP4上传到阿里云
								if (course.getCourseCode() == null || "".equals(course.getCourseCode())) {
									// 上传课件
									String new_Path = OSSUploadUtil.uploadFileNewName(is, fileType, videoPath);
									String[] paths = new_Path.split("/");
									String code = paths[paths.length - 1];
									course.setCourseCode(code);
								} else if (course.getCourseCode() != null && !"".equals(course.getCourseCode())) {
									String new_Path = OSSUploadUtil.updateFile(is, fileType,ossResource +"/VIDEO/"+course.getCourseCode());
									String[] paths = new_Path.split("/");
									String code = paths[paths.length - 1];
									course.setCourseCode(code);
								}
							}
						}
					}
					if (!errors) {
						if (course.getCourseId() != null) {
							// 如果修改了名称，同步到
							Adks_course courseOld = courseApi.getCourseById(course.getCourseId());
							if (!courseOld.getCourseName().equals(course.getCourseName())) {
								courseApi.checkCourseNametoTabs(course.getCourseId(), course.getCourseName());
							}
							//判断讲师是否修改，如果是清除该讲师的缓存
							if(!courseOld.getAuthorId().equals(course.getAuthorId())){
								courseApi.deleteAuthorCourseRedis(courseOld.getAuthorId(),course.getAuthorId());
							}
						} else {
							Map userMap = (Map) request.getSession().getAttribute("user");
							Integer userId = Integer.parseInt(userMap.get("userId") + "");
							String username = userMap.get("username") + "";
							course.setCreatorId(userId);
							course.setCreatorName(username);
							course.setCreateTime(new Date());
							course.setCourseStudiedLong(0);
							course.setCourseCollectNum(0);
							course.setCourseClickNum(0);
							course.setCourseStream("_256K");
						}
						Integer dataId=courseApi.saveCourse(course);
						if(dataId!=null&&dataId!=0){
							Map map = (Map) request.getSession().getAttribute("user");
							//添加/修改 
							if(course.getCourseId()==null){  //添加
								logApi.saveLog(dataId, course.getCourseName(),LogUtil.LOG_COURSE_MODULEID,LogUtil.LOG_ADD_TYPE,map);
							}else{  //修改
								logApi.saveLog(course.getCourseId(), course.getCourseName(),LogUtil.LOG_COURSE_MODULEID,LogUtil.LOG_UPDATE_TYPE,map);
							}
						}
						message = "succ";
						// 推送
						String jsonAndroid = "{\"title\":\"有新的班级消息\",\"description\":\"" + course.getCourseName()
								+ "\",\"notification_builder_id\": 0, \"notification_basic_style\": 7,\"open_type\":0,\"url\": \"http://developer.baidu.com\",\"pkg_content\":\"\",\"custom_content\":{\"id\":\""
								+ course.getCourseId() + "\",\"content\":\"" + course.getCourseName()
								+ "\",\"method\":\"NewCourse\"}}";
						try {
							BaiduyunUtil.sendBaiduyunMessage(jsonAndroid, DeviceEnum.Android.toString());
						} catch (PushServerException e) {
							e.printStackTrace();
						} catch (PushClientException e) {
							e.printStackTrace();
						}

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

	// 课程删除操作
	@RequestMapping(value = "/delCourse", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public String deleteCourseById(HttpServletRequest request, HttpServletResponse response, String courseIds) {
		response.setContentType("application/json;charset=utf-8");
		String message = "succ";
		// try {
		String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
		if (courseIds != null && !"".equals(courseIds)) {
			boolean isDel=true;
			String[] ids = courseIds.split(",");
			for (String id : ids) {
				boolean falg = courseApi.canDelCourse(Integer.parseInt(id));
				if (!falg) {
					isDel=false;
					message = "error";
					break;
				}
			}
			if(isDel){
				//如果有一门课程是公开课，则清除所有的最新课程缓存（只有如果课程是班级课程、课程学习不让删除课程，所以只需清除最新课程的缓存）
				boolean isBelong=false;
				Map mapAuthorId=new HashMap<>();
				Map mapAboutName=new HashMap<>();
				for (String id : ids) {
					Adks_course course = courseApi.getCourseById(Integer.parseInt(id));
					if (course.getCourseSortId() != null) {
						courseApi.updateCourseSortCourseNum(course.getCourseSortId(), 2);
					}
					//删除阿里云的资源
					if(course.getCoursePic()!=null && !"".equals(course.getCoursePic())){
						OSSUploadUtil.deleteFile(ossResource + course.getCoursePic());
					}
					if(course.getCourseCode()!=null && !"".equals(course.getCourseCode()) && course.getCourseType()!=170){
						OSSUploadUtil.deleteFile(ossResource + course.getCourseCode());
					}
					if(course.getCourseBelong()==1){
						isBelong=true;
					}
					mapAuthorId.put(id, course.getAuthorId());
					String _courseName = course.getCourseName();
		    		if(course.getCourseName().length() > 4){
		    			_courseName = course.getCourseName().substring(0, course.getCourseName().length()-4);
		    		}
					mapAboutName.put(id, _courseName);
					//数据操作成功，保存操作日志
					if(id!=null){
						Map map = (Map) request.getSession().getAttribute("user");
						logApi.saveLog(Integer.valueOf(id),null,LogUtil.LOG_COURSE_MODULEID,LogUtil.LOG_DELETE_TYPE, map);
					}
				}
				courseApi.deleteCourse(courseIds,isBelong);
			}
		}
		return message;
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

	// * 合并上传的文件

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

	/**
	 * @Description: scorm 课件上传时读取其时长并保存
	 * @param course
	 * @param file
	 * @author: yzh
	 * @date: 2014-9-1 上午09:09:14
	 */
	private Adks_course getCourseDuration(Adks_course course, String zipPath, String filePath) {
		InputStream inRead = null;
		BufferedOutputStream write = null;
		File inf = null;
		try {
			inf = new File(filePath + "/metadata.xml");
			ZipFile fileT = new ZipFile(zipPath);
			ZipEntry entry = fileT.getEntry("metadata.xml"); // 假如压缩包里的文件名是index.xml
			inRead = fileT.getInputStream(entry);
			// 写入文件
			write = new BufferedOutputStream(new FileOutputStream(inf));
			int cha = 0;
			while ((cha = inRead.read()) != -1) {
				write.write(cha);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			// 要注意IO流关闭的先后顺序
			try {
				write.flush();
				write.close();
				inRead.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// 解析获取课程时长
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(inf.getPath());
			NodeList nl = doc.getElementsByTagName("duration");
			if (nl != null && nl.getLength() > 0) {
				Node n = nl.item(0);
				// String str = n.getAttributes().item(0).getNodeValue();
				String str = n.getTextContent();
				if (str != null && !"".equals(str)) {
					course.setCourseTimeLong(FormatDate.getDurationByCourseFileStr_new(str));
					course.setCourseDuration(FormatDate.stringToInteger(course.getCourseTimeLong()));
					System.out.println("----------courseDuration:" + course.getCourseTimeLong());
				}
			}
		} catch (Exception e) {
			System.out.println("-----1111:" + e.getMessage());
		}
		return course;
	}

}
