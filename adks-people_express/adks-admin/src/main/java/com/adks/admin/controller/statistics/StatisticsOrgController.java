package com.adks.admin.controller.statistics;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adks.commons.util.FormatDate;
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.interfaces.admin.org.OrgApi;
import com.adks.dubbo.commons.Page;

@Controller
@RequestMapping(value = "/orgStatistics")
public class StatisticsOrgController {

	@Autowired
	private OrgApi orgApi;
	
	//跳转到机构统计的页面
	@RequestMapping(value = "/toOrgStatistics")
	public String manager(Model model) {
		return "/statistics/orgStatistics";
	}
	
	@RequestMapping(value = "/getOrgStatisticsListJson")
	@ResponseBody
	public Page<List<Map<String, Object>>> getOrgStatisticsListJson(String orgCode, String userName, Integer page, Integer rows,
			HttpServletRequest request, HttpServletResponse response) {
		Page<List<Map<String, Object>>> page_bean = null;
		try {
			Map userMap = (Map) request.getSession().getAttribute("user");
			page_bean = new Page<List<Map<String, Object>>>();
			page_bean.setCurrentPage(page);
			page_bean.setPageSize(rows);
			Map map = new HashMap<>();
			boolean isSuperManager = "1".equals(userMap.get("isSuper") + "") ? true : false;
			if(orgCode!=null&&orgCode!=""){
				map.put("orgCode",orgCode);
			}else if (isSuperManager) {
				map.put("orgCode", "0A");
			} else {
				String userOrgCode = userMap.get("orgCode").toString();
				map.put("orgCode", userOrgCode);
			}
			if(userName!=null && !"".equals(userName)){
				userName = java.net.URLDecoder.decode(userName, "UTF-8");
				map.put("userName", userName);
			}
			page_bean.setMap(map);
			page_bean = orgApi.getOrgStatisticsListJson(page_bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page_bean;
	}
	
}
