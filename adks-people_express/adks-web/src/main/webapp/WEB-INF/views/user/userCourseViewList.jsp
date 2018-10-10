<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.adks.web.util.BaseConfigHolder"%>
<%@ include file="../../static/common/common.jsp"%>
<form id="formSearch" name="formSearch" method="post"  action="<%=contextPath%>/courseview/getUserCourseViewList.do"  >
	<input type="hidden" id="ajaxSubmit" value="gradeListDiv"/>
		<dl class="min_right">
            <dt>
                <h2>观看记录</h2>
            </dt>
        	<dd class="mi_r2">
            	<div>
                    <ul>
                    	<c:if  test="${ page == null || page.rows== null || page.totalPage == 0 }">
	                		<li>
                        		暂无观看记录
	                        </li>
	                    </c:if>
	                    <c:if test="${ page != null && page.rows != null && page.totalPage > 0 }"> 
	                    	  <c:forEach items="${page.rows}" var="CourseView">
	                    	  	<li>
		                        	<b></b>
		                        	<p><a href="javascript:BeginStudy('${CourseView.courseId}','${CourseView.courseCode}','${CourseView.courseCwType}','${CourseView.courseName}');" class="lan">${CourseView.courseName}</a></p>
		                            <span>观看时间：<fmt:formatDate value="${CourseView.lastStudyDate}" type="date" pattern="yyyy-MM-dd HH:mm:ss" />						观看至：${CourseView.lastPositionStr}</span>
		                            <a class="xuxi_1" href="javascript:BeginStudy('${CourseView.courseId}','${CourseView.courseCode}','${CourseView.courseCwType}','${CourseView.courseName}');">继续学习</a>
		                            <!-- 
		                            <a class="xuxi_2" href="javascript:deleteUserCourseView(${CourseView.courseId});">删除</a>
		                             -->
		                        </li>
	                    	  
	                  		</c:forEach>
	                  		<com:pageCom />
	                    </c:if>
                    </ul>
                </div>
          </dd>
		</dl>
	
		<input type="hidden" id="userId" value="${user.userId}" />
    	<input type="hidden" id="sessionId" value="<%=session.getId()%>" />
        <dl class="gradeCourseMsgInfo">
    	<dt>
        	<h2>提示</h2><img src="<%=contextPath%>/static/images/guan.gif" class="guan"   width="20" height="17" onclick="courseViewMsgClose();"/>
        </dt>
        <dd  id="gradeCourseMsg"></dd>
    </dl>
    
  	  <div class="bg" id="bg"></div>
</form>
<script type="text/javascript">

function courseViewMsgClose(){
	$("#bg").hide();
	$('.gradeCourseMsgInfo').hide();
}

function  showMoreOpenedCourseNameMsgDiv(openedCourseName){
	$("#bg").show();
	$("#gradeCourseMsg").html("<p>课程<span>《"+openedCourseName+"》</span>正在播放，不能再次播放！</p>");
	$('.gradeCourseMsgInfo').show();
}

function  showOpenedCourseNameMsgDiv(openedCourseName){
	$("#bg").show();
	$("#gradeCourseMsg").html("<p>课程<span>《"+openedCourseName+"》</span>正在播放，请先关闭再学习其他课程！</p>");
	$('.gradeCourseMsgInfo').show();
}
</script>


