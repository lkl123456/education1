package com.adks.admin.controller.user;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.adks.admin.controller.common.CommonController;
import com.adks.admin.util.Constants;
import com.adks.admin.util.EnumerationUtil;
import com.adks.admin.util.ExcelUtil;
import com.adks.admin.util.LogUtil;
import com.adks.admin.util.PasswordUtil;
import com.adks.commons.oss.OSSUploadUtil;
import com.adks.commons.util.DateUtils;
import com.adks.commons.util.PropertiesFactory;
import com.adks.dubbo.api.data.enumeration.Adks_enumeration_value;
import com.adks.dubbo.api.data.menu.Adks_menu;
import com.adks.dubbo.api.data.menu.Adks_menulink;
import com.adks.dubbo.api.data.news.Adks_news;
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.data.role.Adks_role;
import com.adks.dubbo.api.data.role.Adks_userrole;
import com.adks.dubbo.api.data.user.Adks_user;
import com.adks.dubbo.api.data.user.Adks_userVo;
import com.adks.dubbo.api.data.zhiji.Adks_rank;
import com.adks.dubbo.api.interfaces.admin.enumeration.EnumerationApi;
import com.adks.dubbo.api.interfaces.admin.log.LogApi;
import com.adks.dubbo.api.interfaces.admin.menu.MenuApi;
import com.adks.dubbo.api.interfaces.admin.org.OrgApi;
import com.adks.dubbo.api.interfaces.admin.role.RoleApi;
import com.adks.dubbo.api.interfaces.admin.role.UserroleApi;
import com.adks.dubbo.api.interfaces.admin.user.UserApi;
import com.adks.dubbo.api.interfaces.admin.zhiji.ZhijiApi;
import com.adks.dubbo.commons.ApiResponse;
import com.adks.dubbo.commons.Page;

@Controller
@RequestMapping(value = "/userIndex")
public class UserIndexController {
    private final Logger logger = LoggerFactory.getLogger(UserIndexController.class);

    @Autowired
    private CommonController commonController;

    @Autowired
    private UserLoginController userLoginController;

    @Autowired
    private EnumerationApi enumerationApi;

    @Autowired
    private MenuApi menuApi;

    @Autowired
    private OrgApi orgApi;

    @Autowired
    private UserApi userApi;

    @Autowired
    private UserroleApi userroleApi;
    
    @Autowired
    private LogApi logApi;
    @Autowired
    private RoleApi roleApi;
    @Autowired
    private ZhijiApi zhijiApi;

    @ModelAttribute
    public void beforec(HttpServletRequest request) {
    }

    @RequestMapping({ "/home" })
    public String home(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        logger.debug("UserIndexController登录成功后,跳转到管理系统首页!");
        return "/index";
    }

