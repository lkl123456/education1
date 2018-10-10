package com.adks.dubbo.dao.web.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.adks.commons.util.DateUtils;
import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.data.user.Adks_user;
import com.adks.dubbo.api.data.user.Adks_user_online;
import com.adks.dubbo.commons.Page;

@Component
public class UserOnLineWebDao extends BaseDao {

    @Override
    protected String getTableName() {
        return "adks_user_online";
    }

    public Adks_user getUserInfo(String username) {
    	String sql = " select userId,userName,userRealName,orgId,orgName,orgCode  from adks_user  where userName= "+username;
		List<Adks_user> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_user>() {
			@Override
			public Adks_user mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_user user=new Adks_user();
				user.setUserId(rs.getInt("userId"));
				user.setUserName(rs.getString("userName"));
				user.setUserRealName(rs.getString("userRealName"));
				user.setOrgId(rs.getInt("orgId"));
				user.setOrgCode(rs.getString("orgCode"));
				user.setOrgName(rs.getString("orgName"));
				return user;
			}});
		if(reslist==null || reslist.size()<=0){
			return null;
		}
		return reslist.get(0);
	}
    
    public Adks_user_online getUserOnLineByName(String username) {
    	String sql = " select userOnlineId,userId,userName,sessionId,lastCheckDate,orgId,orgName,orgCode  from adks_user_online  where userName= '"+username+"'";
		List<Adks_user_online> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_user_online>() {
			@Override
			public Adks_user_online mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_user_online userOnLine=new Adks_user_online();
				userOnLine.setUserOnlineId(rs.getInt("userOnlineId"));
				userOnLine.setUserId(rs.getInt("userId"));
				userOnLine.setUserName(rs.getString("userName"));
				userOnLine.setOrgId(rs.getInt("orgId"));
				userOnLine.setOrgCode(rs.getString("orgCode"));
				userOnLine.setOrgName(rs.getString("orgName"));
				userOnLine.setSessionId(rs.getString("sessionId"));
				userOnLine.setLastCheckDate(rs.getDate("lastCheckDate"));
				return userOnLine;
			}});
		if(reslist==null || reslist.size()<=0){
			return null;
		}
		return reslist.get(0);
	}

	public void deleteUserOnLine(Integer userId) {
		String sql = "delete from adks_user_online where userId=" + userId;
		mysqlClient.update(sql, new Object[] {});
	}
	
	/**
	 * 
	 * @Title deleteUserOnLineBeforeNow
	 * @Description：清除当前时间之前的用户登录信息
	 * @author xrl
	 * @Date 2017年6月27日
	 * @param date
	 */
	public void deleteUserOnLineBeforeNow(Map<String, Object> map){
		Object[] obj = new Object[]{map.get("nowDate"),map.get("errorDate")};
		String sql = "delete from adks_user_online where lastCheckDate<? or lastCheckDate=?";
		mysqlClient.update(sql, obj);
	}
}
