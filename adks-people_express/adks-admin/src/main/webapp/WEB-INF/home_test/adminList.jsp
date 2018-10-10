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
    <title>管理员管理</title>
    <link href="${pageContext.request.contextPath}/static/admin/css/default.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/icon.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/ui-cupertino/easyui.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/color.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.js"></script>
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/admin.js"></script>
    
</head>
<body class="easyui-layout">	
	<!-- 工具栏 -->
	<div id="tb" style="padding:5px;height:auto; ">
		<div style="margin-bottom:5px">
			<a href="#" onclick="add_admin()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
			<a href="#" onclick="edit_admin()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a>
			<a href="#" onclick="del_admin(-1)" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		</div>
		<div>
			<c:if test="${!empty isPlatform}">
			 <input id="s_user_orgSnname" class="easyui-combobox" data-options="url:'/org/orgListSelJson',method:'get',valueField:'uname_sn',textField:'name',prompt:'选择网校...',onChange:function(){doSearch()}" style="width:200px;" />&nbsp;
			</c:if>
			<input type="hidden" id="isPlatform" value="${isPlatform }" />
			<input id="s_user_name" class="easyui-textbox" data-options="prompt:'用户名...'" style="width:200px;height:24px;" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
			&nbsp;&nbsp;<a href="#" onclick="del_admin(2);" class="easyui-linkbutton" iconCls="icon-lock" >冻结</a>
			&nbsp;<a href="#" onclick="del_admin(1);" class="easyui-linkbutton" iconCls="icon-ok" >激活</a>
		</div>
	</div>
	
	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<div title="数据列表信息" data-options="iconCls:'icon icon-nav',fit:true">
	<table id="adminList_table" style="width:800px;height:350px;" class="easyui-datagrid"
			toolbar="#tb" data-options="
			url:'/admin/getAdminListJson',
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
				<th data-options="field:'user_name'" align="center" width="80">用户名</th>
				<th data-options="field:'org_name',align:'center'" width="100">所属分校名称</th>
				<th data-options="field:'org_id'">所属分校ID</th>
				<th data-options="field:'mobile',align:'center'" width="80">手机号码</th>
				<th data-options="field:'email',align:'center'" width="100">邮箱</th>
				<th data-options="field:'status',align:'center',formatter:function(value,row,index){if(value=='0'){return '未激活'}else if(value=='1'){return '可用'}else if(value=='2'){return '冻结'}}" width="50">状态</th>
				<!-- <th data-options="field:'admin_type',align:'center'" width="50">管理员类型</th> -->
				<th data-options="field:'create_time',align:'center',formatter:function(value,row,index){if(value != null){var unixTimestamp = new Date(value); return unixTimestamp.Format('yyyy-MM-dd');}}" width="80">创建时间</th>
				<th data-options="field:'update_time',align:'center',formatter:function(value,row,index){if(value != null){var unixTimestamp = new Date(value); return unixTimestamp.Format('yyyy-MM-dd');}}" width="80">更新时间</th>
			</tr>
		</thead>
	</table>
	</div>
	</div>
	
	<!-- 添加管理员对话框 -->
	<div id="dlg" class="easyui-dialog" title="添加管理员" style="width:460px;height:350px;padding:10px;"
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
	    <form id="adminForm" method="post" action="${pageContext.request.contextPath}/admin/saveAdmin">
	    	<input type="hidden" id="t_id" name="id" >
	    	<table cellpadding="5">
	    		<tr>
	    			<td>用户名:</td>
	    			<td><input class="easyui-textbox" type="text" id="t_user_name" name="user_name" data-options="required:true"></input></td>
	    		</tr>
	    		<tr id="tr_org_list">
	    			<td>所属分校:</td>
	    			<td>
		    			<input id="orgListSel" name="org_id"/>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>手机号码:</td>
	    			<td><input class="easyui-textbox" type="text" id="t_mobile" name="mobile" data-options="required:true,validType:'number'"></input></td>
	    		</tr>
	    		<tr>
	    			<td>电子邮箱:</td>
	    			<td><input class="easyui-textbox" type="text" id="t_email" name="email" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>状态:</td>
	    			<td>
		    			<select id="status_sel" class="easyui-combobox" name="status" style="width:128px;">
							<option value="0">未激活</option>
							<option value="1">可用</option>
							<option value="2">冻结</option>
						</select>
	    			</td>
	    		</tr>
	    		<tr id="tr_admin_role">
	    			<td>选择角色:</td>
	    			<td>
		    			<input class="easyui-combobox" id="admin_role_id" name="admin_role_id" />
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	    </div>
	</div>
	

</body>
</html>