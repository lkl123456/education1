<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript">    	
	var contextpath = '${pageContext.request.contextPath}';
</script>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>用户管理</title>
    <%@include file="../public/header.jsp" %>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/user/js/userRoleList.js"></script>

</head>
<body class="easyui-layout">
	
	<!-- 工具栏 -->
	<div id="tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			用户名：${userName }
			<a href="#" onclick="addDialog();" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加角色</a>
			&nbsp;&nbsp;
			<a href="#" onclick="delDialog();" class="easyui-linkbutton" iconCls="icon-add" plain="true">取消角色</a>
		</div>
		<div>
			<input id="s_role_name" class="easyui-textbox" data-options="prompt:'角色名称...'" style="width:200px;height:24px;" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
		</div>
	</div>
	<input type="hidden" id="userId" value="${userId }">
	<input type="hidden" id="userName" value="${userName }">
	<input type="hidden" id="canAdd" value="">
	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<div title="数据列表信息" data-options="iconCls:'icon icon-nav',fit:true">
	<table id="roleList_table" style="width:800px;height:100%;" class="easyui-datagrid"
			toolbar="#tb" data-options="
			url:'${pageContext.request.contextPath}/role/getRoleListJson?userId=${userId }',
			method:'post',
			singleSelect:false,
			fit:true,
			fitColumns:true,
			striped:true,
			nowarp:false,
			pagination:true,
			pageSize:20,
			pageList:[20,25,30],
			rownumbers:true
			">
		<thead>
			<tr>
				<th data-options="field:'roleId',checkbox:true"></th>
				<th data-options="field:'roleName'" align="center" width="80">角色名称</th>
				<th data-options="field:'displayRef',align:'center'" width="100">显示的名称</th>
				<th data-options="field:'roleDes',align:'center'" width="100">描述</th>
				<th data-options="field:'isGlob',align:'center'" formatter="formatColumnData" width="100">是否是全局</th>
				<th data-options="field:'creatorName',align:'center'" width="100">创建人</th>
				<th data-options="field:'isCheckedOk',align:'center'" formatter="formatYes" width="100">选中</th>
			</tr>
		</thead>
	</table>
	</div>
	</div>

</body>
</html>