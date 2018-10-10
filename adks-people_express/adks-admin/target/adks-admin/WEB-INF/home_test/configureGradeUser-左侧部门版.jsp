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
			<input id="courseName" class="easyui-searchbox" data-options="prompt:'用户名...',searcher:doSearch" style="width:200px;height:24px;">
			<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<a href="javascript: removeGradeUser(${grade.id })" iconCls="icon-remove" class="easyui-linkbutton c5">&nbsp;&nbsp;移除&nbsp;&nbsp;</a>
		</div>
	</div>
	<div id="tb2" style="padding:5px;height:auto; ">
		<div style="margin-bottom:5px">
			<span class="easyui-linkbutton c6">班级可选用户：</span>
		</div>
		<div>
			<input id="courseName" class="easyui-searchbox" data-options="prompt:'用户名...',searcher:doSearch" style="width:200px;height:24px;">
			<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<a href="javascript: addGradeUser(${grade.id })" iconCls="icon-add" class="easyui-linkbutton c6">&nbsp;&nbsp;添加&nbsp;&nbsp;</a>
		</div>
	</div>
	
	<div id="mainText" data-options="region:'center',border:false">
			
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'west',collapsed:false,border:false" style="width:40%">
				<table id="gradeUsered" style="width:400px;height:350px;" class="easyui-datagrid"
					toolbar="#tb" data-options="
					url:'/gradeUser/gradeUserList?gradeId=${grade.id }',
					method:'get',
					singleSelect:false,
					collapsible:true,
					fit:true,
					fitColumns:false,
					pagination:true,
					pageSize:10,
					pageList:[10,20,30],
					rownumbers:true,
					">
				<thead>
					<tr>
						<th data-options="field:'id',checkbox:true"></th>
						<th data-options="field:'login_name',align:'center'" width="80">用户名</th>
						<th data-options="field:'user_name',align:'center'" width="80">真实姓名</th>
						<th data-options="field:'dept_name',align:'center'" width="100">所属部门</th>
						<th data-options="field:'office',align:'center'" width="100">职务</th>
						<th data-options="field:'rank',align:'center'" width="100">职级</th>
						<th data-options="field:'email',align:'center'" width="120">邮箱</th>
						<th data-options="field:'telphone',align:'center'" width="100">电话</th>
					</tr>
				</thead>
			</table>
			</div>
			<div data-options="region:'center',border:false" style="width:60%">
				<div class="easyui-layout" data-options="fit:true">
					<div data-options="region:'west',collapsed:false,border:false,title:'部门列表'" style="width:20%">
						<div style="margin-left:5px;">
						 	<a href="#" onclick="reloadTree();" class="easyui-linkbutton" iconCls="icon-reload">刷新</a>
						</div>
						<div>
							<ul id="treeDemo1" class="ztree"></ul>
						</div>
					</div>
					<div data-options="region:'center',border:false" style="width:80%">
						<table id="gradeUsering" style="width:400px;height:350px;" class="easyui-datagrid"
							toolbar="#tb2" data-options="
							url:'/gradeUser/gradeSelUserList?gradeId=${grade.id }',
							method:'get',
							singleSelect:false,
							collapsible:true,
							fit:true,
							fitColumns:false,
							pagination:true,
							pageSize:10,
							pageList:[10,20,30],
							rownumbers:true,
							">
						<thead>
							<tr>
								<th data-options="field:'id',checkbox:true"></th>
								<th data-options="field:'login_name',align:'center'" width="100">用户名</th>
								<th data-options="field:'user_name',align:'center'" width="100">真实姓名</th>
								<th data-options="field:'dept_name',align:'center'" width="100">所属部门</th>
								<th data-options="field:'office',align:'center'" width="100">职务</th>
								<th data-options="field:'rank',align:'center'" width="100">职级</th>
								<th data-options="field:'email',align:'center'" width="120">邮箱</th>
								<th data-options="field:'telphone',align:'center'" width="100">电话</th>
								<th data-options="field:'status',align:'center'" width="80">状态</th>
							</tr>
						</thead>
					</table>
					</div>
		</div>
			</div>
		</div>
	</div>
	
</body>
</html>