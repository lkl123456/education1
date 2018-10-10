package com.adks.dubbo.dao.web.author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.adks.commons.util.DateUtils;
import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.author.Adks_author;
import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.api.data.course.Adks_course_sort;
import com.adks.dubbo.commons.Page;

@Repository
public class AuthorWebDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_author";
	}
	
	public List<Adks_author> getTopHotAuthorList(Map map) {
		String sql = "select a1.authorId,a1.authorName,a1.authorDes,a1.authorSex,a1.authorPhoto,a1.authorFirstLetter,"
				+ "a1.orgId,a1.orgName,a1.orgCode,a1.creatorId,a1.creatorName,a1.createTime from Adks_author a1,"
				+ "(select authorId,count(authorId) as cou from adks_course group by authorId )a2 "
				+ "where a1.authorId=a2.authorId ";
		if(map.get("orgId")!=null && !"".equals(map.get("orgId"))){
			sql+=" and a1.orgId="+map.get("orgId");
		}
		if(map.get("num")!=null && !"".equals(map.get("num"))){
			sql+=" order by a2.cou desc limit "+map.get("num");
		}
		List<Adks_author> reslist=getAuthorListBySql(sql);
		if(reslist==null || reslist.size()<=0)
			return null;
		return reslist;
	}
	
	public Adks_author getAuthorById(Integer authorId){
		String sql = "select authorId,authorName,authorDes,authorSex,authorPhoto,authorFirstLetter,orgId,orgName,orgCode,"
				+ "creatorId,creatorName,createTime from Adks_author where authorId='"+authorId+"'";
		List<Adks_author> reslist=getAuthorListBySql(sql);
		if(reslist==null || reslist.size()<=0)
			return null;
		return reslist.get(0);
	}
	
	public List<Adks_author> getAuthorListBySql(String sql){
		List<Adks_author> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_author>() {
			@Override
			public Adks_author mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_author author=new Adks_author();
				author.setAuthorId(rs.getInt("authorId"));
				author.setAuthorName(rs.getString("authorName"));
				author.setAuthorDes(rs.getString("authorDes"));
				author.setAuthorSex(rs.getInt("authorSex"));
				author.setAuthorPhoto(rs.getString("authorPhoto"));
				author.setAuthorFirstLetter(rs.getString("authorFirstLetter"));
				author.setOrgCode(rs.getString("orgCode"));
				author.setOrgId(rs.getInt("orgId"));
				author.setOrgName(rs.getString("orgName"));
				author.setCreatorId(rs.getInt("creatorId"));
				author.setCreatorName(rs.getString("creatorName"));
				author.setCreateTime(rs.getDate("createTime"));
				return author;
			}});
		return reslist;
	}
}
