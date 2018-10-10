package com.adks.dubbo.dao.admin.user;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.adks.commons.util.DateUtils;
import com.adks.dbclient.dao.singaltanent.BaseDao;
/**
 * 
 * ClassName UserOnlineDao
 * @Description
 * @author xrl
 * @Date 2017年6月22日
 */
import com.adks.dubbo.commons.Page;
@Component
public class UserOnlineDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_user_online";
	}
	
	/**
	 * 
	 * @Title getUserOnlineStatisticsListPage
	 * @Description：获取在线用户分页列表
	 * @author xrl
	 * @Date 2017年6月22日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getUserOnlineStatisticsListPage(Page<List<Map<String, Object>>> page){
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); //limit 起始位置
		StringBuffer sqlbuffer = new StringBuffer(" select uo.userId,uo.lastCheckDate,u.userName,u.userRealName,u.orgName "
				+ "from adks_user_online uo left join adks_user u on uo.userId=u.userId where 1=1 ");
		StringBuffer countsql = new StringBuffer("select count(uo.userId) from adks_user_online uo left join adks_user u on uo.userId=u.userId where 1=1  ");
		
		Map map = page.getMap();
		if(map != null && map.size() > 0){
			//添加查询条件 
			if(map.get("orgCode") != null && !"".equals(map.get("orgCode"))){
				sqlbuffer.append(" and u.orgCode like '%" + map.get("orgCode") + "%'");
				countsql.append(" and u.orgCode like '%" + map.get("orgCode") + "%'");
			}
			if(map.get("username") != null && !"".equals(map.get("username"))){
				sqlbuffer.append(" and (u.username like '%" + map.get("username") + "%' or u.userRealName like '%"+map.get("username")+"%')");
				countsql.append(" and (u.username like '%" + map.get("username") + "%' or u.userRealName like '%"+map.get("username")+"%')");
			}
		}
		//分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();
		List<Map<String, Object>> userlist = mysqlClient.queryForList(sql, new Object[0]);
		//查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(userlist);
		return page;
	}

}
