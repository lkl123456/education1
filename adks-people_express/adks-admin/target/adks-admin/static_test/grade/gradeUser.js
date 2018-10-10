/*var setting = {
	async : {
		enable : true,
		url : contextpath+"/user/getDeptsJson",
		autoParam : [ "id=parent" ],
		otherParam : [ "orgId", 'orgId' ],
		type : "get"
	},
	view : {
		dblClickExpand : false
	},
	callback : {
//		onAsyncSuccess : zTreeOnSuccess
	}
};
$(function(){
	$.fn.zTree.init($("#treeDemo1"), setting);
});*/


// 查询按钮点击
function doSearch() {
	var depts = $('#userDeptTree').combotree('getValues');
	var name = $('#userName').val();
	$("#gradeUsering").datagrid('load', {userName:name,deptIds:depts.toString()});
}
function doSearchUserName(){
	$("#gradeUsered").datagrid('load', {
		userName : $('#userNameInGrade').val()
	});
}

//返回培训班列表
function backToGradeList(){
	parent.$('#tabs').tabs('select','培训设置'); //这行必须在下一行的上面，否则效果不太对。亲测
	parent.$('#tabs').tabs('close', '配置培训班');
}

//添加班级学员
function addGradeUser(gradeId){
	var rows = $('#gradeUsering').datagrid('getSelections');
	if(rows.length < 1){
		$.messager.alert('提示','请至少选择一名学员','warning');
		return ;
	}
	var ss = [];
	for(var i=0; i<rows.length; i++){
		var row = rows[i];
		ss[i] = row.id;
	}
	if(ss.length > 0){
		//添加
		$.ajax({
			url: contextpath+"/gradeUser/addGradeUsers",
			async: false,
			type: "get",
			data: {"userIds":ss.toString(),"gradeId":gradeId},
			success: function (data) {
				$('#gradeUsering').datagrid('reload'); //刷新数据
				$('#gradeUsered').datagrid('reload'); //刷新数据
			},error:function(){
				alert('error');
			}
		});
	}
}
//移除班级学员
function removeGradeUser(gradeId){
	var rows = $('#gradeUsered').datagrid('getSelections');
	if(rows.length < 1){
		$.messager.alert('提示','请至少选择一名学员','warning');
		return ;
	}
	var ss = [];
	for(var i=0; i<rows.length; i++){
		var row = rows[i];
		ss[i] = row.id;
	}
	if(ss.length > 0){
		//移除
		$.ajax({
			url: contextpath+"/gradeUser/removeGradeUsers",
			async: false,
			type: "get",
			data: {"gcIds":ss.toString(),"gradeId":gradeId},
			success: function (data) {
				$('#gradeUsering').datagrid('reload'); //刷新数据
				$('#gradeUsered').datagrid('reload'); //刷新数据
			},error:function(){
				alert('error');
			}
		});
	}
}


//点击配置学员
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
