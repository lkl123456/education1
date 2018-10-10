<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.adks.web.util.BaseConfigHolder"%>
<%@ include file="../../static/common/common.jsp"%>
<%@ include file="../../static/common/script.jsp"%>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="<%=contextPath %>/static/css/courseInfo.css" />
	<style type="text/css">
		.help{ width:128px; position:fixed; right:-129px; top:260px;}
		.hel{ position:absolute; right:128px; top:15px;}
		.helpy{ width:128px; background:#fff; border:#CCC solid 1px; float:left; border-right:none; }
		.helpy ul{ background:url() 15px 9px no-repeat;}
		.helpy ul li{ height:40px; line-height:40px; padding-left:50px; border-bottom:#CCC dotted 1px;}
		.helpy ul li a{ color:#666}
		.helpy ul li a:hover{ color:#c2340e}
		.helpy div a:link{ color:#555}
		.helpy div a:visited{ color:#555}
		.helpy div a:hover{ color:#c2340e}

		.helpy div{ background:#efefef; height:31px; line-height:31px; padding-left:47px; font-weight:bold; color:#555; background:url() 15px no-repeat;}
		.he00{position:absolute; right:128px; top:111px; }
		.he11{position:absolute; right:128px; top:143px; }
	</style>
	
	<script type="text/javascript">
    
	function zk(){
			document.getElementById('sq').style.display='inline';
			document.getElementById('zk').style.display='none';
	}
	function sq(){
			document.getElementById('sq').style.display='none';
			document.getElementById('zk').style.display='inline';
	}
	function zkk(){
			document.getElementById('sqq').style.display='inline';
			document.getElementById('zkk').style.display='none';
	}
	function sqq(){
			document.getElementById('sqq').style.display='none';
			document.getElementById('zkk').style.display='inline';
	}
	

	</script>
</head>

<body style="background:url(../images/body.png) repeat-x">

<div class="kcbf">

	         

	<div class="kctop">${course.courseName }  </div>
	<div class="bfl" id="divleftTop">
	  <dl class="bfdl">
	    <dd class="bfd1" style=" position: relative;"> <a href="#" onclick="BeginStudy('${course.courseId}','${course.courseCode}','${course.courseType}','${course.courseName}');">
	    <img src="<%=imgServerPath%>${course.coursePic}" width="186" height="118" />
	    <span class="tpo"></span>
	    </a>
	      <div class="wangle">
	        <p style=" float:left; width:200px;"> <span>讲 师：</span>${course.authorName } <br />
	          <span>类  型：</span>
	          	 <c:if test="${course.courseType == 170}">SCORM课件</c:if>
	          	 <c:if test="${course.courseType == 171}">普通视频</c:if>
	          	 <c:if test="${course.courseType == 683}">微课</c:if>
	          <br/>
	          <span>播  放：${course.courseClickNum } </span><br />
	          <!-- <span>评  论：100 </span> -->
	        </p>
	        <p>
	          <span>分 类：</span> <a style="background: none;float: none;" href="#"/>${course.courseSortName } </a><br />
	          <span>收 藏：${course.courseCollectNum }</span><br />
	          <span>发布时间：<fmt:formatDate value="${course.createTime}"  type="date" pattern="yyyy-MM-dd" />  </span>
	         </p>
	        <p style=" float:left; width:200px;"> 
	          <span>所属机构：</span>${course.orgName } <br />
	        </p>
	        <br />
	         <span>简 介:</span> 
	                 <c:if test="${fn:length(course.courseDes)>100}">
	                                 ${fn:substring(course.courseDes,0,100)}   
	                 <b id="zk" onclick="zk()"><span style="color:#333">...</span>更多>></b>
	                 <i id="sq" style="color:#333" >
	                 ${fn:substring(course.courseDes,100,fn:length(course.courseDes))}
	                 <strong id="shou" onclick="sq()">收起&lt;&lt;</strong></i>
	                 </c:if>
	                 <c:if test="${fn:length(course.courseDes)<100}">
	                      ${course.courseDes}
	                 </c:if>
	                 <!-- 
	                <b id="zk" onclick="zk()"><span style="color:#333">${course.courseDes}</span>展开>></b>
	                 <i id="sq" style="color:#333" >
	                
	                 <strong id="shou" onclick="sq()">收起&lt;&lt;</strong></i>
	                  -->          
	         <div style=" position: relative; height: 36px; padding: 15px 0 15px;">       
	        <input name="input2" class="bf5" type="button" onclick="BeginStudy('${course.courseId}','${course.courseCode}','${course.courseType}','${course.courseName}');"   />
	          <%-- <input class="bf2" name="input2" type="button" onclick="javascript:addUserCollection(${course.courseId});"  /> --%>
          	</div> 
          </div>
        </dd>
        <dd class="gaibian">
	    	<h2><b>系列课程</b></h2>
	    	<div style=" ">
	    	<ol>
	    	<c:if test="${empty nameLikedCourseList }"></c:if>
	    	<c:if test="${!empty nameLikedCourseList }">
	    	<c:forEach items="${nameLikedCourseList}" var="course">
	    	
	    		<li><a href="#" onclick="BeginStudy('${course.courseId}','${course.courseCode}','${course.courseType}','${course.courseName}');"><img src="<%=imgServerPath%>${course.coursePic}" width="143" height="91" />
	    		<span></span>
	    		</a>
	    		<a href="<%=contextPath %>/course/courseInfo/${course.courseId}/${orgId }.shtml" title="${course.courseName }" style="line-height: 14px; display:block; padding: 0; font-size: 12px; width:148px; text-align:center; overflow: hidden; white-space: nowrap; text-overflow:ellipsis;" >${course.courseName }</a>
	    		</li>
	    	</c:forEach>
	    	</c:if>
	    	</ol>
	    	</div>
	    </dd>
        <!-- 
	    <iframe scrolling="no" frameborder="0" id="abumCourseFrame"  name="abumCourseFrame" src="" style="width:725px; height:380px; float:left; overflow:hidden; border:0px; "></iframe>
	    -->
      </dl>
	 <!--  <iframe scrolling="no" frameborder="0" id="abumDiscussFrame"  name="abumDiscussFrame" src="" style="width:725px; float:left; overflow:hidden; border:0px; "></iframe>
     -->
     </div>
  <div class="bfr"  id="divRight">
    	<dl class="jsjs">
        	<dt><h2>主讲人</h2></dt>
            <dd style="overflow:hidden">
           	  <div class="jsjs1"> <a href="<%=contextPath %>/author/authorInfo/${author.authorId}/${orgId }.shtml" > <img src="<%=imgServerPath%>/${author.authorPhoto}" width="75" height="76" /></a>
                    <span>讲师：</span><b><a href="<%=contextPath %>/author/authorInfo/${author.authorId}/${orgId }.shtml">${author.authorName }</a>  </b><br />
				  <ol style=""><span>简介：</span>
				   <c:if test="${fn:length(author.authorDes) > 30}">
				   		${fn:substring(author.authorDes,0,30)}
				   </c:if>
				  <b id="zkk" onclick="zkk()">更多>></b> <i id="sqq" > ${fn:substring(author.authorDes,30,fn:length(author.authorDes)) } <strong id="shouu" onclick="sqq()">收起&lt;&lt;</strong></i> 
                  </ol>
            </div>
            </dd>
        </dl>
        
       <dl style="margin-top: 15px;" class="jsjs">
        	<dt><h2>讲师主讲</h2></dt>
            <dd style="height: 206px;"><ul>
            <c:forEach items="${authorCourseList}" var="course" begin="0" end="${length }" >
           	  <li class="du">
           	  <a href="#" onclick="BeginStudy('${course.courseId}','${course.courseCode}','${course.courseType}','${course.courseName}');" title="${course.courseName}" ><img src="<%=imgServerPath%>${course.coursePic}" width="106" height="73" /></a>
           	  <a href="#" onclick="BeginStudy('${course.courseId}','${course.courseCode}','${course.courseType}','${course.courseName}');" title="${course.courseName}" style=" background: none; display:block; height:40px; overflow:hidden; float: none;" href="javascript:" >${course.courseName } 
			   </a>
			   <span style="display: block; overflow:hidden; white-space: nowrap; text-overflow:ellipsis;"><a style="background: none;float: none;padding-right: 2px;" href="#"/></a><a style="background: none;float: none;" href="#">${course.courseSortName }</a></span>
                  <span><img src="<%=contextPath %>/static/images/ztxx_166.jpg" /> ${course.courseClickNum }</span>
              </li>
            </c:forEach>
               </ul>
            </dd>
    </dl> 
    </div>
	<%-- <input type="hidden" id="userId" value="${user.id}" /> --%>
    <input type="hidden" id="sessionId" value="<%=session.getId()%>" />
</div>
<!-- 
 <iframe scrolling="no" frameborder="0" src="#" style="width:100%;; float:left; height:130px; overflow:hidden; border:0px; "></iframe>	
	 -->
		<!-- 评论弹出开始 -->
		<!-- 
		<div class="pinglun" id="pinglunList"
			style="display: none; margin:0px auto;">
			<div class="top">
				<span>发表评论</span><a href="javascript:hide_course('pinglunList');"></a>
			</div>
			   	    <textarea class="dhsj" style="width: 600px; margin:10px 0px 0px 30px;" id="content"  name="content"></textarea><br />
                   <font color="red"> <span id="message" style=" margin-left: 30px;  margin-top:8px; float:left"></span></font>
                   <input class="fbpl" name="addDiscussBtn" style=" margin-right: 39px"  onclick="addDiscuss();" type="button" />
			</div>
		 -->
		<!-- 评论弹出结束 -->

		<!-- 选择学习列表DIV -->
		<!-- 
		<div class="black_overlay" id="black_overlayTest" style="display: none; position:fixed;"></div>
		<div class="tanchuceng" id="categoryPlayDivTest" style="display: none; top: 260px; width: 281px; min-height:245px; height: 245px; position:fixed;">
			<div class="top">
				<span>学习列表</span><a href="#" onclick="testChange_hide('categoryPlayDivTest')"></a>
			</div>
			<p style="color: #888; margin-top: 2px;margin-left: 8px; height: 18px;">提示：选择列表后，该专辑下的所有课程都会加入</p>
			<iframe frameborder="0" id="ifrm" scrolling="yes" onclick="testChange_hide('categoryPlayDivTest')" style="width: 280px; height: 187px; overflow:hidden; margin: 0 auto" src=""></iframe>
		</div>
		
		<div class="tanchuceng" id="categoryPlayDivTest_course" style="display: none; top: 260px; width: 281px; min-height:245px; height: 245px; position:fixed;">
			<div class="top">
				<span>学习列表</span><a href="#" onclick="testChange_hide('categoryPlayDivTest_course')"></a>
			</div>
			<p style="color: #888; margin-top: 2px;margin-left: 8px; height: 18px;">提示：选择列表后，该课程都会加入</p>
			<iframe frameborder="0" id="ifrm_course" scrolling="yes" onclick="testChange_hide('categoryPlayDivTest_course')" style="width: 280px; height: 187px; overflow:hidden; margin: 0 auto" src=""></iframe>
		</div>

		 -->

</body>
</html>
