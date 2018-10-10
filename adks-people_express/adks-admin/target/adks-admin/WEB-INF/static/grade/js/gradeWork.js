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
	$("#gradeWorkListTable").datagrid('load', {
		gradeId : treeNode.id // 左侧班级gradeId
	});
}
//##############################################班级列表树end##############################################

//##############################################工具栏start###############################################
// 查询按钮点击
function doSearch() {
	var  workTitle= encodeURI($('#workTitle').val());
	$("#gradeWorkListTable").datagrid('load', {
		workTitle : workTitle
	});
}
//添加班级作业
function addGradeWork(){
	var treeObj = $.fn.zTree.getZTreeObj("gradeTree");
	var nodes = treeObj.getSelectedNodes();
	if (nodes == null || nodes.length < 1) {
		$.messager.alert('提示', '请选中一个班级进行添加', 'warning');
		return;
	}else{
		//作业状态下拉框
//		$('#ss1').combobox({
//			url : contextpath+"/userIndex/getUserNationalityJson?name=isPublish",
//			valueField : 'id',
//			textField : 'text',
//			onLoadSuccess : function() {
//				$('#ss1').combobox('select', '20');// 菜单项可以text或者id
//			}
//		});
		//是否允许上传附件
//		$('#ss2').combobox({
//			url : contextpath+"/userIndex/getUserNationalityJson?name=yesNo",
//			valueField : 'id',
//			textField : 'text',
//			onLoadSuccess : function() {
//				$('#ss2').combobox('select', '2');// 菜单项可以text或者id
//			}
//		});
		$('#dlg_work').dialog('open').dialog('setTitle','新增作业');
		$('#gradeWorkFrom').form('clear');
	}
}
//编辑班级作业
function editGradeWork(){
	var row = $('#gradeWorkListTable').datagrid('getSelections');
	if(row.length != 1){
		$.messager.alert('提示','请选中一条记录编辑','warning');
		return ;
	}
	$('#dlg_work').dialog('open').dialog('setTitle','编辑作业');
	$('#gradeWorkFrom').form('clear');
	$('#gradeWorkFrom').form('load',row[0]);
}

function deleteGradeWork(){
	var selectRows = $('#gradeWorkListTable').datagrid('getSelections');
	var t_info = "删除";
	var t_url = contextpath + "/gradeWork/delGradeWork";
	if (selectRows.length < 1) {
		$.messager.alert('提示', '请至少选中一条记录删除', 'warning');
		return;
	}else{
		var strIds = "";
		for (var i = 0; i < selectRows.length; i++) {
			strIds += selectRows[i].gradeWorkId + ",";
		}
		strIds = strIds.substr(0, strIds.length - 1);
		$.post(t_url, {
			ids : strIds
		}, function(data) {
			if (data == "succ") {
				// 刷新表格，去掉选中状态的 那些行。
				$('#gradeWorkListTable').datagrid("reload");
				$('#gradeWorkListTable').datagrid("clearSelections");
			} else {
				$.messager.alert("操作失败~~", data);
			}
		});
	}
}
//查看班级作业
function selGradeWork(){
	var row = $('#gradeWorkListTable').datagrid('getSelections');
	if (row.length != 1) {
		$.messager.alert('提示', '请选中一条记录', 'warning');
		return;
	}
//	alert(row[0].gradeWorkId);
	var url = contextpath + "/gradeWork/selGradeWork?gradeWorkId="+ row[0].gradeWorkId;
	parent.addTab_update('学员作业', url, 'icon-tip');
}
//##############################################工具栏end#################################################

//##############################################列表start################################################
function formatStatus(value,row,index){
	if(value == 1){
		return '发布';
	}else if(value == 2){
		return '未发布';
	}
}
function formatDateConversion(value,row,index){
	if(value != null){
		var unixTimestamp = new Date(value);
		return unixTimestamp.Format('yyyy-MM-dd');
	}
}
//##############################################列表end##################################################

//##############################################子页面操作start##################################################
function cancleAdd(){
	$('#gradeWorkFrom').form('clear');
	$('#dlg_work').dialog('close')
}
//提交表单
function submitAdd(){
	var startDate = $('#s_startDate_str').datebox('getValue');
	var sDate=new Date((startDate.substring(0,10)+" 00:00:00").replace(/-/g,"/"));
	var endDate = $('#s_endDate_str').datebox('getValue');
	var eDate=new Date((endDate.substring(0,10)+" 00:00:00").replace(/-/g,"/"));
	if(sDate>=eDate){
		$.messager.alert('友情提示', '开始时间不能大于或等于结束时间!', 'info');
		return;
	}
	$('#gradeWorkFrom').form('submit',{
		url:contextpath+'/gradeWork/saveGradeWork',
		onSubmit: function(param){
			param.s_startDate_str=$('#s_startDate_str').datetimebox('getValue');
			param.s_endDate_str=$('#s_endDate_str').datetimebox('getValue');
			param.ztree_gradeId=$("#ztree_gradeId").val();
			param.ztree_gradeName=$("#ztree_gradeName").val();
			return $(this).form('validate');
		},
		success: function(data){
			var datajson = $.parseJSON(data);
			if(datajson['mesg']=='succ'){
				$('#dlg_work').dialog('close');
				$('#gradeWorkListTable').datagrid('reload'); //刷新数据
			}else if(datajson['mesg']=='sameName'){
				$.messager.alert('友情提示', '班级作业名不能重复', 'info');
			}
		},error:function(){
			alert('error');
		}
	});
}
//验证方法
$.extend($.fn.validatebox.defaults.rules, {
	//验证班级名是否重复
	checkWorkTitle:{
    	validator : function(value, param) {
    		var gradeId=$("#gradeId").val();
    		console.log(gradeId);
    		console.log(value);
    		value= encodeURI(value);
    		var flag = false;
    		$.ajax({
    			async: false,
    			url: contextpath+"/gradeWork/checkWorkTitle",
    			type: "post", 
    			data: {"workTitle":value,"gradeId":gradeId},
    			success: function(data){
    				if(data == "succ"){
    					flag = false;
    				}else{
    					flag = true;
    				}
    			},error:function(){
    				alert('error');
    			}
    			
    		});
    		return flag;
        },  
        message : "班级作业名称不能重复！" 
    }
});
//##############################################子页面操作end##################################################
function setRowStyle(index,row){
	if(row.status == 0){
		return 'background-color:#eee;color:blue;font-weight:bold;';
	}
}
