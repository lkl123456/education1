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
    <title>讲师管理</title>
   <%@include file="../public/header.jsp" %>
    
</head>
<body class="easyui-layout">
	<input id="orgCode" value="${orgCode }"/>
	<input id="orgId" value="${orgId }"/>
	<input id="courseId" value="${courseId }"/>
	<input id="courseSortId" value="${courseSortId }"/>
	<div data-options="region:'west',split:true,title:'机构列表'" style="width:260px;padding:10px;">
		<div>
			<ul id="treeDemo" class="ztree"></ul>
		</div>

	</div>
	<!-- 工具栏 -->
	<div id="tb" style="padding:5px;height:auto">
		<div>
			<input id="s_author_name" class="easyui-textbox" data-options="prompt:'讲师名...'" style="width:200px;height:24px;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
		</div>
	</div>
	<div id="mainText" data-options="region:'center'">
			<table id="authorlistdatagrid" style="width:800px;height:350px" class="easyui-datagrid" 
					toolbar="#tb" data-options="
					url:'${pageContext.request.contextPath}/author/authorListPage?orgCode=${orgCode }',
					method:'post',
					singleSelect:false,
					fit:true,
					fitColumns:true,
					nowarp:false,
					pagination:true,
					pageSize:15,
					pageList:[15,20,30],
					rownumbers:true,
					rowStyler:setRowStyle
					">
				<thead>
					<tr>
						<th data-options="field:'authorId',checkbox:true"></th>
						<th data-options="field:'authorName'" align="center" width="100">讲师名</th>
						<th data-options="field:'authorFirstLetter'" align="center" width="100">首字母</th>
						<th data-options="field:'authorSex',align:'center'" formatter="formatColumnData" width="80">性别</th>
						<th data-options="field:'orgName',align:'center'" width="150">所属机构</th>
						<th data-options="field:'creatorName',align:'center'"  width="50">创建人</th>
						<th data-options="field:'_operate',width:80,align:'center'" formatter="formatColumnData1" width="50">操作</th>
					</tr>
				</thead>
			</table>
	</div>
	
</body>
 <script type="text/javascript" src="${pageContext.request.contextPath}/static/course/toAddAuthorList.js"></script>
</html>