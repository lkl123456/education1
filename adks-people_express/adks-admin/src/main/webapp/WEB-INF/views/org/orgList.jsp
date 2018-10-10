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
<%@include file="../public/header.jsp"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/org/js/org.js"></script>

</head>

<body class="easyui-layout">
	<!-- 工具栏 -->
	<div id="tb" style="padding:15px 10px 10px 10px;height:auto; overflow:hidden; ">
		<div style="float:left;">
			<input id="s_org_name" class="easyui-textbox"
				data-options="prompt:'机构名称...'" style="width: 200px; height: 24px;" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search"
				onclick="doSearch()">查询</a> &nbsp;&nbsp;
			<!-- <a href="#" onclick="del_org(2);" class="easyui-linkbutton" iconCls="icon-lock" >禁用</a>
			&nbsp;<a href="#" onclick="del_org(1);" class="easyui-linkbutton" iconCls="icon-ok" >开通</a> -->
		</div>
		<div style="margin-bottom:5px; float:right;">
			<a href="#" onclick="add_org()" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">添加</a> <a href="#"
				onclick="edit_org()" class="easyui-linkbutton" iconCls="icon-edit"
				plain="true">编辑</a> <a href="#" onclick="del_org(-1)"
				class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
			<a href="javascript: editOrgVal()" class="easyui-linkbutton"
				iconCls="icon-tip" plain="true">查看子级机构</a>
			<c:if test="${! empty parentId && parentId != 0 && parentId !=overParentId }">
				<a href="javascript: editOrgValBefore()" class="easyui-linkbutton"
					iconCls="icon-tip" plain="true">返回上一级</a>
			</c:if>

		</div>
	</div>

	<input id="orgParentId" value="${parentId }" />
	<input id="orgParentName" value="${parentName }" />
	<input id="overParentId" value="${overParentId }" />

	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<div title="数据列表信息" data-options="iconCls:'icon icon-nav',fit:true">
			<table id="orgList_table" style="width: 800px; height: 100%;"
				class="easyui-datagrid" toolbar="#tb"
				data-options="
			url:'${pageContext.request.contextPath}/org/getOrgListJson?parentId='+${parentId },
			method:'post',
			singleSelect:false,
			fit:true,
			fitColumns:true,
			striped:true,
			nowarp:false,
			pagination:true,
			pageSize:10,
			pageList:[25,30,35],
			rownumbers:true
			">
				<thead>
					<tr>
						<th data-options="field:'orgId',checkbox:true"></th>
						<th data-options="field:'orgName'" align="center" width="80">机构名称</th>
						<th data-options="field:'parentName',align:'center'" width="100">父机构名称</th>
						<th data-options="field:'usernum',align:'center'" width="100">总人数</th>
						<th data-options="field:'orgstudylong',align:'center'" width="100">总流量</th>
						<th data-options="field:'creatorName',align:'center'" width="100">创建人</th>
						<th data-options="field:'createtime',align:'center'" width="90">创建时间</th>
						<th
							data-options="field:'_operate',width:80,align:'center',formatter:formatOper">配置信息</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

	<!-- 添加机构对话框 -->
	<div id="dlg" class="easyui-dialog" title="添加机构"
		style="width: 430px; height: 220px; padding: 10px;"
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
			<form id="orgForm" method="post"
				action="${pageContext.request.contextPath}/org/saveOrg">
				<input type="hidden" id="orgId" name="orgId" value="" /> <input
					type="hidden" id="parentId" name="parentId" value="" /> <input
					type="hidden" id="parentName" name="parentName" value="" /> <input
					type="hidden" id="usernum" name="usernum" value="" /> <input
					type="hidden" id="orgstudylong" name="orgstudylong" value="" /> <input
					type="hidden" id="creatorId" name="creatorId" value="" /> <input
					type="hidden" id="creatorName" name="creatorName" value="" />
				<table cellpadding="5">
					<tr>
						<td>父级机构名称:</td>
						<td><input id="orgNameOrgShow" type="text"
							disabled="disabled"></input></td>
					</tr>
					<tr>
						<td>机构名称:</td>
						<td><input class="easyui-textbox" id="orgName" type="text"
							name="orgName" data-options="required:true,validType:['isNullOrEmpty','length[1,64]']"></input></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<input type="hidden" id="ossPath" value="${ossResource }" />
	<!-- 添加机构配置信息对话框 -->
	<div id="dlgConfig" class="easyui-dialog" title="添加机构配置信息"
		style="width: 480px; height: 500px; padding: 10px;"
		data-options="
				iconCls: 'icon-save',
				modal:true,closed:true,
				buttons: [{
					text:'保存',
					iconCls:'icon-ok',
					handler:function(){
						submitFormConfig();
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
			<form id="orgConfigForm" method="post"
				action="${pageContext.request.contextPath}/org/saveOrgConfig"
				enctype="multipart/form-data">
				<input type="hidden" id="orgConfigId" name="orgConfigId" value="" />
				<input type="hidden" id="orgConId" name="orgId" value="" /> 
				<inputtype="hidden" id="orgConName" name="orgName" value="" />
				<input type="hidden" id="orgLogoPathTrue" name="orgLogoPath" />
				<input type="hidden" id="orgBannerTrue" name="orgBanner" />
				
				<table cellpadding="5">
					<tr>
						<td>机构名称:</td>
						<td><input id="orgNameShow" type="text" disabled="disabled"></input></td>
					</tr>
					<tr>
						<td>机构域名:</td>
						<td><input class="easyui-textbox" id="orgUrl" name="orgUrl"
							type="text"></input></td>
					</tr>
					<tr>
						<td>机构Logo:</td>
						<td><img id="orgLogoPath" height="86" width="131" /> <br />
							<input class="easyui-filebox" id="orgLogoPathfile"
							name="orgLogoPathfile" data-options="prompt:'选择一张机构Logo...'"
							style="width: 80%"> </input>
						</td>
					</tr>
					<tr>
						<td>首页Banner:</td>
						<td><img id="orgBanner" height="86" width="131" /> <br /> <input
							class="easyui-filebox" id="orgBannerfile" name="orgBannerfile"
							data-options="prompt:'选择一张首页Banner...'" style="width: 80%">
							</input>
						</td>
					</tr>
					<tr>
						<td>联系人姓名:</td>
						<td><input class="easyui-textbox" id="contactUser"
							name="contactUser" type="text"></input></td>
					</tr>
					<tr>
						<td>联系人电话:</td>
						<td><input class="easyui-textbox" id="contactPhone"
							name="contactPhone" type="text"></input></td>
					</tr>
					<tr>
						<td>联系人QQ:</td>
						<td><input class="easyui-textbox" id="contactQQ"
							name="contactQQ" type="text"></input></td>
					</tr>
					<tr>
						<td>联系人邮箱:</td>
						<td><input class="easyui-textbox" id="contactMail"
							name="contactMail" type="text"></input></td>
					</tr>
					<tr>
						<td>机构描述:</td>

						<td><textarea id="orgDesc" style="width: 320px; height: 60px"
								name="orgDesc">
	    				</textarea></td>
					</tr>
					<tr>
						<td>版权信息:</td>
						<td><textarea id="copyRight"
								style="width: 320px; height: 60px" name="copyRight">
	    				</textarea></td>
					</tr>
				</table>
			</form>
		</div>
	</div>

</body>
</html>