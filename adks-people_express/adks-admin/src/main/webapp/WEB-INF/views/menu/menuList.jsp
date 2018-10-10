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
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/menu/js/menu.js"></script>
   
    
</head>
<body class="easyui-layout">	
	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<div title="数据列表信息" data-options="iconCls:'icon icon-nav',fit:true">
		<!-- 工具栏 -->
	<div id="tb" style="padding:15px 10px 10px 10px;height:auto; overflow:hidden; ">
		<div style="float:left;">
			<input id="s_en_name" class="easyui-textbox" data-options="prompt:'菜单名称...'" style="width:200px;height:24px;" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
			&nbsp;&nbsp;
		</div>
		<div style="margin-bottom:5px; float:right;">
			<a href="#" onclick="add_menu()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加菜单</a>
			<a href="#" onclick="edit_menu()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a>
			<a href="#" onclick="del_menu(-1)" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
			<a href="javascript: editMenuVal()" class="easyui-linkbutton" iconCls="icon-tip" plain="true">查看菜单链接</a>
		</div>
	</div>
	<table id="menuList_table" style="width:800px;height:350px;" class="easyui-datagrid"
			toolbar="#tb" data-options="
			url:'${pageContext.request.contextPath}/menu/getMenuListJson',
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
				<th data-options="field:'menuId',checkbox:true"></th>
				<th data-options="field:'menuName'" align="center" width="80">菜单名称</th>
				<th data-options="field:'menuDesc',align:'center'" width="100">描述</th>
				<th data-options="field:'orderNum',align:'center'" width="100">排序</th>
				<th data-options="field:'creatorName',align:'center'" width="100">创建人</th>
				<th data-options="field:'createTime',align:'center'" width="80">创建时间</th>
				<!-- <th data-options="field:'_operate',width:80,align:'center',formatter:formatOper">操作</th> -->  
			</tr>
		</thead>
	</table>
	</div>
	
	</div>
	
	<!-- 添加菜单对话框 -->
	<div id="dlg" class="easyui-dialog" title="添加菜单" style="width:430px;height:220px;padding:10px;"
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
	    <form id="menuForm" method="post" action="${pageContext.request.contextPath}/menu/saveMenu">
	    	<input type="hidden" id="menuId" name="menuId" value="" />
	    	<input type="hidden" id="creatorId" name="creatorId" value="" />
	    	<input type="hidden" id="creatorName" name="creatorName" value="" />
	    	<table cellpadding="5">
	    		<tr>
	    			<td>菜单名称:</td>
	    			<td><input class="easyui-textbox" id="menuName" type="text" name="menuName" data-options="required:true,validType:['isNullOrEmpty','length[1,16]']"></input></td>
	    		</tr>
	    		<tr>
	    			<td>排序:</td>
	    			<td><input class="easyui-numberbox"  id="orderNum" type="text" name="orderNum" data-options="required:true,validType:['isNullOrEmpty','length[1,3]']"></input></td>
	    		</tr>
	    		<tr>
	    			<td>描述:</td>
	    			<td><input class="easyui-textbox" id="menuDesc" type="text" name="menuDesc"></input></td>
	    		</tr>
	    	</table>
	    </form>
	    </div>
	</div>
</body>
</html>