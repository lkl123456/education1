<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro"  uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@include file="../public/header.jsp"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/grade/js/grade.js"></script>
<body class="easyui-layout">
	<!-- 工具栏 -->
	<div id="tb" style="padding:15px 10px 10px 10px;height:auto; overflow:hidden; ">
		<div style="float:left;">
			<input id="gradeName" class="easyui-searchbox"
				data-options="prompt:'班级名称...',searcher:doSearch"
				style="width: 200px; height: 24px;">
		</div>
		<div style="margin-bottom:5px; float:right;">
			<shiro:hasAnyRoles name="超级管理员,单位管理员,培训管理员"><a href="javascript: addGrade()" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">添加</a> 
				
				<a
				href="javascript: editGrade()" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true">编辑</a></shiro:hasAnyRoles> <a
				href="javascript: configerGrade()" class="easyui-linkbutton"
				iconCls="icon-tip" plain="true">配置</a><shiro:hasAnyRoles name="超级管理员,单位管理员,培训管理员"> <a
				href="javascript: deleteGrade()" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true">删除</a></shiro:hasAnyRoles>
				
		</div>
	</div>
	<div id="mainText" data-options="region:'center'">
		<table id="gradeListTable" style="width: 800px; height: 350px;"
			class="easyui-datagrid" toolbar="#tb"
			data-options="
					url:'${pageContext.request.contextPath}/grade/getGradeListJson',
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
					<th data-options="field:'gradeId',checkbox:true"></th>
					<th data-options="field:'gradeName',align:'center'" width="100">班级名称</th>
					<th data-options="field:'orgName'" align="center" width="100">所属机构</th>
					<th data-options="field:'startDate_str'" align="center" width="70">开始时间</th>
					<th data-options="field:'endDate_str'" align="center" width="70">结束时间</th>
					<th data-options="field:'gradeState',align:'center'" formatter="formatStatus" width="50">状态</th>
					<th data-options="field:'createTime_str',align:'center'" width="70">创建时间</th>
				</tr>
			</thead>
		</table>
	</div>
	<input type="hidden" id="imgServer" value="${ossResource}" />
	<!-- 添加培训班div -->
	<div id="dlg_grade" class="easyui-dialog" title="aaa"
		style="width: 700px; height: 600px; padding: 10px;"
		data-options="
				iconCls: 'icon-save',
				buttons:'#dlg-buttons',
				modal:true,closed:true
			">
		<form id="gradeForm" method="post" action="${pageContext.request.contextPath}/grade/saveGrade" enctype="multipart/form-data">
			<input type="hidden" id="gradeId" name="gradeId" value="" /> 
			<input type="hidden" id="status" value="" />
			<table cellpadding="5">
				<tr>
					<td style="width: 70px;">班级名称:</td>
					<td><input class="easyui-textbox" type="text" name="gradeName" id="gradeName" style="width: 240px;" 
					data-options="required:true,validType:['isNullOrEmpty','length[1,32]']" ></input></td>
				</tr>
				<tr>
					<td>班级状态:</td>
					<td><select id="state" class="easyui-combobox" name="gradeState" style="width: 100px;" data-options="required:true,editable:false">
							<option value="1">发布</option>
							<option value="2">未发布</option>
					</select>
				</tr>
				<tr>
					<td>作业:</td>
					<td><select id="state" class="easyui-combobox" name="workRequire" style="width: 100px;" data-options="required:true,editable:false">
							<option value="1">有</option>
							<option value="2">无</option>
					</select>
				</tr>
				<tr>
					<td>考试:</td>
					<td><select id="" class="easyui-combobox" name="examRequire" style="width: 100px;" data-options="required:true,editable:false">
							<option value="1">有</option>
							<option value="2">无</option>
					</select>
				</tr>
				<tr>
					<td>必修学时:</td>
					<td><input class="easyui-numberbox" type="text" name="requiredPeriod" style="width: 100px; height: 30px" data-options="required:true,validType:['length[1,4]']"></input></td>
				</tr>
				<tr>
					<td>选修学时:</td>
					<td><input class="easyui-numberbox" type="text" name="optionalPeriod" style="width: 100px; height: 30px" data-options="required:true,validType:['length[1,4]']"></input></td>
				</tr>
				<tr>
					<td>开始时间:</td>
					<td><input class="easyui-datetimebox" type="text" id="s_startDate_str" name="startDate_str" style="width: 240px;" data-options="editable:false,required:true,validType:['compareStartDateAndEndDate']"></input></td>
				</tr>
				<tr>
					<td>结束时间:</td>
					<td><input class="easyui-datetimebox" type="text" id="s_endDate_str" name="endDate_str" style="width: 240px;" 
					data-options="editable:false,required:true,validType:['checkDate']"></input></td>
				</tr>
				<tr>
					<td>班级图片:</td>
					<td><img height="100px;" width="100px;" id="gipath"><br>
					<input class="easyui-filebox" id="gradeImgfile" name="gradeImgfile" data-options="prompt:'选择一张班级图片...'"  style="width: 50%"> </input>
					<input type="hidden" id="gradeImg" name="gradeImg" /></td>
				</tr>
				<tr>
					<td>证书图片:</td>
					<td><img height="100px;" width="100px;" id="ciPath" ><br>
						<input class="easyui-filebox" id="certificateImgfile" name="certificateImgfile" data-options="accept:'image/jpeg',prompt:'选择一张证书图片...'" style="width: 50%"> </input>
						<input type="hidden" id="certificateImg" name="certificateImg" /></td>
				</tr>
				<tr>
					<td>电 子 章:</td>
					<td><img height="100px;" width="100px;" id="ePath"><br> 
					<input class="easyui-filebox" id="eleSealfile" name="eleSealfile" data-options="prompt:'选择一张电子章图片...'" style="width: 50%"> </input>
					<input type="hidden" id="eleSeal" name="eleSeal" /></td>
				</tr>
				<tr>
					<td>毕业条件:</td>
					<td><input class="easyui-textbox" type="text"
						name="graduationDesc" style="width: 400px; height: 60px"
						data-options="multiline:true,required:false,prompt:'毕业条件为选填...',validType:['length[0,250]']"></input></td>
				</tr>
				<tr>
					<td>培训目标:</td>
					<td><input class="easyui-textbox" type="text"
						name="gradeTarget" style="width: 400px; height: 60px"
						data-options="multiline:true,required:false,validType:['length[0,250]']"></input></td>
				</tr>
				<tr>
					<td>培训描述:</td>
					<td><input class="easyui-textbox" type="text" name="gradeDesc"
						style="width: 400px; height: 160px"
						data-options="multiline:true,required:false,validType:['length[0,250]']"></input></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="dlg-buttons" align="center">
		<a href="#" id="gradeSave" class="easyui-linkbutton c3" iconCls="icon-ok" onclick="javascript:submitAddGrade('1');">保存</a> 
		<a href="#" id="gradeConfig" class="easyui-linkbutton c1" iconCls="icon-ok" onclick="javascript:submitAddGrade('2');">下一步:配置课程</a> 
		<a href="#" id="gradeCancle" class="easyui-linkbutton c5" iconCls="icon-cancel" onclick="javascript:$('#dlg_grade').dialog('close')">取消</a>
	</div>
</body>
</html>