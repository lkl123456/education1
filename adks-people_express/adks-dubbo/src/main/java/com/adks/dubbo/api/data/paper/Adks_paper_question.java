package com.adks.dubbo.api.data.paper;

import java.io.Serializable;

/**
 * 试卷试题表（用于统一考试时使用）
 * @author Administrator
 *
 */
public class Adks_paper_question implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer paperQsId;//id
	private Integer paperId;//试卷id
	private Integer qsId;//试题id
	private Integer score;//分数
	private Integer questionType;//试题类型（1.单选；2.多选；3.判断；4.填空；5.问答）
	
	public Adks_paper_question(){
	}		
	
	public Adks_paper_question(Integer paperId, Integer qsId, Integer score, Integer questionType) {
		super();
		this.paperId = paperId;
		this.qsId = qsId;
		this.score = score;
		this.questionType = questionType;
	}
	
	public Integer getQuestionType() {
		return questionType;
	}
	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}
	public Integer getPaperQsId() {
		return paperQsId;
	}
	public void setPaperQsId(Integer paperQsId) {
		this.paperQsId = paperQsId;
	}
	public Integer getPaperId() {
		return paperId;
	}
	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}
	public Integer getQsId() {
		return qsId;
	}
	public void setQsId(Integer qsId) {
		this.qsId = qsId;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	
}
