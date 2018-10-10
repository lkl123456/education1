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
    <link href="${pageContext.request.contextPath}/static/admin/css/default.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/icon.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/ui-cupertino/easyui.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/color.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/easyui-lang-zh_CN.js"></script>
   
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/zTree/jquery.ztree.all-3.5.min.js"></script>
    <link href="${pageContext.request.contextPath}/static/admin/zTree/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/user/js/user.js"></script>

</head>
<body class="easyui-layout">

	
	
	<div data-options="region:'west',split:true,title:'部门列表'" style="width:260px;padding:10px;">
		<div>
			<a href="#" onclick="addDialog('deptadd');" class="easyui-linkbutton" iconCls="icon-add">添加</a>
		 	<!-- 
		 	<a href="#" class="easyui-linkbutton" iconCls="icon-edit">编辑</a>
		 	 -->
		 	<a href="#" onclick="deleteDept();" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
		 	<a href="#" onclick="reloadTree();" class="easyui-linkbutton" iconCls="icon-reload">刷新</a>
		</div>
		<div>
			<ul id="treeDemo" class="ztree"></ul>
		</div>

	</div>
	
	<!-- 工具栏 -->
	<div id="tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="#" onclick="addDialog('useradd');" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
			<a href="#" onclick="editUser();" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a>
			<a href="#" onclick="deleteUser();" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		</div>
		
		<div>
		<c:choose>
			<c:when test="${org_sn_name =='gclc' }">
				<input id="orgunameValue" class="easyui-combobox" data-options="url:'/org/orgListSelJson',method:'get',valueField:'uname_sn',textField:'name',prompt:'选择网校...',onChange:function(){refreshLeftDept()}" style="width:200px;" />
			</c:when>
			<c:otherwise>
				<input type="hidden" id="orgunameValue" class="easyui-combobox" style="display: none;"  />
			</c:otherwise>
		</c:choose>
			<input id="loginname" class="easyui-textbox" data-options="prompt:'用户名...'" style="width:200px;height:24px;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
			<a href="#" onclick="changeUserStatus('resetpass');" class="easyui-linkbutton" iconCls="icon-reload" >重置密码</a>
			<a href="#" onclick="changeUserStatus('stop');" class="easyui-linkbutton" iconCls="icon-lock" >停用</a>
			<a href="#" onclick="changeUserStatus('continue');" class="easyui-linkbutton" iconCls="icon-undo" >恢复</a>
		</div>
	</div>
	<div id="mainText" data-options="region:'center'">
			<table id="userlistdatagrid" style="width:800px;height:350px" class="easyui-datagrid" 
					toolbar="#tb" data-options="
					url:'/user/getUsersJson',
					method:'get',
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
						<th data-options="field:'id',checkbox:true"></th>
						<th data-options="field:'login_name'" align="center" width="100">用户名</th>
						<th data-options="field:'user_name',align:'center'" width="80">真实姓名</th>
						<th data-options="field:'dept_name',align:'center'" width="150">所属部门</th>
						<th data-options="field:'email',align:'center'" width="150">邮箱</th>
						<th data-options="field:'telphone',align:'center'" width="90">电话</th>
						<th data-options="field:'birthday_str',align:'center'" width="90">生日</th>
						<th data-options="field:'status',align:'center'" formatter="formatColumnData" width="50">用户状态</th>
					</tr>
				</thead>
			</table>
	</div>


<!-- 添加部门对话框 -->
<div id="dlg" class="easyui-dialog" title="新建部门" style="width:580px;height:240px;padding:10px;"
			data-options="
				iconCls: 'icon-save',
				modal:true,closed:true,
				buttons: [{
					text:'保存',
					iconCls:'icon-ok',
					handler:function(){
						submitAdd('deptadd');
					}
				},{
					text:'取消',
					iconCls:'icon-cancel',
					handler:function(){
						cancleAdd('deptadd');
					}
				}]
			">
		<div style="padding:10px 120px 20px 120px">
	    <form id="ff" method="get">
	    	<table cellpadding="5">
	    		<tr>
	    			<td>部门名称:</td>
	    			<td><input class="easyui-textbox" type="text" name="name" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>上级部门:</td>
	    			<td>
	    			<input id="cc" name="parent_id" data-options="required:true"  style="width:200px;">
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>排序:</td>
	    			<td><input name="order_num" class="easyui-textbox" type="text" name="subject" data-options="required:false,validType:'number'"></input></td>
	    		</tr>
	    	</table>
	    </form>
	    </div>
	</div>
	
	<!-- 添加用户div -->
	<div id="dlg_user" class="easyui-dialog" title="添加用户" style="width:600px;height:500px;padding:10px;"
			data-options="
				iconCls: 'icon-save',
				modal:true,closed:true,
				buttons: [{
					text:'保存',
					iconCls:'icon-ok',
					handler:function(){
						submitAdd('useradd');
					}
				},{
					text:'取消',
					iconCls:'icon-cancel',
					handler:function(){
						cancleAdd('useradd');
					}
				}]
			">
		<div style="padding:10px 120px 20px 120px">
	    <form id="hh" method="post">
	    	<input type="hidden" name="id" value="" />
	    	<table cellpadding="5">
	    		<a class="easyui-linkbutton c4">基本信息</a>
	    		<tr>
	    			<td>用户名:</td>
	    			<td><input class="easyui-textbox" type="text" name="login_name" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>姓   名:</td>
	    			<td><input class="easyui-textbox" type="text" name="user_name" data-options="required:true"></input></td>
	    		</tr>
	    		<br>
	    		<tr>
	    			<td>所属部门:</td>
	    			<td>
	    			<select id="cc2"  name="dept_id" data-options="required:true" style="width:200px;">
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>职   务:</td>
	    			<td>
	    			<select class="easyui-combobox" name="office" data-options="required:true" style="width:100px;">
							<option value="1">科长</option>
							<option value="2">处长</option>
							<option value="3">局长</option>
							<option value="4">组长</option>
					</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>职   级:</td>
	    			<td>
	    			<select class="easyui-combobox" name="rank" data-options="required:true" style="width:100px;">
							<option value="1">老师</option>
							<option value="2">校长</option>
							<option value="3">主任</option>
							<option value="4">播音</option>
					</select>
	    			</td>
	    		</tr>
	    	</table>
	    		<br>
    		<table cellpadding="5">
	    		<a class="easyui-linkbutton c4">联系方式</a>
	    		<tr>
	    			<td>邮    箱:</td>
	    			<td><input class="easyui-textbox" type="text" name="email" data-options="required:true" style="width:200px;"></input></td>
	    		</tr>
	    		<tr>
	    			<td>手    机:</td>
	    			<td><input class="easyui-textbox" type="text" name="telphone" data-options="required:true"></input></td>
	    		</tr>
	    		
	    	</table>
	    </form>
	    </div>
	</div>
</body>
</html>