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
		onAsyncSuccess : zTreeOnSuccess
	}
};
//默认展开一级部门
function zTreeOnSuccess(event, treeId, treeNode, msg) {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo1");
	var nodes = treeObj.getNodesByParam("id", "1", null);
	/*if (nodes) {
		treeObj.expandNode(nodes[0], true, false, true);
	}*/
}

//添加机构
function changeValue1(event, treeId, treeNode, clickFlag) {
	if(treeNode.id!=null && (treeNode.id!='' || treeNode.id == '0')){
		$("#selUserOrgTree").val(treeNode.name);
		$("#selUserOrgTreeOrgCode").val(treeNode.orgCode);
		$("#treeDemo1").css("display","none");
	}
}

$(function(){
	$.fn.zTree.init($("#treeDemo1"), setting1);
	// 模块名称
	$('#ss1').combobox({
		url : contextpath+ "/userIndex/getUserNationalityJson?name=module",
		valueField : 'id',
		textField : 'text',
		onLoadSuccess : function() {
			//$('#ss1').combobox('select', '3');// 菜单项可以text或者id
		}
	});
	// 操作类型
	$('#ss2').combobox({
		url : contextpath+ "/userIndex/getUserNationalityJson?name=operateType",
		valueField : 'id',
		textField : 'text',
		onLoadSuccess : function() {
			//$('#ss2').combobox('select', '14');// 菜单项可以text或者id
		}
	});
});

function doSearch() {
	//var orgIds = $('#userOrgTree').combotree('getValues');
	var orgCode=$("#selUserOrgTreeOrgCode").val();
	var moduleId=$('#ss1').combobox('getValue');
	var operateType=$('#ss2').combobox('getValue');
	var minTime=$('#minTime').datebox('getValue');
	var maxTime=$('#maxTime').datebox('getValue');
	
	$("#logStatisticsListTable").datagrid('load', {moduleId:moduleId,orgCode:orgCode,operateType:operateType,minTime:minTime,maxTime:maxTime});
}

function showOrgTree1(){
	$("#treeDemo1").css("display","");
}
function canle(){
	$("#selUserOrgTree").val('');
	$("#selUserOrgTreeOrgCode").val('');
	$("#treeDemo1").css("display","none");
	$('#ss1').combobox('setValue','');
	$('#ss2').combobox('setValue','');
	$('#minTime').datebox('setValue','');
	$('#maxTime').datebox('setValue','');
}
//操作类型
function operateTypeFormat(value,row,index){
	if(value == 81 || value == '81'){
		return "添加";
	}else if(value == 82 || value=='82'){
		return "修改";
	}else if(value == 83 || value=='83'){
		return "删除";
	}else if(value == 84 || value =='84'){
		return "批改";
	}else{
		return "数据有误";
	}
}
//时间
function formatDateConversion(value,row,index){
	if(value != null){
		var unixTimestamp = new Date(value);
		return unixTimestamp.Format('yyyy-MM-dd hh:mm:ss');
	}
}
//备注信息
function beiZhuFormat(value,row,index){
	var msg=row.creatorName+"在【"+row.valDisplay+"】下";
	if(row.operateType == 81 || row.operateType == '81'){
		msg += "添加";
	}else if(row.operateType == 82 || row.operateType=='82'){
		msg += "修改";
	}else if(row.operateType == 83 || row.operateType=='83'){
		msg += "删除";
	}else if(row.operateType == 84 || row.operateType =='84'){
		msg += "批改";
	}else{
		msg += "数据有误";
	}
	if(row.operateType == 83 || row.operateType=='83'){
		msg+="了一条ID为"+row.dataId+"的信息！";
	}else{
		msg+="了一条ID为"+row.dataId+"、name为"+row.dataName+"的信息！";
	}
	return msg;
}