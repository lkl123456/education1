package com.adks.admin.controller.role;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.adks.dubbo.api.data.role.Adks_role;
import com.adks.dubbo.api.interfaces.admin.log.LogApi;
import com.adks.dubbo.api.interfaces.admin.role.RoleApi;
import com.adks.dubbo.api.interfaces.admin.role.UserroleApi;
import com.adks.dubbo.commons.Page;

@Controller
@RequestMapping(value="/role")
public class RoleController {
	private final Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	private RoleApi roleApi;
	@Autowired
	private UserroleApi userroleApi;
	@Autowired
	private LogApi logApi;
	
	@RequestMapping({"/roleList"})
	public String home(HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
    	logger.debug("进入 RoleController  roleList...");
    	return "roleRank/roleRankList";
    }
	//角色页面的表格
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/getRoleListJson", produces = {"application/json; charset=UTF-8"})
	@ResponseBody
	public Page getRoleListJson(HttpServletRequest request,Integer page,Integer userId, Integer rows, String s_role_name){
		Page<List<Map<String, Object>>> page_bean=null;
		try {
			page_bean = new Page<List<Map<String, Object>>>();
			page_bean.setCurrentPage(page);
			page_bean.setPageSize(rows);
			Map map = new HashMap<>();
			if(s_role_name != null){
				s_role_name= java.net.URLDecoder.decode(s_role_name,"UTF-8");
				map.put("roleName", s_role_name);
			}
			Map userMap = (Map) request.getSession().getAttribute("user");
			boolean isSuperManager = "1".equals(userMap.get("isSuper") + "") ? true : false;
			if(isSuperManager){
				//map.put("isGlob", 1);
        	}else{
        		map.put("isGlob", 2);
        	}
			page_bean.setMap(map);
			page_bean = roleApi.getRoleListPage(page_bean);
			if(page_bean.getRows()!=null && page_bean.getRows().size()>0){
				List<Map<String, Object>> userRoles = userroleApi.getRoleListByUser(userId);
	            if (userRoles != null && userRoles.size() > 0) {
	            	for(Map<String, Object> roles:page_bean.getRows()){
	            		for (Map<String, Object> userrole : userRoles) {
	            			if(roles.get("roleId").equals(userrole.get("roleId"))){
	            				roles.put("isCheckedOk", "1");
	            			}
		                }
	            	}
	            }
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return page_bean;
	}
	
    /**
     * 保存角色
     * @return
	 * @throws IOException 
     */
	@RequestMapping(value="/saveRole", produces = {"text/html;charset=utf-8"})
    @ResponseBody
    public void saveRoleJson(HttpServletRequest request, HttpServletResponse response,Adks_role role) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String message = "error";
		try {
			if(role != null){
				boolean flag = true;
				// 网校唯一标识
				if(role.getRoleName() != null){
					Map<String, Object> temp = roleApi.getRoleByName(role.getRoleName());
					if(temp != null && temp.size() > 0 && 
							((role.getRoleId() != null && role.getRoleId() != MapUtils.getIntValue(temp, "roleId")) 
							|| (role.getRoleId() == null))){
						message = "snnameExists";
						flag = false;
					}
				}
				if(flag){
					if(role.getIsGlob()==null){
						role.setIsGlob(2);
					}
					if(role.getRoleId() != null){
						
					}else{	
						Map userMap=(Map)request.getSession().getAttribute("user");
						Integer userId = Integer.parseInt(userMap.get("userId")+"");
						String username = userMap.get("username")+"";
						role.setCreatorId(userId);
						role.setCreatorName(username);
	    				role.setCreateTime(new Date());
					}
					Integer dataId=roleApi.saveRole(role);
					//数据操作成功，保存操作日志
					if(dataId!=null&&dataId!=0){
						Map map=(Map)request.getSession().getAttribute("user");
						//添加/修改 
						if(role.getRoleId()==null){  //添加
							logApi.saveLog(dataId, role.getRoleName(),LogUtil.LOG_ROLE_MODULEID,LogUtil.LOG_ADD_TYPE,map);
						}else{  //修改
							logApi.saveLog(role.getRoleId(), role.getRoleName(),LogUtil.LOG_ROLE_MODULEID,LogUtil.LOG_UPDATE_TYPE,map);
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
	 * 删除机构信息
	 * @param request
	 * @param response
	 * @param ids
	 */
	@RequestMapping(value = "/delRole", method=RequestMethod.POST)
	public void delRole(HttpServletRequest request, HttpServletResponse response, String ids){
		response.setContentType("application/text;charset=utf-8");
		try {
			PrintWriter pWriter = response.getWriter();
			if(ids != null){
				boolean flag=true;
				String[] id=ids.split(",");
				for(String roleId:id){
					Integer oId=Integer.parseInt(roleId);
					flag=userroleApi.getUserRoleListByRole(oId);
					if(!flag){
						break;
					}
				}
				if(flag){
					roleApi.deleteRoleByIds(ids);
					Map map=(Map)request.getSession().getAttribute("user");
					for (String roleId : id) {
						//数据操作成功，保存操作日志
						if(roleId!=null){
							logApi.saveLog(Integer.valueOf(roleId),null,LogUtil.LOG_ROLE_MODULEID, LogUtil.LOG_DELETE_TYPE, map);
						}
					}
					pWriter.write("succ");
				}else{
					pWriter.write("hasUser");
				} 
			}
			pWriter.flush();
			pWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
