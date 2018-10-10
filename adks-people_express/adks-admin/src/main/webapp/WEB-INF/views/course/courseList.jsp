<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<title>课程管理</title>
<%@include file="../public/header.jsp"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/zTree/jquery.ztree.all-3.5.min.js"></script>
<link href="${pageContext.request.contextPath}/static/admin/zTree/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/static/course/course.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'west',split:true,title:'课程分类列表'"
		style="width: 260px; padding: 10px;">
		<div>
			<ul id="treeDemo" class="ztree"></ul>
		</div>
	</div>

	<!-- 工具栏 -->
	<div id="tb" style="padding:15px 10px 10px 10px;height:auto; overflow:hidden; ">
		<div style="float:left;">
			<input id="s_course_name" class="easyui-textbox" data-options="prompt:'课程类名...'" style="width: 200px; height: 24px;" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a> 
			<a href="#" onclick="courseUpdateData('1','','')" class="easyui-linkbutton" iconCls="icon-ok" plain="true">审核通过</a> 
			<a href="#" onclick="courseUpdateData('2','','');" class="easyui-linkbutton" iconCls="icon-lock" plain="true">推荐</a> 
			<a href="#" onclick="courseUpdateData('3','','')" class="easyui-linkbutton" iconCls="icon-redo" plain="true">激活</a>
		</div>
		
		<div style="margin-bottom:5px; float:right;">
			<a href="#" onclick="addCourse()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">添加</a> 
			<a href="#" onclick="editCourse()" class="easyui-linkbutton" iconCls="icon-add" plain="true">编辑</a> 
			<a href="#" onclick="delCourse()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">删除</a>
		</div>

	</div>
	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<div title="课程列表数据" data-options="iconCls:'icon icon-nav',fit:true">
			<table id="cslistdatagrid" style="width: 800px; height: 350px"
				class="easyui-datagrid" toolbar="#tb"
				data-options="
					url:'${pageContext.request.contextPath}/course/courseListPage',
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
						<th data-options="field:'courseId',checkbox:true"></th>
						<th data-options="field:'courseName'" align="center" width="100">课程名称</th>
						<th data-options="field:'courseType'" align="center" formatter="formatColumnData4" width="100">课程类型</th>
						<th data-options="field:'authorName',align:'center'" width="100">讲师</th>
						<th data-options="field:'courseSortName',align:'center'" width="100">课程分类</th>
						<th data-options="field:'courseTimeLong',align:'center'" width="100">课程时长</th>
						<th data-options="field:'orgName',align:'center'" width="80">所属机构</th>
						<th data-options="field:'isAudit',align:'center'" formatter="formatColumnData1" width="100">审核状态</th>
						<th data-options="field:'isRecommend',align:'center'" formatter="formatColumnData2" width="80">是否推荐</th>
						<th data-options="field:'courseStatus',align:'center'" formatter="formatColumnData3" width="80">课程状态</th>
						<th data-options="field:'creatorName',align:'center'" width="80">创建人</th>
						<th data-options="field:'createtime',align:'center'" width="80">创建时间</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

</body>
</html>