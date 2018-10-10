package com.adks.dubbo.dao.web.grade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.adks.commons.util.DateUtils;
import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.exam.Adks_exam;
import com.adks.dubbo.api.data.exam.Adks_exam_score;
import com.adks.dubbo.api.data.grade.Adks_grade_exam;
import com.adks.dubbo.api.data.paper.Adks_paper;
import com.adks.dubbo.api.data.paper.Adks_paper_question;
import com.adks.dubbo.api.data.question.Adks_question;
import com.adks.dubbo.commons.Page;

@Repository
public class GradeExamWebDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_grade_exam";
	}

	public List<Adks_exam_score> getExamScoreList(Map map) {
		String sql = "select examScoreId,useTime,examCounts,submitDate,isCorrent,score from adks_exam_score "
				+ "where gradeId=" + map.get("gradeId") + " and examId=" + map.get("examId") + " and userId="
				+ map.get("userId");
		List<Adks_exam_score> gradeList = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_exam_score>() {
			@Override
			public Adks_exam_score mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_exam_score examScore = new Adks_exam_score();
				examScore.setExamScoreId(rs.getInt("examScoreId"));
				examScore.setUseTime(rs.getInt("useTime"));
				examScore.setExamCounts(rs.getInt("examCounts"));
				examScore.setSubmitDate(DateUtils.getStr2LDate(rs.getString("submitDate")));
				examScore.setIsCorrent(rs.getInt("isCorrent"));
				examScore.setScore(rs.getInt("score"));
				return examScore;
			}
		});
		if (gradeList != null && gradeList.size() > 0) {
			return gradeList;
		}
		return null;
	}

	public Adks_exam getExamById(Integer gradeId, Integer examId) {
		String sql = "select e.examId,e.examName,e.scoreSum,e.examDate,e.passScore,e.examTimes,ge.gradeId,ge.gradeName "
				+ "from adks_exam e , adks_grade_exam ge where e.examId=ge.examId and ge.gradeId=" + gradeId
				+ " and e.examId=" + examId;
		List<Adks_exam> gradeList = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_exam>() {
			@Override
			public Adks_exam mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_exam exam = new Adks_exam();
				exam.setExamId(rs.getInt("examId"));
				exam.setExamName(rs.getString("examName"));
				exam.setScoreSum(rs.getInt("scoreSum"));
				exam.setExamDate(rs.getInt("examDate"));
				exam.setPassScore(rs.getInt("passScore"));
				exam.setExamTimes(rs.getInt("examTimes"));
				exam.setGradeId(rs.getInt("gradeId"));
				exam.setGradeName(rs.getString("gradeName"));
				return exam;
			}
		});
		if (gradeList != null && gradeList.size() > 0) {
			return gradeList.get(0);
		}
		return null;
	}

	// 得到用户考试的剩余次数
	public Integer getUserExamTimes(Map map) {
		String sql = "select count(1) from adks_exam_score t1 where userId=" + map.get("userId") + " and t1.gradeId="
				+ map.get("gradeId") + " and t1.examId=" + map.get("examId");
		Integer totalcount = mysqlClient.queryforInt(sql, new Object[0]);
		return totalcount;
	}

	public Integer getGradeExamCount(Integer gradeId) {
		String sql = "select count(*) as sum from adks_grade_exam where gradeId=" + gradeId;
		List<Map<String, Object>> reslist = mysqlClient.queryForList(sql, new Object[0]);
		if (reslist != null && reslist.size() > 0) {
			String sum = reslist.get(0).get("sum") + "";
			if (sum == null || "".equals(sum) || "null".equals("sum")) {
				return 0;
			} else {
				return Integer.parseInt(sum);
			}
		}
		return 0;
	}

	public Integer getGradeExamUserNum(Integer gradeId, Integer userId) {
		String sql = "select count(*) as sum from adks_exam_score where gradeId=" + gradeId + " and userId=" + userId
				+ " and score>=60";
		List<Map<String, Object>> reslist = mysqlClient.queryForList(sql, new Object[0]);
		if (reslist != null && reslist.size() > 0) {
			String sum = reslist.get(0).get("sum") + "";
			if (sum == null || "".equals(sum) || "null".equals("sum")) {
				return 0;
			} else {
				return Integer.parseInt(sum);
			}
		}
		return 0;
	}

	// 获取班级考试信息的分页
	public Page<List<Adks_grade_exam>> gradeExamList(Page<List<Adks_grade_exam>> page) {
		Integer offset = null; // limit 起始位置
		if (page.getCurrentPage() <= 1) {
			offset = 0;
			page.setCurrentPage(1);
		} else {
			offset = (page.getCurrentPage() - 1) * page.getPageSize();
		}
		Map map = page.getMap();
		StringBuffer sqlbuffer = new StringBuffer(
				"select t2.examId,t2.examName,t2.startDate,t2.endDate,t2.examDate,t2.scoreSum,t2.passScore,t2.examTimes  "
						+ "from adks_grade_exam t1 , adks_exam t2 where t1.examId = t2.examId and t1.gradeId="
						+ map.get("gradeId"));
		StringBuffer countsql = new StringBuffer("select count(1)  "
				+ "from adks_grade_exam t1 , adks_exam t2 where t1.examId = t2.examId and t1.gradeId="
				+ map.get("gradeId"));

		sqlbuffer.append(" order by t1.gradeExamId desc ");
		// 分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();

		List<Adks_grade_exam> gradeList = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_grade_exam>() {
			@Override
			public Adks_grade_exam mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_grade_exam gradeExam = new Adks_grade_exam();
				gradeExam.setExamId(rs.getInt("examId"));
				gradeExam.setExamName(rs.getString("examName"));
				gradeExam.setExamDate(rs.getInt("examDate"));
				gradeExam.setStartDate(rs.getDate("startDate"));
				gradeExam.setEndDate(rs.getDate("endDate"));
				gradeExam.setScoreSum(rs.getInt("scoreSum"));
				gradeExam.setExamTimes(rs.getInt("examTimes"));
				gradeExam.setPassScore(rs.getInt("passScore"));
				return gradeExam;
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
	 * 获取班级考试所添加的全部试卷信息
	 * 
	 * @param examId
	 * @return
	 */
	public List<Map<String, Object>> getExamPaperList(int examId) {
		StringBuffer sqlbuffer = new StringBuffer(
				"SELECT ap.paperName,ifnull(ap.score,0)as score,ifnull(ap.qsNum,0) as qsNum,ifnull(ap.danxuanNum,0) as danxuanNum,ifnull(ap.danxuanScore,0) as danxuanScore,ifnull(ap.duoxuanNum,0) as duoxuanNum,ifnull(ap.duoxuanScore,0) as duoxuanScore,ifnull(ap.panduanNum,0) as panduanNum,ifnull(ap.panduanScore,0) as panduanScore,ifnull(ap.tiankongNum,0) as tiankongNum,ifnull(ap.tiankongScore,0) as tiankongScore,ifnull(ap.wendaNum,0) as wendaNum,ifnull(ap.wendaScore,0) as wendaScore,ap.paperId,ep.examId,ep.id "
						+ "FROM adks_paper ap,adks_exam_paper ep WHERE ap.paperId = ep.paperId ");
		if (examId != 0) {
			sqlbuffer.append(" and ep.examId = " + examId);
		}
		String sql = sqlbuffer.toString();
		return mysqlClient.queryForList(sql, new Object[0]);
	};

	/**
	 * 获取试卷下全部题目
	 * 
	 * @param paperId
	 * @return
	 */
	public List<Adks_paper_question> getPaperQuestionListByPaperId(Integer paperId) {
		String sql = "select paperQsId,paperId,qsId,score from adks_paper_question where paperId=" + paperId;
		List<Adks_paper_question> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_paper_question>() {
			@Override
			public Adks_paper_question mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_paper_question paper_question = new Adks_paper_question();
				paper_question.setPaperQsId(rs.getInt("paperQsId"));
				paper_question.setPaperId(rs.getInt("paperId"));
				paper_question.setQsId(rs.getInt("qsId"));
				paper_question.setScore(rs.getInt("score"));
				return paper_question;
			}
		});
		return reslist;
	}

	/**
	 * 获取试卷信息
	 * 
	 * @param id
	 * @return
	 */
	public Adks_paper getPaperById(Integer id) {
		String sql = "select * from adks_paper where paperId=" + id;
		List<Adks_paper> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_paper>() {
			@Override
			public Adks_paper mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_paper paper = new Adks_paper();
				paper.setPaperId(rs.getInt("paperId"));
				paper.setPaperName(rs.getString("paperName"));
				paper.setScore(rs.getInt("score"));
				paper.setQsNum(rs.getInt("qsNum"));
				paper.setDanxuanNum(rs.getInt("danxuanNum"));
				paper.setDanxuanScore(rs.getInt("danxuanScore"));
				paper.setDuoxuanNum(rs.getInt("duoxuanNum"));
				paper.setDuoxuanScore(rs.getInt("duoxuanScore"));
				paper.setPanduanNum(rs.getInt("panduanNum"));
				paper.setPanduanScore(rs.getInt("panduanScore"));
				paper.setTiankongNum(rs.getInt("tiankongNum"));
				paper.setTiankongScore(rs.getInt("tiankongScore"));
				paper.setPaperHtmlAdress(rs.getString("paperHtmlAdress"));
				paper.setWendaNum(rs.getInt("wendaNum"));
				paper.setWendaScore(rs.getInt("wendaScore"));
				paper.setPaperType(rs.getInt("paperType"));
				paper.setOrgId(rs.getInt("orgId"));
				paper.setOrgName(rs.getString("orgName"));
				paper.setCreatorId(rs.getInt("creatorId"));
				paper.setCreatorName(rs.getString("creatorName"));
				paper.setCreateTime(rs.getDate("createTime"));
				return paper;
			}
		});
		if (reslist == null || reslist.size() <= 0)
			return null;
		return reslist.get(0);
	}

	public Adks_question getQuestionById(Integer id) {
		String sql = new String("select * FROM adks_question where questionId = " + id);
		List<Adks_question> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_question>() {
			@Override
			public Adks_question mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_question question = new Adks_question();
				question.setQuestionId(rs.getInt("questionId"));
				question.setQuestionName(rs.getString("questionName"));
				question.setQuestionType(rs.getInt("questionType"));
				question.setQuestionValue(rs.getInt("questionValue"));
				question.setOptionA(rs.getString("optionA"));
				question.setOptionB(rs.getString("optionB"));
				question.setOptionC(rs.getString("optionC"));
				question.setOptionD(rs.getString("optionD"));
				question.setOptionE(rs.getString("optionE"));
				question.setOptionF(rs.getString("optionF"));
				question.setOptionG(rs.getString("optionG"));
				question.setOptionH(rs.getString("optionH"));
				question.setAnwsers(rs.getString("anwsers"));
				question.setQtSortId(rs.getInt("qtSortId"));
				question.setQsSortName(rs.getString("qsSortName"));
				question.setQsSortCode(rs.getString("qsSortCode"));
				question.setCourseId(rs.getInt("courseId"));
				question.setCourseName(rs.getString("courseName"));
				question.setOrgId(rs.getInt("orgId"));
				question.setCreatorId(rs.getInt("creatorId"));
				question.setCreatorName(rs.getString("creatorName"));
				question.setCreateTime(rs.getDate("createTime"));
				return question;
			}
		});
		if (reslist == null || reslist.size() <= 0)
			return null;
		return reslist.get(0);
	}
}
