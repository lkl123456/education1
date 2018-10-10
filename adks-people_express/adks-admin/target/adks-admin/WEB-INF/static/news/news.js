var setting = {
	async : {
		enable : true,
		url : contextpath + "/newsSort/getNewsSortList",
	// autoParam : [ "id=parent" ],
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

$(function() {
	$.fn.zTree.init($("#newsSortTreeDemo"), setting);
});
// 左侧课程分类树 点击事件
function updateDataTabs(event, treeId, treeNode, clickFlag) {
	$('#s_news_name').textbox("setValue", '');
	var orgId = treeNode.orgId;
	var orgCode = treeNode.orgCode;
	var newsSortId = '';
	if (orgId != null && orgId != '') {
		$("#cc_ztree_node_id").val(treeNode.id);
		$("#cc_ztree_node_name").val(treeNode.name);
		//$("#cc_ztree_node_code").val(treeNode.orgCode);
		newsSortId = treeNode.id;
		$("#newslistdatagrid").datagrid('load', {
			newsSortId : newsSortId,
		// 左侧课程分类id
		});
	}
	if (name == '全部' && (orgId == null || orgId == '')) {
		$("#cc_ztree_node_id").val('');
		$("#cc_ztree_node_name").val('');
		//$("#cc_ztree_node_code").val('');
		newsSortId = treeNode.id;
		$("#newslistdatagrid").datagrid('load', {
			newsSortId : null,
		// 左侧课程分类id
		});
	} else if (name != '全部' && (orgId == null || orgId == '')) {
		$("#cc_ztree_node_id").val('');
		$("#cc_ztree_node_name").val('');
		//$("#cc_ztree_node_code").val('');
		newsSortId = treeNode.id;
		$("#newslistdatagrid").datagrid('load', {
			newsSortId : newsSortId
		// 左侧课程分类id
		});
	}
}
function zTreeOnSuccess(event, treeId, treeNode, msg) {
	var treeObj = $.fn.zTree.getZTreeObj("newsSortTreeDemo");
	var nodes = treeObj.getNodesByParam("id", "0", null);
	if (nodes) {
		treeObj.expandNode(nodes[0], true, false, true);
	}
}
// ===========================上面是树==============================

// 查询按钮点击
function doSearch() {
	var treeObj = $.fn.zTree.getZTreeObj("newsSortTreeDemo");
	var nodes = treeObj.getSelectedNodes();
	var newsSortId = '';
	if (nodes != null && nodes.length >= 1) {
		var orgId = nodes[0].orgId;
		if (orgId != null && orgId != '') {
			newsSortId = nodes[0].id;
		}
	}
	var s_news_name = encodeURI($('#s_news_name').val());
	if (typeof ($('#s_news_orgSnname').val()) == "undefined") {
		$("#newslistdatagrid").datagrid('load', {
			s_news_name : s_news_name,
			newsSortId : newsSortId,
		});
	} else {
		$("#newslistdatagrid").datagrid('load', {
			s_news_name : $('#s_news_name').val(),
			s_news_orgSnname : $('#s_news_orgSnname').combobox('getValue'),
			newsSortId : newsSortId,
		});
	}
}

// 点击添加新闻
function add_news() {
	var treeObj = $.fn.zTree.getZTreeObj("newsSortTreeDemo");
	var nodes = treeObj.getSelectedNodes();
	if (nodes == null || nodes.length < 1) {
		$.messager.alert('提示', '请选中一个分类进行添加', 'warning');
		return;
	} else {
		var newsSortId = nodes[0].id;
//		var newsSortType = nodes[0].newsSortType;
//		var name = nodes[0].name;
		if (newsSortId == null) {
			$.messager.alert('提示', '请选中一个分类进行添加', 'warning');
			return;
		} else {
			var url = contextpath + '/news/toAddNews?newsSortId=' + newsSortId;
			parent.addTab_update('添加新闻', url, 'icon-save');
		}
	}

}

// 点击编辑新闻
function edit_news() {
	var rows = $('#newslistdatagrid').datagrid('getSelections');
	if (rows == null || rows.length <= 0) {
		$.messager.alert('提示', '请选中一条记录编辑', 'warning');
		return;
	}
	var newsId = rows[0].newsId;
	var url = contextpath + '/news/toAddNews?newsId=' + newsId;
	parent.addTab_update('编辑新闻', url, 'icon-save');
}

// 点击删新闻（flag：-1表示删除）
function del_news(flag) {
	// 把你选中的 数据查询出来。
	var selectRows = $('#newslistdatagrid').datagrid("getSelections");
	var t_info = "删除";
	if (selectRows.length < 1) {
		$.messager.alert("提示", "请选中要" + t_info + "的数据信息！", "info");
		return;
	}
	$.messager.confirm("确认", "您确定要" + t_info + "选中的信息吗？", function(r) {
		if (r) {
			var strIds = "";
			var orgId_types = "";
			for (var i = 0; i < selectRows.length; i++) {
				strIds += selectRows[i].newsId + ",";
				var orgId = selectRows[i].orgId;
				var nst = selectRows[i].newsSortType;
				var orgId_type = orgId + "_" + nst;
				orgId_types += orgId_type + ",";
			}
			strIds = strIds.substr(0, strIds.length - 1);
			orgId_types = orgId_types.substr(0, orgId_types.length - 1);

			if (strIds.length > 0) {
				$.ajax({
					url : contextpath + "/news/deleteNews",
					async : false,// 改为同步方式
					type : "get",
					data : {
						"newsIds" : strIds,
						"orgId_types" : orgId_types
					},
					success : function(data) {
						// 刷新表格，去掉选中状态的 那些行。
						$('#newslistdatagrid').datagrid("reload");
						$('#newslistdatagrid').datagrid("clearSelections");
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

// 上传新闻焦点图图片
function showImg() {
	var imgPath = $('#newsFocusPicFile').val();
	$('#newsFocusPic').attr('src', imgPath);
}

function changeNewsType(newsType) {
	if (newsType == 1) {
		$("#lianjie").hide();
		$("#neirong").show();
	} else {
		$("#lianjie").show();
		$("#neirong").hide();
	}
}
