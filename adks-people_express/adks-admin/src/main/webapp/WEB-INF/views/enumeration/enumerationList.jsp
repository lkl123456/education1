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
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/enumeration/js/enumeration.js"></script>
   
    
</head>
<body class="easyui-layout">	
	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<div title="数据列表信息" data-options="iconCls:'icon icon-nav',fit:true">
		<!-- 工具栏 -->
	<div id="tb" style="padding:15px 10px 10px 10px;height:auto; overflow:hidden; ">
		<div style="float:left;">
			<input id="s_en_name" class="easyui-textbox" data-options="prompt:'枚举名称...'" style="width:200px;height:24px;" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
			&nbsp;&nbsp;
		</div>
		<div style="margin-bottom:5px; float:right;">
			<a href="#" onclick="add_en()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加枚举</a>
			<a href="#" onclick="edit_en()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a>
			<a href="#" onclick="del_en(-1)" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
			<a href="javascript: editEnmVal()" class="easyui-linkbutton" iconCls="icon-tip" plain="true">查看枚举值</a>
		</div>
	</div>
	<table id="enumerationList_table" style="width:800px;height:350px;" class="easyui-datagrid"
			toolbar="#tb" data-options="
			url:'${pageContext.request.contextPath}/enumeration/getEnumerationListJson',
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
				<th data-options="field:'enId',checkbox:true"></th>
				<th data-options="field:'enDisplay'" align="center" width="80">枚举名称</th>
				<th data-options="field:'enName',align:'center'" width="100">枚举代码</th>
				<th data-options="field:'creatorName',align:'center'" width="100">创建人</th>
				<th data-options="field:'createTime',align:'center'" width="80">创建时间</th>
				<!-- <th data-options="field:'_operate',width:80,align:'center',formatter:formatOper">操作</th> -->  
			</tr>
		</thead>
	</table>
	</div>
	
	</div>
	
	<!-- 添加网校对话框 -->
	<div id="dlg" class="easyui-dialog" title="添加枚举" style="width:430px;height:250px;padding:10px;"
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
	    <form id="enumerationForm" method="post" action="${pageContext.request.contextPath}/enumeration/saveEnumeration">
	    	<input type="hidden" id="enId" name="enId" value="" />
	    	<input type="hidden" id="creatorId" name="creatorId" value="" />
	    	<input type="hidden" id="creatorName" name="creatorName" value="" />
	    	<table cellpadding="5">
	    		<tr>
	    			<td>枚举名称:</td>
	    			<td><input class="easyui-textbox" id="enDisplay" type="text" name="enDisplay" data-options="required:true,validType:['isNullOrEmpty','length[1,32]']"></td>
	    		</tr>
	    		<tr>
	    			<td>枚举代码:</td>
	    			<td></input><input class="easyui-textbox" id="enName" type="text" name="enName" data-options="required:true,validType:['isNullOrEmpty','length[1,32]']"></input></td>
	    		</tr>
	    		<tr>
	    			<td>描述:</td>
	    			<td><textarea style="width: 200px; height: 30px"  id="enDesc" name="enDesc"></textarea></td>
	    		</tr>
	    	</table>
	    </form>
	    </div>
	</div>
</body>
</html>