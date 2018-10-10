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

//默认展开一级部门
function zTreeOnSuccess(event, treeId, treeNode, msg) {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getNodesByParam("id", "0", null);
	if (nodes) {
		treeObj.expandNode(nodes[0], true, false, true);
	}
}
//左侧部门树 点击
function updateDataTabs(event, treeId, treeNode, clickFlag) {
	$("#userOnlineStatisticsListTable").datagrid('load', {
		userOrgCode : treeNode.orgCode
	});
}

$(function(){
	$.fn.zTree.init($("#treeDemo"), setting);
});

//##############################################工具栏start##############################################
//查询按钮点击
function doSearch() {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getSelectedNodes();
	var deptv = "";
	if (nodes != null && nodes.length > 0) {
		deptv = nodes[0].orgCode;
	}
	var vl = $('#username').val();
	var  lname= encodeURI(vl);
	$("#userOnlineStatisticsListTable").datagrid('load', {
		username : lname,
		userOrgCode:deptv
	});
}
//##############################################工具栏end################################################

//##############################################列表start################################################
function formatDateConversion(value,row,index){
	if(value != null){
		var unixTimestamp = new Date(value);
		return unixTimestamp.Format('yyyy-MM-dd hh:mm:ss');
	}
}
//##############################################列表end################################################