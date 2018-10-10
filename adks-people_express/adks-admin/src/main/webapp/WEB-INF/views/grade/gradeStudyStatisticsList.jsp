<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@include file="../public/header.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/grade/js/gradeStudy.js"></script>
<body class="easyui-layout">
	<!-- 班级列表树 -->
	<div data-options="region:'west',split:true,title:'班级列表'" style="width:260px;padding:10px;">
			<input type="hidden" id="ztree_gradeId" />
			<input type="hidden" id="ztree_gradeName" /> 
			<div>
				<ul id="gradeTree" class="ztree"></ul>
			</div>
	</div>
	<!-- 工具栏 -->
	<div id="tb" style="padding:15px 10px 10px 10px;height:auto; overflow:hidden; ">
		<div style="float:left;">
			<input id="userName" class="easyui-textbox" data-options="prompt:'用户名或真实姓名...'" style="width:150px;height:24px;">
			<input type="text" id="selOrgTree" onclick="showOrgTree1()">
			<ul id="treeDemo" class="ztree" style="display:none; position:absolute; left:162px; top:40px; z-index:9999; border:1px solid #ddd; background:#fff; min-width:200px;overflow-y:scroll;"></ul>
			<input type="hidden" id="selOrgTreeOrgCode">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
		</div>
		<div style="margin-bottom:5px; float:right;">
			<a href="javascript:updateGradeUserGraduate()" class="easyui-linkbutton" iconCls="icon-reload" plain="true">准许毕业</a>
			<a href="javascript:completeCourseUser()" class="easyui-linkbutton" iconCls="icon-reload" plain="true">批量完成学习</a>
			<a href="javascript:exportGradeStudyExcel()" class="easyui-linkbutton" iconCls="icon-print" plain="true">导出Excel</a>
		</div>
	</div>
	<!-- 列表 -->
	<div id="mainText" data-options="region:'center'">
			<table id="gradeStudyListTable" style="width:800px;height:350px;" class="easyui-datagrid"
					toolbar="#tb" data-options="
					url:'${pageContext.request.contextPath}/gradeStudy/getGradeStudyListJson',
					method:'post',
					singleSelect:false,
					collapsible:true,
					fit:true,
					fitColumns:true,
					pagination:true,
					pageSize:20,
					pageList:[20,25,30],
					rownumbers:true,
					rowStyler:setRowStyle
					">
				<thead>
					<tr>
						<th data-options="field:'gradeUserId',checkbox:true"></th>
						<th data-options="field:'userName',align:'center'" width="70">用户名</th>
						<th data-options="field:'userRealName',align:'center'" width="70">姓名</th>
						<th data-options="field:'gradeName',align:'center'" width="150">所在班级</th>
						<th data-options="field:'period',align:'center'" width="50">总学时</th>
						<th data-options="field:'requiredPeriod',align:'center'" width="50">必修学时</th>
						<th data-options="field:'optionalPeriod',align:'center'" width="50">选修学时</th>
						<th data-options="field:'examScore',align:'center'" width="50">考试得分</th>
						<th data-options="field:'workScore',align:'center'" width="50">作业得分</th>
						<th data-options="field:'isGraduate'" formatter="graduateStatus" align="center" width="50">是否毕业</th>
						<th data-options="field:'_'" formatter="formatOper" align="center" width="50">操作</th>
					</tr>
				</thead>
			</table>
	</div>
	<!-- 批量导入对话框 -->
	<div style="padding-top: 2px; padding-left: 20px; z-index: 0;">
		<form id="export_gradeStudyStatistics" method="post" action="${pageContext.request.contextPath}/gradeStudy/exportGradeStudyStatistics">
			<input id="exGradeId" name="exGradeId" type="hidden" value="">
			<input id="exOrgCode" name="exOrgCode" type="hidden" value="">
		</form>
	</div>
</body>
</html>