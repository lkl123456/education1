package com.adks.dubbo.dao.app.grade;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.adks.dbclient.dao.singaltanent.BaseDao;

@Repository
public class GradeAppDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_grade";
	}

	/**
	 * 根据gradeId获取班级信息
	 * 
	 * @param gradeId
	 * @return
	 */
	public Map<String, Object> getById(int id) {
		Object[] obj = new Object[] { id };
		String sql = "select "
				+ "gradeName as name,gradeImg as logoPath,userNum,startDate,endDate,gradeTarget as target,gradeDesc,graduationDesc,headTeacherId,headTeacherName,requiredPeriod,optionalPeriod "
				+ "from adks_grade where gradeId=?";
		return mysqlClient.queryForMap(sql, obj);
	}
}
