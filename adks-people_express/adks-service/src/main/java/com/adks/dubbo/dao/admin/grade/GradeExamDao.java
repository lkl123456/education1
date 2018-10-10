package com.adks.dubbo.dao.admin.grade;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.commons.Page;

@Component
public class GradeExamDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_grade_exam";
	}

	/**
	 * 
	 * @Title getGradeExamListPage
	 * @Description：班级已选考试分页列表
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradeExamListPage(Page<List<Map<String, Object>>> page) {
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); // limit
																			// 起始位置
		String sqlbuffer = "select e.examId,e.examName,e.startDate,e.endDate,e.scoreSum,e.passScore,e.examTimes,e.createTime,"
				+ "ge.gradeId,ge.gradeName " + "from adks_grade_exam ge,adks_exam e where 1=1 and ge.examId=e.examId ";
		StringBuilder countsql = new StringBuilder(
				"select count(1) from adks_grade_exam ge,adks_exam e where 1=1 and ge.examId=e.examId ");
		Map map = page.getMap();
		if (map != null && map.size() > 0) {
			// 添加查询条件 。。
			if (map.get("examName") != null) {
				sqlbuffer += " and e.examName like '%" + map.get("examName") + "%'";
				countsql.append(" and e.examName like '%" + map.get("examName") + "%'");
			}
			if (map.get("gradeId") != null) {
				sqlbuffer += " and ge.gradeId = " + map.get("gradeId");
				countsql.append(" and ge.gradeId = " + map.get("gradeId"));
			}
		}
		// 分页
		sqlbuffer += " limit " + offset + ", " + page.getPageSize();
		List<Map<String, Object>> gradeExamList = mysqlClient.queryForList(sqlbuffer, new Object[0]);
		// 查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(gradeExamList);
		return page;
	}

	/**
	 * 
	 * @Title getSelExamListPage
	 * @Description
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getSelExamListPage(Page<List<Map<String, Object>>> page) {
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); // limit
																			// 起始位置
		String sqlbuffer = "select examId,examName,startDate,endDate,scoreSum,passScore,examTimes,createTime "
				+ " from adks_exam " + " where 1=1 ";
		StringBuilder countsql = new StringBuilder("select count(1) from adks_exam where 1=1 ");
		Map map = page.getMap();
		if (map != null && map.size() > 0) {
			// 添加查询条件 。。
			if (map.get("examName") != null) {
				sqlbuffer += " and examName like '%" + map.get("examName") + "%'";
				countsql.append(" and examName like '%" + map.get("examName") + "%'");
			}
			if (map.get("orgCode") != null) {
				sqlbuffer += " and orgCode like '" + map.get("orgCode") + "%'";
				countsql.append(" and orgCode like '" + map.get("orgCode") + "%'");
			}
			if (map.get("examIds") != null) {
				sqlbuffer += " and examId not in (" + map.get("examIds") + ")";
				countsql.append(" and examId not in (" + map.get("examIds") + ")");
			}
		}
		// 分页
		sqlbuffer += " limit " + offset + ", " + page.getPageSize();
		List<Map<String, Object>> courseList = mysqlClient.queryForList(sqlbuffer, new Object[0]);
		// 查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(courseList);
		return page;
	}

	/**
	 * 
	 * @Title removeGradeExam
	 * @Description：移除班级考试
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param map
	 */
	public void removeGradeExam(Map<String, Object> map) {
		String sql = "delete from adks_grade_exam where gradeId=" + map.get("gradeId") + " and examId="
				+ map.get("examId");
		mysqlClient.update(sql, null);
	}

	/**
	 * 
	 * @Title getExamByExamId
	 * @Description：根据examId获取班级信息
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param courseId
	 * @return
	 */
	public Map<String, Object> getExamByExamId(Integer examId) {
		Object[] obj = new Object[] { examId };
		String sql = "select * from adks_exam where examId=? ";
		return mysqlClient.queryForMap(sql, obj);
	}

	/**
	 * 
	 * @Title getGradeExamListPage
	 * @Description：考试授权班级的分页列表
	 * @author shn
	 * @Date 2017年4月14日11:42:21
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradeExamInfoListPage(Page<List<Map<String, Object>>> page) {
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); // limit
																			// 起始位置
		String sqlbuffer = "select g.gradeId,g.gradeName,g.startDate,g.endDate,ge.gradeId,ge.gradeName "
				+ "from adks_grade_exam ge,adks_grade g where 1=1 and ge.gradeId=g.gradeId ";
		StringBuilder countsql = new StringBuilder(
				"select count(1) from adks_grade_exam ge,adks_grade g where 1=1 and ge.gradeId=g.gradeId ");
		Map map = page.getMap();
		if (map != null && map.size() > 0) {
			// 添加查询条件 。。
			if (map.get("examId") != null) {
				sqlbuffer += " and ge.examId = " + map.get("examId");
				countsql.append(" and ge.examId = " + map.get("examId"));
			}
			if (map.get("gradeName") != null) {
				sqlbuffer += " and g.gradeName like '%" + map.get("gradeName") + "%'";
				countsql.append(" and g.gradeName like '%" + map.get("gradeName") + "%'");
			}
		}
		// 分页
		sqlbuffer += " limit " + offset + ", " + page.getPageSize();
		List<Map<String, Object>> gradeExamList = mysqlClient.queryForList(sqlbuffer, new Object[0]);
		// 查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(gradeExamList);
		return page;
	}

	/**
	 * 
	 * @Title delGradeExamByGradeId
	 * @Description：根据gradeId删除班级考试
	 * @author xrl
	 * @Date 2017年4月27日
	 * @param gradeId
	 */
	public void delGradeExamByGradeId(Integer gradeId) {
		String sql = "delete from adks_grade_exam where gradeId=" + gradeId;
		mysqlClient.update(sql, null);
	}
	
	/**
	 * 
	 * @Title getGradeExamListByGradeId
	 * @Description：根据班级ID获取班级考试
	 * @author xrl
	 * @Date 2017年5月5日
	 * @param gradeId
	 * @return
	 */
	public List<Map<String, Object>> getGradeExamListByGradeId(Integer gradeId){
		String sql = " select gradeExamId,examId from adks_grade_exam where gradeId="+gradeId;
		return mysqlClient.queryForList(sql, new Object[]{});
	}
}
