$(function() {
	$('#questionForm').form({
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(data) {
			if (data == 'succ') {
				// $.messager.alert("提示","信息保存成功!");
				close_div();
				doSearch();
				$('#dlg_export_question').dialog('close');
			}
		}
	});
	$("#qt").val($("input[name='questionType']:checked").val());
	$("#answerCount").val(4);
});

// 点击查询按钮
function doSearch() {
	var questionName = encodeURI($('#s_questionName').val());
	var questionType = $('#s_questionType').combobox('getValue');
	;
	$("#questionList_table").datagrid('load', {
		s_questionName : questionName,
		s_questionType : questionType
	});
}
// 格式化列数据
function formatStatus(value, row, index) {
	if (value == 1) {
		return '单选';
	} else if (value == 2) {
		return '多选';
	} else if (value == 3) {
		return '判断';
	} else if (value == 4) {
		return '填空';
	} else if (value == 5) {
		return '问答';
	}
}
// 点击添加试题
function add_question() {
	$('#dlg_question').dialog('open').dialog('setTitle', '新增试题');
	$('#questionForm').form('clear');
	$('#cc2').combotree({
		url : contextpath + '/questionSort/getQuestionSortJson',
		method : 'get'
	});
	$("#questionType").attr("disabled", false);
	$("#questionType2").attr("disabled", false);
	$("#questionType3").attr("disabled", false);
	$("#questionType4").attr("disabled", false);
	$("#questionType5").attr("disabled", false);
	$("#questionType").attr("checked", true);
	$("#answerCount").val(4);
}

// 点击编辑网校信息(加载显示相关信息)
function edit_question() {
	var selectRows = $('#questionList_table').datagrid("getSelections");
	if (selectRows.length < 1 || selectRows.length > 1) {
		$.messager.alert("提示", "请您选中一条需要编辑的信息！", "info");
		return;
	}
	$('#dlg_question').dialog({
		title : "编辑网校"
	});
	$('#dlg_question').dialog('open');
	$('#questionForm').form('clear');
	// 给表单赋值
	$("#qt").val(selectRows[0].questionType);
	if (selectRows[0].questionId != null) {
		$("#questionType").attr("disabled", true);
		$("#questionType2").attr("disabled", true);
		$("#questionType3").attr("disabled", true);
		$("#questionType4").attr("disabled", true);
		$("#questionType5").attr("disabled", true);
	}
	$('#cc2').combotree({
		url : contextpath + '/questionSort/getQuestionSortJson',
		method : 'get'
	});
	$('#questionForm').form('load', selectRows[0]);
	changeType();
	var answers = selectRows[0].anwsers.split(',');

	if (selectRows[0].questionType == 1) {
		for (var i = 0; i < answers.length; i++) {
			if (answers[i] == 'A') {
				$("#radioA").attr("checked", true);
			} else if (answers[i] == 'B') {
				$("#radioB").attr("checked", true);
			} else if (answers[i] == 'C') {
				$("#radioC").attr("checked", true);
			} else if (answers[i] == 'D') {
				$("#radioD").attr("checked", true);
			} else if (answers[i] == 'E') {
				$("#radioE").attr("checked", true);
			} else if (answers[i] == 'F') {
				$("#radioF").attr("checked", true);
			} else if (answers[i] == 'G') {
				$("#radioG").attr("checked", true);
			} else if (answers[i] == 'H') {
				$("#radioH").attr("checked", true);
			}
		}
	} else if (selectRows[0].questionType == 2) {
		for (var i = 0; i < answers.length; i++) {
			if (answers[i] == 'A') {
				$("#checkboxA").attr("checked", true);
			} else if (answers[i] == 'B') {
				$("#checkboxB").attr("checked", true);
			} else if (answers[i] == 'C') {
				$("#checkboxC").attr("checked", true);
			} else if (answers[i] == 'D') {
				$("#checkboxD").attr("checked", true);
			} else if (answers[i] == 'E') {
				$("#checkboxE").attr("checked", true);
			} else if (answers[i] == 'F') {
				$("#checkboxF").attr("checked", true);
			} else if (answers[i] == 'G') {
				$("#checkboxG").attr("checked", true);
			} else if (answers[i] == 'H') {
				$("#checkboxH").attr("checked", true);
			}
		}
	} else if (selectRows[0].questionType == 3) {
		for (var i = 0; i < answers.length; i++) {
			if (answers[i] == 1) {
				$("#pdAnswerA").attr("checked", true);
			} else if (answers[i] == 0) {
				$("#pdAnswerB").attr("checked", true);
			}
		}
	} else if (selectRows[0].questionType == 5
			|| selectRows[0].questionType == 4) {
		console.log(selectRows[0].anwsers);
		$("#bz_anwsers").textbox("setValue", selectRows[0].anwsers);

	}
}

