<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@page import="com.adks.web.util.DesEncrypt"%>
<%@ include file="../../static/common/common.jsp"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	
	DesEncrypt des = new DesEncrypt();
	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>中共民航局党校</title>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/xcConfirm.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/index.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/css.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/courseInfo.css" />
		<script type="text/javascript" src="<%=basePath %>static/js/jquery-1.8.2.min.js"></script>
		<script type="text/javascript">    	
			var contextPath = '${pageContext.request.contextPath}';
		</script>
		<script src="<%=basePath %>static/js/index.js"></script>
	</head>
	<body>
		<input type="hidden" id="orgId" value="${orgId }">
		<div class="header" id="header">
	    	<script type="text/javascript">
				var orgId=$("#orgId").val();
			     $(document).ready(function(){
				   	 $.ajax({
						async:false,
						url:'<%=path%>/index/top.do?orgId='+orgId,
							type:"post", 
							success:function(data){
								$("#header").html(data);
							}
						});
				   }); 
		   </script>
	    </div>
	    <input type="hidden" id="mainFlag" value="${mainFlag}" />
        <input type="hidden" id="authorId" value="${authorId}" />
        <input type="hidden" id="letter" value="${letter}" />
        <input type="hidden" id="org" value="${org}" />
        <input type="hidden" id="courseId" value="${courseId}" />
        <input type="hidden" id="newsSortId" value="${newsSortId}" />
        <input type="hidden" id="newsSortType" value="${newsSortType}" />
        <input type="hidden" id="newsId" value="${newsId}" />
        <input type="hidden" id="newsHtmlAdress" value="${newsHtmlAdress}" />
        <input type="hidden" id="gradeId" value="${gradeId}" />
		<input type="hidden" id="catalogType" value="<%=request.getParameter("catalogType") %>">
		<div id="mainIndex" ></div>
		<!--查询底部信息  -->
		<div class="footer" id="footer">
			<script type="text/javascript">
				var orgId=$("#orgId").val();
			     $(document).ready(function(){
				   	 $.ajax({
						async:false,
						url:'<%=path%>/index/footer.do?orgId='+orgId,
							type:"post", 
							success:function(data){
								$("#footer").html(data);
								checkData();
							}
						});
				   }); 
		   </script>
		</div>
		<!-- 
	    <ul class="ce_bian">
	    	<li class="ce_li_1"><a href="<%=basePath %>user/feedbackIndex.shtml">在线反馈</a></li>
	        <li class="ce_li_2"><a href="#">15101000011</a></li>
	        <li class="ce_li_3"><a href="#">100861152</a></li>
	    </ul>
	     -->
	</body>
<script type="text/javascript">
		function checkData(){ 
			var mainFlag = $("#mainFlag").val();
			$("#index").removeClass("nav_li_n");
			var orgId=$("#orgId").val();
			if(mainFlag=="newsIndex"){
				var newsSortId = $("#newsSortId").val();
				var newsSortType = $("#newsSortType").val();
				var newsId = $("#newsId").val();
				//alert("sa:"+newsSortId+","+orgId);
				if(newsId!=null&&newsId!=""){
					var newsHtmlAdress=$("#newsHtmlAdress").val();
					getNewsDetail(newsHtmlAdress,newsId,newsSortId,orgId,newsSortType);
				}else{
					getNewsList(newsSortId,orgId,newsSortType);
				}
				$("#"+mainFlag).addClass("nav_li_n");
			}else if(mainFlag=="feedback"){
				toOnlineHelp("xxlc");
				$("#contactUs").addClass("nav_li_n");
			}else if(mainFlag=="centerIndex"){
				toCenter(orgId);
				$("#"+mainFlag).addClass("nav_li_n");
			}else if(mainFlag == "courseIndex"){
				//getCourseCatalogTreeDate();//加载分类列表
				toCourseList(orgId);
				$("#"+mainFlag).addClass("nav_li_n");
			}else if(mainFlag == "downloadIndex"){
				toDownloadtList();
				$("#"+mainFlag).addClass("nav_li_n");
			}else if(mainFlag == "phIndex"){
				toPhIndex(orgId);
				$("#index").addClass("nav_li_n");
			}else if(mainFlag == "xxlc"||mainFlag == "czzn"||mainFlag == "cjwt"||mainFlag == "kqbd"){
				toOnlineHelp(mainFlag);
				$("#contactUs").addClass("nav_li_n");
			}else if(mainFlag == "subjectIndex"){
				toSubjectList();
				$("#"+mainFlag).addClass("nav_li_n");
			}else if(mainFlag == "courseInfo"){
				var courseId = $("#courseId").val();
				toCourseInfo(courseId,orgId);
				$("#courseIndex").addClass("nav_li_n");
			}else if(mainFlag == "allAuthor"){  //讲师栏目
				var letter = $("#letter").val();
				var org = $("#org").val();
				toAllAuthor(letter,org);
				$("#"+mainFlag).addClass("nav_li_n");
			}else if(mainFlag=="contactUs"){
				toContactUs(orgId);
				$("#"+mainFlag).addClass("nav_li_n");
			}else if(mainFlag=="emergencyIndex"){
				toEmergencyIndex();
			}else if(mainFlag == "authorInfo"){  //讲师详情
				var authorId = $("#authorId").val();
				toAuthorInfo(authorId,orgId);
				$("#allAuthor").addClass("nav_li_n");
			}else if(mainFlag == "toRegisterGradeList"){  //培训班list
				toRegisterGradeList(orgId);
				$("#registGradeList").addClass("nav_li_n");
			}else if(mainFlag=="toGradeCenterIndex"){
				var gradeId = $("#gradeId").val();
				toGradeCenterIndex(gradeId,orgId);
			}else if(mainFlag == "findPwd"){//找回密码
				findPwd(orgId);
			}
		};

</script>
</html>

