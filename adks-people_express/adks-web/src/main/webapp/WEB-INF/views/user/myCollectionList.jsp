<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../static/common/common.jsp"%>
<form id="formSearch" name="formSearch" method="post" action="<%=contextPath%>/user/myCollectionList.do">
	<input type="hidden" id="ajaxSubmit" value="gradeListDiv" />
	<dl class="min_right">
            <dt>
                <h2>我的收藏</h2>
            </dt>
            <dd class="main_r_dd4">
           		<c:if var="current" test="${ page == null || page.rows== null || page.totalPage == 0 }">
					<div class="main_r_dd2">
						暂无收藏信息
					</div>
				</c:if>
				<c:if test="${ page != null && page.rows != null && page.totalPage > 0 }">
					<c:forEach items="${page.rows}" var="map">
		                <div class="main_r_dd2">
		                    <a class="zuo_min">
		                    	<c:choose>
									<c:when test="${map.courseImg == null || map.courseImg == ''}">
										<img  src="<%=contextPath%>/static/images/courseImg.jpg" width="97" height="73" />
									</c:when>
									<c:otherwise>
										<img  src="<%=imgServerPath%>${map.courseImg}" width="97" height="73"/>
									</c:otherwise>
								</c:choose>
		                    </a>
		                  	<div class="yoy_min">
		                        <p><a class="lan" href="javascript:" onclick="BeginStudy('${map.courseId}','${map.courseCode}','${map.courseType}','${map.courseName}');"><c:out value="${map.courseName}" /></a></p>
		                        <ul>
		                            <li><span class="hui">讲师：</span>${map.authorName}</li>
		                            <li><span class="hui">类型：</span><c:if test="${map.courseType==1}">scorm课件</c:if><c:if test="${map.courseType==2}">普通视频</c:if></li>
		                        </ul>
		                        <ul>    
		                            <li><span class="hui">分类：</span><b class="cheng">${map.courseSortName}</b></li>
		                            <!-- <li class="jdu"><span class="hui">进度：</span><div class="jindu" ><div style="width:<fmt:formatNumber value="${map.studyTimeLong/map.courseDuration}" pattern="#0%"/>"></div></div><span class="bfbi"><fmt:formatNumber value="${map.studyTimeLong/map.courseDuration}" pattern="#0%"/></span></li>-->
		                            <li class="jdu"><span class="hui">观看至：${map.lastPositionStr}</span></li>
		                        </ul>
		                    </div>
		                    <a class="kaoshi_1" href="javascript:" onclick="removeMyCollection(${map.userConId},${map.courseId});">取消收藏</a>
		                    <!-- 
		                    <a class="kaoshi_1 kaoshi_2" href="javascript:" onclick="coursesPlay_myc(${map.courseId},${map.courseType},'${map.courseName}');">继续观看</a>
		                     -->
		                    <a class="kaoshi_1 kaoshi_2" href="javascript:" onclick="BeginStudy('${map.courseId}','${map.courseCode}','${map.courseType}','${map.courseName}');">继续观看</a>
		                </div>
					</c:forEach>
					<com:pageCom />
				</c:if>
            </dd>
        </dl>
	
	    <dl class="gradeCourseMsgInfo">
    	<dt>
        	<h2>提示</h2><img src="<%=contextPath%>/static/images/guan.gif" class="guan"   width="20" height="17" onclick="collectionMsgClose();"/>
        </dt>
        <dd  id="gradeCourseMsg"></dd>
    </dl>
    
  	  <div class="bg" id="bg"></div>
  	  <input type="hidden" id="userId" value="${user.userId}" />
    <input type="hidden" id="sessionId" value="<%=session.getId()%>" />
</form>
<script type="text/javascript">
//  课程 播放
function coursesPlay_myc(courseId,courseType,courseName){

	if(courseType!=null &&courseId!=null){
			if(courseType==170){
				//三分屏 
				window.open(contextPath+"/course/courseListPlayScorm.do?courseId="+courseId,"courseplay",'width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-90)+ ',top=0,left=0,resizable=yes,status=yes,menubar=no,scrollbars=yes')
			}else if(courseType==171){ // 单视频 
				window.open(contextPath+"/course/courseListPlayVideo.do?courseId="+courseId,"courseplay",'width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-90)+ ',top=0,left=0,resizable=yes,status=yes,menubar=no,scrollbars=yes')
			}
	}
	/*
	var openedCourseName=isOpenedCourseName(courseName);
	if(openedCourseName!=null && openedCourseName!='' && openedCourseName==courseName){
		 // 提示 此课程正在播放 
		showMoreOpenedCourseNameMsgDiv(openedCourseName);
	}else if(openedCourseName!=null && openedCourseName!='' && openedCourseName!=courseName) {
        // 提示 已经 其他课程正在播放 
       	 showOpenedCourseNameMsgDiv(openedCourseName);
	}else {
		if(courseType!=null &&courseId!=null){
			if(courseType==170){
				//三分屏 
				window.open(contextPath+"/course/courseListPlayScorm.do?courseId="+courseId,"courseplay",'width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-90)+ ',top=0,left=0,resizable=yes,status=yes,menubar=no,scrollbars=yes')
			}else if(courseType==171){ // 单视频 
				window.open(contextPath+"/course/courseListPlayVideo.do?courseId="+courseId,"courseplay",'width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-90)+ ',top=0,left=0,resizable=yes,status=yes,menubar=no,scrollbars=yes')
			}
		}
	}
	*/
	
}


function collectionMsgClose(){
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