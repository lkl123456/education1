<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@include file="../public/header.jsp"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/notice/sysNoticeList.js"></script>
<body class="easyui-layout">
	<!-- 工具栏 -->
	<div id="tb"
		style="padding: 15px 10px 10px 10px; height: auto; overflow: hidden;">
		<div style="float: left;">
			<input id="" class="easyui-searchbox"
				data-options="prompt:'公告名称...',searcher:doSearch"
				style="width: 200px; height: 24px;">
		</div>
		<div style="margin-bottom: 5px; float: right;">
			<a href="javascript: addSysNotice()" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">添加</a> <a
				href="javascript: editSysNotice()" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true">编辑</a> <a
				href="javascript: deleteSysNotice()" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true">删除</a>
		</div>
	</div>
	<!-- 列表 -->
	<div id="mainText" data-options="region:'center'">
		<table id="sysNoticeListTable" style="width: 800px; height: 350px;"
			class="easyui-datagrid" toolbar="#tb"
			data-options="
					url:'${pageContext.request.contextPath}/sysNotice/getSysNoticeListJson',
					method:'post',
					singleSelect:false,
					collapsible:true,
					fit:true,
					fitColumns:true,
					pagination:true,
					pageSize:20,
					pageList:[20,25,30],
					rownumbers:true,
					rowStyler:setRowStyle
					">
			<thead>
				<tr>
					<th data-options="field:'newsId',checkbox:true"></th>
					<th data-options="field:'newsTitle',align:'center'" width="100">标题</th>
					<th data-options="field:'creatorName'" align="center" width="100">创建人</th>
					<th data-options="field:'createTime'"
						formatter="formatDateConversion" align="center" width="70">创建时间</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
</html>