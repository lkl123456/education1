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
	src="${pageContext.request.contextPath}/static/question/js/question.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/question/js/questionSort.js"></script>
</head>
<body class="easyui-layout">
	<!-- 课程分类机构树 -->
	<div data-options="region:'west',split:true,title:'题库分类列表'"
		style="width: 260px; padding: 10px;">
		<input type="hidden" id="cc_ztree_node_id" value="0" /> <input
			type="hidden" id="cc_ztree_node_name" value="顶级分类" /> <input
			type="hidden" id="cc_ztree_node_code" value="0-" />
		<div id="btn_questionSort_div">
			&nbsp;<a href="#" onclick="add_questionSort();"
				class="easyui-linkbutton" iconCls="icon-add">添加</a> &nbsp;<a
				href="#" onclick="edit_questionSort();" class="easyui-linkbutton"
				iconCls="icon-edit">编辑</a> &nbsp;<a href="#"
				onclick="del_questionSort();" class="easyui-linkbutton"
				iconCls="icon-remove">删除</a>

		</div>
		<div>
			<ul id="questionSortTreeDemo" class="ztree"></ul>
		</div>
	</div>
	<!-- 工具栏 -->
	<div id="tb"
		style="padding: 15px 10px 10px 10px; height: auto; overflow: hidden;">
		<div style="float: left;">

			题目:<input id="s_questionName" class="easyui-textbox"
				data-options="prompt:'题目...'" style="width: 200px; height: 24px;" />
			题型:<select class="easyui-combobox" id="s_questionType"
				data-options="required:true" style="width: 100px;">
				<option value="">全部</option>
				<option value="1">单选</option>
				<option value="2">多选</option>
				<option value="3">判断</option>
				<option value="4">填空</option>
				<option value="5">问答</option>
			</select> <a href="#" class="easyui-linkbutton" iconCls="icon-search"
				onclick="doSearch()">查询</a> &nbsp;&nbsp; &nbsp;&nbsp;
		</div>
		<div style="margin-bottom: 5px; float: right;">
			<a href="#" onclick="add_question()" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">添加</a> <a href="#"
				onclick="edit_question()" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true">编辑</a> <a href="#"
				onclick="del_question(-1)" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true">删除</a><a
				href="javascript:void(0);" class="easyui-linkbutton"
				onclick="importQuestion()" iconCls="icon-save" plain="true">批量导入</a>
			&nbsp;<a href="#" class="easyui-linkbutton" iconCls="icon-save"
				onclick="exportQuestion();" plain="true">批量导出</a>
		</div>
	</div>

	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<div title="试题列表信息" data-options="iconCls:'icon icon-nav',fit:true">
			<table id="questionList_table" style="width: 800px; height: 100%;"
				class="easyui-datagrid" toolbar="#tb"
				data-options="
			url:'${pageContext.request.contextPath}/question/getQuestionListJson',
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
						<th data-options="field:'questionId',checkbox:true"></th>
						<th data-options="field:'questionName'" align="center" width="80">题目</th>
						<th data-options="field:'questionType',align:'center'"
							formatter="formatStatus" width="100">题型</th>
						<th data-options="field:'questionValue',align:'center'"
							width="100">分值</th>
						<th data-options="field:'qsSortName',align:'center'" width="100">所属题库</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

	<!-- 添加、编辑课程分类窗口 -->
	<div id="dlg_questionSort" class="easyui-dialog" fit="true"
		title="添加题库分类" style="width: 600px; height: 600px; padding: 10px;"
		data-options="
				iconCls: 'icon-save',
				modal:true,closed:true,
				buttons: [{
					text:'保存',
					iconCls:'icon-ok',
					handler:function(){
						submitAdd_questionSort();
					}
				},{
					text:'取消',
					iconCls:'icon-cancel',
					handler:function(){
						cancleAdd_questionSort();
					}
				}]
			">
		<div style="padding: 10px 120px 20px 120px">
			<form id="questionSortForm" method="post"
				action="${pageContext.request.contextPath}/questionSort/saveQuestionSort">
				<input id="id_parentQsSortID" name="parentQsSortID" type="hidden">
				<input name="qsSortCode" type="hidden" /> <input name="qtSortID"
					type="hidden"> <input name="qsNum" type="hidden">

				<table cellpadding="5">
					<tr>
						<td>题库名称:</td>
						<td><input class="easyui-textbox" type="text"
							name="qsSortName" id="qsSortName"
							data-options="required:true,validType:['isNullOrEmpty']"
							style="width: 160px;"></input></td>
					</tr>
					<tr>
						<td>上级题库:</td>
						<td><input id="id_parentQsSortName" name="parentQsSortName"
							type="text" readonly="readonly"></td>
					</tr>
				</table>
			</form>
		</div>
	</div>

	<!-- 添加网校对话框 -->
	<div id="dlg_question" class="easyui-dialog" title="添加试题" fit="true"
		style="width: 600px; height: 600px; padding: 10px;"
		data-options="
				iconCls: 'icon-save',
				modal:true,closed:true,
				buttons: [{
					text:'保存',
					iconCls:'icon-ok',
					handler:function(){
						submitForm();
					}
				},{
					text:'取消',
					iconCls:'icon-cancel',
					handler:function(){
						close_div();
					}
				}]
			">
		<div style="padding-top: 2px; padding-left: 20px;">
			<form id="questionForm" method="post"
				action="${pageContext.request.contextPath}/question/saveQuestion">
				<input type="hidden" name="questionId" /> <input type="hidden"
					name="questionType" id="qt" />
				<table cellpadding="5">
					<tr>
						<td>题型:</td>
						<td><input onclick="changeType();" type="radio"
							id="questionType" name="questionType" value="1" />单选 <input
							onclick="changeType();" type="radio" id="questionType2"
							name="questionType" value="2" />多选 <input
							onclick="changeType();" type="radio" id="questionType3"
							name="questionType" value="3" />判断 <input
							onclick="changeType();" type="radio" id="questionType4"
							name="questionType" value="4" />填空 <input
							onclick="changeType();" type="radio" id="questionType5"
							name="questionType" value="5" />问答</td>
					</tr>
					<tr>
						<td>题目:</td>
						<td><input class="easyui-textbox" id="questionName"
							type="text" name="questionName"
							data-options="required:true,validType:['isNullOrEmpty','length[1,50]']"></input></td>
					</tr>
					<tr id="tiankongMessage" style="display: none;">
						<td>填空出题须知：</td>
						<td><font style="background-color: #EDEDED">1.在需要填空的地方，用英文输入法输入三根下划线表示，即“___”。<br>2.多个答案应该用“|”隔开。<br>3.如果一个空有多个标准答案请用“&amp;”隔开。
						</font></td>
					</tr>
					<tr id="danxuanAnswer">
						<td>候选项:</td>
						<td>
							<div id="danxuantip1">
								<input type="hidden" id="answerCount" value="4"> <input
									type="radio" id="radioA" name="anwsers"
									onclick="check_questionType();" value="A">A.
								<!-- 							<input onblur="check_questionType();" class="easyui-textbox" type="text" id="answer_radioA" name="optionA" data-options="required:false" style="width: 238px;" > -->
								<input onblur="check_questionType();"
									class="easyui-validatebox textbox" type="text"
									id="answer_radioA" name="optionA"
									data-options="required:false,validType:['length[1,50]']"
									style="width: 238px;"> <span style="color: red;"
									id="all"></span>
							</div>
							<div id="danxuantip2">
								<input type="radio" id="radioB" name="anwsers"
									onclick="check_questionType();" value="B">B. <input
									onblur="check_questionType();"
									class="easyui-validatebox textbox" type="text"
									id="answer_radioB" name="optionB"
									data-options="required:false,validType:['length[1,50]']"
									style="width: 238px;">
							</div>
							<div id="danxuantip3">
								<input type="radio" id="radioC" name="anwsers"
									onclick="check_questionType();" value="C">C. <input
									onblur="check_questionType();"
									class="easyui-validatebox textbox" type="text"
									id="answer_radioC" name="optionC"
									data-options="required:false,validType:['length[1,50]']"
									style="width: 238px;">
							</div>
							<div id="danxuantip4">
								<input type="radio" id="radioD" name="anwsers"
									onclick="check_questionType();" value="D">D. <input
									onblur="check_questionType();"
									class="easyui-validatebox textbox" type="text"
									id="answer_radioD" name="optionD"
									data-options="required:false,validType:['length[1,50]']"
									style="width: 238px;">
							</div>
							<div id="danxuantip5" style="display: none;">
								<input type="radio" id="radioE" name="anwsers"
									onclick="check_questionType();" value="E">E. <input
									onblur="check_questionType();"
									class="easyui-validatebox textbox" type="text"
									id="answer_radioE" name="optionE"
									data-options="required:false,validType:['length[1,50]']"
									style="width: 238px;">
							</div>
							<div id="danxuantip6" style="display: none;">
								<input type="radio" id="radioF" name="anwsers"
									onclick="check_questionType();" value="F">F. <input
									onblur="check_questionType();"
									class="easyui-validatebox textbox" type="text"
									id="answer_radioF" name="optionF"
									data-options="required:false,validType:['length[1,50]']"
									style="width: 238px;">
							</div>
							<div id="danxuantip7" style="display: none;">
								<input type="radio" id="radioG" name="anwsers"
									onclick="check_questionType();" value="G">G. <input
									onblur="check_questionType();"
									class="easyui-validatebox textbox" type="text"
									id="answer_radioG" name="optionG"
									data-options="required:false,validType:['length[1,50]']"
									style="width: 238px;">
							</div>
							<div id="danxuantip8" style="display: none;">
								<input type="radio" id="radioH" name="anwsers"
									onclick="check_questionType();" value="H">H. <input
									onblur="check_questionType();"
									class="easyui-validatebox textbox" type="text"
									id="answer_radioH" name="optionH"
									data-options="required:false,validType:['length[1,50]']"
									style="width: 238px;">
							</div>
						</td>
						<td><input type="button" value="增加" class="btnInput3"
							onclick="changeCount('danxuantip','add');"><br> <br>
							<input type="button" value="减少" class="btnInput3"
							onclick="changeCount('danxuantip','delete');"></td>
					</tr>
					<tr id="duoxuanAnswer" style="display: none;">
						<td>候选项:</td>
						<td>
							<div id="duoxuantip1">
								<input id="checkboxA" type="checkbox" name="anwsers" value="A">A.
								<input onblur="check_questionType();"
									class="easyui-validatebox textbox" type="text"
									id="answer_checkboxA" name="optionA"
									data-options="required:false,validType:['length[1,50]']"
									style="width: 238px;"> <span class="fontCom"
									id="checkbox_all"></span>
							</div>
							<div id="duoxuantip2">
								<input id="checkboxB" type="checkbox" name="anwsers" value="B">B.
								<input onblur="check_questionType();"
									class="easyui-validatebox textbox" type="text"
									id="answer_checkboxB" name="optionB"
									data-options="required:false,validType:['length[1,50]']"
									style="width: 238px;">
							</div>
							<div id="duoxuantip3">
								<input id="checkboxC" type="checkbox" name="anwsers" value="C">C.
								<input onblur="check_questionType();"
									class="easyui-validatebox textbox" type="text"
									id="answer_checkboxC" name="optionC"
									data-options="required:false,validType:['length[1,50]']"
									style="width: 238px;">
							</div>
							<div id="duoxuantip4">
								<input id="checkboxD" type="checkbox" name="anwsers" value="D">D.
								<input onblur="check_questionType();"
									class="easyui-validatebox textbox" type="text"
									id="answer_checkboxD" name="optionD"
									data-options="required:false,validType:['length[1,50]']"
									style="width: 238px;">
							</div>
							<div id="duoxuantip5" style="display: none;">
								<input id="checkboxE" type="checkbox" name="anwsers" value="E">E.
								<input onblur="check_questionType();"
									class="easyui-validatebox textbox" type="text"
									id="answer_checkboxE" name="optionE"
									data-options="required:false,validType:['length[1,50]']"
									style="width: 238px;">
							</div>
							<div id="duoxuantip6" style="display: none;">
								<input id="checkboxF" type="checkbox" name="anwsers" value="F">F.
								<input onblur="check_questionType();"
									class="easyui-validatebox textbox" type="text"
									id="answer_checkboxF" name="optionF"
									data-options="required:false,validType:['length[1,50]']"
									style="width: 238px;">
							</div>
							<div id="duoxuantip7" style="display: none;">
								<input id="checkboxG" type="checkbox" name="anwsers" value="G">G.
								<input onblur="check_questionType();"
									class="easyui-validatebox textbox" type="text"
									id="answer_checkboxG" name="optionG"
									data-options="required:false,validType:['length[1,50]']"
									style="width: 238px;">
							</div>
							<div id="duoxuantip8" style="display: none;">
								<input id="checkboxH" type="checkbox" name="anwsers" value="H">H.
								<input onblur="check_questionType();"
									class="easyui-validatebox textbox" type="text"
									id="answer_checkboxH" name="optionH"
									data-options="required:false,validType:['length[1,50]']"
									style="width: 238px;">
							</div>
						</td>
						<td><input type="button" value="增加" class="btnInput3"
							onclick="changeCount('duoxuantip','add');"><br> <br>
							<input type="button" value="减少" class="btnInput3"
							onclick="changeCount('duoxuantip','delete');"></td>
					</tr>
					<tr id="pdAnswer" style="display: none">
						<td>正确答案：</td>
						<td>正确：<input type="radio" id="pdAnswerA" name="anwsers"
							value="1"> 错误：<input type="radio" id="pdAnswerB"
							name="anwsers" value="0">&nbsp;&nbsp;<font size="5"
							color="red">*</font>
						</td>
					</tr>
					<tr id="zgAnswer" style="display: none;">
						<td>标准答案：</td>
						<td><input class="easyui-textbox" id="bz_anwsers" type="text"
							name="anwsers"
							data-options="required:false,validType:['length[1,50]']"
							style="width: 238px;" /></td>
					</tr>
					<tr>
						<td>所属题库:</td>
						<td><select id="cc2" name="qtSortId"
							data-options="required:true" style="width: 200px;"></td>
					</tr>
					<tr>
						<td>分值:</td>
						<td><input class="easyui-numberbox" min="0" max="100"
							type="text" name="questionValue"
							data-options="required:true,validType:['length[1,3]','min:0','max:150']"
							style="width: 238px;"></input></td>
						<!-- 	    			<td><input class="easyui-textbox" type="text" name="questionValue" data-options="required:true" style="width: 238px;" ></input></td> -->
					</tr>
				</table>
			</form>
		</div>
	</div>

	<!-- 批量导入对话框 -->
	<div id="dlg_import_question" class="easyui-dialog" title="批量导入"
		fit="true" style="width: 600px; height: 600px; padding: 10px;"
		data-options="
				iconCls: 'icon-save',
				modal:true,closed:true,
				buttons: [{
					text:'保存',
					iconCls:'icon-ok',
					handler:function(){
						submitImport();
					}
				},{
					text:'取消',
					iconCls:'icon-cancel',
					handler:function(){
						cancleImport();
					}
				}]
			">
		<div style="padding-top: 2px; padding-left: 20px;">
			<form id="importQuestion" method="post" enctype="multipart/form-data">
				<h3>批量导入试题-注意事项</h3>
				<table cellpadding="5">
					<tr>
						<td>1、请先选择文件，然后再导入。</td>
					</tr>
					<tr>
						<td>2、导入的文件为excel文件，请按模板中的样式制表。</td>
					</tr>
					<tr>
						<td>3、请将excel文件中的单元格格式中的数字设置为文本。</td>
					</tr>
					<tr>
						<td>第一步：下载文件模板&nbsp;&nbsp;<a href="#" onclick="downmb()"
							style="color: blue;"><u>下载模板</u></a>&nbsp;（点击下载）
						</td>
					</tr>

					<tr>
						<td>第二步：选择试题存放题库<br /> <select id="iptk" name="qtSortId"
							data-options="required:true" style="width: 200px;"></td>
					</tr>
					<tr>
						<td>第三步：上传试题文件&nbsp;&nbsp;&nbsp;<input class="easyui-filebox"
							name="importExcelFile" id="importExcelFile"
							data-options="prompt:'选择模板文件...',required:true"
							style="width: 230px;"><i
							style="color: red; margin-left: 20px;">*文件大小不能超过10M</i>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<!-- 批量导入对话框 -->
	<div style="padding-top: 2px; padding-left: 20px; z-index: 0;">
		<form id="export_questions" method="post"
			action="${pageContext.request.contextPath}/question/exportQuestions">
			<input id="questionIds" name="questionIds" type="hidden" value="">
		</form>
	</div>
</body>
</html>