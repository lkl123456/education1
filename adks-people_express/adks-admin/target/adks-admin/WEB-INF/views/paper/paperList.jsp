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
	src="${pageContext.request.contextPath}/static/paper/js/paper.js"></script>
</head>
<body class="easyui-layout">
	<!-- 工具栏 -->
	<div id="tb" style="padding:15px 10px 10px 10px;height:auto; overflow:hidden; ">
		<div style="float:left;">
			试卷名称:<input id="s_paperName" class="easyui-textbox"
				data-options="prompt:'试卷名称...'" style="width: 200px; height: 24px;" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search"
				onclick="doSearch()">查询</a> &nbsp;&nbsp;
		</div>
		<div style="margin-bottom:5px; float:right;">
			<a href="#" onclick="add_paper()" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">添加</a> <a href="#"
				onclick="edit_paper()" class="easyui-linkbutton" iconCls="icon-edit"
				plain="true">编辑</a> <a href="#" onclick="view_paper()"
				class="easyui-linkbutton" iconCls="icon-edit" plain="true">预览</a> <a
				href="#" onclick="del_paper(-1)" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true">删除</a>
		</div>
	</div>

	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<div title="试卷列表信息" data-options="iconCls:'icon icon-nav',fit:true">
			<table id="paperList_table" style="width: 800px; height: 100%;"
				class="easyui-datagrid" toolbar="#tb"
				data-options="
			url:'${pageContext.request.contextPath}/paper/getPaperListJson',
			method:'post',
			singleSelect:false,
			fit:true,
			fitColumns:true,
			striped:true,
			nowarp:false,
			pagination:true,
			pageSize:10,
			pageList:[20,25,30],
			rownumbers:true
			">
				<thead>
					<tr>
						<th data-options="field:'paperId',checkbox:true"></th>
						<th data-options="field:'paperName'" align="center" width="80">试卷名称</th>
						<th data-options="field:'createTime_str',align:'center'"
							width="100">创建时间</th>
						<th data-options="field:'creatorName',align:'center'" width="100">创建人</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

	<!-- 添加试卷对话框 -->
	<div id="dlg_paper" class="easyui-dialog" fit="true" title="添加试卷"
		style="width: 600px; height: 500px; padding: 10px;"
		data-options="
				iconCls: 'icon-save',
				modal:true,closed:true,
				buttons: [{
					text:'保存并下一步',
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
			<form id="paperForm" method="post">
				<input type="hidden" name="paperId" />
				<table cellpadding="5">
					<tr>
						<td>试卷名称:</td>
						<td><input class="easyui-textbox" onblur="checkPaperName();"
							type="text" name="paperName" id="paperName"
							data-options="required:true,validType:['isNullOrEmpty','length[1,50]']"></input></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>