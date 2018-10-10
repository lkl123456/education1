
$(function() {
	$('#orgForm').form({
		onSubmit: function(){
			return $(this).form('validate');
		},
		success:function(data){
			if(data == 'succ'){
				//$.messager.alert("提示","信息保存成功!");  
				close_div();
				doSearch();
			}else if(data == 'snnameExists'){
				$.messager.alert("提示","唯一标识已存在，请重新输入!");
			}
		}
	});
	
	// 隐藏列（机构ID）
	$('#orgList_table').datagrid('hideColumn','org_id');
})

// 点击查询按钮
function doSearch() {
	$("#orgList_table").datagrid('load', {
		s_org_customer_name : $('#s_org_customer_name').val()
	});
}

// 点击添加网校信息
function add_org(){
	$('#dlg').dialog({title: "添加网校"});
	$('#dlg').dialog('open');
	$('#orgForm').form('clear');
	// 初始化状态下拉列表
	$("#user_count_sel").combobox('setValue', 100);
}

// 点击编辑网校信息(加载显示相关信息)
function edit_org(){
    var selectRows = $('#orgList_table').datagrid("getSelections");  
    if (selectRows.length < 1 || selectRows.length > 1) {  
        $.messager.alert("提示", "请您选中一条需要编辑的信息！","info");  
        return;  
    }
    $('#dlg').dialog({title: "编辑网校"});
    $('#dlg').dialog('open');
    $('#orgForm').form('clear');
	//给表单赋值
	$('#orgForm').form('load',selectRows[0]);
}


// 点击取消 添加或编辑信息
function close_div(){
	$('#dlg').dialog('close');
}

// 点击保存 添加或编辑信息
function submitForm(){
	//ajax form提交
	$('#orgForm').submit();
}

// 点击删除（flag: -1表示删除，1表示开通，2表示禁用）
function del_org(flag) {  
    //把你选中的 数据查询出来。  
    var selectRows = $('#orgList_table').datagrid("getSelections");
    var t_info = "删除";
    var t_url= contextpath+"/org/delOrg";
    if(flag == 1){
    	t_info = "开通";
    	t_url= contextpath+"/org/updateOrg_kt";
    }else if(flag == 2){
    	t_info = "禁用";
    	t_url= contextpath+"/org/updateOrg_jy";
    }
    if (selectRows.length < 1) {  
        $.messager.alert("提示", "请选中要"+t_info+"的数据信息！","info");  
        return;  
    }  
 
    $.messager.confirm("确认", "您确定要"+t_info+"选中的信息吗？", function (r) {  
        if (r) {  
            var strIds = "";  
            for (var i = 0; i < selectRows.length; i++) {  
                strIds += selectRows[i].id + ",";  
            }  
            strIds = strIds.substr(0, strIds.length - 1);  
            $.post(t_url, { ids: strIds, flag: flag }, function (data) { 
                if (data == "succ") {  
                    //刷新表格，去掉选中状态的 那些行。  
                    $('#orgList_table').datagrid("reload");  
                    $('#orgList_table').datagrid("clearSelections");  
                } else {  
                    $.messager.alert("操作失败~~", data);  
                }  
            });  
        }  
    });  
}




