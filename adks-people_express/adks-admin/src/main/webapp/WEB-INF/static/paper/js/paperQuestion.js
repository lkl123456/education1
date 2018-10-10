function getQuestions(type) {
	if (type == 'random') {
		var win = window
				.open(
						contextpath + '/paperQuestion/paperRandomQuestion',
						'JavaScript',
						'height=500, width=1200, top=50, left=150, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');
	} else {
		var win = window
				.open(
						contextpath + '/paperQuestion/paperCustomQuestion',
						'JavaScript',
						'height=500, width=1200, top=50, left=150, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');
	}
}
$(function() {
	$.fn.zTree.init($("#questionSortTreeDemo"), setting);
	$('#questionConfigureList_table')
			.datagrid(
					{
						iconCls : 'icon-edit',
						idField : 'paperQsId',
						url : contextpath
								+ '/paperQuestion/getPaperQuestionListJson?paperId='
								+ $("#paperId").val(),
						method : 'post',
						singleSelect : false,
						fit : true,
						fitColumns : true,
						striped : true,
						nowarp : false,
						pagination : true,
						pageSize : 10,
						pageList : [ 5, 10, 15 ],
						rownumbers : true,
						columns : [ [
								{
									field : 'paperQsId',
									checkbox : true
								},
								{
									field : 'questionName',
									title : '题目',
									align : 'center',
									width : 200
								},
								{
									field : 'courseName',
									title : '课程名称',
									align : 'center',
									width : 80
								},
								{
									field : 'questionType',
									title : '题型',
									align : 'center',
									width : 80,
									formatter : formatStatus
								},
								{
									field : 'score',
									title : '分值',
									width : 80,
									align : 'center',
									editor : {
										type : 'numberbox'
									}
								},
								{
									field : 'action',
									title : '操作',
									width : 70,
									align : 'center',
									formatter : function(value, row, index) {
										if (row.editing) {
											var s = '<a href="#" onclick="saverow(this)"><font color=blue>保存</font></a> ';
											var c = '<a href="#" onclick="cancelrow(this)"><font color=blue>取消</font></a>';
											return s + c;
										} else {
											var e = '<a href="#" onclick="editrow(this)"><font color=blue>修改</font></a> ';
											return e;
										}
									}
								} ] ],
						onBeforeEdit : function(index, row) {
							row.editing = true;
							updateActions(index);
						},
						onAfterEdit : function(index, row) {
							row.editing = false;
							updateActions(index);
						},
						onCancelEdit : function(index, row) {
							row.editing = false;
							updateActions(index);
						}
					});
});

function updateActions(index) {
	$('#questionConfigureList_table').datagrid('updateRow', {
		index : index,
		row : {}
	});
}
function getRowIndex(target) {
	var tr = $(target).closest('tr.datagrid-row');
	return parseInt(tr.attr('datagrid-row-index'));
}
function editrow(target) {
	$('#questionConfigureList_table')
			.datagrid('beginEdit', getRowIndex(target));
}
function saverow(target) {
	var rows = $('#questionConfigureList_table').datagrid('getRows');
	var row = rows[getRowIndex(target)];
	$('#questionConfigureList_table').datagrid('endEdit', getRowIndex(target));
	var t_url = contextpath + "/paperQuestion/savePaperQuestion";
	$.post(t_url, {
		paperQsId : row.paperQsId,
		score : row.score
	}, function(data) {
		if (data == "succ") {
			// 刷新表格，去掉选中状态的 那些行。
			var url = contextpath + "/paper/toConfigurePaper?paperId="
					+ $("#paperId").val();
			parent.addTab_update('组卷出题', url, 'icon-save');
		} else {
			$.messager.alert("操作失败~~", data);
		}
	});
}
function cancelrow(target) {
	$('#questionConfigureList_table').datagrid('cancelEdit',
			getRowIndex(target));
}
// 格式化列数据
function formatInputNum(value, row, index) {
	var num = 'num' + index;
	return "<input type='text' id=" + num + ">"
}
function formatInputScore(value, row, index) {
	var score = 'score' + index;
	return "<input type='text' id=" + score + " maxlength='3'>"
}

