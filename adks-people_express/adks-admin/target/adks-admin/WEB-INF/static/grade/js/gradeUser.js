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
function changeValue2(event, treeId, treeNode, clickFlag) {
	if(treeNode.id!=null && (treeNode.id!='' || treeNode.id == '0')){
		$("#userOrgTree").val(treeNode.name);
		$("#userOrgTreeOrgCode").val(treeNode.orgCode);
		$("#treeDemo2").css("display","none");
	}
}
$(function(){
	$.fn.zTree.init($("#treeDemo1"), setting1);
	$.fn.zTree.init($("#treeDemo2"), setting2);
	// 职级树
	$('#zhijiTree').combotree({
		url : contextpath + "/zhiji/getZhijisJsonAddSpace",
		method : 'get',
		onLoadSuccess : function(node, data) {
			$('#zhijiTree').combotree("setValue","");
			changeZhiWu();
		},
		onChange:function(node, data) {
			changeZhiWu();
		}
	});
});

function showOrgTree1(){
	$("#treeDemo1").css("display","");
}
function showOrgTree2(){
	$("#treeDemo2").css("display","");
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
	//var orgIds = $('#userOrgTree').combotree('getValues');
	var orgCodes=$("#userOrgTreeOrgCode").val();
	var  userName= encodeURI($('#userName').val());
	var rankId=$("#zhijiTree").combotree("getValue");
	var positionId=$("#positionId").val();
	//alert(rankId+","+positionId);
	$("#gradeUsering").datagrid('load', {userName:userName,orgCodes:orgCodes,rankId:rankId,positionId:positionId});
}
function doSearchUserName(){
	//var orgIds = $('#selUserOrgTree').combotree('getValues');
	var orgCodes=$("#selUserOrgTreeOrgCode").val();
	var  userNameInGrade= encodeURI($('#userNameInGrade').val());
	$("#gradeUsered").datagrid('load', {
		userRealName : userNameInGrade,orgCodes:orgCodes
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
//点击配置班主任
function configureGradeHeadTeacher(gradeId){
	var currTab = parent.$('#tabs').tabs('getSelected');
	var url = contextpath+"/grade/toConfigureGradeHeadTeacher?gradeId="+gradeId;
	parent.$('#tabs').tabs('update',{
		tab:currTab,
		options:{
			content:'<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>'
		}
	});
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
		ss[i] = row.userId;
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
		ss[i] = row.gradeUserId;
	}
	if(ss.length > 0){
		//移除
		$.ajax({
			url: contextpath+"/gradeUser/removeGradeUsers",
			async: false,
			type: "get",
			data: {"gradeUserIds":ss.toString(),"gradeId":gradeId},
			success: function (data) {
				$('#gradeUsering').datagrid('reload'); //刷新数据
				$('#gradeUsered').datagrid('reload'); //刷新数据
			},error:function(){
				alert('error');
			}
		});
	}
}
//更新学员结业状态
function updateAllGradeUserGraduate(gradeId){
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
						$('#gradeUsering').datagrid("reload");
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

//更新课程进度
function updateAllCourseUser(gradeId){
	$.messager.confirm("确认", "您确定要同步课程进度吗？", function(r) {
		if (r) {
			if (gradeId!=null&&gradeId!="undefined") {
				$.ajax({
					url : contextpath + "/gradeUser/updateAllCourseUser",
					async : false,// 改为同步方式
					type : "get",
					data : {"gradeId" : gradeId},
					success : function(data) {
						$.messager.alert('提示','更新成功！','warning');
						// 刷新表格。
						$('#gradeUsering').datagrid("reload");
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

function updateAllGradeUserCredit(gradeId){
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
						$('#gradeUsering').datagrid("reload");
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

function changeZhiWu(){
	var t1 = $('#zhijiTree').combotree('tree'); // 获取树对象
	var n1 = t1.tree('getSelected'); // 获取选择的节点
	var rankId=n1.id;
	$.ajax({
		async:false,
		url:contextpath+"/userIndex/getZhiwu.do?zhijiId="+rankId,
		type:'post',
		success:function(data){
			var dataJSON=$.parseJSON(data);
			$('#positionId').empty();
			$('#positionId').append("<option value='' selected = 'true'></option>");
			for (i = 0; i < dataJSON.length; i++){
				$('#positionId').append("<option value='"+dataJSON[i]['rankId']+"'>"+dataJSON[i]['rankName']+"</option>");
			}
		}
	});
}
function completeCourseUser(gradeId){
	$.messager.confirm("确认", "您确定要批量完成学习吗？", function(r) {
		if (r) {
			if (gradeId!=null&&gradeId!="undefined") {
				$.ajax({
					url : contextpath + "/gradeUser/completeCourseUser",
					async : false,// 改为同步方式
					type : "get",
					data : {"gradeId" : gradeId},
					success : function(data) {
						$.messager.alert('提示','更新成功！','warning');
						// 刷新表格。
						$('#gradeUsering').datagrid("reload");
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
//##############################################工具栏end##############################################

