<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>课程管理</title>
<%@include file="../public/header.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/zTree/jquery.ztree.all-3.5.min.js"></script>
<link href="${pageContext.request.contextPath}/static/admin/zTree/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/static/course/addCourse.js"></script>
<style>
  .loading1{width: 400px; margin-top:10px; height:20px; text-align:center; border:1px solid #aed0ea;}
  .loading2{width: 400px; margin-top:10px; height:20px; background:url(../static/admin/images/animated-overlay.gif) repeat-x}
</style>
</head>
<body>
	<div style="padding-top: 2px;">
		<input type="hidden" id="ossPath" value="${ossResource }" />
		<form id="courseAddForm" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/course/saveCourse">
			<input type="hidden" id="courseId" name="courseId" value="${course.courseId }"/>
			<input type="hidden" id="imgServer" value="${imgServer}" />
			<input type="hidden" id="orgNameTrue" value="${course.orgName }" /> 
			<table cellpadding="1">
				<a class="easyui-linkbutton c4">课程信息</a>
				<tr>
					<td width="150px" align="right">课程类型:</td>
					<td><input type="radio" id="courseType" name="courseType" value="170"
						<c:if test="${empty course.courseType || course.courseType eq 170}">checked="checked"</c:if>>三分屏&nbsp;&nbsp;
						<input type="radio" id="courseType" name="courseType" value="171"
						<c:if test="${!empty course.courseType && course.courseType eq 171}">checked="checked"</c:if>>单视频&nbsp;&nbsp;
						<input type="radio" id="courseType" name="courseType" value="683"
						<c:if test="${!empty course.courseType && course.courseType eq 683}">checked="checked"</c:if>>微课&nbsp;&nbsp;
						<%-- <input type="radio" id="courseType" name="courseType" value="3"
						<c:if test="${!empty course.courseType && course.courseType eq 4}">checked="checked"</c:if>>多媒体&nbsp;&nbsp; --%>
					</td>
				</tr>
				<tr>
					<td  width="150px" align="right">课程名称:</td>
					<td><input class="easyui-textbox" type="text" id="courseName"name="courseName" data-options="required:true,validType:['isNullOrEmpty','length[1,64]']" 
						value="${course.courseName }" style="width: 238px;"></input>
					</td>
				</tr>
				<tr>
					<td width="150px" align="right">所属机构:</td>
					<td><input type="text" id="orgName"  onclick="showOrgTree();" value="${course.orgName }" <c:if test="${!empty course.orgId }">disabled="disabled"</c:if> ></input><span style="color: red"> * </span>
						<ul id="treeDemo1" class="ztree" style="display:none;"></ul>
						<input type="hidden" id="orgId" name="orgId" value="${course.orgId }">
						<input type="hidden" id="orgCode" name="orgCode" value="${course.orgCode }">
						<input type="hidden" id="orgNameShow" name="orgName" value="${course.orgName }">
					</td>
				</tr>
				<tr>
					<td width="150px" align="right">所属分类:</td>
					<td><input class="easyui-textbox" type="text" id="courseSortNameShow" value="${course.courseSortName }" style="width: 238px;" disabled="disabled"></td>
					<input type="hidden" id="courseSortId" name="courseSortId" value="${course.courseSortId }">
					<input type="hidden" id="courseSortCode" name="courseSortCode" value="${course.courseSortCode }">
					<input type="hidden" id="courseSortName" name="courseSortName" value="${course.courseSortName }">
				</tr>
				<tr>
					<td width="150px" align="right">讲师:</td>
					<td><input type="text" id="showAuthorName" value="${course.authorName }" disabled="disabled" style="width: 58px;"></input> 
						<a id="uploadfiles" href="javascript:checkAuthor();" class="easyui-linkbutton c5">选择讲师</a> 
						<input type="hidden" id="authorId" name="authorId" value="${course.authorId }">
						<input type="hidden" id="authorName" name="authorName" value="${course.authorName }">
					</td>
				</tr>
				<tr>
					<td width="150px" align="right">课程图片:</td>
					<td><img id="ciPath" height="86" width="131" /> <br /> 
						<input class="easyui-filebox" id="courseImgfile" name="courseImgfile" data-options="prompt:'选择一张课程图片...'" 
						style="width: 80%"> </input>
						<input type="hidden" id="coursePic" name="coursePic" value="${course.coursePic }" />
					</td>
				</tr>
				<tr>
					<td width="150px" align="right">课程时长:</td>
					<td><input class="easyui-textbox" type="text" id="courseTimeLong" name="courseTimeLong" value="${course.courseTimeLong }" style="width: 238px;">
						</input> 例如： 00:00:00</td>
				</tr>

				<tr>
					<td width="150px" align="right">上传视频:</td>
					<td> 
						<!--<pre id="console"></pre>
						<div id="filelist"></div> -->
						<!-- <a id="pickfiles" href="javascript:void(0);" class="easyui-linkbutton">选择视频</a> 
						<a id="uploadfiles" href="javascript:void(0);" class="easyui-linkbutton c5">开始上传</a> -->
						<input class="easyui-filebox" id="coursefile" name="coursefile" data-options="prompt:'选择课程...'" 
						style="width: 80%"> </input>
						<div id="pload" class="loading1">0%</div>
						<!-- <div id="pload"  class="loading2"></div> -->
						<input type="hidden" id="courseCode" name="courseCode" value="${course.courseCode }">
					</td>
				</tr>
				<tr>
					<td width="150px" align="right">是否推荐:</td>
					<td><input type="radio" id="isRecommend" name="isRecommend" value="1"
						<c:if test="${empty course.isRecommend || course.isRecommend eq 1}">checked="checked"</c:if>>推荐&nbsp;&nbsp;
						<input type="radio" id="isRecommend" name="isRecommend" value="2"
						<c:if test="${!empty course.isRecommend && course.isRecommend eq 2}">checked="checked"</c:if>>不推荐&nbsp;&nbsp;
					</td>
				</tr>
				<tr>
					<td width="150px" align="right">是否公开:</td>
					<td><input type="radio" id="courseBelong" name="courseBelong" value="1"
						<c:if test="${empty course.courseBelong || course.courseBelong eq 1}">checked="checked"</c:if>>公开&nbsp;&nbsp;
						<input type="radio" id="courseBelong" name="courseBelong" value="2"
						<c:if test="${!empty course.courseBelong && course.courseBelong eq 2}">checked="checked"</c:if>>专属&nbsp;&nbsp;
						<input type="radio" id="courseBelong" name="courseBelong" value="3"
						<c:if test="${!empty course.courseBelong && course.courseBelong eq 3}">checked="checked"</c:if>>私密&nbsp;&nbsp;
					</td>
				</tr>
				<tr>
					<td width="150px" align="right">审核通过:</td>
					<td><input type="radio" id="isAudit" name="isAudit" value="1"
						<c:if test="${empty course.isAudit || course.isAudit eq 1}">checked="checked"</c:if>>通过&nbsp;&nbsp;
						<input type="radio" id="isAudit" name="isAudit" value="2"
						<c:if test="${!empty course.isAudit && course.isAudit eq 2}">checked="checked"</c:if>>未通过&nbsp;&nbsp;
						<input type="radio" id="isAudit" name="isAudit" value="3"
						<c:if test="${!empty course.isAudit && course.isAudit eq 3}">checked="checked"</c:if>>待审核&nbsp;&nbsp;
					</td>
				</tr>
				<tr>
					<td width="150px" align="right">课程状态:</td>
					<td><input type="radio" id="courseStatus" name="courseStatus" value="1"
						<c:if test="${empty course.courseStatus || course.courseStatus eq 1}">checked="checked"</c:if>>激活&nbsp;&nbsp;
						<input type="radio" id="courseStatus" name="courseStatus" value="2"
						<c:if test="${!empty course.courseStatus && course.courseStatus eq 2}">checked="checked"</c:if>>冻结&nbsp;&nbsp;
					</td>
				</tr>
				<tr>
					<td width="150px" align="right">课程简介:</td>
					<td><input class="easyui-textbox" data-options="multiline:true" name="courseDes"
						style="height: 90px; width: 238px;" value="${course.courseDes }"></input>
					</td>
				</tr>
			</table>
			<div>
				<input type="button" id="submitAdd" value="保存"  onclick="addCourseOk()"/>  &nbsp;&nbsp;
				<input type="button" id="cancelAdd" value="取消"   onclick="addCourseCancle()"/>  
			</div>
		</form>
	</div>
</body>
</html>