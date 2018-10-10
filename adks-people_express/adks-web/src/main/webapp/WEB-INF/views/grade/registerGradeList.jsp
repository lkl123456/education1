<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ include file="../../static/common/common.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/md5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/des.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.js"></script>
<script type="text/javascript">
	$('.ztb .zhank').click(function(){
		$key=$(this).index('.ztb .zhank');
		$('.ztb ul').eq($key).css({'height':'auto'});
		$('.sqi').eq($key).show();
		$(this).hide();
	})
	$('.ztb .sqi').click(function(){
		$key1=$(this).index('.ztb .sqi');
		$('.ztb ul').eq($key1).css({'height':'115px'});
		$('.zhank').eq($key1).show();
		$(this).hide();
	})
	function completeUserData(userId,gradeId){
		if(userId ==null || userId ==''){
			alert("请先登录...");
		}else{
			$.ajax({
				async:true,
				url:contextPath+"/user/checkData.do",
				type:"post",
				success:function(data){
					//alert(data);
					if(data == 'succ'){
						toGradeCenterIndex(gradeId);
					}else if(data == 'nouser'){
						alert("请先登录...");
					}else if(data == 'error'){
						//完善信息
						completeData(gradeId);
					}
				}
			});
		}
	}
	//进入培训班信息
function toGradeCenterIndex(gradeId){
	var url = contextPath+"/grade/toGradeCenterIndex.do?gradeId="+gradeId;
	window.location.href=url;
}
	function completeData(gradeId){
		$.ajax({
			async:true,
			url:contextPath+"/user/completeData.do",
			data:{"gradeId":gradeId},
			type:"post",
			success:function(data){
				$("#mainIndex").html(data);
			}
		});
	}
</script>
<script>
	//暂时没用
	function toRecordPage(){
		$("#registGradeList").removeClass("nav_li_n");
		$("#centerIndex").addClass("nav_li_n");
		toCenter(1);
	}
	// 点击历史培训班个人学习情况
function checkGradeUserDetail(gradeId){
	var url = contextPath+"/registGrade/checkGradeUserDetail.do?gradeId="+gradeId;
	window.open(url);
}  
</script>
<form style="*padding-bottom: 2px" id="formSearch" name="formSearch" method="post"
	action="<%=contextPath%>/registGrade/registerGradeList.do">
	<input type="hidden" id="ajaxSubmit" value="mainIndex" />
<dl class="ztxx">
	<dt>
		<h2>
			全部专题
		</h2>
	</dt>
	<c:choose>
		<c:when test="${page == null || page.rows== null || page.totalPage == 0  }">
		</c:when>
		<c:otherwise>
			<c:forEach items="${page.rows}" var="map">
				<dd>
					<div class="ztl">
						<c:choose>
							<c:when test="${map.gradeImg == null || map.gradeImg == ''}">                        		
	                      			<img src="<%=contextPath %>/static/images/gradeLogoImg.jpg" width="451" height="260" title="${map.gradeName}"  />
                      		</c:when>
                      		<c:otherwise>
                      			<img src="<%=imgServerPath %>/${map.gradeImg}" width="451" height="260" title="${map.gradeName}" />
                      		</c:otherwise>
						</c:choose>
						<div class="nal">
							<input name="" class="jrz" type="button" />
							<input name="" class="jrx" type="button" />
						</div>
					</div>
					<div class="ztr">
						<div class="ztt">
							<h2>
								${map.gradeName}
									<c:if test="${map.isDatePast==1}">
										<a href="#" onclick="checkGradeUserDetail('${map.gradeId }')" class="px_btn2">查看档案</a>
									</c:if>
									<c:if test="${map.isDatePast==2}">
										<a href="javascript:completeUserData('1','${map.gradeId }')" class="px_btn" >进入培训</a>
									</c:if>
									<c:if test="${map.isDatePast==3}">
										<a href="#" class="px_btn1">进入培训</a>
									</c:if>
								
								<a class="download_btn" href="<%=contextPath%>/registGrade/downloadGradeInfo.do?gradeId=${map.gradeId}&gradeName=${map.gradeName} ">目录下载</a>
							</h2>
							<p>
								<span>课程数量：${map.requiredNum + map.optionalNum}门</span><span>发布时间：<fmt:formatDate value="${map.createTime}" type="date" pattern="yyyy-MM-dd" /></span>
								<span>结束时间：<fmt:formatDate value="${map.endDate}" type="date" pattern="yyyy-MM-dd" /></span>
							</p>
							
						</div>
						<div class="ztb">
							<dl class="all_zt">
								<span class="zt_title"><b>专题全部课程</b></span><span class="zhank">展开查看全部 ﹀</span><span class="sqi" style="display: none">收起 ﹀</span>
							</dl>
							<ul>
								<c:choose>
									<c:when test="${empty gradeCourseMap[map.gradeId]}">
									</c:when>
									<c:otherwise>
										<c:forEach items="${gradeCourseMap[map.gradeId]}" var="gradeCourse" varStatus="gradeCourseIndex">
											<li>
												<c:if test="${map.isDatePast==1}">
													<a href="javascript:" >${gradeCourse.courseName}</a>
												</c:if>
												<c:if test="${map.isDatePast==2}">
													<a href="javascript:" >${gradeCourse.courseName}</a>
												</c:if>
												<c:if test="${map.isDatePast==3}">
													<a href="javascript:">${gradeCourse.courseName}</a>
												</c:if>
											</li>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</ul>
						</div>
					</div>
				</dd>
			</c:forEach>
			<div style="overflow: hidden; padding: 12px 0 ">
			<com:pageCom />
			</div>
		</c:otherwise>
	</c:choose>
	<input type="hidden" id="userId" value="${user.userId}" />
    <input type="hidden" id="sessionId" value="<%=session.getId()%>" />
</dl>
</form>