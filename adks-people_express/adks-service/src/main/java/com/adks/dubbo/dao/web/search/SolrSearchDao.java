package com.adks.dubbo.dao.web.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.request.FieldAnalysisRequest;
import org.apache.solr.client.solrj.response.AnalysisResponseBase.AnalysisPhase;
import org.apache.solr.client.solrj.response.AnalysisResponseBase.TokenInfo;
import org.apache.solr.client.solrj.response.FieldAnalysisResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adks.dubbo.api.data.author.Adks_author;
import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.api.data.grade.Adks_grade;
import com.adks.dubbo.api.data.news.Adks_news;
import com.adks.dubbo.commons.SearchResult;

/**
 * solr的查询方法类 <b>Application name:</b>CSTP维护<br>
 * <b>Application describing:</b> <br>
 * <b>Copyright:</b>Copyright &copy; 2017 CSTP部版权所有。<br>
 * <b>Company:</b>CSTP<br>
 * <b>Date:</b>2017年5月11日<br>
 * 
 * @author Author
 * @version $Revision$
 */
@Component
public class SolrSearchDao {
	@Autowired
	private SolrServer solrServer;

	public SearchResult search(SolrQuery query) {
		// 返回值对象
		SearchResult result = new SearchResult();
		// 根据查询条件查询索引库
		try {

			QueryResponse queryResponse = solrServer.query(query);
			// 取查询结果
			SolrDocumentList solrDocumentList = queryResponse.getResults();
			// 获取响应时间
			result.setResponseTime(queryResponse.getQTime());
			// 取查询结果总数量
			result.setRecordCount(solrDocumentList.getNumFound());
			// 取高亮显示
			Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();

			// 取商品列表
			List<Adks_course> courseList = new ArrayList<>();
			// 新闻列表
			List<Adks_news> newsList = new ArrayList<>();
			// 专题列表
			List<Adks_grade> gradeList = new ArrayList<>();
			// 讲师列表
			List<Adks_author> authorList = new ArrayList<>();
			Adks_course course = null;
			Adks_news news = null;
			Adks_grade grade = null;
			Adks_author author = null;
			for (SolrDocument solrDocument : solrDocumentList) {
				course = new Adks_course();
				news = new Adks_news();
				grade = new Adks_grade();
				author = new Adks_author();

				String type = (String) solrDocument.get("id");
				type = type.split("_")[0] + "_";
				if (type.startsWith("news")) {
					Map<String, List<String>> map = highlighting.get(type + solrDocument.get("newsId"));
					news.setNewsId((int) solrDocument.get("newsId"));
					news.setOrgId((int) solrDocument.get("orgId"));

					if (map != null) {
						List<String> list = map.get("newsTitle");
						if (list != null && list.size() > 0) {
							news.setNewsTitle(list.get(0));
						} else {
							news.setNewsTitle((String) solrDocument.get("newsTitle"));
						}
					} else
						news.setNewsTitle((String) solrDocument.get("newsTitle"));

					news.setNewsFocusPic((String) solrDocument.get("newsFocusPic"));
					news.setNewsHtmlAdress((String) solrDocument.get("newsHtmlAdress"));
					// highlighting.get(solrDocument.get("id")).get("newsSortName");
					news.setNewsSortName((String) solrDocument.get("newsSortName"));
					news.setCreatorName((String) solrDocument.get("creatorName"));
					news.setCreateTime((Date) solrDocument.get("createTime"));
					if (map != null) {
						List<String> list = map.get("newsContent");
						if (list != null && list.size() > 0) {
							news.setContent(list.get(0));
						} else {
							news.setContent((String) solrDocument.get("newsContent"));
						}
					} else
						news.setContent((String) solrDocument.get("newsContent"));
					news.setNewsSortType((int) solrDocument.get("newsSortType"));
					news.setNewsType((int) solrDocument.get("newsType"));
					news.setNewsLink((String) solrDocument.get("newsLink"));
					news.setGradeId((int) solrDocument.get("gradeId"));
					news.setCreatorId((int) solrDocument.get("creatorId"));
					news.setNewsSortId((int) solrDocument.get("newsSortId"));
					newsList.add(news);
				}
				if (type.startsWith("course")) {
					Map<String, List<String>> map = highlighting.get(type + solrDocument.get("courseId"));
					course.setCourseId((int) solrDocument.get("courseId"));
					course.setOrgId((int) solrDocument.get("orgId"));
					course.setOrgName((String) solrDocument.get("orgName"));
					if (map != null) {
						List<String> list = map.get("courseName");
						if (list != null && list.size() > 0)
							course.setCourseName(list.get(0));
						else
							course.setCourseName((String) solrDocument.get("courseName"));
					} else
						course.setCourseName((String) solrDocument.get("courseName"));
					course.setCoursePic((String) solrDocument.get("coursePic"));
					course.setCreateTime((Date) solrDocument.get("createtime"));

					if (map != null) {
						List<String> list = map.get("courseDes");
						if (list != null && list.size() > 0)
							course.setCourseDes(list.get(0));
						else
							course.setCourseDes((String) solrDocument.get("courseDes"));
					} else
						course.setCourseDes((String) solrDocument.get("courseDes"));
					course.setCourseCode((String) solrDocument.get("courseCode"));
					course.setCourseType((int) solrDocument.get("courseType"));
					course.setCourseSortId((int) solrDocument.get("courseSortId"));
					course.setCourseSortName((String) solrDocument.get("courseSortName"));
					course.setCourseSortCode((String) solrDocument.get("courseSortCode"));
					course.setCreatorId((int) solrDocument.get("creatorId"));
					course.setCreatorName((String) solrDocument.get("creatorName"));
					course.setAuthorId((int) solrDocument.get("authorId"));
					if (map != null) {
						List<String> authorName = map.get("authorName");
						if (authorName != null && authorName.size() > 0) {
							course.setAuthorName(authorName.get(0));
						} else
							course.setAuthorName((String) solrDocument.get("authorName"));
					} else
						course.setAuthorName((String) solrDocument.get("authorName"));

					courseList.add(course);
				}
				if (type.startsWith("grade")) {
					Map<String, List<String>> map = highlighting.get(type + solrDocument.get("gradeId"));
					grade.setGradeId((int) solrDocument.get("gradeId"));
					grade.setOrgId((int) solrDocument.get("orgId"));
					if (map != null) {
						List<String> listName = map.get("gradeName");
						if (listName != null && listName.size() > 0) {
							grade.setGradeName(listName.get(0));
						} else
							grade.setGradeName((String) solrDocument.get("gradeName"));
					} else
						grade.setGradeName((String) solrDocument.get("gradeName"));
					grade.setGradeImg((String) solrDocument.get("gradeImg"));
					grade.setStartDate((Date) solrDocument.get("startDate"));
					grade.setEndDate((Date) solrDocument.get("endDate"));
					grade.setUserNum((int) solrDocument.get("userNum"));
					if (map != null) {
						List<String> list = map.get("gradeDesc");
						if (list != null && list.size() > 0) {
							String gradeDesc = list.get(0);
							grade.setGradeDesc(gradeDesc);
						} else
							grade.setGradeDesc((String) solrDocument.get("gradeDesc"));
					} else
						grade.setGradeDesc((String) solrDocument.get("gradeDesc"));

					gradeList.add(grade);
				}
				if (type.startsWith("author")) {
					Map<String, List<String>> map = highlighting.get(type + solrDocument.get("authorId"));
					author.setAuthorId((int) solrDocument.get("authorId"));
					author.setOrgId((int) solrDocument.get("orgId"));
					if (map != null) {
						List<String> authorName = map.get("authorName");
						if (authorName != null && authorName.size() > 0) {
							author.setAuthorName(authorName.get(0));
						} else
							author.setAuthorName((String) solrDocument.get("authorName"));
					} else
						author.setAuthorName((String) solrDocument.get("authorName"));
					author.setAuthorPhoto((String) solrDocument.get("authorPhoto"));
					if (map != null) {
						List<String> authorDes = map.get("authorDes");
						if (authorDes != null && authorDes.size() > 0) {
							author.setAuthorDes(authorDes.get(0));
						} else
							author.setAuthorDes((String) solrDocument.get("authorDes"));
					} else
						author.setAuthorDes((String) solrDocument.get("authorDes"));
					author.setAuthorSex((int) solrDocument.get("authorSex"));
					authorList.add(author);
				}
			}
			result.setAuthor(authorList);
			result.setCourse(courseList);
			result.setGrad(gradeList);
			result.setNews(newsList);
		} catch (

		Exception e) {
			return null;
		}
		return result;
	}

	/**
	 * {根据用户输入的关键词进行分词}
	 * 
	 */
	public List<String> getAnalysis(String queryString) {
		FieldAnalysisRequest request = new FieldAnalysisRequest("/analysis/field");
		request.addFieldName("newsTitle");// 字段名，随便指定一个支持中文分词的字段
		request.setFieldValue("");// 字段值，可以为空字符串，但是需要显式指定此参数
		request.setQuery(queryString);

		FieldAnalysisResponse response = null;
		try {
			response = request.process(solrServer);
		} catch (Exception e) {
		}

		List<String> results = new ArrayList<String>();
		Iterator<AnalysisPhase> it = response.getFieldNameAnalysis("newsTitle").getQueryPhases().iterator();
		while (it.hasNext()) {
			AnalysisPhase pharse = (AnalysisPhase) it.next();
			List<TokenInfo> list = pharse.getTokens();
			for (TokenInfo info : list) {
				results.add(info.getText());
			}

		}
		return results;
	}

}