// 点击取消 添加或编辑信息
function close_div() {
	$('#dlg_question').dialog('close');
}

// 点击保存 添加或编辑信息
function submitForm() {
	var questionName = $('#questionName').val();
	// ajax form提交
	var typeObj = document.getElementsByName("questionType");
	var type;
	for (i = 0; i < typeObj.length; i++) {
		if (typeObj[i].checked == true) {
			type = typeObj[i].value;
		}
	}
	$("#qt").val(type);
	if (type == "3") {
		var obj = document.getElementsByName("anwsers");
		var num = 0;
		for (var i = 0; i < obj.length; i++) {
			if (obj[i].checked) {
				num++;
			}
		}
		if (num == 0) {
			// alert("请选择正确答案");
			$.messager.alert('友情提示', '请选择正确答案', 'info');
			return false;
		}
	}
	if (type == "5" || type == "4") {
		var anwsers = $('#bz_anwsers').val();
		if (null == anwsers || trim(anwsers) == "") {
			alert("标准答案不能为空");
			return false;
		}
		if (anwsers.length > 500) {
			alert("标准答案长度不能超过500个字符");
			return false;
		}
	}

	var t_url = $('#questionForm').attr("action");
	$('#questionForm').form('submit', {
		url : contextpath + '/question/saveQuestion',
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(data) {
			if (data == "succ") {
				close_div();
			} else if (data == "sameQuestionName") {
				$.messager.alert('友情提示', '试题名称不能重复', 'info');
			} else {
				$.messager.alert("操作失败~~", data);
			}
		},
		error : function() {
			alert('error');
		}
	});
	// $('#questionForm').submit();
}

// 点击删除（flag: -1表示删除，1表示开通，2表示禁用）

