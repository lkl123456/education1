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
import com.adks.dubbo.api.data.grade.Adks_grade_exam;
import com.adks.dubbo.api.data.grade.Adks_grade_user;
import com.adks.dubbo.api.data.grade.Adks_grade_work;
import com.adks.dubbo.api.data.news.Adks_news;
import com.adks.dubbo.commons.Page;

@Repository
public class GradeWorkReplyWebDao extends BaseDao {

	
	@Override
	protected String getTableName() {
		return "adks_grade_work_reply";
	}
	
}
