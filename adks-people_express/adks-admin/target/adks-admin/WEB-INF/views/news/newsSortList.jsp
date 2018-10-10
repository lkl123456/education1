<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<script type="text/javascript">
	var contextpath = '${pageContext.request.contextPath}';
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新闻管理</title>
<%@include file="../public/header.jsp"%>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/news/newsSort.js"></script>

</head>
<body class="easyui-layout">
	<input type="hidden" id="imgServer" value="${imgServer}" />
	<!-- 
		<div data-options="region:'west',split:true,title:'新闻分类类型列表'"
			style="width: 260px; padding: 10px;">
			<div>
				<ul id="newsTypeTreeDemo" class="ztree"></ul>
			</div>
	
		</div>
	 -->
	<!-- 工具栏 -->
	<div id="tb"
		style="padding: 15px 10px 10px 10px; height: auto; overflow: hidden;">
		<div style="float: left;">
			<c:if test="${!empty isPlatform}">
				<input id="s_newsSort_orgSnname" class="easyui-combobox"
					data-options="url:'/org/orgListSelJson',method:'get',valueField:'uname_sn',textField:'name',prompt:'选择网校...',onChange:function(){doSearch()}"
					style="width: 200px;" />&nbsp;
			</c:if>
			<input type="hidden" id="isPlatform" value="${isPlatform }" /> <input
				id="s_newsSort_name" class="easyui-textbox"
				data-options="prompt:'新闻分类名...'" style="width: 200px; height: 24px;" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search"
				onclick="doSearch()">查询</a>
		</div>
		<div style="margin-bottom: 5px; float: right;">
			<a href="#" onclick="add_newsSort();" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">添加</a> <a href="#"
				onclick="edit_newsSort()" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true">编辑</a> <a href="#"
				onclick="del_newsSort(-1)" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true">删除</a>
		</div>
	</div>
	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<div title="新闻分类列表数据" data-options="iconCls:'icon icon-nav',fit:true">
			<table id="newsSortlistdatagrid" style="width: 800px; height: 350px"
				class="easyui-datagrid" toolbar="#tb"
				data-options="
					url:'${pageContext.request.contextPath}/newsSort/getNewsSortListJson',
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
						<th data-options="field:'newsSortId',checkbox:true"></th>
						<th data-options="field:'newsSortName'" align="center" width="100">新闻分类名称</th>
						<th data-options="field:'newsSortLocation'" align="center"
							width="100">分类排序</th>
						<!-- 
							<th data-options="field:'newsSortType'" align="center"
								formatter="newsSortTypeData" width="100">分类栏目类型</th>
							<th data-options="field:'orgName',align:'center'" width="80">分类隶属于哪一个学校</th>
						 -->
						<th data-options="field:'createTimeStr',align:'center'" width="80">发布时间</th>
						<th data-options="field:'creatorName',align:'center'" width="80">发布人</th>
						<th data-options="field:'orgId'" align="center" width="80"
							hidden="true">orgId</th>
						<!--  
						<th data-options="field:'update_time',align:'center',formatter:function(value,row,index){if(value != null){var unixTimestamp = new Date(value); return unixTimestamp.Format('yyyy-MM-dd');}}" width="80">更新时间</th>
						<th data-options="field:'status',align:'center',formatter:function(value,row,index){if(value=='0'){return '草稿'}else if(value=='1'){return '已发布'}else if(value=='2'){return '已下架'}}" width="50">发布状态</th>
						-->
					</tr>
				</thead>
			</table>
		</div>
	</div>

	<!-- 添加新闻分类信息div  -->
	<div id="dlg_newsSort" class="easyui-dialog" title="添加新闻分类"
		style="width: 620px; height: 580px; padding: 10px;"
		data-options="
				iconCls: 'icon-save',
				modal:true,closed:true,
				buttons: [{
					text:'发布',
					iconCls:'icon-ok',
					handler:function(){
						submitAdd_newsSort();
					}
				},{
					text:'取消',
					iconCls:'icon-cancel',
					handler:function(){
						cancleAdd_newsSort();
					}
				}]
			">
		<div style="padding-left: 20px; padding-top: 2px;">
			<form id="newsSortForm" method="post" enctype="multipart/form-data"
				action="${pageContext.request.contextPath}/newsSort/saveNewsSort">
				<input type="hidden" id="t_id" name="newsSortId"> <input
					type="hidden" id="newsSortType" name="newsSortType"> <input
					type="hidden" id="userOrgId" name="orgId"> <input
					type="hidden" id="userOrgName" name="orgName"> <input
					type="hidden" id="userOrgCode" name="orgCode">
				<table cellpadding="5">
					<tr>
						<td>分类名称:</td>
						<td><input class="easyui-textbox" type="text"
							id="newsSortName" name="newsSortName"
							data-options="required:true,validType:['isNullOrEmpty','length[1,32]']"
							style="width: 238px;"></td>
					</tr>
					<tr>
						<td>分类排序:</td>
						<td><input class="easyui-numberbox" type="text"
							id="newsSortLocation" name="newsSortLocation"
							data-options="required:true,validType:['length[1,4]']"></td>
					</tr>
					<!-- <tr>
	    			<td>分类栏目类型:</td>
	    			<td><input class="easyui-combobox" type="text" id="ss1" name="newsSortType"></input></td>
	    		</tr> -->
	    		<!-- 
					<tr>
						<td>分类栏目类型:</td>
						<td><input type="text" id="newsSortTypeName"
							disabled="disabled"></input></td>
					</tr>
	    		 -->
	    		 <!-- 
					<tr>
						<td>所属部门:</td>
						<td><input type="text" id="orgNameShow"
							onclick="showOrgTree();"></input><span style="color: red">
								* </span>
							<ul id="treeDemo1" class="ztree" style="display: none;"></ul></td>
					</tr>
					</table>
	    		  -->
			</form>

		</div>
	</div>

</body>
</html>