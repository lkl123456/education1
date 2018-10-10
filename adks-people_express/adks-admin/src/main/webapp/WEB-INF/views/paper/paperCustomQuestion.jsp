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
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/paper/js/paperQuestion.js"></script>
</head>
<body class="easyui-layout">	
	<!-- 课程分类机构树 -->
	<div data-options="region:'west',split:true,title:'题库分类列表'" style="width:260px;padding:10px;">
		<input type="hidden" id="cc_ztree_node_id" value="0" />
		<input type="hidden" id="cc_ztree_node_name" value="顶级分类" />
		<input type="hidden" id="cc_ztree_node_code" value="0-" />
		<div>
			<ul id="questionSortTreeDemo" class="ztree"></ul>
		</div>
	</div>
	<!-- 工具栏 -->
	<div id="tb" style="padding:15px 10px 10px 10px;height:auto; overflow:hidden; ">
		<div style="float:left;">
 		题目:<input id="s_questionName" class="easyui-textbox" style="width:200px;height:24px;" />
 		分值:<input id="s_questionValue_start" class="easyui-textbox" style="width:50px;height:24px;" />
		至<input id="s_questionValue_end" class="easyui-textbox" style="width:50px;height:24px;" />
		题型:<select class="easyui-combobox" id="s_questionType" style="width:100px;">
					<option value="">全部</option>
					<option value="1">单选</option>
					<option value="2">多选</option>
					<option value="3">判断</option>
					<option value="4">填空</option>
					<option value="5">问答</option>
			</select>
		课程名称:<input id="s_courseName" class="easyui-textbox" style="width:200px;height:24px;" />	
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
			&nbsp;&nbsp;
		</div>
		<div style="margin-bottom:5px; float:right;">
			<a href="#" onclick="saveCustomQuestion()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加试题</a>
<!-- 			<a href="#" onclick="saveCustomQuestion('all')" class="easyui-linkbutton" iconCls="icon-edit" plain="true">一键添加</a> -->
			<a href="#" onclick="window.parent.close()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">关闭</a>
		</div>
	</div>
	
	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<div title="试题列表信息" data-options="iconCls:'icon icon-nav',fit:true">
	<table id="paperCustomList_table" style="width:800px;height:100%;" class="easyui-datagrid"
			toolbar="#tb" data-options="
			url:'${pageContext.request.contextPath}/question/getQuestionListJson',
			method:'get',
			singleSelect:false,
			fit:true,
			fitColumns:true,
			striped:true,
			nowarp:false,
			pagination:true,
			pageSize:10,
			pageList:[5,10,15],
			rownumbers:true
			">
		<thead>
			<tr>
				<th data-options="field:'questionId',checkbox:true"></th>
				<th data-options="field:'questionName'" align="center" width="80">题目</th>
				<th data-options="field:'courseName',align:'center'" width="100">课程名称</th>
				<th data-options="field:'questionType',align:'center'" formatter="formatStatus" width="100">题型</th>
				<th data-options="field:'questionValue',align:'center'" width="100">分值</th>
			</tr>
		</thead>
	</table>
	</div>
	</div>

</body>
</html>