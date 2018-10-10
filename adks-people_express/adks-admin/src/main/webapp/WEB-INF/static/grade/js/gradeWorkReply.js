//##############################################列表start##############################################
//格式化列数据
function formatStatus(value, row, index) {
	if (value == 1) {
		return '批改';
	} else if (value == 2) {
		return '未批改';
	}
}
function formatDateConversion(value,row,index){
	if(value != null){
		var unixTimestamp = new Date(value);
		return unixTimestamp.Format('yyyy-MM-dd');
	}
}
// ##############################################列表操作end##############################################

// ##############################################工具栏操作start##############################################
// 查询按钮点击
function doSearch() {
	$("#gradeWorkReplyListTable").datagrid('load', {
		studentName : $('#studentName').val()
	});
}
// 编辑班级
function editGradeWorkRreply() {
	var row = $('#gradeWorkReplyListTable').datagrid('getSelections');
	if (row.length != 1) {
		$.messager.alert('提示', '请选中一条记录批改', 'warning');
		return;
	}
	$('#dlg_workReply').dialog('open').dialog('setTitle', '作业详情');
	$('#gradeWorkReplyFrom').form('clear');
	// 给表单赋值
	$('#gradeWorkReplyFrom').form('load', row[0]);
	$('#submitFilePath').attr('href',row[0].submitFilePath); 
	$('#workAnswer').val(row[0].workAnswerStr);
}
// 删除班级学员作业
function deleteGradeWorkRreply() {
	// 把你选中的 数据查询出来。
	var selectRows = $('#gradeWorkReplyListTable').datagrid("getSelections");
	var t_info = "删除";
	if (selectRows.length < 1) {
		$.messager.alert("提示", "请选中要" + t_info + "的作业！", "info");
		return;
	}
	$.messager.confirm("确认", "您确定要" + t_info + "选中的作业吗？", function(r) {
		if (r) {
			var strIds = "";
			for (var i = 0; i < selectRows.length; i++) {
				strIds += selectRows[i].gradeWorkReplyId + ",";
			}
			strIds = strIds.substr(0, strIds.length - 1);
//			alert(strIds);
			if (strIds.length > 0) {
				$.ajax({
					url : contextpath + "/gradeWork/delGradeWorkReply",
					async : false,// 改为同步方式
					type : "get",
					data : {
						"gradeWorkReplyIds" : strIds
					},
					success : function(data) {
						// 刷新表格，去掉选中状态的 那些行。
						$('#gradeWorkReplyListTable').datagrid("reload");
						$('#gradeWorkReplyListTable').datagrid("clearSelections");
					},
					error : function() {
						alert('error');
					}
				});
			} else {
				$.messager.alert("操作失败~~", data);
			}

		}
	});
}
// ##############################################工具栏操作end##############################################

// ##############################################子页面操作start##############################################
//取消
function cancleAdd(){
	$('#gradeWorkReplyFrom').form('clear');
	$('#dlg_workReply').dialog('close')
}
//提交批改
function submitAdd(){
	$('#gradeWorkReplyFrom').form('submit',{
		url:contextpath+'/gradeWork/saveGradeWorkReply',
		onSubmit: function(param){
			return $(this).form('validate');
		},
		success: function(data){
			$('#dlg_workReply').dialog('close');
			$('#gradeWorkReplyListTable').datagrid('reload'); //刷新数据
		},error:function(){
			alert('error');
		}
	});
}
// ##############################################子页面操作end##############################################
// 添加面板——样式
$('#state').combo({
	panelHeight : 50,
});
function setRowStyle(index, row) {
	if (row.status == 0) {
		return 'background-color:#eee;color:blue;font-weight:bold;';
	}
}