    @ResponseBody
    @RequestMapping(value = "/userMenuList", method = RequestMethod.POST)
    public List<Adks_menu> userMenuList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletContext application = request.getSession().getServletContext();
        List<Adks_menu> menuApplication = (List<Adks_menu>) application.getAttribute("menus");
        if (menuApplication == null) {
            List<Adks_menu> menuAll = menuApi.getMenuAll();
            List<Adks_menulink> menulinkAll = menuApi.getMenuLinkAll();
            if (menulinkAll != null && menulinkAll.size() > 0) {
                List<Adks_menulink> menus = null;
                for (Adks_menu menu : menuAll) {
                    menus = new ArrayList<Adks_menulink>();
                    for (Adks_menulink menuLink : menulinkAll) {
                        if (menuLink.getMenuId() == menu.getMenuId()) {
                            menus.add(menuLink);
                        }
                    }
                    menu.setMenus(menus);
                }
            }
            // 查询菜单列表
            application.setAttribute("menus", menuAll);
            menuApplication = menuAll;
        }
        return menuApplication;
    }

    @ResponseBody
    @RequestMapping(value = "/userMenu", method = RequestMethod.POST)
    public void menuLink(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        ApiResponse<Map> apiResponse = new ApiResponse<Map>();
        try {
            Map userMap = (Map) request.getSession().getAttribute("user");
            // Integer userId=1;
            Adks_userVo adks_userVo = commonController.getUserBySession();
            Integer userId = null;
            if (null != adks_userVo && null != adks_userVo.getUserId()) {
                userId = adks_userVo.getUserId();
                String menus = menuApi.getMenuLinksByuserId(userId);
                pw.print(menus);
                pw.flush();
            }

        }
        catch (Exception e) {
            logger.error("login failed. username:{}", request.getParameter("abc"), e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        finally {
            pw.close();
        }

    }

    /**
     * 页面跳转，进入用户管理主界面，仅仅是页面跳转，没有任何业务逻辑
     * 
     * @return
     */
    @RequestMapping({ "/userList" })
    // @RequireLogin(ResultTypeEnum.page)
    // @RequirePermission({Permission.UserListView})
    public String userList(Model model) {
        logger.debug("进入userList controller...");
        // model.addAttribute("org_sn_name",org_sn_name);

        String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
        model.addAttribute("ossResource", ossResource);
        return "/user/userList";
    }

    /**
     * 用户管理主界面，获取用户列表json数
     * 
     * @param page
     *            :分页参数
     * @param rows
     *            :分页参数
     * @param dept
     *            : 部门id
     * @param loginname
     *            : 搜索登录名
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/getUsersJson", produces = { "application/json; charset=UTF-8" })
    @ResponseBody
    public Page getUsersJson( String loginname, Integer orgId,Integer rankId,Integer positionId,Integer userStatus,
    		HttpServletRequest request, HttpServletResponse response,Integer page, Integer rows) {

        Page<List<Map<String, Object>>> paramPage = null;
        try {
            paramPage = new Page<List<Map<String, Object>>>();
            Map userMap = (Map) request.getSession().getAttribute("user");
            // 模糊查询 的参数
            Map likemap = new HashMap();
            boolean isSuperManager = "1".equals(userMap.get("isSuper") + "") ? true : false;
            if (!isSuperManager) {
                String orgCode = userMap.get("orgCode") + "";
                likemap.put("orgCode", orgCode);
            }
            if (orgId != null && !"".equals(orgId) && orgId!=0) {
                Adks_org org = orgApi.getOrgById(orgId);
                likemap.remove("orgCode");
                likemap.put("orgCode", org.getOrgCode());
            }else if(orgId != null && !"".equals(orgId) && orgId==0){
            	likemap.remove("orgCode");
                likemap.put("orgCode", "0A");
            }
            if (loginname != null && !"".equals(loginname)) {
                loginname = java.net.URLDecoder.decode(loginname, "UTF-8");
                likemap.put("userName", loginname);
            }
            
            //职务
    		if(rankId!=null&&rankId!=0){
    			likemap.put("rankId", rankId);
    		}
    		if(positionId!=null){
    			likemap.put("positionId", positionId);
    		}
    		
    		//用户状态
    		if(userStatus!=null){
    			likemap.put("userStatus", userStatus);
    		}

            paramPage.setMap(likemap);
            paramPage.setPageSize(rows);
            paramPage.setCurrentPage(page);
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return userApi.getUserListPage(paramPage);
    }

    //
    /**
     * 用户管理主界面，获取添加用户时的所有机构树，Ztree
     * 
     * @param parent
     *            :父部门id
     * @return
     */
    @RequestMapping(value = "/getOrgsJson", produces = { "application/json; charset=UTF-8" })
    @ResponseBody
    public List<Adks_org> getDeptsJson(HttpServletRequest request, String orgCode,Integer parentId) {
        Map userMap = (Map) request.getSession().getAttribute("user");
        List<Adks_org> maps=new ArrayList<Adks_org>();
        // dubbo获取 部门机构树json数据
        boolean isSuperManager = "1".equals(userMap.get("isSuper") + "") ? true : false;
        List<Adks_org> listOrg=null;
        if(parentId==null){
        	if(isSuperManager){
        		parentId=0;
        	}else{
        		parentId=Integer.parseInt(userMap.get("orgId")+"");
        	}
        }
        listOrg = orgApi.getOrgsListAll2(parentId);
        if(parentId==0){
        	Adks_org org=new Adks_org();
        	org.setId(0);
        	org.setName("机构");
        	org.setText("机构");
        	org.setOrgId(0);
        	org.setOrgCode("0A");
        	org.setOrgName("机构");
        	org.setChildren(listOrg);
        	maps.add(org);
        }else if(!isSuperManager && parentId==Integer.parseInt(userMap.get("orgId")+"")){
        	Adks_org org=orgApi.getOrgById(parentId);
        	org.setId(org.getOrgId());
        	org.setText(org.getOrgName());
        	org.setName(org.getOrgName());
        	org.setChildren(listOrg);
        	maps.add(org);
        }else{
        	maps=listOrg;
        }
        return maps;
    }
    
    /**
     * 只有添加课程时选择讲师页面使用，Ztree
     * 
     * @param parent
     *            :父部门id
     * @return
     */
    @RequestMapping(value = "/getOrgsJsontoAuthor", produces = { "application/json; charset=UTF-8" })
    @ResponseBody
    public List<Adks_org> getOrgsJsontoAuthor(HttpServletRequest request, String orgCode,Integer parentId,Integer first) {
        Map userMap = (Map) request.getSession().getAttribute("user");
        List<Adks_org> maps=new ArrayList<Adks_org>();
        List<Adks_org> listOrg = orgApi.getOrgsListAll2(parentId);
        if(first==1){
        	 if(parentId==0){
             	Adks_org org=new Adks_org();
             	org.setId(0);
             	org.setName("机构");
             	org.setText("机构");
             	org.setOrgId(0);
             	org.setOrgCode("0A");
             	org.setOrgName("机构");
             	org.setChildren(listOrg);
             	maps.add(org);
             }else{
             	Adks_org org=orgApi.getOrgById(parentId);
             	if(org!=null){
             		org.setId(org.getOrgId());
                 	org.setText(org.getOrgName());
                 	org.setName(org.getOrgName());
                 	org.setChildren(listOrg);
             	}
             	maps.add(org);
             }
        }else{
        	maps=listOrg;
        }
        return maps;
    }

    /**
     * 用户管理主界面，获取添加用户时的所有机构树
     * 
     * @param parent
     *            :父部门id
     * @return
     */
    @RequestMapping(value = "/getUserNationalityJson", produces = { "application/json; charset=UTF-8" })
    @ResponseBody
    public List<Adks_enumeration_value> getUserNationalityJson(String name) {
        // dubbo获取 部门机构树json数据
        List<Adks_enumeration_value> maps = null;
        if (EnumerationUtil.getEnValMap() == null) {
            EnumerationUtil.init(enumerationApi.getEnValAll(), enumerationApi.getEnAll());
        }
        maps = EnumerationUtil.getEnValByEnName(name);
        return maps;
    }
    
    /*
	 * 到用户添加的页面
	 */
	@RequestMapping(value = "/toAddUser", method = RequestMethod.GET)
	public String toAddNews(Integer userId, Model model) {
		Map<String,Object> user=new HashMap<String,Object>();
		if (userId == null && userId != null) {
		}
		if (userId != null) {
			user=userApi.getUserInfoById(userId);
			if(user.get("userBirthday")!=null && !"".equals(user.get("userBirthday"))){
				model.addAttribute("userBirthdayStr", user.get("userBirthday"));
			}
			if(user.get("overdate")!=null && !"".equals(user.get("overdate"))){
				model.addAttribute("overdateStr", user.get("overdate"));
			}
			/*if(user.get("rankId")!=null && !"".equals(user.get("rankId")) && !"0".equals(user.get("rankId"))){
				List<Adks_rank> zhiwuList=zhijiApi.getZhijiListByCon(Integer.parseInt(user.get("rankId")+""));
				model.addAttribute("zhiWuList", zhiwuList);
			}*/
		}
		String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
		model.addAttribute("ossResource", ossResource);
		model.addAttribute("user",user);
		return "/user/addUser";
	}

    /**
     * 保存、编辑用户
     * 
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/saveUser", produces = { "text/html;charset=utf-8" })
    @ResponseBody
    public void saveUser(HttpServletRequest request, HttpServletResponse response, Adks_user user, String userBir,
            String odate, @RequestParam(value = "headPhotofile", required = false) MultipartFile headPhotofile) {
        response.setContentType("text/html;charset=utf-8");

        // 用户头像图片存储地址
        String userImgPath = PropertiesFactory.getProperty("ossConfig.properties", "oss.UserImg_Path");
        String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
        String result = "";
        try {
            if (user != null) {
                boolean flag = true;
                // 网校唯一标识
                if (user.getUserName() != null) {
                    Adks_user temp = userApi.getUserByName(user.getUserName());
                    if (temp != null && ((temp.getUserId() != null && !temp.getUserId().equals(user.getUserId()))
                            || (user.getUserId() == null))) {
                        result = "snnameExists";
                        flag = false;
                    }
                }
                if (flag) {
                    // 上传用户头像图片
                    if (headPhotofile != null) {
                        if (StringUtils.isNotEmpty(headPhotofile.getOriginalFilename())) {
                            // 将MultipartFile转换成文件流
                            //CommonsMultipartFile cf = (CommonsMultipartFile) headPhotofile;
                            //DiskFileItem fi = (DiskFileItem) cf.getFileItem();
                            //File file = fi.getStoreLocation();
                            InputStream is = headPhotofile.getInputStream();
                            // 获取文件类型
                            String type = headPhotofile.getOriginalFilename();
                            type = type.substring(type.lastIndexOf(".") + 1, type.length());
                            if (user.getHeadPhoto() == null || "".equals(user.getHeadPhoto())) {
                                // 上传图片
                                String new_Path = OSSUploadUtil.uploadFileNewName(is, type, userImgPath);
                                String[] paths = new_Path.split("/");
                                String code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
                                user.setHeadPhoto(code);
                            }
                            else if (user.getHeadPhoto() != null && !"".equals(user.getHeadPhoto())) {
                                String new_Path = OSSUploadUtil.replaceFile(is, type, ossResource + user.getHeadPhoto(),
                                        userImgPath);
                                //String new_Path = OSSUploadUtil.updateFile(is, type, ossResource + user.getHeadPhoto());
                                String[] paths = new_Path.split("/");
                                String code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
                                user.setHeadPhoto(code);
                            }
                        }
                    }
                    if (userBir != null && !"".equals(userBir)) {
                        user.setUserBirthday(DateUtils.getStr2SDate(userBir));
                    }
                    if (odate != null && !"".equals(odate)) {
                        user.setOverdate(DateUtils.getStr2SDate(odate));
                    }
                    if (user.getUserId() == null) {
                        Map userMap = (Map) request.getSession().getAttribute("user");
                        Integer userId = Integer.parseInt(userMap.get("userId") + "");
                        String username = userMap.get("username") + "";
                        user.setUserPassword(PasswordUtil.getSHA1Password(user.getUserPassword()));
                        user.setCreatorId(userId);
                        user.setCreatorName(username);
                        user.setCreatedate(new Date());
                        user.setUserOnlineLong(0);
                        user.setUserStudyLong(0);
                    }
                    if((user.getPositionId()==null || user.getPositionId() ==0)&& user.getRankNameStr()!=null && !"".equals(user.getRankNameStr())){
        				Map<String,Object> map=zhijiApi.getZhiWuByName(user.getRankNameStr());
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
        					Integer rankId=zhijiApi.saveZhiWu(rank);
        					user.setPositionId(rankId);
        				}
        			}
                    Integer dataId=userApi.saveUser(user);
                    //数据操作成功，保存操作日志
					if(dataId!=null&&dataId!=0){
						Map map=(Map)request.getSession().getAttribute("user");
						//添加/修改 
						if(user.getUserId()==null){  //添加
							logApi.saveLog(dataId, user.getUserName(),LogUtil.LOG_USER_MODULEID,LogUtil.LOG_ADD_TYPE,map);
						}else{  //修改
							logApi.saveLog(user.getUserId(), user.getUserName(),LogUtil.LOG_USER_MODULEID,LogUtil.LOG_UPDATE_TYPE,map);
						}
					}
                    //机构人数的修改
                    if(user.getUserId()==null){
                    	 orgApi.updateOrgUserNum(user.getOrgCode(),1);
                    }
                    result = "succ";
                }
            }
            PrintWriter pWriter = response.getWriter();
            pWriter.write(result);
            pWriter.flush();
            pWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除用户
     * 
     * @param request
     * @param response
     * @param ids
     */
    @RequestMapping(value = "/delUser", method = RequestMethod.POST)
    public void delUser(HttpServletRequest request, HttpServletResponse response, String ids) {
        response.setContentType("application/text;charset=utf-8");
        try {
        	String result="succ";
        	boolean flag=true;
            if (ids != null) {
            	String[] userIds = ids.split(",");
    			for (String id : userIds) {
    				boolean isCan=userApi.canDelUser(Integer.parseInt(id));
    				if(!isCan){
    					flag=false;
    					break;
    				}
    			}
    			if(flag){
    				Map map =new HashMap<>();
                    for (String id : userIds) {
                    	Map<String,Object> usertemp=userApi.getUserInfoById(Integer.parseInt(id));
						orgApi.updateOrgUserNum(usertemp.get("orgCode")+"",2);
						map.put(id, usertemp.get("userName"));
        			}
                    userApi.deleteUserByIds(ids,map);
                    // 删除用户角色表
                    userroleApi.deleteUserRoleByUserId(ids);
                    Map userMap=(Map)request.getSession().getAttribute("user");
                    for (String userId : userIds) {
						//数据操作成功，保存操作日志
						if(userId!=null){
							logApi.saveLog(Integer.valueOf(userId),null,LogUtil.LOG_USER_MODULEID, LogUtil.LOG_DELETE_TYPE, userMap);
						}
					}
                    result="succ";
    			}else{
    				result="error";
    			}
            }
            PrintWriter pWriter = response.getWriter();
            pWriter.write(result);
            pWriter.flush();
            pWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/changeUser", method = RequestMethod.POST)
    public void changeUser(String userIds, String handelType, HttpServletRequest request,
            HttpServletResponse response) {
        response.setContentType("application/text;charset=utf-8");
        try {
            if (userIds != null && userIds.length() > 0) {
                String[] uids = userIds.split(",");
                String pwd = PasswordUtil.getSHA1Password(Constants.SYS_DEF_PASSWORD);
                for (String string : uids) {
                	Map map=userApi.getUserInfoById(Integer.parseInt(string));
                    Adks_user user = new Adks_user();
                    user.setUserPassword(pwd); // 重置密码，传过来，111111，修改密码，传过来用户填写的密码
                    user.setUserId(Integer.parseInt(string));
                    userApi.changeUser(user, Integer.parseInt(handelType),map.get("userName")+"");
                }
            }

            PrintWriter pWriter = response.getWriter();
            pWriter.write("succ");
            pWriter.flush();
            pWriter.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量导入用户
     * 
     * @param request
     * @param response
     */
    @RequestMapping(value = "/importUsers", produces = { "text/html;charset=utf-8" }, method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse<Object> importBatchUsers(
            @RequestParam(value = "importExcelFile", required = false) MultipartFile importExcelFile, Integer orgId,
            String orgName, String orgCode, HttpServletRequest request, HttpServletResponse response) {
        if (importExcelFile != null && !StringUtils.isEmpty(importExcelFile.getOriginalFilename())) {
            InputStream inputStream = null;
            try {
                inputStream = importExcelFile.getInputStream();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            if (orgName != null && !"".equals(orgName)) {
                try {
                    orgName = java.net.URLDecoder.decode(orgName, "UTF-8");
                }
                catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {

                ApiResponse<List<Adks_user>> usersResult = ExcelUtil.readExcelToUsers(inputStream);
                if (usersResult.isSuccess()) {

                    List<Adks_user> users = usersResult.getBody();
                    System.out.println("读取的excel获取用户个数：" + users.size());
                    // 循环users 入库
                    Map<String, Object> deptInfo = null;

                    // 先校验excel数据是否 不重复
                    for (int i = 0; i < users.size(); i++) {
                        Adks_user user = users.get(i);
                        if (userApi.checkUserName(user.getUserName())) {
                            continue; 
                        }
                        else {
                            return ApiResponse.fail(500, "第" + (i + 1) + "行用户名已经被使用");
                        }
                    }

                    for (Adks_user user : users) {
                        // 验证用户登录名和邮箱是否可用，
                        user.setUserName(user.getUserName());
                        user.setOrgId(orgId);
                        user.setOrgName(orgName);
                        user.setOrgCode(orgCode);

                        Map userMap = (Map) request.getSession().getAttribute("user");
                        Integer userId = Integer.parseInt(userMap.get("userId") + "");
                        String username = userMap.get("username") + "";
                        user.setCreatorId(userId);
                        user.setCreatorName(username);
                        user.setCreatedate(new Date());
                        user.setUserStatus(1); // 默认添加用户开通
                        // 默认密码：111111
                        user.setUserPassword(PasswordUtil.getSHA1Password(Constants.SYS_DEF_PASSWORD));
                        user.setUserSex(1);
                        user.setUserStudyLong(0);
                        user.setUserOnlineLong(0);
                        user.setUserParty(14);//政治面貌
                        user.setUserNationality(3);//名族
                        user.setOverdate(DateUtils.getStr2LDate("2099-12-30 00:00:00"));
                        userApi.saveUser(user);
                        if(user.getUserId()==null){
                       	 orgApi.updateOrgUserNum(user.getOrgCode(),1);
                       }

                    }
                    return ApiResponse.success("导入成功!共导入" + users.size() + "个用户", null);
                }
                else {
                    return ApiResponse.fail(500, usersResult.getMessage());
                }

            }
            else {
                return ApiResponse.fail(500, "File inputStream is null !");
            }
        }

        return null;
    }

    /*
     * 以下是对用户角色的操作
     * 
     */
    @RequestMapping({ "/userRoleList" })
    public String userRoleList(HttpServletRequest request, HttpServletResponse response, Integer userId,
            String userName, Model model) throws IOException {
        // String username=ObjectUtil.getUserName(redisCient.get("user"));
        ServletContext application = request.getSession().getServletContext();
        System.out.println("application----------------" + application.getAttribute("attr"));
        userName = java.net.URLDecoder.decode(userName, "UTF-8");
        Map<String,Object> user=userApi.getUserInfoById(userId);
        model.addAttribute("userName", user.get("userName"));
        model.addAttribute("userId", userId);
        return "/user/userRoleList";
    }

    /**
     * 根据用户的id获取用户的角色
     * 
     * @param parent
     *            :父部门id
     * @return
     */
    @RequestMapping(value = "/getUserRoleByUser", produces = { "application/json; charset=UTF-8" })
    @ResponseBody
    public String getUserRoleByUser(Integer userId) {
        String roleIds = "";
        if (userId != null) {
            List<Map<String, Object>> userRoles = userroleApi.getRoleListByUser(userId);
            if (userRoles != null && userRoles.size() > 0) {
                for (Map<String, Object> map : userRoles) {
                    roleIds = map.get("roleId") + ",";
                }
            }
            if (!"".equals(roleIds)) {
                roleIds = roleIds.substring(0, roleIds.length() - 1);
            }
        }
        System.out.println("UserIndexController  userroles:" + roleIds);
        return roleIds;
    }

    /**
     * 取消用户角色关联
     * 
     * @param parent
     *            :父部门id
     * @return
     */
    @RequestMapping(value = "/delUserRole", produces = { "application/json; charset=UTF-8" })
    @ResponseBody
    public String delUserRole(Integer userId, String roleIds) {
        if (userId != null && roleIds != null && !"".equals(roleIds)) {
            String[] ids = roleIds.split(",");
            for (String id : ids) {
                userroleApi.deleteUserRoleByCon(Integer.parseInt(id), userId);
            }
        }
        return "succ";
    }

    /**
     * 添加用户角色相关联
     * 
     * @param parent
     *            :父部门id
     * @return
     */
    @RequestMapping(value = "/addUserRole", produces = { "application/json; charset=UTF-8" })
    @ResponseBody
    public String addUserRole(HttpServletRequest request, Integer userId, String roleIds) {
        String result = "succ";
        if (userId != null && roleIds != null && !"".equals(roleIds)) {
            String[] ids = roleIds.split(",");
            // 判断登录用户是否有权限设置响应的角色
            Map userMap = (Map) request.getSession().getAttribute("user");
            Integer userloginId = Integer.parseInt(userMap.get("userId") + "");
            Integer isGlobRole = userroleApi.isGlobRole(userloginId);
            boolean falg = false;
            if (isGlobRole == 1) {
                falg = true;
            }
            else {
                boolean ok = true;
                for (String id : ids) {
                    Integer roleId = Integer.parseInt(id);
                    Adks_role role = roleApi.getRoleById(roleId);
                    if (role.getIsGlob() == 1) {
                        ok = false;
                        break;
                    }
                }
                if (ok) {
                    falg = true;
                }
            }
            if (falg) {
                for (String id : ids) {
                    Adks_userrole userrole = userroleApi.getUserRoleByCon(Integer.parseInt(id), userId);
                    if (userrole == null) {
                        Adks_userrole ur = new Adks_userrole();
                        ur.setRoleId(Integer.parseInt(id));
                        ur.setUserId(userId);
                        userroleApi.addUserRole(ur);
                    }

                }
            }
            else {
                result = "noSucc";
            }
        }
        return result;
    }

    /*修改密码*/
    @RequestMapping({ "/personalSetting" })
    public String personalSetting(HttpServletRequest request, HttpServletResponse response, Model model)
            throws IOException {
        // String username=ObjectUtil.getUserName(redisCient.get("user"));
        ServletContext application = request.getSession().getServletContext();
        Map map = (Map) request.getSession().getAttribute("user");
        Integer userId = Integer.parseInt(map.get("userId") + "");
        String username = map.get("username") + "";
        model.addAttribute("username", username);
        return "/user/personalSetting";
    }

    @RequestMapping({ "/updatePassword" })
    @ResponseBody
    public void updatePassword(HttpServletRequest request, HttpServletResponse response, String passwordNew,
            String password, Model model) throws IOException {
        response.setContentType("application/text;charset=utf-8");
        String result = "succ";
        try {
            Map map = (Map) request.getSession().getAttribute("user");
            Integer userId = Integer.parseInt(map.get("userId") + "");
            Map<String, Object> user = userApi.getUserInfoById(userId);
            if (user != null && user.size() > 0) {
                password = PasswordUtil.getSHA1Password(password);
                passwordNew = PasswordUtil.getSHA1Password(passwordNew);
                if (!password.equals(user.get("userPassword"))) {
                    result = "nosucc";
                }
                else {
                    Integer i = userApi.updatePassword(userId,user.get("userName")+"", passwordNew);
                    if (i > 0) {
                        result = "succ";
                    }
                    else {
                        result = "error";
                    }
                }
            }
            else {
                result = "error";
            }
            PrintWriter pWriter = response.getWriter();
            pWriter.write(result);
            pWriter.flush();
            pWriter.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    //手机号码查重
    @RequestMapping({ "/checkUserPhone" })
    @ResponseBody
    public void updatePassword(HttpServletRequest request, HttpServletResponse response, String userPhone,
            Integer userId, Model model) throws IOException {
        response.setContentType("application/text;charset=utf-8");
        String result = "succ";
        try {
        	Map<String,Object> map=userApi.getUserInfoByPhone(userPhone);
            if (map != null && map.size() > 0) {
            	String mapUserId=map.get("userId")+"";
                if (userId!=null && mapUserId.equals(userId+"")) {
                    result = "succ";
                }else {
                    result = "error";
                }
            }else {
                result = "succ";
            }
            PrintWriter pWriter = response.getWriter();
            pWriter.write(result);
            pWriter.flush();
            pWriter.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
  //根据职级得到职务
  	@RequestMapping(value = "/getZhiwu.do")
  	@ResponseBody
  	public List<Adks_rank> getZhiwu(HttpServletRequest request, HttpServletResponse response, Integer zhijiId){
  		List<Adks_rank> zhiwuList=zhijiApi.getZhijiListByCon(zhijiId);
  		return zhiwuList;
  	}
  	@RequestMapping(value = "/loginOut.do")
    public String loginOut(HttpServletRequest request, HttpServletResponse response, HttpSession session,Integer userId,String userName, Model model) {
        if (session != null && session.getAttribute("user") != null) {
            userApi.deleteUserToRedis(userId,userName);
            session.removeAttribute("user");
            session.removeAttribute("orgConfigList");
        }
        String viewName = "";
        viewName="/login";
        return viewName;
    }
}
