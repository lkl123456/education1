<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript">
	var contextpath = '${pageContext.request.contextPath}';
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>讲师管理</title>
<%@include file="../public/header.jsp"%>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/author/author.js"></script>
</head>
<body class="easyui-layout">

	<div data-options="region:'west',split:true,title:'机构列表'"
		style="width: 260px; padding: 10px;">
		<div>
			<ul id="treeDemo" class="ztree"></ul>
		</div>

	</div>

	<!-- 工具栏 -->
	<div id="tb" style="padding:15px 10px 10px 10px;height:auto; overflow:hidden; ">
		<div style="float:left;">
			<input id="s_author_name" class="easyui-textbox"
				data-options="prompt:'讲师名...'" style="width: 200px; height: 24px;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search"
				onclick="doSearch()">查询</a>
		</div>
		<div style="margin-bottom:5px; float:right;">
			<a href="#" onclick="addDialog();" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a> 
			<a href="#" onclick="editAuthor();" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a> 
			<a href="#" onclick="del_author()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		</div>
	</div>
	<div id="mainText" data-options="region:'center'">
		<table id="authorlistdatagrid" style="width: 800px; height: 350px"
			class="easyui-datagrid" toolbar="#tb"
			data-options="
					url:'${pageContext.request.contextPath}/author/authorListPage',
					method:'post',
					singleSelect:false,
					fit:true,
					fitColumns:true,
					nowarp:false,
					pagination:true,
					pageSize:20,
					pageList:[20,25,30],
					rownumbers:true,
					rowStyler:setRowStyle
					">
			<thead>
				<tr>
					<th data-options="field:'authorId',checkbox:true"></th>
					<th data-options="field:'authorName'" align="center" width="100">讲师名</th>
					<th data-options="field:'authorFirstLetter'" align="center"
						width="100">首字母</th>
					<th data-options="field:'authorSex',align:'center'"
						formatter="formatColumnData" width="80">性别</th>
					<th data-options="field:'orgName',align:'center'" width="150">所属机构</th>
					<th data-options="field:'creatorName',align:'center'" width="50">创建人</th>
				</tr>
			</thead>
		</table>
	</div>

	<input type="hidden" id="ossPath" value="${ossResource }" />
	<!-- 添加用户div -->
	<div id="dlg_author" class="easyui-dialog" title="添加讲师"
		style="width: 490px; height: 380px; padding: 10px;"
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
		<div style="padding-top: 2px; padding-left: 20px;">
			<form id="hh" method="post" enctype="multipart/form-data"
				action="${pageContext.request.contextPath}/author/saveAuthor">
				<input type="hidden" name="authorId" value="" /> 
				<input type="hidden" id="orgId" name="orgId" value="" /> 
				<input type="hidden" id="orgCode" name="orgCode" value="" /> 
				<input type="hidden" id="orgName" name="orgName" value="" /> 
				<input type="hidden" id="authorFirstLetter" name="authorFirstLetter" value="" />
				<input type="hidden" id="authorPhoto" name="authorPhoto"  value=""/>
				<table>
					<tr>
						<td>讲师名称:</td>
						<td><input class="easyui-textbox" type="text" id="authorName"
							name="authorName" data-options="required:true,validType:['isNullOrEmpty','length[1,32]']"
							style="width: 160px;"></input></td>
					</tr>
					<tr>
						<td>讲师图像:</td>
						<td><img id="apPath" height="86" width="131" /> <br /> 
							<input class="easyui-filebox" id="authorPhotofile" name="authorPhotofile" data-options="prompt:'选择一张讲师图片...'" 
							style="width: 80%"> </input>
						</td>
					</tr>
					<tr>
						<td>讲师性别:</td>
						<td><input type="radio" name="authorSex" checked="checked"
							value="1" />男 <input type="radio" name="authorSex" value="2" />女
						</td>
					</tr>
					<tr>
						<td>所属机构:</td>
						<td><input id="orgNameShow" disabled="disabled" type="text"
							style="width: 160px;"></input></td>
					</tr>
					<tr>
						<td>讲师简介:</td>
						<td><textarea id="authorDes"
								style="width: 320px; height: 60px" name="authorDes">
	    				</textarea></td>
					</tr>
				</table>
			</form>
		</div>
	</div>

</body>
</html>