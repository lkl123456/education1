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
	src="${pageContext.request.contextPath}/static/exam/js/exam.js"></script>
</head>
<body class="easyui-layout">
	<!-- 工具栏 -->
	<div id="tb" style="padding:15px 10px 10px 10px;height:auto; overflow:hidden; ">
		<div style="float:left;">
			考试名称:<input id="s_examName" class="easyui-textbox"
				data-options="prompt:'试卷名称...'" style="width: 200px; height: 24px;" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search"
				onclick="doSearch()">查询</a> &nbsp;&nbsp;
		</div>
		<div style="margin-bottom:5px; float:right;">
			<a href="#" onclick="add_exam()" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">添加</a> <a href="#"
				onclick="edit_exam()" class="easyui-linkbutton" iconCls="icon-edit"
				plain="true">编辑</a> <a href="#" onclick="del_exam(-1)"
				class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
			<a href="#" onclick="selectPaper()" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true">选择试卷</a> <a href="#"
				onclick="selectGrade()" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true">考试授权</a>
		</div>
	</div>

	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<div title="考试列表信息" data-options="iconCls:'icon icon-nav',fit:true">
			<table id="examList_table" style="width: 800px; height: 100%;"
				class="easyui-datagrid" toolbar="#tb"
				data-options="
			url:'${pageContext.request.contextPath}/exam/getExamListJson',
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
						<th data-options="field:'examId',checkbox:true"></th>
						<th data-options="field:'examName'" align="center" width="80">考试名称</th>
						<th data-options="field:'startDate_str',align:'center'"
							width="100">开始时间</th>
						<th data-options="field:'endDate_str',align:'center'" width="100">结束时间</th>
						<th data-options="field:'createTime_str',align:'center'"
							width="100">创建时间</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

	<!-- 添加试卷对话框 -->
	<div id="dlg_exam" class="easyui-dialog" fit="true" title="添加考试"
		style="width: 600px; height: 500px; padding: 10px;"
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
			<form id="examForm" method="post"
				action="${pageContext.request.contextPath}/exam/saveExam">
				<input type="hidden" name="examId" />
				<table cellpadding="5">
					<tr>
						<td>考试名称:</td>
						<td><input class="easyui-textbox" type="text" name="examName"
							id="examName"
							data-options="required:true,validType:['isNullOrEmpty','length[1,32]']"></input></td>
					</tr>
					<tr>
						<td>试卷分数:</td>
						<td><input class="easyui-numberbox" type="text"
							name="scoreSum"
							data-options="required:true,validType:['length[1,3]']"></input></td>
					</tr>
					<tr>
						<td>及格线:</td>
						<td><input class="easyui-numberbox" type="text"
							name="passScore"
							data-options="required:true,validType:['length[1,3]']"></input></td>
					</tr>
					<tr>
						<td>考试次数:</td>
						<td><input class="easyui-numberbox" type="text"
							name="examTimes"
							data-options="required:true,validType:['length[0,1]']"></input></td>
					</tr>
					<tr>
						<td>考试时长:</td>
						<td><input class="easyui-numberbox" type="text"
							name="examDate"
							data-options="required:true,validType:['length[1,3]']"></input></td>
					</tr>
					<tr>
						<td>开始时间:</td>
						<td><input class="easyui-datetimebox" type="text"
							id="s_startDate_str" name="startDate_str"
							data-options="editable:false,required:true"></input></td>
					</tr>
					<tr>
						<td>结束时间:</td>
						<td><input class="easyui-datetimebox" type="text"
							id="s_endDate_str" name="endDate_str"
							data-options="editable:false,required:true,validType:['checkDate']"></input></td>
					</tr>
					<tr>
						<td>考试描述:</td>
						<td><input class="easyui-textbox"
							style="width: 400px; height: 160px" type="text" name="examDesc"
							data-options="multiline:true,required:false,validType:['isNullOrEmpty','length[0,250]']"></input></td>
					</tr>
				</table>
			</form>
		</div>
	</div>



</body>
</html>