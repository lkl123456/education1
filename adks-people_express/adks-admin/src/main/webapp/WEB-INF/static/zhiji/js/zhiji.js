
$(function() {
	$('#zhijiForm').form({
		onSubmit: function(){
			return $(this).form('validate');
		},
		success:function(data){
			if(data == 'succ'){
				close_div();
				doSearch();
			}else if(data == 'snnameExists'){
				$.messager.alert("提示","职级名称已存在，请重新输入!");
			}
		}
	});
	
	// 隐藏列（机构ID）
	//$('#zhijiList_table').datagrid('hideColumn','org_id');
})

// 点击查询按钮
function doSearch() {
	var rankName=encodeURI($('#s_org_name').val());
	//alert(orgName);
	$("#zhijiList_table").datagrid('load', {
		s_org_name : rankName
	});
}
//查看子级机构管理
function editZhijiVal(){
	var selectRows = $('#zhijiList_table').datagrid("getSelections");
	if (selectRows ==null || selectRows.length <= 0) {  
        $.messager.alert("提示", "请先选中一个父级职级","info");  
        return;  
    }
	var row=selectRows[0];
	var rankId=row.rankId;//现在请求的父级id
	$("#zhijiParentId").val(rankId);//当前页的父级id
	
	var url=contextpath+'/zhiji/zhijiList?parentId='+rankId;
	parent.addTab_update('职级管理',url,'icon-save');
	
}
//返回上一级的机构
function editZhijiValBefore(){
	var overParentId=$("#overParentId").val();
	var url=contextpath+'/zhiji/zhijiList?parentId='+overParentId;
	parent.addTab_update('职级管理',url,'icon-save');
}

// 点击添加机构信息
function add_zhiji(parentName){
	$('#dlg').dialog({title: "添加职级"});
	$('#dlg').dialog('open');
	$('#zhijiForm').form('clear');
	
	var zhijiParentName=$("#zhijiParentName").val();
	var zhijiParentId=$("#zhijiParentId").val();
	//alert(zhijiParentName);
	//alert(zhijiParentId);
	if(parentName ==null || parentName == ''){
		parentName='职级';
		zhijiParentId=0;
	}
	$('#parentName').val(parentName);
	$('#parentId').val(zhijiParentId);
	$('#parentNameShow').val(parentName);
}

// 点击编辑网校信息(加载显示相关信息)
function edit_zhiji(parentName){
    var selectRows = $('#zhijiList_table').datagrid("getSelections");  
    if (selectRows.length < 1 || selectRows.length > 1) {  
        $.messager.alert("提示", "请您选中一条需要编辑的信息！","info");  
        return;  
    }
    $('#dlg').dialog({title: "编辑职级"});
    $('#dlg').dialog('open');
    $('#zhijiForm').form('clear');
	//给表单赋值
	$('#zhijiForm').form('load',selectRows[0]);
	var zhijiParentName=$("#zhijiParentName").val();
	var zhijiParentId=$("#zhijiParentId").val();
	//alert(zhijiParentName);
	//alert(zhijiParentId);
	if(parentName ==null || parentName == ''){
		parentName='职级';
		zhijiParentId=0;
	}
	$('#parentName').val(parentName);
	$('#parentId').val(zhijiParentId);
	$('#parentNameShow').val(parentName);
}


// 点击取消 添加或编辑信息
function close_div(){
	$('#dlg').dialog('close');
}

// 点击保存 添加或编辑信息
function submitForm(){
	//ajax form提交
	var rankName=$("#rankName").val();
	if(rankName == null || rankName == '' || trimStr(rankName) == ''){
		 $.messager.alert("提示", "请输入职级名称","info");
		return ;
	}else if(rankName.length>32){
		 $.messager.alert("提示", "职级名称应小于32个字符","info");
		return ;
	}
	var orderNum=$("#orderNum").val();
	if(orderNum!=null&&orderNum!='' && orderNum.length>5){
		$.messager.alert("提示", "排序数字过大","info");
		return ;
	}
	
	$('#zhijiForm').submit();
	 $('#zhijiList_table').datagrid("clearSelections");  
}

// 点击删除（flag: -1表示删除，1表示开通，2表示禁用）
function del_zhiji(flag) {  
    //把你选中的 数据查询出来。  
    var selectRows = $('#zhijiList_table').datagrid("getSelections");
    var t_info = "删除";
    var t_url= contextpath+"/zhiji/delZhiji";
    if (selectRows.length < 1) {  
        $.messager.alert("提示", "请选中要"+t_info+"的数据信息！","info");  
        return;  
    }  
 
    $.messager.confirm("确认", "您确定要"+t_info+"选中的信息吗？", function (r) {  
        if (r) {  
            var strIds = "";  
            for (var i = 0; i < selectRows.length; i++) {  
                strIds += selectRows[i].rankId + ",";  
            }  
            strIds = strIds.substr(0, strIds.length - 1);  
            $.post(t_url, { ids: strIds }, function (data) { 
                if (data == "succ") {  
                    //刷新表格，去掉选中状态的 那些行。  
                    $('#zhijiList_table').datagrid("reload");  
                    $('#zhijiList_table').datagrid("clearSelections");  
                } else {  
                    $.messager.alert("操作失败~~", data);  
                }  
            });  
        }  
    });  
}

function trimStr(str){ //删除左右两端的空格　　
   return str.replace(/(^s*)|(s*$)/g, "");
}
