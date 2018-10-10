//$(function() {
//	$('#questionForm').form({
//		onSubmit: function(){
//			return $(this).form('validate');
//		},
//		success:function(data){
//			if(data == 'succ'){
//				//$.messager.alert("提示","信息保存成功!");  
//				close_div();
//				doSearch();
//			}
//		}
//	});
//	
//});

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
// 点击查询按钮
function doSearch() {
	var paperName = encodeURI($('#s_paperName').val());
	$("#paperList_table").datagrid('load', {
		s_paperName : paperName
	});
}
// 点击添加试卷
function add_paper() {
	$('#dlg_paper').dialog('open').dialog('setTitle', '新增试卷');
	$('#paperForm').form('clear');
}

// 点击编辑试卷(加载显示相关信息)
function edit_paper() {
	var selectRows = $('#paperList_table').datagrid("getSelections");
	if (selectRows.length < 1 || selectRows.length > 1) {
		$.messager.alert("提示", "请您选中一条需要编辑的信息！", "info");
		return;
	}
	var paperId = selectRows[0].paperId;
	var t_url = contextpath + "/paper/isUpdate";
	$.post(t_url, {
		paperId : paperId
	}, function(data) {
		if (data == "false") {
			alert("该试卷正在考试，不允许修改");
			return false;
		} else {
			$('#dlg_paper').dialog({
				title : "编辑试卷"
			});
			$('#paperForm').form('clear');
			$('#dlg_paper').dialog('open');
			// 给表单赋值
			$('#paperForm').form('load', selectRows[0]);
		}
	});
}

// 点击取消 添加或编辑信息
function close_div() {
	$('#dlg_paper').dialog('close');
}

// 点击保存 添加或编辑信息
function submitForm() {
	var paperName = $('#paperName').val();

	$('#paperForm').form(
			'submit',
			{
				url : contextpath + '/paper/savePaper',
				onSubmit : function() {
					return $(this).form('validate');
				},
				success : function(data) {
					var datajson = $.parseJSON(data);
					if (datajson['msg'] == 'succ') {
						$('#dlg_paper').dialog('close');
						$('#paperList_table').datagrid('reload');
						var url = contextpath
								+ "/paper/toConfigurePaper?paperId="
								+ datajson['paperId'];

						parent.addTab_update('组卷出题', url, 'icon-save');
					} else if (datajson['mesg'] == 'samePaperName') {
						$.messager.alert('友情提示', '试卷名称不能重复!', 'info');
					} else {
						$("#dlg_paper").val(strIds);
						$('#paperList_table').submit();
					}
				},
				error : function() {
					alert('error');
				}
			});
}

// 点击删除（flag: -1表示删除，1表示开通，2表示禁用）

function del_paper(flag) {
	// 把你选中的 数据查询出来。
	var selectRows = $('#paperList_table').datagrid("getSelections");
	var t_info = "删除";
	var t_url = contextpath + "/paper/delPaper";
	if (selectRows.length < 1) {
		$.messager.alert("提示", "请选中要" + t_info + "的数据信息！", "info");
		return;
	}

	$.messager.confirm("确认", "您确定要" + t_info + "选中的信息吗？", function(r) {
		if (r) {
			var strIds = "";
			for (var i = 0; i < selectRows.length; i++) {
				strIds += selectRows[i].paperId + ",";
			}
			strIds = strIds.substr(0, strIds.length - 1);
			$.post(t_url, {
				paperId : strIds
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

// 预览试卷
function view_paper() {
	// 把你选中的 数据查询出来。
	var selectRows = $('#paperList_table').datagrid("getSelections");
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
