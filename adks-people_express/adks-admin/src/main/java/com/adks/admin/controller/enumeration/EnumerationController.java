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
import com.adks.dubbo.api.interfaces.admin.enumeration.EnumerationApi;
import com.adks.dubbo.api.interfaces.admin.log.LogApi;
import com.adks.dubbo.commons.Page;

@Controller
@RequestMapping(value="/enumeration")
public class EnumerationController {
	private final Logger logger = LoggerFactory.getLogger(EnumerationController.class);
	
	@Autowired
	private EnumerationApi enumerationApi;
	@Autowired
	private LogApi logApi;
	
	private Integer parent;
	
	@RequestMapping({"/enumerationList"})
	public String home(HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
    	logger.debug("进入 EnumerationController  orgList...");
    	return "enumeration/enumerationList";
    }
	//机构界面在使用
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/getEnumerationListJson", produces = {"application/json; charset=UTF-8"})
	@ResponseBody
	public Page getEnumerationListJson(Integer page, Integer rows, String s_en_name){
		Page<List<Map<String, Object>>> page_bean=null;
		try {
			page_bean = new Page<List<Map<String, Object>>>();
			page_bean.setCurrentPage(page);
			page_bean.setPageSize(rows);
			if(s_en_name != null){
				s_en_name= java.net.URLDecoder.decode(s_en_name,"UTF-8");
				Map map = new HashMap<>();
				map.put("enName", s_en_name);
				page_bean.setMap(map);
			}
			page_bean = enumerationApi.getEnumerationListPage(page_bean);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return page_bean;
	}
	
    /**
     * 保存枚举
     * @return
	 * @throws IOException 
     */
	@RequestMapping(value="/saveEnumeration", produces = {"text/html;charset=utf-8"})
    @ResponseBody
    public void saveEnumerationJson(HttpServletRequest request, HttpServletResponse response,Adks_enumeration enumeration) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String message = "error";
		try {
			if(enumeration != null){
				boolean flag = true;
				// 网校唯一标识
				if(enumeration.getEnName() != null){
					Map<String, Object> temp = enumerationApi.getEnumerationByName(enumeration.getEnName());
					if(temp != null && temp.size() > 0 && 
							((enumeration.getEnName() != null && enumeration.getEnId() != MapUtils.getIntValue(temp, "enId")) 
							|| (enumeration.getEnId() == null))){
						message = "snnameExists";
						flag = false;
					}
				}
				if(flag){
					
					if(enumeration.getEnId() != null){
						
					}else{
						Map userMap=(Map)request.getSession().getAttribute("user");
						Integer userId = Integer.parseInt(userMap.get("userId")+"");
						String username = userMap.get("username")+"";
						enumeration.setCreatorId(userId);
						enumeration.setCreatorName(username);
						enumeration.setCreateTime(new Date());
						enumeration.setEnumerationType(0);
					}
					Integer dataId=enumerationApi.saveEnumerationService(enumeration);
					//数据操作成功，保存操作日志
					if(dataId!=null&&dataId!=0){
						Map map=(Map)request.getSession().getAttribute("user");
						//添加/修改 
						if(enumeration.getEnId()==null){  //添加
							logApi.saveLog(dataId, enumeration.getEnName(),LogUtil.LOG_ENUMERATION_MODULEID,LogUtil.LOG_ADD_TYPE,map);
						}else{  //修改
							logApi.saveLog(enumeration.getEnId(), enumeration.getEnName(),LogUtil.LOG_ENUMERATION_MODULEID,LogUtil.LOG_UPDATE_TYPE,map);
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
	 * 删除枚举
	 * @param request
	 * @param response
	 * @param ids
	 */
	@RequestMapping(value = "/delEnumeration", method=RequestMethod.POST)
	public void delOrg(HttpServletRequest request, HttpServletResponse response, String ids){
		response.setContentType("application/text;charset=utf-8");
		try {
			if(ids != null){
				enumerationApi.deleteEnumerationByIds(ids);
				String[] enIds=ids.split(",");
				Map map=(Map)request.getSession().getAttribute("user");
				for (String enId : enIds) {
					//数据操作成功，保存操作日志
					if(enId!=null){
						logApi.saveLog(Integer.valueOf(enId),null,LogUtil.LOG_ENUMERATION_MODULEID, LogUtil.LOG_DELETE_TYPE, map);
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
