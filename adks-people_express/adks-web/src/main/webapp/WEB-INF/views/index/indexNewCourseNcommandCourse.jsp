<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="../../static/common/common.jsp"%>
		<div class="main_1 main_1_cd">
			<dl class="main_left_1">
				<dt>
					<ol>
						<li class="shnax">
							最新课程
						</li>
						<!-- 
						<li>
							推荐课程
						</li>
						 -->
					</ol>
				</dt>
				<dd>
					<c:choose>
						<c:when test="${empty topNewCourseList }">
						</c:when>
						<c:otherwise>
							<c:forEach items="${topNewCourseList}" var="course" varStatus="courseIndex" begin="0" end="0">
								<div class="right_1_left">
									<a href="javascript:" onclick="BeginStudy('${course.courseId}','${course.courseCode}','${course.courseType}','${course.courseName}');" title="${course.courseName}"> <img src="<%=imgServerPath%>${course.coursePic }" width="333" height="229" title="${course.courseName}" /> </a>
									<p>
										<a href="javascript:" onclick="BeginStudy('${course.courseId}','${course.courseCode}','${course.courseType}','${course.courseName}');" title="${course.courseName}">${course.courseName }</a>
									</p>
									<div>
										主讲人：${course.authorName} 课件分类：${course.courseSortName}
										<br />
										课程简介：
										<span title="${course.courseDes }"><c:if test="${fn:length(course.courseDes)>15}">${fn:substring(course.courseDes,0,15)}...</c:if> <c:if test="${fn:length(course.courseDes)<15}">${course.courseDes}</c:if> </span>
										<!-- <a href="javascript:" onclick="BeginStudy('${course.courseId}','${course.courseCode}','${course.courseType}','${course.courseName}');" title="${course.courseName}" class="lan">详细>></a> -->
									</div>
								</div>
							</c:forEach>
							<ul class="right_1_right">
								<c:if test="${fn:length(topNewCourseList) > 1}">
									<c:forEach items="${topNewCourseList}" var="course" varStatus="courseIndex" begin="1">
										<li>
											<a href="javascript:" onclick="BeginStudy('${course.courseId}','${course.courseCode}','${course.courseType}','${course.courseName}');" title="${course.courseName}"> <img src="<%=imgServerPath%>${course.coursePic }" width="158" height="108" title="${course.courseName}" /> </a>
											<p>
												<a href="javascript:" onclick="BeginStudy('${course.courseId}','${course.courseCode}','${course.courseType}','${course.courseName}');" title="${course.courseName}">${course.courseName }</a>
											</p>
										</li>
									</c:forEach>
								</c:if>
							</ul>
						</c:otherwise>
					</c:choose>
				</dd>
				
				<dd style="display: none">
					<c:choose>
						<c:when test="${empty topTJCourseList }">
						</c:when>
						<c:otherwise>
							<c:forEach items="${topTJCourseList}" var="course" varStatus="courseIndex" begin="0" end="0">
								<div class="right_1_left">
									<a href="javascript:" onclick="BeginStudy('${course.courseId}','${course.courseCode}','${course.courseType}','${course.courseName}');" title="${course.courseName}"> <img src="<%=imgServerPath%>${course.coursePic }" width="333" height="229" title="${course.courseName}" /> </a>
									<p>
										<a href="javascript:" onclick="BeginStudy('${course.courseId}','${course.courseCode}','${course.courseType}','${course.courseName}');" title="${course.courseName}">${course.courseName }</a>
									</p>
									<div>
										主讲人：${course.authorName} 课件分类：${course.courseSortName}
										<br />
										课程简介：
										<span title="${course.courseDes }"><c:if test="${fn:length(course.courseDes)>15}">${fn:substring(course.courseDes,0,15)}...</c:if> <c:if test="${fn:length(course.courseDes)<15}">${course.courseDes}</c:if> </span>
										<!-- <a href="javascript:" onclick="BeginStudy('${course.courseId}','${course.courseCode}','${course.courseType}','${course.courseName}');" title="${course.courseName}" class="lan">详细>></a> -->
									</div>
								</div>
							</c:forEach>
							<ul class="right_1_right">
								<c:if test="${fn:length(topTJCourseList) > 1}">
									<c:forEach items="${topTJCourseList}" var="course" varStatus="courseIndex" begin="1">
										<li>
											<a href="javascript:" onclick="BeginStudy('${course.courseId}','${course.courseCode}','${course.courseType}','${course.courseName}');" title="${course.courseName}"> <img src="<%=imgServerPath%>${course.coursePic }" width="158" height="108" title="${course.courseName}" /> </a>
											<p>
												<a href="javascript:" onclick="BeginStudy('${course.courseId}','${course.courseCode}','${course.courseType}','${course.courseName}');" title="${course.courseName}">${course.courseName }</a>
											</p>
										</li>
									</c:forEach>
								</c:if>
							</ul>
						</c:otherwise>
					</c:choose>
				</dd>
				
			</dl>
			<dl class="main_right_1 mian_right_2" style="border:none; border-bottom:1px solid #d8dade">
				<dt>
					<h2>
						大家正在看
					</h2>
				</dt>
				<dd class="scrolltop" style="border:1px solid #d8dade; border-top:none; height:325px;">
					<ol>
						<c:choose>
							<c:when test="${empty topCourseUserList }">
							</c:when>
							<c:otherwise>
								<c:forEach items="${topCourseUserList}" var="courseUser">
									<li>
										<span>${fn:substring(courseUser.userName,0,1)}**正在看</span> 《
										<a href="javascript:BeginStudy('${courseUser.courseId}','${courseUser.courseCode}','${courseUser.courseCwType}','${courseUser.courseName}');" title="${courseUser.courseName}">${courseUser.courseName}</a>》
									</li>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</ol>
				</dd>
				<script type="text/javascript">
				$(document).ready(function(){
					$('.main_left_1 ol li').mouseover(function(){
						var $kcn=$(this).index();
						$('.main_left_1 ol li').removeClass('shnax');
						$(this).addClass('shnax');
						$('.main_left_1 dd').hide();
						$('.main_left_1 dd').eq($kcn).show();
					})
					$(".scrolltop").imgscroll({
						speed: 40,    //图片滚动速度
						amount: 0,    //图片滚动过渡时间
						width: 1,     //图片滚动步数
						dir: "up"   // "left" 或 "up" 向左或向上滚动
					});
					
				});
				</script>
			</dl>
		</div>

