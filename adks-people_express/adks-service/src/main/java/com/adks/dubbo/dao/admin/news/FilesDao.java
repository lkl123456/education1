package com.adks.dubbo.dao.admin.news;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.commons.Page;
@Component
public class FilesDao extends BaseDao{

	@Override
	protected String getTableName() {
		return "adks_files";
	}
	
	/**
	 * 删除、批量删除
	 * @param newsIds
	 */
	public void deleteFilesByIds(String filesIds) {
		String sql = "delete from adks_files where filesId in ( "+filesIds+" )";
		mysqlClient.update(sql, new Object[]{});
	}
	
	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getFilesListPage(Page<List<Map<String, Object>>> page) {
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); //limit 起始位置
		StringBuffer sqlbuffer = new StringBuffer("select * from adks_files where 1=1 ");
		StringBuffer countsql = new StringBuffer("select count(1) from adks_files where 1=1 ");
		Map map = page.getMap();
		if(map != null && map.size() > 0){
			//添加查询条件 。。
			if(map.get("gradeId") != null){
				sqlbuffer.append(" and gradeId="+map.get("gradeId"));
				countsql.append(" and gradeId="+map.get("gradeId"));
			}
			if(map.get("orgId") != null){
				sqlbuffer.append(" and orgId="+map.get("orgId"));
				countsql.append(" and orgId="+map.get("orgId"));
			}
			if(map.get("filesName") != null){
				sqlbuffer.append(" and filesName like '%"+map.get("filesName")+"%'");
				countsql.append(" and filesName like '%"+map.get("filesName")+"%'");
			}
			if(map.get("orgCode") != null){
				sqlbuffer.append(" and orgCode like '"+map.get("orgCode")+"%'");
				countsql.append(" and orgCode like '"+map.get("orgCode")+"%'");
			}
		}
		
		//分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();
		List<Map<String, Object>> filesList = mysqlClient.queryForList(sql, new Object[0]);

		//查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(filesList);
		return page;
	}
	
	public Map<String, Object> getFilesInfoById(Integer filesId) {
		String sql = " select * from adks_files where 1=1  and filesId="+filesId;
		return mysqlClient.queryForMap(sql, new Object[]{});
	}
	
	/**
	 * 
	 * @Title checkGradeFilesName
	 * @Description:检查班级资料名在该班级中的唯一性
	 * @author xrl
	 * @Date 2017年5月9日
	 * @param map
	 * @return
	 */
	public Map<String, Object> checkGradeFilesName(Map<String, Object> map){
		Object[] obj = new Object[]{};
		String sql = "select * from adks_files where filesName='"+map.get("filesName")+"'";
		if(map.get("orgId")!=null){
			sql+=" and orgId="+map.get("orgId");
		}
		if(map.get("gradeId")!=null){
			sql+=" and gradeId="+map.get("gradeId");
		}
		if(map.get("filesId")!=null){
			sql+=" and filesId!="+map.get("filesId");
		}
		return mysqlClient.queryForMap(sql, obj);
	}
}
