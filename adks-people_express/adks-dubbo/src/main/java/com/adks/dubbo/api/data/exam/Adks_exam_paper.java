package com.adks.dubbo.api.data.exam;

import java.io.Serializable;

/**
 * 考试试卷关联表
 * 
 * @author Administrator
 *
 */
public class Adks_exam_paper implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;// id
	private Integer paperId;// 试卷ID
	private Integer examId;// 考试ID
	private String paperName;// 试卷名称
	private String paperHtmlAdress;// 试卷html路径

	public Adks_exam_paper() {
	}

	public Adks_exam_paper(Integer paperId, Integer examId, String paperName, String paperHtmlAdress) {
		super();
		this.paperId = paperId;
		this.examId = examId;
		this.paperName = paperName;
		this.paperHtmlAdress = paperHtmlAdress;
	}

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPaperId() {
		return paperId;
	}

	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}

	public Integer getExamId() {
		return examId;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	public String getPaperName() {
		return paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

}
