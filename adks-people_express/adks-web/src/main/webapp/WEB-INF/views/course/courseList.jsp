<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.adks.web.util.BaseConfigHolder"%>
<%@ include file="../../static/common/common.jsp"%>
<%@ include file="../../static/common/script.jsp"%>
<form style="*padding-bottom: 2px" id="formSearch" name="formSearch" method="post"
	action="<%=contextPath%>/course/getCoursesList.do">
	<input type="hidden" id="ajaxSubmit" value="courseListDiv" />
	<input type="hidden"  id="courseSortCode" name="courseSortCode" value="${courseSortCode}" />
	<input type="hidden"  id="courseType" name="courseType" value="${courseType}" />
	<input type="hidden" id="showStyle"  name="showStyle" value="${showStyle}" />
	<input type="hidden" id="orgId"  name="orgId" value="${orgId}" />
	<dt>
		<h2>
			课程中心
		</h2>
		<div class="soso">
			<input name="searchKeyValue" id="searchKeyValue" class="sonei"
				type="text" value="${searchKeyValue}" />
			<input class="sosouo" name="" type="button"
				onclick="javascript:getCoursesList();" />
		</div>
	</dt>
	<dd>
		<div class="peixin_right">
			<div class="kecheng_tiao">
				<div class="tiao_left">
					<span>显示：</span>
					<p class="xs1y" id="xs_y" onclick="dj1()"></p>
					<p class="xs2" id="xs_e" onclick="dj2()"></p>
					<p class="xs3" id="xs_s" onclick="dj3()"></p>
				</div>
				<div style="margin-top: 5px;" class="coursepagecss">
				</div>
			</div>
			<div class="xianshi1" id="xianshi">
				<c:if
					test="${ page != null && page.rows != null && page.totalPage > 0 }">
					<c:forEach items="${page.rows}" var="course">
						<div class="kc_shipin">
							<div class="kc_shipinleft">
								
								 <a href="#" onclick="BeginStudy('${course.courseId}','${course.courseCode}','${course.courseType}','${course.courseName}');" >
								
								<c:choose>
										<c:when
											test="${course.coursePic == null || course.coursePic == ''}">
											<img src="<%=contextPath%>/static/images/courseImg.jpg" width="108"
												height="72" />
										</c:when>
										<c:otherwise>
											<img src="<%=imgServerPath%>${course.coursePic}"
												width="108" height="72" />
										</c:otherwise>
									</c:choose>
									<div class="a_hover"></div> </a>
							</div>
							<div class="kc_shipinright">
								<p class="kc_shipintitle">
									<a href="#" title="${course.courseName}" onclick="courseInfo('${course.courseId}','${orgId }');" >${course.courseName}</a>
									
								</p>
								<p>
									<a class="sy_author" title="${course.authorName}">讲　　师：${course.authorName}</a>
									<span class="kc_shipin_fenlei">课件类型：<c:if test="${course.courseType==170}">scorm课件</c:if><c:if test="${course.courseType==171}">普通视频</c:if><c:if test="${course.courseType==683}">微课</c:if><c:if test="${course.courseType==4}">多媒体课程</c:if> </span>
									<span class="kc_shipin_fenlei" title="${course.courseSortName}">课程分类：${course.courseSortName}</span>
									<span class="kc_shipintime">发布时间：<fmt:formatDate value="${course.createTime}"  type="date" pattern="yyyy-MM-dd" /></span>
								</p>
								<p class="kc_shipin_jj" title="${course.courseDes}">
									简介：${course.courseDes}
								</p>
							</div>
						</div>
					</c:forEach>
				</c:if>

			</div>
			<br/><br/>
			 <div class="kecheng_tiao kecheng_tiao1">
				<div class="tiao_left">
					<span>显示：</span>
					<p class="xs1y" id="xs_y_2" onclick="dj1()"></p>
					<p class="xs2" id="xs_e_2" onclick="dj2()"></p>
					<p class="xs3" id="xs_s_2" onclick="dj3()"></p>
				</div>
				<div style="margin-top: 5px;" class="coursepagecss">
				<com:pageCom />
				</div>
			</div>
	</dd>
	<input type="hidden" id="userId" value="${userId}" />
    <input type="hidden" id="sessionId" value="<%=session.getId()%>" />
</form>

<script type="text/javascript">
$(document).ready(function(){
	var showStyle = $("#showStyle").val();
	if(showStyle == 'style2'){
		dj2();
	}else if(showStyle == 'style3'){
		dj3();
	}else{
		dj1();
	}
	
	$('#xs_y,#xs_y_2').click(function(){
		$('#xianshi').addClass('xianshi1');	
		$('#xianshi').removeClass('xianshi2,xianshi3');	
	})		
	$('#xs_e,#xs_e_2').click(function(){
		$('#xianshi').addClass('xianshi2');	
		$('#xianshi').removeClass('xianshi1,xianshi3');	
	})
	$('#xs_s,#xs_s_2').click(function(){
		$('#xianshi').addClass('xianshi3');	
		$('#xianshi').removeClass('xianshi2,xianshi1');	
	})
	
	
})

</script>

