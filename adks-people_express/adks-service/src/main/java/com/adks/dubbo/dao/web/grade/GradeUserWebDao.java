package com.adks.dubbo.dao.web.grade;

import java.math.RoundingMode;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.adks.commons.util.DateUtils;
import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.grade.Adks_grade;
import com.adks.dubbo.api.data.grade.Adks_grade_user;
import com.adks.dubbo.commons.Page;

@Repository
public class GradeUserWebDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_grade_user";
	}
	
	public String getUserTotleXS(Integer id) {
		String sql="select sum(IFNULL(requiredPeriod ,0)+IFNULL(optionalPeriod ,0)) as sum from adks_grade_user where userId = "+id;
		List<Map<String, Object>> result = mysqlClient.queryForList(sql, new Object[0]);
		if(result!=null && result.size()>0){
			DecimalFormat df = new DecimalFormat("0.0");//格式化小数，不足的补0
			df.setRoundingMode(RoundingMode.FLOOR);  //取消四舍五入
			String sum=MapUtils.getString(result.get(0), "sum");
			Double period=null;
			if(sum==null || "null".equals(sum)){
				period=0.0/Double.valueOf(2700);
			}else{
				period=(Double)result.get(0).get("sum")/Double.valueOf(2700);
			}
			String credit = df.format(period);//返回的是String类型的
			return credit;
		}
		return null;
	}
	
	public Adks_grade_user getGradeUserByCon(Integer gradeId, Integer userId) {
		String sql = "select gradeUserId,isGraduate ,requiredPeriod ,optionalPeriod,isCertificate,gradeId,gradeName,userId,"
				+ "userName,orgId,orgName,creatorId,creatorName,createTime"
				+ " from adks_grade_user where gradeId="+gradeId+" and userId="+userId;
		
		List<Adks_grade_user> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_grade_user>() {
			@Override
			public Adks_grade_user mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_grade_user gradeUser=new Adks_grade_user();
				gradeUser.setGradeUserId(rs.getInt("gradeUserId"));
				gradeUser.setUserId(rs.getInt("userId"));
				gradeUser.setUserName(rs.getString("userName"));
				gradeUser.setGradeId(rs.getInt("gradeId"));
				gradeUser.setGradeName(rs.getString("gradeName"));
				gradeUser.setOptionalPeriod(rs.getDouble("optionalPeriod"));
				gradeUser.setRequiredPeriod(rs.getDouble("requiredPeriod"));
				gradeUser.setOrgId(rs.getInt("orgId"));
				gradeUser.setOrgName(rs.getString("orgName"));
				gradeUser.setCreateTime(rs.getDate("createTime"));
				gradeUser.setCreatorName(rs.getString("creatorName"));
				gradeUser.setIsGraduate(rs.getInt("isGraduate"));
				gradeUser.setIsCertificate(rs.getInt("isCertificate"));
				gradeUser.setCreatorId(rs.getInt("creatorId"));
				return gradeUser;
			}});
		if(reslist == null || reslist.size()<=0){
			return null;
		}
		return reslist.get(0);
	}
	
	public Adks_grade_user getUserStudysFromGradeUser(Integer userId, Integer gradeId) {
		String sql = "select gradeUserId,isGraduate ,requiredPeriod ,optionalPeriod,isCertificate,gradeId,gradeName,userId,"
				+ "userName,orgId,orgName,creatorId,creatorName,createTime"
				+ " from adks_grade_user where gradeId="+gradeId+" and userId="+userId;
		
		List<Adks_grade_user> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_grade_user>() {
			@Override
			public Adks_grade_user mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_grade_user gradeUser=new Adks_grade_user();
				gradeUser.setGradeUserId(rs.getInt("gradeUserId"));
				gradeUser.setUserId(rs.getInt("userId"));
				gradeUser.setUserName(rs.getString("userName"));
				gradeUser.setGradeId(rs.getInt("gradeId"));
				gradeUser.setGradeName(rs.getString("gradeName"));
				gradeUser.setOptionalPeriod(rs.getDouble("optionalPeriod"));
				gradeUser.setRequiredPeriod(rs.getDouble("requiredPeriod"));
				gradeUser.setOrgId(rs.getInt("orgId"));
				gradeUser.setOrgName(rs.getString("orgName"));
				gradeUser.setCreateTime(rs.getDate("createTime"));
				gradeUser.setCreatorName(rs.getString("creatorName"));
				gradeUser.setIsGraduate(rs.getInt("isGraduate"));
				gradeUser.setIsCertificate(rs.getInt("isCertificate"));
				gradeUser.setCreatorId(rs.getInt("creatorId"));
				return gradeUser;
			}});
		if(reslist == null || reslist.size()<=0){
			return null;
		}
		return reslist.get(0);
	}
	/**
	 * 
	 * @Title getGradeUserListPage
	 * @Description
	 * @author xrl
	 * @Date 2017年3月29日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradeUserListPage(Page<List<Map<String, Object>>> page) {
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); //limit 起始位置
		String sqlbuffer = "select t1.gradeUserId,t1.gradeId,t1.userId,t1.createTime,"
				+ "t2.userName,t2.userRealName,t2.rankName,t2.rankId,t2.userMail,t2.userPhone,t2.orgName "
				+" from adks_grade_user t1 left join adks_user t2  on t1.userId = t2.userId "
				+" where 1=1 ";
		StringBuilder countsql = new StringBuilder("select count(1) from adks_grade_user t1 left join adks_user t2  on t1.userId = t2.userId where 1=1 ");
		Map map = page.getMap();
		if(map != null && map.size() > 0){
			//添加查询条件 。。
			if(map.get("userName") != null){
				sqlbuffer += " and t1.userName like '%"+map.get("userName")+"%'";
				countsql.append(" and t1.userName like '%"+map.get("userName")+"%'");
			}
			if(map.get("gradeId") != null){
				sqlbuffer += " and t1.gradeId = "+map.get("gradeId");
				countsql.append(" and t1.gradeId = "+map.get("gradeId"));
			}
		}
		//分页
		sqlbuffer += " limit " + offset +", " + page.getPageSize();
		List<Map<String, Object>> gradeUserList = mysqlClient.queryForList(sqlbuffer, new Object[0]);
		//查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(gradeUserList);
		return page;
	}
	
	/**
	 * 
	 * @Title getGradeHeadTeacherPage
	 * @Description：班级已添加的班主任
	 * @author xrl
	 * @Date 2017年3月30日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradeHeadTeacherPage(Page<List<Map<String, Object>>> page){
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); //limit 起始位置
		String sqlbuffer = "select t1.gradeId,t1.gradeName,t1.headTeacherId as userId,t1.headTeacherName as userRealName,"
				+ "t2.userName,t2.rankName,t2.rankId,t2.userMail,t2.orgName,t2.userPhone "
				+ "from adks_grade t1 right join adks_user t2 on t1.headTeacherId=t2.userId "
				+ "where 1=1 ";
		StringBuilder countsql = new StringBuilder("select count(1) from adks_grade t1 right join adks_user t2 on t1.headTeacherId=t2.userId where 1=1 ");
		Map map = page.getMap();
		if(map != null && map.size() > 0){
			//添加查询条件 。。
			if(map.get("userName") != null){
				sqlbuffer += " and t1.headTeacherName like '%"+map.get("userRealName")+"%'";
				countsql.append(" and t1.headTeacherName like '%"+map.get("userRealName")+"%'");
			}
			if(map.get("gradeId") != null){
				sqlbuffer += " and t1.gradeId = "+map.get("gradeId");
				countsql.append(" and t1.gradeId = "+map.get("gradeId"));
			}
		}
		//分页
		sqlbuffer += " limit " + offset +", " + page.getPageSize();
		List<Map<String, Object>> gradeUserList = mysqlClient.queryForList(sqlbuffer, new Object[0]);
		//查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(gradeUserList);
		return page;
	}
	
	/**
	 * 
	 * @Title getHeadTeacherIdByGradeId
	 * @Description：获取班级已添加的班主任的userId
	 * @author xrl
	 * @Date 2017年3月30日
	 * @param gradeId
	 * @return
	 */
	public Map<String, Object> getHeadTeacherIdByGradeId(Integer gradeId){
		Object[] obj = new Object[]{gradeId};
		String sql = "select gradeId,gradeName,headTeacherId,headTeacherName  "
				+ " from adks_grade where gradeId=? ";
		return mysqlClient.queryForMap(sql, obj);
	}
	
	/**
	 * 
	 * @Title updateGradeHeadTeacher
	 * @Description：添加班级班主任
	 * @author xrl
	 * @Date 2017年3月30日
	 * @param map
	 */
	public void updateGradeHeadTeacher(Map<String, Object> map){
		String sql = "update adks_grade set headTeacherId="+map.get("headTeacherId")
		+",headTeacherName='"+map.get("headTeacherName")+"' where gradeId="+map.get("gradeId");
		mysqlClient.update(sql, null);
	}
	
	/**
	 * 
	 * @Title getGradeUserByUserId
	 * @Description:根据userId查询班级用户信息
	 * @author xrl
	 * @Date 2017年3月30日
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getGradeUserByUserId(Integer userId){
		Object[] obj = new Object[]{userId};
		String sql = "select * from adks_grade_user where userId=? ";
		return mysqlClient.queryForMap(sql, obj);
	}
	
	public List<Adks_grade_user> getTopCourseStudyTimeUserList(Map map){
		/*String sql = "SELECT u.userRealName as userName,ifnull(a.sumUserStudytime,0) as sumUserStudytime,u.orgName as orgName from adks_user u LEFT JOIN "
				+ "(select round((SUM(gu.requiredPeriod)+SUM(gu.optionalPeriod))/100,1) as sumUserStudytime, gu.userId ,u.userRealName as realName"
				+ " from adks_grade_user gu,adks_user u where gu.userId=u.userId  GROUP BY gu.userId ) a on a.userId  = u.userId"
				+ " where 1=1 and u.ORGCODE like CONCAT('%','"+map.get("orgCode")+"','%') order by a.sumUserStudytime desc limit 0,2";*/
		String sql = "SELECT u.userRealName as userName,ifnull(a.sumUserStudytime,0) as sumUserStudytime,u.orgName as orgName from adks_user u LEFT JOIN "
				+ "(select (SUM(gu.requiredPeriod)+SUM(gu.optionalPeriod)) as sumUserStudytime, gu.userId ,u.userRealName as realName"
				+ " from adks_grade_user gu,adks_user u where gu.userId=u.userId  GROUP BY gu.userId ) a on a.userId  = u.userId"
				+ " where 1=1 and u.ORGCODE like CONCAT('%','"+map.get("orgCode")+"','%') order by a.sumUserStudytime desc limit 0,"+map.get("num");
		List<Map<String, Object>> mapResult = mysqlClient.queryForList(sql, new Object[0]);
		if(mapResult == null || mapResult.size()<=0){
			return null;
		}
		List<Adks_grade_user> reslist=new ArrayList<Adks_grade_user>();
		for (Map<String, Object> map2 : mapResult) {
			Adks_grade_user gradeUser=new Adks_grade_user();
			if(map2.get("userName") != null){
				gradeUser.setUserName(map2.get("userName")+"");
			}
			if(map2.get("sumUserStudytime") != null){
				DecimalFormat df = new DecimalFormat("0.0");//格式化小数，不足的补0
				df.setRoundingMode(RoundingMode.FLOOR);  //取消四舍五入
				Double sumUserStudytime=(Double)map2.get("sumUserStudytime")/Double.valueOf(2700);
				String suStudyTime = df.format(sumUserStudytime);//返回的是String类型的
				gradeUser.setSumUserStudytime(Float.valueOf(suStudyTime));			
			}
			if(map2.get("orgName") != null){
				gradeUser.setOrgName(map2.get("orgName")+"");
			}
			reslist.add(gradeUser);
		}
		return reslist;
	}
	
	
	public void updateGradeUserForCredit(Map<String, Object> map){
		if(map.get("requiredPeriod")!=null){
        	String sql="update adks_grade_user set requiredPeriod="+map.get("requiredPeriod")+" where gradeUserId="+map.get("gradeUserId");
        	mysqlClient.update(sql, new Object[] {});
        }else if(map.get("optionalPeriod")!=null){
        	String sql="update adks_grade_user set optionalPeriod="+map.get("optionalPeriod")+" where gradeUserId="+map.get("gradeUserId");
        	mysqlClient.update(sql, new Object[] {});
        }
	}
}
