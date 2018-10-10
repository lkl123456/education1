<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="../../static/common/common.jsp"%>
<script type="text/javascript"
	src="<%=contextPath%>/static/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/des.js"></script>
<script type="text/javascript">
	$(function() {
		$(".subNav").click(
				function() {
					$(this).toggleClass("currentDd").siblings(".subNav")
							.removeClass("currentDd")
					$(this).toggleClass("currentDt").siblings(".subNav")
							.removeClass("currentDt")

					// 修改数字控制速度， slideUp(500)控制卷起速度
					$(this).next(".navContent").slideToggle(500).siblings(
							".navContent").slideUp(500);
				})

	})

	
</script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/css/css.css" />

<form style="*padding-bottom: 2px" id="formSearch" name="formSearch"
	method="post" action="<%=contextPath%>/search/query">
	<input type="hidden" name="q" value="${q }" /> <input
		type="hidden" name="qt" value="${qt }" /> <input type="hidden"
		id="ajaxSubmit" value="mainIndex" />
	<div class="search_r_con">
		<div class="r_con_t" style="color: #999;">
			您搜索包含“<b class="r_c_b7">${queryString }</b>”的结果：
		</div>
		<div class="r_con_b">
			全部：<a href="javascript:indexSearch(0);"
				class="<c:if test="${qt==0}">current</c:if>"> 全部<c:if
					test="${qt==0}">(${searchResult.recordCount})</c:if></a> <a
				href="javascript:indexSearch(1);"
				class="<c:if test="${qt==1}">current</c:if>"> 课程<c:if
					test="${qt==1}">(${searchResult.recordCount})</c:if></a> <a
				href="javascript:indexSearch(2);"
				class="<c:if test="${qt==2}">current</c:if>">新闻<c:if
					test="${qt==2}">(${searchResult.recordCount})</c:if></a> <a
				href="javascript:indexSearch(3);"
				class="<c:if test="${qt==3}">current</c:if>">专题<c:if
					test="${qt==3}">(${searchResult.recordCount})</c:if></a> <a
				href="javascript:indexSearch(4);"
				class="<c:if test="${qt==4}">current</c:if>">讲师<c:if
					test="${qt==4}">(${searchResult.recordCount})</c:if></a>
		</div>
		<!-- <div>
			&nbsp; <span>响应时间：${searchResult.responseTime }ms</span>
		</div> -->
	</div>
	<div class="search_con">
		<ul>
			<c:if test="${not empty searchResult.course }">
				<c:forEach items="${searchResult.course }" var="course">
					<li><a
						href="<%=contextPath%>/course/courseInfo/${course.courseId }/1.shtml"><img
							src="<%=imgServerPath%>${course.coursePic }" class="s_img"></a>
						<dl>
							<dt>
								<b style="color: #999">[<c:if
										test="${course.courseType==170 }">三分屏</c:if><c:if
										test="${course.courseType==171 }">单视频</c:if><c:if
										test="${course.courseType==683 }">微课</c:if><c:if
										test="${course.courseType==4 }">多媒体课程</c:if>]
								</b><a
									href="<%=contextPath%>/course/courseInfo/${course.courseId }/1.shtml">${course.courseName }</a>

							</dt>
							<dd>
								<span>讲 师：${course.authorName }</span> <span>发布时间：<fmt:formatDate
										value="${course.createTime }" pattern="yyyy-MM-dd" /></span>
							</dd>
							<dd>${course.courseDes }</dd>
						</dl></li>
				</c:forEach>
			</c:if>
			<c:if test="${not empty searchResult.news }">
				<c:forEach items="${searchResult.news }" var="news">
					<li><c:if test="${news.newsSortId==1 }">
							<a
								href="<%=contextPath%>/cms/newsIndex/${news.newsId }_${news.newsSortId }_${news.newsSortType }/${news.orgId }.shtml"><img
								src="<%=imgServerPath%>${news.newsFocusPic }" class="s_img"></a>
						</c:if>
						<dl>
							<dt>
								<a
									href="<%=contextPath%>/cms/newsIndex/${news.newsId }_${news.newsSortId }_${news.newsSortType }/${news.orgId }.shtml">
									<b style="color: #999">[${news.newsSortName }]</b>${news.newsTitle }
								</a>
							</dt>
							<dd>
								<span>发 布 人：${news.creatorName }</span> <span>发布时间：<fmt:formatDate
										value="${news.createTime }" pattern="yyyy-MM-dd" /></span>
							</dd>
							<dd>${news.content }</dd>
						</dl></li>
				</c:forEach>
			</c:if>
			<c:if test="${not empty searchResult.grad }">
				<c:forEach items="${searchResult.grad }" var="grad">
					<li><a href="javascript:void(0);"
						onclick="isGetInfo(${grad.gradeId })"><img
							src="<%=imgServerPath%>${grad.gradeImg }" class="s_img"></a>
						<dl>
							<dt>
								<a href="javascript:void(0);"
									onclick="isGetInfo(${grad.gradeId })">${grad.gradeName }</a>
							</dt>
							<dd>${grad.gradeDesc }</dd>
						</dl></li>
				</c:forEach>
			</c:if>
			<c:if test="${not empty searchResult.author }">
				<c:forEach items="${searchResult.author }" var="author">
					<li><a
						href="<%=contextPath%>/author/authorInfo/${author.authorId }/1.shtml"><img
							src="<%=imgServerPath%>${author.authorPhoto }" class="s_img"></a>
						<dl>
							<dt>
								<a
									href="<%=contextPath%>/author/authorInfo/${author.authorId }/1.shtml">${author.authorName }</a>
							</dt>
							<dd>${author.authorDes }</dd>
						</dl></li>
				</c:forEach>
			</c:if>
		</ul>
		<div style="padding: 10px; overflow: hidden;">
			<com:pageCom />
		</div>
	</div>
</form>