package com.adks.dubbo.dao.app.grade;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.commons.Page;

@Repository
public class GradeUserAppDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_grade_user";
	}

	/**
	 * 根据userId获取的当前班级信息
	 * 
	 * @param page
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Page<List<Map<String, Object>>> getCurrentGradeListByUserId(Page<List<Map<String, Object>>> page) {
		Integer offset = page.getStartLimit(); // limit
		String sqlbuffer = "select "
				+ "g.gradeId,g.gradeName as name,g.gradeDesc as description,g.gradeImg as logoPath,g.userNum,g.startDate,g.endDate,gu.isGraduate "
				+ "from adks_grade g,adks_grade_user gu where 1=1 and g.gradeId=gu.gradeId ";
		StringBuffer countsql = new StringBuffer(
				"select count(1) from adks_grade g,adks_grade_user gu where 1=1 and g.gradeId=gu.gradeId ");
		Map map = page.getMap();
		if (map != null && map.size() > 0) {
			// 添加查询条件
			if (map.get("userId") != null) {
				sqlbuffer += " and gu.userId =" + map.get("userId");
				countsql.append(" and gu.userId =" + map.get("userId"));
			}
		}
		sqlbuffer += " and now() < g.endDate and now() > g.startDate order by gu.createTime desc";
		// 分页
		sqlbuffer += " limit " + offset + ", " + page.getPageSize();
		List<Map<String, Object>> list = mysqlClient.queryForList(sqlbuffer, new Object[0]);
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(list);
		return page;
	}

	/**
	 * 根据userId获取的历史班级信息
	 * 
	 * @param page
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Page<List<Map<String, Object>>> getHistoryGradeListByUserId(Page<List<Map<String, Object>>> page) {
		Integer offset = page.getStartLimit(); // limit
		String sqlbuffer = "select "
				+ "g.gradeId,g.gradeName as name,g.gradeDesc as description,g.gradeImg as logoPath,g.userNum as userNum,g.startDate as startDate,g.endDate as endDate,gu.isGraduate as isGuradue "
				+ "from adks_grade g left join adks_grade_user gu on g.gradeId=gu.gradeId where 1=1 ";
		StringBuffer countsql = new StringBuffer(
				"select count(1) from adks_grade g left join adks_grade_user gu on g.gradeId=gu.gradeId where 1=1");
		Map map = page.getMap();
		if (map != null && map.size() > 0) {
			// 添加查询条件
			if (map.get("userId") != null) {
				sqlbuffer += " and gu.userId =" + map.get("userId");
				countsql.append(" and gu.userId =" + map.get("userId"));
			}
		}
		sqlbuffer += " and now() > g.endDate order by gu.createTime desc";
		// 分页
		sqlbuffer += " limit " + offset + ", " + page.getPageSize();

		List<Map<String, Object>> list = mysqlClient.queryForList(sqlbuffer, new Object[0]);
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(list);
		return page;
	}

	/**
	 * 根据userId和gradeId查询班级信息
	 * 
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getGradeByUserIdAndGradeId(int userId, int gradeId) {
		Object[] obj = new Object[] { userId, gradeId };
		String sql = "select "
				+ "g.gradeId,g.gradeName,g.gradeImg as logoPath,datediff(g.endDate,now()) as remainedDays,sum(gu.requiredPeriod+gu.optionalPeriod) as myXueshi "
				+ "from adks_grade_user gu,adks_grade g where 1=1 and g.gradeId = gu.gradeId and gu.userId=? and g.gradeId =?";
		return mysqlClient.queryForMap(sql, obj);
	}

	/**
	 * 根据userId和gradeId获取学员排名（必修学时+选修学时）
	 * 
	 * @param userId
	 * @return
	 */
	public int getGradeOrderRequiredPeriod(Integer userId, Integer gradeId) {
		Object[] obj = new Object[] { userId, gradeId };
		String sql = "select t4.rowno from (select (@rowNO := @rowNo+1) AS rowno,t2.userId "
				+ "from (select (requiredPeriod+optionalPeriod)  totleXS, userId from adks_grade_user t1 where gradeId = 1 group by userId ORDER BY totleXS desc,userId) t2, (select @rowNO :=0) t3 ) t4 where t4.userid=5";
		return mysqlClient.queryforInt(sql, obj);
	}

	/**
	 * 根据userId获取全部班级
	 * 
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getGradeListByUserId(int userId) {
		String sql = "select * from adks_grade_user where 1=1 and userId=" + userId;
		return mysqlClient.queryForList(sql, new Object[0]);
	}
}
