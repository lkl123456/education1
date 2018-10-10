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
	src="${pageContext.request.contextPath}/static/grade/js/gradeExamInfo.js"></script>
</head>
<body class="easyui-layout">
	<input type="hidden" id="examGradeid" />
	<input type="hidden" id="examId" value="${examId }" />
	<!-- 工具栏 -->
	<div id="tb" style="padding:15px 10px 10px 10px;height:auto; overflow:hidden; ">
		<div style="float:left;">
			班级名称:<input id="gradeName" class="easyui-textbox"
				data-options="prompt:'班级名称...'" style="width: 200px; height: 24px;" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search"
				onclick="doSearch()">查询</a> &nbsp;&nbsp;
		</div>
		<div style="margin-bottom:5px; float:right;">
			<a href="#" onclick="add_grade()" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">添加</a><a href="#"
				onclick="del_grade()" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true">取消授权</a>
		</div>
	</div>

	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<div title="班级列表信息" data-options="iconCls:'icon icon-nav',fit:true">
			<table id="gradeExaming" style="width: 800px; height: 100%;"
				class="easyui-datagrid" toolbar="#tb"
				data-options="
			url:'${pageContext.request.contextPath}/gradeExam/gradeExamInfoList?examId=${examId}',
			method:'get',
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
						<th data-options="field:'gradeId',checkbox:true"></th>
						<th data-options="field:'gradeName',align:'center'" width="100">班级名称</th>
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
							type="text" name="paperName" data-options="required:true"></input></td>
					</tr>
				</table>
			</form>
		</div>
	</div>



</body>
</html>