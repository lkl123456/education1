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
	if(treeNode.id!=null && treeNode.id!='' && treeNode.id!='0'){
		$("#selUserOrgTree").val(treeNode.name);
		$("#selUserOrgTreeOrgCode").val(treeNode.orgCode);
		$("#treeDemo1").css("display","none");
	}
}
$(function(){
	$.fn.zTree.init($("#treeDemo1"), setting1);
});
function showOrgTree1(){
	$("#treeDemo1").css("display","");
}
//##############################################列表start##############################################
function formatStatus(value,row,index){
	if(value == 1){
		return '激活';
	}else if(value == 2){
		return '停用';
	}
}
//##############################################列表end##############################################

//##############################################工具栏start##############################################
// 查询按钮点击
function doSearch() {
	//var depts = $('#userOrgTree').combotree('getValues');
	var orgCodes=$("#selUserOrgTreeOrgCode").val();
	var  userRealName= encodeURI($('#userRealName').val());
	$("#gradeHeadTeachering").datagrid('load', {userRealName:userRealName,orgCodes:orgCodes});
}
function doSearchUserName(){
	var  userNameInGrade= encodeURI($('#userNameInGrade').val());
	$("#gradeHeadTeachered").datagrid('load', {
		userRealName : userNameInGrade
	});
}
//返回培训班列表
function backToGradeList(){
	parent.$('#tabs').tabs('select','培训设置'); //这行必须在下一行的上面，否则效果不太对。亲测
	parent.$('#tabs').tabs('close', '配置培训班');
}
//点击配置考试
function configureGradeExam(gradeId){
	var currTab = parent.$('#tabs').tabs('getSelected');
	var url = contextpath+"/grade/toConfigureGradeExam?gradeId="+gradeId;
	parent.$('#tabs').tabs('update',{
		tab:currTab,
		options:{
			content:'<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>'
		}
	});
}
//点击配置课程
function configureGradeCourse(gradeId){
	var currTab = parent.$('#tabs').tabs('getSelected');
	var url = contextpath+"/grade/toConfigureGradeCourse?gradeId="+gradeId;
	parent.$('#tabs').tabs('update',{
		tab:currTab,
		options:{
			content:'<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>'
		}
	});
}
//点击配置学员
function configureGradeUser(gradeId){
	var currTab = parent.$('#tabs').tabs('getSelected');
	var url = contextpath+"/grade/toConfigureGradeUser?gradeId="+gradeId;
	parent.$('#tabs').tabs('update',{
		tab:currTab,
		options:{
			content:'<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>'
		}
	});
}
//添加班主任
function addGradeHeadTeacher(gradeId){
	//gradeName=encodeURI(gradeName);
	var arrRows=$('#gradeHeadTeachered').datagrid('getRows');
	if(arrRows.length==1){
		$.messager.alert('提示','一个班级只允许有一位班主任,请移除已选班主任后再添加','warning');
		return ;
	}
	var rows = $('#gradeHeadTeachering').datagrid('getSelections');
	if(rows.length < 1){
		$.messager.alert('提示','请选择一名班主任','warning');
		return ;
	}else if(rows.length>1){
		$.messager.alert('提示','一个班级只允许有一位班主任','warning');
		return ;
	}
	var ss = [];
	for(var i=0; i<rows.length; i++){
		var row = rows[i];
		ss[i] = row.userId;
	}
	if(ss.length > 0){
		//添加
		$.ajax({
			url: contextpath+"/gradeUser/addGradeHeadTeacher",
			async: false,
			type: "get",
			data: {"userIds":ss.toString(),"gradeId":gradeId},
			success: function (data) {
				$('#gradeHeadTeachering').datagrid('reload'); //刷新数据
				$('#gradeHeadTeachered').datagrid('reload'); //刷新数据
			},error:function(){
				alert('error');
			}
		});
	}
}
//移除班主任
function removeGradeHeadTeacher(gradeId){
	var rows = $('#gradeHeadTeachered').datagrid('getSelections');
	if(rows.length < 1){
		$.messager.alert('提示','请选择一名班主任','warning');
		return ;
	}
	var ss = [];
	for(var i=0; i<rows.length; i++){
		var row = rows[i];
		ss[i] = row.userId;
	}
	if(ss.length > 0){
		//移除
		$.ajax({
			url: contextpath+"/gradeUser/removeGradeHeadTeacher",
			async: false,
			type: "get",
			data: {"userIds":ss.toString(),"gradeId":gradeId},
			success: function (data) {
				$('#gradeHeadTeachering').datagrid('reload'); //刷新数据
				$('#gradeHeadTeachered').datagrid('reload'); //刷新数据
			},error:function(){
				alert('error');
			}
		});
	}
}
//##############################################工具栏end##############################################

