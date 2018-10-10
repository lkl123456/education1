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
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/exam/js/examPaper.js"></script>
</head>
<body class="easyui-layout">
	<!-- 工具栏 -->
	<div id="tb" style="padding:15px 10px 10px 10px;height:auto; overflow:hidden; ">
		<div style="float:left;">
			<input type="hidden" name="examId" id="examId" value="${examId}" />
			试卷名称:<input id="s_paperName" class="easyui-textbox"
				data-options="prompt:'试卷名称...'" style="width: 200px; height: 24px;" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search"
				onclick="doExamPaperSearch()">查询</a> &nbsp;&nbsp;
		</div>
		<div style="margin-bottom:5px; float:right;">
			<a href="#" onclick="add_examPaper()" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">添加</a> <a href="#"
				onclick="view_paper()" class="easyui-linkbutton" iconCls="icon-edit"
				plain="true">预览</a> <a href="#" onclick="del_examPaper()"
				class="easyui-linkbutton" iconCls="icon-remove" plain="true">移除</a>
		</div>
	</div>
	<input type="hidden" value="" id="epid" />
	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<div title="试卷列表信息" data-options="iconCls:'icon icon-nav',fit:true">
			<table id="examPaperList_table" style="width: 800px; height: 100%;"
				class="easyui-datagrid" toolbar="#tb"
				data-options="
			url:'${pageContext.request.contextPath}/examPaper/getExamPaperListJson',
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
						<th data-options="field:'id',checkbox:true"></th>
						<th data-options="field:'paperName'" align="center" width="80">试卷名称</th>
						<th data-options="field:'score',align:'center'"
							formatter="formatScore" width="100">试卷总分/总题数</th>
						<th data-options="field:'danxuanScore',align:'center'"
							formatter="formatDanXuan" width="100">单选题总分/总题数</th>
						<th data-options="field:'duoxuanScore',align:'center'"
							formatter="formatDuoXuan" width="100">多选题总分/总题数</th>
						<th data-options="field:'panduanScore',align:'center'"
							formatter="formatPanDuan" width="100">判断题总分/总题数</th>
						<th data-options="field:'tiankongScore',align:'center'"
							formatter="formatTianKong" width="100">填空题总分/总题数</th>
						<th data-options="field:'wendaScore',align:'center'"
							formatter="formatWenDa" width="100">问答题总分/总题数</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
</html>