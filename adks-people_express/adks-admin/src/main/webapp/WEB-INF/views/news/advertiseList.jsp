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
<title>广告位管理</title>
<%@include file="../public/header.jsp"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/news/advertise.js"></script>

</head>
<body class="easyui-layout">
	<div data-options="region:'west',split:true,title:'机构列表'"
		style="width: 260px; padding: 10px;">
		<input type="hidden" id="ztree_orgId" />
		<div>
			<ul id="orgTreeDemo" class="ztree"></ul>
		</div>
	</div>
	<!-- 工具栏 -->
	<div id="tb"
		style="padding: 15px 10px 10px 10px; height: auto; overflow: hidden;">
		<div style="float: left;">
			<c:if test="${!empty isPlatform}">
				<input id="s_advertise_orgSnname" class="easyui-combobox"
					data-options="url:'${pageContext.request.contextPath}/org/orgListSelJson',method:'get',valueField:'uname_sn',textField:'name',prompt:'选择网校...',onChange:function(){doSearch()}"
					style="width: 200px;" />&nbsp;
			</c:if>
			<input type="hidden" id="isPlatform" value="${isPlatform }" />
			<!-- <input id="s_advertise_name" class="easyui-textbox" data-options="prompt:'广告位...'" style="width:200px;height:24px;" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a> -->
		</div>
		<div style="margin-bottom: 5px; float: right;">
			<a href="#" onclick="add_advertise();" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">添加</a> <a href="#"
				onclick="edit_advertise()" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true">编辑</a> <a href="#"
				onclick="del_advertise(-1)" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true">删除</a>
		</div>
	</div>
	<input type="hidden" id="imgServer" value="${ossResource}" />
	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<div title="广告位列表数据" data-options="iconCls:'icon icon-nav',fit:true">
			<table id="advertiselistdatagrid" style="width: 800px; height: 350px"
				class="easyui-datagrid" toolbar="#tb"
				data-options="
					url:'${pageContext.request.contextPath}/advertise/getAdvertiseListJson',
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
						<th data-options="field:'adId',checkbox:true"></th>
						<th data-options="field:'orgName'" align="center" width="100">学校名称</th>
						<th data-options="field:'adLocation',align:'center'"
							formatter="locationStatus" width="80">广告图位置</th>
						<th data-options="field:'adHref',align:'center'" width="80">广告链接</th>
						<th data-options="field:'createTimeStr',align:'center'" width="80">创建时间</th>
						<th data-options="field:'status',align:'center',"
							formatter="fromartStatus" width="50">发布状态</th>
						<th data-options="field:'orgId'" align="center" width="80"
							hidden="true">orgId</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

	<!-- 添加新闻信息div -->
	<div id="dlg_advertise" class="easyui-dialog" title="添加广告位"
		style="width: 520px; height: 380px; padding: 10px;"
		data-options="
				iconCls: 'icon-save',
				modal:true,closed:true,
				buttons: [{
					text:'发布',
					iconCls:'icon-ok',
					handler:function(){
						submitAdd_advertise();
					}
				},{
					text:'取消',
					iconCls:'icon-cancel',
					handler:function(){
						cancleAdd_advertise();
					}
				}]
			">

		<div style="padding-left: 20px; padding-top: 2px;">
			<form id="advertiseForm" method="post" enctype="multipart/form-data"
				action="${pageContext.request.contextPath}/advertise/saveAdvertise">
				<input type="hidden" id="t_id" name="adId"> <input
					type="hidden" id="orgId" name="orgId"> <input type="hidden"
					id="orgCode" name="orgCode"> <input type="hidden"
					id="orgName" name="orgName">
				<table cellpadding="5">
					<tr>
						<td>广告图片:</td>
						<td><img id="aip" height="100" width="100" /> <br /> <input
							class="easyui-filebox" id="adImgPathFile" name="adImgPathFile"
							data-options="prompt:'选择一张广告图片...'" style="width: 200px"></input>
							<input type="hidden" name="adImgPath" id="adImgPath" /></td>
					</tr>
					<tr>
						<td>广告位位置:</td>
						<td><select class="easyui-combobox"
							data-options="panelHeight:'auto',editable:false"
							style="width: 100px" name="adLocation">
								<option value="1" selected="selected">中上</option>
								<option value="2">中下</option>
								<option value="3">左侧竖联</option>
								<option value="4">右侧竖联</option>
								<option value="5">飘窗</option>
								<option value="6">左下角浮动</option>
						</select></td>
					</tr>
					<tr>
						<td>广告链接:</td>
						<td><input class="easyui-textbox" type="text" id="adHref"
							name="adHref" /></td>
					</tr>
					<tr>
						<td>是否启用:</td>
						<td><input type="radio" name="status" value="1"
							data-options="required:true" /><span>启用</span> <input
							type="radio" name="status" value="2" data-options="required:true" /><span>禁止</span>
						</td>
					</tr>
				</table>
			</form>

		</div>
	</div>

</body>
</html>