var setting = {
	async : {
		enable : true,
		url : contextpath + "/questionSort/getQuestionSortJson",
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	view : {
		dblClickExpand : false
	},
	callback : {
		onClick : updateDataTabs,
		onAsyncSuccess : zTreeOnSuccess
	}
};
// 左侧课程分类树 点击事件
function updateDataTabs(event, treeId, treeNode, clickFlag) {
	$("#cc_ztree_node_id").val(treeNode.id);
	$("#cc_ztree_node_name").val(treeNode.name);
	$("#cc_ztree_node_code").val(treeNode.code);
	$("#questionList_table").datagrid('load', {
		s_qtSortId : treeNode.id
	// 左侧课程分类id
	});
}
function zTreeOnSuccess(event, treeId, treeNode, msg) {
	var treeObj = $.fn.zTree.getZTreeObj("questionSortTreeDemo");
	var nodes = treeObj.getNodesByParam("id", "0", null);
	if (nodes) {
		treeObj.expandNode(nodes[0], true, false, true);
	}
}
function del_paperQuestion() {
	// 把你选中的 数据查询出来。
	var selectRows = $('#questionConfigureList_table')
			.datagrid("getSelections");
	var t_info = "删除";
	var t_url = contextpath + "/paperQuestion/delPaperQuestion";
	if (selectRows.length < 1) {
		$.messager.alert("提示", "请选中要" + t_info + "的数据信息！", "info");
		return;
	}

	$.messager.confirm("确认", "您确定要" + t_info + "选中的信息吗？", function(r) {
		if (r) {
			var strIds = "";
			for (var i = 0; i < selectRows.length; i++) {
				strIds += selectRows[i].paperQsId + ",";
			}
			strIds = strIds.substr(0, strIds.length - 1);
			$.post(t_url, {
				paperQsId : strIds
			}, function(data) {
				if (data == "succ") {
					// 刷新表格，去掉选中状态的 那些行。
					// doSearch();
					var url = contextpath + "/paper/toConfigurePaper?paperId="
							+ $("#paperId").val();
					parent.addTab_update('组卷出题', url, 'icon-save');
				} else {
					$.messager.alert("操作失败~~", data);
				}
			});
		}
	});
}
// 格式化列数据
function formatStatus(value, row, index) {
	var questionType = 'questionType' + index;
	var result = "<input type='hidden' value='" + value + "' id="
			+ questionType + ">";
	if (value == 1) {
		return '单选' + result;
	} else if (value == 2) {
		return '多选' + result;
	} else if (value == 3) {
		return '判断' + result;
	} else if (value == 4) {
		return '填空' + result;
	} else if (value == 5) {
		return '问答' + result;
	} else if (value == null) {
		return 0;
	}

}
// 点击查询按钮
function doSearch() {
	var questionName = encodeURI($('#s_questionName').val());
	var courseName = encodeURI($('#s_courseName').val());
	var questionValue_start = $('#s_questionValue_start').val();
	var questionValue_end = $('#s_questionValue_end').val();
	var questionType = $('#s_questionType').combobox('getValue');
	$("#questionList_table").datagrid('load', {
		s_questionName : questionName,
		s_courseName : courseName,
		s_questionValue_start : questionValue_start,
		s_questionValue_end : questionValue_end,
		s_questionType : questionType
	});
}
// 手动添加试题
function saveCustomQuestion(flag) {
	var paperId = window.parent.opener.document.getElementById("paperId").value;
	// 把你选中的 数据查询出来。
	var selectRows = $('#paperCustomList_table').datagrid("getSelections");
	var t_info = "添加";
	var t_url = contextpath + "/paperQuestion/saveCustomQuestion";
	if (selectRows.length < 1) {
		$.messager.alert("提示", "请选中要" + t_info + "的数据信息！", "info");
		return false;
	}

	$.messager.confirm("确认", "您确定要" + t_info + "选中的信息吗？", function(r) {
		if (r) {
			var strIds = "";
			var scores = "";
			var questionTypes = "";
			for (var i = 0; i < selectRows.length; i++) {
				strIds += selectRows[i].questionId + ",";
				questionTypes += selectRows[i].questionType + ",";

				scores += selectRows[i].questionValue + ",";
			}
			strIds = strIds.substr(0, strIds.length - 1);
			scores = scores.substr(0, scores.length - 1);
			questionTypes = questionTypes.substr(0, questionTypes.length - 1);
			$.post(t_url, {
				questionId : strIds,
				paperId : paperId,
				questionValue : scores,
				questionType : questionTypes
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
	});
}

function saveRandomQuestion() {
	var paperId = window.parent.opener.document.getElementById("paperId").value;
	var data = $('#questionList_table').datagrid('getData');
	// alert('当前页数据量:'+data.rows.length)
	// if(parseInt($j("#td_danxuan_easy_count").text()) <
	// parseInt($j("#danxuan_easy_count").val())){
	// $.messager.alert('友情提示', '出题数量不能超过已有试题数量！', 'info');
	// return false;
	// }
	var nums = "";
	var scores = "";
	var questionTypes = "";
	for (var i = 0; i < data.rows.length; i++) {
		nums += $("#num" + i).val() + ",";
		scores += $("#score" + i).val() + ",";
		questionTypes += $("#questionType" + i).val() + ",";
	}
	nums = nums.substr(0, nums.length - 1);
	scores = scores.substr(0, scores.length - 1);
	questionTypes = questionTypes.substr(0, questionTypes.length - 1);
	var t_url = contextpath + "/paperQuestion/saveRandomQuestion";
	$.post(t_url, {
		nums : nums,
		paperId : paperId,
		questionValue : scores,
		questionType : questionTypes
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
function save_paperQuestion() {
	var paperId = $("#paperId").val();
	var t_url = contextpath + "/paperQuestion/savePaperHtml";
	$.post(t_url, {
		paperId : paperId
	}, function(data) {
		if (data == "succ") {
			alert("试卷生成成功！");
			parent.$('#tabs').tabs('close', '组卷出题');
			$('#paperList_table').datagrid('reload');
		} else {
			alert("试卷生成失败！");
			return false;
		}
	});
}
