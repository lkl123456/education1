//##############################################班级列表树start##############################################
var setting = {
	async : {
		enable : true,
		url : contextpath+"/gradeWork/getGradesJson",
//		autoParam : [ "id=parent" ],
//		otherParam : [ "orgId", 'orgId' ],
		type : "get"
	},
	view : {
		dblClickExpand : false
	},
	callback : {
		onClick : updateDataTabs,
		onAsyncSuccess : zTreeOnSuccess,
	}
};
$(function(){
	$.fn.zTree.init($("#gradeTree"), setting);
});
//刷新树结构
function reloadTree(){
//	alert('刷新，重新加载树');
	var treeObj = $.fn.zTree.getZTreeObj("gradeTree");
	treeObj.reAsyncChildNodes(null, "refresh");
}
//默认展开
function zTreeOnSuccess(event, treeId, treeNode, msg) {
	var treeObj = $.fn.zTree.getZTreeObj("gradeTree");
	var nodes = treeObj.getNodesByParam("id", "0", null);
	if(nodes){
		treeObj.expandNode(nodes[0], true, false, true);
	}
}
//左侧班级树 点击事件
function updateDataTabs(event, treeId, treeNode, clickFlag) {
	$("#ztree_gradeId").val(treeNode.id);
	$("#ztree_gradeName").val(treeNode.name);
//	$("#cc_ztree_node_code").val(treeNode.text);
	$("#gradeNoticeListTable").datagrid('load', {
		gradeId : treeNode.id // 左侧班级gradeId
	});
}
//##############################################班级列表树end##############################################

//##############################################工具栏start###############################################
// 查询按钮点击
function doSearch() {
	var depts = $('#userDeptTree').combotree('getValues');
	var name = $('#userName').val();
	$("#gradeNoticeListTable").datagrid('load', {userName:name,deptIds:depts.toString()});
}
//点击添加公告
function addGradeNotice() {
	var gradeId=$("#ztree_gradeId").val();
	var treeObj = $.fn.zTree.getZTreeObj("gradeTree");
	var nodes = treeObj.getSelectedNodes();
	if (nodes == null || nodes.length < 1) {
		$.messager.alert('提示', '请选中一个班级进行添加', 'warning');
		return;
	}else{
		$('#gradeNoticeListTable').datagrid("reload");
		var newsSortId = nodes[0].gradeId;
		parent.addTab("添加公告",contextpath +'/gradeNotice/toAddGradeNotice?gradeId='+gradeId,"icon icon-nav");
	}
	var newsSortId = nodes[0].id;
	var newsSortType=nodes[0].newsSortType;
	$('#gradeNoticeListTable').datagrid("reload");
	$('#gradeNoticeListTable').datagrid("clearSelections");
	var url = contextpath +'/gradeNotice/toAddGradeNotice?gradeId=' + gradeId;
	parent.addTab_update('添加公告', url, 'icon-save');

}

//点击编辑公告
function editGradeNotice() {
	var rows = $('#gradeNoticeListTable').datagrid('getSelections');
	if (rows==null || rows.length <=0) {
		$.messager.alert('提示', '请选中一条记录编辑', 'warning');
		return;
	}
	var newsId=rows[0].newsId;
//	alert(newsId);
	$('#gradeNoticeListTable').datagrid("reload");
	$('#gradeNoticeListTable').datagrid("clearSelections");
	var url = contextpath +'/gradeNotice/toAddGradeNotice?newsId=' + newsId;
	parent.addTab_update('编辑公告', url, 'icon-save');
}
//删除班级公告
function deleteGradeNotice(){
	// 把你选中的 数据查询出来。
	var selectRows = $('#gradeNoticeListTable').datagrid("getSelections");
	var t_info = "删除";
	if (selectRows.length < 1) {
		$.messager.alert("提示", "请选中要" + t_info + "的数据信息！", "info");
		return;
	}
	$.messager.confirm("确认", "您确定要" + t_info + "选中的信息吗？", function(r) {
		if (r) {
			var strIds = "";
			for (var i = 0; i < selectRows.length; i++) {
				strIds += selectRows[i].newsId + ",";
			}
			strIds = strIds.substr(0, strIds.length - 1);

			if (strIds.length > 0) {
				$.ajax({
					url : contextpath + "/gradeNotice/deleteGradeNotice",
					async : false,// 改为同步方式
					type : "get",
					data : {
						"newsIds" : strIds
					},
					success : function(data) {
						// 刷新表格，去掉选中状态的 那些行。
						$('#gradeNoticeListTable').datagrid("reload");
						$('#gradeNoticeListTable').datagrid("clearSelections");
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
//##############################################工具栏end#################################################

//##############################################列表start################################################
function formatDateConversion(value,row,index){
	if(value != null){
		var unixTimestamp = new Date(value);
		return unixTimestamp.Format('yyyy-MM-dd');
	}
}
//##############################################列表end##################################################
function setRowStyle(index,row){
	if(row.status == 0){
		return 'background-color:#eee;color:blue;font-weight:bold;';
	}
}
