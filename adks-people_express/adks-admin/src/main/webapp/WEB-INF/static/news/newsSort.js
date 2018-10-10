var setting = {
	async : {
		enable : true,
		url : contextpath + "/newsSort/getNewsTypeJson",
	// /autoParam : [ "id=parent" ],
	// otherParam : [ "orgId", 'orgId' ]
	},
	view : {
		dblClickExpand : false
	},
	callback : {
		onClick : updateDataTabs,
		onAsyncSuccess : zTreeOnSuccess
	}
};
var setting1 = {
	async : {
		enable : true,
		url : contextpath + "/userIndex/getOrgsJson",
		autoParam : [ "id=parentId" ],
		// otherParam : ["orgunameValue",orgunameValue],
		type : "get"
	},
	view : {
		dblClickExpand : false
	},
	callback : {
		onClick : changeValue,
		onAsyncSuccess : zTreeOnSuccess
	}
};
// 添加机构
function changeValue(event, treeId, treeNode, clickFlag) {
	if (treeNode.id != null && treeNode.id != '' && treeNode.id != '0') {
		$("#userOrgName").val(treeNode.name);
		$("#userOrgId").val(treeNode.id);
		$("#userOrgCode").val(treeNode.orgCode);
		$("#orgNameShow").val(treeNode.name);
		$("#treeDemo1").css("display", "none");
	}
}
function showOrgTree() {
	$("#treeDemo1").css("display", "");
}

$(function() {
	$.fn.zTree.init($("#newsTypeTreeDemo"), setting);
});
// 左侧课程分类树 点击事件
function updateDataTabs(event, treeId, treeNode, clickFlag) {
	$("#newsSortlistdatagrid").datagrid('load', {
		newsSortType : treeNode.id
	// 左侧分类id
	});
}
function zTreeOnSuccess(event, treeId, treeNode, msg) {
	var treeObj = $.fn.zTree.getZTreeObj("newsTypeTreeDemo");
	var nodes = treeObj.getNodesByParam("id", "15", null);
	if (nodes) {
		treeObj.expandNode(nodes[0], true, false, true);
	}
}

// 查询按钮点击
function doSearch() {
//	var treeObj = $.fn.zTree.getZTreeObj("newsTypeTreeDemo");
//	var nodes = treeObj.getSelectedNodes();
//	var newsSortType = '';
//	if (nodes != null && nodes.length >= 1) {
//		newsSortType = nodes.id;
//	}
	var s_newsSort_name = encodeURI($('#s_newsSort_name').val());
	$("#newsSortlistdatagrid").datagrid('load', {
		s_newsSort_name : s_newsSort_name,
	});
//	var categoryCode = $("#cc_ztree_node_code").val();
//	if (typeof ($('#s_newsSort_orgSnname').val()) == "undefined") {
//		$("#newsSortlistdatagrid").datagrid('load', {
//			s_newsSort_name : s_newsSort_name,
//			newsSortType : newsSortType
//		});
//	} else {
//		$("#newsSortlistdatagrid").datagrid(
//				'load',
//				{
//					s_newsSort_name : $('#s_newsSort_name').val(),
//					s_newsSort_orgSnname : $('#s_newsSort_orgSnname').combobox(
//							'getValue'),
//					newsSortType : newsSortType
//				});
//	}
}

// 点击添加新闻分类
function add_newsSort() {
//	$.fn.zTree.init($("#treeDemo1"), setting1);
//	$("#treeDemo1").css("display", "none");
	// 必须选择一个类型
//	var treeObj = $.fn.zTree.getZTreeObj("newsTypeTreeDemo");
//	var nodes = treeObj.getSelectedNodes();
//	if (nodes == null || nodes.length < 1) {
//		$.messager.alert('提示', '请选中一个新闻分类类型进行添加', 'warning');
//		return;
//	} else if (nodes[0].id == null) {
//		$.messager.alert('提示', '选择的新闻分类类型不能是全部！', 'warning');
//		return;
//	}
//	var newsSortTypeName = nodes[0].name;
//	var newsSortType = nodes[0].id;
	$('#dlg_newsSort').dialog('open');
	$('#newsSortForm').form('clear');
	$('#p').progressbar('setValue', 0);
//	$('#t_newsSort_newsSortId').val($("#cc_ztree_node_id").val());
//	$('#cc2').combotree({
//		url : contextpath + "/userIndex/getOrgsJson",
//		method : 'get',
//		onLoadSuccess : function(node, data) {
//			var node = $('#cc2').combotree("tree").tree('getRoot');
//			$('#cc2').combotree("setValue", node.id);
//		}
//	});
//	$("#newsSortType").val(newsSortType);
//	$("#newsSortTypeName").val(newsSortTypeName);
	/*
	 * $('#ss1').combobox({
	 * url:contextpath+"/userIndex/getUserNationalityJson?name=newsSortType",
	 * valueField:'id', textField:'text', onLoadSuccess:function(){
	 * $('#ss1').combobox('select','15');//菜单项可以text或者id } });
	 */
}

