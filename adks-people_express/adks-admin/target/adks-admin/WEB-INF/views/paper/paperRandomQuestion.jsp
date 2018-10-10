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
	src="${pageContext.request.contextPath}/static/paper/js/paperQuestion.js"></script>
</head>
<body class="easyui-layout">
	<!-- 课程分类机构树 -->
	<div data-options="region:'west',split:true,title:'题库分类列表'"
		style="width: 260px; padding: 10px;">
		<input type="hidden" id="cc_ztree_node_id" value="0" /> <input
			type="hidden" id="cc_ztree_node_name" value="顶级分类" /> <input
			type="hidden" id="cc_ztree_node_code" value="0-" />
		<div>
			<ul id="questionSortTreeDemo" class="ztree"></ul>
		</div>
	</div>
	<!-- 工具栏 -->
	<div id="tb" style="padding: 5px; height: auto;">
		<div style="margin-bottom: 5px">
			<a href="#" onclick="saveRandomQuestion();" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">点击生成试卷</a>
		</div>
	</div>
	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<div title="试题列表信息" data-options="iconCls:'icon icon-nav',fit:true">
			<table id="questionList_table" style="width: 800px; height: 100%;"
				class="easyui-datagrid" toolbar="#tb"
				data-options="
			url:'${pageContext.request.contextPath}/question/getRandomQuestionPaper',
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
						<th data-options="field:'questionType'" formatter="formatStatus"
							align="center" width="80">题型名称</th>
						<th data-options="field:'questionTotal',align:'center'"
							width="100">已有试题数量</th>
						<th data-options="field:'inputNum',align:'center'"
							formatter="formatInputNum" width="100">出题数量</th>
						<th
							data-options="field:'inputScore',align:'center',validType:['length[1,3]']"
							formatter="formatInputScore" width="100">每小题分数</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

</body>
</html>