//##############################################列表树start##############################################
var setting = {
		async : {
			enable : true,
			url : contextpath+"/userIndex/getOrgsJson",
			autoParam : [ "id=parentId" ],
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
	$("#fileslistdatagrid").datagrid('load', {
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

//##############################################工具栏操作start##############################################
// 查询按钮点击
function doSearch() {
	var categoryCode = $("#cc_ztree_node_code").val();
	var  filesName= encodeURI($('#s_files_name').val());
	if(typeof($('#s_files_orgSnname').val()) == "undefined"){
		$("#fileslistdatagrid").datagrid('load', {
			filesName : filesName,
		});
	}else{
		$("#fileslistdatagrid").datagrid('load', {
			filesName : filesName,
			s_files_orgSnname : $('#s_files_orgSnname').combobox('getValue')
		});
	}
}
//点击添加教参
function add_files(){
	var orgId=$("#ztree_orgId").val();
	if (orgId == null || orgId.length < 1) {
		$.messager.alert('提示', '请选中一个机构进行添加', 'warning');
		return;
	}else{
		$('#dlg_files').dialog('open');
		$('#filesForm').form('clear');
		$('#p').progressbar('setValue', 0);
		$('#img_coverpath').attr('src','');
		$('#gradeId').combotree({
			url:contextpath+"/grade/getGradesJson?orgId="+orgId,
			method:'get',
			onLoadSuccess:function(node,data){
				var node = $('#gradeId').combotree("tree").tree('getRoot');
			    $('#gradeId').combotree("setValue", node.id);
			}
		});
	}
}
//点击编辑教参
function edit_files(){
	var rows = $('#fileslistdatagrid').datagrid('getSelections');
	if(rows.length != 1){
		$.messager.alert('提示','请选中一条记录编辑','warning');
		return ;
	}
	$('#dlg_files').dialog('open').dialog('setTitle','编辑教参');
	$('#filesForm').form('clear');
	$('#p').progressbar('setValue', 0);
	//给表单赋值
	$('#filesForm').form('load', rows[0]);
}
//点击删除教参（flag：-1表示删除）
function del_files(flag) { 
    //把你选中的 数据查询出来。  
    var selectRows = $('#fileslistdatagrid').datagrid("getSelections");
    var t_info = "删除";
    var t_url = contextpath+"/files/deleteFiles";
    if (selectRows.length < 1) {  
        $.messager.alert("提示", "请选中要"+t_info+"的数据信息！", "info");  
        return;  
    }  
 
    $.messager.confirm("确认", "您确定要"+t_info+"选中的信息吗？", function (r) {  
        if (r) {  
            var strIds = "";  
            for (var i = 0; i < selectRows.length; i++) {  
                strIds += selectRows[i].filesId + ",";  
            }  
            strIds = strIds.substr(0, strIds.length - 1);  
            if(strIds.length > 0){
         		 $.ajax({
         		        url: contextpath+"/files/deleteFiles",
         		        async: false,//改为同步方式
         		        type: "get",
         		        data: {"filesIds":strIds},
         		        success: function (data) {
         		       //刷新表格，去掉选中状态的 那些行。  
                          $('#fileslistdatagrid').datagrid("reload");  
                          $('#fileslistdatagrid').datagrid("clearSelections");  
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
//##############################################工具栏操作end##############################################

//##############################################子页面start##############################################
//提交保存教参信息
function submitAdd_files(){
	var orgId=$("#ztree_orgId").val();
	$('#filesForm').form('submit',{
		url:contextpath+'/files/saveFiles',
		onSubmit: function(param){
			param.orgId=orgId;
			return $(this).form('validate');
		},
		success:function(data){
			var datajson = $.parseJSON(data);
			if(datajson['mesg']=='succ'){
				$('#dlg_files').dialog('close');
				$('#fileslistdatagrid').datagrid("reload");
			}else if(datajson['mesg']=='sameName'){
				$.messager.alert("提示", "班级资料名不能重复", "info");
			}
		},
		error : function() {
			alert('error');
		}
	});
}
// 点击取消添加、编辑教参
function cancleAdd_files(){
	$('#dlg_files').dialog('close');
}

//验证方法
$.extend($.fn.validatebox.defaults.rules, {
	//验证班级名是否重复
    checkGradeFilesName:{
    	validator : function(value, param) {
    		var gradeId=$('#gradeId option:selected') .val();
    		console.log(gradeId);
    		value= encodeURI(value);
    		var flag = false;
    		$.ajax({
    			async: false,
    			url: contextpath+"/files/checkGradeFilesName",
    			type: "post", 
    			data: {"filesName":value,"gradeId":value},
    			success: function(data){
    				if(data == "succ"){
    					flag = false;
    				}else{
    					flag = true;
    				}
    			},error:function(){
    				console.log("error");
    			}
    			
    		});
    		return flag;
        },  
        message : "班级资料名称不能重复！" 
    }
});
//##############################################子页面end##############################################
