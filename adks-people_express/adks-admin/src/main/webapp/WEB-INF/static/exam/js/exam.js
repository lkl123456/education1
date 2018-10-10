// 点击查询按钮
function doSearch() {
	var examName = encodeURI($('#s_examName').val());
	$("#examList_table").datagrid('load', {
		s_examName : examName
	});
}
// 点击添加试卷
function add_exam() {
	$('#dlg_exam').dialog('open').dialog('setTitle', '新增考试');
	$('#examForm').form('clear');
}

// 点击编辑试卷(加载显示相关信息)
function edit_exam() {
	var selectRows = $('#examList_table').datagrid("getSelections");
	if (selectRows.length < 1 || selectRows.length > 1) {
		$.messager.alert("提示", "请您选中一条需要编辑的信息！", "info");
		return;
	}
	var startTime = selectRows[0].startDate_str;
	var endTime = selectRows[0].endDate_str;

	var datetime = new Date();
	// 获取当前日期
	var year = datetime.getFullYear();
	var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1)
			: datetime.getMonth() + 1;
	var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime
			.getDate();
	// 获取当前时间
	var hour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime
			.getHours();
	var minute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes()
			: datetime.getMinutes();
	var second = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds()
			: datetime.getSeconds();

	var d = year + "-" + month + "-" + date + " " + hour + ":" + minute + ":"
			+ second;
	if (startTime < d && d < endTime) {
		alert("考试进行中，不允许修改")
		return false;
	}

	$('#dlg_exam').dialog({
		title : "编辑考试"
	});
	$('#examForm').form('clear');
	$('#dlg_exam').dialog('open');
	// 给表单赋值
	$('#examForm').form('load', selectRows[0]);
}

// 点击取消 添加或编辑信息
function close_div() {
	$('#dlg_exam').dialog('close');
}

// 点击保存 添加或编辑信息
function submitForm() {
	var startDate = $('#s_startDate_str').datebox('getValue');
	var sDate = new Date(startDate.replace(/-/g, "/"));
	var endDate = $('#s_endDate_str').datebox('getValue');
	var eDate = new Date(endDate.replace(/-/g, "/"));
	if (sDate >= eDate) {
		$.messager.alert('友情提示', '开始时间不能大于或等于结束时间!', 'info');
		return;
	}

	var examName = $('#examName').val();
	$('#examForm').form('submit', {
		url : contextpath + '/exam/saveExam',
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(data) {
			var datajson = $.parseJSON(data);
			if (datajson['msg'] == 'succ') {
				close_div();
				doSearch();
			} else if (datajson['mesg'] == 'sameExamName') {
				$.messager.alert('友情提示', '考试名称不能重复!', 'info');
				return false;
			}
		},
		error : function() {
			alert('error');
		}
	});

}

// 点击删除（flag: -1表示删除，1表示开通，2表示禁用）

