<%@ page language="java" import="java.lang.String" pageEncoding="UTF-8"%>
<%@ include file="../../static/common/common.jsp"%>
<%@ include file="../../static/common/script.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>课程中心 - 中共民航局党校</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<%-- <%@ include file="/common/meta.jsp"%> --%>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/index.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/dtree.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dtree/dtree.js"></script>
		<style>
			.dtree{border:0;}
		</style>
	</head>
	<body>
		<input type="hidden" id="orgId" value="${orgId }">
		<div class="list">
			<div class="bgang"></div>
			<div class="listl listl1">
				<div class="peixin_left" id="peixin_left">
					<dl style="display: none;">
						<dd>
							<dd>
								<a href="#" id="topNew" onclick="getTopNewCourseList();">最新课程</a>
							</dd>
						</dd>
					</dl>
					<dl style="margin-top: 10px; border: 0px; padding-bottom: 0px;">
						<dd>
							<a href="#" class="a_hover" id="cateAll"  name="cataname" onclick="courseCateAllView(${orgId},'0A')"><div class="tbqb"></div>课程分类</a>
						</dd>
					</dl>
					<!-- 分类树 开始 -->
					<div id="menu_zzjs_net"></div>
					<!-- 分类树 结束 -->
					
					<script type="text/javascript">
							var adkstree = new dTree('adkstree');//创建一个对象.
							var orgId=$("#orgId").val();
							adkstree.add(0,-1," 全部课程","javascript:courseCateView("+orgId+",'0A')");
							var _html = "";
							$.ajax({
								type: "POST",
								url:  contextPath+"/course/getCourseSortTreeDate.do",
								data: {timeStampe:new Date()},
								async: false,
								success: function(data){
									_html = data;
								}
							});
							if(_html!=""){
								_html=JSON.parse(_html);
								for(var i=0;i<_html.length;i++){
									var courseCode=_html[i].courseSortCode;
									adkstree.add(
												_html[i].id,//当前分了ID
												_html[i].courseParentId,//父级分类ID
												_html[i].name,//当前分类名称
												"javascript:courseCateView("+orgId+",'"+courseCode+"')",//当前分类链接
												_html[i].name//当前分类名称当作title
												);
								} 
							}
							document.getElementById("menu_zzjs_net").innerHTML=adkstree;
						</script>
					
					 <div class="kecheng_top">
                            <p class="p1">课件类型</p>
                            <div><a href="#" name="courseType"  id="courseType_all"  class="a_hover" onclick="javascript:courseTypeAllView('all','0A');" >全部：</a>
                            <span class="langfei"><a href="#" name="courseType" id="courseType_170" onclick="javascript:courseTypeAllView('170','0A');">SCORM课件</a> 
                            <a style="margin-right: 30px;" href="#" name="courseType" id="courseType_171" onclick="javascript:courseTypeAllView('171','0A');" >普通视频</a>
                            <a href="#" name="courseType" id="courseType_683" onclick="javascript:courseTypeAllView('683','0A');" >微课</a>
                            </span>
                            </div>
             		 </div>
					<input type="hidden" id="courseSortCode" value="">
				</div>
			</div>
			<dl class="listr" id="courseListDiv">
				<p align="center"><img src="<%=contextPath%>/static/images/loading.gif" /></p>
			</dl>
			
			 <dl class="gradeCourseMsgInfo">
    			<dt>
        			<h2>提示</h2><img src="<%=contextPath%>/static/images/guan.gif" class="guan"   width="20" height="17" onclick="courseListMsgClose();"/>
        		</dt>
        		<dd  id="gradeCourseMsg"></dd>
    		</dl>
    		  <div class="bg" id="bg"></div>
		</div>
         
	</body>
	<script type="text/javascript" src="<%=contextPath%>/static/course/course.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/static/course/courseCategory.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){ 
			var orgId=$("#orgId").val();
			//getCourseSortTreeDate('${orgId}');
			var catalogType = $("#catalogType").val();//课程类型
			if(catalogType!=null&&"null"!=catalogType){
				var target = document.getElementById(catalogType).nodeName;
				var tp = document.getElementById(catalogType).parentNode.parentNode.parentNode;
				$("#courseSortId").val(catalogType);
				courseCateView(orgId,'0A');
				document.getElementById("cateAll").style.color="";//---
				var temp = document.getElementsByName('cataname');
				for(var te1=0;te1<temp.length;te1++){
					temp[te1].style.color="";
				}
				document.getElementById(catalogType).style.color="#000000";
				if(tp&&tp.nodeName == 'UL'){
					if(tp.style.display != 'block' ){
						tp.style.display = 'block';
					}else{
						tp.style.display = 'none';
					}
				}
			}else{
				getCoursesList(orgId,'0A');
			}
		});

		function courseListMsgClose(){
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

</html>
