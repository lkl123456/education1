package com.adks.admin.controller.menu;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adks.admin.util.LogUtil;
import com.adks.dubbo.api.data.menu.Adks_menu;
import com.adks.dubbo.api.data.menu.Adks_menulink;
import com.adks.dubbo.api.interfaces.admin.log.LogApi;
import com.adks.dubbo.api.interfaces.admin.menu.MenuApi;
import com.adks.dubbo.commons.Page;

@Controller
@RequestMapping(value="/menuLink")
public class MenuLinkController {
	private final Logger logger = LoggerFactory.getLogger(MenuLinkController.class);
	
	@Autowired
	private MenuApi menuApi;
	@Autowired
	private LogApi logApi;
	
	@RequestMapping({"/menuLinkList"})
	public String home(HttpServletRequest request, HttpServletResponse response,Integer menuId,String enName,Model model) throws IOException {
    	logger.debug("进入 MenuLinkController  menuLinkList...");
    	Adks_menu menu=menuApi.getMenuById(menuId);
    	model.addAttribute("menu", menu);
    	return "menu/menuLinkList";
    }
	//链接菜单
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/getMenuLinkListJson", produces = {"application/json; charset=UTF-8"})
	@ResponseBody
	public Page getMenuLinkListJson(Integer page, Integer rows,Integer menuId,String s_menuLink_name){
		Page<List<Map<String, Object>>> page_bean=null;
		try {
			page_bean = new Page<List<Map<String, Object>>>();
			page_bean.setCurrentPage(page);
			page_bean.setPageSize(rows);
			if((s_menuLink_name != null && !"".equals(s_menuLink_name)) || menuId !=null){
				Map map = new HashMap<>();
				if(s_menuLink_name != null && !"".equals(s_menuLink_name)){
					s_menuLink_name= java.net.URLDecoder.decode(s_menuLink_name,"UTF-8");
					map.put("menuLinkName", s_menuLink_name);
				}
				if(menuId != null){
					map.put("menuId", menuId);
				}
				page_bean.setMap(map);
			}
			page_bean = menuApi.getMenuLinkListPage(page_bean);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return page_bean;
	}
	
    /**
     * 保存链接菜单
     * @return
	 * @throws IOException 
     */
	@RequestMapping(value="/saveMenuLink", produces = {"text/html;charset=utf-8"})
    @ResponseBody
    public void saveEnumerationValueJson(HttpServletRequest request, HttpServletResponse response,Adks_menulink menuLink) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String message = "error";
		try {
			if(menuLink != null){
				boolean flag = true;
				// 网校唯一标识
				if(menuLink.getMenuLinkName()!= null){
					Map<String, Object> temp = menuApi.getMenuLinkByName(menuLink.getMenuLinkName());
					if(temp != null && temp.size() > 0 && 
							((menuLink.getMenuLinkName() != null && menuLink.getMenuLinkId() != MapUtils.getIntValue(temp, "menuLinkId")) 
							|| (menuLink.getMenuLinkName() == null))){
						message = "snnameExists";
						flag = false;
					}
				}
				if(flag){
					//清除菜单
					ServletContext application = request.getSession().getServletContext();
					application.setAttribute("menus", null);
					if(menuLink.getMenuLinkId() != null){
						
					}else{			
						Map userMap=(Map)request.getSession().getAttribute("user");
						Integer userId = Integer.parseInt(userMap.get("userId")+"");
						String username = userMap.get("username")+"";
						menuLink.setCreatorId(userId);
						menuLink.setCreatorName(username);
						menuLink.setCreateTime(new Date());
					}
					Integer dataId=menuApi.saveMenuLink(menuLink);
					//数据操作成功，保存操作日志
					if(dataId!=null&&dataId!=0){
						Map map=(Map)request.getSession().getAttribute("user");
						//添加/修改 
						if(menuLink.getMenuLinkId()==null){  //添加
							logApi.saveLog(dataId,menuLink.getMenuLinkName(),LogUtil.LOG_SECONDMENU_MODULEID,LogUtil.LOG_ADD_TYPE,map);
						}else{  //修改
							logApi.saveLog(menuLink.getMenuLinkId(),menuLink.getMenuLinkName(),LogUtil.LOG_SECONDMENU_MODULEID,LogUtil.LOG_UPDATE_TYPE,map);
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
	 * 删除链接菜单
	 * @param request
	 * @param response
	 * @param ids
	 */
	@RequestMapping(value = "/delMenuLink", method=RequestMethod.POST)
	public void delMenuLink(HttpServletRequest request, HttpServletResponse response, String ids){
		response.setContentType("application/text;charset=utf-8");
		try {
			if(ids != null){
				//清除菜单
				ServletContext application = request.getSession().getServletContext();
				application.setAttribute("menus", null);
				
				menuApi.deleteMenuLinkById(ids);
				String[] menuLinkIds=ids.split(",");
				Map map=(Map)request.getSession().getAttribute("user");
				for (String menuLinkId : menuLinkIds) {
					//数据操作成功，保存操作日志
					if(menuLinkId!=null){
						logApi.saveLog(Integer.valueOf(menuLinkId),null,LogUtil.LOG_SECONDMENU_MODULEID, LogUtil.LOG_DELETE_TYPE, map);
					}
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
}