function del_exam(flag) {
	// 把你选中的 数据查询出来。
	var selectRows = $('#examList_table').datagrid("getSelections");
	var t_info = "删除";
	var t_url = contextpath + "/exam/delExam";
	if (selectRows.length < 1) {
		$.messager.alert("提示", "请选中要" + t_info + "的数据信息！", "info");
		return;
	}

	$.messager.confirm("确认", "您确定要" + t_info + "选中的信息吗？", function(r) {
		if (r) {
			var strIds = "";
			for (var i = 0; i < selectRows.length; i++) {
				strIds += selectRows[i].examId + ",";
			}
			strIds = strIds.substr(0, strIds.length - 1);
			$.post(t_url, {
				examId : strIds
			}, function(data) {
				if (data == "succ") {
					// 刷新表格，去掉选中状态的 那些行。
					doSearch();
				} else {
					$.messager.alert("操作失败~~", data);
				}
			});
		}
	});
}
// 选择试卷
function selectPaper() {
	var selectRows = $('#examList_table').datagrid("getSelections");
	if (selectRows.length < 1) {
		$.messager.alert("提示", "请选中要选择试卷的数据信息！", "info");
		return;
	}
	var url = contextpath + "/exam/examPaperList?examId="
			+ selectRows[0].examId;
	parent.addTab_update('选择试卷', url, 'icon-nav');
}
// 考试授权
function selectGrade() {
	var selectRows = $('#examList_table').datagrid("getSelections");
	if (selectRows.length < 1) {
		$.messager.alert("提示", "请选中要考试授权的数据信息！", "info");
		return;
	}
	var url = contextpath + "/exam/examGradeList?examId="
			+ selectRows[0].examId;
	parent.addTab_update('选择班级', url, 'icon-nav');
}
// ///////////////////////////////////////////////////////////////////////////////////
function formatScore(value, row, index) {
	return row.score + "/" + row.qsNum;
}
function formatDanXuan(value, row, index) {
	return row.danxuanScore + "/" + row.danxuanNum;
}
function formatDuoXuan(value, row, index) {
	return row.duoxuanNum + "/" + row.duoxuanScore;
}
function formatPanDuan(value, row, index) {
	return row.panduanNum + "/" + row.panduanScore;
}
function formatTianKong(value, row, index) {
	return row.tiankongNum + "/" + row.tiankongScore;
}
function formatWenDa(value, row, index) {
	return row.wendaNum + "/" + row.wendaScore;
}
function doExamPaperSearch() {
	var paperName = encodeURI($('#s_paperName').val());
	$("#examPaperList_table").datagrid('load', {
		s_paperName : paperName
	});
}
function del_examPaper() {
	// 把你选中的 数据查询出来。
	var selectRows = $('#examPaperList_table').datagrid("getSelections");
	var t_info = "删除";
	var t_url = contextpath + "/examPaper/delExamPaper";
	if (selectRows.length < 1) {
		$.messager.alert("提示", "请选中要" + t_info + "的数据信息！", "info");
		return;
	}

	$.messager.confirm("确认", "您确定要" + t_info + "选中的信息吗？", function(r) {
		if (r) {
			var strIds = "";
			for (var i = 0; i < selectRows.length; i++) {
				strIds += selectRows[i].id + ",";
			}
			strIds = strIds.substr(0, strIds.length - 1);
			$.post(t_url, {
				id : strIds
			}, function(data) {
				if (data == "succ") {
					// 刷新表格，去掉选中状态的 那些行。
					doExamPaperSearch();
				} else {
					$.messager.alert("操作失败~~", data);
				}
			});
		}
	});
}
function add_examPaper() {
	var epid = $("#epid").val();
	var examId = $("#examId").val();
	var win = window
			.open(
					contextpath + '/examPaper/addExamPaper?examId=' + examId
							+ '&epid=' + epid,
					'JavaScript',
					'height=500, width=1200, top=50, left=150, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');
}
function save_examPaper() {
	var examId = window.parent.opener.document.getElementById("examId").value;
	var scoreSum = $("#scoreSum").val();
	var selectRows = $('#addExamPaper_table').datagrid("getSelections");
	if (selectRows.length < 1) {
		$.messager.alert("提示", "请选中要添加的试卷信息！", "info");
		return;
	}

	var strIds = "";
	var paperNames = "";
	var scoreSums = "";
	for (var i = 0; i < selectRows.length; i++) {
		var html = selectRows[i].paperHtmlAdress;
		if (html != null && html != "" && html != undefined) {
			strIds += selectRows[i].paperId + ",";
			paperNames += selectRows[i].paperName + ",";
			scoreSums += selectRows[i].score + ",";
		} else {
			alert(selectRows[i].paperName + ",该试卷暂未生成html页面");
			return false;
		}
	}
	strIds = strIds.substr(0, strIds.length - 1);
	paperNames = paperNames.substr(0, paperNames.length - 1);
	scoreSums = scoreSums.substring(0, scoreSums.length - 1);

	var arr = new Array(scoreSums);
	$.unique(arr);

	if (arr.length > 1) {
		alert("所选试卷总分不一致");
		return false;
	} else {
		if (arr != scoreSum) {
			alert("所选的试卷总分和考试总分不一致");
			return false;
		}
	}
	var t_url = contextpath + "/examPaper/saveExamPaper";
	$.post(t_url, {
		examId : examId,
		paperId : strIds,
		paperName : paperNames
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

function doAddExamPaperSearch() {
	var paperName = encodeURI($('#s_paperName').val());
	$("#addExamPaper_table").datagrid('load', {
		s_paperName : paperName
	});
}

// 预览试卷
function view_paper() {
	// 把你选中的 数据查询出来。
	var selectRows = $('#examPaperList_table').datagrid("getSelections");
	var t_info = "预览";
	var t_url = contextpath + "/paperQuestion/paperView";
	if (selectRows.length < 1) {
		$.messager.alert("提示", "请选中要" + t_info + "的数据信息！", "info");
		return;
	}
	if (selectRows.length > 1) {
		$.messager.alert("提示", "只能" + t_info + "一条数据信息！", "info");
		return;
	}
	var paperId = selectRows[0].paperId;
	window.open(t_url + '?paperId=' + paperId, '', 'width='
			+ (window.screen.availWidth - 220) + ',height='
			+ (window.screen.availHeight - 210)
			+ ',toolbar=no,menubar=no,scrollbars=yes,resizable=yes');
}