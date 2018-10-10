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
<script type="text/javascript">
	setTimeout("dis_exam_grade()", 5);
	function dis_exam_grade() {
		var egradeId = $("#examGradeid").val();
		egradeId = egradeId.substr(0, egradeId.length - 1);
		var egids = "";
		egids = egradeId.split(",");
		if (egids != null) {
			$("input[type='checkbox']:first").attr("disabled", true);
			for (i = 0; i < egids.length; i++) {
				$('input[type="checkbox"][value="' + egids[i] + '"]').attr(
						"disabled", true);
			}
		}
	}
</script>
</head>
<body class="easyui-layout">
	<input type="hidden" value="${examGradeid }" id="examGradeid" />
	<input type="hidden" value="${examId }" id="examId" />
	<!-- 工具栏 -->
	<div id="tb" style="padding:15px 10px 10px 10px;height:auto; overflow:hidden; ">
		<div style="float:left;">
			班级名称:<input id="s_paperName" class="easyui-textbox"
				data-options="prompt:'试卷名称...'" style="width: 200px; height: 24px;" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search"
				onclick="doAddExamPaperSearch()">查询</a> &nbsp;&nbsp;
		</div>
		<div style="margin-bottom:5px; float:right;">
			<a href="#" onclick="save_examGrade()" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">授权班级</a> <a href="#"
				onclick="window.parent.close()" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">关闭</a>
		</div>
	</div>

	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<div title="班级列表信息" data-options="iconCls:'icon icon-nav',fit:true">
			<table id="addExamGrade_table" style="width: 800px; height: 100%;"
				class="easyui-datagrid" toolbar="#tb"
				data-options="
			url:'${pageContext.request.contextPath}/grade/getGradeListJson',
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
						<th data-options="field:'gradeId',checkbox:true"></th>
						<th data-options="field:'gradeName'" align="center" width="80">班级名称</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
</html>