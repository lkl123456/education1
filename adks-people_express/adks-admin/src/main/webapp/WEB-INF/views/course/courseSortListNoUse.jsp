<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>

<head>
	<title>课程管理</title>
	<%@include file="../public/header.jsp" %>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/zTree/jquery.ztree.all-3.5.min.js"></script>
    <link href="${pageContext.request.contextPath}/static/admin/zTree/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/course/course.js"></script>
</head>

<body class="easyui-layout">
<input type="hidden"  id="imgServer" value="${imgServer}" />
	<div data-options="region:'west',split:true,title:'机构列表'" style="width:260px;padding:10px;">
		<div>
			<ul id="treeDemo" class="ztree"></ul>
		</div>

	</div>
	
	<!-- 工具栏 -->
	<div id="tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="#" onclick="add_course();" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
			<a href="#" onclick="edit_course()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a>
			<a href="#" onclick="del_course(-1)" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		</div>
		
		<div>
			<c:if test="${!empty isPlatform}">
			 <input id="s_course_orgSnname" class="easyui-combobox" data-options="url:'/org/orgListSelJson',method:'get',valueField:'uname_sn',textField:'name',prompt:'选择网校...',onChange:function(){doSearch()}" style="width:200px;" />&nbsp;
			</c:if>
			<input type="hidden" id="isPlatform" value="${isPlatform }" />
			<input id="s_course_name" class="easyui-textbox" data-options="prompt:'课程名...'" style="width:200px;height:24px;" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
			&nbsp;&nbsp;<a href="#" onclick="del_course(2);" class="easyui-linkbutton" iconCls="icon-lock" >下架</a>
			&nbsp;<a href="#" onclick="del_course(1);" class="easyui-linkbutton" iconCls="icon-ok" >发布</a>
		</div>
	</div>
	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<div title="课程列表数据" data-options="iconCls:'icon icon-nav',fit:true">
			<table id="courselistdatagrid" style="width:800px;height:350px" class="easyui-datagrid" 
					toolbar="#tb" data-options="
					url:'/course/courseList',
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
						<th data-options="field:'courseName'" align="center" width="100">课程名称</th>
						<th data-options="field:'courseSortName'" align="center" width="100">所属分类</th>
						<th data-options="field:'org_name',align:'center'" width="100">课程来源</th>
						<th data-options="field:'courseTimeLong',align:'center'" width="80">播放时长</th>
						<th data-options="field:'createtime',align:'center',formatter:function(value,row,index){if(value != null){var unixTimestamp = new Date(value); return unixTimestamp.Format('yyyy-MM-dd');}}" width="80">更新时间</th>
						<th data-options="field:'courseStatus',align:'center',formatter:function(value,row,index){if(value=='1'){return '激活'}else if(value=='2'){return '冻结'}}" width="50">发布状态</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>


	<!-- 添加、编辑课程分类窗口 -->
	<div id="dlg" class="easyui-dialog" title="添加课程分类" style="width:600px;height:300px;padding:10px;"
			data-options="
				iconCls: 'icon-save',
				modal:true,closed:true,
				buttons: [{
					text:'保存',
					iconCls:'icon-ok',
					handler:function(){
						submitAdd_category();
					}
				},{
					text:'取消',
					iconCls:'icon-cancel',
					handler:function(){
						cancleAdd_category();
					}
				}]
			">
		<div style="padding:10px 120px 20px 120px">
	    <form id="categoryForm" method="post" action="${pageContext.request.contextPath}/courseCategory/saveCourseCategory">
	    	<table cellpadding="5">
	    		<tr>
	    			<td>分类名称:</td>
	    			<td><input class="easyui-textbox" type="text" name="name" data-options="required:true" style="width: 160px;"></input></td>
	    		</tr>
	    		<tr>
	    			<td>上级分类:</td>
	    			<td>
	    			<input id="cc" name="parent_id" class="easyui-combotree" data-options="url:'/courseCategory/getCourseCategoryTreeJson',method:'get',required:true" style="width:160px;">
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>排序:</td>
	    			<td><input name="order_num" class="easyui-textbox" type="text" data-options="required:false,validType:'number'" style="width: 160px;"></input></td>
	    		</tr>
	    		<tr>
	    			<td>是否显示:</td>
	    			<td>
		    			<input type="radio" id="t_category_status" name="status" value="1"/>是
						<input type="radio" name="status" value="2"/>否
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	    </div>
	</div>
	
	<!-- 添加课程信息div  -->
	<div id="dlg_course" class="easyui-dialog" title="添加课程" style="width:620px;height:580px;padding:10px;"
			data-options="
				iconCls: 'icon-save',
				modal:true,closed:true,
				buttons: [{
					text:'发布',
					iconCls:'icon-ok',
					handler:function(){
						submitAdd_course(1);
					}
				},{
					text:'存草稿',
					iconCls:'icon-ok',
					handler:function(){
						submitAdd_course(0);
					}
				},{
					text:'取消',
					iconCls:'icon-cancel',
					handler:function(){
						cancleAdd_course();
					}
				}]
			">
		
	</div>
	
</body>
</html>