// 点击编辑新闻
function edit_newsSort() {
//	$.fn.zTree.init($("#treeDemo1"), setting1);
//	$("#treeDemo1").css("display", "none");
	var rows = $('#newsSortlistdatagrid').datagrid('getSelections');
	if (rows.length != 1) {
		$.messager.alert('提示', '请选中一条记录编辑', 'warning');
		return;
	}

	$('#dlg_newsSort').dialog('open').dialog('setTitle', '编辑新闻分类');
	$("#orgNameShow").val(rows[0].orgName);
	/*
	 * $('#ss1').combobox({
	 * url:contextpath+"/userIndex/getUserNationalityJson?name=newsSortType",
	 * valueField:'id', textField:'text', onLoadSuccess:function(){
	 * $('#ss1').combobox('select',rows[0].newsSortType); } });
	 */
	// 给表单赋值
	$('#newsSortForm').form('load', rows[0]);
//	var newsSortType = rows[0].newsSortType;
//	var newsSortTypeName = '';
//	if (newsSortType == '15') {
//		newsSortTypeName = '图片新闻';
//	} else if (newsSortType == '16') {
//		newsSortTypeName = '通知公告';
//	} else if (newsSortType == '17') {
//		newsSortTypeName = '培训动态';
//	} else if (newsSortType == '18') {
//		newsSortTypeName = '新闻资讯';
//	}
//	$("#newsSortTypeName").val(newsSortTypeName);
}

// 点击删新闻分类（flag：-1表示删除）
function del_newsSort(flag) {
	// 把你选中的 数据查询出来。
	var selectRows = $('#newsSortlistdatagrid').datagrid("getSelections");
	var t_info = "删除";
	var t_url = contextpath + "/newsSort/deleteNewsSort";

	if (selectRows.length < 1) {
		$.messager.alert("提示", "请选中要" + t_info + "的数据信息！", "info");
		return;
	}

	$.messager.confirm("确认", "您确定要" + t_info + "选中的信息吗？", function(r) {
		if (r) {
			var strIds = "";
//			var orgIds = "";
			for (var i = 0; i < selectRows.length; i++) {
				strIds += selectRows[i].newsSortId + ",";
//				orgIds += selectRows[i].orgId + ",";
			}
			strIds = strIds.substr(0, strIds.length - 1);
//			orgIds = orgIds.substr(0, orgIds.length - 1);
			if (strIds.length > 0) {
				$.ajax({
					url : contextpath + "/newsSort/deleteNewsSort",
					async : false,// 改为同步方式
					type : "get",
					data : {
						"newsSortIds" : strIds,
					},
					success : function(data) {
						// 刷新表格，去掉选中状态的 那些行。
						$('#newsSortlistdatagrid').datagrid("reload");
						$('#newsSortlistdatagrid').datagrid("clearSelections");
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

// 提交保存新闻分类信息
function submitAdd_newsSort() {
	var newsSortName = $("#newsSortName").val();
	if (newsSortName == null || newsSortName == '') {
		$.messager.alert('提示', '新闻分类名称不能为空！', 'warning');
		return;
	}
	/*
	 * var t = $('#cc2').combotree('tree'); // 获取树对象 var n =
	 * t.tree('getSelected'); // 获取选择的节点 if (n.text == '' || n.text == '机构') {
	 * $.messager.alert('提示', '请选中一个机构', 'warning'); return; }
	 */
//	var orgId = $("#userOrgId").val();
//	if (orgId == '' || orgId == '0') {
//		$.messager.alert('提示', '请选中一个机构', 'warning');
//		return;
//	}
	$('#newsSortForm').form('submit', {
		url : contextpath + '/newsSort/saveNewsSort',
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(data) {
			doSearch();
			$('#dlg_newsSort').dialog('close');
		}
	});
}

// 点击取消添加、编辑新闻分类
function cancleAdd_newsSort() {
	$('#dlg_newsSort').dialog('close');
}

function newsSortTypeData(value, row, index) {
	if (value == '15') {
		return '图片新闻类';
	} else if (value == '16') {
		return '通知公告类';
	} else if (value == '17') {
		return '动态新闻类';
	} else if (value == '18') {
		return '资讯新闻类';
	}
}
