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
    <link href="${pageContext.request.contextPath}/static/admin/css/default.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/icon.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/ui-cupertino/easyui.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/color.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.js"></script>
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/menu/js/menuLink.js"></script>
    
</head>
<body class="easyui-layout">	
	<!-- 工具栏 -->
	<div id="tb" style="padding:15px 10px 10px 10px;height:auto; overflow:hidden; ">
		<div style="float:left;">
			<input id="s_menuLink_name" class="easyui-textbox" data-options="prompt:'菜单链接名称...'" style="width:200px;height:24px;" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
		</div>
		<div style="margin-bottom:5px; float:right;">
			<a href="#" onclick="addMenuLink()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
			<a href="#" onclick="edit_menuLink()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a>
			<a href="#" onclick="del_menuLink(-1)" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		</div>
	</div>
	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<div title="数据列表信息" data-options="iconCls:'icon icon-nav',fit:true">
	<table id="menuLinkList_table" style="width:800px;height:350px;" class="easyui-datagrid"
			toolbar="#tb" data-options="
			url:'${pageContext.request.contextPath}/menuLink/getMenuLinkListJson?menuId='+${menu.menuId},
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
				<th data-options="field:'menuLinkId',checkbox:true"></th>
				<th data-options="field:'menuLinkName'" align="center" width="100">菜单链接名称</th>
				<th data-options="field:'menuName',align:'center'" width="80">菜单名称</th>
				<th data-options="field:'linkUrl',align:'center'" width="80">链接地址</th>
				<th data-options="field:'orderNum',align:'center'" width="80">排序</th>
				<th data-options="field:'creatorName',align:'center'" width="100">创建人</th>
				<th data-options="field:'createTime',align:'center'" width="80">创建时间</th>
			</tr>
		</thead>
	</table>
	</div>
	</div>
	<!-- 添加网校对话框 -->
	<div id="dlg" class="easyui-dialog" title="添加菜单链接" style="width:430px;height:280px;padding:10px;"
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
		<input type="hidden" id="menuIdOne"  value="${menu.menuId }" />
		<input type="hidden" id="menuNameOne"  value="${menu.menuName }" />
		<div style="padding-top: 2px; padding-left: 20px;">
	    <form id="menuLinkForm" method="post" action="${pageContext.request.contextPath}/menuLink/saveMenuLink">
	    	<input type="hidden" id="menuLinkId" name="menuLinkId" value="" />
	    	<input type="hidden" id="menuId" name="menuId" value="${menuId }" />
	    	<input type="hidden" id="creatorId" name="creatorId" value="" />
	    	<input type="hidden" id="creatorName" name="creatorName" value="" />
	    	<table cellpadding="5">
	    		<tr>
	    			<td>菜单名称:</td>
	    			<td><input  id="menuName" type="text" disabled="disabled"></input></td>
	    		</tr>
	    		<tr>
	    			<td>菜单链接名称:</td>
	    			<td><input class="easyui-textbox" id="menuLinkName" type="text" name="menuLinkName" data-options="required:true,validType:['isNullOrEmpty','length[1,16]']"></input></td>
	    		</tr>
	    		<tr>
	    			<td>链接地址:</td>
	    			<td><input class="easyui-textbox" id="linkUrl" type="text" name="linkUrl" data-options="required:true,validType:['isNullOrEmpty','length[1,64]']"></input></td>
	    		</tr>
	    		<tr>
	    			<td>排序:</td>
	    			<td><input class="easyui-numberbox" id="orderNum" type="text" name="orderNum" data-options="required:true,validType:['isNullOrEmpty','length[1,3]']"></input></td>
	    		</tr>
	    		<tr>
	    			<td>描述:</td>
	    			<td><input class="easyui-textbox" id="mLinkDisplay" type="text" name="mLinkDisplay"></input></td>
	    		</tr>
	    		
	    	</table>
	    </form>
	    </div>
	</div>
</body>
</html>