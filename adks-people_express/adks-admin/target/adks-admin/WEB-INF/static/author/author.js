var setting = {
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
		onClick : updateDataTabs,
		onAsyncSuccess : zTreeOnSuccess
	}
};
// 默认展开一级部门
function zTreeOnSuccess(event, treeId, treeNode, msg) {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getNodesByParam("id", "1", null);
	if (nodes) {
		treeObj.expandNode(nodes[0], true, false, true);
	}
}
$(function() {
	$.fn.zTree.init($("#treeDemo"), setting);
	$("#hh").form({
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(data) {
			if (data == 'succ') {
				doSearch();
				cancleAdd();
			} else if (data == 'snnameExists') {
				$.messager.alert("提示", "唯一标识已存在，请重新输入!");
			}
		}
	});
});

// 左侧部门树 点击
function updateDataTabs(event, treeId, treeNode, clickFlag) {
	$("#s_author_name").textbox('setValue', "");
	$("#authorlistdatagrid").datagrid('load', {
		orgCode : treeNode.orgCode
	// 左侧部门id
	});
}

// 查询按钮点击
function doSearch() {
	var s_author_name = encodeURI($('#s_author_name').val());
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getSelectedNodes();
	var orgCode = "";
	if (nodes != null && nodes.length > 0) {
		orgCode = nodes[0].orgCode;
	}

	$("#authorlistdatagrid").datagrid('load', {
		s_author_name : s_author_name,
		orgCode : orgCode
	});
}

// 点击添加讲师
function addDialog() {
	var orgId = "";
	var orgCode = "";
	var orgName = "";
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getSelectedNodes();

	if (nodes != null && nodes.length > 0) {
		orgId = nodes[0].id;
		orgName = nodes[0].name;
		orgCode = nodes[0].orgCode;
	} else {
		alert("请选择一个机构进行添加讲师");
		return;
	}
	$('#dlg_author').dialog('open').dialog('setTitle', '新增讲师');
	$('#dlg_author').dialog('open');
	$('#hh').form('clear');
	$("input[name='authorSex'][value=1]").attr("checked", true);
	$("#orgId").val(orgId);
	$("#orgName").val(orgName);
	$("#orgCode").val(orgCode);
	$("#orgNameShow").val(orgName);
}
// 点击取消
function cancleAdd(tid) {
	$('#dlg_author').dialog('close');
}

// 点击编辑按钮
function editAuthor() {
	var row = $('#authorlistdatagrid').datagrid('getSelections');
	if (row.length != 1) {
		$.messager.alert('提示', '请选中一条记录编辑', 'warning');
		return;
	}
	$('#dlg_author').dialog('open').dialog('setTitle', '编辑讲师');
	$('#hh').form('clear');
	// 给表单赋值
	$('#hh').form('load', row[0]);
	$("#orgNameShow").val($("#orgName").val());

	var ossPath = $("#ossPath").val();
	var apPath = $("#authorPhoto").val();
	$("#apPath").attr("src",'');
	if (apPath != null && apPath != 'undefined' && apPath != '') {
		apPath = ossPath + apPath;
	} else {
		// 默认图片
		apPath="../static/admin/images/qsimage.jpg";
	}
	$("#apPath").attr("src",apPath);
}
// 点击删除
function del_author() {
	var selectRows = $('#authorlistdatagrid').datagrid('getSelections');
	var t_info = "删除";
	var t_url = contextpath + "/author/delAuthor";
	if (selectRows.length < 1) {
		$.messager.alert('提示', '请至少选中一条记录删除', 'warning');
		return;
	}
	$.messager.confirm('确认', '确认要删除这批数据吗?', function(r) {
		if (r) {
			var strIds = "";
			for (var i = 0; i < selectRows.length; i++) {
				strIds += selectRows[i].authorId + ",";
			}
			strIds = strIds.substr(0, strIds.length - 1);
			$.post(t_url, {
				ids : strIds
			}, function(data) {
				if (data == "succ") {
					// 刷新表格，去掉选中状态的 那些行。
					$('#authorlistdatagrid').datagrid("reload");
					$('#authorlistdatagrid').datagrid("clearSelections");
				} else if (data == "hascourse") {
					$.messager.alert("删除失败，该讲师已关联了课程", data);
				} else {
					$.messager.alert("操作失败~~", data);
				}
			});
		}
	});
}
// 上传用户图像
function uploadAuthorCover() {
	var imgServer = $('#imgServer').val();
	/*
	 * $('#courseForm').form('submit',{
	 * url:contextpath+'/course/updateCourseCover', onSubmit: function(){ return
	 * true; }, success:function(data){ //alert(data);
	 * $('#cover_path').val(data);
	 * $('#img_coverpath').attr('src',imgServer+data); } });
	 */
}

// 提交保存
function submitAdd(tid) {
	// 设置ajax form
	/*var authorName = $("#authorName").val();
	if (authorName == null || authorName == '' || trimStr(authorName) == '') {
		alert("讲师名称不能为空");
		return;
	} else if (authorName.length >= 32) {
		alert("讲师名称不能超过32个字符");
		return;
	}*/
	var authorDes=$("#authorDes").val();
	if(authorDes != null && authorDes != '' && trimStr(authorDes) != ''){
		if(authorDes.length>500){
			alert("讲师简介不能超过500个字符");
			return;
		}
	}
	$('#hh').submit();
}

function formatColumnData(value, row, index) {
	if (value == 1) {
		return '男';
	} else {
		return '女';
	}
}

function setRowStyle(index, row) {
	if (row.status == 2) {
		return 'background-color:#cccccc;font-weight:bold;';
	}
}

function trimStr(str){ //删除左右两端的空格　　
   return str.replace(/(^s*)|(s*$)/g, "");
}