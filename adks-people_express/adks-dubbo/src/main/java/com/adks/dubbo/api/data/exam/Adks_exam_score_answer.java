package com.adks.dubbo.api.data.exam;

import java.io.Serializable;

public class Adks_exam_score_answer implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer esaId;
	private String userAnswer;
	private Integer userScore;
	private Integer examScoreId;
	private Integer qsId;

	/**
	 * @return the esaId
	 */
	public Integer getEsaId() {
		return esaId;
	}

	/**
	 * @param esaId
	 *            the esaId to set
	 */
	public void setEsaId(Integer esaId) {
		this.esaId = esaId;
	}

	/**
	 * @return the userAnswer
	 */
	public String getUserAnswer() {
		return userAnswer;
	}

	/**
	 * @param userAnswer
	 *            the userAnswer to set
	 */
	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}

	/**
	 * @return the userScore
	 */
	public Integer getUserScore() {
		return userScore;
	}

	/**
	 * @param userScore
	 *            the userScore to set
	 */
	public void setUserScore(Integer userScore) {
		this.userScore = userScore;
	}

	/**
	 * @return the examScoreId
	 */
	public Integer getExamScoreId() {
		return examScoreId;
	}

	/**
	 * @param examScoreId
	 *            the examScoreId to set
	 */
	public void setExamScoreId(Integer examScoreId) {
		this.examScoreId = examScoreId;
	}

	/**
	 * @return the qsId
	 */
	public Integer getQsId() {
		return qsId;
	}

	/**
	 * @param qsId
	 *            the qsId to set
	 */
	public void setQsId(Integer qsId) {
		this.qsId = qsId;
	}

}
