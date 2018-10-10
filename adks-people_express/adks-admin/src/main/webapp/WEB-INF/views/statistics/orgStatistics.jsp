<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<title>机构学习统计</title>
<%@include file="../public/header.jsp"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/zTree/jquery.ztree.all-3.5.min.js"></script>
<link href="${pageContext.request.contextPath}/static/admin/zTree/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/static/statistics/js/orgStatistics.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'west',split:true,title:'机构列表'"
		style="width: 260px; padding: 10px;">
		<div>
			<ul id="treeDemo" class="ztree"></ul>
		</div>
	</div>

	<!-- 工具栏 -->
	<div id="tb" style="padding:15px 10px 10px 10px;height:auto; overflow:hidden; ">
		<div style="float:left;">
			<input id="s_org_name" class="easyui-textbox" data-options="prompt:'用户名称...'" style="width: 200px; height: 24px;" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a> 
		</div>
		
	</div>
	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<div title="数据列表" data-options="iconCls:'icon icon-nav',fit:true">
			<table id="cslistdatagrid" style="width: 800px; height: 350px"
				class="easyui-datagrid" toolbar="#tb"
				data-options="
					url:'${pageContext.request.contextPath}/orgStatistics/getOrgStatisticsListJson',
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
						<th data-options="field:'userId',checkbox:true"></th>
						<th data-options="field:'userName'" align="center" width="100">用户名称</th>
						<th data-options="field:'requiredPeriod',align:'center'" width="100">必修学时</th>
						<th data-options="field:'optionalPeriod',align:'center'" width="100">选修学时</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

</body>
</html>