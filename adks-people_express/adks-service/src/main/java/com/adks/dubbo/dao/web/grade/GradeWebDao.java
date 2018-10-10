package com.adks.dubbo.dao.web.grade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.adks.commons.util.DateTimeUtil;
import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.grade.Adks_grade;
import com.adks.dubbo.api.data.grade.Adks_grade_work;
import com.adks.dubbo.commons.Page;

@Repository
public class GradeWebDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_grade";
	}

	public Page<List<Adks_grade_work>> getGradeWorkByCon(Page<List<Adks_grade_work>> page) {
		Integer offset = null; // limit 起始位置
		if (page.getCurrentPage() <= 1) {
			offset = 0;
			page.setCurrentPage(1);
		} else {
			offset = (page.getCurrentPage() - 1) * page.getPageSize();
		}
		StringBuffer sqlbuffer = new StringBuffer(
				"select gw.gradeWorkId,gw.workTitle,gw.startDate,gw.endDate from adks_grade_work gw "
						+ "where   1=1  and releaseState=1 ");
		StringBuffer countsql = new StringBuffer(
				"select count(1) from adks_grade_work gw " + "where   1=1 and releaseState=1 ");
		Map map = page.getMap();
		if (map != null && map.size() > 0) {
			// 添加查询条件 。。
			/*
			 * if(map.get("userId") != null){ sqlbuffer.append(
			 * " and gwr.studentId="+map.get("userId")); countsql.append(
			 * " and gwr.studentId="+map.get("userId")); }
			 */
			if (map.get("gradeId") != null) {
				sqlbuffer.append(" and gw.gradeId=" + map.get("gradeId"));
				countsql.append(" and gw.gradeId=" + map.get("gradeId"));
			}
		}
		sqlbuffer.append(" order by gw.gradeWorkId desc ");
		// 分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();

		List<Adks_grade_work> gradeList = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_grade_work>() {
			@Override
			public Adks_grade_work mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_grade_work gradeWork = new Adks_grade_work();

				gradeWork.setGradeWorkId(rs.getInt("gradeWorkId"));
				gradeWork.setWorkTitle(rs.getString("workTitle"));
				gradeWork.setStartDate(rs.getDate("startDate"));
				gradeWork.setEndDate(rs.getDate("endDate"));
				return gradeWork;
			}
		});

		// 查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setTotalRecords(totalcount);
		if (totalcount % page.getPageSize() == 0) {
			page.setTotalPage(totalcount / page.getPageSize());
			page.setTotalPages(totalcount / page.getPageSize());
		} else {
			page.setTotalPage(totalcount / page.getPageSize() + 1);
			page.setTotalPages(totalcount / page.getPageSize() + 1);
		}

		page.setRows(gradeList);
		return page;
	}

	/**
	 * 
	 * @Title getGradeById
	 * @Description：根据gradeId获取班级信息
	 * @author xrl
	 * @Date 2017年3月25日
	 * @param gradeId
	 * @return
	 */
	public Adks_grade getGradeById(Integer gradeId) {
		String sql = "select gradeId,gradeName ,gradeDesc ,gradeTarget,gradeState,startDate,endDate,gradeImg,"
				+ "requiredPeriod,optionalPeriod,workRequire,examRequire,certificateImg,eleSeal,isRegisit,headTeacherId,headTeacherName,userNum,"
				+ "requiredNum,optionalNum,orgId,orgName,orgCode,creatorName,createTime"
				+ " from adks_grade where gradeId=" + gradeId;

		List<Adks_grade> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_grade>() {
			@Override
			public Adks_grade mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_grade grade = new Adks_grade();
				grade.setGradeId(rs.getInt("gradeId"));
				grade.setGradeName(rs.getString("gradeName"));
				grade.setGradeDesc(rs.getString("gradeDesc"));
				grade.setGradeTarget(rs.getString("gradeTarget"));
				grade.setGradeState(rs.getInt("gradeState"));
				grade.setStartDate(rs.getDate("startDate"));
				grade.setEndDate(rs.getDate("endDate"));
				grade.setGradeImg(rs.getString("gradeImg"));
				grade.setOptionalPeriod(rs.getInt("optionalPeriod"));
				grade.setRequiredPeriod(rs.getInt("requiredPeriod"));
				grade.setWorkRequire(rs.getInt("workRequire"));
				grade.setExamRequire(rs.getInt("examRequire"));
				grade.setCertificateImg(rs.getString("certificateImg"));
				grade.setEleSeal(rs.getString("eleSeal"));
				grade.setIsRegisit(rs.getInt("isRegisit"));
				grade.setHeadTeacherId(rs.getInt("headTeacherId"));
				grade.setHeadTeacherName(rs.getString("headTeacherName"));
				grade.setUserNum(rs.getInt("userNum"));
				grade.setRequiredNum(rs.getInt("requiredNum"));
				grade.setOptionalNum(rs.getInt("optionalNum"));
				grade.setOrgId(rs.getInt("orgId"));
				grade.setOrgName(rs.getString("orgName"));
				grade.setOrgCode(rs.getString("orgCode"));
				grade.setCreateTime(rs.getDate("createTime"));
				grade.setCreatorName(rs.getString("creatorName"));
				return grade;
			}
		});
		if (reslist == null || reslist.size() <= 0) {
			return null;
		}
		return reslist.get(0);
	}

	// 查看用户所在的班级
	public List<Adks_grade> registerGradeListByUserid(Integer userId) {
		String sql = "select g.gradeId,g.gradeName,g.gradeState,g.startDate,g.endDate,g.userNum,g.createTime,g.gradeDesc,g.gradeImg,g.gradeTarget,"
				+ "g.requiredPeriod,g.optionalPeriod from adks_grade as g,adks_grade_user as gu  where  g.gradeId=gu.gradeId "
				+ "and g.gradeState=1 and gu.userId=" + userId + " and g.isRegisit=1 order by g.gradeId desc ";

		List<Adks_grade> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_grade>() {
			@Override
			public Adks_grade mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_grade grade = new Adks_grade();
				grade.setGradeId(rs.getInt("gradeId"));
				grade.setGradeName(rs.getString("gradeName"));
				grade.setGradeState(rs.getInt("gradeState"));
				grade.setStartDate(rs.getDate("startDate"));
				grade.setEndDate(rs.getDate("endDate"));
				grade.setUserNum(rs.getInt("userNum"));
				grade.setCreateTime(rs.getDate("createTime"));
				return grade;
			}
		});
		if (reslist == null || reslist.size() <= 0) {
			return null;
		}
		return reslist;
	}

	// 暂时没有用到
	public List<Adks_grade> gradeTopCurrentList(Map map) {
		String sql = "select SQL_NO_CACHE g.gradeId as gradeId,g.gradeName as gradeName,g.gradeState,g.startDate,g.endDate,g.userNum,u.orgName as orgName,"
				+ "g.gradeDesc,g.gradeImg from adks_grade as g,adks_user as u where  g.creatorId = u.userId and g.gradeState=1 and now()< g.endDate "
				+ "and now() > g.startDate order by g.gradeId desc ";
		if (map.get("orgId") != null && !"".equals(map.get("orgId"))) {
			sql += " and orgId =" + map.get("orgId");
		}
		List<Adks_grade> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_grade>() {
			@Override
			public Adks_grade mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_grade grade = new Adks_grade();

				return grade;
			}
		});
		if (reslist == null || reslist.size() <= 0) {
			return null;
		}
		return reslist;
	}

	/* 用户当前班级页面的分页 */
	public Page<List<Adks_grade>> getGradeCurrentList(Page<List<Adks_grade>> page) {
		Integer offset = null; // limit 起始位置
		if (page.getCurrentPage() <= 1) {
			offset = 0;
			page.setCurrentPage(1);
		} else {
			offset = (page.getCurrentPage() - 1) * page.getPageSize();
		}
		StringBuffer sqlbuffer = new StringBuffer(
				"select g.gradeId,g.gradeName,g.startDate,g.endDate,g.gradeImg,g.userNum,g.orgName "
						+ "from adks_grade g,adks_grade_user gu where g.gradeId=gu.gradeId "
						+ " and g.gradeState=1 and g.startDate < now() and g.endDate > now() ");
		StringBuffer countsql = new StringBuffer(
				"select count(1) from adks_grade g,adks_grade_user gu where g.gradeId=gu.gradeId "
						+ "and g.gradeState=1 and g.startDate < now() and g.endDate > now() ");
		Map map = page.getMap();
		if (map != null && map.size() > 0) {
			// 添加查询条件 。。
			if (map.get("userId") != null) {
				sqlbuffer.append(" and gu.userId=" + map.get("userId"));
				countsql.append(" and gu.userId=" + map.get("userId"));
			}
			if (map.get("orgId") != null) {
				sqlbuffer.append(" and g.orgId=" + map.get("orgId"));
				countsql.append(" and g.orgId=" + map.get("orgId"));
			}
		}
		sqlbuffer.append(" order by g.gradeId desc ");
		// 分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();

		List<Adks_grade> gradeList = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_grade>() {
			@Override
			public Adks_grade mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_grade grade = new Adks_grade();
				grade.setGradeId(rs.getInt("gradeId"));
				grade.setGradeName(rs.getString("gradeName"));
				grade.setStartDate(rs.getDate("startDate"));
				grade.setEndDate(rs.getDate("endDate"));
				grade.setGradeImg(rs.getString("gradeImg"));
				grade.setUserNum(rs.getInt("userNum"));
				grade.setOrgName(rs.getString("orgName"));
				return grade;
			}
		});

		// 查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setTotalRecords(totalcount);
		if (totalcount % page.getPageSize() == 0) {
			page.setTotalPage(totalcount / page.getPageSize());
			page.setTotalPages(totalcount / page.getPageSize());
		} else {
			page.setTotalPage(totalcount / page.getPageSize() + 1);
			page.setTotalPages(totalcount / page.getPageSize() + 1);
		}

		page.setRows(gradeList);
		return page;
	}

	/* 用户当前班级页面的分页 */
	public Page<List<Adks_grade>> getGradeOverList(Page<List<Adks_grade>> page) {
		Integer offset = null; // limit 起始位置
		if (page.getCurrentPage() <= 1) {
			offset = 0;
			page.setCurrentPage(1);
		} else {
			offset = (page.getCurrentPage() - 1) * page.getPageSize();
		}
		StringBuffer sqlbuffer = new StringBuffer(
				"select g.gradeId,g.gradeName,g.startDate,g.endDate,g.gradeImg,g.userNum,g.orgName "
						+ "from adks_grade g,adks_grade_user gu where g.gradeId=gu.gradeId "
						+ " and g.gradeState=1 and g.isRegisit=1 and now() > g.endDate ");
		StringBuffer countsql = new StringBuffer(
				"select count(1) from adks_grade g,adks_grade_user gu where g.gradeId=gu.gradeId and g.gradeState=1 and g.isRegisit=1 and now() > g.endDate ");
		Map map = page.getMap();
		if (map != null && map.size() > 0) {
			// 添加查询条件 。。
			if (map.get("userId") != null) {
				sqlbuffer.append(" and gu.userId=" + map.get("userId"));
				countsql.append(" and gu.userId=" + map.get("userId"));
			}
			if (map.get("orgId") != null) {
				sqlbuffer.append(" and g.orgId=" + map.get("orgId"));
				countsql.append(" and g.orgId=" + map.get("orgId"));
			}
		}
		sqlbuffer.append(" order by g.gradeId desc ");
		// 分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();

		List<Adks_grade> gradeList = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_grade>() {
			@Override
			public Adks_grade mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_grade grade = new Adks_grade();
				grade.setGradeId(rs.getInt("gradeId"));
				grade.setGradeName(rs.getString("gradeName"));
				grade.setStartDate(rs.getDate("startDate"));
				grade.setEndDate(rs.getDate("endDate"));
				grade.setGradeImg(rs.getString("gradeImg"));
				grade.setUserNum(rs.getInt("userNum"));
				grade.setOrgName(rs.getString("orgName"));
				return grade;
			}
		});

		// 查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setTotalRecords(totalcount);
		if (totalcount % page.getPageSize() == 0) {
			page.setTotalPage(totalcount / page.getPageSize());
			page.setTotalPages(totalcount / page.getPageSize());
		} else {
			page.setTotalPage(totalcount / page.getPageSize() + 1);
			page.setTotalPages(totalcount / page.getPageSize() + 1);
		}

		page.setRows(gradeList);
		return page;
	}

	/* 专题班级分页 */
	public Page<List<Adks_grade>> getGradeListWeb(Page<List<Adks_grade>> page) {
		Integer offset = null; // limit 起始位置
		if (page.getCurrentPage() <= 1) {
			offset = 0;
			page.setCurrentPage(1);
		} else {
			offset = (page.getCurrentPage() - 1) * page.getPageSize();
		}
		
		Map map = page.getMap();
		StringBuffer sqlbuffer=null;
		StringBuffer countsql=null;
		if(map.get("userId")!=null){
			sqlbuffer = new StringBuffer(
					"select g.gradeId,g.gradeName,g.gradeDesc,g.gradeTarget,g.gradeState,g.startDate,g.endDate,g.gradeImg,"
							+ "g.requiredPeriod,g.optionalPeriod,g.workRequire,g.examRequire,g.certificateImg,g.eleSeal,g.isRegisit,g.headTeacherId,g.headTeacherName,g.userNum,"
							+ "g.requiredNum,g.optionalNum,g.orgId,g.orgName,g.orgCode,g.creatorName,g.createTime"
							+ " from adks_grade g left join adks_grade_user gu on gu.gradeId=g.gradeId and gu.userId="+map.get("userId")+" where 1=1 and gradeState=1 and isRegisit=1 ");
			countsql = new StringBuffer(
					"select count(1) from adks_grade g left join adks_grade_user gu on gu.gradeId=g.gradeId and gu.userId="+map.get("userId")+"  where 1=1 and gradeState=1 and isRegisit=1 ");
		}else{
			sqlbuffer = new StringBuffer(
					"select gradeId,gradeName ,gradeDesc ,gradeTarget,gradeState,startDate,endDate,gradeImg,"
							+ "requiredPeriod,optionalPeriod,workRequire,examRequire,certificateImg,eleSeal,isRegisit,headTeacherId,headTeacherName,userNum,"
							+ "requiredNum,optionalNum,orgId,orgName,orgCode,creatorName,createTime"
							+ " from adks_grade where 1=1 and gradeState=1 and isRegisit=1 ");
			countsql = new StringBuffer(
					"select count(1) from adks_grade where 1=1 and gradeState=1 and isRegisit=1 ");
		}
		if (map != null && map.size() > 0) {
			// 添加查询条件 。。
			if (map.get("gradeId") != null) {
				sqlbuffer.append(" and gradeId=" + map.get("gradeId"));
				countsql.append(" and gradeId=" + map.get("gradeId"));
			}
			if (map.get("gradeName") != null) {
				sqlbuffer.append(" and gradeName like '%" + map.get("gradeName") + "%'");
				countsql.append(" and gradeName like '%" + map.get("gradeName") + "%'");
			}
			if (map.get("orgCode") != null) {
				sqlbuffer.append(" and orgCode like '%" + map.get("orgCode") + "%'");
				countsql.append(" and orgCode like '%" + map.get("orgCode") + "%'");
			}
		}
		if(map.get("userId")!=null){
			sqlbuffer.append(" ORDER BY gu.gradeId DESC,g.gradeId DESC");
		}else{
			sqlbuffer.append(" order by gradeId desc ");
		}
		// 分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();

		List<Adks_grade> gradeList = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_grade>() {
			@Override
			public Adks_grade mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_grade grade = new Adks_grade();
				grade.setGradeId(rs.getInt("gradeId"));
				grade.setGradeName(rs.getString("gradeName"));
				grade.setGradeDesc(rs.getString("gradeDesc"));
				grade.setGradeTarget(rs.getString("gradeTarget"));
				grade.setGradeState(rs.getInt("gradeState"));
				grade.setStartDate(rs.getDate("startDate"));
				grade.setEndDate(rs.getDate("endDate"));
				grade.setGradeImg(rs.getString("gradeImg"));
				grade.setOptionalPeriod(rs.getInt("optionalPeriod"));
				grade.setRequiredPeriod(rs.getInt("requiredPeriod"));
				grade.setWorkRequire(rs.getInt("workRequire"));
				grade.setExamRequire(rs.getInt("examRequire"));
				grade.setCertificateImg(rs.getString("certificateImg"));
				grade.setEleSeal(rs.getString("eleSeal"));
				grade.setIsRegisit(rs.getInt("isRegisit"));
				grade.setHeadTeacherId(rs.getInt("headTeacherId"));
				grade.setHeadTeacherName(rs.getString("headTeacherName"));
				grade.setUserNum(rs.getInt("userNum"));
				grade.setRequiredNum(rs.getInt("requiredNum"));
				grade.setOptionalNum(rs.getInt("optionalNum"));
				grade.setOrgId(rs.getInt("orgId"));
				grade.setOrgName(rs.getString("orgName"));
				grade.setOrgCode(rs.getString("orgCode"));
				grade.setCreateTime(rs.getDate("createTime"));
				grade.setCreatorName(rs.getString("creatorName"));
				return grade;
			}
		});

		// 查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setTotalRecords(totalcount);
		if (totalcount % page.getPageSize() == 0) {
			page.setTotalPage(totalcount / page.getPageSize());
			page.setTotalPages(totalcount / page.getPageSize());
		} else {
			page.setTotalPage(totalcount / page.getPageSize() + 1);
			page.setTotalPages(totalcount / page.getPageSize() + 1);
		}

		page.setRows(gradeList);
		return page;
	}

	// 获取班级 所在的 年份
	public List<String> getGradeYears(Integer userId) {
		String sql = "select   max(year(g.STARTDATE) ) as maxYear,min(year(g.STARTDATE) )  "
				+ "as minYear from  adks_grade  g, adks_grade_user gu  "
				+ "where   g.gradeId=gu.gradeId  and gu.userId=" + userId;
		List<Map<String, Object>> resultMap = mysqlClient.queryForList(sql, new Object[0]);
		List<String> resultList = new ArrayList<>();
		if (resultMap != null) {
			for (Map<String, Object> map : resultMap) {
				Long maxYear = Long.parseLong(map.get("maxYear") + "");
				Long minYear = Long.parseLong(map.get("minYear") + "");
				if (maxYear.intValue() == minYear.intValue()) {
					resultList.add(maxYear.toString());
				} else {
					for (int i = maxYear.intValue(); i >= minYear.intValue(); i--) {
						resultList.add(String.valueOf(i));
					}
				}
			}
		} else {
			resultList.add(DateTimeUtil.getYearByStr(DateTimeUtil.dateToString(new Date())));
		}
		return resultList;
	}

	public List<Map<String, Object>> getGradeUserRecordList(Integer userId, String selYear) {
		String sql = "select  g.gradeId,g.gradeName,g.startDate,g.endDate,gu.isGraduate "
				+ "from  adks_grade  g, adks_grade_user gu  where   g.gradeId=gu.gradeId " + "and gu.userId=" + userId
				+ " and year(g.startDate)= " + selYear;
		List<Map<String, Object>> resultMap = mysqlClient.queryForList(sql, new Object[0]);
		return resultMap;
	}

	public Integer getUserRanking(Integer gradeId, Integer userId) {
		/*String sql = "select t4.rowno from (select (@rowNO := @rowNo+1) AS rowno,t2.userId from "
				+ " (select (requiredPeriod+optionalPeriod) totleXS, userId "
				+ " from adks_grade_user t1 where gradeId = " + gradeId
				+ " group by userid ORDER BY totleXS desc,userId) t2, (select @rowNO :=0) t3 ) t4 where t4.userid= "
				+ userId;*/
		String sql="SELECT p.rowno from "
				+ "(SELECT obj.userId,obj.period,"
				+ "CASE WHEN @rowtotal = obj.period THEN @rownum "
				+ "WHEN @rowtotal := obj.period THEN @rownum :=@rownum + 1 "
				+ "WHEN @rowtotal = 0 THEN @rownum :=@rownum + 1 END AS rowno FROM "
				+ "(SELECT (requiredPeriod + optionalPeriod) period,userId FROM adks_grade_user where orgId<>0 and gradeId="+gradeId
				+" ORDER BY period DESC) AS obj,(SELECT @rownum := 0 ,@rowtotal := NULL) r) p where p.userId="+userId;
		Map<String, Object> resultMap = mysqlClient.queryForMap(sql, new Object[0]);
		if (resultMap != null && resultMap.size() > 0) {
			String res = resultMap.get("rowno") + "";
			if (res == null || "".equals(res)) {
				return 0;
			} else {
				res = res.indexOf(".") >= 0 ? res.substring(0, res.indexOf(".")) : res;
				return Integer.parseInt(res);
			}
		}
		return 0;
	}

	// 获取班级论文详情信息
	public Adks_grade_work gradeThesisInfo(Map map) {
		String sql = "select gw.gradeWorkId,gw.workTitle,gw.workContent,gw.startDate,gw.endDate,gw.allowFile,gw.maxSize,gw.leastSize,gw.filePath,gwr.submitFilePath"
				+ ",gwr.workAnswer,gwr.workScore,gwr.gradeWorkReplyId,gwr.correntName,gwr.correctId from adks_grade_work gw "
				+ "LEFT JOIN adks_grade_work_reply gwr on gw.gradeWorkId=gwr.workId and gwr.studentId="
				+ map.get("userId") + " where gw.releaseState=1 and gw.gradeWorkId=" + map.get("gradeWorkId");
		List<Adks_grade_work> gradeWorkList = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_grade_work>() {
			@Override
			public Adks_grade_work mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_grade_work gradeWork = new Adks_grade_work();
				gradeWork.setGradeWorkId(rs.getInt("gradeWorkId"));
				gradeWork.setWorkTitle(rs.getString("workTitle"));
				gradeWork.setWorkContent(rs.getString("workContent"));
				gradeWork.setStartDate(rs.getDate("startDate"));
				gradeWork.setEndDate(rs.getDate("endDate"));
				gradeWork.setAllowFile(rs.getInt("allowFile"));
				gradeWork.setMaxSize(rs.getInt("maxSize"));
				gradeWork.setLeastSize(rs.getInt("leastSize"));
				gradeWork.setFilePath(rs.getString("filePath"));
				gradeWork.setSubmitFilePath(rs.getString("submitFilePath"));
				String workScore=rs.getString("workScore");
				if(workScore ==null || "".equals(workScore) || "null".equals(workScore)){
					gradeWork.setWorkScore(null);
				}else{
					gradeWork.setWorkScore(rs.getInt("workScore"));
				}
				gradeWork.setWorkAnswer(rs.getBytes("workAnswer"));
				gradeWork.setGradeWorkReplyId(rs.getInt("gradeWorkReplyId"));
				gradeWork.setCorrentName(rs.getString("correntName"));
				gradeWork.setCorrectId(rs.getInt("correctId"));
				return gradeWork;
			}
		});
		if (gradeWorkList == null || gradeWorkList.size() <= 0) {
			return null;
		}
		return gradeWorkList.get(0);
	}

	public List<Adks_grade> queryAll() {
		List<Adks_grade> list = new ArrayList<>();
		String sql = "SELECT gradeId,orgId,isRegisit,gradeName,gradeImg,startDate,endDate,userNum,gradeDesc,createTime FROM adks_grade";
		List<Map<String, Object>> queryList = mysqlClient.queryList(sql);
		String startDate = null;
		String endDate = null;
		String createTime = null;
		try {
			Adks_grade grade = null;
			for (Map<String, Object> map : queryList) {
				grade = new Adks_grade();
				if (null != map.get("startDate")) {
					startDate = (String) map.get("startDate").toString();
					map.remove("startDate");
					grade.setStartDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate));
				}
				if (null != map.get("endDate")) {
					endDate = (String) map.get("endDate").toString();
					map.remove("endDate");
					grade.setEndDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate));
				}
				if (null != map.get("createTime")) {
					createTime = (String) map.get("createTime").toString();
					map.remove("createTime");
					grade.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createTime));
				}
				BeanUtils.populate(grade, map);
				list.add(grade);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
