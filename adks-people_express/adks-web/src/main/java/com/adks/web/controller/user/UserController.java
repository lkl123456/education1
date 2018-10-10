package com.adks.web.controller.user;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.adks.commons.oss.OSSUploadUtil;
import com.adks.commons.util.ComUtil;
import com.adks.commons.util.PasswordUtil;
import com.adks.commons.util.PropertiesFactory;
import com.adks.commons.util.TimeUtils;
import com.adks.dubbo.api.data.org.Adks_org_config;
import com.adks.dubbo.api.data.user.Adks_user;
import com.adks.dubbo.api.data.user.Adks_usercollection;
import com.adks.dubbo.api.data.zhiji.Adks_rank;
import com.adks.dubbo.api.interfaces.web.course.CourseApi;
import com.adks.dubbo.api.interfaces.web.org.OrgApi;
import com.adks.dubbo.api.interfaces.web.user.UserApi;
import com.adks.dubbo.api.interfaces.web.user.UserconllectionApi;
import com.adks.dubbo.api.interfaces.web.zhiji.ZhijiApi;
import com.adks.dubbo.commons.Page;
import com.adks.web.framework.sms.interfacej.SmsClientBanlance;
import com.adks.web.framework.sms.interfacej.SmsClientSend;
import com.adks.web.util.BaseConfigHolder;

@Controller
@RequestMapping({ "/user" })
public class UserController {

	@Autowired
	private UserApi userApi;

	@Autowired
	private ZhijiApi zhijiApi;

	@Autowired
	private UserconllectionApi ucApi;
	@Autowired
	private CourseApi courseWebApi;
	@Autowired
	private OrgApi orgApi;

	/* 首页点击登录后的跳转 */
	@RequestMapping(value = "/toLogin.do")
	public String toLogin(HttpServletRequest request, HttpServletResponse response, Integer orgId, Model model) {
		Adks_org_config orgConfig = orgApi.getOrgConfigByOrgId(orgId);
		model.addAttribute("orgConfig", orgConfig);// 机构配置信息，首页上边的banner图地址
		model.addAttribute("orgId", orgId);
		return "/login";
	}

	/* 登录拦截时判断用户已经登录的方法 */
	@RequestMapping(value = "/otherPlaceLogin.do")
	public String otherPlaceLogin(HttpServletRequest request, HttpServletResponse response, Integer orgId,
			Model model) {
		model.addAttribute("orgId", orgId);
		model.addAttribute("message", "对不起，您的账号已在其它地方登录！");
		return "/login";
	}

	// 获取职级信息
	@RequestMapping(value = "/getZhijiListAll.do")
	@ResponseBody
	public List<Adks_rank> getZhijiListAll(HttpServletRequest request, HttpServletResponse response, Integer orgId,
			Model model) {
		List<Adks_rank> zhijiList = zhijiApi.getZhijiListAll();
		if(zhijiList!=null && zhijiList.size()>0){
			Adks_rank rank=new Adks_rank();
			rank.setRankId(0);
			rank.setRankCode("0A");
			rank.setRankName("全部");
			rank.setParentId(-1);
			zhijiList.add(rank);
		}
		return zhijiList;
	}

	/* 获取用户个人信息 */
	@RequestMapping(value = "/userInfo.do")
	public String userInfo(HttpServletRequest request, HttpServletResponse response, Integer orgId, Model model) {
		String flag = request.getParameter("flag");
		Adks_user user = (Adks_user) request.getSession().getAttribute("user");
		user = userApi.getUserInfoById(user.getUserId());
		if(user.getRankId()!=null && user.getRankId()!=0){
			List<Adks_rank> zhiwuList=zhijiApi.getZhijiListByCon(user.getRankId());
			model.addAttribute("zhiWuList", zhiwuList);
		}
		// List<Adks_rank> zhijiList=zhijiApi.getZhijiListAll();
		// model.addAttribute("zhijiList", zhijiList);
		model.addAttribute("flag", flag);
		model.addAttribute("user", user);
		model.addAttribute("orgId", orgId);
		return "/user/userInfo";
	}
	
