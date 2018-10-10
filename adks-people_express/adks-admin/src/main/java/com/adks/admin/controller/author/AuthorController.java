package com.adks.admin.controller.author;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.adks.admin.controller.enumeration.EnumerationController;
import com.adks.admin.util.LogUtil;
import com.adks.commons.oss.OSSUploadUtil;
import com.adks.commons.util.PYUtil;
import com.adks.commons.util.PropertiesFactory;
import com.adks.dubbo.api.data.author.Adks_author;
import com.adks.dubbo.api.interfaces.admin.author.AuthorApi;
import com.adks.dubbo.api.interfaces.admin.log.LogApi;
import com.adks.dubbo.commons.Page;

@Controller
@RequestMapping(value = "/author")
public class AuthorController {

	private static final Logger logger = LoggerFactory.getLogger(EnumerationController.class);

	private static final int BUFFER_SIZE = 100 * 1024;
	@Autowired
	private AuthorApi authorApi;
	@Autowired
	private LogApi logApi;

	@RequestMapping(value = "/authorList")
	public String home(HttpServletRequest request, HttpServletResponse response, Model model, Integer orgId,
			Integer parentId) throws IOException {

		String ossPath = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
		model.addAttribute("ossResource", ossPath);
		logger.debug("进入 AuthorController  authorList...");
		if (parentId == null) {
			parentId = 0;
		}
		return "/author/authorList";
	}

