package com.adks.admin.controller.role;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adks.dubbo.api.data.role.Adks_role;
import com.adks.dubbo.api.data.role.Adks_role_menu_link;
import com.adks.dubbo.api.interfaces.admin.menu.MenuApi;
import com.adks.dubbo.api.interfaces.admin.role.RoleApi;
import com.adks.dubbo.commons.Page;

@Controller
@RequestMapping(value="/roleMenuLink")
public class RoleMenuLinkController {
	private final Logger logger = LoggerFactory.getLogger(RoleMenuLinkController.class);
	
	@Autowired
	private RoleApi roleApi;
	@Autowired
	private MenuApi menuApi;
	
	@RequestMapping({"/roleMenuLinkList"})
	public String home(HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
    	logger.debug("进入 RoleController  roleList...");
    	return "roleRank/roleMenuLinkList";
    }
	
	 /**
     * 得到所有的角色
     * @param parent :父部门id
     * @return
     */
    @RequestMapping(value="/getRolesJson", produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public List<Adks_role> getRolesJson() {
    	List<Adks_role> maps = roleApi.getRolesListAll();
    	return maps;
    }
    
    /**
     * 所有的链接菜单
     * @param page :分页参数
     * @param rows :分页参数
     * @param loginname : 搜索登录名
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/getMenuLinksJson", produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public Page getMenuLinksJson(String s_menuName,String s_menuLinkName,Integer page,Integer rows) {
    	
    	Page<List<Map<String, Object>>> paramPage = new Page<List<Map<String, Object>>>();
    	try {
			//模糊查询 的参数
			Map likemap = new HashMap();
			if(s_menuName != null && !"".equals(s_menuName)){
				s_menuName=java.net.URLDecoder.decode(s_menuName,"UTF-8");
				likemap.put("menuName", s_menuName);
			}
			if(s_menuLinkName != null && !"".equals(s_menuLinkName)){
				s_menuLinkName=java.net.URLDecoder.decode(s_menuLinkName,"UTF-8");
				likemap.put("menuLinkName", s_menuLinkName);
			}
			paramPage.setMap(likemap);
			paramPage.setPageSize(rows);
			paramPage.setCurrentPage(page);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	
    	return menuApi.getMenuLinkListPage(paramPage);
    }
    
    /*
     * 获取角色相关联的菜单链接
     * */
    @RequestMapping(value="/getMenuLinkIdByRole", produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String getMenuLinkIdByRole(HttpServletRequest request, HttpServletResponse response,Integer roleId) {
    	List<Adks_role_menu_link> maps = roleApi.getMenuLinkIdByRole(roleId);
		String ids="";
		if(maps!=null){
			for(Adks_role_menu_link map:maps){
				Integer menuLinkId=map.getMenuLinkId();
				ids+=menuLinkId+",";
			}
			if(!"".equals(ids)){
				ids=ids.substring(0,ids.length()-1);
			}
		}
		return ids;
    }
    
    /*
     * 保存角色跟菜单的关联关系
     * */
    @RequestMapping(value="/saveMenuLinkRole", produces = {"text/html;charset=utf-8"})
    @ResponseBody
    public String saveMenuLinkRole(HttpServletRequest request, HttpServletResponse response,Integer roleId,String menuLinkIds) {
    	//response.setContentType("application/json;charset=utf-8");
		String message = "error";
		//try {
			if(menuLinkIds!=null &&!"".equals(menuLinkIds)){
				String[] mlIds=menuLinkIds.split(",");
				List<Adks_role_menu_link> maps = roleApi.getMenuLinkIdByRole(roleId);
				if(maps!=null && maps.size()>0){
					//判断哪一些需要添加，修改的不需入数据库
					for(String id:mlIds){
						Integer menuLinkIdTemp=Integer.parseInt(id);
						boolean falg=true;
						for(Adks_role_menu_link map:maps){
							Integer menuLinkId=map.getMenuLinkId();
							if(menuLinkIdTemp == menuLinkId){//相等，原先已存在
								falg=false;
								break;
							}
						}
						if(falg){
							Adks_role_menu_link roleMenuLink=new Adks_role_menu_link();
							roleMenuLink.setRoleId(roleId);
							roleMenuLink.setMenuLinkId(Integer.parseInt(id));
							roleApi.saveRoleMenuLinK(roleMenuLink);
						}
					}
					
				}else{
					//全部添加
					for(String id:mlIds){
						//判断那些是删除，添加，修改
						Adks_role_menu_link roleMenuLink=new Adks_role_menu_link();
						roleMenuLink.setRoleId(roleId);
						roleMenuLink.setMenuLinkId(Integer.parseInt(id));
						roleApi.saveRoleMenuLinK(roleMenuLink);
					}
				}
				
				message="succ";
			}
			//PrintWriter pWriter = response.getWriter();
			//pWriter.write(message);
			//pWriter.flush();
			//pWriter.close();
		/*} catch (IOException e) {
			e.printStackTrace();
		}*/
		return message;
    }
    
    /*
     * 保存角色跟菜单的关联关系
     * */
    @RequestMapping(value="/delMenuLinkRole", produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String delMenuLinkRole(HttpServletRequest request, HttpServletResponse response,Integer roleId,String menuLinkIds) {
    	//response.setContentType("application/json;charset=utf-8");
		String message = "error";
		//try {
			if(menuLinkIds!=null &&!"".equals(menuLinkIds)){
				String[] mlIds=menuLinkIds.split(",");
				//全部添加
				for(String id:mlIds){
					//判断那些是删除，添加，修改
					roleApi.deleteRoleMenuLinKByCon(roleId,Integer.parseInt(id));
				}
				message="succ";
			}
			/*PrintWriter pWriter = response.getWriter();
			pWriter.write(message);
			pWriter.flush();
			pWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		return message;
    }
}
