<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>配置试卷</title>
<%@include file="../public/header.jsp"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/paper/js/paperQuestion.js"></script>
</head>
<body class="easyui-layout">
	<div id="tb"
		style="padding: 15px 10px 10px 10px; height: auto; overflow: hidden;">
		<div style="margin-bottom: 5px; float: right;">
			<a href="#" onclick="save_paperQuestion()" class="easyui-linkbutton"
				iconCls="icon-save" plain="true">保存</a><font color="red" id="tishi">${not empty paper.paperHtmlAdress?"已生成试卷":"*保存成功后,才会生成试卷" }</font>
			<a href="#" onclick="del_paperQuestion()" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true">删除试题</a>
		</div>
		<div class="tagTable" style="float: left;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>&nbsp;试卷总分：<input type="text" formatter="formatStatus"
						type="easyui-textbox" name="score" value="${paper.score }"
						readonly="readonly" size="5">&nbsp;&nbsp; 单选总分：<input
						type="easyui-textbox" formatter="formatStatus" name="danxuanScore"
						value="${paper.danxuanScore }" readonly="readonly" size="5">&nbsp;&nbsp;
						多选总分：<input type="easyui-textbox" formatter="formatStatus"
						name="duoxuanScore" value="${paper.duoxuanScore }"
						readonly="readonly" size="5">&nbsp;&nbsp; 判断总分：<input
						type="easyui-textbox" name="panduanScore"
						value="${paper.panduanScore }" readonly="readonly" size="5">&nbsp;&nbsp;
						填空总分：<input type="easyui-textbox" name="tiankongScore"
						value="${paper.tiankongScore }" readonly="readonly" size="5">&nbsp;&nbsp;
						问答总分：<input type="easyui-textbox" name="wendaScore"
						value="${paper.wendaScore }" readonly="readonly" size="5">&nbsp;&nbsp;
						<input type="hidden" name="paperScore" value="10000" /> <a
						href="#" onclick='getQuestions("random")'
						class="easyui-linkbutton" iconCls="icon-edit" plain="true">随机加题</a>
						<a href="#" onclick='getQuestions("custom")'
						class="easyui-linkbutton" iconCls="icon-edit" plain="true">手工加题</a></td>
				</tr>
			</table>
		</div>

		<input type="hidden" name="paperId" id="paperId"
			value="${paper.paperId }" />
	</div>

	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<div title=" 添加试卷-第二步-组卷出题"
			data-options="iconCls:'icon icon-nav',fit:true">
			<table id="questionConfigureList_table"
				style="width: 800px; height: 100%;" class="easyui-datagrid"
				toolbar="#tb"<%-- data-options="
			url:'${pageContext.request.contextPath}/paperQuestion/getPaperQuestionListJson?paperId=${paper.paperId }',
			method:'post',
			singleSelect:false,
			fit:true,
			fitColumns:true,
			striped:true,
			nowarp:false,
			pagination:true,
			pageSize:10,
			pageList:[5,10,15],
			rownumbers:true
			" --%>>
				<!-- <thead>
					<tr>
						<th data-options="field:'paperQsId',checkbox:true"></th>
						<th data-options="field:'questionName',align:'center'" width="200">题目</th>
						<th data-options="field:'courseName',align:'center'" width="80">课程名称</th>
						<th data-options="field:'questionType',align:'center'"
							formatter="formatStatus" width="80">题型</th>
						<th data-options="field:'score',align:'center',editor:'numberbox'"
							formatter="" width="80">分值</th>
					</tr>
				</thead> -->
			</table>
		</div>
	</div>

</body>
</html>