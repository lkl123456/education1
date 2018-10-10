<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<script type="text/javascript">    	
	var contextpath = '${pageContext.request.contextPath}';
</script>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>新闻管理</title>
	<%@include file="../public/header.jsp" %>   	
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/news/files.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'west',split:true,title:'机构列表'" style="width:260px;padding:10px;">
		<input type="hidden" id="ztree_orgId" />
		<div>
			<ul id="orgTreeDemo" class="ztree"></ul>
		</div>
	</div>

	<!-- 工具栏 -->
	<div id="tb" style="padding:15px 10px 10px 10px;height:auto; overflow:hidden; ">
		<div style="float:left;">
			<c:if test="${!empty isPlatform}">
			 <input id="s_files_orgSnname" class="easyui-combobox" data-options="url:'${pageContext.request.contextPath}/org/orgListSelJson',method:'get',valueField:'uname_sn',textField:'name',prompt:'选择网校...',onChange:function(){doSearch()}" style="width:200px;" />&nbsp;
			</c:if>
			<input type="hidden" id="isPlatform" value="${isPlatform }" />
			<input id="s_files_name" class="easyui-textbox" data-options="prompt:'教参名...'" style="width:200px;height:24px;" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
		</div>
		<div style="margin-bottom:5px; float:right;">
			<a href="#" onclick="add_files();" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
			<a href="#" onclick="edit_files()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a>
			<a href="#" onclick="del_files(-1)" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		</div>
	</div>
	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<div title="教参列表数据" data-options="iconCls:'icon icon-nav',fit:true">
			<table id="fileslistdatagrid" style="width:800px;height:350px" class="easyui-datagrid" 
					toolbar="#tb" data-options="
					url:'${pageContext.request.contextPath}/files/getFilesListJson',
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
						<th data-options="field:'filesId',checkbox:true"></th>
						<th data-options="field:'filesName'" align="center" width="100">教参名称</th>
						<th data-options="field:'filesType',align:'center',formatter:function(value,row,index){if(value=='1'){return 'word'}else if(value=='2'){return 'pdf'}else if(value=='3'){return 'excel'}else if(value=='4'){return 'ppt'}else if(value=='5'){return 'zip'}else if(value=='6'){return '视频'}}" width="100">教参类型</th>
						<th data-options="field:'gradeName',align:'center'" width="80">班级名称</th>
						<th data-options="field:'orgName',align:'center'" width="80">学校名称</th>
						<th data-options="field:'filesBelong',align:'center',formatter:function(value,row,index){if(value=='1'){return '公开'}else if(value=='2'){return '专属'}else if(value=='3'){return '秘密'}}" width="80">是否公开</th>
						<th data-options="field:'createTimeStr',align:'center'" width="80">创建时间</th>
						<th data-options="field:'downloadNum',align:'center'" width="80">下载量</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	
	<!-- 添加课程信息div  -->
	<div id="dlg_files" class="easyui-dialog" title="添加教参" style="width:620px;height:480px;padding:10px;"
			data-options="
				iconCls: 'icon-save',
				modal:true,closed:true,
				buttons: [{
					text:'发布',
					iconCls:'icon-ok',
					handler:function(){
						submitAdd_files();
					}
				},{
					text:'取消',
					iconCls:'icon-cancel',
					handler:function(){
						cancleAdd_files();
					}
				}]
			">
		<div style="padding-left: 20px; padding-top: 2px;width:100%;height:100%;" class="easyui-panel" title="教参">
	    <form id="filesForm" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/files/saveFiles">
	    	<input type="hidden" id="t_id" name="filesId" >
	    	<input type="hidden" id="filesUrl" name="filesUrl" >
	    	<table cellpadding="5">
	    		<tr>
	    			<td>教参名称:</td>
	    			<td><input class="easyui-textbox" type="text" id="" name="filesName" data-options="required:true,validType:['isNullOrEmpty','length[2,20]']" style="width: 238px;"></td>
	    		</tr>
	    		<tr>
	    			<td>教参类型:</td>
	    			<td>
	    				<!--<input class="easyui-textbox" type="text" id="" name="filesType" data-options="required:true" >-->
						<select class="easyui-combobox" data-options="editable:false,panelHeight:'auto'" style="width: 150px" name="filesType">
							<option value="1" selected >word</option>
							<option value="2">pdf</option>
							<option value="3">excel</option>
							<option value="4">ppt</option>
							<option value="5">zip</option>
							<option value="6">视频</option>
						</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>附件:</td>
	    			<td>
	    				<input class="easyui-filebox" id="filesUrlFile" name="filesUrlFile" data-options="accept:'application/vnd.ms-excel,audio/mp4,video/mp4,application/msword,application/pdf,aplication/zip',prompt:'选择文件...'" style="width:80%"/>
					</td>
	    		</tr>
	    		<tr>
	    			<td>是否公开:</td>
	    			<td>
	    				<input  type="radio" name="filesBelong" value="1" data-options="required:true" /><span>公开</span>
						<input  type="radio" name="filesBelong" value="2" data-options="required:true" /><span>专属</span>
						<input  type="radio" name="filesBelong" value="3" data-options="required:true" /><span>秘密</span>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>所属班级:</td>
	    			<td>
	    			<select id="gradeId"  name="gradeId" data-options="required:true" style="width:200px;"></select>
	    			</td>
	    		</tr>
				<tr>
	    			<td>教参简介:</td>
	    			<td><input class="easyui-textbox" name="filesDes" data-options="multiline:true" style="height:100px;width:200px"></input></td>
	    		</tr>
	    	</table>
	    </form>
	    </div>
	</div>
	
</body>
</html>