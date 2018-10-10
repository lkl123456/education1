<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新闻管理</title>
<%@include file="../public/header.jsp"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/news/news.js"></script>
</head>
<body class="easyui-layout">
	<input type="hidden" id="ossPath" value="${ossResource}" />
	<!-- 课程分类机构树 -->
	<div data-options="region:'west',split:true,title:'新闻分类列表'"
		style="width: 260px; padding: 10px;">
		<input type="hidden" id="cc_ztree_node_id" value="0" /> <input
			type="hidden" id="cc_ztree_node_name" value="顶级分类" /> <input
			type="hidden" id="cc_ztree_node_code" value="0-" />
		<div>
			<ul id="newsSortTreeDemo" class="ztree"></ul>
		</div>
	</div>
	<!-- 工具栏 -->
	<div id="tb"
		style="padding: 15px 10px 10px 10px; height: auto; overflow: hidden;">
		<div style="float: left;">
			<c:if test="${!empty isPlatform}">
				<input id="s_news_orgSnname" class="easyui-combobox"
					data-options="url:'/org/orgListSelJson',method:'get',valueField:'uname_sn',textField:'name',prompt:'选择网校...',onChange:function(){doSearch()}"
					style="width: 200px;" />&nbsp;
			</c:if>
			<input type="hidden" id="isPlatform" value="${isPlatform }" /> <input
				id="s_news_name" class="easyui-textbox"
				data-options="prompt:'新闻名...'" style="width: 200px; height: 24px;" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search"
				onclick="doSearch()">查询</a>
		</div>
		<div style="margin-bottom: 5px; float: right;">
			<a href="#" onclick="add_news();" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">添加</a> <a href="#"
				onclick="edit_news()" class="easyui-linkbutton" iconCls="icon-edit"
				plain="true">编辑</a> <a href="#" onclick="del_news(-1)"
				class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		</div>
	</div>
	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<div title="新闻列表数据" data-options="iconCls:'icon icon-nav',fit:true">
			<table id="newslistdatagrid" style="width: 800px; height: 350px"
				class="easyui-datagrid" toolbar="#tb"
				data-options="
					url:'${pageContext.request.contextPath}/news/getNewsListJson',
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
						<th data-options="field:'newsId',checkbox:true"></th>
						<th data-options="field:'newsTitle'" align="center" width="100">新闻名称</th>
						<th data-options="field:'newsSortName'" align="center" width="100">所属分类</th>
						<th data-options="field:'orgName'" align="center" width="70">机构名称</th>
						<th data-options="field:'createTimeStr',align:'center'" width="80">发布时间</th>
						<th data-options="field:'creatorName',align:'center'" width="80">发布人</th>
						<th data-options="field:'orgId'" align="center" width="80"
							hidden="true">orgId</th>
						<th data-options="field:'newsSortType'" align="center" width="80"
							hidden="true">newsSortType</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
</html>