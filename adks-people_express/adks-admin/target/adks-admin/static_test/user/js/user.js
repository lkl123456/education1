

var setting = {
	async : {
		enable : true,
		url : contextpath+"/user/getDeptsJson",
		autoParam : [ "id=parent" ],
//		otherParam : ["orgunameValue",orgunameValue],
		type : "get"
	},
	view : {
		dblClickExpand : false
	},
	callback : {
		onClick : updateDataTabs,
		onAsyncSuccess : zTreeOnSuccess,
		beforeAsync: zTreeBeforeAsync
	}
};
//默认展开一级部门
function zTreeOnSuccess(event, treeId, treeNode, msg) {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getNodesByParam("id", "1", null);
	if(nodes){
		treeObj.expandNode(nodes[0], true, false, true);
	}
}
//加载数据之前，动态添加otherparam
function zTreeBeforeAsync(treeId, treeNode){
	var orgunameValue = $('#orgunameValue').combobox('getValue');
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	treeObj.setting.async.otherParam = {"orgunameValue":orgunameValue};
	
	return true;
}
$(function(){
	$.fn.zTree.init($("#treeDemo"), setting);
	
});

// 左侧部门树 点击
function updateDataTabs(event, treeId, treeNode, clickFlag) {
	$("#userlistdatagrid").datagrid('load', {
		dept : treeNode.id, // 左侧部门id
		orgunameValue : $('#orgunameValue').combobox('getValue')
	});
}

// 查询按钮点击
function doSearch() {
	var vl = $('#loginname').val();
	var orgunameValue = $('#orgunameValue').combobox('getValue');
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getSelectedNodes();
	var deptv = "";
	if(nodes != null && nodes.length > 0){
		deptv = nodes[0].id;
	}
	
	$("#userlistdatagrid").datagrid('load', {
		loginname : $('#loginname').val(),
		orgunameValue : orgunameValue,
		dept : deptv
	});
}

//选择分校后，刷新左侧部门树
function refreshLeftDept(){
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	treeObj.reAsyncChildNodes(null, "refresh");
	$("#userlistdatagrid").datagrid('load', {
		loginname : $('#loginname').val(),
		orgunameValue : $('#orgunameValue').combobox('getValue')
	});
}

//点击添加部门/用户
function addDialog(tid){
	var orgunameValue = $('#orgunameValue').combobox('getValue');
	if(tid == 'deptadd'){
		$('#dlg').dialog('open');
		$('#ff').form('clear');
		$('#cc').combotree({
			url:'/dept/getDeptTreeJson?orgunameValue='+orgunameValue,
			method:'get'
		});
	}else if(tid == 'useradd'){
		$('#dlg_user').dialog('open').dialog('setTitle','新增用户');
		$('#hh').form('clear');
		$('#cc2').combotree({
			url:'/dept/getDeptTreeJson?orgunameValue='+orgunameValue,
			method:'get'
		});
	}
}
//点击取消
function cancleAdd(tid){
	if(tid == "deptadd"){
		$('#dlg').dialog('close');
	}else if (tid == 'useradd'){
		$('#dlg_user').dialog('close');
	}
}

