//##############################################树start##############################################
var setting1 = {
	async : {
		enable : true,
		url : contextpath + "/course/getCourseSortListJson",
		autoParam : [ "id=parentId" ],
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

var setting2 = {
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
			onClick : changeValue2,
			onAsyncSuccess : zTreeOnSuccess2
		}
	};

//默认展开一级部门
function zTreeOnSuccess1(event, treeId, treeNode, msg) {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo1");
	var nodes = treeObj.getNodesByParam("id", "1", null);
	/*if (nodes) {
		treeObj.expandNode(nodes[0], true, false, true);
	}*/
}

//默认展开一级部门
function zTreeOnSuccess2(event, treeId, treeNode, msg) {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo2");
	var nodes = treeObj.getNodesByParam("id", "1", null);
	/*if (nodes) {
		treeObj.expandNode(nodes[0], true, false, true);
	}*/
}

function changeValue1(event, treeId, treeNode, clickFlag) {
	if(treeNode.id!=null && treeNode.id!='' && treeNode.id!='0'){
		$("#selCourseSortTree").val(treeNode.name);
		$("#selCourseSortCode").val(treeNode.courseSortCode);
		$("#treeDemo1").css("display","none");
	}
}
function changeValue2(event, treeId, treeNode, clickFlag) {
	if(treeNode.id!=null && treeNode.id!='' && treeNode.id!='0'){
		$("#selCourseOrgTree").val(treeNode.name);
		$("#selCourseOrgTreeOrgCode").val(treeNode.orgCode);
		$("#treeDemo2").css("display","none");
	}
}
function showOrgTree1(){
	$("#treeDemo1").css("display","");
}
function showOrgTree2(){
	$("#treeDemo2").css("display","");
}
$(function(){
	$.fn.zTree.init($("#treeDemo1"), setting1);
	$.fn.zTree.init($("#treeDemo2"), setting2);
});
//##############################################树end##############################################

//##############################################工具栏start##############################################
//查询按钮点击
function doSearch() {
	var  courseName= encodeURI($('#courseName').val());
	var courseSortCode = $('#selCourseSortCode').val();
	var orgCode = $('#selCourseOrgTreeOrgCode').val();
	$("#courseStatisticsListTable").datagrid('load', {
		courseName : courseName,courseSortCode:courseSortCode.toString(),courseOrgCode:orgCode.toString()
	});
}
//导出Excel
function exportCourseExcle(){
	// 把你选中的 数据查询出来。
	var courseSortCode=$("#selCourseSortCode").val();
	var courseOrgCode=$("#selCourseOrgTreeOrgCode").val();
	$.messager.confirm("确认", "您确定要导出课程统计信息吗？", function (r) {  
        if (r) {  
        	$("#excourseOrgCode").val(courseSortCode);
        	$("#excourseOrgCode").val(courseOrgCode);
        	$('#export_courseStatistics').submit();
        }  
    });
}
//##############################################工具栏end################################################
