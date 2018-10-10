<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript">
	var contextpath = '${pageContext.request.contextPath}';
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户管理</title>
<%@include file="../public/header.jsp"%>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/user/js/user.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'west',split:true,title:'机构列表'" style="width: 260px; padding: 10px;">
		<div>
			<ul id="treeDemo" class="ztree"></ul>
		</div>
	</div>
	<!-- 工具栏 -->
	<div id="tb" style="padding:15px 10px 10px 10px;height:auto; overflow:hidden; ">
		<div style="float:left;">
			<input id="loginname" class="easyui-textbox" data-options="prompt:'用户名...'" style="width:150px; height: 24px;">
			职级：<select id="zhijiTree" name="rankId" style="width:70px;"></select>
			用户状态：<select id="userStatus" class="easyui-combobox" name="userStatus" style="width:70px;" data-options="required:true,editable:false">
							<option value="1">开通</option>
							<option value="2">禁用</option>
					</select>
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
			<a href="#" onclick="changeUserStatus('resetpass');" class="easyui-linkbutton" iconCls="icon-reload">重置密码</a> 
			<a href="#" onclick="changeUserStatus('stop');" class="easyui-linkbutton" iconCls="icon-lock">停用</a> 
			<a href="#" onclick="changeUserStatus('continue');" class="easyui-linkbutton" iconCls="icon-undo">恢复</a>
		</div>
		<div style="margin-bottom:5px; float:right;">
			<!-- <a href="#" onclick="addDialog('useradd');" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>  -->
			<a href="#" onclick="addUser();" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a> 
			<a href="#" onclick="editUser();" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a> 
			<a href="#" onclick="deleteUser();" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
			<a href="#" onclick="importUser();" class="easyui-linkbutton" iconCls="icon-save" plain="true">批量导入</a> 
			<a href="#" onclick="updateUserRole();" class="easyui-linkbutton" iconCls="icon-tip" plain="true">修改用户角色</a>
		</div>
	</div>
	<div id="mainText" data-options="region:'center'">
		<table id="userlistdatagrid" style="width: 800px; height: 350px"
			class="easyui-datagrid" toolbar="#tb"
			data-options="
					url:'${pageContext.request.contextPath}/userIndex/getUsersJson',
					method:'post',
					singleSelect:false,
					fit:true,
					fitColumns:true,
					nowarp:false,
					pagination:true,
					pageSize:20,
					pageList:[20,25,30],
					rownumbers:true,
					rowStyler:setRowStyle
					">
			<thead>
				<tr>
					<th data-options="field:'userId',checkbox:true"></th>
					<th data-options="field:'userName'" align="center" width="100">用户名</th>
					<th data-options="field:'userRealName',align:'center'" width="80">真实姓名</th>
					<th data-options="field:'orgName',align:'center'" width="150">所属机构</th>
					<th data-options="field:'userMail',align:'center'" width="150">邮箱</th>
					<th data-options="field:'userPhone',align:'center'" width="90">电话</th>
					<th data-options="field:'userBirthday',align:'center'" width="90">生日</th>
					<th data-options="field:'userStatus',align:'center'"
						formatter="formatColumnData" width="50">用户状态</th>
				</tr>
			</thead>
		</table>
	</div>
	<input type="hidden" id="ossPath" value="${ossResource }" />

	<!-- 添加用户div -->
	<div id="dlg_user" class="easyui-dialog" title="添加用户"
		style="width: 700px; height: 600px; padding: 10px;"
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
		<div style="padding: 10px 120px 20px 120px">
			<form id="hh" method="post" action="${pageContext.request.contextPath}/userIndex/saveUser"
				enctype="multipart/form-data">
				<input type="hidden" id="userId" name="userId" value="" />
				<input type="hidden" id="orgId" name="orgId" value="" />
				<input type="hidden" id="orgNameTrue" name="orgName" value="" /> 
				<input type="hidden" id="rankName" name="rankName" value="" /> 
				<input type="hidden" id="orgCode" name="orgCode" value="" /> 
				<input type="hidden" id="headPhoto" name="headPhoto" />
				<table cellpadding="5">
					<a class="easyui-linkbutton c4">基本信息</a>
					<tr>
						<td>用户名:</td>
						<td><input class="easyui-textbox" type="text" name="userName" id="userName"
							data-options="required:true,validType:['isNullOrEmpty','length[1,32]']"></input> <span style="color: red"> * </span></td>
					</tr>
					<tr>
						<td>密码:</td>
						<td><input class="easyui-textbox" type="password" name="userPassword" id="userPassword" 
							data-options="required:true,validType:['isNullOrEmpty','length[6,32]']"></input><span style="color: red"> * </span></td>
					</tr>
					<tr>
						<td>姓 名:</td>
						<td><input class="easyui-textbox" type="text" id="userRealName"
							name="userRealName" data-options="required:true,validType:['isNullOrEmpty','length[1,32]']"></input><span style="color: red"> * </span></td>
					</tr>
					<tr>
						<td>性别:</td>
						<td><input type="radio" name="userSex"
							value="1" />男 <input type="radio" name="userSex" value="2" />女</td>
					</tr>
					<tr>
						<td>用户图像:</td>
						<td><img id="uiPath" height="100px" width="100px" /> <br /> <input
							class="easyui-filebox" id="headPhotofile" name="headPhotofile"
							data-options="prompt:'选择一张头像...'" style="width: 200px"></input></td>
					</tr>
					<tr>
						<td>身份证号:</td>
						<td><input class="easyui-textbox" type="text" id="cardNumer"
							name="cardNumer"></input></td>
					</tr>
					<tr>
						<td>出生日期:</td>
						<td><input id="userBirthday" type="text"
							class="easyui-datebox" data-options="editable:false"></input></td>
					</tr>
					<tr>
						<td>民族:</td>
						<td><input class="easyui-combobox" data-options="required:true,editable:false" type="text" id="ss1"
							name="userNationality"></input> <span style="color: red"> * </span></td>
					</tr>
				</table>
				<table cellpadding="5">
					<a class="easyui-linkbutton c4">联系方式</a>
					<tr>
						<td>邮 箱:</td>
						<td><input class="easyui-textbox" type="text" name="userMail" id="userMail"
							style="width: 200px;"></input></td>
					</tr>
					<tr>
						<td>手 机:</td>
						<td><input class="easyui-textbox" type="text" id="userPhone"
							name="userPhone"></input></td>
					</tr>

				</table>

				<table cellpadding="5">
					<a class="easyui-linkbutton c4">学习信息</a>
					<tr>
						<td>所属部门:</td>
						<td><input type="text" name="orgName" id="orgName"></input>
						<!-- <select id="cc2" name="orgId" data-options="required:true,editable:false" style="width: 200px;"></select> -->
							<ul id="treeDemo1" class="ztree"></ul>
							<span style="color: red"> * </span></td>
					</tr>
					<tr>
						<td>政治面貌:</td>
						<td><input class="easyui-combobox" id="ss2" name="userParty" data-options="required:true,editable:false" 
							style="width: 100px;"> <span style="color: red"> * </span> <!-- <option value="1">团员</option>
							<option value="2">党员</option>
							<option value="3">无</option> --> </input></td>
					</tr>
					<tr>
						<td>职 级:</td>
						<td><select id="zhijiTree" name="rankId"  
							style="width: 200px;"></select>
						</td>
					</tr>
					<tr>
						<td>用户状态:</td>
						<td><input type="radio" name="userStatus" checked="checked"
							value="1" />开通 <input type="radio" name="userStatus" value="2" />停用
						</td>
					</tr>
					<tr>
						<td>有效期至:</td>
						<td><input id="overdate" type="text" class="easyui-datebox" data-options="editable:false"></input>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<!-- 导入用户对话框 -->
	<div id="importUser" class="easyui-dialog" title="导入用户"
		style="width: 580px; height: 240px; padding: 10px;"
		data-options="
				iconCls: 'icon-save',
				modal:true,closed:true,
				buttons: [{
					text:'确定',
					iconCls:'icon-ok',
					handler:function(){
						submitImport();
					}
				},{
					text:'取消',
					iconCls:'icon-cancel',
					handler:function(){
						cancleImport();
					}
				}]
			">
		<div style="padding: 10px 120px 20px 120px">
			<form id="imUser" method="post" enctype="multipart/form-data">
				<table cellpadding="5">
					<tr>
						<td>选择部门:</td>
						<td>
							<input type="text" id="orgNameShow" onclick="showOrgTree();"></input><span style="color: red"> * </span>
							<ul id="importTreeDemo" class="ztree" style="display:none;"></ul>
						</td>
					</tr>
					<tr>
						<td>Excel模板下载:</td>
						<td><a href="${pageContext.request.contextPath}/static/user/importUser.xls">template.xls</a> </input></td>
					</tr>
					<tr>
						<td>导入文件:</td>
						<td><input class="easyui-filebox" name="importExcelFile" id="importExcelFile"
							data-options="prompt:'选择模板文件...',required:true"
							style="width: 200px;"> </input></td>
					</tr>
				</table>
				<input type="hidden" id="userOrgId">
				<input type="hidden" id="userOrgName">
				<input type="hidden" id="userOrgCode">
			</form>
		</div>
	</div>
</body>
</html>