package com.adks.dubbo.dao.admin.grade;

import java.math.RoundingMode;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.adks.commons.util.DateUtils;
import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.grade.Adks_grade;
import com.adks.dubbo.api.data.grade.Adks_grade_user;
import com.adks.dubbo.commons.Page;

@Component
public class GradeUserDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_grade_user";
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
				+" from adks_grade_user t1,adks_user t2 "
				+" where t1.userId = t2.userId ";
		StringBuilder countsql = new StringBuilder("select count(1) from adks_grade_user t1,adks_user t2 where t1.userId = t2.userId ");
		Map map = page.getMap();
		if(map != null && map.size() > 0){
			//添加查询条件 。。
			if(map.get("userName") != null){
				sqlbuffer += " and (t1.userName like '%"+map.get("userName")+"%' or t2.userName like '%"+map.get("userName")+"%')";
				countsql.append(" and (t1.userName like '%"+map.get("userName")+"%' or t2.userName like '%"+map.get("userName")+"%')");
			}
			if(map.get("gradeId") != null){
				sqlbuffer += " and t1.gradeId = "+map.get("gradeId");
				countsql.append(" and t1.gradeId = "+map.get("gradeId"));
			}
			if(map.get("orgCode")!=null){
				sqlbuffer += " and t2.orgCode like '"+map.get("orgCode")+"%'";
				countsql.append(" and t2.orgCode like '"+map.get("orgCode")+"%'");
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
				sqlbuffer += " and (t2.userName like '%"+map.get("userName")+"%' or t2.userName like '%"+map.get("userName")+"%')";
				countsql.append(" and (t2.userName like '%"+map.get("userName")+"%' or t2.userName like '%"+map.get("userName")+"%')");
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
		Object[] obj = new Object[]{map.get("headTeacherId"),map.get("headTeacherName"),map.get("gradeId")};
		String sql = "update adks_grade set headTeacherId=?,headTeacherName=? where gradeId=? ";
		mysqlClient.update(sql, obj);
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
	public Map<String, Object> getGradeUserByUserId(Map<String, Object> map){
		Object[] obj = new Object[]{map.get("userId"),map.get("gradeId")};
		String sql = "select * from adks_grade_user where userId=? and gradeId=? ";
		return mysqlClient.queryForMap(sql, obj);
	}
	
	public List<Adks_grade_user> getTopCourseStudyTimeUserList(Map map){
		String sql = "SELECT u.userRealName as userName,ifnull(a.sumUserStudytime,0) as sumUserStudytime,u.orgName as orgName from adks_user u LEFT JOIN "
				+ "(select round((SUM(gu.requiredPeriod)+SUM(gu.optionalPeriod))/100,1) as sumUserStudytime, gu.userId ,u.userRealName as realName"
				+ " from adks_grade_user gu,adks_user u where gu.userId=u.userId  GROUP BY gu.userId ) a on a.userId  = u.userId"
				+ " where 1=1 and u.ORGCODE like CONCAT('%','"+map.get("orgCode")+"','%') order by a.sumUserStudytime desc limit 0,2";
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
				gradeUser.setSumUserStudytime(new Float(map2.get("sumUserStudytime")+""));			
			}
			if(map2.get("orgName") != null){
				gradeUser.setOrgName(map2.get("orgName")+"");
			}
			//gradeUser.setCreateTime(new java.util.Date());
			reslist.add(gradeUser);
		}
		return reslist;
	}
	
	/**
	 * 
	 * @Title delGradeUserByGradeId
	 * @Description:删除班级时，删除班级用户
	 * @author xrl
	 * @Date 2017年5月5日
	 * @param gradeId
	 */
	public void delGradeUserByGradeId(Integer gradeId){
		Object[] obj = new Object[]{gradeId};
		String sql = "delete from adks_grade_user where gradeId=? ";
        mysqlClient.update(sql,obj);
	}
	
	/**
	 * 
	 * @Title removeGradeUsers
	 * @Description：删除班级用户
	 * @author xrl
	 * @Date 2017年5月5日
	 * @param map
	 */
	public void removeGradeUsers(Map<String, Object> map){
		Object[] obj = new Object[]{map.get("gradeId"),map.get("gradeUserId")};
		String sql = "delete from adks_grade_user where gradeId=? and gradeUserId=? ";
        mysqlClient.update(sql,obj);
	}
	
	/**
	 * 
	 * @Title getGradeUserList
	 * @Description：获取班级学员列表
	 * @author xrl
	 * @Date 2017年5月19日
	 * @param gradeId
	 * @return
	 */
	public List<Map<String, Object>> getGradeUserList(Integer gradeId){
		Object[] obj = new Object[]{gradeId};
		String sql = " select * from adks_grade_user where gradeId=?";
		return mysqlClient.queryForList(sql,obj);
	}
	
	/**
	 * 
	 * @Title updateAllGradeUserGraduate
	 * @Description:更新班级用户结业状态
	 * @author xrl
	 * @Date 2017年5月20日
	 * @param gradeId
	 * @param userId
	 */
	public void updateAllGradeUserGraduate(Integer gradeId, Integer userId){
		Object[] obj = new Object[]{gradeId,userId};
		String sql = "update adks_grade_user set isGraduate=1 where gradeId=? and userId=?";
		mysqlClient.update(sql, obj);
	}
	
	/**
	 * 
	 * @Title updateGradeUserCredit
	 * @Description：更新用户必修和选修学时
	 * @author xrl
	 * @Date 2017年6月15日
	 * @param map
	 */
	public void updateGradeUserCredit(Map<String, Object> map){
		Object[] obj = new Object[]{map.get("requiredPeriod"),map.get("optionalPeriod"),map.get("gradeId"),map.get("userId")};
		String sql = "update adks_grade_user set requiredPeriod=?,optionalPeriod=? where gradeId=? and userId=?";
		mysqlClient.update(sql, obj);
	}
	
	/**
	 * 
	 * @Title getGradeStudyListPage
	 * @Description：获取班级学习统计列表
	 * @author xrl
	 * @Date 2017年6月14日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradeStudyListPage(Page<List<Map<String, Object>>> page) {
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); //limit 起始位置
		String sqlbuffer = "select gu.gradeUserId,gu.gradeId,g.gradeName,IFNULL((gu.requiredPeriod+gu.optionalPeriod),1) as period,IFNULL(gu.requiredPeriod,0) as requiredPeriod,IFNULL(gu.optionalPeriod,0) as optionalPeriod,gu.isGraduate,gu.isCertificate,"
				+ "u.userRealName,u.userId,u.userName,IFNULL(sum(es.score),0) as examScore,IFNULL(sum(gwr.workScore),0) as workScore "
				+ "from adks_grade g "
				+ " LEFT JOIN adks_grade_user gu ON g.gradeId = gu.gradeId"
				+ " LEFT JOIN adks_user u ON u.userId = gu.userId"
				+ " LEFT JOIN adks_grade_exam gx ON g.gradeId = gx.gradeId"
				+ " LEFT JOIN adks_exam_score es ON es.examId = gx.examId AND es.gradeId = g.gradeId AND es.userId = gu.userId AND es.userId = u.userId"
				+ " LEFT JOIN adks_grade_work gw ON gw.gradeId = g.gradeId"
				+ " LEFT JOIN adks_grade_work_reply gwr ON gw.gradeWorkId = gwr.workId AND gwr.studentId = gu.userId AND gwr.studentId = u.userId"
				+ " where g.gradeId = gu.gradeId and gu.userId=u.userId";
		StringBuilder countsql = new StringBuilder("select count(gu.gradeUserId) from adks_grade g "
				+ " LEFT JOIN adks_grade_user gu ON g.gradeId = gu.gradeId"
				+ " LEFT JOIN adks_user u ON u.userId = gu.userId"
				+ " LEFT JOIN adks_grade_exam gx ON g.gradeId = gx.gradeId"
				+ " LEFT JOIN adks_exam_score es ON es.examId = gx.examId AND es.gradeId = g.gradeId AND es.userId = gu.userId AND es.userId = u.userId"
				+ " LEFT JOIN adks_grade_work gw ON gw.gradeId = g.gradeId"
				+ " LEFT JOIN adks_grade_work_reply gwr ON gw.gradeWorkId = gwr.workId AND gwr.studentId = gu.userId AND gwr.studentId = u.userId"
				+ " where g.gradeId = gu.gradeId and gu.userId=u.userId");
		Map map = page.getMap();
		if(map != null && map.size() > 0){
			//添加查询条件 。。
			if(map.get("gradeId") != null){
				sqlbuffer += " and g.gradeId = "+map.get("gradeId");
				countsql.append(" and g.gradeId = "+map.get("gradeId"));
			}
			if(map.get("userName") != null){
				sqlbuffer += " and (u.userName like '%"+map.get("userName")+"%' or u.userRealName like '%"+map.get("userName")+"%')";
				countsql.append(" and (u.userName like '%"+map.get("userName")+"%' or u.userRealName like '%"+map.get("userName")+"%')");
			}
			if(map.get("userId") != null){
				sqlbuffer += " and g.headTeacherId = "+map.get("userId");
				countsql.append(" and g.headTeacherId = "+map.get("userId"));
			}
			if(map.get("orgCode")!=null){
				sqlbuffer += " and g.orgCode like '"+map.get("orgCode")+"%'";
				countsql.append(" and g.orgCode like '"+map.get("orgCode")+"%'");
			}
			if(map.get("userOrgCode")!=null){
				sqlbuffer += " and u.orgCode like '"+map.get("userOrgCode")+"%'";
				countsql.append(" and u.orgCode like '"+map.get("userOrgCode")+"%'");
			}
		}
		sqlbuffer += " group by gu.gradeUserId";
		//分页
		sqlbuffer += " limit " + offset +", " + page.getPageSize();
		List<Map<String, Object>> gradeUserList = mysqlClient.queryForList(sqlbuffer, new Object[0]);
		for (Map<String, Object> gradeUser : gradeUserList) {
			DecimalFormat df = new DecimalFormat("0.0");//格式化小数，不足的补0
			df.setRoundingMode(RoundingMode.FLOOR);  //取消四舍五入
			Double period=(Double)gradeUser.get("period")/Double.valueOf(2700);
			String pd = df.format(period);//返回的是String类型的
			gradeUser.put("period", pd);
			Double requiredPeriod=(Double)gradeUser.get("requiredPeriod")/Double.valueOf(2700);
			String rpd = df.format(requiredPeriod);//返回的是String类型的
			gradeUser.put("requiredPeriod", rpd);
			Double optionalPeriod=(Double)gradeUser.get("optionalPeriod")/Double.valueOf(2700);
			String opd = df.format(optionalPeriod);//返回的是String类型的
			gradeUser.put("optionalPeriod", opd);
		}
		//查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(gradeUserList);
		return page;
	}
	
	/**
	 * 
	 * @Title getGradeUserByGradeUserId
	 * @Description：根据gradeUserId获取班级用户信息
	 * @author xrl
	 * @Date 2017年6月15日
	 * @param gradeUserId
	 * @return
	 */
	public Map<String, Object> getGradeUserByGradeUserId(Integer gradeUserId){
		Object[] obj = new Object[]{gradeUserId};
		String sql = "select * from adks_grade_user where gradeUserId=? ";
		return mysqlClient.queryForMap(sql, obj);
	}
	
	/**
	 * 
	 * @Title getGradeUserByUserIdAndGradeId
	 * @Description:根据userId和gradeId获取班级学员信息
	 * @author xrl
	 * @Date 2017年6月15日
	 * @param map
	 * @return
	 */
	public Map<String, Object> getGradeUserByUserIdAndGradeId(Map<String, Object> map){
		Object[] obj = new Object[]{map.get("gradeId"),map.get("userId")};
		String sql = "select * from adks_grade_user where gradeId=? and userId=?";
		return mysqlClient.queryForMap(sql, obj);
	}
	
	/**
	 * 
	 * @Title addGradeUserCredit
	 * @Description：更新班级学员必修或选修学时
	 * @author xrl
	 * @Date 2017年6月15日
	 * @param map
	 */
	public void addGradeUserCredit(Map<String, Object> map){
		if(map.get("requiredPeriod")!=null){
			Object[] obj = new Object[]{map.get("requiredPeriod"),map.get("gradeId"),map.get("userId")};
			String sql = "update adks_grade_user set requiredPeriod=? where gradeId=? and userId=?";
			mysqlClient.update(sql, obj);
		}
		if(map.get("optionalPeriod")!=null){
			Object[] obj = new Object[]{map.get("optionalPeriod"),map.get("gradeId"),map.get("userId")};
			String sql = "update adks_grade_user set optionalPeriod=? where gradeId=? and userId=?";
			mysqlClient.update(sql, obj);
		}
	}
	
	/**
	 * 
	 * @Title getGradePerformanceListPage
	 * @Description:班级学员成绩分页列表
	 * @author xrl
	 * @Date 2017年6月16日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradePerformanceListPage(Page<List<Map<String, Object>>> page) {
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); //limit 起始位置
		String sqlbuffer = "select gu.gradeUserId,gu.gradeId,g.gradeName,IFNULL((gu.requiredPeriod+gu.optionalPeriod),1) as period,IFNULL(gu.requiredPeriod,0) as requiredPeriod,IFNULL(gu.optionalPeriod,0) as optionalPeriod,gu.isGraduate,gu.isCertificate,"
				+ "u.userRealName,u.userId,u.userName "
				+ "from adks_grade g "
				+ " LEFT JOIN adks_grade_user gu ON g.gradeId = gu.gradeId"
				+ " LEFT JOIN adks_user u ON u.userId = gu.userId"
				+ " where g.gradeId = gu.gradeId and gu.userId=u.userId";
		StringBuilder countsql = new StringBuilder("select count(gu.gradeUserId) from adks_grade g "
				+ " LEFT JOIN adks_grade_user gu ON g.gradeId = gu.gradeId"
				+ " LEFT JOIN adks_user u ON u.userId = gu.userId"
				+ " where g.gradeId = gu.gradeId and gu.userId=u.userId");
		Map map = page.getMap();
		if(map != null && map.size() > 0){
			//添加查询条件 。。
			if(map.get("gradeId") != null){
				sqlbuffer += " and g.gradeId = "+map.get("gradeId");
				countsql.append(" and g.gradeId = "+map.get("gradeId"));
			}
			if(map.get("userName") != null){
				sqlbuffer += " and (u.userName like '%"+map.get("userName")+"%' or u.userRealName like '%"+map.get("userName")+"%')";
				countsql.append(" and (u.userName like '%"+map.get("userName")+"%' or u.userRealName like '%"+map.get("userName")+"%')");
			}
			if(map.get("userId") != null){
				sqlbuffer += " and g.headTeacherId = "+map.get("userId");
				countsql.append(" and g.headTeacherId = "+map.get("userId"));
			}
			if(map.get("orgCode")!=null){
				sqlbuffer += " and g.orgCode like '"+map.get("orgCode")+"%'";
				countsql.append(" and g.orgCode like '"+map.get("orgCode")+"%'");
			}
			if(map.get("userOrgCode")!=null){
				sqlbuffer += " and u.orgCode like '"+map.get("userOrgCode")+"%'";
				countsql.append(" and u.orgCode like '"+map.get("userOrgCode")+"%'");
			}
		}
		sqlbuffer += " group by gu.gradeUserId";
		//分页
		sqlbuffer += " limit " + offset +", " + page.getPageSize();
		List<Map<String, Object>> gradeUserList = mysqlClient.queryForList(sqlbuffer, new Object[0]);
		for (Map<String, Object> gradeUser : gradeUserList) {
			DecimalFormat df = new DecimalFormat("0.0");//格式化小数，不足的补0
			df.setRoundingMode(RoundingMode.FLOOR);  //取消四舍五入
			Double period=(Double)gradeUser.get("period")/Double.valueOf(2700);
			String pd = df.format(period);//返回的是String类型的
			gradeUser.put("period", pd);
			Double requiredPeriod=(Double)gradeUser.get("requiredPeriod")/Double.valueOf(2700);
			String rpd = df.format(requiredPeriod);//返回的是String类型的
			gradeUser.put("requiredPeriod", rpd);
			Double optionalPeriod=(Double)gradeUser.get("optionalPeriod")/Double.valueOf(2700);
			String opd = df.format(optionalPeriod);//返回的是String类型的
			gradeUser.put("optionalPeriod", opd);
		}
		//查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(gradeUserList);
		return page;
	}
	
	/**
	 * 
	 * @Title getGradeStudyStatisticsList
	 * @Description：获取班级学习详情列表
	 * @author xrl
	 * @Date 2017年6月20日
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getGradeStudyStatisticsList(Map<String, Object> map){
		String sql = "select gu.gradeId,g.gradeName,IFNULL((gu.requiredPeriod+gu.optionalPeriod),1) as period,IFNULL(gu.requiredPeriod,0) as requiredPeriod,IFNULL(gu.optionalPeriod,0) as optionalPeriod,gu.isGraduate,gu.isCertificate,"
				+ "u.userRealName,u.userName,u.userSex,u.userPhone,u.orgName,IFNULL(sum(es.score),0) as examScore,IFNULL(sum(gwr.workScore),0) as workScore "
				+ "from adks_grade g "
				+ " LEFT JOIN adks_grade_user gu ON g.gradeId = gu.gradeId"
				+ " LEFT JOIN adks_user u ON u.userId = gu.userId"
				+ " LEFT JOIN adks_grade_exam gx ON g.gradeId = gx.gradeId"
				+ " LEFT JOIN adks_exam_score es ON es.examId = gx.examId AND es.gradeId = g.gradeId AND es.userId = gu.userId AND es.userId = u.userId"
				+ " LEFT JOIN adks_grade_work gw ON gw.gradeId = g.gradeId"
				+ " LEFT JOIN adks_grade_work_reply gwr ON gw.gradeWorkId = gwr.workId AND gwr.studentId = gu.userId AND gwr.studentId = u.userId"
				+ " where g.gradeId = gu.gradeId and gu.userId=u.userId and u.orgId <> 0 ";
		if(map.get("gradeId")!=null){
			sql+=" and g.gradeId="+map.get("gradeId");
		}
		if(map.get("orgCode")!=null){
			sql+=" and u.orgCode like '"+map.get("orgCode")+"%'";
		}
		sql += " group by gu.gradeUserId";
		return mysqlClient.queryForList(sql,new Object[0]);
	}
	
	/**
	 * 
	 * @Title getGradePerformanceList
	 * @Description：获取班级成绩统计列表
	 * @author xrl
	 * @Date 2017年6月20日
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getGradePerformanceList(Map<String, Object> map){
		String sql = "select gu.gradeUserId,gu.gradeId,g.gradeName,IFNULL((gu.requiredPeriod+gu.optionalPeriod),1) as period,IFNULL(gu.requiredPeriod,0) as requiredPeriod,IFNULL(gu.optionalPeriod,0) as optionalPeriod,gu.isGraduate,gu.isCertificate,"
				+ "u.userRealName,u.userId,u.userName,u.userSex,u.userPhone,u.orgName "
				+ "from adks_grade g "
				+ " LEFT JOIN adks_grade_user gu ON g.gradeId = gu.gradeId"
				+ " LEFT JOIN adks_user u ON u.userId = gu.userId"
				+ " where g.gradeId = gu.gradeId and gu.userId=u.userId and u.orgId <> 0 ";
		if(map.get("gradeId")!=null){
			sql+=" and g.gradeId="+map.get("gradeId");
		}
		if(map.get("orgCode")!=null){
			sql+=" and u.orgCode like '"+map.get("orgCode")+"%'";
		}
		sql += " group by gu.gradeUserId";
		return mysqlClient.queryForList(sql,new Object[0]);
	}
	
	public Integer getUserCount(Integer gradeId){
		String sql="select count(gradeUserId) from adks_grade_user where orgId <>0 and gradeId=? ";
		return mysqlClient.queryforInt(sql,new Object[]{gradeId});
    }
	
}