function del_question(flag) {
	// 把你选中的 数据查询出来。
	var selectRows = $('#questionList_table').datagrid("getSelections");
	var t_info = "删除";
	var t_url = contextpath + "/question/delQuestion";
	if (selectRows.length < 1) {
		$.messager.alert("提示", "请选中要" + t_info + "的数据信息！", "info");
		return;
	}

	$.messager.confirm("确认", "您确定要" + t_info + "选中的信息吗？", function(r) {
		if (r) {
			var strIds = "";
			for (var i = 0; i < selectRows.length; i++) {
				strIds += selectRows[i].questionId + ",";
			}
			strIds = strIds.substr(0, strIds.length - 1);
			$.post(t_url, {
				questionId : strIds
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
function clearCheckbox() {
	/*
	 * $("#answer_checkboxA").textbox('setValue','');
	 * $("#answer_checkboxB").textbox('setValue','');
	 * $("#answer_checkboxC").textbox('setValue','');
	 * $("#answer_checkboxD").textbox('setValue','');
	 * $("#answer_checkboxE").textbox('setValue','');
	 * $("#answer_checkboxF").textbox('setValue','');
	 * $("#answer_checkboxG").textbox('setValue','');
	 * $("#answer_checkboxH").textbox('setValue','');
	 */
	$("#answer_checkboxA").val("");
	$("#answer_checkboxB").val("");
	$("#answer_checkboxC").val("");
	$("#answer_checkboxD").val("");
	$("#answer_checkboxE").val("");
	$("#answer_checkboxF").val("");
	$("#answer_checkboxG").val("");
	$("#answer_checkboxH").val("");
}
function clearRadio() {
	// $("#answer_radioA").textbox('setValue','');
	// $("#answer_radioB").textbox('setValue','');
	// $("#answer_radioC").textbox('setValue','');
	// $("#answer_radioD").textbox('setValue','');
	// $("#answer_radioE").textbox('setValue','');
	// $("#answer_radioF").textbox('setValue','');
	// $("#answer_radioG").textbox('setValue','');
	// $("#answer_radioH").textbox('setValue','');
	$("#answer_radioA").val("");
	$("#answer_radioB").val("");
	$("#answer_radioC").val("");
	$("#answer_radioD").val("");
	$("#answer_radioE").val("");
	$("#answer_radioF").val("");
	$("#answer_radioG").val("");
	$("#answer_radioH").val("");
}
function clearPd() {
	// $("#pdAnswerA").val("");
	// $("#pdAnswerB").val("");
}
// 试题类型转换
function changeType() {
	var typeObj = document.getElementsByName("questionType");
	var type;
	for (i = 0; i < typeObj.length; i++) {
		if (typeObj[i].checked == true) {
			type = typeObj[i].value;
		}
	}
	if (type == "1") {
		var obj2 = document.getElementsByName("anwsers");
		for (var j = 0; j < obj2.length; j++) {
			obj2[j].checked = false;
		}

		clearCheckbox();
		clearPd();

		$("#duoxuanAnswer").css('display', 'none');
		$("#pdAnswer").css('display', 'none');
		$("#zgAnswer").css('display', 'none');
		$("#tiankongMessage").css('display', 'none');
		$("#danxuanAnswer").removeAttr("style");
	} else if (type == "2") {
		var obj = document.getElementsByName("anwsers");
		for (var i = 0; i < obj.length; i++) {
			obj[i].checked = false;
		}
		clearRadio();
		clearPd();
		$("#danxuanAnswer").css('display', 'none');
		$("#pdAnswer").css('display', 'none');
		$("#zgAnswer").css('display', 'none');
		$("#tiankongMessage").css('display', 'none');
		$("#duoxuanAnswer").removeAttr("style");
	} else if (type == "3") {
		clearCheckbox();
		clearRadio();
		$("#danxuanAnswer").css('display', 'none');
		$("#duoxuanAnswer").css('display', 'none');
		$("#zgAnswer").css('display', 'none');
		$("#tiankongMessage").css('display', 'none');
		$("#pdAnswer").removeAttr("style");
	} else if (type == "4") {
		clearCheckbox();
		clearRadio();
		clearPd();
		$("#danxuanAnswer").css('display', 'none');
		$("#duoxuanAnswer").css('display', 'none');
		$("#pdAnswer").css('display', 'none');
		$("#tiankongMessage").removeAttr("style");
		$("#zgAnswer").removeAttr("style");
	} else if (type == "5") {
		clearCheckbox();
		clearRadio();
		clearPd();
		$("#danxuanAnswer").css('display', 'none');
		$("#duoxuanAnswer").css('display', 'none');
		$("#pdAnswer").css('display', 'none');
		$("#tiankongMessage").css('display', 'none');
		$("#zgAnswer").removeAttr("style");
	}
	$("#bz_anwsers").textbox('setValue', '');

}

function check_questionType() {
	document.getElementById("all").innerHTML = "";
	document.getElementById("checkbox_all").innerHTML = "";
	var typeObj = document.getElementsByName("questionType");
	var type;
	for (i = 0; i < typeObj.length; i++) {
		if (typeObj[i].checked == true) {
			type = typeObj[i].value;
		}
	}
	// 单选、多选
	if (type == "1" || type == "2") {
		var obj = document.getElementsByName("anwsers");
		var obj2 = document.getElementsByName("anwsers");
		var num = 0;
		var num2 = 0;
		for (var i = 0; i < obj.length; i++) {
			if (obj[i].checked) {
				num++;
			}
		}
		for (var j = 0; j < obj2.length; j++) {
			if (obj2[j].checked) {
				num2++;
			}
		}
		if (num == 0 && num2 == 0) {
			document.getElementById("all").innerHTML = "请为候选项选择正确答案";
			// parent.$.messager.alert('友情提示', '请为候选项选择正确答案', 'info');
			document.getElementById("checkbox_all").innerHTML = "请为候选项选择正确答案";

			return false;
		}
		// 增加判断选项是否填写内容
		/*
		 * if(num > 0 || num2 > 0){ for(var i = 1; i < 11; i++){ if(type == "1" &&
		 * document.getElementById("danxuantip" + i).style.display == ""){ var
		 * radioId = "answer_radio" + obj[i-1].value; var temp =
		 * document.getElementById(radioId).value; var spanId = "answer_radio_"+
		 * obj[i-1].value; //alert(spanId);
		 * document.getElementById(spanId).innerHTML=""; if(trimStr(temp) ==
		 * ""){ document.getElementById(spanId).innerHTML="请为候选项【" +
		 * obj[i-1].value + "】输入内容"; return false; } } if(type == "2" &&
		 * document.getElementById("duoxuantip" + i).style.display == ""){ var
		 * checkboxId = "answer_checkbox" + obj2[i-1].value; var temp =
		 * document.getElementById(checkboxId).value; var span_Id =
		 * "answer_checkbox_" + obj2[i-1].value;
		 * document.getElementById(span_Id).innerHTML=""; if(trimStr(temp) ==
		 * ""){ document.getElementById(span_Id).innerHTML="请为候选项【" +
		 * obj2[i-1].value + "】输入内容"; return false; } } } }
		 */

	}
	if (type == "3") {
		var obj = document.getElementsByName("anwsers");
		var num = 0;
		for (var i = 0; i < obj.length; i++) {
			if (obj[i].checked) {
				num++;
			}
		}
		if (num == 0) {
			// alert("请选择正确答案");
			$.messager.alert('友情提示', '请选择正确答案', 'info');
			return false;
		}
	}
}

function changeCount(type, flag) {
	var num = $("#answerCount").val();
	console.log(num);
	// 点击增加一行或减少一行后，当前的选项的数量是默认数量加1或者减1
	if (flag == "add") {
		var answerCount = parseInt(num) + parseInt(1);
		$("#answerCount").val(answerCount);
	} else if (flag == "delete") {
		if (num == 4) {
			$.messager.alert('友情提示', '候选项数量不能小于4个！', 'info');
			return false;
		}
		var answerCount = parseInt(num) - parseInt(1);
		$("#answerCount").val(answerCount);
	}
	var answer_radio = document.getElementsByName("answer_radio");
	// for(var i=0;i<answer_radio.length;i++){
	// if(answer_radio[i].checked !="checked"){
	// answer_radio[i].checked=false;
	// }
	// }
	var answer_checkbox = document.getElementsByName("answer_checkbox");
	// for(var i=0;i<answer_checkbox.length;i++){
	// answer_checkbox[i].checked=false;
	// }
	if (answerCount == 3) {
		$("#" + type + "1").removeAttr("style");
		$("#" + type + "2").removeAttr("style");
		$("#" + type + "3").removeAttr("style");
		$("#" + type + "4").css('display', 'none');
		$("#" + type + "5").css('display', 'none');
		$("#" + type + "6").css('display', 'none');
		$("#" + type + "7").css('display', 'none');
		$("#" + type + "8").css('display', 'none');
		if (document.getElementById("answer_radioD")) {
			answer_radio[3].checked = false;
			document.getElementById("answer_radioD").value = "";
		}
		if (document.getElementById("answer_radioE")) {
			answer_radio[4].checked = false;
			document.getElementById("answer_radioE").value = "";
		}
		if (document.getElementById("answer_radioF")) {
			answer_radio[5].checked = false;
			document.getElementById("answer_radioF").value = "";
		}
		if (document.getElementById("answer_radioG")) {
			answer_radio[6].checked = false;
			document.getElementById("answer_radioG").value = "";
		}
		if (document.getElementById("answer_radioH")) {
			answer_radio[7].checked = false;
			document.getElementById("answer_radioH").value = "";
		}

		if (document.getElementById("answer_checkboxD")) {
			answer_checkbox[3].checked = false;
			document.getElementById("answer_checkboxD").value = ""
		}
		if (document.getElementById("answer_checkboxE")) {
			answer_checkbox[4].checked = false;
			document.getElementById("answer_checkboxE").value = ""
		}
		if (document.getElementById("answer_checkboxF")) {
			answer_checkbox[5].checked = false;
			document.getElementById("answer_checkboxF").value = ""
		}
		if (document.getElementById("answer_checkboxG")) {
			answer_checkbox[6].checked = false;
			document.getElementById("answer_checkboxG").value = ""
		}
		if (document.getElementById("answer_checkboxH")) {
			answer_checkbox[7].checked = false;
			document.getElementById("answer_checkboxH").value = ""
		}
	} else if (answerCount == 4) {
		$("#" + type + "1").removeAttr("style");
		$("#" + type + "2").removeAttr("style");
		$("#" + type + "3").removeAttr("style");
		$("#" + type + "4").removeAttr("style");
		$("#" + type + "5").css('display', 'none');
		$("#" + type + "6").css('display', 'none');
		$("#" + type + "7").css('display', 'none');
		$("#" + type + "8").css('display', 'none');
		if (document.getElementById("answer_radioE")) {
			answer_radio[4].checked = false;
			document.getElementById("answer_radioE").value = "";
		}
		if (document.getElementById("answer_radioF")) {
			answer_radio[5].checked = false;
			document.getElementById("answer_radioF").value = "";
		}
		if (document.getElementById("answer_radioG")) {
			answer_radio[6].checked = false;
			document.getElementById("answer_radioG").value = "";
		}
		if (document.getElementById("answer_radioH")) {
			answer_radio[7].checked = false;
			document.getElementById("answer_radioH").value = "";
		}

		if (document.getElementById("answer_checkboxE")) {
			answer_checkbox[4].checked = false;
			document.getElementById("answer_checkboxE").value = ""
		}
		if (document.getElementById("answer_checkboxF")) {
			answer_checkbox[5].checked = false;
			document.getElementById("answer_checkboxF").value = ""
		}
		if (document.getElementById("answer_checkboxG")) {
			answer_checkbox[6].checked = false;
			document.getElementById("answer_checkboxG").value = ""
		}
		if (document.getElementById("answer_checkboxH")) {
			answer_checkbox[7].checked = false;
			document.getElementById("answer_checkboxH").value = ""
		}
	} else if (answerCount == 5) {
		$("#" + type + "1").removeAttr("style");
		$("#" + type + "2").removeAttr("style");
		$("#" + type + "3").removeAttr("style");
		$("#" + type + "4").removeAttr("style");
		$("#" + type + "5").removeAttr("style");
		$("#" + type + "6").css('display', 'none');
		$("#" + type + "7").css('display', 'none');
		$("#" + type + "8").css('display', 'none');
		if (document.getElementById("answer_radioF")) {
			answer_radio[5].checked = false;
			document.getElementById("answer_radioF").value = "";
		}
		if (document.getElementById("answer_radioG")) {
			answer_radio[6].checked = false;
			document.getElementById("answer_radioG").value = "";
		}
		if (document.getElementById("answer_radioH")) {
			answer_radio[7].checked = false;
			document.getElementById("answer_radioH").value = "";
		}

		if (document.getElementById("answer_checkboxF")) {
			answer_checkbox[5].checked = false;
			document.getElementById("answer_checkboxF").value = ""
		}
		if (document.getElementById("answer_checkboxG")) {
			answer_checkbox[6].checked = false;
			document.getElementById("answer_checkboxG").value = ""
		}
		if (document.getElementById("answer_checkboxH")) {
			answer_checkbox[7].checked = false;
			document.getElementById("answer_checkboxH").value = ""
		}
	} else if (answerCount == 6) {
		$("#" + type + "1").removeAttr("style");
		$("#" + type + "2").removeAttr("style");
		$("#" + type + "3").removeAttr("style");
		$("#" + type + "4").removeAttr("style");
		$("#" + type + "5").removeAttr("style");
		$("#" + type + "6").removeAttr("style");
		$("#" + type + "7").css('display', 'none');
		$("#" + type + "8").css('display', 'none');
		if (document.getElementById("answer_radioG")) {
			answer_radio[6].checked = false;
			document.getElementById("answer_radioG").value = "";
		}
		if (document.getElementById("answer_radioH")) {
			answer_radio[7].checked = false;
			document.getElementById("answer_radioH").value = "";
		}

		if (document.getElementById("answer_checkboxG")) {
			answer_checkbox[6].checked = false;
			document.getElementById("answer_checkboxG").value = ""
		}
		if (document.getElementById("answer_checkboxH")) {
			answer_checkbox[7].checked = false;
			document.getElementById("answer_checkboxH").value = ""
		}
	} else if (answerCount == 7) {
		$("#" + type + "1").removeAttr("style");
		$("#" + type + "2").removeAttr("style");
		$("#" + type + "3").removeAttr("style");
		$("#" + type + "4").removeAttr("style");
		$("#" + type + "5").removeAttr("style");
		$("#" + type + "6").removeAttr("style");
		$("#" + type + "7").removeAttr("style");
		$("#" + type + "8").css('display', 'none');

		if (document.getElementById("answer_radioH")) {
			answer_radio[7].checked = false;
			document.getElementById("answer_radioH").value = "";
		}

		if (document.getElementById("answer_checkboxH")) {
			answer_checkbox[7].checked = false;
			document.getElementById("answer_checkboxH").value = ""
		}
	} else if (answerCount == 8) {
		$("#" + type + "1").removeAttr("style");
		$("#" + type + "2").removeAttr("style");
		$("#" + type + "3").removeAttr("style");
		$("#" + type + "4").removeAttr("style");
		$("#" + type + "5").removeAttr("style");
		$("#" + type + "6").removeAttr("style");
		$("#" + type + "7").removeAttr("style");
		$("#" + type + "8").removeAttr("style");
	} else if (answerCount > 8) {
		// alert("候选项数量不能超过10个！");
		$.messager.alert('友情提示', '候选项数量不能超过8个！', 'info');
		return false;
	} else if (answerCount < 4) {
		// alert("候选项数量不能小于3个！");
		$.messager.alert('友情提示', '候选项数量不能小于4个！', 'info');
		return false;
	}
	$("#answerCount").val(answerCount);
}
// 批量导入
function importQuestion() {
	$('#dlg_import_question').dialog('open');
	$('#questionForm').form('clear');

	$('#iptk').combotree({
		url : contextpath + '/questionSort/getQuestionSortJson',
		method : 'get'
	});
}
// 批量导出
function exportQuestion() {
	// 把你选中的 数据查询出来。
	var selectRows = $('#questionList_table').datagrid("getSelections");
	var t_info = "导出";
	var t_url = contextpath + "/question/exportQuestions";
	if (selectRows.length < 1) {
		$.messager.alert("提示", "请选中要" + t_info + "的数据信息！", "info");
		return false;
	}
	$.messager.confirm("确认", "您确定要" + t_info + "选中的信息吗？", function(r) {
		if (r) {
			var strIds = "";
			for (var i = 0; i < selectRows.length; i++) {
				strIds += selectRows[i].questionId + ",";
			}
			$("#questionIds").val(strIds);
			$('#export_questions').submit();
		}
	});
}
function downmb() {
	window.open("../static/question/xls/import_questions.xlsx");
}

function submitImport() {
	$('#importQuestion').form({
		url : contextpath + "/question/importQuestions",
		success : function(data) {
			var json = $.parseJSON(data);
			if (json['code'] == 200) {
				$('#dlg_import_question').dialog('close');
				doSearch();
				$.messager.alert('Info', json['message']);

			} else {
				// 失败
				$.messager.alert('Error', json['message']);
			}
		}
	});
	$('#importQuestion').submit();
}

// 点击取消 添加或编辑信息
function cancleImport() {
	$('#dlg_import_question').dialog('close');
}