//点击编辑用户按钮
function editUser(){
	var orgunameValue = $('#orgunameValue').combobox('getValue');
	var row = $('#userlistdatagrid').datagrid('getSelections');
	if(row.length != 1){
		$.messager.alert('提示','请选中一条记录编辑','warning');
		return ;
	}
	$('#dlg_user').dialog('open').dialog('setTitle','编辑用户');
	$('#hh').form('clear');
	$('#cc2').combotree({
		url:'/dept/getDeptTreeJson?orgunameValue='+orgunameValue,
		method:'get'
	});
	//给表单赋值
	$('#hh').form('load',row[0]);
}
//点击删除用户
function deleteUser(){
	var orgunameValue = $('#orgunameValue').combobox('getValue');
	var rows = $('#userlistdatagrid').datagrid('getSelections');
	if(rows.length < 1){
		$.messager.alert('提示','请至少选中一条记录删除','warning');
		return ;
	}
	var ss = [];
	for(var i=0; i<rows.length; i++){
		var row = rows[i];
		ss[i] = row.id;
	}
	if(ss.length > 0){
		$.messager.confirm('确认','确认要删除这批数据吗?',function(r){
			if(r){
				$.ajax({
					url: contextpath+"/user/deleteUser?orgunameValue="+orgunameValue,
					async: false,//改为同步方式
					type: "get",
					data: {"userIds":ss.toString()},
					success: function (data) {
						doSearch();
//						$('#userlistdatagrid').datagrid('reload', {}); //刷新数据
					},error:function(){
						alert('error');
					}
				});
			}
		});
	}
}

//删除部门
function deleteDept(){
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getCheckedNodes(true); //获取选中的节点
	alert('部门关联业务数据，暂不可删除');
	
}

//提交保存
function submitAdd(tid){
	
	var orgunameValue = $('#orgunameValue').combobox('getValue');
	//设置ajax form
	if(tid == "deptadd"){
		$('#ff').form({
			url: contextpath+'/dept/saveDept',
			onSubmit: function(param){
				param.orgunameValue = orgunameValue;
				return $(this).form('validate');
			},
			success:function(data){
				if(data == 'succ'){
//					alert('保存部门成功，局部刷新部门节点...');
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
					treeObj.reAsyncChildNodes(null, "refresh");//后续可以改为：单独节点的局部刷新
					$('#dlg').dialog('close');
				}
			}
		});
		$('#ff').submit();
	}else if (tid == 'useradd'){
		$('#hh').form({
			url: contextpath+'/user/saveUser',
			onSubmit: function(param){
				param.orgunameValue = orgunameValue;
				return $(this).form('validate');
			},
			success:function(data){
				$('#dlg_user').dialog('close');
				doSearch();
//				$("#userlistdatagrid").datagrid('reload', {});
			}
		});
		$('#hh').submit();
	}
}
//刷新树结构
function reloadTree(){
//	alert('刷新，重新加载树');
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	treeObj.reAsyncChildNodes(null, "refresh");
}

function addUserclick(){
	$('#dlg_user').dialog('open');
}

function formatColumnData(value,row,index){
	if(value == 1){
		return '开通';
	}else{
		return '停用';
	}
}

//改变用户状态，开通/停用
function changeUserStatus(tips){
	var orgunameValue = $('#orgunameValue').combobox('getValue');
	var ss = [];
	var rows = $('#userlistdatagrid').datagrid('getSelections');
	for(var i=0; i<rows.length; i++){
		var row = rows[i];
		ss[i] = row.id;
	}
	var handelType = "";//操作类型 1：重置密码，2：停用，3：恢复
	if(tips == 'stop'){
		//停用
		handelType = 2;
	}else if(tips == 'continue'){
		//恢复
		handelType = 3;
	}else if(tips == 'resetpass'){
		//重置密码
		handelType = 1;
	}
	if(ss.length > 0){
		 $.ajax({
		        url: contextpath+"/user/changeUser",
		        async: false,//改为同步方式
		        type: "get",
		        data: {"userIds":ss.toString(),"password":"111111","handelType":handelType,"orgunameValue":orgunameValue},
		        success: function (data) {
//		        	$.messager.alert('Info', '操作成功..');
					$('#userlistdatagrid').datagrid('reload', {}); //刷新数据
		        },error:function(){
		        	alert('error');
		        }
		 });
	}else{
		$.messager.alert('提示','请勾选需要操作的用户!','warning');
	}
	
}

//Date 格式化“yyyy-MM-dd”
Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function setRowStyle(index,row){
	if(row.status == 2){
		return 'background-color:#cccccc;font-weight:bold;';
	}
}


