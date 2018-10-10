<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../static/common/common.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/css/index.css" />
<script type="text/javascript" src="<%=contextPath%>/static/grade/js.js"></script>
<form id="formSearch" name="formSearch" method="post"
	action="<%=contextPath%>/grade/gradeCourseList.do">
	<input type="hidden" id="ajaxSubmit" value="gradeDiv" /> <input
		type="hidden" id="gradeId" name="gradeId" value="${gradeId}" /> <input
		type="hidden" id="gradeName" name="gradeName" value="${gradeName}" />
	<input type="hidden" id="userId" name="userId" value="${user.userId}" />
	<input type="hidden" id="flag" name="flag" value="${flag}" />
	<dl class="min_right min_rightq">
		<dt>
			<ol>
				<li id="qb"><a href="javascript:getGradeCourseList('')">全部</a></li>
				<li id="bx"><a href="javascript:getGradeCourseList('2')">必修</a></li>
				<li id="xx"><a href="javascript:getGradeCourseList('3')">选修</a></li>
				<li id="xxz"><a href="javascript:getGradeCourseList('5')">学习中</a></li>
				<li id="wks"><a href="javascript:getGradeCourseList('4')">未开始</a></li>
				<li id="yxw"><a href="javascript:getGradeCourseList('6')">已学完</a></li>
			</ol>
		</dt>
		<input type="hidden" id="xsFlag" name="xsFlag" value="${xsFlag }" />
		<c:if var="current"
			test="${ page == null || page.rows== null || page.totalPage == 0 }">
			<br />暂无班级课程信息
        </c:if>
		<c:if
			test="${ page != null && page.rows != null && page.totalPage > 0 }">
			<c:set var="e_num" value="0"></c:set>
			<c:forEach items="${page.rows}" var="map">
				<input type="hidden" id="courseId_${e_num}" value="${map.courseId }" />
				<dd class="main_r_dd2" id="image_${e_num }">
					<!-- 需要同步学时 -->
					<c:if test="${gIdsMap.get(map.courseId)==1}">
						<a class="zuo_min"
							href="javascript:UpdateGradeUser('${map.courseId}','${user.userId}','${gradeId }');">
							<c:choose>
								<c:when test="${map.courseImg == null || map.courseImg == ''}">
									<img src="<%=contextPath%>/static/images/ad.jpg" width="97"
										height="73" />
								</c:when>
								<c:otherwise>
									<img src="<%=imgServerPath%>${map.courseImg}" width="97"
										height="73" />
								</c:otherwise>
							</c:choose> <!-- 
				                	<span>${map.courseTimeLong }</span>
								 --> 
							<c:if test="${map.gcState == 1}">
								<b class="bixiu"></b>
							</c:if> <c:if test="${map.gcState == 2}">
								<b class="xuanxiu"></b>
							</c:if>
						</a>
					</c:if>
					<!-- 不需要同步学时 -->
					<c:if
						test="${gIdsMap.get(map.courseId)==2||gIdsMap.get(map.courseId)==3}">
						<a class="zuo_min"
							href="javascript:BeginStudy('${map.courseId}','${map.courseCode}','${map.courseCwType}','${map.courseName}');">
							<c:choose>
								<c:when test="${map.courseImg == null || map.courseImg == ''}">
									<img src="<%=contextPath%>/static/images/ad.jpg" width="97"
										height="73" />
								</c:when>
								<c:otherwise>
									<img src="<%=imgServerPath%>${map.courseImg}" width="97"
										height="73" />
								</c:otherwise>
							</c:choose> <!-- 
				                	<span>${map.courseTimeLong }</span>
								 -->
							<c:if test="${map.gcState == 1}">
								<b class="bixiu"></b>
							</c:if> <c:if test="${map.gcState == 2}">
								<b class="xuanxiu"></b>
							</c:if>
						</a>
					</c:if>
					<div class="yoy_min">
						<!-- 需要同步学时 -->
						<c:if test="${gIdsMap.get(map.courseId)==1 }">
							<p>
								<a class="lan"
									href="javascript:UpdateGradeUser('${map.courseId}','${user.userId}','${gradeId }');">
									${map.courseName }	
								</a>
							</p>
						</c:if>
						<!-- 不需要同步学时 -->
						<c:if
							test="${gIdsMap.get(map.courseId)==2||gIdsMap.get(map.courseId)==3 }">
							<p>
								<a class="lan"
									href="javascript:BeginStudy('${map.courseId}','${map.courseCode}','${map.courseCwType}','${map.courseName}');"
									title="${map.courseName }">${map.courseName }</a>
							</p>
						</c:if>
						<ul>
							<li><span class="hui">讲师：</span>${map.authorName }</li>
							<li><span class="hui">类型：</span> <c:if
									test="${map.courseCwType == 170}">SCORM课件</c:if> <c:if
									test="${map.courseCwType == 171}">普通视频</c:if> <%-- <c:if test="${map.courseCwType == 683}">行政学院课件</c:if> --%>
							</li>
						</ul>
						<ul>
							<li><span class="hui">时长：</span><b class="cheng">${map.courseTimeLong }</b></li>
							<!-- 需要同步学时 -->
							<c:if test="${gIdsMap.get(map.courseId)==1 }">
								<li class="jdu"><span class="hui">进度：</span>
								<div class="jindu"><div id="jindu" style="width:<fmt:formatNumber value="0.0" pattern="#0%"/>"></div></div><span  class="bfbi"><fmt:formatNumber value="0.0" pattern="#0%" /></span></li>
							</c:if>
							<!-- 不需要同步学时 -->
							<c:if
								test="${gIdsMap.get(map.courseId)==2||gIdsMap.get(map.courseId)==3 }">
								<li class="jdu"><span class="hui">进度：</span>
								<div class="jindu"><div id="jindu_${e_num }" style="width:<fmt:formatNumber value="0.0" pattern="#0%"/>"></div></div><span id="jinduSpan_${e_num }" class="bfbi"><fmt:formatNumber value="0.0" pattern="#0%" /></span></li>
							</c:if>
						</ul>
					</div>
					<!-- 需要同步学时 -->
					<c:if test="${gIdsMap.get(map.courseId)==1 }">
						<a class="kaoshi_1"
							href="javascript:UpdateGradeUser('${map.courseId}','${user.userId}','${gradeId }');">同步学时</a>
					</c:if>
					<!-- 不需要同步学时 -->
					<c:if
						test="${gIdsMap.get(map.courseId)==2||gIdsMap.get(map.courseId)==3}">
						<a class="kaoshi_1"
							href="javascript:BeginStudy('${map.courseId}','${map.courseCode}','${map.courseCwType}','${map.courseName}');">继续观看</a>
					</c:if>
					<c:if test="${map.isOver == 1}">
						<img class="wancheng"
							src="<%=contextPath%>/static/images/chenggong.gif" width="76"
							height="75" />
					</c:if>
				</dd>
				<c:set var="e_num" value="${e_num+1}"></c:set>
			</c:forEach>
			<input type="hidden" id="ex_num" value="${e_num }" />
		</c:if>
		<com:pageCom />
	</dl>
