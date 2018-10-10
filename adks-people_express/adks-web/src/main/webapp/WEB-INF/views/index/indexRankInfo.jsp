<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="../../static/common/common.jsp"%>

<dl class="jxph">
			<dt>
				<h2>
					竞学排行
				</h2>
				<a href="<%=contextPath%>/index/phIndex/${orgId }.shtml">更多</a>
			</dt>
			<dd>
				<h2>
					课程排行
				</h2>
				<div>
					<span>排行</span>
					<a>课程名</a>
					<i>点击量</i>
				</div>
				<ol>
					<c:choose>
						<c:when test="${empty topHotCourseList }">
						</c:when>
						<c:otherwise>
							<c:forEach items="${topHotCourseList}" var="course" begin="0" end="${length3 }" varStatus="courseIndex">
								<li>
									<span><c:if test="${courseIndex.index<3}">
											<b class="cd_bb">
										</c:if> 
										<c:if test="${courseIndex.index>2}">
											<b>
										</c:if>
										${courseIndex.index+1}</b> </span>
									<a href="javascript:" onclick="BeginStudy('${course.courseId}','${course.courseCode}','${course.courseType}','${course.courseName}');" title="${course.courseName}">${course.courseName }</a>
									<i>${course.courseClickNum }</i>
								</li>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</ol>
			</dd>
			<dd class="nbsq">
				<h2>
				<c:if test="${!empty orgName }">
					${orgName }排行
				</c:if>
				</h2>
				<div>
					<span>排行</span>
					<a>单位名称</a>
					<i>人均学时</i>
				</div>
				<ol>
					<c:choose>
						<c:when test="${empty orgStudyTimelist }">
						</c:when>
						<c:otherwise>
							<c:forEach items="${orgStudyTimelist}"  begin="0" end="${length1 }"  var="gradeUser" varStatus="guIndex">
								<li>
									<span><c:if test="${guIndex.index<3}">
											<b class="cd_bb">
										</c:if>
										<c:if test="${guIndex.index>2}">
											<b>
										</c:if>
										${guIndex.index+1}</b>
									</span>
									<a href="javascript:" title="${gradeUser.orgName }">${gradeUser.orgName }</a>
									<i>${gradeUser.avgXs }</i>
								</li>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</ol>
			</dd>
			<dd class="nbso">
				<h2>
					党员排行
				</h2>
				<div>
					<span>排行</span>
					<a class="nodb">姓名</a>
					<a>单位</a>
					<i>累计学时</i>
				</div>
				<ol>
					<c:choose>
						<c:when test="${empty userStudyTimelist }">
						</c:when>
						<c:otherwise>
							<c:forEach items="${userStudyTimelist}"  begin="0" end="${length2 }"  var="gradeUser" varStatus="guIndex">
								<li>
									<span><c:if test="${guIndex.index<3}">
											<b class="cd_bb">
										</c:if> 
										<c:if test="${guIndex.index>2}">
											<b>
										</c:if>
										${guIndex.index+1}</b> </span>
									<a href="javascript:" title="${gradeUser.userName }" class="nodb">${gradeUser.userName }</a>
									<a>${gradeUser.orgName }</a>
									<i>${gradeUser.sumUserStudytime }</i>
								</li>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</ol>
			</dd>
</dl>
		

