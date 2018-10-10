<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>配置班级课程</title>
    <%@include file="../public/header.jsp" %>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/grade/js/gradeCourse.js"></script>
	 <link href="${pageContext.request.contextPath}/static/grade/css/configure.css" rel="stylesheet" type="text/css" />
</head>
<body class="easyui-layout">
	<div class="easyui-layout" style="width:100%;height:100%;">
		<div data-options="region:'north'" style="height:70px">
			<span style="padding-left:400px; font-size: 20px;font-weight: bold; ">当前班级：${grade.gradeName}</span>
			<div style="margin-bottom:0;margin-top: 2px;">
				<a href="javascript: " class="easyui-linkbutton c2" iconCls="icon-tip" plain="true">配置课程</a>
				<a href="javascript: configureGradeUser(${grade.gradeId })" class="easyui-linkbutton c4" iconCls="icon-tip" plain="true">配置学员</a>
				<a href="javascript: configureGradeExam(${grade.gradeId })" class="easyui-linkbutton c4" iconCls="icon-tip" plain="true">配置考试</a>
				<a href="javascript: configureGradeHeadTeacher(${grade.gradeId })" class="easyui-linkbutton c4" iconCls="icon-tip" plain="true">配置班主任</a>
				<a href="javascript: backToGradeList()" class="easyui-linkbutton c5" iconCls="icon-undo" plain="true">返回列表</a>
			</div>
		</div>
		<!-- Center Start -->
		<div data-options="region:'center',title:'操作'">
			<div style="padding:40% 0; text-align:center; margin-top:180px">
				<a href="javascript: addGradeCourse(${grade.gradeId })" ><img src="${pageContext.request.contextPath}/static/admin/images/right.png"/></a><br><br><br><br>
				<a href="javascript: removeGradeCourse(${grade.gradeId })"><img src="${pageContext.request.contextPath}/static/admin/images/left.png"/></a>
			</div>
		</div>
		<!-- Center End -->
		<!-- East Start -->
		<div data-options="region:'east',split:true,collapsible: false" title="班级已配置课程" style="width:47%;">
			<div>
				<input id="courseNameInGrade" class="easyui-textbox" data-options="prompt:'课程名称...'" style="width:200px;height:24px;">
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearchCourseName()">查询</a>
				<a href="javascript: setCourseType(${grade.gradeId },1)" iconCls="icon-add" class="easyui-linkbutton c6">&nbsp;设为必修&nbsp;</a>
				<a href="javascript: setCourseType(${grade.gradeId },2)" iconCls="icon-add" class="easyui-linkbutton c6">&nbsp;设为选修&nbsp;</a>
			</div>
			<div data-options="region:'center',border:false" style="height:95%">
				<div class="easyui-layout" data-options="fit:true">
					<table id="gradeCoursed" style="width:400px;height:350px;" class="easyui-datagrid"
						toolbar="#tb" data-options="
						url:'${pageContext.request.contextPath}/gradeCourse/getGradeCourseList?gradeId=${grade.gradeId }',
						method:'post',
						singleSelect:false,
						collapsible:true,
						fit:true,
						fitColumns:true,
						pagination:true,
						pageSize:5,
						pageList:[20,25,30],
						rownumbers:true,
						">
						<thead>
							<tr>
								<th data-options="field:'courseId',checkbox:true"></th>
								<th data-options="field:'courseName',align:'center'" width="150">课程名称</th>
								<th data-options="field:'courseTimeLong',align:'center'" width="60">课程时长</th>
								<th data-options="field:'credit',align:'center'" width="50">课程学时</th>
								<th data-options="field:'gcState',align:'center'" formatter="formatCourseType" width="50">课程类型</th>
								<th data-options="field:'cwType',align:'center'" formatter="formatCourse" width="50">课件类型</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
		<!-- East End -->
		<!-- West Start -->
		<div data-options="region:'west',split:true,collapsible: false" title="班级可选课程" style="width:47%">
			<div>
				<input id="courseName" class="easyui-textbox" data-options="prompt:'课程名称...'" style="width:200px;height:24px;">
				<!--
				<select id="courseSortTree" class="easyui-combotree" data-options="url:'${pageContext.request.contextPath}/course/getCourseSortListJson',method:'get',prompt:'选择分类...'" multiple style="width:260px;"></select>
				 -->
				<input type="text" id="selCourseSortTree" onclick="showCourseSortTree()">
				<ul id="treeDemo" class="ztree" style="display:none; position:absolute; left:206px; top:50px; z-index:9999; border:1px solid #ddd; background:#fff; min-width:200px;overflow-y:scroll;"></ul>
				<input type="hidden" id="selCourseSortCode">
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
			</div>
			<div data-options="region:'center',border:false" style="height:95%">
					<div class="easyui-layout" data-options="fit:true">
					<table id="gradeCourseing" style="width:400px;height:350px;" class="easyui-datagrid"
						toolbar="#tb2" data-options="
						url:'${pageContext.request.contextPath}/gradeCourse/gradeSelCourseList?gradeId=${grade.gradeId }',
						method:'post',
						singleSelect:false,
						collapsible:true,
						fit:true,
						fitColumns:true,
						pagination:true,
						pageSize:5,
						pageList:[20,25,30],
						rownumbers:true,
						">
						<thead>
							<tr>
								<th data-options="field:'courseId',checkbox:true"></th>
								<th data-options="field:'courseName',align:'center'" width="150">课程名称</th>
								<th data-options="field:'courseTimeLong',align:'center'" width="60">课程时长</th>
								<th data-options="field:'credit',align:'center'" width="50">课程学时</th>
								<th data-options="field:'courseType',align:'center'" formatter="formatCourse" width="50">课件类型</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
		<!-- West End -->
	</div>
</body>
</html>