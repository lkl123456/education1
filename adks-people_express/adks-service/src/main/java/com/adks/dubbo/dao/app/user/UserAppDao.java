package com.adks.dubbo.dao.app.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.user.Adks_user;

@Component
public class UserAppDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_user";
	}

	/**
	 * 根据用户名查询用户信息
	 * 
	 * @param userame
	 * @return 用户全部字段
	 */
	public Adks_user getUserByName(String username) {
		Object[] obj = new Object[] { username };
		String sql = " select userId,userName,userPassword,userRealName,userSex,orgId,orgName,orgCode,"
				+ "headPhoto,userMail,userPhone,userParty,cardNumer,userNationality,overdate,"
				+ "userStatus,rankId,rankName,userStudyLong,userOnlineLong,creatorId,creatorName,createdate "
				+ "from adks_user  where userName=?";
		List<Adks_user> reslist = mysqlClient.query(sql, obj, new RowMapper<Adks_user>() {
			@Override
			public Adks_user mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_user user = new Adks_user();
				user.setUserId(rs.getInt("userId"));
				user.setUserName(rs.getString("userName"));
				user.setUserPassword(rs.getString("userPassword"));
				user.setUserRealName(rs.getString("userRealName"));
				user.setUserSex(rs.getInt("userSex"));
				user.setOrgId(rs.getInt("orgId"));
				user.setOrgName(rs.getString("orgName"));
				user.setOrgCode(rs.getString("orgCode"));
				user.setHeadPhoto(rs.getString("headPhoto"));
				user.setUserMail(rs.getString("userMail"));
				user.setUserPhone(rs.getString("userPhone"));
				user.setUserParty(rs.getInt("userParty"));
				user.setCardNumer(rs.getString("cardNumer"));
				user.setUserNationality(rs.getInt("userNationality"));
				user.setOverdate(rs.getDate("overdate"));
				user.setUserStatus(rs.getInt("userStatus"));
				user.setRankId(rs.getInt("rankId"));
				user.setRankName(rs.getString("rankName"));
				user.setUserStudyLong(rs.getInt("userStudyLong"));
				user.setUserOnlineLong(rs.getInt("userOnlineLong"));
				user.setCreatorId(rs.getInt("creatorId"));
				user.setCreatorName(rs.getString("creatorName"));

				user.setCreatedate(rs.getDate("createdate"));

				return user;
			}
		});
		if (reslist == null || reslist.size() <= 0)
			return null;
		return reslist.get(0);
	}

	/**
	 * 根据userId获取用户信息
	 * 
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getUserByUserId(int userId) {
		Object[] obj = new Object[] { userId };
		String sql = "select u.userId,u.userName as name,u.userRealName as realname,u.userSex as sex,u.userPhone as cellphone,u.rankName as zhiJiName,u.orgId,u.orgCode,u.orgName,u.headPhoto as headPicpath,u.userMail as mail,u.userStudyLong,e.valName as ationalityName,ifnull(sum(gu.requiredPeriod+gu.optionalPeriod),0) as studyAllXueShi "
				+ "from adks_enumeration_value e,adks_user u left join adks_grade_user gu on gu.userId = u.userId "
				+ "where 1=1 and u.userId=? and u.userNationality=e.enValueId ";
		return mysqlClient.queryForMap(sql, obj);
	}
}
