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
    <title>友情链接管理</title>
	<%@include file="../public/header.jsp" %>   	
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/news/friendlylink.js"></script>

</head>
<body class="easyui-layout">
	<div data-options="region:'west',split:true,title:'机构列表'" style="width:260px;padding:10px;">
		<input type="hidden" id="ztree_orgId" />
		<div>
			<ul id="orgTreeDemo" class="ztree"></ul>
		</div>
	</div>

	<!-- 工具栏 -->
	<div id="tb" style="padding:15px 10px 10px 10px;height:auto; overflow:hidden; ">
		<div style="float:left;">
			<c:if test="${!empty isPlatform}">
			 <input id="s_friendlylink_orgSnname" class="easyui-combobox" data-options="url:'/org/orgListSelJson',method:'get',valueField:'uname_sn',textField:'name',prompt:'选择网校...',onChange:function(){doSearch()}" style="width:200px;" />&nbsp;
			</c:if>
			<input type="hidden" id="isPlatform" value="${isPlatform }" />
			<input id="s_friendlylink_name" class="easyui-textbox" data-options="prompt:'友情链接名...'" style="width:200px;height:24px;" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
		</div>
		<div style="margin-bottom:5px; float:right;">
			<a href="#" onclick="add_friendlylink();" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
			<a href="#" onclick="edit_friendlylink()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a>
			<a href="#" onclick="del_friendlylink(-1)" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		</div>
	</div>
	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<div title="新闻列表数据" data-options="iconCls:'icon icon-nav',fit:true">
			<table id="friendlylinklistdatagrid" style="width:800px;height:350px" class="easyui-datagrid" 
					toolbar="#tb" data-options="
					url:'${pageContext.request.contextPath}/friendlylink/getFriendlylinkListJson',
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
						<th data-options="field:'fdLinkId',checkbox:true"></th>
						<th data-options="field:'fdLinkName'" align="center" width="100">链接名称</th>
						<th data-options="field:'fdLinkHref'" align="center" width="100">链接地址</th>
						<th data-options="field:'orgName'" align="center" width="100">机构</th>
						<th data-options="field:'createTimeStr',align:'center'" width="80">发布时间</th>
						<th data-options="field:'creatorName',align:'center'" width="80">发布人</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
		
	<!-- 添加新闻信息div  -->
	<div id="dlg_friendlylink" class="easyui-dialog" title="添加友情链接" style="width:620px;height:580px;padding:10px;"
			data-options="
				iconCls: 'icon-save',
				modal:true,closed:true,
				buttons: [{
					text:'发布',
					iconCls:'icon-ok',
					handler:function(){
						submitAdd_friendlylink();
					}
				},{
					text:'取消',
					iconCls:'icon-cancel',
					handler:function(){
						cancleAdd_friendlylink();
					}
				}]
			">
		<div style="padding-left: 20px; padding-top: 2px;">
	    <form id="friendlylinkForm" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/friendlylink/saveFriendlylink">
	    	<input type="hidden" id="t_id" name="fdLinkId" >
	    	<input type="hidden" id="orgId" name="orgId" >
	    	<table cellpadding="5">
	    		<tr>
	    			<td>链接名称:</td>
	    			<td><input class="easyui-textbox" type="text" id="" name="fdLinkName" data-options="required:true,validType:['isNullOrEmpty','length[2,20]']" style="width: 238px;"></td>
	    		</tr>
	    		<tr>
	    			<td>链接地址:</td>
	    			<td><input class="easyui-textbox" type="text" id="" name="fdLinkHref" data-options="required:true,validType:'url'"></td>
	    		</tr>
	    	</table>
	    </form>
	    
	    </div>
	</div>
	
</body>
</html>