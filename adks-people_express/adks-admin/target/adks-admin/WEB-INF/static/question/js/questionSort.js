var setting = {
	async : {
		enable : true,
		url : contextpath + "/questionSort/getQuestionSortJson",
	// autoParam : [ "id=parent" ],
	// otherParam : [ "orgId", 'orgId' ]
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
$(function() {
	$.fn.zTree.init($("#questionSortTreeDemo"), setting);
	// 设置ajax form
	$('#questionSortForm').form({
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(data) {
			if (data == 'succ') {
				reloadTree();
				$('#questionSortForm').form('clear');
				$('#dlg_questionSort').dialog('close');

			}
		}
	});
});

// 点击添加题库分类
function add_questionSort() {
	var treeObj = $.fn.zTree.getZTreeObj("questionSortTreeDemo");
	var nodes = treeObj.getSelectedNodes();
	for (var i = 0; i < nodes.length; i++) {
		$('#dlg_questionSort').dialog('open');
		$("#id_parentQsSortName").val(nodes[i].name);
		$("#id_parentQsSortID").val(nodes[i].id);
	}
	if (nodes.length <= 0) {
		$.messager.alert("提示", "请选择试题题节点！", "info");
	}
}

// 点击取消添加题库分类
function cancleAdd_questionSort() {
	$('#dlg_questionSort').dialog('close');
}

// 提交保存题库分类信息
function submitAdd_questionSort() {
	var qsSortName = $('#qsSortName').val();
	if (null == qsSortName || trim(qsSortName) == "") {
		alert("题库名称不能为空");
		return false;
	}
	if (qsSortName.length > 50) {
		alert("题库名称不能过长");
		return false;
	}
	$('#questionSortForm').submit();
}
// 刷新树结构
function reloadTree() {
	// alert('刷新，重新加载树！');
	var treeObj = $.fn.zTree.getZTreeObj("questionSortTreeDemo");
	treeObj.reAsyncChildNodes(null, "refresh");
}

// 点击删除题库分类
function del_questionSort() {
	var treeObj = $.fn.zTree.getZTreeObj("questionSortTreeDemo");
	var nodes = treeObj.getSelectedNodes();
	if (nodes.length <= 0) {
		$.messager.alert("提示", "请选择试题题节点！", "info");
	}
	if (nodes[0].id == 0) {
		$.messager.alert("提示", "不能删除题库顶级分类信息！", "info");
		return;
	}

	$.messager.confirm("确认", "您确定要删除选中的分类信息吗？", function(r) {
		if (r) {
			$.post(contextpath + "/questionSort/delQuestionSort", {
				qtSortID : nodes[0].id
			}, function(data) {
				if (data == "succ") {
					// 课程分类树
					doSearch();
					reloadTree();
				} else {
					$.messager.alert("删除失败~~", data);
				}
			});
		}
	});
}
// 点击删除题库分类
function edit_questionSort() {
	var treeObj = $.fn.zTree.getZTreeObj("questionSortTreeDemo");
	var nodes = treeObj.getSelectedNodes();
	v = "";
	for (var i = 0; i < nodes.length; i++) {
		$('#questionSortForm').form('clear');
		// 给表单赋值
		$('#questionSortForm').form(
				'load',
				contextpath + '/questionSort/toEditQuestionSort?qtSortID='
						+ nodes[i].id);
		$('#dlg_questionSort').dialog('open');
	}
}
