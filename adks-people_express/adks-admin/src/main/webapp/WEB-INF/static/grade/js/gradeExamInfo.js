// 查询按钮点击
function doSearch() {
	$("#gradeExaming").datagrid('load', {
		gradeName : encodeURI($('#gradeName').val()),
	});
}

// 移除班级课程
function del_grade() {
	var rows = $('#gradeExaming').datagrid('getSelections');
	if (rows.length < 1) {
		$.messager.alert('提示', '请至少选择一个班级', 'warning');
		return;
	}
	var ss = [];
	for (var i = 0; i < rows.length; i++) {
		var row = rows[i];
		ss[i] = row.gradeId;
	}
	if (ss.length > 0) {
		// 移除
		$.ajax({
			url : contextpath + "/gradeExam/removeExamGrades",
			async : false,
			type : "get",
			data : {
				"examId" : $("#examId").val(),
				"gradeIds" : ss.toString()
			},
			success : function(data) {
				$('#gradeExaming').datagrid('reload'); // 刷新数据
				$('#gradeExamed').datagrid('reload'); // 刷新数据
			},
			error : function() {
				alert('error');
			}
		});
	}
}

setTimeout("get_examGradeId()", 50);
function get_examGradeId() {
	var selectRows = $('#gradeExaming').datagrid("getRows");
	var strIds = "";
	if (selectRows.length > 0) {
		for (var i = 0; i < selectRows.length; i++) {
			strIds += selectRows[i].gradeId + ",";
		}
	}
	$("#examGradeid").val(strIds);
}
// 添加班级
function add_grade() {
	var examId = $("#examId").val();
	var win = window
			.open(
					contextpath + '/gradeExam/addExamGradeInfo?examId='
							+ examId + '&examGradeid='
							+ $("#examGradeid").val(),
					'JavaScript',
					'height=500, width=1200, top=50, left=150, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');
}

// 添加班级考试
function save_examGrade() {
	var rows = $('#addExamGrade_table').datagrid('getSelections');
	if (rows.length < 1) {
		$.messager.alert('提示', '请至少选择一个班级', 'warning');
		return;
	}
	var ss = [];
	for (var i = 0; i < rows.length; i++) {
		var row = rows[i];
		ss[i] = row.gradeId;
	}
	if (ss.length > 0) {
		// 添加
		$.get(contextpath + "/gradeExam/addExamGrades", {
			"examId" : $("#examId").val(),
			"gradeIds" : ss.toString()
		}, function(data) {
			if (data == "succ") {
				// 刷新表格，去掉选中状态的 那些行。
				window.parent.opener.location.reload();
				window.parent.close();
			} else {
				$.messager.alert("操作失败~~", data);
			}
		});
	}
}

// ##############################################工具栏end################################################
