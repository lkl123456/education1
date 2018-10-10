package com.adks.admin.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.adks.commons.oss.OSSUploadUtil;
import com.adks.commons.util.DateTimeUtil;
import com.adks.commons.util.PropertiesFactory;
import com.adks.dubbo.api.data.exam.Adks_exam;
import com.adks.dubbo.api.data.news.Adks_news;
import com.adks.dubbo.api.data.paper.Adks_paper;
import com.adks.dubbo.api.data.question.Adks_question;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerUtil {

	@SuppressWarnings("deprecation")
	public static synchronized String getHtmlPath(String newsTitle, String creatorName, Date createTime, String content,
			String newsHtmlAdress, HttpServletRequest request) throws IOException, TemplateException {
		System.out.println("HTML Start........");
		String path = request.getSession().getServletContext().getRealPath("/");
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(path + "WEB-INF/freemarker")); // 设置读取模板文件的目录
		Template t = cfg.getTemplate("freemarker.ftl"); // 读取文件名为Test.ftl的模板

		String newsHtmlPath = PropertiesFactory.getProperty("ossConfig.properties", "oss.NewsHtml_Path");
		String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
		Date date = new Date();
		String htmlName = date.getTime() + ".html";
		String htmlPath = path + "WEB-INF/freemarker/" + htmlName;

		Map root = new HashMap(); // 存储数据
		// 新闻标题
		root.put("newsTitle", newsTitle == null ? null : newsTitle);
		// 新闻内容
		if (content != null && !"".equals(content)) {
			root.put("newsContent", content);
		}
		// 创建者
		if (creatorName != null && !"".equals("creatorName")) {
			creatorName = "创建人：" + creatorName;
			root.put("creatorName", creatorName);
		}
		// 发布时间
		String dateStr = "";
		if (createTime != null) {
			dateStr = " 时间：" + DateTimeUtil.dateToString(createTime, "yyyy-MM-dd");
			root.put("createDate", dateStr);
		}
		Writer out = new OutputStreamWriter(new FileOutputStream(htmlPath), "utf-8"); // 输出流
		t.process(root, out); // 动态加载root中的数据到xxx.html。数据在模板中定义好了。
		// 上传生成的HTML页面到OSS
		String code = "";
		if (newsHtmlAdress == null || "".equals(newsHtmlAdress)) {
			String new_Path = OSSUploadUtil.uploadFileNewName(new File(htmlPath), "html", newsHtmlPath);
			System.out.println(new_Path);
			String[] paths = new_Path.split("/");
			code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
		} else {
			String new_Path = OSSUploadUtil.updateFile(new File(htmlPath), "html", ossResource + newsHtmlAdress);
			System.out.println(new_Path);
			String[] paths = new_Path.split("/");
			code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
		}
		// 删除生成的HTML页面
		File fi = new File(htmlPath);
		if (fi.isFile() && fi.exists()) {
			fi.delete();
		}
		System.out.println("HTML End........");
		return code;
	}

	@SuppressWarnings("deprecation")
	public static synchronized String getPaperHtmlPath(Adks_paper paper, String paperHtmlAdress,
			HttpServletRequest request, List<Adks_question> dQuestion, List<Adks_question> dxQuestion,
			List<Adks_question> pdQuestion, List<Adks_question> tkQuestion, List<Adks_question> wdQuestion)
			throws IOException, TemplateException {
		Map<String, Object> root = new HashMap<String, Object>(); // 存储数据
		String ip = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
		root.put("ip", ip);
		System.out.println("HTML Start........");
		String path = request.getSession().getServletContext().getRealPath("/");
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(path + "WEB-INF/freemarker")); // 设置读取模板文件的目录
		cfg.setDefaultEncoding("UTF-8");
		Template t = cfg.getTemplate("paperHtml.ftl"); // 读取文件名为Test.ftl的模板
		t.setEncoding("UTF-8");

		String newsHtmlPath = PropertiesFactory.getProperty("ossConfig.properties", "oss.NewsHtml_Path");
		String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
		Date date = new Date();
		String htmlName = date.getTime() + ".html";
		String htmlPath = path + "WEB-INF/freemarker/" + htmlName;

		if (paper != null) {
			// 试卷名称
			root.put("paperName", paper.getPaperName() == null ? null : paper.getPaperName());
			// root.put("userId", userId == null ? null : userId);
			// root.put("examDate", exam.getExamDate() == null ? null :
			// exam.getExamDate());
			// root.put("examId", exam.getExamId() == null ? null :
			// exam.getExamId());
			// root.put("gradeId", exam.getGradeId() == null ? null :
			// exam.getGradeId());
			root.put("paperId", paper.getPaperId() == null ? null : paper.getPaperId());
			root.put("qsNum", paper.getQsNum() == null ? null : paper.getQsNum());
			root.put("score", paper.getScore() == null ? null : paper.getScore());
		}
		// 单选Start
		StringBuffer dContent = new StringBuffer();
		StringBuffer dContentDA = new StringBuffer();
		if (dQuestion != null && dQuestion.size() > 0) {
			dContent.append("<div class=\"shiti_l\">");
			dContent.append("<h2>一、单选题（共" + dQuestion.size() + "道题，下列各题选项中，只有一个正确答案）</h2>");
			dContentDA.append("<dl><dt>一、单选题</dt><dd><ul>");
			for (int i = 0; i < dQuestion.size(); i++) {
				dContent.append("<dl class=\"danxuan\">");
				dContent.append("<dt><span>" + (i + 1) + ".</span>" + dQuestion.get(i).getQuestionName() + "（"
						+ dQuestion.get(i).getQuestionValue() + "分）</dt>");
				if (StringUtils.isNotEmpty(dQuestion.get(i).getOptionA())) {
					dContent.append("<dd>A." + dQuestion.get(i).getOptionA() + "</dd>");
				}
				if (StringUtils.isNotEmpty(dQuestion.get(i).getOptionB())) {
					dContent.append("<dd>B." + dQuestion.get(i).getOptionB() + "</dd>");
				}
				if (StringUtils.isNotEmpty(dQuestion.get(i).getOptionC())) {
					dContent.append("<dd>C." + dQuestion.get(i).getOptionC() + "</dd>");
				}
				if (StringUtils.isNotEmpty(dQuestion.get(i).getOptionD())) {
					dContent.append("<dd>D." + dQuestion.get(i).getOptionD() + "</dd>");
				}
				if (StringUtils.isNotEmpty(dQuestion.get(i).getOptionE())) {
					dContent.append("<dd>E." + dQuestion.get(i).getOptionE() + "</dd>");
				}
				if (StringUtils.isNotEmpty(dQuestion.get(i).getOptionF())) {
					dContent.append("<dd>F." + dQuestion.get(i).getOptionF() + "</dd>");
				}
				if (StringUtils.isNotEmpty(dQuestion.get(i).getOptionG())) {
					dContent.append("<dd>G." + dQuestion.get(i).getOptionG() + "</dd>");
				}
				if (StringUtils.isNotEmpty(dQuestion.get(i).getOptionH())) {
					dContent.append("<dd>H." + dQuestion.get(i).getOptionH() + "</dd>");
				}
				dContent.append("</dl>");
				dContent.append("<div class=\"xuxiang\">");
				if (StringUtils.isNotEmpty(dQuestion.get(i).getOptionA())) {
					dContent.append("<label><input type=\"radio\" name=\"danxuan" + dQuestion.get(i).getQuestionId()
							+ "\" value=\"A\" onclick=\"danxOnClick('" + dQuestion.get(i).getQuestionId()
							+ "')\" />A</label>");
				}
				if (StringUtils.isNotEmpty(dQuestion.get(i).getOptionB())) {
					dContent.append("<label><input type=\"radio\" name=\"danxuan" + dQuestion.get(i).getQuestionId()
							+ "\" value=\"B\" onclick=\"danxOnClick('" + dQuestion.get(i).getQuestionId()
							+ "')\" />B</label>");
				}
				if (StringUtils.isNotEmpty(dQuestion.get(i).getOptionC())) {
					dContent.append("<label><input type=\"radio\" name=\"danxuan" + dQuestion.get(i).getQuestionId()
							+ "\" value=\"C\" onclick=\"danxOnClick('" + dQuestion.get(i).getQuestionId()
							+ "')\" />C</label>");
				}
				if (StringUtils.isNotEmpty(dQuestion.get(i).getOptionD())) {
					dContent.append("<label><input type=\"radio\" name=\"danxuan" + dQuestion.get(i).getQuestionId()
							+ "\" value=\"D\" onclick=\"danxOnClick('" + dQuestion.get(i).getQuestionId()
							+ "')\" />D</label>");
				}
				if (StringUtils.isNotEmpty(dQuestion.get(i).getOptionE())) {
					dContent.append("<label><input type=\"radio\" name=\"danxuan" + dQuestion.get(i).getQuestionId()
							+ "\" value=\"E\" onclick=\"danxOnClick('" + dQuestion.get(i).getQuestionId()
							+ "')\" />E</label>");
				}
				if (StringUtils.isNotEmpty(dQuestion.get(i).getOptionF())) {
					dContent.append("<label><input type=\"radio\" name=\"danxuan" + dQuestion.get(i).getQuestionId()
							+ "\" value=\"F\" onclick=\"danxOnClick('" + dQuestion.get(i).getQuestionId()
							+ "')\" />F</label>");
				}
				if (StringUtils.isNotEmpty(dQuestion.get(i).getOptionG())) {
					dContent.append("<label><input type=\"radio\" name=\"danxuan" + dQuestion.get(i).getQuestionId()
							+ "\" value=\"G\" onclick=\"danxOnClick('" + dQuestion.get(i).getQuestionId()
							+ "')\" />G</label>");
				}
				if (StringUtils.isNotEmpty(dQuestion.get(i).getOptionH())) {
					dContent.append("<label><input type=\"radio\" name=\"danxuan" + dQuestion.get(i).getQuestionId()
							+ "\" value=\"H\" onclick=\"danxOnClick('" + dQuestion.get(i).getQuestionId()
							+ "')\" />H</label>");
				}
				dContent.append("</div>");
				dContentDA.append("<li id=\"click_li_" + dQuestion.get(i).getQuestionId() + "\">" + (i + 1) + "</li>");
			}
			dContent.append("</div>");
			dContentDA.append("</ul></dd></dl>");
		} else {
			dContentDA.append("<dl><dt>一、单选题（暂无）</dt></dl>");
		}
		root.put("dContent", dContent);
		root.put("dContentDA", dContentDA);
		// 单选End

		// 多选Start
		StringBuffer dxContent = new StringBuffer();
		StringBuffer dxContentDA = new StringBuffer();
		if (dxQuestion != null && dxQuestion.size() > 0) {
			dxContent.append("<div class=\"shiti_l shiti_2\">");
			dxContent.append("<h2>二、多选题（共" + dxQuestion.size() + "道题，下列各题选项中，可能有多个正确答案）</h2>");
			dxContentDA.append("<dl><dt>二、多选题</dt><dd><ul>");
			for (int i = 0; i < dxQuestion.size(); i++) {
				dxContent.append("<dl class=\"danxuan\">");
				dxContent.append("<dt><span>" + (i + 1) + ".</span>" + dxQuestion.get(i).getQuestionName() + "（"
						+ dxQuestion.get(i).getQuestionValue() + "分）</dt>");
				if (StringUtils.isNotEmpty(dxQuestion.get(i).getOptionA())) {
					dxContent.append("<dd>A." + dxQuestion.get(i).getOptionA() + "</dd>");
				}
				if (StringUtils.isNotEmpty(dxQuestion.get(i).getOptionB())) {
					dxContent.append("<dd>B." + dxQuestion.get(i).getOptionB() + "</dd>");
				}
				if (StringUtils.isNotEmpty(dxQuestion.get(i).getOptionC())) {
					dxContent.append("<dd>C." + dxQuestion.get(i).getOptionC() + "</dd>");
				}
				if (StringUtils.isNotEmpty(dxQuestion.get(i).getOptionD())) {
					dxContent.append("<dd>D." + dxQuestion.get(i).getOptionD() + "</dd>");
				}
				if (StringUtils.isNotEmpty(dxQuestion.get(i).getOptionE())) {
					dxContent.append("<dd>E." + dxQuestion.get(i).getOptionE() + "</dd>");
				}
				if (StringUtils.isNotEmpty(dxQuestion.get(i).getOptionF())) {
					dxContent.append("<dd>F." + dxQuestion.get(i).getOptionF() + "</dd>");
				}
				if (StringUtils.isNotEmpty(dxQuestion.get(i).getOptionG())) {
					dxContent.append("<dd>G." + dxQuestion.get(i).getOptionG() + "</dd>");
				}
				if (StringUtils.isNotEmpty(dxQuestion.get(i).getOptionH())) {
					dxContent.append("<dd>H." + dxQuestion.get(i).getOptionH() + "</dd>");
				}
				dxContent.append("</dl>");
				dxContent.append("<div class=\"xuxiang\">");
				if (StringUtils.isNotEmpty(dxQuestion.get(i).getOptionA())) {
					dxContent.append("<label><input type=\"checkbox\" name=\"duoxuan"
							+ dxQuestion.get(i).getQuestionId() + "\" value=\"A\" onclick=\"duoxOnClick(this.name,'"
							+ dxQuestion.get(i).getQuestionId() + "')\" />A</label>");

				}
				if (StringUtils.isNotEmpty(dQuestion.get(i).getOptionB())) {
					dxContent.append("<label><input type=\"checkbox\" name=\"duoxuan"
							+ dxQuestion.get(i).getQuestionId() + "\" value=\"B\" onclick=\"duoxOnClick(this.name,'"
							+ dxQuestion.get(i).getQuestionId() + "')\" />B</label>");
				}
				if (StringUtils.isNotEmpty(dQuestion.get(i).getOptionC())) {
					dxContent.append("<label><input type=\"checkbox\" name=\"duoxuan"
							+ dxQuestion.get(i).getQuestionId() + "\" value=\"C\" onclick=\"duoxOnClick(this.name,'"
							+ dxQuestion.get(i).getQuestionId() + "')\" />C</label>");
				}
				if (StringUtils.isNotEmpty(dQuestion.get(i).getOptionD())) {
					dxContent.append("<label><input type=\"checkbox\" name=\"duoxuan"
							+ dxQuestion.get(i).getQuestionId() + "\" value=\"D\" onclick=\"duoxOnClick(this.name,'"
							+ dxQuestion.get(i).getQuestionId() + "')\" />D</label>");
				}
				if (StringUtils.isNotEmpty(dQuestion.get(i).getOptionE())) {
					dxContent.append("<label><input type=\"checkbox\" name=\"duoxuan"
							+ dxQuestion.get(i).getQuestionId() + "\" value=\"E\" onclick=\"duoxOnClick(this.name,'"
							+ dxQuestion.get(i).getQuestionId() + "')\" />E</label>");
				}
				if (StringUtils.isNotEmpty(dQuestion.get(i).getOptionF())) {
					dxContent.append("<label><input type=\"checkbox\" name=\"duoxuan"
							+ dxQuestion.get(i).getQuestionId() + "\" value=\"F\" onclick=\"duoxOnClick(this.name,'"
							+ dxQuestion.get(i).getQuestionId() + "')\" />F</label>");
				}
				if (StringUtils.isNotEmpty(dQuestion.get(i).getOptionG())) {
					dxContent.append("<label><input type=\"checkbox\" name=\"duoxuan"
							+ dxQuestion.get(i).getQuestionId() + "\" value=\"G\" onclick=\"duoxOnClick(this.name,'"
							+ dxQuestion.get(i).getQuestionId() + "')\" />G</label>");
				}
				if (StringUtils.isNotEmpty(dQuestion.get(i).getOptionH())) {
					dxContent.append("<label><input type=\"checkbox\" name=\"duoxuan"
							+ dxQuestion.get(i).getQuestionId() + "\" value=\"H\" onclick=\"duoxOnClick(this.name,'"
							+ dxQuestion.get(i).getQuestionId() + "')\" />H</label>");
				}
				dxContent.append("</div>");
				dxContentDA
						.append("<li id=\"click_li_" + dxQuestion.get(i).getQuestionId() + "\">" + (i + 1) + "</li>");
			}
			dxContent.append("</div>");
			dxContentDA.append("</ul></dd></dl>");
		} else {
			dxContentDA.append("<dl><dt>二、多选题（暂无）</dt></dl>");
		}
		root.put("dxContent", dxContent);
		root.put("dxContentDA", dxContentDA);
		// 多选End

		// 判断Start
		StringBuffer pdContent = new StringBuffer();
		StringBuffer pdContentDA = new StringBuffer();
		if (pdQuestion != null && pdQuestion.size() > 0) {
			pdContent.append("<div class=\"shiti_l\">");
			pdContent.append("<h2>三、判断题（共" + pdQuestion.size() + "道题，请判断下列各题是否正确）</h2>");
			pdContentDA.append("<dl><dt>三、判断题</dt><dd><ul>");
			for (int i = 0; i < pdQuestion.size(); i++) {
				pdContent.append("<div class=\"shiti_l\"><dl class=\"danxuan\">");
				pdContent.append("<dt><span>" + (i + 1) + ".</span>" + pdQuestion.get(i).getQuestionName() + "（"
						+ pdQuestion.get(i).getQuestionValue() + "分）</dt>");
				pdContent.append("</dt></dl>");
				pdContent.append("<div class=\"xuxiang\">");
				pdContent.append(
						"<label><input type=\"radio\" value=\"1\" name=\"panduan" + pdQuestion.get(i).getQuestionId()
								+ "\" onclick=\"danxOnClick('" + pdQuestion.get(i).getQuestionId()
								+ "')\" />A.对</label><label><input type=\"radio\" value=\"0\" name=\"panduan"
								+ pdQuestion.get(i).getQuestionId() + "\" onclick=\"danxOnClick('"
								+ pdQuestion.get(i).getQuestionId() + "')\" />B.错</label>");
				pdContent.append("</div></div>");
				pdContentDA
						.append("<li id=\"click_li_" + pdQuestion.get(i).getQuestionId() + "\">" + (i + 1) + "</li>");
			}
			pdContent.append("</div>");
			pdContentDA.append("</ul></dd></dl>");
		} else {
			pdContentDA.append("<dl><dt>三、判断题（暂无）</dt></dl>");
		}
		root.put("pdContent", pdContent);
		root.put("pdContentDA", pdContentDA);
		// 判断End

		// 填空Start
		StringBuffer tkContent = new StringBuffer();
		StringBuffer tkContentDA = new StringBuffer();
		if (tkQuestion != null && tkQuestion.size() > 0) {
			tkContent.append("<div class=\"shiti_l shiti_3\">");
			tkContent.append("<h2>四、填空题（共" + tkQuestion.size() + "道题，请在每题下面的输入框中作答，每个空格的答案用“|”隔开）</h2>");
			tkContentDA.append("<dl><dt>四、填空题</dt><dd><ul>");
			for (int i = 0; i < tkQuestion.size(); i++) {
				tkContent.append("<div class=\"shiti_l shiti_3\"><dl class=\"danxuan\">");
				tkContent.append("<dt><span>" + (i + 1) + ".</span>" + tkQuestion.get(i).getQuestionName() + "（"
						+ tkQuestion.get(i).getQuestionValue() + "分）</dt>");
				tkContent.append("</dt></dl>");
				tkContent.append("<div class=\"tiankong\">");
				tkContent
						.append("<input style=\"vertical-align: top; margin-top: 0px\" type=\"text\" maxlength=\"150\" name=\"tiankong"
								+ tkQuestion.get(i).getQuestionId() + "\" onchange=\"tkOnCheck(this.value,'"
								+ tkQuestion.get(i).getQuestionId() + "')\" onkeyup=\"tkOnCheck(this.value,'"
								+ tkQuestion.get(i).getQuestionId() + "')\" />");
				tkContent.append("</div></div>");
				tkContentDA
						.append("<li id=\"click_li_" + tkQuestion.get(i).getQuestionId() + "\">" + (i + 1) + "</li>");
			}
			tkContent.append("</div>");
			tkContentDA.append("</ul></dd></dl>");
		} else {
			pdContentDA.append("<dl><dt>四、填空题（暂无）</dt></dl>");
		}
		root.put("tkContent", tkContent);
		root.put("tkContentDA", tkContentDA);
		// 填空End

		// 问答Start
		StringBuffer wdContent = new StringBuffer();
		StringBuffer wdContentDA = new StringBuffer();
		if (wdQuestion != null && wdQuestion.size() > 0) {
			wdContent.append("<div class=\"shiti_l shiti_3\">");
			wdContent.append("<h2>五、问答题（共" + wdQuestion.size() + "道题，请在每题下面的输入框中作答）</h2>");
			wdContentDA.append("<dl><dt>五、问答题</dt><dd><ul>");
			for (int i = 0; i < wdQuestion.size(); i++) {
				wdContent.append("<div class=\"shiti_l shiti_3\"><dl class=\"danxuan\">");
				wdContent.append("<dt><span>" + (i + 1) + ".</span>" + wdQuestion.get(i).getQuestionName() + "（"
						+ wdQuestion.get(i).getQuestionValue() + "分）</dt>");
				wdContent.append("</dt></dl>");
				wdContent.append("<div class=\"wenda\">");
				wdContent.append("<textarea rows=\"5\" cols=\"70\" name=\"wenda" + wdQuestion.get(i).getQuestionId()
						+ "\" id=\"wenda" + wdQuestion.get(i).getQuestionId() + "\"onchange=\"wdOnCheck(this.id,200,"
						+ wdQuestion.get(i).getQuestionId() + ")\"onkeyup=\"wdOnCheck(this.id,200,"
						+ wdQuestion.get(i).getQuestionId()
						+ ")\"></textarea><p style=\"font-size: 12px;\">当前字数：0，字数限制：200</p>");
				wdContent.append("</div></div>");
				wdContentDA
						.append("<li id=\"click_li_" + wdQuestion.get(i).getQuestionId() + "\">" + (i + 1) + "</li>");
			}
			wdContent.append("</div>");
			wdContentDA.append("</ul></dd></dl>");
		} else {
			wdContentDA.append("<dl><dt>五、问答题（暂无）</dt></dl>");
		}
		root.put("wdContent", wdContent);
		root.put("wdContentDA", wdContentDA);
		// 问答End

		Writer out = new OutputStreamWriter(new FileOutputStream(htmlPath), "UTF-8"); // 输出流
		t.process(root, out); // 动态加载root中的数据到xxx.html。数据在模板中定义好了。
		// 上传生成的HTML页面到OSS
		String code = "";
		if (paperHtmlAdress == null || "".equals(paperHtmlAdress)) {
			String paper_Path = OSSUploadUtil.uploadFileNewName(new File(htmlPath), "html", newsHtmlPath);
			System.out.println(paper_Path);
			String[] paths = paper_Path.split("/");
			code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
		} else {
			String paper_Path = OSSUploadUtil.updateFile(new File(htmlPath), "html", ossResource + paperHtmlAdress);
			System.out.println(paper_Path);
			String[] paths = paper_Path.split("/");
			code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
		}
		// 删除生成的HTML页面
		File fi = new File(htmlPath);
		if (fi.isFile() && fi.exists()) {
			fi.delete();
		}
		System.out.println("HTML End........");
		return code;
	}
}