</form>
<!-- 动态获取课程进度 -->
<script type="text/javascript">
	$(document).ready(
			function() {

				$('.wenlist').click(function() {
					$('.main_r_dd2').addClass('main_r_dd3');
					$('.bian').addClass('bian1');
					$("#xsFlag").val('w');
				})
				$('.tuwen').click(function() {
					$('.main_r_dd2').removeClass('main_r_dd3');
					$('.bian').removeClass('bian1');
					$("#xsFlag").val('tw');
				})

				var xsFlag = $("#xsFlag").val();
				if (xsFlag == 'w') {
					$('.wenlist').click();
				} else {
					$('.tuwen').click();
				}

				var ex_num = $("#ex_num").val();
				var gradeId = $("#gradeId").val();//班级id
				var userId = $("#userId").val();//用户id

				if (ex_num > 0) {
					for (var i = 0; i < ex_num; i++) {
						var courseId = $("#courseId_" + i).val(); //课程id

						$.ajax({
							async : false,
							url : contextPath
									+ "/gradeCourse/getCourseUsersJindu.do?gradeId="
									+ gradeId + "&userId=" + userId
									+ "&courseId=" + courseId,
							type : "post",
							success : function(data) {
								$("#jindu_" + i).width(data);
								$("#jinduSpan_" + i).text(data);

								if (data == "100%") {
									$("#image_" + i)
											.addClass("main_r_dd_img_1");
								} else if (data == "0%") {
									$("#image_" + i)
											.addClass("main_r_dd_img_3");
								} else {
									$("#image_" + i)
											.addClass("main_r_dd_img_2");
								}
							}
						});
					}
				}
				var flag = $("#flag").val();
				if (flag == 2) {
					$("#bx").addClass("right_min_1");
				} else if (flag == 3) {
					$("#xx").addClass("right_min_1");
				} else if (flag == 4) {
					$("#wks").addClass("right_min_1");
				} else if (flag == 5) {
					$("#xxz").addClass("right_min_1");
				} else if (flag == 6) {
					$("#yxw").addClass("right_min_1");
				} else {
					$("#qb").addClass("right_min_1");
				}
			});
</script>
