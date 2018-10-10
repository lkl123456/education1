//##############################################列表树start##############################################
var setting = {
	async : {
		enable : true,
		url : contextpath + "/userIndex/getOrgsJson",
		autoParam : [ "id=parentId" ],
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
	$.fn.zTree.init($("#orgTreeDemo"), setting);
});
// 左侧课程分类树 点击事件
function updateDataTabs(event, treeId, treeNode, clickFlag) {
	$("#ztree_orgId").val(treeNode.id);
	$("#advertiselistdatagrid").datagrid('load', {
		orgId : treeNode.id
	// 左侧分类id
	});
}
function zTreeOnSuccess(event, treeId, treeNode, msg) {
	var treeObj = $.fn.zTree.getZTreeObj("orgTreeDemo");
	var nodes = treeObj.getNodesByParam("id", "0", null);
	if (nodes) {
		treeObj.expandNode(nodes[0], true, false, true);
	}
}
//##############################################列表树end##############################################
function locationStatus(value,row,index){
	if(value=='1'){
		return '中上'
	}else if(value=='2'){
		return '中下'
	}else if(value=='3'){
		return '左侧竖联'
	}else if(value=='4'){
		return '右侧竖联'
	}else if(value=='5'){
		return '飘窗'
	}else if(value=='6'){
		return '左下角浮动'
	}
}
function fromartStatus(value,row,index){
	if(value=='1'){
		return '是'
	}else if(value=='2'){
		return '否'
	}
}
//##############################################工具栏start##############################################
// 查询按钮点击
function doSearch() {
	var categoryCode = $("#cc_ztree_node_code").val();
	if (typeof ($('#s_advertise_orgSnname').val()) == "undefined") {
		$("#advertiselistdatagrid").datagrid('load', {
			s_advertise_name : $('#s_advertise_name').val(),
		});
	} else {
		$("#advertiselistdatagrid").datagrid(
				'load',
				{
					s_advertise_name : $('#s_advertise_name').val(),
					s_advertise_orgSnname : $('#s_advertise_orgSnname')
							.combobox('getValue')
				});
	}
}

// 点击添加广告位
function add_advertise() {
	var treeObj = $.fn.zTree.getZTreeObj("orgTreeDemo");
	var nodes = treeObj.getSelectedNodes();
	if (nodes == null || nodes.length < 1) {
		$.messager.alert('提示', '请选中一个机构进行添加', 'warning');
		return;
	}else{
		$('#dlg_advertise').dialog('open');
		$('#advertiseForm').form('clear');
		$("input[name='status'][value=1]").attr("checked", true);
		var paths="../static/admin/images/qsimage.jpg";
		$("#aip").attr("src", paths);
		$("#orgId").val(nodes[0].id);
	}
}

// 点击编辑广告位
function edit_advertise() {
	var rows = $('#advertiselistdatagrid').datagrid('getSelections');
	if (rows.length != 1) {
		$.messager.alert('提示', '请选中一条记录编辑', 'warning');
		return;
	}
	$('#dlg_advertise').dialog('open').dialog('setTitle', '编辑广告');
	$("#aip").attr("src","");
	$('#advertiseForm').form('clear');
	$('#p').progressbar('setValue', 0);
	// 给表单赋值
	$('#advertiseForm').form('load', rows[0]);

	var ossPath = $("#imgServer").val();
	var paths = rows[0].adImgPath;
	if (paths!= null && paths !='undefined' && paths!= '') {
		paths = ossPath + paths;
	} else {
		// 默认图片
		paths="../static/admin/images/qsimage.jpg";
	}
	$("#aip").attr("src", paths);
}

// 点击删除广告位（flag：-1表示删除）
function del_advertise(flag) {
	// 把你选中的 数据查询出来。
	var selectRows = $('#advertiselistdatagrid').datagrid("getSelections");
	var t_info = "删除";
	var t_url = contextpath + "/advertise/deleteAdvertise";

	if (selectRows.length < 1) {
		$.messager.alert("提示", "请选中要" + t_info + "的数据信息！", "info");
		return;
	}

	$.messager.confirm("确认", "您确定要" + t_info + "选中的信息吗？", function(r) {
		if (r) {
			var strIds = "";
			var orgIds = "";
			for (var i = 0; i < selectRows.length; i++) {
				strIds += selectRows[i].adId + ",";
				orgIds += selectRows[i].orgId + ",";
			}
			strIds = strIds.substr(0, strIds.length - 1);
			orgIds = orgIds.substr(0, orgIds.length - 1);
			if (strIds.length > 0) {
				$
						.ajax({
							url : contextpath + "/advertise/deleteAdvertise",
							async : false,// 改为同步方式
							type : "get",
							data : {
								"adIds" : strIds,
								"orgIds" : orgIds
							},
							success : function(data) {
								// 刷新表格，去掉选中状态的 那些行。
								$('#advertiselistdatagrid').datagrid("reload");
								$('#advertiselistdatagrid').datagrid(
										"clearSelections");
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
//##############################################工具栏end##############################################

//##############################################子页面start##############################################
// 提交保存广告位信息
function submitAdd_advertise() {
	var adHref=$("#adHref").val();
	if (adHref != null && adHref != '' && trimStr(adHref) != '') {
		var strReg = '(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]';
		var reg = new RegExp(strReg);
		if (!reg.test(adHref)) {
			alert('广告链接格式有误');
			return;
		}
	}
	$('#advertiseForm').form('submit', {
		url : contextpath + '/advertise/saveAdvertise',
		onSubmit : function(param) {
			param.orgId=$("#ztree_orgId").val();
			return $(this).form('validate');
		},
		success : function(data) {
			doSearch();
			$('#dlg_advertise').dialog('close');
		}
	});
}

// 点击取消添加、编辑广告位
function cancleAdd_advertise() {
	$('#dlg_advertise').dialog('close');
}
function trimStr(str){ //删除左右两端的空格　　
   return str.replace(/(^s*)|(s*$)/g, "");
}
//##############################################子页面end##############################################