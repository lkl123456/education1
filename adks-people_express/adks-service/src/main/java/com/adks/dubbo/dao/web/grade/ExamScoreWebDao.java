package com.adks.dubbo.dao.web.grade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.exam.Adks_exam_score;
import com.adks.dubbo.api.data.grade.Adks_grade_course;

@Repository
public class ExamScoreWebDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_exam_score";
	}

	/**
	 * 删除考试成绩
	 * 
	 * @param examScoreId
	 */
	public void deleteExamScore(int examScoreId) {
		String sql = "delete from adks_exam_score where examScoreId=" + examScoreId;
		mysqlClient.update(sql, new Object[] {});
	}
	public List<Adks_exam_score> getExamScoreByCon(Map map) {
		String sql = "select ae.examDate,aes.score,aes.useTime,aes.submitDate,aes.examName  from adks_exam ae,adks_exam_score aes "
				+ "where ae.examId=aes.examId and aes.gradeId="+map.get("gradeId")+" and aes.userId=" +map.get("userId");
		List<Adks_exam_score> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_exam_score>() {
			@Override
			public Adks_exam_score mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_exam_score examScore=new Adks_exam_score();
				examScore.setExamDate(rs.getInt("examDate"));
				examScore.setScore(rs.getInt("score"));
				examScore.setSubmitDate(rs.getDate("submitDate"));
				examScore.setExamName(rs.getString("examName"));
				examScore.setUseTime(rs.getInt("useTime"));
				return examScore;
			}});
		if(reslist == null || reslist.size()<=0){
			return null;
		}
		return reslist;
	}
}
