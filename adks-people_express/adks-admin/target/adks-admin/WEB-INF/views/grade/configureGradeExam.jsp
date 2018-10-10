<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>配置班级考试</title>
    <%@include file="../public/header.jsp" %>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/grade/js/gradeExam.js"></script>
	<link href="${pageContext.request.contextPath}/static/grade/css/configure.css" rel="stylesheet" type="text/css" />
</head>
<body class="easyui-layout">
<div class="easyui-layout" style="width:100%;height:100%;">
		<div data-options="region:'north'" style="height:70px">
			<span style="padding-left:400px; font-size: 20px;font-weight: bold; ">当前班级：${grade.gradeName}</span>
			<div style="margin-bottom:0;margin-top: 2px;">
				<a href="javascript: configureGradeCourse(${grade.gradeId })" class="easyui-linkbutton c4" iconCls="icon-tip" plain="true">配置课程</a>
				<a href="javascript: configureGradeUser(${grade.gradeId })" class="easyui-linkbutton c4" iconCls="icon-tip" plain="true">配置学员</a>
				<a href="javascript: " class="easyui-linkbutton c2" iconCls="icon-tip" plain="true">配置考试</a>
				<a href="javascript: configureGradeHeadTeacher(${grade.gradeId })" class="easyui-linkbutton c4" iconCls="icon-tip" plain="true">配置班主任</a>
				<a href="javascript: backToGradeList()" class="easyui-linkbutton c5" iconCls="icon-undo" plain="true">返回列表</a>
			</div>
		</div>
		<!-- Center Start -->
		<div data-options="region:'center',title:'操作'">
			<div style="padding:40% 0; text-align:center; margin-top:180px">
				<a href="javascript: addGradeExam(${grade.gradeId },'${grade.gradeName }')"><img src="${pageContext.request.contextPath}/static/admin/images/right.png"/></a><br><br><br><br>
				<a href="javascript: removeGradeExam(${grade.gradeId })"><img src="${pageContext.request.contextPath}/static/admin/images/left.png"/></a>
			</div>
		</div>
		<!-- Center End -->
		<!-- East Start -->
		<div data-options="region:'east',split:true,collapsible: false" title="班级已配置学员" style="width:47%;">
			<div>
				<input id="examNameInGrade" class="easyui-textbox" data-options="prompt:'考试名称...'" style="width:200px;height:24px;">
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearchCourseName()">查询</a>
			</div>
			<div data-options="region:'center',border:false" style="height:95%">
			<div class="easyui-layout" data-options="fit:true">
				<table id="gradeExamed" style="width:400px;height:350px;" class="easyui-datagrid"
						toolbar="#tb" data-options="
						url:'${pageContext.request.contextPath}/gradeExam/gradeExamList?gradeId=${grade.gradeId }',
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
							<th data-options="field:'examId',checkbox:true"></th>
							<th data-options="field:'examName',align:'center'" width="200">考试名称</th>
							<th data-options="field:'scoreSum',align:'center'" width="200">总分</th>
							<th data-options="field:'passScore',align:'center'" width="200">及格线</th>
							<th data-options="field:'examTimes',align:'center'" width="200">允许考试次数</th>
							<th data-options="field:'startDate',align:'center'" formatter="formatDateConversion" width="200">开始时间</th>
							<th data-options="field:'endDate',align:'center'" formatter="formatDateConversion" width="200">结束时间</th>
						</tr>
					</thead>
				</table>
			</div>
			</div>
		</div>
		<!-- East End -->
		<!-- West Start -->
		<div data-options="region:'west',split:true,collapsible: false" title="班级可选用户" style="width:47%">
			<div>
				<input id="examName" class="easyui-textbox" data-options="prompt:'考试名称...'" style="width:200px;height:24px;">
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
			</div>
			<div data-options="region:'center',border:false" style="height:95%">
			<div class="easyui-layout" data-options="fit:true">
					<table id="gradeExaming" style="width:400px;height:350px;" class="easyui-datagrid"
							toolbar="#tb2" data-options="
							url:'${pageContext.request.contextPath}/gradeExam/gradeSelExamList?gradeId=${grade.gradeId }',
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
								<th data-options="field:'examId',checkbox:true"></th>
								<th data-options="field:'examName',align:'center'" width="200">考试名称</th>
								<th data-options="field:'scoreSum',align:'center'" width="200">总分</th>
								<th data-options="field:'passScore',align:'center'" width="200">及格线</th>
								<th data-options="field:'examTimes',align:'center'" width="200">允许考试次数</th>
								<th data-options="field:'startDate',align:'center'" formatter="formatDateConversion" width="200">开始时间</th>
								<th data-options="field:'endDate',align:'center'" formatter="formatDateConversion" width="200">结束时间</th>
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