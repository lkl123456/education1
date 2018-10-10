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
	$("#gradePerformanceListTable").datagrid('load', {
		gradeId : treeNode.id // 左侧班级gradeId
	});
}
//机构树
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
			onClick : changeValue1,
			onAsyncSuccess : zTreeOnSuccess1
		}
	};
//默认展开一级部门
function zTreeOnSuccess1(event, treeId, treeNode, msg) {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getNodesByParam("id", "1", null);
	/*if (nodes) {
		treeObj.expandNode(nodes[0], true, false, true);
	}*/
}
//添加机构
function changeValue1(event, treeId, treeNode, clickFlag) {
	if(treeNode.id!=null && treeNode.id!='' && treeNode.id!='0'){
		$("#selOrgTree").val(treeNode.name);
		$("#selOrgTreeOrgCode").val(treeNode.orgCode);
		$("#treeDemo").css("display","none");
	}
}
function showOrgTree1(){
	$("#treeDemo").css("display","");
}
$(function(){
	$.fn.zTree.init($("#treeDemo"), setting1);
});
//##############################################班级列表树end##############################################

//##############################################工具栏start###############################################
//查询按钮点击
function doSearch() {
	var gradeId=$("#ztree_gradeId").val();
	var orgCode=$("#selOrgTreeOrgCode").val();
	var  userName= encodeURI($('#userName').val());
	$("#gradePerformanceListTable").datagrid('load', {
		gradeId:gradeId,userName:userName,userOrgCode:orgCode
	});
}
function updateGradeUserGraduate(){
	var rows = $('#gradePerformanceListTable').datagrid('getSelections');
	if(rows.length < 1){
		$.messager.alert('提示','请至少选择一个学员','warning');
		return ;
	}
	var ss = [];
	for(var i=0; i<rows.length; i++){
		var row = rows[i];
		ss[i] = row.gradeUserId;
	}
	if(ss.length > 0){
		//添加
		$.ajax({
			url: contextpath+"/gradeStudy/updateGradeUserGraduate",
			async: false,
			type: "get",
			data: {"gradeUserIds":ss.toString()},
			success: function (data) {
				$('#gradePerformanceListTable').datagrid('reload'); //刷新数据
			},error:function(){
				alert('error');
			}
		});
	}
}
//更新学员结业状态
function updateAllGradeUserGraduate(){
	var gradeId=$("#ztree_gradeId").val();
	var treeObj = $.fn.zTree.getZTreeObj("gradeTree");
	var nodes = treeObj.getSelectedNodes();
	if (nodes == null || nodes.length < 1) {
		$.messager.alert('提示', '请选中一个班级进行更新', 'warning');
		return;
	}else{
		$.messager.confirm("确认", "您确定要更新学员结业状态吗？", function(r) {
			if (r) {
				if (gradeId!=null&&gradeId!="undefined") {
					$.ajax({
						url : contextpath + "/gradeUser/updateAllGradeUserGraduate",
						async : false,// 改为同步方式
						type : "get",
						data : {"gradeId" : gradeId},
						success : function(data) {
							$.messager.alert('提示','更新成功！','warning');
							// 刷新表格。
							$('#gradePerformanceListTable').datagrid("reload");
						},
						error : function() {
							alert('error');
						}
					});
				} else {
					$.messager.alert('提示','操作失败！','warning');
				}

			}
		});
	}
}
//更新全部学员学时
function updateAllGradeUserCredit(){
	var gradeId=$("#ztree_gradeId").val();
	var treeObj = $.fn.zTree.getZTreeObj("gradeTree");
	var nodes = treeObj.getSelectedNodes();
	if (nodes == null || nodes.length < 1) {
		$.messager.alert('提示', '请选中一个班级进行更新', 'warning');
		return;
	}else{
		$.messager.confirm("确认", "您确定要更新用户学时吗？", function(r) {
			if (r) {
				if (gradeId!=null&&gradeId!="undefined") {
					$.ajax({
						url : contextpath + "/gradeUser/updateAllGradeUserCredit",
						async : false,// 改为同步方式
						type : "get",
						data : {"gradeId" : gradeId},
						success : function(data) {
							$.messager.alert('提示','更新成功！','warning');
							// 刷新表格。
							$('#gradePerformanceListTable').datagrid("reload");
						},
						error : function() {
							alert('error');
						}
					});
				} else {
					$.messager.alert('提示','操作失败！','warning');
				}

			}
		});
	}
}
//导出Excel
function exportGradePerformanceExcel(){
	// 把你选中的 数据查询出来。
	var gradeId=$("#ztree_gradeId").val();
	var orgCode=$("#selOrgTreeOrgCode").val();
	if (gradeId==null||gradeId=='') {
		$.messager.alert('提示', '请选中一个班级导出数据', 'warning');
		return;
	}else{
		$.messager.confirm("确认", "您确定要导出班级成绩统计信息吗？", function (r) {  
	        if (r) {  
	        	$("#exGradeId").val(gradeId);
	        	$("#exOrgCode").val(orgCode);
	        	$('#export_gradePerformance').submit();
	        }  
	    });
	}
}
//##############################################工具栏end#################################################

//##############################################列表start################################################
function graduateStatus(value,row,index){
	if(value == 1){
		return '毕业';
	}else if(value == 2){
		return '未毕业';
	}
}
function formatOper(val, row, index) {
	return '<a href="javascript:toGradeUserInfo(' + row.userId+',' +row.gradeId+ ')">查看档案</a>';
}
//查看班级档案
function toGradeUserInfo(userId,gradeId){
	if((userId!=null&&userId!='')&&(gradeId!=null&&gradeId!='')){
		window.open(contextpath+"/gradePerformance/toGradeUserInfo?userId="+userId+"&gradeId="+gradeId);
	}
}
//##############################################列表end#######################################################

//##############################################子页面操作start##################################################

//##############################################子页面操作end####################################################
function setRowStyle(index,row){
	if(row.status == 0){
		return 'background-color:#eee;color:blue;font-weight:bold;';
	}
}
