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
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/role/js/roleMenuLinkList.js"></script>

</head>
<body class="easyui-layout">
	
	<!-- 工具栏 -->
	<div id="tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			角色列表：<input class="easyui-combobox" type="text" id="roleList" name="roleId"></input>
			<a href="#" onclick="addDialog();" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加关联</a>
			&nbsp;&nbsp;
			<a href="#" onclick="delDialog();" class="easyui-linkbutton" iconCls="icon-add" plain="true">取消关联</a>
		</div>
		
		<div>
			<input id="s_menuLinkName" class="easyui-textbox" data-options="prompt:'菜单链接名...'" style="width:200px;height:24px;">
			<input id="s_menuName" class="easyui-textbox" data-options="prompt:'菜单名...'" style="width:200px;height:24px;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
		</div>
	</div>
	<div id="mainText" data-options="region:'center'">
			<table id="menuLinklistdatagrid" style="width:800px;height:350px" class="easyui-datagrid" 
					toolbar="#tb" data-options="
					url:'${pageContext.request.contextPath}/roleMenuLink/getMenuLinksJson',
					method:'post',
					singleSelect:false,
					fit:true,
					fitColumns:true,
					nowarp:false,
					pagination:true,
					pageSize:20,
					pageList:[20,25,30],
					rownumbers:true,
					rowStyler:setRowStyle,
					onLoadSuccess:datagridSucc
					">
				<thead>
					<tr>
						<th data-options="field:'menuLinkId',checkbox:true"></th>
						<th data-options="field:'menuLinkName'" align="center" width="100">菜单链接名称</th>
						<th data-options="field:'menuName',align:'center'" width="80">菜单名称</th>
						<th data-options="field:'linkUrl',align:'center'" width="80">链接地址</th>
						<th data-options="field:'createTime',align:'center'" width="150">创建时间</th>
					</tr>
				</thead>
			</table>
	</div>

</body>
</html>