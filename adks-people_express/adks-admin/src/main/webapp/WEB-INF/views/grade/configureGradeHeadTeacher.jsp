<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>配置班级学员</title>
    <%@include file="../public/header.jsp" %>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/grade/js/gradeHeadTeacher.js"></script>
	<link href="${pageContext.request.contextPath}/static/grade/css/configure.css" rel="stylesheet" type="text/css" />
</head>
<body class="easyui-layout">
<div class="easyui-layout" style="width:100%;height:100%;">
		<div data-options="region:'north'" style="height:70px">
			<span style="padding-left:400px; font-size: 20px;font-weight: bold; ">当前培训项目：${grade.gradeName }</span>
			<div style="margin-bottom:0;margin-top: 2px;">
				<a href="javascript: configureGradeCourse(${grade.gradeId })" class="easyui-linkbutton c4" iconCls="icon-tip" plain="true">配置课程</a>
				<a href="javascript: configureGradeUser(${grade.gradeId })" class="easyui-linkbutton c4" iconCls="icon-tip" plain="true">配置学员</a>
				<a href="javascript: configureGradeExam(${grade.gradeId })" class="easyui-linkbutton c4" iconCls="icon-tip" plain="true">配置考试</a>
				<a href="javascript: " class="easyui-linkbutton c2" iconCls="icon-tip" plain="true">配置班主任</a>
				<a href="javascript: backToGradeList()" class="easyui-linkbutton c5" iconCls="icon-undo" plain="true">返回列表</a>
			</div>
		</div>
		<!-- Center Start -->
		<div data-options="region:'center',title:'操作'">
			<div style="padding:40% 0; text-align:center; margin-top:180px">
				<a href="javascript: addGradeHeadTeacher(${grade.gradeId })"  ><img src="${pageContext.request.contextPath}/static/admin/images/right.png"/></a><br><br><br><br>
				<a href="javascript: removeGradeHeadTeacher(${grade.gradeId })"><img src="${pageContext.request.contextPath}/static/admin/images/left.png"/></a>
			</div>
		</div>
		<!-- Center End -->
		<!-- East Start -->
		<div data-options="region:'east',split:true,collapsible: false" title="班级已配置学员" style="width:47%;">
			<div>
				<input id="userNameInGrade" class="easyui-textbox" data-options="prompt:'真实姓名...'" style="width:200px;height:24px;">
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearchUserName()">查询</a>
			</div>
			<div data-options="region:'center',border:false" style="height:95%">
			<div class="easyui-layout" data-options="fit:true">
				<table id="gradeHeadTeachered" style="width:400px;height:350px;" class="easyui-datagrid"
						toolbar="#tb" data-options="
						url:'${pageContext.request.contextPath}/gradeUser/gradeHeadTeacherList?gradeId=${grade.gradeId }',
						method:'post',
						singleSelect:false,
						collapsible:false,
						fit:true,
						fitColumns:true,
						pagination:true,
						pageSize:1,
						pageList:[1],
						rownumbers:true,
						">
					<thead>
						<tr>
							<th data-options="field:'userId',checkbox:true"></th>
							<th data-options="field:'userName',align:'center'" width="120">用户名</th>
							<th data-options="field:'userRealName',align:'center'" width="120">真实姓名</th>
							<th data-options="field:'orgName',align:'center'" width="300">所属部门</th>
							<th data-options="field:'rankName',align:'center'" width="100">职级</th>
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
				<input id="userRealName" class="easyui-textbox" data-options="prompt:'真实姓名...'" style="width:200px;height:24px;">
				<!-- 
				<select id="userOrgTree" class="easyui-combotree" data-options="url:'${pageContext.request.contextPath}/org/getOrgsJson',method:'get',prompt:'选择机构...'" multiple style="width:260px;"></select>
				 -->
				<input type="text" id="selUserOrgTree" onclick="showOrgTree1()">
				<ul id="treeDemo1" class="ztree" style="display:none; position:absolute; left:206px; top:50px; z-index:9999; border:1px solid #ddd; background:#fff; min-width:200px;overflow-y:scroll;"></ul>
				<input type="hidden" id="selUserOrgTreeOrgCode">
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
			</div>
			<div data-options="region:'center',border:false" style="height:90%">
			<div class="easyui-layout" data-options="fit:true">
					<table id="gradeHeadTeachering" style="width:400px;height:350px;" class="easyui-datagrid"
								toolbar="#tb2" data-options="
								url:'${pageContext.request.contextPath}/gradeUser/gradeSelHeadTeacherPage?gradeId=${grade.gradeId }',
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
									<th data-options="field:'userId',checkbox:true"></th>
									<th data-options="field:'userName',align:'center'" width="120">用户名</th>
									<th data-options="field:'userRealName',align:'center'" width="120">真实姓名</th>
									<th data-options="field:'orgName',align:'center'" width="200">所属部门</th>
									<th data-options="field:'rankName',align:'center'" width="100">职级</th>
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