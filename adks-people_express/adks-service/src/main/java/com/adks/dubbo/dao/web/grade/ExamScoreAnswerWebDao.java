package com.adks.dubbo.dao.web.grade;

import org.springframework.stereotype.Repository;

import com.adks.dbclient.dao.singaltanent.BaseDao;

@Repository
public class ExamScoreAnswerWebDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_exam_socre_answer";
	}

	/**
	 * 删除考试答案
	 * 
	 * @param examScoreId
	 */
	public void deleteExamScoreAnswer(int examScoreId) {
		String sql = "delete from adks_exam_socre_answer where examScoreId=" + examScoreId;
		mysqlClient.update(sql, new Object[] {});
	}

}
