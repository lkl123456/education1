package com.adks.dubbo.api.data.paper;

import java.io.Serializable;
import java.util.Date;

/**
 * 试卷信息表
 * 
 * @author Administrator
 *
 */
public class Adks_paper implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer paperId;// 试卷id
	private String paperName;// 试卷名称
	private Integer score;// 试卷分数
	private Integer qsNum;// 试卷总题数
	private Integer danxuanNum;// 单选题数
	private Integer danxuanScore;// 单选分值
	private Integer duoxuanNum;// 多选题数
	private Integer duoxuanScore;// 多选分值
	private Integer panduanNum;// 判断题数
	private Integer panduanScore;// 判断分值
	private Integer tiankongNum;// 填空题数
	private Integer tiankongScore;// 填空分值
	private Integer wendaNum;// 问答题数
	private Integer wendaScore;// 问答分值
	private String paperHtmlAdress;// 试卷html路径
	private Integer paperType;// 随机出题手动出题(1.随机出题；2.随机出题)
	private Integer orgId;// 学校ID
	private String orgName;// 学校名
	private Integer creatorId;// 创建人ID
	private String creatorName;// 创建人真实姓名
	private Date createTime;// 创建时间

	/**
	 * @return the paperHtmlAdress
	 */
	public String getPaperHtmlAdress() {
		return paperHtmlAdress;
	}

	/**
	 * @param paperHtmlAdress
	 *            the paperHtmlAdress to set
	 */
	public void setPaperHtmlAdress(String paperHtmlAdress) {
		this.paperHtmlAdress = paperHtmlAdress;
	}

	public Integer getPaperId() {
		return paperId;
	}

	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}

	public String getPaperName() {
		return paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getQsNum() {
		return qsNum;
	}

	public void setQsNum(Integer qsNum) {
		this.qsNum = qsNum;
	}

	public Integer getDanxuanNum() {
		return danxuanNum;
	}

	public void setDanxuanNum(Integer danxuanNum) {
		this.danxuanNum = danxuanNum;
	}

	public Integer getDanxuanScore() {
		return danxuanScore;
	}

	public void setDanxuanScore(Integer danxuanScore) {
		this.danxuanScore = danxuanScore;
	}

	public Integer getDuoxuanNum() {
		return duoxuanNum;
	}

	public void setDuoxuanNum(Integer duoxuanNum) {
		this.duoxuanNum = duoxuanNum;
	}

	public Integer getDuoxuanScore() {
		return duoxuanScore;
	}

	public void setDuoxuanScore(Integer duoxuanScore) {
		this.duoxuanScore = duoxuanScore;
	}

	public Integer getPanduanNum() {
		return panduanNum;
	}

	public void setPanduanNum(Integer panduanNum) {
		this.panduanNum = panduanNum;
	}

	public Integer getPanduanScore() {
		return panduanScore;
	}

	public void setPanduanScore(Integer panduanScore) {
		this.panduanScore = panduanScore;
	}

	public Integer getTiankongNum() {
		return tiankongNum;
	}

	public void setTiankongNum(Integer tiankongNum) {
		this.tiankongNum = tiankongNum;
	}

	public Integer getTiankongScore() {
		return tiankongScore;
	}

	public void setTiankongScore(Integer tiankongScore) {
		this.tiankongScore = tiankongScore;
	}

	public Integer getWendaNum() {
		return wendaNum;
	}

	public void setWendaNum(Integer wendaNum) {
		this.wendaNum = wendaNum;
	}

	public Integer getWendaScore() {
		return wendaScore;
	}

	public void setWendaScore(Integer wendaScore) {
		this.wendaScore = wendaScore;
	}

	public Integer getPaperType() {
		return paperType;
	}

	public void setPaperType(Integer paperType) {
		this.paperType = paperType;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
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

}
