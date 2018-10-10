<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../public/header.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/statistics/js/userOnlineStatistics.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'west',split:true,title:'机构列表'" style="width: 260px; padding: 10px;">
		<div>
			<ul id="treeDemo" class="ztree"></ul>
		</div>
	</div>
	<!-- 工具栏 -->
	<div id="tb" style="padding:15px 10px 10px 10px;height:auto; overflow:hidden; ">
		<div style="float:left;">
			<input id="username" class="easyui-textbox" data-options="prompt:'用户名或用户真实姓名...'" style="width:150px; height: 24px;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
		</div>
	</div>
	<div id="mainText" data-options="region:'center'">
		<table id="userOnlineStatisticsListTable" style="width: 800px; height: 350px;"
			class="easyui-datagrid" toolbar="#tb"
			data-options="
					url:'${pageContext.request.contextPath}/statistics/getUserOnlineStatisticsListJson',
					method:'post',
					singleSelect:false,
					collapsible:true,
					fit:true,
					fitColumns:true,
					pagination:true,
					pageSize:20,
					pageList:[20,25,30],
					rownumbers:true
					">
			<thead>
				<tr>
					<th data-options="field:'userName',align:'center'" width="100">用户名</th>
					<th data-options="field:'userRealName'" align="center" width="100">真实姓名</th>
					<th data-options="field:'lastCheckDate'" formatter="formatDateConversion" align="center" width="100">登录时间</th>
					<th data-options="field:'orgName'" align="center" width="100">机构名称</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
</html>