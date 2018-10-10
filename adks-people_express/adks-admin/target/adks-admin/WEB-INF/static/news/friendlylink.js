//##############################################列表树start##############################################
var setting = {
		async : {
			enable : true,
			url : contextpath+"/userIndex/getOrgsJson",
			autoParam : [ "id=parent" ],
//			otherParam : [ "orgId", 'orgId' ]
		},
		view : {
			dblClickExpand : false
		},
		callback : {
			onClick : updateDataTabs,
			onAsyncSuccess : zTreeOnSuccess
		}
};

$(function() {
	$.fn.zTree.init($("#orgTreeDemo"), setting);
});
//左侧课程分类树 点击事件
function updateDataTabs(event, treeId, treeNode, clickFlag) {
	$("#ztree_orgId").val(treeNode.id);
	$("#friendlylinklistdatagrid").datagrid('load', {
		orgId : treeNode.id // 左侧分类id
	});
}
function zTreeOnSuccess(event, treeId, treeNode, msg) {
	var treeObj = $.fn.zTree.getZTreeObj("orgTreeDemo");
	var nodes = treeObj.getNodesByParam("id", "0", null);
	if(nodes){
		treeObj.expandNode(nodes[0], true, false, true);
	}
}
//##############################################列表树end##############################################

//##############################################工具栏start##############################################
// 查询按钮点击
function doSearch() {
	var  s_friendlylink_name= encodeURI($('#s_friendlylink_name').val());
	var categoryCode = $("#cc_ztree_node_code").val();
	if(typeof($('#s_friendlylink_orgSnname').val()) == "undefined"){
		$("#friendlylinklistdatagrid").datagrid('load', {
			s_friendlylink_name : s_friendlylink_name,
		});
	}else{
		$("#friendlylinklistdatagrid").datagrid('load', {
			s_friendlylink_name : s_friendlylink_name,
			s_friendlylink_orgSnname : $('#s_friendlylink_orgSnname').combobox('getValue')
		});
	}
}

//点击添加友情链接
function add_friendlylink(){
	var treeObj = $.fn.zTree.getZTreeObj("orgTreeDemo");
	var nodes = treeObj.getSelectedNodes();
	if (nodes == null || nodes.length < 1) {
		$.messager.alert('提示', '请选中一个机构进行添加', 'warning');
		return;
	}else{
		$('#dlg_friendlylink').dialog('open');
		$('#friendlylinkForm').form('clear');
		$('#aip').attr('src', '');
		$('#p').progressbar('setValue', 0);
//		$('#cc2').combotree({
//			url:contextpath+"/userIndex/getOrgsJson",
//			method:'get',
//			onLoadSuccess:function(node,data){
//				var node = $('#cc2').combotree("tree").tree('getRoot');
//				$('#cc2').combotree("setValue", node.id);
//			}
//		});
	}
}

//点击编辑友情链接
function edit_friendlylink(){
	var rows = $('#friendlylinklistdatagrid').datagrid('getSelections');
	if(rows.length != 1){
		$.messager.alert('提示','请选中一条记录编辑','warning');
		return ;
	}
	$('#dlg_friendlylink').dialog('open').dialog('setTitle','编辑新闻');
	$('#friendlylinkForm').form('clear');
	$('#p').progressbar('setValue', 0);
	//给表单赋值
	$('#friendlylinkForm').form('load', rows[0]);
}

//点击删友情链接（flag：-1表示删除）
function del_friendlylink(flag) {  
    //把你选中的 数据查询出来。  
    var selectRows = $('#friendlylinklistdatagrid').datagrid("getSelections");
    var t_info = "删除";
    var t_url = contextpath+"/friendlylink/deleteFriendlylink";
    
    if (selectRows.length < 1) {  
        $.messager.alert("提示", "请选中要"+t_info+"的数据信息！", "info");  
        return;  
    }  
 
    $.messager.confirm("确认", "您确定要"+t_info+"选中的信息吗？", function (r) {  
        if (r) {  
            var strIds = "";  
            for (var i = 0; i < selectRows.length; i++) {  
                strIds += selectRows[i].fdLinkId + ",";  
            }  
            strIds = strIds.substr(0, strIds.length - 1);  
            if(strIds.length > 0){
        		 $.ajax({
        		        url: contextpath+"/friendlylink/deleteFriendlylink",
        		        async: false,//改为同步方式
        		        type: "get",
        		        data: {"fdLinkIds":strIds},
        		        success: function (data) {
        		       //刷新表格，去掉选中状态的 那些行。  
                         $('#friendlylinklistdatagrid').datagrid("reload");  
                         $('#friendlylinklistdatagrid').datagrid("clearSelections");  
        		        },error:function(){
        		        	alert('error');
        		        }
        		 });
 	       	}else{
 	       		$.messager.alert("操作失败~~", data);  
 	       	}   
        }  
    });  
}
//##############################################工具栏end##############################################

//##############################################子页面start##############################################
//提交保存友情链接信息
function submitAdd_friendlylink(){
	$('#friendlylinkForm').form('submit',{
		url:contextpath+'/friendlylink/saveFriendlylink',
		onSubmit: function(param){
			param.link_orgId=$("#ztree_orgId").val();
			return $(this).form('validate');
		},
		success:function(data){
			doSearch();
			$('#dlg_friendlylink').dialog('close');
		}
	});
}

// 点击取消添加、编辑友情链接
function cancleAdd_friendlylink(){
	$('#dlg_friendlylink').dialog('close');
}
//##############################################子页面end##############################################