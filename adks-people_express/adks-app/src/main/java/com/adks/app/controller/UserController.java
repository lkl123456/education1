package com.adks.app.controller;

import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.adks.app.util.JsonResponse;
import com.adks.commons.oss.OSSUploadUtil;
import com.adks.commons.util.PropertiesFactory;
import com.adks.dubbo.api.data.user.Adks_user;
import com.adks.dubbo.api.interfaces.app.user.UserAppApi;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value = "/api/user")
public class UserController {

	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserAppApi userApi;

	/**
	 * 获取个人信息
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/userCenter.do")
	@ResponseBody
	public JsonResponse userCenter(String data) throws IOException {
		String error = null;

		/* 将json字符串解析为map */
		ObjectMapper mapper = new ObjectMapper();
		/* 返回参数 */
		Map resultMap = new HashMap();

		if (StringUtils.isNotEmpty(data)) {
			Map map = mapper.readValue(data, Map.class);

			/* 获取用户id */
			int userId = MapUtils.getInteger(map, "userId");

			/* 获取用户信息 */
			Map<String, Object> rMap = userApi.getUserByUserId(userId);
			if(rMap.get("studyAllXueShi")!=null){
				DecimalFormat df = new DecimalFormat("0.0");//格式化小数，不足的补0
				df.setRoundingMode(RoundingMode.FLOOR);  //取消四舍五入
				Double period=Double.valueOf((Integer)rMap.get("studyAllXueShi"))/Double.valueOf(2700);
				rMap.put("studyAllXueShi",df.format(period));//返回的是String类型的
			}
			resultMap.put("user", rMap);
			resultMap.put("xueshi", rMap.get("studyAllXueShi"));/* 用户在线学习时长 */

		} else
			error = "未接受到参数";
		if (StringUtils.isNotEmpty(error))
			return JsonResponse.error(error);
		return JsonResponse.success(resultMap);
	}

	/**
	 * 修改个人信息
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/userConfigSave")
	@ResponseBody
	public JsonResponse userConfigSave(HttpServletRequest request, String data) throws IOException {
		String error = null;
		// 用户头像图片存储地址
		String userImgPath = PropertiesFactory.getProperty("ossConfig.properties", "oss.UserImg_Path");
		String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");

		/* 将json字符串解析为map */
		ObjectMapper mapper = new ObjectMapper();
		/* 返回参数 */
		Map resultMap = new HashMap();

		if (StringUtils.isNotEmpty(data)) {
			Map map = mapper.readValue(data, Map.class);

			/* 获取用户id */
			int userId = MapUtils.getInteger(map, "userId");
			/* 获取用户信息 */
			Map<String, Object> rMap = userApi.getUserByUserId(userId);

			/* 真实名字 */
			String realName = MapUtils.getString(map, "realName");
			/* 性别:1男;2女 */
			int userSex = MapUtils.getInteger(map, "sex");
			/* 电话 */
			String userPhone = MapUtils.getString(map, "cellPhone");
			/* 邮件 */
			String userMail = MapUtils.getString(map, "mail");

			Adks_user user = new Adks_user();
			if (StringUtils.isNotEmpty(realName)) {
				user.setUserRealName(realName);
			}
			if (userSex != 0) {
				user.setUserSex(userSex);
			}
			if (StringUtils.isNotEmpty(userPhone)) {
				user.setUserPhone(userPhone);
			}
			if (StringUtils.isNotEmpty(userMail)) {
				user.setUserMail(userMail);
			}
			if (rMap != null) {
				user.setUserId(MapUtils.getInteger(rMap, "userId"));
				user.setHeadPhoto(MapUtils.getString(rMap, "headPicpath"));
			}

			// 处理头像
			MultipartFile headPhotofile = null;
			if (request instanceof MultipartRequest) {
				MultipartRequest multipartRequest = (MultipartRequest) request;
				headPhotofile = multipartRequest.getFile("headPicpath");
			}
			if (headPhotofile != null && StringUtils.isNotEmpty(headPhotofile.getOriginalFilename())) {
				InputStream is = headPhotofile.getInputStream();
				String type = headPhotofile.getOriginalFilename();
				type = type.substring(type.lastIndexOf(".") + 1, type.length());
				if (user.getHeadPhoto() == null || "".equals(user.getHeadPhoto())) {
					// 上传图片
					String new_Path = OSSUploadUtil.uploadFileNewName(is, type, userImgPath);
					String[] paths = new_Path.split("/");
					String code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
					user.setHeadPhoto(code);
				} else if (user.getHeadPhoto() != null && !"".equals(user.getHeadPhoto())) {
					String new_Path = OSSUploadUtil.replaceFile(is,type,ossResource + user.getHeadPhoto(), userImgPath);
					String[] paths = new_Path.split("/");
					String code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
					user.setHeadPhoto(code);
				}
			}
			/* 保存用户 */
			boolean result = userApi.saveUser(user);
			if (result)
				resultMap.put("succ", "修改信息成功！");
			else
				resultMap.put("succ", "修改信息失败！");
			resultMap.put("user", user);
		} else
			error = "未接受到参数";
		if (StringUtils.isNotEmpty(error))
			return JsonResponse.error(error);
		return JsonResponse.success(resultMap);
	}
}