	@RequestMapping(value = "/authorListPage")
	@ResponseBody
	public Page<List<Map<String, Object>>> authorListPage(HttpServletRequest request,HttpServletResponse response,String orgCode, String s_author_name, Integer page,
			Integer rows, Model model) {
		Page<List<Map<String, Object>>> page_bean = null;
		try {
			Map userMap=(Map)request.getSession().getAttribute("user");
			
			page_bean = new Page<List<Map<String, Object>>>();
			page_bean.setCurrentPage(page);
			page_bean.setPageSize(rows);
			Map map = new HashMap<>();
			if (s_author_name != null && !"".equals(s_author_name)) {
				s_author_name = java.net.URLDecoder.decode(s_author_name, "UTF-8");
				map.put("authorName", s_author_name);
			}
			boolean isSuperManager =  "1".equals(userMap.get("isSuper")+"")?true:false;
			if (!isSuperManager) {
				String userOrgCode = userMap.get("orgCode")+"";
				map.put("orgCode", userOrgCode);
			}
			if (orgCode != null && !"".equals(orgCode)) {
				map.remove("orgCode");
				if (orgCode.indexOf(",") >= 0) {
					String code[] = orgCode.split(",");
					orgCode = code[code.length - 1];
				}
				map.put("orgCode", orgCode);
			}
			page_bean.setMap(map);
			page_bean = authorApi.getAuthorListPage(page_bean);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return page_bean;
	}

	@RequestMapping(value = "/delAuthor", method = RequestMethod.POST)
	public void delAuthor(HttpServletRequest request, HttpServletResponse response, String ids) {
		response.setContentType("application/text;charset=utf-8");
		String result = "";
		try {
			if (ids != null) {
				// 讲师若跟课程相关联，不能直接删除
				String[] id = ids.split(",");
				boolean flag = true;
				for (String courseSortId : id) {
					boolean ok = authorApi.courseByauthorId(Integer.parseInt(courseSortId));
					if (!ok) {
						flag = false;
						break;
					}
				}
				// 删除课程分类
				if (!flag) {// 不能删除
					result = "hascourse";
				} else {// 可以删除，并且删除子分类
					authorApi.deleteAuthor(ids);
					Map map=(Map)request.getSession().getAttribute("user");
					for (String authorId : id) {
						//数据操作成功，保存操作日志
						if(authorId!=null){
							logApi.saveLog(Integer.valueOf(authorId),null,LogUtil.LOG_AUTHOR_MODULEID, LogUtil.LOG_DELETE_TYPE, map);
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
	 * 保存讲师
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/saveAuthor", produces = { "text/html;charset=utf-8" })
	@ResponseBody
	public void saveAuthor(HttpServletRequest request, HttpServletResponse response, Adks_author author,
			@RequestParam(value = "authorPhotofile", required = false) MultipartFile authorPhotofile)
			throws IOException {
		response.setContentType("text/html;charset=utf-8");

		// 讲师图片存储地址
		String userImgPath = PropertiesFactory.getProperty("ossConfig.properties", "oss.AuthorImg_Path");
		String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");

		String message = "error";
		try {
			if (author != null) {
				// 上传讲师图片
				if (authorPhotofile != null) {
					if (StringUtils.isNotEmpty(authorPhotofile.getOriginalFilename())) {
						// 将MultipartFile转换成文件流
						//CommonsMultipartFile cf = (CommonsMultipartFile) authorPhotofile;
						//DiskFileItem fi = (DiskFileItem) cf.getFileItem();
						//File file = fi.getStoreLocation();
						InputStream is=authorPhotofile.getInputStream();
						// 获取文件类型
						String fileType = authorPhotofile.getOriginalFilename();
						fileType = fileType.substring(fileType.lastIndexOf(".") + 1, fileType.length());

						if (author.getAuthorPhoto() == null || "".equals(author.getAuthorPhoto())) {
							// 上传图片
							String new_Path = OSSUploadUtil.uploadFileNewName(is, fileType, userImgPath);
							String[] paths = new_Path.split("/");
							String code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
							author.setAuthorPhoto(code);
						} else if (author.getAuthorPhoto() != null && !"".equals(author.getAuthorPhoto())) {
							//String new_Path = OSSUploadUtil.updateFile(is, fileType,ossResource + author.getAuthorPhoto());
							String new_Path = OSSUploadUtil.replaceFile(is, fileType, ossResource + author.getAuthorPhoto(), userImgPath);

							String[] paths = new_Path.split("/");
							String code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
							author.setAuthorPhoto(code);
						}
					}
				}

				boolean flag = true;
				// 网校唯一标识
				if (author.getAuthorName() != null) {
					Map<String,Object> checkMap=new HashMap<>();
					checkMap.put("authorName", author.getAuthorName());
					if(author.getAuthorId()!=null){
						checkMap.put("authorId", author.getAuthorId());
					}
					if(author.getOrgId()!=null){
						checkMap.put("orgId", author.getOrgId());
					}
					Adks_author temp = authorApi.getAuthorByName(checkMap);
					if (temp != null && ((author.getAuthorId() != null && temp.getAuthorId() != author.getAuthorId())
							|| (author.getAuthorId() == null))) {
						message = "snnameExists";
						flag = false;
					}
					author.setAuthorFirstLetter(PYUtil.converterToFirstSpell(author.getAuthorName().substring(0, 1)));
				}
				if (flag) {
					Map map=(Map)request.getSession().getAttribute("user");
					Integer userId = Integer.parseInt(map.get("userId")+"");
					String username = map.get("username")+"";
					if (author.getAuthorId() != null) {
						// 查看名称是否修改，若是需要同步到课程表
						authorApi.checkCourseNameByAuthor(author.getAuthorId(), author.getAuthorName());
						author.setCreatorId(userId);
						author.setCreatorName(username);
						author.setCreateTime(new Date());
					} else {
						author.setCreatorId(userId);
						author.setCreatorName(username);
						author.setCreateTime(new Date());
					}
					Integer dataId=authorApi.saveAuthor(author);
					//数据操作成功，保存操作日志
					if(dataId!=null&&dataId!=0){
						//添加/修改 
						if(author.getAuthorId()==null){  //添加
							logApi.saveLog(dataId, author.getAuthorName(),LogUtil.LOG_AUTHOR_MODULEID,LogUtil.LOG_ADD_TYPE,map);
						}else{  //修改
							logApi.saveLog(author.getAuthorId(), author.getAuthorName(),LogUtil.LOG_AUTHOR_MODULEID,LogUtil.LOG_UPDATE_TYPE,map);
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

}
