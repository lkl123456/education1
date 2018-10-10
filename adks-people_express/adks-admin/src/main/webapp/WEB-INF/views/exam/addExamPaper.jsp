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
<script type="text/javascript">
	setTimeout("dis_exam_paper()", 5);
	function dis_exam_paper() {
		var epaperId = $("#examPaperid").val();
		epaperId = epaperId.substr(0, epaperId.length - 1);
		var epids = "";
		epids = epaperId.split(",");
		if (epids != null) {
			$("input[type='checkbox']:first").attr("disabled", true);
			for (i = 0; i < epids.length; i++) {
				$('input[type="checkbox"][value="' + epids[i] + '"]').attr(
						"disabled", true);
			}
		}
	}
</script>
</head>
<body class="easyui-layout">
	<input type="hidden" value="${epid }" id="examPaperid" />
	<input type="hidden" value="${scoreSum }" id="scoreSum" />
	<!-- 工具栏 -->
	<div id="tb"
		style="padding: 15px 10px 10px 10px; height: auto; overflow: hidden;">
		<div style="float: left;">
			试卷名称:<input id="s_paperName" class="easyui-textbox"
				data-options="prompt:'试卷名称...'" style="width: 200px; height: 24px;" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search"
				onclick="doAddExamPaperSearch()">查询</a> &nbsp;&nbsp;
		</div>
		<div style="margin-bottom: 5px; float: right;">
			<a href="#" onclick="save_examPaper()" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">添加试卷</a> <a href="#"
				onclick="window.parent.close()" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">关闭</a>
		</div>
	</div>

	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<div title="试卷列表信息" data-options="iconCls:'icon icon-nav',fit:true">
			<table id="addExamPaper_table" style="width: 800px; height: 100%;"
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
			pageList:[5,10,15],
			rownumbers:true
			">
				<thead>
					<tr>
						<th data-options="field:'paperId',checkbox:true"></th>
						<th data-options="field:'paperName'" align="center" width="80">试卷名称</th>
						<th data-options="field:'score',align:'center'" width="100">试卷总分</th>
						<th data-options="field:'createTime_str',align:'center'"
							width="100">创建时间</th>
						<th data-options="field:'creatorName',align:'center'" width="100">创建人</th>
						<th data-options="field:'paperHtmlAdress'" align="center" width="80"
							hidden="true">地址</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
</html>