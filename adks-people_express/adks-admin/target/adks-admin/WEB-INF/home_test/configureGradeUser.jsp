<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript">    	
	var contextpath = '${pageContext.request.contextPath}';
</script>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>配置培训学员</title>
    <link href="${pageContext.request.contextPath}/static/admin/css/default.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/icon.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/ui-cupertino/easyui.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/color.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/zTree/jquery.ztree.all-3.5.min.js"></script>
    <link href="${pageContext.request.contextPath}/static/admin/zTree/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/grade/gradeUser.js"></script>
</head>
<body class="easyui-layout">

	<div data-options="region:'north',split:true,border:false" style="height:70px;background:#FDF5E6;padding:2px">
		
       <span style="padding-left:400px; font-size: 20px;font-weight: bold; ">当前培训项目：${grade.name }</span>
		<div style="margin-bottom:0;margin-top: 2px;">
			<a href="javascript: configureGradeCourse(${grade.id })" class="easyui-linkbutton c4" iconCls="icon-tip" plain="true">配置课程</a>
			<a href="javascript: " class="easyui-linkbutton c2" iconCls="icon-tip" plain="true">配置学员</a>
			<a href="javascript: backToGradeList()" class="easyui-linkbutton c5" iconCls="icon-undo" plain="true">返回列表</a>
		</div>
	</div>
	
	<!-- 工具栏 -->
	<div id="tb" style="padding:5px;height:auto; ">
		<div style="margin-bottom:5px">
			<span class="easyui-linkbutton c6">班级已配置学员：</span>
		</div>
		<div>
			<input id="userNameInGrade" class="easyui-textbox" data-options="prompt:'真实姓名...'" style="width:200px;height:24px;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearchUserName()">查询</a>
			<a href="javascript: removeGradeUser(${grade.id })" iconCls="icon-remove" class="easyui-linkbutton c5">&nbsp;&nbsp;移除选中&nbsp;&nbsp;</a>
		</div>
	</div>
	<div id="tb2" style="padding:5px;height:auto; ">
		<div style="margin-bottom:5px">
			<span class="easyui-linkbutton c6">班级可选用户：</span>
		</div>
		<div>
			<input id="userName" class="easyui-textbox" data-options="prompt:'用户名...'" style="width:200px;height:24px;">
			<select id="userDeptTree" class="easyui-combotree" data-options="url:'/dept/getDeptTreeJson',method:'get',prompt:'选择部门...'" multiple style="width:260px;"></select>
			
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
			<a href="javascript: addGradeUser(${grade.id })" iconCls="icon-add" class="easyui-linkbutton c6">&nbsp;&nbsp;添加选中&nbsp;&nbsp;</a>
		</div>
	</div>
	
	<div id="mainText" data-options="region:'center',border:false">
			
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',split:true,collapsed:false,border:false" style="background:#eee;height:50%;">
				<table id="gradeUsered" style="width:400px;height:350px;" class="easyui-datagrid"
					toolbar="#tb" data-options="
					url:'/gradeUser/gradeUserList?gradeId=${grade.id }',
					method:'get',
					singleSelect:false,
					collapsible:false,
					fit:true,
					fitColumns:false,
					pagination:true,
					pageSize:5,
					pageList:[5,10,20,30],
					rownumbers:true,
					">
				<thead>
					<tr>
						<th data-options="field:'id',checkbox:true"></th>
						<th data-options="field:'login_name',align:'center'" width="120">用户名</th>
						<th data-options="field:'user_name',align:'center'" width="120">真实姓名</th>
						<th data-options="field:'dept_name',align:'center'" width="300">所属部门</th>
						<th data-options="field:'office',align:'center'" width="100">职务</th>
						<th data-options="field:'rank',align:'center'" width="100">职级</th>
						<th data-options="field:'email',align:'center'" width="260">邮箱</th>
						<th data-options="field:'telphone',align:'center'" width="100">电话</th>
					</tr>
				</thead>
			</table>
			</div>
			<div data-options="region:'center',border:false" style="height:50%">
				<div class="easyui-layout" data-options="fit:true">
					<table id="gradeUsering" style="width:400px;height:350px;" class="easyui-datagrid"
							toolbar="#tb2" data-options="
							url:'/gradeUser/gradeSelUserList?gradeId=${grade.id }',
							method:'get',
							singleSelect:false,
							collapsible:true,
							fit:true,
							fitColumns:false,
							pagination:true,
							pageSize:5,
							pageList:[5,10,20,30],
							rownumbers:true,
							">
						<thead>
							<tr>
								<th data-options="field:'id',checkbox:true"></th>
								<th data-options="field:'login_name',align:'center'" width="120">用户名</th>
								<th data-options="field:'user_name',align:'center'" width="120">真实姓名</th>
								<th data-options="field:'dept_name',align:'center'" width="300">所属部门</th>
								<th data-options="field:'office',align:'center'" width="100">职务</th>
								<th data-options="field:'rank',align:'center'" width="100">职级</th>
								<th data-options="field:'email',align:'center'" width="260">邮箱</th>
								<th data-options="field:'telphone',align:'center'" width="100">电话</th>
								<th data-options="field:'status',align:'center'" width="80">状态</th>
							</tr>
						</thead>
					</table>
			</div>
		</div>
	</div>
	
</body>
</html>