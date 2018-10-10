package com.adks.dubbo.dao.web.grade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.adks.commons.util.DateTimeUtil;
import com.adks.commons.util.DateUtils;
import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.course.Adks_course_user;
import com.adks.dubbo.api.data.grade.Adks_grade;
import com.adks.dubbo.api.data.grade.Adks_grade_course;
import com.adks.dubbo.api.data.grade.Adks_grade_exam;
import com.adks.dubbo.api.data.grade.Adks_grade_user;
import com.adks.dubbo.api.data.grade.Adks_grade_work;
import com.adks.dubbo.api.data.grade.Adks_grade_work_reply;
import com.adks.dubbo.api.data.news.Adks_news;
import com.adks.dubbo.commons.Page;

@Repository
public class GradeWorkWebDao extends BaseDao {

	
	@Override
	protected String getTableName() {
		return "adks_grade_work";
	}
	
	public List<Adks_grade_work_reply> getGradeWorkReplyByCon(Map map) {
		String sql="select gw.gradeWorkId,gw.workTitle,gwr.submitDate,gwr.workScore "
				+ "from adks_grade_work gw,adks_grade_work_reply gwr where gw.gradeWorkId=gwr.workId "
				+ "and gwr.studentId="+map.get("userId")+" and gw.gradeId="+map.get("gradeId");
		List<Adks_grade_work_reply> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_grade_work_reply>() {
			@Override
			public Adks_grade_work_reply mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_grade_work_reply gwr=new Adks_grade_work_reply();
				gwr.setGradeWorkId(rs.getInt("gradeWorkId"));
				gwr.setWorkTitle(rs.getString("workTitle"));
				gwr.setSubmitDate(rs.getDate("submitDate"));
				gwr.setWorkScore(rs.getInt("workScore"));
				return gwr;
			}});
		if(reslist == null || reslist.size()<=0){
			return null;
		}
		return reslist;
	}
	
	public Integer getGradeWorkCount(Integer gradeId) {
		String sql = "select count(*) as sum from adks_grade_work where gradeId="+gradeId;
        List<Map<String, Object>> reslist = mysqlClient.queryForList(sql, new Object[0]);
        if (reslist != null && reslist.size() > 0) {
        	String sum=reslist.get(0).get("sum")+"";
        	if(sum ==null || "".equals(sum) || "null".equals("sum")){
        		return 0;
        	}else{
        		return Integer.parseInt(sum);
        	}
        }
        return 0;
	} 
	
	public Integer getGradeWorkUserNum(Integer gradeId, Integer userId) {
		String sql = "select count(*) as sum from adks_grade_work_reply where studentId="+userId+" and workId in ("
				+ " select gradeWorkId from adks_grade_work where gradeId="+gradeId+" )"+" and workScore>=60";
        List<Map<String, Object>> reslist = mysqlClient.queryForList(sql, new Object[0]);
        if (reslist != null && reslist.size() > 0) {
        	String sum=reslist.get(0).get("sum")+"";
        	if(sum ==null || "".equals(sum) || "null".equals("sum")){
        		return 0;
        	}else{
        		return Integer.parseInt(sum);
        	}
        }
        return 0;
	}
	
}
