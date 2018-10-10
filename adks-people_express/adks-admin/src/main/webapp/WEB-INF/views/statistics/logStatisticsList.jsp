<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../public/header.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/statistics/js/logStatistics.js"></script>
</head>
<body class="easyui-layout">
	<!-- 工具栏 -->
	<div id="tb" style="padding:15px 10px 10px 10px;height:auto; overflow:hidden; ">
		<div style="float:left;">
			模块名称：<input class="easyui-combobox" data-options="required:true,editable:false" type="text" id="ss1" >&nbsp;&nbsp;
			机构：
			<input type="text" id="selUserOrgTree" onclick="showOrgTree1()">
			<ul id="treeDemo1" class="ztree" style="display:none; position:absolute; left:206px; top:50px; z-index:9999; border:1px solid #ddd; background:#fff; min-width:200px;overflow-y:scroll;"></ul>
			<input type="hidden" id="selUserOrgTreeOrgCode">
			&nbsp;&nbsp;
			操作类型：<input class="easyui-combobox" data-options="required:true,editable:false" type="text" id="ss2" >&nbsp;&nbsp;
			操作时间：<input id="minTime" type="text" class="easyui-datebox" data-options="editable:false"></input>-
				  <input id="maxTime" type="text" class="easyui-datebox" data-options="editable:false"></input>
			&nbsp;&nbsp;<a href="" onclick="canle();">清空</a>&nbsp;
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
		</div>
	</div>
	<div id="mainText" data-options="region:'center'">
		<table id="logStatisticsListTable" style="width: 800px; height: 350px;"
			class="easyui-datagrid" toolbar="#tb"
			data-options="
					url:'${pageContext.request.contextPath}/statistics/getLogStatisticsListJson',
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
					<th data-options="field:'valDisplay',align:'center'" width="100" >操作模块名称</th>
					<th data-options="field:'creatorName'" align="center" width="100">操作人姓名</th>
					<th data-options="field:'createTime'" formatter="formatDateConversion" align="center" width="100">操作时间</th>
					<th data-options="field:'operateType'" align="center" width="100" formatter="operateTypeFormat">操作类型</th>
					<th data-options="field:'dataId'" align="center" width="100" formatter="beiZhuFormat">备注</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
</html>