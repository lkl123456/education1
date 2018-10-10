package com.adks.dubbo.dao.admin.news;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.commons.Page;

@Component
public class FriendlylinkDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_friendly_link";
	}
	
	/**
	 * 删除、批量删除
	 * @param fdLinkIds
	 */
	public void deleteFriendlylinkByIds(String fdLinkIds) {
		String sql = "delete from adks_friendly_link where fdLinkId in ( "+fdLinkIds+" )";
		mysqlClient.update(sql, new Object[]{});
	}
	
	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getFriendlylinkListPage(Page<List<Map<String, Object>>> page) {
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); //limit 起始位置
		StringBuffer sqlbuffer = new StringBuffer("select * from adks_friendly_link where 1=1 ");
		StringBuffer countsql = new StringBuffer("select count(1) from adks_friendly_link where 1=1 ");
		Map map = page.getMap();
		if(map != null && map.size() > 0){
			//添加查询条件 。。
			if(map.get("orgId") != null){
				sqlbuffer.append(" and orgId like '%"+map.get("orgId")+"%'");
				countsql.append(" and orgId like '%"+map.get("orgId")+"%'");
			}
			if(map.get("fdLinkName") != null){
				sqlbuffer.append(" and fdLinkName like '%"+map.get("fdLinkName")+"%'");
				countsql.append(" and fdLinkName like '%"+map.get("fdLinkName")+"%'");
			}
			if(map.get("orgCode") != null){
				sqlbuffer.append(" and orgCode like '%"+map.get("orgCode")+"%'");
				countsql.append(" and orgCode like '%"+map.get("orgCode")+"%'");
			}
		}
		
		//分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();
		List<Map<String, Object>> friendlylinkList = mysqlClient.queryForList(sql, new Object[0]);

		//查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(friendlylinkList);
		return page;
	}
	
	public Map<String, Object> getFriendlylinkInfoById(Integer fdLinkId) {
		String sql = " select * from adks_friendly_link where 1=1  and fdLinkId="+fdLinkId;
		return mysqlClient.queryForMap(sql, new Object[]{});
	}
}
