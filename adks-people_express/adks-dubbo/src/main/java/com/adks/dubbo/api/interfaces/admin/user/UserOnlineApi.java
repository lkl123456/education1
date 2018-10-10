package com.adks.dubbo.api.interfaces.admin.user;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.commons.Page;

/**
 * 
 * ClassName UserOnlineApi
 * @Description：用户在线Api
 * @author xrl
 * @Date 2017年6月22日
 */
public interface UserOnlineApi {

	/**
	 * 
	 * @Title getUserOnlineStatisticsListPage
	 * @Description：获取在线用户分页列表
	 * @author xrl
	 * @Date 2017年6月22日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String,Object>>> getUserOnlineStatisticsListPage(Page<List<Map<String,Object>>> page);
	
}
