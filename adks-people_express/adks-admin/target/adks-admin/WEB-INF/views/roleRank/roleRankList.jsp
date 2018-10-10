<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript">    	
	var contextpath = '${pageContext.request.contextPath}';
</script>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>网校开通管理</title>
    <%@include file="../public/header.jsp" %>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/role/js/role.js"></script>
    
</head>
<body class="easyui-layout">	
	<!-- 工具栏 -->
	<div id="tb" style="padding:15px 10px 10px 10px;height:auto; overflow:hidden; ">
		<div style="float:left;">
			<input id="s_role_name" class="easyui-textbox" data-options="prompt:'角色名称...'" style="width:200px;height:24px;" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
			&nbsp;&nbsp;<!-- <a href="#" onclick="del_org(2);" class="easyui-linkbutton" iconCls="icon-lock" >禁用</a>
			&nbsp;<a href="#" onclick="del_org(1);" class="easyui-linkbutton" iconCls="icon-ok" >开通</a> -->
		</div>
		<div style="margin-bottom:5px; float:right;">
			<a href="#" onclick="add_role()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
			<a href="#" onclick="edit_role()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a>
			<a href="#" onclick="del_role(-1)" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		</div>
	</div>
	
	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<div title="数据列表信息" data-options="iconCls:'icon icon-nav',fit:true">
	<table id="roleList_table" style="width:800px;height:100%;" class="easyui-datagrid"
			toolbar="#tb" data-options="
			url:'${pageContext.request.contextPath}/role/getRoleListJson',
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
				<th data-options="field:'roleId',checkbox:true"></th>
				<th data-options="field:'roleName'" align="center" width="80">角色名称</th>
				<th data-options="field:'displayRef',align:'center'" width="100">显示的名称</th>
				<th data-options="field:'roleDes',align:'center'" width="100">描述</th>
				<th data-options="field:'isGlob',align:'center'" formatter="formatColumnData" width="100">是否是全局</th>
				<th data-options="field:'creatorName',align:'center'" width="100">创建人</th>
			</tr>
		</thead>
	</table>
	</div>
	</div>
	
	<!-- 添加机构对话框 -->
	<div id="dlg" class="easyui-dialog" title="添加角色" style="width:430px;height:220px;padding:10px;"
			data-options="
				iconCls: 'icon-save',
				modal:true,closed:true,
				buttons: [{
					text:'保存',
					iconCls:'icon-ok',
					handler:function(){
						submitForm();
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
	    <form id="roleForm" method="post" action="${pageContext.request.contextPath}/role/saveRole">
	    	<input type="hidden" id="roleId" name="roleId" value="" />
	    	<table cellpadding="5">
	    		<tr>
	    			<td>角色名称:</td>
	    			<td><input class="easyui-textbox" id="roleName" type="text" name="roleName" data-options="required:true,validType:['isNullOrEmpty','length[1,10]']"></input></td>
	    		</tr>
	    		<tr>
	    			<td>显示的名称:</td>
	    			<td><input class="easyui-textbox" id="displayRef" type="text" name="displayRef"></input></td>
	    		</tr>
	    		<tr>
	    			<td>描述:</td>
	    			<td><input class="easyui-textbox" id="roleDes" type="text" name="roleDes"></input></td>
	    		</tr>
	    		<tr>
	    			<td>是否是全局:</td>
	    			<td>
	    				<input type="radio" name="isGlob" value="1"/>是
	    				<input type="radio" name="isGlob" value="2"/>否
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	    </div>
	</div>
	
</body>
</html>