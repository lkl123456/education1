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
import com.adks.dubbo.api.interfaces.admin.log.LogApi;
import com.adks.dubbo.api.interfaces.admin.menu.MenuApi;
import com.adks.dubbo.commons.Page;

@Controller
@RequestMapping(value="/menu")
public class MenuController {
	private final Logger logger = LoggerFactory.getLogger(MenuController.class);
	
	@Autowired
	private MenuApi menuApi;
	@Autowired
	private LogApi logApi;
	
	@RequestMapping({"/menuList"})
	public String home(HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
    	logger.debug("进入 MenuController  menuList...");
    	return "menu/menuList";
    }
	//菜单界面
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/getMenuListJson", produces = {"application/json; charset=UTF-8"})
	@ResponseBody
	public Page getMenuListJson(Integer page, Integer rows, String s_en_name){
		Page<List<Map<String, Object>>> page_bean=null;
		try {
			page_bean = new Page<List<Map<String, Object>>>();
			page_bean.setCurrentPage(page);
			page_bean.setPageSize(rows);
			if(s_en_name != null){
				s_en_name= java.net.URLDecoder.decode(s_en_name,"UTF-8");
				Map map = new HashMap<>();
				map.put("menuName", s_en_name);
				page_bean.setMap(map);
			}
			page_bean = menuApi.getMenuListPage(page_bean);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return page_bean;
	}
	
    /**
     * 保存菜单
     * @return
	 * @throws IOException 
     */
	@RequestMapping(value="/saveMenu", produces = {"text/html;charset=utf-8"})
    @ResponseBody
    public void saveMenu(HttpServletRequest request, HttpServletResponse response,Adks_menu menu) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String message = "error";
		try {
			if(menu != null){
				boolean flag = true;
				// 网校唯一标识
				if(menu.getMenuName() != null){
					Map<String, Object> temp = menuApi.getMenuByName(menu.getMenuName());
					if(temp != null && temp.size() > 0 && 
							((menu.getMenuName() != null && menu.getMenuId() != MapUtils.getIntValue(temp, "menuId")) 
							|| (menu.getMenuId() == null))){
						message = "snnameExists";
						flag = false;
					}
				}
				if(flag){
					//清除菜单
					ServletContext application = request.getSession().getServletContext();
					application.setAttribute("menus", null);
					if(menu.getMenuId() != null){
						
					}else{			
						Map userMap=(Map)request.getSession().getAttribute("user");
						Integer userId = Integer.parseInt(userMap.get("userId")+"");
						String username = userMap.get("username")+"";
						menu.setCreatorId(userId);
						menu.setCreatorName(username);
						menu.setCreateTime(new Date());
					}
					Integer dataId=menuApi.saveMenu(menu);
					//数据操作成功，保存操作日志
					if(dataId!=null&&dataId!=0){
						Map map=(Map)request.getSession().getAttribute("user");
						//添加/修改 
						if(menu.getMenuId()==null){  //添加
							logApi.saveLog(dataId, menu.getMenuName(),LogUtil.LOG_FIRSTMENU_MODULEID,LogUtil.LOG_ADD_TYPE,map);
						}else{  //修改
							logApi.saveLog(menu.getMenuId(), menu.getMenuName(),LogUtil.LOG_FIRSTMENU_MODULEID,LogUtil.LOG_UPDATE_TYPE,map);
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
	 * 删除菜单
	 * @param request
	 * @param response
	 * @param ids
	 */
	@RequestMapping(value = "/delMenu", method=RequestMethod.POST)
	public void delMenu(HttpServletRequest request, HttpServletResponse response, String ids){
		response.setContentType("application/text;charset=utf-8");
		try {
			if(ids != null){
				//清除菜单
				ServletContext application = request.getSession().getServletContext();
				application.setAttribute("menus", null);
				
				menuApi.deleteMenuById(ids);
				String[] menuIds=ids.split(",");
				Map map=(Map)request.getSession().getAttribute("user");
				for (String menuId : menuIds) {
					//数据操作成功，保存操作日志
					if(menuId!=null){
						logApi.saveLog(Integer.valueOf(menuId),null,LogUtil.LOG_FIRSTMENU_MODULEID, LogUtil.LOG_DELETE_TYPE, map);
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
