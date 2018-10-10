<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@include file="../public/header.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/grade/js/gradeWorkReply.js"></script>
<body class="easyui-layout">
	<!-- 工具栏 -->
	<div id="tb" style="padding:15px 10px 10px 10px;height:auto; overflow:hidden; ">
		<div style="float:left;">
			<input id="studentName" class="easyui-searchbox"
				data-options="prompt:'学员姓名...',searcher:doSearch"
				style="width: 200px; height: 24px;">
		</div>
		<div style="margin-bottom:5px; float:right;">
			<a href="javascript: editGradeWorkRreply()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">批改</a> 
			<a href="javascript: deleteGradeWorkRreply()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		</div>
	</div>
	<div id="mainText" data-options="region:'center'">
		<table id="gradeWorkReplyListTable" style="width: 800px; height: 350px;"
			class="easyui-datagrid" toolbar="#tb"
			data-options="
					url:'${pageContext.request.contextPath}/gradeWork/getGradeWorkRreplyListJson?gradeWorkId=${gradeWorkId }',
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
					<th data-options="field:'gradeWorkReplyId',checkbox:true"></th>
					<th data-options="field:'studentName',align:'center'" width="100">学员</th>
					<th data-options="field:'workTitle'" align="center" width="70">作业标题</th>
					<th data-options="field:'correntName'" align="center" width="100">批改人</th>
					<th data-options="field:'submitDate'" formatter="formatDateConversion" align="center" width="70">提交时间</th>
					<th data-options="field:'correctDate'" formatter="formatDateConversion" align="center" width="70">批改时间</th>
					<th data-options="field:'isCorrent',align:'center'" formatter="formatStatus" width="50">状态</th>
				</tr>
			</thead>
		</table>
	</div>
	
	<!-- 添加作业div -->
	<div id="dlg_workReply" class="easyui-dialog" title="作业详情" style="width:700px;height:600px;padding:10px;"
			data-options="
				iconCls: 'icon-save',
				modal:true,closed:true,
				buttons: [{
					text:'批改',
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
	    <form id="gradeWorkReplyFrom" method="post" action="${pageContext.request.contextPath}/gradeWork/saveGradeWorkReply" >
	    	<input type="hidden" id="gradeWorkReplyId" name="gradeWorkReplyId"/>
	    	<table cellpadding="5">
	    		<tr>
	    			<td>学员:</td>
	    			<td><input class="easyui-textbox" type="text" disabled="disabled" name="studentName" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>分数:</td>
	    			<td><input class="easyui-numberbox" type="text" name="workScore" style="width:100px;height:30px" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>作业附件:</td>
	    			<td><a href="<%=ossResource %>${submitFilePath }" id="submitFilePath" style="width:20%" >下载附件</a></td>	 
	    		</tr>
	    		<tr>
	    			<td>回答内容:</td>
	    			<td><textarea type="text" readonly="readonly" id="workAnswer" maxlength="250" style="width:400px;height:160px"></textarea></td>
	    		</tr>
	    	</table>
	    </form>
	  </div>
</body>
</html>