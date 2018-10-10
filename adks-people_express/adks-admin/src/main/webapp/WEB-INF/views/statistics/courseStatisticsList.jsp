<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../public/header.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/statistics/js/courseStatistics.js"></script>
</head>
<body class="easyui-layout">
	<!-- 工具栏 -->
	<div id="tb" style="padding:15px 10px 10px 10px;height:auto; overflow:hidden; ">
		<div style="float:left;">
			<input id="courseName" class="easyui-textbox" data-options="prompt:'课程名称...'" style="width:150px;height:24px;">
			课程分类：<input type="text" id="selCourseSortTree" onclick="showCourseSortTree()">
			<ul id="treeDemo1" class="ztree" style="display:none; position:absolute; left:222px; top:40px; z-index:9999; border:1px solid #ddd; background:#fff; min-width:200px;overflow-y:scroll;"></ul>
			<input type="hidden" id="selCourseSortCode">
			机构：<input type="text" id="selCourseOrgTree" onclick="showOrgTree2()">
			<ul id="treeDemo2" class="ztree" style="display:none; position:absolute; left:404px; top:40px; z-index:9999; border:1px solid #ddd; background:#fff; min-width:200px;overflow-y:scroll;"></ul>
			<input type="hidden" id="selCourseOrgTreeOrgCode">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
		</div>
		<div style="margin-bottom:5px; float:right;">
			<a href="javascript: exportCourseExcle()" class="easyui-linkbutton" iconCls="icon-print" plain="true">导出Excel</a>
		</div>
	</div>
	<div id="mainText" data-options="region:'center'">
		<table id="courseStatisticsListTable" style="width: 800px; height: 350px;"
			class="easyui-datagrid" toolbar="#tb"
			data-options="
					url:'${pageContext.request.contextPath}/statistics/getCourseStatisticsListJson',
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
					<th data-options="field:'courseName',align:'center'" width="200">课程名称</th>
					<th data-options="field:'courseSortName'" align="center" width="100">课程分类</th>
					<th data-options="field:'courseClickNum'" align="center" width="100">点击量</th>
					<th data-options="field:'courseCollectNum'" align="center" width="100">收藏量</th>
				</tr>
			</thead>
		</table>
	</div>
	<!-- 批量导入对话框 -->
	<div style="padding-top: 2px; padding-left: 20px; z-index: 0;">
		<form id="export_courseStatistics" method="post"
			action="${pageContext.request.contextPath}/statistics/exportCourseStatistics">
			<input id="excourseOrgCode" name="excourseOrgCode" type="hidden" value="">
			<input id="excourseSortCode" name="excourseSortCode" type="hidden" value="">
		</form>
	</div>
</body>
</html>