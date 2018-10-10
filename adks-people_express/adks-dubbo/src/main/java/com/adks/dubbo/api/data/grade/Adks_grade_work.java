package com.adks.dubbo.api.data.grade;

import java.io.Serializable;
import java.util.Date;

/**
* <p>Title: 班级作业  BEAN</p>
* @author: lkl 
* @date: 2017-03-07 
 */
public class Adks_grade_work implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer gradeWorkId;//id
	private String workTitle;//标题
	private String workContent;//作业内容
	private Integer maxSize;//论文字数最多限制
	private Integer leastSize;//论文字数最少限制
	private Date startDate;//开始时间
	private Date endDate;//结束时间
	private Integer releaseState;//1.已发布；2.未发布；	发布状态
	private Integer allowFile;//1.允许；2.不允许；	是否允许上传附件
	private String filePath;//附件地址（老师发布）
	private Integer gradeId;//班级id
	private String gradeName;//班级名称
	private Integer creatorId;//创建人ID
	private String creatorName;//创建人真实姓名
	private Date createTime;//创建时间
	
	//以下仅仅是显示使用
	private String createTime_str;  //创建时间字符串
	private String startDate_str;   //开始时间字符串
	private String endDate_str;     //结束时间字符串
	private Integer isOver;
	private Integer isEnd;
	private Integer workScore;//批改成绩
	private Integer gradeWorkReplyId;//班级作业ID
	private String submitFilePath;//附件路径（学员）
	private byte[] workAnswer;//答案
	private String correntName;
	private Integer correctId;//评改人的id
	
	
	 
	public Integer getCorrectId() {
		return correctId;
	}
	public void setCorrectId(Integer correctId) {
		this.correctId = correctId;
	}
	public String getCorrentName() {
		return correntName;
	}
	public void setCorrentName(String correntName) {
		this.correntName = correntName;
	}
	public Integer getGradeWorkReplyId() {
		return gradeWorkReplyId;
	}
	public void setGradeWorkReplyId(Integer gradeWorkReplyId) {
		this.gradeWorkReplyId = gradeWorkReplyId;
	}
	public String getSubmitFilePath() {
		return submitFilePath;
	}
	public void setSubmitFilePath(String submitFilePath) {
		this.submitFilePath = submitFilePath;
	}
	public byte[] getWorkAnswer() {
		return workAnswer;
	}
	public void setWorkAnswer(byte[] workAnswer) {
		this.workAnswer = workAnswer;
	}
	public Integer getWorkScore() {
		return workScore;
	}
	public void setWorkScore(Integer workScore) {
		this.workScore = workScore;
	}
	public Integer getIsOver() {
		return isOver;
	}
	public void setIsOver(Integer isOver) {
		this.isOver = isOver;
	}
	public Integer getIsEnd() {
		return isEnd;
	}
	public void setIsEnd(Integer isEnd) {
		this.isEnd = isEnd;
	}
	public Integer getGradeWorkId() {
		return gradeWorkId;
	}
	public void setGradeWorkId(Integer gradeWorkId) {
		this.gradeWorkId = gradeWorkId;
	}
	public String getWorkTitle() {
		return workTitle;
	}
	public void setWorkTitle(String workTitle) {
		this.workTitle = workTitle;
	}
	public String getWorkContent() {
		return workContent;
	}
	public void setWorkContent(String workContent) {
		this.workContent = workContent;
	}
	public Integer getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}
	public Integer getLeastSize() {
		return leastSize;
	}
	public void setLeastSize(Integer leastSize) {
		this.leastSize = leastSize;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getReleaseState() {
		return releaseState;
	}
	public void setReleaseState(Integer releaseState) {
		this.releaseState = releaseState;
	}
	public Integer getAllowFile() {
		return allowFile;
	}
	public void setAllowFile(Integer allowFile) {
		this.allowFile = allowFile;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Integer getGradeId() {
		return gradeId;
	}
	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public Integer getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateTime_str() {
		return createTime_str;
	}
	public void setCreateTime_str(String createTime_str) {
		this.createTime_str = createTime_str;
	}
	public String getStartDate_str() {
		return startDate_str;
	}
	public void setStartDate_str(String startDate_str) {
		this.startDate_str = startDate_str;
	}
	public String getEndDate_str() {
		return endDate_str;
	}
	public void setEndDate_str(String endDate_str) {
		this.endDate_str = endDate_str;
	}
	
	
}
