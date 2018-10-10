package com.adks.dubbo.dao.admin.log;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.commons.Page;

/**
 * 
 * ClassName LogDao
 * @Description：系统操作日志DAO
 * @author xrl
 * @Date 2017年6月22日
 */
@Component
public class LogDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_log";
	}

	public Page<List<Map<String, Object>>> getLogStatisticsListJson(Page<List<Map<String, Object>>> page){
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); //limit 起始位置
		StringBuffer sqlbuffer = new StringBuffer(" select al.logId,al.operateType,al.moduleId,al.createTime,al.dataId,al.dataName,al.creatorName,ev.valDisplay "
				+ "from adks_log al,adks_enumeration_value ev where  al.moduleId=ev.enValueId ");
		StringBuffer countsql = new StringBuffer("select count(al.logId) from adks_log al,adks_enumeration_value ev where  al.moduleId=ev.enValueId   ");
		
		Map map = page.getMap();
		if(map != null && map.size() > 0){
			//添加查询条件 
			if(map.get("orgCode") != null && !"".equals(map.get("orgCode"))){
				sqlbuffer.append(" and al.orgCode like '%" + map.get("orgCode") + "%'");
				countsql.append(" and al.orgCode like '%" + map.get("orgCode") + "%'");
			}
			if(map.get("operateType") != null && !"".equals(map.get("operateType"))){
				sqlbuffer.append(" and al.operateType = " + map.get("operateType") );
				countsql.append(" and al.operateType = " + map.get("operateType") );
			}
			if(map.get("moduleId") != null && !"".equals(map.get("moduleId"))){
				sqlbuffer.append(" and al.moduleId = " + map.get("moduleId") );
				countsql.append(" and al.moduleId = " + map.get("moduleId") );
			}
			if(map.get("minTime") != null && !"".equals(map.get("minTime"))){
				sqlbuffer.append(" and al.createTime >= '" + map.get("minTime")+"'" );
				countsql.append(" and al.createTime >= '" + map.get("minTime")+"'" );
			}
			if(map.get("maxTime") != null && !"".equals(map.get("maxTime"))){
				sqlbuffer.append(" and al.createTime <= '" + map.get("maxTime")+"'" );
				countsql.append(" and al.createTime <= '" + map.get("maxTime")+"'" );
			}
		}
		sqlbuffer.append(" order by al.logId desc ");
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
