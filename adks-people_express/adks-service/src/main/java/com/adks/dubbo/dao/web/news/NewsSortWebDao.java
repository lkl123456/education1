package com.adks.dubbo.dao.web.news;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.author.Adks_author;
import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.api.data.news.Adks_news;
import com.adks.dubbo.api.data.news.Adks_news_sort;
import com.adks.dubbo.commons.Page;

@Component
public class NewsSortWebDao extends BaseDao{

	@Override
	protected String getTableName() {
		return "adks_news_sort";
	}
	
	public List<Adks_news_sort> getNewsSortList(Map map){
		String sql = "select newsSortId as id,newsSortName as name,newsSortType,orgId,orgName,orgCode from adks_news_sort where 1=1 ";
		if(map.get("orgCode")!=null && !"".equals(map.get("orgCode"))){
			sql+=" and orgCode like '%"+map.get("orgCode")+"%'";
		}
		sql+=" order by createTime desc";
		List<Adks_news_sort> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_news_sort>() {
			@Override
			public Adks_news_sort mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_news_sort newsSort=new Adks_news_sort();
				newsSort.setNewsSortId(rs.getInt("id"));
				newsSort.setId(rs.getInt("id"));
				newsSort.setNewsSortName(rs.getString("name"));
				newsSort.setName(rs.getString("name"));
				newsSort.setNewsSortType(rs.getString("newsSortType"));
				newsSort.setOrgCode(rs.getString("orgCode"));
				newsSort.setOrgId(rs.getInt("orgId"));
				newsSort.setOrgName(rs.getString("orgName"));
				return newsSort;
			}});
		if(reslist==null || reslist.size()<=0)
			return null;
		return reslist;
	}
	
	public  Map<String, Object> getNewsSortById(Integer newsSortId){
		String sql = "select * from adks_news_sort where 1=1 and newsSortId="+newsSortId;
		return  mysqlClient.queryForMap(sql, new Object[]{});
	}
	public List<Adks_news_sort> getNewsSortListByCon(Map map){
		String sql = "select newsSortId,newsSortName ,newsSortLocation ,newsSortType,orgId,orgName,orgCode,creatorId,creatorName,createTime from adks_news_sort where 1=1 ";
//		if(map.get("orgId")!=null && !"".equals(map.get("orgId"))){
//			sql+=" and orgId="+map.get("orgId");
//		}
//		if(map.get("orgCode")!=null && !"".equals(map.get("orgCode"))){
//			sql+=" and orgCode="+map.get("orgCode");
//		}
//		if(map.get("newsSortType")!=null && !"".equals(map.get("newsSortType"))){
//			sql+=" and newsSortType="+map.get("newsSortType");
//		}
		sql+=" order by newsSortLocation";
		List<Adks_news_sort> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_news_sort>() {
			@Override
			public Adks_news_sort mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_news_sort newsSort=new Adks_news_sort();
				newsSort.setNewsSortId(rs.getInt("newsSortId"));
				newsSort.setNewsSortName(rs.getString("newsSortName"));
				newsSort.setNewsSortLocation(rs.getInt("newsSortLocation"));
				newsSort.setNewsSortType(rs.getString("newsSortType"));
				newsSort.setOrgCode(rs.getString("orgCode"));
				newsSort.setOrgId(rs.getInt("orgId"));
				newsSort.setOrgName(rs.getString("orgName"));
				newsSort.setCreatorId(rs.getInt("creatorId"));
				newsSort.setCreatorName(rs.getString("creatorName"));
				newsSort.setCreateTime(rs.getDate("createTime"));
				return newsSort;
			}});
		if(reslist==null || reslist.size()<=0)
			return null;
		return reslist;
	}
	
	public Adks_news_sort getNewsSortByNewsSortType(Map<String, Object> map){
		String sql = "select * from adks_news_sort where newsSortType='" +map.get("newsSortType")+"'";
        List<Adks_news_sort> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_news_sort>() {
            @Override
            public Adks_news_sort mapRow(ResultSet rs, int rowNum) throws SQLException {
            	Adks_news_sort sort = new Adks_news_sort();
            	sort.setNewsSortId(rs.getInt("newsSortId"));
            	sort.setNewsSortName(rs.getString("newsSortName"));
            	sort.setNewsSortType(rs.getString("newsSortType"));
                return sort;
            }
        });
        if (reslist == null || reslist.size() <= 0) {
            return null;
        }
        return reslist.get(0);
	}
}
