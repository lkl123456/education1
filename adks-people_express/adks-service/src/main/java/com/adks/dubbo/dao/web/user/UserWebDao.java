package com.adks.dubbo.dao.web.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.adks.commons.util.DateUtils;
import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.data.user.Adks_user;
import com.adks.dubbo.commons.Page;

@Component
public class UserWebDao extends BaseDao {

    @Override
    protected String getTableName() {
        return "adks_user";
    }
    
    public String getNationalityName(Integer id) {
    	 String sql = "select valName from adks_enumeration_value where enValueId="+id;
         Map<String, Object> map = mysqlClient.queryForMap(sql, new Object[0]);
         if(map != null && map.size()>0){
        	 return map.get("valName")+"";
         }
		return "";
	}

    public boolean isSuperManager(Integer userId) {
        String sql = "select urid from adks_userrole where userId=" + userId + " and roleId in (select roleId from adks_role where roleName='超级管理员')";
        Map<String, Object> map = mysqlClient.queryForMap(sql, new Object[0]);
        if (map != null && map.size() > 0) {
            return true;
        }
        return false;
    }

    public boolean checkUserName(String userName) {
        String sql = "select userId from adks_user where userName = '" + userName + "'";
        Map<String, Object> map = mysqlClient.queryForMap(sql, new Object[0]);
        if (map != null && map.size() > 0) {
            return false;
        }
        return true;
    }
    /*根据用户id得到密码*/
    public String getUserPassWordByUserId(Integer userId) {
    	String sql = "select userPassword from adks_user where userId =" + userId;
        Map<String, Object> map = mysqlClient.queryForMap(sql, new Object[0]);
        if (map != null && map.size() > 0) {
            return map.get("userPassword")+"";
        }
        return null;
	}

    /**
     * 自定义方法，获取部门dept json数据，拼接给zTree使用
     * @param parent
     * @return
     */
    public List<Map<String, Object>> queryDeptJson(Integer parent) {
        String sql = "select id,parent_id as pId,name from dept where 1=1";
        if (parent != null && !"".equals(parent)) {
            sql = sql + " and parent_id = " + parent;
        }
        List<Map<String, Object>> reslist = mysqlClient.queryForList(sql, new Object[0]);
        if (reslist == null || reslist.size() <= 0) {
            return null;
        }
        return reslist;
    }

    public Map<String, Object> getUserByName(String username) {
        Object[] obj = new Object[] { username };
        String sql = " select userId,userName,userPassword,userStatus,userRealNameorgId,orgName,orgCode  " + " from adks_user  where userName=? ";
        return mysqlClient.queryForMap(sql, obj);
    }

    public Map<String, Object> getUserByUserName(String username) {
        Object[] obj = new Object[] { username };
        String sql = " select * from adks_user  where userName=? ";
        return mysqlClient.queryForMap(sql, obj);
    }

    public Map<String, Object> getUserRoleByUserId(Integer userId) {
        Object[] obj = new Object[] { userId };
        String sql = " select *  " + " from adks_userrole  where roleId!=4 and userId=?  ";
        return mysqlClient.queryForMap(sql, obj);
    }


    public Map<String, Object> getUserInfoByUserId(Integer userId) {
        Object[] obj = new Object[] { userId };
        String sql = " select userId,userName,userRealName,orgId,orgName,orgCode  " + " from adks_user  where userId=? ";
        return mysqlClient.queryForMap(sql, obj);
    }
    //根据电话号码得到用户
    public Adks_user checkUserCellPhone(String userPhone) {
    	String sql = " select userId,userName,userRealName from adks_user  where userPhone='"+userPhone+"'";
		List<Adks_user> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_user>() {
			@Override
			public Adks_user mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_user user=new Adks_user();
				user.setUserId(rs.getInt("userId"));
				user.setUserName(rs.getString("userName"));
				user.setUserRealName(rs.getString("userRealName"));
				return user;
			}});
		if(reslist==null || reslist.size()<=0){
			return null;
		}
		return reslist.get(0);
	}
    
    public Adks_user getUserByCard(String cardNumber) {
    	String sql = " select userId,userName,userRealName,rankId,rankName  from adks_user  where cardNumer='"+cardNumber+"'";
		List<Adks_user> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_user>() {
			@Override
			public Adks_user mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_user user=new Adks_user();
				user.setUserId(rs.getInt("userId"));
				user.setUserName(rs.getString("userName"));
				user.setUserRealName(rs.getString("userRealName"));
				user.setRankId(rs.getInt("rankId"));
				user.setRankName(rs.getString("rankName"));
				return user;
			}});
		if(reslist==null || reslist.size()<=0){
			return null;
		}
		return reslist.get(0);
	}
    
    public Adks_user getUserInfoById(Integer userId) {
    	String sql = " select userId,userName,userRealName,orgId,orgName,orgCode,userStatus,overdate,headPhoto,userSex,userMail,userPhone"
    			+ ",userBirthday,userParty,cardNumer,userNationality,rankId,rankName,positionId,positionName  from adks_user  where userId="+userId;
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
				user.setUserStatus(rs.getInt("userStatus"));
				user.setOverdate(rs.getDate("overdate"));
				user.setHeadPhoto(rs.getString("headPhoto"));
				user.setUserSex(rs.getInt("userSex"));
				user.setUserMail(rs.getString("userMail"));
				user.setUserPhone(rs.getString("userPhone"));
				user.setUserBirthday(rs.getDate("userBirthday"));
				user.setUserParty(rs.getInt("userParty"));
				user.setCardNumer(rs.getString("cardNumer"));
				user.setUserNationality(rs.getInt("userNationality"));
				user.setRankId(rs.getInt("rankId"));
				user.setRankName(rs.getString("rankName"));
				user.setPositionId(rs.getInt("positionId"));
				user.setPositionName(rs.getString("positionName"));
				return user;
			}});
		if(reslist==null || reslist.size()<=0){
			return null;
		}
		return reslist.get(0);
	}

    public Adks_user getUserInfo(String username) {
    	String sql = " select userId,userName,userRealName,orgId,orgName,orgCode,userStatus,overdate,userPassword,headPhoto  from adks_user  where userName= '"+username+"' or userPhone = '"+username+"'";
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
				user.setUserStatus(rs.getInt("userStatus"));
				user.setOverdate(rs.getDate("overdate"));
				user.setUserPassword(rs.getString("userPassword"));
				user.setHeadPhoto(rs.getString("headPhoto"));
				return user;
			}});
		if(reslist==null || reslist.size()<=0){
			return null;
		}
		return reslist.get(0);
	}
    public boolean deleteMyCollectionById(Integer userConId) {
    	String sql="delete from adks_usercollection where userConId="+userConId;
    	Integer i=mysqlClient.update(sql, null);
    	if(i>0){
    		return true;
    	}else{
    		return false;
    	}
	}
    public Map<String,Object> checkUserCard(String card) {
    	String sql="select userId,userName,userPassword,userRealName,cardNumer from adks_user where cardNumer='"+card+"'";
		List<Map<String, Object>> list = mysqlClient.queryForList(sql, new Object[0]);
		if(list==null || list.size()<=0){
			return null;
		}else {
			return list.get(0);
		}
	}
    public void changePwd(String userCellphone) {
		String sql="update adks_user as info set info.userPassword='77907642e3c07367930bcd7829c1730d65f2dbcc' where info.userPhone='"+userCellphone+"'";
	}
}
