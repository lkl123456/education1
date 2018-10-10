package com.adks.dubbo.dao.admin.grade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.adks.commons.util.DateUtils;
import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.grade.Adks_grade;
import com.adks.dubbo.commons.Page;

@Repository
public class GradeWorkDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_grade_work";
	}
	
	/**
	 * 
	 * @Title getGradesJson
	 * @Description：获取班级树信息
	 * @author xrl
	 * @Date 2017年3月25日
	 * @param orgId
	 * @return
	 */
	public List<Adks_grade> getGradesJson(Map<String, Object> map){
		String sql = "select * from adks_grade where 1=1";
		if(map != null && map.size() > 0){
			//添加查询条件 
			//班级名模糊查询
			if(map.get("userId") != null){
				sql+=" and headTeacherId="+map.get("userId");
			}
			//gradeId查询
			if(map.get("orgCode") != null){
				sql+=" and orgCode like '"+map.get("orgCode") + "%'";
			}
		}
		List<Adks_grade> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_grade>() {
			@Override
			public Adks_grade mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_grade grade=new Adks_grade();
				grade.setId(rs.getInt("gradeId"));
				grade.setName(rs.getString("gradeName"));
				grade.setText(rs.getString("gradeName"));
				return grade;
			}});
		return reslist;
	}
	
	/**
	 * 
	 * @Title getGradeWorkListPage
	 * @Description：获取班级作业分页列表
	 * @author xrl
	 * @Date 2017年3月25日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradeWorkListPage(Page<List<Map<String, Object>>> page){
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); //limit 起始位置
		StringBuffer sqlbuffer = new StringBuffer(" select * from adks_grade_work where 1=1 and gradeId > 0 ");
		StringBuffer countsql = new StringBuffer("select count(gradeWorkId) from adks_grade_work where 1=1 and gradeId > 0 ");
		
		Map map = page.getMap();
		if(map != null && map.size() > 0){
			//添加查询条件 
			//班级名模糊查询
			if(map.get("workTitle") != null){
				sqlbuffer.append(" and workTitle like '%" + map.get("workTitle") + "%'");
				countsql.append(" and workTitle like '%" + map.get("workTitle") + "%'");
			}
			//gradeId查询
			if(map.get("gradeId") != null){
				sqlbuffer.append(" and gradeId="+ map.get("gradeId"));
				countsql.append(" and gradeId="+ map.get("gradeId"));
			}
			//若是班主任查询
			if(map.get("userId") != null){
				sqlbuffer.append(" and gradeId in(select gradeId from adks_grade where headTeacherId="+ map.get("userId")+")");
				countsql.append(" and gradeId in(select gradeId from adks_grade where headTeacherId="+ map.get("userId")+")");
			}
			//若非班主任且拥有管理权限
			if(map.get("orgCode") != null){
				sqlbuffer.append(" and gradeId in(select gradeId from adks_grade where orgCode like '%"+ map.get("orgCode")+"%')");
				countsql.append(" and gradeId in(select gradeId from adks_grade where orgCode like '%"+ map.get("orgCode")+"%')");
			}
		}
		
		//分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();
		List<Map<String, Object>> gradeWorkList = mysqlClient.queryForList(sql, new Object[0]);
		for (Map<String, Object> gradeWork : gradeWorkList) {
			//班级开始时间
			if(gradeWork.get("startDate")!=null){
				gradeWork.put("startDate_str", DateUtils.getDate2LStr(DateUtils.getStr2LDate(gradeWork.get("startDate").toString())));
			}
			//班级结束时间
			if(gradeWork.get("endDate")!=null){
				gradeWork.put("endDate_str", DateUtils.getDate2LStr(DateUtils.getStr2LDate(gradeWork.get("endDate").toString())));
			}
			//班级创建时间
			if(gradeWork.get("createTime")!=null){
				gradeWork.put("createTime_str", DateUtils.getDate2LStr(DateUtils.getStr2LDate(gradeWork.get("createTime").toString())));
			}
		}
		//查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(gradeWorkList);
		return page;
	}
	
	/**
	 * 
	 * @Title deleteGradeWorkByIds
	 * @Description：删除班级作业
	 * @author xrl
	 * @Date 2017年4月26日
	 * @param ids
	 */
	public void deleteGradeWorkByIds(String ids) {
        String sql = "delete from adks_grade_work where gradeWorkId in ( " + ids + " )";
        mysqlClient.update(sql, new Object[] {});
    }

	/**
	 * 
	 * @Title delGradeWorkByGradeId
	 * @Description：根据gradeId删除班级作业（论文）
	 * @author xrl
	 * @Date 2017年4月27日
	 * @param gradeId
	 */
	public void delGradeWorkByGradeId(Integer gradeId) {
        String sql = "delete from adks_grade_work where gradeId=" + gradeId;
        mysqlClient.update(sql, new Object[] {});
    }
	
	/**
	 * 
	 * @Title getGradeWorkReplyListPage
	 * @Description：班级学员提交作业分页列表
	 * @author xrl
	 * @Date 2017年4月28日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradeWorkReplyListPage(Page<List<Map<String, Object>>> page){
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); //limit 起始位置
		StringBuffer sqlbuffer = new StringBuffer(" select * from adks_grade_work_reply where 1=1 ");
		StringBuffer countsql = new StringBuffer("select count(*) from adks_grade_work_reply where 1=1 ");
		
		Map map = page.getMap();
		if(map != null && map.size() > 0){
			//添加查询条件 
			//学员姓名模糊查询
			if(map.get("studentName") != null){
				sqlbuffer.append(" and studentName like '%" + map.get("studentName") + "%'");
				countsql.append(" and studentName like '%" + map.get("studentName") + "%'");
			}
			//workId查询
			if(map.get("workId") != null){
				sqlbuffer.append(" and workId="+ map.get("workId"));
				countsql.append(" and workId="+ map.get("workId"));
			}
		}
		
		//分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();
		List<Map<String, Object>> gradeWorkList = mysqlClient.queryForList(sql, new Object[0]);
		//查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(gradeWorkList);
		return page;
	}
	
	/**
	 * 
	 * @Title removeGradeWorkReply
	 * @Description:删除班级时，删除班级学员提交过的作业
	 * @author xrl
	 * @Date 2017年5月5日
	 * @param gradeId
	 */
	public void removeGradeWorkReply(Map<String, Object> map) {
        String sql = "delete from adks_grade_work_reply where workId=" + map.get("gradeWorkId")+" and studentId="+map.get("studentId");
        mysqlClient.update(sql, new Object[] {});
    }
	
	/**
	 * 
	 * @Title getGradeWotkByGradeId
	 * @Description：根据班级ID查询班级作业
	 * @author xrl
	 * @Date 2017年5月5日
	 * @param gradeId
	 * @return
	 */
	public List<Map<String, Object>> getGradeWotkListByGradeId(Integer gradeId){
		String sql = " select gradeWorkId from adks_grade_work where gradeId="+gradeId;
		return mysqlClient.queryForList(sql, new Object[]{});
	}
	
	/**
	 * 
	 * @Title checkWorkTitle
	 * @Description：检查班级作业名在该班级中是否重复
	 * @author xrl
	 * @Date 2017年5月9日
	 * @param map
	 * @return
	 */
	public Map<String, Object> checkWorkTitle(Map<String, Object> map){
		Object[] obj = new Object[]{};
		String sql = "select * from adks_grade_work where workTitle='"+map.get("workTitle")+"'";
		if(map.get("gradeId")!=null){
			sql+=" and gradeId!="+map.get("gradeId");
		}
		return mysqlClient.queryForMap(sql, obj);
	}
	
	/**
	 * 
	 * @Title getGradeWorkListByGradeId
	 * @Description：获取班级作业
	 * @author xrl
	 * @Date 2017年5月20日
	 * @param gradeId
	 * @return
	 */
	public List<Map<String, Object>> getGradeWorkListByGradeId(Integer gradeId){
		String sql = " select * from adks_grade_work where gradeId="+gradeId;
		return mysqlClient.queryForList(sql, new Object[]{});
	}
}