	//根据职级得到职务
	@RequestMapping(value = "/getZhiwu.do")
	@ResponseBody
	public List<Adks_rank> getZhiwu(HttpServletRequest request, HttpServletResponse response, Integer zhijiId){
		List<Adks_rank> zhiwuList=zhijiApi.getZhijiListByCon(zhijiId);
		return zhiwuList;
	}

	// 查看用户手机号码是否重复
	@RequestMapping(value = "/checkUserPhone.do")
	@ResponseBody
	public void checkUserPhone(HttpServletRequest request, HttpServletResponse response, String userPhone,
			Integer userId, Model model) {
		PrintWriter pw = null;
		String result = "error";
		try {
			pw = response.getWriter();
			Adks_user user = userApi.checkUserCellPhone(userPhone);// 返回“yes”
																	// 可用，“no”
																	// 不可用
			if (user != null) {
				if (userId != null && userId.equals(user.getUserId())) {
					result = "succ";
				} else {
					result = "error";
				}
			} else {
				result = "succ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.addHeader("content-type", "text/html");
			pw.write(result);
			pw.flush();
			pw.close();
		}
	}

	// 得到用户的密码
	@RequestMapping(value = "/getUserPassWord.do")
	@ResponseBody
	public void getUserPassWord(HttpServletRequest request, HttpServletResponse response, String password,
			Integer userId, Model model) {
		PrintWriter pw = null;
		String result = "error";
		try {
			pw = response.getWriter();
			String userPassword = userApi.getUserPassWordByUserId(userId);// 返回“yes”
																			// 可用，“no”
																			// 不可用
			if (userPassword == null || "".equals(userPassword)) {
				result = "error";
			} else {
				if (PasswordUtil.getSHA1Password(password).equals(userPassword)) {
					result = "succ";
				} else {
					result = "error";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.addHeader("content-type", "text/html");
			pw.write(result);
			pw.flush();
			pw.close();
		}
	}

	// 修改用户的密码
	@RequestMapping(value = "/saveUserPassWord.do")
	public void saveNewPassWord(HttpServletRequest request, HttpServletResponse response, String password,
			Integer userId) {
		try {
			PrintWriter pw = response.getWriter();
			String newPswd = "";
			if (ComUtil.isNotNullOrEmpty(password)) {
				newPswd = PasswordUtil.getSHA1Password(password);
			}
			boolean retVal = userApi.saveUserPassWordByUserId(userId, newPswd);
			if (retVal) {
				pw.write("succ");
			} else {
				pw.write("error");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 保存用户修改信息saveUserInfo
	@RequestMapping(value = "/saveUserInfo.do")
	public String saveUserInfo(HttpServletRequest request, HttpServletResponse response, Adks_user user, Model model,
			@RequestParam(value = "headPhotofile", required = false) MultipartFile headPhotofile) {
		// 用户头像图片存储地址
		String userImgPath = PropertiesFactory.getProperty("ossConfig.properties", "oss.UserImg_Path");
		String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");

		if (user != null) {
			if (headPhotofile != null) {
				if (StringUtils.isNotEmpty(headPhotofile.getOriginalFilename())) {
					// 将MultipartFile转换成文件流
					/*CommonsMultipartFile cf = (CommonsMultipartFile) headPhotofile;
					DiskFileItem fi = (DiskFileItem) cf.getFileItem();
					File file = fi.getStoreLocation();*/
					try {
						InputStream is=headPhotofile.getInputStream();
						String type = headPhotofile.getOriginalFilename();
						type = type.substring(type.lastIndexOf(".") + 1, type.length());
						if (user.getHeadPhoto() == null || "".equals(user.getHeadPhoto())) {
							// 上传图片
							String new_Path = OSSUploadUtil.uploadFileNewName(is, type, userImgPath);
							System.out.println("new_Path upload:"+new_Path);
							String[] paths = new_Path.split("/");
							String code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
							user.setHeadPhoto(code);
						} else if (user.getHeadPhoto() != null && !"".equals(user.getHeadPhoto())) {
							String new_Path = OSSUploadUtil.replaceFile(is, type, ossResource + user.getHeadPhoto(), userImgPath);
							System.out.println("new_Path replace:"+new_Path);
							String[] paths = new_Path.split("/");
							String code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
							user.setHeadPhoto(code);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			if((user.getPositionId()==null || user.getPositionId() ==0)&& user.getRankNameStr()!=null && !"".equals(user.getRankNameStr())){
				Map<String,Object> map=zhijiApi.getZHijiByName(user.getRankNameStr());
				if(map!=null && map.size()>0){
					user.setPositionId(Integer.parseInt(map.get("rankId")+""));
					user.setPositionName(map.get("rankName")+"");
				}else{
					user.setPositionName(user.getRankNameStr());
					Adks_rank rank=new Adks_rank();
					rank.setParentId(0);
					rank.setParentName("职级");
					rank.setOrderNum(1);
					rank.setIsdelete(2);
					rank.setRankName(user.getRankNameStr());
					rank.setZhijiId(user.getRankId());
					Integer rankId=zhijiApi.saveZhiji(rank);
					user.setPositionId(rankId);
				}
			}
			boolean retVal = userApi.updateUser(user);
			if (retVal) {
				model.addAttribute("succ", "修改信息成功！");
			} else {
				model.addAttribute("succ", "修改信息失败！");
			}
			model.addAttribute("user", user);

			Adks_user session_user = (Adks_user) request.getSession().getAttribute("user"); // 重新设置session中user的图像
			// session_user.setHeadPicpath(user.getHeadPicpath());
			session_user.setUserRealName(user.getUserRealName());
			session_user.setUserPhone(user.getUserPhone());
			session_user.setUserSex(user.getUserSex());
			session_user.setHeadPhoto(user.getHeadPhoto());
			request.getSession().setAttribute("user", session_user);// session中重新放置用户头像,更新
		}
		return "/user/userInfo";
	}

	// 得到用户收藏的课程
	@RequestMapping(value = "/myCollectionList.do")
	public String myCollectionList(Page<List<Adks_usercollection>> page, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		if (null == page) {
			page = new Page();
		}
		Adks_user user = (Adks_user) request.getSession().getAttribute("user"); // 重新设置session中user的图像
		Map map = new HashMap();
		map.put("userId", user.getUserId());
		page.setMap(map);
		page = ucApi.myCollectionList(page);
		List<Adks_usercollection> list = page.getRows();
		if (list != null && list.size() > 0) {
			for (Adks_usercollection uc : list) {
				if (uc.getStudyTimeLong() == null || "".equals(uc.getStudyTimeLong())) {
					uc.setStudyTimeLong(0);
				}
				uc.setLastPositionStr(TimeUtils.secToTime(uc.getStudyTimeLong()));
			}
		}
		model.addAttribute("page", page);
		return "/user/myCollectionList";
	}

	// 查看用户的数据是否完善
	@RequestMapping(value = "/checkData.do")
	@ResponseBody
	public void checkData(HttpServletRequest request, HttpServletResponse response) {
		Adks_user user = (Adks_user) request.getSession().getAttribute("user");
		String result = "succ";
		if (user != null) {
			Adks_user loginUser = userApi.getUserInfoById(user.getUserId());
			if (loginUser.getUserPhone() == null || "".equals(loginUser.getUserPhone())) {
				result = "error";
			}
			/*
			 * if(loginUser.getCard()==null || "".equals(loginUser.getCard())){
			 * result="error"; }
			 */
			if (loginUser.getOrgId() == null || "".equals(loginUser.getOrgId())) {
				result = "error";
			}
		} else {
			result = "nouser";
		}
		PrintWriter pw = null;
		try {
			response.setContentType("application/text;charset=utf-8");
			pw = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		pw.write(result);
		pw.flush();
		pw.close();
	}

	// 完善用户信息
	@RequestMapping(value = "/completeData.do")
	public String completeData(HttpServletRequest request, HttpServletResponse response, Integer gradeId, Model model) {
		Adks_user user = (Adks_user) request.getSession().getAttribute("user");
		Adks_user loginUser = userApi.getUserInfoById(user.getUserId());
		// List<ZhiJi> zhijiList=zhijiDao.getZhiJiList();
		model.addAttribute("user", loginUser);
		// mv.addObject("zhijiList", zhijiList);
		model.addAttribute("gradeId", gradeId);
		return "/user/userComplete";
	}

	// 验证用户的身份证信息
	@RequestMapping(value = "/checkCardName.do")
	@ResponseBody
	public void checkCardName(HttpServletRequest request, HttpServletResponse response, Integer userId,
			String userCard) {
		String result = "succ";
		Adks_user user = userApi.getUserByCard(userCard);
		if (user != null) {
			if (user.getUserId().equals(userId)) {
				result = "succ";
			} else {
				result = "error";
			}
		} else {
			result = "succ";
		}
		PrintWriter pw = null;
		try {
			response.setContentType("application/text;charset=utf-8");
			pw = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		pw.write(result);
		pw.flush();
		pw.close();
	}

	// 得到验证码
	@RequestMapping(value = "/getSeccode.do")
	@ResponseBody
	public void getSeccode(HttpServletRequest request, HttpServletResponse response, String userPhone) {
		PrintWriter pw = null;
		String result = "error";
		try {
			pw = response.getWriter();
			String sendsmsurl = BaseConfigHolder.getSendsmsurl();
			String banlanceurl = BaseConfigHolder.getBanlanceurl();

			String account = BaseConfigHolder.getAccount();
			String password = BaseConfigHolder.getPassword();

			String msg = "";
			Integer i = 6;
			while (i > 0) {
				msg += (Integer) new Random().nextInt(9) + "";
				i--;
			}
			// userCellphone="17326865430";
			// String msgContent = "【中共民航局党校】校验码:"+msg+"";
			String msgContent = "尊敬的用户，您本次手机验证码是：" + msg + "，请在验证页面输入该数字。";
			String sendResult = SmsClientSend.sendSms(sendsmsurl, account, password, userPhone.trim(), msgContent);
			System.out.println("account sendResult " + sendResult);

			String banlanceResult = SmsClientBanlance.queryBanlance(banlanceurl, account, password);

			System.out.println("account banlance value " + banlanceResult);
			result = msg;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.addHeader("content-type", "text/html");
			pw.write(result);
			pw.flush();
			pw.close();
		}
	}

	// 保存用户的完善信息
	@RequestMapping(value = "/saveUserComplete.do")
	@ResponseBody
	public void saveUserComplete(HttpServletRequest request, HttpServletResponse response, Adks_user user) {
		PrintWriter pw = null;
		String result = "error";
		try {
			pw = response.getWriter();
			if (user != null) {
				boolean b = userApi.updateUser(user);
				if (b) {
					result = "succ";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.addHeader("content-type", "text/html");
			pw.write(result);
			pw.flush();
			pw.close();
		}
	}
	
	//在线帮助
	@RequestMapping(value = "/feedbackIndex.do")
	public String feedbackIndex(HttpServletRequest request, HttpServletResponse response,Integer orgId,Model model) {
		model.addAttribute("orgId", orgId);
		model.addAttribute("mainFlag", "feedback");
		return "/index/mainIndex";
	}
	@RequestMapping(value = "/removeMyCollection.do")
	public String removeMyCollection(Page<List<Adks_usercollection>> page,HttpServletRequest request, HttpServletResponse response,Integer courseId,Integer userConId,Model model){
		Adks_user user = (Adks_user) request.getSession().getAttribute("user");
		boolean retVal = userApi.deleteMyCollectionById(userConId);
		//移除收藏成功后,更新该课程的  chooseNum
		courseWebApi.updateCourseChooseNum(courseId,"off");
		
		Map map = new HashMap();
		map.put("userId", user.getUserId());
		page.setMap(map);
		page = ucApi.myCollectionList(page);
		List<Adks_usercollection> list = page.getRows();
		if (list != null && list.size() > 0) {
			for (Adks_usercollection uc : list) {
				if (uc.getStudyTimeLong() == null || "".equals(uc.getStudyTimeLong())) {
					uc.setStudyTimeLong(0);
				}
				uc.setLastPositionStr(TimeUtils.secToTime(uc.getStudyTimeLong()));
			}
		}
		model.addAttribute("page", page);
		return "/user/myCollectionList";
	}
}
