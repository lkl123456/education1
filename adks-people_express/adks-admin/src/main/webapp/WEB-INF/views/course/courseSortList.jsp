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
<script type="text/javascript" src="${pageContext.request.contextPath}/static/course/courseSort.js"></script>
</head>

<body class="easyui-layout">
	<input type="hidden" id="imgServer" value="${ossResource}" />
	<div data-options="region:'west',split:true,title:'课程分类根列表'"
		style="width: 260px; padding: 10px;">
		<div>
			<ul id="treeDemo" class="ztree"></ul>
		</div>

	</div>

	<input type="hidden" id="courseSortIdOne" value="${courseSortIdOne }">
	<input type="hidden" id="courseSortIdTwo" value="${courseSortIdTwo }">
	<input type="hidden" id="courseSortNameOne"
		value="${courseSortNameOne }">

	<!-- 工具栏 -->
	<div id="tb" style="padding:15px 10px 10px 10px;height:auto; overflow:hidden; ">
		<div style="float:left;">
			<input id="s_courseSort_name" class="easyui-textbox"
				data-options="prompt:'课程类名...'" style="width: 200px; height: 24px;" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search"
				onclick="doSearch()">查询</a>
		</div>
		<div style="margin-bottom:5px; float:right;">
			<a href="#" onclick="add_courseSort();" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">添加</a> <a href="#"
				onclick="edit_courseSort()" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true">编辑</a> <a href="#"
				onclick="del_courseSort()" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true">删除</a> <a
				href="javascript: checkChild()" class="easyui-linkbutton"
				iconCls="icon-tip" plain="true">查看子级课程分类</a> <a
				href="javascript: checkParent()" class="easyui-linkbutton"
				iconCls="icon-tip" plain="true">返回上一级</a>
		</div>
	</div>
	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<div title="课程分类列表数据" data-options="iconCls:'icon icon-nav',fit:true">
			<table id="cslistdatagrid" style="width: 800px; height: 350px"
				class="easyui-datagrid" toolbar="#tb"
				data-options="
					url:'${pageContext.request.contextPath}/courseSort/courseSortListPage',
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
						<th data-options="field:'courseSortId',checkbox:true"></th>
						<th data-options="field:'courseSortName'" align="center"
							width="100">课程分类名称</th>
						<th data-options="field:'courseParentName'" align="center"
							width="100">父级分类名称</th>
						<th data-options="field:'courseNum',align:'center'" width="100">课程数量</th>
						<th data-options="field:'creatorName',align:'center'" width="80">创建人</th>
						<th
							data-options="field:'createtime',align:'center'"
							width="80">更新时间</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>


	<!-- 添加、编辑课程分类窗口 -->
	<div id="dlg" class="easyui-dialog" title="添加课程分类"
		style="width: 600px; height: 350px; padding: 10px;"
		data-options="
				iconCls: 'icon-save',
				modal:true,closed:true,
				buttons: [{
					text:'保存',
					iconCls:'icon-ok',
					handler:function(){
						submitAdd();
					}
				},{
					text:'取消',
					iconCls:'icon-cancel',
					handler:function(){
						close_div();
					}
				}]
			">
		<div style="padding-top: 2px; padding-left: 20px;">
			<form id="categoryForm" method="post"
				action="${pageContext.request.contextPath}/courseSort/saveCourseSort"
				enctype="multipart/form-data">
				<input type="hidden" id="courseSortId" name="courseSortId" value="">
				<input type="hidden" id="courseParentName" name="courseParentName" value=""> 
				<input type="hidden" id="courseParentId" name="courseParentId" value=""> 
				<!-- <input type="hidden" id="parentFirstId" name="parentFirstId" value=""> 
				<input type="hidden" id="orgCode" name="orgCode" value=""> 
				<input type="hidden" id="orgName" name="orgName" value=""> --> 
				<input type="hidden" id="creatorId" name="creatorId" value=""> 
				<input type="hidden" id="creatorName" name="creatorName" value="">
				<input type="hidden" id="csImgpath" name="courseSortImgpath" value=""/>
				<table cellpadding="5">
					<tr>
						<td>分类名称:</td>
						<td width="80%"><input class="easyui-textbox" type="text"
							id="courseSortName" name="courseSortName"
							data-options="required:true" style="width: 160px;"></input></td>
					</tr>
					<tr id="imgShow">
						<td>分类图像:</td>
						<td><img id="csip" height="86" width="131" /> <br />
							<input class="easyui-filebox" id="courseSortImgfile"
							name="courseSortImgfile" data-options="prompt:'选择一张分类图片...'"
							style="width: 80%"> </input>
						</td>
					</tr>
					<!-- <tr>
						<td>所属机构:</td>
						<td><input id="orgNameShow" type="text" disabled="disabled"
							style="width: 160px;"></input></td>
					</tr> -->
					<tr>
						<td>父级课程分类:</td>
						<td><input id="courseParentNameShow" disabled="disabled"
							type="text" style="width: 160px;"></input></td>
					</tr>

				</table>
			</form>
		</div>
	</div>
</body>
</html>