package com.adks.admin.controller.news;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adks.admin.util.Constants;
import com.adks.admin.util.LogUtil;
import com.adks.dubbo.api.data.common.Adks_friendly_link;
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.interfaces.admin.log.LogApi;
import com.adks.dubbo.api.interfaces.admin.news.FriendlylinkApi;
import com.adks.dubbo.api.interfaces.admin.org.OrgApi;
import com.adks.dubbo.commons.Page;

@Controller
@RequestMapping(value="/friendlylink")
public class FriendlylinkController {
	
private final Logger logger = LoggerFactory.getLogger(NewsController.class);
	
	@Resource
	private FriendlylinkApi friendlylinkApi;
	
	private String org_sn_name;
	
	@Autowired
	private OrgApi orgApi;
	@Autowired
	private LogApi logApi;
	
	@ModelAttribute
	public void beforec(HttpServletRequest request){
		String orgunameValue = request.getParameter("orgunameValue");
		org_sn_name = (String)request.getSession().getAttribute(Constants.SESSION_USER_ORG_SN_NAME);
		if(orgunameValue != null && !"".equals(orgunameValue)){
			org_sn_name = orgunameValue;
		}
		System.out.println("优先执行的获取到的    org_sn_name是："+org_sn_name);
	}
	
	/**
	 * 页面跳转，进入友情链接管理界面，仅仅是页面跳转，没有任何业务逻辑
	 * @return String
	 * @date 2017-03-08
	 */
    @RequestMapping({"/toFriendlylinkList"})
    public String friendlylinkList(Model model) {
    	logger.debug("进入friendlylinkList controller...");
    	model.addAttribute("org_sn_name",org_sn_name);
    	return "/news/friendlylinkList";
    }

    /**
     * 友情链接管理界面，获取友情链接列表json数据
     * @param page :分页参数
     * @param rows :分页参数
     * @param orgunameValue : 超管选择的分校 uname-sn
     * @return page
     * @date 2017-03-08
     */
    @RequestMapping(value="/getFriendlylinkListJson", produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public Page getFriendlylinkListJson(HttpServletRequest request,HttpServletResponse response,String s_friendlylink_name,Integer page,Integer rows,Integer orgId) {
    	
    	Page<List<Map<String, Object>>> paramPage = new Page<List<Map<String, Object>>>();
    	try {
    		//模糊查询 的参数
    		Map userMap=(Map)request.getSession().getAttribute("user");
    		Map likemap = new HashMap();
    		if(s_friendlylink_name != null && !"".equals(s_friendlylink_name)){
    			s_friendlylink_name = java.net.URLDecoder.decode(s_friendlylink_name, "UTF-8");
    			likemap.put("fdLinkName", s_friendlylink_name);
    		}
    		boolean isSuperManager="1".equals(userMap.get("isSuper")+"")?true:false;
    		if(!isSuperManager){
    			String orgCode=userMap.get("orgCode")+"";
    			likemap.put("orgCode",orgCode);
    		}
    		if(orgId != null && !"".equals(orgId) && orgId!=0){
    			Adks_org org = orgApi.getOrgById(orgId);
    			likemap.remove("orgCode");
    			likemap.put("orgCode", org.getOrgCode());
    		}else if(orgId != null && !"".equals(orgId) && orgId == 0){
    			likemap.remove("orgCode");
    			likemap.put("orgCode","0A");
    		}
    		paramPage.setMap(likemap);
    		paramPage.setPageSize(rows);
    		paramPage.setCurrentPage(page);
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
    	//调用dubbo服务获取map数据
    	Page<List<Map<String, Object>>> pageNew =  friendlylinkApi.getFriendlylinkListPage(paramPage);
    	List<Map<String, Object>> aList = pageNew.getRows();
    	for (Map<String, Object> map : aList) {
//    		System.out.println(map.toString());
    		Date date = (Date)map.get("createTime");
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    		String createTimeStr = sdf.format(date);
    		map.put("createTimeStr", createTimeStr);
    	}
    	return pageNew;
    }
    
    /**
     * 保存友情链接
     * @return
     * 2017-03-08
     */
    @RequestMapping(value="/saveFriendlylink",method=RequestMethod.POST)
    public void saveFriendlylink(Adks_friendly_link adksFriendlylink,Integer link_orgId,HttpServletRequest request,HttpServletResponse response){
    	response.setContentType("text/html;charset=utf-8");
    	if(adksFriendlylink != null){
    		adksFriendlylink.setCreateTime(new Date());
    		Map userMap=(Map)request.getSession().getAttribute("user");
			Integer userId = Integer.parseInt(userMap.get("userId")+"");
			String username = userMap.get("username")+"";
    		adksFriendlylink.setCreatorId(userId);
    		adksFriendlylink.setCreatorName(username);
    		if(link_orgId!= null && !"".equals(link_orgId)){
        		Adks_org org =null;
        		if(link_orgId!=null){
        			org=orgApi.getOrgById(link_orgId);
        		}else{
        			org=orgApi.getOrgById(adksFriendlylink.getOrgId());
        		}
        		adksFriendlylink.setOrgId(org.getOrgId());
        		adksFriendlylink.setOrgName(org.getOrgName());
        		adksFriendlylink.setOrgCode(org.getOrgCode());
        	}
    		try {
    			Integer dataId=friendlylinkApi.saveFriendlylink(adksFriendlylink);
    			//数据操作成功，保存操作日志
				if(dataId!=null&&dataId!=0){
					//添加/修改 
					if(adksFriendlylink.getFdLinkId()==null){  //添加
						logApi.saveLog(dataId,adksFriendlylink.getFdLinkName(),LogUtil.LOG_FRIENDLYLINK_MODULEID,LogUtil.LOG_ADD_TYPE,userMap);
					}else{  //修改
						logApi.saveLog(adksFriendlylink.getFdLinkId(),adksFriendlylink.getFdLinkName(),LogUtil.LOG_FRIENDLYLINK_MODULEID,LogUtil.LOG_UPDATE_TYPE,userMap);
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
     * 删除友情链接
     * @param fdLinkIds
     * @param request
     * @param response
     * 2017-03-08
     */
    @RequestMapping(value="/deleteFriendlylink",method=RequestMethod.GET)
    public void deleteFriendlylink(String fdLinkIds,HttpServletRequest request,HttpServletResponse response){
    	response.setContentType("application/text;charset=utf-8");
    	try {
    		System.out.println(fdLinkIds+"=======================");
    		if(fdLinkIds != null && fdLinkIds.length() > 0){
    			friendlylinkApi.deleteFriendlylinkByIds(fdLinkIds);
    			String[] fLinkIds=fdLinkIds.split(",");
    			Map map=(Map)request.getSession().getAttribute("user");
    			for (String fLinkId : fLinkIds) {
    				//数据操作成功，保存操作日志
					if(fLinkId!=null){
						logApi.saveLog(Integer.valueOf(fLinkId),null,LogUtil.LOG_FRIENDLYLINK_MODULEID, LogUtil.LOG_DELETE_TYPE, map);
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
}
