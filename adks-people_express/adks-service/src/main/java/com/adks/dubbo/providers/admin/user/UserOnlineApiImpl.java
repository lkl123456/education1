package com.adks.dubbo.providers.admin.user;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.interfaces.admin.user.UserOnlineApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.admin.user.UserOnlineService;
/**
 * 
 * ClassName UserOnlineApiImpl
 * @Description：用户在线Api实现
 * @author xrl
 * @Date 2017年6月22日
 */
public class UserOnlineApiImpl implements UserOnlineApi {

	@Autowired
	private UserOnlineService userOnlineService;
	
	/**
	 * 
	 * @Title getUserOnlineStatisticsListPage
	 * @Description：获取在线用户分页列表
	 * @author xrl
	 * @Date 2017年6月22日
	 * @param page
	 * @return
	 */
	@Override
	public Page<List<Map<String, Object>>> getUserOnlineStatisticsListPage(Page<List<Map<String, Object>>> page) {
		return userOnlineService.getUserOnlineStatisticsListPage(page);
	}

}
