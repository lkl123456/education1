<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript">    	
	var contextpath = '${pageContext.request.contextPath}';
</script>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>网校开通管理</title>
    <link href="${pageContext.request.contextPath}/static/admin/css/default.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/icon.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/ui-cupertino/easyui.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/color.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.js"></script>
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/org.js"></script>
    
</head>
<body class="easyui-layout">	
	<!-- 工具栏 -->
	<div id="tb" style="padding:5px;height:auto; ">
		<div style="margin-bottom:5px">
			<a href="#" onclick="add_org()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
			<a href="#" onclick="edit_org()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a>
			<a href="#" onclick="del_org(-1)" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		</div>
		<div>
			<input id="s_org_customer_name" class="easyui-textbox" data-options="prompt:'客户名称...'" style="width:200px;height:24px;" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
			&nbsp;&nbsp;<a href="#" onclick="del_org(2);" class="easyui-linkbutton" iconCls="icon-lock" >禁用</a>
			&nbsp;<a href="#" onclick="del_org(1);" class="easyui-linkbutton" iconCls="icon-ok" >开通</a>
		</div>
	</div>
	
	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<div title="数据列表信息" data-options="iconCls:'icon icon-nav',fit:true">
	<table id="orgList_table" style="width:800px;height:350px;" class="easyui-datagrid"
			toolbar="#tb" data-options="
			url:'/org/getOrgListJson',
			method:'get',
			singleSelect:false,
			fit:true,
			fitColumns:true,
			striped:true,
			nowarp:false,
			pagination:true,
			pageSize:10,
			pageList:[5,10,15],
			rownumbers:true
			">
		<thead>
			<tr>
				<th data-options="field:'id',checkbox:true"></th>
				<th data-options="field:'customer_name'" align="center" width="80">客户名称</th>
				<th data-options="field:'name',align:'center'" width="100">平台名称</th>
				<th data-options="field:'domain',align:'center'" width="100">平台网址</th>
				<th data-options="field:'user_count',align:'center'" width="60">用户数上限</th>
				<th data-options="field:'expire_time',align:'center',formatter:function(value,row,index){if(value != null){var unixTimestamp = new Date(value); return unixTimestamp.Format('yyyy-MM-dd');}}" width="80">截至有效时间</th>
				<th data-options="field:'create_time',align:'center',formatter:function(value,row,index){if(value != null){var unixTimestamp = new Date(value); return unixTimestamp.Format('yyyy-MM-dd');}}" width="80">创建时间</th>
				<th data-options="field:'db_name',align:'center'" width="80">数据库名称</th>
				<th data-options="field:'uname_sn',align:'center'" width="60">唯一标识</th>
				<th data-options="field:'status',align:'center',formatter:function(value,row,index){if(value=='1'){return '开通'}else if(value=='2'){return '禁用'}}" width="50">状态</th>
			</tr>
		</thead>
	</table>
	</div>
	</div>
	
	<!-- 添加网校对话框 -->
	<div id="dlg" class="easyui-dialog" title="添加网校" style="width:530px;height:420px;padding:10px;"
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
	    <form id="orgForm" method="post" action="${pageContext.request.contextPath}/org/saveOrg">
	    	<input type="hidden" name="id" value="" />
	    	<table cellpadding="5">
	    		<tr>
	    			<td>客户名称:</td>
	    			<td><input class="easyui-textbox" type="text" name="customer_name" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>平台名称:</td>
	    			<td><input class="easyui-textbox" type="text" name="name" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>平台网址:</td>
	    			<td><input class="easyui-textbox" type="text" name="domain" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>数据库名称:</td>
	    			<td><input class="easyui-textbox" type="text" name="db_name" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>唯一标识:</td>
	    			<td><input class="easyui-textbox" type="text" name="uname_sn" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>有效期:</td>
	    			<td><input class="easyui-datebox" type="text" name="expire_time_str" data-options="required:false"></input></input></td>
	    		</tr>
	    		<tr>
	    			<td>用户数限制:</td>
	    			<td>
		    			<select id="user_count_sel" class="easyui-combobox" name="user_count" style="width:128px;">
							<option value="100" selected="selected">100以内</option>
							<option value="500">500以内</option>
							<option value="1000">1000以内</option>
							<option value="5000">5000以内</option>
							<option value="10000">10000以内</option>
						</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>负责人联系信息:</td>
	    			<td><input class="easyui-textbox" type="text" name="address" data-options="multiline:true,required:true" style="height:66px; width:218px;"></input></td>
	    		</tr>
	    	</table>
	    </form>
	    </div>
	</div>
	

</body>
</html>