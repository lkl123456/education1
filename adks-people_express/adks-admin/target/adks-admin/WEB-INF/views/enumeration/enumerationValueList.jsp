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
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/enumeration/js/enumerationValue.js"></script>
    
</head>
<body class="easyui-layout">	
	<!-- 工具栏 -->
	<div id="tb" style="padding:15px 10px 10px 10px;height:auto; overflow:hidden; ">
		<div style="float:left;">
			<input id="s_enVal_name" class="easyui-textbox" data-options="prompt:'枚举值名称...'" style="width:200px;height:24px;" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
		</div>
		<div style="margin-bottom:5px; float:right;">
			<a href="#" onclick="addEnVal()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
			<a href="#" onclick="edit_en()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a>
			<a href="#" onclick="del_en(-1)" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		</div>
	</div>
	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<div title="数据列表信息" data-options="iconCls:'icon icon-nav',fit:true">
	<table id="enumerationList_table" style="width:800px;height:350px;" class="easyui-datagrid"
			toolbar="#tb" data-options="
			url:'${pageContext.request.contextPath}/enumerationValue/getEnumerationValueListJson?enId='+${enumer.enId },
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
				<th data-options="field:'enValueId',checkbox:true"></th>
				<th data-options="field:'valName'" align="center" width="80">枚举值名称</th>
				<th data-options="field:'valDisplay',align:'center'" width="100">枚举值显示的名称</th>
				<th data-options="field:'valDesc',align:'center'" width="100">描述</th> 
				<th data-options="field:'enName',align:'center',formatter:formatOper" width="100">枚举名称</th>
				<th data-options="field:'creatorName',align:'center'" width="100">创建人</th>
				<th data-options="field:'createTime',align:'center'" width="80">创建时间</th>
			</tr>
		</thead>
	</table>
	</div>
	</div>
	<!-- 添加网校对话框 -->
	<div id="dlg" class="easyui-dialog" title="添加枚举" style="width:430px;height:240px;padding:10px;"
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
		<input type="hidden" id="enName"  value="${enumer.enName }" />
		<input type="hidden" id="enIdOne" value="${enumer.enId }" />
		<div style="padding-top: 2px; padding-left: 20px;">
	    <form id="enumerationForm" method="post" action="${pageContext.request.contextPath}/enumerationValue/saveEnumerationValue">
	    	<input type="hidden" id="enValueId" name="enValueId" value="" />
	    	<input type="hidden" id="enId" name="enId" value="${enumer.enId }" />
	    	<input type="hidden" id="creatorId" name="creatorId" value="" />
	    	<input type="hidden" id="creatorName" name="creatorName" value="" />
	    	<table cellpadding="5">
	    		<tr>
	    			<td>枚举值名称:</td>
	    			<td><input class="easyui-textbox" id="valName" type="text" name="valName" data-options="required:true,validType:['isNullOrEmpty','length[1,32]']"></input></td>
	    		</tr>
	    		<tr>
	    			<td>枚举值显示的名称:</td>
	    			<td><input class="easyui-textbox" id="valDisplay" type="text" name="valDisplay" data-options="required:true,validType:['isNullOrEmpty','length[1,32]']"></input></td>
	    		</tr>
	    		<tr>
	    			<td>描述:</td>
	    			<td><input class="easyui-textbox" id="valDesc" type="text" name="valDesc" data-options="validType:['isNullOrEmpty','length[0,250]']"></input></td>
	    		</tr>
	    		<tr>
	    			<td>所属枚举:</td>
	    			<td><input  disabled="disabled" type="text"  id="ss1" ></input></td>
	    		</tr>
	    	</table>
	    </form>
	    </div>
	</div>
</body>
</html>