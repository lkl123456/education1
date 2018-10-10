<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@include file="../public/header.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/grade/js/gradeUserCourse.js"></script>
<body class="easyui-layout">
	<div class="easyui-layout" style="width:100%;height:100%;">
		<!-- North Start-->
		<div data-options="region:'north'" style="height:10%">
			<span style="padding-left:500px; font-size:20px;font-weight: bold; ">${grade.gradeName}</span>
			<div style="margin-bottom:0;margin-top:2px;padding-left:350px;color: red;">
				<span style="font-size:10px;font-weight: bold; ">必修学时:${grade.requiredPeriod},</span>
				<span style="font-size:10px;font-weight: bold; ">选修学时:${grade.optionalPeriod},</span>
				<span style="font-size:10px;font-weight: bold; ">必修课程数量:${grade.requiredNum},</span>
				<span style="font-size:10px;font-weight: bold; ">选修课程数量:${grade.optionalNum},</span>
				<span style="font-size:10px;font-weight: bold; ">结业条件:${grade.graduationDesc}</span>
			</div>
		</div>
		<!-- North End-->
		<!-- West Start-->
		<div data-options="region:'west',split:true,collapsible: false" title="学员信息" style="width:20%;">
			<div id="p" class="easyui-panel" style="width:100%;height:100%;padding:10px;background:#fafafa;">
                <p align="center">
                	<c:if test="${gradeUser.headPhoto!=null&&gradeUser.headPhoto!='' }">
                		<img src="${ossResource}${user.headPhoto}"  width="100" height="120" />
                	</c:if>
                	<c:if test="${gradeUser.headPhoto==null||gradeUser.headPhoto=='' }">
                		<img src="${pageContext.request.contextPath}/static/admin/images/userImg.jpg"  width="100" height="120" />
                	</c:if>
                </p>
			    <p>用户名：${gradeUser.uName}</p>
                <p>真实姓名：${gradeUser.userName}</p>
			    <p>所属机构：${gradeUser.orgName}</p>
			    <p>已获得必修学时：${gradeUser.requiredPeriod}</p>
			    <p>已获得选修学时：${gradeUser.optionalPeriod}</p>
			    <input type="hidden" id="gradeUserId" value="${gradeUser.gradeUserId }">
			</div>
		</div>
		<!-- West End-->
		<!-- Center Start-->
		<div data-options="region:'center',split:true,collapsible: false" title="课程" style="height:80%">
			<div>
				<input id="courseName" class="easyui-textbox" data-options="prompt:'课程名称...'" style="width:200px;height:24px;">
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
				<a href="javascript: updateGradeUserCourse(${grade.gradeId },${gradeUser.userId})" iconCls="icon-reload" class="easyui-linkbutton">&nbsp;完成学习&nbsp;</a>
			</div>
			<div data-options="region:'center',border:false" style="height:95%">
				<div class="easyui-layout" data-options="fit:true">
					<table id="gradeUserCourseListTable" style="width:800px;height:300px;" class="easyui-datagrid"
							toolbar="#tb" data-options="
							url:'${pageContext.request.contextPath}/gradeStudy/getGradeUserCourseListJson?gradeId='+${grade.gradeId }+'&userId='+${gradeUser.userId },
							method:'post',
							singleSelect:false,
							collapsible:true,
							fit:true,
							fitColumns:true,
							pagination:true,
							pageSize:20,
							pageList:[20,25,30],
							rownumbers:true,
							rowStyler:setRowStyle
							">
						<thead>
							<tr>
								<th data-options="field:'courseId',checkbox:true"></th>
								<th data-options="field:'courseName',align:'center'" width="200">课程名</th>
								<th data-options="field:'gcState',align:'center'" width="50">课程类型</th>
								<th data-options="field:'credit',align:'center'" width="50">课程学时</th>
								<th data-options="field:'courseCatalogName',align:'center'" width="100">课程分类</th>
								<th data-options="field:'isOver',align:'center'" formatter="courseUserStatus" width="70">是否完成</th>
								<!-- 
								<th data-options="field:'_'" formatter="formatOper" align="center" width="70">操作</th>
								 -->
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
		<!-- Center End-->
	</div>
</body>
</html>