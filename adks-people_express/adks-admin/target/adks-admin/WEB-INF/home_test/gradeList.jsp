<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript">    	
	var contextpath = '${pageContext.request.contextPath}';
</script>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>培训管理</title>
    <link href="${pageContext.request.contextPath}/static/admin/css/default.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/icon.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/ui-cupertino/easyui.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/color.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/easyui-lang-zh_CN.js"></script>
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/grade/grade.js"></script>
    
</head>
<body class="easyui-layout">
<input type="hidden"  id="imgServer" value="${imgServer}" />	
	<!-- 工具栏 -->
	<div id="tb" style="padding:5px;height:auto; ">
		<div style="margin-bottom:5px">
			<a href="javascript: addGrade()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
			<a href="javascript: editGrade()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a>
			<a href="javascript: configerGrade()" class="easyui-linkbutton" iconCls="icon-tip" plain="true">配置</a>
			<a href="javascript: deleteGrade()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		</div>
		<div>
			<input id="gradeName" class="easyui-searchbox" data-options="prompt:'培训名称...',searcher:doSearch" style="width:200px;height:24px;">
		</div>
	</div>
	
	<div id="mainText" data-options="region:'center'">
			<table id="gradeListTable" style="width:800px;height:350px;" class="easyui-datagrid"
					toolbar="#tb" data-options="
					url:'/grade/getGradesJson',
					method:'get',
					singleSelect:false,
					collapsible:true,
					fit:true,
					fitColumns:true,
					pagination:true,
					pageSize:15,
					pageList:[15,20,30],
					rownumbers:true,
					rowStyler:setRowStyle
					">
				<thead>
					<tr>
						<th data-options="field:'id',checkbox:true"></th>
						<th data-options="field:'name',align:'center'" width="100">培训名称</th>
						<th data-options="field:'begin_time_str'" align="center" width="90">开始时间</th>
						<th data-options="field:'end_time_str'" align="center" width="90">结束时间</th>
						<th data-options="field:'status',align:'center'" formatter="formatStatus" width="80">状态</th>
						<th data-options="field:'create_time_str',align:'center'" width="90">创建时间</th>
						<th data-options="field:'creator',align:'center'" width="50">创建人</th>
					</tr>
				</thead>
			</table>
	</div>
	
	<!-- 添加培训班div -->
	<div id="dlg_grade" class="easyui-dialog" title="aaa" style="width:700px;height:600px;padding:10px;"
			data-options="
				iconCls: 'icon-save',
				buttons:'#dlg-buttons',
				modal:true,closed:true
			">
	    <form id="gradeForm" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/grade/saveGrade">
	    	<input type="hidden" name="id" value="" />
	    	<input type="hidden" id="status" name="status" value="" />
	    	<table cellpadding="5">
	    		<tr>
	    			<td style="width:70px;">培训名称:</td>
	    			<td><input class="easyui-textbox" type="text" name="name" style="width:240px;" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>开始时间:</td>
	    			<td><input class="easyui-datetimebox" type="text" name="begin_time_str" style="width:240px;" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>结束时间:</td>
	    			<td><input class="easyui-datetimebox" type="text" name="end_time_str" style="width:240px;" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>封　　面:</td>
	    			<td>
	    			<input type="hidden"  id="cover_path" name="cover_path" />
	    			
	    			<img height="100px;" width="100px;" id="img_coverpath" src="">
	    			<input class="easyui-filebox" name="gradeCoverImgfile" data-options="prompt:'选择一张封面图片...',onChange:function(){uploadGradeCover()}" style="width:80%">
	    			</input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>培训摘要:</td>
	    			<td><input class="easyui-textbox" type="text" name="summary" style="width:400px;height:60px" data-options="multiline:true,required:false"></input></td>
	    		</tr>
	    		<tr>
	    			<td>培训介绍:</td>
	    			<td><input class="easyui-textbox" type="text" name="introduction" style="width:400px;height:160px" data-options="multiline:true,required:false"></input></td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	<div id="dlg-buttons" align="center">
		<a href="#" class="easyui-linkbutton c3" iconCls="icon-ok" onclick="javascript:submitAddGrade('1');">直接发布</a>
		<a href="#" class="easyui-linkbutton c2" iconCls="icon-ok" onclick="javascript:submitAddGrade('0');">存草稿</a>
		<a href="#" class="easyui-linkbutton c1" iconCls="icon-ok" onclick="javascript:submitAddGrade('2');">下一步:配置课程</a>
		<a href="#" class="easyui-linkbutton c5" iconCls="icon-cancel" onclick="javascript:$('#dlg_grade').dialog('close')">取消</a>
	</div>
</body>
</html>