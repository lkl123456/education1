<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript">    	
	var contextpath = '${pageContext.request.contextPath}';
</script>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>配置培训课程</title>
    <link href="${pageContext.request.contextPath}/static/admin/css/default.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/icon.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/ui-cupertino/easyui.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/color.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/grade/gradeCourse.js"></script>
    
</head>
<body class="easyui-layout">

	<div data-options="region:'north',split:true,border:false" style="height:70px;background:#FDF5E6;padding:2px">
		
       <span style="padding-left:400px; font-size: 20px;font-weight: bold; ">当前培训项目：${grade.name }</span>
		<div style="margin-bottom:0;margin-top: 2px;">
			<a href="javascript: " class="easyui-linkbutton c2" iconCls="icon-tip" plain="true">配置课程</a>
			<a href="javascript: configureGradeUser(${grade.id })" class="easyui-linkbutton c4" iconCls="icon-tip" plain="true">配置学员</a>
			<a href="javascript: backToGradeList()" class="easyui-linkbutton c5" iconCls="icon-undo" plain="true">返回列表</a>
		</div>
	</div>
	
	<!-- 工具栏 -->
	<div id="tb" style="padding:5px;height:auto; ">
		<div style="margin-bottom:5px">
			<span class="easyui-linkbutton c6">班级已选课程：</span>
		</div>
		<div>
			<input id="courseNameInGrade" class="easyui-textbox" data-options="prompt:'课程名称...'" style="width:200px;height:24px;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearchCourseName()">查询</a>
			<a href="javascript: removeGradeCourse(${grade.id })" iconCls="icon-remove" class="easyui-linkbutton c5">&nbsp;&nbsp;移除&nbsp;&nbsp;</a>
		</div>
	</div>
	<div id="tb2" style="padding:5px;height:auto; ">
		<div style="margin-bottom:5px">
			<span class="easyui-linkbutton c6">班级可选课程资源：</span>
		</div>
		<div>
			<!-- 
			<input id="courseName" class="easyui-searchbox" data-options="prompt:'课程名称...',searcher:doSearch" style="width:200px;height:24px;">
			<select id="cc" class="easyui-combotree" data-options="url:'tree_data1.json',method:'get'" multiple style="width:200px;"></select>
			<span>课程名称:</span>
			 -->
			<input id="courseName" class="easyui-textbox" data-options="prompt:'课程名...'" style="width:200px;height:24px;">
			<select id="courseCataTree" class="easyui-combotree" data-options="url:'/courseCategory/getCourseCategoryTreeJson',method:'get',prompt:'选择分类...'" multiple style="width:260px;"></select>
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
			<a href="javascript: addGradeCourse(${grade.id })" iconCls="icon-add" class="easyui-linkbutton c6">&nbsp;&nbsp;添加&nbsp;&nbsp;</a>
		</div>
	</div>
	
	<div id="mainText" data-options="region:'center',border:false">
			
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',split:true,collapsed:false,border:false" style="height:50%">
				<table id="gradeCoursed" style="width:400px;height:350px;" class="easyui-datagrid"
					toolbar="#tb" data-options="
					url:'/gradeCourse/gradeCourseList?gradeId=${grade.id }',
					method:'get',
					singleSelect:false,
					collapsible:true,
					fit:true,
					fitColumns:true,
					pagination:true,
					pageSize:5,
					pageList:[5,10,20,30],
					rownumbers:true,
					">
				<thead>
					<tr>
						<th data-options="field:'id',checkbox:true"></th>
						<th data-options="field:'course_name',align:'center'" width="200">课程名称</th>
						<th data-options="field:'course_duration',align:'center'" width="80">播放时长</th>
					</tr>
				</thead>
			</table>
			</div>
			<div data-options="region:'center',border:false" style="height:50%">
				<table id="gradeCourseing" style="width:400px;height:350px;" class="easyui-datagrid"
					toolbar="#tb2" data-options="
					url:'/gradeCourse/gradeSelCourseList?gradeId=${grade.id }',
					method:'get',
					singleSelect:false,
					collapsible:true,
					fit:true,
					fitColumns:true,
					pagination:true,
					pageSize:5,
					pageList:[5,10,20,30],
					rownumbers:true,
					">
				<thead>
					<tr>
						<th data-options="field:'id',checkbox:true"></th>
						<th data-options="field:'name',align:'center'" width="300">课程名称</th>
						<th data-options="field:'category_name',align:'center'" width="100">所属分类</th>
						<th data-options="field:'duration',align:'center'" width="80">播放时长</th>
						<th data-options="field:'update_time',align:'center',formatter:function(value,row,index){if(value != null){var unixTimestamp = new Date(value); return unixTimestamp.Format('yyyy-MM-dd');}}" width="80">更新时间</th>
						<th data-options="field:'status',align:'center'" formatter="formatCourseState" width="50">状态</th>
					</tr>
				</thead>
			</table>
			</div>
		</div>
	</div>
	
</body>
</html>