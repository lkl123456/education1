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
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/zhiji/js/zhiji.js"></script>
    
</head>
<body class="easyui-layout">	
	<!-- 工具栏 -->
	<div id="tb" style="padding:15px 10px 10px 10px;height:auto; overflow:hidden; ">
		<div style="float:left;">
			<input id="s_org_name" class="easyui-textbox" data-options="prompt:'职级名称...'" style="width:200px;height:24px;" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
			&nbsp;&nbsp;<!-- <a href="#" onclick="del_org(2);" class="easyui-linkbutton" iconCls="icon-lock" >禁用</a>
			&nbsp;<a href="#" onclick="del_org(1);" class="easyui-linkbutton" iconCls="icon-ok" >开通</a> -->
		</div>
		<div style="margin-bottom:5px; float:right;">
			<a href="#" onclick="add_zhiji('${parentName }')" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
			<a href="#" onclick="edit_zhiji('${parentName }')" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a>
			<a href="#" onclick="del_zhiji(-1)" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
			<a href="javascript: editZhijiVal()" class="easyui-linkbutton" iconCls="icon-tip" plain="true">查看子级职级</a>
			<c:if test="${! empty parentId && parentId != 0}">
				<a href="javascript: editZhijiValBefore()" class="easyui-linkbutton" iconCls="icon-tip" plain="true">返回上一级</a>
			</c:if>
		</div>
	</div>
	
	<input type="hidden" id="zhijiParentId" value="${parentId }" />
	<input type="hidden" id=zhijiParentName" value="${parentName }" />
	<input type="hidden" id="overParentId" value="${overParentId }" />
	
	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<div title="数据列表信息" data-options="iconCls:'icon icon-nav',fit:true">
	<table id="zhijiList_table" style="width:800px;height:100%;" class="easyui-datagrid"
			toolbar="#tb" data-options="
			url:'${pageContext.request.contextPath}/zhiji/getZhijiListJson?parentId='+${parentId },
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
				<th data-options="field:'rankId',checkbox:true"></th>
				<th data-options="field:'rankName'" align="center" width="80">职级名称</th>
				<th data-options="field:'parentName',align:'center'" width="100">父名称</th>
				<th data-options="field:'orderNum',align:'center'" width="100">排序</th>
			</tr>
		</thead>
	</table>
	</div>
	</div>
	
	<!-- 添加机构对话框 -->
	<div id="dlg" class="easyui-dialog" title="添加机构" style="width:430px;height:220px;padding:10px;"
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
	    <form id="zhijiForm" method="post" action="${pageContext.request.contextPath}/zhiji/saveZhiji">
	    	<input type="hidden" id="rankId" name="rankId" value="" />
	    	<input type="hidden" id="parentId" name="parentId" value="" />
	    	<input type="hidden" id="parentName" name="parentName" value="" />
	    	<table cellpadding="5">
	    		<tr>
	    			<td>父级名称:</td>
	    			<td><input id="parentNameShow" readonly="readonly" type="text" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>职级名称:</td>
	    			<td><input class="easyui-textbox" id="rankName" type="text" name="rankName" data-options="required:true,validType:['isNullOrEmpty','length[1,32]']"></input></td>
	    		</tr>
	    		<tr>
	    			<td>排序:</td>
	    			<td><input class="easyui-numberbox " id="orderNum" type="text" name="orderNum" ></input></td>
	    		</tr>
	    	</table>
	    </form>
	    </div>
	</div>
	
</body>
</html>