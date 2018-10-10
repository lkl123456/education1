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
	var nodes = treeObj.getNodesByParam("id", "0", null);
	if (nodes) {
		treeObj.expandNode(nodes[0], true, false, true);
	}
}
$(function() {
	$.fn.zTree.init($("#treeDemo"), setting);
});

function doSearch() {
	//var orgIds = $('#userOrgTree').combotree('getValues');
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getSelectedNodes();
	var deptv = "";
	if (nodes != null && nodes.length > 0) {
		deptv = nodes[0].orgCode;
	}
	var vl = $('#s_org_name').val();
	var  lname= encodeURI(vl);
	$("#cslistdatagrid").datagrid('load', {
		orgCode : deptv,
		userName:lname
	});
}


//左侧部门树 点击
function updateDataTabs(event, treeId, treeNode, clickFlag) {
	$("#s_org_name").textbox('setValue', "");
	$("#cslistdatagrid").datagrid('load', {
		orgCode : treeNode.orgCode
	// 左侧部门id
	});
}