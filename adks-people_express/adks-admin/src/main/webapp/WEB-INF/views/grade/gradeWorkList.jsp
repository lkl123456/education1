<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@include file="../public/header.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/grade/js/gradeWork.js"></script>
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
			<input id="workTitle" class="easyui-searchbox" data-options="prompt:'作业名称...',searcher:doSearch" style="width:200px;height:24px;">
		</div>
		<div style="margin-bottom:5px; float:right;">
			<a href="javascript: addGradeWork()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
			<a href="javascript: editGradeWork()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a>
			<a href="javascript: deleteGradeWork()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
			<a href="javascript: selGradeWork()" class="easyui-linkbutton" iconCls="icon-tip" plain="true">查看班级作业</a>
		</div>
	</div>
	<!-- 列表 -->
	<div id="mainText" data-options="region:'center'">
			<table id="gradeWorkListTable" style="width:800px;height:350px;" class="easyui-datagrid"
					toolbar="#tb" data-options="
					url:'${pageContext.request.contextPath}/gradeWork/getGradeWorkListJson',
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
						<th data-options="field:'gradeWorkId',checkbox:true"></th>
						<th data-options="field:'workTitle',align:'center'" width="100">标题</th>
						<th data-options="field:'gradeName'" align="center" width="100">所属班级</th>
						<th data-options="field:'startDate'" align="center" formatter="formatDateConversion" width="70">开始时间</th>
						<th data-options="field:'endDate'" align="center" formatter="formatDateConversion" width="70">结束时间</th>
						<th data-options="field:'releaseState',align:'center'" formatter="formatStatus" width="50">状态</th>
						<th data-options="field:'createTime',align:'center'" formatter="formatDateConversion" width="70">创建时间</th>
					</tr>
				</thead>
			</table>
	</div>
	
	<!-- 添加作业div -->
	<div id="dlg_work" class="easyui-dialog" title="aaa" style="width:700px;height:600px;padding:10px;"
			data-options="
				iconCls: 'icon-save',
				modal:true,closed:true,
				buttons: [{
					text:'保存',
					iconCls:'icon-ok',
					handler:function(){
						submitAdd();
					}
				},{
					text:'取消',
					iconCls:'icon-cancel',
					handler:function(){
						cancleAdd();
					}
				}]
			">
	    <form id="gradeWorkFrom" method="post" action="${pageContext.request.contextPath}/gradeWork/saveGradeWork" enctype="multipart/form-data">
	    	<input type="hidden" id="gradeId" name="gradeId"/>
	    	<input type="hidden" id="gradeName" name="gradeName"/>
	    	<input type="hidden" id="gradeWorkId" name="gradeWorkId" />
	    	<input type="hidden" id="filePath" name="filePath" />
	    	<table cellpadding="5">
	    		<tr>
	    			<td>标题:</td>
	    			<td><input class="easyui-textbox" type="text" name="workTitle" data-options="required:true,validType:['isNullOrEmpty','length[1,32]']"></input></td>
	    		</tr>
	    		<tr>
	    			<td>作业状态:</td>
	    			<td><select id="state" class="easyui-combobox" name="releaseState" style="width:100px;" data-options="required:true,editable:false">   
						    <option value="1" >发布</option>   
						    <option value="2">未发布</option> 
						</select>  
	    		</tr>
	    		<tr>
	    			<td>是否允许上传附件:</td>
	    			<td><select id="state" class="easyui-combobox" name="allowFile" style="width:100px;" data-options="required:true,editable:false">   
						    <option value="1">允许</option>   
						    <option value="2">不允许</option> 
						</select>  
	    		</tr>
	    		<tr>
	    			<td>最多字数:</td>
	    			<td><input class="easyui-numberbox" type="text" name="maxSize" style="width:100px;height:30px" data-options="required:true,validType:['length[1,4]']"></input></td>
	    		</tr>
	    		<tr>
	    			<td>最少字数:</td>
	    			<td><input class="easyui-numberbox" type="text" name="leastSize" style="width:100px;height:30px" data-options="required:true,validType:['length[1,4]']"></input></td>
	    		</tr>
	    		<tr>
	    			<td>开始时间:</td>
	    			<td><input class="easyui-datetimebox" type="text" id="s_startDate_str" name="startDate_str" style="width:240px;" data-options="editable:false,required:true,validType:['isNullOrEmpty']"></input></td>
	    		</tr>
	    		<tr>
	    			<td>结束时间:</td>
	    			<td><input class="easyui-datetimebox" type="text" id="s_endDate_str" name="endDate_str" style="width:240px;" data-options="editable:false,required:true,validType:['isNullOrEmpty','checkDate']"></input></td>
	    		</tr>
	    		<tr>
	    			<td>作业附件:</td>
	    			<td>
	    			<input class="easyui-filebox" id="filePathFile" name="filePathFile" data-options="prompt:'选择附件...'" style="width:80%">
	    			</input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>作业内容:</td>
	    			<td><input class="easyui-textbox" type="text" name="workContent" maxlength="250" style="width:400px;height:160px" data-options="multiline:true,required:false,validType:['length[0,250]']"></input></td>
	    		</tr>
	    	</table>
	    </form>
	  </div>
</body>
</html>