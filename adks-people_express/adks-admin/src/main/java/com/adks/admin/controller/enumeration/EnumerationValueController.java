package com.adks.admin.controller.enumeration;

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
import com.adks.dubbo.api.data.enumeration.Adks_enumeration;
import com.adks.dubbo.api.data.enumeration.Adks_enumeration_value;
import com.adks.dubbo.api.interfaces.admin.enumeration.EnumerationApi;
import com.adks.dubbo.api.interfaces.admin.log.LogApi;
import com.adks.dubbo.commons.Page;

@Controller
@RequestMapping(value="/enumerationValue")
public class EnumerationValueController {
	private final Logger logger = LoggerFactory.getLogger(EnumerationValueController.class);
	
	@Autowired
	private EnumerationApi enumerationApi;
	@Autowired
	private LogApi logApi;
	
	@RequestMapping({"/enumerationValueList"})
	public String home(HttpServletRequest request, HttpServletResponse response,Integer enId,String enName,Model model) throws IOException {
    	logger.debug("进入 EnumerationValueController  orgList...");
    	Adks_enumeration adks_en=enumerationApi.getEnumerationById(enId);
    	model.addAttribute("enumer", adks_en);
    	return "enumeration/enumerationValueList";
    }
	//机构界面在使用
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/getEnumerationValueListJson", produces = {"application/json; charset=UTF-8"})
	@ResponseBody
	public Page getEnumerationValueListJson(Integer page, Integer rows,Integer enId,String s_enVal_name){
		Page<List<Map<String, Object>>> page_bean=null;
		try {
			page_bean = new Page<List<Map<String, Object>>>();
			page_bean.setCurrentPage(page);
			page_bean.setPageSize(rows);
			if((s_enVal_name != null && !"".equals(s_enVal_name)) || enId !=null){
				Map map = new HashMap<>();
				if(s_enVal_name != null && !"".equals(s_enVal_name)){
					s_enVal_name= java.net.URLDecoder.decode(s_enVal_name,"UTF-8");
					map.put("valName", s_enVal_name);
				}
				if(enId != null){
					map.put("enId", enId);
				}
				page_bean.setMap(map);
			}
			page_bean = enumerationApi.getEnumerationValueListPage(page_bean);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return page_bean;
	}
	
    /**
     * 保存枚举值
     * @return
	 * @throws IOException 
     */
	@RequestMapping(value="/saveEnumerationValue", produces = {"text/html;charset=utf-8"})
    @ResponseBody
    public void saveEnumerationValueJson(HttpServletRequest request, HttpServletResponse response,Adks_enumeration_value enumerationValue) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String message = "error";
		try {
			if(enumerationValue != null){
				boolean flag = true;
				// 网校唯一标识
				if(enumerationValue.getValName() != null){
					Map<String, Object> temp = enumerationApi.getEnumerationValueByName(enumerationValue.getValName());
					if(temp != null && temp.size() > 0 && 
							((enumerationValue.getValName() != null && enumerationValue.getEnValueId() != MapUtils.getIntValue(temp, "enValueId")) 
							|| (enumerationValue.getEnValueId() == null))){
						message = "snnameExists";
						flag = false;
					}
				}
				if(flag){
					
					if(enumerationValue.getEnValueId() != null){
						
					}else{	
						Map userMap=(Map)request.getSession().getAttribute("user");
						Integer userId = Integer.parseInt(userMap.get("userId")+"");
						String username = userMap.get("username")+"";
						enumerationValue.setCreatorId(userId);
						enumerationValue.setCreatorName(username);
						enumerationValue.setCreateTime(new Date());
					}
					Integer dataId=enumerationApi.saveEnumerationValueService(enumerationValue);
					//数据操作成功，保存操作日志
					if(dataId!=null&&dataId!=0){
						Map map=(Map)request.getSession().getAttribute("user");
						//添加/修改 
						if(enumerationValue.getEnValueId()==null){  //添加
							logApi.saveLog(dataId,enumerationValue.getValDisplay(),LogUtil.LOG_ENUMERATIONVALUE_MODULEID,LogUtil.LOG_ADD_TYPE,map);
						}else{  //修改
							logApi.saveLog(enumerationValue.getEnValueId(),enumerationValue.getValDisplay(),LogUtil.LOG_ENUMERATIONVALUE_MODULEID,LogUtil.LOG_UPDATE_TYPE,map);
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
	 * 删除枚举值
	 * @param request
	 * @param response
	 * @param ids
	 */
	@RequestMapping(value = "/delEnumerationValue", method=RequestMethod.POST)
	public void delOrg(HttpServletRequest request, HttpServletResponse response, String ids){
		response.setContentType("application/text;charset=utf-8");
		try {
			if(ids != null){
				enumerationApi.deleteEnumerationValueByIds(ids);
				String[] envIds=ids.split(",");
				Map map=(Map)request.getSession().getAttribute("user");
				for (String envId : envIds) {
					//数据操作成功，保存操作日志
					if(envId!=null){
						logApi.saveLog(Integer.valueOf(envId),null,LogUtil.LOG_ENUMERATIONVALUE_MODULEID, LogUtil.LOG_DELETE_TYPE, map);
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
